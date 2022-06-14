package se.xfunserver.mainlyckohjul.inventory.utils;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.jetbrains.annotations.NotNull;
import se.xfunserver.mainlyckohjul.LuckyWheel;

public class WheelInventoryHolder implements InventoryHolder {
    private WheelInventory inventory;

    public WheelInventoryHolder(WheelInventory inventory) {
        this.inventory = inventory;
    }

    @NotNull
    @Override
    public Inventory getInventory() {
        return null;
    }

    public String getInventoryName() {
        return inventory.getClass().getName();
    }

    public void selectMenuPoint(InventoryClickEvent event) {
        inventory.selectMenuPoint(event);
    }

    public void destroyInventory(InventoryCloseEvent event) {
        inventory.destroyInventory(event);
    }
}
