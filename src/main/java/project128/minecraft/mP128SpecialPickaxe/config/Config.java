package project128.minecraft.mP128SpecialPickaxe.config;

import org.bukkit.plugin.Plugin;

public class Config {
    private static Config instance;

    private final Plugin plugin;

    private Config(Plugin plugin) {
        this.plugin = plugin;
    }

    public static synchronized Config getInstance(Plugin plugin) {
        if (instance == null)
            instance = new Config(plugin);
        return instance;
    }

}
