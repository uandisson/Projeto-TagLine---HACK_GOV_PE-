package org.osmdroid.tileprovider.tilesource;

import android.content.Context;
import android.support.p000v4.app.NotificationCompat;
import org.osmdroid.tileprovider.MapTile;
import org.osmdroid.tileprovider.util.ManifestUtil;

public class ThunderforestTileSource extends OnlineTileSourceBase {
    public static final int CYCLE = 0;
    public static final int LANDSCAPE = 2;
    public static final int MOBILE_ATLAS = 7;
    public static final int NEIGHBOURHOOD = 8;
    public static final int OUTDOORS = 3;
    public static final int PIONEER = 6;
    public static final int SPINAL_MAP = 5;
    private static final String THUNDERFOREST_MAPID = "THUNDERFOREST_MAPID";
    public static final int TRANSPORT = 1;
    public static final int TRANSPORT_DARK = 4;
    private static final String[] baseUrl;
    private static final String[] uiMap;
    private static final String[] urlMap;
    private final int mMap;
    private final String mMapId;

    static {
        String[] strArr = new String[9];
        strArr[0] = "cycle";
        String[] strArr2 = strArr;
        strArr2[1] = NotificationCompat.CATEGORY_TRANSPORT;
        String[] strArr3 = strArr2;
        strArr3[2] = "landscape";
        String[] strArr4 = strArr3;
        strArr4[3] = "outdoors";
        String[] strArr5 = strArr4;
        strArr5[4] = "transport-dark";
        String[] strArr6 = strArr5;
        strArr6[5] = "spinal-map";
        String[] strArr7 = strArr6;
        strArr7[6] = "pioneer";
        String[] strArr8 = strArr7;
        strArr8[7] = "mobile-atlas";
        String[] strArr9 = strArr8;
        strArr9[8] = "neighbourhood";
        urlMap = strArr9;
        String[] strArr10 = new String[9];
        strArr10[0] = "CycleMap";
        String[] strArr11 = strArr10;
        strArr11[1] = "Transport";
        String[] strArr12 = strArr11;
        strArr12[2] = "Landscape";
        String[] strArr13 = strArr12;
        strArr13[3] = "Outdoors";
        String[] strArr14 = strArr13;
        strArr14[4] = "TransportDark";
        String[] strArr15 = strArr14;
        strArr15[5] = "Spinal";
        String[] strArr16 = strArr15;
        strArr16[6] = "Pioneer";
        String[] strArr17 = strArr16;
        strArr17[7] = "MobileAtlas";
        String[] strArr18 = strArr17;
        strArr18[8] = "Neighbourhood";
        uiMap = strArr18;
        String[] strArr19 = new String[3];
        strArr19[0] = "https://a.tile.thunderforest.com/{map}/";
        String[] strArr20 = strArr19;
        strArr20[1] = "https://b.tile.thunderforest.com/{map}/";
        String[] strArr21 = strArr20;
        strArr21[2] = "https://c.tile.thunderforest.com/{map}/";
        baseUrl = strArr21;
    }

    public static final String mapName(int i) {
        int m = i;
        if (m < 0 || m >= uiMap.length) {
            return "";
        }
        return uiMap[m];
    }

    /* JADX WARNING: Illegal instructions before constructor call */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public ThunderforestTileSource(android.content.Context r12, int r13) {
        /*
            r11 = this;
            r0 = r11
            r1 = r12
            r2 = r13
            r3 = r0
            java.lang.String[] r4 = uiMap
            r5 = r2
            r4 = r4[r5]
            r5 = 0
            r6 = 17
            r7 = 256(0x100, float:3.59E-43)
            java.lang.String r8 = ".png"
            java.lang.String[] r9 = baseUrl
            java.lang.String r10 = "Maps © Thunderforest, Data © OpenStreetMap contributors."
            r3.<init>(r4, r5, r6, r7, r8, r9, r10)
            r3 = r0
            r4 = r2
            r3.mMap = r4
            r3 = r0
            r4 = r0
            r5 = r1
            java.lang.String r4 = r4.retrieveMapId(r5)
            r3.mMapId = r4
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.osmdroid.tileprovider.tilesource.ThunderforestTileSource.<init>(android.content.Context, int):void");
    }

    public final String retrieveMapId(Context aContext) {
        return ManifestUtil.retrieveKey(aContext, THUNDERFOREST_MAPID);
    }

    public String getTileURLString(MapTile mapTile) {
        StringBuilder sb;
        MapTile aMapTile = mapTile;
        new StringBuilder(getBaseUrl().replace("{map}", urlMap[this.mMap]));
        StringBuilder url = sb;
        StringBuilder append = url.append(aMapTile.getZoomLevel());
        StringBuilder append2 = url.append("/");
        StringBuilder append3 = url.append(aMapTile.getX());
        StringBuilder append4 = url.append("/");
        StringBuilder append5 = url.append(aMapTile.getY());
        StringBuilder append6 = url.append(".png?");
        StringBuilder append7 = url.append("apikey=").append(this.mMapId);
        return url.toString();
    }

    public static boolean haveMapId(Context aContext) {
        return !ManifestUtil.retrieveKey(aContext, THUNDERFOREST_MAPID).equals("");
    }
}
