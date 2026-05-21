package com.truemainer.thevail;

import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.minecraft.world.level.ChunkPos;

@EventBusSubscriber(modid = TheVail.MODID)
public class DeathEvents {

    @SubscribeEvent
    public static void onLivingDeath(LivingDeathEvent event) {
        if (!(event.getEntity() instanceof ServerPlayer player)) {
            return;
        }

        String playerName = player.getName().getString();

        System.out.println("=== THE VEIL DEATH EVENT: " + playerName + " has crossed the veil. ===");

        player.sendSystemMessage(Component.literal("You have crossed the veil."));
    }

    @SubscribeEvent
    public static void onPlayerRespawn(PlayerEvent.PlayerRespawnEvent event) {

        if (!(event.getEntity() instanceof ServerPlayer player)) {
        return;
        }

        PlayerCycleData.increaseCycle(player.server, player.getUUID());

        int currentCycle = PlayerCycleData.getCycle(player.getUUID());

        int cycleSpawnX = currentCycle * 10000;
        int cycleSpawnZ = 0;

        ChunkPos chunkPos = new ChunkPos(cycleSpawnX >> 4, cycleSpawnZ >> 4);

    // Force the target chunk to generate/load before checking height.
    player.server.overworld().getChunk(chunkPos.x, chunkPos.z);

        int safeY = player.server.overworld()
        .getHeight(
        net.minecraft.world.level.levelgen.Heightmap.Types.WORLD_SURFACE,
        cycleSpawnX,
        cycleSpawnZ
        );

player.teleportTo(
        player.server.overworld(),
        cycleSpawnX + 0.5,
        safeY + 2,
        cycleSpawnZ + 0.5,
        player.getYRot(),
        player.getXRot()
);

        player.sendSystemMessage(Component.literal("You have entered Cycle " + currentCycle));
    }
}