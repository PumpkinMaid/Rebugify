package com.rebugify.mixin.comparatorDuplicationEnabled;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.rebugify.Rebugify;
import net.minecraft.world.level.block.DiodeBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

// redstoneDustRepeaterComparatorIgnoreUpwardsStateUpdateEnabled conflicts with this due to the nature of it. Having this enabled alongside it will cause vanilla behavior, and neither will work.
@Mixin(DiodeBlock.class)
public abstract class DiodeBlockMixin {
    @ModifyExpressionValue(method = "neighborChanged", at=@At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/state/BlockState;is(Ljava/lang/Object;)Z"))
    private boolean rebugify$comparatorDuplicationEnabled(boolean original) {
        return Rebugify.CONFIG.comparatorDuplicationEnabled.get() || original;
    }
}