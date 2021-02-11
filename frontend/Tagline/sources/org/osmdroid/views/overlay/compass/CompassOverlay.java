package org.osmdroid.views.overlay.compass;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import org.osmdroid.library.C1262R;
import org.osmdroid.views.MapView;
import org.osmdroid.views.Projection;
import org.osmdroid.views.overlay.IOverlayMenuProvider;
import org.osmdroid.views.overlay.Overlay;

public class CompassOverlay extends Overlay implements IOverlayMenuProvider, IOrientationConsumer {
    public static final int MENU_COMPASS = getSafeMenuId();
    private float mAzimuth;
    private float mCompassCenterX;
    private float mCompassCenterY;
    protected Bitmap mCompassFrameBitmap;
    protected final float mCompassFrameCenterX;
    protected final float mCompassFrameCenterY;
    private final Matrix mCompassMatrix;
    private final float mCompassRadius;
    protected Bitmap mCompassRoseBitmap;
    protected final float mCompassRoseCenterX;
    protected final float mCompassRoseCenterY;
    private final Display mDisplay;
    private boolean mIsCompassEnabled;
    protected MapView mMapView;
    private boolean mOptionsMenuEnabled;
    public IOrientationProvider mOrientationProvider;
    protected final float mScale;
    private Paint sSmoothPaint;

    /* JADX WARNING: Illegal instructions before constructor call */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public CompassOverlay(android.content.Context r10, org.osmdroid.views.MapView r11) {
        /*
            r9 = this;
            r0 = r9
            r1 = r10
            r2 = r11
            r3 = r0
            r4 = r1
            org.osmdroid.views.overlay.compass.InternalCompassOrientationProvider r5 = new org.osmdroid.views.overlay.compass.InternalCompassOrientationProvider
            r8 = r5
            r5 = r8
            r6 = r8
            r7 = r1
            r6.<init>(r7)
            r6 = r2
            r3.<init>(r4, r5, r6)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.osmdroid.views.overlay.compass.CompassOverlay.<init>(android.content.Context, org.osmdroid.views.MapView):void");
    }

    public CompassOverlay(Context context, IOrientationProvider orientationProvider, MapView mapView) {
        Paint paint;
        Matrix matrix;
        Context context2 = context;
        new Paint(2);
        this.sSmoothPaint = paint;
        new Matrix();
        this.mCompassMatrix = matrix;
        this.mAzimuth = Float.NaN;
        this.mCompassCenterX = 35.0f;
        this.mCompassCenterY = 35.0f;
        this.mCompassRadius = 20.0f;
        this.mOptionsMenuEnabled = true;
        this.mScale = context2.getResources().getDisplayMetrics().density;
        this.mMapView = mapView;
        this.mDisplay = ((WindowManager) context2.getSystemService("window")).getDefaultDisplay();
        createCompassFramePicture();
        createCompassRosePicture();
        this.mCompassFrameCenterX = ((float) (this.mCompassFrameBitmap.getWidth() / 2)) - 0.5f;
        this.mCompassFrameCenterY = ((float) (this.mCompassFrameBitmap.getHeight() / 2)) - 0.5f;
        this.mCompassRoseCenterX = ((float) (this.mCompassRoseBitmap.getWidth() / 2)) - 0.5f;
        this.mCompassRoseCenterY = ((float) (this.mCompassRoseBitmap.getHeight() / 2)) - 0.5f;
        setOrientationProvider(orientationProvider);
    }

    public void onDetach(MapView mapView) {
        this.mMapView = null;
        this.sSmoothPaint = null;
        disableCompass();
        this.mCompassFrameBitmap.recycle();
        this.mCompassRoseBitmap.recycle();
        super.onDetach(mapView);
    }

    private void invalidateCompass() {
        Rect screenRect = this.mMapView.getProjection().getScreenRect();
        this.mMapView.postInvalidateMapCoordinates((screenRect.left + ((int) Math.ceil((double) ((this.mCompassCenterX - this.mCompassFrameCenterX) * this.mScale)))) - 2, (screenRect.top + ((int) Math.ceil((double) ((this.mCompassCenterY - this.mCompassFrameCenterY) * this.mScale)))) - 2, screenRect.left + ((int) Math.ceil((double) ((this.mCompassCenterX + this.mCompassFrameCenterX) * this.mScale))) + 2, screenRect.top + ((int) Math.ceil((double) ((this.mCompassCenterY + this.mCompassFrameCenterY) * this.mScale))) + 2);
    }

    public void setCompassCenter(float x, float y) {
        this.mCompassCenterX = x;
        this.mCompassCenterY = y;
    }

    public IOrientationProvider getOrientationProvider() {
        return this.mOrientationProvider;
    }

    public void setOrientationProvider(IOrientationProvider iOrientationProvider) throws RuntimeException {
        Throwable th;
        IOrientationProvider orientationProvider = iOrientationProvider;
        if (orientationProvider == null) {
            Throwable th2 = th;
            new RuntimeException("You must pass an IOrientationProvider to setOrientationProvider()");
            throw th2;
        }
        if (isCompassEnabled()) {
            this.mOrientationProvider.stopOrientationProvider();
        }
        this.mOrientationProvider = orientationProvider;
    }

    /* access modifiers changed from: protected */
    public void drawCompass(Canvas canvas, float bearing, Rect rect) {
        Canvas canvas2 = canvas;
        Rect rect2 = rect;
        Projection proj = this.mMapView.getProjection();
        float centerX = this.mCompassCenterX * this.mScale;
        float centerY = this.mCompassCenterY * this.mScale;
        this.mCompassMatrix.setTranslate(-this.mCompassFrameCenterX, -this.mCompassFrameCenterY);
        boolean postTranslate = this.mCompassMatrix.postTranslate(centerX, centerY);
        int save = canvas2.save();
        canvas2.concat(proj.getInvertedScaleRotateCanvasMatrix());
        canvas2.concat(this.mCompassMatrix);
        canvas2.drawBitmap(this.mCompassFrameBitmap, 0.0f, 0.0f, this.sSmoothPaint);
        canvas2.restore();
        this.mCompassMatrix.setRotate(-bearing, this.mCompassRoseCenterX, this.mCompassRoseCenterY);
        boolean postTranslate2 = this.mCompassMatrix.postTranslate(-this.mCompassRoseCenterX, -this.mCompassRoseCenterY);
        boolean postTranslate3 = this.mCompassMatrix.postTranslate(centerX, centerY);
        int save2 = canvas2.save();
        canvas2.concat(proj.getInvertedScaleRotateCanvasMatrix());
        canvas2.concat(this.mCompassMatrix);
        canvas2.drawBitmap(this.mCompassRoseBitmap, 0.0f, 0.0f, this.sSmoothPaint);
        canvas2.restore();
    }

    public void draw(Canvas canvas, MapView mapView, boolean shadow) {
        Canvas c = canvas;
        MapView mapView2 = mapView;
        if (!shadow && isCompassEnabled() && !Float.isNaN(this.mAzimuth)) {
            drawCompass(c, this.mAzimuth + ((float) getDisplayOrientation()), mapView2.getProjection().getScreenRect());
        }
    }

    public void setOptionsMenuEnabled(boolean pOptionsMenuEnabled) {
        boolean z = pOptionsMenuEnabled;
        this.mOptionsMenuEnabled = z;
    }

    public boolean isOptionsMenuEnabled() {
        return this.mOptionsMenuEnabled;
    }

    public boolean onCreateOptionsMenu(Menu pMenu, int pMenuIdOffset, MapView mapView) {
        MapView pMapView = mapView;
        MenuItem checkable = pMenu.add(0, MENU_COMPASS + pMenuIdOffset, 0, pMapView.getContext().getResources().getString(C1262R.string.compass)).setIcon(pMapView.getContext().getResources().getDrawable(C1262R.C1263drawable.ic_menu_compass)).setCheckable(true);
        return true;
    }

    public boolean onPrepareOptionsMenu(Menu pMenu, int pMenuIdOffset, MapView mapView) {
        MapView mapView2 = mapView;
        MenuItem checked = pMenu.findItem(MENU_COMPASS + pMenuIdOffset).setChecked(isCompassEnabled());
        return false;
    }

    public boolean onOptionsItemSelected(MenuItem pItem, int pMenuIdOffset, MapView mapView) {
        MapView mapView2 = mapView;
        if (pItem.getItemId() - pMenuIdOffset != MENU_COMPASS) {
            return false;
        }
        if (isCompassEnabled()) {
            disableCompass();
        } else {
            boolean enableCompass = enableCompass();
        }
        return true;
    }

    public void onOrientationChanged(float orientation, IOrientationProvider iOrientationProvider) {
        IOrientationProvider iOrientationProvider2 = iOrientationProvider;
        this.mAzimuth = orientation;
        invalidateCompass();
    }

    public boolean enableCompass(IOrientationProvider orientationProvider) {
        setOrientationProvider(orientationProvider);
        boolean success = this.mOrientationProvider.startOrientationProvider(this);
        this.mIsCompassEnabled = success;
        if (this.mMapView != null) {
            invalidateCompass();
        }
        return success;
    }

    public boolean enableCompass() {
        return enableCompass(this.mOrientationProvider);
    }

    public void disableCompass() {
        this.mIsCompassEnabled = false;
        if (this.mOrientationProvider != null) {
            this.mOrientationProvider.stopOrientationProvider();
        }
        this.mOrientationProvider = null;
        this.mAzimuth = Float.NaN;
        if (this.mMapView != null) {
            invalidateCompass();
        }
    }

    public boolean isCompassEnabled() {
        return this.mIsCompassEnabled;
    }

    public float getOrientation() {
        return this.mAzimuth;
    }

    private Point calculatePointOnCircle(float centerX, float centerY, float f, float degrees) {
        Point point;
        float radius = f;
        double dblRadians = Math.toRadians((double) ((-degrees) + 90.0f));
        new Point(((int) centerX) + ((int) (((double) radius) * Math.cos(dblRadians))), ((int) centerY) - ((int) (((double) radius) * Math.sin(dblRadians))));
        return point;
    }

    private void drawTriangle(Canvas canvas, float x, float y, float radius, float f, Paint paint) {
        Path path;
        Canvas canvas2 = canvas;
        float degrees = f;
        int save = canvas2.save();
        Point point = calculatePointOnCircle(x, y, radius, degrees);
        canvas2.rotate(degrees, (float) point.x, (float) point.y);
        new Path();
        Path p = path;
        p.moveTo(((float) point.x) - (2.0f * this.mScale), (float) point.y);
        p.lineTo(((float) point.x) + (2.0f * this.mScale), (float) point.y);
        p.lineTo((float) point.x, ((float) point.y) - (5.0f * this.mScale));
        p.close();
        canvas2.drawPath(p, paint);
        canvas2.restore();
    }

    private int getDisplayOrientation() {
        switch (this.mDisplay.getOrientation()) {
            case 1:
                return 90;
            case 2:
                return 180;
            case 3:
                return 270;
            default:
                return 0;
        }
    }

    private void createCompassFramePicture() {
        Paint paint;
        Paint paint2;
        Canvas canvas;
        new Paint();
        Paint innerPaint = paint;
        innerPaint.setColor(-1);
        innerPaint.setAntiAlias(true);
        innerPaint.setStyle(Paint.Style.FILL);
        innerPaint.setAlpha(200);
        new Paint();
        Paint outerPaint = paint2;
        outerPaint.setColor(-7829368);
        outerPaint.setAntiAlias(true);
        outerPaint.setStyle(Paint.Style.STROKE);
        outerPaint.setStrokeWidth(2.0f);
        outerPaint.setAlpha(200);
        int picBorderWidthAndHeight = (int) (50.0f * this.mScale);
        int center = picBorderWidthAndHeight / 2;
        if (this.mCompassFrameBitmap != null) {
            this.mCompassFrameBitmap.recycle();
        }
        this.mCompassFrameBitmap = Bitmap.createBitmap(picBorderWidthAndHeight, picBorderWidthAndHeight, Bitmap.Config.ARGB_8888);
        new Canvas(this.mCompassFrameBitmap);
        Canvas canvas2 = canvas;
        canvas2.drawCircle((float) center, (float) center, 20.0f * this.mScale, innerPaint);
        canvas2.drawCircle((float) center, (float) center, 20.0f * this.mScale, outerPaint);
        drawTriangle(canvas2, (float) center, (float) center, 20.0f * this.mScale, 0.0f, outerPaint);
        drawTriangle(canvas2, (float) center, (float) center, 20.0f * this.mScale, 90.0f, outerPaint);
        drawTriangle(canvas2, (float) center, (float) center, 20.0f * this.mScale, 180.0f, outerPaint);
        drawTriangle(canvas2, (float) center, (float) center, 20.0f * this.mScale, 270.0f, outerPaint);
    }

    private void createCompassRosePicture() {
        Paint paint;
        Paint paint2;
        Paint paint3;
        Canvas canvas;
        Path path;
        Path path2;
        new Paint();
        Paint northPaint = paint;
        northPaint.setColor(-6291456);
        northPaint.setAntiAlias(true);
        northPaint.setStyle(Paint.Style.FILL);
        northPaint.setAlpha(220);
        new Paint();
        Paint southPaint = paint2;
        southPaint.setColor(-16777216);
        southPaint.setAntiAlias(true);
        southPaint.setStyle(Paint.Style.FILL);
        southPaint.setAlpha(220);
        new Paint();
        Paint centerPaint = paint3;
        centerPaint.setColor(-1);
        centerPaint.setAntiAlias(true);
        centerPaint.setStyle(Paint.Style.FILL);
        centerPaint.setAlpha(220);
        int picBorderWidthAndHeight = (int) (50.0f * this.mScale);
        int center = picBorderWidthAndHeight / 2;
        if (this.mCompassRoseBitmap != null) {
            this.mCompassRoseBitmap.recycle();
        }
        this.mCompassRoseBitmap = Bitmap.createBitmap(picBorderWidthAndHeight, picBorderWidthAndHeight, Bitmap.Config.ARGB_8888);
        new Canvas(this.mCompassRoseBitmap);
        Canvas canvas2 = canvas;
        new Path();
        Path pathNorth = path;
        pathNorth.moveTo((float) center, ((float) center) - (17.0f * this.mScale));
        pathNorth.lineTo(((float) center) + (4.0f * this.mScale), (float) center);
        pathNorth.lineTo(((float) center) - (4.0f * this.mScale), (float) center);
        pathNorth.lineTo((float) center, ((float) center) - (17.0f * this.mScale));
        pathNorth.close();
        canvas2.drawPath(pathNorth, northPaint);
        new Path();
        Path pathSouth = path2;
        pathSouth.moveTo((float) center, ((float) center) + (17.0f * this.mScale));
        pathSouth.lineTo(((float) center) + (4.0f * this.mScale), (float) center);
        pathSouth.lineTo(((float) center) - (4.0f * this.mScale), (float) center);
        pathSouth.lineTo((float) center, ((float) center) + (17.0f * this.mScale));
        pathSouth.close();
        canvas2.drawPath(pathSouth, southPaint);
        canvas2.drawCircle((float) center, (float) center, 2.0f, centerPaint);
    }
}
