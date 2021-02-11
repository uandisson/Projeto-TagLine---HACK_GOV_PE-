package org.osmdroid.config;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import java.io.File;
import java.net.Proxy;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import org.osmdroid.tileprovider.constants.OpenStreetMapTileProviderConstants;
import org.osmdroid.tileprovider.modules.SqlTileWriter;
import org.osmdroid.tileprovider.util.StorageUtils;

public class DefaultConfigurationProvider implements IConfigurationProvider {
    protected int animationSpeedDefault;
    protected int animationSpeedShort;
    protected short cacheMapTileCount;
    protected boolean debugMapTileDownloader = false;
    protected boolean debugMapView = false;
    protected boolean debugMode = false;
    protected boolean debugTileProviders = false;
    protected long expirationAdder;
    protected Long expirationOverride;
    protected long gpsWaitTime = 20000;
    protected SimpleDateFormat httpHeaderDateTimeFormat;
    protected Proxy httpProxy;
    protected boolean isMapViewHardwareAccelerated = false;
    private final Map<String, String> mAdditionalHttpRequestProperties;
    protected boolean mapViewRecycler;
    protected File osmdroidBasePath;
    protected File osmdroidTileCache;
    protected short tileDownloadMaxQueueSize;
    protected short tileDownloadThreads;
    protected long tileFileSystemCacheMaxBytes;
    protected long tileFileSystemCacheTrimBytes;
    protected short tileFileSystemMaxQueueSize;
    protected short tileFileSystemThreads;
    protected String userAgentHttpHeader = "User-Agent";
    protected String userAgentValue = "osmdroid";

    public DefaultConfigurationProvider() {
        Map<String, String> map;
        SimpleDateFormat simpleDateFormat;
        File file;
        File file2;
        new HashMap();
        this.mAdditionalHttpRequestProperties = map;
        this.cacheMapTileCount = 9;
        this.tileDownloadThreads = 2;
        this.tileFileSystemThreads = 8;
        this.tileDownloadMaxQueueSize = 40;
        this.tileFileSystemMaxQueueSize = 40;
        this.tileFileSystemCacheMaxBytes = 629145600;
        this.tileFileSystemCacheTrimBytes = 524288000;
        new SimpleDateFormat(OpenStreetMapTileProviderConstants.HTTP_EXPIRES_HEADER_FORMAT, Locale.US);
        this.httpHeaderDateTimeFormat = simpleDateFormat;
        this.expirationAdder = 0;
        this.expirationOverride = null;
        this.httpProxy = null;
        this.animationSpeedDefault = 1000;
        this.animationSpeedShort = 500;
        this.mapViewRecycler = true;
        try {
            new File(StorageUtils.getStorage().getAbsolutePath(), "osmdroid");
            this.osmdroidBasePath = file;
            new File(getOsmdroidBasePath(), "tiles");
            this.osmdroidTileCache = file2;
            boolean mkdirs = this.osmdroidBasePath.mkdirs();
            boolean mkdirs2 = this.osmdroidTileCache.mkdirs();
        } catch (Exception e) {
            Exception exc = e;
        }
    }

    public long getGpsWaitTime() {
        return this.gpsWaitTime;
    }

    public void setGpsWaitTime(long gpsWaitTime2) {
        long j = gpsWaitTime2;
        this.gpsWaitTime = j;
    }

    public boolean isDebugMode() {
        return this.debugMode;
    }

    public void setDebugMode(boolean debugMode2) {
        boolean z = debugMode2;
        this.debugMode = z;
    }

    public boolean isDebugMapView() {
        return this.debugMapView;
    }

    public void setDebugMapView(boolean debugMapView2) {
        boolean z = debugMapView2;
        this.debugMapView = z;
    }

    public boolean isDebugTileProviders() {
        return this.debugTileProviders;
    }

    public void setDebugTileProviders(boolean debugTileProviders2) {
        boolean z = debugTileProviders2;
        this.debugTileProviders = z;
    }

    public boolean isDebugMapTileDownloader() {
        return this.debugMapTileDownloader;
    }

    public void setDebugMapTileDownloader(boolean debugMapTileDownloader2) {
        boolean z = debugMapTileDownloader2;
        this.debugMapTileDownloader = z;
    }

    public boolean isMapViewHardwareAccelerated() {
        return this.isMapViewHardwareAccelerated;
    }

    public void setMapViewHardwareAccelerated(boolean mapViewHardwareAccelerated) {
        boolean z = mapViewHardwareAccelerated;
        this.isMapViewHardwareAccelerated = z;
    }

    public String getUserAgentValue() {
        return this.userAgentValue;
    }

    public void setUserAgentValue(String userAgentValue2) {
        String str = userAgentValue2;
        this.userAgentValue = str;
    }

    public Map<String, String> getAdditionalHttpRequestProperties() {
        return this.mAdditionalHttpRequestProperties;
    }

    public short getCacheMapTileCount() {
        return this.cacheMapTileCount;
    }

    public void setCacheMapTileCount(short cacheMapTileCount2) {
        short s = cacheMapTileCount2;
        this.cacheMapTileCount = s;
    }

    public short getTileDownloadThreads() {
        return this.tileDownloadThreads;
    }

    public void setTileDownloadThreads(short tileDownloadThreads2) {
        short s = tileDownloadThreads2;
        this.tileDownloadThreads = s;
    }

    public short getTileFileSystemThreads() {
        return this.tileFileSystemThreads;
    }

    public void setTileFileSystemThreads(short tileFileSystemThreads2) {
        short s = tileFileSystemThreads2;
        this.tileFileSystemThreads = s;
    }

    public short getTileDownloadMaxQueueSize() {
        return this.tileDownloadMaxQueueSize;
    }

    public void setTileDownloadMaxQueueSize(short tileDownloadMaxQueueSize2) {
        short s = tileDownloadMaxQueueSize2;
        this.tileDownloadMaxQueueSize = s;
    }

    public short getTileFileSystemMaxQueueSize() {
        return this.tileFileSystemMaxQueueSize;
    }

    public void setTileFileSystemMaxQueueSize(short tileFileSystemMaxQueueSize2) {
        short s = tileFileSystemMaxQueueSize2;
        this.tileFileSystemMaxQueueSize = s;
    }

    public long getTileFileSystemCacheMaxBytes() {
        return this.tileFileSystemCacheMaxBytes;
    }

    public void setTileFileSystemCacheMaxBytes(long tileFileSystemCacheMaxBytes2) {
        long j = tileFileSystemCacheMaxBytes2;
        this.tileFileSystemCacheMaxBytes = j;
    }

    public long getTileFileSystemCacheTrimBytes() {
        return this.tileFileSystemCacheTrimBytes;
    }

    public void setTileFileSystemCacheTrimBytes(long tileFileSystemCacheTrimBytes2) {
        long j = tileFileSystemCacheTrimBytes2;
        this.tileFileSystemCacheTrimBytes = j;
    }

    public SimpleDateFormat getHttpHeaderDateTimeFormat() {
        return this.httpHeaderDateTimeFormat;
    }

    public void setHttpHeaderDateTimeFormat(SimpleDateFormat httpHeaderDateTimeFormat2) {
        SimpleDateFormat simpleDateFormat = httpHeaderDateTimeFormat2;
        this.httpHeaderDateTimeFormat = simpleDateFormat;
    }

    public Proxy getHttpProxy() {
        return this.httpProxy;
    }

    public void setHttpProxy(Proxy httpProxy2) {
        Proxy proxy = httpProxy2;
        this.httpProxy = proxy;
    }

    public File getOsmdroidBasePath() {
        return this.osmdroidBasePath;
    }

    public void setOsmdroidBasePath(File osmdroidBasePath2) {
        File file = osmdroidBasePath2;
        this.osmdroidBasePath = file;
    }

    public File getOsmdroidTileCache() {
        return this.osmdroidTileCache;
    }

    public void setOsmdroidTileCache(File osmdroidTileCache2) {
        File file = osmdroidTileCache2;
        this.osmdroidTileCache = file;
    }

    public String getUserAgentHttpHeader() {
        return this.userAgentHttpHeader;
    }

    public void setUserAgentHttpHeader(String userAgentHttpHeader2) {
        String str = userAgentHttpHeader2;
        this.userAgentHttpHeader = str;
    }

    public void load(Context context, SharedPreferences sharedPreferences) {
        File file;
        File file2;
        File file3;
        StringBuilder sb;
        File file4;
        StringBuilder sb2;
        Context ctx = context;
        SharedPreferences prefs = sharedPreferences;
        if (!prefs.contains("osmdroid.basePath")) {
            File discoveredBestPath = getOsmdroidBasePath();
            File discoveredCachPath = getOsmdroidTileCache();
            if (!discoveredBestPath.exists() || !StorageUtils.isWritable(discoveredBestPath)) {
                new StringBuilder();
                new File(sb2.append("/data/data/").append(ctx.getPackageName()).append("/osmdroid/").toString());
                File file5 = file4;
                discoveredBestPath = file5;
                discoveredCachPath = file5;
                boolean mkdirs = discoveredCachPath.mkdirs();
            }
            SharedPreferences.Editor edit = prefs.edit();
            SharedPreferences.Editor putString = edit.putString("osmdroid.basePath", discoveredBestPath.getAbsolutePath());
            SharedPreferences.Editor putString2 = edit.putString("osmdroid.cachePath", discoveredCachPath.getAbsolutePath());
            boolean commit = edit.commit();
            setOsmdroidBasePath(discoveredBestPath);
            setOsmdroidTileCache(discoveredCachPath);
            setUserAgentValue(ctx.getPackageName());
            save(ctx, prefs);
        } else {
            new File(prefs.getString("osmdroid.basePath", getOsmdroidBasePath().getAbsolutePath()));
            setOsmdroidBasePath(file);
            new File(prefs.getString("osmdroid.cachePath", getOsmdroidTileCache().getAbsolutePath()));
            setOsmdroidTileCache(file2);
            setDebugMode(prefs.getBoolean("osmdroid.DebugMode", false));
            setDebugMapTileDownloader(prefs.getBoolean("osmdroid.DebugDownloading", false));
            setDebugMapView(prefs.getBoolean("osmdroid.DebugMapView", false));
            setDebugTileProviders(prefs.getBoolean("osmdroid.DebugTileProvider", false));
            setMapViewHardwareAccelerated(prefs.getBoolean("osmdroid.HardwareAcceleration", false));
            setUserAgentValue(prefs.getString("osmdroid.userAgentValue", ctx.getPackageName()));
            load(prefs, this.mAdditionalHttpRequestProperties, "osmdroid.additionalHttpRequestProperty.");
            setGpsWaitTime(prefs.getLong("osmdroid.gpsWaitTime", this.gpsWaitTime));
            setTileDownloadThreads((short) prefs.getInt("osmdroid.tileDownloadThreads", this.tileDownloadThreads));
            setTileFileSystemThreads((short) prefs.getInt("osmdroid.tileFileSystemThreads", this.tileFileSystemThreads));
            setTileDownloadMaxQueueSize((short) prefs.getInt("osmdroid.tileDownloadMaxQueueSize", this.tileDownloadMaxQueueSize));
            setTileFileSystemMaxQueueSize((short) prefs.getInt("osmdroid.tileFileSystemMaxQueueSize", this.tileFileSystemMaxQueueSize));
            setExpirationExtendedDuration(prefs.getLong("osmdroid.ExpirationExtendedDuration", this.expirationAdder));
            setMapViewRecyclerFriendly(prefs.getBoolean("osmdroid.mapViewRecycler", this.mapViewRecycler));
            setAnimationSpeedDefault(prefs.getInt("osmdroid.ZoomSpeedDefault", this.animationSpeedDefault));
            setAnimationSpeedShort(prefs.getInt("osmdroid.animationSpeedShort", this.animationSpeedShort));
            if (prefs.contains("osmdroid.ExpirationOverride")) {
                this.expirationOverride = Long.valueOf(prefs.getLong("osmdroid.ExpirationOverride", -1));
                if (this.expirationOverride != null && this.expirationOverride.longValue() == -1) {
                    this.expirationOverride = null;
                }
            }
        }
        if (Build.VERSION.SDK_INT >= 9) {
            new StringBuilder();
            new File(sb.append(getOsmdroidTileCache().getAbsolutePath()).append(File.separator).append(SqlTileWriter.DATABASE_FILENAME).toString());
            File dbFile = file3;
            if (dbFile.exists()) {
                long cacheSize = dbFile.length();
                long freeSpace = getOsmdroidTileCache().getFreeSpace();
                if (getTileFileSystemCacheMaxBytes() > freeSpace + cacheSize) {
                    setTileFileSystemCacheMaxBytes((long) (((double) (freeSpace + cacheSize)) * 0.95d));
                    setTileFileSystemCacheTrimBytes((long) (((double) (freeSpace + cacheSize)) * 0.9d));
                    return;
                }
                return;
            }
            long freeSpace2 = getOsmdroidTileCache().length();
            if (getTileFileSystemCacheMaxBytes() > freeSpace2) {
                setTileFileSystemCacheMaxBytes((long) (((double) freeSpace2) * 0.95d));
                setTileFileSystemCacheMaxBytes((long) (((double) freeSpace2) * 0.9d));
            }
        }
    }

    public void save(Context context, SharedPreferences sharedPreferences) {
        Context context2 = context;
        SharedPreferences prefs = sharedPreferences;
        SharedPreferences.Editor edit = prefs.edit();
        SharedPreferences.Editor putString = edit.putString("osmdroid.basePath", getOsmdroidBasePath().getAbsolutePath());
        SharedPreferences.Editor putString2 = edit.putString("osmdroid.cachePath", getOsmdroidTileCache().getAbsolutePath());
        SharedPreferences.Editor putBoolean = edit.putBoolean("osmdroid.DebugMode", isDebugMode());
        SharedPreferences.Editor putBoolean2 = edit.putBoolean("osmdroid.DebugDownloading", isDebugMapTileDownloader());
        SharedPreferences.Editor putBoolean3 = edit.putBoolean("osmdroid.DebugMapView", isDebugMapView());
        SharedPreferences.Editor putBoolean4 = edit.putBoolean("osmdroid.DebugTileProvider", isDebugTileProviders());
        SharedPreferences.Editor putBoolean5 = edit.putBoolean("osmdroid.HardwareAcceleration", isMapViewHardwareAccelerated());
        SharedPreferences.Editor putString3 = edit.putString("osmdroid.userAgentValue", getUserAgentValue());
        save(prefs, edit, this.mAdditionalHttpRequestProperties, "osmdroid.additionalHttpRequestProperty.");
        SharedPreferences.Editor putLong = edit.putLong("osmdroid.gpsWaitTime", this.gpsWaitTime);
        SharedPreferences.Editor putInt = edit.putInt("osmdroid.cacheMapTileCount", this.cacheMapTileCount);
        SharedPreferences.Editor putInt2 = edit.putInt("osmdroid.tileDownloadThreads", this.tileDownloadThreads);
        SharedPreferences.Editor putInt3 = edit.putInt("osmdroid.tileFileSystemThreads", this.tileFileSystemThreads);
        SharedPreferences.Editor putInt4 = edit.putInt("osmdroid.tileDownloadMaxQueueSize", this.tileDownloadMaxQueueSize);
        SharedPreferences.Editor putInt5 = edit.putInt("osmdroid.tileFileSystemMaxQueueSize", this.tileFileSystemMaxQueueSize);
        SharedPreferences.Editor putLong2 = edit.putLong("osmdroid.ExpirationExtendedDuration", this.expirationAdder);
        if (this.expirationOverride != null) {
            SharedPreferences.Editor putLong3 = edit.putLong("osmdroid.ExpirationOverride", this.expirationOverride.longValue());
        }
        SharedPreferences.Editor putInt6 = edit.putInt("osmdroid.ZoomSpeedDefault", this.animationSpeedDefault);
        SharedPreferences.Editor putInt7 = edit.putInt("osmdroid.animationSpeedShort", this.animationSpeedShort);
        SharedPreferences.Editor putBoolean6 = edit.putBoolean("osmdroid.mapViewRecycler", this.mapViewRecycler);
        boolean commit = edit.commit();
    }

    private static void load(SharedPreferences sharedPreferences, Map<String, String> map, String str) {
        SharedPreferences pPrefs = sharedPreferences;
        Map<String, String> pMap = map;
        String pPrefix = str;
        pMap.clear();
        for (String key : pPrefs.getAll().keySet()) {
            if (key.startsWith(pPrefix)) {
                String put = pMap.put(key.substring(pPrefix.length()), pPrefs.getString(key, (String) null));
            }
        }
    }

    private static void save(SharedPreferences pPrefs, SharedPreferences.Editor editor, Map<String, String> map, String str) {
        StringBuilder sb;
        SharedPreferences.Editor pEdit = editor;
        Map<String, String> pMap = map;
        String pPrefix = str;
        for (String key : pPrefs.getAll().keySet()) {
            if (key.startsWith(pPrefix)) {
                SharedPreferences.Editor remove = pEdit.remove(key);
            }
        }
        for (Map.Entry<String, String> entry : pMap.entrySet()) {
            new StringBuilder();
            SharedPreferences.Editor putString = pEdit.putString(sb.append(pPrefix).append(entry.getKey()).toString(), entry.getValue());
        }
    }

    public long getExpirationExtendedDuration() {
        return this.expirationAdder;
    }

    public void setExpirationExtendedDuration(long j) {
        long period = j;
        if (period < 0) {
            this.expirationAdder = 0;
            return;
        }
        this.expirationAdder = period;
    }

    public void setExpirationOverrideDuration(Long period) {
        Long l = period;
        this.expirationOverride = l;
    }

    public Long getExpirationOverrideDuration() {
        return this.expirationOverride;
    }

    public void setAnimationSpeedDefault(int durationsMilliseconds) {
        int i = durationsMilliseconds;
        this.animationSpeedDefault = i;
    }

    public int getAnimationSpeedDefault() {
        return this.animationSpeedDefault;
    }

    public void setAnimationSpeedShort(int durationsMilliseconds) {
        int i = durationsMilliseconds;
        this.animationSpeedShort = i;
    }

    public int getAnimationSpeedShort() {
        return this.animationSpeedShort;
    }

    public boolean isMapViewRecyclerFriendly() {
        return this.mapViewRecycler;
    }

    public void setMapViewRecyclerFriendly(boolean enabled) {
        boolean z = enabled;
        this.mapViewRecycler = z;
    }
}
