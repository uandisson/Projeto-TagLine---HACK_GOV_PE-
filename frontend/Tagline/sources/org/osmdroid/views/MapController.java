package org.osmdroid.views;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Build;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import java.util.Iterator;
import java.util.LinkedList;
import org.osmdroid.api.IGeoPoint;
import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.events.ScrollEvent;
import org.osmdroid.events.ZoomEvent;
import org.osmdroid.util.BoundingBox;
import org.osmdroid.util.BoundingBoxE6;
import org.osmdroid.views.MapView;
import org.osmdroid.views.util.MyMath;

public class MapController implements IMapController, MapView.OnFirstLayoutListener {
    private Animator mCurrentAnimator;
    protected final MapView mMapView;
    private ReplayController mReplayController;
    private ValueAnimator mZoomInAnimation;
    private ScaleAnimation mZoomInAnimationOld;
    private ValueAnimator mZoomOutAnimation;
    private ScaleAnimation mZoomOutAnimationOld;

    private enum ReplayType {
    }

    /* JADX WARNING: type inference failed for: r4v22, types: [android.animation.ValueAnimator$AnimatorUpdateListener] */
    /* JADX WARNING: type inference failed for: r4v30, types: [android.animation.ValueAnimator$AnimatorUpdateListener] */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public MapController(org.osmdroid.views.MapView r16) {
        /*
            r15 = this;
            r0 = r15
            r1 = r16
            r3 = r0
            r3.<init>()
            r3 = r0
            r4 = r1
            r3.mMapView = r4
            r3 = r0
            org.osmdroid.views.MapController$ReplayController r4 = new org.osmdroid.views.MapController$ReplayController
            r14 = r4
            r4 = r14
            r5 = r14
            r6 = r0
            r7 = 0
            r5.<init>(r6, r7)
            r3.mReplayController = r4
            r3 = r0
            org.osmdroid.views.MapView r3 = r3.mMapView
            boolean r3 = r3.isLayoutOccurred()
            if (r3 != 0) goto L_0x0028
            r3 = r0
            org.osmdroid.views.MapView r3 = r3.mMapView
            r4 = r0
            r3.addOnFirstLayoutListener(r4)
        L_0x0028:
            int r3 = android.os.Build.VERSION.SDK_INT
            r4 = 11
            if (r3 < r4) goto L_0x008f
            org.osmdroid.views.MapController$ZoomAnimatorListener r3 = new org.osmdroid.views.MapController$ZoomAnimatorListener
            r14 = r3
            r3 = r14
            r4 = r14
            r5 = r0
            r4.<init>(r5)
            r2 = r3
            r3 = r0
            r4 = 2
            float[] r4 = new float[r4]
            r4 = {1065353216, 1073741824} // fill-array
            android.animation.ValueAnimator r4 = android.animation.ValueAnimator.ofFloat(r4)
            r3.mZoomInAnimation = r4
            r3 = r0
            android.animation.ValueAnimator r3 = r3.mZoomInAnimation
            r4 = r2
            r3.addListener(r4)
            r3 = r0
            android.animation.ValueAnimator r3 = r3.mZoomInAnimation
            r4 = r2
            r3.addUpdateListener(r4)
            r3 = r0
            android.animation.ValueAnimator r3 = r3.mZoomInAnimation
            org.osmdroid.config.IConfigurationProvider r4 = org.osmdroid.config.Configuration.getInstance()
            int r4 = r4.getAnimationSpeedShort()
            long r4 = (long) r4
            android.animation.ValueAnimator r3 = r3.setDuration(r4)
            r3 = r0
            r4 = 2
            float[] r4 = new float[r4]
            r4 = {1065353216, 1056964608} // fill-array
            android.animation.ValueAnimator r4 = android.animation.ValueAnimator.ofFloat(r4)
            r3.mZoomOutAnimation = r4
            r3 = r0
            android.animation.ValueAnimator r3 = r3.mZoomOutAnimation
            r4 = r2
            r3.addListener(r4)
            r3 = r0
            android.animation.ValueAnimator r3 = r3.mZoomOutAnimation
            r4 = r2
            r3.addUpdateListener(r4)
            r3 = r0
            android.animation.ValueAnimator r3 = r3.mZoomOutAnimation
            org.osmdroid.config.IConfigurationProvider r4 = org.osmdroid.config.Configuration.getInstance()
            int r4 = r4.getAnimationSpeedShort()
            long r4 = (long) r4
            android.animation.ValueAnimator r3 = r3.setDuration(r4)
        L_0x008e:
            return
        L_0x008f:
            org.osmdroid.views.MapController$ZoomAnimationListener r3 = new org.osmdroid.views.MapController$ZoomAnimationListener
            r14 = r3
            r3 = r14
            r4 = r14
            r5 = r0
            r4.<init>(r5)
            r2 = r3
            r3 = r0
            android.view.animation.ScaleAnimation r4 = new android.view.animation.ScaleAnimation
            r14 = r4
            r4 = r14
            r5 = r14
            r6 = 1065353216(0x3f800000, float:1.0)
            r7 = 1073741824(0x40000000, float:2.0)
            r8 = 1065353216(0x3f800000, float:1.0)
            r9 = 1073741824(0x40000000, float:2.0)
            r10 = 1
            r11 = 1056964608(0x3f000000, float:0.5)
            r12 = 1
            r13 = 1056964608(0x3f000000, float:0.5)
            r5.<init>(r6, r7, r8, r9, r10, r11, r12, r13)
            r3.mZoomInAnimationOld = r4
            r3 = r0
            android.view.animation.ScaleAnimation r4 = new android.view.animation.ScaleAnimation
            r14 = r4
            r4 = r14
            r5 = r14
            r6 = 1065353216(0x3f800000, float:1.0)
            r7 = 1056964608(0x3f000000, float:0.5)
            r8 = 1065353216(0x3f800000, float:1.0)
            r9 = 1056964608(0x3f000000, float:0.5)
            r10 = 1
            r11 = 1056964608(0x3f000000, float:0.5)
            r12 = 1
            r13 = 1056964608(0x3f000000, float:0.5)
            r5.<init>(r6, r7, r8, r9, r10, r11, r12, r13)
            r3.mZoomOutAnimationOld = r4
            r3 = r0
            android.view.animation.ScaleAnimation r3 = r3.mZoomInAnimationOld
            org.osmdroid.config.IConfigurationProvider r4 = org.osmdroid.config.Configuration.getInstance()
            int r4 = r4.getAnimationSpeedShort()
            long r4 = (long) r4
            r3.setDuration(r4)
            r3 = r0
            android.view.animation.ScaleAnimation r3 = r3.mZoomOutAnimationOld
            org.osmdroid.config.IConfigurationProvider r4 = org.osmdroid.config.Configuration.getInstance()
            int r4 = r4.getAnimationSpeedShort()
            long r4 = (long) r4
            r3.setDuration(r4)
            r3 = r0
            android.view.animation.ScaleAnimation r3 = r3.mZoomInAnimationOld
            r4 = r2
            r3.setAnimationListener(r4)
            r3 = r0
            android.view.animation.ScaleAnimation r3 = r3.mZoomOutAnimationOld
            r4 = r2
            r3.setAnimationListener(r4)
            goto L_0x008e
        */
        throw new UnsupportedOperationException("Method not decompiled: org.osmdroid.views.MapController.<init>(org.osmdroid.views.MapView):void");
    }

    public void onFirstLayout(View view, int i, int i2, int i3, int i4) {
        View view2 = view;
        int i5 = i;
        int i6 = i2;
        int i7 = i3;
        int i8 = i4;
        this.mReplayController.replayCalls();
    }

    @Deprecated
    public void zoomToSpan(BoundingBoxE6 boundingBoxE6) {
        BoundingBoxE6 bb = boundingBoxE6;
        zoomToSpan(bb.getLatitudeSpanE6(), bb.getLongitudeSpanE6());
    }

    public void zoomToSpan(double d, double d2) {
        double latSpan = d;
        double lonSpan = d2;
        if (latSpan > 0.0d && lonSpan > 0.0d) {
            if (!this.mMapView.isLayoutOccurred()) {
                this.mReplayController.zoomToSpan(latSpan, lonSpan);
                return;
            }
            BoundingBox bb = this.mMapView.getProjection().getBoundingBox();
            double curZoomLevel = this.mMapView.getProjection().getZoomLevel();
            double curLatSpan = bb.getLatitudeSpan();
            double diffNeeded = Math.max(latSpan / curLatSpan, lonSpan / bb.getLongitudeSpan());
            if (diffNeeded > 1.0d) {
                double zoomLevel = this.mMapView.setZoomLevel(curZoomLevel - ((double) MyMath.getNextSquareNumberAbove((float) diffNeeded)));
            } else if (diffNeeded < 0.5d) {
                double zoomLevel2 = this.mMapView.setZoomLevel((curZoomLevel + ((double) MyMath.getNextSquareNumberAbove(1.0f / ((float) diffNeeded)))) - 1.0d);
            }
        }
    }

    public void zoomToSpan(int latSpanE6, int lonSpanE6) {
        zoomToSpan(((double) latSpanE6) * 1.0E-6d, ((double) lonSpanE6) * 1.0E-6d);
    }

    public void animateTo(IGeoPoint iGeoPoint) {
        IGeoPoint point = iGeoPoint;
        if (!this.mMapView.isLayoutOccurred()) {
            this.mReplayController.animateTo(point);
            return;
        }
        Point p = this.mMapView.getProjection().toPixels(point, (Point) null);
        animateTo(p.x, p.y);
    }

    public void animateTo(int i, int i2) {
        int x = i;
        int y = i2;
        if (!this.mMapView.isLayoutOccurred()) {
            this.mReplayController.animateTo(x, y);
        } else if (!this.mMapView.isAnimating()) {
            this.mMapView.mIsFlinging = false;
            Point mercatorPoint = this.mMapView.getProjection().toMercatorPixels(x, y, (Point) null);
            mercatorPoint.offset((-this.mMapView.getWidth()) / 2, (-this.mMapView.getHeight()) / 2);
            int xStart = this.mMapView.getScrollX();
            int yStart = this.mMapView.getScrollY();
            int dx = mercatorPoint.x - xStart;
            int dy = mercatorPoint.y - yStart;
            if (dx != 0 || dy != 0) {
                this.mMapView.getScroller().startScroll(xStart, yStart, dx, dy, Configuration.getInstance().getAnimationSpeedDefault());
                this.mMapView.postInvalidate();
            }
        }
    }

    public void scrollBy(int x, int y) {
        this.mMapView.scrollBy(x, y);
    }

    public void setCenter(IGeoPoint iGeoPoint) {
        ScrollEvent scrollEvent;
        IGeoPoint point = iGeoPoint;
        if (this.mMapView.mListener != null) {
            new ScrollEvent(this.mMapView, 0, 0);
            boolean onScroll = this.mMapView.mListener.onScroll(scrollEvent);
        }
        if (!this.mMapView.isLayoutOccurred()) {
            this.mReplayController.setCenter(point);
            return;
        }
        Point p = this.mMapView.getProjection().toPixels(point, (Point) null);
        Point p2 = this.mMapView.getProjection().toMercatorPixels(p.x, p.y, p);
        p2.offset((-this.mMapView.getWidth()) / 2, (-this.mMapView.getHeight()) / 2);
        this.mMapView.scrollTo(p2.x, p2.y);
    }

    public void stopPanning() {
        this.mMapView.mIsFlinging = false;
        this.mMapView.getScroller().forceFinished(true);
    }

    public void stopAnimation(boolean z) {
        boolean jumpToTarget = z;
        if (!this.mMapView.getScroller().isFinished()) {
            if (jumpToTarget) {
                this.mMapView.mIsFlinging = false;
                this.mMapView.getScroller().abortAnimation();
            } else {
                stopPanning();
            }
        }
        if (Build.VERSION.SDK_INT >= 11) {
            Animator currentAnimator = this.mCurrentAnimator;
            if (this.mMapView.mIsAnimating.get()) {
                currentAnimator.end();
            }
        } else if (this.mMapView.mIsAnimating.get()) {
            this.mMapView.clearAnimation();
        }
    }

    public int setZoom(int zoomlevel) {
        return (int) setZoom((double) zoomlevel);
    }

    public double setZoom(double pZoomlevel) {
        return this.mMapView.setZoomLevel(pZoomlevel);
    }

    public boolean zoomIn() {
        return zoomTo(this.mMapView.getZoomLevel(false) + 1.0d);
    }

    public boolean zoomIn(Long animationSpeed) {
        return zoomTo(this.mMapView.getZoomLevel(false) + 1.0d, animationSpeed);
    }

    /* JADX WARNING: type inference failed for: r8v18, types: [android.animation.ValueAnimator$AnimatorUpdateListener] */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean zoomInFixing(int r19, int r20, java.lang.Long r21) {
        /*
            r18 = this;
            r1 = r18
            r2 = r19
            r3 = r20
            r4 = r21
            r7 = r1
            org.osmdroid.views.MapView r7 = r7.mMapView
            android.graphics.PointF r7 = r7.mMultiTouchScalePoint
            r8 = r2
            float r8 = (float) r8
            r9 = r3
            float r9 = (float) r9
            r7.set(r8, r9)
            r7 = r1
            org.osmdroid.views.MapView r7 = r7.mMapView
            boolean r7 = r7.canZoomIn()
            if (r7 == 0) goto L_0x010a
            r7 = r1
            org.osmdroid.views.MapView r7 = r7.mMapView
            org.osmdroid.events.MapListener r7 = r7.mListener
            if (r7 == 0) goto L_0x0045
            r7 = r1
            org.osmdroid.views.MapView r7 = r7.mMapView
            org.osmdroid.events.MapListener r7 = r7.mListener
            org.osmdroid.events.ZoomEvent r8 = new org.osmdroid.events.ZoomEvent
            r17 = r8
            r8 = r17
            r9 = r17
            r10 = r1
            org.osmdroid.views.MapView r10 = r10.mMapView
            r11 = r1
            org.osmdroid.views.MapView r11 = r11.mMapView
            double r11 = r11.getZoomLevelDouble()
            r13 = 4607182418800017408(0x3ff0000000000000, double:1.0)
            double r11 = r11 + r13
            r9.<init>(r10, r11)
            boolean r7 = r7.onZoom(r8)
        L_0x0045:
            r7 = r1
            org.osmdroid.views.MapView r7 = r7.mMapView
            java.util.concurrent.atomic.AtomicBoolean r7 = r7.mIsAnimating
            r8 = 1
            boolean r7 = r7.getAndSet(r8)
            if (r7 == 0) goto L_0x0054
            r7 = 0
            r1 = r7
        L_0x0053:
            return r1
        L_0x0054:
            r7 = r1
            org.osmdroid.views.MapView r7 = r7.mMapView
            java.util.concurrent.atomic.AtomicReference<java.lang.Double> r7 = r7.mTargetZoomLevel
            r8 = r1
            org.osmdroid.views.MapView r8 = r8.mMapView
            r9 = 0
            double r8 = r8.getZoomLevel(r9)
            r10 = 4607182418800017408(0x3ff0000000000000, double:1.0)
            double r8 = r8 + r10
            java.lang.Double r8 = java.lang.Double.valueOf(r8)
            r7.set(r8)
            r7 = r4
            if (r7 != 0) goto L_0x008d
            int r7 = android.os.Build.VERSION.SDK_INT
            r8 = 11
            if (r7 < r8) goto L_0x0083
            r7 = r1
            r8 = r1
            android.animation.ValueAnimator r8 = r8.mZoomInAnimation
            r7.mCurrentAnimator = r8
            r7 = r1
            android.animation.ValueAnimator r7 = r7.mZoomInAnimation
            r7.start()
        L_0x0080:
            r7 = 1
            r1 = r7
            goto L_0x0053
        L_0x0083:
            r7 = r1
            org.osmdroid.views.MapView r7 = r7.mMapView
            r8 = r1
            android.view.animation.ScaleAnimation r8 = r8.mZoomInAnimationOld
            r7.startAnimation(r8)
            goto L_0x0080
        L_0x008d:
            int r7 = android.os.Build.VERSION.SDK_INT
            r8 = 11
            if (r7 < r8) goto L_0x00c8
            org.osmdroid.views.MapController$ZoomAnimatorListener r7 = new org.osmdroid.views.MapController$ZoomAnimatorListener
            r17 = r7
            r7 = r17
            r8 = r17
            r9 = r1
            r8.<init>(r9)
            r5 = r7
            r7 = 2
            float[] r7 = new float[r7]
            r7 = {1065353216, 1073741824} // fill-array
            android.animation.ValueAnimator r7 = android.animation.ValueAnimator.ofFloat(r7)
            r6 = r7
            r7 = r6
            r8 = r5
            r7.addListener(r8)
            r7 = r6
            r8 = r5
            r7.addUpdateListener(r8)
            r7 = r6
            org.osmdroid.config.IConfigurationProvider r8 = org.osmdroid.config.Configuration.getInstance()
            int r8 = r8.getAnimationSpeedShort()
            long r8 = (long) r8
            android.animation.ValueAnimator r7 = r7.setDuration(r8)
            r7 = r6
            r7.start()
            goto L_0x0080
        L_0x00c8:
            org.osmdroid.views.MapController$ZoomAnimationListener r7 = new org.osmdroid.views.MapController$ZoomAnimationListener
            r17 = r7
            r7 = r17
            r8 = r17
            r9 = r1
            r8.<init>(r9)
            r5 = r7
            android.view.animation.ScaleAnimation r7 = new android.view.animation.ScaleAnimation
            r17 = r7
            r7 = r17
            r8 = r17
            r9 = 1065353216(0x3f800000, float:1.0)
            r10 = 1073741824(0x40000000, float:2.0)
            r11 = 1065353216(0x3f800000, float:1.0)
            r12 = 1073741824(0x40000000, float:2.0)
            r13 = 1
            r14 = 1056964608(0x3f000000, float:0.5)
            r15 = 1
            r16 = 1056964608(0x3f000000, float:0.5)
            r8.<init>(r9, r10, r11, r12, r13, r14, r15, r16)
            r6 = r7
            r7 = r6
            org.osmdroid.config.IConfigurationProvider r8 = org.osmdroid.config.Configuration.getInstance()
            int r8 = r8.getAnimationSpeedShort()
            long r8 = (long) r8
            r7.setDuration(r8)
            r7 = r6
            r8 = r5
            r7.setAnimationListener(r8)
            r7 = r1
            org.osmdroid.views.MapView r7 = r7.mMapView
            r8 = r6
            r7.startAnimation(r8)
            goto L_0x0080
        L_0x010a:
            r7 = 0
            r1 = r7
            goto L_0x0053
        */
        throw new UnsupportedOperationException("Method not decompiled: org.osmdroid.views.MapController.zoomInFixing(int, int, java.lang.Long):boolean");
    }

    public boolean zoomInFixing(int xPixel, int yPixel) {
        return zoomInFixing(xPixel, yPixel, (Long) null);
    }

    public boolean zoomOut(Long animationSpeed) {
        return zoomTo(this.mMapView.getZoomLevel(false) - 1.0d, animationSpeed);
    }

    public boolean zoomOut() {
        return zoomTo(this.mMapView.getZoomLevel(false) - 1.0d);
    }

    public boolean zoomOutFixing(int xPixel, int yPixel) {
        ZoomEvent zoomEvent;
        this.mMapView.mMultiTouchScalePoint.set((float) xPixel, (float) yPixel);
        if (!this.mMapView.canZoomOut()) {
            return false;
        }
        if (this.mMapView.mListener != null) {
            new ZoomEvent(this.mMapView, this.mMapView.getZoomLevelDouble() - 1.0d);
            boolean onZoom = this.mMapView.mListener.onZoom(zoomEvent);
        }
        if (this.mMapView.mIsAnimating.getAndSet(true)) {
            return false;
        }
        this.mMapView.mTargetZoomLevel.set(Double.valueOf(this.mMapView.getZoomLevel(false) - 1.0d));
        if (Build.VERSION.SDK_INT >= 11) {
            this.mCurrentAnimator = this.mZoomOutAnimation;
            this.mZoomOutAnimation.start();
        } else {
            this.mMapView.startAnimation(this.mZoomOutAnimationOld);
        }
        return true;
    }

    public boolean zoomTo(int zoomLevel) {
        return zoomToFixing(zoomLevel, this.mMapView.getWidth() / 2, this.mMapView.getHeight() / 2);
    }

    public boolean zoomTo(int zoomLevel, Long animationSpeed) {
        return zoomToFixing(zoomLevel, this.mMapView.getWidth() / 2, this.mMapView.getHeight() / 2, animationSpeed);
    }

    public boolean zoomToFixing(int zoomLevel, int xPixel, int yPixel, Long zoomAnimationSpeed) {
        return zoomToFixing((double) zoomLevel, xPixel, yPixel, zoomAnimationSpeed);
    }

    public boolean zoomTo(double pZoomLevel, Long animationSpeed) {
        return zoomToFixing(pZoomLevel, this.mMapView.getWidth() / 2, this.mMapView.getHeight() / 2, animationSpeed);
    }

    public boolean zoomTo(double pZoomLevel) {
        return zoomToFixing(pZoomLevel, this.mMapView.getWidth() / 2, this.mMapView.getHeight() / 2);
    }

    /* JADX WARNING: type inference failed for: r13v22, types: [android.animation.ValueAnimator$AnimatorUpdateListener] */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean zoomToFixing(double r24, int r26, int r27, java.lang.Long r28) {
        /*
            r23 = this;
            r0 = r23
            r1 = r24
            r3 = r26
            r4 = r27
            r5 = r28
            r12 = r1
            r14 = r0
            org.osmdroid.views.MapView r14 = r14.mMapView
            int r14 = r14.getMaxZoomLevel()
            double r14 = (double) r14
            int r12 = (r12 > r14 ? 1 : (r12 == r14 ? 0 : -1))
            if (r12 <= 0) goto L_0x009c
            r12 = r0
            org.osmdroid.views.MapView r12 = r12.mMapView
            int r12 = r12.getMaxZoomLevel()
            double r12 = (double) r12
        L_0x001f:
            r1 = r12
            r12 = r1
            r14 = r0
            org.osmdroid.views.MapView r14 = r14.mMapView
            int r14 = r14.getMinZoomLevel()
            double r14 = (double) r14
            int r12 = (r12 > r14 ? 1 : (r12 == r14 ? 0 : -1))
            if (r12 >= 0) goto L_0x009e
            r12 = r0
            org.osmdroid.views.MapView r12 = r12.mMapView
            int r12 = r12.getMinZoomLevel()
            double r12 = (double) r12
        L_0x0035:
            r1 = r12
            r12 = r0
            org.osmdroid.views.MapView r12 = r12.mMapView
            double r12 = r12.getZoomLevelDouble()
            r6 = r12
            r12 = r1
            r14 = r6
            int r12 = (r12 > r14 ? 1 : (r12 == r14 ? 0 : -1))
            if (r12 >= 0) goto L_0x004d
            r12 = r0
            org.osmdroid.views.MapView r12 = r12.mMapView
            boolean r12 = r12.canZoomOut()
            if (r12 != 0) goto L_0x005c
        L_0x004d:
            r12 = r1
            r14 = r6
            int r12 = (r12 > r14 ? 1 : (r12 == r14 ? 0 : -1))
            if (r12 <= 0) goto L_0x00a0
            r12 = r0
            org.osmdroid.views.MapView r12 = r12.mMapView
            boolean r12 = r12.canZoomIn()
            if (r12 == 0) goto L_0x00a0
        L_0x005c:
            r12 = 1
        L_0x005d:
            r8 = r12
            r12 = r0
            org.osmdroid.views.MapView r12 = r12.mMapView
            android.graphics.PointF r12 = r12.mMultiTouchScalePoint
            r13 = r3
            float r13 = (float) r13
            r14 = r4
            float r14 = (float) r14
            r12.set(r13, r14)
            r12 = r8
            if (r12 == 0) goto L_0x017c
            r12 = r0
            org.osmdroid.views.MapView r12 = r12.mMapView
            org.osmdroid.events.MapListener r12 = r12.mListener
            if (r12 == 0) goto L_0x008d
            r12 = r0
            org.osmdroid.views.MapView r12 = r12.mMapView
            org.osmdroid.events.MapListener r12 = r12.mListener
            org.osmdroid.events.ZoomEvent r13 = new org.osmdroid.events.ZoomEvent
            r22 = r13
            r13 = r22
            r14 = r22
            r15 = r0
            org.osmdroid.views.MapView r15 = r15.mMapView
            r16 = r1
            r14.<init>(r15, r16)
            boolean r12 = r12.onZoom(r13)
        L_0x008d:
            r12 = r0
            org.osmdroid.views.MapView r12 = r12.mMapView
            java.util.concurrent.atomic.AtomicBoolean r12 = r12.mIsAnimating
            r13 = 1
            boolean r12 = r12.getAndSet(r13)
            if (r12 == 0) goto L_0x00a2
            r12 = 0
            r0 = r12
        L_0x009b:
            return r0
        L_0x009c:
            r12 = r1
            goto L_0x001f
        L_0x009e:
            r12 = r1
            goto L_0x0035
        L_0x00a0:
            r12 = 0
            goto L_0x005d
        L_0x00a2:
            r12 = r0
            org.osmdroid.views.MapView r12 = r12.mMapView
            java.util.concurrent.atomic.AtomicReference<java.lang.Double> r12 = r12.mTargetZoomLevel
            r13 = r1
            java.lang.Double r13 = java.lang.Double.valueOf(r13)
            r12.set(r13)
            r12 = 4611686018427387904(0x4000000000000000, double:2.0)
            r14 = r1
            r16 = r6
            double r14 = r14 - r16
            double r12 = java.lang.Math.pow(r12, r14)
            float r12 = (float) r12
            r9 = r12
            int r12 = android.os.Build.VERSION.SDK_INT
            r13 = 11
            if (r12 < r13) goto L_0x011d
            org.osmdroid.views.MapController$ZoomAnimatorListener r12 = new org.osmdroid.views.MapController$ZoomAnimatorListener
            r22 = r12
            r12 = r22
            r13 = r22
            r14 = r0
            r13.<init>(r14)
            r10 = r12
            r12 = 2
            float[] r12 = new float[r12]
            r22 = r12
            r12 = r22
            r13 = r22
            r14 = 0
            r15 = 1065353216(0x3f800000, float:1.0)
            r13[r14] = r15
            r22 = r12
            r12 = r22
            r13 = r22
            r14 = 1
            r15 = r9
            r13[r14] = r15
            android.animation.ValueAnimator r12 = android.animation.ValueAnimator.ofFloat(r12)
            r11 = r12
            r12 = r11
            r13 = r10
            r12.addListener(r13)
            r12 = r11
            r13 = r10
            r12.addUpdateListener(r13)
            r12 = r5
            if (r12 != 0) goto L_0x0112
            r12 = r11
            org.osmdroid.config.IConfigurationProvider r13 = org.osmdroid.config.Configuration.getInstance()
            int r13 = r13.getAnimationSpeedShort()
            long r13 = (long) r13
            android.animation.ValueAnimator r12 = r12.setDuration(r13)
        L_0x0107:
            r12 = r0
            r13 = r11
            r12.mCurrentAnimator = r13
            r12 = r11
            r12.start()
        L_0x010f:
            r12 = 1
            r0 = r12
            goto L_0x009b
        L_0x0112:
            r12 = r11
            r13 = r5
            long r13 = r13.longValue()
            android.animation.ValueAnimator r12 = r12.setDuration(r13)
            goto L_0x0107
        L_0x011d:
            r12 = r1
            r14 = r6
            int r12 = (r12 > r14 ? 1 : (r12 == r14 ? 0 : -1))
            if (r12 <= 0) goto L_0x0168
            r12 = r0
            org.osmdroid.views.MapView r12 = r12.mMapView
            r13 = r0
            android.view.animation.ScaleAnimation r13 = r13.mZoomInAnimationOld
            r12.startAnimation(r13)
        L_0x012c:
            android.view.animation.ScaleAnimation r12 = new android.view.animation.ScaleAnimation
            r22 = r12
            r12 = r22
            r13 = r22
            r14 = 1065353216(0x3f800000, float:1.0)
            r15 = r9
            r16 = 1065353216(0x3f800000, float:1.0)
            r17 = r9
            r18 = 1
            r19 = 1056964608(0x3f000000, float:0.5)
            r20 = 1
            r21 = 1056964608(0x3f000000, float:0.5)
            r13.<init>(r14, r15, r16, r17, r18, r19, r20, r21)
            r10 = r12
            r12 = r5
            if (r12 != 0) goto L_0x0172
            r12 = r10
            org.osmdroid.config.IConfigurationProvider r13 = org.osmdroid.config.Configuration.getInstance()
            int r13 = r13.getAnimationSpeedShort()
            long r13 = (long) r13
            r12.setDuration(r13)
        L_0x0157:
            r12 = r10
            org.osmdroid.views.MapController$ZoomAnimationListener r13 = new org.osmdroid.views.MapController$ZoomAnimationListener
            r22 = r13
            r13 = r22
            r14 = r22
            r15 = r0
            r14.<init>(r15)
            r12.setAnimationListener(r13)
            goto L_0x010f
        L_0x0168:
            r12 = r0
            org.osmdroid.views.MapView r12 = r12.mMapView
            r13 = r0
            android.view.animation.ScaleAnimation r13 = r13.mZoomOutAnimationOld
            r12.startAnimation(r13)
            goto L_0x012c
        L_0x0172:
            r12 = r10
            r13 = r5
            long r13 = r13.longValue()
            r12.setDuration(r13)
            goto L_0x0157
        L_0x017c:
            r12 = 0
            r0 = r12
            goto L_0x009b
        */
        throw new UnsupportedOperationException("Method not decompiled: org.osmdroid.views.MapController.zoomToFixing(double, int, int, java.lang.Long):boolean");
    }

    public boolean zoomToFixing(double zoomLevel, int xPixel, int yPixel) {
        return zoomToFixing(zoomLevel, xPixel, yPixel, (Long) null);
    }

    public boolean zoomToFixing(int zoomLevel, int xPixel, int yPixel) {
        return zoomToFixing(zoomLevel, xPixel, yPixel, (Long) null);
    }

    /* access modifiers changed from: protected */
    public void onAnimationStart() {
        this.mMapView.mIsAnimating.set(true);
    }

    /* access modifiers changed from: protected */
    public void onAnimationEnd() {
        Rect screenRect = this.mMapView.getProjection().getScreenRect();
        Point p = this.mMapView.getProjection().unrotateAndScalePoint(screenRect.centerX(), screenRect.centerY(), (Point) null);
        Point p2 = this.mMapView.getProjection().toMercatorPixels(p.x, p.y, p);
        p2.offset((-this.mMapView.getWidth()) / 2, (-this.mMapView.getHeight()) / 2);
        this.mMapView.mIsAnimating.set(false);
        this.mMapView.scrollTo(p2.x, p2.y);
        double zoom = setZoom(this.mMapView.mTargetZoomLevel.get().doubleValue());
        this.mMapView.mMultiTouchScale = 1.0f;
        if (Build.VERSION.SDK_INT >= 11) {
            this.mCurrentAnimator = null;
        }
        if (Build.VERSION.SDK_INT <= 10) {
            this.mMapView.clearAnimation();
            this.mZoomInAnimationOld.reset();
            this.mZoomOutAnimationOld.reset();
        }
    }

    @TargetApi(11)
    private static class ZoomAnimatorListener implements Animator.AnimatorListener, ValueAnimator.AnimatorUpdateListener {
        private MapController mMapController;

        public ZoomAnimatorListener(MapController mapController) {
            this.mMapController = mapController;
        }

        public void onAnimationStart(Animator animator) {
            Animator animator2 = animator;
            this.mMapController.onAnimationStart();
        }

        public void onAnimationEnd(Animator animator) {
            Animator animator2 = animator;
            this.mMapController.onAnimationEnd();
        }

        public void onAnimationCancel(Animator animator) {
        }

        public void onAnimationRepeat(Animator animator) {
        }

        public void onAnimationUpdate(ValueAnimator valueAnimator) {
            this.mMapController.mMapView.mMultiTouchScale = ((Float) valueAnimator.getAnimatedValue()).floatValue();
            this.mMapController.mMapView.invalidate();
        }
    }

    protected static class ZoomAnimationListener implements Animation.AnimationListener {
        private MapController mMapController;

        public ZoomAnimationListener(MapController mapController) {
            this.mMapController = mapController;
        }

        public void onAnimationStart(Animation animation) {
            Animation animation2 = animation;
            this.mMapController.onAnimationStart();
        }

        public void onAnimationEnd(Animation animation) {
            Animation animation2 = animation;
            this.mMapController.onAnimationEnd();
        }

        public void onAnimationRepeat(Animation animation) {
        }
    }

    private class ReplayController {
        private LinkedList<ReplayClass> mReplayList;
        final /* synthetic */ MapController this$0;

        private ReplayController(MapController mapController) {
            LinkedList<ReplayClass> linkedList;
            this.this$0 = mapController;
            new LinkedList<>();
            this.mReplayList = linkedList;
        }

        /* JADX INFO: this call moved to the top of the method (can break code semantics) */
        /* synthetic */ ReplayController(MapController x0, C15961 r7) {
            this(x0);
            C15961 r2 = r7;
        }

        public void animateTo(IGeoPoint geoPoint) {
            Object obj;
            new ReplayClass(this, ReplayType.AnimateToGeoPoint, (Point) null, geoPoint);
            boolean add = this.mReplayList.add(obj);
        }

        public void animateTo(int x, int y) {
            Object obj;
            Point point;
            new Point(x, y);
            new ReplayClass(this, ReplayType.AnimateToPoint, point, (IGeoPoint) null);
            boolean add = this.mReplayList.add(obj);
        }

        public void setCenter(IGeoPoint geoPoint) {
            Object obj;
            new ReplayClass(this, ReplayType.SetCenterPoint, (Point) null, geoPoint);
            boolean add = this.mReplayList.add(obj);
        }

        public void zoomToSpan(int x, int y) {
            Object obj;
            Point point;
            new Point(x, y);
            new ReplayClass(this, ReplayType.ZoomToSpanPoint, point, (IGeoPoint) null);
            boolean add = this.mReplayList.add(obj);
        }

        public void zoomToSpan(double x, double y) {
            Object obj;
            Point point;
            new Point((int) (x * 1000000.0d), (int) (y * 1000000.0d));
            new ReplayClass(this, ReplayType.ZoomToSpanPoint, point, (IGeoPoint) null);
            boolean add = this.mReplayList.add(obj);
        }

        public void replayCalls() {
            Iterator it = this.mReplayList.iterator();
            while (it.hasNext()) {
                ReplayClass replay = (ReplayClass) it.next();
                switch (replay.mReplayType) {
                    case AnimateToGeoPoint:
                        if (replay.mGeoPoint == null) {
                            break;
                        } else {
                            this.this$0.animateTo(replay.mGeoPoint);
                            break;
                        }
                    case AnimateToPoint:
                        if (replay.mPoint == null) {
                            break;
                        } else {
                            this.this$0.animateTo(replay.mPoint.x, replay.mPoint.y);
                            break;
                        }
                    case SetCenterPoint:
                        if (replay.mGeoPoint == null) {
                            break;
                        } else {
                            this.this$0.setCenter(replay.mGeoPoint);
                            break;
                        }
                    case ZoomToSpanPoint:
                        if (replay.mPoint == null) {
                            break;
                        } else {
                            this.this$0.zoomToSpan(replay.mPoint.x, replay.mPoint.y);
                            break;
                        }
                }
            }
            this.mReplayList.clear();
        }

        private class ReplayClass {
            /* access modifiers changed from: private */
            public IGeoPoint mGeoPoint;
            /* access modifiers changed from: private */
            public Point mPoint;
            /* access modifiers changed from: private */
            public ReplayType mReplayType;
            final /* synthetic */ ReplayController this$1;

            public ReplayClass(ReplayController replayController, ReplayType mReplayType2, Point mPoint2, IGeoPoint mGeoPoint2) {
                this.this$1 = replayController;
                this.mReplayType = mReplayType2;
                this.mPoint = mPoint2;
                this.mGeoPoint = mGeoPoint2;
            }
        }
    }
}
