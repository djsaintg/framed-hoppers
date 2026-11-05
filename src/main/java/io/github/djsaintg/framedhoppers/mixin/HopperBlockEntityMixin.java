package io.github.djsaintg.framedhoppers.mixin;

import io.github.djsaintg.framedhoppers.FramedHoppers;
import java.util.Set;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.HopperBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

// Hoppers don't override Container.canPlaceItem, so this adds the override. Vanilla checks it for every
// item that goes into a hopper (from a hopper or chest above, a hopper pointing in, or the ground), and so
// does Lithium's faster hopper code, which skips vanilla's own transfer methods.
// Players can still put anything in by hand: the hopper screen doesn't ask canPlaceItem.
@Mixin(HopperBlockEntity.class)
public abstract class HopperBlockEntityMixin {
	// The frames are looked up at most once per game tick per hopper, not once per slot per item
	@Unique private long framedHoppers$checkedAt = Long.MIN_VALUE;
	@Unique private Set<Item> framedHoppers$filter = Set.of();

	public boolean canPlaceItem(int slot, ItemStack stack) {
		HopperBlockEntity self = (HopperBlockEntity) (Object) this;
		Level level = self.getLevel();
		if (level == null || level.isClientSide()) return true;
		long now = level.getGameTime();
		if (now != framedHoppers$checkedAt) {
			framedHoppers$checkedAt = now;
			framedHoppers$filter = FramedHoppers.findFilter(level, self.getBlockPos());
		}
		return framedHoppers$filter.isEmpty() || framedHoppers$filter.contains(stack.getItem());
	}
}
