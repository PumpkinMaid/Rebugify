package com.rebugify.mixin.eidSuppressionEnabled;

import com.rebugify.Rebugify;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

// This is ripped from https://github.com/ForwarD-NerN/AntiShadowPatch. Check that mod out as well!
@Mixin(Entity.class)
public class EntityMixin {
    @Shadow
    private int id;

    @Inject(method = "getId", at = @At("HEAD"), cancellable = true)
    private void rebugify$preventIllegalIdCrash(CallbackInfoReturnable<Integer> cir) {
        if (Rebugify.CONFIG.eidSuppressionEnabled.get()) cir.setReturnValue(this.id);
    }
}