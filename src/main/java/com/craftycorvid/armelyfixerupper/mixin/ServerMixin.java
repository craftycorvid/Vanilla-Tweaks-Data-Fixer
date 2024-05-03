package com.craftycorvid.armelyfixerupper.mixin;

import com.craftycorvid.armelyfixerupper.ArmoredElytraFixerUpper;
import java.util.List;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.minecraft.network.packet.c2s.common.SyncedClientOptions;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.WorldSaveHandler;
import com.mojang.authlib.GameProfile;

@Mixin(MinecraftServer.class)
public abstract class ServerMixin {
    @Final
    @Shadow
    protected WorldSaveHandler saveHandler;

    @Inject(method = "loadWorld", at = @At("TAIL"))
    private void loadWorld(CallbackInfo ci) {
        List<GameProfile> gameProfiles =
                ((MinecraftServer) (Object) this).getUserCache().getAllPlayers();
        SyncedClientOptions defaultOptions = SyncedClientOptions.createDefault();
        gameProfiles.forEach(profile -> {
            ServerPlayerEntity playerEntity = ((MinecraftServer) (Object) this).getPlayerManager()
                    .createPlayer(profile, defaultOptions);
            this.saveHandler.loadPlayerData(playerEntity);
            this.saveHandler.savePlayerData(playerEntity);
        });
        ArmoredElytraFixerUpper.LOGGER.info("Finished updating player data");
    }
}
