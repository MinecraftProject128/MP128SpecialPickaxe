package project128.minecraft.mP128SpecialPickaxe.listeners;

import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;

public class PickaxeListener implements Listener {
    private final Plugin plugin;

    public PickaxeListener(Plugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        ItemStack usedItem = player.getInventory().getItemInMainHand();
        NamespacedKey namespacedKey = NamespacedKey.fromString("specialpickaxe", plugin);
        if (namespacedKey != null)
            plugin.getLogger().info(usedItem.getItemMeta().getPersistentDataContainer()
                    .get(namespacedKey, PersistentDataType.STRING));
    }

}
