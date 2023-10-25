/**
 * 
 */
package minereloaded;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.util.BoundingBox;
import org.bukkit.util.Vector;

/**
 * Copyright (C) 2023 FlorestanII. <br />
 * All rights reserved.
 * 
 * @author FlorestanII
 * 
 */
public class Region {

	private final UUID id;
	private World world;
	private BoundingBox boundingBox;

	private int capacity;

	private List<Vector> spawnpoints = new ArrayList<>();

	public Region(Location loc1, Location loc2) {
		this.id = UUID.randomUUID();

		this.world = loc1.getWorld();
		double minX = Math.min(loc1.getX(), loc2.getX());
		double minY = Math.min(loc1.getY(), loc2.getY());
		double minZ = Math.min(loc1.getZ(), loc2.getZ());
		double maxX = Math.max(loc1.getX(), loc2.getX());
		double maxY = Math.max(loc1.getY(), loc2.getY());
		double maxZ = Math.max(loc1.getZ(), loc2.getZ());

		this.boundingBox = new BoundingBox(minX, minY, minZ, maxX, maxY, maxZ);
	}

	private Region(UUID id, World world, BoundingBox box) {
		this.id = id;
		this.world = world;
		this.boundingBox = box;
	}

	public World getWorld() {
		return this.world;
	}

	public BoundingBox getBoundingBox() {
		return this.boundingBox;
	}

	public void addSpawnpoint(Vector spawnpoint) {
		this.spawnpoints.add(spawnpoint);
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	public int getCapacity() {
		return this.capacity;
	}

	public boolean saveToConfig(ConfigurationSection regionSection) {
		if (regionSection == null) {
			return false;
		}

		ConfigurationSection config;

		if (!regionSection.isConfigurationSection(id.toString())) {
			config = regionSection.createSection(id.toString());
		} else {
			config = regionSection.getConfigurationSection(id.toString());
		}

		config.set("world", this.world.getName());
		config.set("minX", this.boundingBox.getMinX());
		config.set("minY", this.boundingBox.getMinY());
		config.set("minZ", this.boundingBox.getMinZ());
		config.set("maxX", this.boundingBox.getMaxX());
		config.set("maxY", this.boundingBox.getMaxY());
		config.set("maxZ", this.boundingBox.getMaxZ());

		config.set("capacity", this.capacity);

		List<Map<?, ?>> spawnlist = new ArrayList<>();

		for (Vector spawn : this.spawnpoints) {
			Map<String, Object> map = new HashMap<>();
			map.put("x", spawn.getX());
			map.put("y", spawn.getY());
			map.put("z", spawn.getZ());
			spawnlist.add(map);
		}

		config.set("spawnpoints", spawnlist);

		return true;
	}

	public static Region loadFromConfig(ConfigurationSection config) {
		if (config == null) {
			return null;
		}

		String id = config.getName();

		if (!config.isSet("minX") || !config.isSet("minY") || !config.isSet("minZ") || !config.isSet("maxX") || !config.isSet("maxY") || !config.isSet("maxZ")
				|| !config.isSet("capacity")) {
			return null;
		}

		double minX = config.getDouble("minX");
		double minY = config.getDouble("minY");
		double minZ = config.getDouble("minZ");
		double maxX = config.getDouble("maxX");
		double maxY = config.getDouble("maxY");
		double maxZ = config.getDouble("maxZ");
		BoundingBox box = new BoundingBox(minX, minY, minZ, maxX, maxY, maxZ);

		int capacity = config.getInt("capacity");

		if (!config.isSet("world")) {
			return null;
		}

		String worldName = config.getString("world");

		World world = Bukkit.getWorld(worldName);

		if (world == null) {
			return null;
		}

		Region region = new Region(UUID.fromString(id), world, box);
		region.setCapacity(capacity);

		List<Map<?, ?>> spawnlist = config.getMapList("spawnpoints");

		for (Map<?, ?> map : spawnlist) {
			region.spawnpoints.add(new Vector((double) map.get("x"), (double) map.get("y"), (double) map.get("z")));
		}

		return region;
	}

}
