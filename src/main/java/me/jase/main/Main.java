package me.jase.main;

import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import me.jase.main.core.Arena;
import me.jase.main.core.Listeners;
import me.jase.main.core.PracticeCore;
import me.jase.main.core.commands.PracticeCmd;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import us.hotboxlib.main.command.CommandManager;
import us.hotboxlib.main.config.ConfigManager;
import us.hotboxlib.main.config.ConfigSetup;

public class Main extends JavaPlugin {
    private static Main plugin;
    private static PracticeCore practiceCore;

    @Override
    public void onEnable() {
        setInstance(this);
        this.getConfig().options().copyDefaults(true);
        this.saveDefaultConfig();
        ConfigSetup.register(plugin, "arenas.yml");
        practiceCore = new PracticeCore();
        register();
        loadArenas();

    }

    @Override
    public void onDisable() {

    }

    public void register(){
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(new Listeners(), this);
        CommandManager.register(new PracticeCmd());
    }
    //Worldguard & Worldedit Hooks
    public WorldGuardPlugin getWorldGuard() {
        Plugin plugin = getServer().getPluginManager().getPlugin("WorldGuard");

        if (plugin == null || !(plugin instanceof WorldGuardPlugin)) {
            return null;
        }

        return (WorldGuardPlugin) plugin;
    }

    public WorldEditPlugin getWorldEdit() {
        Plugin plugin = getServer().getPluginManager().getPlugin("WorldEdit");

        if (plugin == null || !(plugin instanceof WorldEditPlugin)) {
            return null;
        }

        return (WorldEditPlugin) plugin;
    }

    //Instance
    public static Main getInstance(){
        return plugin;
    }

    public void setInstance(Main plugin){
        Main.plugin = plugin;
    }


    public static PracticeCore getPracticeCore(){
        return practiceCore;
    }

    public void loadArenas(){
        ConfigManager config = new ConfigManager(plugin, "arenas.yml");
        ConfigurationSection arenas = config.getConfig().getConfigurationSection("arenas");
        try{

            for(String key : arenas.getKeys(false)){
                ConfigurationSection arenaConfig = arenas.getConfigurationSection(key);
                Arena arena = Arena.deserialize(arenaConfig.getValues(false));
                /*arena.setSpawnPointOne(new Location
                        (Bukkit.getWorlds().get(0),
                                getConfig().getDouble("arenas." + arena.getName() + ".spawnpointone..x"),
                                getConfig().getDouble("arenas." + arena.getName() + ".spawnpointone.y"),
                                getConfig().getDouble("arenas." + arena.getName() + ".spawnpointone.z"),
                                (float)getConfig().getDouble("arenas." + arena.getName() + ".spawnpointone.pitch"),
                                (float)getConfig().getDouble("arenas." + arena.getName() + ".spawnpointone.yaw")));
                arena.setSpawnPointTwo(new Location
                        (Bukkit.getWorlds().get(0),
                                getConfig().getDouble("arenas." + arena.getName() + ".spawnpointtwo.x"),
                                getConfig().getDouble("arenas." + arena.getName() + ".spawnpointtwo.y"),
                                getConfig().getDouble("arenas." + arena.getName() + ".spawnpointtwo.z"),
                                (float)getConfig().getDouble("arenas." + arena.getName() + ".spawnpointtwo.pitch"),
                                (float)getConfig().getDouble("arenas." + arena.getName() + ".spawnpointtwo.yaw")));*/
                Main.getPracticeCore().loadArena(arena);
            }
        }catch(NullPointerException e){
            System.out.println("\n\nNULL CREATING ARENAS\n\n");
        }
    }
}
