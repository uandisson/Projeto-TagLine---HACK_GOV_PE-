package org.osmdroid.views.drawing;

import android.graphics.BitmapShader;
import android.graphics.Matrix;
import android.graphics.Point;
import org.osmdroid.views.Projection;

public class OsmBitmapShader extends BitmapShader {
    private static final Point sPoint;
    private int mBitmapHeight;
    private int mBitmapWidth;
    private final Matrix mMatrix;

    static {
        Point point;
        new Point();
        sPoint = point;
    }

    /* JADX WARNING: Illegal instructions before constructor call */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public OsmBitmapShader(android.graphics.Bitmap r10, android.graphics.Shader.TileMode r11, android.graphics.Shader.TileMode r12) {
        /*
            r9 = this;
            r0 = r9
            r1 = r10
            r2 = r11
            r3 = r12
            r4 = r0
            r5 = r1
            r6 = r2
            r7 = r3
            r4.<init>(r5, r6, r7)
            r4 = r0
            android.graphics.Matrix r5 = new android.graphics.Matrix
            r8 = r5
            r5 = r8
            r6 = r8
            r6.<init>()
            r4.mMatrix = r5
            r4 = r0
            r5 = r1
            int r5 = r5.getWidth()
            r4.mBitmapWidth = r5
            r4 = r0
            r5 = r1
            int r5 = r5.getHeight()
            r4.mBitmapHeight = r5
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.osmdroid.views.drawing.OsmBitmapShader.<init>(android.graphics.Bitmap, android.graphics.Shader$TileMode, android.graphics.Shader$TileMode):void");
    }

    public void onDrawCycle(Projection projection) {
        Point mercatorPixels = projection.toMercatorPixels(0, 0, sPoint);
        this.mMatrix.setTranslate((float) ((-sPoint.x) % this.mBitmapWidth), (float) ((-sPoint.y) % this.mBitmapHeight));
        setLocalMatrix(this.mMatrix);
    }
}
