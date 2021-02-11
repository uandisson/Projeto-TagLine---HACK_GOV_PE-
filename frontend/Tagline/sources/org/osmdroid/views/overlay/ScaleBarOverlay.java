package org.osmdroid.views.overlay;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import org.osmdroid.api.IGeoPoint;
import org.osmdroid.library.C1262R;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.util.constants.GeoConstants;
import org.osmdroid.views.MapView;
import org.osmdroid.views.Projection;

public class ScaleBarOverlay extends Overlay implements GeoConstants {
    private static final Rect sTextBoundsRect;
    private boolean adjustLength;
    protected boolean alignBottom = false;
    protected boolean alignRight = false;
    private Paint barPaint;
    protected final Path barPath;
    private Paint bgPaint;
    private boolean centred;
    private Context context;
    private double lastLatitude;
    private int lastZoomLevel;
    boolean latitudeBar = true;
    protected final Rect latitudeBarRect;
    boolean longitudeBar = false;
    protected final Rect longitudeBarRect;
    private MapView mMapView;
    private float maxLength;
    int minZoom = 0;
    public int screenHeight;
    public int screenWidth;
    private Paint textPaint;
    UnitsOfMeasure unitsOfMeasure = UnitsOfMeasure.metric;
    int xOffset = 10;
    public float xdpi;
    int yOffset = 10;
    public float ydpi;

    public enum UnitsOfMeasure {
    }

    static {
        Rect rect;
        new Rect();
        sTextBoundsRect = rect;
    }

    public ScaleBarOverlay(MapView mapView) {
        Path path;
        Rect rect;
        Rect rect2;
        Paint paint;
        Paint paint2;
        MapView mapView2 = mapView;
        new Path();
        this.barPath = path;
        new Rect();
        this.latitudeBarRect = rect;
        new Rect();
        this.longitudeBarRect = rect2;
        this.lastZoomLevel = -1;
        this.lastLatitude = 0.0d;
        this.centred = false;
        this.adjustLength = false;
        this.mMapView = mapView2;
        this.context = mapView2.getContext();
        DisplayMetrics dm = this.context.getResources().getDisplayMetrics();
        new Paint();
        this.barPaint = paint;
        this.barPaint.setColor(-16777216);
        this.barPaint.setAntiAlias(true);
        this.barPaint.setStyle(Paint.Style.STROKE);
        this.barPaint.setAlpha(255);
        this.barPaint.setStrokeWidth(2.0f * dm.density);
        this.bgPaint = null;
        new Paint();
        this.textPaint = paint2;
        this.textPaint.setColor(-16777216);
        this.textPaint.setAntiAlias(true);
        this.textPaint.setStyle(Paint.Style.FILL);
        this.textPaint.setAlpha(255);
        this.textPaint.setTextSize(10.0f * dm.density);
        this.xdpi = dm.xdpi;
        this.ydpi = dm.ydpi;
        this.screenWidth = dm.widthPixels;
        this.screenHeight = dm.heightPixels;
        String manufacturer = null;
        try {
            manufacturer = (String) Build.class.getField("MANUFACTURER").get((Object) null);
        } catch (Exception e) {
            Exception exc = e;
        }
        if (!"motorola".equals(manufacturer) || !"DROIDX".equals(Build.MODEL)) {
            if ("motorola".equals(manufacturer) && "Droid".equals(Build.MODEL)) {
                this.xdpi = 264.0f;
                this.ydpi = 264.0f;
            }
        } else if (((WindowManager) this.context.getSystemService("window")).getDefaultDisplay().getOrientation() > 0) {
            this.xdpi = (float) (((double) this.screenWidth) / 3.75d);
            this.ydpi = (float) (((double) this.screenHeight) / 2.1d);
        } else {
            this.xdpi = (float) (((double) this.screenWidth) / 2.1d);
            this.ydpi = (float) (((double) this.screenHeight) / 3.75d);
        }
        this.maxLength = 2.54f;
    }

    public void setMinZoom(int zoom) {
        int i = zoom;
        this.minZoom = i;
    }

    public void setScaleBarOffset(int x, int y) {
        this.xOffset = x;
        this.yOffset = y;
    }

    public void setLineWidth(float width) {
        this.barPaint.setStrokeWidth(width);
    }

    public void setTextSize(float size) {
        this.textPaint.setTextSize(size);
    }

    public void setUnitsOfMeasure(UnitsOfMeasure unitsOfMeasure2) {
        this.unitsOfMeasure = unitsOfMeasure2;
        this.lastZoomLevel = -1;
    }

    public UnitsOfMeasure getUnitsOfMeasure() {
        return this.unitsOfMeasure;
    }

    public void drawLatitudeScale(boolean latitude) {
        this.latitudeBar = latitude;
        this.lastZoomLevel = -1;
    }

    public void drawLongitudeScale(boolean longitude) {
        this.longitudeBar = longitude;
        this.lastZoomLevel = -1;
    }

    public void setCentred(boolean z) {
        boolean centred2 = z;
        this.centred = centred2;
        this.alignBottom = !centred2;
        this.alignRight = !centred2;
        this.lastZoomLevel = -1;
    }

    public void setAlignBottom(boolean alignBottom2) {
        this.centred = false;
        this.alignBottom = alignBottom2;
        this.lastZoomLevel = -1;
    }

    public void setAlignRight(boolean alignRight2) {
        this.centred = false;
        this.alignRight = alignRight2;
        this.lastZoomLevel = -1;
    }

    public Paint getBarPaint() {
        return this.barPaint;
    }

    public void setBarPaint(Paint paint) {
        Throwable th;
        Paint pBarPaint = paint;
        if (pBarPaint == null) {
            Throwable th2 = th;
            new IllegalArgumentException("pBarPaint argument cannot be null");
            throw th2;
        }
        this.barPaint = pBarPaint;
        this.lastZoomLevel = -1;
    }

    public Paint getTextPaint() {
        return this.textPaint;
    }

    public void setTextPaint(Paint paint) {
        Throwable th;
        Paint pTextPaint = paint;
        if (pTextPaint == null) {
            Throwable th2 = th;
            new IllegalArgumentException("pTextPaint argument cannot be null");
            throw th2;
        }
        this.textPaint = pTextPaint;
        this.lastZoomLevel = -1;
    }

    public void setBackgroundPaint(Paint pBgPaint) {
        this.bgPaint = pBgPaint;
        this.lastZoomLevel = -1;
    }

    public void setEnableAdjustLength(boolean adjustLength2) {
        this.adjustLength = adjustLength2;
        this.lastZoomLevel = -1;
    }

    public void setMaxLength(float pMaxLengthInCm) {
        this.maxLength = pMaxLengthInCm;
        this.lastZoomLevel = -1;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:18:0x00ac, code lost:
        if (r10 == false) goto L_0x00c6;
     */
    /* JADX WARNING: Removed duplicated region for block: B:15:0x0094  */
    /* JADX WARNING: Removed duplicated region for block: B:22:0x00d3  */
    /* JADX WARNING: Removed duplicated region for block: B:25:0x00de  */
    /* JADX WARNING: Removed duplicated region for block: B:47:0x0178  */
    /* JADX WARNING: Removed duplicated region for block: B:51:0x01f5  */
    /* JADX WARNING: Removed duplicated region for block: B:54:0x0202  */
    /* JADX WARNING: Removed duplicated region for block: B:57:0x0213  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void draw(android.graphics.Canvas r22, org.osmdroid.views.MapView r23, boolean r24) {
        /*
            r21 = this;
            r2 = r21
            r3 = r22
            r4 = r23
            r5 = r24
            r15 = r5
            if (r15 == 0) goto L_0x000c
        L_0x000b:
            return
        L_0x000c:
            r15 = r4
            boolean r15 = r15.isAnimating()
            if (r15 == 0) goto L_0x0014
            goto L_0x000b
        L_0x0014:
            r15 = r4
            int r15 = r15.getZoomLevel()
            r6 = r15
            r15 = r6
            r16 = r2
            r0 = r16
            int r0 = r0.minZoom
            r16 = r0
            r0 = r16
            if (r15 < r0) goto L_0x020e
            r15 = r4
            org.osmdroid.views.Projection r15 = r15.getProjection()
            r7 = r15
            r15 = r7
            if (r15 != 0) goto L_0x0031
            goto L_0x000b
        L_0x0031:
            r15 = r4
            int r15 = r15.getWidth()
            r8 = r15
            r15 = r4
            int r15 = r15.getHeight()
            r9 = r15
            r15 = r9
            r16 = r2
            r0 = r16
            int r0 = r0.screenHeight
            r16 = r0
            r0 = r16
            if (r15 != r0) goto L_0x0057
            r15 = r8
            r16 = r2
            r0 = r16
            int r0 = r0.screenWidth
            r16 = r0
            r0 = r16
            if (r15 == r0) goto L_0x0210
        L_0x0057:
            r15 = 1
        L_0x0058:
            r10 = r15
            r15 = r2
            r16 = r9
            r0 = r16
            r15.screenHeight = r0
            r15 = r2
            r16 = r8
            r0 = r16
            r15.screenWidth = r0
            r15 = r7
            r16 = r2
            r0 = r16
            int r0 = r0.screenWidth
            r16 = r0
            r17 = 2
            int r16 = r16 / 2
            r17 = r2
            r0 = r17
            int r0 = r0.screenHeight
            r17 = r0
            r18 = 2
            int r17 = r17 / 2
            r18 = 0
            org.osmdroid.api.IGeoPoint r15 = r15.fromPixels(r16, r17, r18)
            r11 = r15
            r15 = r6
            r16 = r2
            r0 = r16
            int r0 = r0.lastZoomLevel
            r16 = r0
            r0 = r16
            if (r15 != r0) goto L_0x00ae
            r15 = r11
            double r15 = r15.getLatitude()
            int r15 = (int) r15
            r16 = r2
            r0 = r16
            double r0 = r0.lastLatitude
            r16 = r0
            r0 = r16
            int r0 = (int) r0
            r16 = r0
            r0 = r16
            if (r15 != r0) goto L_0x00ae
            r15 = r10
            if (r15 == 0) goto L_0x00c6
        L_0x00ae:
            r15 = r2
            r16 = r6
            r0 = r16
            r15.lastZoomLevel = r0
            r15 = r2
            r16 = r11
            double r16 = r16.getLatitude()
            r0 = r16
            r15.lastLatitude = r0
            r15 = r2
            r16 = r7
            r15.rebuildBarPath(r16)
        L_0x00c6:
            r15 = r2
            int r15 = r15.xOffset
            r12 = r15
            r15 = r2
            int r15 = r15.yOffset
            r13 = r15
            r15 = r2
            boolean r15 = r15.alignBottom
            if (r15 == 0) goto L_0x00d9
            r15 = r13
            r16 = -1
            int r15 = r15 * -1
            r13 = r15
        L_0x00d9:
            r15 = r2
            boolean r15 = r15.alignRight
            if (r15 == 0) goto L_0x00e4
            r15 = r12
            r16 = -1
            int r15 = r15 * -1
            r12 = r15
        L_0x00e4:
            r15 = r2
            boolean r15 = r15.centred
            if (r15 == 0) goto L_0x0107
            r15 = r2
            boolean r15 = r15.latitudeBar
            if (r15 == 0) goto L_0x0107
            r15 = r12
            r16 = r2
            r0 = r16
            android.graphics.Rect r0 = r0.latitudeBarRect
            r16 = r0
            int r16 = r16.width()
            r0 = r16
            int r0 = -r0
            r16 = r0
            r17 = 2
            int r16 = r16 / 2
            int r15 = r15 + r16
            r12 = r15
        L_0x0107:
            r15 = r2
            boolean r15 = r15.centred
            if (r15 == 0) goto L_0x012a
            r15 = r2
            boolean r15 = r15.longitudeBar
            if (r15 == 0) goto L_0x012a
            r15 = r13
            r16 = r2
            r0 = r16
            android.graphics.Rect r0 = r0.longitudeBarRect
            r16 = r0
            int r16 = r16.height()
            r0 = r16
            int r0 = -r0
            r16 = r0
            r17 = 2
            int r16 = r16 / 2
            int r15 = r15 + r16
            r13 = r15
        L_0x012a:
            r15 = r3
            int r15 = r15.save()
            r15 = r3
            r16 = r7
            android.graphics.Matrix r16 = r16.getInvertedScaleRotateCanvasMatrix()
            r15.concat(r16)
            r15 = r3
            r16 = r12
            r0 = r16
            float r0 = (float) r0
            r16 = r0
            r17 = r13
            r0 = r17
            float r0 = (float) r0
            r17 = r0
            r15.translate(r16, r17)
            r15 = r2
            boolean r15 = r15.latitudeBar
            if (r15 == 0) goto L_0x0169
            r15 = r2
            android.graphics.Paint r15 = r15.bgPaint
            if (r15 == 0) goto L_0x0169
            r15 = r3
            r16 = r2
            r0 = r16
            android.graphics.Rect r0 = r0.latitudeBarRect
            r16 = r0
            r17 = r2
            r0 = r17
            android.graphics.Paint r0 = r0.bgPaint
            r17 = r0
            r15.drawRect(r16, r17)
        L_0x0169:
            r15 = r2
            boolean r15 = r15.longitudeBar
            if (r15 == 0) goto L_0x01dc
            r15 = r2
            android.graphics.Paint r15 = r15.bgPaint
            if (r15 == 0) goto L_0x01dc
            r15 = r2
            boolean r15 = r15.latitudeBar
            if (r15 == 0) goto L_0x0213
            r15 = r2
            android.graphics.Rect r15 = r15.latitudeBarRect
            int r15 = r15.height()
        L_0x017f:
            r14 = r15
            r15 = r3
            r16 = r2
            r0 = r16
            android.graphics.Rect r0 = r0.longitudeBarRect
            r16 = r0
            r0 = r16
            int r0 = r0.left
            r16 = r0
            r0 = r16
            float r0 = (float) r0
            r16 = r0
            r17 = r2
            r0 = r17
            android.graphics.Rect r0 = r0.longitudeBarRect
            r17 = r0
            r0 = r17
            int r0 = r0.top
            r17 = r0
            r18 = r14
            int r17 = r17 + r18
            r0 = r17
            float r0 = (float) r0
            r17 = r0
            r18 = r2
            r0 = r18
            android.graphics.Rect r0 = r0.longitudeBarRect
            r18 = r0
            r0 = r18
            int r0 = r0.right
            r18 = r0
            r0 = r18
            float r0 = (float) r0
            r18 = r0
            r19 = r2
            r0 = r19
            android.graphics.Rect r0 = r0.longitudeBarRect
            r19 = r0
            r0 = r19
            int r0 = r0.bottom
            r19 = r0
            r0 = r19
            float r0 = (float) r0
            r19 = r0
            r20 = r2
            r0 = r20
            android.graphics.Paint r0 = r0.bgPaint
            r20 = r0
            r15.drawRect(r16, r17, r18, r19, r20)
        L_0x01dc:
            r15 = r3
            r16 = r2
            r0 = r16
            android.graphics.Path r0 = r0.barPath
            r16 = r0
            r17 = r2
            r0 = r17
            android.graphics.Paint r0 = r0.barPaint
            r17 = r0
            r15.drawPath(r16, r17)
            r15 = r2
            boolean r15 = r15.latitudeBar
            if (r15 == 0) goto L_0x01fd
            r15 = r2
            r16 = r3
            r17 = r7
            r15.drawLatitudeText(r16, r17)
        L_0x01fd:
            r15 = r2
            boolean r15 = r15.longitudeBar
            if (r15 == 0) goto L_0x020a
            r15 = r2
            r16 = r3
            r17 = r7
            r15.drawLongitudeText(r16, r17)
        L_0x020a:
            r15 = r3
            r15.restore()
        L_0x020e:
            goto L_0x000b
        L_0x0210:
            r15 = 0
            goto L_0x0058
        L_0x0213:
            r15 = 0
            goto L_0x017f
        */
        throw new UnsupportedOperationException("Method not decompiled: org.osmdroid.views.overlay.ScaleBarOverlay.draw(android.graphics.Canvas, org.osmdroid.views.MapView, boolean):void");
    }

    public void disableScaleBar() {
        setEnabled(false);
    }

    public void enableScaleBar() {
        setEnabled(true);
    }

    private void drawLatitudeText(Canvas canvas, Projection projection) {
        double d;
        float y;
        Canvas canvas2 = canvas;
        Projection projection2 = projection;
        int xdpcm = (int) (((double) this.xdpi) / 2.54d);
        int xLen = (int) (this.maxLength * ((float) xdpcm));
        IGeoPoint p1 = projection2.fromPixels((this.screenWidth / 2) - (xLen / 2), this.yOffset, (GeoPoint) null);
        int xMeters = ((GeoPoint) p1).distanceTo(projection2.fromPixels((this.screenWidth / 2) + (xLen / 2), this.yOffset, (GeoPoint) null));
        if (this.adjustLength) {
            d = adjustScaleBarLength((double) xMeters);
        } else {
            d = (double) xMeters;
        }
        double xMetersAdjusted = d;
        int xBarLengthPixels = (int) ((((double) xLen) * xMetersAdjusted) / ((double) xMeters));
        String xMsg = scaleBarLengthText((int) xMetersAdjusted);
        this.textPaint.getTextBounds(xMsg, 0, xMsg.length(), sTextBoundsRect);
        int xTextSpacing = (int) (((double) sTextBoundsRect.height()) / 5.0d);
        float x = (float) ((xBarLengthPixels / 2) - (sTextBoundsRect.width() / 2));
        if (this.alignRight) {
            x += (float) (this.screenWidth - xBarLengthPixels);
        }
        if (this.alignBottom) {
            y = (float) (this.screenHeight - (xTextSpacing * 2));
        } else {
            y = (float) (sTextBoundsRect.height() + xTextSpacing);
        }
        canvas2.drawText(xMsg, x, y, this.textPaint);
    }

    private void drawLongitudeText(Canvas canvas, Projection projection) {
        double d;
        float x;
        Canvas canvas2 = canvas;
        Projection projection2 = projection;
        int ydpcm = (int) (((double) this.ydpi) / 2.54d);
        int yLen = (int) (this.maxLength * ((float) ydpcm));
        IGeoPoint p1 = projection2.fromPixels(this.screenWidth / 2, (this.screenHeight / 2) - (yLen / 2), (GeoPoint) null);
        int yMeters = ((GeoPoint) p1).distanceTo(projection2.fromPixels(this.screenWidth / 2, (this.screenHeight / 2) + (yLen / 2), (GeoPoint) null));
        if (this.adjustLength) {
            d = adjustScaleBarLength((double) yMeters);
        } else {
            d = (double) yMeters;
        }
        double yMetersAdjusted = d;
        int yBarLengthPixels = (int) ((((double) yLen) * yMetersAdjusted) / ((double) yMeters));
        String yMsg = scaleBarLengthText((int) yMetersAdjusted);
        this.textPaint.getTextBounds(yMsg, 0, yMsg.length(), sTextBoundsRect);
        int yTextSpacing = (int) (((double) sTextBoundsRect.height()) / 5.0d);
        if (this.alignRight) {
            x = (float) (this.screenWidth - (yTextSpacing * 2));
        } else {
            x = (float) (sTextBoundsRect.height() + yTextSpacing);
        }
        float y = (float) ((yBarLengthPixels / 2) + (sTextBoundsRect.width() / 2));
        if (this.alignBottom) {
            y += (float) (this.screenHeight - yBarLengthPixels);
        }
        int save = canvas2.save();
        canvas2.rotate(-90.0f, x, y);
        canvas2.drawText(yMsg, x, y, this.textPaint);
        canvas2.restore();
    }

    /* access modifiers changed from: protected */
    public void rebuildBarPath(Projection projection) {
        double d;
        Rect rect;
        Rect rect2;
        Projection projection2 = projection;
        int xdpcm = (int) (((double) this.xdpi) / 2.54d);
        int ydpcm = (int) (((double) this.ydpi) / 2.54d);
        int xLen = (int) (this.maxLength * ((float) xdpcm));
        int yLen = (int) (this.maxLength * ((float) ydpcm));
        int xMeters = ((GeoPoint) projection2.fromPixels((this.screenWidth / 2) - (xLen / 2), this.yOffset, (GeoPoint) null)).distanceTo(projection2.fromPixels((this.screenWidth / 2) + (xLen / 2), this.yOffset, (GeoPoint) null));
        if (this.adjustLength) {
            d = adjustScaleBarLength((double) xMeters);
        } else {
            d = (double) xMeters;
        }
        double xMetersAdjusted = d;
        int xBarLengthPixels = (int) ((((double) xLen) * xMetersAdjusted) / ((double) xMeters));
        int yMeters = ((GeoPoint) projection2.fromPixels(this.screenWidth / 2, (this.screenHeight / 2) - (yLen / 2), (GeoPoint) null)).distanceTo(projection2.fromPixels(this.screenWidth / 2, (this.screenHeight / 2) + (yLen / 2), (GeoPoint) null));
        double yMetersAdjusted = this.adjustLength ? adjustScaleBarLength((double) yMeters) : (double) yMeters;
        int yBarLengthPixels = (int) ((((double) yLen) * yMetersAdjusted) / ((double) yMeters));
        String xMsg = scaleBarLengthText((int) xMetersAdjusted);
        new Rect();
        Rect xTextRect = rect;
        this.textPaint.getTextBounds(xMsg, 0, xMsg.length(), xTextRect);
        int xTextSpacing = (int) (((double) xTextRect.height()) / 5.0d);
        String yMsg = scaleBarLengthText((int) yMetersAdjusted);
        new Rect();
        Rect yTextRect = rect2;
        this.textPaint.getTextBounds(yMsg, 0, yMsg.length(), yTextRect);
        int yTextSpacing = (int) (((double) yTextRect.height()) / 5.0d);
        int xTextHeight = xTextRect.height();
        int yTextHeight = yTextRect.height();
        this.barPath.rewind();
        int barOriginX = 0;
        int barOriginY = 0;
        int barToX = xBarLengthPixels;
        int barToY = yBarLengthPixels;
        if (this.alignBottom) {
            xTextSpacing *= -1;
            xTextHeight *= -1;
            barOriginY = this.mMapView.getHeight();
            barToY = barOriginY - yBarLengthPixels;
        }
        if (this.alignRight) {
            yTextSpacing *= -1;
            yTextHeight *= -1;
            barOriginX = this.mMapView.getWidth();
            barToX = barOriginX - xBarLengthPixels;
        }
        if (this.latitudeBar) {
            this.barPath.moveTo((float) barToX, (float) (barOriginY + xTextHeight + (xTextSpacing * 2)));
            this.barPath.lineTo((float) barToX, (float) barOriginY);
            this.barPath.lineTo((float) barOriginX, (float) barOriginY);
            if (!this.longitudeBar) {
                this.barPath.lineTo((float) barOriginX, (float) (barOriginY + xTextHeight + (xTextSpacing * 2)));
            }
            this.latitudeBarRect.set(barOriginX, barOriginY, barToX, barOriginY + xTextHeight + (xTextSpacing * 2));
        }
        if (this.longitudeBar) {
            if (!this.latitudeBar) {
                this.barPath.moveTo((float) (barOriginX + yTextHeight + (yTextSpacing * 2)), (float) barOriginY);
                this.barPath.lineTo((float) barOriginX, (float) barOriginY);
            }
            this.barPath.lineTo((float) barOriginX, (float) barToY);
            this.barPath.lineTo((float) (barOriginX + yTextHeight + (yTextSpacing * 2)), (float) barToY);
            this.longitudeBarRect.set(barOriginX, barOriginY, barOriginX + yTextHeight + (yTextSpacing * 2), barToY);
        }
    }

    private double adjustScaleBarLength(double d) {
        double length;
        double length2 = d;
        long pow = 0;
        boolean feet = false;
        if (this.unitsOfMeasure == UnitsOfMeasure.imperial) {
            if (length2 >= 321.8688d) {
                length2 /= 1609.344d;
            } else {
                length2 *= 3.2808399d;
                feet = true;
            }
        } else if (this.unitsOfMeasure == UnitsOfMeasure.nautical) {
            if (length2 >= 370.4d) {
                length2 /= 1852.0d;
            } else {
                length2 *= 3.2808399d;
                feet = true;
            }
        }
        while (length2 >= 10.0d) {
            pow++;
            length2 /= 10.0d;
        }
        while (length2 < 1.0d && length2 > 0.0d) {
            pow--;
            length2 *= 10.0d;
        }
        if (length2 < 2.0d) {
            length = 1.0d;
        } else if (length2 < 5.0d) {
            length = 2.0d;
        } else {
            length = 5.0d;
        }
        if (feet) {
            length /= 3.2808399d;
        } else if (this.unitsOfMeasure == UnitsOfMeasure.imperial) {
            length *= 1609.344d;
        } else if (this.unitsOfMeasure == UnitsOfMeasure.nautical) {
            length *= 1852.0d;
        }
        return length * Math.pow(10.0d, (double) pow);
    }

    /* access modifiers changed from: protected */
    public String scaleBarLengthText(int i) {
        int meters = i;
        switch (this.unitsOfMeasure) {
            case imperial:
                if (((double) meters) >= 8046.72d) {
                    return this.context.getResources().getString(C1262R.string.format_distance_miles, new Object[]{Integer.valueOf((int) (((double) meters) / 1609.344d))});
                } else if (((double) meters) >= 321.8688d) {
                    return this.context.getResources().getString(C1262R.string.format_distance_miles, new Object[]{Double.valueOf(((double) ((int) (((double) meters) / 160.9344d))) / 10.0d)});
                } else {
                    return this.context.getResources().getString(C1262R.string.format_distance_feet, new Object[]{Integer.valueOf((int) (((double) meters) * 3.2808399d))});
                }
            case nautical:
                if (((double) meters) >= 9260.0d) {
                    return this.context.getResources().getString(C1262R.string.format_distance_nautical_miles, new Object[]{Integer.valueOf((int) (((double) meters) / 1852.0d))});
                } else if (((double) meters) >= 370.4d) {
                    return this.context.getResources().getString(C1262R.string.format_distance_nautical_miles, new Object[]{Double.valueOf(((double) ((int) (((double) meters) / 185.2d))) / 10.0d)});
                } else {
                    return this.context.getResources().getString(C1262R.string.format_distance_feet, new Object[]{Integer.valueOf((int) (((double) meters) * 3.2808399d))});
                }
            default:
                if (meters >= 5000) {
                    return this.context.getResources().getString(C1262R.string.format_distance_kilometers, new Object[]{Integer.valueOf(meters / 1000)});
                } else if (meters >= 200) {
                    return this.context.getResources().getString(C1262R.string.format_distance_kilometers, new Object[]{Double.valueOf(((double) ((int) (((double) meters) / 100.0d))) / 10.0d)});
                } else {
                    return this.context.getResources().getString(C1262R.string.format_distance_meters, new Object[]{Integer.valueOf(meters)});
                }
        }
    }

    public void onDetach(MapView mapView) {
        MapView mapView2 = mapView;
        this.context = null;
        this.mMapView = null;
        this.barPaint = null;
        this.bgPaint = null;
        this.textPaint = null;
    }
}
