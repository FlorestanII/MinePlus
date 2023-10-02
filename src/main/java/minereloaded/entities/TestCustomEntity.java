/**
 * 
 */
package minereloaded.entities;

import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_20_R1.CraftWorld;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.Turtle;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.player.Player;

/**
 * Copyright (C) 2023 Johannes Pollitt. <br />
 * All rights reserved.
 * 
 * @author FlorestanII
 * 
 */
public class TestCustomEntity extends Turtle {

	private AttributeMap attributes;

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

		// Attributes
		getAttributes().getInstance(Attributes.MOVEMENT_SPEED).setBaseValue(1); // Normal player movement speed

		this.goalSelector.removeAllGoals((o) -> true);
		this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.0, true));
		this.goalSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, true));

		((CraftWorld) loc.getWorld()).getHandle().addFreshEntity(this, SpawnReason.CUSTOM);
	}

	@Override
	public AttributeMap getAttributes() {
		if (this.attributes == null) {
			this.attributes = new AttributeMap(Zombie.createAttributes().build());
		}

		return attributes;
	}
}
