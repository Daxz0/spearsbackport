package daxz.dev.spearsbackport.Items;

import daxz.dev.spearsbackport.Events.SpearsAttackHandler;
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

public class WoodenSpear implements SpearItem {



    @Override
    public String getID() {
        return "wooden_spear";
    }

    public static final WoodenSpear INSTANCE = new WoodenSpear();

    private float jab_damage = 1f;
    private float attackSpeed = -2.46f;
    private float cooldown = 0.65f;
    private double activationDelay = 0.75;



    @Override
    public ItemStack createItem() {

        Material material = Material.WOODEN_SWORD;
        ItemStack item = ItemStack.of(material);
        //stats
        /*
            1 heart (2 dmg)
            1.54 attack speed
            0.7x charge mult
            13 tick cooldown
            4.5 block attack range
            0.75 activation delay
        */




        item.setData(DataComponentTypes.CUSTOM_NAME, Component.text("Wooden Spear", NamedTextColor.WHITE).decoration(TextDecoration.ITALIC, false));
        item.setData(DataComponentTypes.CONSUMABLE, Consumable.consumable().animation(ItemUseAnimation.BRUSH).consumeSeconds(3.0f).sound(Key.key("")).hasConsumeParticles(false));
        item.setData(DataComponentTypes.USE_COOLDOWN, UseCooldown.useCooldown(cooldown));



        NamespacedKey spearAttributes = new NamespacedKey(Spearsbackport.getInstance(), "spearAttributes");

        item.editMeta(meta -> {
            meta.addAttributeModifier(Attribute.BLOCK_INTERACTION_RANGE, new AttributeModifier(spearAttributes, 0.5, AttributeModifier.Operation.ADD_NUMBER));
            meta.addAttributeModifier(Attribute.ENTITY_INTERACTION_RANGE, new AttributeModifier(spearAttributes, 0.5, AttributeModifier.Operation.ADD_NUMBER));
            meta.addAttributeModifier(Attribute.ATTACK_SPEED, new AttributeModifier(spearAttributes, attackSpeed, AttributeModifier.Operation.ADD_NUMBER));
            meta.addAttributeModifier(Attribute.ATTACK_DAMAGE, new AttributeModifier(spearAttributes, jab_damage, AttributeModifier.Operation.ADD_NUMBER));
        });


        item.editPersistentDataContainer(pdc -> {
            pdc.set(SpearsAttackHandler.spearItemID, PersistentDataType.STRING, getID());
        });


        return item;
    }

    @Override
    public @Nullable ShapedRecipe getRecipe() {
        return null;
    }

    @Override
    public double getCooldown() {
        return cooldown;
    }
}
