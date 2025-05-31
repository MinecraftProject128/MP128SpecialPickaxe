package project128.minecraft.mP128SpecialPickaxe;

import org.bukkit.plugin.java.JavaPlugin;
import project128.minecraft.mP128SpecialPickaxe.config.Config;

public final class MP128SpecialPickaxe extends JavaPlugin {

    @Override
    public void onEnable() {
        saveDefaultConfig();

        getLogger().info("Версия файла конфигурации: " + Config.getInstance(this).getField(Config.Field.VERSION));

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
