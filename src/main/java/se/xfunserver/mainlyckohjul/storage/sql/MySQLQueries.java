package se.xfunserver.mainlyckohjul.storage.sql;

public class MySQLQueries {

    public static String ACCOUNT_CREATE_TABLE = "CREATE TABLE IF NOT EXISTS LuckyWheelAccount (" +
            "UUID VARCHAR(36) PRIMARY KEY NOT NULL, Name VARCHAR(16) NOT NULL, Rank VARCHAR(16) NOT NULL, " +
            "FirstPlayed BIGINT(50) NOT NULL, LastPlayed BIGINT(50) NOT NULL, TimesPlayed BIGINT(50) NOT NULL);";

    public static String ACCOUNT_GET_FROM_UUID = "SELECT UUID, Name, Rank, FirstPlayed, LastPlayed, TimesPlayed FROM LuckyWheelAccount WHERE UUID=?;";

    public static String ACCOUNT_INSERT_NEW = "INSERT INTO LuckyWheelAccount VALUES(?,?,?,?,?,?);";

    public static String ACCOUNT_GET_FIELD = "SELECT %field% FROM LuckyWheelAccount WHERE UUID=?;";

    public static String ACCOUNT_UPDATE_FIELD = "UPDATE LuckyWheelAccount SET %field%=? WHERE UUID=?";
}
