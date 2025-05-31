package project128.minecraft.mP128SpecialPickaxe.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import project128.minecraft.mP128SpecialPickaxe.config.Config;

import java.util.List;

public class MasterCommandExecutor implements CommandExecutor, TabExecutor {
    private final Plugin plugin;

    public MasterCommandExecutor(Plugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        return false;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (strings.length == 1)
            return List.of("give");
        if (strings.length == 2 && strings[0].equals("give"))
            return Config.getInstance(plugin).getSection(new String[]{"pickaxes"}).getKeys(false).stream().toList();
        return List.of();
    }
}
