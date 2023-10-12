/**
 * 
 */
package minereloaded.entities.walkingblockentity;

import org.bukkit.World;
import org.bukkit.craftbukkit.v1_20_R1.CraftWorld;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;

import net.minecraft.world.entity.Entity;

/**
 * Copyright (C) 2023 FlorestanII & Elekt0. <br />
 * All rights reserved.
 * 
 * @author FlorestanII
 * 
 */
public class EntityUtil {
	public static boolean spawnInWorld(World world, Entity entity) {
		return ((CraftWorld) world).getHandle().addFreshEntity(entity, SpawnReason.CUSTOM);
	}
}
