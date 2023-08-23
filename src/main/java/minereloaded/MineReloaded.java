/**
 * 
 */
package minereloaded;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import minereloaded.entities.TestCustomEntity;
import net.minecraft.world.entity.Entity.RemovalReason;

/**
 * Copyright (C) 2023 FlorestanII & Elekt0. <br />
 * All rights reserved.
 * 
 * @author FlorestanII, Elekt0
 * 
 */
public class MineReloaded extends JavaPlugin {

	private List<TestCustomEntity> entities = new ArrayList<>(32);

	@Override
	public void onDisable() {
		for (TestCustomEntity entity : entities) {
			entity.remove(RemovalReason.DISCARDED);
		}
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (label.equals("testspawn") && sender instanceof Player player) {
			TestCustomEntity entity = new TestCustomEntity(player.getLocation());
			entities.add(entity);

			return true;
		}

		return false;
	}

}
