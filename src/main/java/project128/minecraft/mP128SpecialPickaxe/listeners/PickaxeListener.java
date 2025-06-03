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
            double angle = Math.PI * player.getLocation().getYaw() / 180;

            removeBlocks(event.getBlock().getLocation(), angle, data);
        }
    }

    private List<Pair<Integer, Integer>> getLine(double angle, int length, boolean mirror) {
        List<Pair<Integer, Integer>> points = new ArrayList<>();
        int l = 0;
        if (mirror)
            l = -length;
        while (l <= length) {
            points.add(Pair.of(
                    ((Long) (Math.round(Math.cos(angle) * l))).intValue(),
                    ((Long) (Math.round(Math.sin(angle) * l))).intValue()
            ));
            l += 1;
        }
        return points;
    }

    private void removeBlocks(Location started, double angle, SpecialPickaxe data) {
        World w = started.getWorld();
        int sx = started.getBlockX();
        int sz = started.getBlockZ();
        List<Pair<Integer, Integer>> line = getLine(angle, data.getShape().length(), false);
        List<Pair<Integer, Integer>> perpendicular = getLine(angle + Math.PI / 2, data.getShape().width(), true);
        new Location(w, sx, started.getBlockY(), sz)
                .getBlock().breakNaturally();
        for (int sy = started.getBlockY(); sy < started.getBlockY() + data.getShape().height(); sy++) {
            for (Pair<Integer, Integer> masterPoint : line) {
                new Location(w, sx - masterPoint.second(), sy, sz + masterPoint.first())
                        .getBlock().breakNaturally();
                for (Pair<Integer, Integer> perpPoint : perpendicular) {
                    new Location(w, sx - masterPoint.second() - perpPoint.second(), sy,
                            sz + masterPoint.first() + perpPoint.first())
                            .getBlock().breakNaturally();
                    new Location(w, sx - masterPoint.second() - perpPoint.second(), sy,
                            sz + masterPoint.first() + perpPoint.first() + 1)
                            .getBlock().breakNaturally();
                    new Location(w, sx - masterPoint.second() - perpPoint.second(), sy,
                            sz + masterPoint.first() + perpPoint.first() - 1)
                            .getBlock().breakNaturally();
                }
            }
        }
    }

}
