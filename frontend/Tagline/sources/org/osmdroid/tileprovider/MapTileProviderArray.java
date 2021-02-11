package org.osmdroid.tileprovider;

import android.graphics.drawable.Drawable;
import android.util.Log;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import microsoft.mappoint.TileSystem;
import org.osmdroid.api.IMapView;
import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.modules.IFilesystemCache;
import org.osmdroid.tileprovider.modules.MapTileModuleProviderBase;
import org.osmdroid.tileprovider.tilesource.ITileSource;

public class MapTileProviderArray extends MapTileProviderBase {
    protected IRegisterReceiver mRegisterReceiver;
    protected final List<MapTileModuleProviderBase> mTileProviderList;
    protected final HashMap<MapTile, MapTileRequestState> mWorking;

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    protected MapTileProviderArray(ITileSource pTileSource, IRegisterReceiver pRegisterReceiver) {
        this(pTileSource, pRegisterReceiver, new MapTileModuleProviderBase[0]);
    }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public MapTileProviderArray(ITileSource pTileSource, IRegisterReceiver aRegisterReceiver, MapTileModuleProviderBase[] pTileProviderArray) {
        super(pTileSource);
        HashMap<MapTile, MapTileRequestState> hashMap;
        List<MapTileModuleProviderBase> list;
        this.mRegisterReceiver = null;
        new HashMap<>();
        this.mWorking = hashMap;
        this.mRegisterReceiver = aRegisterReceiver;
        new ArrayList();
        this.mTileProviderList = list;
        boolean addAll = Collections.addAll(this.mTileProviderList, pTileProviderArray);
    }

    /* JADX INFO: finally extract failed */
    public void detach() {
        List<MapTileModuleProviderBase> list = this.mTileProviderList;
        List<MapTileModuleProviderBase> list2 = list;
        synchronized (list) {
            try {
                for (MapTileModuleProviderBase tileProvider : this.mTileProviderList) {
                    tileProvider.detach();
                }
                this.mTileCache.clear();
                HashMap<MapTile, MapTileRequestState> hashMap = this.mWorking;
                HashMap<MapTile, MapTileRequestState> hashMap2 = hashMap;
                synchronized (hashMap) {
                    try {
                        this.mWorking.clear();
                        clearTileCache();
                        if (this.mRegisterReceiver != null) {
                            this.mRegisterReceiver.destroy();
                            this.mRegisterReceiver = null;
                        }
                        super.detach();
                    } catch (Throwable th) {
                        while (true) {
                            Throwable th2 = th;
                            HashMap<MapTile, MapTileRequestState> hashMap3 = hashMap2;
                            throw th2;
                        }
                    }
                }
            } catch (Throwable th3) {
                while (true) {
                    Throwable th4 = th3;
                    List<MapTileModuleProviderBase> list3 = list2;
                    throw th4;
                }
            }
        }
    }

    /* access modifiers changed from: protected */
    public boolean isDowngradedMode() {
        return false;
    }

    /* JADX INFO: finally extract failed */
    public Drawable getMapTile(MapTile mapTile) {
        MapTileRequestState mapTileRequestState;
        StringBuilder sb;
        MapTile pTile = mapTile;
        Drawable tile = this.mTileCache.getMapTile(pTile);
        if (tile != null) {
            if (ExpirableBitmapDrawable.getState(tile) == -1) {
                return tile;
            }
            if (isDowngradedMode()) {
                return tile;
            }
        }
        if (this.mWorking.containsKey(pTile)) {
            return tile;
        }
        if (Configuration.getInstance().isDebugTileProviders()) {
            new StringBuilder();
            int d = Log.d(IMapView.LOGTAG, sb.append("MapTileProviderArray.getMapTile() requested but not in cache, trying from async providers: ").append(pTile).toString());
        }
        new MapTileRequestState(pTile, this.mTileProviderList, (IMapTileProviderCallback) this);
        MapTileRequestState state = mapTileRequestState;
        HashMap<MapTile, MapTileRequestState> hashMap = this.mWorking;
        HashMap<MapTile, MapTileRequestState> hashMap2 = hashMap;
        synchronized (hashMap) {
            try {
                if (this.mWorking.containsKey(pTile)) {
                    Drawable drawable = tile;
                    return drawable;
                }
                MapTileRequestState put = this.mWorking.put(pTile, state);
                MapTileModuleProviderBase provider = findNextAppropriateProvider(state);
                if (provider != null) {
                    provider.loadMapTileAsync(state);
                } else {
                    mapTileRequestFailed(state);
                }
                return tile;
            } catch (Throwable th) {
                while (true) {
                    Throwable th2 = th;
                    HashMap<MapTile, MapTileRequestState> hashMap3 = hashMap2;
                    throw th2;
                }
            }
        }
    }

    /* JADX INFO: finally extract failed */
    public void mapTileRequestCompleted(MapTileRequestState mapTileRequestState, Drawable drawable) {
        MapTileRequestState aState = mapTileRequestState;
        Drawable aDrawable = drawable;
        HashMap<MapTile, MapTileRequestState> hashMap = this.mWorking;
        HashMap<MapTile, MapTileRequestState> hashMap2 = hashMap;
        synchronized (hashMap) {
            try {
                MapTileRequestState remove = this.mWorking.remove(aState.getMapTile());
                super.mapTileRequestCompleted(aState, aDrawable);
            } catch (Throwable th) {
                while (true) {
                    Throwable th2 = th;
                    HashMap<MapTile, MapTileRequestState> hashMap3 = hashMap2;
                    throw th2;
                }
            }
        }
    }

    /* JADX INFO: finally extract failed */
    public void mapTileRequestFailed(MapTileRequestState mapTileRequestState) {
        MapTileRequestState aState = mapTileRequestState;
        MapTileModuleProviderBase nextProvider = findNextAppropriateProvider(aState);
        if (nextProvider != null) {
            nextProvider.loadMapTileAsync(aState);
            return;
        }
        HashMap<MapTile, MapTileRequestState> hashMap = this.mWorking;
        HashMap<MapTile, MapTileRequestState> hashMap2 = hashMap;
        synchronized (hashMap) {
            try {
                MapTileRequestState remove = this.mWorking.remove(aState.getMapTile());
                super.mapTileRequestFailed(aState);
            } catch (Throwable th) {
                while (true) {
                    Throwable th2 = th;
                    HashMap<MapTile, MapTileRequestState> hashMap3 = hashMap2;
                    throw th2;
                }
            }
        }
    }

    public void mapTileRequestExpiredTile(MapTileRequestState mapTileRequestState, Drawable aDrawable) {
        MapTileRequestState aState = mapTileRequestState;
        super.mapTileRequestExpiredTile(aState, aDrawable);
        MapTileModuleProviderBase nextProvider = findNextAppropriateProvider(aState);
        if (nextProvider != null) {
            nextProvider.loadMapTileAsync(aState);
            return;
        }
        HashMap<MapTile, MapTileRequestState> hashMap = this.mWorking;
        HashMap<MapTile, MapTileRequestState> hashMap2 = hashMap;
        synchronized (hashMap) {
            try {
                MapTileRequestState remove = this.mWorking.remove(aState.getMapTile());
            } catch (Throwable th) {
                Throwable th2 = th;
                HashMap<MapTile, MapTileRequestState> hashMap3 = hashMap2;
                throw th2;
            }
        }
    }

    public IFilesystemCache getTileWriter() {
        return null;
    }

    public long getQueueSize() {
        if (this.mWorking != null) {
            return (long) this.mWorking.size();
        }
        return -1;
    }

    /* access modifiers changed from: protected */
    public MapTileModuleProviderBase findNextAppropriateProvider(MapTileRequestState mapTileRequestState) {
        MapTileModuleProviderBase provider;
        MapTileRequestState aState = mapTileRequestState;
        boolean providerDoesntExist = false;
        boolean providerCantGetDataConnection = false;
        boolean providerCantServiceZoomlevel = false;
        while (true) {
            provider = aState.getNextProvider();
            if (provider != null) {
                providerDoesntExist = !getProviderExists(provider);
                providerCantGetDataConnection = !useDataConnection() && provider.getUsesDataConnection();
                int zoomLevel = aState.getMapTile().getZoomLevel();
                providerCantServiceZoomlevel = zoomLevel > provider.getMaximumZoomLevel() || zoomLevel < provider.getMinimumZoomLevel();
            }
            if (provider == null || (!providerDoesntExist && !providerCantGetDataConnection && !providerCantServiceZoomlevel)) {
            }
        }
        return provider;
    }

    public boolean getProviderExists(MapTileModuleProviderBase mapTileModuleProviderBase) {
        MapTileModuleProviderBase provider = mapTileModuleProviderBase;
        List<MapTileModuleProviderBase> list = this.mTileProviderList;
        List<MapTileModuleProviderBase> list2 = list;
        synchronized (list) {
            try {
                boolean contains = this.mTileProviderList.contains(provider);
                return contains;
            } catch (Throwable th) {
                Throwable th2 = th;
                List<MapTileModuleProviderBase> list3 = list2;
                throw th2;
            }
        }
    }

    /* JADX INFO: finally extract failed */
    public int getMinimumZoomLevel() {
        int result = TileSystem.getMaximumZoomLevel();
        List<MapTileModuleProviderBase> list = this.mTileProviderList;
        List<MapTileModuleProviderBase> list2 = list;
        synchronized (list) {
            try {
                for (MapTileModuleProviderBase tileProvider : this.mTileProviderList) {
                    if (tileProvider.getMinimumZoomLevel() < result) {
                        result = tileProvider.getMinimumZoomLevel();
                    }
                }
                return result;
            } catch (Throwable th) {
                Throwable th2 = th;
                List<MapTileModuleProviderBase> list3 = list2;
                throw th2;
            }
        }
    }

    /* JADX INFO: finally extract failed */
    public int getMaximumZoomLevel() {
        int result = 0;
        List<MapTileModuleProviderBase> list = this.mTileProviderList;
        List<MapTileModuleProviderBase> list2 = list;
        synchronized (list) {
            try {
                for (MapTileModuleProviderBase tileProvider : this.mTileProviderList) {
                    if (tileProvider.getMaximumZoomLevel() > result) {
                        result = tileProvider.getMaximumZoomLevel();
                    }
                }
                return result;
            } catch (Throwable th) {
                Throwable th2 = th;
                List<MapTileModuleProviderBase> list3 = list2;
                throw th2;
            }
        }
    }

    /* JADX INFO: finally extract failed */
    public void setTileSource(ITileSource iTileSource) {
        ITileSource aTileSource = iTileSource;
        super.setTileSource(aTileSource);
        List<MapTileModuleProviderBase> list = this.mTileProviderList;
        List<MapTileModuleProviderBase> list2 = list;
        synchronized (list) {
            try {
                for (MapTileModuleProviderBase tileProvider : this.mTileProviderList) {
                    tileProvider.setTileSource(aTileSource);
                    clearTileCache();
                }
            } catch (Throwable th) {
                Throwable th2 = th;
                List<MapTileModuleProviderBase> list3 = list2;
                throw th2;
            }
        }
    }
}
