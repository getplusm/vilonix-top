package plusm.vilonix.top.api;

import org.bukkit.inventory.ItemStack;
import plusm.vilonix.api.player.IBaseGamer;

public interface StandTopData {
    Top getTop();

    int getPlayerID();

    int getPosition();

    ItemStack getHead();

    String getLastString();

    String getDisplayName();

    IBaseGamer getGamer();
}
