/**
 * 
 */
package minereloaded.entities;

import java.lang.reflect.Field;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_20_R1.CraftWorld;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.entity.ai.attributes.Attributes;
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

	private static Field attributeField;

	static {
		try {
			attributeField = AttributeMap.class.getDeclaredField("b");
			attributeField.setAccessible(true);
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		}
	}

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

		registerGenericAttribute(this, Attributes.ATTACK_DAMAGE);
		getAttributes().getInstance(Attributes.ATTACK_DAMAGE).setBaseValue(1);
		getAttributes().getInstance(Attributes.MOVEMENT_SPEED).setBaseValue(1); // Normal player movement speed
		getAttributes().getInstance(Attributes.MAX_HEALTH).setBaseValue(1);
		this.goalSelector.removeAllGoals((o) -> true);
		this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.0, true));
		this.goalSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, true));

		((CraftWorld) loc.getWorld()).getHandle().addFreshEntity(this, SpawnReason.CUSTOM);
	}

	public void registerGenericAttribute(LivingEntity entity, Attribute attribute) {
		try {
			AttributeMap attributeMap = entity.getAttributes();
			Map<Attribute, AttributeInstance> map = (Map<Attribute, AttributeInstance>) attributeField.get(attributeMap);
			AttributeInstance attributeModifiable = new AttributeInstance(attribute, AttributeInstance::removeModifiers);
			map.put(attribute, attributeModifiable);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}

	}
}
