package project128.minecraft.mP128SpecialPickaxe.config;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import project128.minecraft.mP128SpecialPickaxe.config.exceptions.*;

import java.util.*;


public class Config {
    private static Config instance;

    private final Plugin plugin;

    private Config(Plugin plugin) {
        this.plugin = plugin;
    }

    public static synchronized Config getInstance(Plugin plugin) {
        if (instance == null)
            instance = new Config(plugin);
        return instance;
    }

    public <T> T getField(@NotNull String @NotNull [] path, Class<T> type) {
        String fieldName = path[path.length - 1];
        ConfigurationSection section = getSection(Arrays.copyOfRange(path, 0, path.length - 1));
        if (section.get(fieldName) == null)
            throw new FieldDoesNotExist(fieldName, plugin);
        if (section.get(fieldName).getClass() != type)
            throw new InvalidFieldType(fieldName, type,
                    section.get(fieldName).getClass(), plugin);

        return section.getObject(fieldName, type);
    }

    public Object getField(@NotNull Field target) {
        return getField(target.path, target.type);
    }

    public SpecialPickaxe getPickaxe(@NotNull String name) {
        String displayName = getField(new String[]{"pickaxes", name, "display-name"}, String.class);
        List<String> description = getField(new String[]{"pickaxes", name, "description"}, ArrayList.class);
        boolean freeUse = getField(new String[]{"pickaxes", name, "free-use"}, Boolean.class);
        SpecialPickaxe.Shape shape = new SpecialPickaxe.Shape(
                SpecialPickaxe.ShapeType.get(
                        getField(new String[]{"pickaxes", name, "shape", "type"}, String.class), plugin),
                getField(new String[]{"pickaxes", name, "shape", "size", "radius"}, Integer.class),
                getField(new String[]{"pickaxes", name, "shape", "size", "width"}, Integer.class),
                getField(new String[]{"pickaxes", name, "shape", "size", "height"}, Integer.class),
                getField(new String[]{"pickaxes", name, "shape", "size", "length"}, Integer.class)
        );
        SpecialPickaxe.PickaxeMaterial material = SpecialPickaxe.PickaxeMaterial.get(
                getField(new String[]{"pickaxes", name, "material"}, String.class), plugin);
        Map<Enchantment, Integer> enchantments = new HashMap<>();
        ConfigurationSection enchantmentsSection = getSection(new String[]{"pickaxes", name, "enchantments"});
        for (String enchantmentName : enchantmentsSection.getKeys(false)) {
            Enchantment enchantment = Enchantment.getByKey(NamespacedKey.minecraft(enchantmentName));
            int level = getField(new String[]{"pickaxes", name, "enchantments", enchantmentName}, Integer.class);

            if (enchantment == null)
                throw new UnacceptableEnchantment("Зачарование с именем \"" +
                        enchantmentName + "\" не существует!", plugin);
            if (!enchantment.canEnchantItem(new ItemStack(Material.WOODEN_PICKAXE)))
                throw new UnacceptableEnchantment("Зачарование с именем \"" +
                        enchantmentName + "\" нельзя наложить на кирку!", plugin);
            int fixLevel = Math.max(Math.min(level, enchantment.getMaxLevel()), 1);
            if (level != fixLevel)
                plugin.getLogger().warning("Неверный уровень кирки \"" + level +
                        "\" был изменён на \"" + fixLevel + "\".");
            enchantments.put(enchantment, fixLevel);
        }
        return new SpecialPickaxe(name, displayName, description, freeUse, shape, material, enchantments);
    }

    public @NotNull ConfigurationSection getSection(@NotNull String[] path) {
        ConfigurationSection section = plugin.getConfig();
        for (String s : path) {
            assert section != null;
            if (!section.isSet(s))
                throw new SectionDoesNotExist(s, plugin);
            if (!section.isConfigurationSection(s))
                throw new ThisIsNotASection(s, plugin);
            section = section.getConfigurationSection(s);
        }
        assert section != null;
        return section;
    }

    public enum Field {
        VERSION(new String[]{"version"}, Integer.class),
        NOT_ALLOWED_COMMAND(new String[]{"messages", "not-allowed-command"}, String.class),
        NOT_ALLOWED_USE(new String[]{"messages", "not-allowed-use"}, String.class),
        GIVE_SELF(new String[]{"messages", "give-self"}, String.class);

        public final String[] path;
        public final Class<?> type;

        Field(String @NotNull [] path, Class<?> type) {
            this.path = path;
            this.type = type;
        }
    }

}
