package com.rebugify.mixin.redstoneDustRepeaterComparatorIgnoreUpwardsStateUpdateEnabled;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.rebugify.Rebugify;
import net.minecraft.world.level.block.ComparatorBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ComparatorBlock.class)
public abstract class ComparatorBlockMixin {
    @ModifyExpressionValue(
            method = "updateShape",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/ComparatorBlock;canSurviveOn(Lnet/minecraft/world/level/LevelReader;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;)Z"),
            allow = 1
    )
    private boolean rebugify$redstoneDustRepeaterComparatorIgnoreUpwardsStateUpdateEnabled_comparator(boolean canPlaceAbove) {
        if (Rebugify.CONFIG.redstoneDustRepeaterComparatorIgnoreUpwardsStateUpdateEnabled.get()) {
            canPlaceAbove = true;
        }
        return canPlaceAbove;
    }
}