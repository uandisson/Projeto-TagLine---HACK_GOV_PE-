package org.osmdroid.tileprovider.tilesource;

import android.content.Context;
import org.osmdroid.tileprovider.MapTile;
import org.osmdroid.tileprovider.util.ManifestUtil;

public class HEREWeGoTileSource extends OnlineTileSourceBase {
    private static final String APPCODE = "HEREWEGO_APPCODE";
    private static final String HEREWEGO_APPID = "HEREWEGO_APPID";
    private static final String HEREWEGO_DOMAIN_OVERRIDE = "HEREWEGO_OVERRIDE";
    private static final String HEREWEGO_MAPID = "HEREWEGO_MAPID";
    private static final String[] mapBoxBaseUrl;
    private String appCode = "";
    private String appId = "";
    private String domainOverride = "aerial.maps.cit.api.here.com";
    private String herewegoMapId = "hybrid.day";

    static {
        String[] strArr = new String[4];
        strArr[0] = "http://1.{domain}/maptile/2.1/maptile/newest/";
        String[] strArr2 = strArr;
        strArr2[1] = "http://2.{domain}/maptile/2.1/maptile/newest/";
        String[] strArr3 = strArr2;
        strArr3[2] = "http://3.{domain}/maptile/2.1/maptile/newest/";
        String[] strArr4 = strArr3;
        strArr4[3] = "http://4.{domain}/maptile/2.1/maptile/newest/";
        mapBoxBaseUrl = strArr4;
    }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public HEREWeGoTileSource() {
        super("herewego", 1, 20, 256, ".png", mapBoxBaseUrl, "© 1987 - 2017 HERE. All rights reserved.");
    }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public HEREWeGoTileSource(Context context) {
        super("herewego", 1, 20, 256, ".png", mapBoxBaseUrl, "© 1987 - 2017 HERE. All rights reserved.");
        StringBuilder sb;
        Context ctx = context;
        retrieveAppId(ctx);
        retrieveMapBoxMapId(ctx);
        retrieveAppCode(ctx);
        retrieveDomainOverride(ctx);
        new StringBuilder();
        this.mName = sb.append("herewego").append(this.herewegoMapId).toString();
    }

    private void retrieveDomainOverride(Context aContext) {
        String temp = ManifestUtil.retrieveKey(aContext, HEREWEGO_DOMAIN_OVERRIDE);
        if (temp != null && temp.length() > 0) {
            this.domainOverride = temp;
        }
    }

    public void setDomainOverride(String hostname) {
        String str = hostname;
        this.domainOverride = str;
    }

    /* JADX WARNING: Illegal instructions before constructor call */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public HEREWeGoTileSource(java.lang.String r14, java.lang.String r15, java.lang.String r16) {
        /*
            r13 = this;
            r0 = r13
            r1 = r14
            r2 = r15
            r3 = r16
            r4 = r0
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r12 = r5
            r5 = r12
            r6 = r12
            r6.<init>()
            java.lang.String r6 = "herewego"
            java.lang.StringBuilder r5 = r5.append(r6)
            r6 = r1
            java.lang.StringBuilder r5 = r5.append(r6)
            java.lang.String r5 = r5.toString()
            r6 = 1
            r7 = 20
            r8 = 256(0x100, float:3.59E-43)
            java.lang.String r9 = ".png"
            java.lang.String[] r10 = mapBoxBaseUrl
            java.lang.String r11 = "© 1987 - 2017 HERE. All rights reserved."
            r4.<init>(r5, r6, r7, r8, r9, r10, r11)
            r4 = r0
            java.lang.String r5 = "hybrid.day"
            r4.herewegoMapId = r5
            r4 = r0
            java.lang.String r5 = ""
            r4.appId = r5
            r4 = r0
            java.lang.String r5 = ""
            r4.appCode = r5
            r4 = r0
            java.lang.String r5 = "aerial.maps.cit.api.here.com"
            r4.domainOverride = r5
            r4 = r0
            r5 = r2
            r4.appId = r5
            r4 = r0
            r5 = r1
            r4.herewegoMapId = r5
            r4 = r0
            r5 = r3
            r4.appCode = r5
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.osmdroid.tileprovider.tilesource.HEREWeGoTileSource.<init>(java.lang.String, java.lang.String, java.lang.String):void");
    }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public HEREWeGoTileSource(String name, int zoomMinLevel, int zoomMaxLevel, int tileSizePixels, String imageFilenameEnding) {
        super(name, zoomMinLevel, zoomMaxLevel, tileSizePixels, imageFilenameEnding, mapBoxBaseUrl, "© 1987 - 2017 HERE. All rights reserved.");
    }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public HEREWeGoTileSource(String name, int zoomMinLevel, int zoomMaxLevel, int tileSizePixels, String imageFilenameEnding, String str, String mapBoxVersionBaseUrl) {
        super(name, zoomMinLevel, zoomMaxLevel, tileSizePixels, imageFilenameEnding, new String[]{mapBoxVersionBaseUrl}, "© 1987 - 2017 HERE. All rights reserved.");
        String str2 = str;
    }

    public final void retrieveAppCode(Context aContext) {
        String retrieveKey = ManifestUtil.retrieveKey(aContext, APPCODE);
        this.appCode = retrieveKey;
    }

    public final void retrieveMapBoxMapId(Context aContext) {
        String retrieveKey = ManifestUtil.retrieveKey(aContext, HEREWEGO_MAPID);
        this.herewegoMapId = retrieveKey;
    }

    public final void retrieveAppId(Context aContext) {
        String retrieveKey = ManifestUtil.retrieveKey(aContext, HEREWEGO_APPID);
        this.appId = retrieveKey;
    }

    public void setHereWeGoMapid(String key) {
        StringBuilder sb;
        this.herewegoMapId = key;
        new StringBuilder();
        this.mName = sb.append("herewego").append(this.herewegoMapId).toString();
    }

    public String getHerewegoMapId() {
        return this.herewegoMapId;
    }

    public String getTileURLString(MapTile mapTile) {
        StringBuilder sb;
        MapTile aMapTile = mapTile;
        new StringBuilder(getBaseUrl().replace("{domain}", this.domainOverride));
        StringBuilder url = sb;
        StringBuilder append = url.append(getHerewegoMapId());
        StringBuilder append2 = url.append("/");
        StringBuilder append3 = url.append(aMapTile.getZoomLevel());
        StringBuilder append4 = url.append("/");
        StringBuilder append5 = url.append(aMapTile.getX());
        StringBuilder append6 = url.append("/");
        StringBuilder append7 = url.append(aMapTile.getY());
        StringBuilder append8 = url.append("/").append(getTileSizePixels()).append("/png8?");
        StringBuilder append9 = url.append("app_id=").append(getAppId());
        StringBuilder append10 = url.append("&app_code=").append(getAppCode());
        StringBuilder append11 = url.append("&lg=pt-BR");
        return url.toString();
    }

    public String getAppId() {
        return this.appId;
    }

    public void setAppId(String accessTokeninput) {
        String str = accessTokeninput;
        this.appId = str;
    }

    public String getAppCode() {
        return this.appCode;
    }

    public void setAppCode(String appCode2) {
        String str = appCode2;
        this.appCode = str;
    }
}
