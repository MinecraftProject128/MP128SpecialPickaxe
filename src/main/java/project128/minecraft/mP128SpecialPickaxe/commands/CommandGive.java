package project128.minecraft.mP128SpecialPickaxe.commands;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.NamespacedKey;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import project128.minecraft.mP128SpecialPickaxe.config.Config;
import project128.minecraft.mP128SpecialPickaxe.config.SpecialPickaxe;

import java.util.ArrayList;
import java.util.List;

public class CommandGive extends MP128Command {

    public CommandGive(Plugin plugin, List<List<ValueType>> typesOfArguments) {
        super(plugin, typesOfArguments);
    }

    @Override
    public void run(@NotNull CommandSender commandSender, @NotNull String[] strings) {
        if (getTypesOfArguments(strings) == this.typesOfArguments.get(0)) {
            SpecialPickaxe pickaxe = Config.getInstance(plugin).getPickaxe(strings[0]);
            if (commandSender instanceof Player player)
                givePickaxe(player, pickaxe);
        }
    }

    @Override
    public boolean hasPermission(@NotNull CommandSender commandSender, @NotNull String[] strings, @NotNull String permissionPrefix) {
        return commandSender.hasPermission(permissionPrefix + "give");
    }

    @Override
    public List<String> tabComplete(@NotNull CommandSender commandSender, @NotNull String[] strings) {
        if (strings.length == 2)
            return Config.getInstance(plugin).getPickaxes();
        return List.of();
    }

    public void givePickaxe(Player player, SpecialPickaxe data) {
        player.getInventory().addItem(getPickaxe(data));
    }

    public ItemStack getPickaxe(SpecialPickaxe data) {
        ItemStack pickaxe = new ItemStack(SpecialPickaxe.PickaxeMaterial.toPickaxe(
                data.getMaterial()
        ), 1);
        pickaxe.setItemMeta(getPickaxeMeta(pickaxe.getItemMeta(), data));
        addEnchantments(pickaxe, data);

        return pickaxe;
    }

    // TODO: Разобраться как убирать курсив по желанию пользователя
    private ItemMeta getPickaxeMeta(ItemMeta meta, SpecialPickaxe data) {
        meta.displayName(LegacyComponentSerializer.legacySection().deserialize(
                "§r" + data.getDisplayName().replace("&", "§")
        ).decoration(TextDecoration.ITALIC, false));
        meta.lore(formatLegacyListToComponent(data.getDescription()));
        meta.getPersistentDataContainer().set(
                new NamespacedKey(plugin, "specialpickaxe"),
                PersistentDataType.STRING,
                data.getName()
        );
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

    private void addEnchantments(ItemStack pickaxe, SpecialPickaxe data) {
        for (Enchantment enchantment : data.getEnchantments().keySet())
            pickaxe.addEnchantment(enchantment, data.getEnchantments().get(enchantment));
    }

}
