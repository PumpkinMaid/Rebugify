package com.rebugify.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.rebugify.Rebugify;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.MerchantMenu;
import net.minecraft.world.item.trading.Merchant;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(MerchantMenu.class)
public abstract class MerchantScreenHandlerMixin {
    @Shadow
    @Final
    private Merchant trader;

    @WrapOperation(
            method = "stillValid",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/item/trading/Merchant;stillValid(Lnet/minecraft/world/entity/player/Player;)Z"
            )
    )
    private boolean rebugify$voidTradingEnabled(Merchant instance, Player playerEntity, Operation<Boolean> original) {
        if (Rebugify.CONFIG.voidTradingEnabled.get()) {
            return this.trader.getTradingPlayer() == playerEntity;
        } else {
            return original.call(instance, playerEntity);
        }
    }
}