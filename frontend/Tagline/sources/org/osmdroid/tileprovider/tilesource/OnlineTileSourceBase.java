package org.osmdroid.tileprovider.tilesource;

import org.osmdroid.tileprovider.MapTile;

public abstract class OnlineTileSourceBase extends BitmapTileSourceBase {
    private final String[] mBaseUrls;

    public abstract String getTileURLString(MapTile mapTile);

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public OnlineTileSourceBase(String aName, int aZoomMinLevel, int aZoomMaxLevel, int aTileSizePixels, String aImageFilenameEnding, String[] aBaseUrl) {
        this(aName, aZoomMinLevel, aZoomMaxLevel, aTileSizePixels, aImageFilenameEnding, aBaseUrl, (String) null);
    }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public OnlineTileSourceBase(String aName, int aZoomMinLevel, int aZoomMaxLevel, int aTileSizePixels, String aImageFilenameEnding, String[] aBaseUrl, String copyyright) {
        super(aName, aZoomMinLevel, aZoomMaxLevel, aTileSizePixels, aImageFilenameEnding, copyyright);
        this.mBaseUrls = aBaseUrl;
    }

    public String getBaseUrl() {
        return this.mBaseUrls[this.random.nextInt(this.mBaseUrls.length)];
    }
}
