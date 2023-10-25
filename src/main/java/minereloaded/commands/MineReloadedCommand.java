/**
 * 
 */
package minereloaded.commands;

import java.util.Arrays;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import minereloaded.MineReloaded;

/**
 * Copyright (C) 2023 Johannes Pollitt. <br />
 * All rights reserved.
 * 
 * @author Johannes Pollitt
 * 
 */
public class MineReloadedCommand implements CommandExecutor, TabCompleter {

	private final MineReloaded plugin;

	public MineReloadedCommand(MineReloaded plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (args.length == 0) {
			sender.sendMessage(MineReloaded.PLUGIN_PREFIX + "The MineReloaded plugin is currently " + ChatColor.GOLD
					+ (this.plugin.isActive() ? "Enabled" : "Disabled") + ChatColor.GRAY + "!");
			return true;
		} else if (args.length > 1) {
			return false;
		}

		switch (args[0]) {
		case "enable": {
			if (!this.plugin.isActive()) {
				this.plugin.setActive(true);
				sender.sendMessage(MineReloaded.PLUGIN_PREFIX + "Plugin enabled.");
			} else {
				sender.sendMessage(MineReloaded.PLUGIN_PREFIX + "Plugin could not be enabled! Is it already enabled?");
			}
			break;
		}
		case "disable": {
			if (this.plugin.isActive()) {
				this.plugin.setActive(false);
				sender.sendMessage(MineReloaded.PLUGIN_PREFIX + "Plugin disabled.");
			} else {
				sender.sendMessage(MineReloaded.PLUGIN_PREFIX + "Plugin could not be disabled! Is it already disabled?");
			}
			break;
		}
		case "reload": {
			this.plugin.deactivate();
			this.plugin.activate();
			sender.sendMessage(MineReloaded.PLUGIN_PREFIX + "Plugin reloaded.");
			break;
		}
		default:
			return false;
		}

		return true;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
		if (args.length != 1) {
			return null;
		}

		return Arrays.asList("enable", "disable", "reload").stream().filter(s -> s.startsWith(args[0])).toList();
	}

}
