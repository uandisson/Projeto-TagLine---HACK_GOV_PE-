package org.locationtech.jts.p006io.gml2;

import java.util.HashMap;
import java.util.List;
import java.util.WeakHashMap;
import java.util.regex.Pattern;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.CoordinateSequence;
import org.locationtech.jts.geom.Envelope;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.LineString;
import org.locationtech.jts.geom.LinearRing;
import org.locationtech.jts.geom.MultiLineString;
import org.locationtech.jts.geom.MultiPoint;
import org.locationtech.jts.geom.MultiPolygon;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.Polygon;
import org.locationtech.jts.p006io.gml2.GMLHandler;
import org.locationtech.jts.util.StringUtil;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

/* renamed from: org.locationtech.jts.io.gml2.GeometryStrategies */
public class GeometryStrategies {
    private static HashMap strategies = loadStrategies();

    /* renamed from: org.locationtech.jts.io.gml2.GeometryStrategies$ParseStrategy */
    interface ParseStrategy {
        Object parse(GMLHandler.Handler handler, GeometryFactory geometryFactory) throws SAXException;
    }

    public GeometryStrategies() {
    }

    private static HashMap loadStrategies() {
        HashMap hashMap;
        Object obj;
        Object obj2;
        Object obj3;
        Object obj4;
        Object obj5;
        Object obj6;
        Object obj7;
        Object obj8;
        Object obj9;
        Object obj10;
        Object obj11;
        ParseStrategy parseStrategy;
        ParseStrategy parseStrategy2;
        new HashMap();
        HashMap strats = hashMap;
        new ParseStrategy() {
            public Object parse(GMLHandler.Handler handler, GeometryFactory geometryFactory) throws SAXException {
                Point p;
                Throwable th;
                GMLHandler.Handler arg = handler;
                GeometryFactory gf = geometryFactory;
                if (arg.children.size() != 1) {
                    Throwable th2 = th;
                    new SAXException("Cannot create a point without exactly one coordinate");
                    throw th2;
                }
                int srid = GeometryStrategies.getSrid(arg.attrs, gf.getSRID());
                Object c = arg.children.get(0);
                if (c instanceof Coordinate) {
                    p = gf.createPoint((Coordinate) c);
                } else {
                    p = gf.createPoint((CoordinateSequence) c);
                }
                if (p.getSRID() != srid) {
                    p.setSRID(srid);
                }
                return p;
            }
        };
        Object put = strats.put("Point".toLowerCase(), obj);
        new ParseStrategy() {
            public Object parse(GMLHandler.Handler handler, GeometryFactory geometryFactory) throws SAXException {
                Throwable th;
                LineString ls;
                Throwable th2;
                Throwable th3;
                GMLHandler.Handler arg = handler;
                GeometryFactory gf = geometryFactory;
                if (arg.children.size() < 1) {
                    Throwable th4 = th3;
                    new SAXException("Cannot create a linestring without atleast two coordinates or one coordinate sequence");
                    throw th4;
                }
                int srid = GeometryStrategies.getSrid(arg.attrs, gf.getSRID());
                if (arg.children.size() == 1) {
                    try {
                        ls = gf.createLineString((CoordinateSequence) arg.children.get(0));
                    } catch (ClassCastException e) {
                        ClassCastException e2 = e;
                        Throwable th5 = th2;
                        new SAXException("Cannot create a linestring without atleast two coordinates or one coordinate sequence", e2);
                        throw th5;
                    }
                } else {
                    try {
                        ls = gf.createLineString((Coordinate[]) arg.children.toArray(new Coordinate[arg.children.size()]));
                    } catch (ClassCastException e3) {
                        ClassCastException e4 = e3;
                        Throwable th6 = th;
                        new SAXException("Cannot create a linestring without atleast two coordinates or one coordinate sequence", e4);
                        throw th6;
                    }
                }
                if (ls.getSRID() != srid) {
                    ls.setSRID(srid);
                }
                return ls;
            }
        };
        Object put2 = strats.put("LineString".toLowerCase(), obj2);
        new ParseStrategy() {
            public Object parse(GMLHandler.Handler handler, GeometryFactory geometryFactory) throws SAXException {
                Throwable th;
                LinearRing ls;
                Throwable th2;
                Throwable th3;
                GMLHandler.Handler arg = handler;
                GeometryFactory gf = geometryFactory;
                if (arg.children.size() == 1 || arg.children.size() >= 4) {
                    int srid = GeometryStrategies.getSrid(arg.attrs, gf.getSRID());
                    if (arg.children.size() == 1) {
                        try {
                            ls = gf.createLinearRing((CoordinateSequence) arg.children.get(0));
                        } catch (ClassCastException e) {
                            ClassCastException e2 = e;
                            Throwable th4 = th2;
                            new SAXException("Cannot create a linear ring without atleast four coordinates or one coordinate sequence", e2);
                            throw th4;
                        }
                    } else {
                        try {
                            ls = gf.createLinearRing((Coordinate[]) arg.children.toArray(new Coordinate[arg.children.size()]));
                        } catch (ClassCastException e3) {
                            ClassCastException e4 = e3;
                            Throwable th5 = th;
                            new SAXException("Cannot create a linear ring without atleast four coordinates or one coordinate sequence", e4);
                            throw th5;
                        }
                    }
                    if (ls.getSRID() != srid) {
                        ls.setSRID(srid);
                    }
                    return ls;
                }
                Throwable th6 = th3;
                new SAXException("Cannot create a linear ring without atleast four coordinates or one coordinate sequence");
                throw th6;
            }
        };
        Object put3 = strats.put(GMLConstants.GML_LINEARRING.toLowerCase(), obj3);
        new ParseStrategy() {
            public Object parse(GMLHandler.Handler handler, GeometryFactory geometryFactory) throws SAXException {
                Throwable th;
                GMLHandler.Handler arg = handler;
                GeometryFactory gf = geometryFactory;
                if (arg.children.size() < 1) {
                    Throwable th2 = th;
                    new SAXException("Cannot create a polygon without atleast one linear ring");
                    throw th2;
                }
                int srid = GeometryStrategies.getSrid(arg.attrs, gf.getSRID());
                LinearRing outer = (LinearRing) arg.children.get(0);
                List t = arg.children.size() > 1 ? arg.children.subList(1, arg.children.size()) : null;
                Polygon p = gf.createPolygon(outer, t == null ? null : (LinearRing[]) t.toArray(new LinearRing[t.size()]));
                if (p.getSRID() != srid) {
                    p.setSRID(srid);
                }
                return p;
            }
        };
        Object put4 = strats.put("Polygon".toLowerCase(), obj4);
        new ParseStrategy() {
            public Object parse(GMLHandler.Handler handler, GeometryFactory geometryFactory) throws SAXException {
                Throwable th;
                Envelope envelope;
                Envelope box;
                Envelope envelope2;
                GMLHandler.Handler arg = handler;
                GeometryFactory geometryFactory2 = geometryFactory;
                if (arg.children.size() < 1 || arg.children.size() > 2) {
                    Throwable th2 = th;
                    new SAXException("Cannot create a box without either two coords or one coordinate sequence");
                    throw th2;
                }
                if (arg.children.size() == 1) {
                    new Envelope();
                    box = ((CoordinateSequence) arg.children.get(0)).expandEnvelope(envelope2);
                } else {
                    new Envelope((Coordinate) arg.children.get(0), (Coordinate) arg.children.get(1));
                    box = envelope;
                }
                return box;
            }
        };
        Object put5 = strats.put(GMLConstants.GML_BOX.toLowerCase(), obj5);
        new ParseStrategy() {
            public Object parse(GMLHandler.Handler handler, GeometryFactory geometryFactory) throws SAXException {
                Throwable th;
                GMLHandler.Handler arg = handler;
                GeometryFactory gf = geometryFactory;
                if (arg.children.size() < 1) {
                    Throwable th2 = th;
                    new SAXException("Cannot create a multi-point without atleast one point");
                    throw th2;
                }
                int srid = GeometryStrategies.getSrid(arg.attrs, gf.getSRID());
                MultiPoint mp = gf.createMultiPoint((Point[]) arg.children.toArray(new Point[arg.children.size()]));
                if (mp.getSRID() != srid) {
                    mp.setSRID(srid);
                }
                return mp;
            }
        };
        Object put6 = strats.put("MultiPoint".toLowerCase(), obj6);
        new ParseStrategy() {
            public Object parse(GMLHandler.Handler handler, GeometryFactory geometryFactory) throws SAXException {
                Throwable th;
                GMLHandler.Handler arg = handler;
                GeometryFactory gf = geometryFactory;
                if (arg.children.size() < 1) {
                    Throwable th2 = th;
                    new SAXException("Cannot create a multi-linestring without atleast one linestring");
                    throw th2;
                }
                int srid = GeometryStrategies.getSrid(arg.attrs, gf.getSRID());
                MultiLineString mp = gf.createMultiLineString((LineString[]) arg.children.toArray(new LineString[arg.children.size()]));
                if (mp.getSRID() != srid) {
                    mp.setSRID(srid);
                }
                return mp;
            }
        };
        Object put7 = strats.put("MultiLineString".toLowerCase(), obj7);
        new ParseStrategy() {
            public Object parse(GMLHandler.Handler handler, GeometryFactory geometryFactory) throws SAXException {
                Throwable th;
                GMLHandler.Handler arg = handler;
                GeometryFactory gf = geometryFactory;
                if (arg.children.size() < 1) {
                    Throwable th2 = th;
                    new SAXException("Cannot create a multi-polygon without atleast one polygon");
                    throw th2;
                }
                int srid = GeometryStrategies.getSrid(arg.attrs, gf.getSRID());
                MultiPolygon mp = gf.createMultiPolygon((Polygon[]) arg.children.toArray(new Polygon[arg.children.size()]));
                if (mp.getSRID() != srid) {
                    mp.setSRID(srid);
                }
                return mp;
            }
        };
        Object put8 = strats.put("MultiPolygon".toLowerCase(), obj8);
        new ParseStrategy() {
            public Object parse(GMLHandler.Handler handler, GeometryFactory geometryFactory) throws SAXException {
                Throwable th;
                GMLHandler.Handler arg = handler;
                GeometryFactory gf = geometryFactory;
                if (arg.children.size() < 1) {
                    Throwable th2 = th;
                    new SAXException("Cannot create a multi-polygon without atleast one geometry");
                    throw th2;
                }
                return gf.createGeometryCollection((Geometry[]) arg.children.toArray(new Geometry[arg.children.size()]));
            }
        };
        Object put9 = strats.put(GMLConstants.GML_MULTI_GEOMETRY.toLowerCase(), obj9);
        new ParseStrategy() {
            private WeakHashMap patterns;

            {
                WeakHashMap weakHashMap;
                new WeakHashMap();
                this.patterns = weakHashMap;
            }

            public Object parse(GMLHandler.Handler handler, GeometryFactory geometryFactory) throws SAXException {
                Throwable th;
                String str;
                Throwable th2;
                Throwable th3;
                String str2;
                GMLHandler.Handler arg = handler;
                GeometryFactory gf = geometryFactory;
                if (arg.text == null || "".equals(arg.text)) {
                    Throwable th4 = th;
                    new SAXException("Cannot create a coordinate sequence without text to parse");
                    throw th4;
                }
                String decimal = ".";
                String coordSeperator = ",";
                String toupleSeperator = " ";
                if (arg.attrs.getIndex("decimal") >= 0) {
                    decimal = arg.attrs.getValue("decimal");
                } else if (arg.attrs.getIndex(GMLConstants.GML_NAMESPACE, "decimal") >= 0) {
                    decimal = arg.attrs.getValue(GMLConstants.GML_NAMESPACE, "decimal");
                }
                if (arg.attrs.getIndex("cs") >= 0) {
                    coordSeperator = arg.attrs.getValue("cs");
                } else if (arg.attrs.getIndex(GMLConstants.GML_NAMESPACE, "cs") >= 0) {
                    coordSeperator = arg.attrs.getValue(GMLConstants.GML_NAMESPACE, "cs");
                }
                if (arg.attrs.getIndex("ts") >= 0) {
                    toupleSeperator = arg.attrs.getValue("ts");
                } else if (arg.attrs.getIndex(GMLConstants.GML_NAMESPACE, "ts") >= 0) {
                    toupleSeperator = arg.attrs.getValue(GMLConstants.GML_NAMESPACE, "ts");
                }
                String t = arg.text.toString().replaceAll("\\s", " ");
                Pattern ptn = (Pattern) this.patterns.get(toupleSeperator);
                if (ptn == null) {
                    new String(toupleSeperator);
                    String ts = str2;
                    if (ts.indexOf(92) > -1) {
                        ts = ts.replaceAll("\\", "\\\\");
                    }
                    if (ts.indexOf(46) > -1) {
                        ts = ts.replaceAll("\\.", "\\\\.");
                    }
                    ptn = Pattern.compile(ts);
                    Object put = this.patterns.put(toupleSeperator, ptn);
                }
                String[] touples = ptn.split(t.trim());
                if (touples.length == 0) {
                    Throwable th5 = th3;
                    new SAXException("Cannot create a coordinate sequence without a touple to parse");
                    throw th5;
                }
                int numNonNullTouples = 0;
                for (int i = 0; i < touples.length; i++) {
                    if (touples[i] != null && !"".equals(touples[i].trim())) {
                        if (i != numNonNullTouples) {
                            touples[numNonNullTouples] = touples[i];
                        }
                        numNonNullTouples++;
                    }
                }
                for (int i2 = numNonNullTouples; i2 < touples.length; i2++) {
                    touples[i2] = null;
                }
                if (numNonNullTouples == 0) {
                    Throwable th6 = th2;
                    new SAXException("Cannot create a coordinate sequence without a non-null touple to parse");
                    throw th6;
                }
                CoordinateSequence cs = gf.getCoordinateSequenceFactory().create(numNonNullTouples, StringUtil.split(touples[0], coordSeperator).length);
                int dim = cs.getDimension();
                boolean replaceDec = !".".equals(decimal);
                for (int i3 = 0; i3 < numNonNullTouples; i3++) {
                    Pattern ptn2 = (Pattern) this.patterns.get(coordSeperator);
                    if (ptn2 == null) {
                        new String(coordSeperator);
                        String ts2 = str;
                        if (ts2.indexOf(92) > -1) {
                            ts2 = ts2.replaceAll("\\", "\\\\");
                        }
                        if (ts2.indexOf(46) > -1) {
                            ts2 = ts2.replaceAll("\\.", "\\\\.");
                        }
                        ptn2 = Pattern.compile(ts2);
                        Object put2 = this.patterns.put(coordSeperator, ptn2);
                    }
                    String[] coords = ptn2.split(touples[i3]);
                    int dimIndex = 0;
                    int j = 0;
                    while (j < coords.length && j < dim) {
                        if (coords[j] != null && !"".equals(coords[j].trim())) {
                            int i4 = dimIndex;
                            dimIndex++;
                            cs.setOrdinate(i3, i4, Double.parseDouble(replaceDec ? coords[j].replaceAll(decimal, ".") : coords[j]));
                        }
                        j++;
                    }
                    while (dimIndex < dim) {
                        int i5 = dimIndex;
                        dimIndex++;
                        cs.setOrdinate(i3, i5, Double.NaN);
                    }
                }
                return cs;
            }
        };
        Object put10 = strats.put(GMLConstants.GML_COORDINATES.toLowerCase(), obj10);
        new ParseStrategy() {
            public Object parse(GMLHandler.Handler handler, GeometryFactory geometryFactory) throws SAXException {
                Coordinate coordinate;
                Throwable th;
                Throwable th2;
                GMLHandler.Handler arg = handler;
                GeometryFactory geometryFactory2 = geometryFactory;
                if (arg.children.size() < 1) {
                    Throwable th3 = th2;
                    new SAXException("Cannot create a coordinate without atleast one axis");
                    throw th3;
                } else if (arg.children.size() > 3) {
                    Throwable th4 = th;
                    new SAXException("Cannot create a coordinate with more than 3 axis");
                    throw th4;
                } else {
                    Double[] axis = (Double[]) arg.children.toArray(new Double[arg.children.size()]);
                    new Coordinate();
                    Coordinate c = coordinate;
                    c.f412x = axis[0].doubleValue();
                    if (axis.length > 1) {
                        c.f413y = axis[1].doubleValue();
                    }
                    if (axis.length > 2) {
                        c.f414z = axis[2].doubleValue();
                    }
                    return c;
                }
            }
        };
        Object put11 = strats.put(GMLConstants.GML_COORD.toLowerCase(), obj11);
        new ParseStrategy() {
            public Object parse(GMLHandler.Handler handler, GeometryFactory geometryFactory) throws SAXException {
                Object obj;
                GMLHandler.Handler arg = handler;
                GeometryFactory geometryFactory2 = geometryFactory;
                if (arg.text == null) {
                    return null;
                }
                new Double(arg.text.toString());
                return obj;
            }
        };
        ParseStrategy coord_child = parseStrategy;
        Object put12 = strats.put(GMLConstants.GML_COORD_X.toLowerCase(), coord_child);
        Object put13 = strats.put(GMLConstants.GML_COORD_Y.toLowerCase(), coord_child);
        Object put14 = strats.put(GMLConstants.GML_COORD_Z.toLowerCase(), coord_child);
        new ParseStrategy() {
            public Object parse(GMLHandler.Handler handler, GeometryFactory geometryFactory) throws SAXException {
                Throwable th;
                GMLHandler.Handler arg = handler;
                GeometryFactory geometryFactory2 = geometryFactory;
                if (arg.children.size() == 1) {
                    return arg.children.get(0);
                }
                Throwable th2 = th;
                new SAXException("Geometry Members may only contain one geometry.");
                throw th2;
            }
        };
        ParseStrategy member = parseStrategy2;
        Object put15 = strats.put(GMLConstants.GML_OUTER_BOUNDARY_IS.toLowerCase(), member);
        Object put16 = strats.put(GMLConstants.GML_INNER_BOUNDARY_IS.toLowerCase(), member);
        Object put17 = strats.put(GMLConstants.GML_POINT_MEMBER.toLowerCase(), member);
        Object put18 = strats.put(GMLConstants.GML_LINESTRING_MEMBER.toLowerCase(), member);
        Object put19 = strats.put(GMLConstants.GML_POLYGON_MEMBER.toLowerCase(), member);
        return strats;
    }

    static int getSrid(Attributes attributes, int i) {
        Attributes attrs = attributes;
        int defaultValue = i;
        String srs = null;
        if (attrs.getIndex(GMLConstants.GML_ATTR_SRSNAME) >= 0) {
            srs = attrs.getValue(GMLConstants.GML_ATTR_SRSNAME);
        } else if (attrs.getIndex(GMLConstants.GML_NAMESPACE, GMLConstants.GML_ATTR_SRSNAME) >= 0) {
            srs = attrs.getValue(GMLConstants.GML_NAMESPACE, GMLConstants.GML_ATTR_SRSNAME);
        }
        if (srs != null) {
            String srs2 = srs.trim();
            if (srs2 != null && !"".equals(srs2)) {
                try {
                    return Integer.parseInt(srs2);
                } catch (NumberFormatException e) {
                    NumberFormatException numberFormatException = e;
                    int index = srs2.lastIndexOf(35);
                    if (index > -1) {
                        srs2 = srs2.substring(index);
                    }
                    try {
                        return Integer.parseInt(srs2);
                    } catch (NumberFormatException e2) {
                        NumberFormatException numberFormatException2 = e2;
                    }
                }
            }
        }
        return defaultValue;
    }

    public static ParseStrategy findStrategy(String str, String str2) {
        String str3 = str;
        String localName = str2;
        return localName == null ? null : (ParseStrategy) strategies.get(localName.toLowerCase());
    }
}
