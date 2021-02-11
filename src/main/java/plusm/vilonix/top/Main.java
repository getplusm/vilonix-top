package plusm.vilonix.top;

import org.bukkit.plugin.java.JavaPlugin;
import plusm.vilonix.top.api.StandPlayer;
import plusm.vilonix.top.config.MainConfig;
import plusm.vilonix.top.config.TopConfig;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Main extends JavaPlugin {

    private final Map<String, MainConfig> configs = new ConcurrentHashMap<>();
    private static Main instance;

    public static Main getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;
        initConfig(TopConfig.class);
    }

    @Override
    public void onDisable() {
        for (StandPlayer sp : StandPlayer.cachedPlayers.values())
            sp.hide();
    }

    private <T extends MainConfig> T initConfig(Class<T> configClass) {
        String name = configClass.getSimpleName().toLowerCase();
        T config = null;
        try {
            config = configClass.getConstructor(Main.class).newInstance(this);
            config.load();
            config.init();
            configs.put(name, config);
        } catch (Exception ignored) {
        }
        return config;
    }

    @SuppressWarnings("unchecked")
    public <T extends MainConfig> T getConfig(Class<T> configClass) {
        String name = configClass.getSimpleName().toLowerCase();
        return (T) configs.get(name);
    }
}

