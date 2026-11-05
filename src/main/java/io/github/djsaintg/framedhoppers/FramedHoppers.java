package io.github.djsaintg.framedhoppers;

import java.util.HashSet;
import java.util.Set;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.decoration.ItemFrame;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

/** A hopper with item frames on it only takes in the items those frames show. All the work happens on the server. */
public final class FramedHoppers {
	public static final String MOD_ID = "framed_hoppers";

	private FramedHoppers() {}

	/**
	 * The items shown in the frames attached to the hopper at {@code pos}, or an empty set if it has no
	 * filled frames (so it works like a normal hopper). Glow item frames count too.
	 */
	public static Set<Item> findFilter(Level level, BlockPos pos) {
		Set<Item> filter = new HashSet<>();
		for (ItemFrame frame : level.getEntitiesOfClass(ItemFrame.class, new AABB(pos).inflate(1.0),
				f -> f.isAlive() && f.getPos().relative(f.getDirection().getOpposite()).equals(pos))) {
			ItemStack shown = frame.getItem();
			if (!shown.isEmpty()) filter.add(shown.getItem());
		}
		return filter;
	}
}
