package project128.minecraft.mP128SpecialPickaxe.commands;

import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import project128.minecraft.mP128SpecialPickaxe.config.SpecialPickaxe;

public class GiveSpecialPickaxe {
    private static GiveSpecialPickaxe instance;

    private final Plugin plugin;

    private GiveSpecialPickaxe(Plugin plugin) {
        this.plugin = plugin;
    }

    public static synchronized GiveSpecialPickaxe getInstance(Plugin plugin) {
        if (instance == null)
            instance = new GiveSpecialPickaxe(plugin);
        return instance;
    }

    private ItemMeta setupPickaxeMeta(ItemMeta meta, SpecialPickaxe data) {
        meta.displayName(LegacyComponentSerializer.legacySection().deserialize(
                "§r" + data.getDisplayName().replace("&", "§")
        ));

        return meta;
    }

    public void givePickaxe(Player player, SpecialPickaxe data) {
        ItemStack pickaxe = new ItemStack(SpecialPickaxe.PickaxeMaterial.toPickaxe(
                data.getMaterial()
        ), 1);
        pickaxe.setItemMeta(setupPickaxeMeta(pickaxe.getItemMeta(), data));

        player.getInventory().addItem(pickaxe);
    }

}
