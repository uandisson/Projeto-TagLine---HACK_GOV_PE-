package org.osmdroid.tileprovider.tilesource;

public class FileBasedTileSource extends XYTileSource {
    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public FileBasedTileSource(String aName, int aZoomMinLevel, int aZoomMaxLevel, int aTileSizePixels, String aImageFilenameEnding, String[] aBaseUrl) {
        super(aName, aZoomMinLevel, aZoomMaxLevel, aTileSizePixels, aImageFilenameEnding, aBaseUrl);
    }

    public static ITileSource getSource(String str) {
        ITileSource iTileSource;
        String name = str;
        if (name.contains(".")) {
            name = name.substring(0, name.indexOf("."));
        }
        ITileSource iTileSource2 = iTileSource;
        new FileBasedTileSource(name, 0, 18, 256, ".png", new String[]{"http://localhost"});
        return iTileSource2;
    }
}
