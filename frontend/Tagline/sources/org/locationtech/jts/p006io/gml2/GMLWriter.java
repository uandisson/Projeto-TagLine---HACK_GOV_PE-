package org.locationtech.jts.p006io.gml2;

import com.microsoft.appcenter.Constants;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryCollection;
import org.locationtech.jts.geom.LineString;
import org.locationtech.jts.geom.LinearRing;
import org.locationtech.jts.geom.MultiLineString;
import org.locationtech.jts.geom.MultiPoint;
import org.locationtech.jts.geom.MultiPolygon;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.Polygon;
import org.locationtech.jts.util.Assert;

/* renamed from: org.locationtech.jts.io.gml2.GMLWriter */
public class GMLWriter {
    private static final String coordinateSeparator = ",";
    private static final String tupleSeparator = " ";
    private final String INDENT = "  ";
    private String[] customElements = null;
    private boolean emitNamespace = false;
    private boolean isRootTag = false;
    private int maxCoordinatesPerLine = 10;
    private String namespace = GMLConstants.GML_NAMESPACE;
    private String prefix = GMLConstants.GML_PREFIX;
    private String srsName = null;
    private int startingIndentIndex = 0;

    public GMLWriter() {
    }

    public GMLWriter(boolean emitNamespace2) {
        setNamespace(emitNamespace2);
    }

    public void setPrefix(String prefix2) {
        String str = prefix2;
        this.prefix = str;
    }

    public void setSrsName(String srsName2) {
        String str = srsName2;
        this.srsName = str;
    }

    public void setNamespace(boolean emitNamespace2) {
        boolean z = emitNamespace2;
        this.emitNamespace = z;
    }

    public void setCustomElements(String[] customElements2) {
        String[] strArr = customElements2;
        this.customElements = strArr;
    }

    public void setStartingIndentIndex(int i) {
        int indent = i;
        if (indent < 0) {
            indent = 0;
        }
        this.startingIndentIndex = indent;
    }

    public void setMaxCoordinatesPerLine(int i) {
        Throwable th;
        int num = i;
        if (num < 1) {
            Throwable th2 = th;
            new IndexOutOfBoundsException("Invalid coordinate count per line, must be > 0");
            throw th2;
        }
        this.maxCoordinatesPerLine = num;
    }

    public String write(Geometry geom) {
        StringWriter stringWriter;
        new StringWriter();
        StringWriter writer = stringWriter;
        try {
            write(geom, writer);
        } catch (IOException e) {
            IOException iOException = e;
            Assert.shouldNeverReachHere();
        }
        return writer.toString();
    }

    public void write(Geometry geom, Writer writer) throws IOException {
        write(geom, writer, this.startingIndentIndex);
    }

    private void write(Geometry geometry, Writer writer, int i) throws IOException {
        Throwable th;
        StringBuilder sb;
        Geometry geom = geometry;
        Writer writer2 = writer;
        int level = i;
        this.isRootTag = true;
        if (geom instanceof Point) {
            writePoint((Point) geom, writer2, level);
        } else if (geom instanceof LineString) {
            writeLineString((LineString) geom, writer2, level);
        } else if (geom instanceof Polygon) {
            writePolygon((Polygon) geom, writer2, level);
        } else if (geom instanceof MultiPoint) {
            writeMultiPoint((MultiPoint) geom, writer2, level);
        } else if (geom instanceof MultiLineString) {
            writeMultiLineString((MultiLineString) geom, writer2, level);
        } else if (geom instanceof MultiPolygon) {
            writeMultiPolygon((MultiPolygon) geom, writer2, level);
        } else if (geom instanceof GeometryCollection) {
            writeGeometryCollection((GeometryCollection) geom, writer2, this.startingIndentIndex);
        } else {
            Throwable th2 = th;
            new StringBuilder();
            new IllegalArgumentException(sb.append("Unhandled geometry type: ").append(geom.getGeometryType()).toString());
            throw th2;
        }
        writer2.flush();
    }

    private void writePoint(Point point, Writer writer, int i) throws IOException {
        Point p = point;
        Writer writer2 = writer;
        int level = i;
        startLine(level, writer2);
        startGeomTag("Point", p, writer2);
        write(new Coordinate[]{p.getCoordinate()}, writer2, level + 1);
        startLine(level, writer2);
        endGeomTag("Point", writer2);
    }

    private void writeLineString(LineString lineString, Writer writer, int i) throws IOException {
        LineString ls = lineString;
        Writer writer2 = writer;
        int level = i;
        startLine(level, writer2);
        startGeomTag("LineString", ls, writer2);
        write(ls.getCoordinates(), writer2, level + 1);
        startLine(level, writer2);
        endGeomTag("LineString", writer2);
    }

    private void writeLinearRing(LinearRing linearRing, Writer writer, int i) throws IOException {
        LinearRing lr = linearRing;
        Writer writer2 = writer;
        int level = i;
        startLine(level, writer2);
        startGeomTag(GMLConstants.GML_LINEARRING, lr, writer2);
        write(lr.getCoordinates(), writer2, level + 1);
        startLine(level, writer2);
        endGeomTag(GMLConstants.GML_LINEARRING, writer2);
    }

    private void writePolygon(Polygon polygon, Writer writer, int i) throws IOException {
        Polygon p = polygon;
        Writer writer2 = writer;
        int level = i;
        startLine(level, writer2);
        startGeomTag("Polygon", p, writer2);
        startLine(level + 1, writer2);
        startGeomTag(GMLConstants.GML_OUTER_BOUNDARY_IS, (Geometry) null, writer2);
        writeLinearRing((LinearRing) p.getExteriorRing(), writer2, level + 2);
        startLine(level + 1, writer2);
        endGeomTag(GMLConstants.GML_OUTER_BOUNDARY_IS, writer2);
        for (int t = 0; t < p.getNumInteriorRing(); t++) {
            startLine(level + 1, writer2);
            startGeomTag(GMLConstants.GML_INNER_BOUNDARY_IS, (Geometry) null, writer2);
            writeLinearRing((LinearRing) p.getInteriorRingN(t), writer2, level + 2);
            startLine(level + 1, writer2);
            endGeomTag(GMLConstants.GML_INNER_BOUNDARY_IS, writer2);
        }
        startLine(level, writer2);
        endGeomTag("Polygon", writer2);
    }

    private void writeMultiPoint(MultiPoint multiPoint, Writer writer, int i) throws IOException {
        MultiPoint mp = multiPoint;
        Writer writer2 = writer;
        int level = i;
        startLine(level, writer2);
        startGeomTag("MultiPoint", mp, writer2);
        for (int t = 0; t < mp.getNumGeometries(); t++) {
            startLine(level + 1, writer2);
            startGeomTag(GMLConstants.GML_POINT_MEMBER, (Geometry) null, writer2);
            writePoint((Point) mp.getGeometryN(t), writer2, level + 2);
            startLine(level + 1, writer2);
            endGeomTag(GMLConstants.GML_POINT_MEMBER, writer2);
        }
        startLine(level, writer2);
        endGeomTag("MultiPoint", writer2);
    }

    private void writeMultiLineString(MultiLineString multiLineString, Writer writer, int i) throws IOException {
        MultiLineString mls = multiLineString;
        Writer writer2 = writer;
        int level = i;
        startLine(level, writer2);
        startGeomTag("MultiLineString", mls, writer2);
        for (int t = 0; t < mls.getNumGeometries(); t++) {
            startLine(level + 1, writer2);
            startGeomTag(GMLConstants.GML_LINESTRING_MEMBER, (Geometry) null, writer2);
            writeLineString((LineString) mls.getGeometryN(t), writer2, level + 2);
            startLine(level + 1, writer2);
            endGeomTag(GMLConstants.GML_LINESTRING_MEMBER, writer2);
        }
        startLine(level, writer2);
        endGeomTag("MultiLineString", writer2);
    }

    private void writeMultiPolygon(MultiPolygon multiPolygon, Writer writer, int i) throws IOException {
        MultiPolygon mp = multiPolygon;
        Writer writer2 = writer;
        int level = i;
        startLine(level, writer2);
        startGeomTag("MultiPolygon", mp, writer2);
        for (int t = 0; t < mp.getNumGeometries(); t++) {
            startLine(level + 1, writer2);
            startGeomTag(GMLConstants.GML_POLYGON_MEMBER, (Geometry) null, writer2);
            writePolygon((Polygon) mp.getGeometryN(t), writer2, level + 2);
            startLine(level + 1, writer2);
            endGeomTag(GMLConstants.GML_POLYGON_MEMBER, writer2);
        }
        startLine(level, writer2);
        endGeomTag("MultiPolygon", writer2);
    }

    private void writeGeometryCollection(GeometryCollection geometryCollection, Writer writer, int i) throws IOException {
        GeometryCollection gc = geometryCollection;
        Writer writer2 = writer;
        int level = i;
        startLine(level, writer2);
        startGeomTag(GMLConstants.GML_MULTI_GEOMETRY, gc, writer2);
        for (int t = 0; t < gc.getNumGeometries(); t++) {
            startLine(level + 1, writer2);
            startGeomTag(GMLConstants.GML_GEOMETRY_MEMBER, (Geometry) null, writer2);
            write(gc.getGeometryN(t), writer2, level + 2);
            startLine(level + 1, writer2);
            endGeomTag(GMLConstants.GML_GEOMETRY_MEMBER, writer2);
        }
        startLine(level, writer2);
        endGeomTag(GMLConstants.GML_MULTI_GEOMETRY, writer2);
    }

    private void write(Coordinate[] coordinateArr, Writer writer, int i) throws IOException {
        StringBuilder sb;
        StringBuilder sb2;
        StringBuilder sb3;
        StringBuilder sb4;
        StringBuilder sb5;
        Coordinate[] coords = coordinateArr;
        Writer writer2 = writer;
        int level = i;
        startLine(level, writer2);
        startGeomTag(GMLConstants.GML_COORDINATES, (Geometry) null, writer2);
        int dim = 2;
        if (coords.length > 0 && !Double.isNaN(coords[0].f414z)) {
            dim = 3;
        }
        boolean isNewLine = true;
        for (int i2 = 0; i2 < coords.length; i2++) {
            if (isNewLine) {
                startLine(level + 1, writer2);
                isNewLine = false;
            }
            if (dim == 2) {
                new StringBuilder();
                writer2.write(sb4.append("").append(coords[i2].f412x).toString());
                writer2.write(coordinateSeparator);
                new StringBuilder();
                writer2.write(sb5.append("").append(coords[i2].f413y).toString());
            } else if (dim == 3) {
                new StringBuilder();
                writer2.write(sb.append("").append(coords[i2].f412x).toString());
                writer2.write(coordinateSeparator);
                new StringBuilder();
                writer2.write(sb2.append("").append(coords[i2].f413y).toString());
                writer2.write(coordinateSeparator);
                new StringBuilder();
                writer2.write(sb3.append("").append(coords[i2].f414z).toString());
            }
            writer2.write(tupleSeparator);
            if ((i2 + 1) % this.maxCoordinatesPerLine == 0 && i2 < coords.length - 1) {
                writer2.write("\n");
                isNewLine = true;
            }
        }
        if (!isNewLine) {
            writer2.write("\n");
        }
        startLine(level, writer2);
        endGeomTag(GMLConstants.GML_COORDINATES, writer2);
    }

    private void startLine(int i, Writer writer) throws IOException {
        int level = i;
        Writer writer2 = writer;
        for (int i2 = 0; i2 < level; i2++) {
            writer2.write("  ");
        }
    }

    private void startGeomTag(String str, Geometry geometry, Writer writer) throws IOException {
        StringBuilder sb;
        String str2;
        StringBuilder sb2;
        String geometryName = str;
        Geometry g = geometry;
        Writer writer2 = writer;
        Writer writer3 = writer2;
        new StringBuilder();
        StringBuilder append = sb.append("<");
        if (this.prefix == null || "".equals(this.prefix)) {
            str2 = "";
        } else {
            new StringBuilder();
            str2 = sb2.append(this.prefix).append(Constants.COMMON_SCHEMA_PREFIX_SEPARATOR).toString();
        }
        writer3.write(append.append(str2).toString());
        writer2.write(geometryName);
        writeAttributes(g, writer2);
        writer2.write(">\n");
        writeCustomElements(g, writer2);
        this.isRootTag = false;
    }

    private void writeAttributes(Geometry geom, Writer writer) throws IOException {
        StringBuilder sb;
        StringBuilder sb2;
        String str;
        StringBuilder sb3;
        Writer writer2 = writer;
        if (geom != null && this.isRootTag) {
            if (this.emitNamespace) {
                Writer writer3 = writer2;
                new StringBuilder();
                StringBuilder append = sb2.append(" xmlns");
                if (this.prefix == null || "".equals(this.prefix)) {
                    str = "";
                } else {
                    new StringBuilder();
                    str = sb3.append(Constants.COMMON_SCHEMA_PREFIX_SEPARATOR).append(this.prefix).toString();
                }
                writer3.write(append.append(str).append("='").append(this.namespace).append("'").toString());
            }
            if (this.srsName != null && this.srsName.length() > 0) {
                new StringBuilder();
                writer2.write(sb.append(" srsName='").append(this.srsName).append("'").toString());
            }
        }
    }

    private void writeCustomElements(Geometry geom, Writer writer) throws IOException {
        Writer writer2 = writer;
        if (geom != null && this.isRootTag && this.customElements != null) {
            for (int i = 0; i < this.customElements.length; i++) {
                writer2.write(this.customElements[i]);
                writer2.write("\n");
            }
        }
    }

    private void endGeomTag(String geometryName, Writer writer) throws IOException {
        StringBuilder sb;
        Writer writer2 = writer;
        new StringBuilder();
        writer2.write(sb.append("</").append(prefix()).toString());
        writer2.write(geometryName);
        writer2.write(">\n");
    }

    private String prefix() {
        StringBuilder sb;
        if (this.prefix == null || this.prefix.length() == 0) {
            return "";
        }
        new StringBuilder();
        return sb.append(this.prefix).append(Constants.COMMON_SCHEMA_PREFIX_SEPARATOR).toString();
    }
}
