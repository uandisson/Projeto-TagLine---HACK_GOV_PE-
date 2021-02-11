package com.caverock.androidsvg;

import android.graphics.Bitmap;
import android.graphics.Typeface;

public abstract class SVGExternalFileResolver {
    public SVGExternalFileResolver() {
    }

    public Typeface resolveFont(String str, int i, String str2) {
        String str3 = str;
        int i2 = i;
        String str4 = str2;
        return null;
    }

    public Bitmap resolveImage(String str) {
        String str2 = str;
        return null;
    }

    public boolean isFormatSupported(String str) {
        String str2 = str;
        return false;
    }
}
