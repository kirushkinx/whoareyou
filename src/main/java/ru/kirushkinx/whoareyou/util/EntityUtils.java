package ru.kirushkinx.whoareyou.util;

import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.Display;

public class EntityUtils {

    public static boolean isNPC(LivingEntity entity) {
        if (!(entity instanceof Player player)) {
            return false;
        }

        Minecraft client = Minecraft.getInstance();
        if (client.getConnection() == null) {
            return false;
        }

        if (client.getConnection().getPlayerInfo(player.getUUID()) == null) {
            return true;
        }

        String name = player.getName().getString();
        return name.matches(".*[$<>:\\[\\]{}].*");
    }

    public static boolean hasCustomNameTag(LivingEntity entity) {
        if (entity.isVehicle()) {
            for (Entity passenger : entity.getPassengers()) {
                if (passenger instanceof ArmorStand || passenger instanceof Display) {
                    return true;
                }
            }
        }
        return false;
    }
}
