package org.locationtech.jts.p006io;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.CoordinateSequence;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryCollection;
import org.locationtech.jts.geom.LineString;
import org.locationtech.jts.geom.LinearRing;
import org.locationtech.jts.geom.MultiLineString;
import org.locationtech.jts.geom.MultiPoint;
import org.locationtech.jts.geom.MultiPolygon;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.Polygon;
import org.locationtech.jts.geom.PrecisionModel;
import org.locationtech.jts.util.Assert;

/* renamed from: org.locationtech.jts.io.WKTWriter */
public class WKTWriter {
    private static final int INDENT = 2;
    private int coordsPerLine = -1;
    private DecimalFormat formatter;
    private String indentTabStr = "  ";
    private boolean isFormatted = false;
    private int level = 0;
    private int outputDimension = 2;
    private boolean useFormatting = false;

    public static String toPoint(Coordinate coordinate) {
        StringBuilder sb;
        Coordinate p0 = coordinate;
        new StringBuilder();
        return sb.append("POINT ( ").append(p0.f412x).append(" ").append(p0.f413y).append(" )").toString();
    }

    public static String toLineString(CoordinateSequence coordinateSequence) {
        StringBuffer stringBuffer;
        StringBuilder sb;
        CoordinateSequence seq = coordinateSequence;
        new StringBuffer();
        StringBuffer buf = stringBuffer;
        StringBuffer append = buf.append("LINESTRING ");
        if (seq.size() == 0) {
            StringBuffer append2 = buf.append(" EMPTY");
        } else {
            StringBuffer append3 = buf.append("(");
            for (int i = 0; i < seq.size(); i++) {
                if (i > 0) {
                    StringBuffer append4 = buf.append(", ");
                }
                new StringBuilder();
                StringBuffer append5 = buf.append(sb.append(seq.getX(i)).append(" ").append(seq.getY(i)).toString());
            }
            StringBuffer append6 = buf.append(")");
        }
        return buf.toString();
    }

    public static String toLineString(Coordinate[] coordinateArr) {
        StringBuffer stringBuffer;
        StringBuilder sb;
        Coordinate[] coord = coordinateArr;
        new StringBuffer();
        StringBuffer buf = stringBuffer;
        StringBuffer append = buf.append("LINESTRING ");
        if (coord.length == 0) {
            StringBuffer append2 = buf.append(" EMPTY");
        } else {
            StringBuffer append3 = buf.append("(");
            for (int i = 0; i < coord.length; i++) {
                if (i > 0) {
                    StringBuffer append4 = buf.append(", ");
                }
                new StringBuilder();
                StringBuffer append5 = buf.append(sb.append(coord[i].f412x).append(" ").append(coord[i].f413y).toString());
            }
            StringBuffer append6 = buf.append(")");
        }
        return buf.toString();
    }

    public static String toLineString(Coordinate coordinate, Coordinate coordinate2) {
        StringBuilder sb;
        Coordinate p0 = coordinate;
        Coordinate p1 = coordinate2;
        new StringBuilder();
        return sb.append("LINESTRING ( ").append(p0.f412x).append(" ").append(p0.f413y).append(", ").append(p1.f412x).append(" ").append(p1.f413y).append(" )").toString();
    }

    private static DecimalFormat createFormatter(PrecisionModel precisionModel) {
        DecimalFormatSymbols decimalFormatSymbols;
        StringBuilder sb;
        DecimalFormat decimalFormat;
        int decimalPlaces = precisionModel.getMaximumSignificantDigits();
        new DecimalFormatSymbols();
        DecimalFormatSymbols symbols = decimalFormatSymbols;
        symbols.setDecimalSeparator('.');
        new StringBuilder();
        new DecimalFormat(sb.append("0").append(decimalPlaces > 0 ? "." : "").append(stringOfChar('#', decimalPlaces)).toString(), symbols);
        return decimalFormat;
    }

    public static String stringOfChar(char c, int i) {
        StringBuffer stringBuffer;
        char ch = c;
        int count = i;
        new StringBuffer();
        StringBuffer buf = stringBuffer;
        for (int i2 = 0; i2 < count; i2++) {
            StringBuffer append = buf.append(ch);
        }
        return buf.toString();
    }

    public WKTWriter() {
    }

    public WKTWriter(int i) {
        Throwable th;
        int outputDimension2 = i;
        this.outputDimension = outputDimension2;
        if (outputDimension2 < 2 || outputDimension2 > 3) {
            Throwable th2 = th;
            new IllegalArgumentException("Invalid output dimension (must be 2 or 3)");
            throw th2;
        }
    }

    public void setFormatted(boolean isFormatted2) {
        boolean z = isFormatted2;
        this.isFormatted = z;
    }

    public void setMaxCoordinatesPerLine(int coordsPerLine2) {
        int i = coordsPerLine2;
        this.coordsPerLine = i;
    }

    public void setTab(int i) {
        Throwable th;
        int size = i;
        if (size <= 0) {
            Throwable th2 = th;
            new IllegalArgumentException("Tab count must be positive");
            throw th2;
        }
        this.indentTabStr = stringOfChar(' ', size);
    }

    public String write(Geometry geometry) {
        Writer writer;
        new StringWriter();
        Writer sw = writer;
        try {
            writeFormatted(geometry, this.isFormatted, sw);
        } catch (IOException e) {
            IOException iOException = e;
            Assert.shouldNeverReachHere();
        }
        return sw.toString();
    }

    public void write(Geometry geometry, Writer writer) throws IOException {
        writeFormatted(geometry, false, writer);
    }

    public String writeFormatted(Geometry geometry) {
        Writer writer;
        new StringWriter();
        Writer sw = writer;
        try {
            writeFormatted(geometry, true, sw);
        } catch (IOException e) {
            IOException iOException = e;
            Assert.shouldNeverReachHere();
        }
        return sw.toString();
    }

    public void writeFormatted(Geometry geometry, Writer writer) throws IOException {
        writeFormatted(geometry, true, writer);
    }

    private void writeFormatted(Geometry geometry, boolean useFormatting2, Writer writer) throws IOException {
        Geometry geometry2 = geometry;
        this.useFormatting = useFormatting2;
        this.formatter = createFormatter(geometry2.getPrecisionModel());
        appendGeometryTaggedText(geometry2, 0, writer);
    }

    private void appendGeometryTaggedText(Geometry geometry, int i, Writer writer) throws IOException {
        StringBuilder sb;
        Geometry geometry2 = geometry;
        int level2 = i;
        Writer writer2 = writer;
        indent(level2, writer2);
        if (geometry2 instanceof Point) {
            Point point = (Point) geometry2;
            appendPointTaggedText(point.getCoordinate(), level2, writer2, point.getPrecisionModel());
        } else if (geometry2 instanceof LinearRing) {
            appendLinearRingTaggedText((LinearRing) geometry2, level2, writer2);
        } else if (geometry2 instanceof LineString) {
            appendLineStringTaggedText((LineString) geometry2, level2, writer2);
        } else if (geometry2 instanceof Polygon) {
            appendPolygonTaggedText((Polygon) geometry2, level2, writer2);
        } else if (geometry2 instanceof MultiPoint) {
            appendMultiPointTaggedText((MultiPoint) geometry2, level2, writer2);
        } else if (geometry2 instanceof MultiLineString) {
            appendMultiLineStringTaggedText((MultiLineString) geometry2, level2, writer2);
        } else if (geometry2 instanceof MultiPolygon) {
            appendMultiPolygonTaggedText((MultiPolygon) geometry2, level2, writer2);
        } else if (geometry2 instanceof GeometryCollection) {
            appendGeometryCollectionTaggedText((GeometryCollection) geometry2, level2, writer2);
        } else {
            new StringBuilder();
            Assert.shouldNeverReachHere(sb.append("Unsupported Geometry implementation:").append(geometry2.getClass()).toString());
        }
    }

    private void appendPointTaggedText(Coordinate coordinate, int level2, Writer writer, PrecisionModel precisionModel) throws IOException {
        Writer writer2 = writer;
        writer2.write("POINT ");
        appendPointText(coordinate, level2, writer2, precisionModel);
    }

    private void appendLineStringTaggedText(LineString lineString, int level2, Writer writer) throws IOException {
        Writer writer2 = writer;
        writer2.write("LINESTRING ");
        appendLineStringText(lineString, level2, false, writer2);
    }

    private void appendLinearRingTaggedText(LinearRing linearRing, int level2, Writer writer) throws IOException {
        Writer writer2 = writer;
        writer2.write("LINEARRING ");
        appendLineStringText(linearRing, level2, false, writer2);
    }

    private void appendPolygonTaggedText(Polygon polygon, int level2, Writer writer) throws IOException {
        Writer writer2 = writer;
        writer2.write("POLYGON ");
        appendPolygonText(polygon, level2, false, writer2);
    }

    private void appendMultiPointTaggedText(MultiPoint multipoint, int level2, Writer writer) throws IOException {
        Writer writer2 = writer;
        writer2.write("MULTIPOINT ");
        appendMultiPointText(multipoint, level2, writer2);
    }

    private void appendMultiLineStringTaggedText(MultiLineString multiLineString, int level2, Writer writer) throws IOException {
        Writer writer2 = writer;
        writer2.write("MULTILINESTRING ");
        appendMultiLineStringText(multiLineString, level2, false, writer2);
    }

    private void appendMultiPolygonTaggedText(MultiPolygon multiPolygon, int level2, Writer writer) throws IOException {
        Writer writer2 = writer;
        writer2.write("MULTIPOLYGON ");
        appendMultiPolygonText(multiPolygon, level2, writer2);
    }

    private void appendGeometryCollectionTaggedText(GeometryCollection geometryCollection, int level2, Writer writer) throws IOException {
        Writer writer2 = writer;
        writer2.write("GEOMETRYCOLLECTION ");
        appendGeometryCollectionText(geometryCollection, level2, writer2);
    }

    private void appendPointText(Coordinate coordinate, int i, Writer writer, PrecisionModel precisionModel) throws IOException {
        Coordinate coordinate2 = coordinate;
        int i2 = i;
        Writer writer2 = writer;
        PrecisionModel precisionModel2 = precisionModel;
        if (coordinate2 == null) {
            writer2.write("EMPTY");
            return;
        }
        writer2.write("(");
        appendCoordinate(coordinate2, writer2);
        writer2.write(")");
    }

    private void appendCoordinate(CoordinateSequence coordinateSequence, int i, Writer writer) throws IOException {
        StringBuilder sb;
        CoordinateSequence seq = coordinateSequence;
        int i2 = i;
        Writer writer2 = writer;
        new StringBuilder();
        writer2.write(sb.append(writeNumber(seq.getX(i2))).append(" ").append(writeNumber(seq.getY(i2))).toString());
        if (this.outputDimension >= 3 && seq.getDimension() >= 3) {
            double z = seq.getOrdinate(i2, 3);
            if (!Double.isNaN(z)) {
                writer2.write(" ");
                writer2.write(writeNumber(z));
            }
        }
    }

    private void appendCoordinate(Coordinate coordinate, Writer writer) throws IOException {
        StringBuilder sb;
        Coordinate coordinate2 = coordinate;
        Writer writer2 = writer;
        new StringBuilder();
        writer2.write(sb.append(writeNumber(coordinate2.f412x)).append(" ").append(writeNumber(coordinate2.f413y)).toString());
        if (this.outputDimension >= 3 && !Double.isNaN(coordinate2.f414z)) {
            writer2.write(" ");
            writer2.write(writeNumber(coordinate2.f414z));
        }
    }

    private String writeNumber(double d) {
        return this.formatter.format(d);
    }

    private void appendSequenceText(CoordinateSequence coordinateSequence, int i, boolean z, Writer writer) throws IOException {
        CoordinateSequence seq = coordinateSequence;
        int level2 = i;
        boolean doIndent = z;
        Writer writer2 = writer;
        if (seq.size() == 0) {
            writer2.write("EMPTY");
            return;
        }
        if (doIndent) {
            indent(level2, writer2);
        }
        writer2.write("(");
        for (int i2 = 0; i2 < seq.size(); i2++) {
            if (i2 > 0) {
                writer2.write(", ");
                if (this.coordsPerLine > 0 && i2 % this.coordsPerLine == 0) {
                    indent(level2 + 1, writer2);
                }
            }
            appendCoordinate(seq, i2, writer2);
        }
        writer2.write(")");
    }

    private void appendLineStringText(LineString lineString, int i, boolean z, Writer writer) throws IOException {
        LineString lineString2 = lineString;
        int level2 = i;
        boolean doIndent = z;
        Writer writer2 = writer;
        if (lineString2.isEmpty()) {
            writer2.write("EMPTY");
            return;
        }
        if (doIndent) {
            indent(level2, writer2);
        }
        writer2.write("(");
        for (int i2 = 0; i2 < lineString2.getNumPoints(); i2++) {
            if (i2 > 0) {
                writer2.write(", ");
                if (this.coordsPerLine > 0 && i2 % this.coordsPerLine == 0) {
                    indent(level2 + 1, writer2);
                }
            }
            appendCoordinate(lineString2.getCoordinateN(i2), writer2);
        }
        writer2.write(")");
    }

    private void appendPolygonText(Polygon polygon, int i, boolean z, Writer writer) throws IOException {
        Polygon polygon2 = polygon;
        int level2 = i;
        boolean indentFirst = z;
        Writer writer2 = writer;
        if (polygon2.isEmpty()) {
            writer2.write("EMPTY");
            return;
        }
        if (indentFirst) {
            indent(level2, writer2);
        }
        writer2.write("(");
        appendLineStringText(polygon2.getExteriorRing(), level2, false, writer2);
        for (int i2 = 0; i2 < polygon2.getNumInteriorRing(); i2++) {
            writer2.write(", ");
            appendLineStringText(polygon2.getInteriorRingN(i2), level2 + 1, true, writer2);
        }
        writer2.write(")");
    }

    private void appendMultiPointText(MultiPoint multiPoint, int i, Writer writer) throws IOException {
        MultiPoint multiPoint2 = multiPoint;
        int level2 = i;
        Writer writer2 = writer;
        if (multiPoint2.isEmpty()) {
            writer2.write("EMPTY");
            return;
        }
        writer2.write("(");
        for (int i2 = 0; i2 < multiPoint2.getNumGeometries(); i2++) {
            if (i2 > 0) {
                writer2.write(", ");
                indentCoords(i2, level2 + 1, writer2);
            }
            writer2.write("(");
            appendCoordinate(((Point) multiPoint2.getGeometryN(i2)).getCoordinate(), writer2);
            writer2.write(")");
        }
        writer2.write(")");
    }

    private void appendMultiLineStringText(MultiLineString multiLineString, int i, boolean z, Writer writer) throws IOException {
        MultiLineString multiLineString2 = multiLineString;
        int level2 = i;
        boolean indentFirst = z;
        Writer writer2 = writer;
        if (multiLineString2.isEmpty()) {
            writer2.write("EMPTY");
            return;
        }
        int level22 = level2;
        boolean doIndent = indentFirst;
        writer2.write("(");
        for (int i2 = 0; i2 < multiLineString2.getNumGeometries(); i2++) {
            if (i2 > 0) {
                writer2.write(", ");
                level22 = level2 + 1;
                doIndent = true;
            }
            appendLineStringText((LineString) multiLineString2.getGeometryN(i2), level22, doIndent, writer2);
        }
        writer2.write(")");
    }

    private void appendMultiPolygonText(MultiPolygon multiPolygon, int i, Writer writer) throws IOException {
        MultiPolygon multiPolygon2 = multiPolygon;
        int level2 = i;
        Writer writer2 = writer;
        if (multiPolygon2.isEmpty()) {
            writer2.write("EMPTY");
            return;
        }
        int level22 = level2;
        boolean doIndent = false;
        writer2.write("(");
        for (int i2 = 0; i2 < multiPolygon2.getNumGeometries(); i2++) {
            if (i2 > 0) {
                writer2.write(", ");
                level22 = level2 + 1;
                doIndent = true;
            }
            appendPolygonText((Polygon) multiPolygon2.getGeometryN(i2), level22, doIndent, writer2);
        }
        writer2.write(")");
    }

    private void appendGeometryCollectionText(GeometryCollection geometryCollection, int i, Writer writer) throws IOException {
        GeometryCollection geometryCollection2 = geometryCollection;
        int level2 = i;
        Writer writer2 = writer;
        if (geometryCollection2.isEmpty()) {
            writer2.write("EMPTY");
            return;
        }
        int level22 = level2;
        writer2.write("(");
        for (int i2 = 0; i2 < geometryCollection2.getNumGeometries(); i2++) {
            if (i2 > 0) {
                writer2.write(", ");
                level22 = level2 + 1;
            }
            appendGeometryTaggedText(geometryCollection2.getGeometryN(i2), level22, writer2);
        }
        writer2.write(")");
    }

    private void indentCoords(int i, int i2, Writer writer) throws IOException {
        int coordIndex = i;
        int level2 = i2;
        Writer writer2 = writer;
        if (this.coordsPerLine > 0 && coordIndex % this.coordsPerLine == 0) {
            indent(level2, writer2);
        }
    }

    private void indent(int i, Writer writer) throws IOException {
        int level2 = i;
        Writer writer2 = writer;
        if (this.useFormatting && level2 > 0) {
            writer2.write("\n");
            for (int i2 = 0; i2 < level2; i2++) {
                writer2.write(this.indentTabStr);
            }
        }
    }
}
