package org.locationtech.jts.p006io.gml2;

import java.util.LinkedList;
import java.util.List;
import java.util.Stack;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.p006io.gml2.GeometryStrategies;
import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.ErrorHandler;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.AttributesImpl;
import org.xml.sax.helpers.DefaultHandler;

/* renamed from: org.locationtech.jts.io.gml2.GMLHandler */
public class GMLHandler extends DefaultHandler {
    private ErrorHandler delegate = null;

    /* renamed from: gf */
    private GeometryFactory f450gf = null;
    private Locator locator = null;
    private Stack stack;

    /* renamed from: org.locationtech.jts.io.gml2.GMLHandler$Handler */
    static class Handler {
        protected Attributes attrs = null;
        protected List children = null;
        protected GeometryStrategies.ParseStrategy strategy;
        protected StringBuffer text = null;

        public Handler(GeometryStrategies.ParseStrategy parseStrategy, Attributes attributes) {
            Attributes attributes2;
            GeometryStrategies.ParseStrategy strategy2 = parseStrategy;
            Attributes attributes3 = attributes;
            if (attributes3 != null) {
                new AttributesImpl(attributes3);
                this.attrs = attributes2;
            }
            this.strategy = strategy2;
        }

        public void addText(String str) {
            StringBuffer stringBuffer;
            String str2 = str;
            if (this.text == null) {
                new StringBuffer();
                this.text = stringBuffer;
            }
            StringBuffer append = this.text.append(str2);
        }

        public void keep(Object obj) {
            List list;
            Object obj2 = obj;
            if (this.children == null) {
                new LinkedList();
                this.children = list;
            }
            boolean add = this.children.add(obj2);
        }

        public Object create(GeometryFactory gf) throws SAXException {
            return this.strategy.parse(this, gf);
        }
    }

    public GMLHandler(GeometryFactory gf, ErrorHandler delegate2) {
        Stack stack2;
        Object obj;
        new Stack();
        this.stack = stack2;
        this.delegate = delegate2;
        this.f450gf = gf;
        new Handler((GeometryStrategies.ParseStrategy) null, (Attributes) null);
        Object push = this.stack.push(obj);
    }

    public boolean isGeometryComplete() {
        if (this.stack.size() > 1) {
            return false;
        }
        if (((Handler) this.stack.peek()).children.size() < 1) {
            return false;
        }
        return true;
    }

    public Geometry getGeometry() {
        Throwable th;
        StringBuilder sb;
        if (this.stack.size() == 1) {
            Handler h = (Handler) this.stack.peek();
            if (h.children.size() == 1) {
                return (Geometry) h.children.get(0);
            }
            return this.f450gf.createGeometryCollection((Geometry[]) h.children.toArray(new Geometry[this.stack.size()]));
        }
        Throwable th2 = th;
        new StringBuilder();
        new IllegalStateException(sb.append("Parse did not complete as expected, there are ").append(this.stack.size()).append(" elements on the Stack").toString());
        throw th2;
    }

    public void characters(char[] cArr, int i, int i2) throws SAXException {
        String str;
        char[] ch = cArr;
        int start = i;
        int length = i2;
        if (!this.stack.isEmpty()) {
            new String(ch, start, length);
            ((Handler) this.stack.peek()).addText(str);
        }
    }

    public void ignorableWhitespace(char[] cArr, int i, int i2) throws SAXException {
        char[] cArr2 = cArr;
        int i3 = i;
        int i4 = i2;
        if (!this.stack.isEmpty()) {
            ((Handler) this.stack.peek()).addText(" ");
        }
    }

    public void endElement(String str, String str2, String str3) throws SAXException {
        String str4 = str;
        String str5 = str2;
        String str6 = str3;
        ((Handler) this.stack.peek()).keep(((Handler) this.stack.pop()).create(this.f450gf));
    }

    public void startElement(String uri, String localName, String str, Attributes attributes) throws SAXException {
        Object obj;
        String qName = str;
        Attributes attributes2 = attributes;
        GeometryStrategies.ParseStrategy ps = GeometryStrategies.findStrategy(uri, localName);
        if (ps == null) {
            ps = GeometryStrategies.findStrategy((String) null, qName.substring(qName.indexOf(58) + 1, qName.length()));
        }
        new Handler(ps, attributes2);
        Object push = this.stack.push(obj);
    }

    public void setDocumentLocator(Locator locator2) {
        Locator locator3 = locator2;
        this.locator = locator3;
        if (this.delegate != null && (this.delegate instanceof ContentHandler)) {
            ((ContentHandler) this.delegate).setDocumentLocator(locator3);
        }
    }

    /* access modifiers changed from: protected */
    public Locator getDocumentLocator() {
        return this.locator;
    }

    public void fatalError(SAXParseException sAXParseException) throws SAXException {
        SAXParseException e = sAXParseException;
        if (this.delegate != null) {
            this.delegate.fatalError(e);
        } else {
            super.fatalError(e);
        }
    }

    public void error(SAXParseException sAXParseException) throws SAXException {
        SAXParseException e = sAXParseException;
        if (this.delegate != null) {
            this.delegate.error(e);
        } else {
            super.error(e);
        }
    }

    public void warning(SAXParseException sAXParseException) throws SAXException {
        SAXParseException e = sAXParseException;
        if (this.delegate != null) {
            this.delegate.warning(e);
        } else {
            super.warning(e);
        }
    }
}
