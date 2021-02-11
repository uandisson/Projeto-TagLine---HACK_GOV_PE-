package org.osmdroid.tileprovider;

import android.content.Context;
import org.osmdroid.tileprovider.modules.IFilesystemCache;
import org.osmdroid.tileprovider.modules.INetworkAvailablityCheck;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;

public class MapTileProviderBasic extends MapTileProviderArray implements IMapTileProviderCallback {
    private final INetworkAvailablityCheck mNetworkAvailabilityCheck;
    protected IFilesystemCache tileWriter;

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public MapTileProviderBasic(Context pContext) {
        this(pContext, TileSourceFactory.DEFAULT_TILE_SOURCE);
    }

    /* JADX WARNING: Illegal instructions before constructor call */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public MapTileProviderBasic(android.content.Context r11, org.osmdroid.tileprovider.tilesource.ITileSource r12) {
        /*
            r10 = this;
            r0 = r10
            r1 = r11
            r2 = r12
            r3 = r0
            org.osmdroid.tileprovider.util.SimpleRegisterReceiver r4 = new org.osmdroid.tileprovider.util.SimpleRegisterReceiver
            r9 = r4
            r4 = r9
            r5 = r9
            r6 = r1
            r5.<init>(r6)
            org.osmdroid.tileprovider.modules.NetworkAvailabliltyCheck r5 = new org.osmdroid.tileprovider.modules.NetworkAvailabliltyCheck
            r9 = r5
            r5 = r9
            r6 = r9
            r7 = r1
            r6.<init>(r7)
            r6 = r2
            r7 = r1
            r8 = 0
            r3.<init>(r4, r5, r6, r7, r8)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.osmdroid.tileprovider.MapTileProviderBasic.<init>(android.content.Context, org.osmdroid.tileprovider.tilesource.ITileSource):void");
    }

    /* JADX WARNING: Illegal instructions before constructor call */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public MapTileProviderBasic(android.content.Context r12, org.osmdroid.tileprovider.tilesource.ITileSource r13, org.osmdroid.tileprovider.modules.IFilesystemCache r14) {
        /*
            r11 = this;
            r0 = r11
            r1 = r12
            r2 = r13
            r3 = r14
            r4 = r0
            org.osmdroid.tileprovider.util.SimpleRegisterReceiver r5 = new org.osmdroid.tileprovider.util.SimpleRegisterReceiver
            r10 = r5
            r5 = r10
            r6 = r10
            r7 = r1
            r6.<init>(r7)
            org.osmdroid.tileprovider.modules.NetworkAvailabliltyCheck r6 = new org.osmdroid.tileprovider.modules.NetworkAvailabliltyCheck
            r10 = r6
            r6 = r10
            r7 = r10
            r8 = r1
            r7.<init>(r8)
            r7 = r2
            r8 = r1
            r9 = r3
            r4.<init>(r5, r6, r7, r8, r9)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.osmdroid.tileprovider.MapTileProviderBasic.<init>(android.content.Context, org.osmdroid.tileprovider.tilesource.ITileSource, org.osmdroid.tileprovider.modules.IFilesystemCache):void");
    }

    /* JADX WARNING: Illegal instructions before constructor call */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public MapTileProviderBasic(org.osmdroid.tileprovider.IRegisterReceiver r18, org.osmdroid.tileprovider.modules.INetworkAvailablityCheck r19, org.osmdroid.tileprovider.tilesource.ITileSource r20, android.content.Context r21, org.osmdroid.tileprovider.modules.IFilesystemCache r22) {
        /*
            r17 = this;
            r0 = r17
            r1 = r18
            r2 = r19
            r3 = r20
            r4 = r21
            r5 = r22
            r11 = r0
            r12 = r3
            r13 = r1
            r11.<init>(r12, r13)
            r11 = r0
            r12 = r2
            r11.mNetworkAvailabilityCheck = r12
            r11 = r5
            if (r11 == 0) goto L_0x00a7
            r11 = r0
            r12 = r5
            r11.tileWriter = r12
        L_0x001d:
            org.osmdroid.tileprovider.modules.MapTileAssetsProvider r11 = new org.osmdroid.tileprovider.modules.MapTileAssetsProvider
            r16 = r11
            r11 = r16
            r12 = r16
            r13 = r1
            r14 = r4
            android.content.res.AssetManager r14 = r14.getAssets()
            r15 = r3
            r12.<init>(r13, r14, r15)
            r6 = r11
            r11 = r0
            java.util.List r11 = r11.mTileProviderList
            r12 = r6
            boolean r11 = r11.add(r12)
            int r11 = android.os.Build.VERSION.SDK_INT
            r12 = 10
            if (r11 >= r12) goto L_0x00cd
            org.osmdroid.tileprovider.modules.MapTileFilesystemProvider r11 = new org.osmdroid.tileprovider.modules.MapTileFilesystemProvider
            r16 = r11
            r11 = r16
            r12 = r16
            r13 = r1
            r14 = r3
            r12.<init>(r13, r14)
            r7 = r11
        L_0x004c:
            r11 = r0
            java.util.List r11 = r11.mTileProviderList
            r12 = r7
            boolean r11 = r11.add(r12)
            org.osmdroid.tileprovider.modules.MapTileFileArchiveProvider r11 = new org.osmdroid.tileprovider.modules.MapTileFileArchiveProvider
            r16 = r11
            r11 = r16
            r12 = r16
            r13 = r1
            r14 = r3
            r12.<init>(r13, r14)
            r8 = r11
            r11 = r0
            java.util.List r11 = r11.mTileProviderList
            r12 = r8
            boolean r11 = r11.add(r12)
            org.osmdroid.tileprovider.modules.MapTileDownloader r11 = new org.osmdroid.tileprovider.modules.MapTileDownloader
            r16 = r11
            r11 = r16
            r12 = r16
            r13 = r3
            r14 = r0
            org.osmdroid.tileprovider.modules.IFilesystemCache r14 = r14.tileWriter
            r15 = r2
            r12.<init>(r13, r14, r15)
            r9 = r11
            r11 = r0
            java.util.List r11 = r11.mTileProviderList
            r12 = r9
            boolean r11 = r11.add(r12)
            org.osmdroid.tileprovider.modules.MapTileApproximater r11 = new org.osmdroid.tileprovider.modules.MapTileApproximater
            r16 = r11
            r11 = r16
            r12 = r16
            r12.<init>()
            r10 = r11
            r11 = r0
            java.util.List r11 = r11.mTileProviderList
            r12 = r10
            boolean r11 = r11.add(r12)
            r11 = r10
            r12 = r6
            r11.addProvider(r12)
            r11 = r10
            r12 = r7
            r11.addProvider(r12)
            r11 = r10
            r12 = r8
            r11.addProvider(r12)
            return
        L_0x00a7:
            int r11 = android.os.Build.VERSION.SDK_INT
            r12 = 10
            if (r11 >= r12) goto L_0x00bd
            r11 = r0
            org.osmdroid.tileprovider.modules.TileWriter r12 = new org.osmdroid.tileprovider.modules.TileWriter
            r16 = r12
            r12 = r16
            r13 = r16
            r13.<init>()
            r11.tileWriter = r12
            goto L_0x001d
        L_0x00bd:
            r11 = r0
            org.osmdroid.tileprovider.modules.SqlTileWriter r12 = new org.osmdroid.tileprovider.modules.SqlTileWriter
            r16 = r12
            r12 = r16
            r13 = r16
            r13.<init>()
            r11.tileWriter = r12
            goto L_0x001d
        L_0x00cd:
            org.osmdroid.tileprovider.modules.MapTileSqlCacheProvider r11 = new org.osmdroid.tileprovider.modules.MapTileSqlCacheProvider
            r16 = r11
            r11 = r16
            r12 = r16
            r13 = r1
            r14 = r3
            r12.<init>(r13, r14)
            r7 = r11
            goto L_0x004c
        */
        throw new UnsupportedOperationException("Method not decompiled: org.osmdroid.tileprovider.MapTileProviderBasic.<init>(org.osmdroid.tileprovider.IRegisterReceiver, org.osmdroid.tileprovider.modules.INetworkAvailablityCheck, org.osmdroid.tileprovider.tilesource.ITileSource, android.content.Context, org.osmdroid.tileprovider.modules.IFilesystemCache):void");
    }

    public IFilesystemCache getTileWriter() {
        return this.tileWriter;
    }

    public void detach() {
        if (this.tileWriter != null) {
            this.tileWriter.onDetach();
        }
        this.tileWriter = null;
        super.detach();
    }

    /* access modifiers changed from: protected */
    public boolean isDowngradedMode() {
        return this.mNetworkAvailabilityCheck != null && !this.mNetworkAvailabilityCheck.getNetworkAvailable();
    }
}
