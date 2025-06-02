package project128.minecraft.mP128SpecialPickaxe.commands;

import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface IMP128Command {

    void run(@NotNull CommandSender commandSender, @NotNull String[] strings,
             int typesOfArgumentsIndex);

    boolean hasPermission(@NotNull CommandSender commandSender, @NotNull String[] strings,
                          @NotNull String permissionPrefix, int typesOfArgumentsIndex);

    List<String> tabComplete(@NotNull CommandSender commandSender, @NotNull String[] strings);

}
