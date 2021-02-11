package org.osmdroid.tileprovider.modules;

import android.graphics.drawable.Drawable;
import android.util.Log;
import java.io.Closeable;
import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicReference;
import microsoft.mappoint.TileSystem;
import org.osmdroid.api.IMapView;
import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.IRegisterReceiver;
import org.osmdroid.tileprovider.MapTile;
import org.osmdroid.tileprovider.modules.MapTileModuleProviderBase;
import org.osmdroid.tileprovider.tilesource.ITileSource;
import org.osmdroid.tileprovider.util.StreamUtils;

public class MapTileFileArchiveProvider extends MapTileFileStorageProviderBase {
    private final ArrayList<IArchiveFile> mArchiveFiles;
    private final boolean mSpecificArchivesProvided;
    /* access modifiers changed from: private */
    public final AtomicReference<ITileSource> mTileSource;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public MapTileFileArchiveProvider(IRegisterReceiver pRegisterReceiver, ITileSource pTileSource, IArchiveFile[] iArchiveFileArr) {
        super(pRegisterReceiver, Configuration.getInstance().getTileFileSystemThreads(), Configuration.getInstance().getTileFileSystemMaxQueueSize());
        ArrayList<IArchiveFile> arrayList;
        AtomicReference<ITileSource> atomicReference;
        IArchiveFile[] pArchives = iArchiveFileArr;
        new ArrayList<>();
        this.mArchiveFiles = arrayList;
        new AtomicReference<>();
        this.mTileSource = atomicReference;
        setTileSource(pTileSource);
        if (pArchives == null) {
            this.mSpecificArchivesProvided = false;
            findArchiveFiles();
            return;
        }
        this.mSpecificArchivesProvided = true;
        for (int i = pArchives.length - 1; i >= 0; i--) {
            boolean add = this.mArchiveFiles.add(pArchives[i]);
        }
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public MapTileFileArchiveProvider(IRegisterReceiver pRegisterReceiver, ITileSource pTileSource) {
        this(pRegisterReceiver, pTileSource, (IArchiveFile[]) null);
    }

    public boolean getUsesDataConnection() {
        return false;
    }

    /* access modifiers changed from: protected */
    public String getName() {
        return "File Archive Provider";
    }

    /* access modifiers changed from: protected */
    public String getThreadGroupName() {
        return "filearchive";
    }

    public TileLoader getTileLoader() {
        TileLoader tileLoader;
        new TileLoader(this);
        return tileLoader;
    }

    public int getMinimumZoomLevel() {
        ITileSource tileSource = this.mTileSource.get();
        return tileSource != null ? tileSource.getMinimumZoomLevel() : 0;
    }

    public int getMaximumZoomLevel() {
        int maximumZoomLevel;
        ITileSource tileSource = this.mTileSource.get();
        if (tileSource != null) {
            maximumZoomLevel = tileSource.getMaximumZoomLevel();
        } else {
            maximumZoomLevel = TileSystem.getMaximumZoomLevel();
        }
        return maximumZoomLevel;
    }

    /* access modifiers changed from: protected */
    public void onMediaMounted() {
        if (!this.mSpecificArchivesProvided) {
            findArchiveFiles();
        }
    }

    /* access modifiers changed from: protected */
    public void onMediaUnmounted() {
        if (!this.mSpecificArchivesProvided) {
            findArchiveFiles();
        }
    }

    public void setTileSource(ITileSource pTileSource) {
        this.mTileSource.set(pTileSource);
    }

    public void detach() {
        while (!this.mArchiveFiles.isEmpty()) {
            if (this.mArchiveFiles.get(0) != null) {
                this.mArchiveFiles.get(0).close();
            }
            IArchiveFile remove = this.mArchiveFiles.remove(0);
        }
        super.detach();
    }

    private void findArchiveFiles() {
        File[] files;
        this.mArchiveFiles.clear();
        if (isSdCardAvailable() && (files = Configuration.getInstance().getOsmdroidBasePath().listFiles()) != null) {
            File[] fileArr = files;
            int length = fileArr.length;
            for (int i = 0; i < length; i++) {
                IArchiveFile archiveFile = ArchiveFileFactory.getArchiveFile(fileArr[i]);
                if (archiveFile != null) {
                    boolean add = this.mArchiveFiles.add(archiveFile);
                }
            }
        }
    }

    /* access modifiers changed from: private */
    public synchronized InputStream getInputStream(MapTile mapTile, ITileSource iTileSource) {
        InputStream inputStream;
        InputStream in;
        StringBuilder sb;
        MapTile pTile = mapTile;
        ITileSource tileSource = iTileSource;
        synchronized (this) {
            Iterator<IArchiveFile> it = this.mArchiveFiles.iterator();
            while (true) {
                if (!it.hasNext()) {
                    inputStream = null;
                    break;
                }
                IArchiveFile archiveFile = it.next();
                if (archiveFile != null && (in = archiveFile.getInputStream(tileSource, pTile)) != null) {
                    if (Configuration.getInstance().isDebugMode()) {
                        new StringBuilder();
                        int d = Log.d(IMapView.LOGTAG, sb.append("Found tile ").append(pTile).append(" in ").append(archiveFile).toString());
                    }
                    inputStream = in;
                }
            }
        }
        return inputStream;
    }

    protected class TileLoader extends MapTileModuleProviderBase.TileLoader {
        final /* synthetic */ MapTileFileArchiveProvider this$0;

        /* JADX WARNING: Illegal instructions before constructor call */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        protected TileLoader(org.osmdroid.tileprovider.modules.MapTileFileArchiveProvider r5) {
            /*
                r4 = this;
                r0 = r4
                r1 = r5
                r2 = r0
                r3 = r1
                r2.this$0 = r3
                r2 = r0
                r3 = r1
                r2.<init>(r3)
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: org.osmdroid.tileprovider.modules.MapTileFileArchiveProvider.TileLoader.<init>(org.osmdroid.tileprovider.modules.MapTileFileArchiveProvider):void");
        }

        public Drawable loadTile(MapTile mapTile) {
            StringBuilder sb;
            StringBuilder sb2;
            StringBuilder sb3;
            MapTile pTile = mapTile;
            ITileSource tileSource = (ITileSource) this.this$0.mTileSource.get();
            if (tileSource == null) {
                return null;
            }
            if (!MapTileFileStorageProviderBase.isSdCardAvailable()) {
                if (Configuration.getInstance().isDebugMode()) {
                    new StringBuilder();
                    int d = Log.d(IMapView.LOGTAG, sb3.append("No sdcard - do nothing for tile: ").append(pTile).toString());
                }
                return null;
            }
            try {
                if (Configuration.getInstance().isDebugMode()) {
                    new StringBuilder();
                    int d2 = Log.d(IMapView.LOGTAG, sb2.append("Archives - Tile doesn't exist: ").append(pTile).toString());
                }
                InputStream inputStream = this.this$0.getInputStream(pTile, tileSource);
                if (inputStream != null) {
                    if (Configuration.getInstance().isDebugMode()) {
                        new StringBuilder();
                        int d3 = Log.d(IMapView.LOGTAG, sb.append("Use tile from archive: ").append(pTile).toString());
                    }
                    Drawable drawable = tileSource.getDrawable(inputStream);
                    if (inputStream != null) {
                        StreamUtils.closeStream(inputStream);
                    }
                    return drawable;
                }
                if (inputStream != null) {
                    StreamUtils.closeStream(inputStream);
                }
                return null;
            } catch (Throwable th) {
                Throwable th2 = th;
                if (0 != 0) {
                    StreamUtils.closeStream((Closeable) null);
                }
                throw th2;
            }
        }
    }
}
