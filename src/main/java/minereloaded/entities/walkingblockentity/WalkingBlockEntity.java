/**
 * 
 */
package minereloaded.entities.walkingblockentity;

import org.bukkit.Location;
import org.bukkit.Material;

import minereloaded.OreType;
import net.minecraft.world.entity.Entity.RemovalReason;

/**
 * Copyright (C) 2023 FlorestanII & Elekt0. <br />
 * All rights reserved.
 * 
 * @author FlorestanII
 * 
 */
public class WalkingBlockEntity {

	private final OreType type;

	private WalkingBlockBase base;
	private WalkingBlockPassenger passenger;

	/**
	 * Create a new walking block entity at the given location.
	 * 
	 * @param loc the location to spawn the entity at
	 */
	public WalkingBlockEntity(Location loc, OreType type) {
		this.type = type;

		this.base = new WalkingBlockBase(this, loc);
		this.passenger = new WalkingBlockPassenger(this, loc);

		this.base.getBukkitEntity().addPassenger(this.passenger.getBukkitEntity());
	}

	public void remove() {
		try {
			this.base.remove(RemovalReason.DISCARDED);
		} catch (IllegalStateException e) {
		}
		try {
			this.passenger.remove(RemovalReason.DISCARDED);
		} catch (IllegalStateException e) {
		}
	}

	public OreType getType() {
		return this.type;
	}

	public Material getMaterial() {
		return this.type.getType();
	}

	public WalkingBlockBase getBase() {
		return this.base;
	}

	public WalkingBlockPassenger getPassenger() {
		return this.passenger;
	}

}
