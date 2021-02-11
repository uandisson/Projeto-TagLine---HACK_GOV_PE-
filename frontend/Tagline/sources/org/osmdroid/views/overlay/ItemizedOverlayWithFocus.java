package org.osmdroid.views.overlay;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.util.TypedValue;
import java.util.List;
import org.osmdroid.library.C1262R;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.ItemizedIconOverlay;
import org.osmdroid.views.overlay.OverlayItem;

public class ItemizedOverlayWithFocus<Item extends OverlayItem> extends ItemizedIconOverlay<Item> {
    private final int DEFAULTMARKER_BACKGROUNDCOLOR;
    private int DESCRIPTION_BOX_CORNERWIDTH;
    private int DESCRIPTION_BOX_PADDING;
    private int DESCRIPTION_LINE_HEIGHT;
    private int DESCRIPTION_MAXWIDTH;
    private int DESCRIPTION_TITLE_EXTRA_LINE_HEIGHT;
    private int FONT_SIZE_DP;
    private String UNKNOWN;
    private int fontSizePixels;
    private Context mContext;
    protected Paint mDescriptionPaint;
    protected boolean mFocusItemsOnTap;
    protected int mFocusedItemIndex;
    private final Point mFocusedScreenCoords;
    protected Paint mMarkerBackgroundPaint;
    protected int mMarkerFocusedBackgroundColor;
    protected Drawable mMarkerFocusedBase;
    private final Rect mRect;
    protected Paint mTitlePaint;

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public ItemizedOverlayWithFocus(Context pContext, List<Item> aList, ItemizedIconOverlay.OnItemGestureListener<Item> aOnItemTapListener) {
        this(aList, aOnItemTapListener, pContext);
    }

    /* JADX WARNING: Illegal instructions before constructor call */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public ItemizedOverlayWithFocus(java.util.List<Item> r12, org.osmdroid.views.overlay.ItemizedIconOverlay.OnItemGestureListener<Item> r13, android.content.Context r14) {
        /*
            r11 = this;
            r0 = r11
            r1 = r12
            r2 = r13
            r3 = r14
            r4 = r0
            r5 = r1
            r6 = r3
            android.content.res.Resources r6 = r6.getResources()
            int r7 = org.osmdroid.library.C1262R.C1263drawable.marker_default
            android.graphics.drawable.Drawable r6 = r6.getDrawable(r7)
            r7 = 0
            r8 = -2147483648(0xffffffff80000000, float:-0.0)
            r9 = r2
            r10 = r3
            r4.<init>(r5, r6, r7, r8, r9, r10)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.osmdroid.views.overlay.ItemizedOverlayWithFocus.<init>(java.util.List, org.osmdroid.views.overlay.ItemizedIconOverlay$OnItemGestureListener, android.content.Context):void");
    }

    /* JADX WARNING: Illegal instructions before constructor call */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public ItemizedOverlayWithFocus(java.util.List<Item> r14, android.graphics.drawable.Drawable r15, android.graphics.drawable.Drawable r16, int r17, org.osmdroid.views.overlay.ItemizedIconOverlay.OnItemGestureListener<Item> r18, android.content.Context r19) {
        /*
            r13 = this;
            r0 = r13
            r1 = r14
            r2 = r15
            r3 = r16
            r4 = r17
            r5 = r18
            r6 = r19
            r7 = r0
            r8 = r1
            r9 = r2
            r10 = r5
            r11 = r6
            r7.<init>(r8, r9, r10, r11)
            r7 = r0
            r8 = 101(0x65, float:1.42E-43)
            r9 = 185(0xb9, float:2.59E-43)
            r10 = 74
            int r8 = android.graphics.Color.rgb(r8, r9, r10)
            r7.DEFAULTMARKER_BACKGROUNDCOLOR = r8
            r7 = r0
            r8 = 3
            r7.DESCRIPTION_BOX_PADDING = r8
            r7 = r0
            r8 = 3
            r7.DESCRIPTION_BOX_CORNERWIDTH = r8
            r7 = r0
            r8 = 2
            r7.DESCRIPTION_TITLE_EXTRA_LINE_HEIGHT = r8
            r7 = r0
            r8 = 14
            r7.FONT_SIZE_DP = r8
            r7 = r0
            r8 = 600(0x258, float:8.41E-43)
            r7.DESCRIPTION_MAXWIDTH = r8
            r7 = r0
            r8 = 30
            r7.DESCRIPTION_LINE_HEIGHT = r8
            r7 = r0
            android.graphics.Point r8 = new android.graphics.Point
            r12 = r8
            r8 = r12
            r9 = r12
            r9.<init>()
            r7.mFocusedScreenCoords = r8
            r7 = r0
            android.graphics.Rect r8 = new android.graphics.Rect
            r12 = r8
            r8 = r12
            r9 = r12
            r9.<init>()
            r7.mRect = r8
            r7 = r0
            r8 = r6
            r7.mContext = r8
            r7 = r3
            if (r7 != 0) goto L_0x007f
            r7 = r0
            r8 = r0
            r9 = r6
            android.content.res.Resources r9 = r9.getResources()
            int r10 = org.osmdroid.library.C1262R.C1263drawable.marker_default_focused_base
            android.graphics.drawable.Drawable r9 = r9.getDrawable(r10)
            org.osmdroid.views.overlay.OverlayItem$HotspotPlace r10 = org.osmdroid.views.overlay.OverlayItem.HotspotPlace.BOTTOM_CENTER
            android.graphics.drawable.Drawable r8 = r8.boundToHotspot(r9, r10)
            r7.mMarkerFocusedBase = r8
        L_0x006d:
            r7 = r0
            r8 = r4
            r9 = -2147483648(0xffffffff80000000, float:-0.0)
            if (r8 == r9) goto L_0x0084
            r8 = r4
        L_0x0074:
            r7.mMarkerFocusedBackgroundColor = r8
            r7 = r0
            r7.calculateDrawSettings()
            r7 = r0
            r7.unSetFocusedItem()
            return
        L_0x007f:
            r7 = r0
            r8 = r3
            r7.mMarkerFocusedBase = r8
            goto L_0x006d
        L_0x0084:
            r8 = r0
            int r8 = r8.DEFAULTMARKER_BACKGROUNDCOLOR
            goto L_0x0074
        */
        throw new UnsupportedOperationException("Method not decompiled: org.osmdroid.views.overlay.ItemizedOverlayWithFocus.<init>(java.util.List, android.graphics.drawable.Drawable, android.graphics.drawable.Drawable, int, org.osmdroid.views.overlay.ItemizedIconOverlay$OnItemGestureListener, android.content.Context):void");
    }

    private void calculateDrawSettings() {
        Paint paint;
        Paint paint2;
        Paint paint3;
        this.fontSizePixels = (int) TypedValue.applyDimension(1, (float) this.FONT_SIZE_DP, this.mContext.getResources().getDisplayMetrics());
        this.DESCRIPTION_LINE_HEIGHT = this.fontSizePixels + 5;
        this.DESCRIPTION_MAXWIDTH = (int) (((double) this.mContext.getResources().getDisplayMetrics().widthPixels) * 0.8d);
        this.UNKNOWN = this.mContext.getResources().getString(C1262R.string.unknown);
        new Paint();
        this.mMarkerBackgroundPaint = paint;
        new Paint();
        this.mDescriptionPaint = paint2;
        this.mDescriptionPaint.setAntiAlias(true);
        this.mDescriptionPaint.setTextSize((float) this.fontSizePixels);
        new Paint();
        this.mTitlePaint = paint3;
        this.mTitlePaint.setTextSize((float) this.fontSizePixels);
        this.mTitlePaint.setFakeBoldText(true);
        this.mTitlePaint.setAntiAlias(true);
    }

    public void setDescriptionBoxPadding(int value) {
        int i = value;
        this.DESCRIPTION_BOX_PADDING = i;
    }

    public void setDescriptionBoxCornerWidth(int value) {
        int i = value;
        this.DESCRIPTION_BOX_CORNERWIDTH = i;
    }

    public void setDescriptionTitleExtraLineHeight(int value) {
        int i = value;
        this.DESCRIPTION_TITLE_EXTRA_LINE_HEIGHT = i;
    }

    public void setMarkerBackgroundColor(int value) {
        int i = value;
        this.mMarkerFocusedBackgroundColor = i;
    }

    public void setMarkerTitleForegroundColor(int value) {
        this.mTitlePaint.setColor(value);
    }

    public void setMarkerDescriptionForegroundColor(int value) {
        this.mDescriptionPaint.setColor(value);
    }

    public void setFontSize(int value) {
        this.FONT_SIZE_DP = value;
        calculateDrawSettings();
    }

    public void setDescriptionMaxWidth(int value) {
        this.DESCRIPTION_MAXWIDTH = value;
        calculateDrawSettings();
    }

    public void setDescriptionLineHeight(int value) {
        this.DESCRIPTION_LINE_HEIGHT = value;
        calculateDrawSettings();
    }

    public Item getFocusedItem() {
        if (this.mFocusedItemIndex == Integer.MIN_VALUE) {
            return null;
        }
        return (OverlayItem) this.mItemList.get(this.mFocusedItemIndex);
    }

    public void setFocusedItem(int pIndex) {
        int i = pIndex;
        this.mFocusedItemIndex = i;
    }

    public void unSetFocusedItem() {
        this.mFocusedItemIndex = Integer.MIN_VALUE;
    }

    public void setFocusedItem(Item pItem) {
        Throwable th;
        int indexFound = this.mItemList.indexOf(pItem);
        if (indexFound < 0) {
            Throwable th2 = th;
            new IllegalArgumentException();
            throw th2;
        }
        setFocusedItem(indexFound);
    }

    public void setFocusItemsOnTap(boolean doit) {
        boolean z = doit;
        this.mFocusItemsOnTap = z;
    }

    /* access modifiers changed from: protected */
    public boolean onSingleTapUpHelper(int i, Item item, MapView mapView) {
        int index = i;
        Item item2 = item;
        MapView mapView2 = mapView;
        if (this.mFocusItemsOnTap) {
            this.mFocusedItemIndex = index;
            mapView2.postInvalidate();
        }
        return this.mOnItemGestureListener.onItemSingleTapUp(index, item2);
    }

    public void draw(Canvas canvas, MapView mapView, boolean z) {
        String title;
        String snippet;
        StringBuilder sb;
        int i;
        RectF rectF;
        RectF rectF2;
        Canvas c = canvas;
        MapView osmv = mapView;
        boolean shadow = z;
        super.draw(c, osmv, shadow);
        if (!shadow) {
            if (this.mFocusedItemIndex != Integer.MIN_VALUE) {
                if (this.mItemList != null) {
                    Item focusedItem = (OverlayItem) this.mItemList.get(this.mFocusedItemIndex);
                    Drawable markerFocusedBase = focusedItem.getMarker(4);
                    if (markerFocusedBase == null) {
                        markerFocusedBase = this.mMarkerFocusedBase;
                    }
                    Point pixels = osmv.getProjection().toPixels(focusedItem.getPoint(), this.mFocusedScreenCoords);
                    markerFocusedBase.copyBounds(this.mRect);
                    this.mRect.offset(this.mFocusedScreenCoords.x, this.mFocusedScreenCoords.y);
                    if (focusedItem.getTitle() == null) {
                        title = this.UNKNOWN;
                    } else {
                        title = focusedItem.getTitle();
                    }
                    String itemTitle = title;
                    if (focusedItem.getSnippet() == null) {
                        snippet = this.UNKNOWN;
                    } else {
                        snippet = focusedItem.getSnippet();
                    }
                    String itemDescription = snippet;
                    float[] widths = new float[itemDescription.length()];
                    int textWidths = this.mDescriptionPaint.getTextWidths(itemDescription, widths);
                    new StringBuilder();
                    StringBuilder sb2 = sb;
                    int maxWidth = 0;
                    int curLineWidth = 0;
                    int lastStop = 0;
                    int lastwhitespace = 0;
                    int i2 = 0;
                    while (i < widths.length) {
                        if (!Character.isLetter(itemDescription.charAt(i))) {
                            lastwhitespace = i;
                        }
                        float charwidth = widths[i];
                        if (((float) curLineWidth) + charwidth > ((float) this.DESCRIPTION_MAXWIDTH)) {
                            if (lastStop == lastwhitespace) {
                                i--;
                            } else {
                                i = lastwhitespace;
                            }
                            StringBuilder append = sb2.append(itemDescription.subSequence(lastStop, i));
                            StringBuilder append2 = sb2.append(10);
                            lastStop = i;
                            maxWidth = Math.max(maxWidth, curLineWidth);
                            curLineWidth = 0;
                        }
                        curLineWidth = (int) (((float) curLineWidth) + charwidth);
                        i2 = i + 1;
                    }
                    if (i != lastStop) {
                        String rest = itemDescription.substring(lastStop, i);
                        maxWidth = Math.max(maxWidth, (int) this.mDescriptionPaint.measureText(rest));
                        StringBuilder append3 = sb2.append(rest);
                    }
                    String[] lines = sb2.toString().split("\n");
                    int descWidth = Math.min(Math.max(maxWidth, (int) this.mDescriptionPaint.measureText(itemTitle)), this.DESCRIPTION_MAXWIDTH);
                    int descBoxLeft = ((this.mRect.left - (descWidth / 2)) - this.DESCRIPTION_BOX_PADDING) + (this.mRect.width() / 2);
                    int descBoxRight = descBoxLeft + descWidth + (2 * this.DESCRIPTION_BOX_PADDING);
                    int descBoxBottom = this.mRect.top;
                    int descBoxTop = ((descBoxBottom - this.DESCRIPTION_TITLE_EXTRA_LINE_HEIGHT) - ((lines.length + 1) * this.DESCRIPTION_LINE_HEIGHT)) - (2 * this.DESCRIPTION_BOX_PADDING);
                    this.mMarkerBackgroundPaint.setColor(-16777216);
                    new RectF((float) (descBoxLeft - 1), (float) (descBoxTop - 1), (float) (descBoxRight + 1), (float) (descBoxBottom + 1));
                    c.drawRoundRect(rectF, (float) this.DESCRIPTION_BOX_CORNERWIDTH, (float) this.DESCRIPTION_BOX_CORNERWIDTH, this.mDescriptionPaint);
                    this.mMarkerBackgroundPaint.setColor(this.mMarkerFocusedBackgroundColor);
                    new RectF((float) descBoxLeft, (float) descBoxTop, (float) descBoxRight, (float) descBoxBottom);
                    c.drawRoundRect(rectF2, (float) this.DESCRIPTION_BOX_CORNERWIDTH, (float) this.DESCRIPTION_BOX_CORNERWIDTH, this.mMarkerBackgroundPaint);
                    int descLeft = descBoxLeft + this.DESCRIPTION_BOX_PADDING;
                    int descTextLineBottom = descBoxBottom - this.DESCRIPTION_BOX_PADDING;
                    for (int j = lines.length - 1; j >= 0; j--) {
                        c.drawText(lines[j].trim(), (float) descLeft, (float) descTextLineBottom, this.mDescriptionPaint);
                        descTextLineBottom -= this.DESCRIPTION_LINE_HEIGHT;
                    }
                    c.drawText(itemTitle, (float) descLeft, (float) (descTextLineBottom - this.DESCRIPTION_TITLE_EXTRA_LINE_HEIGHT), this.mTitlePaint);
                    c.drawLine((float) descBoxLeft, (float) descTextLineBottom, (float) descBoxRight, (float) descTextLineBottom, this.mDescriptionPaint);
                    Overlay.drawAt(c, markerFocusedBase, this.mFocusedScreenCoords.x, this.mFocusedScreenCoords.y, false, osmv.getMapOrientation());
                }
            }
        }
    }

    public void onDetach(MapView mapView) {
        super.onDetach(mapView);
        this.mContext = null;
    }
}
