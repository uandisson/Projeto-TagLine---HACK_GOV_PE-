package org.locationtech.jts.noding;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.LineString;
import org.locationtech.jts.geom.util.LinearComponentExtracter;

public class SegmentStringUtil {
    public SegmentStringUtil() {
    }

    public static List extractSegmentStrings(Geometry geom) {
        return extractNodedSegmentStrings(geom);
    }

    public static List extractNodedSegmentStrings(Geometry geometry) {
        List list;
        Object obj;
        Geometry geom = geometry;
        new ArrayList();
        List segStr = list;
        for (LineString line : LinearComponentExtracter.getLines(geom)) {
            new NodedSegmentString(line.getCoordinates(), geom);
            boolean add = segStr.add(obj);
        }
        return segStr;
    }

    public static Geometry toGeometry(Collection collection, GeometryFactory geometryFactory) {
        Collection<SegmentString> segStrings = collection;
        GeometryFactory geomFact = geometryFactory;
        LineString[] lines = new LineString[segStrings.size()];
        int index = 0;
        for (SegmentString ss : segStrings) {
            int i = index;
            index++;
            lines[i] = geomFact.createLineString(ss.getCoordinates());
        }
        if (lines.length == 1) {
            return lines[0];
        }
        return geomFact.createMultiLineString(lines);
    }

    public static String toString(List segStrings) {
        StringBuffer stringBuffer;
        new StringBuffer();
        StringBuffer buf = stringBuffer;
        Iterator i = segStrings.iterator();
        while (i.hasNext()) {
            StringBuffer append = buf.append(((SegmentString) i.next()).toString());
            StringBuffer append2 = buf.append("\n");
        }
        return buf.toString();
    }
}
