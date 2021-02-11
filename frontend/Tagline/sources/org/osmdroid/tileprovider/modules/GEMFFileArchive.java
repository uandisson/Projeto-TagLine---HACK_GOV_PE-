package org.osmdroid.tileprovider.modules;

import android.util.Log;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Set;
import org.osmdroid.api.IMapView;
import org.osmdroid.tileprovider.MapTile;
import org.osmdroid.tileprovider.tilesource.ITileSource;
import org.osmdroid.util.GEMFFile;

public class GEMFFileArchive implements IArchiveFile {
    private GEMFFile mFile;

    public GEMFFileArchive() {
    }

    private GEMFFileArchive(File pFile) throws FileNotFoundException, IOException {
        GEMFFile gEMFFile;
        new GEMFFile(pFile);
        this.mFile = gEMFFile;
    }

    public static GEMFFileArchive getGEMFFileArchive(File pFile) throws FileNotFoundException, IOException {
        GEMFFileArchive gEMFFileArchive;
        new GEMFFileArchive(pFile);
        return gEMFFileArchive;
    }

    public void init(File pFile) throws Exception {
        GEMFFile gEMFFile;
        GEMFFile gEMFFile2 = gEMFFile;
        new GEMFFile(pFile);
        this.mFile = gEMFFile2;
    }

    public InputStream getInputStream(ITileSource iTileSource, MapTile mapTile) {
        ITileSource iTileSource2 = iTileSource;
        MapTile pTile = mapTile;
        return this.mFile.getInputStream(pTile.getX(), pTile.getY(), pTile.getZoomLevel());
    }

    public Set<String> getTileSources() {
        Set<String> set;
        new HashSet();
        Set<String> ret = set;
        try {
            boolean addAll = ret.addAll(this.mFile.getSources().values());
        } catch (Exception e) {
            int w = Log.w(IMapView.LOGTAG, "Error getting tile sources: ", e);
        }
        return ret;
    }

    public void close() {
        try {
            this.mFile.close();
        } catch (IOException e) {
            IOException iOException = e;
        }
    }

    public String toString() {
        StringBuilder sb;
        new StringBuilder();
        return sb.append("GEMFFileArchive [mGEMFFile=").append(this.mFile.getName()).append("]").toString();
    }
}
