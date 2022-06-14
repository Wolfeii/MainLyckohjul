package se.xfunserver.mainlyckohjul.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import se.xfunserver.mainlyckohjul.LuckyWheel;
import se.xfunserver.mainlyckohjul.inventory.SectionGUI;
import se.xfunserver.mainlyckohjul.wheel.Wheel;

public class TestCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length == 1) {
            LuckyWheel.getInstance().getWheelManager().getWheel().shift();
            return true;
        }

        SectionGUI.open(LuckyWheel.getInstance(), (Player) commandSender);
        return true;
    }
}
