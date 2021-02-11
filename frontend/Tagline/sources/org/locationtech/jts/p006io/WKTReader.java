package org.locationtech.jts.p006io;

import com.google.appinventor.components.common.ComponentConstants;
import java.io.IOException;
import java.io.Reader;
import java.io.StreamTokenizer;
import java.io.StringReader;
import java.util.ArrayList;
import org.locationtech.jts.geom.Coordinate;
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
import org.locationtech.jts.p006io.gml2.GMLConstants;
import org.locationtech.jts.util.Assert;

/* renamed from: org.locationtech.jts.io.WKTReader */
public class WKTReader {
    private static final boolean ALLOW_OLD_JTS_MULTIPOINT_SYNTAX = true;
    private static final String COMMA = ",";
    private static final String EMPTY = "EMPTY";
    private static final String L_PAREN = "(";
    private static final String NAN_SYMBOL = "NaN";
    private static final String R_PAREN = ")";
    private GeometryFactory geometryFactory;

    /* renamed from: m */
    private boolean f448m;
    private PrecisionModel precisionModel;
    private StreamTokenizer tokenizer;

    /* renamed from: z */
    private boolean f449z;

    /* JADX WARNING: Illegal instructions before constructor call */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public WKTReader() {
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
        throw new UnsupportedOperationException("Method not decompiled: org.locationtech.jts.p006io.WKTReader.<init>():void");
    }

    public WKTReader(GeometryFactory geometryFactory2) {
        GeometryFactory geometryFactory3 = geometryFactory2;
        this.geometryFactory = geometryFactory3;
        this.precisionModel = geometryFactory3.getPrecisionModel();
    }

    /* JADX INFO: finally extract failed */
    public Geometry read(String wellKnownText) throws ParseException {
        StringReader stringReader;
        new StringReader(wellKnownText);
        StringReader reader = stringReader;
        try {
            Geometry read = read((Reader) reader);
            reader.close();
            return read;
        } catch (Throwable th) {
            Throwable th2 = th;
            reader.close();
            throw th2;
        }
    }

    public Geometry read(Reader reader) throws ParseException {
        StreamTokenizer streamTokenizer;
        Throwable th;
        new StreamTokenizer(reader);
        this.tokenizer = streamTokenizer;
        this.tokenizer.resetSyntax();
        this.tokenizer.wordChars(97, 122);
        this.tokenizer.wordChars(65, 90);
        this.tokenizer.wordChars(ComponentConstants.TEXTBOX_PREFERRED_WIDTH, 255);
        this.tokenizer.wordChars(48, 57);
        this.tokenizer.wordChars(45, 45);
        this.tokenizer.wordChars(43, 43);
        this.tokenizer.wordChars(46, 46);
        this.tokenizer.whitespaceChars(0, 32);
        this.tokenizer.commentChar(35);
        this.f449z = false;
        this.f448m = false;
        try {
            return readGeometryTaggedText();
        } catch (IOException e) {
            IOException e2 = e;
            Throwable th2 = th;
            new ParseException(e2.toString());
            throw th2;
        }
    }

    private Coordinate[] getCoordinates() throws IOException, ParseException {
        ArrayList arrayList;
        if (getNextEmptyOrOpener().equals(EMPTY)) {
            return new Coordinate[0];
        }
        new ArrayList();
        ArrayList coordinates = arrayList;
        boolean add = coordinates.add(getPreciseCoordinate());
        for (String nextToken = getNextCloserOrComma(); nextToken.equals(COMMA); nextToken = getNextCloserOrComma()) {
            boolean add2 = coordinates.add(getPreciseCoordinate());
        }
        return (Coordinate[]) coordinates.toArray(new Coordinate[coordinates.size()]);
    }

    private Coordinate[] getCoordinatesNoLeftParen() throws IOException, ParseException {
        ArrayList arrayList;
        new ArrayList();
        ArrayList coordinates = arrayList;
        boolean add = coordinates.add(getPreciseCoordinate());
        for (String nextToken = getNextCloserOrComma(); nextToken.equals(COMMA); nextToken = getNextCloserOrComma()) {
            boolean add2 = coordinates.add(getPreciseCoordinate());
        }
        return (Coordinate[]) coordinates.toArray(new Coordinate[coordinates.size()]);
    }

    private Coordinate getPreciseCoordinate() throws IOException, ParseException {
        Coordinate coordinate;
        new Coordinate();
        Coordinate coord = coordinate;
        coord.f412x = getNextNumber();
        coord.f413y = getNextNumber();
        if (isNumberNext()) {
            coord.f414z = getNextNumber();
        }
        if (isNumberNext()) {
            double nextNumber = getNextNumber();
        }
        this.precisionModel.makePrecise(coord);
        return coord;
    }

    private boolean isNumberNext() throws IOException {
        int type = this.tokenizer.nextToken();
        this.tokenizer.pushBack();
        return type == -3;
    }

    private double getNextNumber() throws IOException, ParseException {
        StringBuilder sb;
        switch (this.tokenizer.nextToken()) {
            case -3:
                if (!this.tokenizer.sval.equalsIgnoreCase(NAN_SYMBOL)) {
                    try {
                        return Double.parseDouble(this.tokenizer.sval);
                    } catch (NumberFormatException e) {
                        NumberFormatException numberFormatException = e;
                        new StringBuilder();
                        parseErrorWithLine(sb.append("Invalid number: ").append(this.tokenizer.sval).toString());
                        break;
                    }
                } else {
                    return Double.NaN;
                }
        }
        parseErrorExpected("number");
        return 0.0d;
    }

    private String getNextEmptyOrOpener() throws IOException, ParseException {
        String nextWord = getNextWord();
        if (nextWord.equalsIgnoreCase(GMLConstants.GML_COORD_Z)) {
            this.f449z = true;
            nextWord = getNextWord();
        } else if (nextWord.equalsIgnoreCase("M")) {
            this.f448m = true;
            nextWord = getNextWord();
        } else if (nextWord.equalsIgnoreCase("ZM")) {
            this.f449z = true;
            this.f448m = true;
            nextWord = getNextWord();
        }
        if (nextWord.equals(EMPTY) || nextWord.equals(L_PAREN)) {
            return nextWord;
        }
        parseErrorExpected("EMPTY or (");
        return null;
    }

    private String getNextCloserOrComma() throws IOException, ParseException {
        String nextWord = getNextWord();
        if (nextWord.equals(COMMA) || nextWord.equals(R_PAREN)) {
            return nextWord;
        }
        parseErrorExpected(", or )");
        return null;
    }

    private String getNextCloser() throws IOException, ParseException {
        String nextWord = getNextWord();
        if (nextWord.equals(R_PAREN)) {
            return nextWord;
        }
        parseErrorExpected(R_PAREN);
        return null;
    }

    private String getNextWord() throws IOException, ParseException {
        switch (this.tokenizer.nextToken()) {
            case -3:
                String word = this.tokenizer.sval;
                return word.equalsIgnoreCase(EMPTY) ? EMPTY : word;
            case 40:
                return L_PAREN;
            case 41:
                return R_PAREN;
            case 44:
                return COMMA;
            default:
                parseErrorExpected("word");
                return null;
        }
    }

    private String lookaheadWord() throws IOException, ParseException {
        String nextWord = getNextWord();
        this.tokenizer.pushBack();
        return nextWord;
    }

    private void parseErrorExpected(String str) throws ParseException {
        StringBuilder sb;
        String expected = str;
        if (this.tokenizer.ttype == -2) {
            Assert.shouldNeverReachHere("Unexpected NUMBER token");
        }
        if (this.tokenizer.ttype == 10) {
            Assert.shouldNeverReachHere("Unexpected EOL token");
        }
        String tokenStr = tokenString();
        new StringBuilder();
        parseErrorWithLine(sb.append("Expected ").append(expected).append(" but found ").append(tokenStr).toString());
    }

    private void parseErrorWithLine(String msg) throws ParseException {
        Throwable th;
        StringBuilder sb;
        Throwable th2 = th;
        new StringBuilder();
        new ParseException(sb.append(msg).append(" (line ").append(this.tokenizer.lineno()).append(R_PAREN).toString());
        throw th2;
    }

    private String tokenString() {
        StringBuilder sb;
        StringBuilder sb2;
        switch (this.tokenizer.ttype) {
            case -3:
                new StringBuilder();
                return sb.append("'").append(this.tokenizer.sval).append("'").toString();
            case -2:
                return "<NUMBER>";
            case -1:
                return "End-of-Stream";
            case 10:
                return "End-of-Line";
            default:
                new StringBuilder();
                return sb2.append("'").append((char) this.tokenizer.ttype).append("'").toString();
        }
    }

    private Geometry readGeometryTaggedText() throws IOException, ParseException {
        StringBuilder sb;
        try {
            String type = getNextWord().toUpperCase();
            if (type.endsWith(GMLConstants.GML_COORD_Z)) {
                this.f449z = true;
            }
            if (type.endsWith("M")) {
                this.f448m = true;
            }
            if (type.startsWith("POINT")) {
                return readPointText();
            }
            if (type.startsWith("LINESTRING")) {
                return readLineStringText();
            }
            if (type.startsWith("LINEARRING")) {
                return readLinearRingText();
            }
            if (type.startsWith("POLYGON")) {
                return readPolygonText();
            }
            if (type.startsWith("MULTIPOINT")) {
                return readMultiPointText();
            }
            if (type.startsWith("MULTILINESTRING")) {
                return readMultiLineStringText();
            }
            if (type.startsWith("MULTIPOLYGON")) {
                return readMultiPolygonText();
            }
            if (type.startsWith("GEOMETRYCOLLECTION")) {
                return readGeometryCollectionText();
            }
            new StringBuilder();
            parseErrorWithLine(sb.append("Unknown geometry type: ").append(type).toString());
            return null;
        } catch (IOException e) {
            IOException iOException = e;
            return null;
        } catch (ParseException e2) {
            ParseException parseException = e2;
            return null;
        }
    }

    private Point readPointText() throws IOException, ParseException {
        if (getNextEmptyOrOpener().equals(EMPTY)) {
            return this.geometryFactory.createPoint((Coordinate) null);
        }
        Point point = this.geometryFactory.createPoint(getPreciseCoordinate());
        String nextCloser = getNextCloser();
        return point;
    }

    private LineString readLineStringText() throws IOException, ParseException {
        return this.geometryFactory.createLineString(getCoordinates());
    }

    private LinearRing readLinearRingText() throws IOException, ParseException {
        return this.geometryFactory.createLinearRing(getCoordinates());
    }

    private MultiPoint readMultiPointText() throws IOException, ParseException {
        ArrayList arrayList;
        if (getNextEmptyOrOpener().equals(EMPTY)) {
            return this.geometryFactory.createMultiPoint(new Point[0]);
        }
        if (lookaheadWord() != L_PAREN) {
            return this.geometryFactory.createMultiPoint(toPoints(getCoordinatesNoLeftParen()));
        }
        new ArrayList();
        ArrayList points = arrayList;
        boolean add = points.add(readPointText());
        for (String nextToken = getNextCloserOrComma(); nextToken.equals(COMMA); nextToken = getNextCloserOrComma()) {
            boolean add2 = points.add(readPointText());
        }
        return this.geometryFactory.createMultiPoint((Point[]) points.toArray(new Point[points.size()]));
    }

    private Point[] toPoints(Coordinate[] coordinateArr) {
        ArrayList arrayList;
        Coordinate[] coordinates = coordinateArr;
        new ArrayList();
        ArrayList points = arrayList;
        for (int i = 0; i < coordinates.length; i++) {
            boolean add = points.add(this.geometryFactory.createPoint(coordinates[i]));
        }
        return (Point[]) points.toArray(new Point[0]);
    }

    private Polygon readPolygonText() throws IOException, ParseException {
        ArrayList arrayList;
        if (getNextEmptyOrOpener().equals(EMPTY)) {
            return this.geometryFactory.createPolygon(this.geometryFactory.createLinearRing(new Coordinate[0]), new LinearRing[0]);
        }
        new ArrayList();
        ArrayList holes = arrayList;
        LinearRing shell = readLinearRingText();
        for (String nextToken = getNextCloserOrComma(); nextToken.equals(COMMA); nextToken = getNextCloserOrComma()) {
            boolean add = holes.add(readLinearRingText());
        }
        return this.geometryFactory.createPolygon(shell, (LinearRing[]) holes.toArray(new LinearRing[holes.size()]));
    }

    private MultiLineString readMultiLineStringText() throws IOException, ParseException {
        ArrayList arrayList;
        if (getNextEmptyOrOpener().equals(EMPTY)) {
            return this.geometryFactory.createMultiLineString(new LineString[0]);
        }
        new ArrayList();
        ArrayList lineStrings = arrayList;
        boolean add = lineStrings.add(readLineStringText());
        for (String nextToken = getNextCloserOrComma(); nextToken.equals(COMMA); nextToken = getNextCloserOrComma()) {
            boolean add2 = lineStrings.add(readLineStringText());
        }
        return this.geometryFactory.createMultiLineString((LineString[]) lineStrings.toArray(new LineString[lineStrings.size()]));
    }

    private MultiPolygon readMultiPolygonText() throws IOException, ParseException {
        ArrayList arrayList;
        if (getNextEmptyOrOpener().equals(EMPTY)) {
            return this.geometryFactory.createMultiPolygon(new Polygon[0]);
        }
        new ArrayList();
        ArrayList polygons = arrayList;
        boolean add = polygons.add(readPolygonText());
        for (String nextToken = getNextCloserOrComma(); nextToken.equals(COMMA); nextToken = getNextCloserOrComma()) {
            boolean add2 = polygons.add(readPolygonText());
        }
        return this.geometryFactory.createMultiPolygon((Polygon[]) polygons.toArray(new Polygon[polygons.size()]));
    }

    private GeometryCollection readGeometryCollectionText() throws IOException, ParseException {
        ArrayList arrayList;
        if (getNextEmptyOrOpener().equals(EMPTY)) {
            return this.geometryFactory.createGeometryCollection(new Geometry[0]);
        }
        new ArrayList();
        ArrayList geometries = arrayList;
        boolean add = geometries.add(readGeometryTaggedText());
        for (String nextToken = getNextCloserOrComma(); nextToken.equals(COMMA); nextToken = getNextCloserOrComma()) {
            boolean add2 = geometries.add(readGeometryTaggedText());
        }
        return this.geometryFactory.createGeometryCollection((Geometry[]) geometries.toArray(new Geometry[geometries.size()]));
    }
}
