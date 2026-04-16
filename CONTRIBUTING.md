# Contributing to GhastRider

Thanks for your interest in contributing! Here's how to get started.

## Prerequisites

- **Java 25** (Temurin recommended)
- **Gradle 9.2** (included via wrapper)
- IntelliJ IDEA is recommended but any IDE works

## Setup

```bash
git clone https://github.com/Gaspard4i/ghastrider.git
cd ghastrider
./gradlew build
```

## Running the mod

```bash
# Fabric client
./gradlew :fabric:runClient

# NeoForge client
./gradlew :neoforge:runClient
```

Once in-game, create a Superflat Creative world with cheats, then run:
```
/function ghastrider:debug
```

## Project structure

```
common/     Shared code (dash logic, state machine, renderer, mixins)
fabric/     Fabric-specific (entrypoints, keybinds, mixin)
neoforge/   NeoForge-specific (entrypoints, keybinds, mixin)
buildSrc/   Gradle build plugins
```

All shared code goes in `common/`. Only put loader-specific code (registration, entrypoints, platform-dependent mixins) in `fabric/` or `neoforge/`.

## Code style

- **Tabs** for Java indentation
- **Spaces** (2) for JSON, YAML, properties, TOML
- **LF** line endings (enforced by `.editorconfig` and `.gitattributes`)
- Run `./gradlew build` before submitting — it must compile for both loaders

## Submitting changes

1. Fork the repo and create a feature branch (`feat/my-feature` or `fix/my-fix`)
2. Make your changes and test on **both** Fabric and NeoForge
3. Update `CHANGELOG.md` if the change is user-facing
4. Open a Pull Request against `main`

## Reporting bugs

Use the [Bug Report template](https://github.com/Gaspard4i/ghastrider/issues/new?template=bug_report.yml). Include your loader, Minecraft version, mod version, and steps to reproduce.

## Suggesting features

Use the [Feature Request template](https://github.com/Gaspard4i/ghastrider/issues/new?template=feature_request.yml).
