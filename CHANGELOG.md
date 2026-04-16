# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/), and this project adheres to [Semantic Versioning](https://semver.org/).

## [26.1.2.1-alpha.1] - 2026-04-16

### Added
- **Dash mechanic**: hold G to charge, release to boost forward while riding a Happy Ghast
- **Dash charge bar** on the HUD (replaces XP bar, same system as the horse jump bar)
- **Overcharge decay**: holding past max charge gradually reduces power
- **Proportional cooldown**: shorter charges mean shorter cooldowns
- **Minimum charge threshold** (2/18 bars): quick taps are ignored, prevents accidental dashes
- **Auto-recharge**: holding G through cooldown automatically starts the next charge
- **Vertical boost** during dash to maintain altitude
- **Ice feeding**: give ice to Happy Ghasts for speed effects
  - Ice → Speed I (10s, stackable duration)
  - Packed Ice → Speed II (10s, stackable duration)
  - Blue Ice → Speed III (10s, stackable duration)
  - Powder Snow Bucket → Speed I (empties to bucket)
  - Different ice type resets duration to 10s with new level
- **Custom sounds**: wind whoosh on dash, ice crunch + happy ghast purr on feeding
- **Item particles** when feeding ice to ghasts
- **Debug function**: `/function ghastrider:debug` sets up a test environment with 3 ghasts and all items
- **Fabric and NeoForge** support for Minecraft 26.1.2
