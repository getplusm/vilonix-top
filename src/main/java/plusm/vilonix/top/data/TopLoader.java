package plusm.vilonix.top.data;

/*import plusm.vilonix.api.VilonixNetwork;
import plusm.vilonix.api.player.IBaseGamer;
import plusm.vilonix.api.sql.GlobalLoader;
import plusm.vilonix.gapi.module.stats.Stats;
import plusm.vilonix.grief.manager.stats.StatsLoader;
import plusm.vilonix.guilds.api.guild.GuildAPI;
import plusm.vilonix.guilds.manager.guild.GuildAPIImpl;
import plusm.vilonix.top.api.StandTopData;
import plusm.vilonix.top.types.guilds.kills.GuildKills;
import plusm.vilonix.top.types.guilds.level.GuildLevel;
import plusm.vilonix.top.types.guilds.winbattles.GuildWB;
import plusm.vilonix.top.types.kills.KillsData;
import plusm.vilonix.top.types.money.MoneyData;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class TopLoader {

    public static Map<String, List<StandTopData>> cachedTop = new ConcurrentHashMap<>();

    public static void init() {
        loadKills();
        loadMoney();
        loadGuild(guildType.LEVEL);
        loadGuild(guildType.BATTLES);
        loadGuild(guildType.KILLS);
    }

    public static void reload() {
        cachedTop.clear();
        init();
    }

    public static void loadMoney() {
        GlobalLoader.getMysqlDatabase().executeQuery("SELECT `playerId`, `value` FROM `player_money` WHERE `typeMoney` = 0 ORDER BY `value` DESC LIMIT 20;", rs -> {
            int pos = 0;
            List<StandTopData> moneyTop = new ArrayList<>();
            while (rs.next()) {
                IBaseGamer iGamer = VilonixNetwork.getGamerManager().getOrCreate(rs.getInt("playerId"));
                if (iGamer == null || iGamer.getPlayerID() == -1)
                    continue;
                if (iGamer.isHelper() || iGamer.isYouTube())
                    continue;
                pos++;
                if (pos > 6)
                    continue;
                MoneyData top = new MoneyData(pos, rs.getInt("value"),
                        iGamer);
                moneyTop.add(top);
            }
            cachedTop.put("moneyTop", moneyTop);
            return Void.TYPE;
        });
    }

    public static void loadKills() {
        StatsLoader.getMysqlDatabase().executeQuery("SELECT `playerID`, `kills` FROM `Stats` ORDER BY `kills` DESC LIMIT 20;", rs -> {
            int pos = 0;
            List<StandTopData> killsTop = new ArrayList<>();
            while (rs.next()) {
                IBaseGamer iGamer = VilonixNetwork.getGamerManager().getOrCreate(rs.getInt("playerID"));
                if (iGamer == null || iGamer.getPlayerID() == -1)
                    continue;
                if (iGamer.isHelper() || iGamer.isYouTube())
                    continue;
                pos++;
                if (pos > 6)
                    continue;
                KillsData top = new KillsData(pos, rs.getInt("kills"),
                        iGamer);
                killsTop.add(top);
            }
            cachedTop.put("killsTop", killsTop);
            return Void.TYPE;
        });
    }

    public static void loadGuild(guildType type) {
        GlobalLoader.getMysqlDatabase().executeQuery("SELECT `name`, `kills`, `winningBattles`, `level` FROM `GUILDS` ORDER BY `" + type.getColumn() + "` DESC LIMIT 20;", rs -> {
            int pos = 0;
            List<StandTopData> guildTop = new ArrayList<>();
            while (rs.next()) {
                GuildAPI api = GuildAPIImpl.getGuild(rs.getString("name"));
                if (api == null || api.getLeader() == null)
                    continue;
                pos++;
                if (pos > 6)
                    continue;
                switch (type) {
                    case KILLS:
                        GuildKills top = new GuildKills(pos, rs.getInt(type.getColumn()),
                                api);
                        guildTop.add(top);
                        break;
                    case LEVEL:
                        GuildLevel top1 = new GuildLevel(pos, rs.getInt(type.getColumn()),
                                api);
                        guildTop.add(top1);
                        break;
                    case BATTLES:
                        GuildWB top2 = new GuildWB(pos, rs.getInt(type.getColumn()),
                                api);
                        guildTop.add(top2);
                        break;
                }
            }
            cachedTop.put("guild" + type.getColumn(), guildTop);
            return Void.TYPE;
        });
    }

    public enum guildType {
        LEVEL("level"),
        KILLS("kills"),
        BATTLES("winningBattles"),
        ;

        private final String s;

        guildType(String s) {
            this.s = s;
        }

        public String getColumn() {
            return s;
        }
    }
}
*/