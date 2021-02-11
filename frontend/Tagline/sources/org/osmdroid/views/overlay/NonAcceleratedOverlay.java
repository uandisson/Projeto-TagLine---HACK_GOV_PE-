package org.osmdroid.views.overlay;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.os.Build;
import android.util.Log;
import org.osmdroid.api.IMapView;
import org.osmdroid.views.MapView;

public abstract class NonAcceleratedOverlay extends Overlay {
    private Bitmap mBackingBitmap;
    private Canvas mBackingCanvas;
    private final Matrix mBackingMatrix;
    private final Matrix mCanvasIdentityMatrix;

    /* access modifiers changed from: protected */
    public abstract void onDraw(Canvas canvas, MapView mapView, boolean z);

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    @Deprecated
    public NonAcceleratedOverlay(Context ctx) {
        super(ctx);
        Matrix matrix;
        Matrix matrix2;
        new Matrix();
        this.mBackingMatrix = matrix;
        new Matrix();
        this.mCanvasIdentityMatrix = matrix2;
    }

    public NonAcceleratedOverlay() {
        Matrix matrix;
        Matrix matrix2;
        new Matrix();
        this.mBackingMatrix = matrix;
        new Matrix();
        this.mCanvasIdentityMatrix = matrix2;
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas c, Canvas canvas, MapView osmv, boolean shadow) {
        Canvas canvas2 = canvas;
        onDraw(c, osmv, shadow);
    }

    public boolean isUsingBackingBitmap() {
        return true;
    }

    public void onDetach(MapView mapView) {
        this.mBackingBitmap = null;
        this.mBackingCanvas = null;
        super.onDetach(mapView);
    }

    public final void draw(Canvas canvas, MapView mapView, boolean z) {
        Canvas canvas2;
        Canvas c = canvas;
        MapView osmv = mapView;
        boolean shadow = z;
        boolean atLeastHoneycomb = Build.VERSION.SDK_INT >= 11;
        if (!isUsingBackingBitmap() || !atLeastHoneycomb || !c.isHardwareAccelerated()) {
            onDraw(c, c, osmv, shadow);
        } else if (!shadow && c.getWidth() != 0 && c.getHeight() != 0) {
            if (!(this.mBackingBitmap != null && this.mBackingBitmap.getWidth() == c.getWidth() && this.mBackingBitmap.getHeight() == c.getHeight())) {
                this.mBackingBitmap = null;
                this.mBackingCanvas = null;
                try {
                    this.mBackingBitmap = Bitmap.createBitmap(c.getWidth(), c.getHeight(), Bitmap.Config.ARGB_8888);
                    new Canvas(this.mBackingBitmap);
                    this.mBackingCanvas = canvas2;
                } catch (OutOfMemoryError e) {
                    OutOfMemoryError outOfMemoryError = e;
                    int e2 = Log.e(IMapView.LOGTAG, "OutOfMemoryError creating backing bitmap in NonAcceleratedOverlay.");
                    System.gc();
                    return;
                }
            }
            this.mBackingCanvas.drawColor(0, PorterDuff.Mode.CLEAR);
            c.getMatrix(this.mBackingMatrix);
            this.mBackingCanvas.setMatrix(this.mBackingMatrix);
            onDraw(this.mBackingCanvas, c, osmv, shadow);
            int save = c.save();
            c.getMatrix(this.mCanvasIdentityMatrix);
            boolean invert = this.mCanvasIdentityMatrix.invert(this.mCanvasIdentityMatrix);
            c.concat(this.mCanvasIdentityMatrix);
            c.drawBitmap(this.mBackingBitmap, 0.0f, 0.0f, (Paint) null);
            c.restore();
        }
    }
}
