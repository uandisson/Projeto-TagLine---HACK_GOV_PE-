package org.osmdroid.tileprovider.cachemanager;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Point;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;
import java.io.Closeable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.osmdroid.api.IMapView;
import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.MapTile;
import org.osmdroid.tileprovider.MapTileProviderBase;
import org.osmdroid.tileprovider.constants.OpenStreetMapTileProviderConstants;
import org.osmdroid.tileprovider.modules.IFilesystemCache;
import org.osmdroid.tileprovider.tilesource.ITileSource;
import org.osmdroid.tileprovider.tilesource.OnlineTileSourceBase;
import org.osmdroid.tileprovider.util.Counters;
import org.osmdroid.tileprovider.util.StreamUtils;
import org.osmdroid.util.BoundingBox;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.util.MyMath;
import org.osmdroid.util.TileSystem;

public class CacheManager {
    protected final int mMaxZoomLevel;
    protected final int mMinZoomLevel;
    protected Set<CacheManagerTask> mPendingTasks;
    protected final ITileSource mTileSource;
    protected final IFilesystemCache mTileWriter;
    protected boolean verifyCancel;

    public interface CacheManagerAction {
        int getProgressModulo();

        boolean preCheck();

        boolean tileAction(MapTile mapTile);
    }

    public interface CacheManagerCallback {
        void downloadStarted();

        void onTaskComplete();

        void onTaskFailed(int i);

        void setPossibleTilesInArea(int i);

        void updateProgress(int i, int i2, int i3, int i4);
    }

    /* JADX WARNING: Illegal instructions before constructor call */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public CacheManager(org.osmdroid.views.MapView r6) {
        /*
            r5 = this;
            r0 = r5
            r1 = r6
            r2 = r0
            r3 = r1
            r4 = r1
            org.osmdroid.tileprovider.MapTileProviderBase r4 = r4.getTileProvider()
            org.osmdroid.tileprovider.modules.IFilesystemCache r4 = r4.getTileWriter()
            r2.<init>(r3, r4)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.osmdroid.tileprovider.cachemanager.CacheManager.<init>(org.osmdroid.views.MapView):void");
    }

    /* JADX WARNING: Illegal instructions before constructor call */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public CacheManager(org.osmdroid.views.MapView r9, org.osmdroid.tileprovider.modules.IFilesystemCache r10) {
        /*
            r8 = this;
            r0 = r8
            r1 = r9
            r2 = r10
            r3 = r0
            r4 = r1
            org.osmdroid.tileprovider.MapTileProviderBase r4 = r4.getTileProvider()
            r5 = r2
            r6 = r1
            int r6 = r6.getMinZoomLevel()
            r7 = r1
            int r7 = r7.getMaxZoomLevel()
            r3.<init>((org.osmdroid.tileprovider.MapTileProviderBase) r4, (org.osmdroid.tileprovider.modules.IFilesystemCache) r5, (int) r6, (int) r7)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.osmdroid.tileprovider.cachemanager.CacheManager.<init>(org.osmdroid.views.MapView, org.osmdroid.tileprovider.modules.IFilesystemCache):void");
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public CacheManager(MapTileProviderBase pTileProvider, IFilesystemCache pWriter, int pMinZoomLevel, int pMaxZoomLevel) {
        this(pTileProvider.getTileSource(), pWriter, pMinZoomLevel, pMaxZoomLevel);
    }

    public CacheManager(ITileSource pTileSource, IFilesystemCache pWriter, int pMinZoomLevel, int pMaxZoomLevel) {
        Set<CacheManagerTask> set;
        new HashSet();
        this.mPendingTasks = set;
        this.verifyCancel = true;
        this.mTileSource = pTileSource;
        this.mTileWriter = pWriter;
        this.mMinZoomLevel = pMinZoomLevel;
        this.mMaxZoomLevel = pMaxZoomLevel;
    }

    public int getPendingJobs() {
        return this.mPendingTasks.size();
    }

    public static Point getMapTileFromCoordinates(double d, double aLon, int i) {
        Point point;
        double aLat = d;
        int zoom = i;
        int y = (int) Math.floor(((1.0d - (Math.log(Math.tan((aLat * 3.141592653589793d) / 180.0d) + (1.0d / Math.cos((aLat * 3.141592653589793d) / 180.0d))) / 3.141592653589793d)) / 2.0d) * ((double) (1 << zoom)));
        new Point((int) Math.floor(((aLon + 180.0d) / 360.0d) * ((double) (1 << zoom))), y);
        return point;
    }

    public static GeoPoint getCoordinatesFromMapTile(int x, int y, int i) {
        GeoPoint geoPoint;
        int zoom = i;
        double n = 3.141592653589793d - ((6.283185307179586d * ((double) y)) / ((double) (1 << zoom)));
        new GeoPoint(57.29577951308232d * Math.atan(0.5d * (Math.exp(n) - Math.exp(-n))), ((360.0d * ((double) x)) / ((double) (1 << zoom))) - 180.0d);
        return geoPoint;
    }

    public static File getFileName(ITileSource tileSource, MapTile tile) {
        File file;
        StringBuilder sb;
        File osmdroidTileCache = Configuration.getInstance().getOsmdroidTileCache();
        new StringBuilder();
        new File(osmdroidTileCache, sb.append(tileSource.getTileRelativeFilenameString(tile)).append(OpenStreetMapTileProviderConstants.TILE_PATH_EXTENSION).toString());
        return file;
    }

    public boolean loadTile(OnlineTileSourceBase onlineTileSourceBase, MapTile mapTile) {
        OnlineTileSourceBase tileSource = onlineTileSourceBase;
        MapTile tile = mapTile;
        if (getFileName(tileSource, tile).exists()) {
            return true;
        }
        if (this.mTileWriter.exists(tileSource, tile)) {
            return true;
        }
        return forceLoadTile(tileSource, tile);
    }

    public boolean forceLoadTile(OnlineTileSourceBase onlineTileSourceBase, MapTile mapTile) {
        StringBuilder sb;
        StringBuilder sb2;
        StringBuilder sb3;
        StringBuilder sb4;
        URL url;
        Date date;
        Date dateExpires;
        StringBuilder sb5;
        Date date2;
        StringBuilder sb6;
        StringBuilder sb7;
        OnlineTileSourceBase tileSource = onlineTileSourceBase;
        MapTile tile = mapTile;
        InputStream in = null;
        HttpURLConnection c = null;
        try {
            String tileURLString = tileSource.getTileURLString(tile);
            if (Configuration.getInstance().isDebugMode()) {
                new StringBuilder();
                int d = Log.d(IMapView.LOGTAG, sb7.append("Downloading Maptile from url: ").append(tileURLString).toString());
            }
            if (TextUtils.isEmpty(tileURLString)) {
                StreamUtils.closeStream((Closeable) null);
                HttpURLConnection httpURLConnection = null;
                try {
                    httpURLConnection.disconnect();
                } catch (Exception e) {
                    Exception exc = e;
                }
                return false;
            }
            new URL(tileURLString);
            c = (HttpURLConnection) url.openConnection();
            c.setUseCaches(true);
            c.setRequestProperty(Configuration.getInstance().getUserAgentHttpHeader(), Configuration.getInstance().getUserAgentValue());
            for (Map.Entry<String, String> entry : Configuration.getInstance().getAdditionalHttpRequestProperties().entrySet()) {
                c.setRequestProperty(entry.getKey(), entry.getValue());
            }
            c.connect();
            if (c.getResponseCode() != 200) {
                new StringBuilder();
                int w = Log.w(IMapView.LOGTAG, sb6.append("Problem downloading MapTile: ").append(tile).append(" HTTP response: ").append(c.getResponseMessage()).toString());
                Counters.tileDownloadErrors++;
                StreamUtils.closeStream((Closeable) null);
                try {
                    c.disconnect();
                } catch (Exception e2) {
                    Exception exc2 = e2;
                }
                return false;
            }
            in = c.getInputStream();
            Long override = Configuration.getInstance().getExpirationOverrideDuration();
            if (override != null) {
                new Date(System.currentTimeMillis() + override.longValue());
                dateExpires = date2;
            } else {
                new Date(System.currentTimeMillis() + 604800000 + Configuration.getInstance().getExpirationExtendedDuration());
                dateExpires = date;
                String expires = c.getHeaderField(OpenStreetMapTileProviderConstants.HTTP_EXPIRES_HEADER);
                if (expires != null && expires.length() > 0) {
                    try {
                        dateExpires = Configuration.getInstance().getHttpHeaderDateTimeFormat().parse(expires);
                        dateExpires.setTime(dateExpires.getTime() + Configuration.getInstance().getExpirationExtendedDuration());
                    } catch (Exception e3) {
                        Exception ex = e3;
                        if (Configuration.getInstance().isDebugMapTileDownloader()) {
                            new StringBuilder();
                            int d2 = Log.d(IMapView.LOGTAG, sb5.append("Unable to parse expiration tag for tile, using default, server returned ").append(expires).toString(), ex);
                        }
                    }
                }
            }
            tile.setExpires(dateExpires);
            boolean saveFile = this.mTileWriter.saveFile(tileSource, tile, in);
            StreamUtils.closeStream(in);
            try {
                c.disconnect();
            } catch (Exception e4) {
                Exception exc3 = e4;
            }
            return true;
        } catch (UnknownHostException e5) {
            UnknownHostException e6 = e5;
            new StringBuilder();
            int w2 = Log.w(IMapView.LOGTAG, sb4.append("UnknownHostException downloading MapTile: ").append(tile).append(" : ").append(e6).toString());
            Counters.tileDownloadErrors++;
            StreamUtils.closeStream(in);
            try {
                c.disconnect();
            } catch (Exception e7) {
                Exception exc4 = e7;
            }
            return false;
        } catch (FileNotFoundException e8) {
            FileNotFoundException e9 = e8;
            Counters.tileDownloadErrors++;
            new StringBuilder();
            int w3 = Log.w(IMapView.LOGTAG, sb3.append("Tile not found: ").append(tile).append(" : ").append(e9).toString());
            StreamUtils.closeStream(in);
            try {
                c.disconnect();
            } catch (Exception e10) {
                Exception exc5 = e10;
            }
        } catch (IOException e11) {
            IOException e12 = e11;
            Counters.tileDownloadErrors++;
            new StringBuilder();
            int w4 = Log.w(IMapView.LOGTAG, sb2.append("IOException downloading MapTile: ").append(tile).append(" : ").append(e12).toString());
            StreamUtils.closeStream(in);
            try {
                c.disconnect();
            } catch (Exception e13) {
                Exception exc6 = e13;
            }
        } catch (Throwable th) {
            Throwable th2 = th;
            StreamUtils.closeStream(in);
            try {
                c.disconnect();
            } catch (Exception e14) {
                Exception exc7 = e14;
            }
            throw th2;
        }
        return false;
    }

    public boolean deleteTile(MapTile mapTile) {
        MapTile pTile = mapTile;
        return this.mTileWriter.exists(this.mTileSource, pTile) && this.mTileWriter.remove(this.mTileSource, pTile);
    }

    public boolean checkTile(MapTile pTile) {
        return this.mTileWriter.exists(this.mTileSource, pTile);
    }

    public boolean isTileToBeDownloaded(ITileSource pTileSource, MapTile pTile) {
        Long expiration = this.mTileWriter.getExpirationTimestamp(pTileSource, pTile);
        if (expiration == null) {
            return true;
        }
        return System.currentTimeMillis() > expiration.longValue();
    }

    public static List<MapTile> getTilesCoverage(BoundingBox boundingBox, int pZoomMin, int i) {
        List<MapTile> list;
        BoundingBox pBB = boundingBox;
        int pZoomMax = i;
        new ArrayList();
        List<MapTile> result = list;
        for (int zoomLevel = pZoomMin; zoomLevel <= pZoomMax; zoomLevel++) {
            boolean addAll = result.addAll(getTilesCoverage(pBB, zoomLevel));
        }
        return result;
    }

    public static Collection<MapTile> getTilesCoverage(BoundingBox boundingBox, int i) {
        Set<MapTile> set;
        Object obj;
        BoundingBox pBB = boundingBox;
        int pZoomLevel = i;
        new HashSet<>();
        Set<MapTile> result = set;
        int mapTileUpperBound = 1 << pZoomLevel;
        Point lowerRight = getMapTileFromCoordinates(pBB.getLatSouth(), pBB.getLonEast(), pZoomLevel);
        Point upperLeft = getMapTileFromCoordinates(pBB.getLatNorth(), pBB.getLonWest(), pZoomLevel);
        int width = (lowerRight.x - upperLeft.x) + 1;
        if (width <= 0) {
            width += mapTileUpperBound;
        }
        int height = (lowerRight.y - upperLeft.y) + 1;
        if (height <= 0) {
            height += mapTileUpperBound;
        }
        for (int i2 = 0; i2 < width; i2++) {
            for (int j = 0; j < height; j++) {
                new MapTile(pZoomLevel, MyMath.mod(upperLeft.x + i2, mapTileUpperBound), MyMath.mod(upperLeft.y + j, mapTileUpperBound));
                boolean add = result.add(obj);
            }
        }
        return result;
    }

    public static List<MapTile> getTilesCoverage(ArrayList<GeoPoint> arrayList, int pZoomMin, int i) {
        List<MapTile> list;
        ArrayList<GeoPoint> pGeoPoints = arrayList;
        int pZoomMax = i;
        new ArrayList();
        List<MapTile> result = list;
        for (int zoomLevel = pZoomMin; zoomLevel <= pZoomMax; zoomLevel++) {
            boolean addAll = result.addAll(getTilesCoverage(pGeoPoints, zoomLevel));
        }
        return result;
    }

    public static Collection<MapTile> getTilesCoverage(ArrayList<GeoPoint> pGeoPoints, int i) {
        Set<MapTile> set;
        Object obj;
        double brng;
        GeoPoint geoPoint;
        Point lastPoint;
        Object obj2;
        int pZoomLevel = i;
        new HashSet<>();
        Set<MapTile> result = set;
        GeoPoint prevPoint = null;
        Point prevTile = null;
        int mapTileUpperBound = 1 << pZoomLevel;
        Iterator<GeoPoint> it = pGeoPoints.iterator();
        while (it.hasNext()) {
            GeoPoint geoPoint2 = it.next();
            double d = TileSystem.GroundResolution(geoPoint2.getLatitude(), pZoomLevel);
            if (result.size() == 0) {
                Point tile = getMapTileFromCoordinates(geoPoint2.getLatitude(), geoPoint2.getLongitude(), pZoomLevel);
                prevTile = tile;
                int ofsx = tile.x >= 0 ? 0 : -tile.x;
                int ofsy = tile.y >= 0 ? 0 : -tile.y;
                for (int xAround = tile.x + ofsx; xAround <= tile.x + 1 + ofsx; xAround++) {
                    for (int yAround = tile.y + ofsy; yAround <= tile.y + 1 + ofsy; yAround++) {
                        new MapTile(pZoomLevel, MyMath.mod(xAround, mapTileUpperBound), MyMath.mod(yAround, mapTileUpperBound));
                        boolean add = result.add(obj);
                    }
                }
            } else if (prevPoint != null) {
                double leadCoef = (geoPoint2.getLatitude() - prevPoint.getLatitude()) / (geoPoint2.getLongitude() - prevPoint.getLongitude());
                if (geoPoint2.getLongitude() > prevPoint.getLongitude()) {
                    brng = 1.5707963267948966d - Math.atan(leadCoef);
                } else {
                    brng = 4.71238898038469d - Math.atan(leadCoef);
                }
                new GeoPoint(prevPoint.getLatitude(), prevPoint.getLongitude());
                GeoPoint wayPoint = geoPoint;
                while (true) {
                    if (((geoPoint2.getLatitude() <= prevPoint.getLatitude() || wayPoint.getLatitude() >= geoPoint2.getLatitude()) && (geoPoint2.getLatitude() >= prevPoint.getLatitude() || wayPoint.getLatitude() <= geoPoint2.getLatitude())) || ((geoPoint2.getLongitude() <= prevPoint.getLongitude() || wayPoint.getLongitude() >= geoPoint2.getLongitude()) && (geoPoint2.getLongitude() >= prevPoint.getLongitude() || wayPoint.getLongitude() <= geoPoint2.getLongitude()))) {
                        break;
                    }
                    new Point();
                    Point LatLongToPixelXY = TileSystem.LatLongToPixelXY(geoPoint2.getLatitude(), geoPoint2.getLongitude(), pZoomLevel, lastPoint);
                    double prevLatRad = (wayPoint.getLatitude() * 3.141592653589793d) / 180.0d;
                    double prevLonRad = (wayPoint.getLongitude() * 3.141592653589793d) / 180.0d;
                    double latRad = Math.asin((Math.sin(prevLatRad) * Math.cos(d / 6378137.0d)) + (Math.cos(prevLatRad) * Math.sin(d / 6378137.0d) * Math.cos(brng)));
                    double lonRad = prevLonRad + Math.atan2(Math.sin(brng) * Math.sin(d / 6378137.0d) * Math.cos(prevLatRad), Math.cos(d / 6378137.0d) - (Math.sin(prevLatRad) * Math.sin(latRad)));
                    wayPoint.setLatitude((latRad * 180.0d) / 3.141592653589793d);
                    wayPoint.setLongitude((lonRad * 180.0d) / 3.141592653589793d);
                    Point tile2 = getMapTileFromCoordinates(wayPoint.getLatitude(), wayPoint.getLongitude(), pZoomLevel);
                    if (!tile2.equals(prevTile)) {
                        int ofsx2 = tile2.x >= 0 ? 0 : -tile2.x;
                        int ofsy2 = tile2.y >= 0 ? 0 : -tile2.y;
                        for (int xAround2 = tile2.x + ofsx2; xAround2 <= tile2.x + 1 + ofsx2; xAround2++) {
                            for (int yAround2 = tile2.y + ofsy2; yAround2 <= tile2.y + 1 + ofsy2; yAround2++) {
                                new MapTile(pZoomLevel, MyMath.mod(xAround2, mapTileUpperBound), MyMath.mod(yAround2, mapTileUpperBound));
                                boolean add2 = result.add(obj2);
                            }
                        }
                        prevTile = tile2;
                    }
                }
            }
            prevPoint = geoPoint2;
        }
        return result;
    }

    public int possibleTilesInArea(BoundingBox pBB, int pZoomMin, int pZoomMax) {
        return getTilesCoverage(pBB, pZoomMin, pZoomMax).size();
    }

    public int possibleTilesCovered(ArrayList<GeoPoint> pGeoPoints, int pZoomMin, int pZoomMax) {
        return getTilesCoverage(pGeoPoints, pZoomMin, pZoomMax).size();
    }

    public CacheManagerTask execute(CacheManagerTask cacheManagerTask) {
        CacheManagerTask pTask = cacheManagerTask;
        AsyncTask execute = pTask.execute(new Object[0]);
        boolean add = this.mPendingTasks.add(pTask);
        return pTask;
    }

    public CacheManagerTask downloadAreaAsync(Context ctx, BoundingBox bb, int zoomMin, int zoomMax) {
        CacheManagerTask cacheManagerTask;
        new CacheManagerTask(this, getDownloadingAction(), bb, zoomMin, zoomMax);
        CacheManagerTask task = cacheManagerTask;
        task.addCallback(getDownloadingDialog(ctx, task));
        return execute(task);
    }

    public CacheManagerTask downloadAreaAsync(Context ctx, ArrayList<GeoPoint> geoPoints, int zoomMin, int zoomMax) {
        CacheManagerTask cacheManagerTask;
        new CacheManagerTask(this, getDownloadingAction(), geoPoints, zoomMin, zoomMax);
        CacheManagerTask task = cacheManagerTask;
        task.addCallback(getDownloadingDialog(ctx, task));
        return execute(task);
    }

    public CacheManagerTask downloadAreaAsync(Context ctx, BoundingBox bb, int zoomMin, int zoomMax, CacheManagerCallback callback) {
        CacheManagerTask cacheManagerTask;
        new CacheManagerTask(this, getDownloadingAction(), bb, zoomMin, zoomMax);
        CacheManagerTask task = cacheManagerTask;
        task.addCallback(callback);
        task.addCallback(getDownloadingDialog(ctx, task));
        return execute(task);
    }

    public CacheManagerTask downloadAreaAsync(Context ctx, ArrayList<GeoPoint> geoPoints, int zoomMin, int zoomMax, CacheManagerCallback callback) {
        CacheManagerTask cacheManagerTask;
        new CacheManagerTask(this, getDownloadingAction(), geoPoints, zoomMin, zoomMax);
        CacheManagerTask task = cacheManagerTask;
        task.addCallback(callback);
        task.addCallback(getDownloadingDialog(ctx, task));
        return execute(task);
    }

    public CacheManagerTask downloadAreaAsyncNoUI(Context context, ArrayList<GeoPoint> geoPoints, int zoomMin, int zoomMax, CacheManagerCallback callback) {
        CacheManagerTask cacheManagerTask;
        Context context2 = context;
        new CacheManagerTask(this, getDownloadingAction(), geoPoints, zoomMin, zoomMax);
        CacheManagerTask task = cacheManagerTask;
        task.addCallback(callback);
        return execute(task);
    }

    public CacheManagerTask downloadAreaAsyncNoUI(Context context, BoundingBox bb, int zoomMin, int zoomMax, CacheManagerCallback callback) {
        CacheManagerTask cacheManagerTask;
        Context context2 = context;
        new CacheManagerTask(this, getDownloadingAction(), bb, zoomMin, zoomMax);
        CacheManagerTask task = cacheManagerTask;
        task.addCallback(callback);
        CacheManagerTask execute = execute(task);
        return task;
    }

    public void cancelAllJobs() {
        for (CacheManagerTask next : this.mPendingTasks) {
            boolean cancel = next.cancel(true);
        }
        this.mPendingTasks.clear();
    }

    public CacheManagerTask downloadAreaAsync(Context ctx, List<MapTile> pTiles, int zoomMin, int zoomMax) {
        CacheManagerTask cacheManagerTask;
        new CacheManagerTask(this, getDownloadingAction(), pTiles, zoomMin, zoomMax);
        CacheManagerTask task = cacheManagerTask;
        task.addCallback(getDownloadingDialog(ctx, task));
        return execute(task);
    }

    public void setVerifyCancel(boolean state) {
        boolean z = state;
        this.verifyCancel = z;
    }

    public boolean getVerifyCancel() {
        return this.verifyCancel;
    }

    public static abstract class CacheManagerDialog implements CacheManagerCallback {
        /* access modifiers changed from: private */
        public final ProgressDialog mProgressDialog;
        /* access modifiers changed from: private */
        public final CacheManagerTask mTask;

        /* access modifiers changed from: protected */
        public abstract String getUITitle();

        public CacheManagerDialog(Context context, CacheManagerTask cacheManagerTask) {
            ProgressDialog progressDialog;
            DialogInterface.OnCancelListener onCancelListener;
            DialogInterface.OnCancelListener onCancelListener2;
            Context pCtx = context;
            CacheManagerTask pTask = cacheManagerTask;
            this.mTask = pTask;
            new ProgressDialog(pCtx);
            this.mProgressDialog = progressDialog;
            this.mProgressDialog.setProgressStyle(1);
            this.mProgressDialog.setCancelable(true);
            if (pTask.mManager.getVerifyCancel()) {
                final Context context2 = pCtx;
                new DialogInterface.OnCancelListener(this) {
                    final /* synthetic */ CacheManagerDialog this$0;

                    {
                        this.this$0 = this$0;
                    }

                    public void onCancel(DialogInterface dialogInterface) {
                        AlertDialog.Builder builder;
                        DialogInterface.OnClickListener onClickListener;
                        DialogInterface.OnClickListener onClickListener2;
                        DialogInterface dialogInterface2 = dialogInterface;
                        new AlertDialog.Builder(context2);
                        AlertDialog.Builder builder2 = builder;
                        AlertDialog.Builder title = builder2.setTitle("Cancel map download");
                        AlertDialog.Builder message = builder2.setMessage("Do you want to cancel the map download?");
                        new DialogInterface.OnClickListener(this) {
                            final /* synthetic */ C15811 this$1;

                            {
                                this.this$1 = this$1;
                            }

                            public void onClick(DialogInterface dialogInterface, int i) {
                                DialogInterface dialogInterface2 = dialogInterface;
                                int i2 = i;
                                boolean cancel = this.this$1.this$0.mTask.cancel(true);
                            }
                        };
                        AlertDialog.Builder positiveButton = builder2.setPositiveButton("Yes", onClickListener);
                        new DialogInterface.OnClickListener(this) {
                            final /* synthetic */ C15811 this$1;

                            {
                                this.this$1 = this$1;
                            }

                            public void onClick(DialogInterface dialog, int i) {
                                int i2 = i;
                                dialog.dismiss();
                                this.this$1.this$0.mProgressDialog.show();
                            }
                        };
                        AlertDialog.Builder negativeButton = builder2.setNegativeButton("No", onClickListener2);
                        AlertDialog show = builder2.show();
                    }
                };
                this.mProgressDialog.setOnCancelListener(onCancelListener2);
                return;
            }
            new DialogInterface.OnCancelListener(this) {
                final /* synthetic */ CacheManagerDialog this$0;

                {
                    this.this$0 = this$0;
                }

                public void onCancel(DialogInterface dialogInterface) {
                    DialogInterface dialogInterface2 = dialogInterface;
                    boolean cancel = this.this$0.mTask.cancel(true);
                }
            };
            this.mProgressDialog.setOnCancelListener(onCancelListener);
        }

        /* access modifiers changed from: protected */
        public String zoomMessage(int zoomLevel, int zoomMin, int zoomMax) {
            StringBuilder sb;
            new StringBuilder();
            return sb.append("Handling zoom level: ").append(zoomLevel).append(" (from ").append(zoomMin).append(" to ").append(zoomMax).append(")").toString();
        }

        public void updateProgress(int progress, int currentZoomLevel, int zoomMin, int zoomMax) {
            this.mProgressDialog.setProgress(progress);
            this.mProgressDialog.setMessage(zoomMessage(currentZoomLevel, zoomMin, zoomMax));
        }

        public void downloadStarted() {
            this.mProgressDialog.setTitle(getUITitle());
            this.mProgressDialog.show();
        }

        public void setPossibleTilesInArea(int total) {
            this.mProgressDialog.setMax(total);
        }

        public void onTaskComplete() {
            dismiss();
        }

        public void onTaskFailed(int i) {
            int i2 = i;
            dismiss();
        }

        private void dismiss() {
            if (this.mProgressDialog.isShowing()) {
                this.mProgressDialog.dismiss();
            }
        }
    }

    public static class CacheManagerTask extends AsyncTask<Object, Integer, Integer> {
        private final CacheManagerAction mAction;
        private final ArrayList<CacheManagerCallback> mCallbacks;
        /* access modifiers changed from: private */
        public final CacheManager mManager;
        private final List<MapTile> mTiles;
        private final int mZoomMax;
        private final int mZoomMin;

        public CacheManagerTask(CacheManager cacheManager, CacheManagerAction pAction, List<MapTile> pTiles, int pZoomMin, int pZoomMax) {
            ArrayList<CacheManagerCallback> arrayList;
            CacheManager pManager = cacheManager;
            new ArrayList<>();
            this.mCallbacks = arrayList;
            this.mManager = pManager;
            this.mAction = pAction;
            this.mTiles = pTiles;
            this.mZoomMin = Math.max(pZoomMin, pManager.mMinZoomLevel);
            this.mZoomMax = Math.min(pZoomMax, pManager.mMaxZoomLevel);
        }

        /* JADX WARNING: Illegal instructions before constructor call */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public CacheManagerTask(org.osmdroid.tileprovider.cachemanager.CacheManager r13, org.osmdroid.tileprovider.cachemanager.CacheManager.CacheManagerAction r14, java.util.ArrayList<org.osmdroid.util.GeoPoint> r15, int r16, int r17) {
            /*
                r12 = this;
                r0 = r12
                r1 = r13
                r2 = r14
                r3 = r15
                r4 = r16
                r5 = r17
                r6 = r0
                r7 = r1
                r8 = r2
                r9 = r3
                r10 = r4
                r11 = r5
                java.util.List r9 = org.osmdroid.tileprovider.cachemanager.CacheManager.getTilesCoverage((java.util.ArrayList<org.osmdroid.util.GeoPoint>) r9, (int) r10, (int) r11)
                r10 = r4
                r11 = r5
                r6.<init>((org.osmdroid.tileprovider.cachemanager.CacheManager) r7, (org.osmdroid.tileprovider.cachemanager.CacheManager.CacheManagerAction) r8, (java.util.List<org.osmdroid.tileprovider.MapTile>) r9, (int) r10, (int) r11)
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: org.osmdroid.tileprovider.cachemanager.CacheManager.CacheManagerTask.<init>(org.osmdroid.tileprovider.cachemanager.CacheManager, org.osmdroid.tileprovider.cachemanager.CacheManager$CacheManagerAction, java.util.ArrayList, int, int):void");
        }

        /* JADX WARNING: Illegal instructions before constructor call */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public CacheManagerTask(org.osmdroid.tileprovider.cachemanager.CacheManager r13, org.osmdroid.tileprovider.cachemanager.CacheManager.CacheManagerAction r14, org.osmdroid.util.BoundingBox r15, int r16, int r17) {
            /*
                r12 = this;
                r0 = r12
                r1 = r13
                r2 = r14
                r3 = r15
                r4 = r16
                r5 = r17
                r6 = r0
                r7 = r1
                r8 = r2
                r9 = r3
                r10 = r4
                r11 = r5
                java.util.List r9 = org.osmdroid.tileprovider.cachemanager.CacheManager.getTilesCoverage((org.osmdroid.util.BoundingBox) r9, (int) r10, (int) r11)
                r10 = r4
                r11 = r5
                r6.<init>((org.osmdroid.tileprovider.cachemanager.CacheManager) r7, (org.osmdroid.tileprovider.cachemanager.CacheManager.CacheManagerAction) r8, (java.util.List<org.osmdroid.tileprovider.MapTile>) r9, (int) r10, (int) r11)
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: org.osmdroid.tileprovider.cachemanager.CacheManager.CacheManagerTask.<init>(org.osmdroid.tileprovider.cachemanager.CacheManager, org.osmdroid.tileprovider.cachemanager.CacheManager$CacheManagerAction, org.osmdroid.util.BoundingBox, int, int):void");
        }

        public void addCallback(CacheManagerCallback cacheManagerCallback) {
            CacheManagerCallback pCallback = cacheManagerCallback;
            if (pCallback != null) {
                boolean add = this.mCallbacks.add(pCallback);
            }
        }

        /* access modifiers changed from: protected */
        public void onPreExecute() {
            int total = this.mTiles.size();
            Iterator<CacheManagerCallback> it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                CacheManagerCallback callback = it.next();
                try {
                    callback.setPossibleTilesInArea(total);
                    callback.downloadStarted();
                    callback.updateProgress(0, this.mZoomMin, this.mZoomMin, this.mZoomMax);
                } catch (Throwable th) {
                    logFaultyCallback(th);
                }
            }
        }

        private void logFaultyCallback(Throwable pThrowable) {
            int w = Log.w(IMapView.LOGTAG, "Error caught processing cachemanager callback, your implementation is faulty", pThrowable);
        }

        /* access modifiers changed from: protected */
        public void onProgressUpdate(Integer... numArr) {
            Integer[] count = numArr;
            Iterator<CacheManagerCallback> it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                try {
                    it.next().updateProgress(count[0].intValue(), count[1].intValue(), this.mZoomMin, this.mZoomMax);
                } catch (Throwable th) {
                    logFaultyCallback(th);
                }
            }
        }

        /* access modifiers changed from: protected */
        public void onCancelled() {
            boolean remove = this.mManager.mPendingTasks.remove(this);
        }

        /* access modifiers changed from: protected */
        public void onPostExecute(Integer num) {
            Integer specialCount = num;
            boolean remove = this.mManager.mPendingTasks.remove(this);
            Iterator<CacheManagerCallback> it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                CacheManagerCallback callback = it.next();
                try {
                    if (specialCount.intValue() == 0) {
                        callback.onTaskComplete();
                    } else {
                        callback.onTaskFailed(specialCount.intValue());
                    }
                } catch (Throwable th) {
                    logFaultyCallback(th);
                }
            }
        }

        /* access modifiers changed from: protected */
        public Integer doInBackground(Object... objArr) {
            Object[] objArr2 = objArr;
            if (!this.mAction.preCheck()) {
                return null;
            }
            int tileCounter = 0;
            int errors = 0;
            for (MapTile tile : this.mTiles) {
                int zoom = tile.getZoomLevel();
                if (zoom >= this.mZoomMin && zoom <= this.mZoomMax && this.mAction.tileAction(tile)) {
                    errors++;
                }
                tileCounter++;
                if (tileCounter % this.mAction.getProgressModulo() == 0) {
                    if (isCancelled()) {
                        return Integer.valueOf(errors);
                    }
                    Integer[] numArr = new Integer[2];
                    numArr[0] = Integer.valueOf(tileCounter);
                    Integer[] numArr2 = numArr;
                    numArr2[1] = Integer.valueOf(tile.getZoomLevel());
                    publishProgress(numArr2);
                }
            }
            return Integer.valueOf(errors);
        }
    }

    public CacheManagerDialog getDownloadingDialog(Context context, CacheManagerTask pTask) {
        CacheManagerDialog cacheManagerDialog;
        Context pCtx = context;
        final Context context2 = pCtx;
        new CacheManagerDialog(this, pCtx, pTask) {
            final /* synthetic */ CacheManager this$0;

            {
                this.this$0 = this$0;
            }

            /* access modifiers changed from: protected */
            public String getUITitle() {
                return "Downloading tiles";
            }

            public void onTaskFailed(int i) {
                StringBuilder sb;
                int errors = i;
                super.onTaskFailed(errors);
                Context context = context2;
                new StringBuilder();
                Toast.makeText(context, sb.append("Loading completed with ").append(errors).append(" errors.").toString(), 0).show();
            }
        };
        return cacheManagerDialog;
    }

    public CacheManagerDialog getCleaningDialog(Context context, CacheManagerTask pTask) {
        CacheManagerDialog cacheManagerDialog;
        Context pCtx = context;
        final Context context2 = pCtx;
        new CacheManagerDialog(this, pCtx, pTask) {
            final /* synthetic */ CacheManager this$0;

            {
                this.this$0 = this$0;
            }

            /* access modifiers changed from: protected */
            public String getUITitle() {
                return "Cleaning tiles";
            }

            public void onTaskFailed(int i) {
                StringBuilder sb;
                int deleted = i;
                super.onTaskFailed(deleted);
                Context context = context2;
                new StringBuilder();
                Toast.makeText(context, sb.append("Cleaning completed, ").append(deleted).append(" tiles deleted.").toString(), 0).show();
            }
        };
        return cacheManagerDialog;
    }

    public CacheManagerAction getDownloadingAction() {
        CacheManagerAction cacheManagerAction;
        new CacheManagerAction(this) {
            final /* synthetic */ CacheManager this$0;

            {
                this.this$0 = this$0;
            }

            public boolean preCheck() {
                if (this.this$0.mTileSource instanceof OnlineTileSourceBase) {
                    return true;
                }
                int e = Log.e(IMapView.LOGTAG, "TileSource is not an online tile source");
                return false;
            }

            public int getProgressModulo() {
                return 10;
            }

            public boolean tileAction(MapTile pTile) {
                return !this.this$0.loadTile((OnlineTileSourceBase) this.this$0.mTileSource, pTile);
            }
        };
        return cacheManagerAction;
    }

    public CacheManagerAction getCleaningAction() {
        CacheManagerAction cacheManagerAction;
        new CacheManagerAction(this) {
            final /* synthetic */ CacheManager this$0;

            {
                this.this$0 = this$0;
            }

            public boolean preCheck() {
                return true;
            }

            public int getProgressModulo() {
                return 1000;
            }

            public boolean tileAction(MapTile pTile) {
                return this.this$0.deleteTile(pTile);
            }
        };
        return cacheManagerAction;
    }

    public CacheManagerTask cleanAreaAsync(Context ctx, BoundingBox bb, int zoomMin, int zoomMax) {
        CacheManagerTask cacheManagerTask;
        new CacheManagerTask(this, getCleaningAction(), bb, zoomMin, zoomMax);
        CacheManagerTask task = cacheManagerTask;
        task.addCallback(getCleaningDialog(ctx, task));
        return execute(task);
    }

    public CacheManagerTask cleanAreaAsync(Context ctx, ArrayList<GeoPoint> geoPoints, int i, int zoomMax) {
        int zoomMin = i;
        return cleanAreaAsync(ctx, extendedBoundsFromGeoPoints(geoPoints, zoomMin), zoomMin, zoomMax);
    }

    public CacheManagerTask cleanAreaAsync(Context ctx, List<MapTile> tiles, int zoomMin, int zoomMax) {
        CacheManagerTask cacheManagerTask;
        new CacheManagerTask(this, getCleaningAction(), tiles, zoomMin, zoomMax);
        CacheManagerTask task = cacheManagerTask;
        task.addCallback(getCleaningDialog(ctx, task));
        return execute(task);
    }

    public BoundingBox extendedBoundsFromGeoPoints(ArrayList<GeoPoint> geoPoints, int i) {
        BoundingBox extendedBounds;
        int minZoomLevel = i;
        BoundingBox bb = BoundingBox.fromGeoPoints(geoPoints);
        Point mLowerRight = getMapTileFromCoordinates(bb.getLatSouth(), bb.getLonEast(), minZoomLevel);
        GeoPoint lowerRightPoint = getCoordinatesFromMapTile(mLowerRight.x + 1, mLowerRight.y + 1, minZoomLevel);
        Point mUpperLeft = getMapTileFromCoordinates(bb.getLatNorth(), bb.getLonWest(), minZoomLevel);
        GeoPoint upperLeftPoint = getCoordinatesFromMapTile(mUpperLeft.x - 1, mUpperLeft.y - 1, minZoomLevel);
        new BoundingBox(upperLeftPoint.getLatitude(), upperLeftPoint.getLongitude(), lowerRightPoint.getLatitude(), lowerRightPoint.getLongitude());
        return extendedBounds;
    }

    public long currentCacheUsage() {
        return directorySize(Configuration.getInstance().getOsmdroidTileCache());
    }

    public long cacheCapacity() {
        return Configuration.getInstance().getTileFileSystemCacheMaxBytes();
    }

    public long directorySize(File pDirectory) {
        long usedCacheSpace = 0;
        File[] z = pDirectory.listFiles();
        if (z != null) {
            File[] fileArr = z;
            int length = fileArr.length;
            for (int i = 0; i < length; i++) {
                File file = fileArr[i];
                if (file.isFile()) {
                    usedCacheSpace += file.length();
                } else if (file.isDirectory()) {
                    usedCacheSpace += directorySize(file);
                }
            }
        }
        return usedCacheSpace;
    }
}
