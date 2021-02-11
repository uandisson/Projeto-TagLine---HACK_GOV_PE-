package org.osmdroid.tileprovider.tilesource;

import org.osmdroid.tileprovider.MapTile;

public class QuadTreeTileSource extends OnlineTileSourceBase {
    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public QuadTreeTileSource(String aName, int aZoomMinLevel, int aZoomMaxLevel, int aTileSizePixels, String aImageFilenameEnding, String[] aBaseUrl) {
        super(aName, aZoomMinLevel, aZoomMaxLevel, aTileSizePixels, aImageFilenameEnding, aBaseUrl);
    }

    public String getTileURLString(MapTile aTile) {
        StringBuilder sb;
        new StringBuilder();
        return sb.append(getBaseUrl()).append(quadTree(aTile)).append(this.mImageFilenameEnding).toString();
    }

    /* access modifiers changed from: protected */
    public String quadTree(MapTile mapTile) {
        StringBuilder sb;
        StringBuilder sb2;
        MapTile aTile = mapTile;
        new StringBuilder();
        StringBuilder quadKey = sb;
        for (int i = aTile.getZoomLevel(); i > 0; i--) {
            int digit = 0;
            int mask = 1 << (i - 1);
            if ((aTile.getX() & mask) != 0) {
                digit = 0 + 1;
            }
            if ((aTile.getY() & mask) != 0) {
                digit += 2;
            }
            new StringBuilder();
            StringBuilder append = quadKey.append(sb2.append("").append(digit).toString());
        }
        return quadKey.toString();
    }
}
