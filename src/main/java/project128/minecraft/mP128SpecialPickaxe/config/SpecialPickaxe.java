package project128.minecraft.mP128SpecialPickaxe.config;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import project128.minecraft.mP128SpecialPickaxe.config.exceptions.NonExistentValue;

import java.util.List;
import java.util.Map;

public class SpecialPickaxe {
    private final @NotNull String name;
    private @NotNull String displayName;
    private @NotNull List<String> description;
    private boolean freeUse;
    private @NotNull Shape shape;
    private @NotNull PickaxeMaterial material;
    private @NotNull Map<Enchantment, Integer> enchantments;

    public SpecialPickaxe(@NotNull String name, @NotNull String displayName,
                          @NotNull List<String> description, boolean freeUse, @NotNull Shape shape,
                          @NotNull PickaxeMaterial material, @NotNull Map<Enchantment, Integer> enchantments) {
        this.name = name;
        this.displayName = displayName;
        this.description = description;
        this.freeUse = freeUse;
        this.shape = shape;
        this.material = material;
        this.enchantments = enchantments;
    }

    public @NotNull String getName() {
        return name;
    }

    public @NotNull String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(@NotNull String displayName) {
        this.displayName = displayName;
    }

    public @NotNull List<String> getDescription() {
        return description;
    }

    public void setDescription(@NotNull List<String> description) {
        this.description = description;
    }

    public boolean isFreeUse() {
        return freeUse;
    }

    public void setFreeUse(boolean freeUse) {
        this.freeUse = freeUse;
    }

    public @NotNull Shape getShape() {
        return shape;
    }

    public void setShape(@NotNull Shape shape) {
        this.shape = shape;
    }

    public @NotNull PickaxeMaterial getMaterial() {
        return material;
    }

    public void setMaterial(@NotNull PickaxeMaterial material) {
        this.material = material;
    }

    public @NotNull Map<Enchantment, Integer> getEnchantments() {
        return enchantments;
    }

    public void setEnchantments(@NotNull Map<Enchantment, Integer> enchantments) {
        this.enchantments = enchantments;
    }

    public enum ShapeType {
        CIRCLE,
        RECTANGLE;

        public static ShapeType get(String name, Plugin plugin) {
            try {
                return valueOf(name);
            } catch (IllegalArgumentException e) {
                throw new NonExistentValue(name, ShapeType.class, plugin);
            }
        }
    }

    public enum PickaxeMaterial {
        WOOD,
        STONE,
        IRON,
        GOLD,
        DIAMOND,
        NETHERITE;

        public static PickaxeMaterial get(String name, Plugin plugin) {
            try {
                return valueOf(name);
            } catch (IllegalArgumentException e) {
                throw new NonExistentValue(name, PickaxeMaterial.class, plugin);
            }
        }

        public static Material toPickaxe(PickaxeMaterial material) {
            Map<PickaxeMaterial, Material> pickaxes = Map.of(
                    WOOD, Material.WOODEN_PICKAXE,
                    STONE, Material.STONE_PICKAXE,
                    IRON, Material.IRON_PICKAXE,
                    GOLD, Material.GOLDEN_PICKAXE,
                    DIAMOND, Material.DIAMOND_PICKAXE,
                    NETHERITE, Material.NETHERITE_PICKAXE
            );
            return pickaxes.get(material);
        }
    }

    public record Shape(ShapeType type, int radius, int width, int height, int length) {
    }

    @Override
    public String toString() {
        return "SpecialPickaxe{" +
                "name='" + name + '\'' +
                ", displayName='" + displayName + '\'' +
                ", description=" + description +
                ", freeUse=" + freeUse +
                ", shape=" + shape +
                ", material=" + material +
                ", enchantments=" + enchantments +
                '}';
    }
}
