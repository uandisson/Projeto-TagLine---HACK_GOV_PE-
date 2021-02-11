package org.osmdroid.views.overlay;

import android.graphics.Point;
import android.graphics.drawable.Drawable;
import org.osmdroid.api.IGeoPoint;

public class OverlayItem {
    protected static final Point DEFAULT_MARKER_SIZE;
    public static final int ITEM_STATE_FOCUSED_MASK = 4;
    public static final int ITEM_STATE_PRESSED_MASK = 1;
    public static final int ITEM_STATE_SELECTED_MASK = 2;
    protected final IGeoPoint mGeoPoint;
    protected HotspotPlace mHotspotPlace;
    protected Drawable mMarker;
    protected final String mSnippet;
    protected final String mTitle;
    protected final String mUid;

    public enum HotspotPlace {
    }

    static {
        Point point;
        new Point(26, 94);
        DEFAULT_MARKER_SIZE = point;
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public OverlayItem(String aTitle, String aSnippet, IGeoPoint aGeoPoint) {
        this((String) null, aTitle, aSnippet, aGeoPoint);
    }

    public OverlayItem(String aUid, String aTitle, String aDescription, IGeoPoint aGeoPoint) {
        this.mTitle = aTitle;
        this.mSnippet = aDescription;
        this.mGeoPoint = aGeoPoint;
        this.mUid = aUid;
    }

    public String getUid() {
        return this.mUid;
    }

    public String getTitle() {
        return this.mTitle;
    }

    public String getSnippet() {
        return this.mSnippet;
    }

    public IGeoPoint getPoint() {
        return this.mGeoPoint;
    }

    public Drawable getMarker(int i) {
        int stateBitset = i;
        if (this.mMarker == null) {
            return null;
        }
        setState(this.mMarker, stateBitset);
        return this.mMarker;
    }

    public void setMarker(Drawable marker) {
        Drawable drawable = marker;
        this.mMarker = drawable;
    }

    public void setMarkerHotspot(HotspotPlace hotspotPlace) {
        HotspotPlace place = hotspotPlace;
        this.mHotspotPlace = place == null ? HotspotPlace.BOTTOM_CENTER : place;
    }

    public HotspotPlace getMarkerHotspot() {
        return this.mHotspotPlace;
    }

    public static void setState(Drawable drawable, int i) {
        Drawable drawable2 = drawable;
        int stateBitset = i;
        int[] states = new int[3];
        int index = 0;
        if ((stateBitset & 1) > 0) {
            index = 0 + 1;
            states[0] = 16842919;
        }
        if ((stateBitset & 2) > 0) {
            int i2 = index;
            index++;
            states[i2] = 16842913;
        }
        if ((stateBitset & 4) > 0) {
            int i3 = index;
            int index2 = index + 1;
            states[i3] = 16842908;
        }
        boolean state = drawable2.setState(states);
    }

    public Drawable getDrawable() {
        return this.mMarker;
    }

    public int getWidth() {
        return this.mMarker.getIntrinsicWidth();
    }

    public int getHeight() {
        return this.mMarker.getIntrinsicHeight();
    }
}
