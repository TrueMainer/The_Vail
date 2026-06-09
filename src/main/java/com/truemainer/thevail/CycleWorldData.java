package com.truemainer.thevail;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.storage.LevelResource;

import java.nio.file.Path;
import java.util.HashMap;

public class CycleWorldData {

    public static boolean cycleExists(int cycle) {
    return cycleSpawns.containsKey(cycle);
    }

    public static void createCycle(int cycle, int spawnX, int spawnY, int spawnZ) {

            cycleSpawns.put(
                cycle,
                new CycleSpawn(
                        spawnX,
                        spawnY,
                        spawnZ
                )
        );
    }

    public static CycleSpawn getCycleSpawn(int cycle) {
        return cycleSpawns.get(cycle);
    }

    private static final Gson GSON = new GsonBuilder()
            .setPrettyPrinting()
            .create();

    private static final HashMap<Integer, CycleSpawn> cycleSpawns = new HashMap<>();

    private static Path getDataFile(MinecraftServer server) {
        return server.getWorldPath(LevelResource.ROOT)
                .resolve("thevail")
                .resolve("cycle_worlds.json");
    }

    public static class CycleSpawn {

        public int spawnX;
        public int spawnY;
        public int spawnZ;

        public CycleSpawn(int spawnX, int spawnY, int spawnZ) {
            this.spawnX = spawnX;
            this.spawnY = spawnY;
            this.spawnZ = spawnZ;
        }
    }
}