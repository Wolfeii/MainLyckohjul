package se.xfunserver.mainlyckohjul.wheel.object;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import se.xfunserver.mainlyckohjul.LuckyWheel;

import java.util.ArrayList;
import java.util.List;

public enum CircleColor {

    BLACK("BLACK_TERRACOTTA"),
    BLUE("BLUE_TERRACOTTA"),
    GREEN("GREEN_TERRACOTTA"),
    RED("RED_TERRACOTTA"),
    PURPLE("PURPLE_TERRACOTTA"),
    ORANGE("ORANGE_TERRACOTTA"),
    LIGHT_GRAY("LIGHT_GRAY_TERRACOTTA"),
    GRAY("GRAY_TERRACOTTA"),
    LIGHT_BLUE("LIGHT_BLUE_TERRACOTTA"),
    LIME("LIME_TERRACOTTA"),
    CYAN("CYAN_TERRACOTTA"),
    PINK("PINK_TERRACOTTA"),
    MAGENTA("MAGENTA_TERRACOTTA"),
    YELLOW("YELLOW_TERRACOTTA"),
    WHITE("WHITE_TERRACOTTA");

    private String materialColorName;

    CircleColor(final String materialColorName) {
        this.materialColorName = materialColorName;
    }

    public ChatColor getChatColor() {
        return ChatColor.valueOf(toString());
    }

    public String beautifulName() {
        final List<String> result = new ArrayList<>();

        for (String part : toString().split("_")) {
            result.add(part.charAt(0) + part.substring(1).toLowerCase());
        }

        return getChatColor().toString() + String.join(" ", result);
    }

    public Material getAsMaterial() {
        return Material.getMaterial(materialColorName);
    }
}
