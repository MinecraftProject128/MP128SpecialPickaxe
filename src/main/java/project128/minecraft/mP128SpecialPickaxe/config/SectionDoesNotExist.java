package project128.minecraft.mP128SpecialPickaxe.config;

import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

public class SectionDoesNotExist extends RuntimeException {
    public SectionDoesNotExist(String section) {
        super("Секции \"" + section + "\" не существует в файле config.yml!");
    }

    public SectionDoesNotExist(String section, @NotNull Plugin plugin) {
        super("Секции \"" + section + "\" не существует в файле config.yml!");
        plugin.getLogger().severe(this.getMessage());
    }
}
