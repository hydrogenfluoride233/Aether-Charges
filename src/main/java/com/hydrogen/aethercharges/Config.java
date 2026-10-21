package com.hydrogen.aethercharges;

import net.neoforged.neoforge.common.ModConfigSpec;

public final class Config {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    public static final ModConfigSpec.DoubleValue FROST_SPEED = BUILDER
            .comment("Speed of the frost charge (the Aether's own ice crystal).")
            .defineInRange("frost_speed", 2.5D, 0.1D, 10.0D);

    public static final ModConfigSpec.DoubleValue FROST_DAMAGE = BUILDER
            .comment("Damage of the frost bolt.")
            .defineInRange("frost_damage", 7.0D, 0.0D, 100.0D);

    public static final ModConfigSpec.BooleanValue FROST_FREEZES_SUN_SPIRIT = BUILDER
            .comment("Whether the frost bolt freezes the Sun Spirit, like the Aether's own ice crystal.")
            .define("frost_freezes_sun_spirit", true);

    public static final ModConfigSpec.IntValue FROST_FREEZE_TICKS = BUILDER
            .comment("Freeze duration applied to the Sun Spirit, in ticks.")
            .defineInRange("frost_freeze_ticks", 175, 1, 1200);

    public static final ModConfigSpec.DoubleValue BOLT_SPEED = BUILDER
            .comment("Speed of the mod's own crystal bolts.")
            .defineInRange("bolt_speed", 2.0D, 0.1D, 10.0D);

    public static final ModConfigSpec.IntValue CHARGE_COOLDOWN = BUILDER
            .comment("Cooldown after throwing a single-use charge, in ticks.")
            .defineInRange("charge_cooldown", 20, 0, 400);

    public static final ModConfigSpec.IntValue SCEPTER_COOLDOWN = BUILDER
            .comment("Cooldown after casting with a scepter, in ticks.")
            .defineInRange("scepter_cooldown", 20, 0, 400);

    public static final ModConfigSpec.DoubleValue BLAZE_DAMAGE = BUILDER
            .comment("Damage of the blaze bolt.")
            .defineInRange("blaze_damage", 15.0D, 0.0D, 100.0D);

    public static final ModConfigSpec.DoubleValue BLAZE_IGNITE_SECONDS = BUILDER
            .comment("Seconds the blaze bolt sets the target on fire.")
            .defineInRange("blaze_ignite_seconds", 6.0D, 0.0D, 30.0D);

    public static final ModConfigSpec.DoubleValue THUNDER_DAMAGE = BUILDER
            .comment("Damage of the thunder bolt.")
            .defineInRange("thunder_damage", 5.0D, 0.0D, 100.0D);

    public static final ModConfigSpec.BooleanValue THUNDER_SUMMONS_LIGHTNING = BUILDER
            .comment("Whether the thunder bolt calls down lightning.")
            .define("thunder_summon_lightning", true);

    public static final ModConfigSpec.DoubleValue CLOUD_KNOCKBACK = BUILDER
            .comment("Horizontal knockback strength of the cloud bolt.")
            .defineInRange("cloud_knockback", 1.5D, 0.0D, 5.0D);

    public static final ModConfigSpec.DoubleValue CLOUD_DAMAGE = BUILDER
            .comment("Damage of the cloud bolt.")
            .defineInRange("cloud_damage", 0.0D, 0.0D, 100.0D);


    public static final ModConfigSpec.DoubleValue CHARGE_DAMAGE_MULTIPLIER = BUILDER
            .comment("Damage multiplier applied to single-use charges (scepters use the full value).")
            .defineInRange("charge_damage_multiplier", 0.55D, 0.0D, 2.0D);

    public static final ModConfigSpec.DoubleValue CHARGE_SPEED_MULTIPLIER = BUILDER
            .comment("Projectile speed multiplier applied to single-use charges.")
            .defineInRange("charge_speed_multiplier", 0.85D, 0.0D, 2.0D);

    public static final ModConfigSpec.DoubleValue CHARGE_KNOCKBACK_MULTIPLIER = BUILDER
            .comment("Knockback multiplier applied to the cloud charge.")
            .defineInRange("charge_knockback_multiplier", 0.7D, 0.0D, 2.0D);

    public static final ModConfigSpec.DoubleValue CHARGE_FREEZE_MULTIPLIER = BUILDER
            .comment("Freeze duration multiplier applied to the frost charge.")
            .defineInRange("charge_freeze_multiplier", 0.35D, 0.0D, 2.0D);

    public static final ModConfigSpec.DoubleValue CHARGE_IGNITE_MULTIPLIER = BUILDER
            .comment("Ignite duration multiplier applied to the blaze charge.")
            .defineInRange("charge_ignite_multiplier", 0.5D, 0.0D, 2.0D);

    public static final ModConfigSpec SPEC = BUILDER.build();

    private Config() {
    }
}
