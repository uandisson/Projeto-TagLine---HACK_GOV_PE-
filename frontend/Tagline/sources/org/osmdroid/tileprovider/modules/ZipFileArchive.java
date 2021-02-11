package org.osmdroid.tileprovider.modules;

import android.util.Log;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;
import org.osmdroid.api.IMapView;
import org.osmdroid.tileprovider.MapTile;
import org.osmdroid.tileprovider.tilesource.ITileSource;

public class ZipFileArchive implements IArchiveFile {
    protected ZipFile mZipFile;

    public ZipFileArchive() {
    }

    private ZipFileArchive(ZipFile pZipFile) {
        this.mZipFile = pZipFile;
    }

    public static ZipFileArchive getZipFileArchive(File pFile) throws ZipException, IOException {
        ZipFileArchive zipFileArchive;
        ZipFile zipFile;
        new ZipFile(pFile);
        new ZipFileArchive(zipFile);
        return zipFileArchive;
    }

    public void init(File pFile) throws Exception {
        ZipFile zipFile;
        ZipFile zipFile2 = zipFile;
        new ZipFile(pFile);
        this.mZipFile = zipFile2;
    }

    public InputStream getInputStream(ITileSource pTileSource, MapTile mapTile) {
        StringBuilder sb;
        MapTile pTile = mapTile;
        try {
            ZipEntry entry = this.mZipFile.getEntry(pTileSource.getTileRelativeFilenameString(pTile));
            if (entry != null) {
                return this.mZipFile.getInputStream(entry);
            }
        } catch (IOException e) {
            new StringBuilder();
            int w = Log.w(IMapView.LOGTAG, sb.append("Error getting zip stream: ").append(pTile).toString(), e);
        }
        return null;
    }

    public Set<String> getTileSources() {
        Set<String> set;
        new HashSet();
        Set<String> ret = set;
        try {
            Enumeration<? extends ZipEntry> entries = this.mZipFile.entries();
            while (entries.hasMoreElements()) {
                String str = ((ZipEntry) entries.nextElement()).getName();
                if (str.contains("/")) {
                    boolean add = ret.add(str.split("/")[0]);
                }
            }
        } catch (Exception e) {
            int w = Log.w(IMapView.LOGTAG, "Error getting tile sources: ", e);
        }
        return ret;
    }

    public void close() {
        try {
            this.mZipFile.close();
        } catch (IOException e) {
            IOException iOException = e;
        }
    }

    public String toString() {
        StringBuilder sb;
        new StringBuilder();
        return sb.append("ZipFileArchive [mZipFile=").append(this.mZipFile.getName()).append("]").toString();
    }
}
