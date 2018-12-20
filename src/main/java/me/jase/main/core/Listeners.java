package me.jase.main.core;

import me.jase.main.Main;
import me.jase.main.inventory.InventoryManager;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

public class Listeners implements Listener {

    @EventHandler
    public void playerJoinEvent(PlayerJoinEvent event){
        Player p = event.getPlayer();

        //Puts player in playerState map with State.IDLE
        if (!Main.getPracticeCore().playerState.containsKey(p.getUniqueId())){
            Main.getPracticeCore().playerState.put(p.getUniqueId(), PracticeCore.PlayerState.IDLE);
            InventoryManager.queueInventory(p);
            p.teleport(Main.getPracticeCore().getMainSpawn());
            p.sendMessage(Main.getPracticeCore().playerState.get(p.getUniqueId()).toString());
        }else {
            p.kickPlayer(ChatColor.RED + "Error");
        }
    }

    @EventHandler
    public void quitEvent(PlayerQuitEvent e){
        Player p = e.getPlayer();

        if (Main.getPracticeCore().playerState.containsKey(p.getUniqueId())){
            Main.getPracticeCore().playerState.remove(p.getUniqueId());
        }
    }




    /*
        ---------------------------------------
             INVENTORYMANAGER LISTENERS
        ---------------------------------------
     */

    @EventHandler
    public void clickItemEvent(PlayerInteractEvent e){
        Player p = e.getPlayer();
        if ((e.getAction().equals(Action.RIGHT_CLICK_AIR) || (e.getAction().equals(Action.RIGHT_CLICK_BLOCK)))){
            if (p.getInventory().getItemInHand() != null){
                ItemStack itemInHand = p.getItemInHand();
                if (itemInHand.equals(InventoryManager.unrankedQueue)){
                    p.sendMessage("Unranked inventory");
                    InventoryManager.createUnrankedQueueInventory(p);
                }
            }
        }
    }
    @EventHandler
    public void inventoryInteract(InventoryClickEvent e){
        if (Main.getPracticeCore().getPlayerState((Player) e.getWhoClicked()) == PracticeCore.PlayerState.IDLE){
            if (e.getInventory() != null){
                if (e.getClickedInventory().getName() == "Unranked Queue"){
                    Player p = (Player) e.getWhoClicked();
                    ItemStack clicked = e.getCurrentItem();
                    if (clicked.equals(InventoryManager.noDebuff)){
                        e.setCancelled(true);
                        p.closeInventory();
                        p.sendMessage("Queue: Unranked NoDebuff");
                        Main.getPracticeCore().setNoDebuff(p);
                        if (Main.getPracticeCore().noDebuffQueue.size() > 1){

                            Main.getPracticeCore().startMatch(
                                    Main.getPracticeCore().noDebuffQueue.remove(),
                                    Main.getPracticeCore().noDebuffQueue.remove(),
                                    PracticeCore.QueueType.NODEBUFF);
                        }
                    }
                }
            }

        }

    }
    //TODO - CODE WHAT HAPPENDS WHEN A MATCH ENDS <PVP LISTENERS> FIX MATCH TIMER, FIX ARENA SPAWNPOINTS (PITCH YAW)
}
