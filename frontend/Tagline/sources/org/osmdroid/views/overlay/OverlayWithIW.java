package org.osmdroid.views.overlay;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.infowindow.InfoWindow;

public abstract class OverlayWithIW extends Overlay {
    protected Drawable mImage;
    protected InfoWindow mInfoWindow;
    protected Object mRelatedObject;
    protected String mSnippet;
    protected String mSubDescription;
    protected String mTitle;

    public abstract void accept(OverlayWithIWVisitor overlayWithIWVisitor);

    public abstract void showInfoWindow();

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    @Deprecated
    public OverlayWithIW(Context context) {
        this();
        Context context2 = context;
    }

    public OverlayWithIW() {
    }

    public void setDraggable(boolean draggable) {
    }

    public boolean isDraggable() {
        return false;
    }

    public void setTitle(String title) {
        String str = title;
        this.mTitle = str;
    }

    public String getTitle() {
        return this.mTitle;
    }

    public void setSnippet(String snippet) {
        String str = snippet;
        this.mSnippet = str;
    }

    public String getSnippet() {
        return this.mSnippet;
    }

    public void setSubDescription(String subDescription) {
        String str = subDescription;
        this.mSubDescription = str;
    }

    public String getSubDescription() {
        return this.mSubDescription;
    }

    public void setRelatedObject(Object relatedObject) {
        Object obj = relatedObject;
        this.mRelatedObject = obj;
    }

    public Object getRelatedObject() {
        return this.mRelatedObject;
    }

    public void setInfoWindow(InfoWindow infoWindow) {
        InfoWindow infoWindow2 = infoWindow;
        this.mInfoWindow = infoWindow2;
    }

    public InfoWindow getInfoWindow() {
        return this.mInfoWindow;
    }

    public void setImage(Drawable image) {
        Drawable drawable = image;
        this.mImage = drawable;
    }

    public Drawable getImage() {
        return this.mImage;
    }

    public void closeInfoWindow() {
        if (this.mInfoWindow != null) {
            this.mInfoWindow.close();
        }
    }

    public void onDestroy() {
        if (this.mInfoWindow != null) {
            this.mInfoWindow.close();
            this.mInfoWindow.onDetach();
            this.mInfoWindow = null;
            this.mRelatedObject = null;
        }
    }

    public boolean isInfoWindowOpen() {
        return this.mInfoWindow != null && this.mInfoWindow.isOpen();
    }

    public void onDetach(MapView mapView) {
        Bitmap bitmap;
        MapView view = mapView;
        if (Build.VERSION.SDK_INT < 9 && (this.mImage instanceof BitmapDrawable) && (bitmap = ((BitmapDrawable) this.mImage).getBitmap()) != null) {
            bitmap.recycle();
        }
        setInfoWindow((InfoWindow) null);
        super.onDetach(view);
    }
}
