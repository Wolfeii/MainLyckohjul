package se.xfunserver.mainlyckohjul.inventory.utils;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;

public interface WheelInventory {

    String getInventoryId();

    default void openInstance(Player player) {
        throw new RuntimeException("Inventory cannot be opened directly.");
    }

    void selectMenuPoint(InventoryClickEvent event);

    default void destroyInventory(InventoryCloseEvent event) {

    }
}
