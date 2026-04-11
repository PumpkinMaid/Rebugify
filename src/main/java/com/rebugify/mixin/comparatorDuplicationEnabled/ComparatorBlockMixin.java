package com.rebugify.mixin.comparatorDuplicationEnabled;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.rebugify.Rebugify;
import net.minecraft.world.level.block.ComparatorBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

// redstoneDustRepeaterComparatorIgnoreUpwardsStateUpdateEnabled conflicts with this due to the nature of it. Having this enabled alongside it will cause vanilla behavior, and neither will work.
@Mixin(ComparatorBlock.class)
public abstract class ComparatorBlockMixin {
    @ModifyExpressionValue(method = "useWithoutItem", at=@At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/state/BlockState;is(Ljava/lang/Object;)Z"))
    private boolean rebugify$comparatorDuplicationEnabled(boolean original) {
        return Rebugify.CONFIG.comparatorDuplicationEnabled.get() || original;
    }
}