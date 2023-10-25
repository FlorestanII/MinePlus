/**
 * 
 */
package minereloaded.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import minereloaded.MineReloaded;

/**
 * Copyright (C) 2023 Johannes Pollitt. <br />
 * All rights reserved.
 * 
 * @author Johannes Pollitt
 * 
 */
public class MineReloadedCommand implements CommandExecutor {

	private final MineReloaded plugin;

	public MineReloadedCommand(MineReloaded plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (args.length == 0) {
			sender.sendMessage(MineReloaded.PLUGIN_PREFIX + "The MineReloaded plugin is currently " + ChatColor.GOLD
					+ (this.plugin.isActive() ? "Enabled" : "Disabled") + ChatColor.GRAY + "!");
		} else if (args.length > 1) {
			return false;
		}

		switch (args[0]) {
		case "enable": {
			if (this.plugin.activate()) {
				sender.sendMessage(MineReloaded.PLUGIN_PREFIX + "Plugin enabled.");
			} else {
				sender.sendMessage(MineReloaded.PLUGIN_PREFIX + "Plugin could not be enabled! Is it already enabled?");
			}
			break;
		}
		case "disable": {
			if (this.plugin.deactivate()) {
				sender.sendMessage(MineReloaded.PLUGIN_PREFIX + "Plugin disabled.");
			} else {
				sender.sendMessage(MineReloaded.PLUGIN_PREFIX + "Plugin could not be disabled! Is it already disabled?");
			}
			break;
		}
		case "reload": {
			this.plugin.deactivate();

			if (this.plugin.activate()) {
				sender.sendMessage(MineReloaded.PLUGIN_PREFIX + "Plugin reloaded.");
			} else {
				sender.sendMessage(MineReloaded.PLUGIN_PREFIX + "Reloading of the plugin failed!");
			}
			break;
		}
		default:
			return false;
		}

		return true;
	}

}
