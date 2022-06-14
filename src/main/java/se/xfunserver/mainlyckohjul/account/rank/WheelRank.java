package se.xfunserver.mainlyckohjul.account.rank;

public class WheelRank implements Rank {

    private final String name;
    private final long cooldown;
    private final boolean operator;

    public WheelRank(String name, long cooldown, boolean operator) {
        this.name = name;
        this.cooldown = cooldown;
        this.operator = operator;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public long getCooldown() {
        return cooldown;
    }

    @Override
    public boolean isOperator() {
        return operator;
    }
}
