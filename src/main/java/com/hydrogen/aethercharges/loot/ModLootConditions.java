package com.hydrogen.aethercharges.loot;

import com.hydrogen.aethercharges.AetherCharges;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class ModLootConditions {
    public static final DeferredRegister<LootItemConditionType> CONDITIONS =
            DeferredRegister.create(Registries.LOOT_CONDITION_TYPE, AetherCharges.MODID);

    public static final DeferredHolder<LootItemConditionType, LootItemConditionType> MODS_LOADED =
            CONDITIONS.register("mods_loaded", () -> new LootItemConditionType(ModsLoadedCondition.CODEC));

    private ModLootConditions() {
    }
}
