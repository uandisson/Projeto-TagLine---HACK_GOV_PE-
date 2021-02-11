package org.locationtech.jts.p006io.kml;

import java.io.IOException;
import java.io.Writer;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryCollection;
import org.locationtech.jts.geom.LineString;
import org.locationtech.jts.geom.LinearRing;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.Polygon;
import org.locationtech.jts.p006io.gml2.GMLConstants;
import org.locationtech.jts.util.StringUtil;

/* renamed from: org.locationtech.jts.io.kml.KMLWriter */
public class KMLWriter {
    public static String ALTITUDE_MODE_ABSOLUTE = "absolute";
    public static String ALTITUDE_MODE_CLAMPTOGROUND = "clampToGround ";
    public static String ALTITUDE_MODE_RELATIVETOGROUND = "relativeToGround  ";
    private static final String COORDINATE_SEPARATOR = ",";
    private static final String TUPLE_SEPARATOR = " ";
    private final int INDENT_SIZE = 2;
    private String altitudeMode = null;
    private boolean extrude = false;
    private String linePrefix = null;
    private int maxCoordinatesPerLine = 5;
    private DecimalFormat numberFormatter = null;
    private boolean tesselate;
    private double zVal = Double.NaN;

    public static String writeGeometry(Geometry geometry, double z) {
        KMLWriter kMLWriter;
        new KMLWriter();
        KMLWriter writer = kMLWriter;
        writer.setZ(z);
        return writer.write(geometry);
    }

    public static String writeGeometry(Geometry geometry, double z, int precision, boolean extrude2, String altitudeMode2) {
        KMLWriter kMLWriter;
        new KMLWriter();
        KMLWriter writer = kMLWriter;
        writer.setZ(z);
        writer.setPrecision(precision);
        writer.setExtrude(extrude2);
        writer.setAltitudeMode(altitudeMode2);
        return writer.write(geometry);
    }

    public KMLWriter() {
    }

    public void setLinePrefix(String linePrefix2) {
        String str = linePrefix2;
        this.linePrefix = str;
    }

    public void setMaximumCoordinatesPerLine(int i) {
        int maxCoordinatesPerLine2 = i;
        if (maxCoordinatesPerLine2 > 0) {
            this.maxCoordinatesPerLine = maxCoordinatesPerLine2;
        }
    }

    public void setZ(double zVal2) {
        double d = zVal2;
        this.zVal = d;
    }

    public void setExtrude(boolean extrude2) {
        boolean z = extrude2;
        this.extrude = z;
    }

    public void setTesselate(boolean tesselate2) {
        boolean z = tesselate2;
        this.tesselate = z;
    }

    public void setAltitudeMode(String altitudeMode2) {
        String str = altitudeMode2;
        this.altitudeMode = str;
    }

    public void setPrecision(int i) {
        int precision = i;
        if (precision >= 0) {
            this.numberFormatter = createFormatter(precision);
        }
    }

    public String write(Geometry geom) {
        StringBuffer stringBuffer;
        new StringBuffer();
        StringBuffer buf = stringBuffer;
        write(geom, buf);
        return buf.toString();
    }

    public void write(Geometry geometry, Writer writer) throws IOException {
        writer.write(write(geometry));
    }

    public void write(Geometry geometry, StringBuffer buf) {
        writeGeometry(geometry, 0, buf);
    }

    private void writeGeometry(Geometry geometry, int i, StringBuffer stringBuffer) {
        Throwable th;
        StringBuilder sb;
        Geometry g = geometry;
        int level = i;
        StringBuffer buf = stringBuffer;
        String attributes = "";
        if (g instanceof Point) {
            writePoint((Point) g, attributes, level, buf);
        } else if (g instanceof LinearRing) {
            writeLinearRing((LinearRing) g, attributes, true, level, buf);
        } else if (g instanceof LineString) {
            writeLineString((LineString) g, attributes, level, buf);
        } else if (g instanceof Polygon) {
            writePolygon((Polygon) g, attributes, level, buf);
        } else if (g instanceof GeometryCollection) {
            writeGeometryCollection((GeometryCollection) g, attributes, level, buf);
        } else {
            Throwable th2 = th;
            new StringBuilder();
            new IllegalArgumentException(sb.append("Geometry type not supported: ").append(g.getGeometryType()).toString());
            throw th2;
        }
    }

    private void startLine(String str, int i, StringBuffer stringBuffer) {
        String text = str;
        int level = i;
        StringBuffer buf = stringBuffer;
        if (this.linePrefix != null) {
            StringBuffer append = buf.append(this.linePrefix);
        }
        StringBuffer append2 = buf.append(StringUtil.spaces(2 * level));
        StringBuffer append3 = buf.append(text);
    }

    private String geometryTag(String geometryName, String str) {
        StringBuffer stringBuffer;
        String attributes = str;
        new StringBuffer();
        StringBuffer buf = stringBuffer;
        StringBuffer append = buf.append("<");
        StringBuffer append2 = buf.append(geometryName);
        if (attributes != null && attributes.length() > 0) {
            StringBuffer append3 = buf.append(TUPLE_SEPARATOR);
            StringBuffer append4 = buf.append(attributes);
        }
        StringBuffer append5 = buf.append(">");
        return buf.toString();
    }

    private void writeModifiers(int i, StringBuffer stringBuffer) {
        StringBuilder sb;
        int level = i;
        StringBuffer buf = stringBuffer;
        if (this.extrude) {
            startLine("<extrude>1</extrude>\n", level, buf);
        }
        if (this.tesselate) {
            startLine("<tesselate>1</tesselate>\n", level, buf);
        }
        if (this.altitudeMode != null) {
            new StringBuilder();
            startLine(sb.append("<altitudeMode>").append(this.altitudeMode).append("</altitudeMode>\n").toString(), level, buf);
        }
    }

    private void writePoint(Point p, String attributes, int i, StringBuffer stringBuffer) {
        StringBuilder sb;
        int level = i;
        StringBuffer buf = stringBuffer;
        new StringBuilder();
        startLine(sb.append(geometryTag("Point", attributes)).append("\n").toString(), level, buf);
        writeModifiers(level, buf);
        write(new Coordinate[]{p.getCoordinate()}, level + 1, buf);
        startLine("</Point>\n", level, buf);
    }

    private void writeLineString(LineString ls, String attributes, int i, StringBuffer stringBuffer) {
        StringBuilder sb;
        int level = i;
        StringBuffer buf = stringBuffer;
        new StringBuilder();
        startLine(sb.append(geometryTag("LineString", attributes)).append("\n").toString(), level, buf);
        writeModifiers(level, buf);
        write(ls.getCoordinates(), level + 1, buf);
        startLine("</LineString>\n", level, buf);
    }

    private void writeLinearRing(LinearRing linearRing, String attributes, boolean writeModifiers, int i, StringBuffer stringBuffer) {
        StringBuilder sb;
        LinearRing lr = linearRing;
        int level = i;
        StringBuffer buf = stringBuffer;
        new StringBuilder();
        startLine(sb.append(geometryTag(GMLConstants.GML_LINEARRING, attributes)).append("\n").toString(), level, buf);
        if (writeModifiers) {
            writeModifiers(level, buf);
        }
        write(lr.getCoordinates(), level + 1, buf);
        startLine("</LinearRing>\n", level, buf);
    }

    private void writePolygon(Polygon polygon, String attributes, int i, StringBuffer stringBuffer) {
        StringBuilder sb;
        Polygon p = polygon;
        int level = i;
        StringBuffer buf = stringBuffer;
        new StringBuilder();
        startLine(sb.append(geometryTag("Polygon", attributes)).append("\n").toString(), level, buf);
        writeModifiers(level, buf);
        startLine("  <outerBoundaryIs>\n", level, buf);
        writeLinearRing((LinearRing) p.getExteriorRing(), (String) null, false, level + 1, buf);
        startLine("  </outerBoundaryIs>\n", level, buf);
        for (int t = 0; t < p.getNumInteriorRing(); t++) {
            startLine("  <innerBoundaryIs>\n", level, buf);
            writeLinearRing((LinearRing) p.getInteriorRingN(t), (String) null, false, level + 1, buf);
            startLine("  </innerBoundaryIs>\n", level, buf);
        }
        startLine("</Polygon>\n", level, buf);
    }

    private void writeGeometryCollection(GeometryCollection geometryCollection, String str, int i, StringBuffer stringBuffer) {
        GeometryCollection gc = geometryCollection;
        String str2 = str;
        int level = i;
        StringBuffer buf = stringBuffer;
        startLine("<MultiGeometry>\n", level, buf);
        for (int t = 0; t < gc.getNumGeometries(); t++) {
            writeGeometry(gc.getGeometryN(t), level + 1, buf);
        }
        startLine("</MultiGeometry>\n", level, buf);
    }

    private void write(Coordinate[] coordinateArr, int i, StringBuffer stringBuffer) {
        Coordinate[] coords = coordinateArr;
        int level = i;
        StringBuffer buf = stringBuffer;
        startLine("<coordinates>", level, buf);
        boolean isNewLine = false;
        for (int i2 = 0; i2 < coords.length; i2++) {
            if (i2 > 0) {
                StringBuffer append = buf.append(TUPLE_SEPARATOR);
            }
            if (isNewLine) {
                startLine("  ", level, buf);
                isNewLine = false;
            }
            write(coords[i2], buf);
            if ((i2 + 1) % this.maxCoordinatesPerLine == 0 && i2 < coords.length - 1) {
                StringBuffer append2 = buf.append("\n");
                isNewLine = true;
            }
        }
        StringBuffer append3 = buf.append("</coordinates>\n");
    }

    private void write(Coordinate coordinate, StringBuffer stringBuffer) {
        Coordinate p = coordinate;
        StringBuffer buf = stringBuffer;
        write(p.f412x, buf);
        StringBuffer append = buf.append(COORDINATE_SEPARATOR);
        write(p.f413y, buf);
        double z = p.f414z;
        if (!Double.isNaN(this.zVal)) {
            z = this.zVal;
        }
        if (!Double.isNaN(z)) {
            StringBuffer append2 = buf.append(COORDINATE_SEPARATOR);
            write(z, buf);
        }
    }

    private void write(double d, StringBuffer stringBuffer) {
        double num = d;
        StringBuffer buf = stringBuffer;
        if (this.numberFormatter != null) {
            StringBuffer append = buf.append(this.numberFormatter.format(num));
        } else {
            StringBuffer append2 = buf.append(num);
        }
    }

    private static DecimalFormat createFormatter(int precision) {
        DecimalFormatSymbols decimalFormatSymbols;
        DecimalFormat decimalFormat;
        StringBuilder sb;
        new DecimalFormatSymbols();
        DecimalFormatSymbols symbols = decimalFormatSymbols;
        symbols.setDecimalSeparator('.');
        new StringBuilder();
        new DecimalFormat(sb.append("0.").append(StringUtil.chars('#', precision)).toString(), symbols);
        DecimalFormat format = decimalFormat;
        format.setDecimalSeparatorAlwaysShown(false);
        return format;
    }
}
