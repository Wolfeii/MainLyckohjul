package se.xfunserver.mainlyckohjul.wheel;

import jdk.jfr.Event;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.BlockFace;
import org.bukkit.configuration.ConfigurationSection;
import org.jetbrains.annotations.NotNull;
import se.xfunserver.mainlyckohjul.LuckyWheel;
import se.xfunserver.mainlyckohjul.utilities.config.ConfigUtils;
import se.xfunserver.mainlyckohjul.utilities.logging.LogLevel;
import se.xfunserver.mainlyckohjul.utilities.wheel.WheelUtils;
import se.xfunserver.mainlyckohjul.wheel.object.CircleColor;
import se.xfunserver.mainlyckohjul.wheel.object.EventData;

import java.util.*;
import java.util.stream.Collectors;

public final class WheelManager {

    private final NavigableMap<CircleColor, EventData> colorEvents = new TreeMap<>();
    private final LuckyWheel luckyWheel;

    private Wheel wheel;

    public WheelManager(@NotNull LuckyWheel luckyWheel) {
        this.luckyWheel = luckyWheel;
    }

    public boolean setup() {
        ConfigurationSection colorSection = luckyWheel.getSettingsConfig().getConfigurationSection("Wheel.Events");
        if (colorSection != null && colorSection.getKeys(false).size() > 0) {
            for (String colorEvent : colorSection.getKeys(false)) {
                CircleColor circleColor = CircleColor.valueOf(colorEvent.toUpperCase());
                EventData eventData = new EventData(colorEvent,
                        colorSection.getString(colorEvent + ".Command"),
                        circleColor);

                luckyWheel.getSpigotLogger().log(LogLevel.SUCCESS, "Successfully loaded Color Event for color: " + colorEvent);

                colorEvents.put(circleColor, eventData);
            }
        }

        this.wheel = new Wheel(luckyWheel,
                ConfigUtils.getLocation(luckyWheel.getSettingsConfig().getString("Wheel.Location.Center")),
                ConfigUtils.getLocation(luckyWheel.getSettingsConfig().getString("Wheel.Location.CornerOne")),
                ConfigUtils.getLocation(luckyWheel.getSettingsConfig().getString("Wheel.Location.CornerTwo")),
                BlockFace.valueOf(luckyWheel.getSettingsConfig().getString("Wheel.Location.Facing")),
                Sound.valueOf(luckyWheel.getSettingsConfig().getString("Wheel.Sounds.Tick")),
                Sound.valueOf(luckyWheel.getSettingsConfig().getString("Wheel.Sounds.Win")));

        if (!wheel.registerSections(luckyWheel.getSettingsConfig().getStringList("Wheel.IgnoredMaterials")
                .stream().map(Material::getMaterial)
                .collect(Collectors.toList()))) {
            luckyWheel.getSpigotLogger().log(LogLevel.ERROR, "No sections were loaded.");
            return false;
        }

        luckyWheel.getSpigotLogger().log(LogLevel.SUCCESS, "Successfully loaded " + colorEvents.size() + " events.");
        return true;
    }

    public Wheel getWheel() {
        return wheel;
    }
}
