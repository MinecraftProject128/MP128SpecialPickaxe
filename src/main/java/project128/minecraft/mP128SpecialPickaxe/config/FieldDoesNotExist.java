package project128.minecraft.mP128SpecialPickaxe.config;

import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

public class FieldDoesNotExist extends RuntimeException {
    public FieldDoesNotExist(String field) {
        super("Поля \"" + field + "\" не существует в файле config.yml!");
    }

    public FieldDoesNotExist(String field, @NotNull Plugin plugin) {
        super("Поля \"" + field + "\" не существует в файле config.yml!");
        plugin.getLogger().severe(this.getMessage());
    }
}
