package se.xfunserver.mainlyckohjul;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import se.xfunserver.mainlyckohjul.account.AccountManager;
import se.xfunserver.mainlyckohjul.account.rank.RankManager;
import se.xfunserver.mainlyckohjul.command.TestCommand;
import se.xfunserver.mainlyckohjul.listeners.ListenerManager;
import se.xfunserver.mainlyckohjul.storage.StorageManager;
import se.xfunserver.mainlyckohjul.utilities.files.Config;
import se.xfunserver.mainlyckohjul.utilities.logging.LogLevel;
import se.xfunserver.mainlyckohjul.utilities.logging.SpigotLogger;
import se.xfunserver.mainlyckohjul.utilities.message.Message;
import se.xfunserver.mainlyckohjul.utilities.message.MessageManager;
import se.xfunserver.mainlyckohjul.wheel.WheelManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
public final class LuckyWheel extends JavaPlugin {

    // Plugin Instance.
    private static LuckyWheel instance;

    // Plugin Configuration.
    private final Config settingsConfig = new Config(this, "settings.yml", "settings.yml");
    private final Config messagesConfig = new Config(this, "messages.yml", "messages.yml");
    private final Config storageConfig = new Config(this, "storage.yml", "storage.yml");
    private final Config ranksConfig = new Config(this, "ranks.yml", "ranks.yml");

    // Managers.
    private final ListenerManager listenerManager = new ListenerManager(this);
    private final MessageManager messageManager = new MessageManager(this);
    private final AccountManager accountManager = new AccountManager(this);
    private final StorageManager storageManager = new StorageManager(this);
    private final WheelManager wheelManager = new WheelManager(this);
    private final RankManager rankManager = new RankManager(this);

    // Logging.
    private SpigotLogger spigotLogger;

    public LuckyWheel() {
        instance = this;
    }

    @Override
    public void onEnable() {
        spigotLogger = new SpigotLogger("LuckyWheel", ChatColor.AQUA, ChatColor.GRAY, LogLevel.DEBUG);

        // Load all the configuration files.
        if (!setupConfigs(settingsConfig, messagesConfig, storageConfig, ranksConfig)) {
            // If failed -> Stop enabling the plugin any further.
            spigotLogger.log(LogLevel.FATAL, "Failed to load configuration files.");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        // Set up all the storage systems.
        if (!storageManager.setup()) {
            // If failed -> Stop enabling the plugin any further.
            spigotLogger.log(LogLevel.FATAL, "Failed to set up storage systems.");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        // Set up the main wheel.
        if (!wheelManager.setup()) {
            // If failed -> stop enabling the plugin any further.
            spigotLogger.log(LogLevel.FATAL, "Failed to set up main wheel.");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        // Set up all the internal messages.
        if (!messageManager.setup()) {
            // if failed -> stop enabling the plugin any further.
            spigotLogger.log(LogLevel.FATAL, "Failed to set up internal messages.");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        // Set up all the internal ranks.
        if (!rankManager.loadAllRanks(ranksConfig)) {
            // If failed -> stop enabling the plugin any further.
            spigotLogger.log(LogLevel.FATAL, "Failed to set up the wheel ranks.");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        Bukkit.getScheduler().runTaskAsynchronously(this, () -> {
            if (!storageManager.getSQLStorage().testStorage()) {
                // If failed -> Disable LuckyWheel.
            }

            if (!storageManager.getSQLStorage().createPrerequisites()) {
                // If failed -> Disable LuckyWheel.
            }
        });

        getCommand("spin").setExecutor(new TestCommand());
        listenerManager.registerAll();

        spigotLogger.log(LogLevel.SUCCESS, "Enabled plugin.");
    }

    @Override
    public void onDisable() {
        if (storageManager.getSQLStorage().isOperational()) {
            storageManager.getSQLStorage().disable();
        }

        spigotLogger.log(LogLevel.SUCCESS, "Disabled plugin.");
    }

    private boolean setupConfigs(Config... configs) {
        for (Config config : configs) {
            if (!config.createIfNotExists()) {
                return false;
            }

            config.reload();
        }

        return true;
    }

    public static LuckyWheel getInstance() {
        return instance;
    }
}
