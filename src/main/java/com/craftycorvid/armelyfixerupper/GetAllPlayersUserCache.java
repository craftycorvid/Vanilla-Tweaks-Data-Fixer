package com.craftycorvid.armelyfixerupper;

import java.util.ArrayList;
import java.util.List;
import com.mojang.authlib.GameProfile;

public interface GetAllPlayersUserCache {
    default List<GameProfile> getAllPlayers() {
        return new ArrayList<GameProfile>();
    }
}
