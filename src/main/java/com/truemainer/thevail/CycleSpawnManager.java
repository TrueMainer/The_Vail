package com.truemainer.thevail;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.levelgen.Heightmap;

public class CycleSpawnManager {

    private static final int CYCLE_DISTANCE = 10000;

   public static void teleportPlayerToCycle(ServerPlayer player, int cycle) {

    ServerLevel overworld = player.server.overworld();

    CycleWorldData.CycleSpawn cycleSpawn;

    // If this cycle has never been created...
    if (!CycleWorldData.cycleExists(cycle)) {

        int cycleSpawnX = cycle * CYCLE_DISTANCE;
        int cycleSpawnZ = 0;

        ChunkPos chunkPos = new ChunkPos(cycleSpawnX >> 4, cycleSpawnZ >> 4);

        // Generate/load the chunk first.
        overworld.getChunk(chunkPos.x, chunkPos.z);

        // Find the surface.
        int safeY = overworld.getHeight(
                Heightmap.Types.WORLD_SURFACE,
                cycleSpawnX,
                cycleSpawnZ
        );

        // Save the REAL spawn.
        CycleWorldData.createCycle(
                cycle,
                cycleSpawnX,
                safeY + 2,
                cycleSpawnZ
        );

        // Immediately save it to disk.
        CycleWorldData.save(player.server);
    }

    // Read the stored spawn.
    cycleSpawn = CycleWorldData.getCycleSpawn(cycle);

    // Teleport using the stored values.
    player.teleportTo(
            overworld,
            cycleSpawn.spawnX + 0.5,
            cycleSpawn.spawnY,
            cycleSpawn.spawnZ + 0.5,
            player.getYRot(),
            player.getXRot()
    );
}
}