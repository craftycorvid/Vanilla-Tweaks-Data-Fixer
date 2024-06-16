package com.craftycorvid.vtdatafixer.datafix;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.IntStream;
import com.craftycorvid.vtdatafixer.VanillaTweaksDataFixer;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.OptionalDynamic;
import net.minecraft.datafixer.TypeReferences;
import net.minecraft.datafixer.fix.ItemStackComponentizationFix;
import net.minecraft.datafixer.fix.ItemStackComponentizationFix.StackData;
import net.minecraft.util.Uuids;

public class ArmoredElytraFix extends DataFix {
    public ArmoredElytraFix(Schema outputSchema) {
        super(outputSchema, true);
    }

    public static void fixArmEly(ArmElyStackData data) {
        data.getAndRemoveCustomData("armElyData").result().ifPresent(armElyData -> {
            VanillaTweaksDataFixer.LOGGER.info("Fixing Armored Elytra Data");
            Dynamic<?> bundleContentsDynamic = fixEmbeddedItems(armElyData);
            data.setComponent("minecraft:bundle_contents", bundleContentsDynamic);
            Dynamic<?> armoredElytraDynamic = fixArmElyData(armElyData);
            data.setComponent("minecraft:custom_data", armoredElytraDynamic);
        });
        data.getAndRemoveAttributeModifeirs("modifiers").result().ifPresent(attributeModifiers -> {
            VanillaTweaksDataFixer.LOGGER.info("Fixing Armored Elytra Attributes");
            Dynamic<?> fixedModifiers = fixModifiers(attributeModifiers);
            data.setComponent("minecraft:attribute_modifiers", fixedModifiers);
        });
    }

    private static Dynamic<?> fixEmbeddedItems(Dynamic<?> dynamic) {
        List<Dynamic<?>> bundleContentsList = new ArrayList<Dynamic<?>>();
        dynamic.get("chestplate").result().ifPresent(chestplateDynamic -> {
            StackData chestplateData = StackData.fromDynamic(chestplateDynamic).get();
            ItemStackComponentizationFix.fixStack(chestplateData, chestplateData.nbt);
            bundleContentsList.add(chestplateData.finalize());
        });
        dynamic.get("elytra").result().ifPresent(elytraDynamic -> {
            StackData elytraData = StackData.fromDynamic(elytraDynamic).get();
            ItemStackComponentizationFix.fixStack(elytraData, elytraData.nbt);
            bundleContentsList.add(elytraData.finalize());
        });
        return dynamic.createList(bundleContentsList.stream());
    }

    private static Dynamic<?> fixArmElyData(Dynamic<?> dynamic) {
        Dynamic<?> newArmElyData = dynamic.emptyMap();
        Optional<? extends Dynamic<?>> armoredDynamic = dynamic.get("armored").result();
        if (armoredDynamic.isPresent()) {
            newArmElyData = newArmElyData.set("armored", armoredDynamic.get());
        }
        return dynamic.emptyMap().set("armored_elytra", newArmElyData);
    }

    private static Dynamic<?> fixModifiers(Dynamic<?> dynamic) {
        List<Dynamic<?>> newAttributeList = new ArrayList<Dynamic<?>>();
        List<Dynamic<?>> attributeDynamicList =
                dynamic.asList(attributeDynamic -> attributeDynamic);
        attributeDynamicList
                .forEach(attributeDynamic -> newAttributeList.add(attributeDynamic.set("uuid",
                        dynamic.createIntList(IntStream.of(Uuids.toIntArray(UUID.randomUUID()))))));
        return dynamic.createList(newAttributeList.stream());
    }

    protected TypeRewriteRule makeRule() {
        return this.writeFixAndRead("Armored Elytra componentization",
                this.getInputSchema().getType(TypeReferences.ITEM_STACK),
                this.getOutputSchema().getType(TypeReferences.ITEM_STACK), (dynamic) -> {
                    Optional<? extends Dynamic<?>> optional =
                            ArmElyStackData.fromDynamic(dynamic).map((data) -> {
                                fixArmEly(data);
                                return data.finalize2();
                            });
                    return (Dynamic<?>) DataFixUtils.orElse(optional, dynamic);
                });
    }

    static class ArmElyStackData {
        private Dynamic<?> dynamic;
        private Dynamic<?> components;
        public Dynamic<?> custom_data;
        public Dynamic<?> attribute_modifiers;

        private ArmElyStackData(Dynamic<?> dynamic) {
            this.dynamic = dynamic;
            this.components = dynamic.get("components").orElseEmptyMap();
            this.custom_data = components.get("minecraft:custom_data").orElseEmptyMap();
            this.attribute_modifiers =
                    components.get("minecraft:attribute_modifiers").orElseEmptyMap();
        }

        public static Optional<ArmElyStackData> fromDynamic(Dynamic<?> dynamic) {
            Boolean isElytra = dynamic.get("id").asString().result()
                    .filter(i -> i.equals("minecraft:elytra")).isPresent();
            if (isElytra) {
                return Optional.of(new ArmElyStackData(dynamic));
            } else {
                return Optional.empty();
            }
        }

        public OptionalDynamic<?> getAndRemoveCustomData(String key) {
            OptionalDynamic<?> optionalDynamic = this.custom_data.get(key);
            this.custom_data = this.custom_data.remove(key);
            return optionalDynamic;
        }

        public OptionalDynamic<?> getAndRemoveAttributeModifeirs(String key) {
            OptionalDynamic<?> optionalDynamic = this.attribute_modifiers.get(key);
            this.attribute_modifiers = this.attribute_modifiers.remove(key);
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
    }

}
