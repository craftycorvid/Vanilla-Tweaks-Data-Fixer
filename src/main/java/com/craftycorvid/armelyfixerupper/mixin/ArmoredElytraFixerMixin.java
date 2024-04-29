package com.craftycorvid.armelyfixerupper.mixin;

import net.minecraft.datafixer.fix.ItemStackComponentizationFix;
import net.minecraft.datafixer.fix.ItemStackComponentizationFix.StackData;
import java.util.Optional;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import com.mojang.serialization.Dynamic;

@Mixin(ItemStackComponentizationFix.class)
public abstract class ArmoredElytraFixerMixin {
    @Inject(method = "fixStack", at = @At("HEAD"))
    private static void fixStackArmEly(StackData data, Dynamic<?> dynamic, CallbackInfo ci) {
        if (data.itemEquals("minecraft:elytra")) {
            Optional<? extends Dynamic<?>> optional = data.getAndRemove("armElyData").result();
            if (optional.isPresent()) {
                Dynamic<?> armElyDynamic = (Dynamic<?>) optional.get();
                Dynamic<?> newArmElyDynamic = fixArmEly(data, armElyDynamic);
                data.setComponent("minecraft:custom_data", newArmElyDynamic);
            }
        }
    }

    private static Dynamic<?> fixArmEly(StackData data, Dynamic<?> dynamic) {
        Dynamic<?> armElyDynamic = dynamic.emptyMap();
        Dynamic<?> dynamic2 = dynamic.emptyMap();
        Optional<? extends Dynamic<?>> chestplateOptional = dynamic.get("chestplate").result();
        if (chestplateOptional.isPresent()) {
            Dynamic<?> chestplateDynamic = (Dynamic<?>) chestplateOptional.get();
            StackData chestplateData = StackData.fromDynamic(chestplateDynamic).get();
            ItemStackComponentizationFix.fixStack(chestplateData, chestplateData.nbt);
            dynamic2 = dynamic2.set("chestplate", chestplateData.finalize());
        }
        Optional<? extends Dynamic<?>> elytraOptional = dynamic.get("elytra").result();
        if (elytraOptional.isPresent()) {
            Dynamic<?> elytraDynamic = (Dynamic<?>) elytraOptional.get();
            StackData elytraData = StackData.fromDynamic(elytraDynamic).get();
            ItemStackComponentizationFix.fixStack(elytraData, elytraData.nbt);
            dynamic2 = dynamic2.set("elytra", elytraData.finalize());
        }
        Optional<? extends Dynamic<?>> materialOptional = dynamic.get("material").result();
        if (materialOptional.isPresent()) {
            Dynamic<?> materialDynamic = (Dynamic<?>) materialOptional.get();
            dynamic2 = dynamic2.set("material", materialDynamic);
        }
        Optional<? extends Dynamic<?>> armoredOptional = dynamic.get("armored").result();
        if (armoredOptional.isPresent()) {
            Dynamic<?> armoredDynamic = (Dynamic<?>) armoredOptional.get();
            dynamic2 = dynamic2.set("armored", armoredDynamic);
        }
        armElyDynamic = dynamic.set("armElyData", dynamic2);
        return armElyDynamic;
    }
}
