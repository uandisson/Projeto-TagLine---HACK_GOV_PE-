package org.osmdroid.tileprovider.modules;

import org.osmdroid.tileprovider.IMapTileProviderCallback;
import org.osmdroid.tileprovider.MapTileProviderArray;

public class OfflineTileProvider extends MapTileProviderArray implements IMapTileProviderCallback {
    IArchiveFile[] archives;

    /* JADX WARNING: Illegal instructions before constructor call */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public OfflineTileProvider(org.osmdroid.tileprovider.IRegisterReceiver r13, java.io.File[] r14) throws java.lang.Exception {
        /*
            r12 = this;
            r0 = r12
            r1 = r13
            r2 = r14
            r6 = r0
            r7 = r2
            r8 = 0
            r7 = r7[r8]
            java.lang.String r7 = r7.getName()
            org.osmdroid.tileprovider.tilesource.ITileSource r7 = org.osmdroid.tileprovider.tilesource.FileBasedTileSource.getSource(r7)
            r8 = r1
            r6.<init>(r7, r8)
            java.util.ArrayList r6 = new java.util.ArrayList
            r11 = r6
            r6 = r11
            r7 = r11
            r7.<init>()
            r3 = r6
            r6 = 0
            r4 = r6
        L_0x001f:
            r6 = r4
            r7 = r2
            int r7 = r7.length
            if (r6 >= r7) goto L_0x0063
            r6 = r2
            r7 = r4
            r6 = r6[r7]
            org.osmdroid.tileprovider.modules.IArchiveFile r6 = org.osmdroid.tileprovider.modules.ArchiveFileFactory.getArchiveFile(r6)
            r5 = r6
            r6 = r5
            if (r6 == 0) goto L_0x0039
            r6 = r3
            r7 = r5
            boolean r6 = r6.add(r7)
        L_0x0036:
            int r4 = r4 + 1
            goto L_0x001f
        L_0x0039:
            java.lang.String r6 = "OsmDroid"
            java.lang.StringBuilder r7 = new java.lang.StringBuilder
            r11 = r7
            r7 = r11
            r8 = r11
            r8.<init>()
            java.lang.String r8 = "Skipping "
            java.lang.StringBuilder r7 = r7.append(r8)
            r8 = r2
            r9 = r4
            r8 = r8[r9]
            java.lang.StringBuilder r7 = r7.append(r8)
            java.lang.String r8 = ", no tile provider is registered to handle the file extension"
            java.lang.StringBuilder r7 = r7.append(r8)
            java.lang.String r7 = r7.toString()
            int r6 = android.util.Log.w(r6, r7)
            goto L_0x0036
        L_0x0063:
            r6 = r0
            r7 = r3
            int r7 = r7.size()
            org.osmdroid.tileprovider.modules.IArchiveFile[] r7 = new org.osmdroid.tileprovider.modules.IArchiveFile[r7]
            r6.archives = r7
            r6 = r0
            r7 = r3
            r8 = r0
            org.osmdroid.tileprovider.modules.IArchiveFile[] r8 = r8.archives
            java.lang.Object[] r7 = r7.toArray(r8)
            org.osmdroid.tileprovider.modules.IArchiveFile[] r7 = (org.osmdroid.tileprovider.modules.IArchiveFile[]) r7
            r6.archives = r7
            org.osmdroid.tileprovider.modules.MapTileFileArchiveProvider r6 = new org.osmdroid.tileprovider.modules.MapTileFileArchiveProvider
            r11 = r6
            r6 = r11
            r7 = r11
            r8 = r1
            r9 = r0
            org.osmdroid.tileprovider.tilesource.ITileSource r9 = r9.getTileSource()
            r10 = r0
            org.osmdroid.tileprovider.modules.IArchiveFile[] r10 = r10.archives
            r7.<init>(r8, r9, r10)
            r4 = r6
            r6 = r0
            java.util.List r6 = r6.mTileProviderList
            r7 = r4
            boolean r6 = r6.add(r7)
            org.osmdroid.tileprovider.modules.MapTileApproximater r6 = new org.osmdroid.tileprovider.modules.MapTileApproximater
            r11 = r6
            r6 = r11
            r7 = r11
            r7.<init>()
            r5 = r6
            r6 = r0
            java.util.List r6 = r6.mTileProviderList
            r7 = r5
            boolean r6 = r6.add(r7)
            r6 = r5
            r7 = r4
            r6.addProvider(r7)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.osmdroid.tileprovider.modules.OfflineTileProvider.<init>(org.osmdroid.tileprovider.IRegisterReceiver, java.io.File[]):void");
    }

    public IArchiveFile[] getArchives() {
        return this.archives;
    }

    public void detach() {
        if (this.archives != null) {
            for (int i = 0; i < this.archives.length; i++) {
                this.archives[i].close();
            }
        }
        super.detach();
    }
}
