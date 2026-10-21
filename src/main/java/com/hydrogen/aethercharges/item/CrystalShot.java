package com.hydrogen.aethercharges.item;

import com.hydrogen.aethercharges.Config;
import com.hydrogen.aethercharges.entity.CrystalBoltEntity;
import com.hydrogen.aethercharges.entity.Element;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

/**
 * Fires one crystal along the player's look direction.
 */
public final class CrystalShot {
    public static void fire(Level level, Player player, Element element, boolean fromCharge) {
        if (level.isClientSide) {
            return;
        }
        Vec3 eye = player.getEyePosition();
        Vec3 look = player.getLookAngle();
        double speed = element == Element.FROST ? Config.FROST_SPEED.get() : Config.BOLT_SPEED.get();
        if (fromCharge) {
            speed *= Config.CHARGE_SPEED_MULTIPLIER.get();
        }
        CrystalBoltEntity bolt = new CrystalBoltEntity(level, player, element, fromCharge);
        bolt.setPos(eye.x, eye.y, eye.z);
        bolt.setDeltaMovement(look.scale(speed));
        level.addFreshEntity(bolt);
        level.playSound(null, player.getX(), player.getY(), player.getZ(),
                SoundEvents.SNOWBALL_THROW, SoundSource.PLAYERS, 0.5F, 1.1F);
    }

    private CrystalShot() {
    }
}
