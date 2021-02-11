package plusm.vilonix.top.types;

import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;
import plusm.vilonix.api.player.IBaseGamer;
import plusm.vilonix.api.util.Head;
import plusm.vilonix.api.util.StringUtil;
import plusm.vilonix.top.api.StandTopData;
import plusm.vilonix.top.api.Top;
import plusm.vilonix.top.config.TopConfig;
import plusm.vilonix.top.data.DataLoader;

import java.util.List;

public class TopData implements StandTopData {
    private final IBaseGamer gamer;
    private final int pos;
    private final int value;
    private final String lastString;
    private final List<String> correctly;
    private final String dataName;

    public TopData(int pos, int value, IBaseGamer gamer, String lastString, List<String> correctly, String dataName) {
        this.pos = pos;
        this.value = value;
        this.gamer = gamer;
        this.lastString = lastString;
        this.correctly = correctly;
        this.dataName = dataName;
    }

    @Override
    public String getDisplayName() {
        return gamer.getDisplayName();
    }

    @Override
    public IBaseGamer getGamer() {
        return this.gamer;
    }

    @Override
    public ItemStack getHead() {
        return Head.getHeadByPlayerName(gamer.getName());
    }

    @Override
    public String getLastString() {
        String newText = ChatColor.translateAlternateColorCodes('&', lastString.replace("%value%",
                StringUtil.getNumberFormat(value)));
        if (!correctly.isEmpty())
            newText = newText.replace("%correct%", StringUtil.getCorrectWord(value, this.correctly));
        return newText;
    }

    @Override
    public Top getTop() {
        return DataLoader.cachedTop.get(dataName);
    }

    @Override
    public int getPlayerID() {
        return this.gamer.getPlayerID();
    }

    @Override
    public int getPosition() {
        return pos;
    }
}