package org.osmdroid.tileprovider;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.osmdroid.tileprovider.modules.MapTileModuleProviderBase;

public class MapTileRequestState {
    private int index;
    private final IMapTileProviderCallback mCallback;
    private MapTileModuleProviderBase mCurrentProvider;
    private final MapTile mMapTile;
    private final List<MapTileModuleProviderBase> mProviderQueue;

    @Deprecated
    public MapTileRequestState(MapTile mapTile, MapTileModuleProviderBase[] providers, IMapTileProviderCallback callback) {
        List<MapTileModuleProviderBase> list;
        new ArrayList();
        this.mProviderQueue = list;
        boolean addAll = Collections.addAll(this.mProviderQueue, providers);
        this.mMapTile = mapTile;
        this.mCallback = callback;
    }

    public MapTileRequestState(MapTile mapTile, List<MapTileModuleProviderBase> providers, IMapTileProviderCallback callback) {
        this.mProviderQueue = providers;
        this.mMapTile = mapTile;
        this.mCallback = callback;
    }

    public MapTile getMapTile() {
        return this.mMapTile;
    }

    public IMapTileProviderCallback getCallback() {
        return this.mCallback;
    }

    public boolean isEmpty() {
        return this.mProviderQueue == null || this.index >= this.mProviderQueue.size();
    }

    public MapTileModuleProviderBase getNextProvider() {
        MapTileModuleProviderBase mapTileModuleProviderBase;
        if (isEmpty()) {
            mapTileModuleProviderBase = null;
        } else {
            List<MapTileModuleProviderBase> list = this.mProviderQueue;
            int i = this.index;
            int i2 = i + 1;
            this.index = i2;
            mapTileModuleProviderBase = list.get(i);
        }
        this.mCurrentProvider = mapTileModuleProviderBase;
        return this.mCurrentProvider;
    }

    public MapTileModuleProviderBase getCurrentProvider() {
        return this.mCurrentProvider;
    }
}
