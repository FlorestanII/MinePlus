/**
 * 
 */
package minereloaded;

import java.util.Random;

import org.bukkit.Material;

/**
 * Copyright (C) 2023 FlorestanII. <br />
 * All rights reserved.
 * 
 * @author FlorestanII
 * 
 */
public class OreType {

//	COAL(Material.COAL_ORE, Material.COAL), IRON(Material.IRON_ORE, Material.RAW_IRON), COPPER(Material.COPPER_ORE, Material.RAW_COPPER),
//	GOLD(Material.GOLD_ORE, Material.RAW_GOLD), DIAMOND(Material.DIAMOND_ORE, Material.DIAMOND), EMERALD(Material.EMERALD_ORE, Material.EMERALD),
//	REDSTONE(Material.REDSTONE_ORE, Material.REDSTONE, 2, 4), LAPIS(Material.LAPIS_ORE, Material.LAPIS_LAZULI, 2, 4),
//	DEEP_COAL(Material.DEEPSLATE_COAL_ORE, Material.COAL), DEEP_IRON(Material.DEEPSLATE_IRON_ORE, Material.RAW_IRON),
//	DEEP_COPPER(Material.DEEPSLATE_COPPER_ORE, Material.RAW_COPPER), DEEP_GOLD(Material.DEEPSLATE_GOLD_ORE, Material.RAW_GOLD),
//	DEEP_DIAMOND(Material.DEEPSLATE_DIAMOND_ORE, Material.DIAMOND), DEEP_EMERALD(Material.DEEPSLATE_EMERALD_ORE, Material.EMERALD),
//	DEEP_REDSTONE(Material.DEEPSLATE_REDSTONE_ORE, Material.REDSTONE, 2, 4), DEEP_LAPIS(Material.DEEPSLATE_LAPIS_ORE, Material.LAPIS_LAZULI, 2, 4),
//	NETHER_QUARTZ(Material.NETHER_QUARTZ_ORE, Material.QUARTZ), NETHER_GOLD(Material.NETHER_GOLD_ORE, Material.GOLD_NUGGET, 1, 10),
//	ANCIENT_DEBRIS(Material.ANCIENT_DEBRIS, Material.ANCIENT_DEBRIS);

	private static final Random r = new Random();

	private final Material oreType;
	private final Material dropType;

	private final int baseDropCountMin;
	private final int baseDropCountMax;

	public OreType(Material oreType, Material dropType) {
		this(oreType, dropType, 1, 1);
	}

	public OreType(Material oreType, Material dropType, int baseDropCountMin, int baseDropCountMax) {
		this.oreType = oreType;
		this.dropType = dropType;

		this.baseDropCountMin = baseDropCountMin;
		this.baseDropCountMax = baseDropCountMax;
	}

	public Material getType() {
		return this.oreType;
	}

	public Material getDrop() {
		return this.dropType;
	}

	public int getRandomBaseDropCount() {
		return this.baseDropCountMin + r.nextInt(this.baseDropCountMax - this.baseDropCountMin + 1);
	}

}
