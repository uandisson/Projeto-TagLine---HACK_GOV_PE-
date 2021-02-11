package org.osmdroid.tileprovider.tilesource;

import org.osmdroid.tileprovider.MapTile;

public class XYTileSource extends OnlineTileSourceBase {
    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public XYTileSource(String aName, int aZoomMinLevel, int aZoomMaxLevel, int aTileSizePixels, String aImageFilenameEnding, String[] aBaseUrl) {
        this(aName, aZoomMinLevel, aZoomMaxLevel, aTileSizePixels, aImageFilenameEnding, aBaseUrl, (String) null);
    }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public XYTileSource(String aName, int aZoomMinLevel, int aZoomMaxLevel, int aTileSizePixels, String aImageFilenameEnding, String[] aBaseUrl, String copyright) {
        super(aName, aZoomMinLevel, aZoomMaxLevel, aTileSizePixels, aImageFilenameEnding, aBaseUrl, copyright);
    }

    public String toString() {
        return name();
    }

    public String getTileURLString(MapTile mapTile) {
        StringBuilder sb;
        MapTile aTile = mapTile;
        new StringBuilder();
        return sb.append(getBaseUrl()).append(aTile.getZoomLevel()).append("/").append(aTile.getX()).append("/").append(aTile.getY()).append(this.mImageFilenameEnding).toString();
    }
}
