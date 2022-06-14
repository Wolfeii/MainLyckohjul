package se.xfunserver.mainlyckohjul.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import se.xfunserver.mainlyckohjul.LuckyWheel;
import se.xfunserver.mainlyckohjul.inventory.utils.WheelInventoryHolder;

public class InventoryListener extends BaseListener {

    public InventoryListener(LuckyWheel plugin) {
        super(plugin);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();

        if (event.getInventory().getHolder() instanceof WheelInventoryHolder) {
            WheelInventoryHolder holder = (WheelInventoryHolder) event.getInventory().getHolder();

            holder.selectMenuPoint(event);
        }
    }
}
