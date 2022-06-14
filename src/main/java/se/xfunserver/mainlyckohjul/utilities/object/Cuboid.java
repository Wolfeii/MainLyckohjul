package se.xfunserver.mainlyckohjul.utilities.object;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.jetbrains.annotations.NotNull;

import java.util.Iterator;

public class Cuboid implements Iterable<Block>, Cloneable {

    protected final String worldName;
    protected final int x1, y1, z1;
    protected final int x2, y2, z2;

    public Cuboid(Location locOne, Location locTwo) {
        if (!locOne.getWorld().equals(locTwo.getWorld())) {
            throw new IllegalArgumentException("Locations in Cuboid must be the same world.");
        }

        worldName = locOne.getWorld().getName();
        x1 = Math.min(locOne.getBlockX(), locTwo.getBlockX());
        y1 = Math.min(locOne.getBlockY(), locTwo.getBlockY());
        z1 = Math.min(locOne.getBlockZ(), locTwo.getBlockZ());
        x2 = Math.max(locOne.getBlockX(), locTwo.getBlockX());
        y2 = Math.max(locOne.getBlockY(), locTwo.getBlockY());
        z2 = Math.max(locOne.getBlockZ(), locTwo.getBlockZ());
    }

    private Cuboid(String worldName, int x1, int y1, int z1, int x2, int y2, int z2) {
        this.worldName = worldName;
        this.x1 = Math.min(x1, x2);
        this.x2 = Math.max(x1, x2);
        this.y1 = Math.min(y1, y2);
        this.y2 = Math.max(y1, y2);
        this.z1 = Math.min(z1, z2);
        this.z2 = Math.max(z1, z2);
    }

    public Location getLowerNE() {
        return new Location(getWorld(), x1, y1, z1);
    }

    public Location getUpperSW() {
        return new Location(getWorld(), x2, y2, z2);
    }

    public Location getCenter() {
        int x1 = getUpperX() + 1;
        int y1 = getUpperY() + 1;
        int z1 = getUpperZ() + 1;
        return new Location(getWorld(), getLowerX() + (x1 - getLowerX()) / 2.0,
                getLowerY() + (y1 - getLowerY()) / 2.0,
                getLowerZ() + (z1 - getLowerZ()) / 2.0);
    }

    public World getWorld() {
        World world = Bukkit.getWorld(worldName);
        if (world == null) {
            throw new IllegalStateException("world '" + worldName + "' is not loaded");
        }
        return world;
    }

    public int getSizeX() {
        return (x2 - x1) + 1;
    }

    public int getSizeY() {
        return (y2 - y1) + 1;
    }

    public int getSizeZ() {
        return (z2 - z1) + 1;
    }

    public int getLowerX() {
        return x1;
    }

    public int getLowerY() {
        return y1;
    }

    public int getLowerZ() {
        return z1;
    }

    public int getUpperX() {
        return x2;
    }

    public int getUpperY() {
        return y2;
    }

    public int getUpperZ() {
        return z2;
    }

    public Block[] corners() {
        Block[] res = new Block[8];
        World w = getWorld();
        res[0] = w.getBlockAt(x1, y1, z1);
        res[1] = w.getBlockAt(x1, y1, z2);
        res[2] = w.getBlockAt(x1, y2, z1);
        res[3] = w.getBlockAt(x1, y2, z2);
        res[4] = w.getBlockAt(x2, y1, z1);
        res[5] = w.getBlockAt(x2, y1, z2);
        res[6] = w.getBlockAt(x2, y2, z1);
        res[7] = w.getBlockAt(x2, y2, z2);
        return res;
    }

    public Cuboid expand(CuboidDirection dir, int amount) {
        switch (dir) {
            case North:
                return new Cuboid(worldName, x1 - amount, y1, z1, x2, y2, z2);
            case South:
                return new Cuboid(worldName, x1, y1, z1, x2 + amount, y2, z2);
            case East:
                return new Cuboid(worldName, x1, y1, z1 - amount, x2, y2, z2);
            case West:
                return new Cuboid(worldName, x1, y1, z1, x2, y2, z2 + amount);
            case Down:
                return new Cuboid(worldName, x1, y1 - amount, z1, x2, y2, z2);
            case Up:
                return new Cuboid(worldName, x1, y1, z1, x2, y2 + amount, z2);
            default:
                throw new IllegalArgumentException("invalid direction " + dir);
        }
    }

    @NotNull
    @Override
    public Iterator<Block> iterator() {
        return new CuboidIterator(getWorld(), x1, y1, z1, x2, y2, z2);
    }

    public class CuboidIterator implements Iterator<Block> {

        private World world;
        private int baseX, baseY, baseZ;
        private int x, y, z;
        private int sizeX, sizeY, sizeZ;

        public CuboidIterator(World world, int x1, int y1, int z1, int x2, int y2, int z2) {
            this.world = world;
            this.baseX = x1;
            this.baseY = y1;
            this.baseZ = z1;
            this.sizeX = Math.abs(x2 - x1) + 1;
            this.sizeY = Math.abs(y2 - y1) + 1;
            this.sizeZ = Math.abs(z2 - z1) + 1;
            x = y = z = 0;
        }

        @Override
        public boolean hasNext() {
            return x < sizeX && y < sizeY && z < sizeZ;
        }

        @Override
        public Block next() {
            Block block = world.getBlockAt(baseX + x, baseY + y, baseZ + z);
            if (++x >= sizeX) {
                x = 0;
                if (++y >= sizeY) {
                    y = 0;
                    ++z;
                }
            }

            return block;
        }

        @Override
        public void remove() {
            // Wont happen
        }
    }

    public enum CuboidDirection {

        North, East, South, West, Up, Down, Horizontal, Vertical, Both, Unknown;

        public CuboidDirection opposite() {
            switch(this) {
                case North:
                    return South;
                case East:
                    return West;
                case South:
                    return North;
                case West:
                    return East;
                case Horizontal:
                    return Vertical;
                case Vertical:
                    return Horizontal;
                case Up:
                    return Down;
                case Down:
                    return Up;
                case Both:
                    return Both;
                default:
                    return Unknown;
            }
        }
    }
}
