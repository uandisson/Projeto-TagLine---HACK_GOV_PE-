package org.osmdroid.tileprovider;

import android.graphics.Bitmap;

public class ReusableBitmapDrawable extends ExpirableBitmapDrawable {
    private boolean mBitmapRecycled = false;
    private int mUsageRefCount = 0;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public ReusableBitmapDrawable(Bitmap pBitmap) {
        super(pBitmap);
    }

    public void beginUsingDrawable() {
        synchronized (this) {
            try {
                this.mUsageRefCount++;
            } catch (Throwable th) {
                Throwable th2 = th;
                throw th2;
            }
        }
    }

    public void finishUsingDrawable() {
        Throwable th;
        synchronized (this) {
            try {
                this.mUsageRefCount--;
                if (this.mUsageRefCount < 0) {
                    Throwable th2 = th;
                    new IllegalStateException("Unbalanced endUsingDrawable() called.");
                    throw th2;
                }
            } catch (Throwable th3) {
                Throwable th4 = th3;
                throw th4;
            }
        }
    }

    public Bitmap tryRecycle() {
        synchronized (this) {
            try {
                if (this.mUsageRefCount == 0) {
                    this.mBitmapRecycled = true;
                    Bitmap bitmap = getBitmap();
                    return bitmap;
                }
                return null;
            } catch (Throwable th) {
                Throwable th2 = th;
                throw th2;
            }
        }
    }

    public boolean isBitmapValid() {
        synchronized (this) {
            try {
                boolean z = !this.mBitmapRecycled;
                return z;
            } catch (Throwable th) {
                Throwable th2 = th;
                throw th2;
            }
        }
    }
}
