package com.caverock.androidsvg;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Picture;
import android.graphics.RectF;
import android.util.Log;
import com.caverock.androidsvg.CSSParser;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.xml.sax.SAXException;

public class SVG {
    private static final int DEFAULT_PICTURE_HEIGHT = 512;
    private static final int DEFAULT_PICTURE_WIDTH = 512;
    protected static final long SPECIFIED_ALL = -1;
    protected static final long SPECIFIED_CLIP = 1048576;
    protected static final long SPECIFIED_CLIP_PATH = 268435456;
    protected static final long SPECIFIED_CLIP_RULE = 536870912;
    protected static final long SPECIFIED_COLOR = 4096;
    protected static final long SPECIFIED_DIRECTION = 68719476736L;
    protected static final long SPECIFIED_DISPLAY = 16777216;
    protected static final long SPECIFIED_FILL = 1;
    protected static final long SPECIFIED_FILL_OPACITY = 4;
    protected static final long SPECIFIED_FILL_RULE = 2;
    protected static final long SPECIFIED_FONT_FAMILY = 8192;
    protected static final long SPECIFIED_FONT_SIZE = 16384;
    protected static final long SPECIFIED_FONT_STYLE = 65536;
    protected static final long SPECIFIED_FONT_WEIGHT = 32768;
    protected static final long SPECIFIED_MARKER_END = 8388608;
    protected static final long SPECIFIED_MARKER_MID = 4194304;
    protected static final long SPECIFIED_MARKER_START = 2097152;
    protected static final long SPECIFIED_MASK = 1073741824;
    protected static final long SPECIFIED_NON_INHERITING = 68133849088L;
    protected static final long SPECIFIED_OPACITY = 2048;
    protected static final long SPECIFIED_OVERFLOW = 524288;
    protected static final long SPECIFIED_SOLID_COLOR = 2147483648L;
    protected static final long SPECIFIED_SOLID_OPACITY = 4294967296L;
    protected static final long SPECIFIED_STOP_COLOR = 67108864;
    protected static final long SPECIFIED_STOP_OPACITY = 134217728;
    protected static final long SPECIFIED_STROKE = 8;
    protected static final long SPECIFIED_STROKE_DASHARRAY = 512;
    protected static final long SPECIFIED_STROKE_DASHOFFSET = 1024;
    protected static final long SPECIFIED_STROKE_LINECAP = 64;
    protected static final long SPECIFIED_STROKE_LINEJOIN = 128;
    protected static final long SPECIFIED_STROKE_MITERLIMIT = 256;
    protected static final long SPECIFIED_STROKE_OPACITY = 16;
    protected static final long SPECIFIED_STROKE_WIDTH = 32;
    protected static final long SPECIFIED_TEXT_ANCHOR = 262144;
    protected static final long SPECIFIED_TEXT_DECORATION = 131072;
    protected static final long SPECIFIED_VECTOR_EFFECT = 34359738368L;
    protected static final long SPECIFIED_VIEWPORT_FILL = 8589934592L;
    protected static final long SPECIFIED_VIEWPORT_FILL_OPACITY = 17179869184L;
    protected static final long SPECIFIED_VISIBILITY = 33554432;
    private static final double SQRT2 = 1.414213562373095d;
    protected static final String SUPPORTED_SVG_VERSION = "1.2";
    private static final String TAG = "AndroidSVG";
    private static final String VERSION = "1.2.3-beta-1";
    private CSSParser.Ruleset cssRules;
    private String desc = "";
    private SVGExternalFileResolver fileResolver = null;
    Map<String, SvgElementBase> idToElementMap;
    private float renderDPI = 96.0f;
    private Svg rootElement = null;
    private String title = "";

    protected enum GradientSpread {
    }

    public interface HasTransform {
        void setTransform(Matrix matrix);
    }

    public interface NotDirectlyRendered {
    }

    public interface PathInterface {
        void arcTo(float f, float f2, float f3, boolean z, boolean z2, float f4, float f5);

        void close();

        void cubicTo(float f, float f2, float f3, float f4, float f5, float f6);

        void lineTo(float f, float f2);

        void moveTo(float f, float f2);

        void quadTo(float f, float f2, float f3, float f4);
    }

    public interface SvgConditional {
        String getRequiredExtensions();

        Set<String> getRequiredFeatures();

        Set<String> getRequiredFonts();

        Set<String> getRequiredFormats();

        Set<String> getSystemLanguage();

        void setRequiredExtensions(String str);

        void setRequiredFeatures(Set<String> set);

        void setRequiredFonts(Set<String> set);

        void setRequiredFormats(Set<String> set);

        void setSystemLanguage(Set<String> set);
    }

    public interface SvgContainer {
        void addChild(SvgObject svgObject) throws SAXException;

        List<SvgObject> getChildren();
    }

    public interface TextChild {
        TextRoot getTextRoot();

        void setTextRoot(TextRoot textRoot);
    }

    public interface TextRoot {
    }

    protected enum Unit {
    }

    protected SVG() {
        CSSParser.Ruleset ruleset;
        Map<String, SvgElementBase> map;
        new CSSParser.Ruleset();
        this.cssRules = ruleset;
        new HashMap();
        this.idToElementMap = map;
    }

    public static SVG getFromInputStream(InputStream is) throws SVGParseException {
        SVGParser parser;
        new SVGParser();
        return parser.parse(is);
    }

    public static SVG getFromString(String svg) throws SVGParseException {
        SVGParser parser;
        InputStream inputStream;
        new SVGParser();
        new ByteArrayInputStream(svg.getBytes());
        return parser.parse(inputStream);
    }

    public static SVG getFromResource(Context context, int resourceId) throws SVGParseException {
        return getFromResource(context.getResources(), resourceId);
    }

    /* JADX INFO: finally extract failed */
    public static SVG getFromResource(Resources resources, int resourceId) throws SVGParseException {
        SVGParser sVGParser;
        new SVGParser();
        SVGParser parser = sVGParser;
        InputStream is = resources.openRawResource(resourceId);
        try {
            SVG parse = parser.parse(is);
            try {
                is.close();
            } catch (IOException e) {
                IOException iOException = e;
            }
            return parse;
        } catch (Throwable th) {
            Throwable th2 = th;
            try {
                is.close();
            } catch (IOException e2) {
                IOException iOException2 = e2;
            }
            throw th2;
        }
    }

    /* JADX INFO: finally extract failed */
    public static SVG getFromAsset(AssetManager assetManager, String filename) throws SVGParseException, IOException {
        SVGParser sVGParser;
        new SVGParser();
        SVGParser parser = sVGParser;
        InputStream is = assetManager.open(filename);
        try {
            SVG parse = parser.parse(is);
            try {
                is.close();
            } catch (IOException e) {
                IOException iOException = e;
            }
            return parse;
        } catch (Throwable th) {
            Throwable th2 = th;
            try {
                is.close();
            } catch (IOException e2) {
                IOException iOException2 = e2;
            }
            throw th2;
        }
    }

    public void registerExternalFileResolver(SVGExternalFileResolver fileResolver2) {
        SVGExternalFileResolver sVGExternalFileResolver = fileResolver2;
        this.fileResolver = sVGExternalFileResolver;
    }

    public void setRenderDPI(float dpi) {
        float f = dpi;
        this.renderDPI = f;
    }

    public float getRenderDPI() {
        return this.renderDPI;
    }

    public Picture renderToPicture() {
        float h;
        Length width = this.rootElement.width;
        if (width == null) {
            return renderToPicture(512, 512);
        }
        float w = width.floatValue(this.renderDPI);
        Box rootViewBox = this.rootElement.viewBox;
        if (rootViewBox != null) {
            h = (w * rootViewBox.height) / rootViewBox.width;
        } else {
            Length height = this.rootElement.height;
            if (height != null) {
                h = height.floatValue(this.renderDPI);
            } else {
                h = w;
            }
        }
        return renderToPicture((int) Math.ceil((double) w), (int) Math.ceil((double) h));
    }

    public Picture renderToPicture(int i, int i2) {
        Picture picture;
        Box viewPort;
        SVGAndroidRenderer renderer;
        int widthInPixels = i;
        int heightInPixels = i2;
        new Picture();
        Picture picture2 = picture;
        Canvas canvas = picture2.beginRecording(widthInPixels, heightInPixels);
        new Box(0.0f, 0.0f, (float) widthInPixels, (float) heightInPixels);
        new SVGAndroidRenderer(canvas, viewPort, this.renderDPI);
        renderer.renderDocument(this, (Box) null, (PreserveAspectRatio) null, false);
        picture2.endRecording();
        return picture2;
    }

    public Picture renderViewToPicture(String viewId, int i, int i2) {
        Picture picture;
        Box viewPort;
        SVGAndroidRenderer renderer;
        int widthInPixels = i;
        int heightInPixels = i2;
        SvgObject obj = getElementById(viewId);
        if (obj == null) {
            return null;
        }
        if (!(obj instanceof View)) {
            return null;
        }
        View view = (View) obj;
        if (view.viewBox == null) {
            int w = Log.w(TAG, "View element is missing a viewBox attribute.");
            return null;
        }
        new Picture();
        Picture picture2 = picture;
        Canvas canvas = picture2.beginRecording(widthInPixels, heightInPixels);
        new Box(0.0f, 0.0f, (float) widthInPixels, (float) heightInPixels);
        new SVGAndroidRenderer(canvas, viewPort, this.renderDPI);
        renderer.renderDocument(this, view.viewBox, view.preserveAspectRatio, false);
        picture2.endRecording();
        return picture2;
    }

    public void renderToCanvas(Canvas canvas) {
        renderToCanvas(canvas, (RectF) null);
    }

    public void renderToCanvas(Canvas canvas, RectF rectF) {
        Box box;
        Box svgViewPort;
        SVGAndroidRenderer renderer;
        Canvas canvas2 = canvas;
        RectF viewPort = rectF;
        if (viewPort != null) {
            svgViewPort = Box.fromLimits(viewPort.left, viewPort.top, viewPort.right, viewPort.bottom);
        } else {
            new Box(0.0f, 0.0f, (float) canvas2.getWidth(), (float) canvas2.getHeight());
            svgViewPort = box;
        }
        new SVGAndroidRenderer(canvas2, svgViewPort, this.renderDPI);
        renderer.renderDocument(this, (Box) null, (PreserveAspectRatio) null, true);
    }

    public void renderViewToCanvas(String viewId, Canvas canvas) {
        renderViewToCanvas(viewId, canvas, (RectF) null);
    }

    public void renderViewToCanvas(String viewId, Canvas canvas, RectF rectF) {
        Box box;
        Box svgViewPort;
        SVGAndroidRenderer renderer;
        Canvas canvas2 = canvas;
        RectF viewPort = rectF;
        SvgObject obj = getElementById(viewId);
        if (obj != null && (obj instanceof View)) {
            View view = (View) obj;
            if (view.viewBox == null) {
                int w = Log.w(TAG, "View element is missing a viewBox attribute.");
                return;
            }
            if (viewPort != null) {
                svgViewPort = Box.fromLimits(viewPort.left, viewPort.top, viewPort.right, viewPort.bottom);
            } else {
                new Box(0.0f, 0.0f, (float) canvas2.getWidth(), (float) canvas2.getHeight());
                svgViewPort = box;
            }
            new SVGAndroidRenderer(canvas2, svgViewPort, this.renderDPI);
            renderer.renderDocument(this, view.viewBox, view.preserveAspectRatio, true);
        }
    }

    public static String getVersion() {
        return VERSION;
    }

    public String getDocumentTitle() {
        Throwable th;
        if (this.rootElement != null) {
            return this.title;
        }
        Throwable th2 = th;
        new IllegalArgumentException("SVG document is empty");
        throw th2;
    }

    public String getDocumentDescription() {
        Throwable th;
        if (this.rootElement != null) {
            return this.desc;
        }
        Throwable th2 = th;
        new IllegalArgumentException("SVG document is empty");
        throw th2;
    }

    public String getDocumentSVGVersion() {
        Throwable th;
        if (this.rootElement != null) {
            return this.rootElement.version;
        }
        Throwable th2 = th;
        new IllegalArgumentException("SVG document is empty");
        throw th2;
    }

    public Set<String> getViewList() {
        Set<String> set;
        Throwable th;
        if (this.rootElement == null) {
            Throwable th2 = th;
            new IllegalArgumentException("SVG document is empty");
            throw th2;
        }
        List<SvgObject> viewElems = getElementsByTagName(View.class);
        new HashSet(viewElems.size());
        Set<String> viewIds = set;
        Iterator<SvgObject> it = viewElems.iterator();
        while (it.hasNext()) {
            View view = (View) it.next();
            if (view.f337id != null) {
                boolean add = viewIds.add(view.f337id);
            } else {
                int w = Log.w(TAG, "getViewList(): found a <view> without an id attribute");
            }
        }
        return viewIds;
    }

    public float getDocumentWidth() {
        Throwable th;
        if (this.rootElement != null) {
            return getDocumentDimensions(this.renderDPI).width;
        }
        Throwable th2 = th;
        new IllegalArgumentException("SVG document is empty");
        throw th2;
    }

    public void setDocumentWidth(float f) {
        Length length;
        Throwable th;
        float pixels = f;
        if (this.rootElement == null) {
            Throwable th2 = th;
            new IllegalArgumentException("SVG document is empty");
            throw th2;
        }
        Svg svg = this.rootElement;
        new Length(pixels);
        svg.width = length;
    }

    public void setDocumentWidth(String str) throws SVGParseException {
        Throwable th;
        Throwable th2;
        String value = str;
        if (this.rootElement == null) {
            Throwable th3 = th2;
            new IllegalArgumentException("SVG document is empty");
            throw th3;
        }
        try {
            this.rootElement.width = SVGParser.parseLength(value);
        } catch (SAXException e) {
            SAXException e2 = e;
            Throwable th4 = th;
            new SVGParseException(e2.getMessage());
            throw th4;
        }
    }

    public float getDocumentHeight() {
        Throwable th;
        if (this.rootElement != null) {
            return getDocumentDimensions(this.renderDPI).height;
        }
        Throwable th2 = th;
        new IllegalArgumentException("SVG document is empty");
        throw th2;
    }

    public void setDocumentHeight(float f) {
        Length length;
        Throwable th;
        float pixels = f;
        if (this.rootElement == null) {
            Throwable th2 = th;
            new IllegalArgumentException("SVG document is empty");
            throw th2;
        }
        Svg svg = this.rootElement;
        new Length(pixels);
        svg.height = length;
    }

    public void setDocumentHeight(String str) throws SVGParseException {
        Throwable th;
        Throwable th2;
        String value = str;
        if (this.rootElement == null) {
            Throwable th3 = th2;
            new IllegalArgumentException("SVG document is empty");
            throw th3;
        }
        try {
            this.rootElement.height = SVGParser.parseLength(value);
        } catch (SAXException e) {
            SAXException e2 = e;
            Throwable th4 = th;
            new SVGParseException(e2.getMessage());
            throw th4;
        }
    }

    public void setDocumentViewBox(float f, float f2, float f3, float f4) {
        Box box;
        Throwable th;
        float minX = f;
        float minY = f2;
        float width = f3;
        float height = f4;
        if (this.rootElement == null) {
            Throwable th2 = th;
            new IllegalArgumentException("SVG document is empty");
            throw th2;
        }
        Svg svg = this.rootElement;
        new Box(minX, minY, width, height);
        svg.viewBox = box;
    }

    public RectF getDocumentViewBox() {
        Throwable th;
        if (this.rootElement == null) {
            Throwable th2 = th;
            new IllegalArgumentException("SVG document is empty");
            throw th2;
        } else if (this.rootElement.viewBox == null) {
            return null;
        } else {
            return this.rootElement.viewBox.toRectF();
        }
    }

    public void setDocumentPreserveAspectRatio(PreserveAspectRatio preserveAspectRatio) {
        Throwable th;
        PreserveAspectRatio preserveAspectRatio2 = preserveAspectRatio;
        if (this.rootElement == null) {
            Throwable th2 = th;
            new IllegalArgumentException("SVG document is empty");
            throw th2;
        }
        this.rootElement.preserveAspectRatio = preserveAspectRatio2;
    }

    public PreserveAspectRatio getDocumentPreserveAspectRatio() {
        Throwable th;
        if (this.rootElement == null) {
            Throwable th2 = th;
            new IllegalArgumentException("SVG document is empty");
            throw th2;
        } else if (this.rootElement.preserveAspectRatio == null) {
            return null;
        } else {
            return this.rootElement.preserveAspectRatio;
        }
    }

    public float getDocumentAspectRatio() {
        Throwable th;
        if (this.rootElement == null) {
            Throwable th2 = th;
            new IllegalArgumentException("SVG document is empty");
            throw th2;
        }
        Length w = this.rootElement.width;
        Length h = this.rootElement.height;
        if (w == null || h == null || w.unit == Unit.percent || h.unit == Unit.percent) {
            if (this.rootElement.viewBox == null || this.rootElement.viewBox.width == 0.0f || this.rootElement.viewBox.height == 0.0f) {
                return -1.0f;
            }
            return this.rootElement.viewBox.width / this.rootElement.viewBox.height;
        } else if (w.isZero() || h.isZero()) {
            return -1.0f;
        } else {
            return w.floatValue(this.renderDPI) / h.floatValue(this.renderDPI);
        }
    }

    public Svg getRootElement() {
        return this.rootElement;
    }

    /* access modifiers changed from: protected */
    public void setRootElement(Svg rootElement2) {
        Svg svg = rootElement2;
        this.rootElement = svg;
    }

    /* access modifiers changed from: protected */
    public SvgObject resolveIRI(String str) {
        String iri = str;
        if (iri == null) {
            return null;
        }
        if (iri.length() <= 1 || !iri.startsWith("#")) {
            return null;
        }
        return getElementById(iri.substring(1));
    }

    private Box getDocumentDimensions(float f) {
        Box box;
        float hOut;
        Box box2;
        Box box3;
        float dpi = f;
        Length w = this.rootElement.width;
        Length h = this.rootElement.height;
        if (w == null || w.isZero() || w.unit == Unit.percent || w.unit == Unit.f352em || w.unit == Unit.f353ex) {
            new Box(-1.0f, -1.0f, -1.0f, -1.0f);
            return box;
        }
        float wOut = w.floatValue(dpi);
        if (h != null) {
            if (h.isZero() || h.unit == Unit.percent || h.unit == Unit.f352em || h.unit == Unit.f353ex) {
                new Box(-1.0f, -1.0f, -1.0f, -1.0f);
                return box3;
            }
            hOut = h.floatValue(dpi);
        } else if (this.rootElement.viewBox != null) {
            hOut = (wOut * this.rootElement.viewBox.height) / this.rootElement.viewBox.width;
        } else {
            hOut = wOut;
        }
        new Box(0.0f, 0.0f, wOut, hOut);
        return box2;
    }

    /* access modifiers changed from: protected */
    public void addCSSRules(CSSParser.Ruleset ruleset) {
        this.cssRules.addAll(ruleset);
    }

    /* access modifiers changed from: protected */
    public List<CSSParser.Rule> getCSSRules() {
        return this.cssRules.getRules();
    }

    /* access modifiers changed from: protected */
    public boolean hasCSSRules() {
        return !this.cssRules.isEmpty();
    }

    public static class Box implements Cloneable {
        public float height;
        public float minX;
        public float minY;
        public float width;

        public Box(float minX2, float minY2, float width2, float height2) {
            this.minX = minX2;
            this.minY = minY2;
            this.width = width2;
            this.height = height2;
        }

        public static Box fromLimits(float f, float f2, float maxX, float maxY) {
            Box box;
            float minX2 = f;
            float minY2 = f2;
            new Box(minX2, minY2, maxX - minX2, maxY - minY2);
            return box;
        }

        public RectF toRectF() {
            RectF rectF;
            new RectF(this.minX, this.minY, maxX(), maxY());
            return rectF;
        }

        public float maxX() {
            return this.minX + this.width;
        }

        public float maxY() {
            return this.minY + this.height;
        }

        public void union(Box box) {
            Box other = box;
            if (other.minX < this.minX) {
                this.minX = other.minX;
            }
            if (other.minY < this.minY) {
                this.minY = other.minY;
            }
            if (other.maxX() > maxX()) {
                this.width = other.maxX() - this.minX;
            }
            if (other.maxY() > maxY()) {
                this.height = other.maxY() - this.minY;
            }
        }

        public String toString() {
            StringBuilder sb;
            new StringBuilder();
            return sb.append("[").append(this.minX).append(" ").append(this.minY).append(" ").append(this.width).append(" ").append(this.height).append("]").toString();
        }
    }

    public static class Style implements Cloneable {
        public static final int FONT_WEIGHT_BOLD = 700;
        public static final int FONT_WEIGHT_BOLDER = 1;
        public static final int FONT_WEIGHT_LIGHTER = -1;
        public static final int FONT_WEIGHT_NORMAL = 400;
        public CSSClipRect clip;
        public String clipPath;
        public FillRule clipRule;
        public Colour color;
        public TextDirection direction;
        public Boolean display;
        public SvgPaint fill;
        public Float fillOpacity;
        public FillRule fillRule;
        public List<String> fontFamily;
        public Length fontSize;
        public FontStyle fontStyle;
        public Integer fontWeight;
        public String markerEnd;
        public String markerMid;
        public String markerStart;
        public String mask;
        public Float opacity;
        public Boolean overflow;
        public SvgPaint solidColor;
        public Float solidOpacity;
        public long specifiedFlags = 0;
        public SvgPaint stopColor;
        public Float stopOpacity;
        public SvgPaint stroke;
        public Length[] strokeDashArray;
        public Length strokeDashOffset;
        public LineCaps strokeLineCap;
        public LineJoin strokeLineJoin;
        public Float strokeMiterLimit;
        public Float strokeOpacity;
        public Length strokeWidth;
        public TextAnchor textAnchor;
        public TextDecoration textDecoration;
        public VectorEffect vectorEffect;
        public SvgPaint viewportFill;
        public Float viewportFillOpacity;
        public Boolean visibility;

        public enum FillRule {
        }

        public enum FontStyle {
        }

        public enum LineCaps {
        }

        public enum LineJoin {
        }

        public enum TextAnchor {
        }

        public enum TextDecoration {
        }

        public enum TextDirection {
        }

        public enum VectorEffect {
        }

        public Style() {
        }

        public static Style getDefaultStyle() {
            Style style;
            Length length;
            Length length2;
            Length length3;
            new Style();
            Style def = style;
            def.specifiedFlags = -1;
            def.fill = Colour.BLACK;
            def.fillRule = FillRule.NonZero;
            def.fillOpacity = Float.valueOf(1.0f);
            def.stroke = null;
            def.strokeOpacity = Float.valueOf(1.0f);
            new Length(1.0f);
            def.strokeWidth = length;
            def.strokeLineCap = LineCaps.Butt;
            def.strokeLineJoin = LineJoin.Miter;
            def.strokeMiterLimit = Float.valueOf(4.0f);
            def.strokeDashArray = null;
            new Length(0.0f);
            def.strokeDashOffset = length2;
            def.opacity = Float.valueOf(1.0f);
            def.color = Colour.BLACK;
            def.fontFamily = null;
            new Length(12.0f, Unit.f357pt);
            def.fontSize = length3;
            def.fontWeight = Integer.valueOf(FONT_WEIGHT_NORMAL);
            def.fontStyle = FontStyle.Normal;
            def.textDecoration = TextDecoration.None;
            def.direction = TextDirection.LTR;
            def.textAnchor = TextAnchor.Start;
            def.overflow = true;
            def.clip = null;
            def.markerStart = null;
            def.markerMid = null;
            def.markerEnd = null;
            def.display = Boolean.TRUE;
            def.visibility = Boolean.TRUE;
            def.stopColor = Colour.BLACK;
            def.stopOpacity = Float.valueOf(1.0f);
            def.clipPath = null;
            def.clipRule = FillRule.NonZero;
            def.mask = null;
            def.solidColor = null;
            def.solidOpacity = Float.valueOf(1.0f);
            def.viewportFill = null;
            def.viewportFillOpacity = Float.valueOf(1.0f);
            def.vectorEffect = VectorEffect.None;
            return def;
        }

        public void resetNonInheritingProperties() {
            resetNonInheritingProperties(false);
        }

        public void resetNonInheritingProperties(boolean isRootSVG) {
            this.display = Boolean.TRUE;
            this.overflow = isRootSVG ? Boolean.TRUE : Boolean.FALSE;
            this.clip = null;
            this.clipPath = null;
            this.opacity = Float.valueOf(1.0f);
            this.stopColor = Colour.BLACK;
            this.stopOpacity = Float.valueOf(1.0f);
            this.mask = null;
            this.solidColor = null;
            this.solidOpacity = Float.valueOf(1.0f);
            this.viewportFill = null;
            this.viewportFillOpacity = Float.valueOf(1.0f);
            this.vectorEffect = VectorEffect.None;
        }

        /* access modifiers changed from: protected */
        public Object clone() {
            Throwable th;
            try {
                Style obj = (Style) super.clone();
                if (this.strokeDashArray != null) {
                    obj.strokeDashArray = (Length[]) this.strokeDashArray.clone();
                }
                return obj;
            } catch (CloneNotSupportedException e) {
                CloneNotSupportedException e2 = e;
                Throwable th2 = th;
                new InternalError(e2.toString());
                throw th2;
            }
        }
    }

    protected static abstract class SvgPaint implements Cloneable {
        protected SvgPaint() {
        }
    }

    public static class Colour extends SvgPaint {
        public static final Colour BLACK;
        public int colour;

        static {
            Colour colour2;
            new Colour(0);
            BLACK = colour2;
        }

        public Colour(int val) {
            this.colour = val;
        }

        public String toString() {
            return String.format("#%06x", new Object[]{Integer.valueOf(this.colour)});
        }
    }

    public static class CurrentColor extends SvgPaint {
        private static CurrentColor instance;

        static {
            CurrentColor currentColor;
            new CurrentColor();
            instance = currentColor;
        }

        private CurrentColor() {
        }

        public static CurrentColor getInstance() {
            return instance;
        }
    }

    public static class PaintReference extends SvgPaint {
        public SvgPaint fallback;
        public String href;

        public PaintReference(String href2, SvgPaint fallback2) {
            this.href = href2;
            this.fallback = fallback2;
        }

        public String toString() {
            StringBuilder sb;
            new StringBuilder();
            return sb.append(this.href).append(" ").append(this.fallback).toString();
        }
    }

    public static class Length implements Cloneable {
        Unit unit = Unit.f358px;
        float value = 0.0f;

        public Length(float value2, Unit unit2) {
            this.value = value2;
            this.unit = unit2;
        }

        public Length(float value2) {
            this.value = value2;
            this.unit = Unit.f358px;
        }

        public float floatValue() {
            return this.value;
        }

        public float floatValueX(SVGAndroidRenderer sVGAndroidRenderer) {
            SVGAndroidRenderer renderer = sVGAndroidRenderer;
            switch (this.unit) {
                case f358px:
                    return this.value;
                case f352em:
                    return this.value * renderer.getCurrentFontSize();
                case f353ex:
                    return this.value * renderer.getCurrentFontXHeight();
                case f354in:
                    return this.value * renderer.getDPI();
                case f351cm:
                    return (this.value * renderer.getDPI()) / 2.54f;
                case f355mm:
                    return (this.value * renderer.getDPI()) / 25.4f;
                case f357pt:
                    return (this.value * renderer.getDPI()) / 72.0f;
                case f356pc:
                    return (this.value * renderer.getDPI()) / 6.0f;
                case percent:
                    Box viewPortUser = renderer.getCurrentViewPortInUserUnits();
                    if (viewPortUser == null) {
                        return this.value;
                    }
                    return (this.value * viewPortUser.width) / 100.0f;
                default:
                    return this.value;
            }
        }

        public float floatValueY(SVGAndroidRenderer sVGAndroidRenderer) {
            SVGAndroidRenderer renderer = sVGAndroidRenderer;
            if (this.unit != Unit.percent) {
                return floatValueX(renderer);
            }
            Box viewPortUser = renderer.getCurrentViewPortInUserUnits();
            if (viewPortUser == null) {
                return this.value;
            }
            return (this.value * viewPortUser.height) / 100.0f;
        }

        public float floatValue(SVGAndroidRenderer sVGAndroidRenderer) {
            SVGAndroidRenderer renderer = sVGAndroidRenderer;
            if (this.unit != Unit.percent) {
                return floatValueX(renderer);
            }
            Box viewPortUser = renderer.getCurrentViewPortInUserUnits();
            if (viewPortUser == null) {
                return this.value;
            }
            float w = viewPortUser.width;
            float h = viewPortUser.height;
            if (w == h) {
                return (this.value * w) / 100.0f;
            }
            return (this.value * ((float) (Math.sqrt((double) ((w * w) + (h * h))) / SVG.SQRT2))) / 100.0f;
        }

        public float floatValue(SVGAndroidRenderer sVGAndroidRenderer, float f) {
            SVGAndroidRenderer renderer = sVGAndroidRenderer;
            float max = f;
            if (this.unit == Unit.percent) {
                return (this.value * max) / 100.0f;
            }
            return floatValueX(renderer);
        }

        public float floatValue(float f) {
            float dpi = f;
            switch (this.unit) {
                case f358px:
                    return this.value;
                case f354in:
                    return this.value * dpi;
                case f351cm:
                    return (this.value * dpi) / 2.54f;
                case f355mm:
                    return (this.value * dpi) / 25.4f;
                case f357pt:
                    return (this.value * dpi) / 72.0f;
                case f356pc:
                    return (this.value * dpi) / 6.0f;
                default:
                    return this.value;
            }
        }

        public boolean isZero() {
            return this.value == 0.0f;
        }

        public boolean isNegative() {
            return this.value < 0.0f;
        }

        public String toString() {
            StringBuilder sb;
            new StringBuilder();
            return sb.append(String.valueOf(this.value)).append(this.unit).toString();
        }
    }

    public static class CSSClipRect {
        public Length bottom;
        public Length left;
        public Length right;
        public Length top;

        public CSSClipRect(Length top2, Length right2, Length bottom2, Length left2) {
            this.top = top2;
            this.right = right2;
            this.bottom = bottom2;
            this.left = left2;
        }
    }

    public static class SvgObject {
        public SVG document;
        public SvgContainer parent;

        public SvgObject() {
        }

        public String toString() {
            return getClass().getSimpleName();
        }
    }

    public static class SvgElementBase extends SvgObject {
        public Style baseStyle = null;
        public List<String> classNames = null;

        /* renamed from: id */
        public String f337id = null;
        public Boolean spacePreserve = null;
        public Style style = null;

        public SvgElementBase() {
        }
    }

    public static class SvgElement extends SvgElementBase {
        public Box boundingBox = null;

        public SvgElement() {
        }
    }

    public static class SvgConditionalElement extends SvgElement implements SvgConditional {
        public String requiredExtensions = null;
        public Set<String> requiredFeatures = null;
        public Set<String> requiredFonts = null;
        public Set<String> requiredFormats = null;
        public Set<String> systemLanguage = null;

        public SvgConditionalElement() {
        }

        public void setRequiredFeatures(Set<String> features) {
            Set<String> set = features;
            this.requiredFeatures = set;
        }

        public Set<String> getRequiredFeatures() {
            return this.requiredFeatures;
        }

        public void setRequiredExtensions(String extensions) {
            String str = extensions;
            this.requiredExtensions = str;
        }

        public String getRequiredExtensions() {
            return this.requiredExtensions;
        }

        public void setSystemLanguage(Set<String> languages) {
            Set<String> set = languages;
            this.systemLanguage = set;
        }

        public Set<String> getSystemLanguage() {
            return this.systemLanguage;
        }

        public void setRequiredFormats(Set<String> mimeTypes) {
            Set<String> set = mimeTypes;
            this.requiredFormats = set;
        }

        public Set<String> getRequiredFormats() {
            return this.requiredFormats;
        }

        public void setRequiredFonts(Set<String> fontNames) {
            Set<String> set = fontNames;
            this.requiredFonts = set;
        }

        public Set<String> getRequiredFonts() {
            return this.requiredFonts;
        }
    }

    public static class SvgConditionalContainer extends SvgElement implements SvgContainer, SvgConditional {
        public List<SvgObject> children;
        public String requiredExtensions = null;
        public Set<String> requiredFeatures = null;
        public Set<String> requiredFonts = null;
        public Set<String> requiredFormats = null;
        public Set<String> systemLanguage = null;

        public SvgConditionalContainer() {
            List<SvgObject> list;
            new ArrayList();
            this.children = list;
        }

        public List<SvgObject> getChildren() {
            return this.children;
        }

        public void addChild(SvgObject elem) throws SAXException {
            boolean add = this.children.add(elem);
        }

        public void setRequiredFeatures(Set<String> features) {
            Set<String> set = features;
            this.requiredFeatures = set;
        }

        public Set<String> getRequiredFeatures() {
            return this.requiredFeatures;
        }

        public void setRequiredExtensions(String extensions) {
            String str = extensions;
            this.requiredExtensions = str;
        }

        public String getRequiredExtensions() {
            return this.requiredExtensions;
        }

        public void setSystemLanguage(Set<String> languages) {
            Set<String> set = languages;
            this.systemLanguage = set;
        }

        public Set<String> getSystemLanguage() {
            return null;
        }

        public void setRequiredFormats(Set<String> mimeTypes) {
            Set<String> set = mimeTypes;
            this.requiredFormats = set;
        }

        public Set<String> getRequiredFormats() {
            return this.requiredFormats;
        }

        public void setRequiredFonts(Set<String> fontNames) {
            Set<String> set = fontNames;
            this.requiredFonts = set;
        }

        public Set<String> getRequiredFonts() {
            return this.requiredFonts;
        }
    }

    public static class SvgPreserveAspectRatioContainer extends SvgConditionalContainer {
        public PreserveAspectRatio preserveAspectRatio = null;

        public SvgPreserveAspectRatioContainer() {
        }
    }

    public static class SvgViewBoxContainer extends SvgPreserveAspectRatioContainer {
        public Box viewBox;

        public SvgViewBoxContainer() {
        }
    }

    public static class Svg extends SvgViewBoxContainer {
        public Length height;
        public String version;
        public Length width;

        /* renamed from: x */
        public Length f335x;

        /* renamed from: y */
        public Length f336y;

        public Svg() {
        }
    }

    public static class Group extends SvgConditionalContainer implements HasTransform {
        public Matrix transform;

        public Group() {
        }

        public void setTransform(Matrix transform2) {
            Matrix matrix = transform2;
            this.transform = matrix;
        }
    }

    public static class Defs extends Group implements NotDirectlyRendered {
        public Defs() {
        }
    }

    protected static abstract class GraphicsElement extends SvgConditionalElement implements HasTransform {
        public Matrix transform;

        protected GraphicsElement() {
        }

        public void setTransform(Matrix transform2) {
            Matrix matrix = transform2;
            this.transform = matrix;
        }
    }

    public static class Use extends Group {
        public Length height;
        public String href;
        public Length width;

        /* renamed from: x */
        public Length f359x;

        /* renamed from: y */
        public Length f360y;

        public Use() {
        }
    }

    public static class Path extends GraphicsElement {

        /* renamed from: d */
        public PathDefinition f328d;
        public Float pathLength;

        public Path() {
        }

        public /* bridge */ /* synthetic */ void setTransform(Matrix matrix) {
            super.setTransform(matrix);
        }
    }

    public static class Rect extends GraphicsElement {
        public Length height;

        /* renamed from: rx */
        public Length f331rx;

        /* renamed from: ry */
        public Length f332ry;
        public Length width;

        /* renamed from: x */
        public Length f333x;

        /* renamed from: y */
        public Length f334y;

        public Rect() {
        }

        public /* bridge */ /* synthetic */ void setTransform(Matrix matrix) {
            super.setTransform(matrix);
        }
    }

    public static class Circle extends GraphicsElement {

        /* renamed from: cx */
        public Length f313cx;

        /* renamed from: cy */
        public Length f314cy;

        /* renamed from: r */
        public Length f315r;

        public Circle() {
        }

        public /* bridge */ /* synthetic */ void setTransform(Matrix matrix) {
            super.setTransform(matrix);
        }
    }

    public static class Ellipse extends GraphicsElement {

        /* renamed from: cx */
        public Length f316cx;

        /* renamed from: cy */
        public Length f317cy;

        /* renamed from: rx */
        public Length f318rx;

        /* renamed from: ry */
        public Length f319ry;

        public Ellipse() {
        }

        public /* bridge */ /* synthetic */ void setTransform(Matrix matrix) {
            super.setTransform(matrix);
        }
    }

    public static class Line extends GraphicsElement {

        /* renamed from: x1 */
        public Length f322x1;

        /* renamed from: x2 */
        public Length f323x2;

        /* renamed from: y1 */
        public Length f324y1;

        /* renamed from: y2 */
        public Length f325y2;

        public Line() {
        }

        public /* bridge */ /* synthetic */ void setTransform(Matrix matrix) {
            super.setTransform(matrix);
        }
    }

    public static class PolyLine extends GraphicsElement {
        public float[] points;

        public PolyLine() {
        }

        public /* bridge */ /* synthetic */ void setTransform(Matrix matrix) {
            super.setTransform(matrix);
        }
    }

    public static class Polygon extends PolyLine {
        public Polygon() {
        }
    }

    public static class TextContainer extends SvgConditionalContainer {
        public TextContainer() {
        }

        public void addChild(SvgObject svgObject) throws SAXException {
            Throwable th;
            StringBuilder sb;
            SvgObject elem = svgObject;
            if (elem instanceof TextChild) {
                boolean add = this.children.add(elem);
                return;
            }
            Throwable th2 = th;
            new StringBuilder();
            new SAXException(sb.append("Text content elements cannot contain ").append(elem).append(" elements.").toString());
            throw th2;
        }
    }

    public static class TextPositionedContainer extends TextContainer {

        /* renamed from: dx */
        public List<Length> f347dx;

        /* renamed from: dy */
        public List<Length> f348dy;

        /* renamed from: x */
        public List<Length> f349x;

        /* renamed from: y */
        public List<Length> f350y;

        public TextPositionedContainer() {
        }
    }

    public static class Text extends TextPositionedContainer implements TextRoot, HasTransform {
        public Matrix transform;

        public Text() {
        }

        public void setTransform(Matrix transform2) {
            Matrix matrix = transform2;
            this.transform = matrix;
        }
    }

    public static class TSpan extends TextPositionedContainer implements TextChild {
        private TextRoot textRoot;

        public TSpan() {
        }

        public void setTextRoot(TextRoot obj) {
            TextRoot textRoot2 = obj;
            this.textRoot = textRoot2;
        }

        public TextRoot getTextRoot() {
            return this.textRoot;
        }
    }

    public static class TextSequence extends SvgObject implements TextChild {
        public String text;
        private TextRoot textRoot;

        public TextSequence(String text2) {
            this.text = text2;
        }

        public String toString() {
            StringBuilder sb;
            new StringBuilder();
            return sb.append(getClass().getSimpleName()).append(" '").append(this.text).append("'").toString();
        }

        public void setTextRoot(TextRoot obj) {
            TextRoot textRoot2 = obj;
            this.textRoot = textRoot2;
        }

        public TextRoot getTextRoot() {
            return this.textRoot;
        }
    }

    public static class TRef extends TextContainer implements TextChild {
        public String href;
        private TextRoot textRoot;

        public TRef() {
        }

        public void setTextRoot(TextRoot obj) {
            TextRoot textRoot2 = obj;
            this.textRoot = textRoot2;
        }

        public TextRoot getTextRoot() {
            return this.textRoot;
        }
    }

    public static class TextPath extends TextContainer implements TextChild {
        public String href;
        public Length startOffset;
        private TextRoot textRoot;

        public TextPath() {
        }

        public void setTextRoot(TextRoot obj) {
            TextRoot textRoot2 = obj;
            this.textRoot = textRoot2;
        }

        public TextRoot getTextRoot() {
            return this.textRoot;
        }
    }

    public static class Switch extends Group {
        public Switch() {
        }
    }

    public static class Symbol extends SvgViewBoxContainer implements NotDirectlyRendered {
        public Symbol() {
        }
    }

    public static class Marker extends SvgViewBoxContainer implements NotDirectlyRendered {
        public Length markerHeight;
        public boolean markerUnitsAreUser;
        public Length markerWidth;
        public Float orient;
        public Length refX;
        public Length refY;

        public Marker() {
        }
    }

    public static class GradientElement extends SvgElementBase implements SvgContainer {
        public List<SvgObject> children;
        public Matrix gradientTransform;
        public Boolean gradientUnitsAreUser;
        public String href;
        public GradientSpread spreadMethod;

        public GradientElement() {
            List<SvgObject> list;
            new ArrayList();
            this.children = list;
        }

        public List<SvgObject> getChildren() {
            return this.children;
        }

        public void addChild(SvgObject svgObject) throws SAXException {
            Throwable th;
            StringBuilder sb;
            SvgObject elem = svgObject;
            if (elem instanceof Stop) {
                boolean add = this.children.add(elem);
                return;
            }
            Throwable th2 = th;
            new StringBuilder();
            new SAXException(sb.append("Gradient elements cannot contain ").append(elem).append(" elements.").toString());
            throw th2;
        }
    }

    public static class Stop extends SvgElementBase implements SvgContainer {
        public Float offset;

        public Stop() {
        }

        public List<SvgObject> getChildren() {
            return Collections.emptyList();
        }

        public void addChild(SvgObject elem) throws SAXException {
        }
    }

    public static class SvgLinearGradient extends GradientElement {

        /* renamed from: x1 */
        public Length f338x1;

        /* renamed from: x2 */
        public Length f339x2;

        /* renamed from: y1 */
        public Length f340y1;

        /* renamed from: y2 */
        public Length f341y2;

        public SvgLinearGradient() {
        }
    }

    public static class SvgRadialGradient extends GradientElement {

        /* renamed from: cx */
        public Length f342cx;

        /* renamed from: cy */
        public Length f343cy;

        /* renamed from: fx */
        public Length f344fx;

        /* renamed from: fy */
        public Length f345fy;

        /* renamed from: r */
        public Length f346r;

        public SvgRadialGradient() {
        }
    }

    public static class ClipPath extends Group implements NotDirectlyRendered {
        public Boolean clipPathUnitsAreUser;

        public ClipPath() {
        }
    }

    public static class Pattern extends SvgViewBoxContainer implements NotDirectlyRendered {
        public Length height;
        public String href;
        public Boolean patternContentUnitsAreUser;
        public Matrix patternTransform;
        public Boolean patternUnitsAreUser;
        public Length width;

        /* renamed from: x */
        public Length f329x;

        /* renamed from: y */
        public Length f330y;

        public Pattern() {
        }
    }

    public static class Image extends SvgPreserveAspectRatioContainer implements HasTransform {
        public Length height;
        public String href;
        public Matrix transform;
        public Length width;

        /* renamed from: x */
        public Length f320x;

        /* renamed from: y */
        public Length f321y;

        public Image() {
        }

        public void setTransform(Matrix transform2) {
            Matrix matrix = transform2;
            this.transform = matrix;
        }
    }

    public static class View extends SvgViewBoxContainer implements NotDirectlyRendered {
        public View() {
        }
    }

    public static class Mask extends SvgConditionalContainer implements NotDirectlyRendered {
        public Length height;
        public Boolean maskContentUnitsAreUser;
        public Boolean maskUnitsAreUser;
        public Length width;

        /* renamed from: x */
        public Length f326x;

        /* renamed from: y */
        public Length f327y;

        public Mask() {
        }
    }

    public static class SolidColor extends SvgElementBase implements SvgContainer {
        public Length solidColor;
        public Length solidOpacity;

        public SolidColor() {
        }

        public List<SvgObject> getChildren() {
            return Collections.emptyList();
        }

        public void addChild(SvgObject elem) throws SAXException {
        }
    }

    /* access modifiers changed from: protected */
    public void setTitle(String title2) {
        String str = title2;
        this.title = str;
    }

    /* access modifiers changed from: protected */
    public void setDesc(String desc2) {
        String str = desc2;
        this.desc = str;
    }

    /* access modifiers changed from: protected */
    public SVGExternalFileResolver getFileResolver() {
        return this.fileResolver;
    }

    public static class PathDefinition implements PathInterface {
        private static final byte ARCTO = 4;
        private static final byte CLOSE = 8;
        private static final byte CUBICTO = 2;
        private static final byte LINETO = 1;
        private static final byte MOVETO = 0;
        private static final byte QUADTO = 3;
        private byte[] commands;
        private int commandsLength;
        private float[] coords;
        private int coordsLength;

        public PathDefinition() {
            this.commands = null;
            this.commandsLength = 0;
            this.coords = null;
            this.coordsLength = 0;
            this.commands = new byte[8];
            this.coords = new float[16];
        }

        public boolean isEmpty() {
            return this.commandsLength == 0;
        }

        private void addCommand(byte b) {
            byte value = b;
            if (this.commandsLength == this.commands.length) {
                byte[] newCommands = new byte[(this.commands.length * 2)];
                System.arraycopy(this.commands, 0, newCommands, 0, this.commands.length);
                this.commands = newCommands;
            }
            byte[] bArr = this.commands;
            int i = this.commandsLength;
            int i2 = i + 1;
            this.commandsLength = i2;
            bArr[i] = value;
        }

        private void coordsEnsure(int num) {
            if (this.coords.length < this.coordsLength + num) {
                float[] newCoords = new float[(this.coords.length * 2)];
                System.arraycopy(this.coords, 0, newCoords, 0, this.coords.length);
                this.coords = newCoords;
            }
        }

        public void moveTo(float x, float y) {
            addCommand((byte) 0);
            coordsEnsure(2);
            float[] fArr = this.coords;
            int i = this.coordsLength;
            int i2 = i + 1;
            this.coordsLength = i2;
            fArr[i] = x;
            float[] fArr2 = this.coords;
            int i3 = this.coordsLength;
            int i4 = i3 + 1;
            this.coordsLength = i4;
            fArr2[i3] = y;
        }

        public void lineTo(float x, float y) {
            addCommand((byte) 1);
            coordsEnsure(2);
            float[] fArr = this.coords;
            int i = this.coordsLength;
            int i2 = i + 1;
            this.coordsLength = i2;
            fArr[i] = x;
            float[] fArr2 = this.coords;
            int i3 = this.coordsLength;
            int i4 = i3 + 1;
            this.coordsLength = i4;
            fArr2[i3] = y;
        }

        public void cubicTo(float x1, float y1, float x2, float y2, float x3, float y3) {
            addCommand((byte) 2);
            coordsEnsure(6);
            float[] fArr = this.coords;
            int i = this.coordsLength;
            int i2 = i + 1;
            this.coordsLength = i2;
            fArr[i] = x1;
            float[] fArr2 = this.coords;
            int i3 = this.coordsLength;
            int i4 = i3 + 1;
            this.coordsLength = i4;
            fArr2[i3] = y1;
            float[] fArr3 = this.coords;
            int i5 = this.coordsLength;
            int i6 = i5 + 1;
            this.coordsLength = i6;
            fArr3[i5] = x2;
            float[] fArr4 = this.coords;
            int i7 = this.coordsLength;
            int i8 = i7 + 1;
            this.coordsLength = i8;
            fArr4[i7] = y2;
            float[] fArr5 = this.coords;
            int i9 = this.coordsLength;
            int i10 = i9 + 1;
            this.coordsLength = i10;
            fArr5[i9] = x3;
            float[] fArr6 = this.coords;
            int i11 = this.coordsLength;
            int i12 = i11 + 1;
            this.coordsLength = i12;
            fArr6[i11] = y3;
        }

        public void quadTo(float x1, float y1, float x2, float y2) {
            addCommand((byte) 3);
            coordsEnsure(4);
            float[] fArr = this.coords;
            int i = this.coordsLength;
            int i2 = i + 1;
            this.coordsLength = i2;
            fArr[i] = x1;
            float[] fArr2 = this.coords;
            int i3 = this.coordsLength;
            int i4 = i3 + 1;
            this.coordsLength = i4;
            fArr2[i3] = y1;
            float[] fArr3 = this.coords;
            int i5 = this.coordsLength;
            int i6 = i5 + 1;
            this.coordsLength = i6;
            fArr3[i5] = x2;
            float[] fArr4 = this.coords;
            int i7 = this.coordsLength;
            int i8 = i7 + 1;
            this.coordsLength = i8;
            fArr4[i7] = y2;
        }

        public void arcTo(float f, float f2, float f3, boolean largeArcFlag, boolean sweepFlag, float f4, float f5) {
            float rx = f;
            float ry = f2;
            float xAxisRotation = f3;
            float x = f4;
            float y = f5;
            addCommand((byte) (4 | (largeArcFlag ? 2 : 0) | (sweepFlag ? 1 : 0)));
            coordsEnsure(5);
            float[] fArr = this.coords;
            int i = this.coordsLength;
            this.coordsLength = i + 1;
            fArr[i] = rx;
            float[] fArr2 = this.coords;
            int i2 = this.coordsLength;
            this.coordsLength = i2 + 1;
            fArr2[i2] = ry;
            float[] fArr3 = this.coords;
            int i3 = this.coordsLength;
            this.coordsLength = i3 + 1;
            fArr3[i3] = xAxisRotation;
            float[] fArr4 = this.coords;
            int i4 = this.coordsLength;
            this.coordsLength = i4 + 1;
            fArr4[i4] = x;
            float[] fArr5 = this.coords;
            int i5 = this.coordsLength;
            this.coordsLength = i5 + 1;
            fArr5[i5] = y;
        }

        public void close() {
            addCommand((byte) 8);
        }

        public void enumeratePath(PathInterface pathInterface) {
            PathInterface handler = pathInterface;
            int coordsPos = 0;
            for (int commandPos = 0; commandPos < this.commandsLength; commandPos++) {
                byte command = this.commands[commandPos];
                switch (command) {
                    case 0:
                        int i = coordsPos;
                        int coordsPos2 = coordsPos + 1;
                        int i2 = coordsPos2;
                        coordsPos = coordsPos2 + 1;
                        handler.moveTo(this.coords[i], this.coords[i2]);
                        break;
                    case 1:
                        int i3 = coordsPos;
                        int coordsPos3 = coordsPos + 1;
                        int i4 = coordsPos3;
                        coordsPos = coordsPos3 + 1;
                        handler.lineTo(this.coords[i3], this.coords[i4]);
                        break;
                    case 2:
                        int i5 = coordsPos;
                        int coordsPos4 = coordsPos + 1;
                        int i6 = coordsPos4;
                        int coordsPos5 = coordsPos4 + 1;
                        int i7 = coordsPos5;
                        int coordsPos6 = coordsPos5 + 1;
                        int i8 = coordsPos6;
                        int coordsPos7 = coordsPos6 + 1;
                        int i9 = coordsPos7;
                        int coordsPos8 = coordsPos7 + 1;
                        int i10 = coordsPos8;
                        coordsPos = coordsPos8 + 1;
                        handler.cubicTo(this.coords[i5], this.coords[i6], this.coords[i7], this.coords[i8], this.coords[i9], this.coords[i10]);
                        break;
                    case 3:
                        int i11 = coordsPos;
                        int coordsPos9 = coordsPos + 1;
                        int i12 = coordsPos9;
                        int coordsPos10 = coordsPos9 + 1;
                        int i13 = coordsPos10;
                        int coordsPos11 = coordsPos10 + 1;
                        int i14 = coordsPos11;
                        coordsPos = coordsPos11 + 1;
                        handler.quadTo(this.coords[i11], this.coords[i12], this.coords[i13], this.coords[i14]);
                        break;
                    case 8:
                        handler.close();
                        break;
                    default:
                        int i15 = coordsPos;
                        int coordsPos12 = coordsPos + 1;
                        int i16 = coordsPos12;
                        int coordsPos13 = coordsPos12 + 1;
                        int i17 = coordsPos13;
                        int coordsPos14 = coordsPos13 + 1;
                        int i18 = coordsPos14;
                        int coordsPos15 = coordsPos14 + 1;
                        int i19 = coordsPos15;
                        coordsPos = coordsPos15 + 1;
                        handler.arcTo(this.coords[i15], this.coords[i16], this.coords[i17], (command & 2) != 0, (command & 1) != 0, this.coords[i18], this.coords[i19]);
                        break;
                }
            }
        }
    }

    /* access modifiers changed from: protected */
    public SvgObject getElementById(String str) {
        String id = str;
        if (id == null || id.length() == 0) {
            return null;
        }
        if (id.equals(this.rootElement.f337id)) {
            return this.rootElement;
        }
        if (this.idToElementMap.containsKey(id)) {
            return this.idToElementMap.get(id);
        }
        SvgElementBase result = getElementById(this.rootElement, id);
        SvgElementBase put = this.idToElementMap.put(id, result);
        return result;
    }

    private SvgElementBase getElementById(SvgContainer svgContainer, String str) {
        SvgElementBase found;
        SvgContainer obj = svgContainer;
        String id = str;
        SvgElementBase elem = (SvgElementBase) obj;
        if (id.equals(elem.f337id)) {
            return elem;
        }
        for (SvgObject child : obj.getChildren()) {
            if (child instanceof SvgElementBase) {
                SvgElementBase childElem = (SvgElementBase) child;
                if (id.equals(childElem.f337id)) {
                    return childElem;
                }
                if ((child instanceof SvgContainer) && (found = getElementById((SvgContainer) child, id)) != null) {
                    return found;
                }
            }
        }
        return null;
    }

    /* access modifiers changed from: protected */
    public List<SvgObject> getElementsByTagName(Class clazz) {
        return getElementsByTagName(this.rootElement, clazz);
    }

    private List<SvgObject> getElementsByTagName(SvgContainer svgContainer, Class cls) {
        List<SvgObject> list;
        SvgContainer obj = svgContainer;
        Class clazz = cls;
        new ArrayList();
        List<SvgObject> result = list;
        if (obj.getClass() == clazz) {
            boolean add = result.add((SvgObject) obj);
        }
        for (SvgObject child : obj.getChildren()) {
            if (child.getClass() == clazz) {
                boolean add2 = result.add(child);
            }
            if (child instanceof SvgContainer) {
                List<SvgObject> elementsByTagName = getElementsByTagName((SvgContainer) child, clazz);
            }
        }
        return result;
    }
}
