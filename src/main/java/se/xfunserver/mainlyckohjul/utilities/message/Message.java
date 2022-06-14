package se.xfunserver.mainlyckohjul.utilities.message;

import se.xfunserver.mainlyckohjul.LuckyWheel;
import se.xfunserver.mainlyckohjul.utilities.chat.Color;

public enum Message {

    PREFIX("Prefix"),
    PLAYER_OFFLINE("Command.Player.Offline"),
    NOT_FOR_CONSOLE("Command.Player.NotForConsole"),
    NO_PERMISSION("Command.NoPermission"),
    COMMAND_USAGE("Command.Usage"),
    CONFIGS_RELOADED("Command.Reloaded"),
    NO_WHEEL("Wheel.NoWheel"),
    STARTING("Wheel.Starting"),
    NO_EVENT("Wheel.NoEvent"),
    EVENT("Wheel.Event");

    private static LuckyWheel luckyWheel;

    public static void setLuckyWheel(LuckyWheel wheel) {
        luckyWheel = wheel;
    }

    private String path;

    Message(String path) {
        this.path = path;
    }

    public String getMessage() {
        return Color.translate(luckyWheel.getMessagesConfig().getString(path));
    }

}
