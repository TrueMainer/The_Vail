package com.truemainer.thevail;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.levelgen.Heightmap;

public class CycleSpawnManager {

    private static final int CYCLE_DISTANCE = 10000;

    public static void teleportPlayerToCycle(ServerPlayer player, int cycle) {
        ServerLevel overworld = player.server.overworld();

        int cycleSpawnX = cycle * CYCLE_DISTANCE;
        int cycleSpawnZ = 0;

        if (!CycleWorldData.cycleExists(cycle)) {
    CycleWorldData.createCycle(
            cycle,
            cycleSpawnX,
            0,
            cycleSpawnZ
    );
} 

        ChunkPos chunkPos = new ChunkPos(cycleSpawnX >> 4, cycleSpawnZ >> 4);

        overworld.getChunk(chunkPos.x, chunkPos.z);

        int safeY = overworld.getHeight(
                Heightmap.Types.WORLD_SURFACE,
                cycleSpawnX,
                cycleSpawnZ
        );

        player.teleportTo(
                overworld,
                cycleSpawnX + 0.5,
                safeY + 2,
                cycleSpawnZ + 0.5,
                player.getYRot(),
                player.getXRot()
        );
    }
} 