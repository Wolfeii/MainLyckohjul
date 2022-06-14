package se.xfunserver.mainlyckohjul.storage;

public interface Storage {

    boolean createStorage();

    boolean testStorage();

    default boolean createPrerequisites() {
        return true;
    }

    boolean isOperational();

    default void disable() {

    }
}
