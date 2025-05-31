package project128.minecraft.mP128SpecialPickaxe.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import project128.minecraft.mP128SpecialPickaxe.config.Config;

import java.util.ArrayList;
import java.util.List;

public class MasterCommandExecutor implements CommandExecutor, TabExecutor {
    private final Plugin plugin;

    public MasterCommandExecutor(Plugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (strings.length == 2) {
            if (strings[0].equals("give") && commandSender instanceof Player player) {
                GiveSpecialPickaxe.getInstance(plugin).givePickaxe(player,
                        Config.getInstance(plugin).getPickaxe(strings[1]));
            }
        }
        commandSender.sendMessage(Config.getInstance(plugin).getMessage(Config.Field.UNKNOWN_COMMAND));
        return false;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (strings.length == 1)
            return getFirstLevelCommandsTips(commandSender);
        if (strings.length == 2 && strings[0].equals("give"))
            return getSecondLevelCommandsTips(commandSender, strings);
        return List.of();
    }

    private List<String> getFirstLevelCommandsTips(CommandSender commandSender) {
        List<String> tips = new ArrayList<>();
        if (commandSender.hasPermission("specialpickaxe.give"))
            tips.add("give");
        return tips;
    }

    private List<String> getSecondLevelCommandsTips(CommandSender commandSender, String[] strings) {
        List<String> tips = new ArrayList<>();
        if (strings[0].equals("give") && commandSender.hasPermission("specialpickaxe.give"))
            tips.addAll(Config.getInstance(plugin).getSection(new String[]{"pickaxes"}).getKeys(false));
        return tips;
    }
}
