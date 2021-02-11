package plusm.vilonix.top.api;

import plusm.vilonix.api.util.TimeUtil;
import plusm.vilonix.top.Main;
import plusm.vilonix.top.config.TopConfig;
import plusm.vilonix.top.data.DataLoader;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.function.Supplier;

public final class HologramAnimation implements Supplier<String> {
    private final int timeMinutes;
    private LocalDateTime nextTime;
    private TopManager topManager;

    public HologramAnimation(TopManager topManager, final int timeMinutes) {
        this.timeMinutes = timeMinutes;
        this.nextTime = LocalDateTime.now().plusMinutes(timeMinutes);
        this.topManager = topManager;
    }

    @Override
    public String get() {
        final Duration between = Duration.between(LocalDateTime.now(), this.nextTime);
        if (between.isNegative() || between.isZero()) {
            this.nextTime = LocalDateTime.now().plusMinutes(this.timeMinutes);
            for (List<StandTopData> list : DataLoader.cachedStand.values())
                Main.getInstance().getConfig(TopConfig.class).getTopManager().updateStandData(list);
            DataLoader.init(topManager, Main.getInstance().getConfig(TopConfig.class).getConfig());
            return "§cОбновлено";
        }
        final int millis = (int) between.toMillis();
        return "§cДо обновления: §a" + TimeUtil.leftTime(millis);
    }
}
