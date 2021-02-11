package org.osmdroid.tileprovider.tilesource;

import android.util.Log;
import org.osmdroid.api.IMapView;
import org.osmdroid.tileprovider.MapTile;
import org.osmdroid.tileprovider.util.CloudmadeUtil;

public class CloudmadeTileSource extends OnlineTileSourceBase implements IStyledTileSource<Integer> {
    private Integer mStyle = 1;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public CloudmadeTileSource(String pName, int pZoomMinLevel, int pZoomMaxLevel, int pTileSizePixels, String pImageFilenameEnding, String[] pBaseUrl) {
        super(pName, pZoomMinLevel, pZoomMaxLevel, pTileSizePixels, pImageFilenameEnding, pBaseUrl);
    }

    public String pathBase() {
        StringBuilder sb;
        if (this.mStyle == null || this.mStyle.intValue() <= 1) {
            return this.mName;
        }
        new StringBuilder();
        return sb.append(this.mName).append(this.mStyle).toString();
    }

    public String getTileURLString(MapTile mapTile) {
        MapTile pTile = mapTile;
        String key = CloudmadeUtil.getCloudmadeKey();
        if (key.length() == 0) {
            int e = Log.e(IMapView.LOGTAG, "CloudMade key is not set. You should enter it in the manifest and call CloudmadeUtil.retrieveCloudmadeKey()");
        }
        String token = CloudmadeUtil.getCloudmadeToken();
        String baseUrl = getBaseUrl();
        Object[] objArr = new Object[8];
        objArr[0] = key;
        Object[] objArr2 = objArr;
        objArr2[1] = this.mStyle;
        Object[] objArr3 = objArr2;
        objArr3[2] = Integer.valueOf(getTileSizePixels());
        Object[] objArr4 = objArr3;
        objArr4[3] = Integer.valueOf(pTile.getZoomLevel());
        Object[] objArr5 = objArr4;
        objArr5[4] = Integer.valueOf(pTile.getX());
        Object[] objArr6 = objArr5;
        objArr6[5] = Integer.valueOf(pTile.getY());
        Object[] objArr7 = objArr6;
        objArr7[6] = this.mImageFilenameEnding;
        Object[] objArr8 = objArr7;
        objArr8[7] = token;
        return String.format(baseUrl, objArr8);
    }

    public void setStyle(Integer pStyle) {
        Integer num = pStyle;
        this.mStyle = num;
    }

    public void setStyle(String str) {
        StringBuilder sb;
        String pStyle = str;
        try {
            this.mStyle = Integer.valueOf(Integer.parseInt(pStyle));
        } catch (NumberFormatException e) {
            NumberFormatException numberFormatException = e;
            new StringBuilder();
            int e2 = Log.e(IMapView.LOGTAG, sb.append("Error setting integer style: ").append(pStyle).toString());
        }
    }

    public Integer getStyle() {
        return this.mStyle;
    }
}
