package com.truemainer.thevail;

import java.util.HashMap;
import java.util.UUID;

public class PlayerCycleData {

    private static final HashMap<UUID, Integer> playerCycles = new HashMap<>();

    public static int getCycle(UUID playerUUID) {
        return playerCycles.getOrDefault(playerUUID, 1);
    }

    public static void increaseCycle(UUID playerUUID) {
        int currentCycle = getCycle(playerUUID);
        playerCycles.put(playerUUID, currentCycle + 1);
    }
}