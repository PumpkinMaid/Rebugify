package com.rebugify.mixin.stringAndTripwireHookDuplicationEnabled;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.rebugify.Rebugify;
import net.minecraft.world.level.block.TripWireHookBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;

@Mixin(TripWireHookBlock.class)
public abstract class TripWireHookBlockMixin {
    @WrapOperation(
            method = "calculateState",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/state/BlockState;is(Ljava/lang/Object;)Z"),
            slice = @Slice(
                    from = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;getBlockState(Lnet/minecraft/core/BlockPos;)Lnet/minecraft/world/level/block/state/BlockState;", ordinal = 1)
            )
    )
    private static boolean rebugify$stringAndTripwireHookDuplicationEnabled(BlockState instance, Object o, Operation<Boolean> original) {
        if (Rebugify.CONFIG.stringDuplicationEnabled.get() || Rebugify.CONFIG.tripwireHookDuplicationEnabled.get()) {
            return true;
        } else {
            return original.call(instance, o);
        }
    }
}