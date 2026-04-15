# Happy Ghast Dash - Quick ride (attempt auto-mount)
# Usage: /function ghastdash:test_ride
# Spawns a harnessed Happy Ghast and tries to mount the player on it

difficulty peaceful
gamerule doDaylightCycle false
gamerule doMobSpawning false
time set day
weather clear

# Summon Happy Ghast with white harness equipped via equipment component
summon minecraft:happy_ghast ~ ~2 ~2 {equipment:{body:{id:"minecraft:white_harness",count:1}}}

# Try to ride it (ride command available since 1.20.2)
ride @s mount @e[type=minecraft:happy_ghast,sort=nearest,limit=1]

tellraw @s [{"text":"[Ghast Dash] ","color":"aqua","bold":true},{"text":"Hold ","color":"white"},{"text":"G","color":"gold","bold":true},{"text":" to charge, release to DASH!","color":"white"}]
