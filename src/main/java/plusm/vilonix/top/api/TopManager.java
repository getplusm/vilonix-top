package plusm.vilonix.top.api;

import lombok.NonNull;
import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class TopManager {
    private final JavaPlugin javaPlugin;
    private final StandTopSql standTopSql;
    private final StandTopListener standTopListener;
    private final StandPlayerStorage standPlayerStorage;
    private final List<Top> tops = new ArrayList<>();
    private final Map<Top, List<StandTop>> allTops = new ConcurrentHashMap<>();

    public TopManager(@NonNull final JavaPlugin javaPlugin, final String table) {
        if (javaPlugin == null) {
            throw new NullPointerException("javaPlugin is marked @NonNull but is null");
        }
        this.javaPlugin = javaPlugin;
        this.standTopSql = new StandTopSql(this, table);
        this.standPlayerStorage = new StandPlayerStorage();
        this.standTopListener = new StandTopListener(this);
    }

    public List<Top> getTops() {
        return Collections.unmodifiableList(this.tops);
    }

    public Map<Top, List<StandTop>> getAllTops() {
        return Collections.unmodifiableMap(this.allTops);
    }

    @Nullable
    public Top getTop(final int type) {
        if (this.tops.size() <= type) {
            return null;
        }
        return this.tops.get(type);
    }

    @Nullable
    public Top getFirstTop() {
        if (this.tops.isEmpty()) {
            return null;
        }
        return this.getTop(0);
    }

    public int size() {
        return this.allTops.size();
    }

    public void createTop(final Top topType, final List<Location> locations) {
        if (this.tops.contains(topType)) {
            return;
        }
        final List<StandTop> standTops = new ArrayList<>();
        int pos = 1;
        for (final Location location : locations) {
            standTops.add(new StandTop(topType, location, pos++));
        }
        this.tops.add(topType);
        this.allTops.put(topType, standTops);
    }

    public void updateStandData(final List<StandTopData> standTopData) {
        for (final StandTopData data : standTopData) {
            if (!this.tops.contains(data.getTop())) {
                continue;
            }
            final StandTop standTop = this.getStandByPosition(data.getTop(), data.getPosition());
            if (standTop == null) {
                continue;
            }
            standTop.setStandTopData(data);
        }
    }

    @Nullable
    public StandTop getStandByPosition(final Top top, final int position) {
        final List<StandTop> allStands = this.getAllStands(top);
        if (allStands.size() < position) {
            return null;
        }
        return allStands.get(position - 1);
    }

    public List<StandTop> getAllStands(final Top top) {
        final List<StandTop> standTops = this.allTops.get(top);
        if (standTops != null) {
            return Collections.unmodifiableList(standTops);
        }
        return Collections.emptyList();
    }

    public List<StandTop> getAllStands() {
        final List<StandTop> standTops = new ArrayList<>();
        this.allTops.values().forEach(standTops::addAll);
        return standTops;
    }

    public JavaPlugin getJavaPlugin() {
        return this.javaPlugin;
    }

    public StandTopSql getStandTopSql() {
        return this.standTopSql;
    }

    public StandTopListener getStandTopListener() {
        return this.standTopListener;
    }

    public StandPlayerStorage getStandPlayerStorage() {
        return this.standPlayerStorage;
    }
}