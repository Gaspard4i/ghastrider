difficulty peaceful
gamerule advance_time false
gamerule spawn_mobs false
gamerule advance_weather false
time set day
weather clear

clear @s
kill @e[type=minecraft:happy_ghast]
summon minecraft:happy_ghast ~ ~2 ~2 {NoAI:1b,Invulnerable:1b}
summon minecraft:happy_ghast ~3 ~2 ~2 {NoAI:1b,Invulnerable:1b}
summon minecraft:happy_ghast ~-3 ~2 ~2 {NoAI:1b,Invulnerable:1b}

give @s minecraft:white_harness 1
give @s minecraft:ice 64
give @s minecraft:packed_ice 64
give @s minecraft:blue_ice 64
give @s minecraft:powder_snow_bucket 1
give @s minecraft:snowball 64
