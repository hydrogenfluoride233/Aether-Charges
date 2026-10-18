package com.hydrogen.aethercharges.item;

import com.hydrogen.aethercharges.Config;
import com.hydrogen.aethercharges.entity.Element;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

/**
 * Reusable caster: never consumed, gated by a longer cooldown.
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
        CrystalShot.fire(level, player, element);
        if (!level.isClientSide) {
            player.getCooldowns().addCooldown(this, Config.SCEPTER_COOLDOWN.get());
        }
        return InteractionResultHolder.sidedSuccess(stack, level.isClientSide);
    }
}
