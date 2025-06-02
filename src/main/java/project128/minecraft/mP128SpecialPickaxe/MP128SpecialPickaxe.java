package project128.minecraft.mP128SpecialPickaxe;

import org.bukkit.plugin.java.JavaPlugin;
import project128.minecraft.mP128SpecialPickaxe.commands.CommandGive;
import project128.minecraft.mP128SpecialPickaxe.commands.MP128Command;
import project128.minecraft.mP128SpecialPickaxe.commands.MasterCommandExecutor;
import project128.minecraft.mP128SpecialPickaxe.config.Config;
import project128.minecraft.mP128SpecialPickaxe.listeners.PickaxeListener;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MP128SpecialPickaxe extends JavaPlugin {

    @Override
    public void onEnable() {
        saveDefaultConfig();

        getLogger().info("Версия файла конфигурации: " +
                Config.getInstance(this).getField(Config.Field.VERSION));

        Map<String, MP128Command> executors = getCommandExecutors();
        getCommand("specialpickaxe").setExecutor(new MasterCommandExecutor(this, executors));
        getCommand("specialpickaxe").setTabCompleter(new MasterCommandExecutor(this, executors));
        getServer().getPluginManager().registerEvents(new PickaxeListener(this), this);
    }

    private Map<String, MP128Command> getCommandExecutors() {
        Map<String, MP128Command> executors = new HashMap<>();

        executors.put("give", new CommandGive(this, List.of(
                List.of(MP128Command.ValueType.STRING),
                List.of(MP128Command.ValueType.STRING, MP128Command.ValueType.PLAYER)
        )));

        return executors;
    }

}
