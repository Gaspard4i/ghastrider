<p align="center">
  <img src="https://img.shields.io/github/v/release/Gaspard4i/ghastrider?style=for-the-badge&logo=github&color=181717" alt="GitHub Release">
  <img src="https://img.shields.io/github/license/Gaspard4i/ghastrider?style=for-the-badge" alt="License">
  <img src="https://img.shields.io/badge/Minecraft-26.1.2-62B47A?style=for-the-badge&logo=mojangstudios" alt="Minecraft Version">
  <img src="https://img.shields.io/badge/Fabric-Supported-DBB06E?style=for-the-badge" alt="Fabric">
  <img src="https://img.shields.io/badge/NeoForge-Supported-E04E14?style=for-the-badge" alt="NeoForge">
</p>

<h1 align="center">GhastRider</h1>

<p align="center">
  <strong>A charge-and-dash mechanic for the Happy Ghast mount in Minecraft.</strong>
  <br>
  Hold to charge, release to boost — just like the horse jump bar.
</p>

---

## Features

- **Dash charge bar** replaces the XP bar when riding a Happy Ghast (same system as the horse jump bar)
- **Hold G** (configurable) to charge your dash, release to boost forward
- **Overcharge decay** — hold too long past the max and the charge starts dropping
- **Proportional cooldown** — the cooldown duration and bar size match how much you charged
- **Vertical lift** during dash to maintain altitude
- Available for both **Fabric** and **NeoForge**

## How It Works

1. Mount a Happy Ghast (equip it with a harness first)
2. Hold **G** to start charging — the bar fills up
3. Release **G** at the right moment for maximum power
4. After dashing, the bar shows the cooldown shrinking from your charge level
5. Once the bar empties, you can dash again

> **Tip:** Releasing at 80%+ charge gives the best boost. But don't hold too long — the charge decays after hitting the max!

## Downloads

| Platform | Link |
|----------|------|
| GitHub Releases | [Latest Release](https://github.com/Gaspard4i/ghastrider/releases/latest) |
| Modrinth | *Coming soon* |
| CurseForge | *Coming soon* |

## Installation

1. Install [Fabric Loader](https://fabricmc.net/) **or** [NeoForge](https://neoforged.net/) for Minecraft 26.1.2
2. For Fabric: also install [Fabric API](https://modrinth.com/mod/fabric-api)
3. Download the correct JAR for your loader from [Releases](https://github.com/Gaspard4i/ghastrider/releases)
4. Place the JAR in your `mods/` folder
5. Launch the game

## Configuration

| Setting | Default | Description |
|---------|---------|-------------|
| Dash key | `G` | Can be changed in Controls settings |
| Charge time | 2s | Time to reach full charge |
| Cooldown | 0–3s | Proportional to charge level |

## Building from Source

Requires **Java 25**.

```bash
git clone https://github.com/Gaspard4i/ghastrider.git
cd ghastrider
./gradlew build
```

Output JARs:
- `fabric/build/libs/ghastrider-fabric-*.jar`
- `neoforge/build/libs/ghastrider-neoforge-*.jar`

## Development

```bash
# Run Fabric client
./gradlew :fabric:runClient

# Run NeoForge client
./gradlew :neoforge:runClient
```

### Testing in-game

Create a **Superflat Creative** world with cheats enabled, then:

```
/function ghastrider:debug
```

This sets up a peaceful environment, spawns an invulnerable Happy Ghast, and gives you all test items (harness, ice, snowballs).

## Project Structure

```
common/     Shared code — dash logic, state machine, bar renderer, mixins
fabric/     Fabric loader — keybind registration, client entrypoint, Fabric mixin
neoforge/   NeoForge loader — keybind registration, client entrypoint, NeoForge mixin
```

Built with [MultiLoader-Template](https://github.com/jaredlll08/MultiLoader-Template).

## Contributing

Issues and pull requests are welcome! Please use the [issue templates](https://github.com/Gaspard4i/ghastrider/issues/new/choose) when reporting bugs or suggesting features.

## License

[MIT](LICENSE) — Copyright (c) 2026 Gaspard4i
