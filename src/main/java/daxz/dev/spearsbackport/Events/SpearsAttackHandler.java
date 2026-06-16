package daxz.dev.spearsbackport.Events;

import daxz.dev.spearsbackport.Items.SpearItem;
import daxz.dev.spearsbackport.Registry.CustomItem;
import daxz.dev.spearsbackport.Registry.ItemRegistry;
import daxz.dev.spearsbackport.Spearsbackport;
import io.papermc.paper.event.player.PlayerStopUsingItemEvent;
import net.md_5.bungee.api.chat.BaseComponent;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class SpearsAttackHandler implements Listener {

    public static NamespacedKey spearItemID = new NamespacedKey(Spearsbackport.getInstance(), "spearID");
    private static List<UUID> playersUsingSpear = new ArrayList<>();

    @EventHandler
    public void stopSpearConsume(PlayerItemConsumeEvent event) {
        ItemStack item = event.getItem();
        Player player = event.getPlayer();
        if (item == null) return;
        if (item.getItemMeta().getPersistentDataContainer().get(spearItemID, PersistentDataType.STRING) == null) return;
        event.setCancelled(true);

    }

    @EventHandler
    public void playerInteractsUsingSpear(PlayerInteractEvent event) {

        ItemStack item = event.getItem();

        Player player = event.getPlayer();
        if (playersUsingSpear.contains(player.getUniqueId())) return;
        if (item == null) return;
        if (item.getItemMeta().getPersistentDataContainer().get(spearItemID, PersistentDataType.STRING) == null) return;

        playersUsingSpear.add(player.getUniqueId());

        SpearItem registered = ItemRegistry.getSpear(item.getItemMeta().getPersistentDataContainer().get(spearItemID, PersistentDataType.STRING));

        new BukkitRunnable() {

            int timeout = 0;
            int stopTimeout = 0;
            org.bukkit.Location lastLocation = player.getLocation();

            @Override
            public void run() {
                timeout++;

                if (timeout > 35) { cancel(); return; }
                if (!playersUsingSpear.contains(player.getUniqueId())) {
                    stopTimeout++;
                    if (stopTimeout > 5) cancel();
                    return;
                }
                if (timeout <= 2) {
                    lastLocation = player.getLocation();
                    return;
                }

                Location currentLocation = player.getLocation();
                if (player.getVehicle() != null) {
                    currentLocation = player.getVehicle().getLocation();
                }

                double speed = lastLocation.distance(currentLocation) * 20.0;
                lastLocation = currentLocation;

                if (speed <= 5.1) return;

                for (Entity entity : player.getLocation().add(player.getLocation().getDirection().normalize().multiply(0.5)).getNearbyEntities(0.5, 1, 0.5)) {
                    if (player.getVehicle() != null) {
                        if (entity instanceof LivingEntity horse) {
                            if (horse == player.getVehicle()) continue;
                        }
                    }
                    if (!(entity instanceof LivingEntity nearby)) continue;
                    nearby.damage(speed * registered.getMult());
                }
            }

        }.runTaskTimer(Spearsbackport.getInstance(), 0L, 5L);




    }

    @EventHandler
    public void wateringCanCancelled(PlayerStopUsingItemEvent event) {
        ItemStack item = event.getItem();
        if (item == null) return;
        if (item.getItemMeta().getPersistentDataContainer().get(spearItemID, PersistentDataType.STRING) == null) return;

        Player player = event.getPlayer();
        playersUsingSpear.remove(player.getUniqueId());
        player.setCooldown(item.getType(), 13);

    }

}
