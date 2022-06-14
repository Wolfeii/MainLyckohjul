package se.xfunserver.mainlyckohjul.utilities.wheel;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import se.xfunserver.mainlyckohjul.utilities.logging.LogLevel;
import se.xfunserver.mainlyckohjul.wheel.Wheel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class WheelUtils {

    public static List<Block> getSection(Location startLoc) {
        return getSection(new ArrayList<>(), startLoc, startLoc.getBlock().getType(), 0);
    }

    public static List<Block> getSection(List<Block> result, Location startLoc, Material type, int curDepth) {
        if (curDepth >= 250) {
            return result;
        }

        World world = startLoc.getWorld();
        if (world == null) {
            return result;
        }

        Material material = world.getBlockAt(startLoc).getType();
        if (material.isAir() || material != type) return result;

        result.add(startLoc.getBlock());

        for (BlockFace blockFace : BlockFace.values()) {
            Block relativeBlock = startLoc.getBlock().getRelative(blockFace);
            if (relativeBlock.getType() == type && result.stream().filter(block -> block.getLocation().equals(relativeBlock.getLocation())).findFirst().orElse(null) == null) {
                result.add(relativeBlock);
                result = getSection(result, relativeBlock.getLocation(), type, curDepth + 1);
            }
        }

        return result;
    }


}
