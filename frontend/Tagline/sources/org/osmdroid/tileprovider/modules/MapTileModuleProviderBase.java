package org.osmdroid.tileprovider.modules;

import android.graphics.drawable.Drawable;
import android.util.Log;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ThreadFactory;
import org.osmdroid.api.IMapView;
import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.ExpirableBitmapDrawable;
import org.osmdroid.tileprovider.MapTile;
import org.osmdroid.tileprovider.MapTileRequestState;
import org.osmdroid.tileprovider.tilesource.ITileSource;

public abstract class MapTileModuleProviderBase {
    private final ExecutorService mExecutor;
    protected final LinkedHashMap<MapTile, MapTileRequestState> mPending;
    protected final Object mQueueLockObject;
    protected final HashMap<MapTile, MapTileRequestState> mWorking;

    public abstract int getMaximumZoomLevel();

    public abstract int getMinimumZoomLevel();

    /* access modifiers changed from: protected */
    public abstract String getName();

    /* access modifiers changed from: protected */
    public abstract String getThreadGroupName();

    public abstract TileLoader getTileLoader();

    public abstract boolean getUsesDataConnection();

    public abstract void setTileSource(ITileSource iTileSource);

    public MapTileModuleProviderBase(int i, int i2) {
        Object obj;
        ThreadFactory threadFactory;
        HashMap<MapTile, MapTileRequestState> hashMap;
        LinkedHashMap<MapTile, MapTileRequestState> linkedHashMap;
        int pThreadPoolSize = i;
        int pPendingQueueSize = i2;
        new Object();
        this.mQueueLockObject = obj;
        if (pPendingQueueSize < pThreadPoolSize) {
            int w = Log.w(IMapView.LOGTAG, "The pending queue size is smaller than the thread pool size. Automatically reducing the thread pool size.");
            pThreadPoolSize = pPendingQueueSize;
        }
        new ConfigurablePriorityThreadFactory(5, getThreadGroupName());
        this.mExecutor = Executors.newFixedThreadPool(pThreadPoolSize, threadFactory);
        new HashMap<>();
        this.mWorking = hashMap;
        final int i3 = pPendingQueueSize;
        new LinkedHashMap<MapTile, MapTileRequestState>(this, pPendingQueueSize + 2, 0.1f, true) {
            private static final long serialVersionUID = 6455337315681858866L;
            final /* synthetic */ MapTileModuleProviderBase this$0;

            {
                this.this$0 = this$0;
            }

            /* access modifiers changed from: protected */
            public boolean removeEldestEntry(Map.Entry<MapTile, MapTileRequestState> entry) {
                Map.Entry<MapTile, MapTileRequestState> entry2 = entry;
                if (size() > i3) {
                    MapTile result = null;
                    Iterator<MapTile> iterator = this.this$0.mPending.keySet().iterator();
                    while (result == null && iterator.hasNext()) {
                        MapTile tile = iterator.next();
                        if (!this.this$0.mWorking.containsKey(tile)) {
                            result = tile;
                        }
                    }
                    if (result != null) {
                        MapTileRequestState state = this.this$0.mPending.get(result);
                        this.this$0.removeTileFromQueues(result);
                        state.getCallback().mapTileRequestFailed(state);
                    }
                }
                return false;
            }
        };
        this.mPending = linkedHashMap;
    }

    public void loadMapTileAsync(MapTileRequestState mapTileRequestState) {
        StringBuilder sb;
        MapTileRequestState pState = mapTileRequestState;
        if (!this.mExecutor.isShutdown()) {
            Object obj = this.mQueueLockObject;
            Object obj2 = obj;
            synchronized (obj) {
                try {
                    if (Configuration.getInstance().isDebugTileProviders()) {
                        new StringBuilder();
                        int d = Log.d(IMapView.LOGTAG, sb.append("MapTileModuleProviderBase.loadMaptileAsync() on provider: ").append(getName()).append(" for tile: ").append(pState.getMapTile()).toString());
                        if (this.mPending.containsKey(pState.getMapTile())) {
                            int d2 = Log.d(IMapView.LOGTAG, "MapTileModuleProviderBase.loadMaptileAsync() tile already exists in request queue for modular provider. Moving to front of queue.");
                        } else {
                            int d3 = Log.d(IMapView.LOGTAG, "MapTileModuleProviderBase.loadMaptileAsync() adding tile to request queue for modular provider.");
                        }
                    }
                    Object put = this.mPending.put(pState.getMapTile(), pState);
                    try {
                        this.mExecutor.execute(getTileLoader());
                    } catch (RejectedExecutionException e) {
                        int w = Log.w(IMapView.LOGTAG, "RejectedExecutionException", e);
                    }
                } catch (Throwable th) {
                    Throwable th2 = th;
                    Object obj3 = obj2;
                    throw th2;
                }
            }
        }
    }

    /* access modifiers changed from: private */
    public void clearQueue() {
        Object obj = this.mQueueLockObject;
        Object obj2 = obj;
        synchronized (obj) {
            try {
                this.mPending.clear();
                this.mWorking.clear();
            } catch (Throwable th) {
                Throwable th2 = th;
                Object obj3 = obj2;
                throw th2;
            }
        }
    }

    public void detach() {
        clearQueue();
        this.mExecutor.shutdown();
    }

    /* access modifiers changed from: protected */
    public void removeTileFromQueues(MapTile mapTile) {
        StringBuilder sb;
        MapTile mapTile2 = mapTile;
        Object obj = this.mQueueLockObject;
        Object obj2 = obj;
        synchronized (obj) {
            try {
                if (Configuration.getInstance().isDebugTileProviders()) {
                    new StringBuilder();
                    int d = Log.d(IMapView.LOGTAG, sb.append("MapTileModuleProviderBase.removeTileFromQueues() on provider: ").append(getName()).append(" for tile: ").append(mapTile2).toString());
                }
                Object remove = this.mPending.remove(mapTile2);
                MapTileRequestState remove2 = this.mWorking.remove(mapTile2);
            } catch (Throwable th) {
                Throwable th2 = th;
                Object obj3 = obj2;
                throw th2;
            }
        }
    }

    public abstract class TileLoader implements Runnable {
        final /* synthetic */ MapTileModuleProviderBase this$0;

        public abstract Drawable loadTile(MapTile mapTile) throws CantContinueException;

        public TileLoader(MapTileModuleProviderBase this$02) {
            this.this$0 = this$02;
        }

        /* access modifiers changed from: protected */
        @Deprecated
        public Drawable loadTile(MapTileRequestState pState) throws CantContinueException {
            return loadTile(pState.getMapTile());
        }

        /* access modifiers changed from: protected */
        public void onTileLoaderInit() {
        }

        /* access modifiers changed from: protected */
        public void onTileLoaderShutdown() {
        }

        /* JADX INFO: finally extract failed */
        /* access modifiers changed from: protected */
        public MapTileRequestState nextTile() {
            StringBuilder sb;
            StringBuilder sb2;
            Object obj = this.this$0.mQueueLockObject;
            Object obj2 = obj;
            synchronized (obj) {
                MapTile result = null;
                try {
                    for (MapTile tile : this.this$0.mPending.keySet()) {
                        if (!this.this$0.mWorking.containsKey(tile)) {
                            if (Configuration.getInstance().isDebugTileProviders()) {
                                new StringBuilder();
                                int d = Log.d(IMapView.LOGTAG, sb2.append("TileLoader.nextTile() on provider: ").append(this.this$0.getName()).append(" found tile in working queue: ").append(tile).toString());
                            }
                            result = tile;
                        }
                    }
                    if (result != null) {
                        if (Configuration.getInstance().isDebugTileProviders()) {
                            new StringBuilder();
                            int d2 = Log.d(IMapView.LOGTAG, sb.append("TileLoader.nextTile() on provider: ").append(this.this$0.getName()).append(" adding tile to working queue: ").append(result).toString());
                        }
                        MapTileRequestState put = this.this$0.mWorking.put(result, this.this$0.mPending.get(result));
                    }
                    MapTileRequestState mapTileRequestState = result != null ? this.this$0.mPending.get(result) : null;
                    return mapTileRequestState;
                } catch (Throwable th) {
                    Throwable th2 = th;
                    Object obj3 = obj2;
                    throw th2;
                }
            }
        }

        /* access modifiers changed from: protected */
        public void tileLoaded(MapTileRequestState mapTileRequestState, Drawable drawable) {
            StringBuilder sb;
            MapTileRequestState pState = mapTileRequestState;
            Drawable pDrawable = drawable;
            if (Configuration.getInstance().isDebugTileProviders()) {
                new StringBuilder();
                int d = Log.d(IMapView.LOGTAG, sb.append("TileLoader.tileLoaded() on provider: ").append(this.this$0.getName()).append(" with tile: ").append(pState.getMapTile()).toString());
            }
            this.this$0.removeTileFromQueues(pState.getMapTile());
            ExpirableBitmapDrawable.setState(pDrawable, -1);
            pState.getCallback().mapTileRequestCompleted(pState, pDrawable);
        }

        /* access modifiers changed from: protected */
        public void tileLoadedExpired(MapTileRequestState mapTileRequestState, Drawable drawable) {
            StringBuilder sb;
            MapTileRequestState pState = mapTileRequestState;
            Drawable pDrawable = drawable;
            if (Configuration.getInstance().isDebugTileProviders()) {
                new StringBuilder();
                int d = Log.d(IMapView.LOGTAG, sb.append("TileLoader.tileLoadedExpired() on provider: ").append(this.this$0.getName()).append(" with tile: ").append(pState.getMapTile()).toString());
            }
            this.this$0.removeTileFromQueues(pState.getMapTile());
            ExpirableBitmapDrawable.setState(pDrawable, -2);
            pState.getCallback().mapTileRequestExpiredTile(pState, pDrawable);
        }

        /* access modifiers changed from: protected */
        public void tileLoadedScaled(MapTileRequestState mapTileRequestState, Drawable drawable) {
            StringBuilder sb;
            MapTileRequestState pState = mapTileRequestState;
            Drawable pDrawable = drawable;
            if (Configuration.getInstance().isDebugTileProviders()) {
                new StringBuilder();
                int d = Log.d(IMapView.LOGTAG, sb.append("TileLoader.tileLoadedScaled() on provider: ").append(this.this$0.getName()).append(" with tile: ").append(pState.getMapTile()).toString());
            }
            this.this$0.removeTileFromQueues(pState.getMapTile());
            ExpirableBitmapDrawable.setState(pDrawable, -3);
            pState.getCallback().mapTileRequestExpiredTile(pState, pDrawable);
        }

        /* access modifiers changed from: protected */
        public void tileLoadedFailed(MapTileRequestState mapTileRequestState) {
            StringBuilder sb;
            MapTileRequestState pState = mapTileRequestState;
            if (Configuration.getInstance().isDebugTileProviders()) {
                new StringBuilder();
                int d = Log.d(IMapView.LOGTAG, sb.append("TileLoader.tileLoadedFailed() on provider: ").append(this.this$0.getName()).append(" with tile: ").append(pState.getMapTile()).toString());
            }
            this.this$0.removeTileFromQueues(pState.getMapTile());
            pState.getCallback().mapTileRequestFailed(pState);
        }

        public final void run() {
            StringBuilder sb;
            StringBuilder sb2;
            StringBuilder sb3;
            onTileLoaderInit();
            while (true) {
                MapTileRequestState nextTile = nextTile();
                MapTileRequestState state = nextTile;
                if (nextTile != null) {
                    if (Configuration.getInstance().isDebugTileProviders()) {
                        new StringBuilder();
                        int d = Log.d(IMapView.LOGTAG, sb3.append("TileLoader.run() processing next tile: ").append(state.getMapTile()).append(", pending:").append(this.this$0.mPending.size()).append(", working:").append(this.this$0.mWorking.size()).toString());
                    }
                    Drawable result = null;
                    try {
                        result = loadTile(state.getMapTile());
                    } catch (CantContinueException e) {
                        new StringBuilder();
                        int i = Log.i(IMapView.LOGTAG, sb2.append("Tile loader can't continue: ").append(state.getMapTile()).toString(), e);
                        this.this$0.clearQueue();
                    } catch (Throwable th) {
                        new StringBuilder();
                        int i2 = Log.i(IMapView.LOGTAG, sb.append("Error downloading tile: ").append(state.getMapTile()).toString(), th);
                    }
                    if (result == null) {
                        tileLoadedFailed(state);
                    } else if (ExpirableBitmapDrawable.getState(result) == -2) {
                        tileLoadedExpired(state, result);
                    } else if (ExpirableBitmapDrawable.getState(result) == -3) {
                        tileLoadedScaled(state, result);
                    } else {
                        tileLoaded(state, result);
                    }
                } else {
                    onTileLoaderShutdown();
                    return;
                }
            }
        }
    }

    public class CantContinueException extends Exception {
        private static final long serialVersionUID = 146526524087765133L;
        final /* synthetic */ MapTileModuleProviderBase this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        public CantContinueException(MapTileModuleProviderBase this$02, String pDetailMessage) {
            super(pDetailMessage);
            this.this$0 = this$02;
        }

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        public CantContinueException(MapTileModuleProviderBase this$02, Throwable pThrowable) {
            super(pThrowable);
            this.this$0 = this$02;
        }
    }
}
