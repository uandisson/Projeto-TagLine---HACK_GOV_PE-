package org.osmdroid.views.overlay.simplefastpoint;

import android.graphics.Color;
import android.graphics.Paint;
import android.support.p003v7.widget.helper.ItemTouchHelper;

public class SimpleFastPointOverlayOptions {
    protected RenderingAlgorithm mAlgorithm = RenderingAlgorithm.MAXIMUM_OPTIMIZATION;
    protected int mCellSize = 10;
    protected float mCircleRadius = 5.0f;
    protected boolean mClickable = true;
    protected LabelPolicy mLabelPolicy = LabelPolicy.ZOOM_THRESHOLD;
    protected int mMaxNShownLabels = ItemTouchHelper.Callback.DEFAULT_SWIPE_ANIMATION_DURATION;
    protected int mMinZoomShowLabels = 11;
    protected Paint mPointStyle;
    protected float mSelectedCircleRadius = 13.0f;
    protected Paint mSelectedPointStyle;
    protected Shape mSymbol = Shape.CIRCLE;
    protected Paint mTextStyle;

    public enum LabelPolicy {
    }

    public enum RenderingAlgorithm {
    }

    public enum Shape {
    }

    public SimpleFastPointOverlayOptions() {
        Paint paint;
        Paint paint2;
        Paint paint3;
        new Paint();
        this.mPointStyle = paint;
        this.mPointStyle.setStyle(Paint.Style.FILL);
        this.mPointStyle.setColor(Color.parseColor("#ff7700"));
        new Paint();
        this.mSelectedPointStyle = paint2;
        this.mSelectedPointStyle.setStrokeWidth(5.0f);
        this.mSelectedPointStyle.setStyle(Paint.Style.STROKE);
        this.mSelectedPointStyle.setColor(Color.parseColor("#ffff00"));
        new Paint();
        this.mTextStyle = paint3;
        this.mTextStyle.setStyle(Paint.Style.FILL);
        this.mTextStyle.setColor(Color.parseColor("#ffff00"));
        this.mTextStyle.setTextAlign(Paint.Align.CENTER);
        this.mTextStyle.setTextSize(24.0f);
    }

    public static SimpleFastPointOverlayOptions getDefaultStyle() {
        SimpleFastPointOverlayOptions simpleFastPointOverlayOptions;
        SimpleFastPointOverlayOptions simpleFastPointOverlayOptions2 = simpleFastPointOverlayOptions;
        new SimpleFastPointOverlayOptions();
        return simpleFastPointOverlayOptions2;
    }

    public SimpleFastPointOverlayOptions setPointStyle(Paint style) {
        this.mPointStyle = style;
        return this;
    }

    public SimpleFastPointOverlayOptions setSelectedPointStyle(Paint style) {
        this.mSelectedPointStyle = style;
        return this;
    }

    public SimpleFastPointOverlayOptions setRadius(float radius) {
        this.mCircleRadius = radius;
        return this;
    }

    public SimpleFastPointOverlayOptions setSelectedRadius(float radius) {
        this.mSelectedCircleRadius = radius;
        return this;
    }

    public SimpleFastPointOverlayOptions setIsClickable(boolean clickable) {
        this.mClickable = clickable;
        return this;
    }

    public SimpleFastPointOverlayOptions setCellSize(int cellSize) {
        this.mCellSize = cellSize;
        return this;
    }

    public SimpleFastPointOverlayOptions setAlgorithm(RenderingAlgorithm algorithm) {
        this.mAlgorithm = algorithm;
        return this;
    }

    public SimpleFastPointOverlayOptions setSymbol(Shape symbol) {
        this.mSymbol = symbol;
        return this;
    }

    public SimpleFastPointOverlayOptions setTextStyle(Paint textStyle) {
        this.mTextStyle = textStyle;
        return this;
    }

    public SimpleFastPointOverlayOptions setMinZoomShowLabels(int minZoomShowLabels) {
        this.mMinZoomShowLabels = minZoomShowLabels;
        return this;
    }

    public SimpleFastPointOverlayOptions setMaxNShownLabels(int maxNShownLabels) {
        this.mMaxNShownLabels = maxNShownLabels;
        return this;
    }

    public SimpleFastPointOverlayOptions setLabelPolicy(LabelPolicy labelPolicy) {
        this.mLabelPolicy = labelPolicy;
        return this;
    }
}
