package project128.minecraft.mP128SpecialPickaxe.commands;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import project128.minecraft.mP128SpecialPickaxe.config.SpecialPickaxe;

import java.util.ArrayList;
import java.util.List;

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

    public void givePickaxe(Player player, SpecialPickaxe data) {
        ItemStack pickaxe = new ItemStack(SpecialPickaxe.PickaxeMaterial.toPickaxe(
                data.getMaterial()
        ), 1);
        pickaxe.setItemMeta(setupPickaxeMeta(pickaxe.getItemMeta(), data));

        for (Enchantment enchantment : data.getEnchantments().keySet())
            pickaxe.addEnchantment(enchantment, data.getEnchantments().get(enchantment));

        player.getInventory().addItem(pickaxe);
    }

    // TODO: Разобраться как убирать курсив по желанию пользователя
    private ItemMeta setupPickaxeMeta(ItemMeta meta, SpecialPickaxe data) {
        meta.displayName(LegacyComponentSerializer.legacySection().deserialize(
                "§r" + data.getDisplayName().replace("&", "§")
        ).decoration(TextDecoration.ITALIC, false));
        meta.lore(formatLegacyListToComponent(data.getDescription()));
        return meta;
    }

    // TODO: Разобраться как убирать курсив по желанию пользователя
    private List<Component> formatLegacyListToComponent(List<String> legacy) {
        List<Component> newList = new ArrayList<>();
        for (String line : legacy) {
            newList.add(LegacyComponentSerializer.legacySection().deserialize(
                    "§r" + line.replace("&", "§")
            ).decoration(TextDecoration.ITALIC, false));
        }
        return newList;
    }


}
