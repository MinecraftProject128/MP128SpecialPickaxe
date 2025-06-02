package project128.minecraft.mP128SpecialPickaxe.commands;

import org.bukkit.command.*;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import project128.minecraft.mP128SpecialPickaxe.config.Config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class MasterCommandExecutor implements CommandExecutor, TabCompleter {
    private final Plugin plugin;
    private final Map<String, MP128Command> executors;

    public MasterCommandExecutor(Plugin plugin, Map<String, MP128Command> executors) {
        this.plugin = plugin;
        this.executors = executors;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length == 0) {
            sender.sendMessage(Config.getInstance(plugin).getMessage(Config.Field.UNKNOWN_COMMAND));
            return true;
        }
        String cmd = args[0];
        args = Arrays.copyOfRange(args, 1, args.length);
        MP128Command executor = executors.get(cmd);
        int typesOfArgumentsIndex = executor.getTypesOfArgumentsIndex(args);
        if (typesOfArgumentsIndex == -1) {
            sender.sendMessage(Config.getInstance(plugin).getMessage(Config.Field.UNKNOWN_COMMAND));
            return true;
        }
        if (executor.hasPermission(sender, args, "specialpickaxe.", typesOfArgumentsIndex))
            executor.run(sender, args, typesOfArgumentsIndex);
        else
            sender.sendMessage(Config.getInstance(plugin).getMessage(Config.Field.NOT_ALLOWED_COMMAND));

        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length == 1)
            return new ArrayList<>(executors.keySet());
        if (executors.get(args[0]) == null)
            return List.of();

        return executors.get(args[0]).tabComplete(sender, args);
    }
}
