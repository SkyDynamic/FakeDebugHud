package dev.skydynamic.fakedebug.config;

public class ConfigStorage {
    @Ignore
    public static final ConfigStorage DEFAULT = new ConfigStorage(
        "1280x Intel(R) Core(TM) i9-139000KF CPU @ 128.80Ghz",
        "NVIDIA Geforce RTX 9090 Ti/PCIe/SSE2",
        10000,
        8000,
        102400
    );

    String cpuInfo;
    String gpuInfo;
    int maxFps;
    int minFps;
    int maxMemories;

    public ConfigStorage(String cpuInfo, String gpuInfo, int maxFps, int minFps, int maxMemories) {
        this.cpuInfo = cpuInfo;
        this.gpuInfo = gpuInfo;
        this.maxFps = maxFps;
        this.minFps = minFps;
        this.maxMemories = maxMemories;
    }
}
