package plusm.vilonix.top.config;

import lombok.Getter;
import org.bukkit.configuration.file.FileConfiguration;
import plusm.vilonix.api.VilonixNetwork;
import plusm.vilonix.api.player.Spigot;
import plusm.vilonix.api.util.ConfigManager;
import plusm.vilonix.gapi.utils.core.CoreUtil;
import plusm.vilonix.top.Main;

import java.io.File;

public abstract class MainConfig {
    private static final Spigot SPIGOT = VilonixNetwork.getGamerManager().getSpigot();

    ConfigManager configManager;

    @Getter
    protected final Main lobby;

    protected MainConfig(Main lobby, String fileName) {
        this.lobby = lobby;
        File file = new File(
                //Main.getInstance().getDataFolder() + "/" + fileName + ".yml");
        CoreUtil.getConfigDirectory() + "/" + fileName + ".yml");
        if (!file.exists()) {
            SPIGOT.sendMessage("§c[ТОПЫ] Конфиг " + fileName + " не найден, кажется некоторые вещи работать не будут {config=" + CoreUtil.getConfigDirectory() + "/" + fileName + ".yml" + "}");
            return;
        }
        this.configManager = new ConfigManager(file);
    }

    public abstract void load();

    public abstract void init();

    public FileConfiguration getConfig() {
        return configManager.getConfig();
    }
}
