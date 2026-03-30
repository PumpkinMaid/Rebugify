package com.rebugify.mixin.CCESuppressionEnabled;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.rebugify.Rebugify;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.ShulkerBoxBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ShulkerBoxBlock.class)
public abstract class ShulkerBoxBlockMixin {
    @WrapOperation(
            method = "getAnalogOutputSignal",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/inventory/AbstractContainerMenu;getRedstoneSignalFromBlockEntity(Lnet/minecraft/world/level/block/entity/BlockEntity;)I")
    )
    private int rebugify$CCESuppressionEnabled(BlockEntity blockEntity, Operation<Integer> original) {
        if (Rebugify.CONFIG.CCESuppressionEnabled.get()) {
            return AbstractContainerMenu.getRedstoneSignalFromContainer((Container)blockEntity);
        } else {
            return original.call(blockEntity);
        }
    }
}