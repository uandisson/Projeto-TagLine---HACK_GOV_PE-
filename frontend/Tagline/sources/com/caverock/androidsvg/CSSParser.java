package com.caverock.androidsvg;

import android.util.Log;
import com.caverock.androidsvg.SVG;
import com.caverock.androidsvg.SVGParser;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import org.locationtech.jts.geom.Dimension;
import org.slf4j.Marker;
import org.xml.sax.SAXException;

public class CSSParser {
    private static final String CLASS = "class";

    /* renamed from: ID */
    private static final String f311ID = "id";
    private static final String TAG = "AndroidSVG CSSParser";
    private boolean inMediaRule = false;
    private MediaType rendererMediaType = null;

    private enum AttribOp {
    }

    private enum Combinator {
    }

    public enum MediaType {
    }

    public static class Attrib {
        public String name = null;
        public AttribOp operation;
        public String value = null;

        public Attrib(String name2, AttribOp op, String value2) {
            this.name = name2;
            this.operation = op;
            this.value = value2;
        }
    }

    private static class SimpleSelector {
        public List<Attrib> attribs = null;
        public Combinator combinator = null;
        public List<String> pseudos = null;
        public String tag = null;

        public SimpleSelector(Combinator combinator2, String str) {
            Combinator combinator3 = combinator2;
            String tag2 = str;
            this.combinator = combinator3 != null ? combinator3 : Combinator.DESCENDANT;
            this.tag = tag2;
        }

        public void addAttrib(String str, AttribOp attribOp, String str2) {
            Object obj;
            List<Attrib> list;
            String attrName = str;
            AttribOp op = attribOp;
            String attrValue = str2;
            if (this.attribs == null) {
                new ArrayList();
                this.attribs = list;
            }
            new Attrib(attrName, op, attrValue);
            boolean add = this.attribs.add(obj);
        }

        public void addPseudo(String str) {
            List<String> list;
            String pseudo = str;
            if (this.pseudos == null) {
                new ArrayList();
                this.pseudos = list;
            }
            boolean add = this.pseudos.add(pseudo);
        }

        public String toString() {
            StringBuilder sb;
            new StringBuilder();
            StringBuilder sb2 = sb;
            if (this.combinator == Combinator.CHILD) {
                StringBuilder append = sb2.append("> ");
            } else if (this.combinator == Combinator.FOLLOWS) {
                StringBuilder append2 = sb2.append("+ ");
            }
            StringBuilder append3 = sb2.append(this.tag == null ? Marker.ANY_MARKER : this.tag);
            if (this.attribs != null) {
                for (Attrib attr : this.attribs) {
                    StringBuilder append4 = sb2.append('[').append(attr.name);
                    switch (attr.operation) {
                        case EQUALS:
                            StringBuilder append5 = sb2.append('=').append(attr.value);
                            break;
                        case INCLUDES:
                            StringBuilder append6 = sb2.append("~=").append(attr.value);
                            break;
                        case DASHMATCH:
                            StringBuilder append7 = sb2.append("|=").append(attr.value);
                            break;
                    }
                    StringBuilder append8 = sb2.append(']');
                }
            }
            if (this.pseudos != null) {
                for (String pseu : this.pseudos) {
                    StringBuilder append9 = sb2.append(':').append(pseu);
                }
            }
            return sb2.toString();
        }
    }

    public static class Ruleset {
        private List<Rule> rules = null;

        public Ruleset() {
        }

        public void add(Rule rule) {
            List<Rule> list;
            Rule rule2 = rule;
            if (this.rules == null) {
                new ArrayList();
                this.rules = list;
            }
            for (int i = 0; i < this.rules.size(); i++) {
                if (this.rules.get(i).selector.specificity > rule2.selector.specificity) {
                    this.rules.add(i, rule2);
                    return;
                }
            }
            boolean add = this.rules.add(rule2);
        }

        public void addAll(Ruleset ruleset) {
            List<Rule> list;
            Ruleset rules2 = ruleset;
            if (rules2.rules != null) {
                if (this.rules == null) {
                    new ArrayList(rules2.rules.size());
                    this.rules = list;
                }
                for (Rule rule : rules2.rules) {
                    boolean add = this.rules.add(rule);
                }
            }
        }

        public List<Rule> getRules() {
            return this.rules;
        }

        public boolean isEmpty() {
            return this.rules == null || this.rules.isEmpty();
        }

        public String toString() {
            StringBuilder sb;
            if (this.rules == null) {
                return "";
            }
            new StringBuilder();
            StringBuilder sb2 = sb;
            for (Rule rule : this.rules) {
                StringBuilder append = sb2.append(rule.toString()).append(10);
            }
            return sb2.toString();
        }
    }

    public static class Rule {
        public Selector selector = null;
        public SVG.Style style = null;

        public Rule(Selector selector2, SVG.Style style2) {
            this.selector = selector2;
            this.style = style2;
        }

        public String toString() {
            StringBuilder sb;
            new StringBuilder();
            return sb.append(this.selector).append(" {}").toString();
        }
    }

    public static class Selector {
        public List<SimpleSelector> selector = null;
        public int specificity = 0;

        public Selector() {
        }

        public void add(SimpleSelector simpleSelector) {
            List<SimpleSelector> list;
            SimpleSelector part = simpleSelector;
            if (this.selector == null) {
                new ArrayList();
                this.selector = list;
            }
            boolean add = this.selector.add(part);
        }

        public int size() {
            return this.selector == null ? 0 : this.selector.size();
        }

        public SimpleSelector get(int i) {
            return this.selector.get(i);
        }

        public boolean isEmpty() {
            return this.selector == null ? true : this.selector.isEmpty();
        }

        public void addedIdAttribute() {
            this.specificity += 10000;
        }

        public void addedAttributeOrPseudo() {
            this.specificity += 100;
        }

        public void addedElement() {
            this.specificity++;
        }

        public String toString() {
            StringBuilder sb;
            new StringBuilder();
            StringBuilder sb2 = sb;
            for (SimpleSelector sel : this.selector) {
                StringBuilder append = sb2.append(sel).append(' ');
            }
            return sb2.append('(').append(this.specificity).append(')').toString();
        }
    }

    public CSSParser(MediaType rendererMediaType2) {
        this.rendererMediaType = rendererMediaType2;
    }

    public Ruleset parse(String sheet) throws SAXException {
        CSSTextScanner cSSTextScanner;
        new CSSTextScanner(sheet);
        CSSTextScanner scan = cSSTextScanner;
        scan.skipWhitespace();
        return parseRuleset(scan);
    }

    public static boolean mediaMatches(String mediaListStr, MediaType mediaType) throws SAXException {
        CSSTextScanner cSSTextScanner;
        Throwable th;
        MediaType rendererMediaType2 = mediaType;
        new CSSTextScanner(mediaListStr);
        CSSTextScanner scan = cSSTextScanner;
        scan.skipWhitespace();
        List<MediaType> mediaList = parseMediaList(scan);
        if (scan.empty()) {
            return mediaMatches(mediaList, rendererMediaType2);
        }
        Throwable th2 = th;
        new SAXException("Invalid @media type list");
        throw th2;
    }

    private static void warn(String format, Object... args) {
        int w = Log.w(TAG, String.format(format, args));
    }

    private static class CSSTextScanner extends SVGParser.TextScanner {
        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        public CSSTextScanner(String input) {
            super(input.replaceAll("(?s)/\\*.*?\\*/", ""));
        }

        public String nextIdentifier() {
            int end = scanForIdentifier();
            if (end == this.position) {
                return null;
            }
            String result = this.input.substring(this.position, end);
            this.position = end;
            return result;
        }

        /* JADX WARNING: Multi-variable type inference failed */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        private int scanForIdentifier() {
            /*
                r6 = this;
                r0 = r6
                r4 = r0
                boolean r4 = r4.empty()
                if (r4 == 0) goto L_0x000d
                r4 = r0
                int r4 = r4.position
                r0 = r4
            L_0x000c:
                return r0
            L_0x000d:
                r4 = r0
                int r4 = r4.position
                r1 = r4
                r4 = r0
                int r4 = r4.position
                r2 = r4
                r4 = r0
                java.lang.String r4 = r4.input
                r5 = r0
                int r5 = r5.position
                char r4 = r4.charAt(r5)
                r3 = r4
                r4 = r3
                r5 = 45
                if (r4 != r5) goto L_0x002b
                r4 = r0
                int r4 = r4.advanceChar()
                r3 = r4
            L_0x002b:
                r4 = r3
                r5 = 65
                if (r4 < r5) goto L_0x0035
                r4 = r3
                r5 = 90
                if (r4 <= r5) goto L_0x0044
            L_0x0035:
                r4 = r3
                r5 = 97
                if (r4 < r5) goto L_0x003f
                r4 = r3
                r5 = 122(0x7a, float:1.71E-43)
                if (r4 <= r5) goto L_0x0044
            L_0x003f:
                r4 = r3
                r5 = 95
                if (r4 != r5) goto L_0x007d
            L_0x0044:
                r4 = r0
                int r4 = r4.advanceChar()
                r3 = r4
            L_0x004a:
                r4 = r3
                r5 = 65
                if (r4 < r5) goto L_0x0054
                r4 = r3
                r5 = 90
                if (r4 <= r5) goto L_0x0072
            L_0x0054:
                r4 = r3
                r5 = 97
                if (r4 < r5) goto L_0x005e
                r4 = r3
                r5 = 122(0x7a, float:1.71E-43)
                if (r4 <= r5) goto L_0x0072
            L_0x005e:
                r4 = r3
                r5 = 48
                if (r4 < r5) goto L_0x0068
                r4 = r3
                r5 = 57
                if (r4 <= r5) goto L_0x0072
            L_0x0068:
                r4 = r3
                r5 = 45
                if (r4 == r5) goto L_0x0072
                r4 = r3
                r5 = 95
                if (r4 != r5) goto L_0x0079
            L_0x0072:
                r4 = r0
                int r4 = r4.advanceChar()
                r3 = r4
                goto L_0x004a
            L_0x0079:
                r4 = r0
                int r4 = r4.position
                r2 = r4
            L_0x007d:
                r4 = r0
                r5 = r1
                r4.position = r5
                r4 = r2
                r0 = r4
                goto L_0x000c
            */
            throw new UnsupportedOperationException("Method not decompiled: com.caverock.androidsvg.CSSParser.CSSTextScanner.scanForIdentifier():int");
        }

        public boolean nextSimpleSelector(Selector selector) throws SAXException {
            Throwable th;
            Throwable th2;
            Throwable th3;
            Throwable th4;
            SimpleSelector simpleSelector;
            Throwable th5;
            SimpleSelector simpleSelector2;
            SimpleSelector simpleSelector3;
            SimpleSelector simpleSelector4;
            Selector selector2 = selector;
            if (empty()) {
                return false;
            }
            int start = this.position;
            Combinator combinator = null;
            SimpleSelector selectorPart = null;
            if (!selector2.isEmpty()) {
                if (consume('>')) {
                    combinator = Combinator.CHILD;
                    skipWhitespace();
                } else if (consume('+')) {
                    combinator = Combinator.FOLLOWS;
                    skipWhitespace();
                }
            }
            if (consume((char) Dimension.SYM_DONTCARE)) {
                new SimpleSelector(combinator, (String) null);
                selectorPart = simpleSelector4;
            } else {
                String tag = nextIdentifier();
                if (tag != null) {
                    new SimpleSelector(combinator, tag);
                    selectorPart = simpleSelector3;
                    selector2.addedElement();
                }
            }
            while (true) {
                if (!empty()) {
                    if (!consume('.')) {
                        if (consume('#')) {
                            if (selectorPart == null) {
                                new SimpleSelector(combinator, (String) null);
                                selectorPart = simpleSelector;
                            }
                            String value = nextIdentifier();
                            if (value == null) {
                                Throwable th6 = th4;
                                new SAXException("Invalid \"#id\" selector in <style> element");
                                throw th6;
                            }
                            selectorPart.addAttrib("id", AttribOp.EQUALS, value);
                            selector2.addedIdAttribute();
                        }
                        if (selectorPart == null) {
                            break;
                        } else if (consume('[')) {
                            skipWhitespace();
                            String attrName = nextIdentifier();
                            String attrValue = null;
                            if (attrName == null) {
                                Throwable th7 = th;
                                new SAXException("Invalid attribute selector in <style> element");
                                throw th7;
                            }
                            skipWhitespace();
                            AttribOp op = null;
                            if (consume('=')) {
                                op = AttribOp.EQUALS;
                            } else if (consume("~=")) {
                                op = AttribOp.INCLUDES;
                            } else if (consume("|=")) {
                                op = AttribOp.DASHMATCH;
                            }
                            if (op != null) {
                                skipWhitespace();
                                attrValue = nextAttribValue();
                                if (attrValue == null) {
                                    Throwable th8 = th3;
                                    new SAXException("Invalid attribute selector in <style> element");
                                    throw th8;
                                }
                                skipWhitespace();
                            }
                            if (!consume(']')) {
                                Throwable th9 = th2;
                                new SAXException("Invalid attribute selector in <style> element");
                                throw th9;
                            }
                            selectorPart.addAttrib(attrName, op == null ? AttribOp.EXISTS : op, attrValue);
                            selector2.addedAttributeOrPseudo();
                        } else if (consume(':')) {
                            int pseudoStart = this.position;
                            if (nextIdentifier() != null) {
                                if (consume('(')) {
                                    skipWhitespace();
                                    if (nextIdentifier() != null) {
                                        skipWhitespace();
                                        if (!consume(')')) {
                                            this.position = pseudoStart - 1;
                                        }
                                    }
                                }
                                selectorPart.addPseudo(this.input.substring(pseudoStart, this.position));
                                selector2.addedAttributeOrPseudo();
                            }
                        }
                    } else {
                        if (selectorPart == null) {
                            new SimpleSelector(combinator, (String) null);
                            selectorPart = simpleSelector2;
                        }
                        String value2 = nextIdentifier();
                        if (value2 == null) {
                            Throwable th10 = th5;
                            new SAXException("Invalid \".class\" selector in <style> element");
                            throw th10;
                        }
                        selectorPart.addAttrib(CSSParser.CLASS, AttribOp.EQUALS, value2);
                        selector2.addedAttributeOrPseudo();
                    }
                } else {
                    break;
                }
            }
            if (selectorPart != null) {
                selector2.add(selectorPart);
                return true;
            }
            this.position = start;
            return false;
        }

        private String nextAttribValue() {
            if (empty()) {
                return null;
            }
            String result = nextQuotedString();
            if (result != null) {
                return result;
            }
            return nextIdentifier();
        }

        public String nextPropertyValue() {
            if (empty()) {
                return null;
            }
            int start = this.position;
            int lastValidPos = this.position;
            int charAt = this.input.charAt(this.position);
            while (true) {
                int ch = charAt;
                if (ch != -1 && ch != 59 && ch != 125 && ch != 33 && !isEOL(ch)) {
                    if (!isWhitespace(ch)) {
                        lastValidPos = this.position + 1;
                    }
                    charAt = advanceChar();
                }
            }
            if (this.position > start) {
                return this.input.substring(start, lastValidPos);
            }
            this.position = start;
            return null;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:3:0x000f  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static boolean mediaMatches(java.util.List<com.caverock.androidsvg.CSSParser.MediaType> r6, com.caverock.androidsvg.CSSParser.MediaType r7) {
        /*
            r0 = r6
            r1 = r7
            r4 = r0
            java.util.Iterator r4 = r4.iterator()
            r2 = r4
        L_0x0008:
            r4 = r2
            boolean r4 = r4.hasNext()
            if (r4 == 0) goto L_0x0024
            r4 = r2
            java.lang.Object r4 = r4.next()
            com.caverock.androidsvg.CSSParser$MediaType r4 = (com.caverock.androidsvg.CSSParser.MediaType) r4
            r3 = r4
            r4 = r3
            com.caverock.androidsvg.CSSParser$MediaType r5 = com.caverock.androidsvg.CSSParser.MediaType.all
            if (r4 == r5) goto L_0x0020
            r4 = r3
            r5 = r1
            if (r4 != r5) goto L_0x0023
        L_0x0020:
            r4 = 1
            r0 = r4
        L_0x0022:
            return r0
        L_0x0023:
            goto L_0x0008
        L_0x0024:
            r4 = 0
            r0 = r4
            goto L_0x0022
        */
        throw new UnsupportedOperationException("Method not decompiled: com.caverock.androidsvg.CSSParser.mediaMatches(java.util.List, com.caverock.androidsvg.CSSParser$MediaType):boolean");
    }

    private static List<MediaType> parseMediaList(CSSTextScanner cSSTextScanner) throws SAXException {
        ArrayList arrayList;
        Throwable th;
        CSSTextScanner scan = cSSTextScanner;
        new ArrayList();
        ArrayList arrayList2 = arrayList;
        while (!scan.empty()) {
            try {
                boolean add = arrayList2.add(MediaType.valueOf(scan.nextToken(',')));
                if (!scan.skipCommaWhitespace()) {
                    break;
                }
            } catch (IllegalArgumentException e) {
                IllegalArgumentException illegalArgumentException = e;
                Throwable th2 = th;
                new SAXException("Invalid @media type list");
                throw th2;
            }
        }
        return arrayList2;
    }

    private void parseAtRule(Ruleset ruleset, CSSTextScanner cSSTextScanner) throws SAXException {
        Throwable th;
        Throwable th2;
        Throwable th3;
        Ruleset ruleset2 = ruleset;
        CSSTextScanner scan = cSSTextScanner;
        String atKeyword = scan.nextIdentifier();
        scan.skipWhitespace();
        if (atKeyword == null) {
            Throwable th4 = th3;
            new SAXException("Invalid '@' rule in <style> element");
            throw th4;
        }
        if (this.inMediaRule || !atKeyword.equals("media")) {
            warn("Ignoring @%s rule", atKeyword);
            skipAtRule(scan);
        } else {
            List<MediaType> mediaList = parseMediaList(scan);
            if (!scan.consume('{')) {
                Throwable th5 = th2;
                new SAXException("Invalid @media rule: missing rule set");
                throw th5;
            }
            scan.skipWhitespace();
            if (mediaMatches(mediaList, this.rendererMediaType)) {
                this.inMediaRule = true;
                ruleset2.addAll(parseRuleset(scan));
                this.inMediaRule = false;
            } else {
                Ruleset parseRuleset = parseRuleset(scan);
            }
            if (!scan.consume('}')) {
                Throwable th6 = th;
                new SAXException("Invalid @media rule: expected '}' at end of rule set");
                throw th6;
            }
        }
        scan.skipWhitespace();
    }

    private void skipAtRule(CSSTextScanner cSSTextScanner) {
        CSSTextScanner scan = cSSTextScanner;
        int depth = 0;
        while (!scan.empty()) {
            int ch = scan.nextChar().intValue();
            if (ch != 59 || depth != 0) {
                if (ch == 123) {
                    depth++;
                } else if (ch == 125 && depth > 0) {
                    depth--;
                    if (depth == 0) {
                        return;
                    }
                }
            } else {
                return;
            }
        }
    }

    private Ruleset parseRuleset(CSSTextScanner cSSTextScanner) throws SAXException {
        Ruleset ruleset;
        CSSTextScanner scan = cSSTextScanner;
        new Ruleset();
        Ruleset ruleset2 = ruleset;
        while (!scan.empty()) {
            if (!scan.consume("<!--") && !scan.consume("-->")) {
                if (!scan.consume('@')) {
                    if (!parseRule(ruleset2, scan)) {
                        break;
                    }
                } else {
                    parseAtRule(ruleset2, scan);
                }
            }
        }
        return ruleset2;
    }

    private boolean parseRule(Ruleset ruleset, CSSTextScanner cSSTextScanner) throws SAXException {
        Rule rule;
        Throwable th;
        Ruleset ruleset2 = ruleset;
        CSSTextScanner scan = cSSTextScanner;
        List<Selector> selectors = parseSelectorGroup(scan);
        if (selectors == null || selectors.isEmpty()) {
            return false;
        }
        if (!scan.consume('{')) {
            Throwable th2 = th;
            new SAXException("Malformed rule block in <style> element: missing '{'");
            throw th2;
        }
        scan.skipWhitespace();
        SVG.Style ruleStyle = parseDeclarations(scan);
        scan.skipWhitespace();
        for (Selector selector : selectors) {
            new Rule(selector, ruleStyle);
            ruleset2.add(rule);
        }
        return true;
    }

    private List<Selector> parseSelectorGroup(CSSTextScanner cSSTextScanner) throws SAXException {
        ArrayList arrayList;
        Selector selector;
        Selector selector2;
        CSSTextScanner scan = cSSTextScanner;
        if (scan.empty()) {
            return null;
        }
        new ArrayList(1);
        ArrayList arrayList2 = arrayList;
        new Selector();
        Selector selector3 = selector;
        while (!scan.empty() && scan.nextSimpleSelector(selector3)) {
            if (scan.skipCommaWhitespace()) {
                boolean add = arrayList2.add(selector3);
                new Selector();
                selector3 = selector2;
            }
        }
        if (!selector3.isEmpty()) {
            boolean add2 = arrayList2.add(selector3);
        }
        return arrayList2;
    }

    private SVG.Style parseDeclarations(CSSTextScanner cSSTextScanner) throws SAXException {
        SVG.Style style;
        Throwable th;
        Throwable th2;
        CSSTextScanner scan = cSSTextScanner;
        new SVG.Style();
        SVG.Style ruleStyle = style;
        do {
            String propertyName = scan.nextIdentifier();
            scan.skipWhitespace();
            if (!scan.consume(':')) {
                break;
            }
            scan.skipWhitespace();
            String propertyValue = scan.nextPropertyValue();
            if (propertyValue == null) {
                break;
            }
            scan.skipWhitespace();
            if (scan.consume('!')) {
                scan.skipWhitespace();
                if (!scan.consume("important")) {
                    Throwable th3 = th;
                    new SAXException("Malformed rule set in <style> element: found unexpected '!'");
                    throw th3;
                }
                scan.skipWhitespace();
            }
            boolean consume = scan.consume(';');
            SVGParser.processStyleProperty(ruleStyle, propertyName, propertyValue);
            scan.skipWhitespace();
            if (scan.consume('}')) {
                return ruleStyle;
            }
        } while (!scan.empty());
        Throwable th4 = th2;
        new SAXException("Malformed rule set in <style> element");
        throw th4;
    }

    protected static List<String> parseClassAttribute(String str) throws SAXException {
        CSSTextScanner cSSTextScanner;
        Throwable th;
        StringBuilder sb;
        List<String> list;
        String val = str;
        new CSSTextScanner(val);
        CSSTextScanner scan = cSSTextScanner;
        List<String> classNameList = null;
        while (!scan.empty()) {
            String className = scan.nextIdentifier();
            if (className == null) {
                Throwable th2 = th;
                new StringBuilder();
                new SAXException(sb.append("Invalid value for \"class\" attribute: ").append(val).toString());
                throw th2;
            }
            if (classNameList == null) {
                new ArrayList();
                classNameList = list;
            }
            boolean add = classNameList.add(className);
            scan.skipWhitespace();
        }
        return classNameList;
    }

    protected static boolean ruleMatch(Selector selector, SVG.SvgElementBase svgElementBase) {
        List<SVG.SvgContainer> list;
        Selector selector2 = selector;
        SVG.SvgElementBase obj = svgElementBase;
        new ArrayList<>();
        List<SVG.SvgContainer> ancestors = list;
        SVG.SvgContainer svgContainer = obj.parent;
        while (true) {
            SVG.SvgContainer parent = svgContainer;
            if (parent == null) {
                break;
            }
            ancestors.add(0, parent);
            svgContainer = ((SVG.SvgObject) parent).parent;
        }
        int ancestorsPos = ancestors.size() - 1;
        if (selector2.size() == 1) {
            return selectorMatch(selector2.get(0), ancestors, ancestorsPos, obj);
        }
        return ruleMatch(selector2, selector2.size() - 1, ancestors, ancestorsPos, obj);
    }

    private static boolean ruleMatch(Selector selector, int i, List<SVG.SvgContainer> list, int i2, SVG.SvgElementBase svgElementBase) {
        Selector selector2 = selector;
        int selPartPos = i;
        List<SVG.SvgContainer> ancestors = list;
        int ancestorsPos = i2;
        SVG.SvgElementBase obj = svgElementBase;
        SimpleSelector sel = selector2.get(selPartPos);
        if (!selectorMatch(sel, ancestors, ancestorsPos, obj)) {
            return false;
        }
        if (sel.combinator == Combinator.DESCENDANT) {
            if (selPartPos == 0) {
                return true;
            }
            while (ancestorsPos >= 0) {
                if (ruleMatchOnAncestors(selector2, selPartPos - 1, ancestors, ancestorsPos)) {
                    return true;
                }
                ancestorsPos--;
            }
            return false;
        } else if (sel.combinator == Combinator.CHILD) {
            return ruleMatchOnAncestors(selector2, selPartPos - 1, ancestors, ancestorsPos);
        } else {
            int childPos = getChildPosition(ancestors, ancestorsPos, obj);
            if (childPos <= 0) {
                return false;
            }
            return ruleMatch(selector2, selPartPos - 1, ancestors, ancestorsPos, (SVG.SvgElementBase) obj.parent.getChildren().get(childPos - 1));
        }
    }

    private static boolean ruleMatchOnAncestors(Selector selector, int i, List<SVG.SvgContainer> list, int i2) {
        Selector selector2 = selector;
        int selPartPos = i;
        List<SVG.SvgContainer> ancestors = list;
        int ancestorsPos = i2;
        SimpleSelector sel = selector2.get(selPartPos);
        SVG.SvgElementBase obj = (SVG.SvgElementBase) ancestors.get(ancestorsPos);
        if (!selectorMatch(sel, ancestors, ancestorsPos, obj)) {
            return false;
        }
        if (sel.combinator == Combinator.DESCENDANT) {
            if (selPartPos == 0) {
                return true;
            }
            while (ancestorsPos > 0) {
                ancestorsPos--;
                if (ruleMatchOnAncestors(selector2, selPartPos - 1, ancestors, ancestorsPos)) {
                    return true;
                }
            }
            return false;
        } else if (sel.combinator == Combinator.CHILD) {
            return ruleMatchOnAncestors(selector2, selPartPos - 1, ancestors, ancestorsPos - 1);
        } else {
            int childPos = getChildPosition(ancestors, ancestorsPos, obj);
            if (childPos <= 0) {
                return false;
            }
            return ruleMatch(selector2, selPartPos - 1, ancestors, ancestorsPos, (SVG.SvgElementBase) obj.parent.getChildren().get(childPos - 1));
        }
    }

    private static int getChildPosition(List<SVG.SvgContainer> list, int i, SVG.SvgElementBase svgElementBase) {
        List<SVG.SvgContainer> ancestors = list;
        int ancestorsPos = i;
        SVG.SvgElementBase obj = svgElementBase;
        if (ancestorsPos < 0) {
            return -1;
        }
        if (ancestors.get(ancestorsPos) != obj.parent) {
            return -1;
        }
        int childPos = 0;
        for (SVG.SvgObject svgObject : obj.parent.getChildren()) {
            if (svgObject == obj) {
                return childPos;
            }
            childPos++;
        }
        return -1;
    }

    private static boolean selectorMatch(SimpleSelector simpleSelector, List<SVG.SvgContainer> list, int i, SVG.SvgElementBase svgElementBase) {
        SimpleSelector sel = simpleSelector;
        List<SVG.SvgContainer> ancestors = list;
        int ancestorsPos = i;
        SVG.SvgElementBase obj = svgElementBase;
        if (sel.tag != null) {
            if (sel.tag.equalsIgnoreCase("G")) {
                if (!(obj instanceof SVG.Group)) {
                    return false;
                }
            } else if (!sel.tag.equals(obj.getClass().getSimpleName().toLowerCase(Locale.US))) {
                return false;
            }
        }
        if (sel.attribs != null) {
            for (Attrib attr : sel.attribs) {
                if (attr.name == "id") {
                    if (!attr.value.equals(obj.f337id)) {
                        return false;
                    }
                } else if (attr.name != CLASS) {
                    return false;
                } else {
                    if (obj.classNames == null) {
                        return false;
                    }
                    if (!obj.classNames.contains(attr.value)) {
                        return false;
                    }
                }
            }
        }
        if (sel.pseudos != null) {
            for (String pseudo : sel.pseudos) {
                if (!pseudo.equals("first-child")) {
                    return false;
                }
                if (getChildPosition(ancestors, ancestorsPos, obj) != 0) {
                    return false;
                }
            }
        }
        return true;
    }
}
