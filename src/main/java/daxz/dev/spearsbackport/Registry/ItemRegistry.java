package daxz.dev.spearsbackport.Registry;



import daxz.dev.spearsbackport.Items.SpearItem;
import daxz.dev.spearsbackport.Items.WoodenSpear;
import daxz.dev.spearsbackport.Spearsbackport;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;

import java.util.HashMap;
import java.util.Map;

public class ItemRegistry {
    private static final Map<String, CustomItem> REGISTRY = new HashMap<>();
    public static void registerItems() {

        register(WoodenSpear.INSTANCE);

    }

    private static void register(CustomItem item) {

        REGISTRY.put(item.getID(), item);
        ShapedRecipe recipe = item.getRecipe();

        if (recipe != null) {
            Spearsbackport.getInstance().getServer().addRecipe(recipe);
        }
    }

    public static ItemStack getItem(String id) {
        CustomItem item = REGISTRY.get(id);
        return item != null ? item.createItem() : null;
    }

    public static CustomItem getCustomItem(String id) {
        return REGISTRY.get(id);
    }

    public static SpearItem getSpear(String id) {
        CustomItem item = REGISTRY.get(id);
        return item instanceof SpearItem spear ? spear : null;
    }

    public static boolean giveItem(Player player, String id) {
        CustomItem item = REGISTRY.get(id);
        if (item != null) {
            player.getInventory().addItem(item.createItem());
            return true;
        }
        return false;
    }

    public static Map<String, CustomItem> getRegisteredItems() {return REGISTRY;}

}
