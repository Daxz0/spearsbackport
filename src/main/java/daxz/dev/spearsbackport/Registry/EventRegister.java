package daxz.dev.spearsbackport.Registry;


import daxz.dev.spearsbackport.Events.SpearsAttackHandler;
import daxz.dev.spearsbackport.Spearsbackport;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

public class EventRegister {

    public static void registerEvents() {
        PluginManager pm = Spearsbackport.getInstance().getServer().getPluginManager();
        Plugin instance = Spearsbackport.getInstance();
        pm.registerEvents(new SpearsAttackHandler(), instance);

    }
}
