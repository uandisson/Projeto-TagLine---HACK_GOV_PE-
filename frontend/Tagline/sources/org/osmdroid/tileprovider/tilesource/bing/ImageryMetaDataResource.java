package org.osmdroid.tileprovider.tilesource.bing;

import org.json.JSONArray;
import org.json.JSONObject;

public class ImageryMetaDataResource {
    private static final String COPYRIGHT = "copyright";
    private static final String IMAGE_HEIGHT = "imageHeight";
    private static final String IMAGE_URL = "imageUrl";
    private static final String IMAGE_URL_SUBDOMAINS = "imageUrlSubdomains";
    private static final String IMAGE_WIDTH = "imageWidth";
    private static final String ZOOM_MAX = "ZoomMax";
    private static final String ZOOM_MIN = "ZoomMin";
    public String copyright = "";
    public int m_imageHeight = 256;
    public String m_imageUrl;
    public String[] m_imageUrlSubdomains;
    public int m_imageWidth = 256;
    public boolean m_isInitialised = false;
    private int m_subdomainsCounter = 0;
    public int m_zoomMax = 22;
    public int m_zoomMin = 1;

    public ImageryMetaDataResource() {
    }

    public static ImageryMetaDataResource getDefaultInstance() {
        ImageryMetaDataResource imageryMetaDataResource;
        ImageryMetaDataResource imageryMetaDataResource2 = imageryMetaDataResource;
        new ImageryMetaDataResource();
        return imageryMetaDataResource2;
    }

    public static ImageryMetaDataResource getInstanceFromJSON(JSONObject jSONObject, JSONObject jSONObject2) throws Exception {
        ImageryMetaDataResource imageryMetaDataResource;
        Throwable th;
        JSONObject a_jsonObject = jSONObject;
        JSONObject parent = jSONObject2;
        new ImageryMetaDataResource();
        ImageryMetaDataResource result = imageryMetaDataResource;
        if (a_jsonObject == null) {
            Throwable th2 = th;
            new Exception("JSON to parse is null");
            throw th2;
        }
        result.copyright = parent.getString(COPYRIGHT);
        if (a_jsonObject.has(IMAGE_HEIGHT)) {
            result.m_imageHeight = a_jsonObject.getInt(IMAGE_HEIGHT);
        }
        if (a_jsonObject.has(IMAGE_WIDTH)) {
            result.m_imageWidth = a_jsonObject.getInt(IMAGE_WIDTH);
        }
        if (a_jsonObject.has(ZOOM_MIN)) {
            result.m_zoomMin = a_jsonObject.getInt(ZOOM_MIN);
        }
        if (a_jsonObject.has(ZOOM_MAX)) {
            result.m_zoomMax = a_jsonObject.getInt(ZOOM_MAX);
        }
        result.m_imageUrl = a_jsonObject.getString(IMAGE_URL);
        if (result.m_imageUrl != null && result.m_imageUrl.matches(".*?\\{.*?\\}.*?")) {
            result.m_imageUrl = result.m_imageUrl.replaceAll("\\{.*?\\}", "%s");
        }
        JSONArray subdomains = a_jsonObject.getJSONArray(IMAGE_URL_SUBDOMAINS);
        if (subdomains != null && subdomains.length() >= 1) {
            result.m_imageUrlSubdomains = new String[subdomains.length()];
            for (int i = 0; i < subdomains.length(); i++) {
                result.m_imageUrlSubdomains[i] = subdomains.getString(i);
            }
        }
        result.m_isInitialised = true;
        return result;
    }

    public synchronized String getSubDomain() {
        String str;
        synchronized (this) {
            if (this.m_imageUrlSubdomains == null || this.m_imageUrlSubdomains.length <= 0) {
                str = null;
            } else {
                String result = this.m_imageUrlSubdomains[this.m_subdomainsCounter];
                if (this.m_subdomainsCounter < this.m_imageUrlSubdomains.length - 1) {
                    this.m_subdomainsCounter++;
                } else {
                    this.m_subdomainsCounter = 0;
                }
                str = result;
            }
        }
        return str;
    }
}
