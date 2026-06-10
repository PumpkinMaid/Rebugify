package com.rebugify.mixin.eidSuppressionEnabled;

import com.rebugify.Rebugify;
import net.minecraft.server.level.ServerLevel;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.concurrent.atomic.AtomicInteger;

// This is ripped from https://github.com/ForwarD-NerN/AntiShadowPatch. Check that mod out as well!
@Mixin(ServerLevel.class)
public class ServerLevelMixin {
    @Shadow
    @Final
    private static AtomicInteger ENTITY_COUNTER;

    @Inject(method = "getNextEntityId", at = @At("HEAD"), cancellable = true)
    private void rebugify$eidSuppressionEnabled(CallbackInfoReturnable<Integer> cir) {
        if (Rebugify.CONFIG.eidSuppressionEnabled.get()) cir.setReturnValue(ENTITY_COUNTER.incrementAndGet());
    }
}