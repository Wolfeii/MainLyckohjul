package se.xfunserver.mainlyckohjul.account;

public enum AccountField {

    UUID("UUID", false, true),
    NAME("Name", true, true),
    RANK("Rank", true, true),
    FIRST_PLAYED("FirstPlayed", false, true),
    LAST_PLAYED("LastPlayed", true, true),
    TIMES_PLAYED("TimesPlayed", true, true);

    private final String column;
    private final boolean set, get;

    AccountField(String column, boolean set, boolean get) {
        this.column = column;
        this.set = set;
        this.get = get;
    }

    public String getSqlColumn() {
        return column;
    }

    public boolean canSet() {
        return set;
    }

    public boolean canGet() {
        return get;
    }
}
