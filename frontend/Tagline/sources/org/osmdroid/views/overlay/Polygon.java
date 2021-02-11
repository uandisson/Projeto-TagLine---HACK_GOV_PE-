package org.osmdroid.views.overlay;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.RectF;
import android.graphics.Region;
import android.view.MotionEvent;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.osmdroid.api.IGeoPoint;
import org.osmdroid.util.BoundingBox;
import org.osmdroid.util.BoundingBoxE6;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.Projection;

public class Polygon extends OverlayWithIW {
    protected Matrix mDragMatrix;
    protected MotionEvent mDragStartPoint;
    protected boolean mDraggable;
    protected Paint mFillPaint;
    private ArrayList<LinearRing> mHoles;
    protected boolean mIsDragged;
    protected OnClickListener mOnClickListener;
    protected OnDragListener mOnDragListener;
    private LinearRing mOutline;
    protected Paint mOutlinePaint;
    /* access modifiers changed from: private */
    public final Path mPath;
    /* access modifiers changed from: private */
    public final Point mTempPoint1;
    /* access modifiers changed from: private */
    public final Point mTempPoint2;

    public interface OnClickListener {
        boolean onClick(Polygon polygon, MapView mapView, GeoPoint geoPoint);

        boolean onLongClick(Polygon polygon, MapView mapView, GeoPoint geoPoint);
    }

    public interface OnDragListener {
        void onDrag(Polygon polygon);

        void onDragEnd(Polygon polygon);

        void onDragStart(Polygon polygon);
    }

    class LinearRing {
        ArrayList<Point> mConvertedPoints;
        int[][] mOriginalPoints = ((int[][]) Array.newInstance(Integer.TYPE, new int[]{0, 2}));
        boolean mPrecomputed;
        final /* synthetic */ Polygon this$0;

        LinearRing(Polygon this$02) {
            ArrayList<Point> arrayList;
            this.this$0 = this$02;
            new ArrayList<>(0);
            this.mConvertedPoints = arrayList;
            this.mPrecomputed = false;
        }

        /* access modifiers changed from: package-private */
        /* JADX WARNING: Multi-variable type inference failed */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public java.util.ArrayList<org.osmdroid.util.GeoPoint> getPoints() {
            /*
                r11 = this;
                r0 = r11
                r5 = r0
                int[][] r5 = r5.mOriginalPoints
                int r5 = r5.length
                r1 = r5
                java.util.ArrayList r5 = new java.util.ArrayList
                r10 = r5
                r5 = r10
                r6 = r10
                r7 = r1
                r6.<init>(r7)
                r2 = r5
                r5 = 0
                r3 = r5
            L_0x0012:
                r5 = r3
                r6 = r1
                if (r5 >= r6) goto L_0x003a
                org.osmdroid.util.GeoPoint r5 = new org.osmdroid.util.GeoPoint
                r10 = r5
                r5 = r10
                r6 = r10
                r7 = r0
                int[][] r7 = r7.mOriginalPoints
                r8 = r3
                r7 = r7[r8]
                r8 = 0
                r7 = r7[r8]
                r8 = r0
                int[][] r8 = r8.mOriginalPoints
                r9 = r3
                r8 = r8[r9]
                r9 = 1
                r8 = r8[r9]
                r6.<init>((int) r7, (int) r8)
                r4 = r5
                r5 = r2
                r6 = r4
                boolean r5 = r5.add(r6)
                int r3 = r3 + 1
                goto L_0x0012
            L_0x003a:
                r5 = r2
                r0 = r5
                return r0
            */
            throw new UnsupportedOperationException("Method not decompiled: org.osmdroid.views.overlay.Polygon.LinearRing.getPoints():java.util.ArrayList");
        }

        /* access modifiers changed from: package-private */
        public void setPoints(List<GeoPoint> list) {
            ArrayList<Point> arrayList;
            Object obj;
            List<GeoPoint> points = list;
            int size = points.size();
            this.mOriginalPoints = (int[][]) Array.newInstance(Integer.TYPE, new int[]{size, 2});
            new ArrayList<>(size);
            this.mConvertedPoints = arrayList;
            int i = 0;
            for (GeoPoint p : points) {
                this.mOriginalPoints[i][0] = p.getLatitudeE6();
                this.mOriginalPoints[i][1] = p.getLongitudeE6();
                new Point(p.getLatitudeE6(), p.getLongitudeE6());
                boolean add = this.mConvertedPoints.add(obj);
                i++;
            }
            this.mPrecomputed = false;
        }

        /* access modifiers changed from: protected */
        public void buildPathPortion(Projection projection) {
            Projection pj = projection;
            int size = this.mConvertedPoints.size();
            if (size >= 2) {
                if (!this.mPrecomputed) {
                    for (int i = 0; i < size; i++) {
                        Point pt = this.mConvertedPoints.get(i);
                        Point projectedPixels = pj.toProjectedPixels(pt.x, pt.y, pt);
                    }
                    this.mPrecomputed = true;
                }
                Point screenPoint0 = pj.toPixelsFromProjected(this.mConvertedPoints.get(0), this.this$0.mTempPoint1);
                this.this$0.mPath.moveTo((float) screenPoint0.x, (float) screenPoint0.y);
                for (int i2 = 0; i2 < size; i2++) {
                    Point projectedPoint1 = this.mConvertedPoints.get(i2);
                    Point screenPoint1 = pj.toPixelsFromProjected(projectedPoint1, this.this$0.mTempPoint2);
                    if (Math.abs(screenPoint1.x - screenPoint0.x) + Math.abs(screenPoint1.y - screenPoint0.y) > 1) {
                        this.this$0.mPath.lineTo((float) screenPoint1.x, (float) screenPoint1.y);
                        Point projectedPoint0 = projectedPoint1;
                        screenPoint0.x = screenPoint1.x;
                        screenPoint0.y = screenPoint1.y;
                    }
                }
                this.this$0.mPath.close();
            }
        }
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    @Deprecated
    public Polygon(Context context) {
        this();
        Context context2 = context;
    }

    public Polygon() {
        Path path;
        Point point;
        Point point2;
        Matrix matrix;
        Paint paint;
        Paint paint2;
        LinearRing linearRing;
        ArrayList<LinearRing> arrayList;
        new Path();
        this.mPath = path;
        new Point();
        this.mTempPoint1 = point;
        new Point();
        this.mTempPoint2 = point2;
        this.mDragStartPoint = null;
        new Matrix();
        this.mDragMatrix = matrix;
        new Paint();
        this.mFillPaint = paint;
        this.mFillPaint.setColor(0);
        this.mFillPaint.setStyle(Paint.Style.FILL);
        new Paint();
        this.mOutlinePaint = paint2;
        this.mOutlinePaint.setColor(-16777216);
        this.mOutlinePaint.setStrokeWidth(10.0f);
        this.mOutlinePaint.setStyle(Paint.Style.STROKE);
        this.mOutlinePaint.setAntiAlias(true);
        new LinearRing(this);
        this.mOutline = linearRing;
        new ArrayList<>(0);
        this.mHoles = arrayList;
        this.mPath.setFillType(Path.FillType.EVEN_ODD);
    }

    public int getFillColor() {
        return this.mFillPaint.getColor();
    }

    public int getStrokeColor() {
        return this.mOutlinePaint.getColor();
    }

    public float getStrokeWidth() {
        return this.mOutlinePaint.getStrokeWidth();
    }

    public Paint getOutlinePaint() {
        return this.mOutlinePaint;
    }

    public List<GeoPoint> getPoints() {
        return this.mOutline.getPoints();
    }

    public void setDraggable(boolean draggable) {
        boolean z = draggable;
        this.mDraggable = z;
    }

    public boolean isDraggable() {
        return this.mDraggable;
    }

    public boolean isVisible() {
        return isEnabled();
    }

    public void setFillColor(int fillColor) {
        this.mFillPaint.setColor(fillColor);
    }

    public void setStrokeColor(int color) {
        this.mOutlinePaint.setColor(color);
    }

    public void setStrokeWidth(float width) {
        this.mOutlinePaint.setStrokeWidth(width);
    }

    public void setVisible(boolean visible) {
        setEnabled(visible);
    }

    public void setPoints(List<GeoPoint> points) {
        this.mOutline.setPoints(points);
    }

    public void setHoles(List<? extends List<GeoPoint>> list) {
        ArrayList<LinearRing> arrayList;
        LinearRing linearRing;
        List<? extends List<GeoPoint>> holes = list;
        new ArrayList<>(holes.size());
        this.mHoles = arrayList;
        for (List list2 : holes) {
            new LinearRing(this);
            LinearRing newHole = linearRing;
            newHole.setPoints(list2);
            boolean add = this.mHoles.add(newHole);
        }
    }

    public List<ArrayList<GeoPoint>> getHoles() {
        ArrayList arrayList;
        new ArrayList(this.mHoles.size());
        ArrayList arrayList2 = arrayList;
        Iterator<LinearRing> it = this.mHoles.iterator();
        while (it.hasNext()) {
            boolean add = arrayList2.add(it.next().getPoints());
        }
        return arrayList2;
    }

    public void setOnClickListener(OnClickListener listener) {
        OnClickListener onClickListener = listener;
        this.mOnClickListener = onClickListener;
    }

    public void setOnDragListener(OnDragListener listener) {
        OnDragListener onDragListener = listener;
        this.mOnDragListener = onDragListener;
    }

    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.util.ArrayList<org.osmdroid.util.GeoPoint> pointsAsCircle(org.osmdroid.util.GeoPoint r13, double r14) {
        /*
            r1 = r13
            r2 = r14
            java.util.ArrayList r7 = new java.util.ArrayList
            r11 = r7
            r7 = r11
            r8 = r11
            r9 = 60
            r8.<init>(r9)
            r4 = r7
            r7 = 0
            r5 = r7
        L_0x000f:
            r7 = r5
            r8 = 360(0x168, float:5.04E-43)
            if (r7 >= r8) goto L_0x0026
            r7 = r1
            r8 = r2
            r10 = r5
            float r10 = (float) r10
            org.osmdroid.util.GeoPoint r7 = r7.destinationPoint(r8, r10)
            r6 = r7
            r7 = r4
            r8 = r6
            boolean r7 = r7.add(r8)
            int r5 = r5 + 6
            goto L_0x000f
        L_0x0026:
            r7 = r4
            r1 = r7
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: org.osmdroid.views.overlay.Polygon.pointsAsCircle(org.osmdroid.util.GeoPoint, double):java.util.ArrayList");
    }

    @Deprecated
    public static ArrayList<IGeoPoint> pointsAsRect(BoundingBoxE6 boundingBoxE6) {
        ArrayList arrayList;
        Object obj;
        Object obj2;
        Object obj3;
        Object obj4;
        BoundingBoxE6 rectangle = boundingBoxE6;
        new ArrayList(4);
        ArrayList arrayList2 = arrayList;
        new GeoPoint(rectangle.getLatNorthE6(), rectangle.getLonWestE6());
        boolean add = arrayList2.add(obj);
        new GeoPoint(rectangle.getLatNorthE6(), rectangle.getLonEastE6());
        boolean add2 = arrayList2.add(obj2);
        new GeoPoint(rectangle.getLatSouthE6(), rectangle.getLonEastE6());
        boolean add3 = arrayList2.add(obj3);
        new GeoPoint(rectangle.getLatSouthE6(), rectangle.getLonWestE6());
        boolean add4 = arrayList2.add(obj4);
        return arrayList2;
    }

    public static ArrayList<IGeoPoint> pointsAsRect(BoundingBox boundingBox) {
        ArrayList arrayList;
        Object obj;
        Object obj2;
        Object obj3;
        Object obj4;
        BoundingBox rectangle = boundingBox;
        new ArrayList(4);
        ArrayList arrayList2 = arrayList;
        new GeoPoint(rectangle.getLatNorth(), rectangle.getLonWest());
        boolean add = arrayList2.add(obj);
        new GeoPoint(rectangle.getLatNorth(), rectangle.getLonEast());
        boolean add2 = arrayList2.add(obj2);
        new GeoPoint(rectangle.getLatSouth(), rectangle.getLonEast());
        boolean add3 = arrayList2.add(obj3);
        new GeoPoint(rectangle.getLatSouth(), rectangle.getLonWest());
        boolean add4 = arrayList2.add(obj4);
        return arrayList2;
    }

    public static List<GeoPoint> pointsAsRect(GeoPoint geoPoint, double lengthInMeters, double widthInMeters) {
        ArrayList arrayList;
        Object obj;
        Object obj2;
        Object obj3;
        Object obj4;
        GeoPoint center = geoPoint;
        new ArrayList(4);
        ArrayList arrayList2 = arrayList;
        GeoPoint east = center.destinationPoint(lengthInMeters * 0.5d, 90.0f);
        GeoPoint south = center.destinationPoint(widthInMeters * 0.5d, 180.0f);
        double westLon = (center.getLongitude() * 2.0d) - east.getLongitude();
        double northLat = (center.getLatitude() * 2.0d) - south.getLatitude();
        new GeoPoint(south.getLatitude(), east.getLongitude());
        boolean add = arrayList2.add(obj);
        new GeoPoint(south.getLatitude(), westLon);
        boolean add2 = arrayList2.add(obj2);
        new GeoPoint(northLat, westLon);
        boolean add3 = arrayList2.add(obj3);
        new GeoPoint(northLat, east.getLongitude());
        boolean add4 = arrayList2.add(obj4);
        return arrayList2;
    }

    public void draw(Canvas canvas, MapView mapView, boolean shadow) {
        Canvas canvas2 = canvas;
        MapView mapView2 = mapView;
        if (!shadow) {
            Projection pj = mapView2.getProjection();
            this.mPath.rewind();
            this.mOutline.buildPathPortion(pj);
            Iterator<LinearRing> it = this.mHoles.iterator();
            while (it.hasNext()) {
                it.next().buildPathPortion(pj);
            }
            this.mPath.transform(this.mDragMatrix);
            canvas2.drawPath(this.mPath, this.mFillPaint);
            canvas2.drawPath(this.mPath, this.mOutlinePaint);
        }
    }

    public boolean contains(MotionEvent motionEvent) {
        RectF rectF;
        Region region;
        Region region2;
        MotionEvent event = motionEvent;
        if (this.mPath.isEmpty()) {
            return false;
        }
        new RectF();
        RectF bounds = rectF;
        this.mPath.computeBounds(bounds, true);
        new Region();
        Region region3 = region;
        new Region((int) bounds.left, (int) bounds.top, (int) bounds.right, (int) bounds.bottom);
        boolean path = region3.setPath(this.mPath, region2);
        return region3.contains((int) event.getX(), (int) event.getY());
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
        boolean tapped = contains(event);
        if (!tapped) {
            return tapped;
        }
        GeoPoint position = (GeoPoint) mapView2.getProjection().fromPixels((int) event.getX(), (int) event.getY());
        if (this.mOnClickListener == null) {
            return onClickDefault(this, mapView2, position);
        }
        return this.mOnClickListener.onClick(this, mapView2, position);
    }

    public void moveToEventPosition(MotionEvent motionEvent, MotionEvent motionEvent2, MapView mapView) {
        MotionEvent event = motionEvent;
        MotionEvent start = motionEvent2;
        float dX = event.getX() - start.getX();
        float dY = event.getY() - start.getY();
        this.mDragMatrix.setTranslate(dX, dY);
        mapView.invalidate();
    }

    public void finishMove(MotionEvent motionEvent, MotionEvent motionEvent2, MapView mapView) {
        MotionEvent start = motionEvent;
        MotionEvent end = motionEvent2;
        MapView mapView2 = mapView;
        Projection proj = mapView2.getProjection();
        float dX = end.getX() - start.getX();
        float dY = end.getY() - start.getY();
        shiftRing(this.mOutline, dX, dY, proj);
        Iterator<LinearRing> it = this.mHoles.iterator();
        while (it.hasNext()) {
            shiftRing(it.next(), dX, dY, proj);
        }
        this.mDragMatrix.setTranslate(0.0f, 0.0f);
        mapView2.invalidate();
    }

    private void shiftRing(LinearRing linearRing, float f, float f2, Projection projection) {
        GeoPoint geoPoint;
        LinearRing ring = linearRing;
        float dx = f;
        float dy = f2;
        Projection proj = projection;
        new GeoPoint(0.0d, 0.0d);
        GeoPoint gp = geoPoint;
        for (int i = 0; i < ring.mOriginalPoints.length; i++) {
            Point pt = ring.mConvertedPoints.get(i);
            Point projectedPixels = proj.toProjectedPixels(ring.mOriginalPoints[i][0], ring.mOriginalPoints[i][1], this.mTempPoint1);
            Point pixelsFromProjected = proj.toPixelsFromProjected(this.mTempPoint1, this.mTempPoint2);
            this.mTempPoint2.offset((int) dx, (int) dy);
            IGeoPoint fromPixels = proj.fromPixels(this.mTempPoint2.x, this.mTempPoint2.y, gp);
            ring.mOriginalPoints[i][0] = gp.getLatitudeE6();
            ring.mOriginalPoints[i][1] = gp.getLongitudeE6();
            Point projectedPixels2 = proj.toProjectedPixels(gp.getLatitudeE6(), gp.getLongitudeE6(), pt);
        }
    }

    public boolean onLongPress(MotionEvent motionEvent, MapView mapView) {
        MotionEvent event = motionEvent;
        MapView mapView2 = mapView;
        boolean touched = contains(event);
        if (touched) {
            if (this.mDraggable) {
                this.mIsDragged = true;
                closeInfoWindow();
                this.mDragStartPoint = event;
                if (this.mOnDragListener != null) {
                    this.mOnDragListener.onDragStart(this);
                }
                moveToEventPosition(event, this.mDragStartPoint, mapView2);
            } else if (this.mOnClickListener != null) {
                boolean onLongClick = this.mOnClickListener.onLongClick(this, mapView2, (GeoPoint) mapView2.getProjection().fromPixels((int) event.getX(), (int) event.getY()));
            }
        }
        return touched;
    }

    public boolean onTouchEvent(MotionEvent motionEvent, MapView mapView) {
        MotionEvent event = motionEvent;
        MapView mapView2 = mapView;
        if (this.mDraggable && this.mIsDragged) {
            if (event.getAction() == 1) {
                this.mIsDragged = false;
                finishMove(this.mDragStartPoint, event, mapView2);
                if (this.mOnDragListener != null) {
                    this.mOnDragListener.onDragEnd(this);
                }
                return true;
            } else if (event.getAction() == 2) {
                moveToEventPosition(event, this.mDragStartPoint, mapView2);
                if (this.mOnDragListener != null) {
                    this.mOnDragListener.onDrag(this);
                }
                return true;
            }
        }
        return false;
    }

    /* access modifiers changed from: protected */
    public boolean onClickDefault(Polygon polygon, MapView mapView, GeoPoint eventPos) {
        MapView mapView2 = mapView;
        polygon.showInfoWindow(eventPos);
        return true;
    }

    public void onDetach(MapView mapView) {
        MapView mapView2 = mapView;
        this.mOutline = null;
        this.mHoles.clear();
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
