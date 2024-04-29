# Armored Elytra Fixer Upper

A datafixer mod to convert data stored by the [Vanilla Tweaks Armored Elytra Data Pack](https://vanillatweaks.net/picker/datapacks/) from the old NBT format(1.20.4 and below) to the new components format(1.20.5+)

**NOTE** There's no official version of Armored Elytra for 1.20.5 yet. This currently converts to an unofficial port's format which will not be compatible with the official version. This mod will be updated to match the official version once it is released.

## How to use this mod
1. **MAKE A BACKUP OF YOUR WORLD!**
2. Make SURE you can load that backup of your world if things go sideways
3. I'm serious, it's not my fault if you lose your whole world!
4. Install 1.20.5, fabric, the Armored Elytra data pack and this mod
5. Start the server, or load the world you are trying to update
6. Once everything has fully loaded shut down the server or close your game
7. Delete this mod
8. Load your world again and test if your elytra will properly split on a grindstone
9. Enjoy!

## Things to note
- For singleplayer it should be as easy as installing the mod and loading the world.
- For multiplayer I had to come up with a workaround to the fact that Minecraft only updates player data when people connect to your server. This mod uses the `usercache.json` file to load, update and save all your player's data. 
- If you copy your world to a client, install the mod and run it things will seem to work for your player, but other player's data won't be updated. You also need to copy `usercache.json` into your `.minecraft` folder for the mod to find all your players and update them.