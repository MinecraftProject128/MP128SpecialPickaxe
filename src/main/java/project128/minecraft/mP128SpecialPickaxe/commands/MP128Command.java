package project128.minecraft.mP128SpecialPickaxe.commands;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.List;

abstract public class MP128Command implements IMP128Command {
    protected final Plugin plugin;
    protected final List<List<ValueType>> typesOfArguments;

    public MP128Command(Plugin plugin, List<List<ValueType>> typesOfArguments) {
        this.plugin = plugin;
        this.typesOfArguments = typesOfArguments;
    }

    @Nullable
    protected List<ValueType> getTypesOfArguments(@NotNull String[] args) {
        plugin.getLogger().info("typesOfArguments: " + this.typesOfArguments.toString());
        for (List<ValueType> types : this.typesOfArguments) {
            plugin.getLogger().info("types.size() = " + types.size() + ", args.length: " + args.length);
            plugin.getLogger().info("types.size() != args.length: " + (types.size() != args.length));
            if (types.size() != args.length)
                continue;
            for (int i = 0; i < args.length; i++) {
                plugin.getLogger().info(i + ") " + types.get(i) + ", " + args[i]);
                plugin.getLogger().info("ValueType.canCast(types.get(i), args[i]): " + ValueType.canCast(types.get(i), args[i]));
                if (!ValueType.canCast(types.get(i), args[i]))
                    break;
                if (i == args.length - 1)
                    return types;
            }
        }
        return null;
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
            if (type == PLAYER)
                return Bukkit.getPlayer(value) != null;
            return true;
        }
    }

}
