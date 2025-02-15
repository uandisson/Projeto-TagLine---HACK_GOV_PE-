package com.google.appinventor.components.runtime;

import android.graphics.Canvas;
import android.graphics.Paint;
import com.google.appinventor.components.annotations.DesignerComponent;
import com.google.appinventor.components.annotations.DesignerProperty;
import com.google.appinventor.components.annotations.SimpleFunction;
import com.google.appinventor.components.annotations.SimpleObject;
import com.google.appinventor.components.annotations.SimpleProperty;
import com.google.appinventor.components.common.ComponentCategory;
import com.google.appinventor.components.runtime.util.PaintUtil;

@SimpleObject
@DesignerComponent(category = ComponentCategory.ANIMATION, description = "<p>A round 'sprite' that can be placed on a <code>Canvas</code>, where it can react to touches and drags, interact with other sprites (<code>ImageSprite</code>s and other <code>Ball</code>s) and the edge of the Canvas, and move according to its property values.</p><p>For example, to have a <code>Ball</code> move 4 pixels toward the top of a <code>Canvas</code> every 500 milliseconds (half second), you would set the <code>Speed</code> property to 4 [pixels], the <code>Interval</code> property to 500 [milliseconds], the <code>Heading</code> property to 90 [degrees], and the <code>Enabled</code> property to <code>True</code>.</p><p>The difference between a <code>Ball</code> and an <code>ImageSprite</code> is that the latter can get its appearance from an image file, while a <code>Ball</code>'s appearance can be changed only by varying its <code>PaintColor</code> and <code>Radius</code> properties.</p>", version = 6)
public final class Ball extends Sprite {
    private int B8WBXPBCF2jGfUDZZU2zV5EYdqbUBu0lAZ0THCEqYyuE8VACR9dY7rDnwBIqh64T;
    private int hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME;

    /* renamed from: hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME  reason: collision with other field name */
    private Paint f549hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public Ball(ComponentContainer componentContainer) {
        super(componentContainer);
        Paint paint;
        new Paint();
        this.f549hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME = paint;
        PaintColor(-16777216);
        Radius(5);
    }

    /* access modifiers changed from: protected */
    public final void onDraw(Canvas canvas) {
        Canvas canvas2 = canvas;
        if (this.visible) {
            float deviceDensity = (float) (this.xLeft * ((double) this.form.deviceDensity()));
            float deviceDensity2 = (float) (this.yTop * ((double) this.form.deviceDensity()));
            float deviceDensity3 = ((float) this.hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME) * this.form.deviceDensity();
            canvas2.drawCircle(deviceDensity + deviceDensity3, deviceDensity2 + deviceDensity3, deviceDensity3, this.f549hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME);
        }
    }

    public final int Height() {
        return 2 * this.hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME;
    }

    public final void Height(int i) {
    }

    public final void HeightPercent(int i) {
    }

    public final int Width() {
        return 2 * this.hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME;
    }

    public final void Width(int i) {
    }

    public final void WidthPercent(int i) {
    }

    public final boolean containsPoint(double d, double d2) {
        double d3 = d;
        double d4 = d2;
        return ((d3 - this.xCenter) * (d3 - this.xCenter)) + ((d4 - this.yCenter) * (d4 - this.yCenter)) <= ((double) (this.hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME * this.hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME));
    }

    @DesignerProperty(defaultValue = "5", editorType = "non_negative_integer")
    @SimpleProperty(description = "The distance from the edge of the Ball to its center.")
    public final void Radius(int i) {
        int i2 = i;
        int i3 = i2 - this.hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME;
        if (this.originAtCenter) {
            this.xLeft -= (double) i3;
            this.yTop -= (double) i3;
        }
        this.hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME = i2;
        registerChange();
    }

    @SimpleProperty
    public final int Radius() {
        return this.hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME;
    }

    @SimpleProperty(description = "The color of the Ball.")
    public final int PaintColor() {
        return this.B8WBXPBCF2jGfUDZZU2zV5EYdqbUBu0lAZ0THCEqYyuE8VACR9dY7rDnwBIqh64T;
    }

    @DesignerProperty(defaultValue = "&HFF000000", editorType = "color")
    @SimpleProperty
    public final void PaintColor(int i) {
        int i2 = i;
        this.B8WBXPBCF2jGfUDZZU2zV5EYdqbUBu0lAZ0THCEqYyuE8VACR9dY7rDnwBIqh64T = i2;
        if (i2 != 0) {
            PaintUtil.changePaint(this.f549hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME, i2);
        } else {
            PaintUtil.changePaint(this.f549hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME, -16777216);
        }
        registerChange();
    }

    @DesignerProperty(defaultValue = "False", editorType = "boolean")
    @SimpleProperty(description = "Whether the x- and y-coordinates should represent the center of the Ball (<code>true</code>) or its left and top edges (<code>false</code>).", userVisible = false)
    public final void OriginAtCenter(boolean z) {
        super.OriginAtCenter(z);
    }

    @SimpleProperty(description = "The horizontal coordinate of the Ball, increasing as the Ball moves right. If the property OriginAtCenter is true, the coodinate is for the center of the Ball; otherwise, it is for the leftmost point of the Ball.")
    /* renamed from: X */
    public final double mo10194X() {
        return super.mo10194X();
    }

    @SimpleProperty(description = "The vertical coordinate of the Ball, increasing as the Ball moves down. If the property OriginAtCenter is true, the coodinate is for the center of the Ball; otherwise, it is for the uppermost point of the Ball.")
    /* renamed from: Y */
    public final double mo10195Y() {
        return super.mo10195Y();
    }

    @SimpleFunction(description = "Sets the x and y coordinates of the Ball. If CenterAtOrigin is true, the center of the Ball will be placed here. Otherwise, the top left edge of the Ball will be placed at the specified coordinates.")
    public final void MoveTo(double d, double d2) {
        super.MoveTo(d, d2);
    }
}
