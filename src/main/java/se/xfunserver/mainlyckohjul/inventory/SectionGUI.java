package se.xfunserver.mainlyckohjul.inventory;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import se.xfunserver.mainlyckohjul.LuckyWheel;
import se.xfunserver.mainlyckohjul.inventory.utils.WheelInventory;
import se.xfunserver.mainlyckohjul.inventory.utils.WheelInventoryHolder;
import se.xfunserver.mainlyckohjul.utilities.ItemBuilder;
import se.xfunserver.mainlyckohjul.utilities.list.ListUtils;
import se.xfunserver.mainlyckohjul.wheel.section.CircleSection;

import java.util.List;

public class SectionGUI implements WheelInventory {

    @Override
    public String getInventoryId() {
        return "sections";
    }

    @Override
    public void openInstance(Player player) {
        SectionGUI.open(LuckyWheel.getInstance(), player);
    }

    public static void open(LuckyWheel luckyWheel, Player player) {
        String menuTitle = ChatColor.DARK_GRAY + "Wheel Sections";
        Inventory menu = Bukkit.createInventory(new WheelInventoryHolder(new SectionGUI()), 36, menuTitle);

        ItemStack freeSlotItemStack = new ItemBuilder(Material.PAPER)
                .setName("&c&lUnknown Section")
                .build();

        int sectionNumber = 0;
        List<CircleSection> sections = luckyWheel.getWheelManager().getWheel().getSections();
        for (int slot = 10; slot < 26; slot++) {
            if ((slot + "").equalsIgnoreCase("17") || (slot + "").equalsIgnoreCase("18")) {
                continue;
            } else if (sectionNumber + 1 > sections.size()) {
                menu.setItem(slot, freeSlotItemStack);
                continue;
            }

            CircleSection section = sections.get(sectionNumber);
            ItemStack itemStack = new ItemBuilder(section.getColor().getAsMaterial())
                    .setName("&3&lSection " + sectionNumber + 1)
                    .addLore("&7Right click to move to the right.",
                            "&7Left click to move to the left.")
                    .build();
            menu.setItem(slot, itemStack);
            sectionNumber++;
        }

        player.openInventory(menu);
    }

    @Override
    public void selectMenuPoint(InventoryClickEvent event) {
        event.setCancelled(true);
        ItemStack clicked = event.getCurrentItem();
        final Player player = (Player) event.getWhoClicked();

        if (clicked == null || !clicked.getType().name().endsWith("TERRACOTTA")) {
            return;
        }

        List<CircleSection> sections = LuckyWheel.getInstance().getWheelManager().getWheel().getSections();
        if (event.getClick().equals(ClickType.RIGHT)) {
            ListUtils.shiftElement(sections, sections.get(event.getRawSlot() - 10), ListUtils.ShiftType.RIGHT);
            player.sendMessage("Switched Sections. [1]");
        } else if (event.getClick().equals(ClickType.LEFT)) {
            ListUtils.shiftElement(sections, sections.get(event.getRawSlot() - 10), ListUtils.ShiftType.LEFT);
            player.sendMessage("Switched Sections. [2]");
        }

        openInstance(player);
    }

}
