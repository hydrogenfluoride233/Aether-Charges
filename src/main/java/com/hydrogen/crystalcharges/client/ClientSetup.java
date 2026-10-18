package com.hydrogen.crystalcharges.client;

import com.hydrogen.crystalcharges.CrystalCharges;
import com.hydrogen.crystalcharges.ModEntities;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;

@EventBusSubscriber(modid = CrystalCharges.MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public final class ClientSetup {
    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(ModEntities.CRYSTAL_BOLT.get(), CrystalBoltRenderer::new);
    }

    private ClientSetup() {
    }
}
