package se.xfunserver.mainlyckohjul.storage.sql;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.bukkit.craftbukkit.libs.org.apache.http.annotation.Obsolete;
import se.xfunserver.mainlyckohjul.LuckyWheel;
import se.xfunserver.mainlyckohjul.account.Account;
import se.xfunserver.mainlyckohjul.account.AccountField;
import se.xfunserver.mainlyckohjul.account.WheelAccount;
import se.xfunserver.mainlyckohjul.account.rank.Rank;
import se.xfunserver.mainlyckohjul.storage.AccountStorage;
import se.xfunserver.mainlyckohjul.utilities.logging.LogLevel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class HikariStorage implements AccountStorage {

    private final LuckyWheel plugin;
    private final String host, user, password, database;
    private final int port;
    private final boolean ssl;

    private HikariDataSource dataSource;
    private boolean operational;

    public HikariStorage(LuckyWheel plugin, String host, String user, String password, String database, int port, boolean ssl) {
        this.plugin = plugin;
        this.host = host;
        this.user = user;
        this.password = password;
        this.database = database;
        this.port = port;
        this.ssl = ssl;

        this.operational = false;
    }

    @Override
    public boolean createStorage() {
        plugin.getSpigotLogger().log(LogLevel.DEBUG, "Creating Hikari connection pool...");
        try {
            HikariConfig config = new HikariConfig();
            config.setJdbcUrl("jdbc:mysql://" + host + ":" + port + "/" + database + "?useSSL=" + ssl);
            config.setUsername(user);
            config.setPassword(password);
            dataSource = new HikariDataSource(config);
        } catch (Exception exception) {
            plugin.getSpigotLogger().log(LogLevel.FATAL, "Could not create Hikari connection pool.");
            plugin.getSpigotLogger().log(LogLevel.ERROR, exception.getMessage());
            return false;
        }
        plugin.getSpigotLogger().log(LogLevel.INFO, "Created Hikari connection pool " +
                "(" + user + "@" + host + ":" + port + ")");
        return true;
    }

    @Override
    public boolean testStorage() {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("SELECT 1+1 as 'result';");
            ResultSet resultSet = statement.executeQuery();

            boolean success = resultSet.next() && resultSet.getInt("result") == 2;

            resultSet.close();
            statement.close();

            if (!success) {
                plugin.getSpigotLogger().log(LogLevel.FATAL, "Could not create Hikari connection pool.");
                return false;
            }
        } catch (SQLException exception) {
            plugin.getSpigotLogger().log(LogLevel.FATAL, "Could not create Hikari connection pool.");
            plugin.getSpigotLogger().log(LogLevel.ERROR, exception.getMessage());
            return false;
        }

        this.operational = true;
        return true;
    }

    @Override
    public boolean createPrerequisites() {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(MySQLQueries.ACCOUNT_CREATE_TABLE);
            statement.execute();
            statement.close();
        } catch (SQLException exception) {
            plugin.getSpigotLogger().log(LogLevel.FATAL, "Could not create \"LuckyWheelAccount\" table.");
            plugin.getSpigotLogger().log(LogLevel.ERROR, exception.getMessage());
            return false;
        }

        return true;
    }

    @Override
    public void disable() {
        dataSource.close();
    }

    @Override
    public boolean isOperational() {
        return operational;
    }

    @Override
    public Account getAccount(UUID uuid) throws Exception {
        if (uuid == null) {
            throw new IllegalArgumentException("UUID cannot be null.");
        }

        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(MySQLQueries.ACCOUNT_GET_FROM_UUID);
            statement.setString(1, uuid.toString());
            ResultSet resultSet = statement.executeQuery();

            if (!resultSet.next()) {
                // Return nothing, account doesn't exist.
                return null;
            }

            String name = resultSet.getString("Name");
            String rankName = resultSet.getString("Rank");
            Rank rank = plugin.getRankManager().getRank(rankName).orElse(null);

            resultSet.close();
            statement.close();

            if (rank == null) {
                plugin.getSpigotLogger().log(LogLevel.FATAL, name + " has a rank that doesn't exist! (Rank: " + rankName + ").");
                throw new Exception("Your rank \"" + rankName + "\" doesn't exist in our system.");
            }

            return new WheelAccount(uuid, name, rank);
        } catch (SQLException exception) {
            plugin.getSpigotLogger().log(LogLevel.ERROR, exception.getMessage());
            throw new Exception();
        }
    }

    @Override
    public boolean storeAccount(Account account) {
        if (account == null) {
            throw new IllegalArgumentException("Account cannot be null.");
        }

        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(MySQLQueries.ACCOUNT_INSERT_NEW);
            statement.setString(1, account.getUniqueId().toString());
            statement.setString(2, account.getName());
            statement.setString(3, account.getRank().getName());
            statement.setLong(4, System.currentTimeMillis());
            statement.setLong(5, System.currentTimeMillis());
            statement.setLong(6, 0);
            statement.execute();
            statement.close();
        } catch (SQLException exception) {
            plugin.getSpigotLogger().log(LogLevel.ERROR, exception.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public void update(UUID uuid, AccountField field, Object value) {
        if (uuid == null || field == null || value == null) {
            throw new IllegalArgumentException("UUID, Field or Value cannot be null.");
        }

        if (!field.canSet()) {
            throw new IllegalStateException("The specified field cannot be set.");
        }

        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(MySQLQueries.ACCOUNT_UPDATE_FIELD.replace("%field%", field.getSqlColumn()));
            statement.setObject(1, value);
            statement.setString(2, uuid.toString());
            statement.execute();
        } catch (SQLException exception) {
            plugin.getSpigotLogger().log(LogLevel.ERROR, exception.getMessage());
        }
    }

    @Override
    public Object get(UUID uuid, AccountField field) {
        if (uuid == null || field == null) {
            throw new IllegalArgumentException("UUID or Field cannot be null.");
        }

        if (!field.canGet()) {
            throw new IllegalStateException("The specified field cannot be get.");
        }

        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(MySQLQueries.ACCOUNT_GET_FIELD.replace("%field%", field.getSqlColumn()));
            statement.setString(1, uuid.toString());
            ResultSet result = statement.executeQuery();

            if (result.next()) {
                return result.getObject(field.getSqlColumn());
            }
        } catch (SQLException exception) {
            plugin.getSpigotLogger().log(LogLevel.ERROR, exception.getMessage());
        }

        return null;
    }
}
