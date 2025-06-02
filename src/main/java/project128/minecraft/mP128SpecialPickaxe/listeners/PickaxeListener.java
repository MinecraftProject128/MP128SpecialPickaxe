package project128.minecraft.mP128SpecialPickaxe.listeners;

import it.unimi.dsi.fastutil.Pair;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;
import project128.minecraft.mP128SpecialPickaxe.config.Config;
import project128.minecraft.mP128SpecialPickaxe.config.SpecialPickaxe;

import java.util.ArrayList;
import java.util.List;

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
        if (namespacedKey != null) {
            String name = usedItem.getItemMeta().getPersistentDataContainer()
                    .get(namespacedKey, PersistentDataType.STRING);
            if (name == null)
                return;
            SpecialPickaxe data = Config.getInstance(plugin)
                    .getPickaxe(name);
            removeBlocks(event.getBlock().getLocation(), data);
        }
    }

    private List<Pair<Integer, Integer>> getLine(double angle, int length, boolean mirror) {
        List<Pair<Integer, Integer>> points = new ArrayList<>();
        int l = 0;
        if (mirror)
            l = -length;
        while (l <= length) {
            points.add(Pair.of(
                    ((Long) (Math.round(Math.cos(angle)) * l)).intValue(),
                    ((Long) (Math.round(Math.sin(angle)) * l)).intValue()
            ));
            l += 1;
        }
        return points;
    }

    private void removeBlocks(Location started, SpecialPickaxe data) {
        World w = started.getWorld();
        Integer sx = started.getBlockX();
        Integer sy = started.getBlockY();
        Integer sz = started.getBlockZ();
        List<Pair<Integer, Integer>> line = getLine(0, data.getShape().length(), false);
        List<Pair<Integer, Integer>> perpendicular = getLine(Math.PI / 2, data.getShape().width(), true);
        for (Pair<Integer, Integer> masterPoint : line) {
            new Location(w, sx + masterPoint.first(), sy, sz + masterPoint.second())
                    .getBlock().setType(Material.GLASS);
        }
    }

}
