# Armored Elytra Fixer Upper

A datafixer mod to convert data stored by the [Vanilla Tweaks Armored Elytra Data Pack](https://vanillatweaks.net/picker/datapacks/) from the old NBT format(1.20.4 and below) to the new components format(1.20.5+)

## How to use this mod
1. **MAKE A BACKUP OF YOUR WORLD!**
2. Make SURE you can load that backup of your world if things go sideways
3. I'm serious, it's not my fault if you lose your whole world!
4. Install 1.20.6, fabric, the updated Armored Elytra data pack and this mod
5. Start the server and connect to it, or load the world you are trying to update
7. Test if your armored elytra will properly split on a grindstone
7. Shut down the server or close your game
8. Delete this mod, its job is done
9. Enjoy!

## Things to note
- For singleplayer it should be as easy as installing the mod and loading the world.
- For multiplayer I had to come up with a workaround to the fact that Minecraft only updates player data when people connect to your server. This mod uses the `usercache.json` file to load, update and save all your player's data. 
- If you copy your world to a client, install the mod and run it things will seem to work for your player, but other player's data won't be updated. You also need to copy `usercache.json` into your `.minecraft` folder for the mod to find all your players and update them.