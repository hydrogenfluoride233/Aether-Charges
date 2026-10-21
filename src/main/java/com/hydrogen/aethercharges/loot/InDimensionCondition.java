package com.hydrogen.aethercharges.loot;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;

/**
 * 只在指定维度生效，用来把战利品限定在天境。
 */
public record InDimensionCondition(ResourceLocation dimension) implements LootItemCondition {
    public static final MapCodec<InDimensionCondition> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
            ResourceLocation.CODEC.fieldOf("dimension").forGetter(InDimensionCondition::dimension)
    ).apply(inst, InDimensionCondition::new));

    @Override
    public boolean test(LootContext context) {
        return context.getLevel().dimension().location().equals(dimension);
    }

    @Override
    public LootItemConditionType getType() {
        return ModLootConditions.IN_DIMENSION.get();
    }
}
