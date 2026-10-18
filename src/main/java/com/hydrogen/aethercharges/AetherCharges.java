package com.hydrogen.aethercharges;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;

@Mod(AetherCharges.MODID)
public class AetherCharges {
    public static final String MODID = "aether_charges";

    public AetherCharges(IEventBus modBus, ModContainer container) {
        ModItems.ITEMS.register(modBus);
        ModItems.TABS.register(modBus);
        ModEntities.ENTITIES.register(modBus);
        container.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }
}
