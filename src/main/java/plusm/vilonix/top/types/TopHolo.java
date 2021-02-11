package plusm.vilonix.top.types;

import org.bukkit.Location;
import plusm.vilonix.api.util.LocationUtil;
import plusm.vilonix.gapi.utils.bukkit.BukkitUtil;
import plusm.vilonix.top.api.HologramAnimation;
import plusm.vilonix.top.api.Top;
import plusm.vilonix.top.api.TopManager;

import java.util.List;

public class TopHolo extends Top {

    public TopHolo(List<String> text, TopManager standTopManager, Location loc) {
        super(standTopManager, loc);
        BukkitUtil.runTaskAsync(()-> LocationUtil.loadChunk(loc));
        holograms = HOLOGRAM_API.createHologram(location);
        holograms.addTextLine(text);
        holograms.addAnimationLine(1, new HologramAnimation(standTopManager, 20));
        holograms.addTextLine("І7Іoўелкните, чтобы сменить топ");
    }
}