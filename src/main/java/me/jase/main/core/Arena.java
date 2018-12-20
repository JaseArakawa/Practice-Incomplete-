package me.jase.main.core;

import com.sk89q.worldguard.protection.regions.ProtectedCuboidRegion;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.serialization.ConfigurationSerializable;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class Arena implements ConfigurationSerializable {

    private Location spawnPointOne;
    private Location spawnPointTwo;
    private String arenaRegionName;
    private String name;
    //temp solution
    private double x1;
    private double y1;
    private double z1;
    private float pitch1;
    private float yaw1;
    private double x2;
    private double y2;
    private double z2;
    private float pitch2;
    private float yaw2;



    public Arena(String name, String regionName, Location spawn1, Location spawn2 ){
        this.name = name;
        this.arenaRegionName = regionName;
        this.spawnPointOne = spawn1;
        this.spawnPointTwo = spawn2;
    }

    public String getName() {
        return name;
    }

    public Location getSpawnPointOne() {
        return spawnPointOne;
    }

    public Location getSpawnPointTwo() {
        return spawnPointTwo;
    }

    public String getArenaRegionName() {
        return arenaRegionName;
    }

    public void setSpawnPointOne(Location spawnPointOne) {
        this.spawnPointOne = spawnPointOne;
    }

    public void setSpawnPointTwo(Location spawnPointTwo) {
        this.spawnPointTwo = spawnPointTwo;
    }

    public Arena getArenaByName(String name){
        if (this.name == name){
            return this;
        }return null;
    }

    public void setX1(double x1) {
        this.x1 = x1;
    }

    public void setY1(double y1) {
        this.y1 = y1;
    }

    public void setZ1(double z1) {
        this.z1 = z1;
    }

    public void setPitch1(float pitch1) {
        this.pitch1 = pitch1;
    }

    public void setYaw1(float yaw1) {
        this.yaw1 = yaw1;
    }

    public void setX2(double x2) {
        this.x2 = x2;
    }

    public void setY2(double y2) {
        this.y2 = y2;
    }

    public void setZ2(double z2) {
        this.z2 = z2;
    }

    public void setPitch2(float pitch2) {
        this.pitch2 = pitch2;
    }

    public void setYaw2(float yaw2) {
        this.yaw2 = yaw2;
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> result = new LinkedHashMap<>();
        result.put("name", name);

        result.put("arenaregionname", arenaRegionName);
        result.put("x1", x1);
        result.put("y1", y1);
        result.put("z1", z1);
        //result.put("pitch1", pitch1);
        //result.put("yaw1", yaw1);
        result.put("x2", x2);
        result.put("y2", y2);
        result.put("z2", z2);
        //result.put("pitch2", pitch2);
        //result.put("yaw2", yaw2);


        return result;
    }

    public static Arena deserialize(Map<String, Object> args){
        String name = (String) args.get("name");
        String regionName = (String) args.get("arenaregionname");
        double x1 = (double) args.get("x1");
        double y1 = (double) args.get("y1");
        double z1 = (double) args.get("z1");
        //float pitch1 = (float) args.get("pitch1");
        //float yaw1 = (float) args.get("yaw1");
        double x2 = (double) args.get("x2");
        double y2 = (double) args.get("y2");
        double z2 = (double) args.get("z2");
        //float pitch2 = (float) args.get("pitch2");
       // float yaw2 = (float) args.get("yaw2");

        //return new Node(material, x, y, z, blockCount);
        return new Arena(name, regionName, new Location(Bukkit.getWorlds().get(0), x1, y1, z1), new Location(Bukkit.getWorlds().get(0), x2, y2, z2));
    }
}
