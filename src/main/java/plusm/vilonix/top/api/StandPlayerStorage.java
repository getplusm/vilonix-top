package plusm.vilonix.top.api;

import plusm.vilonix.api.player.BukkitGamer;

import javax.annotation.Nullable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class StandPlayerStorage {
    private final Map<String, StandPlayer> players = new ConcurrentHashMap<>();

    public void addPlayer(final StandPlayer standPlayer) {
        this.players.put(standPlayer.getGamer().getName().toLowerCase(), standPlayer);
    }

    void removePlayer(final String name) {
        this.players.remove(name);
    }

    @Nullable
    public StandPlayer getPlayer(final BukkitGamer gamer) {
        return this.players.get(gamer.getName().toLowerCase());
    }
}
