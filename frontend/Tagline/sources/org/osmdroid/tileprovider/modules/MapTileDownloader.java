package org.osmdroid.tileprovider.modules;

import android.graphics.drawable.Drawable;
import java.util.concurrent.atomic.AtomicReference;
import microsoft.mappoint.TileSystem;
import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.BitmapPool;
import org.osmdroid.tileprovider.MapTileRequestState;
import org.osmdroid.tileprovider.ReusableBitmapDrawable;
import org.osmdroid.tileprovider.modules.MapTileModuleProviderBase;
import org.osmdroid.tileprovider.tilesource.ITileSource;
import org.osmdroid.tileprovider.tilesource.OnlineTileSourceBase;

public class MapTileDownloader extends MapTileModuleProviderBase {
    /* access modifiers changed from: private */
    public final IFilesystemCache mFilesystemCache;
    /* access modifiers changed from: private */
    public final INetworkAvailablityCheck mNetworkAvailablityCheck;
    /* access modifiers changed from: private */
    public final AtomicReference<OnlineTileSourceBase> mTileSource;

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public MapTileDownloader(ITileSource pTileSource) {
        this(pTileSource, (IFilesystemCache) null, (INetworkAvailablityCheck) null);
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public MapTileDownloader(ITileSource pTileSource, IFilesystemCache pFilesystemCache) {
        this(pTileSource, pFilesystemCache, (INetworkAvailablityCheck) null);
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public MapTileDownloader(ITileSource pTileSource, IFilesystemCache pFilesystemCache, INetworkAvailablityCheck pNetworkAvailablityCheck) {
        this(pTileSource, pFilesystemCache, pNetworkAvailablityCheck, Configuration.getInstance().getTileDownloadThreads(), Configuration.getInstance().getTileDownloadMaxQueueSize());
    }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public MapTileDownloader(ITileSource pTileSource, IFilesystemCache pFilesystemCache, INetworkAvailablityCheck pNetworkAvailablityCheck, int pThreadPoolSize, int pPendingQueueSize) {
        super(pThreadPoolSize, pPendingQueueSize);
        AtomicReference<OnlineTileSourceBase> atomicReference;
        new AtomicReference<>();
        this.mTileSource = atomicReference;
        this.mFilesystemCache = pFilesystemCache;
        this.mNetworkAvailablityCheck = pNetworkAvailablityCheck;
        setTileSource(pTileSource);
    }

    public ITileSource getTileSource() {
        return this.mTileSource.get();
    }

    public boolean getUsesDataConnection() {
        return true;
    }

    /* access modifiers changed from: protected */
    public String getName() {
        return "Online Tile Download Provider";
    }

    /* access modifiers changed from: protected */
    public String getThreadGroupName() {
        return "downloader";
    }

    public TileLoader getTileLoader() {
        TileLoader tileLoader;
        new TileLoader(this);
        return tileLoader;
    }

    public void detach() {
        super.detach();
        if (this.mFilesystemCache != null) {
            this.mFilesystemCache.onDetach();
        }
    }

    public int getMinimumZoomLevel() {
        OnlineTileSourceBase tileSource = this.mTileSource.get();
        return tileSource != null ? tileSource.getMinimumZoomLevel() : 0;
    }

    public int getMaximumZoomLevel() {
        int maximumZoomLevel;
        OnlineTileSourceBase tileSource = this.mTileSource.get();
        if (tileSource != null) {
            maximumZoomLevel = tileSource.getMaximumZoomLevel();
        } else {
            maximumZoomLevel = TileSystem.getMaximumZoomLevel();
        }
        return maximumZoomLevel;
    }

    public void setTileSource(ITileSource iTileSource) {
        ITileSource tileSource = iTileSource;
        if (tileSource instanceof OnlineTileSourceBase) {
            this.mTileSource.set((OnlineTileSourceBase) tileSource);
        } else {
            this.mTileSource.set((Object) null);
        }
    }

    protected class TileLoader extends MapTileModuleProviderBase.TileLoader {
        final /* synthetic */ MapTileDownloader this$0;

        /* JADX WARNING: Illegal instructions before constructor call */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        protected TileLoader(org.osmdroid.tileprovider.modules.MapTileDownloader r5) {
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
            throw new UnsupportedOperationException("Method not decompiled: org.osmdroid.tileprovider.modules.MapTileDownloader.TileLoader.<init>(org.osmdroid.tileprovider.modules.MapTileDownloader):void");
        }

        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r14v1, resolved type: java.io.InputStream} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r20v133, resolved type: java.io.ByteArrayInputStream} */
        /* JADX WARNING: type inference failed for: r21v60, types: [java.io.OutputStream] */
        /* JADX WARNING: type inference failed for: r20v113, types: [java.io.OutputStream] */
        /* JADX WARNING: type inference failed for: r20v114, types: [java.io.ByteArrayOutputStream] */
        /* JADX WARNING: Multi-variable type inference failed */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public android.graphics.drawable.Drawable loadTile(org.osmdroid.tileprovider.MapTile r28) throws org.osmdroid.tileprovider.modules.MapTileModuleProviderBase.CantContinueException {
            /*
                r27 = this;
                r3 = r27
                r4 = r28
                r20 = r3
                r0 = r20
                org.osmdroid.tileprovider.modules.MapTileDownloader r0 = r0.this$0
                r20 = r0
                java.util.concurrent.atomic.AtomicReference r20 = r20.mTileSource
                java.lang.Object r20 = r20.get()
                org.osmdroid.tileprovider.tilesource.OnlineTileSourceBase r20 = (org.osmdroid.tileprovider.tilesource.OnlineTileSourceBase) r20
                r5 = r20
                r20 = r5
                if (r20 != 0) goto L_0x0021
                r20 = 0
                r3 = r20
            L_0x0020:
                return r3
            L_0x0021:
                r20 = 0
                r6 = r20
                r20 = 0
                r7 = r20
                r20 = 0
                r8 = r20
                r20 = r3
                r0 = r20
                org.osmdroid.tileprovider.modules.MapTileDownloader r0 = r0.this$0     // Catch:{ UnknownHostException -> 0x039e, LowMemoryException -> 0x0404, FileNotFoundException -> 0x0455, IOException -> 0x04a9, Throwable -> 0x04f8 }
                r20 = r0
                org.osmdroid.tileprovider.modules.INetworkAvailablityCheck r20 = r20.mNetworkAvailablityCheck     // Catch:{ UnknownHostException -> 0x039e, LowMemoryException -> 0x0404, FileNotFoundException -> 0x0455, IOException -> 0x04a9, Throwable -> 0x04f8 }
                if (r20 == 0) goto L_0x00a8
                r20 = r3
                r0 = r20
                org.osmdroid.tileprovider.modules.MapTileDownloader r0 = r0.this$0     // Catch:{ UnknownHostException -> 0x039e, LowMemoryException -> 0x0404, FileNotFoundException -> 0x0455, IOException -> 0x04a9, Throwable -> 0x04f8 }
                r20 = r0
                org.osmdroid.tileprovider.modules.INetworkAvailablityCheck r20 = r20.mNetworkAvailablityCheck     // Catch:{ UnknownHostException -> 0x039e, LowMemoryException -> 0x0404, FileNotFoundException -> 0x0455, IOException -> 0x04a9, Throwable -> 0x04f8 }
                boolean r20 = r20.getNetworkAvailable()     // Catch:{ UnknownHostException -> 0x039e, LowMemoryException -> 0x0404, FileNotFoundException -> 0x0455, IOException -> 0x04a9, Throwable -> 0x04f8 }
                if (r20 != 0) goto L_0x00a8
                org.osmdroid.config.IConfigurationProvider r20 = org.osmdroid.config.Configuration.getInstance()     // Catch:{ UnknownHostException -> 0x039e, LowMemoryException -> 0x0404, FileNotFoundException -> 0x0455, IOException -> 0x04a9, Throwable -> 0x04f8 }
                boolean r20 = r20.isDebugMode()     // Catch:{ UnknownHostException -> 0x039e, LowMemoryException -> 0x0404, FileNotFoundException -> 0x0455, IOException -> 0x04a9, Throwable -> 0x04f8 }
                if (r20 == 0) goto L_0x008b
                java.lang.String r20 = "OsmDroid"
                java.lang.StringBuilder r21 = new java.lang.StringBuilder     // Catch:{ UnknownHostException -> 0x039e, LowMemoryException -> 0x0404, FileNotFoundException -> 0x0455, IOException -> 0x04a9, Throwable -> 0x04f8 }
                r26 = r21
                r21 = r26
                r22 = r26
                r22.<init>()     // Catch:{ UnknownHostException -> 0x039e, LowMemoryException -> 0x0404, FileNotFoundException -> 0x0455, IOException -> 0x04a9, Throwable -> 0x04f8 }
                java.lang.String r22 = "Skipping "
                java.lang.StringBuilder r21 = r21.append(r22)     // Catch:{ UnknownHostException -> 0x039e, LowMemoryException -> 0x0404, FileNotFoundException -> 0x0455, IOException -> 0x04a9, Throwable -> 0x04f8 }
                r22 = r3
                r0 = r22
                org.osmdroid.tileprovider.modules.MapTileDownloader r0 = r0.this$0     // Catch:{ UnknownHostException -> 0x039e, LowMemoryException -> 0x0404, FileNotFoundException -> 0x0455, IOException -> 0x04a9, Throwable -> 0x04f8 }
                r22 = r0
                java.lang.String r22 = r22.getName()     // Catch:{ UnknownHostException -> 0x039e, LowMemoryException -> 0x0404, FileNotFoundException -> 0x0455, IOException -> 0x04a9, Throwable -> 0x04f8 }
                java.lang.StringBuilder r21 = r21.append(r22)     // Catch:{ UnknownHostException -> 0x039e, LowMemoryException -> 0x0404, FileNotFoundException -> 0x0455, IOException -> 0x04a9, Throwable -> 0x04f8 }
                java.lang.String r22 = " due to NetworkAvailabliltyCheck."
                java.lang.StringBuilder r21 = r21.append(r22)     // Catch:{ UnknownHostException -> 0x039e, LowMemoryException -> 0x0404, FileNotFoundException -> 0x0455, IOException -> 0x04a9, Throwable -> 0x04f8 }
                java.lang.String r21 = r21.toString()     // Catch:{ UnknownHostException -> 0x039e, LowMemoryException -> 0x0404, FileNotFoundException -> 0x0455, IOException -> 0x04a9, Throwable -> 0x04f8 }
                int r20 = android.util.Log.d(r20, r21)     // Catch:{ UnknownHostException -> 0x039e, LowMemoryException -> 0x0404, FileNotFoundException -> 0x0455, IOException -> 0x04a9, Throwable -> 0x04f8 }
            L_0x008b:
                r20 = 0
                r9 = r20
                r20 = r6
                org.osmdroid.tileprovider.util.StreamUtils.closeStream(r20)
                r20 = r7
                org.osmdroid.tileprovider.util.StreamUtils.closeStream(r20)
                r20 = r8
                r20.disconnect()     // Catch:{ Exception -> 0x00a4 }
            L_0x009e:
                r20 = r9
                r3 = r20
                goto L_0x0020
            L_0x00a4:
                r20 = move-exception
                r10 = r20
                goto L_0x009e
            L_0x00a8:
                r20 = r5
                r21 = r4
                java.lang.String r20 = r20.getTileURLString(r21)     // Catch:{ UnknownHostException -> 0x039e, LowMemoryException -> 0x0404, FileNotFoundException -> 0x0455, IOException -> 0x04a9, Throwable -> 0x04f8 }
                r9 = r20
                org.osmdroid.config.IConfigurationProvider r20 = org.osmdroid.config.Configuration.getInstance()     // Catch:{ UnknownHostException -> 0x039e, LowMemoryException -> 0x0404, FileNotFoundException -> 0x0455, IOException -> 0x04a9, Throwable -> 0x04f8 }
                boolean r20 = r20.isDebugMode()     // Catch:{ UnknownHostException -> 0x039e, LowMemoryException -> 0x0404, FileNotFoundException -> 0x0455, IOException -> 0x04a9, Throwable -> 0x04f8 }
                if (r20 == 0) goto L_0x00df
                java.lang.String r20 = "OsmDroid"
                java.lang.StringBuilder r21 = new java.lang.StringBuilder     // Catch:{ UnknownHostException -> 0x039e, LowMemoryException -> 0x0404, FileNotFoundException -> 0x0455, IOException -> 0x04a9, Throwable -> 0x04f8 }
                r26 = r21
                r21 = r26
                r22 = r26
                r22.<init>()     // Catch:{ UnknownHostException -> 0x039e, LowMemoryException -> 0x0404, FileNotFoundException -> 0x0455, IOException -> 0x04a9, Throwable -> 0x04f8 }
                java.lang.String r22 = "Downloading Maptile from url: "
                java.lang.StringBuilder r21 = r21.append(r22)     // Catch:{ UnknownHostException -> 0x039e, LowMemoryException -> 0x0404, FileNotFoundException -> 0x0455, IOException -> 0x04a9, Throwable -> 0x04f8 }
                r22 = r9
                java.lang.StringBuilder r21 = r21.append(r22)     // Catch:{ UnknownHostException -> 0x039e, LowMemoryException -> 0x0404, FileNotFoundException -> 0x0455, IOException -> 0x04a9, Throwable -> 0x04f8 }
                java.lang.String r21 = r21.toString()     // Catch:{ UnknownHostException -> 0x039e, LowMemoryException -> 0x0404, FileNotFoundException -> 0x0455, IOException -> 0x04a9, Throwable -> 0x04f8 }
                int r20 = android.util.Log.d(r20, r21)     // Catch:{ UnknownHostException -> 0x039e, LowMemoryException -> 0x0404, FileNotFoundException -> 0x0455, IOException -> 0x04a9, Throwable -> 0x04f8 }
            L_0x00df:
                r20 = r9
                boolean r20 = android.text.TextUtils.isEmpty(r20)     // Catch:{ UnknownHostException -> 0x039e, LowMemoryException -> 0x0404, FileNotFoundException -> 0x0455, IOException -> 0x04a9, Throwable -> 0x04f8 }
                if (r20 == 0) goto L_0x0104
                r20 = 0
                r10 = r20
                r20 = r6
                org.osmdroid.tileprovider.util.StreamUtils.closeStream(r20)
                r20 = r7
                org.osmdroid.tileprovider.util.StreamUtils.closeStream(r20)
                r20 = r8
                r20.disconnect()     // Catch:{ Exception -> 0x0100 }
            L_0x00fa:
                r20 = r10
                r3 = r20
                goto L_0x0020
            L_0x0100:
                r20 = move-exception
                r11 = r20
                goto L_0x00fa
            L_0x0104:
                org.osmdroid.config.IConfigurationProvider r20 = org.osmdroid.config.Configuration.getInstance()     // Catch:{ UnknownHostException -> 0x039e, LowMemoryException -> 0x0404, FileNotFoundException -> 0x0455, IOException -> 0x04a9, Throwable -> 0x04f8 }
                java.net.Proxy r20 = r20.getHttpProxy()     // Catch:{ UnknownHostException -> 0x039e, LowMemoryException -> 0x0404, FileNotFoundException -> 0x0455, IOException -> 0x04a9, Throwable -> 0x04f8 }
                if (r20 == 0) goto L_0x0181
                java.net.URL r20 = new java.net.URL     // Catch:{ UnknownHostException -> 0x039e, LowMemoryException -> 0x0404, FileNotFoundException -> 0x0455, IOException -> 0x04a9, Throwable -> 0x04f8 }
                r26 = r20
                r20 = r26
                r21 = r26
                r22 = r9
                r21.<init>(r22)     // Catch:{ UnknownHostException -> 0x039e, LowMemoryException -> 0x0404, FileNotFoundException -> 0x0455, IOException -> 0x04a9, Throwable -> 0x04f8 }
                org.osmdroid.config.IConfigurationProvider r21 = org.osmdroid.config.Configuration.getInstance()     // Catch:{ UnknownHostException -> 0x039e, LowMemoryException -> 0x0404, FileNotFoundException -> 0x0455, IOException -> 0x04a9, Throwable -> 0x04f8 }
                java.net.Proxy r21 = r21.getHttpProxy()     // Catch:{ UnknownHostException -> 0x039e, LowMemoryException -> 0x0404, FileNotFoundException -> 0x0455, IOException -> 0x04a9, Throwable -> 0x04f8 }
                java.net.URLConnection r20 = r20.openConnection(r21)     // Catch:{ UnknownHostException -> 0x039e, LowMemoryException -> 0x0404, FileNotFoundException -> 0x0455, IOException -> 0x04a9, Throwable -> 0x04f8 }
                java.net.HttpURLConnection r20 = (java.net.HttpURLConnection) r20     // Catch:{ UnknownHostException -> 0x039e, LowMemoryException -> 0x0404, FileNotFoundException -> 0x0455, IOException -> 0x04a9, Throwable -> 0x04f8 }
                r8 = r20
            L_0x012b:
                r20 = r8
                r21 = 1
                r20.setUseCaches(r21)     // Catch:{ UnknownHostException -> 0x039e, LowMemoryException -> 0x0404, FileNotFoundException -> 0x0455, IOException -> 0x04a9, Throwable -> 0x04f8 }
                r20 = r8
                org.osmdroid.config.IConfigurationProvider r21 = org.osmdroid.config.Configuration.getInstance()     // Catch:{ UnknownHostException -> 0x039e, LowMemoryException -> 0x0404, FileNotFoundException -> 0x0455, IOException -> 0x04a9, Throwable -> 0x04f8 }
                java.lang.String r21 = r21.getUserAgentHttpHeader()     // Catch:{ UnknownHostException -> 0x039e, LowMemoryException -> 0x0404, FileNotFoundException -> 0x0455, IOException -> 0x04a9, Throwable -> 0x04f8 }
                org.osmdroid.config.IConfigurationProvider r22 = org.osmdroid.config.Configuration.getInstance()     // Catch:{ UnknownHostException -> 0x039e, LowMemoryException -> 0x0404, FileNotFoundException -> 0x0455, IOException -> 0x04a9, Throwable -> 0x04f8 }
                java.lang.String r22 = r22.getUserAgentValue()     // Catch:{ UnknownHostException -> 0x039e, LowMemoryException -> 0x0404, FileNotFoundException -> 0x0455, IOException -> 0x04a9, Throwable -> 0x04f8 }
                r20.setRequestProperty(r21, r22)     // Catch:{ UnknownHostException -> 0x039e, LowMemoryException -> 0x0404, FileNotFoundException -> 0x0455, IOException -> 0x04a9, Throwable -> 0x04f8 }
                org.osmdroid.config.IConfigurationProvider r20 = org.osmdroid.config.Configuration.getInstance()     // Catch:{ UnknownHostException -> 0x039e, LowMemoryException -> 0x0404, FileNotFoundException -> 0x0455, IOException -> 0x04a9, Throwable -> 0x04f8 }
                java.util.Map r20 = r20.getAdditionalHttpRequestProperties()     // Catch:{ UnknownHostException -> 0x039e, LowMemoryException -> 0x0404, FileNotFoundException -> 0x0455, IOException -> 0x04a9, Throwable -> 0x04f8 }
                java.util.Set r20 = r20.entrySet()     // Catch:{ UnknownHostException -> 0x039e, LowMemoryException -> 0x0404, FileNotFoundException -> 0x0455, IOException -> 0x04a9, Throwable -> 0x04f8 }
                java.util.Iterator r20 = r20.iterator()     // Catch:{ UnknownHostException -> 0x039e, LowMemoryException -> 0x0404, FileNotFoundException -> 0x0455, IOException -> 0x04a9, Throwable -> 0x04f8 }
                r10 = r20
            L_0x0159:
                r20 = r10
                boolean r20 = r20.hasNext()     // Catch:{ UnknownHostException -> 0x039e, LowMemoryException -> 0x0404, FileNotFoundException -> 0x0455, IOException -> 0x04a9, Throwable -> 0x04f8 }
                if (r20 == 0) goto L_0x0197
                r20 = r10
                java.lang.Object r20 = r20.next()     // Catch:{ UnknownHostException -> 0x039e, LowMemoryException -> 0x0404, FileNotFoundException -> 0x0455, IOException -> 0x04a9, Throwable -> 0x04f8 }
                java.util.Map$Entry r20 = (java.util.Map.Entry) r20     // Catch:{ UnknownHostException -> 0x039e, LowMemoryException -> 0x0404, FileNotFoundException -> 0x0455, IOException -> 0x04a9, Throwable -> 0x04f8 }
                r11 = r20
                r20 = r8
                r21 = r11
                java.lang.Object r21 = r21.getKey()     // Catch:{ UnknownHostException -> 0x039e, LowMemoryException -> 0x0404, FileNotFoundException -> 0x0455, IOException -> 0x04a9, Throwable -> 0x04f8 }
                java.lang.String r21 = (java.lang.String) r21     // Catch:{ UnknownHostException -> 0x039e, LowMemoryException -> 0x0404, FileNotFoundException -> 0x0455, IOException -> 0x04a9, Throwable -> 0x04f8 }
                r22 = r11
                java.lang.Object r22 = r22.getValue()     // Catch:{ UnknownHostException -> 0x039e, LowMemoryException -> 0x0404, FileNotFoundException -> 0x0455, IOException -> 0x04a9, Throwable -> 0x04f8 }
                java.lang.String r22 = (java.lang.String) r22     // Catch:{ UnknownHostException -> 0x039e, LowMemoryException -> 0x0404, FileNotFoundException -> 0x0455, IOException -> 0x04a9, Throwable -> 0x04f8 }
                r20.setRequestProperty(r21, r22)     // Catch:{ UnknownHostException -> 0x039e, LowMemoryException -> 0x0404, FileNotFoundException -> 0x0455, IOException -> 0x04a9, Throwable -> 0x04f8 }
                goto L_0x0159
            L_0x0181:
                java.net.URL r20 = new java.net.URL     // Catch:{ UnknownHostException -> 0x039e, LowMemoryException -> 0x0404, FileNotFoundException -> 0x0455, IOException -> 0x04a9, Throwable -> 0x04f8 }
                r26 = r20
                r20 = r26
                r21 = r26
                r22 = r9
                r21.<init>(r22)     // Catch:{ UnknownHostException -> 0x039e, LowMemoryException -> 0x0404, FileNotFoundException -> 0x0455, IOException -> 0x04a9, Throwable -> 0x04f8 }
                java.net.URLConnection r20 = r20.openConnection()     // Catch:{ UnknownHostException -> 0x039e, LowMemoryException -> 0x0404, FileNotFoundException -> 0x0455, IOException -> 0x04a9, Throwable -> 0x04f8 }
                java.net.HttpURLConnection r20 = (java.net.HttpURLConnection) r20     // Catch:{ UnknownHostException -> 0x039e, LowMemoryException -> 0x0404, FileNotFoundException -> 0x0455, IOException -> 0x04a9, Throwable -> 0x04f8 }
                r8 = r20
                goto L_0x012b
            L_0x0197:
                r20 = r8
                r20.connect()     // Catch:{ UnknownHostException -> 0x039e, LowMemoryException -> 0x0404, FileNotFoundException -> 0x0455, IOException -> 0x04a9, Throwable -> 0x04f8 }
                r20 = r8
                int r20 = r20.getResponseCode()     // Catch:{ UnknownHostException -> 0x039e, LowMemoryException -> 0x0404, FileNotFoundException -> 0x0455, IOException -> 0x04a9, Throwable -> 0x04f8 }
                r21 = 200(0xc8, float:2.8E-43)
                r0 = r20
                r1 = r21
                if (r0 == r1) goto L_0x0216
                java.lang.String r20 = "OsmDroid"
                java.lang.StringBuilder r21 = new java.lang.StringBuilder     // Catch:{ UnknownHostException -> 0x039e, LowMemoryException -> 0x0404, FileNotFoundException -> 0x0455, IOException -> 0x04a9, Throwable -> 0x04f8 }
                r26 = r21
                r21 = r26
                r22 = r26
                r22.<init>()     // Catch:{ UnknownHostException -> 0x039e, LowMemoryException -> 0x0404, FileNotFoundException -> 0x0455, IOException -> 0x04a9, Throwable -> 0x04f8 }
                java.lang.String r22 = "Problem downloading MapTile: "
                java.lang.StringBuilder r21 = r21.append(r22)     // Catch:{ UnknownHostException -> 0x039e, LowMemoryException -> 0x0404, FileNotFoundException -> 0x0455, IOException -> 0x04a9, Throwable -> 0x04f8 }
                r22 = r4
                java.lang.StringBuilder r21 = r21.append(r22)     // Catch:{ UnknownHostException -> 0x039e, LowMemoryException -> 0x0404, FileNotFoundException -> 0x0455, IOException -> 0x04a9, Throwable -> 0x04f8 }
                java.lang.String r22 = " HTTP response: "
                java.lang.StringBuilder r21 = r21.append(r22)     // Catch:{ UnknownHostException -> 0x039e, LowMemoryException -> 0x0404, FileNotFoundException -> 0x0455, IOException -> 0x04a9, Throwable -> 0x04f8 }
                r22 = r8
                java.lang.String r22 = r22.getResponseMessage()     // Catch:{ UnknownHostException -> 0x039e, LowMemoryException -> 0x0404, FileNotFoundException -> 0x0455, IOException -> 0x04a9, Throwable -> 0x04f8 }
                java.lang.StringBuilder r21 = r21.append(r22)     // Catch:{ UnknownHostException -> 0x039e, LowMemoryException -> 0x0404, FileNotFoundException -> 0x0455, IOException -> 0x04a9, Throwable -> 0x04f8 }
                java.lang.String r21 = r21.toString()     // Catch:{ UnknownHostException -> 0x039e, LowMemoryException -> 0x0404, FileNotFoundException -> 0x0455, IOException -> 0x04a9, Throwable -> 0x04f8 }
                int r20 = android.util.Log.w(r20, r21)     // Catch:{ UnknownHostException -> 0x039e, LowMemoryException -> 0x0404, FileNotFoundException -> 0x0455, IOException -> 0x04a9, Throwable -> 0x04f8 }
                org.osmdroid.config.IConfigurationProvider r20 = org.osmdroid.config.Configuration.getInstance()     // Catch:{ UnknownHostException -> 0x039e, LowMemoryException -> 0x0404, FileNotFoundException -> 0x0455, IOException -> 0x04a9, Throwable -> 0x04f8 }
                boolean r20 = r20.isDebugMapTileDownloader()     // Catch:{ UnknownHostException -> 0x039e, LowMemoryException -> 0x0404, FileNotFoundException -> 0x0455, IOException -> 0x04a9, Throwable -> 0x04f8 }
                if (r20 == 0) goto L_0x01f1
                java.lang.String r20 = "OsmDroid"
                r21 = r9
                int r20 = android.util.Log.d(r20, r21)     // Catch:{ UnknownHostException -> 0x039e, LowMemoryException -> 0x0404, FileNotFoundException -> 0x0455, IOException -> 0x04a9, Throwable -> 0x04f8 }
            L_0x01f1:
                int r20 = org.osmdroid.tileprovider.util.Counters.tileDownloadErrors     // Catch:{ UnknownHostException -> 0x039e, LowMemoryException -> 0x0404, FileNotFoundException -> 0x0455, IOException -> 0x04a9, Throwable -> 0x04f8 }
                r21 = 1
                int r20 = r20 + 1
                org.osmdroid.tileprovider.util.Counters.tileDownloadErrors = r20     // Catch:{ UnknownHostException -> 0x039e, LowMemoryException -> 0x0404, FileNotFoundException -> 0x0455, IOException -> 0x04a9, Throwable -> 0x04f8 }
                r20 = 0
                r10 = r20
                r20 = r6
                org.osmdroid.tileprovider.util.StreamUtils.closeStream(r20)
                r20 = r7
                org.osmdroid.tileprovider.util.StreamUtils.closeStream(r20)
                r20 = r8
                r20.disconnect()     // Catch:{ Exception -> 0x0212 }
            L_0x020c:
                r20 = r10
                r3 = r20
                goto L_0x0020
            L_0x0212:
                r20 = move-exception
                r11 = r20
                goto L_0x020c
            L_0x0216:
                org.osmdroid.config.IConfigurationProvider r20 = org.osmdroid.config.Configuration.getInstance()     // Catch:{ UnknownHostException -> 0x039e, LowMemoryException -> 0x0404, FileNotFoundException -> 0x0455, IOException -> 0x04a9, Throwable -> 0x04f8 }
                boolean r20 = r20.isDebugMapTileDownloader()     // Catch:{ UnknownHostException -> 0x039e, LowMemoryException -> 0x0404, FileNotFoundException -> 0x0455, IOException -> 0x04a9, Throwable -> 0x04f8 }
                if (r20 == 0) goto L_0x0243
                java.lang.String r20 = "OsmDroid"
                java.lang.StringBuilder r21 = new java.lang.StringBuilder     // Catch:{ UnknownHostException -> 0x039e, LowMemoryException -> 0x0404, FileNotFoundException -> 0x0455, IOException -> 0x04a9, Throwable -> 0x04f8 }
                r26 = r21
                r21 = r26
                r22 = r26
                r22.<init>()     // Catch:{ UnknownHostException -> 0x039e, LowMemoryException -> 0x0404, FileNotFoundException -> 0x0455, IOException -> 0x04a9, Throwable -> 0x04f8 }
                r22 = r9
                java.lang.StringBuilder r21 = r21.append(r22)     // Catch:{ UnknownHostException -> 0x039e, LowMemoryException -> 0x0404, FileNotFoundException -> 0x0455, IOException -> 0x04a9, Throwable -> 0x04f8 }
                java.lang.String r22 = " success"
                java.lang.StringBuilder r21 = r21.append(r22)     // Catch:{ UnknownHostException -> 0x039e, LowMemoryException -> 0x0404, FileNotFoundException -> 0x0455, IOException -> 0x04a9, Throwable -> 0x04f8 }
                java.lang.String r21 = r21.toString()     // Catch:{ UnknownHostException -> 0x039e, LowMemoryException -> 0x0404, FileNotFoundException -> 0x0455, IOException -> 0x04a9, Throwable -> 0x04f8 }
                int r20 = android.util.Log.d(r20, r21)     // Catch:{ UnknownHostException -> 0x039e, LowMemoryException -> 0x0404, FileNotFoundException -> 0x0455, IOException -> 0x04a9, Throwable -> 0x04f8 }
            L_0x0243:
                r20 = r8
                java.io.InputStream r20 = r20.getInputStream()     // Catch:{ UnknownHostException -> 0x039e, LowMemoryException -> 0x0404, FileNotFoundException -> 0x0455, IOException -> 0x04a9, Throwable -> 0x04f8 }
                r6 = r20
                java.io.ByteArrayOutputStream r20 = new java.io.ByteArrayOutputStream     // Catch:{ UnknownHostException -> 0x039e, LowMemoryException -> 0x0404, FileNotFoundException -> 0x0455, IOException -> 0x04a9, Throwable -> 0x04f8 }
                r26 = r20
                r20 = r26
                r21 = r26
                r21.<init>()     // Catch:{ UnknownHostException -> 0x039e, LowMemoryException -> 0x0404, FileNotFoundException -> 0x0455, IOException -> 0x04a9, Throwable -> 0x04f8 }
                r10 = r20
                java.io.BufferedOutputStream r20 = new java.io.BufferedOutputStream     // Catch:{ UnknownHostException -> 0x039e, LowMemoryException -> 0x0404, FileNotFoundException -> 0x0455, IOException -> 0x04a9, Throwable -> 0x04f8 }
                r26 = r20
                r20 = r26
                r21 = r26
                r22 = r10
                r23 = 8192(0x2000, float:1.14794E-41)
                r21.<init>(r22, r23)     // Catch:{ UnknownHostException -> 0x039e, LowMemoryException -> 0x0404, FileNotFoundException -> 0x0455, IOException -> 0x04a9, Throwable -> 0x04f8 }
                r7 = r20
                org.osmdroid.config.IConfigurationProvider r20 = org.osmdroid.config.Configuration.getInstance()     // Catch:{ UnknownHostException -> 0x039e, LowMemoryException -> 0x0404, FileNotFoundException -> 0x0455, IOException -> 0x04a9, Throwable -> 0x04f8 }
                java.lang.Long r20 = r20.getExpirationOverrideDuration()     // Catch:{ UnknownHostException -> 0x039e, LowMemoryException -> 0x0404, FileNotFoundException -> 0x0455, IOException -> 0x04a9, Throwable -> 0x04f8 }
                r12 = r20
                r20 = r12
                if (r20 == 0) goto L_0x0307
                java.util.Date r20 = new java.util.Date     // Catch:{ UnknownHostException -> 0x039e, LowMemoryException -> 0x0404, FileNotFoundException -> 0x0455, IOException -> 0x04a9, Throwable -> 0x04f8 }
                r26 = r20
                r20 = r26
                r21 = r26
                long r22 = java.lang.System.currentTimeMillis()     // Catch:{ UnknownHostException -> 0x039e, LowMemoryException -> 0x0404, FileNotFoundException -> 0x0455, IOException -> 0x04a9, Throwable -> 0x04f8 }
                r24 = r12
                long r24 = r24.longValue()     // Catch:{ UnknownHostException -> 0x039e, LowMemoryException -> 0x0404, FileNotFoundException -> 0x0455, IOException -> 0x04a9, Throwable -> 0x04f8 }
                long r22 = r22 + r24
                r21.<init>(r22)     // Catch:{ UnknownHostException -> 0x039e, LowMemoryException -> 0x0404, FileNotFoundException -> 0x0455, IOException -> 0x04a9, Throwable -> 0x04f8 }
                r11 = r20
            L_0x0290:
                r20 = r4
                r21 = r11
                r20.setExpires(r21)     // Catch:{ UnknownHostException -> 0x039e, LowMemoryException -> 0x0404, FileNotFoundException -> 0x0455, IOException -> 0x04a9, Throwable -> 0x04f8 }
                r20 = r6
                r21 = r7
                long r20 = org.osmdroid.tileprovider.util.StreamUtils.copy(r20, r21)     // Catch:{ UnknownHostException -> 0x039e, LowMemoryException -> 0x0404, FileNotFoundException -> 0x0455, IOException -> 0x04a9, Throwable -> 0x04f8 }
                r20 = r7
                r20.flush()     // Catch:{ UnknownHostException -> 0x039e, LowMemoryException -> 0x0404, FileNotFoundException -> 0x0455, IOException -> 0x04a9, Throwable -> 0x04f8 }
                r20 = r10
                byte[] r20 = r20.toByteArray()     // Catch:{ UnknownHostException -> 0x039e, LowMemoryException -> 0x0404, FileNotFoundException -> 0x0455, IOException -> 0x04a9, Throwable -> 0x04f8 }
                r13 = r20
                java.io.ByteArrayInputStream r20 = new java.io.ByteArrayInputStream     // Catch:{ UnknownHostException -> 0x039e, LowMemoryException -> 0x0404, FileNotFoundException -> 0x0455, IOException -> 0x04a9, Throwable -> 0x04f8 }
                r26 = r20
                r20 = r26
                r21 = r26
                r22 = r13
                r21.<init>(r22)     // Catch:{ UnknownHostException -> 0x039e, LowMemoryException -> 0x0404, FileNotFoundException -> 0x0455, IOException -> 0x04a9, Throwable -> 0x04f8 }
                r14 = r20
                r20 = r3
                r0 = r20
                org.osmdroid.tileprovider.modules.MapTileDownloader r0 = r0.this$0     // Catch:{ UnknownHostException -> 0x039e, LowMemoryException -> 0x0404, FileNotFoundException -> 0x0455, IOException -> 0x04a9, Throwable -> 0x04f8 }
                r20 = r0
                org.osmdroid.tileprovider.modules.IFilesystemCache r20 = r20.mFilesystemCache     // Catch:{ UnknownHostException -> 0x039e, LowMemoryException -> 0x0404, FileNotFoundException -> 0x0455, IOException -> 0x04a9, Throwable -> 0x04f8 }
                if (r20 == 0) goto L_0x02e4
                r20 = r3
                r0 = r20
                org.osmdroid.tileprovider.modules.MapTileDownloader r0 = r0.this$0     // Catch:{ UnknownHostException -> 0x039e, LowMemoryException -> 0x0404, FileNotFoundException -> 0x0455, IOException -> 0x04a9, Throwable -> 0x04f8 }
                r20 = r0
                org.osmdroid.tileprovider.modules.IFilesystemCache r20 = r20.mFilesystemCache     // Catch:{ UnknownHostException -> 0x039e, LowMemoryException -> 0x0404, FileNotFoundException -> 0x0455, IOException -> 0x04a9, Throwable -> 0x04f8 }
                r21 = r5
                r22 = r4
                r23 = r14
                boolean r20 = r20.saveFile(r21, r22, r23)     // Catch:{ UnknownHostException -> 0x039e, LowMemoryException -> 0x0404, FileNotFoundException -> 0x0455, IOException -> 0x04a9, Throwable -> 0x04f8 }
                r20 = r14
                r20.reset()     // Catch:{ UnknownHostException -> 0x039e, LowMemoryException -> 0x0404, FileNotFoundException -> 0x0455, IOException -> 0x04a9, Throwable -> 0x04f8 }
            L_0x02e4:
                r20 = r5
                r21 = r14
                android.graphics.drawable.Drawable r20 = r20.getDrawable((java.io.InputStream) r21)     // Catch:{ UnknownHostException -> 0x039e, LowMemoryException -> 0x0404, FileNotFoundException -> 0x0455, IOException -> 0x04a9, Throwable -> 0x04f8 }
                r15 = r20
                r20 = r15
                r16 = r20
                r20 = r6
                org.osmdroid.tileprovider.util.StreamUtils.closeStream(r20)
                r20 = r7
                org.osmdroid.tileprovider.util.StreamUtils.closeStream(r20)
                r20 = r8
                r20.disconnect()     // Catch:{ Exception -> 0x0399 }
            L_0x0301:
                r20 = r16
                r3 = r20
                goto L_0x0020
            L_0x0307:
                java.util.Date r20 = new java.util.Date     // Catch:{ UnknownHostException -> 0x039e, LowMemoryException -> 0x0404, FileNotFoundException -> 0x0455, IOException -> 0x04a9, Throwable -> 0x04f8 }
                r26 = r20
                r20 = r26
                r21 = r26
                long r22 = java.lang.System.currentTimeMillis()     // Catch:{ UnknownHostException -> 0x039e, LowMemoryException -> 0x0404, FileNotFoundException -> 0x0455, IOException -> 0x04a9, Throwable -> 0x04f8 }
                r24 = 604800000(0x240c8400, double:2.988109026E-315)
                long r22 = r22 + r24
                org.osmdroid.config.IConfigurationProvider r24 = org.osmdroid.config.Configuration.getInstance()     // Catch:{ UnknownHostException -> 0x039e, LowMemoryException -> 0x0404, FileNotFoundException -> 0x0455, IOException -> 0x04a9, Throwable -> 0x04f8 }
                long r24 = r24.getExpirationExtendedDuration()     // Catch:{ UnknownHostException -> 0x039e, LowMemoryException -> 0x0404, FileNotFoundException -> 0x0455, IOException -> 0x04a9, Throwable -> 0x04f8 }
                long r22 = r22 + r24
                r21.<init>(r22)     // Catch:{ UnknownHostException -> 0x039e, LowMemoryException -> 0x0404, FileNotFoundException -> 0x0455, IOException -> 0x04a9, Throwable -> 0x04f8 }
                r11 = r20
                r20 = r8
                java.lang.String r21 = "Expires"
                java.lang.String r20 = r20.getHeaderField(r21)     // Catch:{ UnknownHostException -> 0x039e, LowMemoryException -> 0x0404, FileNotFoundException -> 0x0455, IOException -> 0x04a9, Throwable -> 0x04f8 }
                r13 = r20
                r20 = r13
                if (r20 == 0) goto L_0x0290
                r20 = r13
                int r20 = r20.length()     // Catch:{ UnknownHostException -> 0x039e, LowMemoryException -> 0x0404, FileNotFoundException -> 0x0455, IOException -> 0x04a9, Throwable -> 0x04f8 }
                if (r20 <= 0) goto L_0x0290
                org.osmdroid.config.IConfigurationProvider r20 = org.osmdroid.config.Configuration.getInstance()     // Catch:{ Exception -> 0x0365 }
                java.text.SimpleDateFormat r20 = r20.getHttpHeaderDateTimeFormat()     // Catch:{ Exception -> 0x0365 }
                r21 = r13
                java.util.Date r20 = r20.parse(r21)     // Catch:{ Exception -> 0x0365 }
                r11 = r20
                r20 = r11
                r21 = r11
                long r21 = r21.getTime()     // Catch:{ Exception -> 0x0365 }
                org.osmdroid.config.IConfigurationProvider r23 = org.osmdroid.config.Configuration.getInstance()     // Catch:{ Exception -> 0x0365 }
                long r23 = r23.getExpirationExtendedDuration()     // Catch:{ Exception -> 0x0365 }
                long r21 = r21 + r23
                r20.setTime(r21)     // Catch:{ Exception -> 0x0365 }
                goto L_0x0290
            L_0x0365:
                r20 = move-exception
                r14 = r20
                org.osmdroid.config.IConfigurationProvider r20 = org.osmdroid.config.Configuration.getInstance()     // Catch:{ UnknownHostException -> 0x039e, LowMemoryException -> 0x0404, FileNotFoundException -> 0x0455, IOException -> 0x04a9, Throwable -> 0x04f8 }
                boolean r20 = r20.isDebugMapTileDownloader()     // Catch:{ UnknownHostException -> 0x039e, LowMemoryException -> 0x0404, FileNotFoundException -> 0x0455, IOException -> 0x04a9, Throwable -> 0x04f8 }
                if (r20 == 0) goto L_0x0290
                java.lang.String r20 = "OsmDroid"
                java.lang.StringBuilder r21 = new java.lang.StringBuilder     // Catch:{ UnknownHostException -> 0x039e, LowMemoryException -> 0x0404, FileNotFoundException -> 0x0455, IOException -> 0x04a9, Throwable -> 0x04f8 }
                r26 = r21
                r21 = r26
                r22 = r26
                r22.<init>()     // Catch:{ UnknownHostException -> 0x039e, LowMemoryException -> 0x0404, FileNotFoundException -> 0x0455, IOException -> 0x04a9, Throwable -> 0x04f8 }
                java.lang.String r22 = "Unable to parse expiration tag for tile, using default, server returned "
                java.lang.StringBuilder r21 = r21.append(r22)     // Catch:{ UnknownHostException -> 0x039e, LowMemoryException -> 0x0404, FileNotFoundException -> 0x0455, IOException -> 0x04a9, Throwable -> 0x04f8 }
                r22 = r13
                java.lang.StringBuilder r21 = r21.append(r22)     // Catch:{ UnknownHostException -> 0x039e, LowMemoryException -> 0x0404, FileNotFoundException -> 0x0455, IOException -> 0x04a9, Throwable -> 0x04f8 }
                java.lang.String r21 = r21.toString()     // Catch:{ UnknownHostException -> 0x039e, LowMemoryException -> 0x0404, FileNotFoundException -> 0x0455, IOException -> 0x04a9, Throwable -> 0x04f8 }
                r22 = r14
                int r20 = android.util.Log.d(r20, r21, r22)     // Catch:{ UnknownHostException -> 0x039e, LowMemoryException -> 0x0404, FileNotFoundException -> 0x0455, IOException -> 0x04a9, Throwable -> 0x04f8 }
                goto L_0x0290
            L_0x0399:
                r20 = move-exception
                r17 = r20
                goto L_0x0301
            L_0x039e:
                r20 = move-exception
                r9 = r20
                java.lang.String r20 = "OsmDroid"
                java.lang.StringBuilder r21 = new java.lang.StringBuilder     // Catch:{ all -> 0x03ef }
                r26 = r21
                r21 = r26
                r22 = r26
                r22.<init>()     // Catch:{ all -> 0x03ef }
                java.lang.String r22 = "UnknownHostException downloading MapTile: "
                java.lang.StringBuilder r21 = r21.append(r22)     // Catch:{ all -> 0x03ef }
                r22 = r4
                java.lang.StringBuilder r21 = r21.append(r22)     // Catch:{ all -> 0x03ef }
                java.lang.String r22 = " : "
                java.lang.StringBuilder r21 = r21.append(r22)     // Catch:{ all -> 0x03ef }
                r22 = r9
                java.lang.StringBuilder r21 = r21.append(r22)     // Catch:{ all -> 0x03ef }
                java.lang.String r21 = r21.toString()     // Catch:{ all -> 0x03ef }
                int r20 = android.util.Log.w(r20, r21)     // Catch:{ all -> 0x03ef }
                int r20 = org.osmdroid.tileprovider.util.Counters.tileDownloadErrors     // Catch:{ all -> 0x03ef }
                r21 = 1
                int r20 = r20 + 1
                org.osmdroid.tileprovider.util.Counters.tileDownloadErrors = r20     // Catch:{ all -> 0x03ef }
                org.osmdroid.tileprovider.modules.MapTileModuleProviderBase$CantContinueException r20 = new org.osmdroid.tileprovider.modules.MapTileModuleProviderBase$CantContinueException     // Catch:{ all -> 0x03ef }
                r26 = r20
                r20 = r26
                r21 = r26
                r22 = r3
                r0 = r22
                org.osmdroid.tileprovider.modules.MapTileDownloader r0 = r0.this$0     // Catch:{ all -> 0x03ef }
                r22 = r0
                r23 = r9
                r21.<init>((org.osmdroid.tileprovider.modules.MapTileModuleProviderBase) r22, (java.lang.Throwable) r23)     // Catch:{ all -> 0x03ef }
                throw r20     // Catch:{ all -> 0x03ef }
            L_0x03ef:
                r20 = move-exception
                r18 = r20
                r20 = r6
                org.osmdroid.tileprovider.util.StreamUtils.closeStream(r20)
                r20 = r7
                org.osmdroid.tileprovider.util.StreamUtils.closeStream(r20)
                r20 = r8
                r20.disconnect()     // Catch:{ Exception -> 0x053e }
            L_0x0401:
                r20 = r18
                throw r20
            L_0x0404:
                r20 = move-exception
                r9 = r20
                int r20 = org.osmdroid.tileprovider.util.Counters.countOOM     // Catch:{ all -> 0x03ef }
                r21 = 1
                int r20 = r20 + 1
                org.osmdroid.tileprovider.util.Counters.countOOM = r20     // Catch:{ all -> 0x03ef }
                java.lang.String r20 = "OsmDroid"
                java.lang.StringBuilder r21 = new java.lang.StringBuilder     // Catch:{ all -> 0x03ef }
                r26 = r21
                r21 = r26
                r22 = r26
                r22.<init>()     // Catch:{ all -> 0x03ef }
                java.lang.String r22 = "LowMemoryException downloading MapTile: "
                java.lang.StringBuilder r21 = r21.append(r22)     // Catch:{ all -> 0x03ef }
                r22 = r4
                java.lang.StringBuilder r21 = r21.append(r22)     // Catch:{ all -> 0x03ef }
                java.lang.String r22 = " : "
                java.lang.StringBuilder r21 = r21.append(r22)     // Catch:{ all -> 0x03ef }
                r22 = r9
                java.lang.StringBuilder r21 = r21.append(r22)     // Catch:{ all -> 0x03ef }
                java.lang.String r21 = r21.toString()     // Catch:{ all -> 0x03ef }
                int r20 = android.util.Log.w(r20, r21)     // Catch:{ all -> 0x03ef }
                org.osmdroid.tileprovider.modules.MapTileModuleProviderBase$CantContinueException r20 = new org.osmdroid.tileprovider.modules.MapTileModuleProviderBase$CantContinueException     // Catch:{ all -> 0x03ef }
                r26 = r20
                r20 = r26
                r21 = r26
                r22 = r3
                r0 = r22
                org.osmdroid.tileprovider.modules.MapTileDownloader r0 = r0.this$0     // Catch:{ all -> 0x03ef }
                r22 = r0
                r23 = r9
                r21.<init>((org.osmdroid.tileprovider.modules.MapTileModuleProviderBase) r22, (java.lang.Throwable) r23)     // Catch:{ all -> 0x03ef }
                throw r20     // Catch:{ all -> 0x03ef }
            L_0x0455:
                r20 = move-exception
                r9 = r20
                int r20 = org.osmdroid.tileprovider.util.Counters.tileDownloadErrors     // Catch:{ all -> 0x03ef }
                r21 = 1
                int r20 = r20 + 1
                org.osmdroid.tileprovider.util.Counters.tileDownloadErrors = r20     // Catch:{ all -> 0x03ef }
                java.lang.String r20 = "OsmDroid"
                java.lang.StringBuilder r21 = new java.lang.StringBuilder     // Catch:{ all -> 0x03ef }
                r26 = r21
                r21 = r26
                r22 = r26
                r22.<init>()     // Catch:{ all -> 0x03ef }
                java.lang.String r22 = "Tile not found: "
                java.lang.StringBuilder r21 = r21.append(r22)     // Catch:{ all -> 0x03ef }
                r22 = r4
                java.lang.StringBuilder r21 = r21.append(r22)     // Catch:{ all -> 0x03ef }
                java.lang.String r22 = " : "
                java.lang.StringBuilder r21 = r21.append(r22)     // Catch:{ all -> 0x03ef }
                r22 = r9
                java.lang.StringBuilder r21 = r21.append(r22)     // Catch:{ all -> 0x03ef }
                java.lang.String r21 = r21.toString()     // Catch:{ all -> 0x03ef }
                int r20 = android.util.Log.w(r20, r21)     // Catch:{ all -> 0x03ef }
                r20 = r6
                org.osmdroid.tileprovider.util.StreamUtils.closeStream(r20)
                r20 = r7
                org.osmdroid.tileprovider.util.StreamUtils.closeStream(r20)
                r20 = r8
                r20.disconnect()     // Catch:{ Exception -> 0x04a5 }
            L_0x049f:
                r20 = 0
                r3 = r20
                goto L_0x0020
            L_0x04a5:
                r20 = move-exception
                r9 = r20
                goto L_0x049f
            L_0x04a9:
                r20 = move-exception
                r9 = r20
                int r20 = org.osmdroid.tileprovider.util.Counters.tileDownloadErrors     // Catch:{ all -> 0x03ef }
                r21 = 1
                int r20 = r20 + 1
                org.osmdroid.tileprovider.util.Counters.tileDownloadErrors = r20     // Catch:{ all -> 0x03ef }
                java.lang.String r20 = "OsmDroid"
                java.lang.StringBuilder r21 = new java.lang.StringBuilder     // Catch:{ all -> 0x03ef }
                r26 = r21
                r21 = r26
                r22 = r26
                r22.<init>()     // Catch:{ all -> 0x03ef }
                java.lang.String r22 = "IOException downloading MapTile: "
                java.lang.StringBuilder r21 = r21.append(r22)     // Catch:{ all -> 0x03ef }
                r22 = r4
                java.lang.StringBuilder r21 = r21.append(r22)     // Catch:{ all -> 0x03ef }
                java.lang.String r22 = " : "
                java.lang.StringBuilder r21 = r21.append(r22)     // Catch:{ all -> 0x03ef }
                r22 = r9
                java.lang.StringBuilder r21 = r21.append(r22)     // Catch:{ all -> 0x03ef }
                java.lang.String r21 = r21.toString()     // Catch:{ all -> 0x03ef }
                int r20 = android.util.Log.w(r20, r21)     // Catch:{ all -> 0x03ef }
                r20 = r6
                org.osmdroid.tileprovider.util.StreamUtils.closeStream(r20)
                r20 = r7
                org.osmdroid.tileprovider.util.StreamUtils.closeStream(r20)
                r20 = r8
                r20.disconnect()     // Catch:{ Exception -> 0x04f4 }
                goto L_0x049f
            L_0x04f4:
                r20 = move-exception
                r9 = r20
                goto L_0x049f
            L_0x04f8:
                r20 = move-exception
                r9 = r20
                int r20 = org.osmdroid.tileprovider.util.Counters.tileDownloadErrors     // Catch:{ all -> 0x03ef }
                r21 = 1
                int r20 = r20 + 1
                org.osmdroid.tileprovider.util.Counters.tileDownloadErrors = r20     // Catch:{ all -> 0x03ef }
                java.lang.String r20 = "OsmDroid"
                java.lang.StringBuilder r21 = new java.lang.StringBuilder     // Catch:{ all -> 0x03ef }
                r26 = r21
                r21 = r26
                r22 = r26
                r22.<init>()     // Catch:{ all -> 0x03ef }
                java.lang.String r22 = "Error downloading MapTile: "
                java.lang.StringBuilder r21 = r21.append(r22)     // Catch:{ all -> 0x03ef }
                r22 = r4
                java.lang.StringBuilder r21 = r21.append(r22)     // Catch:{ all -> 0x03ef }
                java.lang.String r21 = r21.toString()     // Catch:{ all -> 0x03ef }
                r22 = r9
                int r20 = android.util.Log.e(r20, r21, r22)     // Catch:{ all -> 0x03ef }
                r20 = r6
                org.osmdroid.tileprovider.util.StreamUtils.closeStream(r20)
                r20 = r7
                org.osmdroid.tileprovider.util.StreamUtils.closeStream(r20)
                r20 = r8
                r20.disconnect()     // Catch:{ Exception -> 0x0539 }
                goto L_0x049f
            L_0x0539:
                r20 = move-exception
                r9 = r20
                goto L_0x049f
            L_0x053e:
                r20 = move-exception
                r19 = r20
                goto L_0x0401
            */
            throw new UnsupportedOperationException("Method not decompiled: org.osmdroid.tileprovider.modules.MapTileDownloader.TileLoader.loadTile(org.osmdroid.tileprovider.MapTile):android.graphics.drawable.Drawable");
        }

        /* access modifiers changed from: protected */
        public void tileLoaded(MapTileRequestState mapTileRequestState, Drawable drawable) {
            MapTileRequestState pState = mapTileRequestState;
            Drawable pDrawable = drawable;
            this.this$0.removeTileFromQueues(pState.getMapTile());
            pState.getCallback().mapTileRequestCompleted(pState, (Drawable) null);
            if (pDrawable instanceof ReusableBitmapDrawable) {
                BitmapPool.getInstance().returnDrawableToPool((ReusableBitmapDrawable) pDrawable);
            }
        }
    }
}
