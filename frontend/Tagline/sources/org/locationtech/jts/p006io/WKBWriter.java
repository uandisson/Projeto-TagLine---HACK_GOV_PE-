package org.locationtech.jts.p006io;

import gnu.expr.Declaration;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import org.locationtech.jts.geom.CoordinateSequence;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryCollection;
import org.locationtech.jts.geom.LineString;
import org.locationtech.jts.geom.MultiLineString;
import org.locationtech.jts.geom.MultiPoint;
import org.locationtech.jts.geom.MultiPolygon;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.Polygon;
import org.locationtech.jts.util.Assert;

/* renamed from: org.locationtech.jts.io.WKBWriter */
public class WKBWriter {
    private byte[] buf;
    private ByteArrayOutputStream byteArrayOS;
    private OutStream byteArrayOutStream;
    private int byteOrder;
    private boolean includeSRID;
    private int outputDimension;

    public static String bytesToHex(byte[] bytes) {
        return toHex(bytes);
    }

    public static String toHex(byte[] bArr) {
        StringBuffer stringBuffer;
        byte[] bytes = bArr;
        new StringBuffer();
        StringBuffer buf2 = stringBuffer;
        for (int i = 0; i < bytes.length; i++) {
            byte b = bytes[i];
            StringBuffer append = buf2.append(toHexDigit((b >> 4) & 15));
            StringBuffer append2 = buf2.append(toHexDigit(b & 15));
        }
        return buf2.toString();
    }

    private static char toHexDigit(int i) {
        Throwable th;
        StringBuilder sb;
        int n = i;
        if (n < 0 || n > 15) {
            Throwable th2 = th;
            new StringBuilder();
            new IllegalArgumentException(sb.append("Nibble value out of range: ").append(n).toString());
            throw th2;
        } else if (n <= 9) {
            return (char) (48 + n);
        } else {
            return (char) (65 + (n - 10));
        }
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public WKBWriter() {
        this(2, 1);
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public WKBWriter(int outputDimension2) {
        this(outputDimension2, 1);
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public WKBWriter(int outputDimension2, boolean includeSRID2) {
        this(outputDimension2, 1, includeSRID2);
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public WKBWriter(int outputDimension2, int byteOrder2) {
        this(outputDimension2, byteOrder2, false);
    }

    public WKBWriter(int i, int byteOrder2, boolean includeSRID2) {
        ByteArrayOutputStream byteArrayOutputStream;
        OutStream outStream;
        Throwable th;
        int outputDimension2 = i;
        this.outputDimension = 2;
        this.includeSRID = false;
        new ByteArrayOutputStream();
        this.byteArrayOS = byteArrayOutputStream;
        new OutputStreamOutStream(this.byteArrayOS);
        this.byteArrayOutStream = outStream;
        this.buf = new byte[8];
        this.outputDimension = outputDimension2;
        this.byteOrder = byteOrder2;
        this.includeSRID = includeSRID2;
        if (outputDimension2 < 2 || outputDimension2 > 3) {
            Throwable th2 = th;
            new IllegalArgumentException("Output dimension must be 2 or 3");
            throw th2;
        }
    }

    public byte[] write(Geometry geometry) {
        Throwable th;
        StringBuilder sb;
        Geometry geom = geometry;
        try {
            this.byteArrayOS.reset();
            write(geom, this.byteArrayOutStream);
            return this.byteArrayOS.toByteArray();
        } catch (IOException e) {
            IOException ex = e;
            Throwable th2 = th;
            new StringBuilder();
            new RuntimeException(sb.append("Unexpected IO exception: ").append(ex.getMessage()).toString());
            throw th2;
        }
    }

    public void write(Geometry geometry, OutStream outStream) throws IOException {
        Geometry geom = geometry;
        OutStream os = outStream;
        if (geom instanceof Point) {
            writePoint((Point) geom, os);
        } else if (geom instanceof LineString) {
            writeLineString((LineString) geom, os);
        } else if (geom instanceof Polygon) {
            writePolygon((Polygon) geom, os);
        } else if (geom instanceof MultiPoint) {
            writeGeometryCollection(4, (MultiPoint) geom, os);
        } else if (geom instanceof MultiLineString) {
            writeGeometryCollection(5, (MultiLineString) geom, os);
        } else if (geom instanceof MultiPolygon) {
            writeGeometryCollection(6, (MultiPolygon) geom, os);
        } else if (geom instanceof GeometryCollection) {
            writeGeometryCollection(7, (GeometryCollection) geom, os);
        } else {
            Assert.shouldNeverReachHere("Unknown Geometry type");
        }
    }

    private void writePoint(Point point, OutStream outStream) throws IOException {
        Throwable th;
        Point pt = point;
        OutStream os = outStream;
        if (pt.getCoordinateSequence().size() == 0) {
            Throwable th2 = th;
            new IllegalArgumentException("Empty Points cannot be represented in WKB");
            throw th2;
        }
        writeByteOrder(os);
        writeGeometryType(1, pt, os);
        writeCoordinateSequence(pt.getCoordinateSequence(), false, os);
    }

    private void writeLineString(LineString lineString, OutStream outStream) throws IOException {
        LineString line = lineString;
        OutStream os = outStream;
        writeByteOrder(os);
        writeGeometryType(2, line, os);
        writeCoordinateSequence(line.getCoordinateSequence(), true, os);
    }

    private void writePolygon(Polygon polygon, OutStream outStream) throws IOException {
        Polygon poly = polygon;
        OutStream os = outStream;
        writeByteOrder(os);
        writeGeometryType(3, poly, os);
        writeInt(poly.getNumInteriorRing() + 1, os);
        writeCoordinateSequence(poly.getExteriorRing().getCoordinateSequence(), true, os);
        for (int i = 0; i < poly.getNumInteriorRing(); i++) {
            writeCoordinateSequence(poly.getInteriorRingN(i).getCoordinateSequence(), true, os);
        }
    }

    private void writeGeometryCollection(int geometryType, GeometryCollection geometryCollection, OutStream outStream) throws IOException {
        GeometryCollection gc = geometryCollection;
        OutStream os = outStream;
        writeByteOrder(os);
        writeGeometryType(geometryType, gc, os);
        writeInt(gc.getNumGeometries(), os);
        for (int i = 0; i < gc.getNumGeometries(); i++) {
            write(gc.getGeometryN(i), os);
        }
    }

    private void writeByteOrder(OutStream outStream) throws IOException {
        OutStream os = outStream;
        if (this.byteOrder == 2) {
            this.buf[0] = 1;
        } else {
            this.buf[0] = 0;
        }
        os.write(this.buf, 1);
    }

    private void writeGeometryType(int geometryType, Geometry geometry, OutStream outStream) throws IOException {
        Geometry g = geometry;
        OutStream os = outStream;
        writeInt(geometryType | (this.outputDimension == 3 ? Integer.MIN_VALUE : 0) | (this.includeSRID ? Declaration.EARLY_INIT : 0), os);
        if (this.includeSRID) {
            writeInt(g.getSRID(), os);
        }
    }

    private void writeInt(int intValue, OutStream os) throws IOException {
        ByteOrderValues.putInt(intValue, this.buf, this.byteOrder);
        os.write(this.buf, 4);
    }

    private void writeCoordinateSequence(CoordinateSequence coordinateSequence, boolean writeSize, OutStream outStream) throws IOException {
        CoordinateSequence seq = coordinateSequence;
        OutStream os = outStream;
        if (writeSize) {
            writeInt(seq.size(), os);
        }
        for (int i = 0; i < seq.size(); i++) {
            writeCoordinate(seq, i, os);
        }
    }

    private void writeCoordinate(CoordinateSequence coordinateSequence, int i, OutStream outStream) throws IOException {
        CoordinateSequence seq = coordinateSequence;
        int index = i;
        OutStream os = outStream;
        ByteOrderValues.putDouble(seq.getX(index), this.buf, this.byteOrder);
        os.write(this.buf, 8);
        ByteOrderValues.putDouble(seq.getY(index), this.buf, this.byteOrder);
        os.write(this.buf, 8);
        if (this.outputDimension >= 3) {
            double ordVal = Double.NaN;
            if (seq.getDimension() >= 3) {
                ordVal = seq.getOrdinate(index, 2);
            }
            ByteOrderValues.putDouble(ordVal, this.buf, this.byteOrder);
            os.write(this.buf, 8);
        }
    }
}
