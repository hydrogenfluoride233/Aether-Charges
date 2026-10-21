package com.hydrogen.aethercharges.loot;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;
import net.neoforged.fml.ModList;

import java.util.List;

/**
 * 按“这些模组是否全部加载”决定战利品是否注入。
 * loaded=true：全都装了才生效；loaded=false：只要有一个没装就生效。
 */
public record ModsLoadedCondition(List<String> mods, boolean loaded) implements LootItemCondition {
    public static final MapCodec<ModsLoadedCondition> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
            Codec.STRING.listOf().fieldOf("mods").forGetter(ModsLoadedCondition::mods),
            Codec.BOOL.optionalFieldOf("loaded", true).forGetter(ModsLoadedCondition::loaded)
    ).apply(inst, ModsLoadedCondition::new));

    @Override
    public boolean test(LootContext context) {
        boolean all = mods.stream().allMatch(id -> ModList.get().isLoaded(id));
        return all == loaded;
    }

    @Override
    public LootItemConditionType getType() {
        return ModLootConditions.MODS_LOADED.get();
    }
}
