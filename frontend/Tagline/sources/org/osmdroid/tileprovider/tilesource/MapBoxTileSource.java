package org.osmdroid.tileprovider.tilesource;

import android.content.Context;
import org.osmdroid.tileprovider.MapTile;
import org.osmdroid.tileprovider.util.ManifestUtil;

public class MapBoxTileSource extends OnlineTileSourceBase {
    private static final String ACCESS_TOKEN = "MAPBOX_ACCESS_TOKEN";
    private static final String MAPBOX_MAPID = "MAPBOX_MAPID";
    private static final String[] mapBoxBaseUrl = {"http://api.tiles.mapbox.com/v4/"};
    private String accessToken;
    private String mapBoxMapId = "";

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public MapBoxTileSource() {
        super("mapbox", 1, 19, 256, ".png", mapBoxBaseUrl);
    }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public MapBoxTileSource(Context context) {
        super("mapbox", 1, 19, 256, ".png", mapBoxBaseUrl);
        StringBuilder sb;
        Context ctx = context;
        retrieveAccessToken(ctx);
        retrieveMapBoxMapId(ctx);
        new StringBuilder();
        this.mName = sb.append("mapbox").append(this.mapBoxMapId).toString();
    }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public MapBoxTileSource(String mapboxid, String accesstoken) {
        super("mapbox", 1, 19, 256, ".png", mapBoxBaseUrl);
        StringBuilder sb;
        this.accessToken = accesstoken;
        this.mapBoxMapId = mapboxid;
        new StringBuilder();
        this.mName = sb.append("mapbox").append(this.mapBoxMapId).toString();
    }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public MapBoxTileSource(String name, int zoomMinLevel, int zoomMaxLevel, int tileSizePixels, String imageFilenameEnding) {
        super(name, zoomMinLevel, zoomMaxLevel, tileSizePixels, imageFilenameEnding, mapBoxBaseUrl);
    }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public MapBoxTileSource(String name, int zoomMinLevel, int zoomMaxLevel, int tileSizePixels, String imageFilenameEnding, String str, String mapBoxVersionBaseUrl) {
        super(name, zoomMinLevel, zoomMaxLevel, tileSizePixels, imageFilenameEnding, new String[]{mapBoxVersionBaseUrl});
        String str2 = str;
    }

    public final void retrieveMapBoxMapId(Context aContext) {
        String retrieveKey = ManifestUtil.retrieveKey(aContext, MAPBOX_MAPID);
        this.mapBoxMapId = retrieveKey;
    }

    public final void retrieveAccessToken(Context aContext) {
        String retrieveKey = ManifestUtil.retrieveKey(aContext, ACCESS_TOKEN);
        this.accessToken = retrieveKey;
    }

    public void setMapboxMapid(String key) {
        StringBuilder sb;
        this.mapBoxMapId = key;
        new StringBuilder();
        this.mName = sb.append("mapbox").append(this.mapBoxMapId).toString();
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
