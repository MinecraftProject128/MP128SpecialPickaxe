package project128.minecraft.mP128SpecialPickaxe.commands;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.List;

abstract public class MP128Command implements IMP128Command {
    protected final Plugin plugin;
    protected final List<List<ValueType>> typesOfArguments;

    public MP128Command(Plugin plugin, List<List<ValueType>> typesOfArguments) {
        this.plugin = plugin;
        this.typesOfArguments = typesOfArguments;
    }

    protected int getTypesOfArgumentsIndex(@NotNull String[] args) {
        for (int i = 0; i < this.typesOfArguments.size(); i++) {
            if (typesOfArguments.get(i).size() != args.length)
                continue;
            for (int k = 0; k < args.length; k++) {
                if (!ValueType.canCast(typesOfArguments.get(i).get(k), args[k]))
                    break;
                if (k == args.length - 1)
                    return i;
            }
        }
        return -1;
    }

    public enum ValueType {
        STRING,
        INTEGER,
        PLAYER;

        public static boolean canCast(ValueType type, String value) {
            try {
                if (type == INTEGER)
                    Integer.valueOf(value);
            } catch (NumberFormatException e) {
                return false;
            }
            if (type == PLAYER) {
                return Bukkit.getPlayer(value) != null;
            }
            return true;
        }
    }

}
