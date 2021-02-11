package com.caverock.androidsvg;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.Build;
import android.util.Log;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class SimpleAssetResolver extends SVGExternalFileResolver {
    private static final String TAG = SimpleAssetResolver.class.getSimpleName();
    private static final Set<String> supportedFormats;
    private AssetManager assetManager;

    static {
        Set<String> set;
        new HashSet(8);
        supportedFormats = set;
    }

    public SimpleAssetResolver(AssetManager assetManager2) {
        AssetManager assetManager3 = assetManager2;
        boolean add = supportedFormats.add("image/svg+xml");
        boolean add2 = supportedFormats.add("image/jpeg");
        boolean add3 = supportedFormats.add("image/png");
        boolean add4 = supportedFormats.add("image/pjpeg");
        boolean add5 = supportedFormats.add("image/gif");
        boolean add6 = supportedFormats.add("image/bmp");
        boolean add7 = supportedFormats.add("image/x-windows-bmp");
        if (Build.VERSION.SDK_INT >= 14) {
            boolean add8 = supportedFormats.add("image/webp");
        }
        this.assetManager = assetManager3;
    }

    public Typeface resolveFont(String str, int fontWeight, String fontStyle) {
        StringBuilder sb;
        StringBuilder sb2;
        StringBuilder sb3;
        String fontFamily = str;
        String str2 = TAG;
        new StringBuilder();
        int i = Log.i(str2, sb.append("resolveFont(").append(fontFamily).append(",").append(fontWeight).append(",").append(fontStyle).append(")").toString());
        try {
            AssetManager assetManager2 = this.assetManager;
            new StringBuilder();
            return Typeface.createFromAsset(assetManager2, sb3.append(fontFamily).append(".ttf").toString());
        } catch (Exception e) {
            Exception exc = e;
            try {
                AssetManager assetManager3 = this.assetManager;
                new StringBuilder();
                return Typeface.createFromAsset(assetManager3, sb2.append(fontFamily).append(".otf").toString());
            } catch (Exception e2) {
                Exception exc2 = e2;
                return null;
            }
        }
    }

    public Bitmap resolveImage(String str) {
        StringBuilder sb;
        String filename = str;
        String str2 = TAG;
        new StringBuilder();
        int i = Log.i(str2, sb.append("resolveImage(").append(filename).append(")").toString());
        try {
            return BitmapFactory.decodeStream(this.assetManager.open(filename));
        } catch (IOException e) {
            IOException iOException = e;
            return null;
        }
    }

    public boolean isFormatSupported(String mimeType) {
        return supportedFormats.contains(mimeType);
    }
}
