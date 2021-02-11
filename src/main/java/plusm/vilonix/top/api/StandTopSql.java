package plusm.vilonix.top.api;

import plusm.vilonix.api.player.BukkitGamer;
import plusm.vilonix.api.sql.GlobalLoader;
import plusm.vilonix.api.sql.api.query.MysqlQuery;
import plusm.vilonix.api.sql.api.query.QuerySymbol;
import plusm.vilonix.api.sql.api.table.ColumnType;
import plusm.vilonix.api.sql.api.table.TableColumn;
import plusm.vilonix.api.sql.api.table.TableConstructor;

import javax.annotation.Nullable;

public final class StandTopSql {
    private final TopManager standTopManager;
    private final String table;

    public StandTopSql(final TopManager standTopManager, final String table) {
        this.standTopManager = standTopManager;
        this.table = table;
        new TableConstructor(table,
                new TableColumn("id", ColumnType.INT_11).primaryKey(true).autoIncrement(true).unigue(true),
                new TableColumn("playerID", ColumnType.INT_11).unigue(true).primaryKey(true),
                new TableColumn("type", ColumnType.INT_11)
        ).create(GlobalLoader.getMysqlDatabase());
    }

    @Nullable
    public Top getSelectedType(final BukkitGamer gamer) {
        if (gamer == null) {
            return null;
        }
        final int playerID = gamer.getPlayerID();
        return GlobalLoader.getMysqlDatabase().executeQuery(MysqlQuery.selectFrom(this.table)
                .where("playerID", QuerySymbol.EQUALLY, playerID).limit(), rs -> {
            if (rs.next()) {
                return this.standTopManager.getTop(rs.getInt("type"));
            }
            return null;
        });
    }

    public void changeSelectedType(final StandPlayer standPlayer) {
        final BukkitGamer gamer = standPlayer.getGamer();
        if (gamer == null) {
            return;
        }
        final int playerID = gamer.getPlayerID();
        final int topTypeID = standPlayer.getTopType().getId();
        if (standPlayer.isNewPlayer()) {
            GlobalLoader.getMysqlDatabase().execute(MysqlQuery.insertTo(this.table).set("playerID", playerID).set("type", topTypeID));
        } else {
            GlobalLoader.getMysqlDatabase().execute(MysqlQuery.update(this.table).where("playerID", QuerySymbol.EQUALLY, playerID).set("type", topTypeID));
        }
    }
}
