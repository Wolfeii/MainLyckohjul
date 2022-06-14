package se.xfunserver.mainlyckohjul.wheel.object;

import org.bukkit.Location;
import org.bukkit.Material;
import org.jetbrains.annotations.NotNull;
import se.xfunserver.mainlyckohjul.wheel.Wheel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class WheelSection {

    private Wheel wheel;
    private List<String> commands;
    private List<Location> blockLocs;
    private Material blockType;

    public WheelSection(final @NotNull List<Location> locations,
                        @NotNull Wheel wheel
    ) {
        this.blockLocs = locations;
        this.wheel = wheel;

        this.blockType = this.getBlockLocs().get(0)
                .getBlock()
                .getType();
    }

    public WheelSection() {
        this.commands = new ArrayList<>();
        this.blockLocs = new ArrayList<>();
    }

    public WheelSection associatedWheel(@NotNull Wheel wheel) {
        this.wheel = wheel;
        return this;
    }

    public WheelSection blockLocations(Location... locations) {
        this.blockLocs.addAll(Arrays.asList(locations));
        return this;
    }

    public WheelSection blockType(Material blockType) {
        this.blockType = blockType;
        return this;
    }

    public WheelSection defaultBlockType() {
        this.blockType = getBlockLocs().get(0).getWorld().getType(getBlockLocs().get(0));
        return this;
    }

    public void replaceBlocks(@NotNull Material replaceTo) {
        for (final Location location : getBlockLocs()) {
            location.getWorld().getBlockAt(location)
                    .setType(replaceTo);
        }

        this.blockType = replaceTo;
    }


    public Material getBlockType() {
        return this.blockType;
    }

    public List<Location> getBlockLocs() {
        return this.blockLocs;
    }
}
