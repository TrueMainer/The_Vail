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

        CycleSpawnManager.teleportPlayerToCycle(player, currentCycle);

        player.sendSystemMessage(Component.literal("You have entered Cycle " + currentCycle));
    }
}