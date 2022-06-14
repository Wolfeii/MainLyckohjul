package se.xfunserver.mainlyckohjul.storage;

import se.xfunserver.mainlyckohjul.LuckyWheel;
import se.xfunserver.mainlyckohjul.storage.sql.HikariStorage;

public class StorageManager {

    private final LuckyWheel plugin;

    private AccountStorage sql;

    public StorageManager(LuckyWheel plugin) {
        this.plugin = plugin;
    }

    public boolean setup() {
        return setupSqlStorage();
    }

    private boolean setupSqlStorage() {

        String host = plugin.getStorageConfig().getString("Login.Host");
        int port = plugin.getStorageConfig().getInt("Login.Port");
        String username = plugin.getStorageConfig().getString("Login.User");
        String password = plugin.getStorageConfig().getString("Login.Password");
        String database = plugin.getStorageConfig().getString("Login.Database");
        boolean ssl = plugin.getStorageConfig().getBoolean("Login.SSL");
        this.sql = new HikariStorage(plugin, host, username, password, database, port, ssl);

        return sql.createStorage();
    }

    public AccountStorage getSQLStorage() {
        return sql;
    }
}
