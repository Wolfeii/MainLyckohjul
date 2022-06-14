package se.xfunserver.mainlyckohjul.account;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import se.xfunserver.mainlyckohjul.LuckyWheel;
import se.xfunserver.mainlyckohjul.account.rank.Rank;
import se.xfunserver.mainlyckohjul.utilities.logging.LogLevel;

import java.util.*;

public class AccountManager {

    private final LuckyWheel plugin;
    private final Map<UUID, Account> accounts;

    public AccountManager(LuckyWheel plugin) {
        this.plugin = plugin;
        this.accounts = new HashMap<>();
    }

    public void loadAll() {
        for (Player player : plugin.getServer().getOnlinePlayers()) {
            Account account;
            try {
                account = plugin.getStorageManager().getSQLStorage().getAccount(player.getUniqueId());
                add(player.getUniqueId(), account);
            } catch (Exception exception) {
                // If failed -> Kick the user.
                player.kickPlayer(ChatColor.RED + "LuckyWheel failed to load your account." + ChatColor.RESET + "\n\n"
                        + ChatColor.WHITE + "If this keeps happening, please report this" + ChatColor.RESET + "\n"
                        + ChatColor.WHITE + "to your server administrator." + ChatColor.RESET + "\n\n"
                        + (exception.getMessage() != null ? ChatColor.WHITE + exception.getMessage() + ChatColor.RESET + "\n\n" : ""));
                plugin.getSpigotLogger().log(LogLevel.WARN, "Could not reload the account of " + player.getName() + ".");
            }
        }
    }

    public void add(UUID uuid, Account account) {
        accounts.put(uuid, account);
    }

    public boolean has(UUID uuid) {
        return accounts.containsKey(uuid);
    }

    public Account getAccount(UUID uuid) {
        return accounts.get(uuid);
    }
}
