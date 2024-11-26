package com.craftycorvid.vtdatafixer.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtHelper;

@Mixin(NbtHelper.class)
public abstract class NbtHelperReportLowerVersionMixin {
    @WrapOperation(method = "getDataVersion", at = @At(value = "INVOKE",
            target = "Lnet/minecraft/nbt/NbtCompound;getInt(Ljava/lang/String;)I"))
    private static int reportLowerVersion(NbtCompound instance, String key,
            Operation<Integer> original) {
        int dataVersion = original.call(instance, key);
        if (dataVersion == 3955) {
            return 3954;
        } else {
            return dataVersion;
        }
    }
}
