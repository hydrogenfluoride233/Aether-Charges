package com.hydrogen.aethercharges;

import com.hydrogen.aethercharges.entity.CrystalBoltEntity;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITIES =
            DeferredRegister.create(Registries.ENTITY_TYPE, AetherCharges.MODID);

    public static final DeferredHolder<EntityType<?>, EntityType<CrystalBoltEntity>> CRYSTAL_BOLT =
            ENTITIES.register("crystal_bolt", () -> EntityType.Builder
                    .<CrystalBoltEntity>of(CrystalBoltEntity::new, MobCategory.MISC)
                    .sized(0.5F, 0.5F)
                    .clientTrackingRange(4)
                    .updateInterval(10)
                    .build(ResourceLocation.fromNamespaceAndPath(AetherCharges.MODID, "crystal_bolt").toString()));

    private ModEntities() {
    }
}
