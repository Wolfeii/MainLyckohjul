package se.xfunserver.mainlyckohjul.utilities.message;

import se.xfunserver.mainlyckohjul.LuckyWheel;

public class MessageManager {

    private final LuckyWheel plugin;

    public MessageManager(LuckyWheel plugin) {
        this.plugin = plugin;
    }

    public boolean setup() {
        Message.setLuckyWheel(plugin);

        return true;
    }
}
