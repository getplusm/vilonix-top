package plusm.vilonix.top.api;

import org.bukkit.Location;
import plusm.vilonix.api.VilonixNetwork;
import plusm.vilonix.api.hologram.Hologram;
import plusm.vilonix.api.hologram.HologramAPI;

import java.util.List;

public abstract class Top {
    protected static final HologramAPI HOLOGRAM_API = VilonixNetwork.getHologramAPI();
    protected final TopManager standTopManager;
    protected final Location location;
    protected Hologram holograms;

    public final Hologram getHologramMiddle() {
        return holograms;
    }

    public final List<StandTop> getStands() {
        return this.standTopManager.getAllStands(this);
    }

    public final Location getLocation() {
        return this.location.clone();
    }

    public final int getId() {
        int id = 0;
        for (final Top topType : this.standTopManager.getTops()) {
            if (topType == this) {
                break;
            }
            ++id;
        }
        return id;
    }

    protected Top(final TopManager standTopManager, final Location location) {
        this.standTopManager = standTopManager;
        this.location = location;
    }
}
