package org.osmdroid.views.overlay;

import android.content.Context;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import org.osmdroid.api.IGeoPoint;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.Projection;

public class Polyline extends OverlayWithIW {
    private final Rect mClipRect;
    private Matrix mDragMatrix;
    protected MotionEvent mDragStartPoint;
    protected boolean mDraggable;
    protected boolean mGeodesic;
    protected boolean mIsDragged;
    private final Rect mLineBounds;
    protected OnClickListener mOnClickListener;
    protected OnDragListener mOnDragListener;
    private double[][] mOriginalPoints;
    protected Paint mPaint;
    private ArrayList<Point> mPoints;
    private int mPointsPrecomputed;
    private float[] mPts;
    public boolean mRepeatPath;
    private final Point mTempPoint1;
    private final Point mTempPoint2;

    public interface OnClickListener {
        boolean onClick(Polyline polyline, MapView mapView, GeoPoint geoPoint);

        boolean onLongClick(Polyline polyline, MapView mapView, GeoPoint geoPoint);
    }

    public interface OnDragListener {
        void onDrag(Polyline polyline);

        void onDragEnd(Polyline polyline);

        void onDragStart(Polyline polyline);
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    @Deprecated
    public Polyline(Context context) {
        this();
        Context context2 = context;
    }

    public Polyline() {
        Paint paint;
        Rect rect;
        Matrix matrix;
        Rect rect2;
        Point point;
        Point point2;
        new Paint();
        this.mPaint = paint;
        this.mRepeatPath = false;
        this.mPts = null;
        new Rect();
        this.mClipRect = rect;
        new Matrix();
        this.mDragMatrix = matrix;
        new Rect();
        this.mLineBounds = rect2;
        new Point();
        this.mTempPoint1 = point;
        new Point();
        this.mTempPoint2 = point2;
        this.mDragStartPoint = null;
        this.mPaint.setColor(-16777216);
        this.mPaint.setStrokeWidth(10.0f);
        this.mPaint.setStyle(Paint.Style.STROKE);
        this.mPaint.setAntiAlias(true);
        clearPath();
        this.mOriginalPoints = (double[][]) Array.newInstance(Double.TYPE, new int[]{0, 2});
        this.mGeodesic = false;
    }

    /* access modifiers changed from: protected */
    public void clearPath() {
        ArrayList<Point> arrayList;
        new ArrayList<>();
        this.mPoints = arrayList;
        this.mPointsPrecomputed = 0;
        this.mPts = null;
    }

    /* access modifiers changed from: protected */
    public void addPoint(GeoPoint geoPoint) {
        GeoPoint aPoint = geoPoint;
        addPoint(aPoint.getLatitudeE6(), aPoint.getLongitudeE6());
    }

    /* access modifiers changed from: protected */
    public void addPoint(int aLatitudeE6, int aLongitudeE6) {
        Object obj;
        new Point(aLatitudeE6, aLongitudeE6);
        boolean add = this.mPoints.add(obj);
    }

    public List<GeoPoint> getPoints() {
        List<GeoPoint> list;
        Object obj;
        new ArrayList(this.mOriginalPoints.length);
        List<GeoPoint> result = list;
        for (int i = 0; i < this.mOriginalPoints.length; i++) {
            new GeoPoint(this.mOriginalPoints[i][0], this.mOriginalPoints[i][1]);
            boolean add = result.add(obj);
        }
        return result;
    }

    public int getNumberOfPoints() {
        return this.mOriginalPoints.length;
    }

    public int getColor() {
        return this.mPaint.getColor();
    }

    public void setDraggable(boolean draggable) {
        boolean z = draggable;
        this.mDraggable = z;
    }

    public boolean isDraggable() {
        return this.mDraggable;
    }

    public float getWidth() {
        return this.mPaint.getStrokeWidth();
    }

    public Paint getPaint() {
        return this.mPaint;
    }

    public boolean isVisible() {
        return isEnabled();
    }

    public boolean isGeodesic() {
        return this.mGeodesic;
    }

    public void setColor(int color) {
        this.mPaint.setColor(color);
    }

    public void setWidth(float width) {
        this.mPaint.setStrokeWidth(width);
    }

    public void setVisible(boolean visible) {
        setEnabled(visible);
    }

    public void setOnClickListener(OnClickListener listener) {
        OnClickListener onClickListener = listener;
        this.mOnClickListener = onClickListener;
    }

    public void setOnDragListener(OnDragListener listener) {
        OnDragListener onDragListener = listener;
        this.mOnDragListener = onDragListener;
    }

    /* access modifiers changed from: protected */
    public void addGreatCircle(GeoPoint geoPoint, GeoPoint geoPoint2, int i) {
        GeoPoint startPoint = geoPoint;
        GeoPoint endPoint = geoPoint2;
        int numberOfPoints = i;
        double lat1 = startPoint.getLatitude() * 0.01745329238474369d;
        double lon1 = startPoint.getLongitude() * 0.01745329238474369d;
        double lat2 = endPoint.getLatitude() * 0.01745329238474369d;
        double lon2 = endPoint.getLongitude() * 0.01745329238474369d;
        double d = 2.0d * Math.asin(Math.sqrt(Math.pow(Math.sin((lat1 - lat2) / 2.0d), 2.0d) + (Math.cos(lat1) * Math.cos(lat2) * Math.pow(Math.sin((lon1 - lon2) / 2.0d), 2.0d))));
        double bearing = Math.atan2(Math.sin(lon1 - lon2) * Math.cos(lat2), (Math.cos(lat1) * Math.sin(lat2)) - ((Math.sin(lat1) * Math.cos(lat2)) * Math.cos(lon1 - lon2))) / -0.01745329238474369d;
        double bearing2 = bearing < 0.0d ? 360.0d + bearing : bearing;
        for (int i2 = 1; i2 <= numberOfPoints; i2++) {
            double f = (1.0d * ((double) i2)) / ((double) (numberOfPoints + 1));
            double A = Math.sin((1.0d - f) * d) / Math.sin(d);
            double B = Math.sin(f * d) / Math.sin(d);
            double x = (A * Math.cos(lat1) * Math.cos(lon1)) + (B * Math.cos(lat2) * Math.cos(lon2));
            double y = (A * Math.cos(lat1) * Math.sin(lon1)) + (B * Math.cos(lat2) * Math.sin(lon2));
            addPoint((int) (Math.atan2((A * Math.sin(lat1)) + (B * Math.sin(lat2)), Math.sqrt(Math.pow(x, 2.0d) + Math.pow(y, 2.0d))) * 57.295780181884766d * 1000000.0d), (int) (Math.atan2(y, x) * 57.295780181884766d * 1000000.0d));
        }
    }

    public void setPoints(List<GeoPoint> list) {
        List<GeoPoint> points = list;
        clearPath();
        int size = points.size();
        this.mOriginalPoints = (double[][]) Array.newInstance(Double.TYPE, new int[]{size, 2});
        for (int i = 0; i < size; i++) {
            GeoPoint p = points.get(i);
            this.mOriginalPoints[i][0] = p.getLatitude();
            this.mOriginalPoints[i][1] = p.getLongitude();
            if (!this.mGeodesic) {
                addPoint(p);
            } else {
                if (i > 0) {
                    GeoPoint prev = points.get(i - 1);
                    addGreatCircle(prev, p, prev.distanceTo(p) / 100000);
                }
                addPoint(p);
            }
        }
    }

    public void setGeodesic(boolean geodesic) {
        boolean z = geodesic;
        this.mGeodesic = z;
    }

    /* access modifiers changed from: protected */
    public void precomputePoints(Projection projection) {
        Projection pj = projection;
        int size = this.mPoints.size();
        while (this.mPointsPrecomputed < size) {
            Point pt = this.mPoints.get(this.mPointsPrecomputed);
            Point projectedPixels = pj.toProjectedPixels(pt.x, pt.y, pt);
            this.mPointsPrecomputed++;
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:18:0x0176, code lost:
        if (r13.x >= r2.mClipRect.left) goto L_0x0178;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x01ae, code lost:
        if (r13.x <= r2.mClipRect.right) goto L_0x01b0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x01e6, code lost:
        if (r13.y >= r2.mClipRect.top) goto L_0x01e8;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:0x021e, code lost:
        if (r13.y > r2.mClipRect.bottom) goto L_0x0220;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:0x0220, code lost:
        r12.x = r13.x;
        r12.y = r13.y;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:41:0x0288, code lost:
        if (r20 != r21) goto L_0x028a;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:7:0x0083, code lost:
        if (r2.mPts.length < r6) goto L_0x0085;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void draw(android.graphics.Canvas r26, org.osmdroid.views.MapView r27, boolean r28) {
        /*
            r25 = this;
            r2 = r25
            r3 = r26
            r4 = r27
            r5 = r28
            r20 = r5
            if (r20 == 0) goto L_0x000d
        L_0x000c:
            return
        L_0x000d:
            r20 = r2
            r0 = r20
            java.util.ArrayList<android.graphics.Point> r0 = r0.mPoints
            r20 = r0
            int r20 = r20.size()
            r6 = r20
            r20 = r6
            r21 = 2
            r0 = r20
            r1 = r21
            if (r0 >= r1) goto L_0x0026
            goto L_0x000c
        L_0x0026:
            r20 = r4
            org.osmdroid.views.Projection r20 = r20.getProjection()
            r7 = r20
            r20 = r4
            org.osmdroid.views.Projection r20 = r20.getProjection()
            double r20 = r20.getZoomLevel()
            double r20 = org.osmdroid.util.TileSystem.MapSize((double) r20)
            r22 = 4611686018427387904(0x4000000000000000, double:2.0)
            double r20 = r20 / r22
            r0 = r20
            int r0 = (int) r0
            r20 = r0
            r8 = r20
            r20 = r7
            r21 = 0
            r22 = r8
            r23 = 2
            int r22 = r22 * 2
            r23 = 0
            android.graphics.Point r20 = r20.toPixelsFromMercator(r21, r22, r23)
            r0 = r20
            int r0 = r0.y
            r20 = r0
            r9 = r20
            r20 = r2
            r21 = r7
            r20.precomputePoints(r21)
            r20 = r2
            r0 = r20
            float[] r0 = r0.mPts
            r20 = r0
            if (r20 == 0) goto L_0x0085
            r20 = r2
            r0 = r20
            float[] r0 = r0.mPts
            r20 = r0
            r0 = r20
            int r0 = r0.length
            r20 = r0
            r21 = r6
            r0 = r20
            r1 = r21
            if (r0 >= r1) goto L_0x009f
        L_0x0085:
            r20 = r2
            r21 = 256(0x100, float:3.59E-43)
            r22 = 2
            r23 = r6
            int r22 = r22 * r23
            int r21 = java.lang.Math.max(r21, r22)
            r0 = r21
            float[] r0 = new float[r0]
            r21 = r0
            r0 = r21
            r1 = r20
            r1.mPts = r0
        L_0x009f:
            r20 = 0
            r10 = r20
            r20 = r2
            r0 = r20
            java.util.ArrayList<android.graphics.Point> r0 = r0.mPoints
            r20 = r0
            r21 = 0
            java.lang.Object r20 = r20.get(r21)
            android.graphics.Point r20 = (android.graphics.Point) r20
            r11 = r20
            r20 = r7
            r21 = r11
            r22 = r2
            r0 = r22
            android.graphics.Point r0 = r0.mTempPoint1
            r22 = r0
            android.graphics.Point r20 = r20.toPixelsFromProjected(r21, r22)
            r12 = r20
            r20 = r4
            r21 = r2
            r0 = r21
            android.graphics.Rect r0 = r0.mClipRect
            r21 = r0
            android.graphics.Rect r20 = r20.getScreenRect(r21)
            r20 = 1
            r14 = r20
        L_0x00d9:
            r20 = r14
            r21 = r6
            r0 = r20
            r1 = r21
            if (r0 >= r1) goto L_0x0504
            r20 = r2
            r0 = r20
            java.util.ArrayList<android.graphics.Point> r0 = r0.mPoints
            r20 = r0
            r21 = r14
            java.lang.Object r20 = r20.get(r21)
            android.graphics.Point r20 = (android.graphics.Point) r20
            r15 = r20
            r20 = r7
            r21 = r15
            r22 = r2
            r0 = r22
            android.graphics.Point r0 = r0.mTempPoint2
            r22 = r0
            android.graphics.Point r20 = r20.toPixelsFromProjected(r21, r22)
            r13 = r20
            r20 = r13
            r0 = r20
            int r0 = r0.x
            r20 = r0
            r21 = r12
            r0 = r21
            int r0 = r0.x
            r21 = r0
            int r20 = r20 - r21
            int r20 = java.lang.Math.abs(r20)
            r21 = r13
            r0 = r21
            int r0 = r0.y
            r21 = r0
            r22 = r12
            r0 = r22
            int r0 = r0.y
            r22 = r0
            int r21 = r21 - r22
            int r21 = java.lang.Math.abs(r21)
            int r20 = r20 + r21
            r21 = 1
            r0 = r20
            r1 = r21
            if (r0 > r1) goto L_0x0140
        L_0x013d:
            int r14 = r14 + 1
            goto L_0x00d9
        L_0x0140:
            r20 = r12
            r0 = r20
            int r0 = r0.x
            r20 = r0
            r21 = r2
            r0 = r21
            android.graphics.Rect r0 = r0.mClipRect
            r21 = r0
            r0 = r21
            int r0 = r0.left
            r21 = r0
            r0 = r20
            r1 = r21
            if (r0 >= r1) goto L_0x0178
            r20 = r13
            r0 = r20
            int r0 = r0.x
            r20 = r0
            r21 = r2
            r0 = r21
            android.graphics.Rect r0 = r0.mClipRect
            r21 = r0
            r0 = r21
            int r0 = r0.left
            r21 = r0
            r0 = r20
            r1 = r21
            if (r0 < r1) goto L_0x0220
        L_0x0178:
            r20 = r12
            r0 = r20
            int r0 = r0.x
            r20 = r0
            r21 = r2
            r0 = r21
            android.graphics.Rect r0 = r0.mClipRect
            r21 = r0
            r0 = r21
            int r0 = r0.right
            r21 = r0
            r0 = r20
            r1 = r21
            if (r0 <= r1) goto L_0x01b0
            r20 = r13
            r0 = r20
            int r0 = r0.x
            r20 = r0
            r21 = r2
            r0 = r21
            android.graphics.Rect r0 = r0.mClipRect
            r21 = r0
            r0 = r21
            int r0 = r0.right
            r21 = r0
            r0 = r20
            r1 = r21
            if (r0 > r1) goto L_0x0220
        L_0x01b0:
            r20 = r12
            r0 = r20
            int r0 = r0.y
            r20 = r0
            r21 = r2
            r0 = r21
            android.graphics.Rect r0 = r0.mClipRect
            r21 = r0
            r0 = r21
            int r0 = r0.top
            r21 = r0
            r0 = r20
            r1 = r21
            if (r0 >= r1) goto L_0x01e8
            r20 = r13
            r0 = r20
            int r0 = r0.y
            r20 = r0
            r21 = r2
            r0 = r21
            android.graphics.Rect r0 = r0.mClipRect
            r21 = r0
            r0 = r21
            int r0 = r0.top
            r21 = r0
            r0 = r20
            r1 = r21
            if (r0 < r1) goto L_0x0220
        L_0x01e8:
            r20 = r12
            r0 = r20
            int r0 = r0.y
            r20 = r0
            r21 = r2
            r0 = r21
            android.graphics.Rect r0 = r0.mClipRect
            r21 = r0
            r0 = r21
            int r0 = r0.bottom
            r21 = r0
            r0 = r20
            r1 = r21
            if (r0 <= r1) goto L_0x0242
            r20 = r13
            r0 = r20
            int r0 = r0.y
            r20 = r0
            r21 = r2
            r0 = r21
            android.graphics.Rect r0 = r0.mClipRect
            r21 = r0
            r0 = r21
            int r0 = r0.bottom
            r21 = r0
            r0 = r20
            r1 = r21
            if (r0 <= r1) goto L_0x0242
        L_0x0220:
            r20 = r12
            r21 = r13
            r0 = r21
            int r0 = r0.x
            r21 = r0
            r0 = r21
            r1 = r20
            r1.x = r0
            r20 = r12
            r21 = r13
            r0 = r21
            int r0 = r0.y
            r21 = r0
            r0 = r21
            r1 = r20
            r1.y = r0
            goto L_0x013d
        L_0x0242:
            r20 = r13
            r0 = r20
            int r0 = r0.x
            r20 = r0
            r21 = r12
            r0 = r21
            int r0 = r0.x
            r21 = r0
            int r20 = r20 - r21
            int r20 = java.lang.Math.abs(r20)
            r21 = r8
            r0 = r20
            r1 = r21
            if (r0 > r1) goto L_0x028a
            r20 = r13
            r0 = r20
            int r0 = r0.y
            r20 = r0
            r21 = r9
            r0 = r20
            r1 = r21
            if (r0 < r1) goto L_0x04c0
            r20 = 1
        L_0x0272:
            r21 = r12
            r0 = r21
            int r0 = r0.y
            r21 = r0
            r22 = r9
            r0 = r21
            r1 = r22
            if (r0 < r1) goto L_0x04c4
            r21 = 1
        L_0x0284:
            r0 = r20
            r1 = r21
            if (r0 == r1) goto L_0x03fc
        L_0x028a:
            r20 = r12
            r0 = r20
            int r0 = r0.x
            r20 = r0
            r16 = r20
            r20 = r12
            r0 = r20
            int r0 = r0.y
            r20 = r0
            r17 = r20
            r20 = r13
            r0 = r20
            int r0 = r0.x
            r20 = r0
            r18 = r20
            r20 = r13
            r0 = r20
            int r0 = r0.y
            r20 = r0
            r19 = r20
            r20 = r13
            r0 = r20
            int r0 = r0.x
            r20 = r0
            r21 = r12
            r0 = r21
            int r0 = r0.x
            r21 = r0
            int r20 = r20 - r21
            int r20 = java.lang.Math.abs(r20)
            r21 = r8
            r0 = r20
            r1 = r21
            if (r0 <= r1) goto L_0x0300
            r20 = r13
            r0 = r20
            int r0 = r0.x
            r20 = r0
            r21 = r4
            int r21 = r21.getWidth()
            r22 = 2
            int r21 = r21 / 2
            r0 = r20
            r1 = r21
            if (r0 >= r1) goto L_0x04c8
            r20 = r18
            r21 = r8
            r22 = 2
            int r21 = r21 * 2
            int r20 = r20 + r21
            r18 = r20
            r20 = r16
            r21 = r8
            r22 = 2
            int r21 = r21 * 2
            int r20 = r20 - r21
            r16 = r20
        L_0x0300:
            r20 = r13
            r0 = r20
            int r0 = r0.y
            r20 = r0
            r21 = r9
            r0 = r20
            r1 = r21
            if (r0 < r1) goto L_0x04e2
            r20 = 1
        L_0x0312:
            r21 = r12
            r0 = r21
            int r0 = r0.y
            r21 = r0
            r22 = r9
            r0 = r21
            r1 = r22
            if (r0 < r1) goto L_0x04e6
            r21 = 1
        L_0x0324:
            r0 = r20
            r1 = r21
            if (r0 == r1) goto L_0x0352
            r20 = r13
            r0 = r20
            int r0 = r0.y
            r20 = r0
            r21 = r9
            r0 = r20
            r1 = r21
            if (r0 < r1) goto L_0x04ea
            r20 = r19
            r21 = r8
            r22 = 2
            int r21 = r21 * 2
            int r20 = r20 - r21
            r19 = r20
            r20 = r17
            r21 = r8
            r22 = 2
            int r21 = r21 * 2
            int r20 = r20 + r21
            r17 = r20
        L_0x0352:
            r20 = r10
            r21 = 4
            int r20 = r20 + 4
            r21 = r2
            r0 = r21
            float[] r0 = r0.mPts
            r21 = r0
            r0 = r21
            int r0 = r0.length
            r21 = r0
            r0 = r20
            r1 = r21
            if (r0 <= r1) goto L_0x0388
            r20 = r3
            r21 = r2
            r0 = r21
            float[] r0 = r0.mPts
            r21 = r0
            r22 = 0
            r23 = r10
            r24 = r2
            r0 = r24
            android.graphics.Paint r0 = r0.mPaint
            r24 = r0
            r20.drawLines(r21, r22, r23, r24)
            r20 = 0
            r10 = r20
        L_0x0388:
            r20 = r2
            r0 = r20
            float[] r0 = r0.mPts
            r20 = r0
            r21 = r10
            int r10 = r10 + 1
            r22 = r12
            r0 = r22
            int r0 = r0.x
            r22 = r0
            r0 = r22
            float r0 = (float) r0
            r22 = r0
            r20[r21] = r22
            r20 = r2
            r0 = r20
            float[] r0 = r0.mPts
            r20 = r0
            r21 = r10
            int r10 = r10 + 1
            r22 = r12
            r0 = r22
            int r0 = r0.y
            r22 = r0
            r0 = r22
            float r0 = (float) r0
            r22 = r0
            r20[r21] = r22
            r20 = r2
            r0 = r20
            float[] r0 = r0.mPts
            r20 = r0
            r21 = r10
            int r10 = r10 + 1
            r22 = r18
            r0 = r22
            float r0 = (float) r0
            r22 = r0
            r20[r21] = r22
            r20 = r2
            r0 = r20
            float[] r0 = r0.mPts
            r20 = r0
            r21 = r10
            int r10 = r10 + 1
            r22 = r19
            r0 = r22
            float r0 = (float) r0
            r22 = r0
            r20[r21] = r22
            r20 = r12
            r21 = r16
            r0 = r21
            r1 = r20
            r1.x = r0
            r20 = r12
            r21 = r17
            r0 = r21
            r1 = r20
            r1.y = r0
        L_0x03fc:
            r20 = r10
            r21 = 4
            int r20 = r20 + 4
            r21 = r2
            r0 = r21
            float[] r0 = r0.mPts
            r21 = r0
            r0 = r21
            int r0 = r0.length
            r21 = r0
            r0 = r20
            r1 = r21
            if (r0 <= r1) goto L_0x0432
            r20 = r3
            r21 = r2
            r0 = r21
            float[] r0 = r0.mPts
            r21 = r0
            r22 = 0
            r23 = r10
            r24 = r2
            r0 = r24
            android.graphics.Paint r0 = r0.mPaint
            r24 = r0
            r20.drawLines(r21, r22, r23, r24)
            r20 = 0
            r10 = r20
        L_0x0432:
            r20 = r2
            r0 = r20
            float[] r0 = r0.mPts
            r20 = r0
            r21 = r10
            int r10 = r10 + 1
            r22 = r12
            r0 = r22
            int r0 = r0.x
            r22 = r0
            r0 = r22
            float r0 = (float) r0
            r22 = r0
            r20[r21] = r22
            r20 = r2
            r0 = r20
            float[] r0 = r0.mPts
            r20 = r0
            r21 = r10
            int r10 = r10 + 1
            r22 = r12
            r0 = r22
            int r0 = r0.y
            r22 = r0
            r0 = r22
            float r0 = (float) r0
            r22 = r0
            r20[r21] = r22
            r20 = r2
            r0 = r20
            float[] r0 = r0.mPts
            r20 = r0
            r21 = r10
            int r10 = r10 + 1
            r22 = r13
            r0 = r22
            int r0 = r0.x
            r22 = r0
            r0 = r22
            float r0 = (float) r0
            r22 = r0
            r20[r21] = r22
            r20 = r2
            r0 = r20
            float[] r0 = r0.mPts
            r20 = r0
            r21 = r10
            int r10 = r10 + 1
            r22 = r13
            r0 = r22
            int r0 = r0.y
            r22 = r0
            r0 = r22
            float r0 = (float) r0
            r22 = r0
            r20[r21] = r22
            r20 = r12
            r21 = r13
            r0 = r21
            int r0 = r0.x
            r21 = r0
            r0 = r21
            r1 = r20
            r1.x = r0
            r20 = r12
            r21 = r13
            r0 = r21
            int r0 = r0.y
            r21 = r0
            r0 = r21
            r1 = r20
            r1.y = r0
            goto L_0x013d
        L_0x04c0:
            r20 = 0
            goto L_0x0272
        L_0x04c4:
            r21 = 0
            goto L_0x0284
        L_0x04c8:
            r20 = r18
            r21 = r8
            r22 = 2
            int r21 = r21 * 2
            int r20 = r20 - r21
            r18 = r20
            r20 = r16
            r21 = r8
            r22 = 2
            int r21 = r21 * 2
            int r20 = r20 + r21
            r16 = r20
            goto L_0x0300
        L_0x04e2:
            r20 = 0
            goto L_0x0312
        L_0x04e6:
            r21 = 0
            goto L_0x0324
        L_0x04ea:
            r20 = r19
            r21 = r8
            r22 = 2
            int r21 = r21 * 2
            int r20 = r20 + r21
            r19 = r20
            r20 = r17
            r21 = r8
            r22 = 2
            int r21 = r21 * 2
            int r20 = r20 - r21
            r17 = r20
            goto L_0x0352
        L_0x0504:
            r20 = r10
            if (r20 <= 0) goto L_0x0534
            r20 = r2
            r0 = r20
            android.graphics.Matrix r0 = r0.mDragMatrix
            r20 = r0
            r21 = r2
            r0 = r21
            float[] r0 = r0.mPts
            r21 = r0
            r20.mapPoints(r21)
            r20 = r3
            r21 = r2
            r0 = r21
            float[] r0 = r0.mPts
            r21 = r0
            r22 = 0
            r23 = r10
            r24 = r2
            r0 = r24
            android.graphics.Paint r0 = r0.mPaint
            r24 = r0
            r20.drawLines(r21, r22, r23, r24)
        L_0x0534:
            goto L_0x000c
        */
        throw new UnsupportedOperationException("Method not decompiled: org.osmdroid.views.overlay.Polyline.draw(android.graphics.Canvas, org.osmdroid.views.MapView, boolean):void");
    }

    public boolean isCloseTo(GeoPoint point, double d, MapView mapView) {
        double tolerance = d;
        Projection pj = mapView.getProjection();
        precomputePoints(pj);
        Point p = pj.toPixels(point, (Point) null);
        boolean found = false;
        for (int i = 0; i < this.mPointsPrecomputed - 1 && !found; i++) {
            Point projectedPoint1 = this.mPoints.get(i);
            if (i == 0) {
                Point pixelsFromProjected = pj.toPixelsFromProjected(projectedPoint1, this.mTempPoint1);
            } else {
                this.mTempPoint1.set(this.mTempPoint2.x, this.mTempPoint2.y);
            }
            Point pixelsFromProjected2 = pj.toPixelsFromProjected(this.mPoints.get(i + 1), this.mTempPoint2);
            found = linePointDist(this.mTempPoint1, this.mTempPoint2, p, true) <= tolerance;
        }
        return found;
    }

    private double dot(Point point, Point point2, Point point3) {
        Point A = point;
        Point B = point2;
        Point C = point3;
        double AB_X = (double) (B.x - A.x);
        double AB_Y = (double) (B.y - A.y);
        return (AB_X * ((double) (C.x - B.x))) + (AB_Y * ((double) (C.y - B.y)));
    }

    private double cross(Point point, Point point2, Point point3) {
        Point A = point;
        Point B = point2;
        Point C = point3;
        double AB_X = (double) (B.x - A.x);
        double AB_Y = (double) (B.y - A.y);
        double AC_X = (double) (C.x - A.x);
        return (AB_X * ((double) (C.y - A.y))) - (AB_Y * AC_X);
    }

    private double distance(Point point, Point point2) {
        Point A = point;
        Point B = point2;
        double dX = (double) (A.x - B.x);
        double dY = (double) (A.y - B.y);
        return Math.sqrt((dX * dX) + (dY * dY));
    }

    private double linePointDist(Point point, Point point2, Point point3, boolean z) {
        Point A = point;
        Point B = point2;
        Point C = point3;
        boolean isSegment = z;
        double dAB = distance(A, B);
        if (dAB == 0.0d) {
            return distance(A, C);
        }
        double dist = cross(A, B, C) / dAB;
        if (isSegment) {
            if (dot(A, B, C) > 0.0d) {
                return distance(B, C);
            }
            if (dot(B, A, C) > 0.0d) {
                return distance(A, C);
            }
        }
        return Math.abs(dist);
    }

    public void showInfoWindow(GeoPoint geoPoint) {
        GeoPoint position = geoPoint;
        if (this.mInfoWindow != null) {
            this.mInfoWindow.open(this, position, 0, 0);
        }
    }

    public boolean onSingleTapConfirmed(MotionEvent motionEvent, MapView mapView) {
        MotionEvent event = motionEvent;
        MapView mapView2 = mapView;
        GeoPoint eventPos = (GeoPoint) mapView2.getProjection().fromPixels((int) event.getX(), (int) event.getY());
        boolean touched = isCloseTo(eventPos, (double) this.mPaint.getStrokeWidth(), mapView2);
        if (!touched) {
            return touched;
        }
        if (this.mOnClickListener == null) {
            return onClickDefault(this, mapView2, eventPos);
        }
        return this.mOnClickListener.onClick(this, mapView2, eventPos);
    }

    public boolean onLongPress(MotionEvent motionEvent, MapView mapView) {
        MotionEvent event = motionEvent;
        MapView mapView2 = mapView;
        GeoPoint eventPos = (GeoPoint) mapView2.getProjection().fromPixels((int) event.getX(), (int) event.getY());
        boolean touched = isCloseTo(eventPos, (double) this.mPaint.getStrokeWidth(), mapView2);
        if (touched) {
            if (this.mDraggable) {
                this.mDragStartPoint = event;
            } else {
                boolean onLongClick = this.mOnClickListener.onLongClick(this, mapView2, eventPos);
            }
        }
        return touched;
    }

    public boolean onTouchEvent(MotionEvent motionEvent, MapView mapView) {
        MotionEvent event = motionEvent;
        MapView mapView2 = mapView;
        if (!this.mDraggable || !this.mIsDragged) {
            if (this.mDraggable && this.mDragStartPoint != null && event.getAction() == 2) {
                float dx = Math.abs(event.getX() - this.mDragStartPoint.getX());
                float dy = Math.abs(event.getX() - this.mDragStartPoint.getY());
                if (((double) dx) > 10.0d || ((double) dy) > 10.0d || ((double) ((dx * dx) + (dy * dy))) > 100.0d) {
                    this.mIsDragged = true;
                    closeInfoWindow();
                    if (this.mOnDragListener != null) {
                        this.mOnDragListener.onDragStart(this);
                    }
                    moveToEventPosition(event, mapView2);
                    return true;
                }
            } else if (this.mDraggable && this.mDragStartPoint != null && event.getAction() == 1) {
                if (this.mOnClickListener != null) {
                    boolean onLongClick = this.mOnClickListener.onLongClick(this, mapView2, (GeoPoint) mapView2.getProjection().fromPixels((int) event.getX(), (int) event.getY()));
                }
                return true;
            }
        } else if (event.getAction() == 1) {
            this.mIsDragged = false;
            finishMove(this.mDragStartPoint, event, mapView2);
            this.mDragStartPoint = null;
            if (this.mOnDragListener != null) {
                this.mOnDragListener.onDragEnd(this);
            }
            return true;
        } else if (event.getAction() == 2) {
            moveToEventPosition(event, mapView2);
            if (this.mOnDragListener != null) {
                this.mOnDragListener.onDrag(this);
            }
            return true;
        }
        return false;
    }

    /* access modifiers changed from: protected */
    public void moveToEventPosition(MotionEvent motionEvent, MapView mapView) {
        MotionEvent event = motionEvent;
        this.mDragMatrix.setTranslate(event.getX() - this.mDragStartPoint.getX(), event.getY() - this.mDragStartPoint.getY());
        mapView.invalidate();
    }

    /* access modifiers changed from: protected */
    public void finishMove(MotionEvent motionEvent, MotionEvent motionEvent2, MapView mapView) {
        GeoPoint geoPoint;
        StringBuilder sb;
        StringBuilder sb2;
        MotionEvent start = motionEvent;
        MotionEvent end = motionEvent2;
        MapView mapView2 = mapView;
        Projection proj = mapView2.getProjection();
        float dx = end.getX() - start.getX();
        float dy = end.getY() - start.getY();
        new GeoPoint(0.0d, 0.0d);
        GeoPoint gp = geoPoint;
        for (int i = 0; i < this.mOriginalPoints.length; i++) {
            Point pt = this.mPoints.get(i);
            Point projectedPixels = proj.toProjectedPixels(this.mOriginalPoints[i][0], this.mOriginalPoints[i][1], this.mTempPoint1);
            Point pixelsFromProjected = proj.toPixelsFromProjected(this.mTempPoint1, this.mTempPoint2);
            this.mTempPoint2.offset((int) dx, (int) dy);
            IGeoPoint fromPixels = proj.fromPixels(this.mTempPoint2.x, this.mTempPoint2.y, gp);
            new StringBuilder();
            int d = Log.d("Polyline", sb.append("Moving from (").append(this.mOriginalPoints[i][0]).append(", ").append(this.mOriginalPoints[i][1]).append(")").toString());
            this.mOriginalPoints[i][0] = gp.getLatitude();
            this.mOriginalPoints[i][1] = gp.getLongitude();
            new StringBuilder();
            int d2 = Log.d("Polyline", sb2.append("         to (").append(this.mOriginalPoints[i][0]).append(", ").append(this.mOriginalPoints[i][1]).append(")").toString());
            Point projectedPixels2 = proj.toProjectedPixels(gp, pt);
        }
        this.mDragMatrix.setTranslate(0.0f, 0.0f);
        mapView2.invalidate();
    }

    /* access modifiers changed from: protected */
    public boolean onClickDefault(Polyline polyline, MapView mapView, GeoPoint eventPos) {
        MapView mapView2 = mapView;
        polyline.showInfoWindow(eventPos);
        return true;
    }

    public void onDetach(MapView mapView) {
        MapView mapView2 = mapView;
        this.mOnClickListener = null;
        onDestroy();
    }

    public void accept(OverlayWithIWVisitor visitor) {
        visitor.visit(this);
    }

    public void showInfoWindow() {
        if (this.mInfoWindow == null) {
        }
    }
}
