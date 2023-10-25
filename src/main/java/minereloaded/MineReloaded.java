/**
 * 
 */
package minereloaded;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import minereloaded.commands.AddRegionCommand;
import minereloaded.commands.MineReloadedCommand;
import minereloaded.entities.walkingblockentity.WalkingBlockEntity;

/**
 * Copyright (C) 2023 FlorestanII & Elekt0. <br />
 * All rights reserved.
 * 
 * @author FlorestanII, Elekt0
 * 
 */
public class MineReloaded extends JavaPlugin {

	public static final String PLUGIN_PREFIX = ChatColor.GRAY + "[" + ChatColor.GOLD + "MineReloaded" + ChatColor.GRAY + "] ";

	private List<Region> regions = new ArrayList<>();

	private List<WalkingBlockEntity> entities = new ArrayList<>();

	@Override
	public void onLoad() {
		super.onLoad();
		saveDefaultConfig();
	}

	@Override
	public void onEnable() {
		super.onEnable();

		MineReloadedCommand mainCommand = new MineReloadedCommand(this);

		getCommand("minereloaded").setExecutor(mainCommand);
		getCommand("minereloaded").setTabCompleter(mainCommand);

		getCommand("addregion").setExecutor(new AddRegionCommand(this));

		getCommand("testspawn").setExecutor(new CommandExecutor() {
			@Override
			public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
				if (sender instanceof Player player) {
					if (args.length == 1) {
						// TODO
						// WalkingBlockEntity entity = new WalkingBlockEntity(player.getLocation(),
						// OreTypes.valueOf(args[0].toUpperCase()));
						// entities.add(entity);
						return true;
					}

				}
				return false;
			}
		});

		if (isActive()) {
			activate();
		}
	}

	@Override
	public void onDisable() {
		// TODO add custom events -> listener -> management
		for (WalkingBlockEntity entity : entities) {
			entity.remove();
		}

		if (isActive()) {
			deactivate();
		}

		super.onDisable();
	}

	public boolean isActive() {
		return this.getConfig().getBoolean("enabled", false);
	}

	public void setActive(boolean active) {
		this.getConfig().set("enabled", active);
		this.saveConfig();
	}

	public void activate() {
		this.regions.clear();
		ConfigurationSection section = this.getConfig().getConfigurationSection("regions");

		if (section != null) {
			for (String key : section.getKeys(false)) {
				Region region = Region.loadFromConfig(section.getConfigurationSection(key));
				if (region != null) {
					this.regions.add(region);
				} else {
					getLogger().warning("Failed to load region " + key);
				}
			}
		}
	}

	public void deactivate() {
		// TODO
	}

	public List<Region> getRegions() {
		return this.regions;
	}

	public Region getCurrentRegion(Location location) {
		for (Region region : regions) {
			if (region.getWorld().getUID().equals(location.getWorld().getUID())
					&& region.getBoundingBox().contains(location.getX(), location.getY(), location.getZ())) {
				return region;
			}
		}

		return null;
	}

	public void addRegion(Region region) {
		this.regions.add(region);

		if (this.getConfig().getConfigurationSection("regions") == null) {
			this.getConfig().createSection("regions");
		}

		region.saveToConfig(this.getConfig().getConfigurationSection("regions"));
		this.saveConfig();
	}

	public static MineReloaded getPlugin() {
		return getPlugin(MineReloaded.class);
	}

}
