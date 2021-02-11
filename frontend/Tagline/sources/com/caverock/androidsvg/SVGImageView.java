package com.caverock.androidsvg;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Paint;
import android.graphics.Picture;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.PictureDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;

public class SVGImageView extends ImageView {
    private static Method setLayerTypeMethod = null;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public SVGImageView(Context context) {
        super(context);
        Class<View> cls = View.class;
        try {
            Class[] clsArr = new Class[2];
            clsArr[0] = Integer.TYPE;
            Class[] clsArr2 = clsArr;
            clsArr2[1] = Paint.class;
            setLayerTypeMethod = cls.getMethod("setLayerType", clsArr2);
        } catch (NoSuchMethodException e) {
            NoSuchMethodException noSuchMethodException = e;
        }
    }

    /* JADX WARNING: Illegal instructions before constructor call */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public SVGImageView(android.content.Context r12, android.util.AttributeSet r13) {
        /*
            r11 = this;
            r0 = r11
            r1 = r12
            r2 = r13
            r4 = r0
            r5 = r1
            r6 = r2
            r7 = 0
            r4.<init>(r5, r6, r7)
            java.lang.Class<android.view.View> r4 = android.view.View.class
            java.lang.String r5 = "setLayerType"
            r6 = 2
            java.lang.Class[] r6 = new java.lang.Class[r6]     // Catch:{ NoSuchMethodException -> 0x002f }
            r10 = r6
            r6 = r10
            r7 = r10
            r8 = 0
            java.lang.Class r9 = java.lang.Integer.TYPE     // Catch:{ NoSuchMethodException -> 0x002f }
            r7[r8] = r9     // Catch:{ NoSuchMethodException -> 0x002f }
            r10 = r6
            r6 = r10
            r7 = r10
            r8 = 1
            java.lang.Class<android.graphics.Paint> r9 = android.graphics.Paint.class
            r7[r8] = r9     // Catch:{ NoSuchMethodException -> 0x002f }
            java.lang.reflect.Method r4 = r4.getMethod(r5, r6)     // Catch:{ NoSuchMethodException -> 0x002f }
            setLayerTypeMethod = r4     // Catch:{ NoSuchMethodException -> 0x002f }
        L_0x0028:
            r4 = r0
            r5 = r2
            r6 = 0
            r4.init(r5, r6)
            return
        L_0x002f:
            r4 = move-exception
            r3 = r4
            goto L_0x0028
        */
        throw new UnsupportedOperationException("Method not decompiled: com.caverock.androidsvg.SVGImageView.<init>(android.content.Context, android.util.AttributeSet):void");
    }

    /* JADX WARNING: Illegal instructions before constructor call */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public SVGImageView(android.content.Context r13, android.util.AttributeSet r14, int r15) {
        /*
            r12 = this;
            r0 = r12
            r1 = r13
            r2 = r14
            r3 = r15
            r5 = r0
            r6 = r1
            r7 = r2
            r8 = r3
            r5.<init>(r6, r7, r8)
            java.lang.Class<android.view.View> r5 = android.view.View.class
            java.lang.String r6 = "setLayerType"
            r7 = 2
            java.lang.Class[] r7 = new java.lang.Class[r7]     // Catch:{ NoSuchMethodException -> 0x0030 }
            r11 = r7
            r7 = r11
            r8 = r11
            r9 = 0
            java.lang.Class r10 = java.lang.Integer.TYPE     // Catch:{ NoSuchMethodException -> 0x0030 }
            r8[r9] = r10     // Catch:{ NoSuchMethodException -> 0x0030 }
            r11 = r7
            r7 = r11
            r8 = r11
            r9 = 1
            java.lang.Class<android.graphics.Paint> r10 = android.graphics.Paint.class
            r8[r9] = r10     // Catch:{ NoSuchMethodException -> 0x0030 }
            java.lang.reflect.Method r5 = r5.getMethod(r6, r7)     // Catch:{ NoSuchMethodException -> 0x0030 }
            setLayerTypeMethod = r5     // Catch:{ NoSuchMethodException -> 0x0030 }
        L_0x0029:
            r5 = r0
            r6 = r2
            r7 = r3
            r5.init(r6, r7)
            return
        L_0x0030:
            r5 = move-exception
            r4 = r5
            goto L_0x0029
        */
        throw new UnsupportedOperationException("Method not decompiled: com.caverock.androidsvg.SVGImageView.<init>(android.content.Context, android.util.AttributeSet, int):void");
    }

    private void init(AttributeSet attributeSet, int i) {
        AttributeSet attrs = attributeSet;
        int defStyle = i;
        if (!isInEditMode()) {
            TypedArray a = getContext().getTheme().obtainStyledAttributes(attrs, R.styleable.SVGImageView, defStyle, 0);
            try {
                int resourceId = a.getResourceId(R.styleable.SVGImageView_svg, -1);
                if (resourceId != -1) {
                    setImageResource(resourceId);
                    a.recycle();
                    return;
                }
                String url = a.getString(R.styleable.SVGImageView_svg);
                if (url != null) {
                    if (internalSetImageURI(Uri.parse(url), false)) {
                        a.recycle();
                        return;
                    }
                    setImageAsset(url);
                }
                a.recycle();
            } catch (Throwable th) {
                Throwable th2 = th;
                a.recycle();
                throw th2;
            }
        }
    }

    public void setSVG(SVG svg) {
        Drawable drawable;
        Throwable th;
        SVG mysvg = svg;
        if (mysvg == null) {
            Throwable th2 = th;
            new IllegalArgumentException("Null value passed to setSVG()");
            throw th2;
        }
        setSoftwareLayerType();
        new PictureDrawable(mysvg.renderToPicture());
        setImageDrawable(drawable);
    }

    public void setImageResource(int resourceId) {
        LoadResourceTask loadResourceTask;
        new LoadResourceTask(this, (C15171) null);
        AsyncTask execute = loadResourceTask.execute(new Integer[]{Integer.valueOf(resourceId)});
    }

    public void setImageURI(Uri uri) {
        boolean internalSetImageURI = internalSetImageURI(uri, true);
    }

    public void setImageAsset(String filename) {
        LoadAssetTask loadAssetTask;
        new LoadAssetTask(this, (C15171) null);
        AsyncTask execute = loadAssetTask.execute(new String[]{filename});
    }

    private boolean internalSetImageURI(Uri uri, boolean z) {
        StringBuilder sb;
        LoadURITask loadURITask;
        Uri uri2 = uri;
        boolean isDirectRequestFromUser = z;
        try {
            InputStream is = getContext().getContentResolver().openInputStream(uri2);
            new LoadURITask(this, (C15171) null);
            AsyncTask execute = loadURITask.execute(new InputStream[]{is});
            return true;
        } catch (FileNotFoundException e) {
            FileNotFoundException fileNotFoundException = e;
            if (isDirectRequestFromUser) {
                new StringBuilder();
                int e2 = Log.e("SVGImageView", sb.append("File not found: ").append(uri2).toString());
            }
            return false;
        }
    }

    private class LoadResourceTask extends AsyncTask<Integer, Integer, Picture> {
        final /* synthetic */ SVGImageView this$0;

        private LoadResourceTask(SVGImageView sVGImageView) {
            this.this$0 = sVGImageView;
        }

        /* JADX INFO: this call moved to the top of the method (can break code semantics) */
        /* synthetic */ LoadResourceTask(SVGImageView x0, C15171 r7) {
            this(x0);
            C15171 r2 = r7;
        }

        /* access modifiers changed from: protected */
        public Picture doInBackground(Integer... numArr) {
            Integer[] resourceId = numArr;
            try {
                return SVG.getFromResource(this.this$0.getContext(), resourceId[0].intValue()).renderToPicture();
            } catch (SVGParseException e) {
                Object[] objArr = new Object[2];
                objArr[0] = resourceId;
                Object[] objArr2 = objArr;
                objArr2[1] = e.getMessage();
                int e2 = Log.e("SVGImageView", String.format("Error loading resource 0x%x: %s", objArr2));
                return null;
            }
        }

        /* access modifiers changed from: protected */
        public void onPostExecute(Picture picture) {
            Drawable drawable;
            Picture picture2 = picture;
            if (picture2 != null) {
                this.this$0.setSoftwareLayerType();
                new PictureDrawable(picture2);
                this.this$0.setImageDrawable(drawable);
            }
        }
    }

    private class LoadAssetTask extends AsyncTask<String, Integer, Picture> {
        final /* synthetic */ SVGImageView this$0;

        private LoadAssetTask(SVGImageView sVGImageView) {
            this.this$0 = sVGImageView;
        }

        /* JADX INFO: this call moved to the top of the method (can break code semantics) */
        /* synthetic */ LoadAssetTask(SVGImageView x0, C15171 r7) {
            this(x0);
            C15171 r2 = r7;
        }

        /* access modifiers changed from: protected */
        public Picture doInBackground(String... strArr) {
            StringBuilder sb;
            StringBuilder sb2;
            StringBuilder sb3;
            String[] filename = strArr;
            try {
                return SVG.getFromAsset(this.this$0.getContext().getAssets(), filename[0]).renderToPicture();
            } catch (SVGParseException e) {
                new StringBuilder();
                int e2 = Log.e("SVGImageView", sb3.append("Error loading file ").append(filename).append(": ").append(e.getMessage()).toString());
                return null;
            } catch (FileNotFoundException e3) {
                FileNotFoundException fileNotFoundException = e3;
                new StringBuilder();
                int e4 = Log.e("SVGImageView", sb2.append("File not found: ").append(filename).toString());
                return null;
            } catch (IOException e5) {
                new StringBuilder();
                int e6 = Log.e("SVGImageView", sb.append("Unable to load asset file: ").append(filename).toString(), e5);
                return null;
            }
        }

        /* access modifiers changed from: protected */
        public void onPostExecute(Picture picture) {
            Drawable drawable;
            Picture picture2 = picture;
            if (picture2 != null) {
                this.this$0.setSoftwareLayerType();
                new PictureDrawable(picture2);
                this.this$0.setImageDrawable(drawable);
            }
        }
    }

    private class LoadURITask extends AsyncTask<InputStream, Integer, Picture> {
        final /* synthetic */ SVGImageView this$0;

        private LoadURITask(SVGImageView sVGImageView) {
            this.this$0 = sVGImageView;
        }

        /* JADX INFO: this call moved to the top of the method (can break code semantics) */
        /* synthetic */ LoadURITask(SVGImageView x0, C15171 r7) {
            this(x0);
            C15171 r2 = r7;
        }

        /* access modifiers changed from: protected */
        public Picture doInBackground(InputStream... inputStreamArr) {
            StringBuilder sb;
            InputStream[] is = inputStreamArr;
            try {
                Picture renderToPicture = SVG.getFromInputStream(is[0]).renderToPicture();
                try {
                    is[0].close();
                } catch (IOException e) {
                    IOException iOException = e;
                }
                return renderToPicture;
            } catch (SVGParseException e2) {
                SVGParseException e3 = e2;
                new StringBuilder();
                int e4 = Log.e("SVGImageView", sb.append("Parse error loading URI: ").append(e3.getMessage()).toString());
                try {
                    is[0].close();
                } catch (IOException e5) {
                    IOException iOException2 = e5;
                }
                return null;
            } catch (Throwable th) {
                Throwable th2 = th;
                try {
                    is[0].close();
                } catch (IOException e6) {
                    IOException iOException3 = e6;
                }
                throw th2;
            }
        }

        /* access modifiers changed from: protected */
        public void onPostExecute(Picture picture) {
            Drawable drawable;
            Picture picture2 = picture;
            if (picture2 != null) {
                this.this$0.setSoftwareLayerType();
                new PictureDrawable(picture2);
                this.this$0.setImageDrawable(drawable);
            }
        }
    }

    /* access modifiers changed from: private */
    public void setSoftwareLayerType() {
        Object obj;
        if (setLayerTypeMethod != null) {
            try {
                new View(getContext());
                int LAYER_TYPE_SOFTWARE = View.class.getField("LAYER_TYPE_SOFTWARE").getInt(obj);
                Object[] objArr = new Object[2];
                objArr[0] = Integer.valueOf(LAYER_TYPE_SOFTWARE);
                Object[] objArr2 = objArr;
                objArr2[1] = null;
                Object invoke = setLayerTypeMethod.invoke(this, objArr2);
            } catch (Exception e) {
                int w = Log.w("SVGImageView", "Unexpected failure calling setLayerType", e);
            }
        }
    }
}
