package se.xfunserver.mainlyckohjul.listeners;

import org.bukkit.event.Listener;
import se.xfunserver.mainlyckohjul.LuckyWheel;
import se.xfunserver.mainlyckohjul.account.listeners.PlayerJoinListener;
import se.xfunserver.mainlyckohjul.utilities.logging.LogLevel;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class ListenerManager {

    private final LuckyWheel plugin;
    private final Set<Listener> listeners;

    public ListenerManager(LuckyWheel plugin) {
        this.plugin = plugin;
        this.listeners = new HashSet<>();

        // Add listeners here:
        Collections.addAll(listeners,
                new PlayerJoinListener(plugin),
                new InventoryListener(plugin)
        );
    }

    public void registerAll() {
        for (Listener listener : listeners) {
            plugin.getServer().getPluginManager().registerEvents(listener, plugin);
        }

        plugin.getSpigotLogger().log(LogLevel.DEBUG, "Registered a total of " + listeners.size() + " event listeners.");
    }
}


