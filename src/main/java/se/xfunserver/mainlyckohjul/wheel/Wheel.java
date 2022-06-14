package se.xfunserver.mainlyckohjul.wheel;

import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitTask;
import org.jetbrains.annotations.NotNull;
import se.xfunserver.mainlyckohjul.LuckyWheel;
import se.xfunserver.mainlyckohjul.account.AccountField;
import se.xfunserver.mainlyckohjul.account.WheelAccount;
import se.xfunserver.mainlyckohjul.utilities.UtilNumber;
import se.xfunserver.mainlyckohjul.utilities.logging.LogLevel;
import se.xfunserver.mainlyckohjul.utilities.object.Cuboid;
import se.xfunserver.mainlyckohjul.utilities.wheel.WheelState;
import se.xfunserver.mainlyckohjul.wheel.object.CircleColor;
import se.xfunserver.mainlyckohjul.wheel.object.WheelReward;
import se.xfunserver.mainlyckohjul.wheel.object.WheelSection;
import se.xfunserver.mainlyckohjul.wheel.section.CircleSection;


import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Getter
public final class Wheel {

    private final LuckyWheel plugin;

    private Location middleLocation, cornerOne, cornerTwo;
    private BlockFace wheelFace;
    private List<CircleSection> sections;

    private Sound tickSound;
    private Sound winSound;

    private Player player;
    private WheelState currentState;

    public Wheel(LuckyWheel plugin, Location middleLocation, Location cornerOne, Location cornerTwo, BlockFace wheelFace, Sound tickSound, Sound winSound) {
        this.plugin = plugin;
        this.middleLocation = middleLocation;
        this.cornerOne = cornerOne;
        this.cornerTwo = cornerTwo;
        this.wheelFace = wheelFace;
        this.tickSound = tickSound;
        this.winSound = winSound;
    }

    public boolean startSpin(Player player) {
        return false;
    }

    public void shift() {
        List<CircleColor> backupMat = sections.stream()
                .map(CircleSection::getColor)
                .toList();

        for (int i = 0; i < sections.size(); i++) {
            sections.get(i).setColor(
                    backupMat.get(i == 0
                            ? sections.size() - 1
                            : i - 1));
        }
    }

    public boolean registerSections(List<Material> ignoredMaterials) {
        Cuboid wheelCuboid = new Cuboid(cornerOne, cornerTwo);
        List<CircleSection> sections = new ArrayList<>();

        for (Block cuboidBlock : wheelCuboid) {
            List<Block> blocks = new ArrayList<>();
            sections.stream().map(CircleSection::getBlocks).forEach(blocks::addAll);

            if (cuboidBlock.getType().isAir() || ignoredMaterials.contains(cuboidBlock.getType()) || blocks.contains(cuboidBlock)) {
                continue;
            }

            sections.add(new CircleSection(cuboidBlock.getLocation()));
        }

        plugin.getSpigotLogger().log(LogLevel.DEBUG, sections.size() + " sections were loaded.");
        this.sections = sections;

        return sections.size() > 0;
    }

}
