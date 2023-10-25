/**
 * 
 */
package minereloaded.commands;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.Location;
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
public class AddRegionCommand implements CommandExecutor {

	private final Map<Player, Location> firstLocations = new HashMap<>();

	private final MineReloaded plugin;

	public AddRegionCommand(MineReloaded plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (sender instanceof Player player) {
			if (!this.firstLocations.containsKey(player)) {
				if (plugin.getCurrentRegion(player.getLocation()) != null) {
					sender.sendMessage(
							MineReloaded.PLUGIN_PREFIX + ChatColor.RED + "You are already in an existing region. Leave the region to define a new one.");
				} else {
					this.firstLocations.put(player, player.getLocation().clone());
					sender.sendMessage(MineReloaded.PLUGIN_PREFIX
							+ "Set the first corner of region. Execute same command again in the second corner to create the new region.");
				}
			} else {
				if (args.length == 1) {
					Region region = new Region(this.firstLocations.get(player), player.getLocation());

					for (Region r : this.plugin.getRegions()) {
						if (r.getWorld().getUID().equals(region.getWorld().getUID()) && region.getBoundingBox().overlaps(r.getBoundingBox())) {
							sender.sendMessage(MineReloaded.PLUGIN_PREFIX + ChatColor.RED + "The new region would overlap an existing one.");
							return true;
						}
					}

					this.firstLocations.remove(player);

					this.plugin.addRegion(region);
					sender.sendMessage(MineReloaded.PLUGIN_PREFIX + "New region was created");
				} else {
					return false;
				}
			}

		} else {
			sender.sendMessage(MineReloaded.PLUGIN_PREFIX + ChatColor.RED + "This command can only be used by a player");
		}

		return true;
	}

}
