package com.hydrogen.aethercharges.entity;

import com.hydrogen.aethercharges.Config;
import com.hydrogen.aethercharges.ModEntities;
import com.hydrogen.aethercharges.ModItems;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.HitResult;

/**
 * One projectile type, four flavours: the element is synced so the client renders
 * the matching charge icon and the server applies the matching effect.
 */
public class CrystalBoltEntity extends ThrowableItemProjectile {
    private static final EntityDataAccessor<Integer> DATA_ELEMENT =
            SynchedEntityData.defineId(CrystalBoltEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> DATA_FROM_CHARGE =
            SynchedEntityData.defineId(CrystalBoltEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> DATA_EMPOWER =
            SynchedEntityData.defineId(CrystalBoltEntity.class, EntityDataSerializers.INT);

    public CrystalBoltEntity(EntityType<? extends CrystalBoltEntity> type, Level level) {
        super(type, level);
    }

    public CrystalBoltEntity(Level level, LivingEntity owner, Element element, boolean fromCharge, int empower) {
        super(ModEntities.CRYSTAL_BOLT.get(), owner, level);
        setElement(element);
        setFromCharge(fromCharge);
        entityData.set(DATA_EMPOWER, empower);
        setItem(stackFor(element));
    }

    private static ItemStack stackFor(Element element) {
        Item item = switch (element) {
            case FIRE -> ModItems.BLAZE_CHARGE.get();
            case THUNDER -> ModItems.THUNDER_CHARGE.get();
            case CLOUD -> ModItems.CLOUD_CHARGE.get();
            case FROST -> ModItems.FROST_CHARGE.get();
        };
        return new ItemStack(item);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(DATA_ELEMENT, Element.FIRE.ordinal());
        builder.define(DATA_FROM_CHARGE, false);
        builder.define(DATA_EMPOWER, 0);
    }

    public Element element() {
        Element[] values = Element.values();
        int index = entityData.get(DATA_ELEMENT);
        return values[Math.floorMod(index, values.length)];
    }

    public void setElement(Element element) {
        entityData.set(DATA_ELEMENT, element.ordinal());
    }

    public int empower() {
        return entityData.get(DATA_EMPOWER);
    }

    public boolean fromCharge() {
        return entityData.get(DATA_FROM_CHARGE);
    }

    public void setFromCharge(boolean fromCharge) {
        entityData.set(DATA_FROM_CHARGE, fromCharge);
    }

    @Override
    protected Item getDefaultItem() {
        return ModItems.BLAZE_CHARGE.get();
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        super.onHitEntity(result);
        if (level().isClientSide) {
            return;
        }
        if (result.getEntity() instanceof LivingEntity target) {
            Element element = element();
            float damage = switch (element) {
                case FIRE -> Config.BLAZE_DAMAGE.get().floatValue();
                case THUNDER -> Config.THUNDER_DAMAGE.get().floatValue();
                case CLOUD -> Config.CLOUD_DAMAGE.get().floatValue();
                case FROST -> Config.FROST_DAMAGE.get().floatValue();
            };
            if (fromCharge()) {
                damage *= Config.CHARGE_DAMAGE_MULTIPLIER.get().floatValue();
            }
            boolean damaged = damage > 0.0F && target.hurt(damageSource(element), damage);
            applyOnHit(element, target, damaged);
        }
        discard();
    }

    @Override
    protected void onHit(HitResult result) {
        super.onHit(result);
        if (!level().isClientSide && result.getType() == HitResult.Type.BLOCK) {
            if (element() == Element.THUNDER && Config.THUNDER_SUMMONS_LIGHTNING.get()) {
                strikeLightning();
            }
            discard();
        }
    }

    private void applyOnHit(Element element, LivingEntity target, boolean damaged) {
        switch (element) {
            case FIRE -> {
                if (damaged) {
                    float seconds = Config.BLAZE_IGNITE_SECONDS.get().floatValue();
                    if (fromCharge()) {
                        seconds *= Config.CHARGE_IGNITE_MULTIPLIER.get().floatValue();
                    }
                    seconds += 1.0F * empower();
                    target.igniteForSeconds(Math.min(60.0F, seconds));
                }
            }
            case THUNDER -> {
                // 落雷是独立效果：即使目标免疫这一发伤害，也该打下来
                if (Config.THUNDER_SUMMONS_LIGHTNING.get()) {
                    strikeLightning();
                    // 晶化：每级 +12% 概率多落一道雷，最高 100%
                    double chance = Math.min(1.0D, 0.12D * empower());
                    if (chance > 0.0D && random.nextDouble() < chance) {
                        strikeLightning(getX() + (random.nextDouble() - 0.5D) * 3.0D,
                                getZ() + (random.nextDouble() - 0.5D) * 3.0D);
                    }
                }
            }
            case CLOUD -> {
                // 照抄西风云团的击退：先给一点上抛，再叠 1.5 倍弹道
                Vec3 current = target.getDeltaMovement();
                target.setDeltaMovement(current.x, current.y + 0.5D, current.z);
                Vec3 self = getDeltaMovement();
                double factor = Config.CLOUD_KNOCKBACK.get();
                if (fromCharge()) {
                    factor *= Config.CHARGE_KNOCKBACK_MULTIPLIER.get();
                }
                factor = Math.min(10.0D, factor * (1.0D + 0.15D * empower()));
                target.setDeltaMovement(target.getDeltaMovement().x + self.x * factor,
                        target.getDeltaMovement().y,
                        target.getDeltaMovement().z + self.z * factor);
                target.hurtMarked = true;
                level().playSound(null, getX(), getY(), getZ(),
                        net.minecraft.sounds.SoundEvent.createVariableRangeEvent(
                                net.minecraft.resources.ResourceLocation.fromNamespaceAndPath("aether", "entity.zephyr.shoot")),
                        net.minecraft.sounds.SoundSource.PLAYERS, 0.8F, 1.0F);
            }
            case FROST -> {
                if (damaged) {
                    target.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 120, 0));
                    freezeSunSpirit(target);
                }
            }
            default -> {
            }
        }
    }

    private void strikeLightning() {
        strikeLightning(getX(), getZ());
    }

    private void strikeLightning(double x, double z) {
        if (level() instanceof ServerLevel server) {
            LightningBolt bolt = EntityType.LIGHTNING_BOLT.create(server);
            if (bolt != null) {
                bolt.moveTo(x, getY(), z);
                bolt.setCause(getOwner() instanceof net.minecraft.server.level.ServerPlayer player ? player : null);
                server.addFreshEntity(bolt);
            }
        }
    }

    private net.minecraft.world.damagesource.DamageSource damageSource(Element element) {
        return switch (element) {
            case THUNDER -> level().damageSources().lightningBolt();
            case FROST -> coldDamage();
            default -> level().damageSources().indirectMagic(this, getOwner());
        };
    }

    /**
     * The Aether only lets cold damage through the Sun Spirit's shield, so the frost
     * bolt borrows that damage type instead of shipping its own.
     */
    private net.minecraft.world.damagesource.DamageSource coldDamage() {
        var registry = level().registryAccess().registryOrThrow(net.minecraft.core.registries.Registries.DAMAGE_TYPE);
        var key = net.minecraft.resources.ResourceKey.create(net.minecraft.core.registries.Registries.DAMAGE_TYPE,
                net.minecraft.resources.ResourceLocation.fromNamespaceAndPath("aether", "ice_crystal"));
        return registry.getHolder(key)
                .<net.minecraft.world.damagesource.DamageSource>map(holder ->
                        new net.minecraft.world.damagesource.DamageSource(holder, this, getOwner()))
                .orElseGet(() -> level().damageSources().indirectMagic(this, getOwner()));
    }

    /**
     * Freeze the Sun Spirit through its public API, so the mod keeps compiling without The Aether.
     */
    private void freezeSunSpirit(LivingEntity target) {
        if (!Config.FROST_FREEZES_SUN_SPIRIT.get()) {
            return;
        }
        if (target.getType() != net.minecraft.core.registries.BuiltInRegistries.ENTITY_TYPE
                .get(net.minecraft.resources.ResourceLocation.fromNamespaceAndPath("aether", "sun_spirit"))) {
            return;
        }
        try {
            target.getClass().getMethod("setFrozen", boolean.class).invoke(target, true);
            int ticks = Config.FROST_FREEZE_TICKS.get();
            if (fromCharge()) {
                ticks = Math.max(1, (int) (ticks * Config.CHARGE_FREEZE_MULTIPLIER.get()));
            }
            ticks = Math.min(600, (int) (ticks * (1.0D + 0.10D * empower())));
            target.getClass().getMethod("setFrozenDuration", int.class).invoke(target, ticks);
        } catch (ReflectiveOperationException ignored) {
            // The Aether changed its API; the frost bolt still damages, it just stops freezing.
        }
    }

    @Override
    public void tick() {
        super.tick();
        if (level().isClientSide) {
            if (element() == Element.FIRE && random.nextFloat() < 0.4F) {
                level().addParticle(ParticleTypes.FLAME, getX(), getY(), getZ(), 0.0, 0.0, 0.0);
            }
        } else if (tickCount > 100) {
            discard();
        }
    }

    @Override
    public void remove(Entity.RemovalReason reason) {
        super.remove(reason);
        if (reason == Entity.RemovalReason.DISCARDED && level() instanceof ServerLevel server) {
            server.sendParticles(ParticleTypes.CRIT, getX(), getY(), getZ(), 8, 0.2, 0.2, 0.2, 0.0);
            String sound = switch (element()) {
                case FIRE -> "entity.fire_crystal.explode";
                case THUNDER -> "entity.thunder_crystal.explode";
                case CLOUD -> "entity.cloud_crystal.explode";
                case FROST -> "entity.ice_crystal.explode";
            };
            level().playSound(null, getX(), getY(), getZ(),
                    SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath("aether", sound)),
                    SoundSource.PLAYERS, 0.7F, 1.0F);
        }
    }
}
