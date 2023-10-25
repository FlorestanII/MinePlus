/**
 * 
 */
package minereloaded.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import minereloaded.MineReloaded;
import minereloaded.Region;

/**
 * Copyright (C) 2023 FlorestanII. <br />
 * All rights reserved.
 * 
 * @author FlorestanII
 * 
 */
public class AddSpawnpointCommand implements CommandExecutor {

	private final MineReloaded plugin;

	public AddSpawnpointCommand(MineReloaded plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (sender instanceof Player player) {
			Region region = this.plugin.getCurrentRegion(player.getLocation());

			if (region != null) {
				this.plugin.addSpawnpointToRegion(player.getLocation());

				sender.sendMessage(MineReloaded.PLUGIN_PREFIX + "Added new spawnpoint to region");
			} else {
				sender.sendMessage(
						MineReloaded.PLUGIN_PREFIX + ChatColor.RED + "You are currently in no spawning region! Enter a region to add a new spawnpoint to it.");
			}

		} else {
			sender.sendMessage(MineReloaded.PLUGIN_PREFIX + ChatColor.RED + "This command can only be used by a player");
		}

		return true;
	}

}
