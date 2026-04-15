# Happy Ghast Dash

A Minecraft mod that adds a **charge-and-dash mechanic** to the Happy Ghast mount, similar to the Horse Jump bar.

**Fabric + NeoForge** | Minecraft 26.1.2 | Java 25

## Features

- **Dash charge bar** on the HUD when riding a Happy Ghast (above the XP bar)
- Hold **G** (configurable) to charge, release to boost forward
- Charge bar transitions from cyan to white with a **golden sweet spot** at 80%+
- 3-second cooldown between dashes (grey bar indicator)
- Vertical lift during dash to maintain altitude

## Building

```bash
./gradlew build
```

Output JARs:
- `fabric/build/libs/ghastdash-fabric-*.jar`
- `neoforge/build/libs/ghastdash-neoforge-*.jar`

## Development

```bash
# Run Fabric client
./gradlew :fabric:runClient

# Run NeoForge client
./gradlew :neoforge:runClient
```

### Test in-game

Create a **Superflat Creative** world with cheats enabled, then run:

```
/function ghastdash:test_ride
```

This sets up a peaceful environment with a harnessed Happy Ghast and mounts you on it.

Or for manual setup:
```
/function ghastdash:test_setup
```

## Project Structure

```
common/     Shared code (vanilla MC only) - dash logic, HUD, state machine
fabric/     Fabric loader entrypoints, keybind & HUD registration
neoforge/   NeoForge loader entrypoints, keybind & HUD registration
```

Based on [MultiLoader-Template](https://github.com/jaredlll08/MultiLoader-Template).

## License

MIT
