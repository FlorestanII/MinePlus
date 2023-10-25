/**
 * 
 */
package minereloaded;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

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

	private List<WalkingBlockEntity> entities = new ArrayList<>(32);

	@Override
	public void onEnable() {
		super.onEnable();

		getCommand("minereloaded").setExecutor(new MineReloadedCommand(this));

		getCommand("testspawn").setExecutor(new CommandExecutor() {
			@Override
			public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
				if (sender instanceof Player player) {
					if (args.length == 1) {
						WalkingBlockEntity entity = new WalkingBlockEntity(player.getLocation(), OreTypes.valueOf(args[0].toUpperCase()));
						entities.add(entity);
						return true;
					}

				}
				return false;
			}
		});
	}

	@Override
	public void onDisable() {
		// TODO add custom events -> listener -> management
		for (WalkingBlockEntity entity : entities) {
			entity.remove();
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

	public boolean activate() {
		if (isActive()) {
			return false;
		}

		return true;
	}

	public boolean deactivate() {
		if (!isActive()) {
			return false;
		}

		return true;
	}

}
