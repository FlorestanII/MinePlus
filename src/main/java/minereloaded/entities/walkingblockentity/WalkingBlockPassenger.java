/**
 * 
 */
package minereloaded.entities.walkingblockentity;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.craftbukkit.v1_20_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_20_R1.util.CraftMagicNumbers;

import net.minecraft.core.BlockPos;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.level.block.Block;

/**
 * Copyright (C) 2023 FlorestanII & Elekt0. <br />
 * All rights reserved.
 * 
 * @author FlorestanII
 * 
 */
public class WalkingBlockPassenger extends FallingBlockEntity {

	private WalkingBlockEntity entity;

	public WalkingBlockPassenger(WalkingBlockEntity entity, Location loc) {
		super(EntityType.FALLING_BLOCK, ((CraftWorld) loc.getWorld()).getHandle());
		this.entity = entity;

		init();
		setLocation(loc);
		EntityUtil.spawnInWorld(loc.getWorld(), this);
	}

	private void init() {
		setNoGravity(true);
		this.persist = false;
		this.hurtEntities = false;

		// Disable drops
		this.dropItem = false;
		this.disableDrop();

		// Set block data
		// TODO destroy progress, is this even possible???
		recreateFromPacket(new ClientboundAddEntityPacket(this, Block.getId(CraftMagicNumbers.getBlock(this.entity.getMaterial()).defaultBlockState())));
	}

	public void setLocation(Location loc) {
		this.setPos(loc.getX(), loc.getY(), loc.getZ());
	}

	@Override
	public void tick() {
		super.tick();

		BlockPos blockposition = blockPosition();
		if (blockposition.getY() >= level().getMinBuildHeight() && blockposition.getY() < level().getMaxBuildHeight()) {
			this.time = 0;
		}
	}

	@Override
	public boolean canBeHitByProjectile() {
		return true;
	}

	@Override
	public boolean isAttackable() {
		return true;
	}

	@Override
	public boolean skipAttackInteraction(Entity entity) {
		return false;
	}

	@Override
	public boolean fireImmune() {
		return true;
	}

	@Override
	public boolean hurt(DamageSource damagesource, float f) {
		if (damagesource.getDirectEntity()instanceof ServerPlayer player) {
			level().getWorld().spawnParticle(Particle.BLOCK_CRACK, this.xo, this.yo + 0.5f, this.zo, 25, 0.2f, 0.2f, 0.2f,
					this.entity.getMaterial().createBlockData());

			return this.entity.getBase().hurt(damagesource, f);
		}

		return false;
	}

}
