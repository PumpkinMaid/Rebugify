package com.rebugify;

import de.maxhenkel.configbuilder.ConfigBuilder;
import de.maxhenkel.configbuilder.entry.ConfigEntry;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;

import java.nio.file.Path;

public class Rebugify implements ModInitializer {
    public static Config CONFIG;

    public static class Config {
        public final ConfigEntry<Boolean> stringDuplicationEnabled;
        public final ConfigEntry<Boolean> tripwireHookDuplicationEnabled;
        public final ConfigEntry<Boolean> voidTradingEnabled;

        public Config(ConfigBuilder builder) {
            stringDuplicationEnabled = builder.booleanEntry("stringDuplicationEnabled", false);
            tripwireHookDuplicationEnabled = builder.booleanEntry("tripwireHookDuplicationEnabled", false);
            voidTradingEnabled = builder.booleanEntry("voidTradingEnabled", false);
        }
    }

    @Override
    public void onInitialize() {
        Path path = FabricLoader.getInstance()
                .getConfigDir()
                .resolve("rebugify.toml");

        CONFIG = ConfigBuilder.builder(Config::new)
                .path(path)
                .saveAfterBuild(true)
                .build();
    }
}