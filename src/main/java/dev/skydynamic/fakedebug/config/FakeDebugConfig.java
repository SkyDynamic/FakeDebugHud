package dev.skydynamic.fakedebug.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.lang.reflect.Field;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

public class FakeDebugConfig {
    private final Object lock = new Object();
    private final Gson gson = new GsonBuilder().serializeNulls().setPrettyPrinting().create();
    private final Path configPath = Path.of(System.getProperty("user.dir") + "/config/");
    private ConfigStorage configStorage;
    File path = configPath.toFile();
    File config = configPath.resolve("FakeDebugInfo.json").toFile();

    public void load() {
        synchronized (lock) {
            try {
                if (!path.exists() || !path.isDirectory()) {
                    path.mkdirs();
                }
                if (!config.exists()) {
                    saveModifiedConfig(ConfigStorage.DEFAULT);
                }
                var reader = new FileReader(config);
                var result = gson.fromJson(reader, ConfigStorage.class);
                this.configStorage = fixFields(result, ConfigStorage.DEFAULT);
                saveModifiedConfig(this.configStorage);
                reader.close();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void saveModifiedConfig(ConfigStorage c) {
        synchronized (lock) {
            try {
                if (config.exists()) config.delete();
                if (!config.exists()) config.createNewFile();
                var writer = new FileWriter(config);
                gson.toJson(fixFields(c, ConfigStorage.DEFAULT), writer);
                writer.close();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    private <T> T fixFields(T t, T defaultVal) {
        if (t == null) {
            throw new NullPointerException();
        }
        if (t == defaultVal) {
            return t;
        }
        try {
            var clazz = t.getClass();
            for (Field declaredField : clazz.getDeclaredFields()) {
                if (Arrays.stream(declaredField.getDeclaredAnnotations()).anyMatch(it -> it.annotationType() == Ignore.class))
                    continue;
                declaredField.setAccessible(true);
                var value = declaredField.get(t);
                var dv = declaredField.get(defaultVal);
                if (value == null) {
                    declaredField.set(t, dv);
                }
            }
            return t;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String getCpuInfo() {
        synchronized (lock) {
            return configStorage.cpuInfo;
        }
    }

    public String getGpuInfo() {
        synchronized (lock) {
            return configStorage.gpuInfo;
        }
    }

    public int getMaxFps() {
        synchronized (lock) {
            return configStorage.maxFps;
        }
    }

    public int getMinFps() {
        synchronized (lock) {
            return configStorage.minFps;
        }
    }

    public int getMaxMemories() {
        synchronized (lock) {
            return configStorage.maxMemories;
        }
    }

}
