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

import java.util.*;

public class SpearsAttackHandler implements Listener {

    public static NamespacedKey spearItemID = new NamespacedKey(Spearsbackport.getInstance(), "spearID");
    private static List<UUID> playersUsingSpear = new ArrayList<>();
    private static Map<UUID, Float> originalSpeeds = new HashMap<>();

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

        originalSpeeds.put(player.getUniqueId(), player.getWalkSpeed());
        player.sendMessage(String.valueOf(player.getWalkSpeed()));

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



                double speed = lastLocation.distance(currentLocation) * 1.2;
                lastLocation = currentLocation;

                if (speed <= 5.1) return;

                for (Entity entity : player.getLocation().add(player.getLocation().getDirection().normalize().multiply(1.5)).getNearbyEntities(1.8, 1.3, 1.8)) {
                    if (player.getVehicle() != null) {
                        if (entity instanceof LivingEntity horse) {
                            if (horse == player.getVehicle()) continue;
                        }
                    }
                    if (entity == player) continue;
                    if (!(entity instanceof LivingEntity nearby)) continue;
                    assert registered != null;
                    nearby.damage(speed * registered.getMult(), player);
                    Vector knockback = nearby.getLocation().toVector()
                            .subtract(player.getLocation().toVector());

                    if (knockback.lengthSquared() == 0) {
                        knockback = new Vector(0, 0, 0);
                    } else {
                        knockback.normalize().multiply(1.5);
                    }

                    knockback.setY(0.4);
                    nearby.setVelocity(knockback);

                }
            }

        }.runTaskTimer(Spearsbackport.getInstance(), 0L, 5L);




    }

    @EventHandler
    public void spearCancelled(PlayerStopUsingItemEvent event) {
        ItemStack item = event.getItem();
        if (item == null) return;
        if (item.getItemMeta().getPersistentDataContainer().get(spearItemID, PersistentDataType.STRING) == null) return;

        Player player = event.getPlayer();
        playersUsingSpear.remove(player.getUniqueId());
        float original = originalSpeeds.getOrDefault(player.getUniqueId(), 0.2f);
        player.setWalkSpeed(original);
        originalSpeeds.remove(player.getUniqueId());
        player.setCooldown(item.getType(), 13);

    }

}
