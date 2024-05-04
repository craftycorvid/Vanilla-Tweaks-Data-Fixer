package com.craftycorvid.armelyfixerupper;

import java.util.Optional;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.OptionalDynamic;
import net.minecraft.datafixer.TypeReferences;
import net.minecraft.datafixer.fix.ItemStackComponentizationFix;
import net.minecraft.datafixer.fix.ItemStackComponentizationFix.StackData;
import net.minecraft.datafixer.schema.IdentifierNormalizingSchema;

public class ArmoredElytraFix extends DataFix {
    public ArmoredElytraFix(Schema outputSchema) {
        super(outputSchema, true);
    }

    public static void fixArmEly(ArmElyStackData data, Dynamic<?> dynamic) {
        data.getAndRemove("armElyData").result().ifPresent(armElyData -> {
            ArmoredElytraFixerUpper.LOGGER.info("Fixing ArmEly Data");
            Dynamic<?> newArmElyDynamic = fixEmbeddedItems(armElyData);
            data.setComponent("minecraft:custom_data", newArmElyDynamic);
        });
    }

    private static Dynamic<?> fixEmbeddedItems(Dynamic<?> dynamic) {
        Dynamic<?> armElyDynamic = dynamic.emptyMap();
        Dynamic<?> dynamic2 = dynamic.emptyMap();
        Optional<? extends Dynamic<?>> chestplateOptional = dynamic.get("chestplate").result();
        if (chestplateOptional.isPresent()) {
            Dynamic<?> chestplateDynamic = chestplateOptional.get();
            StackData chestplateData = StackData.fromDynamic(chestplateDynamic).get();
            ItemStackComponentizationFix.fixStack(chestplateData, chestplateData.nbt);
            dynamic2 = dynamic2.set("chestplate", chestplateData.finalize());
        }
        Optional<? extends Dynamic<?>> elytraOptional = dynamic.get("elytra").result();
        if (elytraOptional.isPresent()) {
            Dynamic<?> elytraDynamic = elytraOptional.get();
            StackData elytraData = StackData.fromDynamic(elytraDynamic).get();
            ItemStackComponentizationFix.fixStack(elytraData, elytraData.nbt);
            dynamic2 = dynamic2.set("elytra", elytraData.finalize());
        }
        Optional<? extends Dynamic<?>> materialOptional = dynamic.get("material").result();
        if (materialOptional.isPresent()) {
            Dynamic<?> materialDynamic = materialOptional.get();
            dynamic2 = dynamic2.set("material", materialDynamic);
        }
        Optional<? extends Dynamic<?>> armoredOptional = dynamic.get("armored").result();
        if (armoredOptional.isPresent()) {
            Dynamic<?> armoredDynamic = armoredOptional.get();
            dynamic2 = dynamic2.set("armored", armoredDynamic);
        }
        dynamic2 = dynamic2.set("armElyDataVersion", dynamic2.createInt(3839));
        armElyDynamic = armElyDynamic.set("armElyData", dynamic2);
        return armElyDynamic;
    }


    protected TypeRewriteRule makeRule() {
        return this.writeFixAndRead("Armored Elytra componentization",
                this.getInputSchema().getType(TypeReferences.ITEM_STACK),
                this.getOutputSchema().getType(TypeReferences.ITEM_STACK), (dynamic) -> {
                    Optional<? extends Dynamic<?>> optional = ArmElyStackData.fromDynamic(dynamic)
                            .filter(data -> data.itemEquals("minecraft:elytra")).map((data) -> {
                                fixArmEly(data, dynamic);
                                return data.finalize2();
                            });
                    return (Dynamic<?>) DataFixUtils.orElse(optional, dynamic);
                });
    }

    static class ArmElyStackData {
        private final String itemId;
        private Dynamic<?> dynamic;
        private Dynamic<?> components;
        public Dynamic<?> custom_data;

        private ArmElyStackData(String itemId, Dynamic<?> dynamic) {
            this.itemId = IdentifierNormalizingSchema.normalize(itemId);
            this.dynamic = dynamic;
            this.components = dynamic.get("components").orElseEmptyMap();
            this.custom_data = components.get("minecraft:custom_data").orElseEmptyMap();
        }

        public static Optional<ArmElyStackData> fromDynamic(Dynamic<?> dynamic) {
            return dynamic.get("id").asString().apply2stable((itemId, count) -> {
                return new ArmElyStackData(itemId, dynamic);
            }, dynamic.get("count").asNumber()).result();
        }

        public OptionalDynamic<?> getAndRemove(String key) {
            OptionalDynamic<?> optionalDynamic = this.custom_data.get(key);
            this.custom_data = this.custom_data.remove(key);
            return optionalDynamic;
        }

        public void setComponent(String key, Dynamic<?> value) {
            this.components = this.components.set(key, value);
        }

        public Dynamic<?> finalize2() {
            Dynamic<?> dynamic = this.dynamic;
            dynamic = dynamic.set("components", this.components);

            return dynamic;
        }

        public boolean itemEquals(String itemId) {
            return this.itemId.equals(itemId);
        }
    }

}
