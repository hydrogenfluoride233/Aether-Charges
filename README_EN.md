# Aether Charges

![logo](logo.png)

[中文](README.md) | English

Tired of playing half an hour of bowling with the Sun Spirit? Or maybe you just want a turn at being the boss — throw what they throw, right back at them? Then this mod is for you.

**Aether Charges** adds four charges and their scepters to the Aether:

| Item | Projectile | Effect |
|---|---|---|
| Hoarfrost Charge / Hoarfrost Scepter | Ice Crystal | 7 damage and slows; freezes the Sun Spirit on hit |
| Blaze Charge / Blaze Scepter | Fire Crystal | 15 damage and sets the target alight |
| Thunderbolt Charge / Thunderbolt Scepter | Thunder Crystal | 5 damage, calls down lightning where it lands |
| Driftcloud Charge / Driftcloud Scepter | Cloud Crystal | no damage, knocks the target back along the bolt's path |

Charges are single use; scepters come from the bosses' treasure chests and can be used again and again. Charges hit softer than scepters by design — the scepter is the one you keep.

Scepters can be enchanted:

| Enchantment | Effect |
|---|---|
| Crystallisation | Stronger elemental effect: longer freeze, longer burn, a chance of extra lightning, stronger knockback |
| Swiftness | Shorter cooldown |
| Volley | One extra crystal per cast, slightly spread |

Enchanted books show up in the dungeons' regular chests, in Explore Ruins: The Aether's ruins, and occasionally on a fishing line in the Aether.

Damage, cooldowns, projectile speed, the lightning toggle and every multiplier are in `config/aether_charges-common.toml`.

## Integration

- **Deep Aether**: the Driftcloud charge and scepter only exist when it is installed
- **Deep Aether + Aether: Treasure Reforging**: all four scepters can be crafted in the Combiner — Nature's Staff + the matching charge + a reforging material

To read where each item comes from, drop it into the Aether's Book of Lore.

## Requirements

- Minecraft 1.21.1 / NeoForge 21.1.x
- The Aether 1.21.1-1.5.x

## Credits

- [sweetcornna](https://github.com/sweetcornna) — all item icons
- The Aether Team — the crystal entities, model and projectile textures this addon reuses at runtime

## License

MIT
