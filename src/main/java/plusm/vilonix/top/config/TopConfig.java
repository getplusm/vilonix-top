package plusm.vilonix.top.config;

import plusm.vilonix.api.VilonixNetwork;
import plusm.vilonix.api.player.BukkitGamer;
import plusm.vilonix.api.util.LocationUtil;
import plusm.vilonix.top.Main;
import plusm.vilonix.top.api.StandPlayer;
import plusm.vilonix.top.api.StandTopData;
import plusm.vilonix.top.api.TopManager;
import plusm.vilonix.top.data.DataLoader;

import java.util.List;

public class TopConfig extends MainConfig {

    private TopManager topManager;

    public TopConfig(Main main) {
        super(main, "top");
    }

    public TopManager getTopManager() {
        return this.topManager;
    }

    @Override
    public void load() {
        try {
            this.topManager = new TopManager(Main.getInstance(), "selected_top");
            System.out.println("І6»дет загрузка топов. ќжидайте");
            DataLoader.init(topManager, getConfig());
            DataLoader.cachedTop.values().forEach(f -> this.topManager.createTop(f, LocationUtil.getLoc(this.getConfig(), "LOCATIONS")));
            for (List<StandTopData> list : DataLoader.cachedStand.values())
                topManager.updateStandData(list);
            for (BukkitGamer gamer : VilonixNetwork.getGamerManager().getGamers().values()) {
                StandPlayer sp = new StandPlayer(topManager, gamer);
                topManager.getStandPlayerStorage().addPlayer(sp);
            }
            System.out.println("Іaзагружено Іc" + DataLoader.cachedTop.size() + "Іa топов");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void init() {
    }
}
