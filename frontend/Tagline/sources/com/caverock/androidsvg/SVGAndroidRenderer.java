package com.caverock.androidsvg;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.PathMeasure;
import android.graphics.RadialGradient;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.os.Build;
import android.support.p000v4.media.session.PlaybackStateCompat;
import android.util.Base64;
import android.util.Log;
import com.caverock.androidsvg.CSSParser;
import com.caverock.androidsvg.PreserveAspectRatio;
import com.caverock.androidsvg.SVG;
import com.google.appinventor.components.runtime.util.MapFactory;
import gnu.expr.Declaration;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.Stack;

public class SVGAndroidRenderer {
    private static final float BEZIER_ARC_FACTOR = 0.5522848f;
    private static final String DEFAULT_FONT_FAMILY = "sans-serif";
    private static final int LUMINANCE_FACTOR_SHIFT = 15;
    private static final int LUMINANCE_TO_ALPHA_BLUE = 2362;
    private static final int LUMINANCE_TO_ALPHA_GREEN = 23442;
    private static final int LUMINANCE_TO_ALPHA_RED = 6963;
    private static final String TAG = "SVGAndroidRenderer";
    private static HashSet<String> supportedFeatures = null;
    private Stack<Bitmap> bitmapStack;
    /* access modifiers changed from: private */
    public Canvas canvas;
    private Stack<Canvas> canvasStack;
    private SVG.Box canvasViewPort;
    private boolean directRenderingMode;
    private SVG document;
    private float dpi;
    private Stack<Matrix> matrixStack;
    private Stack<SVG.SvgContainer> parentStack;
    /* access modifiers changed from: private */
    public RendererState state;
    private Stack<RendererState> stateStack;

    private class RendererState implements Cloneable {
        public boolean directRendering;
        public Paint fillPaint;
        public boolean hasFill;
        public boolean hasStroke;
        public boolean spacePreserve;
        public Paint strokePaint;
        public SVG.Style style = SVG.Style.getDefaultStyle();
        final /* synthetic */ SVGAndroidRenderer this$0;
        public SVG.Box viewBox;
        public SVG.Box viewPort;

        public RendererState(SVGAndroidRenderer sVGAndroidRenderer) {
            Paint paint;
            Paint paint2;
            this.this$0 = sVGAndroidRenderer;
            new Paint();
            this.fillPaint = paint;
            this.fillPaint.setFlags(385);
            this.fillPaint.setStyle(Paint.Style.FILL);
            Typeface typeface = this.fillPaint.setTypeface(Typeface.DEFAULT);
            new Paint();
            this.strokePaint = paint2;
            this.strokePaint.setFlags(385);
            this.strokePaint.setStyle(Paint.Style.STROKE);
            Typeface typeface2 = this.strokePaint.setTypeface(Typeface.DEFAULT);
        }

        /* access modifiers changed from: protected */
        public Object clone() {
            Throwable th;
            Paint paint;
            Paint paint2;
            try {
                RendererState obj = (RendererState) super.clone();
                obj.style = (SVG.Style) this.style.clone();
                new Paint(this.fillPaint);
                obj.fillPaint = paint;
                new Paint(this.strokePaint);
                obj.strokePaint = paint2;
                return obj;
            } catch (CloneNotSupportedException e) {
                CloneNotSupportedException e2 = e;
                Throwable th2 = th;
                new InternalError(e2.toString());
                throw th2;
            }
        }
    }

    private void resetState() {
        RendererState rendererState;
        Stack<RendererState> stack;
        Stack<Canvas> stack2;
        Stack<Bitmap> stack3;
        Stack<Matrix> stack4;
        Stack<SVG.SvgContainer> stack5;
        new RendererState(this);
        this.state = rendererState;
        new Stack<>();
        this.stateStack = stack;
        updateStyle(this.state, SVG.Style.getDefaultStyle());
        this.state.viewPort = this.canvasViewPort;
        this.state.spacePreserve = false;
        this.state.directRendering = this.directRenderingMode;
        RendererState push = this.stateStack.push((RendererState) this.state.clone());
        new Stack<>();
        this.canvasStack = stack2;
        new Stack<>();
        this.bitmapStack = stack3;
        new Stack<>();
        this.matrixStack = stack4;
        new Stack<>();
        this.parentStack = stack5;
    }

    protected SVGAndroidRenderer(Canvas canvas2, SVG.Box viewPort, float defaultDPI) {
        this.canvas = canvas2;
        this.dpi = defaultDPI;
        this.canvasViewPort = viewPort;
    }

    /* access modifiers changed from: protected */
    public float getDPI() {
        return this.dpi;
    }

    /* access modifiers changed from: protected */
    public float getCurrentFontSize() {
        return this.state.fillPaint.getTextSize();
    }

    /* access modifiers changed from: protected */
    public float getCurrentFontXHeight() {
        return this.state.fillPaint.getTextSize() / 2.0f;
    }

    /* access modifiers changed from: protected */
    public SVG.Box getCurrentViewPortInUserUnits() {
        if (this.state.viewBox != null) {
            return this.state.viewBox;
        }
        return this.state.viewPort;
    }

    /* access modifiers changed from: protected */
    public void renderDocument(SVG svg, SVG.Box box, PreserveAspectRatio preserveAspectRatio, boolean directRenderingMode2) {
        SVG document2 = svg;
        SVG.Box viewBox = box;
        PreserveAspectRatio positioning = preserveAspectRatio;
        this.document = document2;
        this.directRenderingMode = directRenderingMode2;
        SVG.Svg rootObj = document2.getRootElement();
        if (rootObj == null) {
            warn("Nothing to render. Document is empty.", new Object[0]);
            return;
        }
        resetState();
        checkXMLSpaceAttribute(rootObj);
        render(rootObj, rootObj.width, rootObj.height, viewBox != null ? viewBox : rootObj.viewBox, positioning != null ? positioning : rootObj.preserveAspectRatio);
    }

    private void render(SVG.SvgObject svgObject) {
        SVG.SvgObject obj = svgObject;
        if (!(obj instanceof SVG.NotDirectlyRendered)) {
            statePush();
            checkXMLSpaceAttribute(obj);
            if (obj instanceof SVG.Svg) {
                render((SVG.Svg) obj);
            } else if (obj instanceof SVG.Use) {
                render((SVG.Use) obj);
            } else if (obj instanceof SVG.Switch) {
                render((SVG.Switch) obj);
            } else if (obj instanceof SVG.Group) {
                render((SVG.Group) obj);
            } else if (obj instanceof SVG.Image) {
                render((SVG.Image) obj);
            } else if (obj instanceof SVG.Path) {
                render((SVG.Path) obj);
            } else if (obj instanceof SVG.Rect) {
                render((SVG.Rect) obj);
            } else if (obj instanceof SVG.Circle) {
                render((SVG.Circle) obj);
            } else if (obj instanceof SVG.Ellipse) {
                render((SVG.Ellipse) obj);
            } else if (obj instanceof SVG.Line) {
                render((SVG.Line) obj);
            } else if (obj instanceof SVG.Polygon) {
                render((SVG.Polygon) obj);
            } else if (obj instanceof SVG.PolyLine) {
                render((SVG.PolyLine) obj);
            } else if (obj instanceof SVG.Text) {
                render((SVG.Text) obj);
            }
            statePop();
        }
    }

    private void renderChildren(SVG.SvgContainer svgContainer, boolean z) {
        SVG.SvgContainer obj = svgContainer;
        boolean isContainer = z;
        if (isContainer) {
            parentPush(obj);
        }
        for (SVG.SvgObject child : obj.getChildren()) {
            render(child);
        }
        if (isContainer) {
            parentPop();
        }
    }

    private void statePush() {
        int save = this.canvas.save();
        RendererState push = this.stateStack.push(this.state);
        this.state = (RendererState) this.state.clone();
    }

    private void statePop() {
        this.canvas.restore();
        this.state = this.stateStack.pop();
    }

    private void parentPush(SVG.SvgContainer obj) {
        SVG.SvgContainer push = this.parentStack.push(obj);
        Matrix push2 = this.matrixStack.push(this.canvas.getMatrix());
    }

    private void parentPop() {
        SVG.SvgContainer pop = this.parentStack.pop();
        Matrix pop2 = this.matrixStack.pop();
    }

    private void updateStyleForElement(RendererState rendererState, SVG.SvgElementBase svgElementBase) {
        RendererState state2 = rendererState;
        SVG.SvgElementBase obj = svgElementBase;
        state2.style.resetNonInheritingProperties(obj.parent == null);
        if (obj.baseStyle != null) {
            updateStyle(state2, obj.baseStyle);
        }
        if (this.document.hasCSSRules()) {
            for (CSSParser.Rule rule : this.document.getCSSRules()) {
                if (CSSParser.ruleMatch(rule.selector, obj)) {
                    updateStyle(state2, rule.style);
                }
            }
        }
        if (obj.style != null) {
            updateStyle(state2, obj.style);
        }
    }

    private void checkXMLSpaceAttribute(SVG.SvgObject svgObject) {
        SVG.SvgObject obj = svgObject;
        if (obj instanceof SVG.SvgElementBase) {
            SVG.SvgElementBase bobj = (SVG.SvgElementBase) obj;
            if (bobj.spacePreserve != null) {
                this.state.spacePreserve = bobj.spacePreserve.booleanValue();
            }
        }
    }

    private void doFilledPath(SVG.SvgElement svgElement, Path path) {
        SVG.SvgElement obj = svgElement;
        Path path2 = path;
        if (this.state.style.fill instanceof SVG.PaintReference) {
            SVG.SvgObject ref = this.document.resolveIRI(((SVG.PaintReference) this.state.style.fill).href);
            if (ref instanceof SVG.Pattern) {
                fillWithPattern(obj, path2, (SVG.Pattern) ref);
                return;
            }
        }
        this.canvas.drawPath(path2, this.state.fillPaint);
    }

    private void doStroke(Path path) {
        Path path2;
        Matrix matrix;
        Matrix matrix2;
        Matrix matrix3;
        Path path3 = path;
        if (this.state.style.vectorEffect == SVG.Style.VectorEffect.NonScalingStroke) {
            Matrix currentMatrix = this.canvas.getMatrix();
            new Path();
            Path transformedPath = path2;
            path3.transform(currentMatrix, transformedPath);
            new Matrix();
            this.canvas.setMatrix(matrix);
            Shader shader = this.state.strokePaint.getShader();
            new Matrix();
            Matrix currentShaderMatrix = matrix2;
            if (shader != null) {
                boolean localMatrix = shader.getLocalMatrix(currentShaderMatrix);
                new Matrix(currentShaderMatrix);
                Matrix newShaderMatrix = matrix3;
                boolean postConcat = newShaderMatrix.postConcat(currentMatrix);
                shader.setLocalMatrix(newShaderMatrix);
            }
            this.canvas.drawPath(transformedPath, this.state.strokePaint);
            this.canvas.setMatrix(currentMatrix);
            if (shader != null) {
                shader.setLocalMatrix(currentShaderMatrix);
                return;
            }
            return;
        }
        this.canvas.drawPath(path3, this.state.strokePaint);
    }

    /* access modifiers changed from: private */
    public static void warn(String format, Object... args) {
        int w = Log.w(TAG, String.format(format, args));
    }

    /* access modifiers changed from: private */
    public static void error(String format, Object... args) {
        int e = Log.e(TAG, String.format(format, args));
    }

    /* access modifiers changed from: private */
    public static void debug(String format, Object... args) {
    }

    private static void info(String format, Object... args) {
        int i = Log.i(TAG, String.format(format, args));
    }

    private void render(SVG.Svg svg) {
        SVG.Svg obj = svg;
        render(obj, obj.width, obj.height);
    }

    private void render(SVG.Svg svg, SVG.Length width, SVG.Length height) {
        SVG.Svg obj = svg;
        render(obj, width, height, obj.viewBox, obj.preserveAspectRatio);
    }

    private void render(SVG.Svg svg, SVG.Length length, SVG.Length length2, SVG.Box box, PreserveAspectRatio preserveAspectRatio) {
        float f;
        SVG.Box box2;
        SVG.Svg obj = svg;
        SVG.Length width = length;
        SVG.Length height = length2;
        SVG.Box viewBox = box;
        PreserveAspectRatio positioning = preserveAspectRatio;
        debug("Svg render", new Object[0]);
        if (width != null && width.isZero()) {
            return;
        }
        if (height == null || !height.isZero()) {
            if (positioning == null) {
                positioning = obj.preserveAspectRatio != null ? obj.preserveAspectRatio : PreserveAspectRatio.LETTERBOX;
            }
            updateStyleForElement(this.state, obj);
            if (display()) {
                float _x = 0.0f;
                float _y = 0.0f;
                if (obj.parent != null) {
                    _x = obj.f335x != null ? obj.f335x.floatValueX(this) : 0.0f;
                    _y = obj.f336y != null ? obj.f336y.floatValueY(this) : 0.0f;
                }
                SVG.Box viewPortUser = getCurrentViewPortInUserUnits();
                float _w = width != null ? width.floatValueX(this) : viewPortUser.width;
                if (height != null) {
                    f = height.floatValueY(this);
                } else {
                    f = viewPortUser.height;
                }
                float _h = f;
                RendererState rendererState = this.state;
                new SVG.Box(_x, _y, _w, _h);
                rendererState.viewPort = box2;
                if (!this.state.style.overflow.booleanValue()) {
                    setClipRect(this.state.viewPort.minX, this.state.viewPort.minY, this.state.viewPort.width, this.state.viewPort.height);
                }
                checkForClipPath(obj, this.state.viewPort);
                if (viewBox != null) {
                    this.canvas.concat(calculateViewBoxTransform(this.state.viewPort, viewBox, positioning));
                    this.state.viewBox = obj.viewBox;
                } else {
                    this.canvas.translate(_x, _y);
                }
                boolean compositing = pushLayer();
                viewportFill();
                renderChildren(obj, true);
                if (compositing) {
                    popLayer(obj);
                }
                updateParentBoundingBox(obj);
            }
        }
    }

    private void render(SVG.Group group) {
        SVG.Group obj = group;
        debug("Group render", new Object[0]);
        updateStyleForElement(this.state, obj);
        if (display()) {
            if (obj.transform != null) {
                this.canvas.concat(obj.transform);
            }
            checkForClipPath(obj);
            boolean compositing = pushLayer();
            renderChildren(obj, true);
            if (compositing) {
                popLayer(obj);
            }
            updateParentBoundingBox(obj);
        }
    }

    private void updateParentBoundingBox(SVG.SvgElement svgElement) {
        Matrix matrix;
        RectF rectF;
        SVG.SvgElement obj = svgElement;
        if (obj.parent != null && obj.boundingBox != null) {
            new Matrix();
            Matrix m = matrix;
            if (this.matrixStack.peek().invert(m)) {
                float[] fArr = new float[8];
                fArr[0] = obj.boundingBox.minX;
                float[] fArr2 = fArr;
                fArr2[1] = obj.boundingBox.minY;
                float[] fArr3 = fArr2;
                fArr3[2] = obj.boundingBox.maxX();
                float[] fArr4 = fArr3;
                fArr4[3] = obj.boundingBox.minY;
                float[] fArr5 = fArr4;
                fArr5[4] = obj.boundingBox.maxX();
                float[] fArr6 = fArr5;
                fArr6[5] = obj.boundingBox.maxY();
                float[] fArr7 = fArr6;
                fArr7[6] = obj.boundingBox.minX;
                float[] fArr8 = fArr7;
                fArr8[7] = obj.boundingBox.maxY();
                float[] pts = fArr8;
                boolean preConcat = m.preConcat(this.canvas.getMatrix());
                m.mapPoints(pts);
                new RectF(pts[0], pts[1], pts[0], pts[1]);
                RectF rect = rectF;
                for (int i = 2; i <= 6; i += 2) {
                    if (pts[i] < rect.left) {
                        rect.left = pts[i];
                    }
                    if (pts[i] > rect.right) {
                        rect.right = pts[i];
                    }
                    if (pts[i + 1] < rect.top) {
                        rect.top = pts[i + 1];
                    }
                    if (pts[i + 1] > rect.bottom) {
                        rect.bottom = pts[i + 1];
                    }
                }
                SVG.SvgElement parent = (SVG.SvgElement) this.parentStack.peek();
                if (parent.boundingBox == null) {
                    parent.boundingBox = SVG.Box.fromLimits(rect.left, rect.top, rect.right, rect.bottom);
                    return;
                }
                parent.boundingBox.union(SVG.Box.fromLimits(rect.left, rect.top, rect.right, rect.bottom));
            }
        }
    }

    private boolean pushLayer() {
        if (!requiresCompositing()) {
            return false;
        }
        int saveLayerAlpha = this.canvas.saveLayerAlpha((RectF) null, clamp255(this.state.style.opacity.floatValue()), 4);
        RendererState push = this.stateStack.push(this.state);
        this.state = (RendererState) this.state.clone();
        if (this.state.style.mask != null && this.state.directRendering) {
            SVG.SvgObject ref = this.document.resolveIRI(this.state.style.mask);
            if (ref == null || !(ref instanceof SVG.Mask)) {
                error("Mask reference '%s' not found", this.state.style.mask);
                this.state.style.mask = null;
                return true;
            }
            Canvas push2 = this.canvasStack.push(this.canvas);
            duplicateCanvas();
        }
        return true;
    }

    private void popLayer(SVG.SvgElement svgElement) {
        Matrix matrix;
        SVG.SvgElement obj = svgElement;
        if (this.state.style.mask != null && this.state.directRendering) {
            SVG.SvgObject ref = this.document.resolveIRI(this.state.style.mask);
            duplicateCanvas();
            renderMask((SVG.Mask) ref, obj);
            Bitmap maskedContent = processMaskBitmaps();
            this.canvas = this.canvasStack.pop();
            int save = this.canvas.save();
            new Matrix();
            this.canvas.setMatrix(matrix);
            this.canvas.drawBitmap(maskedContent, 0.0f, 0.0f, this.state.fillPaint);
            maskedContent.recycle();
            this.canvas.restore();
        }
        statePop();
    }

    private boolean requiresCompositing() {
        if (this.state.style.mask != null && !this.state.directRendering) {
            warn("Masks are not supported when using getPicture()", new Object[0]);
        }
        return this.state.style.opacity.floatValue() < 1.0f || (this.state.style.mask != null && this.state.directRendering);
    }

    private void duplicateCanvas() {
        Canvas canvas2;
        try {
            Bitmap newBM = Bitmap.createBitmap(this.canvas.getWidth(), this.canvas.getHeight(), Bitmap.Config.ARGB_8888);
            Bitmap push = this.bitmapStack.push(newBM);
            new Canvas(newBM);
            Canvas newCanvas = canvas2;
            newCanvas.setMatrix(this.canvas.getMatrix());
            this.canvas = newCanvas;
        } catch (OutOfMemoryError e) {
            error("Not enough memory to create temporary bitmaps for mask processing", new Object[0]);
            throw e;
        }
    }

    private Bitmap processMaskBitmaps() {
        Bitmap mask = this.bitmapStack.pop();
        Bitmap maskedContent = this.bitmapStack.pop();
        int w = mask.getWidth();
        int h = mask.getHeight();
        int[] maskBuf = new int[w];
        int[] maskedContentBuf = new int[w];
        for (int y = 0; y < h; y++) {
            mask.getPixels(maskBuf, 0, w, 0, y, w, 1);
            maskedContent.getPixels(maskedContentBuf, 0, w, 0, y, w, 1);
            for (int x = 0; x < w; x++) {
                int px = maskBuf[x];
                int b = px & 255;
                int g = (px >> 8) & 255;
                int r = (px >> 16) & 255;
                int a = (px >> 24) & 255;
                if (a == 0) {
                    maskedContentBuf[x] = 0;
                } else {
                    int maskAlpha = ((((r * LUMINANCE_TO_ALPHA_RED) + (g * LUMINANCE_TO_ALPHA_GREEN)) + (b * LUMINANCE_TO_ALPHA_BLUE)) * a) / 8355840;
                    int content = maskedContentBuf[x];
                    maskedContentBuf[x] = (content & 16777215) | (((((content >> 24) & 255) * maskAlpha) / 255) << 24);
                }
            }
            maskedContent.setPixels(maskedContentBuf, 0, w, 0, y, w, 1);
        }
        mask.recycle();
        return maskedContent;
    }

    private void render(SVG.Switch switchR) {
        SVG.Switch obj = switchR;
        debug("Switch render", new Object[0]);
        updateStyleForElement(this.state, obj);
        if (display()) {
            if (obj.transform != null) {
                this.canvas.concat(obj.transform);
            }
            checkForClipPath(obj);
            boolean compositing = pushLayer();
            renderSwitchChild(obj);
            if (compositing) {
                popLayer(obj);
            }
            updateParentBoundingBox(obj);
        }
    }

    private void renderSwitchChild(SVG.Switch obj) {
        Set<String> syslang;
        String deviceLanguage = Locale.getDefault().getLanguage();
        SVGExternalFileResolver fileResolver = this.document.getFileResolver();
        for (SVG.SvgObject child : obj.getChildren()) {
            if (child instanceof SVG.SvgConditional) {
                SVG.SvgConditional condObj = (SVG.SvgConditional) child;
                if (condObj.getRequiredExtensions() == null && ((syslang = condObj.getSystemLanguage()) == null || (!syslang.isEmpty() && syslang.contains(deviceLanguage)))) {
                    Set<String> reqfeat = condObj.getRequiredFeatures();
                    if (reqfeat != null) {
                        if (supportedFeatures == null) {
                            initialiseSupportedFeaturesMap();
                        }
                        if (reqfeat.isEmpty()) {
                            continue;
                        } else if (!supportedFeatures.containsAll(reqfeat)) {
                            continue;
                        }
                    }
                    Set<String> reqfmts = condObj.getRequiredFormats();
                    if (reqfmts != null) {
                        if (!reqfmts.isEmpty() && fileResolver != null) {
                            Iterator<String> it = reqfmts.iterator();
                            while (true) {
                                if (!it.hasNext()) {
                                    break;
                                }
                                if (!fileResolver.isFormatSupported(it.next())) {
                                    break;
                                }
                            }
                        }
                    }
                    Set<String> reqfonts = condObj.getRequiredFonts();
                    if (reqfonts != null) {
                        if (!reqfonts.isEmpty() && fileResolver != null) {
                            for (String fontName : reqfonts) {
                                if (fileResolver.resolveFont(fontName, this.state.style.fontWeight.intValue(), String.valueOf(this.state.style.fontStyle)) == null) {
                                }
                            }
                        }
                    }
                    render(child);
                    return;
                }
            }
        }
    }

    private static synchronized void initialiseSupportedFeaturesMap() {
        HashSet<String> hashSet;
        synchronized (SVGAndroidRenderer.class) {
            new HashSet();
            supportedFeatures = hashSet;
            boolean add = supportedFeatures.add("Structure");
            boolean add2 = supportedFeatures.add("BasicStructure");
            boolean add3 = supportedFeatures.add("ConditionalProcessing");
            boolean add4 = supportedFeatures.add("Image");
            boolean add5 = supportedFeatures.add("Style");
            boolean add6 = supportedFeatures.add("ViewportAttribute");
            boolean add7 = supportedFeatures.add("Shape");
            boolean add8 = supportedFeatures.add("BasicText");
            boolean add9 = supportedFeatures.add("PaintAttribute");
            boolean add10 = supportedFeatures.add("BasicPaintAttribute");
            boolean add11 = supportedFeatures.add("OpacityAttribute");
            boolean add12 = supportedFeatures.add("BasicGraphicsAttribute");
            boolean add13 = supportedFeatures.add(MapFactory.MapFeatureType.TYPE_MARKER);
            boolean add14 = supportedFeatures.add("Gradient");
            boolean add15 = supportedFeatures.add("Pattern");
            boolean add16 = supportedFeatures.add("Clip");
            boolean add17 = supportedFeatures.add("BasicClip");
            boolean add18 = supportedFeatures.add("Mask");
            boolean add19 = supportedFeatures.add("View");
        }
    }

    private void render(SVG.Use use) {
        Matrix matrix;
        SVG.Length length;
        SVG.Length length2;
        SVG.Length length3;
        SVG.Length _h;
        SVG.Use obj = use;
        debug("Use render", new Object[0]);
        if (obj.width != null && obj.width.isZero()) {
            return;
        }
        if (obj.height == null || !obj.height.isZero()) {
            updateStyleForElement(this.state, obj);
            if (display()) {
                SVG.SvgObject ref = obj.document.resolveIRI(obj.href);
                if (ref == null) {
                    error("Use reference '%s' not found", obj.href);
                    return;
                }
                if (obj.transform != null) {
                    this.canvas.concat(obj.transform);
                }
                new Matrix();
                Matrix m = matrix;
                boolean preTranslate = m.preTranslate(obj.f359x != null ? obj.f359x.floatValueX(this) : 0.0f, obj.f360y != null ? obj.f360y.floatValueY(this) : 0.0f);
                this.canvas.concat(m);
                checkForClipPath(obj);
                boolean compositing = pushLayer();
                parentPush(obj);
                if (ref instanceof SVG.Svg) {
                    statePush();
                    SVG.Svg svgElem = (SVG.Svg) ref;
                    render(svgElem, obj.width != null ? obj.width : svgElem.width, obj.height != null ? obj.height : svgElem.height);
                    statePop();
                } else if (ref instanceof SVG.Symbol) {
                    if (obj.width != null) {
                        length2 = obj.width;
                    } else {
                        length2 = length;
                        new SVG.Length(100.0f, SVG.Unit.percent);
                    }
                    SVG.Length _w = length2;
                    if (obj.height != null) {
                        _h = obj.height;
                    } else {
                        _h = length3;
                        new SVG.Length(100.0f, SVG.Unit.percent);
                    }
                    statePush();
                    render((SVG.Symbol) ref, _w, _h);
                    statePop();
                } else {
                    render(ref);
                }
                parentPop();
                if (compositing) {
                    popLayer(obj);
                }
                updateParentBoundingBox(obj);
            }
        }
    }

    private void render(SVG.Path path) {
        PathConverter pathConverter;
        SVG.Path obj = path;
        debug("Path render", new Object[0]);
        if (obj.f328d != null) {
            updateStyleForElement(this.state, obj);
            if (!display() || !visible()) {
                return;
            }
            if (this.state.hasStroke || this.state.hasFill) {
                if (obj.transform != null) {
                    this.canvas.concat(obj.transform);
                }
                new PathConverter(this, obj.f328d);
                Path path2 = pathConverter.getPath();
                if (obj.boundingBox == null) {
                    obj.boundingBox = calculatePathBounds(path2);
                }
                updateParentBoundingBox(obj);
                checkForGradientsAndPatterns(obj);
                checkForClipPath(obj);
                boolean compositing = pushLayer();
                if (this.state.hasFill) {
                    path2.setFillType(getFillTypeFromState());
                    doFilledPath(obj, path2);
                }
                if (this.state.hasStroke) {
                    doStroke(path2);
                }
                renderMarkers(obj);
                if (compositing) {
                    popLayer(obj);
                }
            }
        }
    }

    private SVG.Box calculatePathBounds(Path path) {
        RectF rectF;
        SVG.Box box;
        new RectF();
        RectF pathBounds = rectF;
        path.computeBounds(pathBounds, true);
        new SVG.Box(pathBounds.left, pathBounds.top, pathBounds.width(), pathBounds.height());
        return box;
    }

    private void render(SVG.Rect rect) {
        SVG.Rect obj = rect;
        debug("Rect render", new Object[0]);
        if (obj.width != null && obj.height != null && !obj.width.isZero() && !obj.height.isZero()) {
            updateStyleForElement(this.state, obj);
            if (display() && visible()) {
                if (obj.transform != null) {
                    this.canvas.concat(obj.transform);
                }
                Path path = makePathAndBoundingBox(obj);
                updateParentBoundingBox(obj);
                checkForGradientsAndPatterns(obj);
                checkForClipPath(obj);
                boolean compositing = pushLayer();
                if (this.state.hasFill) {
                    doFilledPath(obj, path);
                }
                if (this.state.hasStroke) {
                    doStroke(path);
                }
                if (compositing) {
                    popLayer(obj);
                }
            }
        }
    }

    private void render(SVG.Circle circle) {
        SVG.Circle obj = circle;
        debug("Circle render", new Object[0]);
        if (obj.f315r != null && !obj.f315r.isZero()) {
            updateStyleForElement(this.state, obj);
            if (display() && visible()) {
                if (obj.transform != null) {
                    this.canvas.concat(obj.transform);
                }
                Path path = makePathAndBoundingBox(obj);
                updateParentBoundingBox(obj);
                checkForGradientsAndPatterns(obj);
                checkForClipPath(obj);
                boolean compositing = pushLayer();
                if (this.state.hasFill) {
                    doFilledPath(obj, path);
                }
                if (this.state.hasStroke) {
                    doStroke(path);
                }
                if (compositing) {
                    popLayer(obj);
                }
            }
        }
    }

    private void render(SVG.Ellipse ellipse) {
        SVG.Ellipse obj = ellipse;
        debug("Ellipse render", new Object[0]);
        if (obj.f318rx != null && obj.f319ry != null && !obj.f318rx.isZero() && !obj.f319ry.isZero()) {
            updateStyleForElement(this.state, obj);
            if (display() && visible()) {
                if (obj.transform != null) {
                    this.canvas.concat(obj.transform);
                }
                Path path = makePathAndBoundingBox(obj);
                updateParentBoundingBox(obj);
                checkForGradientsAndPatterns(obj);
                checkForClipPath(obj);
                boolean compositing = pushLayer();
                if (this.state.hasFill) {
                    doFilledPath(obj, path);
                }
                if (this.state.hasStroke) {
                    doStroke(path);
                }
                if (compositing) {
                    popLayer(obj);
                }
            }
        }
    }

    private void render(SVG.Line line) {
        SVG.Line obj = line;
        debug("Line render", new Object[0]);
        updateStyleForElement(this.state, obj);
        if (display() && visible() && this.state.hasStroke) {
            if (obj.transform != null) {
                this.canvas.concat(obj.transform);
            }
            Path path = makePathAndBoundingBox(obj);
            updateParentBoundingBox(obj);
            checkForGradientsAndPatterns(obj);
            checkForClipPath(obj);
            boolean compositing = pushLayer();
            doStroke(path);
            renderMarkers(obj);
            if (compositing) {
                popLayer(obj);
            }
        }
    }

    private List<MarkerVector> calculateMarkerPositions(SVG.Line line) {
        List<MarkerVector> list;
        Object obj;
        Object obj2;
        SVG.Line obj3 = line;
        float _x1 = obj3.f322x1 != null ? obj3.f322x1.floatValueX(this) : 0.0f;
        float _y1 = obj3.f324y1 != null ? obj3.f324y1.floatValueY(this) : 0.0f;
        float _x2 = obj3.f323x2 != null ? obj3.f323x2.floatValueX(this) : 0.0f;
        float _y2 = obj3.f325y2 != null ? obj3.f325y2.floatValueY(this) : 0.0f;
        new ArrayList(2);
        List<MarkerVector> markers = list;
        new MarkerVector(this, _x1, _y1, _x2 - _x1, _y2 - _y1);
        boolean add = markers.add(obj);
        new MarkerVector(this, _x2, _y2, _x2 - _x1, _y2 - _y1);
        boolean add2 = markers.add(obj2);
        return markers;
    }

    private void render(SVG.PolyLine polyLine) {
        SVG.PolyLine obj = polyLine;
        debug("PolyLine render", new Object[0]);
        updateStyleForElement(this.state, obj);
        if (!display() || !visible()) {
            return;
        }
        if (this.state.hasStroke || this.state.hasFill) {
            if (obj.transform != null) {
                this.canvas.concat(obj.transform);
            }
            if (obj.points.length >= 2) {
                Path path = makePathAndBoundingBox(obj);
                updateParentBoundingBox(obj);
                checkForGradientsAndPatterns(obj);
                checkForClipPath(obj);
                boolean compositing = pushLayer();
                if (this.state.hasFill) {
                    doFilledPath(obj, path);
                }
                if (this.state.hasStroke) {
                    doStroke(path);
                }
                renderMarkers(obj);
                if (compositing) {
                    popLayer(obj);
                }
            }
        }
    }

    private List<MarkerVector> calculateMarkerPositions(SVG.PolyLine polyLine) {
        List<MarkerVector> list;
        MarkerVector markerVector;
        MarkerVector markerVector2;
        MarkerVector newPos;
        SVG.PolyLine obj = polyLine;
        int numPoints = obj.points.length;
        if (numPoints < 2) {
            return null;
        }
        new ArrayList();
        List<MarkerVector> markers = list;
        new MarkerVector(this, obj.points[0], obj.points[1], 0.0f, 0.0f);
        MarkerVector lastPos = markerVector;
        float x = 0.0f;
        float y = 0.0f;
        for (int i = 2; i < numPoints; i += 2) {
            x = obj.points[i];
            y = obj.points[i + 1];
            lastPos.add(x, y);
            boolean add = markers.add(lastPos);
            new MarkerVector(this, x, y, x - lastPos.f363x, y - lastPos.f364y);
            lastPos = newPos;
        }
        if (!(obj instanceof SVG.Polygon)) {
            boolean add2 = markers.add(lastPos);
        } else if (!(x == obj.points[0] || y == obj.points[1])) {
            float x2 = obj.points[0];
            float y2 = obj.points[1];
            lastPos.add(x2, y2);
            boolean add3 = markers.add(lastPos);
            new MarkerVector(this, x2, y2, x2 - lastPos.f363x, y2 - lastPos.f364y);
            MarkerVector newPos2 = markerVector2;
            newPos2.add(markers.get(0));
            boolean add4 = markers.add(newPos2);
            MarkerVector markerVector3 = markers.set(0, newPos2);
        }
        return markers;
    }

    private void render(SVG.Polygon polygon) {
        SVG.Polygon obj = polygon;
        debug("Polygon render", new Object[0]);
        updateStyleForElement(this.state, obj);
        if (!display() || !visible()) {
            return;
        }
        if (this.state.hasStroke || this.state.hasFill) {
            if (obj.transform != null) {
                this.canvas.concat(obj.transform);
            }
            if (obj.points.length >= 2) {
                Path path = makePathAndBoundingBox((SVG.PolyLine) obj);
                updateParentBoundingBox(obj);
                checkForGradientsAndPatterns(obj);
                checkForClipPath(obj);
                boolean compositing = pushLayer();
                if (this.state.hasFill) {
                    doFilledPath(obj, path);
                }
                if (this.state.hasStroke) {
                    doStroke(path);
                }
                renderMarkers(obj);
                if (compositing) {
                    popLayer(obj);
                }
            }
        }
    }

    private void render(SVG.Text text) {
        TextProcessor textProcessor;
        TextBoundsCalculator textBoundsCalculator;
        SVG.Box box;
        SVG.Text obj = text;
        debug("Text render", new Object[0]);
        updateStyleForElement(this.state, obj);
        if (display()) {
            if (obj.transform != null) {
                this.canvas.concat(obj.transform);
            }
            float x = (obj.f349x == null || obj.f349x.size() == 0) ? 0.0f : ((SVG.Length) obj.f349x.get(0)).floatValueX(this);
            float y = (obj.f350y == null || obj.f350y.size() == 0) ? 0.0f : ((SVG.Length) obj.f350y.get(0)).floatValueY(this);
            float dx = (obj.f347dx == null || obj.f347dx.size() == 0) ? 0.0f : ((SVG.Length) obj.f347dx.get(0)).floatValueX(this);
            float dy = (obj.f348dy == null || obj.f348dy.size() == 0) ? 0.0f : ((SVG.Length) obj.f348dy.get(0)).floatValueY(this);
            SVG.Style.TextAnchor anchor = getAnchorPosition();
            if (anchor != SVG.Style.TextAnchor.Start) {
                float textWidth = calculateTextWidth(obj);
                if (anchor == SVG.Style.TextAnchor.Middle) {
                    x -= textWidth / 2.0f;
                } else {
                    x -= textWidth;
                }
            }
            if (obj.boundingBox == null) {
                new TextBoundsCalculator(this, x, y);
                TextBoundsCalculator proc = textBoundsCalculator;
                enumerateTextSpans(obj, proc);
                new SVG.Box(proc.bbox.left, proc.bbox.top, proc.bbox.width(), proc.bbox.height());
                obj.boundingBox = box;
            }
            updateParentBoundingBox(obj);
            checkForGradientsAndPatterns(obj);
            checkForClipPath(obj);
            boolean compositing = pushLayer();
            new PlainTextDrawer(this, x + dx, y + dy);
            enumerateTextSpans(obj, textProcessor);
            if (compositing) {
                popLayer(obj);
            }
        }
    }

    private SVG.Style.TextAnchor getAnchorPosition() {
        if (this.state.style.direction == SVG.Style.TextDirection.LTR || this.state.style.textAnchor == SVG.Style.TextAnchor.Middle) {
            return this.state.style.textAnchor;
        }
        return this.state.style.textAnchor == SVG.Style.TextAnchor.Start ? SVG.Style.TextAnchor.End : SVG.Style.TextAnchor.Start;
    }

    private class PlainTextDrawer extends TextProcessor {
        final /* synthetic */ SVGAndroidRenderer this$0;

        /* renamed from: x */
        public float f365x;

        /* renamed from: y */
        public float f366y;

        /* JADX WARNING: Illegal instructions before constructor call */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public PlainTextDrawer(com.caverock.androidsvg.SVGAndroidRenderer r8, float r9, float r10) {
            /*
                r7 = this;
                r0 = r7
                r1 = r8
                r2 = r9
                r3 = r10
                r4 = r0
                r5 = r1
                r4.this$0 = r5
                r4 = r0
                r5 = r1
                r6 = 0
                r4.<init>(r5, r6)
                r4 = r0
                r5 = r2
                r4.f365x = r5
                r4 = r0
                r5 = r3
                r4.f366y = r5
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.caverock.androidsvg.SVGAndroidRenderer.PlainTextDrawer.<init>(com.caverock.androidsvg.SVGAndroidRenderer, float, float):void");
        }

        public void processText(String str) {
            String text = str;
            SVGAndroidRenderer.debug("TextSequence render", new Object[0]);
            if (this.this$0.visible()) {
                if (this.this$0.state.hasFill) {
                    this.this$0.canvas.drawText(text, this.f365x, this.f366y, this.this$0.state.fillPaint);
                }
                if (this.this$0.state.hasStroke) {
                    this.this$0.canvas.drawText(text, this.f365x, this.f366y, this.this$0.state.strokePaint);
                }
            }
            this.f365x += this.this$0.state.fillPaint.measureText(text);
        }
    }

    private abstract class TextProcessor {
        final /* synthetic */ SVGAndroidRenderer this$0;

        public abstract void processText(String str);

        private TextProcessor(SVGAndroidRenderer sVGAndroidRenderer) {
            this.this$0 = sVGAndroidRenderer;
        }

        /* JADX INFO: this call moved to the top of the method (can break code semantics) */
        /* synthetic */ TextProcessor(SVGAndroidRenderer x0, C15161 r7) {
            this(x0);
            C15161 r2 = r7;
        }

        public boolean doTextContainer(SVG.TextContainer textContainer) {
            SVG.TextContainer textContainer2 = textContainer;
            return true;
        }
    }

    private void enumerateTextSpans(SVG.TextContainer textContainer, TextProcessor textProcessor) {
        SVG.TextContainer obj = textContainer;
        TextProcessor textprocessor = textProcessor;
        if (display()) {
            Iterator<SVG.SvgObject> iter = obj.children.iterator();
            boolean z = true;
            while (true) {
                boolean isFirstChild = z;
                if (iter.hasNext()) {
                    SVG.SvgObject child = iter.next();
                    if (child instanceof SVG.TextSequence) {
                        textprocessor.processText(textXMLSpaceTransform(((SVG.TextSequence) child).text, isFirstChild, !iter.hasNext()));
                    } else {
                        processTextChild(child, textprocessor);
                    }
                    z = false;
                } else {
                    return;
                }
            }
        }
    }

    private void processTextChild(SVG.SvgObject svgObject, TextProcessor textProcessor) {
        StringBuilder sb;
        float f;
        SVG.SvgObject obj = svgObject;
        TextProcessor textprocessor = textProcessor;
        if (textprocessor.doTextContainer((SVG.TextContainer) obj)) {
            if (obj instanceof SVG.TextPath) {
                statePush();
                renderTextPath((SVG.TextPath) obj);
                statePop();
            } else if (obj instanceof SVG.TSpan) {
                debug("TSpan render", new Object[0]);
                statePush();
                SVG.TSpan tspan = (SVG.TSpan) obj;
                updateStyleForElement(this.state, tspan);
                if (display()) {
                    float x = 0.0f;
                    float y = 0.0f;
                    float dx = 0.0f;
                    float dy = 0.0f;
                    if (textprocessor instanceof PlainTextDrawer) {
                        x = (tspan.f349x == null || tspan.f349x.size() == 0) ? ((PlainTextDrawer) textprocessor).f365x : ((SVG.Length) tspan.f349x.get(0)).floatValueX(this);
                        y = (tspan.f350y == null || tspan.f350y.size() == 0) ? ((PlainTextDrawer) textprocessor).f366y : ((SVG.Length) tspan.f350y.get(0)).floatValueY(this);
                        dx = (tspan.f347dx == null || tspan.f347dx.size() == 0) ? 0.0f : ((SVG.Length) tspan.f347dx.get(0)).floatValueX(this);
                        if (tspan.f348dy == null || tspan.f348dy.size() == 0) {
                            f = 0.0f;
                        } else {
                            f = ((SVG.Length) tspan.f348dy.get(0)).floatValueY(this);
                        }
                        dy = f;
                    }
                    checkForGradientsAndPatterns((SVG.SvgElement) tspan.getTextRoot());
                    if (textprocessor instanceof PlainTextDrawer) {
                        ((PlainTextDrawer) textprocessor).f365x = x + dx;
                        ((PlainTextDrawer) textprocessor).f366y = y + dy;
                    }
                    boolean compositing = pushLayer();
                    enumerateTextSpans(tspan, textprocessor);
                    if (compositing) {
                        popLayer(tspan);
                    }
                }
                statePop();
            } else if (obj instanceof SVG.TRef) {
                statePush();
                SVG.TRef tref = (SVG.TRef) obj;
                updateStyleForElement(this.state, tref);
                if (display()) {
                    checkForGradientsAndPatterns((SVG.SvgElement) tref.getTextRoot());
                    SVG.SvgObject ref = obj.document.resolveIRI(tref.href);
                    if (ref == null || !(ref instanceof SVG.TextContainer)) {
                        error("Tref reference '%s' not found", tref.href);
                    } else {
                        new StringBuilder();
                        StringBuilder str = sb;
                        extractRawText((SVG.TextContainer) ref, str);
                        if (str.length() > 0) {
                            textprocessor.processText(str.toString());
                        }
                    }
                }
                statePop();
            }
        }
    }

    private void renderTextPath(SVG.TextPath textPath) {
        PathConverter pathConverter;
        PathMeasure measure;
        TextProcessor textProcessor;
        SVG.TextPath obj = textPath;
        debug("TextPath render", new Object[0]);
        updateStyleForElement(this.state, obj);
        if (display() && visible()) {
            SVG.SvgObject ref = obj.document.resolveIRI(obj.href);
            if (ref == null) {
                error("TextPath reference '%s' not found", obj.href);
                return;
            }
            SVG.Path pathObj = (SVG.Path) ref;
            new PathConverter(this, pathObj.f328d);
            Path path = pathConverter.getPath();
            if (pathObj.transform != null) {
                path.transform(pathObj.transform);
            }
            new PathMeasure(path, false);
            float startOffset = obj.startOffset != null ? obj.startOffset.floatValue(this, measure.getLength()) : 0.0f;
            SVG.Style.TextAnchor anchor = getAnchorPosition();
            if (anchor != SVG.Style.TextAnchor.Start) {
                float textWidth = calculateTextWidth(obj);
                if (anchor == SVG.Style.TextAnchor.Middle) {
                    startOffset -= textWidth / 2.0f;
                } else {
                    startOffset -= textWidth;
                }
            }
            checkForGradientsAndPatterns((SVG.SvgElement) obj.getTextRoot());
            boolean compositing = pushLayer();
            new PathTextDrawer(this, path, startOffset, 0.0f);
            enumerateTextSpans(obj, textProcessor);
            if (compositing) {
                popLayer(obj);
            }
        }
    }

    private class PathTextDrawer extends PlainTextDrawer {
        private Path path;
        final /* synthetic */ SVGAndroidRenderer this$0;

        /* JADX WARNING: Illegal instructions before constructor call */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public PathTextDrawer(com.caverock.androidsvg.SVGAndroidRenderer r10, android.graphics.Path r11, float r12, float r13) {
            /*
                r9 = this;
                r0 = r9
                r1 = r10
                r2 = r11
                r3 = r12
                r4 = r13
                r5 = r0
                r6 = r1
                r5.this$0 = r6
                r5 = r0
                r6 = r1
                r7 = r3
                r8 = r4
                r5.<init>(r6, r7, r8)
                r5 = r0
                r6 = r2
                r5.path = r6
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.caverock.androidsvg.SVGAndroidRenderer.PathTextDrawer.<init>(com.caverock.androidsvg.SVGAndroidRenderer, android.graphics.Path, float, float):void");
        }

        public void processText(String str) {
            String text = str;
            if (this.this$0.visible()) {
                if (this.this$0.state.hasFill) {
                    this.this$0.canvas.drawTextOnPath(text, this.path, this.f365x, this.f366y, this.this$0.state.fillPaint);
                }
                if (this.this$0.state.hasStroke) {
                    this.this$0.canvas.drawTextOnPath(text, this.path, this.f365x, this.f366y, this.this$0.state.strokePaint);
                }
            }
            this.f365x += this.this$0.state.fillPaint.measureText(text);
        }
    }

    private float calculateTextWidth(SVG.TextContainer parentTextObj) {
        TextWidthCalculator textWidthCalculator;
        new TextWidthCalculator(this, (C15161) null);
        TextWidthCalculator proc = textWidthCalculator;
        enumerateTextSpans(parentTextObj, proc);
        return proc.f371x;
    }

    private class TextWidthCalculator extends TextProcessor {
        final /* synthetic */ SVGAndroidRenderer this$0;

        /* renamed from: x */
        public float f371x;

        /* JADX WARNING: Illegal instructions before constructor call */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        private TextWidthCalculator(com.caverock.androidsvg.SVGAndroidRenderer r6) {
            /*
                r5 = this;
                r0 = r5
                r1 = r6
                r2 = r0
                r3 = r1
                r2.this$0 = r3
                r2 = r0
                r3 = r1
                r4 = 0
                r2.<init>(r3, r4)
                r2 = r0
                r3 = 0
                r2.f371x = r3
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.caverock.androidsvg.SVGAndroidRenderer.TextWidthCalculator.<init>(com.caverock.androidsvg.SVGAndroidRenderer):void");
        }

        /* JADX INFO: this call moved to the top of the method (can break code semantics) */
        /* synthetic */ TextWidthCalculator(SVGAndroidRenderer x0, C15161 r7) {
            this(x0);
            C15161 r2 = r7;
        }

        public void processText(String text) {
            this.f371x += this.this$0.state.fillPaint.measureText(text);
        }
    }

    private class TextBoundsCalculator extends TextProcessor {
        RectF bbox;
        final /* synthetic */ SVGAndroidRenderer this$0;

        /* renamed from: x */
        float f369x;

        /* renamed from: y */
        float f370y;

        /* JADX WARNING: Illegal instructions before constructor call */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public TextBoundsCalculator(com.caverock.androidsvg.SVGAndroidRenderer r9, float r10, float r11) {
            /*
                r8 = this;
                r0 = r8
                r1 = r9
                r2 = r10
                r3 = r11
                r4 = r0
                r5 = r1
                r4.this$0 = r5
                r4 = r0
                r5 = r1
                r6 = 0
                r4.<init>(r5, r6)
                r4 = r0
                android.graphics.RectF r5 = new android.graphics.RectF
                r7 = r5
                r5 = r7
                r6 = r7
                r6.<init>()
                r4.bbox = r5
                r4 = r0
                r5 = r2
                r4.f369x = r5
                r4 = r0
                r5 = r3
                r4.f370y = r5
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.caverock.androidsvg.SVGAndroidRenderer.TextBoundsCalculator.<init>(com.caverock.androidsvg.SVGAndroidRenderer, float, float):void");
        }

        public boolean doTextContainer(SVG.TextContainer textContainer) {
            PathConverter pathConverter;
            RectF rectF;
            SVG.TextContainer obj = textContainer;
            if (!(obj instanceof SVG.TextPath)) {
                return true;
            }
            SVG.TextPath tpath = (SVG.TextPath) obj;
            SVG.SvgObject ref = obj.document.resolveIRI(tpath.href);
            if (ref == null) {
                SVGAndroidRenderer.error("TextPath path reference '%s' not found", tpath.href);
                return false;
            }
            SVG.Path pathObj = (SVG.Path) ref;
            new PathConverter(this.this$0, pathObj.f328d);
            Path path = pathConverter.getPath();
            if (pathObj.transform != null) {
                path.transform(pathObj.transform);
            }
            new RectF();
            RectF pathBounds = rectF;
            path.computeBounds(pathBounds, true);
            this.bbox.union(pathBounds);
            return false;
        }

        public void processText(String str) {
            Rect rect;
            RectF rectF;
            String text = str;
            if (this.this$0.visible()) {
                new Rect();
                Rect rect2 = rect;
                this.this$0.state.fillPaint.getTextBounds(text, 0, text.length(), rect2);
                new RectF(rect2);
                RectF textbounds = rectF;
                textbounds.offset(this.f369x, this.f370y);
                this.bbox.union(textbounds);
            }
            this.f369x += this.this$0.state.fillPaint.measureText(text);
        }
    }

    private void extractRawText(SVG.TextContainer parent, StringBuilder sb) {
        StringBuilder str = sb;
        Iterator<SVG.SvgObject> iter = parent.children.iterator();
        boolean z = true;
        while (true) {
            boolean isFirstChild = z;
            if (iter.hasNext()) {
                SVG.SvgObject child = iter.next();
                if (child instanceof SVG.TextContainer) {
                    extractRawText((SVG.TextContainer) child, str);
                } else if (child instanceof SVG.TextSequence) {
                    StringBuilder append = str.append(textXMLSpaceTransform(((SVG.TextSequence) child).text, isFirstChild, !iter.hasNext()));
                }
                z = false;
            } else {
                return;
            }
        }
    }

    private String textXMLSpaceTransform(String str, boolean z, boolean z2) {
        String text = str;
        boolean isFirstChild = z;
        boolean isLastChild = z2;
        if (this.state.spacePreserve) {
            return text.replaceAll("[\\n\\t]", " ");
        }
        String text2 = text.replaceAll("\\n", "").replaceAll("\\t", " ");
        if (isFirstChild) {
            text2 = text2.replaceAll("^\\s+", "");
        }
        if (isLastChild) {
            text2 = text2.replaceAll("\\s+$", "");
        }
        return text2.replaceAll("\\s{2,}", " ");
    }

    private void render(SVG.Symbol symbol, SVG.Length length, SVG.Length length2) {
        SVG.Box box;
        SVG.Symbol obj = symbol;
        SVG.Length width = length;
        SVG.Length height = length2;
        debug("Symbol render", new Object[0]);
        if (width != null && width.isZero()) {
            return;
        }
        if (height == null || !height.isZero()) {
            PreserveAspectRatio positioning = obj.preserveAspectRatio != null ? obj.preserveAspectRatio : PreserveAspectRatio.LETTERBOX;
            updateStyleForElement(this.state, obj);
            float _w = width != null ? width.floatValueX(this) : this.state.viewPort.width;
            float _h = height != null ? height.floatValueX(this) : this.state.viewPort.height;
            RendererState rendererState = this.state;
            new SVG.Box(0.0f, 0.0f, _w, _h);
            rendererState.viewPort = box;
            if (!this.state.style.overflow.booleanValue()) {
                setClipRect(this.state.viewPort.minX, this.state.viewPort.minY, this.state.viewPort.width, this.state.viewPort.height);
            }
            if (obj.viewBox != null) {
                this.canvas.concat(calculateViewBoxTransform(this.state.viewPort, obj.viewBox, positioning));
                this.state.viewBox = obj.viewBox;
            }
            boolean compositing = pushLayer();
            renderChildren(obj, true);
            if (compositing) {
                popLayer(obj);
            }
            updateParentBoundingBox(obj);
        }
    }

    private void render(SVG.Image image) {
        SVG.Box box;
        SVG.Box box2;
        Paint paint;
        SVG.Image obj = image;
        debug("Image render", new Object[0]);
        if (obj.width != null && !obj.width.isZero() && obj.height != null && !obj.height.isZero() && obj.href != null) {
            PreserveAspectRatio positioning = obj.preserveAspectRatio != null ? obj.preserveAspectRatio : PreserveAspectRatio.LETTERBOX;
            Bitmap image2 = checkForImageDataURL(obj.href);
            if (image2 == null) {
                SVGExternalFileResolver fileResolver = this.document.getFileResolver();
                if (fileResolver != null) {
                    image2 = fileResolver.resolveImage(obj.href);
                } else {
                    return;
                }
            }
            if (image2 == null) {
                error("Could not locate image '%s'", obj.href);
                return;
            }
            updateStyleForElement(this.state, obj);
            if (display() && visible()) {
                if (obj.transform != null) {
                    this.canvas.concat(obj.transform);
                }
                float _x = obj.f320x != null ? obj.f320x.floatValueX(this) : 0.0f;
                float _y = obj.f321y != null ? obj.f321y.floatValueY(this) : 0.0f;
                float _w = obj.width.floatValueX(this);
                float _h = obj.height.floatValueX(this);
                RendererState rendererState = this.state;
                new SVG.Box(_x, _y, _w, _h);
                rendererState.viewPort = box;
                if (!this.state.style.overflow.booleanValue()) {
                    setClipRect(this.state.viewPort.minX, this.state.viewPort.minY, this.state.viewPort.width, this.state.viewPort.height);
                }
                new SVG.Box(0.0f, 0.0f, (float) image2.getWidth(), (float) image2.getHeight());
                obj.boundingBox = box2;
                this.canvas.concat(calculateViewBoxTransform(this.state.viewPort, obj.boundingBox, positioning));
                updateParentBoundingBox(obj);
                checkForClipPath(obj);
                boolean compositing = pushLayer();
                viewportFill();
                new Paint();
                this.canvas.drawBitmap(image2, 0.0f, 0.0f, paint);
                if (compositing) {
                    popLayer(obj);
                }
            }
        }
    }

    private Bitmap checkForImageDataURL(String str) {
        String url = str;
        if (!url.startsWith("data:")) {
            return null;
        }
        if (url.length() < 14) {
            return null;
        }
        int comma = url.indexOf(44);
        if (comma == -1 || comma < 12) {
            return null;
        }
        if (!";base64".equals(url.substring(comma - 7, comma))) {
            return null;
        }
        byte[] imageData = Base64.decode(url.substring(comma + 1), 0);
        return BitmapFactory.decodeByteArray(imageData, 0, imageData.length);
    }

    private boolean display() {
        if (this.state.style.display != null) {
            return this.state.style.display.booleanValue();
        }
        return true;
    }

    /* access modifiers changed from: private */
    public boolean visible() {
        if (this.state.style.visibility != null) {
            return this.state.style.visibility.booleanValue();
        }
        return true;
    }

    private Matrix calculateViewBoxTransform(SVG.Box box, SVG.Box box2, PreserveAspectRatio preserveAspectRatio) {
        Matrix matrix;
        SVG.Box viewPort = box;
        SVG.Box viewBox = box2;
        PreserveAspectRatio positioning = preserveAspectRatio;
        new Matrix();
        Matrix m = matrix;
        if (positioning == null || positioning.getAlignment() == null) {
            return m;
        }
        float xScale = viewPort.width / viewBox.width;
        float yScale = viewPort.height / viewBox.height;
        float xOffset = -viewBox.minX;
        float yOffset = -viewBox.minY;
        if (positioning.equals(PreserveAspectRatio.STRETCH)) {
            boolean preTranslate = m.preTranslate(viewPort.minX, viewPort.minY);
            boolean preScale = m.preScale(xScale, yScale);
            boolean preTranslate2 = m.preTranslate(xOffset, yOffset);
            return m;
        }
        float scale = positioning.getScale() == PreserveAspectRatio.Scale.Slice ? Math.max(xScale, yScale) : Math.min(xScale, yScale);
        float imageW = viewPort.width / scale;
        float imageH = viewPort.height / scale;
        switch (positioning.getAlignment()) {
            case XMidYMin:
            case XMidYMid:
            case XMidYMax:
                xOffset -= (viewBox.width - imageW) / 2.0f;
                break;
            case XMaxYMin:
            case XMaxYMid:
            case XMaxYMax:
                xOffset -= viewBox.width - imageW;
                break;
        }
        switch (positioning.getAlignment()) {
            case XMidYMid:
            case XMaxYMid:
            case XMinYMid:
                yOffset -= (viewBox.height - imageH) / 2.0f;
                break;
            case XMidYMax:
            case XMaxYMax:
            case XMinYMax:
                yOffset -= viewBox.height - imageH;
                break;
        }
        boolean preTranslate3 = m.preTranslate(viewPort.minX, viewPort.minY);
        boolean preScale2 = m.preScale(scale, scale);
        boolean preTranslate4 = m.preTranslate(xOffset, yOffset);
        return m;
    }

    private boolean isSpecified(SVG.Style style, long flag) {
        return (style.specifiedFlags & flag) != 0;
    }

    private void updateStyle(RendererState rendererState, SVG.Style style) {
        PathEffect pathEffect;
        StringBuilder sb;
        RendererState state2 = rendererState;
        SVG.Style style2 = style;
        if (isSpecified(style2, PlaybackStateCompat.ACTION_SKIP_TO_QUEUE_ITEM)) {
            state2.style.color = style2.color;
        }
        if (isSpecified(style2, PlaybackStateCompat.ACTION_PLAY_FROM_SEARCH)) {
            state2.style.opacity = style2.opacity;
        }
        if (isSpecified(style2, 1)) {
            state2.style.fill = style2.fill;
            state2.hasFill = style2.fill != null;
        }
        if (isSpecified(style2, 4)) {
            state2.style.fillOpacity = style2.fillOpacity;
            new StringBuilder();
            int d = Log.d("updateStyle", sb.append("fillOpacity = ").append(state2.style.fillOpacity).toString());
        }
        if (isSpecified(style2, 6149)) {
            setPaintColour(state2, true, state2.style.fill);
        }
        if (isSpecified(style2, 2)) {
            state2.style.fillRule = style2.fillRule;
        }
        if (isSpecified(style2, 8)) {
            state2.style.stroke = style2.stroke;
            state2.hasStroke = style2.stroke != null;
        }
        if (isSpecified(style2, 16)) {
            state2.style.strokeOpacity = style2.strokeOpacity;
        }
        if (isSpecified(style2, 6168)) {
            setPaintColour(state2, false, state2.style.stroke);
        }
        if (isSpecified(style2, 34359738368L)) {
            state2.style.vectorEffect = style2.vectorEffect;
        }
        if (isSpecified(style2, 32)) {
            state2.style.strokeWidth = style2.strokeWidth;
            state2.strokePaint.setStrokeWidth(state2.style.strokeWidth.floatValue(this));
        }
        if (isSpecified(style2, 64)) {
            state2.style.strokeLineCap = style2.strokeLineCap;
            switch (style2.strokeLineCap) {
                case Butt:
                    state2.strokePaint.setStrokeCap(Paint.Cap.BUTT);
                    break;
                case Round:
                    state2.strokePaint.setStrokeCap(Paint.Cap.ROUND);
                    break;
                case Square:
                    state2.strokePaint.setStrokeCap(Paint.Cap.SQUARE);
                    break;
            }
        }
        if (isSpecified(style2, 128)) {
            state2.style.strokeLineJoin = style2.strokeLineJoin;
            switch (style2.strokeLineJoin) {
                case Miter:
                    state2.strokePaint.setStrokeJoin(Paint.Join.MITER);
                    break;
                case Round:
                    state2.strokePaint.setStrokeJoin(Paint.Join.ROUND);
                    break;
                case Bevel:
                    state2.strokePaint.setStrokeJoin(Paint.Join.BEVEL);
                    break;
            }
        }
        if (isSpecified(style2, 256)) {
            state2.style.strokeMiterLimit = style2.strokeMiterLimit;
            state2.strokePaint.setStrokeMiter(style2.strokeMiterLimit.floatValue());
        }
        if (isSpecified(style2, 512)) {
            state2.style.strokeDashArray = style2.strokeDashArray;
        }
        if (isSpecified(style2, PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID)) {
            state2.style.strokeDashOffset = style2.strokeDashOffset;
        }
        if (isSpecified(style2, 1536)) {
            if (state2.style.strokeDashArray == null) {
                PathEffect pathEffect2 = state2.strokePaint.setPathEffect((PathEffect) null);
            } else {
                float intervalSum = 0.0f;
                int n = state2.style.strokeDashArray.length;
                int arrayLen = n % 2 == 0 ? n : n * 2;
                float[] intervals = new float[arrayLen];
                for (int i = 0; i < arrayLen; i++) {
                    intervals[i] = state2.style.strokeDashArray[i % n].floatValue(this);
                    intervalSum += intervals[i];
                }
                if (intervalSum == 0.0f) {
                    PathEffect pathEffect3 = state2.strokePaint.setPathEffect((PathEffect) null);
                } else {
                    float offset = state2.style.strokeDashOffset.floatValue(this);
                    if (offset < 0.0f) {
                        offset = intervalSum + (offset % intervalSum);
                    }
                    new DashPathEffect(intervals, offset);
                    PathEffect pathEffect4 = state2.strokePaint.setPathEffect(pathEffect);
                }
            }
        }
        if (isSpecified(style2, PlaybackStateCompat.ACTION_PREPARE)) {
            float currentFontSize = getCurrentFontSize();
            state2.style.fontSize = style2.fontSize;
            state2.fillPaint.setTextSize(style2.fontSize.floatValue(this, currentFontSize));
            state2.strokePaint.setTextSize(style2.fontSize.floatValue(this, currentFontSize));
        }
        if (isSpecified(style2, PlaybackStateCompat.ACTION_PLAY_FROM_URI)) {
            state2.style.fontFamily = style2.fontFamily;
        }
        if (isSpecified(style2, PlaybackStateCompat.ACTION_PREPARE_FROM_MEDIA_ID)) {
            if (style2.fontWeight.intValue() == -1 && state2.style.fontWeight.intValue() > 100) {
                SVG.Style style3 = state2.style;
                Integer valueOf = Integer.valueOf(style3.fontWeight.intValue() - 100);
                Integer num = valueOf;
                style3.fontWeight = valueOf;
            } else if (style2.fontWeight.intValue() != 1 || state2.style.fontWeight.intValue() >= 900) {
                state2.style.fontWeight = style2.fontWeight;
            } else {
                SVG.Style style4 = state2.style;
                Integer valueOf2 = Integer.valueOf(style4.fontWeight.intValue() + 100);
                Integer num2 = valueOf2;
                style4.fontWeight = valueOf2;
            }
        }
        if (isSpecified(style2, PlaybackStateCompat.ACTION_PREPARE_FROM_SEARCH)) {
            state2.style.fontStyle = style2.fontStyle;
        }
        if (isSpecified(style2, 106496)) {
            Typeface font = null;
            if (!(state2.style.fontFamily == null || this.document == null)) {
                SVGExternalFileResolver fileResolver = this.document.getFileResolver();
                for (String fontName : state2.style.fontFamily) {
                    font = checkGenericFont(fontName, state2.style.fontWeight, state2.style.fontStyle);
                    if (font == null && fileResolver != null) {
                        font = fileResolver.resolveFont(fontName, state2.style.fontWeight.intValue(), String.valueOf(state2.style.fontStyle));
                    }
                    if (font != null) {
                    }
                }
            }
            if (font == null) {
                font = checkGenericFont(DEFAULT_FONT_FAMILY, state2.style.fontWeight, state2.style.fontStyle);
            }
            Typeface typeface = state2.fillPaint.setTypeface(font);
            Typeface typeface2 = state2.strokePaint.setTypeface(font);
        }
        if (isSpecified(style2, PlaybackStateCompat.ACTION_PREPARE_FROM_URI)) {
            state2.style.textDecoration = style2.textDecoration;
            state2.fillPaint.setStrikeThruText(style2.textDecoration == SVG.Style.TextDecoration.LineThrough);
            state2.fillPaint.setUnderlineText(style2.textDecoration == SVG.Style.TextDecoration.Underline);
            if (Build.VERSION.SDK_INT >= 17) {
                state2.strokePaint.setStrikeThruText(style2.textDecoration == SVG.Style.TextDecoration.LineThrough);
                state2.strokePaint.setUnderlineText(style2.textDecoration == SVG.Style.TextDecoration.Underline);
            }
        }
        if (isSpecified(style2, 68719476736L)) {
            state2.style.direction = style2.direction;
        }
        if (isSpecified(style2, PlaybackStateCompat.ACTION_SET_REPEAT_MODE)) {
            state2.style.textAnchor = style2.textAnchor;
        }
        if (isSpecified(style2, PlaybackStateCompat.ACTION_SET_SHUFFLE_MODE_ENABLED)) {
            state2.style.overflow = style2.overflow;
        }
        if (isSpecified(style2, PlaybackStateCompat.ACTION_SET_SHUFFLE_MODE)) {
            state2.style.markerStart = style2.markerStart;
        }
        if (isSpecified(style2, 4194304)) {
            state2.style.markerMid = style2.markerMid;
        }
        if (isSpecified(style2, 8388608)) {
            state2.style.markerEnd = style2.markerEnd;
        }
        if (isSpecified(style2, 16777216)) {
            state2.style.display = style2.display;
        }
        if (isSpecified(style2, 33554432)) {
            state2.style.visibility = style2.visibility;
        }
        if (isSpecified(style2, PlaybackStateCompat.ACTION_SET_CAPTIONING_ENABLED)) {
            state2.style.clip = style2.clip;
        }
        if (isSpecified(style2, 268435456)) {
            state2.style.clipPath = style2.clipPath;
        }
        if (isSpecified(style2, 536870912)) {
            state2.style.clipRule = style2.clipRule;
        }
        if (isSpecified(style2, 1073741824)) {
            state2.style.mask = style2.mask;
        }
        if (isSpecified(style2, 67108864)) {
            state2.style.stopColor = style2.stopColor;
        }
        if (isSpecified(style2, 134217728)) {
            state2.style.stopOpacity = style2.stopOpacity;
        }
        if (isSpecified(style2, Declaration.ENUM_ACCESS)) {
            state2.style.viewportFill = style2.viewportFill;
        }
        if (isSpecified(style2, Declaration.FINAL_ACCESS)) {
            state2.style.viewportFillOpacity = style2.viewportFillOpacity;
        }
    }

    private void setPaintColour(RendererState rendererState, boolean z, SVG.SvgPaint svgPaint) {
        int col;
        StringBuilder sb;
        RendererState state2 = rendererState;
        boolean isFill = z;
        SVG.SvgPaint paint = svgPaint;
        float paintOpacity = (isFill ? state2.style.fillOpacity : state2.style.strokeOpacity).floatValue();
        if (paint instanceof SVG.Colour) {
            col = ((SVG.Colour) paint).colour;
        } else if (paint instanceof SVG.CurrentColor) {
            col = state2.style.color.colour;
        } else {
            return;
        }
        int col2 = (clamp255(paintOpacity) << 24) | col;
        new StringBuilder();
        int d = Log.d(TAG, sb.append("isFill = ").append(isFill).append(", opacity = ").append(paintOpacity).append(", col = ").append(Integer.toHexString(col2)).toString());
        if (isFill) {
            state2.fillPaint.setColor(col2);
        } else {
            state2.strokePaint.setColor(col2);
        }
    }

    private Typeface checkGenericFont(String str, Integer num, SVG.Style.FontStyle fontStyle) {
        String fontName = str;
        Integer fontWeight = num;
        Typeface font = null;
        boolean italic = fontStyle == SVG.Style.FontStyle.Italic;
        int typefaceStyle = fontWeight.intValue() > 500 ? italic ? 3 : 1 : italic ? 2 : 0;
        if (fontName.equals("serif")) {
            font = Typeface.create(Typeface.SERIF, typefaceStyle);
        } else if (fontName.equals(DEFAULT_FONT_FAMILY)) {
            font = Typeface.create(Typeface.SANS_SERIF, typefaceStyle);
        } else if (fontName.equals("monospace")) {
            font = Typeface.create(Typeface.MONOSPACE, typefaceStyle);
        } else if (fontName.equals("cursive")) {
            font = Typeface.create(Typeface.SANS_SERIF, typefaceStyle);
        } else if (fontName.equals("fantasy")) {
            font = Typeface.create(Typeface.SANS_SERIF, typefaceStyle);
        }
        return font;
    }

    private int clamp255(float val) {
        int i = (int) (val * 256.0f);
        return i < 0 ? 0 : i > 255 ? 255 : i;
    }

    private Path.FillType getFillTypeFromState() {
        if (this.state.style.fillRule == null) {
            return Path.FillType.WINDING;
        }
        switch (this.state.style.fillRule) {
            case EvenOdd:
                return Path.FillType.EVEN_ODD;
            default:
                return Path.FillType.WINDING;
        }
    }

    private void setClipRect(float f, float f2, float width, float height) {
        float minX = f;
        float minY = f2;
        float left = minX;
        float top = minY;
        float right = minX + width;
        float bottom = minY + height;
        if (this.state.style.clip != null) {
            left += this.state.style.clip.left.floatValueX(this);
            top += this.state.style.clip.top.floatValueY(this);
            right -= this.state.style.clip.right.floatValueX(this);
            bottom -= this.state.style.clip.bottom.floatValueY(this);
        }
        boolean clipRect = this.canvas.clipRect(left, top, right, bottom);
    }

    private void viewportFill() {
        int col;
        if (this.state.style.viewportFill instanceof SVG.Colour) {
            col = ((SVG.Colour) this.state.style.viewportFill).colour;
        } else if (this.state.style.viewportFill instanceof SVG.CurrentColor) {
            col = this.state.style.color.colour;
        } else {
            return;
        }
        if (this.state.style.viewportFillOpacity != null) {
            col = (clamp255(this.state.style.viewportFillOpacity.floatValue()) << 24) | col;
        }
        this.canvas.drawColor(col);
    }

    private class PathConverter implements SVG.PathInterface {
        float lastX;
        float lastY;
        Path path;
        final /* synthetic */ SVGAndroidRenderer this$0;

        public PathConverter(SVGAndroidRenderer sVGAndroidRenderer, SVG.PathDefinition pathDefinition) {
            Path path2;
            SVG.PathDefinition pathDef = pathDefinition;
            this.this$0 = sVGAndroidRenderer;
            new Path();
            this.path = path2;
            if (pathDef != null) {
                pathDef.enumeratePath(this);
            }
        }

        public Path getPath() {
            return this.path;
        }

        public void moveTo(float f, float f2) {
            float x = f;
            float y = f2;
            this.path.moveTo(x, y);
            this.lastX = x;
            this.lastY = y;
        }

        public void lineTo(float f, float f2) {
            float x = f;
            float y = f2;
            this.path.lineTo(x, y);
            this.lastX = x;
            this.lastY = y;
        }

        public void cubicTo(float x1, float y1, float x2, float y2, float f, float f2) {
            float x3 = f;
            float y3 = f2;
            this.path.cubicTo(x1, y1, x2, y2, x3, y3);
            this.lastX = x3;
            this.lastY = y3;
        }

        public void quadTo(float x1, float y1, float f, float f2) {
            float x2 = f;
            float y2 = f2;
            this.path.quadTo(x1, y1, x2, y2);
            this.lastX = x2;
            this.lastY = y2;
        }

        public void arcTo(float rx, float ry, float xAxisRotation, boolean largeArcFlag, boolean sweepFlag, float f, float f2) {
            float x = f;
            float y = f2;
            SVGAndroidRenderer.arcTo(this.lastX, this.lastY, rx, ry, xAxisRotation, largeArcFlag, sweepFlag, x, y, this);
            this.lastX = x;
            this.lastY = y;
        }

        public void close() {
            this.path.close();
        }
    }

    /* access modifiers changed from: private */
    public static void arcTo(float f, float f2, float f3, float f4, float f5, boolean z, boolean z2, float f6, float f7, SVG.PathInterface pathInterface) {
        Matrix matrix;
        float lastX = f;
        float lastY = f2;
        float rx = f3;
        float ry = f4;
        float angle = f5;
        boolean largeArcFlag = z;
        boolean sweepFlag = z2;
        float x = f6;
        float y = f7;
        SVG.PathInterface pather = pathInterface;
        if (lastX != x || lastY != y) {
            if (rx == 0.0f || ry == 0.0f) {
                pather.lineTo(x, y);
                return;
            }
            float rx2 = Math.abs(rx);
            float ry2 = Math.abs(ry);
            float angleRad = (float) Math.toRadians(((double) angle) % 360.0d);
            float cosAngle = (float) Math.cos((double) angleRad);
            float sinAngle = (float) Math.sin((double) angleRad);
            float dx2 = (lastX - x) / 2.0f;
            float dy2 = (lastY - y) / 2.0f;
            float x1 = (cosAngle * dx2) + (sinAngle * dy2);
            float y1 = ((-sinAngle) * dx2) + (cosAngle * dy2);
            float rx_sq = rx2 * rx2;
            float ry_sq = ry2 * ry2;
            float x1_sq = x1 * x1;
            float y1_sq = y1 * y1;
            float radiiCheck = (x1_sq / rx_sq) + (y1_sq / ry_sq);
            if (radiiCheck > 1.0f) {
                rx2 = ((float) Math.sqrt((double) radiiCheck)) * rx2;
                ry2 = ((float) Math.sqrt((double) radiiCheck)) * ry2;
                rx_sq = rx2 * rx2;
                ry_sq = ry2 * ry2;
            }
            float sq = (((rx_sq * ry_sq) - (rx_sq * y1_sq)) - (ry_sq * x1_sq)) / ((rx_sq * y1_sq) + (ry_sq * x1_sq));
            float coef = (float) (((double) (largeArcFlag == sweepFlag ? -1.0f : 1.0f)) * Math.sqrt((double) (sq < 0.0f ? 0.0f : sq)));
            float cx1 = coef * ((rx2 * y1) / ry2);
            float cy1 = coef * (-((ry2 * x1) / rx2));
            float cx = ((lastX + x) / 2.0f) + ((cosAngle * cx1) - (sinAngle * cy1));
            float cy = ((lastY + y) / 2.0f) + (sinAngle * cx1) + (cosAngle * cy1);
            float ux = (x1 - cx1) / rx2;
            float uy = (y1 - cy1) / ry2;
            float vx = ((-x1) - cx1) / rx2;
            float vy = ((-y1) - cy1) / ry2;
            float angleStart = (float) Math.toDegrees(((double) (uy < 0.0f ? -1.0f : 1.0f)) * Math.acos((double) (ux / ((float) Math.sqrt((double) ((ux * ux) + (uy * uy)))))));
            double angleExtent = Math.toDegrees(((double) ((ux * vy) - (uy * vx) < 0.0f ? -1.0f : 1.0f)) * Math.acos((double) (((ux * vx) + (uy * vy)) / ((float) Math.sqrt((double) (((ux * ux) + (uy * uy)) * ((vx * vx) + (vy * vy))))))));
            if (!sweepFlag && angleExtent > 0.0d) {
                angleExtent -= 360.0d;
            } else if (sweepFlag && angleExtent < 0.0d) {
                angleExtent += 360.0d;
            }
            float[] bezierPoints = arcToBeziers((double) (angleStart % 360.0f), angleExtent % 360.0d);
            new Matrix();
            Matrix m = matrix;
            boolean postScale = m.postScale(rx2, ry2);
            boolean postRotate = m.postRotate(angle);
            boolean postTranslate = m.postTranslate(cx, cy);
            m.mapPoints(bezierPoints);
            bezierPoints[bezierPoints.length - 2] = x;
            bezierPoints[bezierPoints.length - 1] = y;
            for (int i = 0; i < bezierPoints.length; i += 6) {
                pather.cubicTo(bezierPoints[i], bezierPoints[i + 1], bezierPoints[i + 2], bezierPoints[i + 3], bezierPoints[i + 4], bezierPoints[i + 5]);
            }
        }
    }

    private static float[] arcToBeziers(double angleStart, double d) {
        double angleExtent = d;
        int numSegments = (int) Math.ceil(Math.abs(angleExtent) / 90.0d);
        double angleStart2 = Math.toRadians(angleStart);
        float angleIncrement = (float) (Math.toRadians(angleExtent) / ((double) numSegments));
        double controlLength = (1.3333333333333333d * Math.sin(((double) angleIncrement) / 2.0d)) / (1.0d + Math.cos(((double) angleIncrement) / 2.0d));
        float[] coords = new float[(numSegments * 6)];
        int pos = 0;
        for (int i = 0; i < numSegments; i++) {
            double angle = angleStart2 + ((double) (((float) i) * angleIncrement));
            double dx = Math.cos(angle);
            double dy = Math.sin(angle);
            int i2 = pos;
            int pos2 = pos + 1;
            coords[i2] = (float) (dx - (controlLength * dy));
            int i3 = pos2;
            int pos3 = pos2 + 1;
            coords[i3] = (float) (dy + (controlLength * dx));
            double angle2 = angle + ((double) angleIncrement);
            double dx2 = Math.cos(angle2);
            double dy2 = Math.sin(angle2);
            int i4 = pos3;
            int pos4 = pos3 + 1;
            coords[i4] = (float) (dx2 + (controlLength * dy2));
            int i5 = pos4;
            int pos5 = pos4 + 1;
            coords[i5] = (float) (dy2 - (controlLength * dx2));
            int i6 = pos5;
            int pos6 = pos5 + 1;
            coords[i6] = (float) dx2;
            int i7 = pos6;
            pos = pos6 + 1;
            coords[i7] = (float) dy2;
        }
        return coords;
    }

    private class MarkerVector {

        /* renamed from: dx */
        public float f361dx = 0.0f;

        /* renamed from: dy */
        public float f362dy = 0.0f;
        final /* synthetic */ SVGAndroidRenderer this$0;

        /* renamed from: x */
        public float f363x;

        /* renamed from: y */
        public float f364y;

        public MarkerVector(SVGAndroidRenderer sVGAndroidRenderer, float x, float y, float f, float f2) {
            float dx = f;
            float dy = f2;
            this.this$0 = sVGAndroidRenderer;
            this.f363x = x;
            this.f364y = y;
            double len = Math.sqrt((double) ((dx * dx) + (dy * dy)));
            if (len != 0.0d) {
                this.f361dx = (float) (((double) dx) / len);
                this.f362dy = (float) (((double) dy) / len);
            }
        }

        public void add(float x, float y) {
            float dx = x - this.f363x;
            float dy = y - this.f364y;
            double len = Math.sqrt((double) ((dx * dx) + (dy * dy)));
            if (len != 0.0d) {
                this.f361dx += (float) (((double) dx) / len);
                this.f362dy += (float) (((double) dy) / len);
            }
        }

        public void add(MarkerVector markerVector) {
            MarkerVector v2 = markerVector;
            this.f361dx += v2.f361dx;
            this.f362dy += v2.f362dy;
        }

        public String toString() {
            StringBuilder sb;
            new StringBuilder();
            return sb.append("(").append(this.f363x).append(",").append(this.f364y).append(" ").append(this.f361dx).append(",").append(this.f362dy).append(")").toString();
        }
    }

    private class MarkerPositionCalculator implements SVG.PathInterface {
        private boolean closepathReAdjustPending;
        private MarkerVector lastPos = null;
        private List<MarkerVector> markers;
        private boolean normalCubic = true;
        private boolean startArc = false;
        private float startX;
        private float startY;
        private int subpathStartIndex = -1;
        final /* synthetic */ SVGAndroidRenderer this$0;

        public MarkerPositionCalculator(SVGAndroidRenderer sVGAndroidRenderer, SVG.PathDefinition pathDefinition) {
            List<MarkerVector> list;
            SVG.PathDefinition pathDef = pathDefinition;
            this.this$0 = sVGAndroidRenderer;
            new ArrayList();
            this.markers = list;
            if (pathDef != null) {
                pathDef.enumeratePath(this);
                if (this.closepathReAdjustPending) {
                    this.lastPos.add(this.markers.get(this.subpathStartIndex));
                    MarkerVector markerVector = this.markers.set(this.subpathStartIndex, this.lastPos);
                    this.closepathReAdjustPending = false;
                }
                if (this.lastPos != null) {
                    boolean add = this.markers.add(this.lastPos);
                }
            }
        }

        public List<MarkerVector> getMarkers() {
            return this.markers;
        }

        public void moveTo(float f, float f2) {
            MarkerVector markerVector;
            float x = f;
            float y = f2;
            if (this.closepathReAdjustPending) {
                this.lastPos.add(this.markers.get(this.subpathStartIndex));
                MarkerVector markerVector2 = this.markers.set(this.subpathStartIndex, this.lastPos);
                this.closepathReAdjustPending = false;
            }
            if (this.lastPos != null) {
                boolean add = this.markers.add(this.lastPos);
            }
            this.startX = x;
            this.startY = y;
            new MarkerVector(this.this$0, x, y, 0.0f, 0.0f);
            this.lastPos = markerVector;
            this.subpathStartIndex = this.markers.size();
        }

        public void lineTo(float f, float f2) {
            MarkerVector newPos;
            float x = f;
            float y = f2;
            this.lastPos.add(x, y);
            boolean add = this.markers.add(this.lastPos);
            new MarkerVector(this.this$0, x, y, x - this.lastPos.f363x, y - this.lastPos.f364y);
            this.lastPos = newPos;
            this.closepathReAdjustPending = false;
        }

        public void cubicTo(float f, float f2, float f3, float f4, float f5, float f6) {
            MarkerVector newPos;
            float x1 = f;
            float y1 = f2;
            float x2 = f3;
            float y2 = f4;
            float x3 = f5;
            float y3 = f6;
            if (this.normalCubic || this.startArc) {
                this.lastPos.add(x1, y1);
                boolean add = this.markers.add(this.lastPos);
                this.startArc = false;
            }
            new MarkerVector(this.this$0, x3, y3, x3 - x2, y3 - y2);
            this.lastPos = newPos;
            this.closepathReAdjustPending = false;
        }

        public void quadTo(float f, float f2, float f3, float f4) {
            MarkerVector newPos;
            float x1 = f;
            float y1 = f2;
            float x2 = f3;
            float y2 = f4;
            this.lastPos.add(x1, y1);
            boolean add = this.markers.add(this.lastPos);
            new MarkerVector(this.this$0, x2, y2, x2 - x1, y2 - y1);
            this.lastPos = newPos;
            this.closepathReAdjustPending = false;
        }

        public void arcTo(float rx, float ry, float xAxisRotation, boolean largeArcFlag, boolean sweepFlag, float x, float y) {
            this.startArc = true;
            this.normalCubic = false;
            SVGAndroidRenderer.arcTo(this.lastPos.f363x, this.lastPos.f364y, rx, ry, xAxisRotation, largeArcFlag, sweepFlag, x, y, this);
            this.normalCubic = true;
            this.closepathReAdjustPending = false;
        }

        public void close() {
            boolean add = this.markers.add(this.lastPos);
            lineTo(this.startX, this.startY);
            this.closepathReAdjustPending = true;
        }
    }

    private void renderMarkers(SVG.GraphicsElement graphicsElement) {
        List<MarkerVector> markers;
        int markerCount;
        MarkerPositionCalculator markerPositionCalculator;
        SVG.GraphicsElement obj = graphicsElement;
        if (this.state.style.markerStart != null || this.state.style.markerMid != null || this.state.style.markerEnd != null) {
            SVG.Marker _markerStart = null;
            SVG.Marker _markerMid = null;
            SVG.Marker _markerEnd = null;
            if (this.state.style.markerStart != null) {
                SVG.SvgObject ref = obj.document.resolveIRI(this.state.style.markerStart);
                if (ref != null) {
                    _markerStart = (SVG.Marker) ref;
                } else {
                    error("Marker reference '%s' not found", this.state.style.markerStart);
                }
            }
            if (this.state.style.markerMid != null) {
                SVG.SvgObject ref2 = obj.document.resolveIRI(this.state.style.markerMid);
                if (ref2 != null) {
                    _markerMid = (SVG.Marker) ref2;
                } else {
                    error("Marker reference '%s' not found", this.state.style.markerMid);
                }
            }
            if (this.state.style.markerEnd != null) {
                SVG.SvgObject ref3 = obj.document.resolveIRI(this.state.style.markerEnd);
                if (ref3 != null) {
                    _markerEnd = (SVG.Marker) ref3;
                } else {
                    error("Marker reference '%s' not found", this.state.style.markerEnd);
                }
            }
            if (obj instanceof SVG.Path) {
                new MarkerPositionCalculator(this, ((SVG.Path) obj).f328d);
                markers = markerPositionCalculator.getMarkers();
            } else if (obj instanceof SVG.Line) {
                markers = calculateMarkerPositions((SVG.Line) obj);
            } else {
                markers = calculateMarkerPositions((SVG.PolyLine) obj);
            }
            if (markers != null && (markerCount = markers.size()) != 0) {
                SVG.Style style = this.state.style;
                SVG.Style style2 = this.state.style;
                this.state.style.markerEnd = null;
                style2.markerMid = null;
                style.markerStart = null;
                if (_markerStart != null) {
                    renderMarker(_markerStart, markers.get(0));
                }
                if (_markerMid != null) {
                    for (int i = 1; i < markerCount - 1; i++) {
                        renderMarker(_markerMid, markers.get(i));
                    }
                }
                if (_markerEnd != null) {
                    renderMarker(_markerEnd, markers.get(markerCount - 1));
                }
            }
        }
    }

    private void renderMarker(SVG.Marker marker, MarkerVector markerVector) {
        float floatValue;
        Matrix matrix;
        float aspectScale;
        SVG.Marker marker2 = marker;
        MarkerVector pos = markerVector;
        float angle = 0.0f;
        statePush();
        if (marker2.orient != null) {
            if (!Float.isNaN(marker2.orient.floatValue())) {
                angle = marker2.orient.floatValue();
            } else if (!(pos.f361dx == 0.0f && pos.f362dy == 0.0f)) {
                angle = (float) Math.toDegrees(Math.atan2((double) pos.f362dy, (double) pos.f361dx));
            }
        }
        if (marker2.markerUnitsAreUser) {
            floatValue = 1.0f;
        } else {
            floatValue = this.state.style.strokeWidth.floatValue(this.dpi);
        }
        float unitsScale = floatValue;
        this.state = findInheritFromAncestorState(marker2);
        new Matrix();
        Matrix m = matrix;
        boolean preTranslate = m.preTranslate(pos.f363x, pos.f364y);
        boolean preRotate = m.preRotate(angle);
        boolean preScale = m.preScale(unitsScale, unitsScale);
        float _refX = marker2.refX != null ? marker2.refX.floatValueX(this) : 0.0f;
        float _refY = marker2.refY != null ? marker2.refY.floatValueY(this) : 0.0f;
        float _markerWidth = marker2.markerWidth != null ? marker2.markerWidth.floatValueX(this) : 3.0f;
        float _markerHeight = marker2.markerHeight != null ? marker2.markerHeight.floatValueY(this) : 3.0f;
        if (marker2.viewBox != null) {
            float xScale = _markerWidth / marker2.viewBox.width;
            float yScale = _markerHeight / marker2.viewBox.height;
            PreserveAspectRatio positioning = marker2.preserveAspectRatio != null ? marker2.preserveAspectRatio : PreserveAspectRatio.LETTERBOX;
            if (!positioning.equals(PreserveAspectRatio.STRETCH)) {
                if (positioning.getScale() == PreserveAspectRatio.Scale.Slice) {
                    aspectScale = Math.max(xScale, yScale);
                } else {
                    aspectScale = Math.min(xScale, yScale);
                }
                float f = aspectScale;
                yScale = f;
                xScale = f;
            }
            boolean preTranslate2 = m.preTranslate((-_refX) * xScale, (-_refY) * yScale);
            this.canvas.concat(m);
            float imageW = marker2.viewBox.width * xScale;
            float imageH = marker2.viewBox.height * yScale;
            float xOffset = 0.0f;
            float yOffset = 0.0f;
            switch (positioning.getAlignment()) {
                case XMidYMin:
                case XMidYMid:
                case XMidYMax:
                    xOffset = 0.0f - ((_markerWidth - imageW) / 2.0f);
                    break;
                case XMaxYMin:
                case XMaxYMid:
                case XMaxYMax:
                    xOffset = 0.0f - (_markerWidth - imageW);
                    break;
            }
            switch (positioning.getAlignment()) {
                case XMidYMid:
                case XMaxYMid:
                case XMinYMid:
                    yOffset = 0.0f - ((_markerHeight - imageH) / 2.0f);
                    break;
                case XMidYMax:
                case XMaxYMax:
                case XMinYMax:
                    yOffset = 0.0f - (_markerHeight - imageH);
                    break;
            }
            if (!this.state.style.overflow.booleanValue()) {
                setClipRect(xOffset, yOffset, _markerWidth, _markerHeight);
            }
            m.reset();
            boolean preScale2 = m.preScale(xScale, yScale);
            this.canvas.concat(m);
        } else {
            boolean preTranslate3 = m.preTranslate(-_refX, -_refY);
            this.canvas.concat(m);
            if (!this.state.style.overflow.booleanValue()) {
                setClipRect(0.0f, 0.0f, _markerWidth, _markerHeight);
            }
        }
        boolean compositing = pushLayer();
        renderChildren(marker2, false);
        if (compositing) {
            popLayer(marker2);
        }
        statePop();
    }

    private RendererState findInheritFromAncestorState(SVG.SvgObject obj) {
        RendererState rendererState;
        new RendererState(this);
        RendererState newState = rendererState;
        updateStyle(newState, SVG.Style.getDefaultStyle());
        return findInheritFromAncestorState(obj, newState);
    }

    private RendererState findInheritFromAncestorState(SVG.SvgObject svgObject, RendererState rendererState) {
        List<SVG.SvgElementBase> list;
        SVG.SvgObject obj = svgObject;
        RendererState newState = rendererState;
        new ArrayList<>();
        List<SVG.SvgElementBase> ancestors = list;
        while (true) {
            if (obj instanceof SVG.SvgElementBase) {
                ancestors.add(0, (SVG.SvgElementBase) obj);
            }
            if (obj.parent == null) {
                break;
            }
            obj = (SVG.SvgObject) obj.parent;
        }
        for (SVG.SvgElementBase ancestor : ancestors) {
            updateStyleForElement(newState, ancestor);
        }
        newState.viewBox = this.document.getRootElement().viewBox;
        if (newState.viewBox == null) {
            newState.viewBox = this.canvasViewPort;
        }
        newState.viewPort = this.canvasViewPort;
        newState.directRendering = this.state.directRendering;
        return newState;
    }

    private void checkForGradientsAndPatterns(SVG.SvgElement svgElement) {
        SVG.SvgElement obj = svgElement;
        if (this.state.style.fill instanceof SVG.PaintReference) {
            decodePaintReference(true, obj.boundingBox, (SVG.PaintReference) this.state.style.fill);
        }
        if (this.state.style.stroke instanceof SVG.PaintReference) {
            decodePaintReference(false, obj.boundingBox, (SVG.PaintReference) this.state.style.stroke);
        }
    }

    private void decodePaintReference(boolean z, SVG.Box box, SVG.PaintReference paintReference) {
        boolean isFill = z;
        SVG.Box boundingBox = box;
        SVG.PaintReference paintref = paintReference;
        SVG.SvgObject ref = this.document.resolveIRI(paintref.href);
        if (ref == null) {
            Object[] objArr = new Object[2];
            Object[] objArr2 = objArr;
            objArr[0] = isFill ? "Fill" : "Stroke";
            Object[] objArr3 = objArr2;
            objArr3[1] = paintref.href;
            error("%s reference '%s' not found", objArr3);
            if (paintref.fallback != null) {
                setPaintColour(this.state, isFill, paintref.fallback);
            } else if (isFill) {
                this.state.hasFill = false;
            } else {
                this.state.hasStroke = false;
            }
        } else {
            if (ref instanceof SVG.SvgLinearGradient) {
                makeLinearGradient(isFill, boundingBox, (SVG.SvgLinearGradient) ref);
            }
            if (ref instanceof SVG.SvgRadialGradient) {
                makeRadialGradient(isFill, boundingBox, (SVG.SvgRadialGradient) ref);
            }
            if (ref instanceof SVG.SolidColor) {
                setSolidColor(isFill, (SVG.SolidColor) ref);
            }
        }
    }

    private void makeLinearGradient(boolean z, SVG.Box box, SVG.SvgLinearGradient svgLinearGradient) {
        float _x1;
        float _y1;
        float _x2;
        float _y2;
        Matrix matrix;
        LinearGradient linearGradient;
        float f;
        boolean isFill = z;
        SVG.Box boundingBox = box;
        SVG.SvgLinearGradient gradient = svgLinearGradient;
        if (gradient.href != null) {
            fillInChainedGradientFields((SVG.GradientElement) gradient, gradient.href);
        }
        boolean userUnits = gradient.gradientUnitsAreUser != null && gradient.gradientUnitsAreUser.booleanValue();
        Paint paint = isFill ? this.state.fillPaint : this.state.strokePaint;
        if (userUnits) {
            SVG.Box viewPortUser = getCurrentViewPortInUserUnits();
            _x1 = gradient.f338x1 != null ? gradient.f338x1.floatValueX(this) : 0.0f;
            _y1 = gradient.f340y1 != null ? gradient.f340y1.floatValueY(this) : 0.0f;
            _x2 = gradient.f339x2 != null ? gradient.f339x2.floatValueX(this) : viewPortUser.width;
            if (gradient.f341y2 != null) {
                f = gradient.f341y2.floatValueY(this);
            } else {
                f = 0.0f;
            }
            _y2 = f;
        } else {
            _x1 = gradient.f338x1 != null ? gradient.f338x1.floatValue(this, 1.0f) : 0.0f;
            _y1 = gradient.f340y1 != null ? gradient.f340y1.floatValue(this, 1.0f) : 0.0f;
            _x2 = gradient.f339x2 != null ? gradient.f339x2.floatValue(this, 1.0f) : 1.0f;
            _y2 = gradient.f341y2 != null ? gradient.f341y2.floatValue(this, 1.0f) : 0.0f;
        }
        statePush();
        this.state = findInheritFromAncestorState(gradient);
        new Matrix();
        Matrix m = matrix;
        if (!userUnits) {
            boolean preTranslate = m.preTranslate(boundingBox.minX, boundingBox.minY);
            boolean preScale = m.preScale(boundingBox.width, boundingBox.height);
        }
        if (gradient.gradientTransform != null) {
            boolean preConcat = m.preConcat(gradient.gradientTransform);
        }
        int numStops = gradient.children.size();
        if (numStops == 0) {
            statePop();
            if (isFill) {
                this.state.hasFill = false;
            } else {
                this.state.hasStroke = false;
            }
        } else {
            int[] colours = new int[numStops];
            float[] positions = new float[numStops];
            int i = 0;
            float lastOffset = -1.0f;
            for (SVG.SvgObject child : gradient.children) {
                SVG.Stop stop = (SVG.Stop) child;
                if (i == 0 || stop.offset.floatValue() >= lastOffset) {
                    positions[i] = stop.offset.floatValue();
                    lastOffset = stop.offset.floatValue();
                } else {
                    positions[i] = lastOffset;
                }
                statePush();
                updateStyleForElement(this.state, stop);
                SVG.Colour col = (SVG.Colour) this.state.style.stopColor;
                if (col == null) {
                    col = SVG.Colour.BLACK;
                }
                colours[i] = (clamp255(this.state.style.stopOpacity.floatValue()) << 24) | col.colour;
                i++;
                statePop();
            }
            if ((_x1 == _x2 && _y1 == _y2) || numStops == 1) {
                statePop();
                paint.setColor(colours[numStops - 1]);
                return;
            }
            Shader.TileMode tileMode = Shader.TileMode.CLAMP;
            if (gradient.spreadMethod != null) {
                if (gradient.spreadMethod == SVG.GradientSpread.reflect) {
                    tileMode = Shader.TileMode.MIRROR;
                } else if (gradient.spreadMethod == SVG.GradientSpread.repeat) {
                    tileMode = Shader.TileMode.REPEAT;
                }
            }
            statePop();
            new LinearGradient(_x1, _y1, _x2, _y2, colours, positions, tileMode);
            LinearGradient gr = linearGradient;
            gr.setLocalMatrix(m);
            Shader shader = paint.setShader(gr);
        }
    }

    private void makeRadialGradient(boolean z, SVG.Box box, SVG.SvgRadialGradient svgRadialGradient) {
        float _cx;
        float _cy;
        float _r;
        Matrix matrix;
        RadialGradient radialGradient;
        SVG.Length length;
        float floatValue;
        boolean isFill = z;
        SVG.Box boundingBox = box;
        SVG.SvgRadialGradient gradient = svgRadialGradient;
        if (gradient.href != null) {
            fillInChainedGradientFields((SVG.GradientElement) gradient, gradient.href);
        }
        boolean userUnits = gradient.gradientUnitsAreUser != null && gradient.gradientUnitsAreUser.booleanValue();
        Paint paint = isFill ? this.state.fillPaint : this.state.strokePaint;
        if (userUnits) {
            new SVG.Length(50.0f, SVG.Unit.percent);
            SVG.Length fiftyPercent = length;
            _cx = gradient.f342cx != null ? gradient.f342cx.floatValueX(this) : fiftyPercent.floatValueX(this);
            _cy = gradient.f343cy != null ? gradient.f343cy.floatValueY(this) : fiftyPercent.floatValueY(this);
            if (gradient.f346r != null) {
                floatValue = gradient.f346r.floatValue(this);
            } else {
                floatValue = fiftyPercent.floatValue(this);
            }
            _r = floatValue;
        } else {
            _cx = gradient.f342cx != null ? gradient.f342cx.floatValue(this, 1.0f) : 0.5f;
            _cy = gradient.f343cy != null ? gradient.f343cy.floatValue(this, 1.0f) : 0.5f;
            _r = gradient.f346r != null ? gradient.f346r.floatValue(this, 1.0f) : 0.5f;
        }
        statePush();
        this.state = findInheritFromAncestorState(gradient);
        new Matrix();
        Matrix m = matrix;
        if (!userUnits) {
            boolean preTranslate = m.preTranslate(boundingBox.minX, boundingBox.minY);
            boolean preScale = m.preScale(boundingBox.width, boundingBox.height);
        }
        if (gradient.gradientTransform != null) {
            boolean preConcat = m.preConcat(gradient.gradientTransform);
        }
        int numStops = gradient.children.size();
        if (numStops == 0) {
            statePop();
            if (isFill) {
                this.state.hasFill = false;
            } else {
                this.state.hasStroke = false;
            }
        } else {
            int[] colours = new int[numStops];
            float[] positions = new float[numStops];
            int i = 0;
            float lastOffset = -1.0f;
            for (SVG.SvgObject child : gradient.children) {
                SVG.Stop stop = (SVG.Stop) child;
                if (i == 0 || stop.offset.floatValue() >= lastOffset) {
                    positions[i] = stop.offset.floatValue();
                    lastOffset = stop.offset.floatValue();
                } else {
                    positions[i] = lastOffset;
                }
                statePush();
                updateStyleForElement(this.state, stop);
                SVG.Colour col = (SVG.Colour) this.state.style.stopColor;
                if (col == null) {
                    col = SVG.Colour.BLACK;
                }
                colours[i] = (clamp255(this.state.style.stopOpacity.floatValue()) << 24) | col.colour;
                i++;
                statePop();
            }
            if (_r == 0.0f || numStops == 1) {
                statePop();
                paint.setColor(colours[numStops - 1]);
                return;
            }
            Shader.TileMode tileMode = Shader.TileMode.CLAMP;
            if (gradient.spreadMethod != null) {
                if (gradient.spreadMethod == SVG.GradientSpread.reflect) {
                    tileMode = Shader.TileMode.MIRROR;
                } else if (gradient.spreadMethod == SVG.GradientSpread.repeat) {
                    tileMode = Shader.TileMode.REPEAT;
                }
            }
            statePop();
            new RadialGradient(_cx, _cy, _r, colours, positions, tileMode);
            RadialGradient gr = radialGradient;
            gr.setLocalMatrix(m);
            Shader shader = paint.setShader(gr);
        }
    }

    private void fillInChainedGradientFields(SVG.GradientElement gradientElement, String str) {
        SVG.GradientElement gradient = gradientElement;
        String href = str;
        SVG.SvgObject ref = gradient.document.resolveIRI(href);
        if (ref == null) {
            warn("Gradient reference '%s' not found", href);
        } else if (!(ref instanceof SVG.GradientElement)) {
            error("Gradient href attributes must point to other gradient elements", new Object[0]);
        } else if (ref == gradient) {
            error("Circular reference in gradient href attribute '%s'", href);
        } else {
            SVG.GradientElement grRef = (SVG.GradientElement) ref;
            if (gradient.gradientUnitsAreUser == null) {
                gradient.gradientUnitsAreUser = grRef.gradientUnitsAreUser;
            }
            if (gradient.gradientTransform == null) {
                gradient.gradientTransform = grRef.gradientTransform;
            }
            if (gradient.spreadMethod == null) {
                gradient.spreadMethod = grRef.spreadMethod;
            }
            if (gradient.children.isEmpty()) {
                gradient.children = grRef.children;
            }
            try {
                if (gradient instanceof SVG.SvgLinearGradient) {
                    fillInChainedGradientFields((SVG.SvgLinearGradient) gradient, (SVG.SvgLinearGradient) ref);
                } else {
                    fillInChainedGradientFields((SVG.SvgRadialGradient) gradient, (SVG.SvgRadialGradient) ref);
                }
            } catch (ClassCastException e) {
                ClassCastException classCastException = e;
            }
            if (grRef.href != null) {
                fillInChainedGradientFields(gradient, grRef.href);
            }
        }
    }

    private void fillInChainedGradientFields(SVG.SvgLinearGradient svgLinearGradient, SVG.SvgLinearGradient svgLinearGradient2) {
        SVG.SvgLinearGradient gradient = svgLinearGradient;
        SVG.SvgLinearGradient grRef = svgLinearGradient2;
        if (gradient.f338x1 == null) {
            gradient.f338x1 = grRef.f338x1;
        }
        if (gradient.f340y1 == null) {
            gradient.f340y1 = grRef.f340y1;
        }
        if (gradient.f339x2 == null) {
            gradient.f339x2 = grRef.f339x2;
        }
        if (gradient.f341y2 == null) {
            gradient.f341y2 = grRef.f341y2;
        }
    }

    private void fillInChainedGradientFields(SVG.SvgRadialGradient svgRadialGradient, SVG.SvgRadialGradient svgRadialGradient2) {
        SVG.SvgRadialGradient gradient = svgRadialGradient;
        SVG.SvgRadialGradient grRef = svgRadialGradient2;
        if (gradient.f342cx == null) {
            gradient.f342cx = grRef.f342cx;
        }
        if (gradient.f343cy == null) {
            gradient.f343cy = grRef.f343cy;
        }
        if (gradient.f346r == null) {
            gradient.f346r = grRef.f346r;
        }
        if (gradient.f344fx == null) {
            gradient.f344fx = grRef.f344fx;
        }
        if (gradient.f345fy == null) {
            gradient.f345fy = grRef.f345fy;
        }
    }

    private void setSolidColor(boolean z, SVG.SolidColor solidColor) {
        boolean isFill = z;
        SVG.SolidColor ref = solidColor;
        if (isFill) {
            if (isSpecified(ref.baseStyle, Declaration.VOLATILE_ACCESS)) {
                this.state.style.fill = ref.baseStyle.solidColor;
                this.state.hasFill = ref.baseStyle.solidColor != null;
            }
            if (isSpecified(ref.baseStyle, Declaration.TRANSIENT_ACCESS)) {
                this.state.style.fillOpacity = ref.baseStyle.solidOpacity;
            }
            if (isSpecified(ref.baseStyle, 6442450944L)) {
                setPaintColour(this.state, isFill, this.state.style.fill);
                return;
            }
            return;
        }
        if (isSpecified(ref.baseStyle, Declaration.VOLATILE_ACCESS)) {
            this.state.style.stroke = ref.baseStyle.solidColor;
            this.state.hasStroke = ref.baseStyle.solidColor != null;
        }
        if (isSpecified(ref.baseStyle, Declaration.TRANSIENT_ACCESS)) {
            this.state.style.strokeOpacity = ref.baseStyle.solidOpacity;
        }
        if (isSpecified(ref.baseStyle, 6442450944L)) {
            setPaintColour(this.state, isFill, this.state.style.stroke);
        }
    }

    private void checkForClipPath(SVG.SvgElement svgElement) {
        SVG.SvgElement obj = svgElement;
        checkForClipPath(obj, obj.boundingBox);
    }

    private void checkForClipPath(SVG.SvgElement svgElement, SVG.Box box) {
        Path path;
        Matrix matrix;
        Matrix matrix2;
        SVG.SvgElement obj = svgElement;
        SVG.Box boundingBox = box;
        if (this.state.style.clipPath != null) {
            SVG.SvgObject ref = obj.document.resolveIRI(this.state.style.clipPath);
            if (ref == null) {
                error("ClipPath reference '%s' not found", this.state.style.clipPath);
                return;
            }
            SVG.ClipPath clipPath = (SVG.ClipPath) ref;
            if (clipPath.children.isEmpty()) {
                boolean clipRect = this.canvas.clipRect(0, 0, 0, 0);
                return;
            }
            boolean userUnits = clipPath.clipPathUnitsAreUser == null || clipPath.clipPathUnitsAreUser.booleanValue();
            if (!(obj instanceof SVG.Group) || userUnits) {
                clipStatePush();
                if (!userUnits) {
                    new Matrix();
                    Matrix m = matrix2;
                    boolean preTranslate = m.preTranslate(boundingBox.minX, boundingBox.minY);
                    boolean preScale = m.preScale(boundingBox.width, boundingBox.height);
                    this.canvas.concat(m);
                }
                if (clipPath.transform != null) {
                    this.canvas.concat(clipPath.transform);
                }
                this.state = findInheritFromAncestorState(clipPath);
                checkForClipPath(clipPath);
                new Path();
                Path combinedPath = path;
                for (SVG.SvgObject child : clipPath.children) {
                    new Matrix();
                    addObjectToClip(child, true, combinedPath, matrix);
                }
                boolean clipPath2 = this.canvas.clipPath(combinedPath);
                clipStatePop();
                return;
            }
            warn("<clipPath clipPathUnits=\"objectBoundingBox\"> is not supported when referenced from container elements (like %s)", obj.getClass().getSimpleName());
        }
    }

    private void addObjectToClip(SVG.SvgObject svgObject, boolean z, Path path, Matrix matrix) {
        SVG.SvgObject obj = svgObject;
        boolean allowUse = z;
        Path combinedPath = path;
        Matrix combinedPathMatrix = matrix;
        if (display()) {
            clipStatePush();
            if (obj instanceof SVG.Use) {
                if (allowUse) {
                    addObjectToClip((SVG.Use) obj, combinedPath, combinedPathMatrix);
                } else {
                    error("<use> elements inside a <clipPath> cannot reference another <use>", new Object[0]);
                }
            } else if (obj instanceof SVG.Path) {
                addObjectToClip((SVG.Path) obj, combinedPath, combinedPathMatrix);
            } else if (obj instanceof SVG.Text) {
                addObjectToClip((SVG.Text) obj, combinedPath, combinedPathMatrix);
            } else if (obj instanceof SVG.GraphicsElement) {
                addObjectToClip((SVG.GraphicsElement) obj, combinedPath, combinedPathMatrix);
            } else {
                error("Invalid %s element found in clipPath definition", obj.getClass().getSimpleName());
            }
            clipStatePop();
        }
    }

    private void clipStatePush() {
        int save = this.canvas.save(1);
        RendererState push = this.stateStack.push(this.state);
        this.state = (RendererState) this.state.clone();
    }

    private void clipStatePop() {
        this.canvas.restore();
        this.state = this.stateStack.pop();
    }

    private Path.FillType getClipRuleFromState() {
        if (this.state.style.clipRule == null) {
            return Path.FillType.WINDING;
        }
        switch (this.state.style.clipRule) {
            case EvenOdd:
                return Path.FillType.EVEN_ODD;
            default:
                return Path.FillType.WINDING;
        }
    }

    private void addObjectToClip(SVG.Path path, Path path2, Matrix matrix) {
        PathConverter pathConverter;
        SVG.Path obj = path;
        Path combinedPath = path2;
        Matrix combinedPathMatrix = matrix;
        updateStyleForElement(this.state, obj);
        if (display() && visible()) {
            if (obj.transform != null) {
                boolean preConcat = combinedPathMatrix.preConcat(obj.transform);
            }
            new PathConverter(this, obj.f328d);
            Path path3 = pathConverter.getPath();
            if (obj.boundingBox == null) {
                obj.boundingBox = calculatePathBounds(path3);
            }
            checkForClipPath(obj);
            combinedPath.setFillType(getClipRuleFromState());
            combinedPath.addPath(path3, combinedPathMatrix);
        }
    }

    private void addObjectToClip(SVG.GraphicsElement graphicsElement, Path path, Matrix matrix) {
        Path path2;
        SVG.GraphicsElement obj = graphicsElement;
        Path combinedPath = path;
        Matrix combinedPathMatrix = matrix;
        updateStyleForElement(this.state, obj);
        if (display() && visible()) {
            if (obj.transform != null) {
                boolean preConcat = combinedPathMatrix.preConcat(obj.transform);
            }
            if (obj instanceof SVG.Rect) {
                path2 = makePathAndBoundingBox((SVG.Rect) obj);
            } else if (obj instanceof SVG.Circle) {
                path2 = makePathAndBoundingBox((SVG.Circle) obj);
            } else if (obj instanceof SVG.Ellipse) {
                path2 = makePathAndBoundingBox((SVG.Ellipse) obj);
            } else if (obj instanceof SVG.PolyLine) {
                path2 = makePathAndBoundingBox((SVG.PolyLine) obj);
            } else {
                return;
            }
            checkForClipPath(obj);
            combinedPath.setFillType(path2.getFillType());
            combinedPath.addPath(path2, combinedPathMatrix);
        }
    }

    private void addObjectToClip(SVG.Use use, Path path, Matrix matrix) {
        SVG.Use obj = use;
        Path combinedPath = path;
        Matrix combinedPathMatrix = matrix;
        updateStyleForElement(this.state, obj);
        if (display() && visible()) {
            if (obj.transform != null) {
                boolean preConcat = combinedPathMatrix.preConcat(obj.transform);
            }
            SVG.SvgObject ref = obj.document.resolveIRI(obj.href);
            if (ref == null) {
                error("Use reference '%s' not found", obj.href);
                return;
            }
            checkForClipPath(obj);
            addObjectToClip(ref, false, combinedPath, combinedPathMatrix);
        }
    }

    private void addObjectToClip(SVG.Text text, Path path, Matrix matrix) {
        Path path2;
        TextProcessor textProcessor;
        TextBoundsCalculator textBoundsCalculator;
        SVG.Box box;
        SVG.Text obj = text;
        Path combinedPath = path;
        Matrix combinedPathMatrix = matrix;
        updateStyleForElement(this.state, obj);
        if (display()) {
            if (obj.transform != null) {
                boolean preConcat = combinedPathMatrix.preConcat(obj.transform);
            }
            float x = (obj.f349x == null || obj.f349x.size() == 0) ? 0.0f : ((SVG.Length) obj.f349x.get(0)).floatValueX(this);
            float y = (obj.f350y == null || obj.f350y.size() == 0) ? 0.0f : ((SVG.Length) obj.f350y.get(0)).floatValueY(this);
            float dx = (obj.f347dx == null || obj.f347dx.size() == 0) ? 0.0f : ((SVG.Length) obj.f347dx.get(0)).floatValueX(this);
            float dy = (obj.f348dy == null || obj.f348dy.size() == 0) ? 0.0f : ((SVG.Length) obj.f348dy.get(0)).floatValueY(this);
            if (this.state.style.textAnchor != SVG.Style.TextAnchor.Start) {
                float textWidth = calculateTextWidth(obj);
                if (this.state.style.textAnchor == SVG.Style.TextAnchor.Middle) {
                    x -= textWidth / 2.0f;
                } else {
                    x -= textWidth;
                }
            }
            if (obj.boundingBox == null) {
                new TextBoundsCalculator(this, x, y);
                TextBoundsCalculator proc = textBoundsCalculator;
                enumerateTextSpans(obj, proc);
                new SVG.Box(proc.bbox.left, proc.bbox.top, proc.bbox.width(), proc.bbox.height());
                obj.boundingBox = box;
            }
            checkForClipPath(obj);
            new Path();
            Path textAsPath = path2;
            new PlainTextToPath(this, x + dx, y + dy, textAsPath);
            enumerateTextSpans(obj, textProcessor);
            combinedPath.setFillType(getClipRuleFromState());
            combinedPath.addPath(textAsPath, combinedPathMatrix);
        }
    }

    private class PlainTextToPath extends TextProcessor {
        public Path textAsPath;
        final /* synthetic */ SVGAndroidRenderer this$0;

        /* renamed from: x */
        public float f367x;

        /* renamed from: y */
        public float f368y;

        /* JADX WARNING: Illegal instructions before constructor call */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public PlainTextToPath(com.caverock.androidsvg.SVGAndroidRenderer r9, float r10, float r11, android.graphics.Path r12) {
            /*
                r8 = this;
                r0 = r8
                r1 = r9
                r2 = r10
                r3 = r11
                r4 = r12
                r5 = r0
                r6 = r1
                r5.this$0 = r6
                r5 = r0
                r6 = r1
                r7 = 0
                r5.<init>(r6, r7)
                r5 = r0
                r6 = r2
                r5.f367x = r6
                r5 = r0
                r6 = r3
                r5.f368y = r6
                r5 = r0
                r6 = r4
                r5.textAsPath = r6
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.caverock.androidsvg.SVGAndroidRenderer.PlainTextToPath.<init>(com.caverock.androidsvg.SVGAndroidRenderer, float, float, android.graphics.Path):void");
        }

        public boolean doTextContainer(SVG.TextContainer obj) {
            if (!(obj instanceof SVG.TextPath)) {
                return true;
            }
            SVGAndroidRenderer.warn("Using <textPath> elements in a clip path is not supported.", new Object[0]);
            return false;
        }

        public void processText(String str) {
            Path path;
            String text = str;
            if (this.this$0.visible()) {
                new Path();
                Path spanPath = path;
                this.this$0.state.fillPaint.getTextPath(text, 0, text.length(), this.f367x, this.f368y, spanPath);
                this.textAsPath.addPath(spanPath);
            }
            this.f367x += this.this$0.state.fillPaint.measureText(text);
        }
    }

    private Path makePathAndBoundingBox(SVG.Line line) {
        Path path;
        SVG.Box box;
        SVG.Line obj = line;
        float x1 = obj.f322x1 == null ? 0.0f : obj.f322x1.floatValueX(this);
        float y1 = obj.f324y1 == null ? 0.0f : obj.f324y1.floatValueY(this);
        float x2 = obj.f323x2 == null ? 0.0f : obj.f323x2.floatValueX(this);
        float y2 = obj.f325y2 == null ? 0.0f : obj.f325y2.floatValueY(this);
        if (obj.boundingBox == null) {
            new SVG.Box(Math.min(x1, x2), Math.min(y1, y2), Math.abs(x2 - x1), Math.abs(y2 - y1));
            obj.boundingBox = box;
        }
        new Path();
        Path p = path;
        p.moveTo(x1, y1);
        p.lineTo(x2, y2);
        return p;
    }

    private Path makePathAndBoundingBox(SVG.Rect rect) {
        float rx;
        float ry;
        Path path;
        SVG.Box box;
        SVG.Rect obj = rect;
        if (obj.f331rx == null && obj.f332ry == null) {
            rx = 0.0f;
            ry = 0.0f;
        } else if (obj.f331rx == null) {
            float floatValueY = obj.f332ry.floatValueY(this);
            ry = floatValueY;
            rx = floatValueY;
        } else if (obj.f332ry == null) {
            float floatValueX = obj.f331rx.floatValueX(this);
            ry = floatValueX;
            rx = floatValueX;
        } else {
            rx = obj.f331rx.floatValueX(this);
            ry = obj.f332ry.floatValueY(this);
        }
        float rx2 = Math.min(rx, obj.width.floatValueX(this) / 2.0f);
        float ry2 = Math.min(ry, obj.height.floatValueY(this) / 2.0f);
        float x = obj.f333x != null ? obj.f333x.floatValueX(this) : 0.0f;
        float y = obj.f334y != null ? obj.f334y.floatValueY(this) : 0.0f;
        float w = obj.width.floatValueX(this);
        float h = obj.height.floatValueY(this);
        if (obj.boundingBox == null) {
            new SVG.Box(x, y, w, h);
            obj.boundingBox = box;
        }
        float right = x + w;
        float bottom = y + h;
        new Path();
        Path p = path;
        if (rx2 == 0.0f || ry2 == 0.0f) {
            p.moveTo(x, y);
            p.lineTo(right, y);
            p.lineTo(right, bottom);
            p.lineTo(x, bottom);
            p.lineTo(x, y);
        } else {
            float cpx = rx2 * BEZIER_ARC_FACTOR;
            float cpy = ry2 * BEZIER_ARC_FACTOR;
            p.moveTo(x, y + ry2);
            p.cubicTo(x, (y + ry2) - cpy, (x + rx2) - cpx, y, x + rx2, y);
            p.lineTo(right - rx2, y);
            p.cubicTo((right - rx2) + cpx, y, right, (y + ry2) - cpy, right, y + ry2);
            p.lineTo(right, bottom - ry2);
            p.cubicTo(right, (bottom - ry2) + cpy, (right - rx2) + cpx, bottom, right - rx2, bottom);
            p.lineTo(x + rx2, bottom);
            p.cubicTo((x + rx2) - cpx, bottom, x, (bottom - ry2) + cpy, x, bottom - ry2);
            p.lineTo(x, y + ry2);
        }
        p.close();
        return p;
    }

    private Path makePathAndBoundingBox(SVG.Circle circle) {
        Path path;
        SVG.Box box;
        SVG.Circle obj = circle;
        float cx = obj.f313cx != null ? obj.f313cx.floatValueX(this) : 0.0f;
        float cy = obj.f314cy != null ? obj.f314cy.floatValueY(this) : 0.0f;
        float r = obj.f315r.floatValue(this);
        float left = cx - r;
        float top = cy - r;
        float right = cx + r;
        float bottom = cy + r;
        if (obj.boundingBox == null) {
            new SVG.Box(left, top, r * 2.0f, r * 2.0f);
            obj.boundingBox = box;
        }
        float cp = r * BEZIER_ARC_FACTOR;
        new Path();
        Path p = path;
        p.moveTo(cx, top);
        p.cubicTo(cx + cp, top, right, cy - cp, right, cy);
        p.cubicTo(right, cy + cp, cx + cp, bottom, cx, bottom);
        p.cubicTo(cx - cp, bottom, left, cy + cp, left, cy);
        p.cubicTo(left, cy - cp, cx - cp, top, cx, top);
        p.close();
        return p;
    }

    private Path makePathAndBoundingBox(SVG.Ellipse ellipse) {
        Path path;
        SVG.Box box;
        SVG.Ellipse obj = ellipse;
        float cx = obj.f316cx != null ? obj.f316cx.floatValueX(this) : 0.0f;
        float cy = obj.f317cy != null ? obj.f317cy.floatValueY(this) : 0.0f;
        float rx = obj.f318rx.floatValueX(this);
        float ry = obj.f319ry.floatValueY(this);
        float left = cx - rx;
        float top = cy - ry;
        float right = cx + rx;
        float bottom = cy + ry;
        if (obj.boundingBox == null) {
            new SVG.Box(left, top, rx * 2.0f, ry * 2.0f);
            obj.boundingBox = box;
        }
        float cpx = rx * BEZIER_ARC_FACTOR;
        float cpy = ry * BEZIER_ARC_FACTOR;
        new Path();
        Path p = path;
        p.moveTo(cx, top);
        p.cubicTo(cx + cpx, top, right, cy - cpy, right, cy);
        p.cubicTo(right, cy + cpy, cx + cpx, bottom, cx, bottom);
        p.cubicTo(cx - cpx, bottom, left, cy + cpy, left, cy);
        p.cubicTo(left, cy - cpy, cx - cpx, top, cx, top);
        p.close();
        return p;
    }

    private Path makePathAndBoundingBox(SVG.PolyLine polyLine) {
        Path path;
        SVG.PolyLine obj = polyLine;
        new Path();
        Path path2 = path;
        path2.moveTo(obj.points[0], obj.points[1]);
        for (int i = 2; i < obj.points.length; i += 2) {
            path2.lineTo(obj.points[i], obj.points[i + 1]);
        }
        if (obj instanceof SVG.Polygon) {
            path2.close();
        }
        if (obj.boundingBox == null) {
            obj.boundingBox = calculatePathBounds(path2);
        }
        path2.setFillType(getClipRuleFromState());
        return path2;
    }

    private void fillWithPattern(SVG.SvgElement svgElement, Path path, SVG.Pattern pattern) {
        float x;
        float y;
        float w;
        float h;
        RendererState rendererState;
        SVG.Box box;
        Matrix matrix;
        RectF rectF;
        SVG.Box box2;
        float f;
        SVG.SvgElement obj = svgElement;
        Path path2 = path;
        SVG.Pattern pattern2 = pattern;
        boolean patternUnitsAreUser = pattern2.patternUnitsAreUser != null && pattern2.patternUnitsAreUser.booleanValue();
        if (pattern2.href != null) {
            fillInChainedPatternFields(pattern2, pattern2.href);
        }
        if (patternUnitsAreUser) {
            x = pattern2.f329x != null ? pattern2.f329x.floatValueX(this) : 0.0f;
            y = pattern2.f330y != null ? pattern2.f330y.floatValueY(this) : 0.0f;
            w = pattern2.width != null ? pattern2.width.floatValueX(this) : 0.0f;
            if (pattern2.height != null) {
                f = pattern2.height.floatValueY(this);
            } else {
                f = 0.0f;
            }
            h = f;
        } else {
            float x2 = pattern2.f329x != null ? pattern2.f329x.floatValue(this, 1.0f) : 0.0f;
            float y2 = pattern2.f330y != null ? pattern2.f330y.floatValue(this, 1.0f) : 0.0f;
            float w2 = pattern2.width != null ? pattern2.width.floatValue(this, 1.0f) : 0.0f;
            float h2 = pattern2.height != null ? pattern2.height.floatValue(this, 1.0f) : 0.0f;
            x = obj.boundingBox.minX + (x2 * obj.boundingBox.width);
            y = obj.boundingBox.minY + (y2 * obj.boundingBox.height);
            w = w2 * obj.boundingBox.width;
            h = h2 * obj.boundingBox.height;
        }
        if (w != 0.0f && h != 0.0f) {
            PreserveAspectRatio positioning = pattern2.preserveAspectRatio != null ? pattern2.preserveAspectRatio : PreserveAspectRatio.LETTERBOX;
            statePush();
            boolean clipPath = this.canvas.clipPath(path2);
            new RendererState(this);
            RendererState baseState = rendererState;
            updateStyle(baseState, SVG.Style.getDefaultStyle());
            baseState.style.overflow = null;
            this.state = findInheritFromAncestorState(pattern2, baseState);
            SVG.Box patternArea = obj.boundingBox;
            if (pattern2.patternTransform != null) {
                this.canvas.concat(pattern2.patternTransform);
                new Matrix();
                Matrix inverse = matrix;
                if (pattern2.patternTransform.invert(inverse)) {
                    float[] fArr = new float[8];
                    fArr[0] = obj.boundingBox.minX;
                    float[] fArr2 = fArr;
                    fArr2[1] = obj.boundingBox.minY;
                    float[] fArr3 = fArr2;
                    fArr3[2] = obj.boundingBox.maxX();
                    float[] fArr4 = fArr3;
                    fArr4[3] = obj.boundingBox.minY;
                    float[] fArr5 = fArr4;
                    fArr5[4] = obj.boundingBox.maxX();
                    float[] fArr6 = fArr5;
                    fArr6[5] = obj.boundingBox.maxY();
                    float[] fArr7 = fArr6;
                    fArr7[6] = obj.boundingBox.minX;
                    float[] fArr8 = fArr7;
                    fArr8[7] = obj.boundingBox.maxY();
                    float[] pts = fArr8;
                    inverse.mapPoints(pts);
                    new RectF(pts[0], pts[1], pts[0], pts[1]);
                    RectF rect = rectF;
                    for (int i = 2; i <= 6; i += 2) {
                        if (pts[i] < rect.left) {
                            rect.left = pts[i];
                        }
                        if (pts[i] > rect.right) {
                            rect.right = pts[i];
                        }
                        if (pts[i + 1] < rect.top) {
                            rect.top = pts[i + 1];
                        }
                        if (pts[i + 1] > rect.bottom) {
                            rect.bottom = pts[i + 1];
                        }
                    }
                    new SVG.Box(rect.left, rect.top, rect.right - rect.left, rect.bottom - rect.top);
                    patternArea = box2;
                }
            }
            float originX = x + (((float) Math.floor((double) ((patternArea.minX - x) / w))) * w);
            float originY = y + (((float) Math.floor((double) ((patternArea.minY - y) / h))) * h);
            float right = patternArea.maxX();
            float bottom = patternArea.maxY();
            new SVG.Box(0.0f, 0.0f, w, h);
            SVG.Box stepViewBox = box;
            float f2 = originY;
            while (true) {
                float stepY = f2;
                if (stepY < bottom) {
                    float f3 = originX;
                    while (true) {
                        float stepX = f3;
                        if (stepX >= right) {
                            break;
                        }
                        stepViewBox.minX = stepX;
                        stepViewBox.minY = stepY;
                        statePush();
                        if (!this.state.style.overflow.booleanValue()) {
                            setClipRect(stepViewBox.minX, stepViewBox.minY, stepViewBox.width, stepViewBox.height);
                        }
                        if (pattern2.viewBox != null) {
                            this.canvas.concat(calculateViewBoxTransform(stepViewBox, pattern2.viewBox, positioning));
                        } else {
                            boolean patternContentUnitsAreUser = pattern2.patternContentUnitsAreUser == null || pattern2.patternContentUnitsAreUser.booleanValue();
                            this.canvas.translate(stepX, stepY);
                            if (!patternContentUnitsAreUser) {
                                this.canvas.scale(obj.boundingBox.width, obj.boundingBox.height);
                            }
                        }
                        boolean compositing = pushLayer();
                        for (SVG.SvgObject child : pattern2.children) {
                            render(child);
                        }
                        if (compositing) {
                            popLayer(pattern2);
                        }
                        statePop();
                        f3 = stepX + w;
                    }
                    f2 = stepY + h;
                } else {
                    statePop();
                    return;
                }
            }
        }
    }

    private void fillInChainedPatternFields(SVG.Pattern pattern, String str) {
        SVG.Pattern pattern2 = pattern;
        String href = str;
        SVG.SvgObject ref = pattern2.document.resolveIRI(href);
        if (ref == null) {
            warn("Pattern reference '%s' not found", href);
        } else if (!(ref instanceof SVG.Pattern)) {
            error("Pattern href attributes must point to other pattern elements", new Object[0]);
        } else if (ref == pattern2) {
            error("Circular reference in pattern href attribute '%s'", href);
        } else {
            SVG.Pattern pRef = (SVG.Pattern) ref;
            if (pattern2.patternUnitsAreUser == null) {
                pattern2.patternUnitsAreUser = pRef.patternUnitsAreUser;
            }
            if (pattern2.patternContentUnitsAreUser == null) {
                pattern2.patternContentUnitsAreUser = pRef.patternContentUnitsAreUser;
            }
            if (pattern2.patternTransform == null) {
                pattern2.patternTransform = pRef.patternTransform;
            }
            if (pattern2.f329x == null) {
                pattern2.f329x = pRef.f329x;
            }
            if (pattern2.f330y == null) {
                pattern2.f330y = pRef.f330y;
            }
            if (pattern2.width == null) {
                pattern2.width = pRef.width;
            }
            if (pattern2.height == null) {
                pattern2.height = pRef.height;
            }
            if (pattern2.children.isEmpty()) {
                pattern2.children = pRef.children;
            }
            if (pattern2.viewBox == null) {
                pattern2.viewBox = pRef.viewBox;
            }
            if (pattern2.preserveAspectRatio == null) {
                pattern2.preserveAspectRatio = pRef.preserveAspectRatio;
            }
            if (pRef.href != null) {
                fillInChainedPatternFields(pattern2, pRef.href);
            }
        }
    }

    private void renderMask(SVG.Mask mask, SVG.SvgElement svgElement) {
        float w;
        float h;
        float f;
        SVG.Mask mask2 = mask;
        SVG.SvgElement obj = svgElement;
        debug("Mask render", new Object[0]);
        if (mask2.maskUnitsAreUser != null && mask2.maskUnitsAreUser.booleanValue()) {
            w = mask2.width != null ? mask2.width.floatValueX(this) : obj.boundingBox.width;
            h = mask2.height != null ? mask2.height.floatValueY(this) : obj.boundingBox.height;
            float floatValueX = mask2.f326x != null ? mask2.f326x.floatValueX(this) : (float) (((double) obj.boundingBox.minX) - (0.1d * ((double) obj.boundingBox.width)));
            if (mask2.f327y != null) {
                f = mask2.f327y.floatValueY(this);
            } else {
                f = (float) (((double) obj.boundingBox.minY) - (0.1d * ((double) obj.boundingBox.height)));
            }
            float f2 = f;
        } else {
            float x = mask2.f326x != null ? mask2.f326x.floatValue(this, 1.0f) : -0.1f;
            float y = mask2.f327y != null ? mask2.f327y.floatValue(this, 1.0f) : -0.1f;
            float w2 = mask2.width != null ? mask2.width.floatValue(this, 1.0f) : 1.2f;
            float h2 = mask2.height != null ? mask2.height.floatValue(this, 1.0f) : 1.2f;
            float x2 = obj.boundingBox.minX + (x * obj.boundingBox.width);
            float y2 = obj.boundingBox.minY + (y * obj.boundingBox.height);
            w = w2 * obj.boundingBox.width;
            h = h2 * obj.boundingBox.height;
        }
        if (w != 0.0f && h != 0.0f) {
            statePush();
            this.state = findInheritFromAncestorState(mask2);
            this.state.style.opacity = Float.valueOf(1.0f);
            if (!(mask2.maskContentUnitsAreUser == null || mask2.maskContentUnitsAreUser.booleanValue())) {
                this.canvas.translate(obj.boundingBox.minX, obj.boundingBox.minY);
                this.canvas.scale(obj.boundingBox.width, obj.boundingBox.height);
            }
            renderChildren(mask2, false);
            statePop();
        }
    }
}
