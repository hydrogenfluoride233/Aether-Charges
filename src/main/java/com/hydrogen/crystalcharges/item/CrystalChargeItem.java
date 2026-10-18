package com.hydrogen.crystalcharges.item;

import com.hydrogen.crystalcharges.Config;
import com.hydrogen.crystalcharges.entity.Element;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

/**
 * Single-use shot: consumes one and fires.
 */
public class CrystalChargeItem extends Item {
    private final Element element;

    public CrystalChargeItem(Properties properties, Element element) {
        super(properties);
        this.element = element;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        CrystalShot.fire(level, player, element);
        if (!level.isClientSide) {
            if (!player.getAbilities().instabuild) {
                stack.shrink(1);
            }
            player.getCooldowns().addCooldown(this, Config.CHARGE_COOLDOWN.get());
        }
        return InteractionResultHolder.sidedSuccess(stack, level.isClientSide);
    }
}
