package org.osmdroid.tileprovider.tilesource;

import android.content.Context;
import org.osmdroid.tileprovider.MapTile;
import org.osmdroid.tileprovider.util.ManifestUtil;

public class MapQuestTileSource extends OnlineTileSourceBase {
    private static final String ACCESS_TOKEN = "MAPQUEST_ACCESS_TOKEN";
    private static final String MAPBOX_MAPID = "MAPQUEST_MAPID";
    private static final String[] mapBoxBaseUrl = {"http://api.tiles.mapbox.com/v4/"};
    private String accessToken;
    private String mapBoxMapId = "mapquest.streets-mb";

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public MapQuestTileSource(Context context) {
        super("MapQuest", 1, 19, 256, ".png", mapBoxBaseUrl, "MapQuest");
        StringBuilder sb;
        Context ctx = context;
        retrieveAccessToken(ctx);
        retrieveMapBoxMapId(ctx);
        new StringBuilder();
        this.mName = sb.append("MapQuest").append(this.mapBoxMapId).toString();
    }

    /* JADX WARNING: Illegal instructions before constructor call */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public MapQuestTileSource(java.lang.String r13, java.lang.String r14) {
        /*
            r12 = this;
            r0 = r12
            r1 = r13
            r2 = r14
            r3 = r0
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r11 = r4
            r4 = r11
            r5 = r11
            r5.<init>()
            java.lang.String r5 = "MapQuest"
            java.lang.StringBuilder r4 = r4.append(r5)
            r5 = r1
            java.lang.StringBuilder r4 = r4.append(r5)
            java.lang.String r4 = r4.toString()
            r5 = 1
            r6 = 19
            r7 = 256(0x100, float:3.59E-43)
            java.lang.String r8 = ".png"
            java.lang.String[] r9 = mapBoxBaseUrl
            java.lang.String r10 = "MapQuest"
            r3.<init>(r4, r5, r6, r7, r8, r9, r10)
            r3 = r0
            java.lang.String r4 = "mapquest.streets-mb"
            r3.mapBoxMapId = r4
            r3 = r0
            r4 = r2
            r3.accessToken = r4
            r3 = r0
            r4 = r1
            r3.mapBoxMapId = r4
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.osmdroid.tileprovider.tilesource.MapQuestTileSource.<init>(java.lang.String, java.lang.String):void");
    }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public MapQuestTileSource(String name, int zoomMinLevel, int zoomMaxLevel, int tileSizePixels, String imageFilenameEnding) {
        super(name, zoomMinLevel, zoomMaxLevel, tileSizePixels, imageFilenameEnding, mapBoxBaseUrl, "MapQuest");
    }

    /* JADX WARNING: Illegal instructions before constructor call */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public MapQuestTileSource(java.lang.String r20, int r21, int r22, int r23, java.lang.String r24, java.lang.String r25, java.lang.String r26) {
        /*
            r19 = this;
            r0 = r19
            r1 = r20
            r2 = r21
            r3 = r22
            r4 = r23
            r5 = r24
            r6 = r25
            r7 = r26
            r8 = r0
            java.lang.StringBuilder r9 = new java.lang.StringBuilder
            r18 = r9
            r9 = r18
            r10 = r18
            r10.<init>()
            r10 = r1
            java.lang.StringBuilder r9 = r9.append(r10)
            r10 = r6
            java.lang.StringBuilder r9 = r9.append(r10)
            java.lang.String r9 = r9.toString()
            r10 = r2
            r11 = r3
            r12 = r4
            r13 = r5
            r14 = 1
            java.lang.String[] r14 = new java.lang.String[r14]
            r18 = r14
            r14 = r18
            r15 = r18
            r16 = 0
            r17 = r7
            r15[r16] = r17
            java.lang.String r15 = "MapQuest"
            r8.<init>(r9, r10, r11, r12, r13, r14, r15)
            r8 = r0
            java.lang.String r9 = "mapquest.streets-mb"
            r8.mapBoxMapId = r9
            r8 = r0
            r9 = r6
            r8.mapBoxMapId = r9
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.osmdroid.tileprovider.tilesource.MapQuestTileSource.<init>(java.lang.String, int, int, int, java.lang.String, java.lang.String, java.lang.String):void");
    }

    public final void retrieveMapBoxMapId(Context aContext) {
        String temp = ManifestUtil.retrieveKey(aContext, MAPBOX_MAPID);
        if (temp != null && temp.length() > 0) {
            this.mapBoxMapId = temp;
        }
    }

    public final void retrieveAccessToken(Context aContext) {
        String retrieveKey = ManifestUtil.retrieveKey(aContext, ACCESS_TOKEN);
        this.accessToken = retrieveKey;
    }

    public void setMapboxMapid(String key) {
        String str = key;
        this.mapBoxMapId = str;
    }

    public String getMapBoxMapId() {
        return this.mapBoxMapId;
    }

    public String getTileURLString(MapTile mapTile) {
        StringBuilder sb;
        MapTile aMapTile = mapTile;
        new StringBuilder(getBaseUrl());
        StringBuilder url = sb;
        StringBuilder append = url.append(getMapBoxMapId());
        StringBuilder append2 = url.append("/");
        StringBuilder append3 = url.append(aMapTile.getZoomLevel());
        StringBuilder append4 = url.append("/");
        StringBuilder append5 = url.append(aMapTile.getX());
        StringBuilder append6 = url.append("/");
        StringBuilder append7 = url.append(aMapTile.getY());
        StringBuilder append8 = url.append(".png");
        StringBuilder append9 = url.append("?access_token=").append(getAccessToken());
        return url.toString();
    }

    public String getAccessToken() {
        return this.accessToken;
    }

    public void setAccessToken(String accessTokeninput) {
        String str = accessTokeninput;
        this.accessToken = str;
    }
}
