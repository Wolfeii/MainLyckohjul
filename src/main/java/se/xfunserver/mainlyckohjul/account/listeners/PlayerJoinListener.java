package se.xfunserver.mainlyckohjul.account.listeners;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import se.xfunserver.mainlyckohjul.LuckyWheel;
import se.xfunserver.mainlyckohjul.account.Account;
import se.xfunserver.mainlyckohjul.account.WheelAccount;
import se.xfunserver.mainlyckohjul.account.rank.Rank;
import se.xfunserver.mainlyckohjul.listeners.BaseListener;
import se.xfunserver.mainlyckohjul.utilities.logging.LogLevel;

import java.util.UUID;

public class PlayerJoinListener extends BaseListener {

    public PlayerJoinListener(LuckyWheel plugin) {
        super(plugin);
    }

    @EventHandler
    public void onAsyncPlayerPreLogin(AsyncPlayerPreLoginEvent event){
        Account account = null;
        try {
            account = getPlugin().getStorageManager().getSQLStorage().getAccount(event.getUniqueId());
        } catch (Exception exception) {
            // If failed -> Kick the user.
            event.setKickMessage(ChatColor.RED + "LuckyWheel failed to load your account." + ChatColor.RESET + "\n\n"
                    + ChatColor.WHITE + "If this keeps happening, please report this" + ChatColor.RESET + "\n"
                    + ChatColor.WHITE + "to your server administrator." + ChatColor.RESET + "\n\n"
                    + (exception.getMessage() != null ? ChatColor.WHITE + exception.getMessage() + ChatColor.RESET + "\n\n" : ""));
            event.setLoginResult(AsyncPlayerPreLoginEvent.Result.KICK_OTHER);
            getPlugin().getSpigotLogger().log(LogLevel.WARN, "Could not load the account of " + event.getName() + ".");
        }

        // If everything failed, end the connection
        if (account == null) {
            account = new WheelAccount(event.getUniqueId(), event.getName(), getPlugin().getRankManager().getDefaultRank());

            // Store new account in the database.
            if (!getPlugin().getStorageManager().getSQLStorage().storeAccount(account)) {
                getPlugin().getSpigotLogger().log(LogLevel.WARN, "Could not store " + event.getName() + "'s account in the database.");
            }
        }

        getPlugin().getAccountManager().add(event.getUniqueId(), account);
    }
}
