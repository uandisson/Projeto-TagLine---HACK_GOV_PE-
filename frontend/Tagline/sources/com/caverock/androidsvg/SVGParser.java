package com.caverock.androidsvg;

import android.graphics.Matrix;
import android.support.p000v4.media.session.PlaybackStateCompat;
import android.support.p000v4.view.MotionEventCompat;
import android.util.Log;
import com.caverock.androidsvg.CSSParser;
import com.caverock.androidsvg.PreserveAspectRatio;
import com.caverock.androidsvg.SVG;
import com.microsoft.appcenter.ingestion.models.CommonProperties;
import gnu.expr.Declaration;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.zip.GZIPInputStream;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.ext.DefaultHandler2;

public class SVGParser extends DefaultHandler2 {
    private static final String CURRENTCOLOR = "currentColor";
    private static final String FEATURE_STRING_PREFIX = "http://www.w3.org/TR/SVG11/feature#";
    private static final String NONE = "none";
    private static final String SVG_NAMESPACE = "http://www.w3.org/2000/svg";
    private static final String TAG = "SVGParser";
    private static final String VALID_DISPLAY_VALUES = "|inline|block|list-item|run-in|compact|marker|table|inline-table|table-row-group|table-header-group|table-footer-group|table-row|table-column-group|table-column|table-cell|table-caption|none|";
    private static final String VALID_VISIBILITY_VALUES = "|visible|hidden|collapse|";
    private static final String XLINK_NAMESPACE = "http://www.w3.org/1999/xlink";
    private SVG.SvgContainer currentElement = null;
    private int ignoreDepth;
    private boolean ignoring = false;
    private boolean inMetadataElement = false;
    private boolean inStyleElement = false;
    private StringBuilder metadataElementContents = null;
    private SVGElem metadataTag = null;
    private StringBuilder styleElementContents = null;
    private Set<String> supportedFormats = null;
    private SVG svgDocument = null;

    public SVGParser() {
    }

    private enum SVGElem {
        ;
        
        private static final Map<String, SVGElem> cache = null;

        static {
            Map<String, SVGElem> map;
            new HashMap();
            cache = map;
        }

        public static SVGElem fromString(String str) {
            String str2 = str;
            SVGElem elem = cache.get(str2);
            if (elem != null) {
                return elem;
            }
            if (str2.equals("switch")) {
                SVGElem put = cache.put(str2, SWITCH);
                return SWITCH;
            }
            try {
                SVGElem elem2 = valueOf(str2);
                if (elem2 != SWITCH) {
                    SVGElem put2 = cache.put(str2, elem2);
                    return elem2;
                }
            } catch (IllegalArgumentException e) {
                IllegalArgumentException illegalArgumentException = e;
            }
            SVGElem put3 = cache.put(str2, UNSUPPORTED);
            return UNSUPPORTED;
        }
    }

    private enum SVGAttr {
        ;
        
        private static final Map<String, SVGAttr> cache = null;

        static {
            Map<String, SVGAttr> map;
            new HashMap();
            cache = map;
        }

        public static SVGAttr fromString(String str) {
            String str2 = str;
            SVGAttr attr = cache.get(str2);
            if (attr != null) {
                return attr;
            }
            if (str2.equals("class")) {
                SVGAttr put = cache.put(str2, CLASS);
                return CLASS;
            } else if (str2.indexOf(95) != -1) {
                SVGAttr put2 = cache.put(str2, UNSUPPORTED);
                return UNSUPPORTED;
            } else {
                try {
                    SVGAttr attr2 = valueOf(str2.replace('-', '_'));
                    if (attr2 != CLASS) {
                        SVGAttr put3 = cache.put(str2, attr2);
                        return attr2;
                    }
                } catch (IllegalArgumentException e) {
                    IllegalArgumentException illegalArgumentException = e;
                }
                SVGAttr put4 = cache.put(str2, UNSUPPORTED);
                return UNSUPPORTED;
            }
        }
    }

    private static class ColourKeywords {
        private static final Map<String, Integer> colourKeywords;

        private ColourKeywords() {
        }

        static {
            Map<String, Integer> map;
            new HashMap(47);
            colourKeywords = map;
            Integer put = colourKeywords.put("aliceblue", 15792383);
            Integer put2 = colourKeywords.put("antiquewhite", 16444375);
            Integer put3 = colourKeywords.put("aqua", 65535);
            Integer put4 = colourKeywords.put("aquamarine", 8388564);
            Integer put5 = colourKeywords.put("azure", 15794175);
            Integer put6 = colourKeywords.put("beige", 16119260);
            Integer put7 = colourKeywords.put("bisque", 16770244);
            Integer put8 = colourKeywords.put("black", 0);
            Integer put9 = colourKeywords.put("blanchedalmond", 16772045);
            Integer put10 = colourKeywords.put("blue", 255);
            Integer put11 = colourKeywords.put("blueviolet", 9055202);
            Integer put12 = colourKeywords.put("brown", 10824234);
            Integer put13 = colourKeywords.put("burlywood", 14596231);
            Integer put14 = colourKeywords.put("cadetblue", 6266528);
            Integer put15 = colourKeywords.put("chartreuse", 8388352);
            Integer put16 = colourKeywords.put("chocolate", 13789470);
            Integer put17 = colourKeywords.put("coral", 16744272);
            Integer put18 = colourKeywords.put("cornflowerblue", 6591981);
            Integer put19 = colourKeywords.put("cornsilk", 16775388);
            Integer put20 = colourKeywords.put("crimson", 14423100);
            Integer put21 = colourKeywords.put("cyan", 65535);
            Integer put22 = colourKeywords.put("darkblue", 139);
            Integer put23 = colourKeywords.put("darkcyan", 35723);
            Integer put24 = colourKeywords.put("darkgoldenrod", 12092939);
            Integer put25 = colourKeywords.put("darkgray", 11119017);
            Integer put26 = colourKeywords.put("darkgreen", 25600);
            Integer put27 = colourKeywords.put("darkgrey", 11119017);
            Integer put28 = colourKeywords.put("darkkhaki", 12433259);
            Integer put29 = colourKeywords.put("darkmagenta", 9109643);
            Integer put30 = colourKeywords.put("darkolivegreen", 5597999);
            Integer put31 = colourKeywords.put("darkorange", 16747520);
            Integer put32 = colourKeywords.put("darkorchid", 10040012);
            Integer put33 = colourKeywords.put("darkred", 9109504);
            Integer put34 = colourKeywords.put("darksalmon", 15308410);
            Integer put35 = colourKeywords.put("darkseagreen", 9419919);
            Integer put36 = colourKeywords.put("darkslateblue", 4734347);
            Integer put37 = colourKeywords.put("darkslategray", 3100495);
            Integer put38 = colourKeywords.put("darkslategrey", 3100495);
            Integer put39 = colourKeywords.put("darkturquoise", 52945);
            Integer put40 = colourKeywords.put("darkviolet", 9699539);
            Integer put41 = colourKeywords.put("deeppink", 16716947);
            Integer put42 = colourKeywords.put("deepskyblue", 49151);
            Integer put43 = colourKeywords.put("dimgray", 6908265);
            Integer put44 = colourKeywords.put("dimgrey", 6908265);
            Integer put45 = colourKeywords.put("dodgerblue", 2003199);
            Integer put46 = colourKeywords.put("firebrick", 11674146);
            Integer put47 = colourKeywords.put("floralwhite", 16775920);
            Integer put48 = colourKeywords.put("forestgreen", 2263842);
            Integer put49 = colourKeywords.put("fuchsia", 16711935);
            Integer put50 = colourKeywords.put("gainsboro", 14474460);
            Integer put51 = colourKeywords.put("ghostwhite", 16316671);
            Integer put52 = colourKeywords.put("gold", 16766720);
            Integer put53 = colourKeywords.put("goldenrod", 14329120);
            Integer put54 = colourKeywords.put("gray", 8421504);
            Integer put55 = colourKeywords.put("green", 32768);
            Integer put56 = colourKeywords.put("greenyellow", 11403055);
            Integer put57 = colourKeywords.put("grey", 8421504);
            Integer put58 = colourKeywords.put("honeydew", 15794160);
            Integer put59 = colourKeywords.put("hotpink", 16738740);
            Integer put60 = colourKeywords.put("indianred", 13458524);
            Integer put61 = colourKeywords.put("indigo", 4915330);
            Integer put62 = colourKeywords.put("ivory", 16777200);
            Integer put63 = colourKeywords.put("khaki", 15787660);
            Integer put64 = colourKeywords.put("lavender", 15132410);
            Integer put65 = colourKeywords.put("lavenderblush", 16773365);
            Integer put66 = colourKeywords.put("lawngreen", 8190976);
            Integer put67 = colourKeywords.put("lemonchiffon", 16775885);
            Integer put68 = colourKeywords.put("lightblue", 11393254);
            Integer put69 = colourKeywords.put("lightcoral", 15761536);
            Integer put70 = colourKeywords.put("lightcyan", 14745599);
            Integer put71 = colourKeywords.put("lightgoldenrodyellow", 16448210);
            Integer put72 = colourKeywords.put("lightgray", 13882323);
            Integer put73 = colourKeywords.put("lightgreen", 9498256);
            Integer put74 = colourKeywords.put("lightgrey", 13882323);
            Integer put75 = colourKeywords.put("lightpink", 16758465);
            Integer put76 = colourKeywords.put("lightsalmon", 16752762);
            Integer put77 = colourKeywords.put("lightseagreen", 2142890);
            Integer put78 = colourKeywords.put("lightskyblue", 8900346);
            Integer put79 = colourKeywords.put("lightslategray", 7833753);
            Integer put80 = colourKeywords.put("lightslategrey", 7833753);
            Integer put81 = colourKeywords.put("lightsteelblue", 11584734);
            Integer put82 = colourKeywords.put("lightyellow", 16777184);
            Integer put83 = colourKeywords.put("lime", Integer.valueOf(MotionEventCompat.ACTION_POINTER_INDEX_MASK));
            Integer put84 = colourKeywords.put("limegreen", 3329330);
            Integer put85 = colourKeywords.put("linen", 16445670);
            Integer put86 = colourKeywords.put("magenta", 16711935);
            Integer put87 = colourKeywords.put("maroon", 8388608);
            Integer put88 = colourKeywords.put("mediumaquamarine", 6737322);
            Integer put89 = colourKeywords.put("mediumblue", 205);
            Integer put90 = colourKeywords.put("mediumorchid", 12211667);
            Integer put91 = colourKeywords.put("mediumpurple", 9662683);
            Integer put92 = colourKeywords.put("mediumseagreen", 3978097);
            Integer put93 = colourKeywords.put("mediumslateblue", 8087790);
            Integer put94 = colourKeywords.put("mediumspringgreen", 64154);
            Integer put95 = colourKeywords.put("mediumturquoise", 4772300);
            Integer put96 = colourKeywords.put("mediumvioletred", 13047173);
            Integer put97 = colourKeywords.put("midnightblue", 1644912);
            Integer put98 = colourKeywords.put("mintcream", 16121850);
            Integer put99 = colourKeywords.put("mistyrose", 16770273);
            Integer put100 = colourKeywords.put("moccasin", 16770229);
            Integer put101 = colourKeywords.put("navajowhite", 16768685);
            Integer put102 = colourKeywords.put("navy", 128);
            Integer put103 = colourKeywords.put("oldlace", 16643558);
            Integer put104 = colourKeywords.put("olive", 8421376);
            Integer put105 = colourKeywords.put("olivedrab", 7048739);
            Integer put106 = colourKeywords.put("orange", 16753920);
            Integer put107 = colourKeywords.put("orangered", 16729344);
            Integer put108 = colourKeywords.put("orchid", 14315734);
            Integer put109 = colourKeywords.put("palegoldenrod", 15657130);
            Integer put110 = colourKeywords.put("palegreen", 10025880);
            Integer put111 = colourKeywords.put("paleturquoise", 11529966);
            Integer put112 = colourKeywords.put("palevioletred", 14381203);
            Integer put113 = colourKeywords.put("papayawhip", 16773077);
            Integer put114 = colourKeywords.put("peachpuff", 16767673);
            Integer put115 = colourKeywords.put("peru", 13468991);
            Integer put116 = colourKeywords.put("pink", 16761035);
            Integer put117 = colourKeywords.put("plum", 14524637);
            Integer put118 = colourKeywords.put("powderblue", 11591910);
            Integer put119 = colourKeywords.put("purple", 8388736);
            Integer put120 = colourKeywords.put("red", 16711680);
            Integer put121 = colourKeywords.put("rosybrown", 12357519);
            Integer put122 = colourKeywords.put("royalblue", 4286945);
            Integer put123 = colourKeywords.put("saddlebrown", 9127187);
            Integer put124 = colourKeywords.put("salmon", 16416882);
            Integer put125 = colourKeywords.put("sandybrown", 16032864);
            Integer put126 = colourKeywords.put("seagreen", 3050327);
            Integer put127 = colourKeywords.put("seashell", 16774638);
            Integer put128 = colourKeywords.put("sienna", 10506797);
            Integer put129 = colourKeywords.put("silver", 12632256);
            Integer put130 = colourKeywords.put("skyblue", 8900331);
            Integer put131 = colourKeywords.put("slateblue", 6970061);
            Integer put132 = colourKeywords.put("slategray", 7372944);
            Integer put133 = colourKeywords.put("slategrey", 7372944);
            Integer put134 = colourKeywords.put("snow", 16775930);
            Integer put135 = colourKeywords.put("springgreen", 65407);
            Integer put136 = colourKeywords.put("steelblue", 4620980);
            Integer put137 = colourKeywords.put("tan", 13808780);
            Integer put138 = colourKeywords.put("teal", 32896);
            Integer put139 = colourKeywords.put("thistle", 14204888);
            Integer put140 = colourKeywords.put("tomato", 16737095);
            Integer put141 = colourKeywords.put("turquoise", 4251856);
            Integer put142 = colourKeywords.put("violet", 15631086);
            Integer put143 = colourKeywords.put("wheat", 16113331);
            Integer put144 = colourKeywords.put("white", 16777215);
            Integer put145 = colourKeywords.put("whitesmoke", 16119285);
            Integer put146 = colourKeywords.put("yellow", 16776960);
            Integer put147 = colourKeywords.put("yellowgreen", 10145074);
        }

        public static Integer get(String colourName) {
            return colourKeywords.get(colourName);
        }
    }

    private static class FontSizeKeywords {
        private static final Map<String, SVG.Length> fontSizeKeywords;

        private FontSizeKeywords() {
        }

        static {
            Map<String, SVG.Length> map;
            Object obj;
            Object obj2;
            Object obj3;
            Object obj4;
            Object obj5;
            Object obj6;
            Object obj7;
            Object obj8;
            Object obj9;
            new HashMap(9);
            fontSizeKeywords = map;
            new SVG.Length(0.694f, SVG.Unit.f357pt);
            SVG.Length put = fontSizeKeywords.put("xx-small", obj);
            new SVG.Length(0.833f, SVG.Unit.f357pt);
            SVG.Length put2 = fontSizeKeywords.put("x-small", obj2);
            new SVG.Length(10.0f, SVG.Unit.f357pt);
            SVG.Length put3 = fontSizeKeywords.put("small", obj3);
            new SVG.Length(12.0f, SVG.Unit.f357pt);
            SVG.Length put4 = fontSizeKeywords.put("medium", obj4);
            new SVG.Length(14.4f, SVG.Unit.f357pt);
            SVG.Length put5 = fontSizeKeywords.put("large", obj5);
            new SVG.Length(17.3f, SVG.Unit.f357pt);
            SVG.Length put6 = fontSizeKeywords.put("x-large", obj6);
            new SVG.Length(20.7f, SVG.Unit.f357pt);
            SVG.Length put7 = fontSizeKeywords.put("xx-large", obj7);
            new SVG.Length(83.33f, SVG.Unit.percent);
            SVG.Length put8 = fontSizeKeywords.put("smaller", obj8);
            new SVG.Length(120.0f, SVG.Unit.percent);
            SVG.Length put9 = fontSizeKeywords.put("larger", obj9);
        }

        public static SVG.Length get(String fontSize) {
            return fontSizeKeywords.get(fontSize);
        }
    }

    private static class FontWeightKeywords {
        private static final Map<String, Integer> fontWeightKeywords;

        private FontWeightKeywords() {
        }

        static {
            Map<String, Integer> map;
            new HashMap(13);
            fontWeightKeywords = map;
            Integer put = fontWeightKeywords.put("normal", Integer.valueOf(SVG.Style.FONT_WEIGHT_NORMAL));
            Integer put2 = fontWeightKeywords.put("bold", Integer.valueOf(SVG.Style.FONT_WEIGHT_BOLD));
            Integer put3 = fontWeightKeywords.put("bolder", 1);
            Integer put4 = fontWeightKeywords.put("lighter", -1);
            Integer put5 = fontWeightKeywords.put("100", 100);
            Integer put6 = fontWeightKeywords.put("200", 200);
            Integer put7 = fontWeightKeywords.put("300", 300);
            Integer put8 = fontWeightKeywords.put("400", Integer.valueOf(SVG.Style.FONT_WEIGHT_NORMAL));
            Integer put9 = fontWeightKeywords.put("500", 500);
            Integer put10 = fontWeightKeywords.put("600", 600);
            Integer put11 = fontWeightKeywords.put("700", Integer.valueOf(SVG.Style.FONT_WEIGHT_BOLD));
            Integer put12 = fontWeightKeywords.put("800", 800);
            Integer put13 = fontWeightKeywords.put("900", 900);
        }

        public static Integer get(String fontWeight) {
            return fontWeightKeywords.get(fontWeight);
        }
    }

    private static class AspectRatioKeywords {
        private static final Map<String, PreserveAspectRatio.Alignment> aspectRatioKeywords;

        private AspectRatioKeywords() {
        }

        static {
            Map<String, PreserveAspectRatio.Alignment> map;
            new HashMap(10);
            aspectRatioKeywords = map;
            PreserveAspectRatio.Alignment put = aspectRatioKeywords.put("none", PreserveAspectRatio.Alignment.None);
            PreserveAspectRatio.Alignment put2 = aspectRatioKeywords.put("xMinYMin", PreserveAspectRatio.Alignment.XMinYMin);
            PreserveAspectRatio.Alignment put3 = aspectRatioKeywords.put("xMidYMin", PreserveAspectRatio.Alignment.XMidYMin);
            PreserveAspectRatio.Alignment put4 = aspectRatioKeywords.put("xMaxYMin", PreserveAspectRatio.Alignment.XMaxYMin);
            PreserveAspectRatio.Alignment put5 = aspectRatioKeywords.put("xMinYMid", PreserveAspectRatio.Alignment.XMinYMid);
            PreserveAspectRatio.Alignment put6 = aspectRatioKeywords.put("xMidYMid", PreserveAspectRatio.Alignment.XMidYMid);
            PreserveAspectRatio.Alignment put7 = aspectRatioKeywords.put("xMaxYMid", PreserveAspectRatio.Alignment.XMaxYMid);
            PreserveAspectRatio.Alignment put8 = aspectRatioKeywords.put("xMinYMax", PreserveAspectRatio.Alignment.XMinYMax);
            PreserveAspectRatio.Alignment put9 = aspectRatioKeywords.put("xMidYMax", PreserveAspectRatio.Alignment.XMidYMax);
            PreserveAspectRatio.Alignment put10 = aspectRatioKeywords.put("xMaxYMax", PreserveAspectRatio.Alignment.XMaxYMax);
        }

        public static PreserveAspectRatio.Alignment get(String aspectRatio) {
            return aspectRatioKeywords.get(aspectRatio);
        }
    }

    /* access modifiers changed from: protected */
    public void setSupportedFormats(String[] strArr) {
        Set<String> set;
        String[] mimeTypes = strArr;
        new HashSet(mimeTypes.length);
        this.supportedFormats = set;
        boolean addAll = Collections.addAll(this.supportedFormats, mimeTypes);
    }

    /* access modifiers changed from: protected */
    public SVG parse(InputStream inputStream) throws SVGParseException {
        Throwable th;
        StringBuilder sb;
        Throwable th2;
        Throwable th3;
        InputSource inputSource;
        InputStream inputStream2;
        InputStream inputStream3;
        InputStream is = inputStream;
        if (!is.markSupported()) {
            new BufferedInputStream(is);
            is = inputStream3;
        }
        try {
            is.mark(3);
            int firstTwoBytes = is.read() + (is.read() << 8);
            is.reset();
            if (firstTwoBytes == 35615) {
                InputStream inputStream4 = inputStream2;
                new GZIPInputStream(is);
                is = inputStream4;
            }
        } catch (IOException e) {
            IOException iOException = e;
        }
        try {
            XMLReader xr = SAXParserFactory.newInstance().newSAXParser().getXMLReader();
            xr.setContentHandler(this);
            xr.setProperty("http://xml.org/sax/properties/lexical-handler", this);
            new InputSource(is);
            xr.parse(inputSource);
            try {
                is.close();
            } catch (IOException e2) {
                IOException iOException2 = e2;
                int e3 = Log.e(TAG, "Exception thrown closing input stream");
            }
            return this.svgDocument;
        } catch (IOException e4) {
            IOException e5 = e4;
            Throwable th4 = th3;
            new SVGParseException("File error", e5);
            throw th4;
        } catch (ParserConfigurationException e6) {
            ParserConfigurationException e7 = e6;
            Throwable th5 = th2;
            new SVGParseException("XML Parser problem", e7);
            throw th5;
        } catch (SAXException e8) {
            SAXException e9 = e8;
            Throwable th6 = th;
            new StringBuilder();
            new SVGParseException(sb.append("SVG parse error: ").append(e9.getMessage()).toString(), e9);
            throw th6;
        } catch (Throwable th7) {
            Throwable th8 = th7;
            try {
                is.close();
            } catch (IOException e10) {
                IOException iOException3 = e10;
                int e11 = Log.e(TAG, "Exception thrown closing input stream");
            }
            throw th8;
        }
    }

    public void startDocument() throws SAXException {
        SVG svg;
        SVG svg2 = svg;
        new SVG();
        this.svgDocument = svg2;
    }

    public void startElement(String str, String str2, String str3, Attributes attributes) throws SAXException {
        String uri = str;
        String localName = str2;
        String qName = str3;
        Attributes attributes2 = attributes;
        if (this.ignoring) {
            this.ignoreDepth++;
        } else if (SVG_NAMESPACE.equals(uri) || "".equals(uri)) {
            if (localName.equals("")) {
                localName = qName;
            }
            SVGElem elem = SVGElem.fromString(localName);
            switch (elem) {
                case svg:
                    svg(attributes2);
                    return;
                case f390g:
                case f389a:
                    m55g(attributes2);
                    return;
                case defs:
                    defs(attributes2);
                    return;
                case use:
                    use(attributes2);
                    return;
                case path:
                    path(attributes2);
                    return;
                case rect:
                    rect(attributes2);
                    return;
                case circle:
                    circle(attributes2);
                    return;
                case ellipse:
                    ellipse(attributes2);
                    return;
                case line:
                    line(attributes2);
                    return;
                case polyline:
                    polyline(attributes2);
                    return;
                case polygon:
                    polygon(attributes2);
                    return;
                case text:
                    text(attributes2);
                    return;
                case tspan:
                    tspan(attributes2);
                    return;
                case tref:
                    tref(attributes2);
                    return;
                case SWITCH:
                    zwitch(attributes2);
                    return;
                case symbol:
                    symbol(attributes2);
                    return;
                case marker:
                    marker(attributes2);
                    return;
                case linearGradient:
                    linearGradient(attributes2);
                    return;
                case radialGradient:
                    radialGradient(attributes2);
                    return;
                case stop:
                    stop(attributes2);
                    return;
                case title:
                case desc:
                    this.inMetadataElement = true;
                    this.metadataTag = elem;
                    return;
                case clipPath:
                    clipPath(attributes2);
                    return;
                case textPath:
                    textPath(attributes2);
                    return;
                case pattern:
                    pattern(attributes2);
                    return;
                case image:
                    image(attributes2);
                    return;
                case view:
                    view(attributes2);
                    return;
                case mask:
                    mask(attributes2);
                    return;
                case style:
                    style(attributes2);
                    return;
                case solidColor:
                    solidColor(attributes2);
                    return;
                default:
                    this.ignoring = true;
                    this.ignoreDepth = 1;
                    return;
            }
        }
    }

    public void characters(char[] cArr, int i, int i2) throws SAXException {
        SVG.SvgObject svgObject;
        String str;
        StringBuilder sb;
        String str2;
        StringBuilder sb2;
        StringBuilder sb3;
        char[] ch = cArr;
        int start = i;
        int length = i2;
        if (!this.ignoring) {
            if (this.inMetadataElement) {
                if (this.metadataElementContents == null) {
                    new StringBuilder(length);
                    this.metadataElementContents = sb3;
                }
                StringBuilder append = this.metadataElementContents.append(ch, start, length);
            } else if (this.inStyleElement) {
                if (this.styleElementContents == null) {
                    new StringBuilder(length);
                    this.styleElementContents = sb2;
                }
                StringBuilder append2 = this.styleElementContents.append(ch, start, length);
            } else if (this.currentElement instanceof SVG.TextContainer) {
                SVG.SvgConditionalContainer parent = (SVG.SvgConditionalContainer) this.currentElement;
                int numOlderSiblings = parent.children.size();
                SVG.SvgObject previousSibling = numOlderSiblings == 0 ? null : parent.children.get(numOlderSiblings - 1);
                if (previousSibling instanceof SVG.TextSequence) {
                    new StringBuilder();
                    SVG.TextSequence textSequence = (SVG.TextSequence) previousSibling;
                    new String(ch, start, length);
                    textSequence.text = sb.append(textSequence.text).append(str2).toString();
                    return;
                }
                new String(ch, start, length);
                new SVG.TextSequence(str);
                ((SVG.SvgConditionalContainer) this.currentElement).addChild(svgObject);
            }
        }
    }

    public void comment(char[] cArr, int i, int i2) throws SAXException {
        StringBuilder sb;
        char[] ch = cArr;
        int start = i;
        int length = i2;
        if (!this.ignoring && this.inStyleElement) {
            if (this.styleElementContents == null) {
                new StringBuilder(length);
                this.styleElementContents = sb;
            }
            StringBuilder append = this.styleElementContents.append(ch, start, length);
        }
    }

    public void endElement(String str, String str2, String str3) throws SAXException {
        String uri = str;
        String localName = str2;
        String str4 = str3;
        if (this.ignoring) {
            int i = this.ignoreDepth - 1;
            int i2 = i;
            this.ignoreDepth = i;
            if (i2 == 0) {
                this.ignoring = false;
                return;
            }
        }
        if (SVG_NAMESPACE.equals(uri) || "".equals(uri)) {
            switch (SVGElem.fromString(localName)) {
                case svg:
                case f390g:
                case defs:
                case use:
                case text:
                case tspan:
                case SWITCH:
                case symbol:
                case marker:
                case linearGradient:
                case radialGradient:
                case stop:
                case clipPath:
                case textPath:
                case pattern:
                case image:
                case view:
                case mask:
                case solidColor:
                    this.currentElement = ((SVG.SvgObject) this.currentElement).parent;
                    return;
                case title:
                case desc:
                    this.inMetadataElement = false;
                    if (this.metadataElementContents != null) {
                        if (this.metadataTag == SVGElem.title) {
                            this.svgDocument.setTitle(this.metadataElementContents.toString());
                        } else if (this.metadataTag == SVGElem.desc) {
                            this.svgDocument.setDesc(this.metadataElementContents.toString());
                        }
                        this.metadataElementContents.setLength(0);
                        return;
                    }
                    return;
                case style:
                    if (this.styleElementContents != null) {
                        this.inStyleElement = false;
                        parseCSSStyleSheet(this.styleElementContents.toString());
                        this.styleElementContents.setLength(0);
                        return;
                    }
                    return;
                default:
                    return;
            }
        }
    }

    public void endDocument() throws SAXException {
    }

    private void dumpNode(SVG.SvgObject svgObject, String str) {
        StringBuilder sb;
        StringBuilder sb2;
        SVG.SvgObject elem = svgObject;
        String indent = str;
        new StringBuilder();
        int d = Log.d(TAG, sb.append(indent).append(elem).toString());
        if (elem instanceof SVG.SvgConditionalContainer) {
            new StringBuilder();
            String indent2 = sb2.append(indent).append("  ").toString();
            for (SVG.SvgObject child : ((SVG.SvgConditionalContainer) elem).children) {
                dumpNode(child, indent2);
            }
        }
    }

    private void debug(String format, Object... args) {
    }

    private void svg(Attributes attributes) throws SAXException {
        SVG.Svg svg;
        Attributes attributes2 = attributes;
        debug("<svg>", new Object[0]);
        new SVG.Svg();
        SVG.Svg obj = svg;
        obj.document = this.svgDocument;
        obj.parent = this.currentElement;
        parseAttributesCore(obj, attributes2);
        parseAttributesStyle(obj, attributes2);
        parseAttributesConditional(obj, attributes2);
        parseAttributesViewBox(obj, attributes2);
        parseAttributesSVG(obj, attributes2);
        if (this.currentElement == null) {
            this.svgDocument.setRootElement(obj);
        } else {
            this.currentElement.addChild(obj);
        }
        this.currentElement = obj;
    }

    private void parseAttributesSVG(SVG.Svg svg, Attributes attributes) throws SAXException {
        Throwable th;
        Throwable th2;
        SVG.Svg obj = svg;
        Attributes attributes2 = attributes;
        for (int i = 0; i < attributes2.getLength(); i++) {
            String val = attributes2.getValue(i).trim();
            switch (SVGAttr.fromString(attributes2.getLocalName(i))) {
                case f383x:
                    obj.f335x = parseLength(val);
                    break;
                case f386y:
                    obj.f336y = parseLength(val);
                    break;
                case width:
                    obj.width = parseLength(val);
                    if (!obj.width.isNegative()) {
                        break;
                    } else {
                        Throwable th3 = th2;
                        new SAXException("Invalid <svg> element. width cannot be negative");
                        throw th3;
                    }
                case height:
                    obj.height = parseLength(val);
                    if (!obj.height.isNegative()) {
                        break;
                    } else {
                        Throwable th4 = th;
                        new SAXException("Invalid <svg> element. height cannot be negative");
                        throw th4;
                    }
                case version:
                    obj.version = val;
                    break;
            }
        }
    }

    /* renamed from: g */
    private void m55g(Attributes attributes) throws SAXException {
        SVG.Group group;
        Throwable th;
        Attributes attributes2 = attributes;
        debug("<g>", new Object[0]);
        if (this.currentElement == null) {
            Throwable th2 = th;
            new SAXException("Invalid document. Root element must be <svg>");
            throw th2;
        }
        new SVG.Group();
        SVG.Group obj = group;
        obj.document = this.svgDocument;
        obj.parent = this.currentElement;
        parseAttributesCore(obj, attributes2);
        parseAttributesStyle(obj, attributes2);
        parseAttributesTransform(obj, attributes2);
        parseAttributesConditional(obj, attributes2);
        this.currentElement.addChild(obj);
        this.currentElement = obj;
    }

    private void defs(Attributes attributes) throws SAXException {
        SVG.Defs defs;
        Throwable th;
        Attributes attributes2 = attributes;
        debug("<defs>", new Object[0]);
        if (this.currentElement == null) {
            Throwable th2 = th;
            new SAXException("Invalid document. Root element must be <svg>");
            throw th2;
        }
        new SVG.Defs();
        SVG.Defs obj = defs;
        obj.document = this.svgDocument;
        obj.parent = this.currentElement;
        parseAttributesCore(obj, attributes2);
        parseAttributesStyle(obj, attributes2);
        parseAttributesTransform(obj, attributes2);
        this.currentElement.addChild(obj);
        this.currentElement = obj;
    }

    private void use(Attributes attributes) throws SAXException {
        SVG.Use use;
        Throwable th;
        Attributes attributes2 = attributes;
        debug("<use>", new Object[0]);
        if (this.currentElement == null) {
            Throwable th2 = th;
            new SAXException("Invalid document. Root element must be <svg>");
            throw th2;
        }
        new SVG.Use();
        SVG.Use obj = use;
        obj.document = this.svgDocument;
        obj.parent = this.currentElement;
        parseAttributesCore(obj, attributes2);
        parseAttributesStyle(obj, attributes2);
        parseAttributesTransform(obj, attributes2);
        parseAttributesConditional(obj, attributes2);
        parseAttributesUse(obj, attributes2);
        this.currentElement.addChild(obj);
        this.currentElement = obj;
    }

    private void parseAttributesUse(SVG.Use use, Attributes attributes) throws SAXException {
        Throwable th;
        Throwable th2;
        SVG.Use obj = use;
        Attributes attributes2 = attributes;
        for (int i = 0; i < attributes2.getLength(); i++) {
            String val = attributes2.getValue(i).trim();
            switch (SVGAttr.fromString(attributes2.getLocalName(i))) {
                case f383x:
                    obj.f359x = parseLength(val);
                    break;
                case f386y:
                    obj.f360y = parseLength(val);
                    break;
                case width:
                    obj.width = parseLength(val);
                    if (!obj.width.isNegative()) {
                        break;
                    } else {
                        Throwable th3 = th2;
                        new SAXException("Invalid <use> element. width cannot be negative");
                        throw th3;
                    }
                case height:
                    obj.height = parseLength(val);
                    if (!obj.height.isNegative()) {
                        break;
                    } else {
                        Throwable th4 = th;
                        new SAXException("Invalid <use> element. height cannot be negative");
                        throw th4;
                    }
                case href:
                    if (XLINK_NAMESPACE.equals(attributes2.getURI(i))) {
                        obj.href = val;
                        break;
                    } else {
                        break;
                    }
            }
        }
    }

    private void image(Attributes attributes) throws SAXException {
        SVG.Image image;
        Throwable th;
        Attributes attributes2 = attributes;
        debug("<image>", new Object[0]);
        if (this.currentElement == null) {
            Throwable th2 = th;
            new SAXException("Invalid document. Root element must be <svg>");
            throw th2;
        }
        new SVG.Image();
        SVG.Image obj = image;
        obj.document = this.svgDocument;
        obj.parent = this.currentElement;
        parseAttributesCore(obj, attributes2);
        parseAttributesStyle(obj, attributes2);
        parseAttributesTransform(obj, attributes2);
        parseAttributesConditional(obj, attributes2);
        parseAttributesImage(obj, attributes2);
        this.currentElement.addChild(obj);
        this.currentElement = obj;
    }

    private void parseAttributesImage(SVG.Image image, Attributes attributes) throws SAXException {
        Throwable th;
        Throwable th2;
        SVG.Image obj = image;
        Attributes attributes2 = attributes;
        for (int i = 0; i < attributes2.getLength(); i++) {
            String val = attributes2.getValue(i).trim();
            switch (SVGAttr.fromString(attributes2.getLocalName(i))) {
                case f383x:
                    obj.f320x = parseLength(val);
                    break;
                case f386y:
                    obj.f321y = parseLength(val);
                    break;
                case width:
                    obj.width = parseLength(val);
                    if (!obj.width.isNegative()) {
                        break;
                    } else {
                        Throwable th3 = th2;
                        new SAXException("Invalid <use> element. width cannot be negative");
                        throw th3;
                    }
                case height:
                    obj.height = parseLength(val);
                    if (!obj.height.isNegative()) {
                        break;
                    } else {
                        Throwable th4 = th;
                        new SAXException("Invalid <use> element. height cannot be negative");
                        throw th4;
                    }
                case href:
                    if (XLINK_NAMESPACE.equals(attributes2.getURI(i))) {
                        obj.href = val;
                        break;
                    } else {
                        break;
                    }
                case preserveAspectRatio:
                    parsePreserveAspectRatio(obj, val);
                    break;
            }
        }
    }

    private void path(Attributes attributes) throws SAXException {
        SVG.Path path;
        Throwable th;
        Attributes attributes2 = attributes;
        debug("<path>", new Object[0]);
        if (this.currentElement == null) {
            Throwable th2 = th;
            new SAXException("Invalid document. Root element must be <svg>");
            throw th2;
        }
        new SVG.Path();
        SVG.Path obj = path;
        obj.document = this.svgDocument;
        obj.parent = this.currentElement;
        parseAttributesCore(obj, attributes2);
        parseAttributesStyle(obj, attributes2);
        parseAttributesTransform(obj, attributes2);
        parseAttributesConditional(obj, attributes2);
        parseAttributesPath(obj, attributes2);
        this.currentElement.addChild(obj);
    }

    private void parseAttributesPath(SVG.Path path, Attributes attributes) throws SAXException {
        Throwable th;
        SVG.Path obj = path;
        Attributes attributes2 = attributes;
        for (int i = 0; i < attributes2.getLength(); i++) {
            String val = attributes2.getValue(i).trim();
            switch (SVGAttr.fromString(attributes2.getLocalName(i))) {
                case f374d:
                    obj.f328d = parsePath(val);
                    break;
                case pathLength:
                    obj.pathLength = Float.valueOf(parseFloat(val));
                    if (obj.pathLength.floatValue() >= 0.0f) {
                        break;
                    } else {
                        Throwable th2 = th;
                        new SAXException("Invalid <path> element. pathLength cannot be negative");
                        throw th2;
                    }
            }
        }
    }

    private void rect(Attributes attributes) throws SAXException {
        SVG.Rect rect;
        Throwable th;
        Attributes attributes2 = attributes;
        debug("<rect>", new Object[0]);
        if (this.currentElement == null) {
            Throwable th2 = th;
            new SAXException("Invalid document. Root element must be <svg>");
            throw th2;
        }
        new SVG.Rect();
        SVG.Rect obj = rect;
        obj.document = this.svgDocument;
        obj.parent = this.currentElement;
        parseAttributesCore(obj, attributes2);
        parseAttributesStyle(obj, attributes2);
        parseAttributesTransform(obj, attributes2);
        parseAttributesConditional(obj, attributes2);
        parseAttributesRect(obj, attributes2);
        this.currentElement.addChild(obj);
    }

    private void parseAttributesRect(SVG.Rect rect, Attributes attributes) throws SAXException {
        Throwable th;
        Throwable th2;
        Throwable th3;
        Throwable th4;
        SVG.Rect obj = rect;
        Attributes attributes2 = attributes;
        for (int i = 0; i < attributes2.getLength(); i++) {
            String val = attributes2.getValue(i).trim();
            switch (SVGAttr.fromString(attributes2.getLocalName(i))) {
                case f383x:
                    obj.f333x = parseLength(val);
                    break;
                case f386y:
                    obj.f334y = parseLength(val);
                    break;
                case width:
                    obj.width = parseLength(val);
                    if (!obj.width.isNegative()) {
                        break;
                    } else {
                        Throwable th5 = th4;
                        new SAXException("Invalid <rect> element. width cannot be negative");
                        throw th5;
                    }
                case height:
                    obj.height = parseLength(val);
                    if (!obj.height.isNegative()) {
                        break;
                    } else {
                        Throwable th6 = th3;
                        new SAXException("Invalid <rect> element. height cannot be negative");
                        throw th6;
                    }
                case f381rx:
                    obj.f331rx = parseLength(val);
                    if (!obj.f331rx.isNegative()) {
                        break;
                    } else {
                        Throwable th7 = th2;
                        new SAXException("Invalid <rect> element. rx cannot be negative");
                        throw th7;
                    }
                case f382ry:
                    obj.f332ry = parseLength(val);
                    if (!obj.f332ry.isNegative()) {
                        break;
                    } else {
                        Throwable th8 = th;
                        new SAXException("Invalid <rect> element. ry cannot be negative");
                        throw th8;
                    }
            }
        }
    }

    private void circle(Attributes attributes) throws SAXException {
        SVG.Circle circle;
        Throwable th;
        Attributes attributes2 = attributes;
        debug("<circle>", new Object[0]);
        if (this.currentElement == null) {
            Throwable th2 = th;
            new SAXException("Invalid document. Root element must be <svg>");
            throw th2;
        }
        new SVG.Circle();
        SVG.Circle obj = circle;
        obj.document = this.svgDocument;
        obj.parent = this.currentElement;
        parseAttributesCore(obj, attributes2);
        parseAttributesStyle(obj, attributes2);
        parseAttributesTransform(obj, attributes2);
        parseAttributesConditional(obj, attributes2);
        parseAttributesCircle(obj, attributes2);
        this.currentElement.addChild(obj);
    }

    private void parseAttributesCircle(SVG.Circle circle, Attributes attributes) throws SAXException {
        Throwable th;
        SVG.Circle obj = circle;
        Attributes attributes2 = attributes;
        for (int i = 0; i < attributes2.getLength(); i++) {
            String val = attributes2.getValue(i).trim();
            switch (SVGAttr.fromString(attributes2.getLocalName(i))) {
                case f372cx:
                    obj.f313cx = parseLength(val);
                    break;
                case f373cy:
                    obj.f314cy = parseLength(val);
                    break;
                case f380r:
                    obj.f315r = parseLength(val);
                    if (!obj.f315r.isNegative()) {
                        break;
                    } else {
                        Throwable th2 = th;
                        new SAXException("Invalid <circle> element. r cannot be negative");
                        throw th2;
                    }
            }
        }
    }

    private void ellipse(Attributes attributes) throws SAXException {
        SVG.Ellipse ellipse;
        Throwable th;
        Attributes attributes2 = attributes;
        debug("<ellipse>", new Object[0]);
        if (this.currentElement == null) {
            Throwable th2 = th;
            new SAXException("Invalid document. Root element must be <svg>");
            throw th2;
        }
        new SVG.Ellipse();
        SVG.Ellipse obj = ellipse;
        obj.document = this.svgDocument;
        obj.parent = this.currentElement;
        parseAttributesCore(obj, attributes2);
        parseAttributesStyle(obj, attributes2);
        parseAttributesTransform(obj, attributes2);
        parseAttributesConditional(obj, attributes2);
        parseAttributesEllipse(obj, attributes2);
        this.currentElement.addChild(obj);
    }

    private void parseAttributesEllipse(SVG.Ellipse ellipse, Attributes attributes) throws SAXException {
        Throwable th;
        Throwable th2;
        SVG.Ellipse obj = ellipse;
        Attributes attributes2 = attributes;
        for (int i = 0; i < attributes2.getLength(); i++) {
            String val = attributes2.getValue(i).trim();
            switch (SVGAttr.fromString(attributes2.getLocalName(i))) {
                case f381rx:
                    obj.f318rx = parseLength(val);
                    if (!obj.f318rx.isNegative()) {
                        break;
                    } else {
                        Throwable th3 = th2;
                        new SAXException("Invalid <ellipse> element. rx cannot be negative");
                        throw th3;
                    }
                case f382ry:
                    obj.f319ry = parseLength(val);
                    if (!obj.f319ry.isNegative()) {
                        break;
                    } else {
                        Throwable th4 = th;
                        new SAXException("Invalid <ellipse> element. ry cannot be negative");
                        throw th4;
                    }
                case f372cx:
                    obj.f316cx = parseLength(val);
                    break;
                case f373cy:
                    obj.f317cy = parseLength(val);
                    break;
            }
        }
    }

    private void line(Attributes attributes) throws SAXException {
        SVG.Line line;
        Throwable th;
        Attributes attributes2 = attributes;
        debug("<line>", new Object[0]);
        if (this.currentElement == null) {
            Throwable th2 = th;
            new SAXException("Invalid document. Root element must be <svg>");
            throw th2;
        }
        new SVG.Line();
        SVG.Line obj = line;
        obj.document = this.svgDocument;
        obj.parent = this.currentElement;
        parseAttributesCore(obj, attributes2);
        parseAttributesStyle(obj, attributes2);
        parseAttributesTransform(obj, attributes2);
        parseAttributesConditional(obj, attributes2);
        parseAttributesLine(obj, attributes2);
        this.currentElement.addChild(obj);
    }

    private void parseAttributesLine(SVG.Line line, Attributes attributes) throws SAXException {
        SVG.Line obj = line;
        Attributes attributes2 = attributes;
        for (int i = 0; i < attributes2.getLength(); i++) {
            String val = attributes2.getValue(i).trim();
            switch (SVGAttr.fromString(attributes2.getLocalName(i))) {
                case f384x1:
                    obj.f322x1 = parseLength(val);
                    break;
                case f387y1:
                    obj.f324y1 = parseLength(val);
                    break;
                case f385x2:
                    obj.f323x2 = parseLength(val);
                    break;
                case f388y2:
                    obj.f325y2 = parseLength(val);
                    break;
            }
        }
    }

    private void polyline(Attributes attributes) throws SAXException {
        SVG.PolyLine polyLine;
        Throwable th;
        Attributes attributes2 = attributes;
        debug("<polyline>", new Object[0]);
        if (this.currentElement == null) {
            Throwable th2 = th;
            new SAXException("Invalid document. Root element must be <svg>");
            throw th2;
        }
        new SVG.PolyLine();
        SVG.PolyLine obj = polyLine;
        obj.document = this.svgDocument;
        obj.parent = this.currentElement;
        parseAttributesCore(obj, attributes2);
        parseAttributesStyle(obj, attributes2);
        parseAttributesTransform(obj, attributes2);
        parseAttributesConditional(obj, attributes2);
        parseAttributesPolyLine(obj, attributes2, "polyline");
        this.currentElement.addChild(obj);
    }

    private void parseAttributesPolyLine(SVG.PolyLine polyLine, Attributes attributes, String str) throws SAXException {
        TextScanner textScanner;
        List<Float> list;
        Throwable th;
        StringBuilder sb;
        Throwable th2;
        StringBuilder sb2;
        SVG.PolyLine obj = polyLine;
        Attributes attributes2 = attributes;
        String tag = str;
        for (int i = 0; i < attributes2.getLength(); i++) {
            if (SVGAttr.fromString(attributes2.getLocalName(i)) == SVGAttr.points) {
                new TextScanner(attributes2.getValue(i));
                TextScanner scan = textScanner;
                new ArrayList<>();
                List<Float> points = list;
                scan.skipWhitespace();
                while (!scan.empty()) {
                    float x = scan.nextFloat();
                    if (Float.isNaN(x)) {
                        Throwable th3 = th;
                        new StringBuilder();
                        new SAXException(sb.append("Invalid <").append(tag).append("> points attribute. Non-coordinate content found in list.").toString());
                        throw th3;
                    }
                    boolean skipCommaWhitespace = scan.skipCommaWhitespace();
                    float y = scan.nextFloat();
                    if (Float.isNaN(y)) {
                        Throwable th4 = th2;
                        new StringBuilder();
                        new SAXException(sb2.append("Invalid <").append(tag).append("> points attribute. There should be an even number of coordinates.").toString());
                        throw th4;
                    }
                    boolean skipCommaWhitespace2 = scan.skipCommaWhitespace();
                    boolean add = points.add(Float.valueOf(x));
                    boolean add2 = points.add(Float.valueOf(y));
                }
                obj.points = new float[points.size()];
                int j = 0;
                for (Float floatValue : points) {
                    int i2 = j;
                    j++;
                    obj.points[i2] = floatValue.floatValue();
                }
            }
        }
    }

    private void polygon(Attributes attributes) throws SAXException {
        SVG.Polygon polygon;
        Throwable th;
        Attributes attributes2 = attributes;
        debug("<polygon>", new Object[0]);
        if (this.currentElement == null) {
            Throwable th2 = th;
            new SAXException("Invalid document. Root element must be <svg>");
            throw th2;
        }
        new SVG.Polygon();
        SVG.Polygon obj = polygon;
        obj.document = this.svgDocument;
        obj.parent = this.currentElement;
        parseAttributesCore(obj, attributes2);
        parseAttributesStyle(obj, attributes2);
        parseAttributesTransform(obj, attributes2);
        parseAttributesConditional(obj, attributes2);
        parseAttributesPolyLine(obj, attributes2, "polygon");
        this.currentElement.addChild(obj);
    }

    private void text(Attributes attributes) throws SAXException {
        SVG.Text text;
        Throwable th;
        Attributes attributes2 = attributes;
        debug("<text>", new Object[0]);
        if (this.currentElement == null) {
            Throwable th2 = th;
            new SAXException("Invalid document. Root element must be <svg>");
            throw th2;
        }
        new SVG.Text();
        SVG.Text obj = text;
        obj.document = this.svgDocument;
        obj.parent = this.currentElement;
        parseAttributesCore(obj, attributes2);
        parseAttributesStyle(obj, attributes2);
        parseAttributesTransform(obj, attributes2);
        parseAttributesConditional(obj, attributes2);
        parseAttributesTextPosition(obj, attributes2);
        this.currentElement.addChild(obj);
        this.currentElement = obj;
    }

    private void parseAttributesTextPosition(SVG.TextPositionedContainer textPositionedContainer, Attributes attributes) throws SAXException {
        SVG.TextPositionedContainer obj = textPositionedContainer;
        Attributes attributes2 = attributes;
        for (int i = 0; i < attributes2.getLength(); i++) {
            String val = attributes2.getValue(i).trim();
            switch (SVGAttr.fromString(attributes2.getLocalName(i))) {
                case f383x:
                    obj.f349x = parseLengthList(val);
                    break;
                case f386y:
                    obj.f350y = parseLengthList(val);
                    break;
                case f375dx:
                    obj.f347dx = parseLengthList(val);
                    break;
                case f376dy:
                    obj.f348dy = parseLengthList(val);
                    break;
            }
        }
    }

    private void tspan(Attributes attributes) throws SAXException {
        SVG.TSpan tSpan;
        Throwable th;
        Throwable th2;
        Attributes attributes2 = attributes;
        debug("<tspan>", new Object[0]);
        if (this.currentElement == null) {
            Throwable th3 = th2;
            new SAXException("Invalid document. Root element must be <svg>");
            throw th3;
        } else if (!(this.currentElement instanceof SVG.TextContainer)) {
            Throwable th4 = th;
            new SAXException("Invalid document. <tspan> elements are only valid inside <text> or other <tspan> elements.");
            throw th4;
        } else {
            new SVG.TSpan();
            SVG.TSpan obj = tSpan;
            obj.document = this.svgDocument;
            obj.parent = this.currentElement;
            parseAttributesCore(obj, attributes2);
            parseAttributesStyle(obj, attributes2);
            parseAttributesConditional(obj, attributes2);
            parseAttributesTextPosition(obj, attributes2);
            this.currentElement.addChild(obj);
            this.currentElement = obj;
            if (obj.parent instanceof SVG.TextRoot) {
                obj.setTextRoot((SVG.TextRoot) obj.parent);
            } else {
                obj.setTextRoot(((SVG.TextChild) obj.parent).getTextRoot());
            }
        }
    }

    private void tref(Attributes attributes) throws SAXException {
        SVG.TRef tRef;
        Throwable th;
        Throwable th2;
        Attributes attributes2 = attributes;
        debug("<tref>", new Object[0]);
        if (this.currentElement == null) {
            Throwable th3 = th2;
            new SAXException("Invalid document. Root element must be <svg>");
            throw th3;
        } else if (!(this.currentElement instanceof SVG.TextContainer)) {
            Throwable th4 = th;
            new SAXException("Invalid document. <tref> elements are only valid inside <text> or <tspan> elements.");
            throw th4;
        } else {
            new SVG.TRef();
            SVG.TRef obj = tRef;
            obj.document = this.svgDocument;
            obj.parent = this.currentElement;
            parseAttributesCore(obj, attributes2);
            parseAttributesStyle(obj, attributes2);
            parseAttributesConditional(obj, attributes2);
            parseAttributesTRef(obj, attributes2);
            this.currentElement.addChild(obj);
            if (obj.parent instanceof SVG.TextRoot) {
                obj.setTextRoot((SVG.TextRoot) obj.parent);
            } else {
                obj.setTextRoot(((SVG.TextChild) obj.parent).getTextRoot());
            }
        }
    }

    private void parseAttributesTRef(SVG.TRef tRef, Attributes attributes) throws SAXException {
        SVG.TRef obj = tRef;
        Attributes attributes2 = attributes;
        for (int i = 0; i < attributes2.getLength(); i++) {
            String val = attributes2.getValue(i).trim();
            switch (SVGAttr.fromString(attributes2.getLocalName(i))) {
                case href:
                    if (XLINK_NAMESPACE.equals(attributes2.getURI(i))) {
                        obj.href = val;
                        break;
                    } else {
                        break;
                    }
            }
        }
    }

    private void zwitch(Attributes attributes) throws SAXException {
        SVG.Switch switchR;
        Throwable th;
        Attributes attributes2 = attributes;
        debug("<switch>", new Object[0]);
        if (this.currentElement == null) {
            Throwable th2 = th;
            new SAXException("Invalid document. Root element must be <svg>");
            throw th2;
        }
        new SVG.Switch();
        SVG.Switch obj = switchR;
        obj.document = this.svgDocument;
        obj.parent = this.currentElement;
        parseAttributesCore(obj, attributes2);
        parseAttributesStyle(obj, attributes2);
        parseAttributesTransform(obj, attributes2);
        parseAttributesConditional(obj, attributes2);
        this.currentElement.addChild(obj);
        this.currentElement = obj;
    }

    private void parseAttributesConditional(SVG.SvgConditional svgConditional, Attributes attributes) throws SAXException {
        Set set;
        Set set2;
        Set set3;
        SVG.SvgConditional obj = svgConditional;
        Attributes attributes2 = attributes;
        for (int i = 0; i < attributes2.getLength(); i++) {
            String val = attributes2.getValue(i).trim();
            switch (SVGAttr.fromString(attributes2.getLocalName(i))) {
                case requiredFeatures:
                    obj.setRequiredFeatures(parseRequiredFeatures(val));
                    break;
                case requiredExtensions:
                    obj.setRequiredExtensions(val);
                    break;
                case systemLanguage:
                    obj.setSystemLanguage(parseSystemLanguage(val));
                    break;
                case requiredFormats:
                    obj.setRequiredFormats(parseRequiredFormats(val));
                    break;
                case requiredFonts:
                    List<String> fonts = parseFontFamily(val);
                    if (fonts != null) {
                        set2 = set3;
                        new HashSet(fonts);
                    } else {
                        set2 = set;
                        new HashSet(0);
                    }
                    obj.setRequiredFonts(set2);
                    break;
            }
        }
    }

    private void symbol(Attributes attributes) throws SAXException {
        SVG.Symbol symbol;
        Throwable th;
        Attributes attributes2 = attributes;
        debug("<symbol>", new Object[0]);
        if (this.currentElement == null) {
            Throwable th2 = th;
            new SAXException("Invalid document. Root element must be <svg>");
            throw th2;
        }
        new SVG.Symbol();
        SVG.Symbol obj = symbol;
        obj.document = this.svgDocument;
        obj.parent = this.currentElement;
        parseAttributesCore(obj, attributes2);
        parseAttributesStyle(obj, attributes2);
        parseAttributesConditional(obj, attributes2);
        parseAttributesViewBox(obj, attributes2);
        this.currentElement.addChild(obj);
        this.currentElement = obj;
    }

    private void marker(Attributes attributes) throws SAXException {
        SVG.Marker marker;
        Throwable th;
        Attributes attributes2 = attributes;
        debug("<marker>", new Object[0]);
        if (this.currentElement == null) {
            Throwable th2 = th;
            new SAXException("Invalid document. Root element must be <svg>");
            throw th2;
        }
        new SVG.Marker();
        SVG.Marker obj = marker;
        obj.document = this.svgDocument;
        obj.parent = this.currentElement;
        parseAttributesCore(obj, attributes2);
        parseAttributesStyle(obj, attributes2);
        parseAttributesConditional(obj, attributes2);
        parseAttributesViewBox(obj, attributes2);
        parseAttributesMarker(obj, attributes2);
        this.currentElement.addChild(obj);
        this.currentElement = obj;
    }

    private void parseAttributesMarker(SVG.Marker marker, Attributes attributes) throws SAXException {
        Throwable th;
        Throwable th2;
        Throwable th3;
        SVG.Marker obj = marker;
        Attributes attributes2 = attributes;
        for (int i = 0; i < attributes2.getLength(); i++) {
            String val = attributes2.getValue(i).trim();
            switch (SVGAttr.fromString(attributes2.getLocalName(i))) {
                case refX:
                    obj.refX = parseLength(val);
                    break;
                case refY:
                    obj.refY = parseLength(val);
                    break;
                case markerWidth:
                    obj.markerWidth = parseLength(val);
                    if (!obj.markerWidth.isNegative()) {
                        break;
                    } else {
                        Throwable th4 = th3;
                        new SAXException("Invalid <marker> element. markerWidth cannot be negative");
                        throw th4;
                    }
                case markerHeight:
                    obj.markerHeight = parseLength(val);
                    if (!obj.markerHeight.isNegative()) {
                        break;
                    } else {
                        Throwable th5 = th2;
                        new SAXException("Invalid <marker> element. markerHeight cannot be negative");
                        throw th5;
                    }
                case markerUnits:
                    if ("strokeWidth".equals(val)) {
                        obj.markerUnitsAreUser = false;
                        break;
                    } else if ("userSpaceOnUse".equals(val)) {
                        obj.markerUnitsAreUser = true;
                        break;
                    } else {
                        Throwable th6 = th;
                        new SAXException("Invalid value for attribute markerUnits");
                        throw th6;
                    }
                case orient:
                    if (!"auto".equals(val)) {
                        obj.orient = Float.valueOf(parseFloat(val));
                        break;
                    } else {
                        obj.orient = Float.valueOf(Float.NaN);
                        break;
                    }
            }
        }
    }

    private void linearGradient(Attributes attributes) throws SAXException {
        SVG.SvgLinearGradient svgLinearGradient;
        Throwable th;
        Attributes attributes2 = attributes;
        debug("<linearGradient>", new Object[0]);
        if (this.currentElement == null) {
            Throwable th2 = th;
            new SAXException("Invalid document. Root element must be <svg>");
            throw th2;
        }
        new SVG.SvgLinearGradient();
        SVG.SvgLinearGradient obj = svgLinearGradient;
        obj.document = this.svgDocument;
        obj.parent = this.currentElement;
        parseAttributesCore(obj, attributes2);
        parseAttributesStyle(obj, attributes2);
        parseAttributesGradient(obj, attributes2);
        parseAttributesLinearGradient(obj, attributes2);
        this.currentElement.addChild(obj);
        this.currentElement = obj;
    }

    private void parseAttributesGradient(SVG.GradientElement gradientElement, Attributes attributes) throws SAXException {
        Throwable th;
        StringBuilder sb;
        Throwable th2;
        SVG.GradientElement obj = gradientElement;
        Attributes attributes2 = attributes;
        for (int i = 0; i < attributes2.getLength(); i++) {
            String val = attributes2.getValue(i).trim();
            switch (SVGAttr.fromString(attributes2.getLocalName(i))) {
                case href:
                    if (XLINK_NAMESPACE.equals(attributes2.getURI(i))) {
                        obj.href = val;
                        break;
                    } else {
                        break;
                    }
                case gradientUnits:
                    if ("objectBoundingBox".equals(val)) {
                        obj.gradientUnitsAreUser = false;
                        break;
                    } else if ("userSpaceOnUse".equals(val)) {
                        obj.gradientUnitsAreUser = true;
                        break;
                    } else {
                        Throwable th3 = th2;
                        new SAXException("Invalid value for attribute gradientUnits");
                        throw th3;
                    }
                case gradientTransform:
                    obj.gradientTransform = parseTransformList(val);
                    break;
                case spreadMethod:
                    try {
                        obj.spreadMethod = SVG.GradientSpread.valueOf(val);
                        break;
                    } catch (IllegalArgumentException e) {
                        IllegalArgumentException illegalArgumentException = e;
                        Throwable th4 = th;
                        new StringBuilder();
                        new SAXException(sb.append("Invalid spreadMethod attribute. \"").append(val).append("\" is not a valid value.").toString());
                        throw th4;
                    }
            }
        }
    }

    private void parseAttributesLinearGradient(SVG.SvgLinearGradient svgLinearGradient, Attributes attributes) throws SAXException {
        SVG.SvgLinearGradient obj = svgLinearGradient;
        Attributes attributes2 = attributes;
        for (int i = 0; i < attributes2.getLength(); i++) {
            String val = attributes2.getValue(i).trim();
            switch (SVGAttr.fromString(attributes2.getLocalName(i))) {
                case f384x1:
                    obj.f338x1 = parseLength(val);
                    break;
                case f387y1:
                    obj.f340y1 = parseLength(val);
                    break;
                case f385x2:
                    obj.f339x2 = parseLength(val);
                    break;
                case f388y2:
                    obj.f341y2 = parseLength(val);
                    break;
            }
        }
    }

    private void radialGradient(Attributes attributes) throws SAXException {
        SVG.SvgRadialGradient svgRadialGradient;
        Throwable th;
        Attributes attributes2 = attributes;
        debug("<radialGradient>", new Object[0]);
        if (this.currentElement == null) {
            Throwable th2 = th;
            new SAXException("Invalid document. Root element must be <svg>");
            throw th2;
        }
        new SVG.SvgRadialGradient();
        SVG.SvgRadialGradient obj = svgRadialGradient;
        obj.document = this.svgDocument;
        obj.parent = this.currentElement;
        parseAttributesCore(obj, attributes2);
        parseAttributesStyle(obj, attributes2);
        parseAttributesGradient(obj, attributes2);
        parseAttributesRadialGradient(obj, attributes2);
        this.currentElement.addChild(obj);
        this.currentElement = obj;
    }

    private void parseAttributesRadialGradient(SVG.SvgRadialGradient svgRadialGradient, Attributes attributes) throws SAXException {
        Throwable th;
        SVG.SvgRadialGradient obj = svgRadialGradient;
        Attributes attributes2 = attributes;
        for (int i = 0; i < attributes2.getLength(); i++) {
            String val = attributes2.getValue(i).trim();
            switch (SVGAttr.fromString(attributes2.getLocalName(i))) {
                case f372cx:
                    obj.f342cx = parseLength(val);
                    break;
                case f373cy:
                    obj.f343cy = parseLength(val);
                    break;
                case f380r:
                    obj.f346r = parseLength(val);
                    if (!obj.f346r.isNegative()) {
                        break;
                    } else {
                        Throwable th2 = th;
                        new SAXException("Invalid <radialGradient> element. r cannot be negative");
                        throw th2;
                    }
                case f377fx:
                    obj.f344fx = parseLength(val);
                    break;
                case f378fy:
                    obj.f345fy = parseLength(val);
                    break;
            }
        }
    }

    private void stop(Attributes attributes) throws SAXException {
        SVG.Stop stop;
        Throwable th;
        Throwable th2;
        Attributes attributes2 = attributes;
        debug("<stop>", new Object[0]);
        if (this.currentElement == null) {
            Throwable th3 = th2;
            new SAXException("Invalid document. Root element must be <svg>");
            throw th3;
        } else if (!(this.currentElement instanceof SVG.GradientElement)) {
            Throwable th4 = th;
            new SAXException("Invalid document. <stop> elements are only valid inside <linearGradient> or <radialGradient> elements.");
            throw th4;
        } else {
            new SVG.Stop();
            SVG.Stop obj = stop;
            obj.document = this.svgDocument;
            obj.parent = this.currentElement;
            parseAttributesCore(obj, attributes2);
            parseAttributesStyle(obj, attributes2);
            parseAttributesStop(obj, attributes2);
            this.currentElement.addChild(obj);
            this.currentElement = obj;
        }
    }

    private void parseAttributesStop(SVG.Stop stop, Attributes attributes) throws SAXException {
        SVG.Stop obj = stop;
        Attributes attributes2 = attributes;
        for (int i = 0; i < attributes2.getLength(); i++) {
            String val = attributes2.getValue(i).trim();
            switch (SVGAttr.fromString(attributes2.getLocalName(i))) {
                case offset:
                    obj.offset = parseGradientOffset(val);
                    break;
            }
        }
    }

    private Float parseGradientOffset(String str) throws SAXException {
        Throwable th;
        StringBuilder sb;
        Throwable th2;
        String val = str;
        if (val.length() == 0) {
            Throwable th3 = th2;
            new SAXException("Invalid offset value in <stop> (empty string)");
            throw th3;
        }
        int end = val.length();
        boolean isPercent = false;
        if (val.charAt(val.length() - 1) == '%') {
            end--;
            isPercent = true;
        }
        try {
            float scalar = parseFloat(val, 0, end);
            if (isPercent) {
                scalar /= 100.0f;
            }
            return Float.valueOf(scalar < 0.0f ? 0.0f : scalar > 100.0f ? 100.0f : scalar);
        } catch (NumberFormatException e) {
            NumberFormatException e2 = e;
            Throwable th4 = th;
            new StringBuilder();
            new SAXException(sb.append("Invalid offset value in <stop>: ").append(val).toString(), e2);
            throw th4;
        }
    }

    private void solidColor(Attributes attributes) throws SAXException {
        SVG.SolidColor solidColor;
        Throwable th;
        Attributes attributes2 = attributes;
        debug("<solidColor>", new Object[0]);
        if (this.currentElement == null) {
            Throwable th2 = th;
            new SAXException("Invalid document. Root element must be <svg>");
            throw th2;
        }
        new SVG.SolidColor();
        SVG.SolidColor obj = solidColor;
        obj.document = this.svgDocument;
        obj.parent = this.currentElement;
        parseAttributesCore(obj, attributes2);
        parseAttributesStyle(obj, attributes2);
        this.currentElement.addChild(obj);
        this.currentElement = obj;
    }

    private void clipPath(Attributes attributes) throws SAXException {
        SVG.ClipPath clipPath;
        Throwable th;
        Attributes attributes2 = attributes;
        debug("<clipPath>", new Object[0]);
        if (this.currentElement == null) {
            Throwable th2 = th;
            new SAXException("Invalid document. Root element must be <svg>");
            throw th2;
        }
        new SVG.ClipPath();
        SVG.ClipPath obj = clipPath;
        obj.document = this.svgDocument;
        obj.parent = this.currentElement;
        parseAttributesCore(obj, attributes2);
        parseAttributesStyle(obj, attributes2);
        parseAttributesTransform(obj, attributes2);
        parseAttributesConditional(obj, attributes2);
        parseAttributesClipPath(obj, attributes2);
        this.currentElement.addChild(obj);
        this.currentElement = obj;
    }

    private void parseAttributesClipPath(SVG.ClipPath clipPath, Attributes attributes) throws SAXException {
        Throwable th;
        SVG.ClipPath obj = clipPath;
        Attributes attributes2 = attributes;
        for (int i = 0; i < attributes2.getLength(); i++) {
            String val = attributes2.getValue(i).trim();
            switch (SVGAttr.fromString(attributes2.getLocalName(i))) {
                case clipPathUnits:
                    if ("objectBoundingBox".equals(val)) {
                        obj.clipPathUnitsAreUser = false;
                        break;
                    } else if ("userSpaceOnUse".equals(val)) {
                        obj.clipPathUnitsAreUser = true;
                        break;
                    } else {
                        Throwable th2 = th;
                        new SAXException("Invalid value for attribute clipPathUnits");
                        throw th2;
                    }
            }
        }
    }

    private void textPath(Attributes attributes) throws SAXException {
        SVG.TextPath textPath;
        Throwable th;
        Attributes attributes2 = attributes;
        debug("<textPath>", new Object[0]);
        if (this.currentElement == null) {
            Throwable th2 = th;
            new SAXException("Invalid document. Root element must be <svg>");
            throw th2;
        }
        new SVG.TextPath();
        SVG.TextPath obj = textPath;
        obj.document = this.svgDocument;
        obj.parent = this.currentElement;
        parseAttributesCore(obj, attributes2);
        parseAttributesStyle(obj, attributes2);
        parseAttributesConditional(obj, attributes2);
        parseAttributesTextPath(obj, attributes2);
        this.currentElement.addChild(obj);
        this.currentElement = obj;
        if (obj.parent instanceof SVG.TextRoot) {
            obj.setTextRoot((SVG.TextRoot) obj.parent);
        } else {
            obj.setTextRoot(((SVG.TextChild) obj.parent).getTextRoot());
        }
    }

    private void parseAttributesTextPath(SVG.TextPath textPath, Attributes attributes) throws SAXException {
        SVG.TextPath obj = textPath;
        Attributes attributes2 = attributes;
        for (int i = 0; i < attributes2.getLength(); i++) {
            String val = attributes2.getValue(i).trim();
            switch (SVGAttr.fromString(attributes2.getLocalName(i))) {
                case href:
                    if (XLINK_NAMESPACE.equals(attributes2.getURI(i))) {
                        obj.href = val;
                        break;
                    } else {
                        break;
                    }
                case startOffset:
                    obj.startOffset = parseLength(val);
                    break;
            }
        }
    }

    private void pattern(Attributes attributes) throws SAXException {
        SVG.Pattern pattern;
        Throwable th;
        Attributes attributes2 = attributes;
        debug("<pattern>", new Object[0]);
        if (this.currentElement == null) {
            Throwable th2 = th;
            new SAXException("Invalid document. Root element must be <svg>");
            throw th2;
        }
        new SVG.Pattern();
        SVG.Pattern obj = pattern;
        obj.document = this.svgDocument;
        obj.parent = this.currentElement;
        parseAttributesCore(obj, attributes2);
        parseAttributesStyle(obj, attributes2);
        parseAttributesConditional(obj, attributes2);
        parseAttributesViewBox(obj, attributes2);
        parseAttributesPattern(obj, attributes2);
        this.currentElement.addChild(obj);
        this.currentElement = obj;
    }

    private void parseAttributesPattern(SVG.Pattern pattern, Attributes attributes) throws SAXException {
        Throwable th;
        Throwable th2;
        Throwable th3;
        Throwable th4;
        SVG.Pattern obj = pattern;
        Attributes attributes2 = attributes;
        for (int i = 0; i < attributes2.getLength(); i++) {
            String val = attributes2.getValue(i).trim();
            switch (SVGAttr.fromString(attributes2.getLocalName(i))) {
                case f383x:
                    obj.f329x = parseLength(val);
                    break;
                case f386y:
                    obj.f330y = parseLength(val);
                    break;
                case width:
                    obj.width = parseLength(val);
                    if (!obj.width.isNegative()) {
                        break;
                    } else {
                        Throwable th5 = th2;
                        new SAXException("Invalid <pattern> element. width cannot be negative");
                        throw th5;
                    }
                case height:
                    obj.height = parseLength(val);
                    if (!obj.height.isNegative()) {
                        break;
                    } else {
                        Throwable th6 = th;
                        new SAXException("Invalid <pattern> element. height cannot be negative");
                        throw th6;
                    }
                case href:
                    if (XLINK_NAMESPACE.equals(attributes2.getURI(i))) {
                        obj.href = val;
                        break;
                    } else {
                        break;
                    }
                case patternUnits:
                    if ("objectBoundingBox".equals(val)) {
                        obj.patternUnitsAreUser = false;
                        break;
                    } else if ("userSpaceOnUse".equals(val)) {
                        obj.patternUnitsAreUser = true;
                        break;
                    } else {
                        Throwable th7 = th4;
                        new SAXException("Invalid value for attribute patternUnits");
                        throw th7;
                    }
                case patternContentUnits:
                    if ("objectBoundingBox".equals(val)) {
                        obj.patternContentUnitsAreUser = false;
                        break;
                    } else if ("userSpaceOnUse".equals(val)) {
                        obj.patternContentUnitsAreUser = true;
                        break;
                    } else {
                        Throwable th8 = th3;
                        new SAXException("Invalid value for attribute patternContentUnits");
                        throw th8;
                    }
                case patternTransform:
                    obj.patternTransform = parseTransformList(val);
                    break;
            }
        }
    }

    private void view(Attributes attributes) throws SAXException {
        SVG.View view;
        Throwable th;
        Attributes attributes2 = attributes;
        debug("<view>", new Object[0]);
        if (this.currentElement == null) {
            Throwable th2 = th;
            new SAXException("Invalid document. Root element must be <svg>");
            throw th2;
        }
        new SVG.View();
        SVG.View obj = view;
        obj.document = this.svgDocument;
        obj.parent = this.currentElement;
        parseAttributesCore(obj, attributes2);
        parseAttributesConditional(obj, attributes2);
        parseAttributesViewBox(obj, attributes2);
        this.currentElement.addChild(obj);
        this.currentElement = obj;
    }

    private void mask(Attributes attributes) throws SAXException {
        SVG.Mask mask;
        Throwable th;
        Attributes attributes2 = attributes;
        debug("<mask>", new Object[0]);
        if (this.currentElement == null) {
            Throwable th2 = th;
            new SAXException("Invalid document. Root element must be <svg>");
            throw th2;
        }
        new SVG.Mask();
        SVG.Mask obj = mask;
        obj.document = this.svgDocument;
        obj.parent = this.currentElement;
        parseAttributesCore(obj, attributes2);
        parseAttributesStyle(obj, attributes2);
        parseAttributesConditional(obj, attributes2);
        parseAttributesMask(obj, attributes2);
        this.currentElement.addChild(obj);
        this.currentElement = obj;
    }

    private void parseAttributesMask(SVG.Mask mask, Attributes attributes) throws SAXException {
        Throwable th;
        Throwable th2;
        Throwable th3;
        Throwable th4;
        SVG.Mask obj = mask;
        Attributes attributes2 = attributes;
        for (int i = 0; i < attributes2.getLength(); i++) {
            String val = attributes2.getValue(i).trim();
            switch (SVGAttr.fromString(attributes2.getLocalName(i))) {
                case f383x:
                    obj.f326x = parseLength(val);
                    break;
                case f386y:
                    obj.f327y = parseLength(val);
                    break;
                case width:
                    obj.width = parseLength(val);
                    if (!obj.width.isNegative()) {
                        break;
                    } else {
                        Throwable th5 = th2;
                        new SAXException("Invalid <mask> element. width cannot be negative");
                        throw th5;
                    }
                case height:
                    obj.height = parseLength(val);
                    if (!obj.height.isNegative()) {
                        break;
                    } else {
                        Throwable th6 = th;
                        new SAXException("Invalid <mask> element. height cannot be negative");
                        throw th6;
                    }
                case maskUnits:
                    if ("objectBoundingBox".equals(val)) {
                        obj.maskUnitsAreUser = false;
                        break;
                    } else if ("userSpaceOnUse".equals(val)) {
                        obj.maskUnitsAreUser = true;
                        break;
                    } else {
                        Throwable th7 = th4;
                        new SAXException("Invalid value for attribute maskUnits");
                        throw th7;
                    }
                case maskContentUnits:
                    if ("objectBoundingBox".equals(val)) {
                        obj.maskContentUnitsAreUser = false;
                        break;
                    } else if ("userSpaceOnUse".equals(val)) {
                        obj.maskContentUnitsAreUser = true;
                        break;
                    } else {
                        Throwable th8 = th3;
                        new SAXException("Invalid value for attribute maskContentUnits");
                        throw th8;
                    }
            }
        }
    }

    protected static class TextScanner {
        protected String input;
        protected int inputLength = 0;
        private NumberParser numberParser;
        protected int position = 0;

        public TextScanner(String input2) {
            NumberParser numberParser2;
            new NumberParser();
            this.numberParser = numberParser2;
            this.input = input2.trim();
            this.inputLength = this.input.length();
        }

        public boolean empty() {
            return this.position == this.inputLength;
        }

        /* access modifiers changed from: protected */
        public boolean isWhitespace(int i) {
            int c = i;
            return c == 32 || c == 10 || c == 13 || c == 9;
        }

        public void skipWhitespace() {
            while (this.position < this.inputLength && isWhitespace(this.input.charAt(this.position))) {
                this.position++;
            }
        }

        /* access modifiers changed from: protected */
        public boolean isEOL(int i) {
            int c = i;
            return c == 10 || c == 13;
        }

        public boolean skipCommaWhitespace() {
            skipWhitespace();
            if (this.position == this.inputLength) {
                return false;
            }
            if (this.input.charAt(this.position) != ',') {
                return false;
            }
            this.position++;
            skipWhitespace();
            return true;
        }

        public float nextFloat() {
            float val = this.numberParser.parseNumber(this.input, this.position, this.inputLength);
            if (!Float.isNaN(val)) {
                this.position = this.numberParser.getEndPos();
            }
            return val;
        }

        public float possibleNextFloat() {
            boolean skipCommaWhitespace = skipCommaWhitespace();
            float val = this.numberParser.parseNumber(this.input, this.position, this.inputLength);
            if (!Float.isNaN(val)) {
                this.position = this.numberParser.getEndPos();
            }
            return val;
        }

        public float checkedNextFloat(float lastRead) {
            if (Float.isNaN(lastRead)) {
                return Float.NaN;
            }
            boolean skipCommaWhitespace = skipCommaWhitespace();
            return nextFloat();
        }

        public Integer nextInteger() {
            IntegerParser ip = IntegerParser.parseInt(this.input, this.position, this.inputLength);
            if (ip == null) {
                return null;
            }
            this.position = ip.getEndPos();
            return Integer.valueOf(ip.value());
        }

        public Integer nextChar() {
            if (this.position == this.inputLength) {
                return null;
            }
            String str = this.input;
            int i = this.position;
            int i2 = i + 1;
            this.position = i2;
            return Integer.valueOf(str.charAt(i));
        }

        public SVG.Length nextLength() {
            SVG.Length length;
            SVG.Length length2;
            float scalar = nextFloat();
            if (Float.isNaN(scalar)) {
                return null;
            }
            SVG.Unit unit = nextUnit();
            if (unit == null) {
                new SVG.Length(scalar, SVG.Unit.f358px);
                return length2;
            }
            new SVG.Length(scalar, unit);
            return length;
        }

        public Boolean nextFlag() {
            if (this.position == this.inputLength) {
                return null;
            }
            char ch = this.input.charAt(this.position);
            if (ch != '0' && ch != '1') {
                return null;
            }
            this.position++;
            return Boolean.valueOf(ch == '1');
        }

        public Boolean checkedNextFlag(Object lastRead) {
            if (lastRead == null) {
                return null;
            }
            boolean skipCommaWhitespace = skipCommaWhitespace();
            return nextFlag();
        }

        public boolean consume(char ch) {
            boolean found = this.position < this.inputLength && this.input.charAt(this.position) == ch;
            if (found) {
                this.position++;
            }
            return found;
        }

        public boolean consume(String str) {
            String str2 = str;
            int len = str2.length();
            boolean found = this.position <= this.inputLength - len && this.input.substring(this.position, this.position + len).equals(str2);
            if (found) {
                this.position += len;
            }
            return found;
        }

        /* access modifiers changed from: protected */
        public int advanceChar() {
            if (this.position == this.inputLength) {
                return -1;
            }
            this.position++;
            if (this.position < this.inputLength) {
                return this.input.charAt(this.position);
            }
            return -1;
        }

        public String nextToken() {
            return nextToken(' ');
        }

        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v13, resolved type: char} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v1, resolved type: char} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v14, resolved type: char} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v18, resolved type: char} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v7, resolved type: char} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v23, resolved type: char} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v24, resolved type: char} */
        /* JADX WARNING: Multi-variable type inference failed */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public java.lang.String nextToken(char r8) {
            /*
                r7 = this;
                r0 = r7
                r1 = r8
                r4 = r0
                boolean r4 = r4.empty()
                if (r4 == 0) goto L_0x000c
                r4 = 0
                r0 = r4
            L_0x000b:
                return r0
            L_0x000c:
                r4 = r0
                java.lang.String r4 = r4.input
                r5 = r0
                int r5 = r5.position
                char r4 = r4.charAt(r5)
                r2 = r4
                r4 = r0
                r5 = r2
                boolean r4 = r4.isWhitespace(r5)
                if (r4 != 0) goto L_0x0023
                r4 = r2
                r5 = r1
                if (r4 != r5) goto L_0x0026
            L_0x0023:
                r4 = 0
                r0 = r4
                goto L_0x000b
            L_0x0026:
                r4 = r0
                int r4 = r4.position
                r3 = r4
                r4 = r0
                int r4 = r4.advanceChar()
                r2 = r4
            L_0x0030:
                r4 = r2
                r5 = -1
                if (r4 == r5) goto L_0x0047
                r4 = r2
                r5 = r1
                if (r4 == r5) goto L_0x0047
                r4 = r0
                r5 = r2
                boolean r4 = r4.isWhitespace(r5)
                if (r4 != 0) goto L_0x0047
                r4 = r0
                int r4 = r4.advanceChar()
                r2 = r4
                goto L_0x0030
            L_0x0047:
                r4 = r0
                java.lang.String r4 = r4.input
                r5 = r3
                r6 = r0
                int r6 = r6.position
                java.lang.String r4 = r4.substring(r5, r6)
                r0 = r4
                goto L_0x000b
            */
            throw new UnsupportedOperationException("Method not decompiled: com.caverock.androidsvg.SVGParser.TextScanner.nextToken(char):java.lang.String");
        }

        public String nextFunction() {
            int ch;
            if (empty()) {
                return null;
            }
            int start = this.position;
            int charAt = this.input.charAt(this.position);
            while (true) {
                ch = charAt;
                if ((ch < 97 || ch > 122) && (ch < 65 || ch > 90)) {
                    int end = this.position;
                } else {
                    charAt = advanceChar();
                }
            }
            int end2 = this.position;
            while (isWhitespace(ch)) {
                ch = advanceChar();
            }
            if (ch == 40) {
                this.position++;
                return this.input.substring(start, end2);
            }
            this.position = start;
            return null;
        }

        public String ahead() {
            int start = this.position;
            while (!empty() && !isWhitespace(this.input.charAt(this.position))) {
                this.position++;
            }
            String str = this.input.substring(start, this.position);
            this.position = start;
            return str;
        }

        public SVG.Unit nextUnit() {
            if (empty()) {
                return null;
            }
            if (this.input.charAt(this.position) == 37) {
                this.position++;
                return SVG.Unit.percent;
            } else if (this.position > this.inputLength - 2) {
                return null;
            } else {
                try {
                    SVG.Unit result = SVG.Unit.valueOf(this.input.substring(this.position, this.position + 2).toLowerCase(Locale.US));
                    this.position += 2;
                    return result;
                } catch (IllegalArgumentException e) {
                    IllegalArgumentException illegalArgumentException = e;
                    return null;
                }
            }
        }

        public boolean hasLetter() {
            if (this.position == this.inputLength) {
                return false;
            }
            char ch = this.input.charAt(this.position);
            return (ch >= 'a' && ch <= 'z') || (ch >= 'A' && ch <= 'Z');
        }

        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v11, resolved type: char} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v1, resolved type: char} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v12, resolved type: char} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v13, resolved type: char} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v21, resolved type: char} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v25, resolved type: char} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v26, resolved type: char} */
        /* JADX WARNING: Multi-variable type inference failed */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public java.lang.String nextQuotedString() {
            /*
                r9 = this;
                r0 = r9
                r4 = r0
                boolean r4 = r4.empty()
                if (r4 == 0) goto L_0x000b
                r4 = 0
                r0 = r4
            L_0x000a:
                return r0
            L_0x000b:
                r4 = r0
                int r4 = r4.position
                r1 = r4
                r4 = r0
                java.lang.String r4 = r4.input
                r5 = r0
                int r5 = r5.position
                char r4 = r4.charAt(r5)
                r2 = r4
                r4 = r2
                r3 = r4
                r4 = r2
                r5 = 39
                if (r4 == r5) goto L_0x0029
                r4 = r2
                r5 = 34
                if (r4 == r5) goto L_0x0029
                r4 = 0
                r0 = r4
                goto L_0x000a
            L_0x0029:
                r4 = r0
                int r4 = r4.advanceChar()
                r2 = r4
            L_0x002f:
                r4 = r2
                r5 = -1
                if (r4 == r5) goto L_0x003e
                r4 = r2
                r5 = r3
                if (r4 == r5) goto L_0x003e
                r4 = r0
                int r4 = r4.advanceChar()
                r2 = r4
                goto L_0x002f
            L_0x003e:
                r4 = r2
                r5 = -1
                if (r4 != r5) goto L_0x0049
                r4 = r0
                r5 = r1
                r4.position = r5
                r4 = 0
                r0 = r4
                goto L_0x000a
            L_0x0049:
                r4 = r0
                r8 = r4
                r4 = r8
                r5 = r8
                int r5 = r5.position
                r6 = 1
                int r5 = r5 + 1
                r4.position = r5
                r4 = r0
                java.lang.String r4 = r4.input
                r5 = r1
                r6 = 1
                int r5 = r5 + 1
                r6 = r0
                int r6 = r6.position
                r7 = 1
                int r6 = r6 + -1
                java.lang.String r4 = r4.substring(r5, r6)
                r0 = r4
                goto L_0x000a
            */
            throw new UnsupportedOperationException("Method not decompiled: com.caverock.androidsvg.SVGParser.TextScanner.nextQuotedString():java.lang.String");
        }

        public String restOfText() {
            if (empty()) {
                return null;
            }
            int start = this.position;
            this.position = this.inputLength;
            return this.input.substring(start);
        }
    }

    private void parseAttributesCore(SVG.SvgElementBase svgElementBase, Attributes attributes) throws SAXException {
        Throwable th;
        StringBuilder sb;
        SVG.SvgElementBase obj = svgElementBase;
        Attributes attributes2 = attributes;
        int i = 0;
        while (i < attributes2.getLength()) {
            String qname = attributes2.getQName(i);
            if (qname.equals(CommonProperties.f295ID) || qname.equals("xml:id")) {
                obj.f337id = attributes2.getValue(i).trim();
                return;
            } else if (qname.equals("xml:space")) {
                String val = attributes2.getValue(i).trim();
                if ("default".equals(val)) {
                    obj.spacePreserve = Boolean.FALSE;
                    return;
                } else if ("preserve".equals(val)) {
                    obj.spacePreserve = Boolean.TRUE;
                    return;
                } else {
                    Throwable th2 = th;
                    new StringBuilder();
                    new SAXException(sb.append("Invalid value for \"xml:space\" attribute: ").append(val).toString());
                    throw th2;
                }
            } else {
                i++;
            }
        }
    }

    private void parseAttributesStyle(SVG.SvgElementBase svgElementBase, Attributes attributes) throws SAXException {
        SVG.Style style;
        SVG.SvgElementBase obj = svgElementBase;
        Attributes attributes2 = attributes;
        for (int i = 0; i < attributes2.getLength(); i++) {
            String val = attributes2.getValue(i).trim();
            if (val.length() != 0) {
                switch (SVGAttr.fromString(attributes2.getLocalName(i))) {
                    case style:
                        parseStyle(obj, val);
                        break;
                    case CLASS:
                        obj.classNames = CSSParser.parseClassAttribute(val);
                        break;
                    default:
                        if (obj.baseStyle == null) {
                            new SVG.Style();
                            obj.baseStyle = style;
                        }
                        processStyleProperty(obj.baseStyle, attributes2.getLocalName(i), attributes2.getValue(i).trim());
                        break;
                }
            }
        }
    }

    private static void parseStyle(SVG.SvgElementBase svgElementBase, String style) throws SAXException {
        TextScanner textScanner;
        SVG.Style style2;
        SVG.SvgElementBase obj = svgElementBase;
        new TextScanner(style.replaceAll("/\\*.*?\\*/", ""));
        TextScanner scan = textScanner;
        while (true) {
            String propertyName = scan.nextToken(':');
            scan.skipWhitespace();
            if (scan.consume(':')) {
                scan.skipWhitespace();
                String propertyValue = scan.nextToken(';');
                if (propertyValue != null) {
                    scan.skipWhitespace();
                    if (scan.empty() || scan.consume(';')) {
                        if (obj.style == null) {
                            new SVG.Style();
                            obj.style = style2;
                        }
                        processStyleProperty(obj.style, propertyName, propertyValue);
                        scan.skipWhitespace();
                    }
                } else {
                    return;
                }
            } else {
                return;
            }
        }
    }

    protected static void processStyleProperty(SVG.Style style, String str, String str2) throws SAXException {
        Throwable th;
        StringBuilder sb;
        StringBuilder sb2;
        Throwable th2;
        StringBuilder sb3;
        StringBuilder sb4;
        SVG.Style style2 = style;
        String localName = str;
        String val = str2;
        if (val.length() != 0 && !val.equals("inherit")) {
            switch (SVGAttr.fromString(localName)) {
                case fill:
                    style2.fill = parsePaintSpecifier(val, "fill");
                    style2.specifiedFlags |= 1;
                    return;
                case fill_rule:
                    style2.fillRule = parseFillRule(val);
                    style2.specifiedFlags |= 2;
                    return;
                case fill_opacity:
                    style2.fillOpacity = Float.valueOf(parseOpacity(val));
                    style2.specifiedFlags |= 4;
                    return;
                case stroke:
                    style2.stroke = parsePaintSpecifier(val, "stroke");
                    style2.specifiedFlags |= 8;
                    return;
                case stroke_opacity:
                    style2.strokeOpacity = Float.valueOf(parseOpacity(val));
                    style2.specifiedFlags |= 16;
                    return;
                case stroke_width:
                    style2.strokeWidth = parseLength(val);
                    style2.specifiedFlags |= 32;
                    return;
                case stroke_linecap:
                    style2.strokeLineCap = parseStrokeLineCap(val);
                    style2.specifiedFlags |= 64;
                    return;
                case stroke_linejoin:
                    style2.strokeLineJoin = parseStrokeLineJoin(val);
                    style2.specifiedFlags |= 128;
                    return;
                case stroke_miterlimit:
                    style2.strokeMiterLimit = Float.valueOf(parseFloat(val));
                    style2.specifiedFlags |= 256;
                    return;
                case stroke_dasharray:
                    if ("none".equals(val)) {
                        style2.strokeDashArray = null;
                    } else {
                        style2.strokeDashArray = parseStrokeDashArray(val);
                    }
                    style2.specifiedFlags |= 512;
                    return;
                case stroke_dashoffset:
                    style2.strokeDashOffset = parseLength(val);
                    style2.specifiedFlags |= PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID;
                    return;
                case opacity:
                    style2.opacity = Float.valueOf(parseOpacity(val));
                    style2.specifiedFlags |= PlaybackStateCompat.ACTION_PLAY_FROM_SEARCH;
                    return;
                case color:
                    style2.color = parseColour(val);
                    style2.specifiedFlags |= PlaybackStateCompat.ACTION_SKIP_TO_QUEUE_ITEM;
                    return;
                case font:
                    parseFont(style2, val);
                    return;
                case font_family:
                    style2.fontFamily = parseFontFamily(val);
                    style2.specifiedFlags |= PlaybackStateCompat.ACTION_PLAY_FROM_URI;
                    return;
                case font_size:
                    style2.fontSize = parseFontSize(val);
                    style2.specifiedFlags |= PlaybackStateCompat.ACTION_PREPARE;
                    return;
                case font_weight:
                    style2.fontWeight = parseFontWeight(val);
                    style2.specifiedFlags |= PlaybackStateCompat.ACTION_PREPARE_FROM_MEDIA_ID;
                    return;
                case font_style:
                    style2.fontStyle = parseFontStyle(val);
                    style2.specifiedFlags |= PlaybackStateCompat.ACTION_PREPARE_FROM_SEARCH;
                    return;
                case text_decoration:
                    style2.textDecoration = parseTextDecoration(val);
                    style2.specifiedFlags |= PlaybackStateCompat.ACTION_PREPARE_FROM_URI;
                    return;
                case direction:
                    style2.direction = parseTextDirection(val);
                    style2.specifiedFlags |= 68719476736L;
                    return;
                case text_anchor:
                    style2.textAnchor = parseTextAnchor(val);
                    style2.specifiedFlags |= PlaybackStateCompat.ACTION_SET_REPEAT_MODE;
                    return;
                case overflow:
                    style2.overflow = parseOverflow(val);
                    style2.specifiedFlags |= PlaybackStateCompat.ACTION_SET_SHUFFLE_MODE_ENABLED;
                    return;
                case marker:
                    style2.markerStart = parseFunctionalIRI(val, localName);
                    style2.markerMid = style2.markerStart;
                    style2.markerEnd = style2.markerStart;
                    style2.specifiedFlags |= 14680064;
                    return;
                case marker_start:
                    style2.markerStart = parseFunctionalIRI(val, localName);
                    style2.specifiedFlags |= PlaybackStateCompat.ACTION_SET_SHUFFLE_MODE;
                    return;
                case marker_mid:
                    style2.markerMid = parseFunctionalIRI(val, localName);
                    style2.specifiedFlags |= 4194304;
                    return;
                case marker_end:
                    style2.markerEnd = parseFunctionalIRI(val, localName);
                    style2.specifiedFlags |= 8388608;
                    return;
                case display:
                    if (val.indexOf(124) < 0) {
                        new StringBuilder();
                        if (VALID_DISPLAY_VALUES.indexOf(sb4.append('|').append(val).append('|').toString()) != -1) {
                            style2.display = Boolean.valueOf(!val.equals("none"));
                            style2.specifiedFlags |= 16777216;
                            return;
                        }
                    }
                    Throwable th3 = th2;
                    new StringBuilder();
                    new SAXException(sb3.append("Invalid value for \"display\" attribute: ").append(val).toString());
                    throw th3;
                case visibility:
                    if (val.indexOf(124) < 0) {
                        new StringBuilder();
                        if (VALID_VISIBILITY_VALUES.indexOf(sb2.append('|').append(val).append('|').toString()) != -1) {
                            style2.visibility = Boolean.valueOf(val.equals("visible"));
                            style2.specifiedFlags |= 33554432;
                            return;
                        }
                    }
                    Throwable th4 = th;
                    new StringBuilder();
                    new SAXException(sb.append("Invalid value for \"visibility\" attribute: ").append(val).toString());
                    throw th4;
                case stop_color:
                    if (val.equals(CURRENTCOLOR)) {
                        style2.stopColor = SVG.CurrentColor.getInstance();
                    } else {
                        style2.stopColor = parseColour(val);
                    }
                    style2.specifiedFlags |= 67108864;
                    return;
                case stop_opacity:
                    style2.stopOpacity = Float.valueOf(parseOpacity(val));
                    style2.specifiedFlags |= 134217728;
                    return;
                case clip:
                    style2.clip = parseClip(val);
                    style2.specifiedFlags |= PlaybackStateCompat.ACTION_SET_CAPTIONING_ENABLED;
                    return;
                case clip_path:
                    style2.clipPath = parseFunctionalIRI(val, localName);
                    style2.specifiedFlags |= 268435456;
                    return;
                case clip_rule:
                    style2.clipRule = parseFillRule(val);
                    style2.specifiedFlags |= 536870912;
                    return;
                case mask:
                    style2.mask = parseFunctionalIRI(val, localName);
                    style2.specifiedFlags |= 1073741824;
                    return;
                case solid_color:
                    if (val.equals(CURRENTCOLOR)) {
                        style2.solidColor = SVG.CurrentColor.getInstance();
                    } else {
                        style2.solidColor = parseColour(val);
                    }
                    style2.specifiedFlags |= Declaration.VOLATILE_ACCESS;
                    return;
                case solid_opacity:
                    style2.solidOpacity = Float.valueOf(parseOpacity(val));
                    style2.specifiedFlags |= Declaration.TRANSIENT_ACCESS;
                    return;
                case viewport_fill:
                    if (val.equals(CURRENTCOLOR)) {
                        style2.viewportFill = SVG.CurrentColor.getInstance();
                    } else {
                        style2.viewportFill = parseColour(val);
                    }
                    style2.specifiedFlags |= Declaration.ENUM_ACCESS;
                    return;
                case viewport_fill_opacity:
                    style2.viewportFillOpacity = Float.valueOf(parseOpacity(val));
                    style2.specifiedFlags |= Declaration.FINAL_ACCESS;
                    return;
                case vector_effect:
                    style2.vectorEffect = parseVectorEffect(val);
                    style2.specifiedFlags |= 34359738368L;
                    return;
                default:
                    return;
            }
        }
    }

    private void parseAttributesViewBox(SVG.SvgViewBoxContainer svgViewBoxContainer, Attributes attributes) throws SAXException {
        SVG.SvgViewBoxContainer obj = svgViewBoxContainer;
        Attributes attributes2 = attributes;
        for (int i = 0; i < attributes2.getLength(); i++) {
            String val = attributes2.getValue(i).trim();
            switch (SVGAttr.fromString(attributes2.getLocalName(i))) {
                case preserveAspectRatio:
                    parsePreserveAspectRatio(obj, val);
                    break;
                case viewBox:
                    obj.viewBox = parseViewBox(val);
                    break;
            }
        }
    }

    private void parseAttributesTransform(SVG.HasTransform hasTransform, Attributes attributes) throws SAXException {
        SVG.HasTransform obj = hasTransform;
        Attributes attributes2 = attributes;
        for (int i = 0; i < attributes2.getLength(); i++) {
            if (SVGAttr.fromString(attributes2.getLocalName(i)) == SVGAttr.transform) {
                obj.setTransform(parseTransformList(attributes2.getValue(i)));
            }
        }
    }

    private Matrix parseTransformList(String str) throws SAXException {
        Matrix matrix;
        TextScanner textScanner;
        Throwable th;
        StringBuilder sb;
        Throwable th2;
        StringBuilder sb2;
        Throwable th3;
        StringBuilder sb3;
        Throwable th4;
        StringBuilder sb4;
        Throwable th5;
        StringBuilder sb5;
        Throwable th6;
        StringBuilder sb6;
        Throwable th7;
        StringBuilder sb7;
        Throwable th8;
        StringBuilder sb8;
        Throwable th9;
        StringBuilder sb9;
        Matrix matrix2;
        String val = str;
        new Matrix();
        Matrix matrix3 = matrix;
        new TextScanner(val);
        TextScanner scan = textScanner;
        scan.skipWhitespace();
        while (!scan.empty()) {
            String cmd = scan.nextFunction();
            if (cmd == null) {
                Throwable th10 = th;
                new StringBuilder();
                new SAXException(sb.append("Bad transform function encountered in transform list: ").append(val).toString());
                throw th10;
            }
            if (cmd.equals("matrix")) {
                scan.skipWhitespace();
                float a = scan.nextFloat();
                boolean skipCommaWhitespace = scan.skipCommaWhitespace();
                float b = scan.nextFloat();
                boolean skipCommaWhitespace2 = scan.skipCommaWhitespace();
                float c = scan.nextFloat();
                boolean skipCommaWhitespace3 = scan.skipCommaWhitespace();
                float d = scan.nextFloat();
                boolean skipCommaWhitespace4 = scan.skipCommaWhitespace();
                float e = scan.nextFloat();
                boolean skipCommaWhitespace5 = scan.skipCommaWhitespace();
                float f = scan.nextFloat();
                scan.skipWhitespace();
                if (Float.isNaN(f) || !scan.consume(')')) {
                    Throwable th11 = th9;
                    new StringBuilder();
                    new SAXException(sb9.append("Invalid transform list: ").append(val).toString());
                    throw th11;
                }
                new Matrix();
                Matrix m = matrix2;
                float[] fArr = new float[9];
                fArr[0] = a;
                float[] fArr2 = fArr;
                fArr2[1] = c;
                float[] fArr3 = fArr2;
                fArr3[2] = e;
                float[] fArr4 = fArr3;
                fArr4[3] = b;
                float[] fArr5 = fArr4;
                fArr5[4] = d;
                float[] fArr6 = fArr5;
                fArr6[5] = f;
                float[] fArr7 = fArr6;
                fArr7[6] = 0.0f;
                float[] fArr8 = fArr7;
                fArr8[7] = 0.0f;
                float[] fArr9 = fArr8;
                fArr9[8] = 1.0f;
                m.setValues(fArr9);
                boolean preConcat = matrix3.preConcat(m);
            } else if (cmd.equals("translate")) {
                scan.skipWhitespace();
                float tx = scan.nextFloat();
                float ty = scan.possibleNextFloat();
                scan.skipWhitespace();
                if (Float.isNaN(tx) || !scan.consume(')')) {
                    Throwable th12 = th8;
                    new StringBuilder();
                    new SAXException(sb8.append("Invalid transform list: ").append(val).toString());
                    throw th12;
                } else if (Float.isNaN(ty)) {
                    boolean preTranslate = matrix3.preTranslate(tx, 0.0f);
                } else {
                    boolean preTranslate2 = matrix3.preTranslate(tx, ty);
                }
            } else if (cmd.equals("scale")) {
                scan.skipWhitespace();
                float sx = scan.nextFloat();
                float sy = scan.possibleNextFloat();
                scan.skipWhitespace();
                if (Float.isNaN(sx) || !scan.consume(')')) {
                    Throwable th13 = th7;
                    new StringBuilder();
                    new SAXException(sb7.append("Invalid transform list: ").append(val).toString());
                    throw th13;
                } else if (Float.isNaN(sy)) {
                    boolean preScale = matrix3.preScale(sx, sx);
                } else {
                    boolean preScale2 = matrix3.preScale(sx, sy);
                }
            } else if (cmd.equals("rotate")) {
                scan.skipWhitespace();
                float ang = scan.nextFloat();
                float cx = scan.possibleNextFloat();
                float cy = scan.possibleNextFloat();
                scan.skipWhitespace();
                if (Float.isNaN(ang) || !scan.consume(')')) {
                    Throwable th14 = th5;
                    new StringBuilder();
                    new SAXException(sb5.append("Invalid transform list: ").append(val).toString());
                    throw th14;
                } else if (Float.isNaN(cx)) {
                    boolean preRotate = matrix3.preRotate(ang);
                } else if (!Float.isNaN(cy)) {
                    boolean preRotate2 = matrix3.preRotate(ang, cx, cy);
                } else {
                    Throwable th15 = th6;
                    new StringBuilder();
                    new SAXException(sb6.append("Invalid transform list: ").append(val).toString());
                    throw th15;
                }
            } else if (cmd.equals("skewX")) {
                scan.skipWhitespace();
                float ang2 = scan.nextFloat();
                scan.skipWhitespace();
                if (Float.isNaN(ang2) || !scan.consume(')')) {
                    Throwable th16 = th4;
                    new StringBuilder();
                    new SAXException(sb4.append("Invalid transform list: ").append(val).toString());
                    throw th16;
                }
                boolean preSkew = matrix3.preSkew((float) Math.tan(Math.toRadians((double) ang2)), 0.0f);
            } else if (cmd.equals("skewY")) {
                scan.skipWhitespace();
                float ang3 = scan.nextFloat();
                scan.skipWhitespace();
                if (Float.isNaN(ang3) || !scan.consume(')')) {
                    Throwable th17 = th3;
                    new StringBuilder();
                    new SAXException(sb3.append("Invalid transform list: ").append(val).toString());
                    throw th17;
                }
                boolean preSkew2 = matrix3.preSkew(0.0f, (float) Math.tan(Math.toRadians((double) ang3)));
            } else if (cmd != null) {
                Throwable th18 = th2;
                new StringBuilder();
                new SAXException(sb2.append("Invalid transform list fn: ").append(cmd).append(")").toString());
                throw th18;
            }
            if (scan.empty()) {
                break;
            }
            boolean skipCommaWhitespace6 = scan.skipCommaWhitespace();
        }
        return matrix3;
    }

    protected static SVG.Length parseLength(String str) throws SAXException {
        Throwable th;
        StringBuilder sb;
        Throwable th2;
        StringBuilder sb2;
        SVG.Length length;
        Throwable th3;
        String val = str;
        if (val.length() == 0) {
            Throwable th4 = th3;
            new SAXException("Invalid length value (empty string)");
            throw th4;
        }
        int end = val.length();
        SVG.Unit unit = SVG.Unit.f358px;
        char lastChar = val.charAt(end - 1);
        if (lastChar == '%') {
            end--;
            unit = SVG.Unit.percent;
        } else if (end > 2 && Character.isLetter(lastChar) && Character.isLetter(val.charAt(end - 2))) {
            end -= 2;
            try {
                unit = SVG.Unit.valueOf(val.substring(end).toLowerCase(Locale.US));
            } catch (IllegalArgumentException e) {
                IllegalArgumentException illegalArgumentException = e;
                Throwable th5 = th;
                new StringBuilder();
                new SAXException(sb.append("Invalid length unit specifier: ").append(val).toString());
                throw th5;
            }
        }
        try {
            SVG.Length length2 = length;
            new SVG.Length(parseFloat(val, 0, end), unit);
            return length2;
        } catch (NumberFormatException e2) {
            NumberFormatException e3 = e2;
            Throwable th6 = th2;
            new StringBuilder();
            new SAXException(sb2.append("Invalid length value: ").append(val).toString(), e3);
            throw th6;
        }
    }

    private static List<SVG.Length> parseLengthList(String str) throws SAXException {
        List<SVG.Length> list;
        TextScanner textScanner;
        Throwable th;
        StringBuilder sb;
        Object obj;
        Throwable th2;
        String val = str;
        if (val.length() == 0) {
            Throwable th3 = th2;
            new SAXException("Invalid length list (empty string)");
            throw th3;
        }
        new ArrayList(1);
        List<SVG.Length> coords = list;
        new TextScanner(val);
        TextScanner scan = textScanner;
        scan.skipWhitespace();
        while (!scan.empty()) {
            float scalar = scan.nextFloat();
            if (Float.isNaN(scalar)) {
                Throwable th4 = th;
                new StringBuilder();
                new SAXException(sb.append("Invalid length list value: ").append(scan.ahead()).toString());
                throw th4;
            }
            SVG.Unit unit = scan.nextUnit();
            if (unit == null) {
                unit = SVG.Unit.f358px;
            }
            new SVG.Length(scalar, unit);
            boolean add = coords.add(obj);
            boolean skipCommaWhitespace = scan.skipCommaWhitespace();
        }
        return coords;
    }

    private static float parseFloat(String str) throws SAXException {
        Throwable th;
        String val = str;
        int len = val.length();
        if (len != 0) {
            return parseFloat(val, 0, len);
        }
        Throwable th2 = th;
        new SAXException("Invalid float value (empty string)");
        throw th2;
    }

    private static float parseFloat(String str, int offset, int len) throws SAXException {
        NumberParser np;
        Throwable th;
        StringBuilder sb;
        String val = str;
        new NumberParser();
        float num = np.parseNumber(val, offset, len);
        if (!Float.isNaN(num)) {
            return num;
        }
        Throwable th2 = th;
        new StringBuilder();
        new SAXException(sb.append("Invalid float value: ").append(val).toString());
        throw th2;
    }

    private static float parseOpacity(String val) throws SAXException {
        float o = parseFloat(val);
        return o < 0.0f ? 0.0f : o > 1.0f ? 1.0f : o;
    }

    private static SVG.Box parseViewBox(String val) throws SAXException {
        TextScanner textScanner;
        Throwable th;
        SVG.Box box;
        Throwable th2;
        Throwable th3;
        new TextScanner(val);
        TextScanner scan = textScanner;
        scan.skipWhitespace();
        float minX = scan.nextFloat();
        boolean skipCommaWhitespace = scan.skipCommaWhitespace();
        float minY = scan.nextFloat();
        boolean skipCommaWhitespace2 = scan.skipCommaWhitespace();
        float width = scan.nextFloat();
        boolean skipCommaWhitespace3 = scan.skipCommaWhitespace();
        float height = scan.nextFloat();
        if (Float.isNaN(minX) || Float.isNaN(minY) || Float.isNaN(width) || Float.isNaN(height)) {
            Throwable th4 = th;
            new SAXException("Invalid viewBox definition - should have four numbers");
            throw th4;
        } else if (width < 0.0f) {
            Throwable th5 = th3;
            new SAXException("Invalid viewBox. width cannot be negative");
            throw th5;
        } else if (height < 0.0f) {
            Throwable th6 = th2;
            new SAXException("Invalid viewBox. height cannot be negative");
            throw th6;
        } else {
            new SVG.Box(minX, minY, width, height);
            return box;
        }
    }

    private static void parsePreserveAspectRatio(SVG.SvgPreserveAspectRatioContainer svgPreserveAspectRatioContainer, String str) throws SAXException {
        TextScanner textScanner;
        PreserveAspectRatio preserveAspectRatio;
        Throwable th;
        StringBuilder sb;
        SVG.SvgPreserveAspectRatioContainer obj = svgPreserveAspectRatioContainer;
        String val = str;
        new TextScanner(val);
        TextScanner scan = textScanner;
        scan.skipWhitespace();
        PreserveAspectRatio.Scale scale = null;
        String word = scan.nextToken();
        if ("defer".equals(word)) {
            scan.skipWhitespace();
            word = scan.nextToken();
        }
        PreserveAspectRatio.Alignment align = AspectRatioKeywords.get(word);
        scan.skipWhitespace();
        if (!scan.empty()) {
            String meetOrSlice = scan.nextToken();
            if (meetOrSlice.equals("meet")) {
                scale = PreserveAspectRatio.Scale.Meet;
            } else if (meetOrSlice.equals("slice")) {
                scale = PreserveAspectRatio.Scale.Slice;
            } else {
                Throwable th2 = th;
                new StringBuilder();
                new SAXException(sb.append("Invalid preserveAspectRatio definition: ").append(val).toString());
                throw th2;
            }
        }
        new PreserveAspectRatio(align, scale);
        obj.preserveAspectRatio = preserveAspectRatio;
    }

    private static SVG.SvgPaint parsePaintSpecifier(String str, String str2) throws SAXException {
        SVG.SvgPaint svgPaint;
        Throwable th;
        StringBuilder sb;
        String val = str;
        String attrName = str2;
        if (!val.startsWith("url(")) {
            return parseColourSpecifer(val);
        }
        int closeBracket = val.indexOf(")");
        if (closeBracket == -1) {
            Throwable th2 = th;
            new StringBuilder();
            new SAXException(sb.append("Bad ").append(attrName).append(" attribute. Unterminated url() reference").toString());
            throw th2;
        }
        String href = val.substring(4, closeBracket).trim();
        SVG.SvgPaint fallback = null;
        String val2 = val.substring(closeBracket + 1).trim();
        if (val2.length() > 0) {
            fallback = parseColourSpecifer(val2);
        }
        new SVG.PaintReference(href, fallback);
        return svgPaint;
    }

    private static SVG.SvgPaint parseColourSpecifer(String str) throws SAXException {
        String val = str;
        if (val.equals("none")) {
            return null;
        }
        if (val.equals(CURRENTCOLOR)) {
            return SVG.CurrentColor.getInstance();
        }
        return parseColour(val);
    }

    private static SVG.Colour parseColour(String str) throws SAXException {
        TextScanner textScanner;
        Throwable th;
        StringBuilder sb;
        SVG.Colour colour;
        Throwable th2;
        StringBuilder sb2;
        SVG.Colour colour2;
        SVG.Colour colour3;
        Throwable th3;
        StringBuilder sb3;
        String val = str;
        if (val.charAt(0) == '#') {
            IntegerParser ip = IntegerParser.parseHex(val, 1, val.length());
            if (ip == null) {
                Throwable th4 = th3;
                new StringBuilder();
                new SAXException(sb3.append("Bad hex colour value: ").append(val).toString());
                throw th4;
            }
            int pos = ip.getEndPos();
            if (pos == 7) {
                new SVG.Colour(ip.value());
                return colour3;
            } else if (pos == 4) {
                int threehex = ip.value();
                int h1 = threehex & 3840;
                int h2 = threehex & 240;
                int h3 = threehex & 15;
                new SVG.Colour((h1 << 16) | (h1 << 12) | (h2 << 8) | (h2 << 4) | (h3 << 4) | h3);
                return colour2;
            } else {
                Throwable th5 = th2;
                new StringBuilder();
                new SAXException(sb2.append("Bad hex colour value: ").append(val).toString());
                throw th5;
            }
        } else if (!val.toLowerCase(Locale.US).startsWith("rgb(")) {
            return parseColourKeyword(val);
        } else {
            new TextScanner(val.substring(4));
            TextScanner scan = textScanner;
            scan.skipWhitespace();
            float red = scan.nextFloat();
            if (!Float.isNaN(red) && scan.consume('%')) {
                red = (red * 256.0f) / 100.0f;
            }
            float green = scan.checkedNextFloat(red);
            if (!Float.isNaN(green) && scan.consume('%')) {
                green = (green * 256.0f) / 100.0f;
            }
            float blue = scan.checkedNextFloat(green);
            if (!Float.isNaN(blue) && scan.consume('%')) {
                blue = (blue * 256.0f) / 100.0f;
            }
            scan.skipWhitespace();
            if (Float.isNaN(blue) || !scan.consume(')')) {
                Throwable th6 = th;
                new StringBuilder();
                new SAXException(sb.append("Bad rgb() colour value: ").append(val).toString());
                throw th6;
            }
            new SVG.Colour((clamp255(red) << 16) | (clamp255(green) << 8) | clamp255(blue));
            return colour;
        }
    }

    private static int clamp255(float f) {
        float val = f;
        return val < 0.0f ? 0 : val > 255.0f ? 255 : Math.round(val);
    }

    private static SVG.Colour parseColourKeyword(String str) throws SAXException {
        SVG.Colour colour;
        Throwable th;
        StringBuilder sb;
        String name = str;
        Integer col = ColourKeywords.get(name.toLowerCase(Locale.US));
        if (col == null) {
            Throwable th2 = th;
            new StringBuilder();
            new SAXException(sb.append("Invalid colour keyword: ").append(name).toString());
            throw th2;
        }
        new SVG.Colour(col.intValue());
        return colour;
    }

    private static void parseFont(SVG.Style style, String str) throws SAXException {
        StringBuilder sb;
        TextScanner textScanner;
        String item;
        Throwable th;
        Throwable th2;
        SVG.Style style2 = style;
        String val = str;
        Integer fontWeight = null;
        SVG.Style.FontStyle fontStyle = null;
        String fontVariant = null;
        new StringBuilder();
        if ("|caption|icon|menu|message-box|small-caption|status-bar|".indexOf(sb.append('|').append(val).append('|').toString()) == -1) {
            new TextScanner(val);
            TextScanner scan = textScanner;
            while (true) {
                item = scan.nextToken('/');
                scan.skipWhitespace();
                if (item != null) {
                    if (fontWeight != null && fontStyle != null) {
                        break;
                    } else if (!item.equals("normal")) {
                        if (fontWeight == null) {
                            fontWeight = FontWeightKeywords.get(item);
                            if (fontWeight != null) {
                                continue;
                            }
                        }
                        if (fontStyle == null) {
                            fontStyle = fontStyleKeyword(item);
                            if (fontStyle != null) {
                                continue;
                            }
                        }
                        if (fontVariant != null || !item.equals("small-caps")) {
                            break;
                        }
                        fontVariant = item;
                    }
                } else {
                    Throwable th3 = th;
                    new SAXException("Invalid font style attribute: missing font size and family");
                    throw th3;
                }
            }
            SVG.Length fontSize = parseFontSize(item);
            if (scan.consume('/')) {
                scan.skipWhitespace();
                String item2 = scan.nextToken();
                if (item2 == null) {
                    Throwable th4 = th2;
                    new SAXException("Invalid font style attribute: missing line-height");
                    throw th4;
                }
                SVG.Length parseLength = parseLength(item2);
                scan.skipWhitespace();
            }
            style2.fontFamily = parseFontFamily(scan.restOfText());
            style2.fontSize = fontSize;
            style2.fontWeight = Integer.valueOf(fontWeight == null ? SVG.Style.FONT_WEIGHT_NORMAL : fontWeight.intValue());
            style2.fontStyle = fontStyle == null ? SVG.Style.FontStyle.Normal : fontStyle;
            style2.specifiedFlags |= 122880;
        }
    }

    private static List<String> parseFontFamily(String val) throws SAXException {
        TextScanner textScanner;
        List<String> list;
        List<String> fonts = null;
        new TextScanner(val);
        TextScanner scan = textScanner;
        do {
            String item = scan.nextQuotedString();
            if (item == null) {
                item = scan.nextToken(',');
            }
            if (item == null) {
                break;
            }
            if (fonts == null) {
                new ArrayList();
                fonts = list;
            }
            boolean add = fonts.add(item);
            boolean skipCommaWhitespace = scan.skipCommaWhitespace();
        } while (!scan.empty());
        return fonts;
    }

    private static SVG.Length parseFontSize(String str) throws SAXException {
        String val = str;
        SVG.Length size = FontSizeKeywords.get(val);
        if (size == null) {
            size = parseLength(val);
        }
        return size;
    }

    private static Integer parseFontWeight(String str) throws SAXException {
        Throwable th;
        StringBuilder sb;
        String val = str;
        Integer wt = FontWeightKeywords.get(val);
        if (wt != null) {
            return wt;
        }
        Throwable th2 = th;
        new StringBuilder();
        new SAXException(sb.append("Invalid font-weight property: ").append(val).toString());
        throw th2;
    }

    private static SVG.Style.FontStyle parseFontStyle(String str) throws SAXException {
        Throwable th;
        StringBuilder sb;
        String val = str;
        SVG.Style.FontStyle fs = fontStyleKeyword(val);
        if (fs != null) {
            return fs;
        }
        Throwable th2 = th;
        new StringBuilder();
        new SAXException(sb.append("Invalid font-style property: ").append(val).toString());
        throw th2;
    }

    private static SVG.Style.FontStyle fontStyleKeyword(String str) {
        String val = str;
        if ("italic".equals(val)) {
            return SVG.Style.FontStyle.Italic;
        }
        if ("normal".equals(val)) {
            return SVG.Style.FontStyle.Normal;
        }
        if ("oblique".equals(val)) {
            return SVG.Style.FontStyle.Oblique;
        }
        return null;
    }

    private static SVG.Style.TextDecoration parseTextDecoration(String str) throws SAXException {
        Throwable th;
        StringBuilder sb;
        String val = str;
        if ("none".equals(val)) {
            return SVG.Style.TextDecoration.None;
        }
        if ("underline".equals(val)) {
            return SVG.Style.TextDecoration.Underline;
        }
        if ("overline".equals(val)) {
            return SVG.Style.TextDecoration.Overline;
        }
        if ("line-through".equals(val)) {
            return SVG.Style.TextDecoration.LineThrough;
        }
        if ("blink".equals(val)) {
            return SVG.Style.TextDecoration.Blink;
        }
        Throwable th2 = th;
        new StringBuilder();
        new SAXException(sb.append("Invalid text-decoration property: ").append(val).toString());
        throw th2;
    }

    private static SVG.Style.TextDirection parseTextDirection(String str) throws SAXException {
        Throwable th;
        StringBuilder sb;
        String val = str;
        if ("ltr".equals(val)) {
            return SVG.Style.TextDirection.LTR;
        }
        if ("rtl".equals(val)) {
            return SVG.Style.TextDirection.RTL;
        }
        Throwable th2 = th;
        new StringBuilder();
        new SAXException(sb.append("Invalid direction property: ").append(val).toString());
        throw th2;
    }

    private static SVG.Style.FillRule parseFillRule(String str) throws SAXException {
        Throwable th;
        StringBuilder sb;
        String val = str;
        if ("nonzero".equals(val)) {
            return SVG.Style.FillRule.NonZero;
        }
        if ("evenodd".equals(val)) {
            return SVG.Style.FillRule.EvenOdd;
        }
        Throwable th2 = th;
        new StringBuilder();
        new SAXException(sb.append("Invalid fill-rule property: ").append(val).toString());
        throw th2;
    }

    private static SVG.Style.LineCaps parseStrokeLineCap(String str) throws SAXException {
        Throwable th;
        StringBuilder sb;
        String val = str;
        if ("butt".equals(val)) {
            return SVG.Style.LineCaps.Butt;
        }
        if ("round".equals(val)) {
            return SVG.Style.LineCaps.Round;
        }
        if ("square".equals(val)) {
            return SVG.Style.LineCaps.Square;
        }
        Throwable th2 = th;
        new StringBuilder();
        new SAXException(sb.append("Invalid stroke-linecap property: ").append(val).toString());
        throw th2;
    }

    private static SVG.Style.LineJoin parseStrokeLineJoin(String str) throws SAXException {
        Throwable th;
        StringBuilder sb;
        String val = str;
        if ("miter".equals(val)) {
            return SVG.Style.LineJoin.Miter;
        }
        if ("round".equals(val)) {
            return SVG.Style.LineJoin.Round;
        }
        if ("bevel".equals(val)) {
            return SVG.Style.LineJoin.Bevel;
        }
        Throwable th2 = th;
        new StringBuilder();
        new SAXException(sb.append("Invalid stroke-linejoin property: ").append(val).toString());
        throw th2;
    }

    private static SVG.Length[] parseStrokeDashArray(String str) throws SAXException {
        TextScanner textScanner;
        List<SVG.Length> list;
        Throwable th;
        StringBuilder sb;
        Throwable th2;
        StringBuilder sb2;
        Throwable th3;
        StringBuilder sb3;
        String val = str;
        new TextScanner(val);
        TextScanner scan = textScanner;
        scan.skipWhitespace();
        if (scan.empty()) {
            return null;
        }
        SVG.Length dash = scan.nextLength();
        if (dash == null) {
            return null;
        }
        if (dash.isNegative()) {
            Throwable th4 = th3;
            new StringBuilder();
            new SAXException(sb3.append("Invalid stroke-dasharray. Dash segemnts cannot be negative: ").append(val).toString());
            throw th4;
        }
        float sum = dash.floatValue();
        new ArrayList<>();
        List<SVG.Length> dashes = list;
        boolean add = dashes.add(dash);
        while (!scan.empty()) {
            boolean skipCommaWhitespace = scan.skipCommaWhitespace();
            SVG.Length dash2 = scan.nextLength();
            if (dash2 == null) {
                Throwable th5 = th;
                new StringBuilder();
                new SAXException(sb.append("Invalid stroke-dasharray. Non-Length content found: ").append(val).toString());
                throw th5;
            } else if (dash2.isNegative()) {
                Throwable th6 = th2;
                new StringBuilder();
                new SAXException(sb2.append("Invalid stroke-dasharray. Dash segemnts cannot be negative: ").append(val).toString());
                throw th6;
            } else {
                boolean add2 = dashes.add(dash2);
                sum += dash2.floatValue();
            }
        }
        if (sum == 0.0f) {
            return null;
        }
        return (SVG.Length[]) dashes.toArray(new SVG.Length[dashes.size()]);
    }

    private static SVG.Style.TextAnchor parseTextAnchor(String str) throws SAXException {
        Throwable th;
        StringBuilder sb;
        String val = str;
        if ("start".equals(val)) {
            return SVG.Style.TextAnchor.Start;
        }
        if ("middle".equals(val)) {
            return SVG.Style.TextAnchor.Middle;
        }
        if ("end".equals(val)) {
            return SVG.Style.TextAnchor.End;
        }
        Throwable th2 = th;
        new StringBuilder();
        new SAXException(sb.append("Invalid text-anchor property: ").append(val).toString());
        throw th2;
    }

    private static Boolean parseOverflow(String str) throws SAXException {
        Throwable th;
        StringBuilder sb;
        String val = str;
        if ("visible".equals(val) || "auto".equals(val)) {
            return Boolean.TRUE;
        }
        if ("hidden".equals(val) || "scroll".equals(val)) {
            return Boolean.FALSE;
        }
        Throwable th2 = th;
        new StringBuilder();
        new SAXException(sb.append("Invalid toverflow property: ").append(val).toString());
        throw th2;
    }

    private static SVG.CSSClipRect parseClip(String str) throws SAXException {
        TextScanner textScanner;
        SVG.CSSClipRect cSSClipRect;
        Throwable th;
        StringBuilder sb;
        Throwable th2;
        String val = str;
        if ("auto".equals(val)) {
            return null;
        }
        if (!val.toLowerCase(Locale.US).startsWith("rect(")) {
            Throwable th3 = th2;
            new SAXException("Invalid clip attribute shape. Only rect() is supported.");
            throw th3;
        }
        new TextScanner(val.substring(5));
        TextScanner scan = textScanner;
        scan.skipWhitespace();
        SVG.Length top = parseLengthOrAuto(scan);
        boolean skipCommaWhitespace = scan.skipCommaWhitespace();
        SVG.Length right = parseLengthOrAuto(scan);
        boolean skipCommaWhitespace2 = scan.skipCommaWhitespace();
        SVG.Length bottom = parseLengthOrAuto(scan);
        boolean skipCommaWhitespace3 = scan.skipCommaWhitespace();
        SVG.Length left = parseLengthOrAuto(scan);
        scan.skipWhitespace();
        if (!scan.consume(')')) {
            Throwable th4 = th;
            new StringBuilder();
            new SAXException(sb.append("Bad rect() clip definition: ").append(val).toString());
            throw th4;
        }
        new SVG.CSSClipRect(top, right, bottom, left);
        return cSSClipRect;
    }

    private static SVG.Length parseLengthOrAuto(TextScanner textScanner) {
        SVG.Length length;
        TextScanner scan = textScanner;
        if (!scan.consume("auto")) {
            return scan.nextLength();
        }
        new SVG.Length(0.0f);
        return length;
    }

    private static SVG.Style.VectorEffect parseVectorEffect(String str) throws SAXException {
        Throwable th;
        StringBuilder sb;
        String val = str;
        if ("none".equals(val)) {
            return SVG.Style.VectorEffect.None;
        }
        if ("non-scaling-stroke".equals(val)) {
            return SVG.Style.VectorEffect.NonScalingStroke;
        }
        Throwable th2 = th;
        new StringBuilder();
        new SAXException(sb.append("Invalid vector-effect property: ").append(val).toString());
        throw th2;
    }

    /* JADX WARNING: CFG modification limit reached, blocks count: 211 */
    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* JADX WARNING: Removed duplicated region for block: B:105:0x014d A[SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:9:0x006c  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static com.caverock.androidsvg.SVG.PathDefinition parsePath(java.lang.String r32) throws org.xml.sax.SAXException {
        /*
            r2 = r32
            com.caverock.androidsvg.SVGParser$TextScanner r23 = new com.caverock.androidsvg.SVGParser$TextScanner
            r31 = r23
            r23 = r31
            r24 = r31
            r25 = r2
            r24.<init>(r25)
            r3 = r23
            r23 = 63
            r4 = r23
            r23 = 0
            r5 = r23
            r23 = 0
            r6 = r23
            r23 = 0
            r7 = r23
            r23 = 0
            r8 = r23
            r23 = 0
            r9 = r23
            r23 = 0
            r10 = r23
            com.caverock.androidsvg.SVG$PathDefinition r23 = new com.caverock.androidsvg.SVG$PathDefinition
            r31 = r23
            r23 = r31
            r24 = r31
            r24.<init>()
            r22 = r23
            r23 = r3
            boolean r23 = r23.empty()
            if (r23 == 0) goto L_0x0047
            r23 = r22
            r2 = r23
        L_0x0046:
            return r2
        L_0x0047:
            r23 = r3
            java.lang.Integer r23 = r23.nextChar()
            int r23 = r23.intValue()
            r4 = r23
            r23 = r4
            r24 = 77
            r0 = r23
            r1 = r24
            if (r0 == r1) goto L_0x0080
            r23 = r4
            r24 = 109(0x6d, float:1.53E-43)
            r0 = r23
            r1 = r24
            if (r0 == r1) goto L_0x0080
            r23 = r22
            r2 = r23
            goto L_0x0046
        L_0x006c:
            r23 = r3
            boolean r23 = r23.hasLetter()
            if (r23 == 0) goto L_0x0080
            r23 = r3
            java.lang.Integer r23 = r23.nextChar()
            int r23 = r23.intValue()
            r4 = r23
        L_0x0080:
            r23 = r3
            r23.skipWhitespace()
            r23 = r4
            switch(r23) {
                case 65: goto L_0x05c5;
                case 67: goto L_0x01e2;
                case 72: goto L_0x039d;
                case 76: goto L_0x0156;
                case 77: goto L_0x008f;
                case 81: goto L_0x0479;
                case 83: goto L_0x02b6;
                case 84: goto L_0x0525;
                case 86: goto L_0x040b;
                case 90: goto L_0x037e;
                case 97: goto L_0x05c5;
                case 99: goto L_0x01e2;
                case 104: goto L_0x039d;
                case 108: goto L_0x0156;
                case 109: goto L_0x008f;
                case 113: goto L_0x0479;
                case 115: goto L_0x02b6;
                case 116: goto L_0x0525;
                case 118: goto L_0x040b;
                case 122: goto L_0x037e;
                default: goto L_0x008a;
            }
        L_0x008a:
            r23 = r22
            r2 = r23
            goto L_0x0046
        L_0x008f:
            r23 = r3
            float r23 = r23.nextFloat()
            r11 = r23
            r23 = r3
            r24 = r11
            float r23 = r23.checkedNextFloat(r24)
            r12 = r23
            r23 = r12
            boolean r23 = java.lang.Float.isNaN(r23)
            if (r23 == 0) goto L_0x00de
            java.lang.String r23 = "SVGParser"
            java.lang.StringBuilder r24 = new java.lang.StringBuilder
            r31 = r24
            r24 = r31
            r25 = r31
            r25.<init>()
            java.lang.String r25 = "Bad path coords for "
            java.lang.StringBuilder r24 = r24.append(r25)
            r25 = r4
            r0 = r25
            char r0 = (char) r0
            r25 = r0
            java.lang.StringBuilder r24 = r24.append(r25)
            java.lang.String r25 = " path segment"
            java.lang.StringBuilder r24 = r24.append(r25)
            java.lang.String r24 = r24.toString()
            int r23 = android.util.Log.e(r23, r24)
            r23 = r22
            r2 = r23
            goto L_0x0046
        L_0x00de:
            r23 = r4
            r24 = 109(0x6d, float:1.53E-43)
            r0 = r23
            r1 = r24
            if (r0 != r1) goto L_0x0100
            r23 = r22
            boolean r23 = r23.isEmpty()
            if (r23 != 0) goto L_0x0100
            r23 = r11
            r24 = r5
            float r23 = r23 + r24
            r11 = r23
            r23 = r12
            r24 = r6
            float r23 = r23 + r24
            r12 = r23
        L_0x0100:
            r23 = r22
            r24 = r11
            r25 = r12
            r23.moveTo(r24, r25)
            r23 = r11
            r31 = r23
            r23 = r31
            r24 = r31
            r9 = r24
            r31 = r23
            r23 = r31
            r24 = r31
            r7 = r24
            r5 = r23
            r23 = r12
            r31 = r23
            r23 = r31
            r24 = r31
            r10 = r24
            r31 = r23
            r23 = r31
            r24 = r31
            r8 = r24
            r6 = r23
            r23 = r4
            r24 = 109(0x6d, float:1.53E-43)
            r0 = r23
            r1 = r24
            if (r0 != r1) goto L_0x0153
            r23 = 108(0x6c, float:1.51E-43)
        L_0x013d:
            r4 = r23
        L_0x013f:
            r23 = r3
            boolean r23 = r23.skipCommaWhitespace()
            r23 = r3
            boolean r23 = r23.empty()
            if (r23 == 0) goto L_0x006c
            r23 = r22
            r2 = r23
            goto L_0x0046
        L_0x0153:
            r23 = 76
            goto L_0x013d
        L_0x0156:
            r23 = r3
            float r23 = r23.nextFloat()
            r11 = r23
            r23 = r3
            r24 = r11
            float r23 = r23.checkedNextFloat(r24)
            r12 = r23
            r23 = r12
            boolean r23 = java.lang.Float.isNaN(r23)
            if (r23 == 0) goto L_0x01a5
            java.lang.String r23 = "SVGParser"
            java.lang.StringBuilder r24 = new java.lang.StringBuilder
            r31 = r24
            r24 = r31
            r25 = r31
            r25.<init>()
            java.lang.String r25 = "Bad path coords for "
            java.lang.StringBuilder r24 = r24.append(r25)
            r25 = r4
            r0 = r25
            char r0 = (char) r0
            r25 = r0
            java.lang.StringBuilder r24 = r24.append(r25)
            java.lang.String r25 = " path segment"
            java.lang.StringBuilder r24 = r24.append(r25)
            java.lang.String r24 = r24.toString()
            int r23 = android.util.Log.e(r23, r24)
            r23 = r22
            r2 = r23
            goto L_0x0046
        L_0x01a5:
            r23 = r4
            r24 = 108(0x6c, float:1.51E-43)
            r0 = r23
            r1 = r24
            if (r0 != r1) goto L_0x01bf
            r23 = r11
            r24 = r5
            float r23 = r23 + r24
            r11 = r23
            r23 = r12
            r24 = r6
            float r23 = r23 + r24
            r12 = r23
        L_0x01bf:
            r23 = r22
            r24 = r11
            r25 = r12
            r23.lineTo(r24, r25)
            r23 = r11
            r31 = r23
            r23 = r31
            r24 = r31
            r9 = r24
            r5 = r23
            r23 = r12
            r31 = r23
            r23 = r31
            r24 = r31
            r10 = r24
            r6 = r23
            goto L_0x013f
        L_0x01e2:
            r23 = r3
            float r23 = r23.nextFloat()
            r13 = r23
            r23 = r3
            r24 = r13
            float r23 = r23.checkedNextFloat(r24)
            r14 = r23
            r23 = r3
            r24 = r14
            float r23 = r23.checkedNextFloat(r24)
            r15 = r23
            r23 = r3
            r24 = r15
            float r23 = r23.checkedNextFloat(r24)
            r16 = r23
            r23 = r3
            r24 = r16
            float r23 = r23.checkedNextFloat(r24)
            r11 = r23
            r23 = r3
            r24 = r11
            float r23 = r23.checkedNextFloat(r24)
            r12 = r23
            r23 = r12
            boolean r23 = java.lang.Float.isNaN(r23)
            if (r23 == 0) goto L_0x0259
            java.lang.String r23 = "SVGParser"
            java.lang.StringBuilder r24 = new java.lang.StringBuilder
            r31 = r24
            r24 = r31
            r25 = r31
            r25.<init>()
            java.lang.String r25 = "Bad path coords for "
            java.lang.StringBuilder r24 = r24.append(r25)
            r25 = r4
            r0 = r25
            char r0 = (char) r0
            r25 = r0
            java.lang.StringBuilder r24 = r24.append(r25)
            java.lang.String r25 = " path segment"
            java.lang.StringBuilder r24 = r24.append(r25)
            java.lang.String r24 = r24.toString()
            int r23 = android.util.Log.e(r23, r24)
            r23 = r22
            r2 = r23
            goto L_0x0046
        L_0x0259:
            r23 = r4
            r24 = 99
            r0 = r23
            r1 = r24
            if (r0 != r1) goto L_0x0293
            r23 = r11
            r24 = r5
            float r23 = r23 + r24
            r11 = r23
            r23 = r12
            r24 = r6
            float r23 = r23 + r24
            r12 = r23
            r23 = r13
            r24 = r5
            float r23 = r23 + r24
            r13 = r23
            r23 = r14
            r24 = r6
            float r23 = r23 + r24
            r14 = r23
            r23 = r15
            r24 = r5
            float r23 = r23 + r24
            r15 = r23
            r23 = r16
            r24 = r6
            float r23 = r23 + r24
            r16 = r23
        L_0x0293:
            r23 = r22
            r24 = r13
            r25 = r14
            r26 = r15
            r27 = r16
            r28 = r11
            r29 = r12
            r23.cubicTo(r24, r25, r26, r27, r28, r29)
            r23 = r15
            r9 = r23
            r23 = r16
            r10 = r23
            r23 = r11
            r5 = r23
            r23 = r12
            r6 = r23
            goto L_0x013f
        L_0x02b6:
            r23 = 1073741824(0x40000000, float:2.0)
            r24 = r5
            float r23 = r23 * r24
            r24 = r9
            float r23 = r23 - r24
            r13 = r23
            r23 = 1073741824(0x40000000, float:2.0)
            r24 = r6
            float r23 = r23 * r24
            r24 = r10
            float r23 = r23 - r24
            r14 = r23
            r23 = r3
            float r23 = r23.nextFloat()
            r15 = r23
            r23 = r3
            r24 = r15
            float r23 = r23.checkedNextFloat(r24)
            r16 = r23
            r23 = r3
            r24 = r16
            float r23 = r23.checkedNextFloat(r24)
            r11 = r23
            r23 = r3
            r24 = r11
            float r23 = r23.checkedNextFloat(r24)
            r12 = r23
            r23 = r12
            boolean r23 = java.lang.Float.isNaN(r23)
            if (r23 == 0) goto L_0x0331
            java.lang.String r23 = "SVGParser"
            java.lang.StringBuilder r24 = new java.lang.StringBuilder
            r31 = r24
            r24 = r31
            r25 = r31
            r25.<init>()
            java.lang.String r25 = "Bad path coords for "
            java.lang.StringBuilder r24 = r24.append(r25)
            r25 = r4
            r0 = r25
            char r0 = (char) r0
            r25 = r0
            java.lang.StringBuilder r24 = r24.append(r25)
            java.lang.String r25 = " path segment"
            java.lang.StringBuilder r24 = r24.append(r25)
            java.lang.String r24 = r24.toString()
            int r23 = android.util.Log.e(r23, r24)
            r23 = r22
            r2 = r23
            goto L_0x0046
        L_0x0331:
            r23 = r4
            r24 = 115(0x73, float:1.61E-43)
            r0 = r23
            r1 = r24
            if (r0 != r1) goto L_0x035b
            r23 = r11
            r24 = r5
            float r23 = r23 + r24
            r11 = r23
            r23 = r12
            r24 = r6
            float r23 = r23 + r24
            r12 = r23
            r23 = r15
            r24 = r5
            float r23 = r23 + r24
            r15 = r23
            r23 = r16
            r24 = r6
            float r23 = r23 + r24
            r16 = r23
        L_0x035b:
            r23 = r22
            r24 = r13
            r25 = r14
            r26 = r15
            r27 = r16
            r28 = r11
            r29 = r12
            r23.cubicTo(r24, r25, r26, r27, r28, r29)
            r23 = r15
            r9 = r23
            r23 = r16
            r10 = r23
            r23 = r11
            r5 = r23
            r23 = r12
            r6 = r23
            goto L_0x013f
        L_0x037e:
            r23 = r22
            r23.close()
            r23 = r7
            r31 = r23
            r23 = r31
            r24 = r31
            r9 = r24
            r5 = r23
            r23 = r8
            r31 = r23
            r23 = r31
            r24 = r31
            r10 = r24
            r6 = r23
            goto L_0x013f
        L_0x039d:
            r23 = r3
            float r23 = r23.nextFloat()
            r11 = r23
            r23 = r11
            boolean r23 = java.lang.Float.isNaN(r23)
            if (r23 == 0) goto L_0x03e2
            java.lang.String r23 = "SVGParser"
            java.lang.StringBuilder r24 = new java.lang.StringBuilder
            r31 = r24
            r24 = r31
            r25 = r31
            r25.<init>()
            java.lang.String r25 = "Bad path coords for "
            java.lang.StringBuilder r24 = r24.append(r25)
            r25 = r4
            r0 = r25
            char r0 = (char) r0
            r25 = r0
            java.lang.StringBuilder r24 = r24.append(r25)
            java.lang.String r25 = " path segment"
            java.lang.StringBuilder r24 = r24.append(r25)
            java.lang.String r24 = r24.toString()
            int r23 = android.util.Log.e(r23, r24)
            r23 = r22
            r2 = r23
            goto L_0x0046
        L_0x03e2:
            r23 = r4
            r24 = 104(0x68, float:1.46E-43)
            r0 = r23
            r1 = r24
            if (r0 != r1) goto L_0x03f4
            r23 = r11
            r24 = r5
            float r23 = r23 + r24
            r11 = r23
        L_0x03f4:
            r23 = r22
            r24 = r11
            r25 = r6
            r23.lineTo(r24, r25)
            r23 = r11
            r31 = r23
            r23 = r31
            r24 = r31
            r9 = r24
            r5 = r23
            goto L_0x013f
        L_0x040b:
            r23 = r3
            float r23 = r23.nextFloat()
            r12 = r23
            r23 = r12
            boolean r23 = java.lang.Float.isNaN(r23)
            if (r23 == 0) goto L_0x0450
            java.lang.String r23 = "SVGParser"
            java.lang.StringBuilder r24 = new java.lang.StringBuilder
            r31 = r24
            r24 = r31
            r25 = r31
            r25.<init>()
            java.lang.String r25 = "Bad path coords for "
            java.lang.StringBuilder r24 = r24.append(r25)
            r25 = r4
            r0 = r25
            char r0 = (char) r0
            r25 = r0
            java.lang.StringBuilder r24 = r24.append(r25)
            java.lang.String r25 = " path segment"
            java.lang.StringBuilder r24 = r24.append(r25)
            java.lang.String r24 = r24.toString()
            int r23 = android.util.Log.e(r23, r24)
            r23 = r22
            r2 = r23
            goto L_0x0046
        L_0x0450:
            r23 = r4
            r24 = 118(0x76, float:1.65E-43)
            r0 = r23
            r1 = r24
            if (r0 != r1) goto L_0x0462
            r23 = r12
            r24 = r6
            float r23 = r23 + r24
            r12 = r23
        L_0x0462:
            r23 = r22
            r24 = r5
            r25 = r12
            r23.lineTo(r24, r25)
            r23 = r12
            r31 = r23
            r23 = r31
            r24 = r31
            r10 = r24
            r6 = r23
            goto L_0x013f
        L_0x0479:
            r23 = r3
            float r23 = r23.nextFloat()
            r13 = r23
            r23 = r3
            r24 = r13
            float r23 = r23.checkedNextFloat(r24)
            r14 = r23
            r23 = r3
            r24 = r14
            float r23 = r23.checkedNextFloat(r24)
            r11 = r23
            r23 = r3
            r24 = r11
            float r23 = r23.checkedNextFloat(r24)
            r12 = r23
            r23 = r12
            boolean r23 = java.lang.Float.isNaN(r23)
            if (r23 == 0) goto L_0x04dc
            java.lang.String r23 = "SVGParser"
            java.lang.StringBuilder r24 = new java.lang.StringBuilder
            r31 = r24
            r24 = r31
            r25 = r31
            r25.<init>()
            java.lang.String r25 = "Bad path coords for "
            java.lang.StringBuilder r24 = r24.append(r25)
            r25 = r4
            r0 = r25
            char r0 = (char) r0
            r25 = r0
            java.lang.StringBuilder r24 = r24.append(r25)
            java.lang.String r25 = " path segment"
            java.lang.StringBuilder r24 = r24.append(r25)
            java.lang.String r24 = r24.toString()
            int r23 = android.util.Log.e(r23, r24)
            r23 = r22
            r2 = r23
            goto L_0x0046
        L_0x04dc:
            r23 = r4
            r24 = 113(0x71, float:1.58E-43)
            r0 = r23
            r1 = r24
            if (r0 != r1) goto L_0x0506
            r23 = r11
            r24 = r5
            float r23 = r23 + r24
            r11 = r23
            r23 = r12
            r24 = r6
            float r23 = r23 + r24
            r12 = r23
            r23 = r13
            r24 = r5
            float r23 = r23 + r24
            r13 = r23
            r23 = r14
            r24 = r6
            float r23 = r23 + r24
            r14 = r23
        L_0x0506:
            r23 = r22
            r24 = r13
            r25 = r14
            r26 = r11
            r27 = r12
            r23.quadTo(r24, r25, r26, r27)
            r23 = r13
            r9 = r23
            r23 = r14
            r10 = r23
            r23 = r11
            r5 = r23
            r23 = r12
            r6 = r23
            goto L_0x013f
        L_0x0525:
            r23 = 1073741824(0x40000000, float:2.0)
            r24 = r5
            float r23 = r23 * r24
            r24 = r9
            float r23 = r23 - r24
            r13 = r23
            r23 = 1073741824(0x40000000, float:2.0)
            r24 = r6
            float r23 = r23 * r24
            r24 = r10
            float r23 = r23 - r24
            r14 = r23
            r23 = r3
            float r23 = r23.nextFloat()
            r11 = r23
            r23 = r3
            r24 = r11
            float r23 = r23.checkedNextFloat(r24)
            r12 = r23
            r23 = r12
            boolean r23 = java.lang.Float.isNaN(r23)
            if (r23 == 0) goto L_0x058c
            java.lang.String r23 = "SVGParser"
            java.lang.StringBuilder r24 = new java.lang.StringBuilder
            r31 = r24
            r24 = r31
            r25 = r31
            r25.<init>()
            java.lang.String r25 = "Bad path coords for "
            java.lang.StringBuilder r24 = r24.append(r25)
            r25 = r4
            r0 = r25
            char r0 = (char) r0
            r25 = r0
            java.lang.StringBuilder r24 = r24.append(r25)
            java.lang.String r25 = " path segment"
            java.lang.StringBuilder r24 = r24.append(r25)
            java.lang.String r24 = r24.toString()
            int r23 = android.util.Log.e(r23, r24)
            r23 = r22
            r2 = r23
            goto L_0x0046
        L_0x058c:
            r23 = r4
            r24 = 116(0x74, float:1.63E-43)
            r0 = r23
            r1 = r24
            if (r0 != r1) goto L_0x05a6
            r23 = r11
            r24 = r5
            float r23 = r23 + r24
            r11 = r23
            r23 = r12
            r24 = r6
            float r23 = r23 + r24
            r12 = r23
        L_0x05a6:
            r23 = r22
            r24 = r13
            r25 = r14
            r26 = r11
            r27 = r12
            r23.quadTo(r24, r25, r26, r27)
            r23 = r13
            r9 = r23
            r23 = r14
            r10 = r23
            r23 = r11
            r5 = r23
            r23 = r12
            r6 = r23
            goto L_0x013f
        L_0x05c5:
            r23 = r3
            float r23 = r23.nextFloat()
            r17 = r23
            r23 = r3
            r24 = r17
            float r23 = r23.checkedNextFloat(r24)
            r18 = r23
            r23 = r3
            r24 = r18
            float r23 = r23.checkedNextFloat(r24)
            r19 = r23
            r23 = r3
            r24 = r19
            java.lang.Float r24 = java.lang.Float.valueOf(r24)
            java.lang.Boolean r23 = r23.checkedNextFlag(r24)
            r20 = r23
            r23 = r3
            r24 = r20
            java.lang.Boolean r23 = r23.checkedNextFlag(r24)
            r21 = r23
            r23 = r21
            if (r23 != 0) goto L_0x0656
            r23 = 2143289344(0x7fc00000, float:NaN)
            r31 = r23
            r23 = r31
            r24 = r31
            r12 = r24
            r11 = r23
        L_0x0609:
            r23 = r12
            boolean r23 = java.lang.Float.isNaN(r23)
            if (r23 != 0) goto L_0x0621
            r23 = r17
            r24 = 0
            int r23 = (r23 > r24 ? 1 : (r23 == r24 ? 0 : -1))
            if (r23 < 0) goto L_0x0621
            r23 = r18
            r24 = 0
            int r23 = (r23 > r24 ? 1 : (r23 == r24 ? 0 : -1))
            if (r23 >= 0) goto L_0x0669
        L_0x0621:
            java.lang.String r23 = "SVGParser"
            java.lang.StringBuilder r24 = new java.lang.StringBuilder
            r31 = r24
            r24 = r31
            r25 = r31
            r25.<init>()
            java.lang.String r25 = "Bad path coords for "
            java.lang.StringBuilder r24 = r24.append(r25)
            r25 = r4
            r0 = r25
            char r0 = (char) r0
            r25 = r0
            java.lang.StringBuilder r24 = r24.append(r25)
            java.lang.String r25 = " path segment"
            java.lang.StringBuilder r24 = r24.append(r25)
            java.lang.String r24 = r24.toString()
            int r23 = android.util.Log.e(r23, r24)
            r23 = r22
            r2 = r23
            goto L_0x0046
        L_0x0656:
            r23 = r3
            float r23 = r23.possibleNextFloat()
            r11 = r23
            r23 = r3
            r24 = r11
            float r23 = r23.checkedNextFloat(r24)
            r12 = r23
            goto L_0x0609
        L_0x0669:
            r23 = r4
            r24 = 97
            r0 = r23
            r1 = r24
            if (r0 != r1) goto L_0x0683
            r23 = r11
            r24 = r5
            float r23 = r23 + r24
            r11 = r23
            r23 = r12
            r24 = r6
            float r23 = r23 + r24
            r12 = r23
        L_0x0683:
            r23 = r22
            r24 = r17
            r25 = r18
            r26 = r19
            r27 = r20
            boolean r27 = r27.booleanValue()
            r28 = r21
            boolean r28 = r28.booleanValue()
            r29 = r11
            r30 = r12
            r23.arcTo(r24, r25, r26, r27, r28, r29, r30)
            r23 = r11
            r31 = r23
            r23 = r31
            r24 = r31
            r9 = r24
            r5 = r23
            r23 = r12
            r31 = r23
            r23 = r31
            r24 = r31
            r10 = r24
            r6 = r23
            goto L_0x013f
        */
        throw new UnsupportedOperationException("Method not decompiled: com.caverock.androidsvg.SVGParser.parsePath(java.lang.String):com.caverock.androidsvg.SVG$PathDefinition");
    }

    private static Set<String> parseRequiredFeatures(String val) throws SAXException {
        TextScanner textScanner;
        HashSet hashSet;
        new TextScanner(val);
        TextScanner scan = textScanner;
        new HashSet();
        HashSet hashSet2 = hashSet;
        while (!scan.empty()) {
            String feature = scan.nextToken();
            if (feature.startsWith(FEATURE_STRING_PREFIX)) {
                boolean add = hashSet2.add(feature.substring(FEATURE_STRING_PREFIX.length()));
            } else {
                boolean add2 = hashSet2.add("UNSUPPORTED");
            }
            scan.skipWhitespace();
        }
        return hashSet2;
    }

    private static Set<String> parseSystemLanguage(String val) throws SAXException {
        TextScanner textScanner;
        HashSet hashSet;
        Locale locale;
        new TextScanner(val);
        TextScanner scan = textScanner;
        new HashSet();
        HashSet hashSet2 = hashSet;
        while (!scan.empty()) {
            String language = scan.nextToken();
            int hyphenPos = language.indexOf(45);
            if (hyphenPos != -1) {
                language = language.substring(0, hyphenPos);
            }
            new Locale(language, "", "");
            boolean add = hashSet2.add(locale.getLanguage());
            scan.skipWhitespace();
        }
        return hashSet2;
    }

    private static Set<String> parseRequiredFormats(String val) throws SAXException {
        TextScanner textScanner;
        HashSet hashSet;
        new TextScanner(val);
        TextScanner scan = textScanner;
        new HashSet();
        HashSet hashSet2 = hashSet;
        while (!scan.empty()) {
            boolean add = hashSet2.add(scan.nextToken());
            scan.skipWhitespace();
        }
        return hashSet2;
    }

    private static String parseFunctionalIRI(String str, String str2) throws SAXException {
        Throwable th;
        StringBuilder sb;
        String val = str;
        String attrName = str2;
        if (val.equals("none")) {
            return null;
        }
        if (val.startsWith("url(") && val.endsWith(")")) {
            return val.substring(4, val.length() - 1).trim();
        }
        Throwable th2 = th;
        new StringBuilder();
        new SAXException(sb.append("Bad ").append(attrName).append(" attribute. Expected \"none\" or \"url()\" format").toString());
        throw th2;
    }

    private void style(Attributes attributes) throws SAXException {
        Throwable th;
        Attributes attributes2 = attributes;
        debug("<style>", new Object[0]);
        if (this.currentElement == null) {
            Throwable th2 = th;
            new SAXException("Invalid document. Root element must be <svg>");
            throw th2;
        }
        boolean isTextCSS = true;
        String media = "all";
        for (int i = 0; i < attributes2.getLength(); i++) {
            String val = attributes2.getValue(i).trim();
            switch (SVGAttr.fromString(attributes2.getLocalName(i))) {
                case type:
                    isTextCSS = val.equals("text/css");
                    break;
                case media:
                    media = val;
                    break;
            }
        }
        if (!isTextCSS || !CSSParser.mediaMatches(media, CSSParser.MediaType.screen)) {
            this.ignoring = true;
            this.ignoreDepth = 1;
            return;
        }
        this.inStyleElement = true;
    }

    private void parseCSSStyleSheet(String sheet) throws SAXException {
        CSSParser cssp;
        new CSSParser(CSSParser.MediaType.screen);
        this.svgDocument.addCSSRules(cssp.parse(sheet));
    }
}
