package org.osmdroid.tileprovider.tilesource;

import org.osmdroid.tileprovider.MapTile;

public abstract class TMSOnlineTileSourceBase extends OnlineTileSourceBase {
    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public TMSOnlineTileSourceBase(String aName, int aZoomMinLevel, int aZoomMaxLevel, int aTileSizePixels, String aImageFilenameEnding, String[] aBaseUrl) {
        super(aName, aZoomMinLevel, aZoomMaxLevel, aTileSizePixels, aImageFilenameEnding, aBaseUrl);
    }

    public String getTileRelativeFilenameString(MapTile mapTile) {
        StringBuilder sb;
        MapTile tile = mapTile;
        int y_tms = ((1 << tile.getZoomLevel()) - tile.getY()) - 1;
        new StringBuilder();
        StringBuilder sb2 = sb;
        StringBuilder append = sb2.append(pathBase());
        StringBuilder append2 = sb2.append('/');
        StringBuilder append3 = sb2.append(tile.getZoomLevel());
        StringBuilder append4 = sb2.append('/');
        StringBuilder append5 = sb2.append(tile.getX());
        StringBuilder append6 = sb2.append('/');
        StringBuilder append7 = sb2.append(y_tms);
        StringBuilder append8 = sb2.append(imageFilenameEnding());
        return sb2.toString();
    }
}
