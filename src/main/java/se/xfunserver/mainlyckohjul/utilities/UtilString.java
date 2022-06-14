package se.xfunserver.mainlyckohjul.utilities;

import org.bukkit.ChatColor;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class UtilString {

    public static String colorize(@NotNull String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public static List<String> colorize(List<String> messages) {
        List<String> translated = new ArrayList<>();
        for (String str : messages) {
            translated.add(colorize(str));
        }
        return translated;
    }


    public static String formatDate(long milliseconds) {
        long hours = TimeUnit.MILLISECONDS.toHours(milliseconds);
        milliseconds -= TimeUnit.HOURS.toMillis(hours);

        long minutes = TimeUnit.MILLISECONDS.toMinutes(milliseconds);
        milliseconds -= TimeUnit.MINUTES.toMillis(minutes);

        long seconds = TimeUnit.MILLISECONDS.toSeconds(milliseconds);
        milliseconds -= TimeUnit.SECONDS.toMillis(seconds);

        return (hours >= 1 ? hours + " timmar, " : "")
                + (minutes >= 1 ? minutes + " minuter, " : "")
                + (seconds >= 0 ? seconds + " sekunder, " : "");
    }
}
