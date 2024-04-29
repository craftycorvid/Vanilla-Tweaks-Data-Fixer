package com.craftycorvid.armelyfixerupper.mixin;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.spongepowered.asm.mixin.Mixin;
import com.craftycorvid.armelyfixerupper.GetAllPlayersUserCache;
import com.mojang.authlib.GameProfile;
import net.minecraft.util.UserCache;

@Mixin(UserCache.class)
public abstract class GetAllPlayersMixin implements GetAllPlayersUserCache {
    @Override
    public List<GameProfile> getAllPlayers() {
        List<GameProfile> gameProfiles =
                new ArrayList<GameProfile>(((UserCache) (Object) this).byUuid.values().stream()
                        .map(entry -> entry.getProfile()).collect(Collectors.toList()));
        return gameProfiles;
    }
}
