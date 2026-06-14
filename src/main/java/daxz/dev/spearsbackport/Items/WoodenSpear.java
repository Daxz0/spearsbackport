package daxz.dev.spearsbackport.Items;

import daxz.dev.spearsbackport.Registry.CustomItem;
import daxz.dev.spearsbackport.Spearsbackport;
import io.papermc.paper.datacomponent.DataComponentTypes;
import io.papermc.paper.datacomponent.item.Consumable;
import io.papermc.paper.datacomponent.item.UseCooldown;
import io.papermc.paper.datacomponent.item.consumable.ItemUseAnimation;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class WoodenSpear implements CustomItem {



    @Override
    public String getID() {
        return "wooden_spear";
    }

    public static final WoodenSpear INSTANCE = new WoodenSpear();
    private static NamespacedKey spearItemID = new NamespacedKey(Spearsbackport.getInstance(), "spearID");



    @Override
    public ItemStack createItem() {

        Material material = Material.WOODEN_SWORD;
        ItemStack item = ItemStack.of(material);



        item.setData(DataComponentTypes.CUSTOM_NAME, Component.text("Wooden Spear", NamedTextColor.WHITE).decoration(TextDecoration.ITALIC, false));

        //stats
        /*
            1 heart (2 dmg)
            1.54 attack speed
            0.7x charge mult
            13 tick cooldown
            4.5 block attack range
        */

        NamespacedKey spearAttributes = new NamespacedKey(Spearsbackport.getInstance(), "spearAttributes");

        item.getItemMeta().addAttributeModifier(Attribute.BLOCK_INTERACTION_RANGE, new AttributeModifier(spearAttributes, 2.0, AttributeModifier.Operation.ADD_NUMBER));
        item.getItemMeta().addAttributeModifier(Attribute.ENTITY_INTERACTION_RANGE, new AttributeModifier(spearAttributes, 2.0, AttributeModifier.Operation.ADD_NUMBER));



        item.editPersistentDataContainer(pdc -> {
            pdc.set(spearItemID, PersistentDataType.STRING, getID());
        });


        return item;
    }

    @Override
    public @Nullable ShapedRecipe getRecipe() {
        return null;
    }
}
