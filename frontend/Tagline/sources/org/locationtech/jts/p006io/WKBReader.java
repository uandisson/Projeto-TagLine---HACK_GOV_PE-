package org.locationtech.jts.p006io;

import gnu.expr.Declaration;
import java.io.IOException;
import org.locationtech.jts.geom.CoordinateSequence;
import org.locationtech.jts.geom.CoordinateSequenceFactory;
import org.locationtech.jts.geom.CoordinateSequences;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryCollection;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.LineString;
import org.locationtech.jts.geom.LinearRing;
import org.locationtech.jts.geom.MultiLineString;
import org.locationtech.jts.geom.MultiPoint;
import org.locationtech.jts.geom.MultiPolygon;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.Polygon;
import org.locationtech.jts.geom.PrecisionModel;

/* renamed from: org.locationtech.jts.io.WKBReader */
public class WKBReader {
    private static final String INVALID_GEOM_TYPE_MSG = "Invalid geometry type encountered in ";
    private int SRID;
    private CoordinateSequenceFactory csFactory;
    private ByteOrderDataInStream dis;
    private GeometryFactory factory;
    private boolean hasSRID;
    private int inputDimension;
    private boolean isStrict;
    private double[] ordValues;
    private PrecisionModel precisionModel;

    public static byte[] hexToBytes(String str) {
        Throwable th;
        String hex = str;
        byte[] bytes = new byte[(hex.length() / 2)];
        for (int i = 0; i < hex.length() / 2; i++) {
            int i2 = 2 * i;
            if (i2 + 1 > hex.length()) {
                Throwable th2 = th;
                new IllegalArgumentException("Hex string has odd length");
                throw th2;
            }
            bytes[i] = (byte) ((hexToInt(hex.charAt(i2)) << 4) + ((byte) hexToInt(hex.charAt(i2 + 1))));
        }
        return bytes;
    }

    private static int hexToInt(char c) {
        Throwable th;
        StringBuilder sb;
        char hex = c;
        int nib = Character.digit(hex, 16);
        if (nib >= 0) {
            return nib;
        }
        Throwable th2 = th;
        new StringBuilder();
        new IllegalArgumentException(sb.append("Invalid hex digit: '").append(hex).append("'").toString());
        throw th2;
    }

    /* JADX WARNING: Illegal instructions before constructor call */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public WKBReader() {
        /*
            r5 = this;
            r0 = r5
            r1 = r0
            org.locationtech.jts.geom.GeometryFactory r2 = new org.locationtech.jts.geom.GeometryFactory
            r4 = r2
            r2 = r4
            r3 = r4
            r3.<init>()
            r1.<init>(r2)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.locationtech.jts.p006io.WKBReader.<init>():void");
    }

    public WKBReader(GeometryFactory geometryFactory) {
        ByteOrderDataInStream byteOrderDataInStream;
        this.inputDimension = 2;
        this.hasSRID = false;
        this.SRID = 0;
        this.isStrict = false;
        new ByteOrderDataInStream();
        this.dis = byteOrderDataInStream;
        this.factory = geometryFactory;
        this.precisionModel = this.factory.getPrecisionModel();
        this.csFactory = this.factory.getCoordinateSequenceFactory();
    }

    public Geometry read(byte[] bytes) throws ParseException {
        Throwable th;
        StringBuilder sb;
        InStream inStream;
        try {
            new ByteArrayInStream(bytes);
            return read(inStream);
        } catch (IOException e) {
            IOException ex = e;
            Throwable th2 = th;
            new StringBuilder();
            new RuntimeException(sb.append("Unexpected IOException caught: ").append(ex.getMessage()).toString());
            throw th2;
        }
    }

    public Geometry read(InStream is) throws IOException, ParseException {
        this.dis.setInStream(is);
        return readGeometry();
    }

    private Geometry readGeometry() throws IOException, ParseException {
        Throwable th;
        StringBuilder sb;
        Geometry geom;
        Throwable th2;
        StringBuilder sb2;
        byte byteOrderWKB = this.dis.readByte();
        if (byteOrderWKB == 1) {
            this.dis.setOrder(2);
        } else if (byteOrderWKB == 0) {
            this.dis.setOrder(1);
        } else if (this.isStrict) {
            Throwable th3 = th;
            new StringBuilder();
            new ParseException(sb.append("Unknown geometry byte order (not NDR or XDR): ").append(byteOrderWKB).toString());
            throw th3;
        }
        int typeInt = this.dis.readInt();
        int geometryType = (typeInt & 65535) % 1000;
        this.inputDimension = 2 + ((typeInt & Integer.MIN_VALUE) != 0 || (typeInt & 65535) / 1000 == 1 || (typeInt & 65535) / 1000 == 3 ? 1 : 0) + ((typeInt & Declaration.MODULE_REFERENCE) != 0 || (typeInt & 65535) / 1000 == 2 || (typeInt & 65535) / 1000 == 3 ? 1 : 0);
        this.hasSRID = (typeInt & Declaration.EARLY_INIT) != 0;
        int SRID2 = 0;
        if (this.hasSRID) {
            SRID2 = this.dis.readInt();
        }
        if (this.ordValues == null || this.ordValues.length < this.inputDimension) {
            this.ordValues = new double[this.inputDimension];
        }
        switch (geometryType) {
            case 1:
                geom = readPoint();
                break;
            case 2:
                geom = readLineString();
                break;
            case 3:
                geom = readPolygon();
                break;
            case 4:
                geom = readMultiPoint();
                break;
            case 5:
                geom = readMultiLineString();
                break;
            case 6:
                geom = readMultiPolygon();
                break;
            case 7:
                geom = readGeometryCollection();
                break;
            default:
                Throwable th4 = th2;
                new StringBuilder();
                new ParseException(sb2.append("Unknown WKB type ").append(geometryType).toString());
                throw th4;
        }
        Geometry srid = setSRID(geom, SRID2);
        return geom;
    }

    private Geometry setSRID(Geometry geometry, int i) {
        Geometry g = geometry;
        int SRID2 = i;
        if (SRID2 != 0) {
            g.setSRID(SRID2);
        }
        return g;
    }

    private Point readPoint() throws IOException {
        return this.factory.createPoint(readCoordinateSequence(1));
    }

    private LineString readLineString() throws IOException {
        return this.factory.createLineString(readCoordinateSequenceLineString(this.dis.readInt()));
    }

    private LinearRing readLinearRing() throws IOException {
        return this.factory.createLinearRing(readCoordinateSequenceRing(this.dis.readInt()));
    }

    private Polygon readPolygon() throws IOException {
        int numRings = this.dis.readInt();
        LinearRing[] holes = null;
        if (numRings > 1) {
            holes = new LinearRing[(numRings - 1)];
        }
        LinearRing shell = readLinearRing();
        for (int i = 0; i < numRings - 1; i++) {
            holes[i] = readLinearRing();
        }
        return this.factory.createPolygon(shell, holes);
    }

    private MultiPoint readMultiPoint() throws IOException, ParseException {
        Throwable th;
        int numGeom = this.dis.readInt();
        Point[] geoms = new Point[numGeom];
        for (int i = 0; i < numGeom; i++) {
            Geometry g = readGeometry();
            if (!(g instanceof Point)) {
                Throwable th2 = th;
                new ParseException("Invalid geometry type encountered in MultiPoint");
                throw th2;
            }
            geoms[i] = (Point) g;
        }
        return this.factory.createMultiPoint(geoms);
    }

    private MultiLineString readMultiLineString() throws IOException, ParseException {
        Throwable th;
        int numGeom = this.dis.readInt();
        LineString[] geoms = new LineString[numGeom];
        for (int i = 0; i < numGeom; i++) {
            Geometry g = readGeometry();
            if (!(g instanceof LineString)) {
                Throwable th2 = th;
                new ParseException("Invalid geometry type encountered in MultiLineString");
                throw th2;
            }
            geoms[i] = (LineString) g;
        }
        return this.factory.createMultiLineString(geoms);
    }

    private MultiPolygon readMultiPolygon() throws IOException, ParseException {
        Throwable th;
        int numGeom = this.dis.readInt();
        Polygon[] geoms = new Polygon[numGeom];
        for (int i = 0; i < numGeom; i++) {
            Geometry g = readGeometry();
            if (!(g instanceof Polygon)) {
                Throwable th2 = th;
                new ParseException("Invalid geometry type encountered in MultiPolygon");
                throw th2;
            }
            geoms[i] = (Polygon) g;
        }
        return this.factory.createMultiPolygon(geoms);
    }

    private GeometryCollection readGeometryCollection() throws IOException, ParseException {
        int numGeom = this.dis.readInt();
        Geometry[] geoms = new Geometry[numGeom];
        for (int i = 0; i < numGeom; i++) {
            geoms[i] = readGeometry();
        }
        return this.factory.createGeometryCollection(geoms);
    }

    private CoordinateSequence readCoordinateSequence(int i) throws IOException {
        int size = i;
        CoordinateSequence seq = this.csFactory.create(size, this.inputDimension);
        int targetDim = seq.getDimension();
        if (targetDim > this.inputDimension) {
            targetDim = this.inputDimension;
        }
        for (int i2 = 0; i2 < size; i2++) {
            readCoordinate();
            for (int j = 0; j < targetDim; j++) {
                seq.setOrdinate(i2, j, this.ordValues[j]);
            }
        }
        return seq;
    }

    private CoordinateSequence readCoordinateSequenceLineString(int size) throws IOException {
        CoordinateSequence seq = readCoordinateSequence(size);
        if (this.isStrict) {
            return seq;
        }
        if (seq.size() == 0 || seq.size() >= 2) {
            return seq;
        }
        return CoordinateSequences.extend(this.csFactory, seq, 2);
    }

    private CoordinateSequence readCoordinateSequenceRing(int size) throws IOException {
        CoordinateSequence seq = readCoordinateSequence(size);
        if (this.isStrict) {
            return seq;
        }
        if (CoordinateSequences.isRing(seq)) {
            return seq;
        }
        return CoordinateSequences.ensureValidRing(this.csFactory, seq);
    }

    private void readCoordinate() throws IOException {
        for (int i = 0; i < this.inputDimension; i++) {
            if (i <= 1) {
                this.ordValues[i] = this.precisionModel.makePrecise(this.dis.readDouble());
            } else {
                this.ordValues[i] = this.dis.readDouble();
            }
        }
    }
}
