package daxz.dev.spearsbackport.Registry;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;

import javax.annotation.Nullable;

public interface CustomItem {
    String getID();

    ItemStack createItem();
    @Nullable
    ShapedRecipe getRecipe();
}
