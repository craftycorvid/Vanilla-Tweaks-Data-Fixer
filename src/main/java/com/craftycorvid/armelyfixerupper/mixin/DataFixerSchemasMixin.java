package com.craftycorvid.armelyfixerupper.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import com.mojang.datafixers.DataFixerBuilder;
import com.mojang.datafixers.schemas.Schema;
import net.minecraft.datafixer.Schemas;
import net.minecraft.datafixer.schema.IdentifierNormalizingSchema;
import com.craftycorvid.armelyfixerupper.ArmoredElytraFix;

@Mixin(Schemas.class)
public abstract class DataFixerSchemasMixin {
    @Inject(method = "build", at = @At("TAIL"))
    private static void build(DataFixerBuilder builder, CallbackInfo ci) {
        Schema schema223 = builder.addSchema(3839, IdentifierNormalizingSchema::new);
        builder.addFixer(new ArmoredElytraFix(schema223));
    }
}
