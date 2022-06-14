package se.xfunserver.mainlyckohjul.wheel.section;

import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.block.Block;
import se.xfunserver.mainlyckohjul.utilities.wheel.WheelUtils;
import se.xfunserver.mainlyckohjul.wheel.Wheel;
import se.xfunserver.mainlyckohjul.wheel.object.CircleColor;

import java.util.List;

@Getter
public class CircleSection {

    private CircleColor color;
    private final List<Block> blocks;

    public CircleSection(Location location) {
        this.color = CircleColor.valueOf(location.getBlock().getType().name().replace("_TERRACOTTA", ""));
        this.blocks = WheelUtils.getSection(location);
    }

    public void setColor(CircleColor color) {
        for (Block block : blocks) {
            block.setType(color.getAsMaterial());
        }

        this.color = color;
    }

    public List<Block> getBlocks() {
        return blocks;
    }
}
