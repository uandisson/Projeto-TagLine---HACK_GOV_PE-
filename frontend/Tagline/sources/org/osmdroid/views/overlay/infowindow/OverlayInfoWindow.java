package org.osmdroid.views.overlay.infowindow;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;
import org.osmdroid.library.C1262R;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.OverlayWithIW;

public class OverlayInfoWindow extends BasicInfoWindow {
    protected OverlayWithIW mOverlayRef;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public OverlayInfoWindow(int layoutResId, MapView mapView) {
        super(layoutResId, mapView);
    }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public OverlayInfoWindow(MapView mapView) {
        super(C1262R.layout.bonuspack_bubble, mapView);
    }

    public OverlayWithIW getOverlayRef() {
        return this.mOverlayRef;
    }

    public void onOpen(Object obj) {
        Object item = obj;
        super.onOpen(item);
        this.mOverlayRef = (OverlayWithIW) item;
        ImageView imageView = (ImageView) this.mView.findViewById(mImageId);
        Drawable image = this.mOverlayRef.getImage();
        if (image != null) {
            imageView.setImageDrawable(image);
            imageView.setVisibility(0);
            return;
        }
        imageView.setVisibility(8);
    }

    public void onClose() {
        super.onClose();
        this.mOverlayRef = null;
    }
}
