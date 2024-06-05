package com.craftycorvid.vtdatafixer.mixin;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import com.craftycorvid.vtdatafixer.GetAllPlayersUserCache;
import com.mojang.authlib.GameProfile;
import net.minecraft.util.UserCache;
import net.minecraft.util.UserCache.Entry;

@Mixin(UserCache.class)
public abstract class GetAllPlayersMixin implements GetAllPlayersUserCache {
    @Final
    @Shadow
    private Map<UUID, Entry> byUuid;

    @Override
    public List<GameProfile> getAllPlayers() {
        List<GameProfile> gameProfiles = new ArrayList<GameProfile>(this.byUuid.values().stream()
                .map(entry -> entry.getProfile()).collect(Collectors.toList()));
        return gameProfiles;
    }
}
