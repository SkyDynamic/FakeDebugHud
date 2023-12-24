package dev.skydynamic.fakedebug.mixin;

import com.mojang.blaze3d.platform.GlDebugInfo;
import dev.skydynamic.fakedebug.config.Config;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({GlDebugInfo.class})
public abstract class GlDebugInfoMixin
{
    @Inject(method = "getCpuInfo", at = @At("HEAD"), cancellable = true)
    private static void getCpuInfoRedirect(CallbackInfoReturnable<String> cir) {
        cir.setReturnValue(Config.INSTANCE.getCpuInfo());
    }

    @Inject(method = "getRenderer", at = @At("HEAD"), cancellable = true)
    private static void getRendererRedirect(CallbackInfoReturnable<String> cir) {
        cir.setReturnValue(Config.INSTANCE.getGpuInfo());
    }
}