package com.hydrogen.aethercharges.item;

import com.hydrogen.aethercharges.Config;
import com.hydrogen.aethercharges.ModEnchantments;
import com.hydrogen.aethercharges.entity.Element;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

/**
 * Reusable caster: never consumed, gated by a cooldown that enchantments shorten.
 */
public class CrystalScepterItem extends Item {
    private final Element element;

    public CrystalScepterItem(Properties properties, Element element) {
        super(properties);
        this.element = element;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (player.getCooldowns().isOnCooldown(this)) {
            return InteractionResultHolder.fail(stack);
        }
        int empower = ModEnchantments.level(level, ModEnchantments.EMPOWERMENT, stack);
        int volley = ModEnchantments.level(level, ModEnchantments.VOLLEY, stack);
        int swift = ModEnchantments.level(level, ModEnchantments.SWIFT, stack);
        CrystalShot.fire(level, player, element, false, empower, volley);
        if (!level.isClientSide) {
            double factor = Math.max(0.15D, 1.0D - 0.08D * swift);
            int cooldown = (int) Math.round(Config.SCEPTER_COOLDOWN.get() * factor);
            player.getCooldowns().addCooldown(this, Math.max(5, cooldown));
        }
        return InteractionResultHolder.sidedSuccess(stack, level.isClientSide);
    }
}
