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
    public static void fire(Level level, Player player, Element element, boolean fromCharge, int empower, int volley) {
        if (level.isClientSide) {
            return;
        }
        Vec3 eye = player.getEyePosition();
        Vec3 look = player.getLookAngle();
        double speed = element == Element.FROST ? Config.FROST_SPEED.get() : Config.BOLT_SPEED.get();
        if (fromCharge) {
            speed *= Config.CHARGE_SPEED_MULTIPLIER.get();
        }
        spawn(level, player, eye, look, element, speed, fromCharge, empower);
        // 连发：左右各偏 8°
        for (int i = 1; i <= volley; i++) {
            double angle = Math.toRadians(8.0) * (i % 2 == 1 ? 1 : -1);
            double cos = Math.cos(angle);
            double sin = Math.sin(angle);
            Vec3 dir = new Vec3(look.x * cos - look.z * sin, look.y, look.x * sin + look.z * cos);
            spawn(level, player, eye, dir, element, speed, fromCharge, empower);
        }
        level.playSound(null, player.getX(), player.getY(), player.getZ(),
                SoundEvents.SNOWBALL_THROW, SoundSource.PLAYERS, 0.5F, 1.1F);
    }

    private static void spawn(Level level, Player player, Vec3 eye, Vec3 dir, Element element,
                              double speed, boolean fromCharge, int empower) {
        CrystalBoltEntity bolt = new CrystalBoltEntity(level, player, element, fromCharge, empower);
        bolt.setPos(eye.x, eye.y, eye.z);
        bolt.setDeltaMovement(dir.scale(speed));
        level.addFreshEntity(bolt);
    }

    private CrystalShot() {
    }
}
