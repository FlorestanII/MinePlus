/**
 * 
 */
package minereloaded.entities;

import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_20_R1.CraftWorld;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.Turtle;
import net.minecraft.world.entity.player.Player;

/**
 * Copyright (C) 2023 Johannes Pollitt. <br />
 * All rights reserved.
 * 
 * @author FlorestanII
 * 
 */
public class TestCustomEntity extends Turtle {

	/**
	 * @param loc the location to spawn the entity at
	 */
	public TestCustomEntity(Location loc) {
		super(EntityType.TURTLE, ((CraftWorld) loc.getWorld()).getHandle());
		this.setPos(loc.getX(), loc.getY(), loc.getZ());

		// Baby Turtle that won't grow
		this.setBaby(true);
		this.ageLocked = true;

		// Don't save entity on world save
		this.persist = false;

		this.removeAllGoals((Goal g) -> true);
		this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.0, true));
		this.goalSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, true));

		((CraftWorld) loc.getWorld()).getHandle().addFreshEntity(this, SpawnReason.CUSTOM);
	}

}
