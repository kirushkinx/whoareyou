package dev.ugamii.whoareyou.util;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;

public class EntityUtils {

    public static boolean isNPC(LivingEntity entity) {
        if (!(entity instanceof PlayerEntity player)) {
            return false;
        }

        MinecraftClient client = MinecraftClient.getInstance();
        if (client.getNetworkHandler() == null) {
            return false;
        }

        if (client.getNetworkHandler().getPlayerListEntry(player.getUuid()) == null) {
            return true;
        }

        String name = player.getName().getString();
        return name.matches(".*[$<>:\\[\\]{}].*");
    }
}
