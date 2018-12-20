package me.jase.main.core;

import me.jase.main.Main;
import org.bukkit.Bukkit;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

public class Match extends BukkitRunnable implements ConfigurationSerializable {

    /*
        Static Variables & Methods
     */

    //Holds all active matches <
    public static ArrayList<Match> active = new ArrayList<>();
    //Holds inactive matches, matches removed after certain amount of time
    public static ArrayList<Match> cache = new ArrayList<>();

    /*
        Core variables
     */

    private LinkedList<UUID> participants;
    private String matchType;
    private int matchTime;
    private Map<UUID, Integer> cooldowns;
    private int postMatchTime;
    private String arena;

    /*
        Post Match variables (for serialization)
     */
    private Inventory postPlayer1Inventory;
    private Inventory postPlayer2Inventory;

    /*
        Random statistic variables
     */

    private double postPlayer1Health;
    private double postPlayer2Health;

    private int postPlayer1Potions;
    private int postPlayer2Potions;

    private double postPlayer1Food;
    private double postPlayer2Food;

    private UUID winner;
    private UUID loser;



    /*
        Used to start an active Match
     */
    public Match(UUID player1Uuid, UUID player2Uuid, String matchType, String arena){
        this.participants = new LinkedList<>();
        participants.add(player1Uuid);
        participants.add(player2Uuid);
        this.matchType = matchType;
        this.arena = arena;
        matchTime = 0;
        postMatchTime = 5;
        cooldowns = new HashMap<>();
        for (UUID id : participants){
            cooldowns.put(id, 0);
        }
        for (Arena a : Main.getPracticeCore().arenas){
            if (a.getArenaByName(arena) != null){
                Bukkit.getPlayer(player1Uuid).teleport(a.getSpawnPointOne());
                Bukkit.getPlayer(player2Uuid).teleport(a.getSpawnPointTwo());
                break;
            }
        }
        Match.active.add(this);
        this.runTaskTimerAsynchronously(Main.getInstance(), 0L, 20L);
    }

    /*
        Used to load a Match into cache
     */
    /*public Match(UUID[] participants, String matchType, int matchTime, double postPlayer1Health,
                 double postPlayer2Health, int postPlayer1Potions, int postPlayer2Potions, UUID winner, UUID loser) {
        this.participants = participants;
        this.matchType = matchType;
        this.matchTime = matchTime;
        this.postPlayer1Health = postPlayer1Health;
        this.postPlayer2Health = postPlayer2Health;
        this.postPlayer1Potions = postPlayer1Potions;
        this.postPlayer2Potions = postPlayer2Potions;
        this.winner = winner;
        this.loser = loser;
        Match.cache.add(this);
    }*/


    public void setPostPlayer1Inventory(Inventory postPlayer1Inventory) {
        this.postPlayer1Inventory = postPlayer1Inventory;
    }

    public void setPostPlayer2Inventory(Inventory postPlayer2Inventory) {
        this.postPlayer2Inventory = postPlayer2Inventory;
    }

    public void setPostPlayer1Health(double postPlayer1Health) {
        this.postPlayer1Health = postPlayer1Health;
    }

    public void setPostPlayer2Health(double postPlayer2Health) {
        this.postPlayer2Health = postPlayer2Health;
    }

    public void setPostPlayer1Food(double postPlayer1Food) {
        this.postPlayer1Food = postPlayer1Food;
    }

    public void setPostPlayer2Food(double postPlayer2Food) {
        this.postPlayer2Food = postPlayer2Food;
    }

    public void setWinner(UUID winner) {
        this.winner = winner;
    }

    public void setLoser(UUID loser) {
        this.loser = loser;
    }

    /*
        --------------------------------------
                       METHODS
        --------------------------------------
     */


    public boolean apartOfMatch(Player p){
        for (UUID id : participants){
            if (p.getUniqueId().equals(id)){
                return true;

            }
        }return false;
    }



    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> result = new LinkedHashMap<>();
        result.put("winner", winner);
        result.put("loser", loser);
        result.put("participants", participants);
        result.put("matchtype", matchType);

        return result;
    }
    /**
     * When an object implementing interface <code>Runnable</code> is used
     * to create a thread, starting the thread causes the object's
     * <code>run</code> method to be called in that separately executing
     * thread.
     * <p>
     * The general contract of the method <code>run</code> is that it may
     * take any action whatsoever.
     *
     * @see Thread#run()
     */
    @Override
    public void run() {
        if (Match.active.contains(this)){
            matchTime += 1;
            for (int cd : cooldowns.values()){
                if (cd > 0){
                    if ((cd - 1) < 0){
                        cd = 0;
                    }else {
                        cd -= 1;
                    }
                }
            }
        } else {
            if (postMatchTime > 0){
                postMatchTime -= 1;
            }else {
                Main.getPracticeCore().cleanUpMatch(this);
            }
        }
    }
}
