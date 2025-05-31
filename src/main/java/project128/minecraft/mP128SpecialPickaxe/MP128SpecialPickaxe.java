package project128.minecraft.mP128SpecialPickaxe;

import org.bukkit.plugin.java.JavaPlugin;
import project128.minecraft.mP128SpecialPickaxe.commands.MasterCommandExecutor;
import project128.minecraft.mP128SpecialPickaxe.config.Config;

public final class MP128SpecialPickaxe extends JavaPlugin {

    @Override
    public void onEnable() {
        saveDefaultConfig();

        getLogger().info("Версия файла конфигурации: " +
                Config.getInstance(this).getField(Config.Field.VERSION));

        getCommand("specialpickaxe").setExecutor(new MasterCommandExecutor(this));
        getCommand("specialpickaxe").setTabCompleter(new MasterCommandExecutor(this));
    }

}
