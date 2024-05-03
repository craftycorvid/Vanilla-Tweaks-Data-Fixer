package com.craftycorvid.armelyfixerupper.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.datafixers.DataFixerBuilder;

@Mixin(DataFixerBuilder.class)
public abstract class DataFixerBuilderMixin {
    @WrapOperation(method = "addFixer", at = @At(value = "INVOKE",
            target = "Lcom/mojang/datafixers/DataFixUtils;getVersion(I)I"), remap = false)
    public int addFixerBypassVersionCheck(int versionKey, Operation<Integer> original) {
        return 0;
    }
}
