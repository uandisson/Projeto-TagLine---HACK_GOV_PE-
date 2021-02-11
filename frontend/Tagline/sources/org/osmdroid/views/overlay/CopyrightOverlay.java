package org.osmdroid.views.overlay;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.DisplayMetrics;
import org.osmdroid.views.MapView;

public class CopyrightOverlay extends Overlay {
    protected boolean alignBottom = true;
    protected boolean alignRight = false;

    /* renamed from: dm */
    final DisplayMetrics f530dm;
    private Paint paint;
    int xOffset = 10;
    int yOffset = 10;

    public CopyrightOverlay(Context context) {
        Paint paint2;
        this.f530dm = context.getResources().getDisplayMetrics();
        new Paint();
        this.paint = paint2;
        this.paint.setAntiAlias(true);
        this.paint.setTextSize(this.f530dm.density * 12.0f);
    }

    public void setTextSize(int fontSize) {
        this.paint.setTextSize(this.f530dm.density * ((float) fontSize));
    }

    public void setTextColor(int color) {
        this.paint.setColor(color);
    }

    public void setAlignBottom(boolean alignBottom2) {
        boolean z = alignBottom2;
        this.alignBottom = z;
    }

    public void setAlignRight(boolean alignRight2) {
        boolean z = alignRight2;
        this.alignRight = z;
    }

    public void setOffset(int x, int y) {
        this.xOffset = x;
        this.yOffset = y;
    }

    public void draw(Canvas canvas, MapView mapView, boolean shadow) {
        float x;
        float y;
        Canvas canvas2 = canvas;
        MapView map = mapView;
        if (!shadow && !map.isAnimating() && map.getTileProvider().getTileSource().getCopyrightNotice() != null && map.getTileProvider().getTileSource().getCopyrightNotice().length() != 0) {
            int width = canvas2.getWidth();
            int height = canvas2.getHeight();
            if (this.alignRight) {
                x = (float) (width - this.xOffset);
                this.paint.setTextAlign(Paint.Align.RIGHT);
            } else {
                x = (float) this.xOffset;
                this.paint.setTextAlign(Paint.Align.LEFT);
            }
            if (this.alignBottom) {
                y = (float) (height - this.yOffset);
            } else {
                y = this.paint.getTextSize() + ((float) this.yOffset);
            }
            int save = canvas2.save();
            canvas2.concat(map.getProjection().getInvertedScaleRotateCanvasMatrix());
            canvas2.drawText(map.getTileProvider().getTileSource().getCopyrightNotice(), x, y, this.paint);
            canvas2.restore();
        }
    }
}
