package me.jase.main.inventory;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

//TODO - Make inventories configurable
public class InventoryManager {

    /**
     *
     * CREATE ITEMSTACK WITH LORE
     */
    public static ItemStack createItemStack(String name, Material material, int amount, List<String> lore){
        ItemStack item = new ItemStack(material, amount);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        meta.setLore(lore);
        item.setItemMeta(meta);

        return item;
    }
    /*
        CREATE ITEMSTACK WITHOUT LORE
     */
    public static ItemStack createItemStack(String name, Material material, int amount){
        ItemStack item = new ItemStack(material, amount);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        item.setItemMeta(meta);

        return item;
    }

    /*
        UNRANKED QUEUE ITEM CREATOR
     */
    private static ItemStack unrankedQueue(){
        return createItemStack(ChatColor.
                translateAlternateColorCodes('&', "&bUnranked Queue"),
                Material.IRON_SWORD, 1);
    }
    private static ItemStack noDebuff(){
        return createItemStack(ChatColor.
                        translateAlternateColorCodes('&', "&bNoDebuff"),
                Material.FEATHER, 1);
    }
    /*
        ITEM REFERENCES
     */
    public static ItemStack unrankedQueue = unrankedQueue();
    public static ItemStack noDebuff = noDebuff();

    /*
        GIVES PLAYER A QUEUE INVENTORY
     */
    public static void queueInventory(Player p){
        p.getInventory().clear();
        //Slot 1 in hotbar
        p.getInventory().setItem(0, unrankedQueue());
    }

    /*
        GIVES PLAYER A PVP INVENTORY
     */
    public static void pvpInventory(Player p) {
        p.getInventory().setHelmet(new ItemStack(Material.DIAMOND_HELMET));
        p.getInventory().setChestplate(new ItemStack(Material.DIAMOND_CHESTPLATE));
        p.getInventory().setLeggings(new ItemStack(Material.DIAMOND_LEGGINGS));
        p.getInventory().setBoots(new ItemStack(Material.DIAMOND_BOOTS));
        p.getInventory().setItem(0, new ItemStack(Material.DIAMOND_SWORD));
    }

    public static void createUnrankedQueueInventory(Player p){
        Inventory inv = Bukkit.createInventory(null, 9, "Unranked Queue");
        inv.setItem(0, noDebuff());
        p.openInventory(inv);
    }

}
