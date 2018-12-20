package me.jase.main.core.commands;

import com.sk89q.worldedit.BlockVector;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.bukkit.selections.Selection;
import com.sk89q.worldguard.protection.regions.ProtectedCuboidRegion;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import me.jase.main.Main;
import me.jase.main.core.Arena;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import us.hotboxlib.main.command.Command;
import us.hotboxlib.main.command.CommandListener;
import us.hotboxlib.main.config.ConfigManager;

public class PracticeCmd implements CommandListener {

    @Command(name = "practice p", arguments = "")
    public void p(Player p, String[] args){

        if (args.length == 1){
            if (args[0].equalsIgnoreCase("help")){
                p.sendMessage("/p createarena <name>\n" + "/p setspawn1 <arena>\n" + "/p setspawn2 <arena>\n" + "/p setspawn");
                return;
            }else if (args[0].equalsIgnoreCase("setspawn")){
                Location loc = p.getLocation();
                Main.getInstance().getConfig().set("main-spawn.x", loc.getX());
                Main.getInstance().getConfig().set("main-spawn.y", loc.getY());
                Main.getInstance().getConfig().set("main-spawn.z", loc.getZ());
                Main.getInstance().getConfig().set("main-spawn.pitch", loc.getPitch());
                Main.getInstance().getConfig().set("main-spawn.yaw", loc.getYaw());
                Main.getInstance().saveConfig();
                p.sendMessage("Spawn location changed to: " +
                        "\nx:" + Math.floor(loc.getX()) + " " +
                        "\ny:"+ Math.floor(loc.getY()) + " " +
                        "\nz:" + Math.floor(loc.getZ()));
                return;
            }else if (args[0].equalsIgnoreCase("spawn")){
                p.teleport(Main.getPracticeCore().getMainSpawn());
                return;
            }else if (args[0].equalsIgnoreCase("setarenaspawn1")){
                Location loc = p.getLocation();
                Vector v = new Vector(loc.getX(), loc.getY(), loc.getZ());
                for (Arena arena : Main.getPracticeCore().arenas){
                    ProtectedRegion r = Main.getInstance().getWorldGuard().getRegionManager(loc.getWorld()).getRegion(arena.getArenaRegionName());
                    if (r.contains(v)){
                        arena.setX1(p.getLocation().getX());
                        arena.setY1(p.getLocation().getY());
                        arena.setZ1(p.getLocation().getZ());
                        arena.setPitch1(p.getLocation().getPitch());
                        arena.setYaw1(p.getLocation().getYaw());

                        ConfigManager cm = new ConfigManager(Main.getInstance(), "arenas.yml");
                        cm.getConfig().set("arenas.." + arena.getName(), arena.serialize());
                        cm.saveConfig();
                        p.sendMessage("Spawn point 1 has been set for " + arena.getName());
                        return;
                    }
                }
                p.sendMessage("Not a valid arena");

            }else if (args[0].equalsIgnoreCase("setarenaspawn2")){
                Location loc = p.getLocation();
                Vector v = new Vector(loc.getX(), loc.getY(), loc.getZ());
                for (Arena arena : Main.getPracticeCore().arenas){
                    ProtectedRegion r = Main.getInstance().getWorldGuard().getRegionManager(loc.getWorld()).getRegion(arena.getArenaRegionName());
                    if (r.contains(v)){
                        arena.setX2(p.getLocation().getX());
                        arena.setY2(p.getLocation().getY());
                        arena.setZ2(p.getLocation().getZ());
                        arena.setPitch2(p.getLocation().getPitch());
                        arena.setYaw2(p.getLocation().getYaw());
                        ConfigManager cm = new ConfigManager(Main.getInstance(), "arenas.yml");
                        cm.getConfig().set("arenas.." + arena.getName(), arena.serialize());
                        cm.saveConfig();
                        p.sendMessage("Spawn point 2 has been set for " + arena.getName());
                        return;
                    }
                }
                p.sendMessage("Not a valid arena");
            }
        }else if (args.length == 2){
            if (args[0].equalsIgnoreCase("createarena")){
                String name = args[1].trim();
                Selection selection = Main.getInstance().getWorldEdit().getSelection(p);
                if (selection == null){
                    p.sendMessage("Create a region with //wand first");
                    return;
                }

                ProtectedCuboidRegion region = new ProtectedCuboidRegion(name,
                        new BlockVector(selection.getNativeMinimumPoint()),
                        new BlockVector(selection.getNativeMaximumPoint()));
                Main.getInstance().getWorldGuard().getRegionManager(p.getWorld()).addRegion(region);
                Arena arena = new Arena(name, region.getId(), p.getLocation(), p.getLocation());
                ConfigManager cm = new ConfigManager(Main.getInstance(), "arenas.yml");
                cm.getConfig().set("arenas.." + arena.getName(), arena.serialize());
                cm.saveConfig();
                Main.getPracticeCore().arenas.add(arena);
                p.sendMessage("Arena created! Please create 2 spawn points for the arena with " +
                        "\n /p setarenaspawn1 /p setarenaspawn2");
            }
        }
    }
}
