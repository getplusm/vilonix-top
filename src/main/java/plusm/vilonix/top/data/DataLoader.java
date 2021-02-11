package plusm.vilonix.top.data;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import plusm.vilonix.api.VilonixNetwork;
import plusm.vilonix.api.player.IBaseGamer;
import plusm.vilonix.api.sql.ConnectionConstants;
import plusm.vilonix.api.sql.GlobalLoader;
import plusm.vilonix.api.sql.api.MySqlDatabase;
import plusm.vilonix.api.util.ConfigUtil;
import plusm.vilonix.api.util.LocationUtil;
import plusm.vilonix.top.api.StandTopData;
import plusm.vilonix.top.api.Top;
import plusm.vilonix.top.api.TopManager;
import plusm.vilonix.top.types.TopData;
import plusm.vilonix.top.types.TopHolo;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DataLoader {

    public static Map<String, List<StandTopData>> cachedStand = new ConcurrentHashMap<>();
    public static Map<String, Top> cachedTop = new ConcurrentHashMap<>();
    public static Map<String, MySqlDatabase> cachedSQL = new ConcurrentHashMap<>();

    public static void init(TopManager topManager, FileConfiguration configuration) {
        try {
            cachedTop.clear();
            for (String s : configuration.getConfigurationSection("DATA").getKeys(false)) {
                String path = "DATA." + s + ".";
                String table = configuration.getString(path + "table");
                String base = configuration.getString(path + "base");
                String counters = configuration.getString(path + "counter");
                String identifier = configuration.getString(path + "identifier");
                String lastString = ChatColor.translateAlternateColorCodes('&', configuration.getString(path + "string"));
                List<String> text = configuration.getStringList(path + "holo");
                Location location = LocationUtil.stringToLocation(configuration.getString("CENTER"), false);
                MySqlDatabase MYSQL_DATABASE;
                if (!base.equalsIgnoreCase(ConnectionConstants.DATA.getValue())) {
                    if (cachedSQL.containsKey(base))
                        MYSQL_DATABASE = cachedSQL.get(base);
                    else {
                        MYSQL_DATABASE = MySqlDatabase.newBuilder()
                                .host(ConnectionConstants.DOMAIN.getValue())
                                .password(ConnectionConstants.PASSWORD.getValue())
                                .user(ConnectionConstants.USER.getValue())
                                .data(base)
                                .create();
                        cachedSQL.put(base, MYSQL_DATABASE);
                    }
                } else {
                    if (cachedSQL.containsKey(ConnectionConstants.DATA.getValue()))
                        MYSQL_DATABASE = cachedSQL.get(ConnectionConstants.DATA.getValue());
                    else {
                        MYSQL_DATABASE = GlobalLoader.getMysqlDatabase();
                        cachedSQL.put(ConnectionConstants.DATA.getValue(), MYSQL_DATABASE);
                    }
                }
                if (MYSQL_DATABASE == null)
                    return;
                if (counters.split(":").length > 1) {
                    MYSQL_DATABASE.executeQuery("SELECT * FROM `" + table + "` WHERE " + counters.split(":")[0] + " ORDER BY `" + counters.split(":")[1] + "` DESC", rs -> {
                        int pos = 0;

                        List<String> correctly = new LinkedList<>();
                        if (configuration.contains(path + "correctly") && configuration.getString(path + "correctly").startsWith("config:"))
                            correctly.addAll(ConfigUtil.getList(configuration.getString(path + "correctly").replace("config:", "")));
                        else if (configuration.contains(path + "correctly"))
                            correctly = configuration.getStringList(path + "correctly");

                        List<StandTopData> standData = new ArrayList<>();
                        while (rs.next()) {
                            final IBaseGamer iGamer = VilonixNetwork.getGamerManager().getOrCreate(rs.getInt(identifier));
                            if (iGamer != null) {
                                if (iGamer.getPlayerID() == -1) {
                                    continue;
                                }
                                if (iGamer.isHelper()) {
                                    continue;
                                }
                                if (iGamer.isYouTube()) {
                                    continue;
                                }
                                if (++pos > 6) {
                                    continue;
                                }
                                TopData top = new TopData(pos, rs.getInt(counters.split(":")[1]), iGamer, lastString, correctly, s);
                                standData.add(top);
                            }
                        }
                        cachedStand.put(s, standData);
                        Top top = new TopHolo(text, topManager, location);
                        cachedTop.put(s, top);
                        return Void.TYPE;
                    });
                } else

                    MYSQL_DATABASE.executeQuery("SELECT * FROM `" + table + "` ORDER BY `" + counters + "` DESC", rs -> {
                        int pos = 0;

                        List<String> correctly = new LinkedList<>();
                        if (configuration.contains(path + "correctly") && configuration.getString(path + "correctly").startsWith("config:"))
                            correctly.addAll(ConfigUtil.getList(configuration.getString(path + "correctly").replace("config:", "")));
                        else if (configuration.contains(path + "correctly"))
                            correctly = configuration.getStringList(path + "correctly");

                        List<StandTopData> standData = new ArrayList<>();
                        while (rs.next()) {
                            final IBaseGamer iGamer = VilonixNetwork.getGamerManager().getOrCreate(rs.getInt(identifier));
                            if (iGamer != null) {
                                if (iGamer.getPlayerID() == -1) {
                                    continue;
                                }
                                if (iGamer.isHelper()) {
                                    continue;
                                }
                                if (iGamer.isYouTube()) {
                                    continue;
                                }
                                if (++pos > 6) {
                                    continue;
                                }
                                TopData top = new TopData(pos, rs.getInt(counters), iGamer, lastString, correctly, s);
                                standData.add(top);
                            }
                        }
                        cachedStand.put(s, standData);
                        Top top = new TopHolo(text, topManager, location);
                        cachedTop.put(s, top);
                        return Void.TYPE;
                    });
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
