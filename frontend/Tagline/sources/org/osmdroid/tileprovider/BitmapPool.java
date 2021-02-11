package org.osmdroid.tileprovider;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import java.util.Iterator;
import java.util.LinkedList;

public class BitmapPool {
    private static BitmapPool sInstance;
    final LinkedList<Bitmap> mPool;

    public BitmapPool() {
        LinkedList<Bitmap> linkedList;
        new LinkedList<>();
        this.mPool = linkedList;
    }

    public static BitmapPool getInstance() {
        BitmapPool bitmapPool;
        if (sInstance == null) {
            new BitmapPool();
            sInstance = bitmapPool;
        }
        return sInstance;
    }

    public void returnDrawableToPool(ReusableBitmapDrawable drawable) {
        Bitmap b = drawable.tryRecycle();
        if (b != null && b.isMutable()) {
            LinkedList<Bitmap> linkedList = this.mPool;
            LinkedList<Bitmap> linkedList2 = linkedList;
            synchronized (linkedList) {
                try {
                    this.mPool.addLast(b);
                } catch (Throwable th) {
                    Throwable th2 = th;
                    LinkedList<Bitmap> linkedList3 = linkedList2;
                    throw th2;
                }
            }
        }
    }

    public void applyReusableOptions(BitmapFactory.Options options) {
        BitmapFactory.Options aBitmapOptions = options;
        if (Build.VERSION.SDK_INT >= 11) {
            aBitmapOptions.inBitmap = obtainBitmapFromPool();
            aBitmapOptions.inSampleSize = 1;
            aBitmapOptions.inMutable = true;
        }
    }

    /* JADX INFO: finally extract failed */
    public Bitmap obtainBitmapFromPool() {
        LinkedList<Bitmap> linkedList = this.mPool;
        LinkedList<Bitmap> linkedList2 = linkedList;
        synchronized (linkedList) {
            try {
                if (this.mPool.isEmpty()) {
                    return null;
                }
                Bitmap bitmap = this.mPool.removeFirst();
                if (bitmap.isRecycled()) {
                    Bitmap obtainBitmapFromPool = obtainBitmapFromPool();
                    return obtainBitmapFromPool;
                }
                Bitmap bitmap2 = bitmap;
                return bitmap2;
            } catch (Throwable th) {
                Throwable th2 = th;
                LinkedList<Bitmap> linkedList3 = linkedList2;
                throw th2;
            }
        }
    }

    /* JADX INFO: finally extract failed */
    public Bitmap obtainSizedBitmapFromPool(int i, int i2) {
        int aWidth = i;
        int aHeight = i2;
        LinkedList<Bitmap> linkedList = this.mPool;
        LinkedList<Bitmap> linkedList2 = linkedList;
        synchronized (linkedList) {
            try {
                if (this.mPool.isEmpty()) {
                    return null;
                }
                Iterator it = this.mPool.iterator();
                while (it.hasNext()) {
                    Bitmap bitmap = (Bitmap) it.next();
                    if (bitmap.isRecycled()) {
                        boolean remove = this.mPool.remove(bitmap);
                        Bitmap obtainSizedBitmapFromPool = obtainSizedBitmapFromPool(aWidth, aHeight);
                        return obtainSizedBitmapFromPool;
                    } else if (bitmap.getWidth() == aWidth && bitmap.getHeight() == aHeight) {
                        boolean remove2 = this.mPool.remove(bitmap);
                        Bitmap bitmap2 = bitmap;
                        return bitmap2;
                    }
                }
                return null;
            } catch (Throwable th) {
                Throwable th2 = th;
                LinkedList<Bitmap> linkedList3 = linkedList2;
                throw th2;
            }
        }
    }

    /* JADX INFO: finally extract failed */
    public void clearBitmapPool() {
        LinkedList<Bitmap> linkedList = sInstance.mPool;
        LinkedList<Bitmap> linkedList2 = linkedList;
        synchronized (linkedList) {
            while (!sInstance.mPool.isEmpty()) {
                try {
                    sInstance.mPool.remove().recycle();
                } catch (Throwable th) {
                    Throwable th2 = th;
                    LinkedList<Bitmap> linkedList3 = linkedList2;
                    throw th2;
                }
            }
        }
    }
}
