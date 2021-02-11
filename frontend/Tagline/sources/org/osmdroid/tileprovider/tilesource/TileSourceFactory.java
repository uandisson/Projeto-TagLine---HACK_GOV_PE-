package org.osmdroid.tileprovider.tilesource;

import java.util.ArrayList;
import java.util.List;
import org.osmdroid.tileprovider.MapTile;

public class TileSourceFactory {
    public static final OnlineTileSourceBase BASE_OVERLAY_NL;
    public static final OnlineTileSourceBase CLOUDMADESMALLTILES;
    public static final OnlineTileSourceBase CLOUDMADESTANDARDTILES;
    public static final OnlineTileSourceBase ChartbundleENRH;
    public static final OnlineTileSourceBase ChartbundleENRL;
    public static final OnlineTileSourceBase ChartbundleWAC;
    public static final OnlineTileSourceBase DEFAULT_TILE_SOURCE = MAPNIK;
    public static final OnlineTileSourceBase FIETS_OVERLAY_NL;
    public static final OnlineTileSourceBase HIKEBIKEMAP;
    public static final OnlineTileSourceBase MAPNIK;
    public static final OnlineTileSourceBase OPEN_SEAMAP;
    public static final OnlineTileSourceBase OpenTopo;
    public static final OnlineTileSourceBase PUBLIC_TRANSPORT;
    public static final OnlineTileSourceBase ROADS_OVERLAY_NL;
    public static final OnlineTileSourceBase USGS_SAT;
    public static final OnlineTileSourceBase USGS_TOPO;
    private static List<ITileSource> mTileSources;

    public TileSourceFactory() {
    }

    public static ITileSource getTileSource(String str) throws IllegalArgumentException {
        Throwable th;
        StringBuilder sb;
        String aName = str;
        for (ITileSource tileSource : mTileSources) {
            if (tileSource.name().equals(aName)) {
                return tileSource;
            }
        }
        Throwable th2 = th;
        new StringBuilder();
        new IllegalArgumentException(sb.append("No such tile source: ").append(aName).toString());
        throw th2;
    }

    public static boolean containsTileSource(String str) {
        String aName = str;
        for (ITileSource tileSource : mTileSources) {
            if (tileSource.name().equals(aName)) {
                return true;
            }
        }
        return false;
    }

    @Deprecated
    public static ITileSource getTileSource(int i) throws IllegalArgumentException {
        Throwable th;
        StringBuilder sb;
        int aOrdinal = i;
        for (ITileSource tileSource : mTileSources) {
            if (tileSource.ordinal() == aOrdinal) {
                return tileSource;
            }
        }
        Throwable th2 = th;
        new StringBuilder();
        new IllegalArgumentException(sb.append("No tile source at position: ").append(aOrdinal).toString());
        throw th2;
    }

    public static List<ITileSource> getTileSources() {
        return mTileSources;
    }

    public static void addTileSource(ITileSource mTileSource) {
        boolean add = mTileSources.add(mTileSource);
    }

    public static int removeTileSources(String str) {
        String aRegex = str;
        int n = 0;
        for (int i = mTileSources.size() - 1; i >= 0; i--) {
            if (mTileSources.get(i).name().matches(aRegex)) {
                ITileSource remove = mTileSources.remove(i);
                n++;
            }
        }
        return n;
    }

    static {
        OnlineTileSourceBase onlineTileSourceBase;
        OnlineTileSourceBase onlineTileSourceBase2;
        OnlineTileSourceBase onlineTileSourceBase3;
        OnlineTileSourceBase onlineTileSourceBase4;
        OnlineTileSourceBase onlineTileSourceBase5;
        OnlineTileSourceBase onlineTileSourceBase6;
        OnlineTileSourceBase onlineTileSourceBase7;
        OnlineTileSourceBase onlineTileSourceBase8;
        OnlineTileSourceBase onlineTileSourceBase9;
        OnlineTileSourceBase onlineTileSourceBase10;
        OnlineTileSourceBase onlineTileSourceBase11;
        OnlineTileSourceBase onlineTileSourceBase12;
        OnlineTileSourceBase onlineTileSourceBase13;
        OnlineTileSourceBase onlineTileSourceBase14;
        OnlineTileSourceBase onlineTileSourceBase15;
        List<ITileSource> list;
        OnlineTileSourceBase onlineTileSourceBase16 = onlineTileSourceBase;
        String[] strArr = new String[3];
        strArr[0] = "http://a.tile.openstreetmap.org/";
        String[] strArr2 = strArr;
        strArr2[1] = "http://b.tile.openstreetmap.org/";
        String[] strArr3 = strArr2;
        strArr3[2] = "http://c.tile.openstreetmap.org/";
        new XYTileSource("Mapnik", 0, 19, 256, ".png", strArr3, "© OpenStreetMap contributors");
        MAPNIK = onlineTileSourceBase16;
        OnlineTileSourceBase onlineTileSourceBase17 = onlineTileSourceBase2;
        new XYTileSource("OSMPublicTransport", 0, 17, 256, ".png", new String[]{"http://openptmap.org/tiles/"}, "© OpenStreetMap contributors");
        PUBLIC_TRANSPORT = onlineTileSourceBase17;
        OnlineTileSourceBase onlineTileSourceBase18 = onlineTileSourceBase3;
        String[] strArr4 = new String[3];
        strArr4[0] = "http://a.tile.cloudmade.com/%s/%d/%d/%d/%d/%d%s?token=%s";
        String[] strArr5 = strArr4;
        strArr5[1] = "http://b.tile.cloudmade.com/%s/%d/%d/%d/%d/%d%s?token=%s";
        String[] strArr6 = strArr5;
        strArr6[2] = "http://c.tile.cloudmade.com/%s/%d/%d/%d/%d/%d%s?token=%s";
        new CloudmadeTileSource("CloudMadeStandardTiles", 0, 18, 256, ".png", strArr6);
        CLOUDMADESTANDARDTILES = onlineTileSourceBase18;
        OnlineTileSourceBase onlineTileSourceBase19 = onlineTileSourceBase4;
        String[] strArr7 = new String[3];
        strArr7[0] = "http://a.tile.cloudmade.com/%s/%d/%d/%d/%d/%d%s?token=%s";
        String[] strArr8 = strArr7;
        strArr8[1] = "http://b.tile.cloudmade.com/%s/%d/%d/%d/%d/%d%s?token=%s";
        String[] strArr9 = strArr8;
        strArr9[2] = "http://c.tile.cloudmade.com/%s/%d/%d/%d/%d/%d%s?token=%s";
        new CloudmadeTileSource("CloudMadeSmallTiles", 0, 21, 64, ".png", strArr9);
        CLOUDMADESMALLTILES = onlineTileSourceBase19;
        OnlineTileSourceBase onlineTileSourceBase20 = onlineTileSourceBase5;
        new XYTileSource("Fiets", 3, 18, 256, ".png", new String[]{"http://overlay.openstreetmap.nl/openfietskaart-overlay/"}, "© OpenStreetMap contributors");
        FIETS_OVERLAY_NL = onlineTileSourceBase20;
        OnlineTileSourceBase onlineTileSourceBase21 = onlineTileSourceBase6;
        new XYTileSource("BaseNL", 0, 18, 256, ".png", new String[]{"http://overlay.openstreetmap.nl/basemap/"});
        BASE_OVERLAY_NL = onlineTileSourceBase21;
        OnlineTileSourceBase onlineTileSourceBase22 = onlineTileSourceBase7;
        new XYTileSource("RoadsNL", 0, 18, 256, ".png", new String[]{"http://overlay.openstreetmap.nl/roads/"}, "© OpenStreetMap contributors");
        ROADS_OVERLAY_NL = onlineTileSourceBase22;
        OnlineTileSourceBase onlineTileSourceBase23 = onlineTileSourceBase8;
        String[] strArr10 = new String[3];
        strArr10[0] = "http://a.tiles.wmflabs.org/hikebike/";
        String[] strArr11 = strArr10;
        strArr11[1] = "http://b.tiles.wmflabs.org/hikebike/";
        String[] strArr12 = strArr11;
        strArr12[2] = "http://c.tiles.wmflabs.org/hikebike/";
        new XYTileSource("HikeBikeMap", 0, 18, 256, ".png", strArr12);
        HIKEBIKEMAP = onlineTileSourceBase23;
        OnlineTileSourceBase onlineTileSourceBase24 = onlineTileSourceBase9;
        new XYTileSource("OpenSeaMap", 3, 18, 256, ".png", new String[]{"http://tiles.openseamap.org/seamark/"}, "OpenSeaMap");
        OPEN_SEAMAP = onlineTileSourceBase24;
        OnlineTileSourceBase onlineTileSourceBase25 = onlineTileSourceBase10;
        new OnlineTileSourceBase("USGS National Map Topo", 0, 15, 256, "", new String[]{"https://basemap.nationalmap.gov/arcgis/rest/services/USGSTopo/MapServer/tile/"}, "USGS") {
            public String getTileURLString(MapTile mapTile) {
                StringBuilder sb;
                MapTile aTile = mapTile;
                new StringBuilder();
                return sb.append(getBaseUrl()).append(aTile.getZoomLevel()).append("/").append(aTile.getY()).append("/").append(aTile.getX()).toString();
            }
        };
        USGS_TOPO = onlineTileSourceBase25;
        OnlineTileSourceBase onlineTileSourceBase26 = onlineTileSourceBase11;
        new OnlineTileSourceBase("USGS National Map Sat", 0, 15, 256, "", new String[]{"https://basemap.nationalmap.gov/arcgis/rest/services/USGSImageryTopo/MapServer/tile/"}, "USGS") {
            public String getTileURLString(MapTile mapTile) {
                StringBuilder sb;
                MapTile aTile = mapTile;
                new StringBuilder();
                return sb.append(getBaseUrl()).append(aTile.getZoomLevel()).append("/").append(aTile.getY()).append("/").append(aTile.getX()).toString();
            }
        };
        USGS_SAT = onlineTileSourceBase26;
        OnlineTileSourceBase onlineTileSourceBase27 = onlineTileSourceBase12;
        new XYTileSource("ChartbundleWAC", 4, 12, 256, ".png?type=google", new String[]{"http://wms.chartbundle.com/tms/v1.0/wac/"}, "chartbundle.com");
        ChartbundleWAC = onlineTileSourceBase27;
        OnlineTileSourceBase onlineTileSourceBase28 = onlineTileSourceBase13;
        String[] strArr13 = new String[2];
        strArr13[0] = "http://wms.chartbundle.com/tms/v1.0/enrh/";
        String[] strArr14 = strArr13;
        strArr14[1] = "chartbundle.com";
        new XYTileSource("ChartbundleENRH", 4, 12, 256, ".png?type=google", strArr14);
        ChartbundleENRH = onlineTileSourceBase28;
        OnlineTileSourceBase onlineTileSourceBase29 = onlineTileSourceBase14;
        String[] strArr15 = new String[2];
        strArr15[0] = "http://wms.chartbundle.com/tms/v1.0/enrl/";
        String[] strArr16 = strArr15;
        strArr16[1] = "chartbundle.com";
        new XYTileSource("ChartbundleENRL", 4, 12, 256, ".png?type=google", strArr16);
        ChartbundleENRL = onlineTileSourceBase29;
        OnlineTileSourceBase onlineTileSourceBase30 = onlineTileSourceBase15;
        new XYTileSource("OpenTopoMap", 0, 19, 256, ".png", new String[]{"https://opentopomap.org/"}, "Kartendaten: © OpenStreetMap-Mitwirkende, SRTM | Kartendarstellung: © OpenTopoMap (CC-BY-SA)");
        OpenTopo = onlineTileSourceBase30;
        new ArrayList();
        mTileSources = list;
        boolean add = mTileSources.add(MAPNIK);
        boolean add2 = mTileSources.add(PUBLIC_TRANSPORT);
        boolean add3 = mTileSources.add(HIKEBIKEMAP);
        boolean add4 = mTileSources.add(USGS_TOPO);
        boolean add5 = mTileSources.add(USGS_SAT);
        boolean add6 = mTileSources.add(ChartbundleWAC);
        boolean add7 = mTileSources.add(ChartbundleENRH);
        boolean add8 = mTileSources.add(ChartbundleENRL);
        boolean add9 = mTileSources.add(OpenTopo);
    }
}
