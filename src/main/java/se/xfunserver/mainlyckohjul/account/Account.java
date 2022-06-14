package se.xfunserver.mainlyckohjul.account;

import se.xfunserver.mainlyckohjul.account.rank.Rank;

import java.util.UUID;

public interface Account {

    UUID getUniqueId();

    String getName();

    Rank getRank();

    boolean isOnline();

    void setRank(Rank rank);
}
