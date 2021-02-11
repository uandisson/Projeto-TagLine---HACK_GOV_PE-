package org.osmdroid.views.overlay.infowindow;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.Iterator;
import org.osmdroid.api.IMapView;
import org.osmdroid.config.Configuration;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;

public abstract class InfoWindow {
    protected boolean mIsVisible = false;
    protected MapView mMapView;
    protected View mView;

    public abstract void onClose();

    public abstract void onOpen(Object obj);

    public InfoWindow(int layoutResId, MapView mapView) {
        MapView mapView2 = mapView;
        this.mMapView = mapView2;
        ViewGroup parent = (ViewGroup) mapView2.getParent();
        this.mView = ((LayoutInflater) mapView2.getContext().getSystemService("layout_inflater")).inflate(layoutResId, parent, false);
        this.mView.setTag(this);
    }

    public MapView getMapView() {
        return this.mMapView;
    }

    public View getView() {
        return this.mView;
    }

    public void open(Object object, GeoPoint position, int offsetX, int offsetY) {
        ViewGroup.LayoutParams layoutParams;
        StringBuilder sb;
        close();
        onOpen(object);
        new MapView.LayoutParams(-2, -2, position, 8, offsetX, offsetY);
        ViewGroup.LayoutParams layoutParams2 = layoutParams;
        if (this.mMapView == null || this.mView == null) {
            new StringBuilder();
            int w = Log.w(IMapView.LOGTAG, sb.append("Error trapped, InfoWindow.open mMapView: ").append(this.mMapView == null ? "null" : "ok").append(" mView: ").append(this.mView == null ? "null" : "ok").toString());
            return;
        }
        this.mMapView.addView(this.mView, layoutParams2);
        this.mIsVisible = true;
    }

    public void close() {
        if (this.mIsVisible) {
            this.mIsVisible = false;
            ((ViewGroup) this.mView.getParent()).removeView(this.mView);
            onClose();
        }
    }

    public void onDetach() {
        close();
        if (this.mView != null) {
            this.mView.setTag((Object) null);
        }
        this.mView = null;
        this.mMapView = null;
        if (Configuration.getInstance().isDebugMode()) {
            int d = Log.d(IMapView.LOGTAG, "Marked detached");
        }
    }

    public boolean isOpen() {
        return this.mIsVisible;
    }

    public static void closeAllInfoWindowsOn(MapView mapView) {
        Iterator<InfoWindow> it = getOpenedInfoWindowsOn(mapView).iterator();
        while (it.hasNext()) {
            it.next().close();
        }
    }

    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.util.ArrayList<org.osmdroid.views.overlay.infowindow.InfoWindow> getOpenedInfoWindowsOn(org.osmdroid.views.MapView r11) {
        /*
            r0 = r11
            r7 = r0
            int r7 = r7.getChildCount()
            r1 = r7
            java.util.ArrayList r7 = new java.util.ArrayList
            r10 = r7
            r7 = r10
            r8 = r10
            r9 = r1
            r8.<init>(r9)
            r2 = r7
            r7 = 0
            r3 = r7
        L_0x0013:
            r7 = r3
            r8 = r1
            if (r7 >= r8) goto L_0x0039
            r7 = r0
            r8 = r3
            android.view.View r7 = r7.getChildAt(r8)
            r4 = r7
            r7 = r4
            java.lang.Object r7 = r7.getTag()
            r5 = r7
            r7 = r5
            if (r7 == 0) goto L_0x0036
            r7 = r5
            boolean r7 = r7 instanceof org.osmdroid.views.overlay.infowindow.InfoWindow
            if (r7 == 0) goto L_0x0036
            r7 = r5
            org.osmdroid.views.overlay.infowindow.InfoWindow r7 = (org.osmdroid.views.overlay.infowindow.InfoWindow) r7
            r6 = r7
            r7 = r2
            r8 = r6
            boolean r7 = r7.add(r8)
        L_0x0036:
            int r3 = r3 + 1
            goto L_0x0013
        L_0x0039:
            r7 = r2
            r0 = r7
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.osmdroid.views.overlay.infowindow.InfoWindow.getOpenedInfoWindowsOn(org.osmdroid.views.MapView):java.util.ArrayList");
    }
}
