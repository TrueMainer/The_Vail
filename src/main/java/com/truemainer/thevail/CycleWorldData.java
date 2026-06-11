package com.truemainer.thevail;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.storage.LevelResource;

import java.nio.file.Path;
import java.util.HashMap;

import java.io.Writer;
import java.nio.file.Files;
import java.util.Map;

import com.google.gson.reflect.TypeToken;
import java.io.Reader;
import java.lang.reflect.Type;

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

    public static void save(MinecraftServer server) {
    try {
        Path dataFile = getDataFile(server);
        Files.createDirectories(dataFile.getParent());

        HashMap<String, CycleSpawn> dataToSave = new HashMap<>();

        for (Map.Entry<Integer, CycleSpawn> entry : cycleSpawns.entrySet()) {
            dataToSave.put(String.valueOf(entry.getKey()), entry.getValue());
        }

        try (Writer writer = Files.newBufferedWriter(dataFile)) {
            GSON.toJson(dataToSave, writer);
        }

        System.out.println("=== THE VEIL: Saved cycle world data. ===");

    } catch (Exception error) {
        System.out.println("=== THE VEIL ERROR: Failed to save cycle world data. ===");
        error.printStackTrace();
    }
}

public static void load(MinecraftServer server) {
    try {
        Path dataFile = getDataFile(server);

        if (!Files.exists(dataFile)) {
            save(server);
            return;
        }

        Type type = new TypeToken<HashMap<String, CycleSpawn>>() {}.getType();

        try (Reader reader = Files.newBufferedReader(dataFile)) {

            HashMap<String, CycleSpawn> loadedData =
                    GSON.fromJson(reader, type);

            cycleSpawns.clear();

            if (loadedData != null) {
                for (Map.Entry<String, CycleSpawn> entry : loadedData.entrySet()) {

                    cycleSpawns.put(
                            Integer.parseInt(entry.getKey()),
                            entry.getValue()
                    );
                }
            }
        }

        System.out.println("=== THE VEIL: Loaded cycle world data. ===");

    } catch (Exception error) {
        System.out.println("=== THE VEIL ERROR: Failed to load cycle world data. ===");
        error.printStackTrace();
    }
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