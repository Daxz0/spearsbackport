package daxz.dev.spearsbackport.Registry;

import daxz.dev.haulover.Haulover;
import daxz.dev.haulover.Skills.Farming.FarmingTools.FarmingToolHandlers.WateringCanHandler;
import daxz.dev.haulover.Skills.Farming.FarmingTools.WateringCans.AdvancedWateringCanRecipe;
import daxz.dev.haulover.Skills.Farming.Helpers.DisabledBonemeal;
import daxz.dev.haulover.Skills.Farming.Helpers.OverrideFarmland;
import daxz.dev.haulover.Skills.Farming.Helpers.RandomSeedDrop;
import daxz.dev.haulover.Utilities.FlagEvents.NoConsume;
import daxz.dev.haulover.Utilities.FlagEvents.NoInteractions;
import daxz.dev.spearsbackport.Spearsbackport;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

public class EventRegister {

    public static void registerEvents() {
        PluginManager pm = Spearsbackport.getInstance().getServer().getPluginManager();
        Plugin instance = Spearsbackport.getInstance();
        pm.registerEvents(new (), instance);

    }
}
