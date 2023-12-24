package dev.skydynamic.fakedebug;

import dev.skydynamic.fakedebug.config.Config;
import net.fabricmc.api.ClientModInitializer;

public class FakeDebug implements ClientModInitializer {
    public void onInitializeClient() {
        Config.INSTANCE.load();

        FpsRandomTimer.runTimer();
    }
}

