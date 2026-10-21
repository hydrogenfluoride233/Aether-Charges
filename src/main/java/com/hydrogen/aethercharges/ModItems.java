package com.hydrogen.aethercharges;

import com.hydrogen.aethercharges.entity.Element;
import com.hydrogen.aethercharges.item.CrystalChargeItem;
import com.hydrogen.aethercharges.item.CrystalScepterItem;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.fml.ModList;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(AetherCharges.MODID);
    public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, AetherCharges.MODID);

    public static final DeferredItem<Item> FROST_CHARGE = charge("frost_charge", Element.FROST);
    public static final DeferredItem<Item> BLAZE_CHARGE = charge("blaze_charge", Element.FIRE);
    public static final DeferredItem<Item> THUNDER_CHARGE = charge("thunder_charge", Element.THUNDER);
    public static final DeferredItem<Item> CLOUD_CHARGE = charge("cloud_charge", Element.CLOUD);

    public static final DeferredItem<Item> FROST_SCEPTER = scepter("frost_scepter", Element.FROST);
    public static final DeferredItem<Item> BLAZE_SCEPTER = scepter("blaze_scepter", Element.FIRE);
    public static final DeferredItem<Item> THUNDER_SCEPTER = scepter("thunder_scepter", Element.THUNDER);
    public static final DeferredItem<Item> CLOUD_SCEPTER = scepter("cloud_scepter", Element.CLOUD);

    private static boolean deepAetherLoaded() {
        return ModList.get().isLoaded("deep_aether");
    }

    private static DeferredItem<Item> charge(String name, Element element) {
        return ITEMS.register(name, () -> new CrystalChargeItem(new Item.Properties().stacksTo(16), element));
    }

    private static DeferredItem<Item> scepter(String name, Element element) {
        return ITEMS.register(name, () -> new CrystalScepterItem(new Item.Properties().stacksTo(1), element));
    }

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> TAB = TABS.register("main", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.aether_charges"))
            .icon(() -> FROST_CHARGE.get().getDefaultInstance())
            .displayItems((params, output) -> {
                output.accept(FROST_CHARGE.get());
                output.accept(BLAZE_CHARGE.get());
                output.accept(THUNDER_CHARGE.get());
                if (deepAetherLoaded()) {
                    output.accept(CLOUD_CHARGE.get());
                }
                output.accept(FROST_SCEPTER.get());
                output.accept(BLAZE_SCEPTER.get());
                output.accept(THUNDER_SCEPTER.get());
                if (deepAetherLoaded()) {
                    output.accept(CLOUD_SCEPTER.get());
                }
            }).build());

    private ModItems() {
    }
}
