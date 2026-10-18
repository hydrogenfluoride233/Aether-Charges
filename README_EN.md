# Aether Charges

English | [中文](README.md)

Tired of playing half an hour of bowling with the Sun Spirit? Or maybe you just want a turn at being the boss — throw what they throw, right back at them.

| Item | Projectile | Effect |
|---|---|---|
| Hoarfrost Charge | Ice Crystal | 7 damage and slows; freezes the Sun Spirit on hit |
| Blaze Charge | Fire Crystal | 15 damage and sets the target alight |
| Thunderbolt Charge | Thunder Crystal | 5 damage, calls down lightning where it lands |
| Driftcloud Charge | Cloud Crystal | no damage, knocks the target back along the bolt's path |

Charges are single use: one ambrosium shard, one gunpowder and one Aether material, two per craft. The matching scepters never consume anything and can be used again and again, at the cost of a longer cooldown — and they cannot be crafted, only found in the chests of the bronze, silver and gold dungeons.

Damage, cooldowns, projectile speed and the lightning toggle are all in `config/aether_charges-common.toml`.

To read where each item comes from, drop it into the Aether's Book of Lore (天境传说).

## Requirements

- Minecraft 1.21.1 / NeoForge 21.1.x
- The Aether 1.21.1-1.5.x

## Building

```
# 1. put the Aether jar into libs/ (compile-only, never redistributed)
#    libs/aether-1.21.1-1.5.10-neoforge.jar

# 2. build
./gradlew build
```

The jar lands in `build/libs/`. `libs/` is git-ignored.

## Textures

All eight item icons are currently procedurally generated placeholders; `preview/` holds two animation tests. Proper sprites are still being looked for — see [issue #1](https://github.com/hydrogenfluoride233/Aether-Charges/issues/1).

## Credits

- The Aether Team — the crystal entities, model and projectile textures this addon reuses at runtime

## License

MIT
