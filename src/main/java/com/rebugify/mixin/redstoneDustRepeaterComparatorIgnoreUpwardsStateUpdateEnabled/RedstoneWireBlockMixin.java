package com.rebugify.mixin.redstoneDustRepeaterComparatorIgnoreUpwardsStateUpdateEnabled;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.rebugify.Rebugify;
import net.minecraft.world.level.block.RedStoneWireBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

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