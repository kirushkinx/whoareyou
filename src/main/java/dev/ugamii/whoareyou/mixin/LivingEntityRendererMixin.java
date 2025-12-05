package dev.ugamii.whoareyou.mixin;

import dev.ugamii.whoareyou.config.WhoAreYouConfig;
import dev.ugamii.whoareyou.util.EntityUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.LivingEntity;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntityRenderer.class)
public class LivingEntityRendererMixin {

	@Inject(at = @At("HEAD"), method = "hasLabel(Lnet/minecraft/entity/LivingEntity;D)Z", cancellable = true)
	private void viewOwnLabel(LivingEntity entity, double distance, CallbackInfoReturnable<Boolean> cir) {
		MinecraftClient client = MinecraftClient.getInstance();

		if (EntityUtils.isNPC(entity)) {
			cir.setReturnValue(false);
			return;
		}

		if (EntityUtils.hasCustomNameTag(entity)) {
			cir.setReturnValue(false);
			return;
		}

		if (WhoAreYouConfig.hideInvisiblePlayersName && entity.isInvisible()) {
			cir.setReturnValue(false);
			return;
		}

		if (WhoAreYouConfig.enabledOwnName && entity == client.cameraEntity) {
			cir.setReturnValue(MinecraftClient.isHudEnabled());
		}

		if (WhoAreYouConfig.enabledOtherPlayersName && entity != client.cameraEntity) {
			cir.setReturnValue(MinecraftClient.isHudEnabled());
		}
	}
}
