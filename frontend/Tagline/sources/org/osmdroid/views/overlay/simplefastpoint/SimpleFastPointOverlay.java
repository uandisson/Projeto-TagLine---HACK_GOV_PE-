package org.osmdroid.views.overlay.simplefastpoint;

import android.graphics.Point;
import android.view.MotionEvent;
import java.lang.reflect.Array;
import java.util.Arrays;
import org.osmdroid.api.IGeoPoint;
import org.osmdroid.util.BoundingBox;
import org.osmdroid.views.MapView;
import org.osmdroid.views.Projection;
import org.osmdroid.views.overlay.Overlay;
import org.osmdroid.views.overlay.simplefastpoint.SimpleFastPointOverlayOptions;

public class SimpleFastPointOverlay extends Overlay {
    private OnClickListener clickListener;
    private float curX;
    private float curY;
    private LabelledPoint[][] grid;
    private boolean[][] gridBool;
    private int gridHei;
    private int gridWid;
    private final BoundingBox mBoundingBox;
    private final PointAdapter mPointList;
    private Integer mSelectedPoint;
    private final SimpleFastPointOverlayOptions mStyle;
    private int numLabels;
    private float offsetX;
    private float offsetY;
    private BoundingBox prevBoundingBox;
    private int prevNumPointers;
    private float startX;
    private float startY;
    private int viewHei;
    private int viewWid;

    public interface OnClickListener {
        void onClick(PointAdapter pointAdapter, Integer num);
    }

    public interface PointAdapter extends Iterable<IGeoPoint> {
        IGeoPoint get(int i);

        boolean isLabelled();

        int size();
    }

    public class LabelledPoint extends Point {
        /* access modifiers changed from: private */
        public String mlabel;
        final /* synthetic */ SimpleFastPointOverlay this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        public LabelledPoint(SimpleFastPointOverlay this$02, Point point, String label) {
            super(point);
            this.this$0 = this$02;
            this.mlabel = label;
        }
    }

    public SimpleFastPointOverlay(PointAdapter pointList, SimpleFastPointOverlayOptions style) {
        BoundingBox boundingBox;
        BoundingBox boundingBox2;
        new BoundingBox(0.0d, 0.0d, 0.0d, 0.0d);
        this.prevBoundingBox = boundingBox;
        this.mStyle = style;
        this.mPointList = pointList;
        Double east = null;
        Double west = null;
        Double north = null;
        Double south = null;
        for (IGeoPoint p : this.mPointList) {
            if (p != null) {
                east = (east == null || p.getLongitude() > east.doubleValue()) ? Double.valueOf(p.getLongitude()) : east;
                west = (west == null || p.getLongitude() < west.doubleValue()) ? Double.valueOf(p.getLongitude()) : west;
                north = (north == null || p.getLatitude() > north.doubleValue()) ? Double.valueOf(p.getLatitude()) : north;
                if (south == null || p.getLatitude() < south.doubleValue()) {
                    south = Double.valueOf(p.getLatitude());
                }
            }
        }
        if (east != null) {
            new BoundingBox(north.doubleValue(), east.doubleValue(), south.doubleValue(), west.doubleValue());
            this.mBoundingBox = boundingBox2;
            return;
        }
        this.mBoundingBox = null;
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public SimpleFastPointOverlay(PointAdapter pointList) {
        this(pointList, SimpleFastPointOverlayOptions.getDefaultStyle());
    }

    private void updateGrid(MapView mapView) {
        MapView mapView2 = mapView;
        this.viewWid = mapView2.getWidth();
        this.viewHei = mapView2.getHeight();
        this.gridWid = ((int) Math.floor((double) (((float) this.viewWid) / ((float) this.mStyle.mCellSize)))) + 1;
        this.gridHei = ((int) Math.floor((double) (((float) this.viewHei) / ((float) this.mStyle.mCellSize)))) + 1;
        if (this.mStyle.mAlgorithm == SimpleFastPointOverlayOptions.RenderingAlgorithm.MAXIMUM_OPTIMIZATION) {
            this.grid = (LabelledPoint[][]) Array.newInstance(LabelledPoint.class, new int[]{this.gridWid, this.gridHei});
            return;
        }
        this.gridBool = (boolean[][]) Array.newInstance(Boolean.TYPE, new int[]{this.gridWid, this.gridHei});
    }

    private void computeGrid(MapView mapView) {
        BoundingBox boundingBox;
        Point point;
        MapView pMapView = mapView;
        BoundingBox viewBBox = pMapView.getBoundingBox();
        if (viewBBox.getLatNorth() != this.prevBoundingBox.getLatNorth() || viewBBox.getLatSouth() != this.prevBoundingBox.getLatSouth() || viewBBox.getLonWest() != this.prevBoundingBox.getLonWest() || viewBBox.getLonEast() != this.prevBoundingBox.getLonEast()) {
            new BoundingBox(viewBBox.getLatNorth(), viewBBox.getLonEast(), viewBBox.getLatSouth(), viewBBox.getLonWest());
            this.prevBoundingBox = boundingBox;
            if (this.grid != null && this.viewHei == pMapView.getHeight() && this.viewWid == pMapView.getWidth()) {
                Point[][] pointArr = this.grid;
                int length = pointArr.length;
                for (int i = 0; i < length; i++) {
                    Arrays.fill(pointArr[i], (Object) null);
                }
            } else {
                updateGrid(pMapView);
            }
            new Point();
            Point mPositionPixels = point;
            Projection pj = pMapView.getProjection();
            this.numLabels = 0;
            for (IGeoPoint pt1 : this.mPointList) {
                if (pt1 != null && pt1.getLatitude() > viewBBox.getLatSouth() && pt1.getLatitude() < viewBBox.getLatNorth() && pt1.getLongitude() > viewBBox.getLonWest() && pt1.getLongitude() < viewBBox.getLonEast()) {
                    Point pixels = pj.toPixels(pt1, mPositionPixels);
                    int gridX = (int) Math.floor((double) (((float) mPositionPixels.x) / ((float) this.mStyle.mCellSize)));
                    int gridY = (int) Math.floor((double) (((float) mPositionPixels.y) / ((float) this.mStyle.mCellSize)));
                    if (gridX < this.gridWid && gridY < this.gridHei && gridX >= 0 && gridY >= 0 && this.grid[gridX][gridY] == null) {
                        LabelledPoint[] labelledPointArr = this.grid[gridX];
                        int i2 = gridY;
                        LabelledPoint labelledPoint = r21;
                        LabelledPoint labelledPoint2 = new LabelledPoint(this, mPositionPixels, this.mPointList.isLabelled() ? ((LabelledGeoPoint) pt1).getLabel() : null);
                        labelledPointArr[i2] = labelledPoint;
                        this.numLabels++;
                    }
                }
            }
        }
    }

    public boolean onTouchEvent(MotionEvent motionEvent, MapView mapView) {
        MotionEvent event = motionEvent;
        MapView mapView2 = mapView;
        if (this.mStyle.mAlgorithm != SimpleFastPointOverlayOptions.RenderingAlgorithm.MAXIMUM_OPTIMIZATION) {
            return false;
        }
        switch (event.getAction()) {
            case 0:
                this.prevNumPointers = event.getPointerCount();
                this.startX = event.getX(0);
                this.startY = event.getY(0);
                for (int i = 1; i < this.prevNumPointers; i++) {
                    this.startX += event.getX(i);
                    this.startY += event.getY(i);
                }
                this.startX /= (float) this.prevNumPointers;
                this.startY /= (float) this.prevNumPointers;
                break;
            case 1:
                this.startX = 0.0f;
                this.startY = 0.0f;
                this.curX = 0.0f;
                this.curY = 0.0f;
                this.offsetX = 0.0f;
                this.offsetY = 0.0f;
                mapView2.invalidate();
                break;
            case 2:
                this.curX = event.getX(0);
                this.curY = event.getY(0);
                for (int i2 = 1; i2 < event.getPointerCount(); i2++) {
                    this.curX += event.getX(i2);
                    this.curY += event.getY(i2);
                }
                this.curX /= (float) event.getPointerCount();
                this.curY /= (float) event.getPointerCount();
                if (event.getPointerCount() != this.prevNumPointers) {
                    computeGrid(mapView2);
                    this.prevNumPointers = event.getPointerCount();
                    this.offsetX = this.curX - this.startX;
                    this.offsetY = this.curY - this.startY;
                    break;
                }
                break;
        }
        return false;
    }

    public boolean onSingleTapConfirmed(MotionEvent motionEvent, MapView mapView) {
        Point point;
        MotionEvent event = motionEvent;
        MapView mapView2 = mapView;
        if (!this.mStyle.mClickable) {
            return false;
        }
        Float minHyp = null;
        int closest = -1;
        new Point();
        Point tmp = point;
        Projection pj = mapView2.getProjection();
        for (int i = 0; i < this.mPointList.size(); i++) {
            if (this.mPointList.get(i) != null) {
                Point pixels = pj.toPixels(this.mPointList.get(i), tmp);
                if (Math.abs(event.getX() - ((float) tmp.x)) <= 50.0f && Math.abs(event.getY() - ((float) tmp.y)) <= 50.0f) {
                    float hyp = ((event.getX() - ((float) tmp.x)) * (event.getX() - ((float) tmp.x))) + ((event.getY() - ((float) tmp.y)) * (event.getY() - ((float) tmp.y)));
                    if (minHyp == null || hyp < minHyp.floatValue()) {
                        minHyp = Float.valueOf(hyp);
                        closest = i;
                    }
                }
            }
        }
        if (minHyp == null) {
            return false;
        }
        setSelectedPoint(Integer.valueOf(closest));
        mapView2.invalidate();
        if (this.clickListener != null) {
            this.clickListener.onClick(this.mPointList, Integer.valueOf(closest));
        }
        return true;
    }

    public void setSelectedPoint(Integer num) {
        Integer toSelect = num;
        if (toSelect == null || toSelect.intValue() < 0 || toSelect.intValue() >= this.mPointList.size()) {
            this.mSelectedPoint = null;
            return;
        }
        this.mSelectedPoint = toSelect;
    }

    public Integer getSelectedPoint() {
        return this.mSelectedPoint;
    }

    public BoundingBox getBoundingBox() {
        return this.mBoundingBox;
    }

    public void setOnClickListener(OnClickListener listener) {
        OnClickListener onClickListener = listener;
        this.clickListener = onClickListener;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:24:0x013c, code lost:
        if (r5.isAnimating() == false) goto L_0x013e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x0175, code lost:
        if (r3.numLabels > r3.mStyle.mMaxNShownLabels) goto L_0x0177;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:33:0x01a8, code lost:
        if (r5.getZoomLevelDouble() >= ((double) r3.mStyle.mMinZoomShowLabels)) goto L_0x01aa;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:34:0x01aa, code lost:
        r18 = true;
     */
    /* JADX WARNING: Removed duplicated region for block: B:116:0x07b4  */
    /* JADX WARNING: Removed duplicated region for block: B:139:0x004b A[SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:28:0x015b  */
    /* JADX WARNING: Removed duplicated region for block: B:32:0x018d  */
    /* JADX WARNING: Removed duplicated region for block: B:38:0x01ea  */
    /* JADX WARNING: Removed duplicated region for block: B:66:0x049a  */
    /* JADX WARNING: Removed duplicated region for block: B:72:0x04d9  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void draw(android.graphics.Canvas r26, org.osmdroid.views.MapView r27, boolean r28) {
        /*
            r25 = this;
            r3 = r25
            r4 = r26
            r5 = r27
            r6 = r28
            r18 = r6
            if (r18 == 0) goto L_0x000d
        L_0x000c:
            return
        L_0x000d:
            android.graphics.Point r18 = new android.graphics.Point
            r24 = r18
            r18 = r24
            r19 = r24
            r19.<init>()
            r8 = r18
            r18 = r5
            org.osmdroid.views.Projection r18 = r18.getProjection()
            r9 = r18
            r18 = r3
            r0 = r18
            org.osmdroid.views.overlay.simplefastpoint.SimpleFastPointOverlayOptions r0 = r0.mStyle
            r18 = r0
            r0 = r18
            android.graphics.Paint r0 = r0.mPointStyle
            r18 = r0
            if (r18 == 0) goto L_0x004b
            int[] r18 = org.osmdroid.views.overlay.simplefastpoint.SimpleFastPointOverlay.C16121.f533x97395854
            r19 = r3
            r0 = r19
            org.osmdroid.views.overlay.simplefastpoint.SimpleFastPointOverlayOptions r0 = r0.mStyle
            r19 = r0
            r0 = r19
            org.osmdroid.views.overlay.simplefastpoint.SimpleFastPointOverlayOptions$RenderingAlgorithm r0 = r0.mAlgorithm
            r19 = r0
            int r19 = r19.ordinal()
            r18 = r18[r19]
            switch(r18) {
                case 1: goto L_0x0110;
                case 2: goto L_0x044b;
                case 3: goto L_0x075f;
                default: goto L_0x004b;
            }
        L_0x004b:
            r18 = r3
            r0 = r18
            java.lang.Integer r0 = r0.mSelectedPoint
            r18 = r0
            if (r18 == 0) goto L_0x010e
            r18 = r3
            r0 = r18
            java.lang.Integer r0 = r0.mSelectedPoint
            r18 = r0
            int r18 = r18.intValue()
            r19 = r3
            r0 = r19
            org.osmdroid.views.overlay.simplefastpoint.SimpleFastPointOverlay$PointAdapter r0 = r0.mPointList
            r19 = r0
            int r19 = r19.size()
            r0 = r18
            r1 = r19
            if (r0 >= r1) goto L_0x010e
            r18 = r3
            r0 = r18
            org.osmdroid.views.overlay.simplefastpoint.SimpleFastPointOverlay$PointAdapter r0 = r0.mPointList
            r18 = r0
            r19 = r3
            r0 = r19
            java.lang.Integer r0 = r0.mSelectedPoint
            r19 = r0
            int r19 = r19.intValue()
            org.osmdroid.api.IGeoPoint r18 = r18.get(r19)
            if (r18 == 0) goto L_0x010e
            r18 = r3
            r0 = r18
            org.osmdroid.views.overlay.simplefastpoint.SimpleFastPointOverlayOptions r0 = r0.mStyle
            r18 = r0
            r0 = r18
            android.graphics.Paint r0 = r0.mSelectedPointStyle
            r18 = r0
            if (r18 == 0) goto L_0x010e
            r18 = r9
            r19 = r3
            r0 = r19
            org.osmdroid.views.overlay.simplefastpoint.SimpleFastPointOverlay$PointAdapter r0 = r0.mPointList
            r19 = r0
            r20 = r3
            r0 = r20
            java.lang.Integer r0 = r0.mSelectedPoint
            r20 = r0
            int r20 = r20.intValue()
            org.osmdroid.api.IGeoPoint r19 = r19.get(r20)
            r20 = r8
            android.graphics.Point r18 = r18.toPixels(r19, r20)
            r18 = r3
            r0 = r18
            org.osmdroid.views.overlay.simplefastpoint.SimpleFastPointOverlayOptions r0 = r0.mStyle
            r18 = r0
            r0 = r18
            org.osmdroid.views.overlay.simplefastpoint.SimpleFastPointOverlayOptions$Shape r0 = r0.mSymbol
            r18 = r0
            org.osmdroid.views.overlay.simplefastpoint.SimpleFastPointOverlayOptions$Shape r19 = org.osmdroid.views.overlay.simplefastpoint.SimpleFastPointOverlayOptions.Shape.CIRCLE
            r0 = r18
            r1 = r19
            if (r0 != r1) goto L_0x0953
            r18 = r4
            r19 = r8
            r0 = r19
            int r0 = r0.x
            r19 = r0
            r0 = r19
            float r0 = (float) r0
            r19 = r0
            r20 = r8
            r0 = r20
            int r0 = r0.y
            r20 = r0
            r0 = r20
            float r0 = (float) r0
            r20 = r0
            r21 = r3
            r0 = r21
            org.osmdroid.views.overlay.simplefastpoint.SimpleFastPointOverlayOptions r0 = r0.mStyle
            r21 = r0
            r0 = r21
            float r0 = r0.mSelectedCircleRadius
            r21 = r0
            r22 = r3
            r0 = r22
            org.osmdroid.views.overlay.simplefastpoint.SimpleFastPointOverlayOptions r0 = r0.mStyle
            r22 = r0
            r0 = r22
            android.graphics.Paint r0 = r0.mSelectedPointStyle
            r22 = r0
            r18.drawCircle(r19, r20, r21, r22)
        L_0x010e:
            goto L_0x000c
        L_0x0110:
            r18 = r3
            r0 = r18
            org.osmdroid.views.overlay.simplefastpoint.SimpleFastPointOverlay$LabelledPoint[][] r0 = r0.grid
            r18 = r0
            if (r18 == 0) goto L_0x013e
            r18 = r3
            r0 = r18
            float r0 = r0.curX
            r18 = r0
            r19 = 0
            int r18 = (r18 > r19 ? 1 : (r18 == r19 ? 0 : -1))
            if (r18 != 0) goto L_0x0145
            r18 = r3
            r0 = r18
            float r0 = r0.curY
            r18 = r0
            r19 = 0
            int r18 = (r18 > r19 ? 1 : (r18 == r19 ? 0 : -1))
            if (r18 != 0) goto L_0x0145
            r18 = r5
            boolean r18 = r18.isAnimating()
            if (r18 != 0) goto L_0x0145
        L_0x013e:
            r18 = r3
            r19 = r5
            r18.computeGrid(r19)
        L_0x0145:
            r18 = r3
            r0 = r18
            org.osmdroid.views.overlay.simplefastpoint.SimpleFastPointOverlayOptions r0 = r0.mStyle
            r18 = r0
            r0 = r18
            org.osmdroid.views.overlay.simplefastpoint.SimpleFastPointOverlayOptions$LabelPolicy r0 = r0.mLabelPolicy
            r18 = r0
            org.osmdroid.views.overlay.simplefastpoint.SimpleFastPointOverlayOptions$LabelPolicy r19 = org.osmdroid.views.overlay.simplefastpoint.SimpleFastPointOverlayOptions.LabelPolicy.DENSITY_THRESHOLD
            r0 = r18
            r1 = r19
            if (r0 != r1) goto L_0x0177
            r18 = r3
            r0 = r18
            int r0 = r0.numLabels
            r18 = r0
            r19 = r3
            r0 = r19
            org.osmdroid.views.overlay.simplefastpoint.SimpleFastPointOverlayOptions r0 = r0.mStyle
            r19 = r0
            r0 = r19
            int r0 = r0.mMaxNShownLabels
            r19 = r0
            r0 = r18
            r1 = r19
            if (r0 <= r1) goto L_0x01aa
        L_0x0177:
            r18 = r3
            r0 = r18
            org.osmdroid.views.overlay.simplefastpoint.SimpleFastPointOverlayOptions r0 = r0.mStyle
            r18 = r0
            r0 = r18
            org.osmdroid.views.overlay.simplefastpoint.SimpleFastPointOverlayOptions$LabelPolicy r0 = r0.mLabelPolicy
            r18 = r0
            org.osmdroid.views.overlay.simplefastpoint.SimpleFastPointOverlayOptions$LabelPolicy r19 = org.osmdroid.views.overlay.simplefastpoint.SimpleFastPointOverlayOptions.LabelPolicy.ZOOM_THRESHOLD
            r0 = r18
            r1 = r19
            if (r0 != r1) goto L_0x0348
            r18 = r5
            double r18 = r18.getZoomLevelDouble()
            r20 = r3
            r0 = r20
            org.osmdroid.views.overlay.simplefastpoint.SimpleFastPointOverlayOptions r0 = r0.mStyle
            r20 = r0
            r0 = r20
            int r0 = r0.mMinZoomShowLabels
            r20 = r0
            r0 = r20
            double r0 = (double) r0
            r20 = r0
            int r18 = (r18 > r20 ? 1 : (r18 == r20 ? 0 : -1))
            if (r18 < 0) goto L_0x0348
        L_0x01aa:
            r18 = 1
        L_0x01ac:
            r11 = r18
            r18 = r3
            r0 = r18
            float r0 = r0.curX
            r18 = r0
            r19 = r3
            r0 = r19
            float r0 = r0.startX
            r19 = r0
            float r18 = r18 - r19
            r12 = r18
            r18 = r3
            r0 = r18
            float r0 = r0.curY
            r18 = r0
            r19 = r3
            r0 = r19
            float r0 = r0.startY
            r19 = r0
            float r18 = r18 - r19
            r13 = r18
            r18 = 0
            r14 = r18
        L_0x01da:
            r18 = r14
            r19 = r3
            r0 = r19
            int r0 = r0.gridWid
            r19 = r0
            r0 = r18
            r1 = r19
            if (r0 >= r1) goto L_0x0449
            r18 = 0
            r15 = r18
        L_0x01ee:
            r18 = r15
            r19 = r3
            r0 = r19
            int r0 = r0.gridHei
            r19 = r0
            r0 = r18
            r1 = r19
            if (r0 >= r1) goto L_0x0445
            r18 = r3
            r0 = r18
            org.osmdroid.views.overlay.simplefastpoint.SimpleFastPointOverlay$LabelledPoint[][] r0 = r0.grid
            r18 = r0
            r19 = r14
            r18 = r18[r19]
            r19 = r15
            r18 = r18[r19]
            if (r18 == 0) goto L_0x0344
            r18 = r3
            r0 = r18
            org.osmdroid.views.overlay.simplefastpoint.SimpleFastPointOverlayOptions r0 = r0.mStyle
            r18 = r0
            r0 = r18
            org.osmdroid.views.overlay.simplefastpoint.SimpleFastPointOverlayOptions$Shape r0 = r0.mSymbol
            r18 = r0
            org.osmdroid.views.overlay.simplefastpoint.SimpleFastPointOverlayOptions$Shape r19 = org.osmdroid.views.overlay.simplefastpoint.SimpleFastPointOverlayOptions.Shape.CIRCLE
            r0 = r18
            r1 = r19
            if (r0 != r1) goto L_0x034c
            r18 = r4
            r19 = r3
            r0 = r19
            org.osmdroid.views.overlay.simplefastpoint.SimpleFastPointOverlay$LabelledPoint[][] r0 = r0.grid
            r19 = r0
            r20 = r14
            r19 = r19[r20]
            r20 = r15
            r19 = r19[r20]
            r0 = r19
            int r0 = r0.x
            r19 = r0
            r0 = r19
            float r0 = (float) r0
            r19 = r0
            r20 = r12
            float r19 = r19 + r20
            r20 = r3
            r0 = r20
            float r0 = r0.offsetX
            r20 = r0
            float r19 = r19 - r20
            r20 = r3
            r0 = r20
            org.osmdroid.views.overlay.simplefastpoint.SimpleFastPointOverlay$LabelledPoint[][] r0 = r0.grid
            r20 = r0
            r21 = r14
            r20 = r20[r21]
            r21 = r15
            r20 = r20[r21]
            r0 = r20
            int r0 = r0.y
            r20 = r0
            r0 = r20
            float r0 = (float) r0
            r20 = r0
            r21 = r13
            float r20 = r20 + r21
            r21 = r3
            r0 = r21
            float r0 = r0.offsetY
            r21 = r0
            float r20 = r20 - r21
            r21 = r3
            r0 = r21
            org.osmdroid.views.overlay.simplefastpoint.SimpleFastPointOverlayOptions r0 = r0.mStyle
            r21 = r0
            r0 = r21
            float r0 = r0.mCircleRadius
            r21 = r0
            r22 = r3
            r0 = r22
            org.osmdroid.views.overlay.simplefastpoint.SimpleFastPointOverlayOptions r0 = r0.mStyle
            r22 = r0
            r0 = r22
            android.graphics.Paint r0 = r0.mPointStyle
            r22 = r0
            r18.drawCircle(r19, r20, r21, r22)
        L_0x0299:
            r18 = r3
            r0 = r18
            org.osmdroid.views.overlay.simplefastpoint.SimpleFastPointOverlay$PointAdapter r0 = r0.mPointList
            r18 = r0
            boolean r18 = r18.isLabelled()
            if (r18 == 0) goto L_0x0344
            r18 = r11
            if (r18 == 0) goto L_0x0344
            r18 = r3
            r0 = r18
            org.osmdroid.views.overlay.simplefastpoint.SimpleFastPointOverlay$LabelledPoint[][] r0 = r0.grid
            r18 = r0
            r19 = r14
            r18 = r18[r19]
            r19 = r15
            r18 = r18[r19]
            java.lang.String r18 = r18.mlabel
            r24 = r18
            r18 = r24
            r19 = r24
            r10 = r19
            if (r18 == 0) goto L_0x0344
            r18 = r4
            r19 = r10
            r20 = r3
            r0 = r20
            org.osmdroid.views.overlay.simplefastpoint.SimpleFastPointOverlay$LabelledPoint[][] r0 = r0.grid
            r20 = r0
            r21 = r14
            r20 = r20[r21]
            r21 = r15
            r20 = r20[r21]
            r0 = r20
            int r0 = r0.x
            r20 = r0
            r0 = r20
            float r0 = (float) r0
            r20 = r0
            r21 = r12
            float r20 = r20 + r21
            r21 = r3
            r0 = r21
            float r0 = r0.offsetX
            r21 = r0
            float r20 = r20 - r21
            r21 = r3
            r0 = r21
            org.osmdroid.views.overlay.simplefastpoint.SimpleFastPointOverlay$LabelledPoint[][] r0 = r0.grid
            r21 = r0
            r22 = r14
            r21 = r21[r22]
            r22 = r15
            r21 = r21[r22]
            r0 = r21
            int r0 = r0.y
            r21 = r0
            r0 = r21
            float r0 = (float) r0
            r21 = r0
            r22 = r13
            float r21 = r21 + r22
            r22 = r3
            r0 = r22
            float r0 = r0.offsetY
            r22 = r0
            float r21 = r21 - r22
            r22 = r3
            r0 = r22
            org.osmdroid.views.overlay.simplefastpoint.SimpleFastPointOverlayOptions r0 = r0.mStyle
            r22 = r0
            r0 = r22
            float r0 = r0.mCircleRadius
            r22 = r0
            float r21 = r21 - r22
            r22 = 1084227584(0x40a00000, float:5.0)
            float r21 = r21 - r22
            r22 = r3
            r0 = r22
            org.osmdroid.views.overlay.simplefastpoint.SimpleFastPointOverlayOptions r0 = r0.mStyle
            r22 = r0
            r0 = r22
            android.graphics.Paint r0 = r0.mTextStyle
            r22 = r0
            r18.drawText(r19, r20, r21, r22)
        L_0x0344:
            int r15 = r15 + 1
            goto L_0x01ee
        L_0x0348:
            r18 = 0
            goto L_0x01ac
        L_0x034c:
            r18 = r4
            r19 = r3
            r0 = r19
            org.osmdroid.views.overlay.simplefastpoint.SimpleFastPointOverlay$LabelledPoint[][] r0 = r0.grid
            r19 = r0
            r20 = r14
            r19 = r19[r20]
            r20 = r15
            r19 = r19[r20]
            r0 = r19
            int r0 = r0.x
            r19 = r0
            r0 = r19
            float r0 = (float) r0
            r19 = r0
            r20 = r12
            float r19 = r19 + r20
            r20 = r3
            r0 = r20
            float r0 = r0.offsetX
            r20 = r0
            float r19 = r19 - r20
            r20 = r3
            r0 = r20
            org.osmdroid.views.overlay.simplefastpoint.SimpleFastPointOverlayOptions r0 = r0.mStyle
            r20 = r0
            r0 = r20
            float r0 = r0.mCircleRadius
            r20 = r0
            float r19 = r19 - r20
            r20 = r3
            r0 = r20
            org.osmdroid.views.overlay.simplefastpoint.SimpleFastPointOverlay$LabelledPoint[][] r0 = r0.grid
            r20 = r0
            r21 = r14
            r20 = r20[r21]
            r21 = r15
            r20 = r20[r21]
            r0 = r20
            int r0 = r0.y
            r20 = r0
            r0 = r20
            float r0 = (float) r0
            r20 = r0
            r21 = r13
            float r20 = r20 + r21
            r21 = r3
            r0 = r21
            float r0 = r0.offsetY
            r21 = r0
            float r20 = r20 - r21
            r21 = r3
            r0 = r21
            org.osmdroid.views.overlay.simplefastpoint.SimpleFastPointOverlayOptions r0 = r0.mStyle
            r21 = r0
            r0 = r21
            float r0 = r0.mCircleRadius
            r21 = r0
            float r20 = r20 - r21
            r21 = r3
            r0 = r21
            org.osmdroid.views.overlay.simplefastpoint.SimpleFastPointOverlay$LabelledPoint[][] r0 = r0.grid
            r21 = r0
            r22 = r14
            r21 = r21[r22]
            r22 = r15
            r21 = r21[r22]
            r0 = r21
            int r0 = r0.x
            r21 = r0
            r0 = r21
            float r0 = (float) r0
            r21 = r0
            r22 = r12
            float r21 = r21 + r22
            r22 = r3
            r0 = r22
            float r0 = r0.offsetX
            r22 = r0
            float r21 = r21 - r22
            r22 = r3
            r0 = r22
            org.osmdroid.views.overlay.simplefastpoint.SimpleFastPointOverlayOptions r0 = r0.mStyle
            r22 = r0
            r0 = r22
            float r0 = r0.mCircleRadius
            r22 = r0
            float r21 = r21 + r22
            r22 = r3
            r0 = r22
            org.osmdroid.views.overlay.simplefastpoint.SimpleFastPointOverlay$LabelledPoint[][] r0 = r0.grid
            r22 = r0
            r23 = r14
            r22 = r22[r23]
            r23 = r15
            r22 = r22[r23]
            r0 = r22
            int r0 = r0.y
            r22 = r0
            r0 = r22
            float r0 = (float) r0
            r22 = r0
            r23 = r13
            float r22 = r22 + r23
            r23 = r3
            r0 = r23
            float r0 = r0.offsetY
            r23 = r0
            float r22 = r22 - r23
            r23 = r3
            r0 = r23
            org.osmdroid.views.overlay.simplefastpoint.SimpleFastPointOverlayOptions r0 = r0.mStyle
            r23 = r0
            r0 = r23
            float r0 = r0.mCircleRadius
            r23 = r0
            float r22 = r22 + r23
            r23 = r3
            r0 = r23
            org.osmdroid.views.overlay.simplefastpoint.SimpleFastPointOverlayOptions r0 = r0.mStyle
            r23 = r0
            r0 = r23
            android.graphics.Paint r0 = r0.mPointStyle
            r23 = r0
            r18.drawRect(r19, r20, r21, r22, r23)
            goto L_0x0299
        L_0x0445:
            int r14 = r14 + 1
            goto L_0x01da
        L_0x0449:
            goto L_0x004b
        L_0x044b:
            r18 = r3
            r0 = r18
            org.osmdroid.views.overlay.simplefastpoint.SimpleFastPointOverlay$LabelledPoint[][] r0 = r0.grid
            r18 = r0
            if (r18 == 0) goto L_0x047d
            r18 = r3
            r0 = r18
            int r0 = r0.viewHei
            r18 = r0
            r19 = r5
            int r19 = r19.getHeight()
            r0 = r18
            r1 = r19
            if (r0 != r1) goto L_0x047d
            r18 = r3
            r0 = r18
            int r0 = r0.viewWid
            r18 = r0
            r19 = r5
            int r19 = r19.getWidth()
            r0 = r18
            r1 = r19
            if (r0 == r1) goto L_0x04e8
        L_0x047d:
            r18 = r3
            r19 = r5
            r18.updateGrid(r19)
        L_0x0484:
            r18 = r3
            r0 = r18
            org.osmdroid.views.overlay.simplefastpoint.SimpleFastPointOverlayOptions r0 = r0.mStyle
            r18 = r0
            r0 = r18
            org.osmdroid.views.overlay.simplefastpoint.SimpleFastPointOverlayOptions$LabelPolicy r0 = r0.mLabelPolicy
            r18 = r0
            org.osmdroid.views.overlay.simplefastpoint.SimpleFastPointOverlayOptions$LabelPolicy r19 = org.osmdroid.views.overlay.simplefastpoint.SimpleFastPointOverlayOptions.LabelPolicy.ZOOM_THRESHOLD
            r0 = r18
            r1 = r19
            if (r0 != r1) goto L_0x051b
            r18 = r5
            double r18 = r18.getZoomLevelDouble()
            r20 = r3
            r0 = r20
            org.osmdroid.views.overlay.simplefastpoint.SimpleFastPointOverlayOptions r0 = r0.mStyle
            r20 = r0
            r0 = r20
            int r0 = r0.mMinZoomShowLabels
            r20 = r0
            r0 = r20
            double r0 = (double) r0
            r20 = r0
            int r18 = (r18 > r20 ? 1 : (r18 == r20 ? 0 : -1))
            if (r18 < 0) goto L_0x051b
            r18 = 1
        L_0x04b9:
            r11 = r18
            r18 = r5
            org.osmdroid.util.BoundingBox r18 = r18.getBoundingBox()
            r7 = r18
            r18 = r3
            r0 = r18
            org.osmdroid.views.overlay.simplefastpoint.SimpleFastPointOverlay$PointAdapter r0 = r0.mPointList
            r18 = r0
            java.util.Iterator r18 = r18.iterator()
            r16 = r18
        L_0x04d1:
            r18 = r16
            boolean r18 = r18.hasNext()
            if (r18 == 0) goto L_0x075d
            r18 = r16
            java.lang.Object r18 = r18.next()
            org.osmdroid.api.IGeoPoint r18 = (org.osmdroid.api.IGeoPoint) r18
            r17 = r18
            r18 = r17
            if (r18 != 0) goto L_0x051e
            goto L_0x04d1
        L_0x04e8:
            r18 = r3
            r0 = r18
            boolean[][] r0 = r0.gridBool
            r18 = r0
            r14 = r18
            r18 = r14
            r0 = r18
            int r0 = r0.length
            r18 = r0
            r15 = r18
            r18 = 0
            r16 = r18
        L_0x04ff:
            r18 = r16
            r19 = r15
            r0 = r18
            r1 = r19
            if (r0 >= r1) goto L_0x0484
            r18 = r14
            r19 = r16
            r18 = r18[r19]
            r17 = r18
            r18 = r17
            r19 = 0
            java.util.Arrays.fill(r18, r19)
            int r16 = r16 + 1
            goto L_0x04ff
        L_0x051b:
            r18 = 0
            goto L_0x04b9
        L_0x051e:
            r18 = r17
            double r18 = r18.getLatitude()
            r20 = r7
            double r20 = r20.getLatSouth()
            int r18 = (r18 > r20 ? 1 : (r18 == r20 ? 0 : -1))
            if (r18 <= 0) goto L_0x06d2
            r18 = r17
            double r18 = r18.getLatitude()
            r20 = r7
            double r20 = r20.getLatNorth()
            int r18 = (r18 > r20 ? 1 : (r18 == r20 ? 0 : -1))
            if (r18 >= 0) goto L_0x06d2
            r18 = r17
            double r18 = r18.getLongitude()
            r20 = r7
            double r20 = r20.getLonWest()
            int r18 = (r18 > r20 ? 1 : (r18 == r20 ? 0 : -1))
            if (r18 <= 0) goto L_0x06d2
            r18 = r17
            double r18 = r18.getLongitude()
            r20 = r7
            double r20 = r20.getLonEast()
            int r18 = (r18 > r20 ? 1 : (r18 == r20 ? 0 : -1))
            if (r18 >= 0) goto L_0x06d2
            r18 = r9
            r19 = r17
            r20 = r8
            android.graphics.Point r18 = r18.toPixels(r19, r20)
            r18 = r8
            r0 = r18
            int r0 = r0.x
            r18 = r0
            r0 = r18
            float r0 = (float) r0
            r18 = r0
            r19 = r3
            r0 = r19
            org.osmdroid.views.overlay.simplefastpoint.SimpleFastPointOverlayOptions r0 = r0.mStyle
            r19 = r0
            r0 = r19
            int r0 = r0.mCellSize
            r19 = r0
            r0 = r19
            float r0 = (float) r0
            r19 = r0
            float r18 = r18 / r19
            r0 = r18
            double r0 = (double) r0
            r18 = r0
            double r18 = java.lang.Math.floor(r18)
            r0 = r18
            int r0 = (int) r0
            r18 = r0
            r14 = r18
            r18 = r8
            r0 = r18
            int r0 = r0.y
            r18 = r0
            r0 = r18
            float r0 = (float) r0
            r18 = r0
            r19 = r3
            r0 = r19
            org.osmdroid.views.overlay.simplefastpoint.SimpleFastPointOverlayOptions r0 = r0.mStyle
            r19 = r0
            r0 = r19
            int r0 = r0.mCellSize
            r19 = r0
            r0 = r19
            float r0 = (float) r0
            r19 = r0
            float r18 = r18 / r19
            r0 = r18
            double r0 = (double) r0
            r18 = r0
            double r18 = java.lang.Math.floor(r18)
            r0 = r18
            int r0 = (int) r0
            r18 = r0
            r15 = r18
            r18 = r14
            r19 = r3
            r0 = r19
            int r0 = r0.gridWid
            r19 = r0
            r0 = r18
            r1 = r19
            if (r0 >= r1) goto L_0x04d1
            r18 = r15
            r19 = r3
            r0 = r19
            int r0 = r0.gridHei
            r19 = r0
            r0 = r18
            r1 = r19
            if (r0 >= r1) goto L_0x04d1
            r18 = r14
            if (r18 < 0) goto L_0x04d1
            r18 = r15
            if (r18 < 0) goto L_0x04d1
            r18 = r3
            r0 = r18
            boolean[][] r0 = r0.gridBool
            r18 = r0
            r19 = r14
            r18 = r18[r19]
            r19 = r15
            boolean r18 = r18[r19]
            if (r18 == 0) goto L_0x0608
            goto L_0x04d1
        L_0x0608:
            r18 = r3
            r0 = r18
            boolean[][] r0 = r0.gridBool
            r18 = r0
            r19 = r14
            r18 = r18[r19]
            r19 = r15
            r20 = 1
            r18[r19] = r20
            r18 = r3
            r0 = r18
            org.osmdroid.views.overlay.simplefastpoint.SimpleFastPointOverlayOptions r0 = r0.mStyle
            r18 = r0
            r0 = r18
            org.osmdroid.views.overlay.simplefastpoint.SimpleFastPointOverlayOptions$Shape r0 = r0.mSymbol
            r18 = r0
            org.osmdroid.views.overlay.simplefastpoint.SimpleFastPointOverlayOptions$Shape r19 = org.osmdroid.views.overlay.simplefastpoint.SimpleFastPointOverlayOptions.Shape.CIRCLE
            r0 = r18
            r1 = r19
            if (r0 != r1) goto L_0x06d4
            r18 = r4
            r19 = r8
            r0 = r19
            int r0 = r0.x
            r19 = r0
            r0 = r19
            float r0 = (float) r0
            r19 = r0
            r20 = r8
            r0 = r20
            int r0 = r0.y
            r20 = r0
            r0 = r20
            float r0 = (float) r0
            r20 = r0
            r21 = r3
            r0 = r21
            org.osmdroid.views.overlay.simplefastpoint.SimpleFastPointOverlayOptions r0 = r0.mStyle
            r21 = r0
            r0 = r21
            float r0 = r0.mCircleRadius
            r21 = r0
            r22 = r3
            r0 = r22
            org.osmdroid.views.overlay.simplefastpoint.SimpleFastPointOverlayOptions r0 = r0.mStyle
            r22 = r0
            r0 = r22
            android.graphics.Paint r0 = r0.mPointStyle
            r22 = r0
            r18.drawCircle(r19, r20, r21, r22)
        L_0x066b:
            r18 = r3
            r0 = r18
            org.osmdroid.views.overlay.simplefastpoint.SimpleFastPointOverlay$PointAdapter r0 = r0.mPointList
            r18 = r0
            boolean r18 = r18.isLabelled()
            if (r18 == 0) goto L_0x06d2
            r18 = r11
            if (r18 == 0) goto L_0x06d2
            r18 = r17
            org.osmdroid.views.overlay.simplefastpoint.LabelledGeoPoint r18 = (org.osmdroid.views.overlay.simplefastpoint.LabelledGeoPoint) r18
            java.lang.String r18 = r18.getLabel()
            r24 = r18
            r18 = r24
            r19 = r24
            r10 = r19
            if (r18 == 0) goto L_0x06d2
            r18 = r4
            r19 = r10
            r20 = r8
            r0 = r20
            int r0 = r0.x
            r20 = r0
            r0 = r20
            float r0 = (float) r0
            r20 = r0
            r21 = r8
            r0 = r21
            int r0 = r0.y
            r21 = r0
            r0 = r21
            float r0 = (float) r0
            r21 = r0
            r22 = r3
            r0 = r22
            org.osmdroid.views.overlay.simplefastpoint.SimpleFastPointOverlayOptions r0 = r0.mStyle
            r22 = r0
            r0 = r22
            float r0 = r0.mCircleRadius
            r22 = r0
            float r21 = r21 - r22
            r22 = 1084227584(0x40a00000, float:5.0)
            float r21 = r21 - r22
            r22 = r3
            r0 = r22
            org.osmdroid.views.overlay.simplefastpoint.SimpleFastPointOverlayOptions r0 = r0.mStyle
            r22 = r0
            r0 = r22
            android.graphics.Paint r0 = r0.mTextStyle
            r22 = r0
            r18.drawText(r19, r20, r21, r22)
        L_0x06d2:
            goto L_0x04d1
        L_0x06d4:
            r18 = r4
            r19 = r8
            r0 = r19
            int r0 = r0.x
            r19 = r0
            r0 = r19
            float r0 = (float) r0
            r19 = r0
            r20 = r3
            r0 = r20
            org.osmdroid.views.overlay.simplefastpoint.SimpleFastPointOverlayOptions r0 = r0.mStyle
            r20 = r0
            r0 = r20
            float r0 = r0.mCircleRadius
            r20 = r0
            float r19 = r19 - r20
            r20 = r8
            r0 = r20
            int r0 = r0.y
            r20 = r0
            r0 = r20
            float r0 = (float) r0
            r20 = r0
            r21 = r3
            r0 = r21
            org.osmdroid.views.overlay.simplefastpoint.SimpleFastPointOverlayOptions r0 = r0.mStyle
            r21 = r0
            r0 = r21
            float r0 = r0.mCircleRadius
            r21 = r0
            float r20 = r20 - r21
            r21 = r8
            r0 = r21
            int r0 = r0.x
            r21 = r0
            r0 = r21
            float r0 = (float) r0
            r21 = r0
            r22 = r3
            r0 = r22
            org.osmdroid.views.overlay.simplefastpoint.SimpleFastPointOverlayOptions r0 = r0.mStyle
            r22 = r0
            r0 = r22
            float r0 = r0.mCircleRadius
            r22 = r0
            float r21 = r21 + r22
            r22 = r8
            r0 = r22
            int r0 = r0.y
            r22 = r0
            r0 = r22
            float r0 = (float) r0
            r22 = r0
            r23 = r3
            r0 = r23
            org.osmdroid.views.overlay.simplefastpoint.SimpleFastPointOverlayOptions r0 = r0.mStyle
            r23 = r0
            r0 = r23
            float r0 = r0.mCircleRadius
            r23 = r0
            float r22 = r22 + r23
            r23 = r3
            r0 = r23
            org.osmdroid.views.overlay.simplefastpoint.SimpleFastPointOverlayOptions r0 = r0.mStyle
            r23 = r0
            r0 = r23
            android.graphics.Paint r0 = r0.mPointStyle
            r23 = r0
            r18.drawRect(r19, r20, r21, r22, r23)
            goto L_0x066b
        L_0x075d:
            goto L_0x004b
        L_0x075f:
            r18 = r3
            r0 = r18
            org.osmdroid.views.overlay.simplefastpoint.SimpleFastPointOverlayOptions r0 = r0.mStyle
            r18 = r0
            r0 = r18
            org.osmdroid.views.overlay.simplefastpoint.SimpleFastPointOverlayOptions$LabelPolicy r0 = r0.mLabelPolicy
            r18 = r0
            org.osmdroid.views.overlay.simplefastpoint.SimpleFastPointOverlayOptions$LabelPolicy r19 = org.osmdroid.views.overlay.simplefastpoint.SimpleFastPointOverlayOptions.LabelPolicy.ZOOM_THRESHOLD
            r0 = r18
            r1 = r19
            if (r0 != r1) goto L_0x07c3
            r18 = r5
            double r18 = r18.getZoomLevelDouble()
            r20 = r3
            r0 = r20
            org.osmdroid.views.overlay.simplefastpoint.SimpleFastPointOverlayOptions r0 = r0.mStyle
            r20 = r0
            r0 = r20
            int r0 = r0.mMinZoomShowLabels
            r20 = r0
            r0 = r20
            double r0 = (double) r0
            r20 = r0
            int r18 = (r18 > r20 ? 1 : (r18 == r20 ? 0 : -1))
            if (r18 < 0) goto L_0x07c3
            r18 = 1
        L_0x0794:
            r11 = r18
            r18 = r5
            org.osmdroid.util.BoundingBox r18 = r18.getBoundingBox()
            r7 = r18
            r18 = r3
            r0 = r18
            org.osmdroid.views.overlay.simplefastpoint.SimpleFastPointOverlay$PointAdapter r0 = r0.mPointList
            r18 = r0
            java.util.Iterator r18 = r18.iterator()
            r16 = r18
        L_0x07ac:
            r18 = r16
            boolean r18 = r18.hasNext()
            if (r18 == 0) goto L_0x004b
            r18 = r16
            java.lang.Object r18 = r18.next()
            org.osmdroid.api.IGeoPoint r18 = (org.osmdroid.api.IGeoPoint) r18
            r17 = r18
            r18 = r17
            if (r18 != 0) goto L_0x07c6
            goto L_0x07ac
        L_0x07c3:
            r18 = 0
            goto L_0x0794
        L_0x07c6:
            r18 = r17
            double r18 = r18.getLatitude()
            r20 = r7
            double r20 = r20.getLatSouth()
            int r18 = (r18 > r20 ? 1 : (r18 == r20 ? 0 : -1))
            if (r18 <= 0) goto L_0x08c8
            r18 = r17
            double r18 = r18.getLatitude()
            r20 = r7
            double r20 = r20.getLatNorth()
            int r18 = (r18 > r20 ? 1 : (r18 == r20 ? 0 : -1))
            if (r18 >= 0) goto L_0x08c8
            r18 = r17
            double r18 = r18.getLongitude()
            r20 = r7
            double r20 = r20.getLonWest()
            int r18 = (r18 > r20 ? 1 : (r18 == r20 ? 0 : -1))
            if (r18 <= 0) goto L_0x08c8
            r18 = r17
            double r18 = r18.getLongitude()
            r20 = r7
            double r20 = r20.getLonEast()
            int r18 = (r18 > r20 ? 1 : (r18 == r20 ? 0 : -1))
            if (r18 >= 0) goto L_0x08c8
            r18 = r9
            r19 = r17
            r20 = r8
            android.graphics.Point r18 = r18.toPixels(r19, r20)
            r18 = r3
            r0 = r18
            org.osmdroid.views.overlay.simplefastpoint.SimpleFastPointOverlayOptions r0 = r0.mStyle
            r18 = r0
            r0 = r18
            org.osmdroid.views.overlay.simplefastpoint.SimpleFastPointOverlayOptions$Shape r0 = r0.mSymbol
            r18 = r0
            org.osmdroid.views.overlay.simplefastpoint.SimpleFastPointOverlayOptions$Shape r19 = org.osmdroid.views.overlay.simplefastpoint.SimpleFastPointOverlayOptions.Shape.CIRCLE
            r0 = r18
            r1 = r19
            if (r0 != r1) goto L_0x08ca
            r18 = r4
            r19 = r8
            r0 = r19
            int r0 = r0.x
            r19 = r0
            r0 = r19
            float r0 = (float) r0
            r19 = r0
            r20 = r8
            r0 = r20
            int r0 = r0.y
            r20 = r0
            r0 = r20
            float r0 = (float) r0
            r20 = r0
            r21 = r3
            r0 = r21
            org.osmdroid.views.overlay.simplefastpoint.SimpleFastPointOverlayOptions r0 = r0.mStyle
            r21 = r0
            r0 = r21
            float r0 = r0.mCircleRadius
            r21 = r0
            r22 = r3
            r0 = r22
            org.osmdroid.views.overlay.simplefastpoint.SimpleFastPointOverlayOptions r0 = r0.mStyle
            r22 = r0
            r0 = r22
            android.graphics.Paint r0 = r0.mPointStyle
            r22 = r0
            r18.drawCircle(r19, r20, r21, r22)
        L_0x0861:
            r18 = r3
            r0 = r18
            org.osmdroid.views.overlay.simplefastpoint.SimpleFastPointOverlay$PointAdapter r0 = r0.mPointList
            r18 = r0
            boolean r18 = r18.isLabelled()
            if (r18 == 0) goto L_0x08c8
            r18 = r11
            if (r18 == 0) goto L_0x08c8
            r18 = r17
            org.osmdroid.views.overlay.simplefastpoint.LabelledGeoPoint r18 = (org.osmdroid.views.overlay.simplefastpoint.LabelledGeoPoint) r18
            java.lang.String r18 = r18.getLabel()
            r24 = r18
            r18 = r24
            r19 = r24
            r10 = r19
            if (r18 == 0) goto L_0x08c8
            r18 = r4
            r19 = r10
            r20 = r8
            r0 = r20
            int r0 = r0.x
            r20 = r0
            r0 = r20
            float r0 = (float) r0
            r20 = r0
            r21 = r8
            r0 = r21
            int r0 = r0.y
            r21 = r0
            r0 = r21
            float r0 = (float) r0
            r21 = r0
            r22 = r3
            r0 = r22
            org.osmdroid.views.overlay.simplefastpoint.SimpleFastPointOverlayOptions r0 = r0.mStyle
            r22 = r0
            r0 = r22
            float r0 = r0.mCircleRadius
            r22 = r0
            float r21 = r21 - r22
            r22 = 1084227584(0x40a00000, float:5.0)
            float r21 = r21 - r22
            r22 = r3
            r0 = r22
            org.osmdroid.views.overlay.simplefastpoint.SimpleFastPointOverlayOptions r0 = r0.mStyle
            r22 = r0
            r0 = r22
            android.graphics.Paint r0 = r0.mTextStyle
            r22 = r0
            r18.drawText(r19, r20, r21, r22)
        L_0x08c8:
            goto L_0x07ac
        L_0x08ca:
            r18 = r4
            r19 = r8
            r0 = r19
            int r0 = r0.x
            r19 = r0
            r0 = r19
            float r0 = (float) r0
            r19 = r0
            r20 = r3
            r0 = r20
            org.osmdroid.views.overlay.simplefastpoint.SimpleFastPointOverlayOptions r0 = r0.mStyle
            r20 = r0
            r0 = r20
            float r0 = r0.mCircleRadius
            r20 = r0
            float r19 = r19 - r20
            r20 = r8
            r0 = r20
            int r0 = r0.y
            r20 = r0
            r0 = r20
            float r0 = (float) r0
            r20 = r0
            r21 = r3
            r0 = r21
            org.osmdroid.views.overlay.simplefastpoint.SimpleFastPointOverlayOptions r0 = r0.mStyle
            r21 = r0
            r0 = r21
            float r0 = r0.mCircleRadius
            r21 = r0
            float r20 = r20 - r21
            r21 = r8
            r0 = r21
            int r0 = r0.x
            r21 = r0
            r0 = r21
            float r0 = (float) r0
            r21 = r0
            r22 = r3
            r0 = r22
            org.osmdroid.views.overlay.simplefastpoint.SimpleFastPointOverlayOptions r0 = r0.mStyle
            r22 = r0
            r0 = r22
            float r0 = r0.mCircleRadius
            r22 = r0
            float r21 = r21 + r22
            r22 = r8
            r0 = r22
            int r0 = r0.y
            r22 = r0
            r0 = r22
            float r0 = (float) r0
            r22 = r0
            r23 = r3
            r0 = r23
            org.osmdroid.views.overlay.simplefastpoint.SimpleFastPointOverlayOptions r0 = r0.mStyle
            r23 = r0
            r0 = r23
            float r0 = r0.mCircleRadius
            r23 = r0
            float r22 = r22 + r23
            r23 = r3
            r0 = r23
            org.osmdroid.views.overlay.simplefastpoint.SimpleFastPointOverlayOptions r0 = r0.mStyle
            r23 = r0
            r0 = r23
            android.graphics.Paint r0 = r0.mPointStyle
            r23 = r0
            r18.drawRect(r19, r20, r21, r22, r23)
            goto L_0x0861
        L_0x0953:
            r18 = r4
            r19 = r8
            r0 = r19
            int r0 = r0.x
            r19 = r0
            r0 = r19
            float r0 = (float) r0
            r19 = r0
            r20 = r3
            r0 = r20
            org.osmdroid.views.overlay.simplefastpoint.SimpleFastPointOverlayOptions r0 = r0.mStyle
            r20 = r0
            r0 = r20
            float r0 = r0.mSelectedCircleRadius
            r20 = r0
            float r19 = r19 - r20
            r20 = r8
            r0 = r20
            int r0 = r0.y
            r20 = r0
            r0 = r20
            float r0 = (float) r0
            r20 = r0
            r21 = r3
            r0 = r21
            org.osmdroid.views.overlay.simplefastpoint.SimpleFastPointOverlayOptions r0 = r0.mStyle
            r21 = r0
            r0 = r21
            float r0 = r0.mSelectedCircleRadius
            r21 = r0
            float r20 = r20 - r21
            r21 = r8
            r0 = r21
            int r0 = r0.x
            r21 = r0
            r0 = r21
            float r0 = (float) r0
            r21 = r0
            r22 = r3
            r0 = r22
            org.osmdroid.views.overlay.simplefastpoint.SimpleFastPointOverlayOptions r0 = r0.mStyle
            r22 = r0
            r0 = r22
            float r0 = r0.mSelectedCircleRadius
            r22 = r0
            float r21 = r21 + r22
            r22 = r8
            r0 = r22
            int r0 = r0.y
            r22 = r0
            r0 = r22
            float r0 = (float) r0
            r22 = r0
            r23 = r3
            r0 = r23
            org.osmdroid.views.overlay.simplefastpoint.SimpleFastPointOverlayOptions r0 = r0.mStyle
            r23 = r0
            r0 = r23
            float r0 = r0.mSelectedCircleRadius
            r23 = r0
            float r22 = r22 + r23
            r23 = r3
            r0 = r23
            org.osmdroid.views.overlay.simplefastpoint.SimpleFastPointOverlayOptions r0 = r0.mStyle
            r23 = r0
            r0 = r23
            android.graphics.Paint r0 = r0.mSelectedPointStyle
            r23 = r0
            r18.drawRect(r19, r20, r21, r22, r23)
            goto L_0x010e
        */
        throw new UnsupportedOperationException("Method not decompiled: org.osmdroid.views.overlay.simplefastpoint.SimpleFastPointOverlay.draw(android.graphics.Canvas, org.osmdroid.views.MapView, boolean):void");
    }
}
