package se.xfunserver.mainlyckohjul.utilities.config;

import org.bukkit.Bukkit;
import org.bukkit.Location;

public class ConfigUtils {

    public static Location getLocation(String input) {
        if (input == null) {
            return null;
        }

        String[] splitString = input.split(" ");
        if (splitString.length != 4) {
            throw new IllegalArgumentException("Location must contain four different spacers.");
        }

        return new Location(Bukkit.getWorld(splitString[0]),
                Double.parseDouble(splitString[1]),
                Double.parseDouble(splitString[2]),
                Double.parseDouble(splitString[3]));
    }
}
