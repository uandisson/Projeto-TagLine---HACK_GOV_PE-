package org.osmdroid.views.overlay.infowindow;

import android.graphics.drawable.Drawable;
import android.util.Log;
import android.widget.ImageView;
import org.osmdroid.api.IMapView;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;

public class MarkerInfoWindow extends BasicInfoWindow {
    protected Marker mMarkerRef;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public MarkerInfoWindow(int layoutResId, MapView mapView) {
        super(layoutResId, mapView);
    }

    public Marker getMarkerReference() {
        return this.mMarkerRef;
    }

    public void onOpen(Object obj) {
        Object item = obj;
        super.onOpen(item);
        this.mMarkerRef = (Marker) item;
        if (this.mView == null) {
            int w = Log.w(IMapView.LOGTAG, "Error trapped, MarkerInfoWindow.open, mView is null!");
            return;
        }
        ImageView imageView = (ImageView) this.mView.findViewById(mImageId);
        Drawable image = this.mMarkerRef.getImage();
        if (image != null) {
            imageView.setImageDrawable(image);
            imageView.setVisibility(0);
            return;
        }
        imageView.setVisibility(8);
    }

    public void onClose() {
        super.onClose();
        this.mMarkerRef = null;
    }
}
