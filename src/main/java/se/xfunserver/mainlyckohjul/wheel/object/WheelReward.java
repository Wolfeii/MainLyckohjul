package se.xfunserver.mainlyckohjul.wheel.object;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import se.xfunserver.mainlyckohjul.wheel.Wheel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class WheelReward {

    private Wheel wheel;
    private List<String> commands;
    private Material blockType;

    public WheelReward(@NotNull Material blockType, List<String> commands, @NotNull Wheel wheel) {
        this.wheel = wheel;
        this.commands = commands;
        this.blockType = blockType;
    }

    public void win(@NotNull Player player) {
        this.commands.forEach(command ->
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
                        command.replace("{player}", player.getName())
                )
        );
    }

    public Wheel getWheel() {
        return this.wheel;
    }

    public Material getBlockType() {
        return this.blockType;
    }

    public List<String> getCommands() {
        return this.commands;
    }
}
