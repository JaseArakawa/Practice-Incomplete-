package me.jase.main.core;

import me.jase.main.Main;
import me.jase.main.inventory.InventoryManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.*;

public class PracticeCore {

    public enum QueueType {
        NODEBUFF, DEBUFF, GAPPLE;
    }
    public enum PlayerState {
        IDLE, MATCH;
    }

    //NoDebuff Queue
    public LinkedList<UUID> noDebuffQueue = new LinkedList<>();
    //Active arenas
    public ArrayList<Arena> arenas = new ArrayList<>();
    //Player state
    public Map<UUID, PlayerState> playerState = new HashMap<>();


    /*
        METHODS
     */
    public void startMatch(UUID player1, UUID player2, QueueType type){
        Random random = new Random();
        int num = random.nextInt(arenas.size());
        Match match = new Match(player1, player2, type.toString(), arenas.get(num).getName());
        playerState.put(player1, PlayerState.MATCH);
        playerState.put(player2, PlayerState.MATCH);
    }

    public void cleanUpMatch(Match match){
        match.cancel();
        Match.cache.add(match);

        for (UUID id : match.getParticipants()){
            resetPlayer(Bukkit.getPlayer(id));
        }
    }
    public void resetPlayer(Player p){
        playerState.put(p.getUniqueId(), PlayerState.IDLE);
        p.teleport(getMainSpawn());
        InventoryManager.queueInventory(p);
    }

    public Map<UUID, PlayerState> getPlayerStateMap() {
        return playerState;
    }

    public PlayerState getPlayerState(Player p){
        return playerState.get(p.getUniqueId());
    }

    /*
            Some shit idk
         */
    public void setNoDebuff(Player p){
        noDebuffQueue.add(p.getUniqueId());
    }

    public Location getMainSpawn() {
        return new Location(Bukkit.getWorlds().get(0),
                Main.getInstance().getConfig().getDouble("main-spawn.x"),
                Main.getInstance().getConfig().getDouble("main-spawn.y"),
                Main.getInstance().getConfig().getDouble("main-spawn.z"),
                (float) Main.getInstance().getConfig().getDouble("main-spawn.yaw"),
                (float) Main.getInstance().getConfig().getDouble("main-spawn.pitch"));
    }

    public void loadArena(Arena arena){
        if (!arenas.contains(arena)){
            arenas.add(arena);
        }else {
            Bukkit.getLogger().info("Error loading arena");
        }
    }

}
