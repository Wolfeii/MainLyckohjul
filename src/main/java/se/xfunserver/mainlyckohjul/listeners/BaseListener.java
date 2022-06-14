package se.xfunserver.mainlyckohjul.listeners;

import org.bukkit.event.Listener;
import se.xfunserver.mainlyckohjul.LuckyWheel;

public abstract class BaseListener implements Listener {

    private final LuckyWheel plugin;

    public BaseListener(LuckyWheel plugin) {
        this.plugin = plugin;
    }

    public LuckyWheel getPlugin() {
        return plugin;
    }
}
