package io.github.djsaintg.framedhoppers.mixin;

import io.github.djsaintg.framedhoppers.FilteredHopper;
import io.github.djsaintg.framedhoppers.FramedHoppers;
import java.util.Set;
import net.minecraft.core.Direction;
import net.minecraft.world.Container;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.HopperBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

// Every way an item gets into a hopper (from a hopper or chest above, from a hopper pointing into it,
// or picked up off the ground) goes through canPlaceItemInContainer, so one check covers them all.
@Mixin(HopperBlockEntity.class)
public abstract class HopperBlockEntityMixin implements FilteredHopper {
	// The frames are looked up at most once per game tick per hopper, not once per slot per item
	@Unique private long framedHoppers$checkedAt = Long.MIN_VALUE;
	@Unique private Set<Item> framedHoppers$filter = Set.of();

	@Override
	public boolean framedHoppers$accepts(ItemStack stack) {
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

	@Inject(method = "canPlaceItemInContainer", at = @At("HEAD"), cancellable = true)
	private static void framedHoppers$filter(Container container, ItemStack stack, int slot, Direction direction,
			CallbackInfoReturnable<Boolean> cir) {
		if (container instanceof FilteredHopper hopper && !hopper.framedHoppers$accepts(stack)) cir.setReturnValue(false);
	}
}
