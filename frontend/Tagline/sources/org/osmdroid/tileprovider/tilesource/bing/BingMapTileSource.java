package org.osmdroid.tileprovider.tilesource.bing;

import android.content.Context;
import android.util.Log;
import java.util.Locale;
import microsoft.mappoint.TileSystem;
import org.osmdroid.api.IMapView;
import org.osmdroid.tileprovider.MapTile;
import org.osmdroid.tileprovider.tilesource.IStyledTileSource;
import org.osmdroid.tileprovider.tilesource.QuadTreeTileSource;
import org.osmdroid.tileprovider.util.ManifestUtil;

public class BingMapTileSource extends QuadTreeTileSource implements IStyledTileSource<String> {
    private static final String BASE_URL_PATTERN = "http://dev.virtualearth.net/REST/V1/Imagery/Metadata/%s?mapVersion=v1&output=json&key=%s";
    private static final String BING_KEY = "BING_KEY";
    private static final String FILENAME_ENDING = ".jpeg";
    public static final String IMAGERYSET_AERIAL = "Aerial";
    public static final String IMAGERYSET_AERIALWITHLABELS = "AerialWithLabels";
    public static final String IMAGERYSET_ROAD = "Road";
    private static String mBingMapKey = "";
    private String mBaseUrl;
    private ImageryMetaDataResource mImageryData = ImageryMetaDataResource.getDefaultInstance();
    private String mLocale;
    private String mStyle = IMAGERYSET_ROAD;
    private String mUrl;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public BingMapTileSource(String aLocale) {
        super("BingMaps", 0, 19, 256, FILENAME_ENDING, (String[]) null);
        StringBuilder sb;
        this.mLocale = aLocale;
        if (this.mLocale == null) {
            new StringBuilder();
            this.mLocale = sb.append(Locale.getDefault().getLanguage()).append("-").append(Locale.getDefault().getCountry()).toString();
        }
    }

    public static void retrieveBingKey(Context aContext) {
        mBingMapKey = ManifestUtil.retrieveKey(aContext, BING_KEY);
    }

    public static String getBingKey() {
        return mBingMapKey;
    }

    public static void setBingKey(String key) {
        mBingMapKey = key;
    }

    public String getBaseUrl() {
        if (!this.mImageryData.m_isInitialised) {
            ImageryMetaDataResource initMetaData = initMetaData();
        }
        return this.mBaseUrl;
    }

    public String getTileURLString(MapTile mapTile) {
        MapTile pTile = mapTile;
        if (!this.mImageryData.m_isInitialised) {
            ImageryMetaDataResource initMetaData = initMetaData();
        }
        return String.format(this.mUrl, new Object[]{quadTree(pTile)});
    }

    public int getMinimumZoomLevel() {
        return this.mImageryData.m_zoomMin;
    }

    public int getMaximumZoomLevel() {
        return this.mImageryData.m_zoomMax;
    }

    public int getTileSizePixels() {
        return this.mImageryData.m_imageHeight;
    }

    public String pathBase() {
        StringBuilder sb;
        new StringBuilder();
        return sb.append(this.mName).append(this.mStyle).toString();
    }

    public String getCopyrightNotice() {
        return this.mImageryData.copyright;
    }

    public void setStyle(String str) {
        String pStyle = str;
        if (!pStyle.equals(this.mStyle)) {
            String str2 = this.mStyle;
            String str3 = str2;
            synchronized (str2) {
                try {
                    this.mUrl = null;
                    this.mBaseUrl = null;
                    this.mImageryData.m_isInitialised = false;
                } catch (Throwable th) {
                    while (true) {
                        Throwable th2 = th;
                        String str4 = str3;
                        throw th2;
                    }
                }
            }
        }
        this.mStyle = pStyle;
        this.mName = pathBase();
    }

    public String getStyle() {
        return this.mStyle;
    }

    /* JADX INFO: finally extract failed */
    public ImageryMetaDataResource initMetaData() {
        if (!this.mImageryData.m_isInitialised) {
            synchronized (this) {
                try {
                    if (!this.mImageryData.m_isInitialised) {
                        ImageryMetaDataResource imageryData = getMetaData();
                        if (imageryData != null) {
                            this.mImageryData = imageryData;
                            TileSystem.setTileSize(getTileSizePixels());
                            updateBaseUrl();
                        }
                    }
                } catch (Throwable th) {
                    while (true) {
                        Throwable th2 = th;
                        throw th2;
                    }
                }
            }
        }
        return this.mImageryData;
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v0, resolved type: java.io.OutputStream} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r10v44, resolved type: java.io.BufferedOutputStream} */
    /* JADX WARNING: type inference failed for: r10v45, types: [java.io.ByteArrayOutputStream] */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private org.osmdroid.tileprovider.tilesource.bing.ImageryMetaDataResource getMetaData() {
        /*
            r18 = this;
            r1 = r18
            java.lang.String r10 = "OsmDroid"
            java.lang.String r11 = "getMetaData"
            int r10 = android.util.Log.d(r10, r11)
            r10 = 0
            r2 = r10
            java.net.URL r10 = new java.net.URL     // Catch:{ Exception -> 0x0169 }
            r17 = r10
            r10 = r17
            r11 = r17
            java.lang.String r12 = "http://dev.virtualearth.net/REST/V1/Imagery/Metadata/%s?mapVersion=v1&output=json&key=%s"
            r13 = 2
            java.lang.Object[] r13 = new java.lang.Object[r13]     // Catch:{ Exception -> 0x0169 }
            r17 = r13
            r13 = r17
            r14 = r17
            r15 = 0
            r16 = r1
            r0 = r16
            java.lang.String r0 = r0.mStyle     // Catch:{ Exception -> 0x0169 }
            r16 = r0
            r14[r15] = r16     // Catch:{ Exception -> 0x0169 }
            r17 = r13
            r13 = r17
            r14 = r17
            r15 = 1
            java.lang.String r16 = mBingMapKey     // Catch:{ Exception -> 0x0169 }
            r14[r15] = r16     // Catch:{ Exception -> 0x0169 }
            java.lang.String r12 = java.lang.String.format(r12, r13)     // Catch:{ Exception -> 0x0169 }
            r11.<init>(r12)     // Catch:{ Exception -> 0x0169 }
            java.net.URLConnection r10 = r10.openConnection()     // Catch:{ Exception -> 0x0169 }
            java.net.HttpURLConnection r10 = (java.net.HttpURLConnection) r10     // Catch:{ Exception -> 0x0169 }
            java.net.HttpURLConnection r10 = (java.net.HttpURLConnection) r10     // Catch:{ Exception -> 0x0169 }
            r2 = r10
            java.lang.String r10 = "OsmDroid"
            java.lang.StringBuilder r11 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0169 }
            r17 = r11
            r11 = r17
            r12 = r17
            r12.<init>()     // Catch:{ Exception -> 0x0169 }
            java.lang.String r12 = "make request "
            java.lang.StringBuilder r11 = r11.append(r12)     // Catch:{ Exception -> 0x0169 }
            r12 = r2
            java.net.URL r12 = r12.getURL()     // Catch:{ Exception -> 0x0169 }
            java.lang.String r12 = r12.toString()     // Catch:{ Exception -> 0x0169 }
            java.lang.String r12 = r12.toString()     // Catch:{ Exception -> 0x0169 }
            java.lang.StringBuilder r11 = r11.append(r12)     // Catch:{ Exception -> 0x0169 }
            java.lang.String r11 = r11.toString()     // Catch:{ Exception -> 0x0169 }
            int r10 = android.util.Log.d(r10, r11)     // Catch:{ Exception -> 0x0169 }
            r10 = r2
            org.osmdroid.config.IConfigurationProvider r11 = org.osmdroid.config.Configuration.getInstance()     // Catch:{ Exception -> 0x0169 }
            java.lang.String r11 = r11.getUserAgentHttpHeader()     // Catch:{ Exception -> 0x0169 }
            org.osmdroid.config.IConfigurationProvider r12 = org.osmdroid.config.Configuration.getInstance()     // Catch:{ Exception -> 0x0169 }
            java.lang.String r12 = r12.getUserAgentValue()     // Catch:{ Exception -> 0x0169 }
            r10.setRequestProperty(r11, r12)     // Catch:{ Exception -> 0x0169 }
            org.osmdroid.config.IConfigurationProvider r10 = org.osmdroid.config.Configuration.getInstance()     // Catch:{ Exception -> 0x0169 }
            java.util.Map r10 = r10.getAdditionalHttpRequestProperties()     // Catch:{ Exception -> 0x0169 }
            java.util.Set r10 = r10.entrySet()     // Catch:{ Exception -> 0x0169 }
            java.util.Iterator r10 = r10.iterator()     // Catch:{ Exception -> 0x0169 }
            r3 = r10
        L_0x009b:
            r10 = r3
            boolean r10 = r10.hasNext()     // Catch:{ Exception -> 0x0169 }
            if (r10 == 0) goto L_0x00bd
            r10 = r3
            java.lang.Object r10 = r10.next()     // Catch:{ Exception -> 0x0169 }
            java.util.Map$Entry r10 = (java.util.Map.Entry) r10     // Catch:{ Exception -> 0x0169 }
            r4 = r10
            r10 = r2
            r11 = r4
            java.lang.Object r11 = r11.getKey()     // Catch:{ Exception -> 0x0169 }
            java.lang.String r11 = (java.lang.String) r11     // Catch:{ Exception -> 0x0169 }
            r12 = r4
            java.lang.Object r12 = r12.getValue()     // Catch:{ Exception -> 0x0169 }
            java.lang.String r12 = (java.lang.String) r12     // Catch:{ Exception -> 0x0169 }
            r10.setRequestProperty(r11, r12)     // Catch:{ Exception -> 0x0169 }
            goto L_0x009b
        L_0x00bd:
            r10 = r2
            r10.connect()     // Catch:{ Exception -> 0x0169 }
            r10 = r2
            int r10 = r10.getResponseCode()     // Catch:{ Exception -> 0x0169 }
            r11 = 200(0xc8, float:2.8E-43)
            if (r10 == r11) goto L_0x011d
            java.lang.String r10 = "OsmDroid"
            java.lang.StringBuilder r11 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0169 }
            r17 = r11
            r11 = r17
            r12 = r17
            r12.<init>()     // Catch:{ Exception -> 0x0169 }
            java.lang.String r12 = "Cannot get response for url "
            java.lang.StringBuilder r11 = r11.append(r12)     // Catch:{ Exception -> 0x0169 }
            r12 = r2
            java.net.URL r12 = r12.getURL()     // Catch:{ Exception -> 0x0169 }
            java.lang.String r12 = r12.toString()     // Catch:{ Exception -> 0x0169 }
            java.lang.StringBuilder r11 = r11.append(r12)     // Catch:{ Exception -> 0x0169 }
            java.lang.String r12 = " "
            java.lang.StringBuilder r11 = r11.append(r12)     // Catch:{ Exception -> 0x0169 }
            r12 = r2
            java.lang.String r12 = r12.getResponseMessage()     // Catch:{ Exception -> 0x0169 }
            java.lang.StringBuilder r11 = r11.append(r12)     // Catch:{ Exception -> 0x0169 }
            java.lang.String r11 = r11.toString()     // Catch:{ Exception -> 0x0169 }
            int r10 = android.util.Log.e(r10, r11)     // Catch:{ Exception -> 0x0169 }
            r10 = 0
            r3 = r10
            r10 = r2
            if (r10 == 0) goto L_0x010d
            r10 = r2
            r10.disconnect()     // Catch:{ Exception -> 0x011a }
        L_0x010d:
            java.lang.String r10 = "OsmDroid"
            java.lang.String r11 = "end getMetaData"
            int r10 = android.util.Log.d(r10, r11)
            r10 = r3
            r1 = r10
        L_0x0119:
            return r1
        L_0x011a:
            r10 = move-exception
            r4 = r10
            goto L_0x010d
        L_0x011d:
            r10 = r2
            java.io.InputStream r10 = r10.getInputStream()     // Catch:{ Exception -> 0x0169 }
            r3 = r10
            java.io.ByteArrayOutputStream r10 = new java.io.ByteArrayOutputStream     // Catch:{ Exception -> 0x0169 }
            r17 = r10
            r10 = r17
            r11 = r17
            r11.<init>()     // Catch:{ Exception -> 0x0169 }
            r4 = r10
            java.io.BufferedOutputStream r10 = new java.io.BufferedOutputStream     // Catch:{ Exception -> 0x0169 }
            r17 = r10
            r10 = r17
            r11 = r17
            r12 = r4
            r13 = 8192(0x2000, float:1.14794E-41)
            r11.<init>(r12, r13)     // Catch:{ Exception -> 0x0169 }
            r5 = r10
            r10 = r3
            r11 = r5
            long r10 = org.osmdroid.tileprovider.util.StreamUtils.copy(r10, r11)     // Catch:{ Exception -> 0x0169 }
            r10 = r5
            r10.flush()     // Catch:{ Exception -> 0x0169 }
            r10 = r4
            java.lang.String r10 = r10.toString()     // Catch:{ Exception -> 0x0169 }
            org.osmdroid.tileprovider.tilesource.bing.ImageryMetaDataResource r10 = org.osmdroid.tileprovider.tilesource.bing.ImageryMetaData.getInstanceFromJSON(r10)     // Catch:{ Exception -> 0x0169 }
            r6 = r10
            r10 = r2
            if (r10 == 0) goto L_0x0159
            r10 = r2
            r10.disconnect()     // Catch:{ Exception -> 0x0166 }
        L_0x0159:
            java.lang.String r10 = "OsmDroid"
            java.lang.String r11 = "end getMetaData"
            int r10 = android.util.Log.d(r10, r11)
            r10 = r6
            r1 = r10
            goto L_0x0119
        L_0x0166:
            r10 = move-exception
            r7 = r10
            goto L_0x0159
        L_0x0169:
            r10 = move-exception
            r3 = r10
            java.lang.String r10 = "OsmDroid"
            java.lang.String r11 = "Error getting imagery meta data"
            r12 = r3
            int r10 = android.util.Log.e(r10, r11, r12)     // Catch:{ all -> 0x018d }
            r10 = r2
            if (r10 == 0) goto L_0x017d
            r10 = r2
            r10.disconnect()     // Catch:{ Exception -> 0x018a }
        L_0x017d:
            java.lang.String r10 = "OsmDroid"
            java.lang.String r11 = "end getMetaData"
            int r10 = android.util.Log.d(r10, r11)
            r10 = 0
            r1 = r10
            goto L_0x0119
        L_0x018a:
            r10 = move-exception
            r3 = r10
            goto L_0x017d
        L_0x018d:
            r10 = move-exception
            r8 = r10
            r10 = r2
            if (r10 == 0) goto L_0x0196
            r10 = r2
            r10.disconnect()     // Catch:{ Exception -> 0x01a2 }
        L_0x0196:
            java.lang.String r10 = "OsmDroid"
            java.lang.String r11 = "end getMetaData"
            int r10 = android.util.Log.d(r10, r11)
            r10 = r8
            throw r10
        L_0x01a2:
            r10 = move-exception
            r9 = r10
            goto L_0x0196
        */
        throw new UnsupportedOperationException("Method not decompiled: org.osmdroid.tileprovider.tilesource.bing.BingMapTileSource.getMetaData():org.osmdroid.tileprovider.tilesource.bing.ImageryMetaDataResource");
    }

    /* access modifiers changed from: protected */
    public void updateBaseUrl() {
        StringBuilder sb;
        int d = Log.d(IMapView.LOGTAG, "updateBaseUrl");
        String subDomain = this.mImageryData.getSubDomain();
        int idx = this.mImageryData.m_imageUrl.lastIndexOf("/");
        if (idx > 0) {
            this.mBaseUrl = this.mImageryData.m_imageUrl.substring(0, idx);
        } else {
            this.mBaseUrl = this.mImageryData.m_imageUrl;
        }
        this.mUrl = this.mImageryData.m_imageUrl;
        if (subDomain != null) {
            this.mBaseUrl = String.format(this.mBaseUrl, new Object[]{subDomain});
            String str = this.mUrl;
            Object[] objArr = new Object[3];
            objArr[0] = subDomain;
            Object[] objArr2 = objArr;
            objArr2[1] = "%s";
            Object[] objArr3 = objArr2;
            objArr3[2] = this.mLocale;
            this.mUrl = String.format(str, objArr3);
        }
        new StringBuilder();
        int d2 = Log.d(IMapView.LOGTAG, sb.append("updated url = ").append(this.mUrl).toString());
        int d3 = Log.d(IMapView.LOGTAG, "end updateBaseUrl");
    }
}
