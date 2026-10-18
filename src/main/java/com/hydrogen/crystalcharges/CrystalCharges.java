package com.hydrogen.crystalcharges;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;

@Mod(CrystalCharges.MODID)
public class CrystalCharges {
    public static final String MODID = "crystal_charges";

    public CrystalCharges(IEventBus modBus, ModContainer container) {
        ModItems.ITEMS.register(modBus);
        ModItems.TABS.register(modBus);
        ModEntities.ENTITIES.register(modBus);
        container.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }
}
