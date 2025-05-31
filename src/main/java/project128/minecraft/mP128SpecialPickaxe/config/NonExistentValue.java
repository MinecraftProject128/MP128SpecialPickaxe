package project128.minecraft.mP128SpecialPickaxe.config;

import org.apache.commons.lang.StringUtils;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public class NonExistentValue extends RuntimeException {
    public <E extends Enum<E>> NonExistentValue(String value, @NotNull Class<E> clazz) {
        super("Значение \"" + value + "\" неуместно! (Ожидается любое из значений: " +
                StringUtils.join(Arrays.stream(clazz.getEnumConstants()).toArray(), ", ") + ").");
    }
    public <E extends Enum<E>> NonExistentValue(String value, @NotNull Class<E> clazz, @NotNull Plugin plugin) {
        super("Значение \"" + value + "\" неуместно! (Ожидается любое из значений: " +
                StringUtils.join(Arrays.stream(clazz.getEnumConstants()).toArray(), ", ") + ").");
        plugin.getLogger().severe(this.getMessage());
    }
}
