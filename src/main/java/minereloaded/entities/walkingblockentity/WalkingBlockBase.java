/**
 * 
 */
package minereloaded.entities.walkingblockentity;

import java.util.Random;

import javax.annotation.Nullable;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.craftbukkit.v1_20_R2.CraftWorld;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.Turtle;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.player.Player;

/**
 * Copyright (C) 2023 FlorestanII & Elekt0. <br />
 * All rights reserved.
 * 
 * @author FlorestanII
 * 
 */
public class WalkingBlockBase extends Turtle {

	private static final Random r = new Random();

	private WalkingBlockEntity entity;

	private AttributeMap attributes;
	private EntityDimensions dimensions = new EntityDimensions(0.98F, 0.98F, false);

	public WalkingBlockBase(WalkingBlockEntity entity, Location loc) {
		super(EntityType.TURTLE, ((CraftWorld) loc.getWorld()).getHandle());
		this.entity = entity;

		init();
		setLocation(loc);
		EntityUtil.spawnInWorld(loc.getWorld(), this);
	}

	public void init() {
		// Age: Baby - Locked
		this.setBaby(true);
		this.ageLocked = true;

		// Do not save entity in world files
		this.persist = false;

		// Invisible
		this.setInvisible(true);
		this.persistentInvisibility = true;
		this.updateInvisibilityStatus();

		this.drops.clear();

		// Attributes
		// TODO
		this.attributes.getInstance(Attributes.MOVEMENT_SPEED).setBaseValue(1);

		// AI
		this.goalSelector.removeAllGoals(o -> true);
		this.goalSelector.addGoal(1, new WaterAvoidingRandomStrollGoal(this, 0.5F));
		this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.0, true));
		this.goalSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
	}

	public void setLocation(Location loc) {
		this.setPos(loc.getX(), loc.getY(), loc.getZ());
	}

	@Override
	public boolean hurt(DamageSource damagesource, float f) {
		Entity e = damagesource.getEntity().getBukkitEntity();

		if (e instanceof org.bukkit.entity.Player damager) {
			ItemStack mainHand = damager.getInventory().getItemInMainHand();

			if (mainHand.getType().toString().contains("PICKAXE")) {
				// TODO consider efficiency

				level().getWorld().spawnParticle(Particle.BLOCK_CRACK, this.xo, this.yo + 0.5f, this.zo, 25, 0.2f, 0.2f, 0.2f,
						this.entity.getMaterial().createBlockData());

				return super.hurt(damagesource, f);
			}
		}

		return false;
	}

	@Override
	protected void dropFromLootTable(DamageSource damagesource, boolean flag) {
		// No normal drops
	}

	@Override
	protected void dropCustomDeathLoot(DamageSource damagesource, int i, boolean flag) {
		Entity e = damagesource.getEntity().getBukkitEntity();

		if (e instanceof org.bukkit.entity.Player damager) {
			ItemStack mainHand = damager.getInventory().getItemInMainHand();

			if (mainHand.getEnchantmentLevel(Enchantment.SILK_TOUCH) > 0) {
				this.drops.add(new ItemStack(this.entity.getMaterial()));
			} else {
				int fortune = mainHand.getEnchantmentLevel(Enchantment.LOOT_BONUS_BLOCKS);
				int amount = this.entity.getType().getRandomBaseDropCount();

				amount += amount * r.nextInt(fortune + 1);

				this.drops.add(new ItemStack(this.entity.getType().getDrop(), amount));
			}

		}
	}

	@Override
	protected void tickDeath() {
		try {
			this.entity.getPassenger().kill();
		} catch (IllegalStateException e) {
		}

		super.tickDeath();
	}

	@Override
	public AttributeMap getAttributes() {
		if (this.attributes == null) {
			this.attributes = new AttributeMap(Zombie.createAttributes().build()); // TODO independent builder
		}

		return this.attributes;
	}

	/**
	 * Override entity dimensions to influence melee attack range.
	 * 
	 */
	@Override
	public EntityDimensions getDimensions(Pose entitypose) {
		return this.dimensions;
	}

	@Override
	public boolean canBeHitByProjectile() {
		return false;
	}

	@Override
	public boolean fireImmune() {
		return true;
	}

	@Override
	protected boolean shouldDropLoot() {
		// TODO only if killed with pickaxe
		return true;
	}

	@Override
	protected @Nullable SoundEvent getDeathSound() {
		return SoundEvents.STONE_BREAK;
	}

	@Override
	protected @Nullable SoundEvent getHurtSound(DamageSource damagesource) {
		return SoundEvents.STONE_HIT;
	}

	@Override
	protected @Nullable SoundEvent getAmbientSound() {
		return null;
	}

}
