package project128.minecraft.mP128SpecialPickaxe.config;

import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

public class InvalidFieldType extends RuntimeException {
    public InvalidFieldType(String field, Class<?> expected, Class<?> received) {
        super("Поле \"" + field + "\" имеет не верный тип в файле config.yml! (Ожидается " +
                expected.getName() + ", но получен " + received.getName() + ").");
    }

    public InvalidFieldType(String field, Class<?> expected, Class<?> received, @NotNull Plugin plugin) {
        super("Поле \"" + field + "\" имеет не верный тип в файле config.yml! (Ожидается " +
                expected.getName() + ", но получен " + received.getName() + ").");
        plugin.getLogger().severe(this.getMessage());
    }
}
