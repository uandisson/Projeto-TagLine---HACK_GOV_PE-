package org.osmdroid.tileprovider;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

public class ExpirableBitmapDrawable extends BitmapDrawable {
    public static final int EXPIRED = -2;
    public static final int NOT_FOUND = -4;
    public static final int SCALED = -3;
    public static final int UP_TO_DATE = -1;
    private static final int defaultStatus = -1;
    private static final int[] settableStatuses = {-2, -3, -4};
    private int[] mState = new int[0];

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public ExpirableBitmapDrawable(Bitmap pBitmap) {
        super(pBitmap);
    }

    public int[] getState() {
        return this.mState;
    }

    public boolean isStateful() {
        return this.mState.length > 0;
    }

    public boolean setState(int[] pStateSet) {
        int[] iArr = pStateSet;
        this.mState = iArr;
        return true;
    }

    @Deprecated
    public static boolean isDrawableExpired(Drawable pTile) {
        return getState(pTile) == -2;
    }

    public static int getState(Drawable pTile) {
        int[] state = pTile.getState();
        int length = state.length;
        for (int i = 0; i < length; i++) {
            int statusItem = state[i];
            int[] iArr = settableStatuses;
            int length2 = iArr.length;
            for (int i2 = 0; i2 < length2; i2++) {
                if (statusItem == iArr[i2]) {
                    return statusItem;
                }
            }
        }
        return -1;
    }

    @Deprecated
    public static void setDrawableExpired(Drawable pTile) {
        setState(pTile, -2);
    }

    public static void setState(Drawable pTile, int status) {
        boolean state = pTile.setState(new int[]{status});
    }
}
