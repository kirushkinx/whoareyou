package ru.kirushkinx.whoareyou.mixin;

import ru.kirushkinx.whoareyou.config.WhoAreYouConfig;
import ru.kirushkinx.whoareyou.util.EntityUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntityRenderer.class)
public class LivingEntityRendererMixin {

	@Inject(at = @At("HEAD"), method = "shouldShowName(Lnet/minecraft/world/entity/LivingEntity;D)Z", cancellable = true)
	private void viewOwnLabel(LivingEntity entity, double distance, CallbackInfoReturnable<Boolean> cir) {
		Minecraft client = Minecraft.getInstance();

		if (EntityUtils.isNPC(entity)) {
			cir.setReturnValue(false);
			return;
		}

		if (EntityUtils.hasCustomNameTag(entity)) {
			cir.setReturnValue(false);
			return;
		}

		if (entity.isInvisible()) {
			cir.setReturnValue(false);
			return;
		}

		if (WhoAreYouConfig.enabledOwnName && entity == client.getCameraEntity()) {
			cir.setReturnValue(!client.options.hideGui);
		}

		if (WhoAreYouConfig.enabledOtherPlayersName && entity != client.getCameraEntity()) {
			cir.setReturnValue(!client.options.hideGui);
		}
	}
}
