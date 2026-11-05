package io.github.djsaintg.framedhoppers;

import net.minecraft.world.item.ItemStack;

/** Added to every hopper block entity by {@code HopperBlockEntityMixin}. */
public interface FilteredHopper {
	/** Whether this hopper's item frames let {@code stack} in. */
	boolean framedHoppers$accepts(ItemStack stack);
}
