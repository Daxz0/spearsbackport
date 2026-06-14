package daxz.dev.spearsbackport;

import daxz.dev.spearsbackport.Commands.CommandHandler;
import daxz.dev.spearsbackport.Registry.EventRegister;
import daxz.dev.spearsbackport.Registry.ItemRegistry;
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;
import org.bukkit.plugin.java.JavaPlugin;

import java.beans.EventHandler;

public final class Spearsbackport extends JavaPlugin {

    public static Spearsbackport instance;

    @Override
    public void onEnable() {

        instance = this;
        EventRegister.registerEvents();
        ItemRegistry.registerItems();
        this.getLifecycleManager().registerEventHandler(LifecycleEvents.COMMANDS, commands -> {
            commands.registrar().register(CommandHandler.spears());
        });


    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static Spearsbackport getInstance() {
        return instance;
    }
}
