package com.truemainer.thevail;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.storage.LevelResource;

import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerCycleData {

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final HashMap<UUID, Integer> playerCycles = new HashMap<>();

    private static Path getDataFile(MinecraftServer server) {
        return server.getWorldPath(LevelResource.ROOT)
                .resolve("thevail")
                .resolve("player_cycles.json");
    }

    public static void load(MinecraftServer server) {
        try {
            Path dataFile = getDataFile(server);

            if (!Files.exists(dataFile)) {
                save(server);
                return;
            }

            Type type = new TypeToken<HashMap<String, Integer>>() {}.getType();

            try (Reader reader = Files.newBufferedReader(dataFile)) {
                Map<String, Integer> loadedData = GSON.fromJson(reader, type);

                playerCycles.clear();

                if (loadedData != null) {
                    for (Map.Entry<String, Integer> entry : loadedData.entrySet()) {
                        playerCycles.put(UUID.fromString(entry.getKey()), entry.getValue());
                    }
                }
            }

            System.out.println("=== THE VEIL: Loaded player cycle data. ===");

        } catch (Exception error) {
            System.out.println("=== THE VEIL ERROR: Failed to load player cycle data. ===");
            error.printStackTrace();
        }
    }

    public static void save(MinecraftServer server) {
        try {
            Path dataFile = getDataFile(server);
            Files.createDirectories(dataFile.getParent());

            HashMap<String, Integer> dataToSave = new HashMap<>();

            for (Map.Entry<UUID, Integer> entry : playerCycles.entrySet()) {
                dataToSave.put(entry.getKey().toString(), entry.getValue());
            }

            try (Writer writer = Files.newBufferedWriter(dataFile)) {
                GSON.toJson(dataToSave, writer);
            }

        } catch (Exception error) {
            System.out.println("=== THE VEIL ERROR: Failed to save player cycle data. ===");
            error.printStackTrace();
        }
    }

    public static int getCycle(UUID playerUUID) {
        return playerCycles.getOrDefault(playerUUID, 1);
    }

    public static void increaseCycle(MinecraftServer server, UUID playerUUID) {
        int currentCycle = getCycle(playerUUID);
        playerCycles.put(playerUUID, currentCycle + 1);
        save(server);
    }
}