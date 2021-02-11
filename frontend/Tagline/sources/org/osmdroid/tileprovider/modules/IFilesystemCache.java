package org.osmdroid.tileprovider.modules;

import android.graphics.drawable.Drawable;
import java.io.InputStream;
import org.osmdroid.tileprovider.MapTile;
import org.osmdroid.tileprovider.tilesource.ITileSource;

public interface IFilesystemCache {
    boolean exists(ITileSource iTileSource, MapTile mapTile);

    Long getExpirationTimestamp(ITileSource iTileSource, MapTile mapTile);

    Drawable loadTile(ITileSource iTileSource, MapTile mapTile) throws Exception;

    void onDetach();

    boolean remove(ITileSource iTileSource, MapTile mapTile);

    boolean saveFile(ITileSource iTileSource, MapTile mapTile, InputStream inputStream);
}
