# Aether Charges

An unofficial addon for [The Aether](https://modrinth.com/mod/aether) that turns the bosses' own crystal projectiles into things you can throw.

> Not affiliated with The Aether Team.

## What it adds

| Item | Projectile | Effect |
|---|---|---|
| Hoarfrost Charge | Ice Crystal | 7 damage, slows; freezes the Sun Spirit |
| Blaze Charge | Fire Crystal | 15 damage, sets the target alight |
| Thunderbolt Charge | Thunder Crystal | 5 damage, calls down lightning |
| Driftcloud Charge | Cloud Crystal | knocks the target back along the bolt's path |

Charges are single use. The matching **scepters** fire the same projectile without consuming anything, but have a longer cooldown; they are found in bronze, silver and gold dungeon chests rather than crafted.

Damage, cooldown, projectile speed and the lightning toggle are all in `config/crystal_charges-common.toml`.

## Requirements

- Minecraft 1.21.1, NeoForge 21.1
- The Aether 1.21.1-1.5.x (required)

Lore text is shipped as plain language keys, so the items show up in the Aether's own Book of Lore — no extra library needed.

## Building

```
# 1. drop the Aether jar into libs/ (compile-only dependency)
#    libs/aether-1.21.1-1.5.10-neoforge.jar

# 2. build
./gradlew build
```

The jar lands in `build/libs/`. `libs/` is git-ignored on purpose: the Aether jar is only needed at compile time and is not redistributed.

## Textures

Current item icons are procedurally generated placeholders — see [preview/](preview/) for the animation test. Help with proper sprites is very welcome.

## Credits

- The Aether Team — the crystal entities, model and projectile textures this addon reuses at runtime

## License

MIT
