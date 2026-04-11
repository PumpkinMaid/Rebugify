package com.rebugify.mixin.redstoneDustRepeaterComparatorIgnoreUpwardsStateUpdateEnabled;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.rebugify.Rebugify;
import net.minecraft.world.level.block.RedStoneWireBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

// comparatorDuplicationEnabled conflicts with this due to the nature of it. Having this enabled alongside it will cause vanilla behavior, and neither will work.
@Mixin(RedStoneWireBlock.class)
public abstract class RedstoneWireBlockMixin {
    @ModifyExpressionValue(
            method = "updateShape",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/RedStoneWireBlock;canSurviveOn(Lnet/minecraft/world/level/BlockGetter;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;)Z"),
            allow = 1
    )
    private boolean rebugify$redstoneDustRepeaterComparatorIgnoreUpwardsStateUpdateEnabled_dust(boolean canRunOnTop) {
        if (Rebugify.CONFIG.redstoneDustRepeaterComparatorIgnoreUpwardsStateUpdateEnabled.get()) {
            canRunOnTop = true;
        }
        return canRunOnTop;
    }
}