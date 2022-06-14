package se.xfunserver.mainlyckohjul.account.rank;


import se.xfunserver.mainlyckohjul.LuckyWheel;
import se.xfunserver.mainlyckohjul.utilities.files.Config;
import se.xfunserver.mainlyckohjul.utilities.logging.LogLevel;

import java.util.*;

public class RankManager {

    private final LuckyWheel plugin;
    private final Set<Rank> ranks;

    private Rank defaultRank;

    public RankManager(LuckyWheel plugin) {
        this.plugin = plugin;
        this.ranks = new HashSet<>();
    }

    public boolean loadAllRanks(Config ranksConfig) {
        Set<String> section = ranksConfig.getKeys(false);
        for (String name : section) {
            if (name.length() > 16) {
                plugin.getSpigotLogger().log(LogLevel.WARN, "Tried to load rank '" + name + "', but it has an invalid " +
                        "name. It should not be longer than 16 characters. Skipping it...");
                continue;
            }

            boolean operator = ranksConfig.getBoolean(name + ".Operator");
            long cooldown = ranksConfig.getLong(name + ".Cooldown");

            Rank rank = new WheelRank(name, cooldown, operator);
            ranks.add(rank);
        }

        plugin.getSpigotLogger().log(LogLevel.DEBUG, "Loaded " + ranks.size() + " ranks from your ranks.yml.");

        this.defaultRank = getRank(plugin.getSettingsConfig().getString("DefaultRank")).orElse(null);
        if (defaultRank == null) {
            plugin.getSpigotLogger().log(LogLevel.FATAL, "Default rank isn't specified in your 'settings.yml'.");
            return false;
        }

        plugin.getSpigotLogger().log(LogLevel.DEBUG, "Set \"" + defaultRank.getName() + "\" as the default rank.");
        return true;
    }

    public Optional<Rank> getRank(String name) {
        return ranks.stream().filter(rank -> rank.getName().equalsIgnoreCase(name)).findFirst();
    }

    public Rank getDefaultRank() {
        return defaultRank;
    }
}