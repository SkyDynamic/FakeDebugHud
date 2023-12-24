package dev.skydynamic.fakedebug.mixin;

import dev.skydynamic.fakedebug.FpsRandom;
import java.util.List;
import java.util.Locale;

import dev.skydynamic.fakedebug.config.Config;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.DebugHud;
import net.minecraft.client.option.CloudRenderMode;
import net.minecraft.util.Formatting;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
@Environment(EnvType.CLIENT)
@Mixin({DebugHud.class})
public class GlDebugMixin
{
    @Shadow
    @Final
    private MinecraftClient client;
  
    @Unique
    private String getFpsDebug() {
        int k = this.client.getFramerateLimit();
        Object string = (this.client.getGpuUtilizationPercentage() > 0.0D) ? (" GPU: " + ((this.client.getGpuUtilizationPercentage() > 100.0D) ? ("" + Formatting.RED + "100%") : ("" + Math.round(this.client.getGpuUtilizationPercentage()) + "%"))) : "";
        return String.format(Locale.ROOT, "%d fps T: %s%s%s%s B: %d%s", new Object[] { Integer.valueOf(FpsRandom.currentFps), (k == 260) ? "inf" : Integer.valueOf(k), ((Boolean)this.client.options.getEnableVsync().getValue()).booleanValue() ? " vsync" : "", this.client.options.getGraphicsMode().getValue(), (this.client.options.getCloudRenderMode().getValue() == CloudRenderMode.OFF) ? "" : ((this.client.options.getCloudRenderMode().getValue() == CloudRenderMode.FAST) ? " fast-clouds" : " fancy-clouds"), this.client.options.getBiomeBlendRadius().getValue(), string });
    }

    @Inject(method = "getLeftText", at = @At("RETURN"), cancellable = true)
    private void modifyFps(CallbackInfoReturnable<List<String>> cir) {
        List<String> value = cir.getReturnValue();
        value.set(1, getFpsDebug());
        cir.setReturnValue(value);
    }

    @ModifyVariable(method = "getRightText", at = @At("STORE"), ordinal = 0)
    private long modifyMaxMemories(long l) {
        return (long) Config.INSTANCE.getMaxMemories() * 1024 * 1024;
    }

}
