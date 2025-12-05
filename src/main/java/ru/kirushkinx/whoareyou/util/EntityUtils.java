package ru.kirushkinx.whoareyou.util;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.decoration.ArmorStandEntity;
import net.minecraft.entity.decoration.DisplayEntity;

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

    public static boolean hasCustomNameTag(LivingEntity entity) {
        if (entity.hasPassengers()) {
            for (Entity passenger : entity.getPassengerList()) {
                if (passenger instanceof ArmorStandEntity || passenger instanceof DisplayEntity) {
                    return true;
                }
            }
        }
        return false;
    }
}
