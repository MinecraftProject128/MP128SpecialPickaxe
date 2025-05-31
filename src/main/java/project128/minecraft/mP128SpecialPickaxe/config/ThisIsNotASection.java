package project128.minecraft.mP128SpecialPickaxe.config;

import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

public class ThisIsNotASection extends RuntimeException {
    public ThisIsNotASection(String section) {
        super("\"" + section + "\" в файле config.yml - это не секция, а должна быть!");
    }

    public ThisIsNotASection(String section, @NotNull Plugin plugin) {
        super("\"" + section + "\" в файле config.yml - это не секция, а должна быть!");
        plugin.getLogger().severe(this.getMessage());
    }
}
