package com.craftycorvid.vtdatafixer.mixin;

import java.util.function.BiFunction;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import com.craftycorvid.vtdatafixer.datafix.ArmoredElytraFix;
// import com.craftycorvid.vtdatafixer.datafix.GravesFix;
import com.mojang.datafixers.DataFixerBuilder;
import com.mojang.datafixers.schemas.Schema;
import net.minecraft.datafixer.Schemas;

@Mixin(Schemas.class)
public abstract class DataFixerSchemasMixin {
    @Final
    @Shadow
    private static BiFunction<Integer, Schema, Schema> EMPTY_IDENTIFIER_NORMALIZE;

    @Inject(method = "build", at = @At("TAIL"))
    private static void build(DataFixerBuilder builder, CallbackInfo ci) {
        /*
         * Schema schema223 = builder.addSchema(3838, EMPTY_IDENTIFIER_NORMALIZE);
         * builder.addFixer(new GravesFix(schema223));
         */
        Schema schema224 = builder.addSchema(3953, EMPTY_IDENTIFIER_NORMALIZE);
        builder.addFixer(new ArmoredElytraFix(schema224));
    }
}
