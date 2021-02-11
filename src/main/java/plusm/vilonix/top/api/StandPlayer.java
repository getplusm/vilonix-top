package plusm.vilonix.top.api;

import plusm.vilonix.api.hologram.Hologram;
import plusm.vilonix.api.player.BukkitGamer;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class StandPlayer {
    public static Map<Integer, StandPlayer> cachedPlayers = new ConcurrentHashMap<>();
    private final TopManager manager;
    private final BukkitGamer gamer;
    private Top topType;
    private boolean newPlayer;

    public StandPlayer(final TopManager manager, final BukkitGamer gamer) {
        this.manager = manager;
        this.gamer = gamer;
        final Top selectedType = manager.getStandTopSql().getSelectedType(gamer);
        if (selectedType != null) {
            this.topType = selectedType;
            this.newPlayer = false;
        } else {
            this.topType = manager.getFirstTop();
            this.newPlayer = true;
        }
        this.show();
        cachedPlayers.put(gamer.getPlayerID(), this);
    }

    public void changeSelected() {
        final List<StandTop> stand = this.manager.getAllStands(this.topType);
        if (stand != null) {
            for (final StandTop standTop : stand) {
                standTop.removeTo(this.gamer, true);
            }
        }
        final Hologram hologramMiddle = this.topType.getHologramMiddle();
        if (hologramMiddle != null) {
            hologramMiddle.removeTo(this.gamer);
        }
        Top newTopType = this.manager.getFirstTop();
        final int newId = this.topType.getId() + 1;
        if (this.manager.getTops().size() > newId) {
            newTopType = this.manager.getTop(newId);
        }
        this.topType = newTopType;
        this.show();
        this.manager.getStandTopSql().changeSelectedType(this);
        this.newPlayer = false;
    }

    public void show() {
        final List<StandTop> stand = this.manager.getAllStands(this.topType);
        if (stand != null)
            for (final StandTop standTop : stand)
                standTop.showTo(this.gamer, true);
        final Hologram hologramMiddle = this.topType.getHologramMiddle();
        if (hologramMiddle != null)
            hologramMiddle.showTo(this.gamer);
    }

    public void hide() {
        final List<StandTop> stand = this.manager.getAllStands(this.topType);
        if (stand != null)
            for (final StandTop standTop : stand)
                standTop.removeTo(this.gamer, true);
        final Hologram hologramMiddle = this.topType.getHologramMiddle();
        if (hologramMiddle != null)
            hologramMiddle.removeTo(this.gamer);
    }

    public TopManager getManager() {
        return this.manager;
    }

    public BukkitGamer getGamer() {
        return this.gamer;
    }

    public Top getTopType() {
        return this.topType;
    }

    public boolean isNewPlayer() {
        return this.newPlayer;
    }
}
