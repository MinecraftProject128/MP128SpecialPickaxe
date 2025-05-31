package project128.minecraft.mP128SpecialPickaxe.config.exceptions;

import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

public class UnacceptableEnchantment extends RuntimeException {
    public UnacceptableEnchantment(String message) {
        super(message);
    }

    public UnacceptableEnchantment(String message, @NotNull Plugin plugin) {
        super(message);
        plugin.getLogger().severe(this.getMessage());
    }
}
