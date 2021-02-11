package org.osmdroid.views.overlay;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.TypedValue;
import android.view.MotionEvent;
import org.osmdroid.library.C1262R;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.Projection;
import org.osmdroid.views.overlay.infowindow.MarkerInfoWindow;

public class Marker extends OverlayWithIW {
    public static final float ANCHOR_BOTTOM = 1.0f;
    public static final float ANCHOR_CENTER = 0.5f;
    public static final float ANCHOR_LEFT = 0.0f;
    public static final float ANCHOR_RIGHT = 1.0f;
    public static final float ANCHOR_TOP = 0.0f;
    public static boolean ENABLE_TEXT_LABELS_WHEN_NO_IMAGE = false;
    protected static Drawable mDefaultIcon = null;
    protected static MarkerInfoWindow mDefaultInfoWindow = null;
    protected float mAlpha;
    protected float mAnchorU;
    protected float mAnchorV;
    protected float mBearing;
    protected float mDragOffsetY;
    protected boolean mDraggable;
    protected boolean mFlat;
    protected float mIWAnchorU;
    protected float mIWAnchorV;
    protected Drawable mIcon;
    protected boolean mIsDragged;
    protected OnMarkerClickListener mOnMarkerClickListener;
    protected OnMarkerDragListener mOnMarkerDragListener;
    protected boolean mPanToView;
    protected GeoPoint mPosition;
    protected Point mPositionPixels;
    protected int mTextLabelBackgroundColor;
    protected int mTextLabelFontSize;
    protected int mTextLabelForegroundColor;
    protected Resources resource;

    public interface OnMarkerClickListener {
        boolean onMarkerClick(Marker marker, MapView mapView);

        boolean onMarkerLongPress(Marker marker, MapView mapView);
    }

    public interface OnMarkerDragListener {
        void onMarkerDrag(Marker marker);

        void onMarkerDragEnd(Marker marker);

        void onMarkerDragStart(Marker marker);
    }

    /* JADX WARNING: Illegal instructions before constructor call */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public Marker(org.osmdroid.views.MapView r6) {
        /*
            r5 = this;
            r0 = r5
            r1 = r6
            r2 = r0
            r3 = r1
            r4 = r1
            android.content.Context r4 = r4.getContext()
            r2.<init>(r3, r4)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.osmdroid.views.overlay.Marker.<init>(org.osmdroid.views.MapView):void");
    }

    public Marker(MapView mapView, Context context) {
        GeoPoint geoPoint;
        Point point;
        MarkerInfoWindow markerInfoWindow;
        MapView mapView2 = mapView;
        Context resourceProxy = context;
        this.mTextLabelBackgroundColor = -1;
        this.mTextLabelForegroundColor = -16777216;
        this.mTextLabelFontSize = 24;
        this.resource = mapView2.getContext().getResources();
        this.mBearing = 0.0f;
        this.mAlpha = 1.0f;
        new GeoPoint(0.0d, 0.0d);
        this.mPosition = geoPoint;
        this.mAnchorU = 0.5f;
        this.mAnchorV = 0.5f;
        this.mIWAnchorU = 0.5f;
        this.mIWAnchorV = 0.0f;
        this.mDraggable = false;
        this.mIsDragged = false;
        new Point();
        this.mPositionPixels = point;
        this.mPanToView = true;
        this.mDragOffsetY = 0.0f;
        this.mFlat = false;
        this.mOnMarkerClickListener = null;
        this.mOnMarkerDragListener = null;
        if (mDefaultIcon == null) {
            mDefaultIcon = resourceProxy.getResources().getDrawable(C1262R.C1263drawable.marker_default);
        }
        this.mIcon = mDefaultIcon;
        if (mDefaultInfoWindow == null || mDefaultInfoWindow.getMapView() != mapView2) {
            new MarkerInfoWindow(C1262R.layout.bonuspack_bubble, mapView2);
            mDefaultInfoWindow = markerInfoWindow;
        }
        setInfoWindow(mDefaultInfoWindow);
    }

    public void setIcon(Drawable drawable) {
        Paint paint;
        Paint paint2;
        Canvas canvas;
        Drawable drawable2;
        Drawable icon = drawable;
        if (ENABLE_TEXT_LABELS_WHEN_NO_IMAGE && icon == null && this.mTitle != null && this.mTitle.length() > 0) {
            new Paint();
            Paint background = paint;
            background.setColor(this.mTextLabelBackgroundColor);
            new Paint();
            Paint p = paint2;
            p.setTextSize((float) this.mTextLabelFontSize);
            p.setColor(this.mTextLabelForegroundColor);
            p.setAntiAlias(true);
            Typeface typeface = p.setTypeface(Typeface.DEFAULT_BOLD);
            p.setTextAlign(Paint.Align.LEFT);
            int width = (int) (p.measureText(getTitle()) + 0.5f);
            float baseline = (float) ((int) ((-p.ascent()) + 0.5f));
            Bitmap image = Bitmap.createBitmap(width, (int) (baseline + p.descent() + 0.5f), Bitmap.Config.ARGB_8888);
            new Canvas(image);
            Canvas c = canvas;
            c.drawPaint(background);
            c.drawText(getTitle(), 0.0f, baseline, p);
            new BitmapDrawable(this.resource, image);
            this.mIcon = drawable2;
        } else if (!ENABLE_TEXT_LABELS_WHEN_NO_IMAGE && icon != null) {
            this.mIcon = icon;
        } else if (this.mIcon != null) {
            this.mIcon = icon;
        } else {
            this.mIcon = mDefaultIcon;
        }
    }

    public Drawable getIcon() {
        return this.mIcon;
    }

    public GeoPoint getPosition() {
        return this.mPosition;
    }

    public void setPosition(GeoPoint position) {
        GeoPoint clone = position.clone();
        this.mPosition = clone;
    }

    public float getRotation() {
        return this.mBearing;
    }

    public void setRotation(float rotation) {
        float f = rotation;
        this.mBearing = f;
    }

    public void setAnchor(float anchorU, float anchorV) {
        this.mAnchorU = anchorU;
        this.mAnchorV = anchorV;
    }

    public void setInfoWindowAnchor(float anchorU, float anchorV) {
        this.mIWAnchorU = anchorU;
        this.mIWAnchorV = anchorV;
    }

    public void setAlpha(float alpha) {
        float f = alpha;
        this.mAlpha = f;
    }

    public float getAlpha() {
        return this.mAlpha;
    }

    public void setDraggable(boolean draggable) {
        boolean z = draggable;
        this.mDraggable = z;
    }

    public boolean isDraggable() {
        return this.mDraggable;
    }

    public void setFlat(boolean flat) {
        boolean z = flat;
        this.mFlat = z;
    }

    public boolean isFlat() {
        return this.mFlat;
    }

    public void remove(MapView mapView) {
        boolean remove = mapView.getOverlays().remove(this);
    }

    public void setOnMarkerClickListener(OnMarkerClickListener listener) {
        OnMarkerClickListener onMarkerClickListener = listener;
        this.mOnMarkerClickListener = onMarkerClickListener;
    }

    public void setOnMarkerDragListener(OnMarkerDragListener listener) {
        OnMarkerDragListener onMarkerDragListener = listener;
        this.mOnMarkerDragListener = onMarkerDragListener;
    }

    public void setDragOffset(float mmUp) {
        float f = mmUp;
        this.mDragOffsetY = f;
    }

    public float getDragOffset() {
        return this.mDragOffsetY;
    }

    public void setInfoWindow(MarkerInfoWindow markerInfoWindow) {
        MarkerInfoWindow infoWindow = markerInfoWindow;
        if (!(this.mInfoWindow == null || this.mInfoWindow == mDefaultInfoWindow)) {
            this.mInfoWindow.onDetach();
        }
        this.mInfoWindow = infoWindow;
    }

    public void setPanToView(boolean panToView) {
        boolean z = panToView;
        this.mPanToView = z;
    }

    public void showInfoWindow() {
        if (this.mInfoWindow != null) {
            int markerWidth = this.mIcon.getIntrinsicWidth();
            int markerHeight = this.mIcon.getIntrinsicHeight();
            this.mInfoWindow.open(this, this.mPosition, ((int) (this.mIWAnchorU * ((float) markerWidth))) - ((int) (this.mAnchorU * ((float) markerWidth))), ((int) (this.mIWAnchorV * ((float) markerHeight))) - ((int) (this.mAnchorV * ((float) markerHeight))));
        }
    }

    public boolean isInfoWindowShown() {
        if (!(this.mInfoWindow instanceof MarkerInfoWindow)) {
            return super.isInfoWindowOpen();
        }
        MarkerInfoWindow iw = (MarkerInfoWindow) this.mInfoWindow;
        return iw != null && iw.isOpen() && iw.getMarkerReference() == this;
    }

    public void draw(Canvas canvas, MapView mapView, boolean shadow) {
        Rect rect;
        Canvas canvas2 = canvas;
        MapView mapView2 = mapView;
        if (!shadow && this.mIcon != null) {
            Point pixels = mapView2.getProjection().toPixels(this.mPosition, this.mPositionPixels);
            int width = this.mIcon.getIntrinsicWidth();
            int height = this.mIcon.getIntrinsicHeight();
            new Rect(0, 0, width, height);
            Rect rect2 = rect;
            rect2.offset(-((int) (this.mAnchorU * ((float) width))), -((int) (this.mAnchorV * ((float) height))));
            this.mIcon.setBounds(rect2);
            this.mIcon.setAlpha((int) (this.mAlpha * 255.0f));
            drawAt(canvas2, this.mIcon, this.mPositionPixels.x, this.mPositionPixels.y, false, this.mFlat ? -this.mBearing : mapView2.getMapOrientation() - this.mBearing);
        }
    }

    public void onDetach(MapView mapView) {
        Bitmap bitmap;
        MapView mapView2 = mapView;
        if (Build.VERSION.SDK_INT < 9 && (this.mIcon instanceof BitmapDrawable) && (bitmap = ((BitmapDrawable) this.mIcon).getBitmap()) != null) {
            bitmap.recycle();
        }
        this.mIcon = null;
        this.mOnMarkerClickListener = null;
        this.mOnMarkerDragListener = null;
        this.resource = null;
        setRelatedObject((Object) null);
        if (this.mInfoWindow != mDefaultInfoWindow && isInfoWindowShown()) {
            closeInfoWindow();
        }
        setInfoWindow((MarkerInfoWindow) null);
        onDestroy();
        super.onDetach(mapView2);
    }

    public static void cleanDefaults() {
        mDefaultIcon = null;
        mDefaultInfoWindow = null;
    }

    public boolean hitTest(MotionEvent motionEvent, MapView mapView) {
        MotionEvent event = motionEvent;
        Projection pj = mapView.getProjection();
        Point pixels = pj.toPixels(this.mPosition, this.mPositionPixels);
        Rect screenRect = pj.getIntrinsicScreenRect();
        return this.mIcon.getBounds().contains((-this.mPositionPixels.x) + screenRect.left + ((int) event.getX()), (-this.mPositionPixels.y) + screenRect.top + ((int) event.getY()));
    }

    public boolean onSingleTapConfirmed(MotionEvent event, MapView mapView) {
        MapView mapView2 = mapView;
        boolean touched = hitTest(event, mapView2);
        if (!touched) {
            return touched;
        }
        if (this.mOnMarkerClickListener == null) {
            return onMarkerClickDefault(this, mapView2);
        }
        return this.mOnMarkerClickListener.onMarkerClick(this, mapView2);
    }

    public void moveToEventPosition(MotionEvent motionEvent, MapView mapView) {
        MotionEvent event = motionEvent;
        MapView mapView2 = mapView;
        float offsetY = TypedValue.applyDimension(5, this.mDragOffsetY, mapView2.getContext().getResources().getDisplayMetrics());
        this.mPosition = (GeoPoint) mapView2.getProjection().fromPixels((int) event.getX(), (int) (event.getY() - offsetY));
        mapView2.invalidate();
    }

    public boolean onLongPress(MotionEvent motionEvent, MapView mapView) {
        MotionEvent event = motionEvent;
        MapView mapView2 = mapView;
        boolean touched = hitTest(event, mapView2);
        if (touched) {
            if (this.mDraggable) {
                this.mIsDragged = true;
                closeInfoWindow();
                if (this.mOnMarkerDragListener != null) {
                    this.mOnMarkerDragListener.onMarkerDragStart(this);
                }
                moveToEventPosition(event, mapView2);
            } else {
                boolean onMarkerLongPress = this.mOnMarkerClickListener.onMarkerLongPress(this, mapView2);
            }
        }
        return touched;
    }

    public boolean onTouchEvent(MotionEvent motionEvent, MapView mapView) {
        MotionEvent event = motionEvent;
        MapView mapView2 = mapView;
        if (!this.mDraggable || !this.mIsDragged) {
            return false;
        }
        if (event.getAction() == 1) {
            this.mIsDragged = false;
            if (this.mOnMarkerDragListener != null) {
                this.mOnMarkerDragListener.onMarkerDragEnd(this);
            }
            return true;
        } else if (event.getAction() != 2) {
            return false;
        } else {
            moveToEventPosition(event, mapView2);
            if (this.mOnMarkerDragListener != null) {
                this.mOnMarkerDragListener.onMarkerDrag(this);
            }
            return true;
        }
    }

    /* access modifiers changed from: protected */
    public boolean onMarkerClickDefault(Marker marker, MapView mapView) {
        Marker marker2 = marker;
        MapView mapView2 = mapView;
        marker2.showInfoWindow();
        if (marker2.mPanToView) {
            mapView2.getController().animateTo(marker2.getPosition());
        }
        return true;
    }

    public int getTextLabelBackgroundColor() {
        return this.mTextLabelBackgroundColor;
    }

    public void setTextLabelBackgroundColor(int mTextLabelBackgroundColor2) {
        int i = mTextLabelBackgroundColor2;
        this.mTextLabelBackgroundColor = i;
    }

    public int getTextLabelForegroundColor() {
        return this.mTextLabelForegroundColor;
    }

    public void setTextLabelForegroundColor(int mTextLabelForegroundColor2) {
        int i = mTextLabelForegroundColor2;
        this.mTextLabelForegroundColor = i;
    }

    public int getTextLabelFontSize() {
        return this.mTextLabelFontSize;
    }

    public void setTextLabelFontSize(int mTextLabelFontSize2) {
        int i = mTextLabelFontSize2;
        this.mTextLabelFontSize = i;
    }

    public void accept(OverlayWithIWVisitor visitor) {
        visitor.visit(this);
    }
}
