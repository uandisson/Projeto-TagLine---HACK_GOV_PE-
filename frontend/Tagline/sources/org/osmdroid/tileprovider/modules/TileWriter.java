package org.osmdroid.tileprovider.modules;

import android.graphics.drawable.Drawable;
import android.util.Log;
import java.io.BufferedOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import org.osmdroid.api.IMapView;
import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.ExpirableBitmapDrawable;
import org.osmdroid.tileprovider.MapTile;
import org.osmdroid.tileprovider.constants.OpenStreetMapTileProviderConstants;
import org.osmdroid.tileprovider.tilesource.ITileSource;
import org.osmdroid.tileprovider.util.Counters;
import org.osmdroid.tileprovider.util.StreamUtils;

public class TileWriter implements IFilesystemCache {
    static boolean hasInited = false;
    /* access modifiers changed from: private */
    public static long mUsedCacheSpace;
    Thread initThread = null;
    private long mMaximumCachedFileAge;

    static /* synthetic */ long access$002(long x0) {
        long x02 = x0;
        mUsedCacheSpace = x02;
        return x02;
    }

    public TileWriter() {
        Thread thread;
        if (!hasInited) {
            hasInited = true;
            new Thread(this) {
                final /* synthetic */ TileWriter this$0;

                {
                    this.this$0 = this$0;
                }

                public void run() {
                    long access$002 = TileWriter.access$002(0);
                    this.this$0.calculateDirectorySize(Configuration.getInstance().getOsmdroidTileCache());
                    if (TileWriter.mUsedCacheSpace > Configuration.getInstance().getTileFileSystemCacheMaxBytes()) {
                        this.this$0.cutCurrentCache();
                    }
                    if (Configuration.getInstance().isDebugMode()) {
                        int d = Log.d(IMapView.LOGTAG, "Finished init thread");
                    }
                }
            };
            this.initThread = thread;
            this.initThread.setPriority(1);
            this.initThread.start();
        }
    }

    public static long getUsedCacheSpace() {
        return mUsedCacheSpace;
    }

    public void setMaximumCachedFileAge(long mMaximumCachedFileAge2) {
        long j = mMaximumCachedFileAge2;
        this.mMaximumCachedFileAge = j;
    }

    public boolean saveFile(ITileSource pTileSource, MapTile pTile, InputStream inputStream) {
        OutputStream outputStream;
        OutputStream outputStream2;
        StringBuilder sb;
        InputStream pStream = inputStream;
        File file = getFile(pTileSource, pTile);
        if (Configuration.getInstance().isDebugTileProviders()) {
            new StringBuilder();
            int d = Log.d(IMapView.LOGTAG, sb.append("TileWrite ").append(file.getAbsolutePath()).toString());
        }
        File parent = file.getParentFile();
        if (!parent.exists() && !createFolderAndCheckIfExists(parent)) {
            return false;
        }
        try {
            new FileOutputStream(file.getPath());
            new BufferedOutputStream(outputStream2, 8192);
            OutputStream outputStream3 = outputStream;
            mUsedCacheSpace += StreamUtils.copy(pStream, outputStream3);
            if (mUsedCacheSpace > Configuration.getInstance().getTileFileSystemCacheMaxBytes()) {
                cutCurrentCache();
            }
            if (outputStream3 != null) {
                StreamUtils.closeStream(outputStream3);
            }
            return true;
        } catch (IOException e) {
            IOException iOException = e;
            Counters.fileCacheSaveErrors++;
            if (0 != 0) {
                StreamUtils.closeStream((Closeable) null);
            }
            return false;
        } catch (Throwable th) {
            Throwable th2 = th;
            if (0 != 0) {
                StreamUtils.closeStream((Closeable) null);
            }
            throw th2;
        }
    }

    public void onDetach() {
        if (this.initThread != null) {
            try {
                this.initThread.interrupt();
            } catch (Throwable th) {
                Throwable th2 = th;
            }
        }
    }

    public boolean remove(ITileSource iTileSource, MapTile mapTile) {
        StringBuilder sb;
        ITileSource pTileSource = iTileSource;
        MapTile pTile = mapTile;
        File file = getFile(pTileSource, pTile);
        if (file.exists()) {
            try {
                return file.delete();
            } catch (Exception e) {
                new StringBuilder();
                int i = Log.i(IMapView.LOGTAG, sb.append("Unable to delete cached tile from ").append(pTileSource.name()).append(" ").append(pTile.toString()).toString(), e);
            }
        }
        return false;
    }

    public File getFile(ITileSource pTileSource, MapTile pTile) {
        File file;
        StringBuilder sb;
        File osmdroidTileCache = Configuration.getInstance().getOsmdroidTileCache();
        new StringBuilder();
        new File(osmdroidTileCache, sb.append(pTileSource.getTileRelativeFilenameString(pTile)).append(OpenStreetMapTileProviderConstants.TILE_PATH_EXTENSION).toString());
        return file;
    }

    public boolean exists(ITileSource pTileSource, MapTile pTile) {
        return getFile(pTileSource, pTile).exists();
    }

    private boolean createFolderAndCheckIfExists(File file) {
        StringBuilder sb;
        StringBuilder sb2;
        StringBuilder sb3;
        File pFile = file;
        if (pFile.mkdirs()) {
            return true;
        }
        if (Configuration.getInstance().isDebugMode()) {
            new StringBuilder();
            int d = Log.d(IMapView.LOGTAG, sb3.append("Failed to create ").append(pFile).append(" - wait and check again").toString());
        }
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            InterruptedException interruptedException = e;
        }
        if (pFile.exists()) {
            if (Configuration.getInstance().isDebugMode()) {
                new StringBuilder();
                int d2 = Log.d(IMapView.LOGTAG, sb2.append("Seems like another thread created ").append(pFile).toString());
            }
            return true;
        }
        if (Configuration.getInstance().isDebugMode()) {
            new StringBuilder();
            int d3 = Log.d(IMapView.LOGTAG, sb.append("File still doesn't exist: ").append(pFile).toString());
        }
        return false;
    }

    /* access modifiers changed from: private */
    public void calculateDirectorySize(File file) {
        File pDirectory = file;
        File[] z = pDirectory.listFiles();
        if (z != null) {
            File[] fileArr = z;
            int length = fileArr.length;
            for (int i = 0; i < length; i++) {
                File file2 = fileArr[i];
                if (file2.isFile()) {
                    mUsedCacheSpace += file2.length();
                }
                if (file2.isDirectory() && !isSymbolicDirectoryLink(pDirectory, file2)) {
                    calculateDirectorySize(file2);
                }
            }
        }
    }

    private boolean isSymbolicDirectoryLink(File pParentDirectory, File pDirectory) {
        try {
            return !pParentDirectory.getCanonicalPath().equals(pDirectory.getCanonicalFile().getParent());
        } catch (IOException e) {
            IOException iOException = e;
            return true;
        } catch (NoSuchElementException e2) {
            NoSuchElementException noSuchElementException = e2;
            return true;
        }
    }

    private List<File> getDirectoryFileList(File aDirectory) {
        List<File> list;
        new ArrayList();
        List<File> files = list;
        File[] z = aDirectory.listFiles();
        if (z != null) {
            File[] fileArr = z;
            int length = fileArr.length;
            for (int i = 0; i < length; i++) {
                File file = fileArr[i];
                if (file.isFile()) {
                    boolean add = files.add(file);
                }
                if (file.isDirectory()) {
                    boolean addAll = files.addAll(getDirectoryFileList(file));
                }
            }
        }
        return files;
    }

    /* JADX INFO: finally extract failed */
    /* access modifiers changed from: private */
    public void cutCurrentCache() {
        StringBuilder sb;
        Comparator comparator;
        StringBuilder sb2;
        File osmdroidTileCache = Configuration.getInstance().getOsmdroidTileCache();
        File file = osmdroidTileCache;
        synchronized (osmdroidTileCache) {
            try {
                if (mUsedCacheSpace > Configuration.getInstance().getTileFileSystemCacheTrimBytes()) {
                    new StringBuilder();
                    int d = Log.d(IMapView.LOGTAG, sb.append("Trimming tile cache from ").append(mUsedCacheSpace).append(" to ").append(Configuration.getInstance().getTileFileSystemCacheTrimBytes()).toString());
                    File[] files = (File[]) getDirectoryFileList(Configuration.getInstance().getOsmdroidTileCache()).toArray(new File[0]);
                    new Comparator<File>(this) {
                        final /* synthetic */ TileWriter this$0;

                        {
                            this.this$0 = this$0;
                        }

                        public int compare(File f1, File f2) {
                            return Long.valueOf(f1.lastModified()).compareTo(Long.valueOf(f2.lastModified()));
                        }
                    };
                    Arrays.sort(files, comparator);
                    File[] fileArr = files;
                    int length = fileArr.length;
                    for (int i = 0; i < length; i++) {
                        File file2 = fileArr[i];
                        if (mUsedCacheSpace <= Configuration.getInstance().getTileFileSystemCacheTrimBytes()) {
                            break;
                        }
                        long length2 = file2.length();
                        if (file2.delete()) {
                            if (Configuration.getInstance().isDebugTileProviders()) {
                                new StringBuilder();
                                int d2 = Log.d(IMapView.LOGTAG, sb2.append("Cache trim deleting ").append(file2.getAbsolutePath()).toString());
                            }
                            mUsedCacheSpace -= length2;
                        }
                    }
                    int d3 = Log.d(IMapView.LOGTAG, "Finished trimming tile cache");
                }
            } catch (Throwable th) {
                Throwable th2 = th;
                File file3 = file;
                throw th2;
            }
        }
    }

    public Long getExpirationTimestamp(ITileSource iTileSource, MapTile mapTile) {
        ITileSource iTileSource2 = iTileSource;
        MapTile mapTile2 = mapTile;
        return null;
    }

    public Drawable loadTile(ITileSource iTileSource, MapTile mapTile) throws Exception {
        StringBuilder sb;
        ITileSource pTileSource = iTileSource;
        MapTile pTile = mapTile;
        File file = getFile(pTileSource, pTile);
        if (!file.exists()) {
            return null;
        }
        Drawable drawable = pTileSource.getDrawable(file.getPath());
        if ((file.lastModified() < System.currentTimeMillis() - this.mMaximumCachedFileAge) && drawable != null) {
            if (Configuration.getInstance().isDebugMode()) {
                new StringBuilder();
                int d = Log.d(IMapView.LOGTAG, sb.append("Tile expired: ").append(pTile).toString());
            }
            ExpirableBitmapDrawable.setState(drawable, -2);
        }
        return drawable;
    }
}
