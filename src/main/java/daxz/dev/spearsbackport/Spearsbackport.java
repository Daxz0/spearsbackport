package daxz.dev.spearsbackport;

import org.bukkit.plugin.java.JavaPlugin;

public final class Spearsbackport extends JavaPlugin {

    public static Spearsbackport instance;

    @Override
    public void onEnable() {

        instance = this;


    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static Spearsbackport getInstance() {
        return instance;
    }
}
