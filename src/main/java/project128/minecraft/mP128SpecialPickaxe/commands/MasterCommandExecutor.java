package project128.minecraft.mP128SpecialPickaxe.commands;

import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import project128.minecraft.mP128SpecialPickaxe.config.Config;
import project128.minecraft.mP128SpecialPickaxe.config.SpecialPickaxe;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MasterCommandExecutor implements CommandExecutor, TabExecutor {
    private final Plugin plugin;

    public MasterCommandExecutor(Plugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (strings.length == 2) {
            if (strings[0].equals("give") && commandSender instanceof Player player) {
                if (player.hasPermission("specialpickaxe.give")) {
                    SpecialPickaxe pickaxe = Config.getInstance(plugin).getPickaxe(strings[1]);
                    if (pickaxe == null) {
                        player.sendMessage(Config.getInstance(plugin).getMessage(Config.Field.UNKNOWN_PICKAXE));
                        return false;
                    }
                    GiveSpecialPickaxe.getInstance(plugin).givePickaxe(player, pickaxe);
                    player.sendMessage(Config.getInstance(plugin).getMessage(Config.Field.GIVE_SELF,
                            Map.of("{pickaxe-name}", strings[1])));
                    return true;
                } else {
                    player.sendMessage(Config.getInstance(plugin).getMessage(Config.Field.NOT_ALLOWED_COMMAND));
                    return false;
                }
            } else if (strings[0].equals("give") && commandSender instanceof ConsoleCommandSender) {
                commandSender.sendMessage(Config.getInstance(plugin).getMessage(Config.Field.USE_GIVE_COMMAND));
                return false;
            }
        } else if (strings.length == 1) {
            if (strings[0].equals("give") && commandSender.hasPermission("specialpickaxe.give")) {
                commandSender.sendMessage(Config.getInstance(plugin).getMessage(Config.Field.USE_GIVE_COMMAND));
                return true;
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
