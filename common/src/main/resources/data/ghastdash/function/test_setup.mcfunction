# Happy Ghast Dash - Test Setup
# Usage: /function ghastdash:test_setup
# Creates a test environment with a Happy Ghast ready to ride

# Set peaceful + clear weather + day
difficulty peaceful
gamerule doDaylightCycle false
gamerule doMobSpawning false
time set day
weather clear

# Give player a white harness
give @s minecraft:white_harness 1

# Summon an adult Happy Ghast right next to player
summon minecraft:happy_ghast ~ ~1 ~2

# Notify
tellraw @s [{"text":"[Ghast Dash] ","color":"aqua","bold":true},{"text":"Setup OK! Use the harness on the Ghast, then ride it. Hold ","color":"white"},{"text":"G","color":"gold","bold":true},{"text":" to charge, release to DASH!","color":"white"}]
