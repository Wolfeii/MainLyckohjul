package se.xfunserver.mainlyckohjul.wheel.object;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

@Data
@AllArgsConstructor
public class EventData {

    private String name;
    private String command;
    private CircleColor circleColor;

    public void execute(Player player) {
        if (player == null)
            return;

        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command
                .replace("%player%", player.getName())
                .replace("%uuid%", player.getUniqueId().toString())
                .replace("%display-name%", player.getDisplayName()));
    }
}
