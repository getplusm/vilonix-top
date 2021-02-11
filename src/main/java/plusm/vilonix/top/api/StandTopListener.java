package plusm.vilonix.top.api;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.plugin.java.JavaPlugin;
import plusm.vilonix.api.event.gamer.GamerInteractHologramEvent;
import plusm.vilonix.api.event.gamer.async.AsyncGamerJoinEvent;
import plusm.vilonix.api.event.gamer.async.AsyncGamerQuitEvent;
import plusm.vilonix.api.hologram.Hologram;
import plusm.vilonix.api.player.BukkitGamer;
import plusm.vilonix.api.sound.SoundType;
import plusm.vilonix.gapi.listeners.DListener;

public final class StandTopListener extends DListener<JavaPlugin> {
    private final TopManager manager;

    public StandTopListener(final TopManager standTopManager) {
        super(standTopManager.getJavaPlugin());
        this.manager = standTopManager;
    }

    @EventHandler
    public void onJoinAsync(final AsyncGamerJoinEvent e) {
        if (this.manager.size() < 1) {
            return;
        }
        this.manager.getStandPlayerStorage().addPlayer(new StandPlayer(this.manager, e.getGamer()));
    }

    @EventHandler
    public void onRemove(final AsyncGamerQuitEvent e) {
        this.manager.getStandPlayerStorage().removePlayer(e.getGamer().getName());
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onClick(final GamerInteractHologramEvent e) {
        final Hologram hologram = e.getHologram();
        final BukkitGamer gamer = e.getGamer();
        if (this.manager.size() <= 1) {
            return;
        }
        final StandPlayer standPlayer = this.manager.getStandPlayerStorage().getPlayer(gamer);
        if (standPlayer == null) {
            return;
        }
        final Top topType = standPlayer.getTopType();
        final Hologram mainHologram = topType.getHologramMiddle();
        if (mainHologram == null || mainHologram != hologram) {
            return;
        }
        standPlayer.changeSelected();
        gamer.playSound(SoundType.CLICK);
    }
}