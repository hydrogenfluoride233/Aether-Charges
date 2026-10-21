package com.hydrogen.aethercharges;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;

/**
 * 附魔本身是数据驱动的（data/aether_charges/enchantment/*.json），这里有三个键就够查等级了。
 */
public final class ModEnchantments {
    public static final ResourceKey<Enchantment> EMPOWERMENT = key("empowerment");
    public static final ResourceKey<Enchantment> SWIFT = key("swift");
    public static final ResourceKey<Enchantment> VOLLEY = key("volley");

    private static ResourceKey<Enchantment> key(String name) {
        return ResourceKey.create(Registries.ENCHANTMENT, ResourceLocation.fromNamespaceAndPath(AetherCharges.MODID, name));
    }

    public static int level(Level level, ResourceKey<Enchantment> enchantment, ItemStack stack) {
        return level.registryAccess().registryOrThrow(Registries.ENCHANTMENT)
                .getHolder(enchantment)
                .map(holder -> EnchantmentHelper.getItemEnchantmentLevel(holder, stack))
                .orElse(0);
    }

    /**
     * 读等级并按附魔自己定义的上限钳制：古卷、神化之类的“超等级”不会让数值失控。
     */
    public static int clamped(Level level, ResourceKey<Enchantment> enchantment, ItemStack stack) {
        return level.registryAccess().registryOrThrow(Registries.ENCHANTMENT)
                .getHolder(enchantment)
                .map(holder -> Math.min(EnchantmentHelper.getItemEnchantmentLevel(holder, stack), holder.value().getMaxLevel()))
                .orElse(0);
    }

    private ModEnchantments() {
    }
}
