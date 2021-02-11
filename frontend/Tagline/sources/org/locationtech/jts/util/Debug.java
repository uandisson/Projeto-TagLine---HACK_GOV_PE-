package org.locationtech.jts.util;

import java.io.PrintStream;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Iterator;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.CoordinateSequence;
import org.locationtech.jts.geom.CoordinateSequenceFilter;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.LineString;

public class Debug {
    private static final String DEBUG_LINE_TAG = "D! ";
    public static String DEBUG_PROPERTY_NAME = "jts.debug";
    public static String DEBUG_PROPERTY_VALUE_ON = "on";
    public static String DEBUG_PROPERTY_VALUE_TRUE = "true";
    private static final Debug debug;
    private static boolean debugOn;
    private static final GeometryFactory fact;
    private static long lastTimePrinted;
    private static Stopwatch stopwatch;
    private Object[] args = new Object[1];
    private PrintStream out = System.out;
    private Class[] printArgs = new Class[1];
    private Object watchObj = null;

    static {
        Stopwatch stopwatch2;
        Debug debug2;
        GeometryFactory geometryFactory;
        debugOn = false;
        String debugValue = System.getProperty(DEBUG_PROPERTY_NAME);
        if (debugValue != null && (debugValue.equalsIgnoreCase(DEBUG_PROPERTY_VALUE_ON) || debugValue.equalsIgnoreCase(DEBUG_PROPERTY_VALUE_TRUE))) {
            debugOn = true;
        }
        new Stopwatch();
        stopwatch = stopwatch2;
        new Debug();
        debug = debug2;
        new GeometryFactory();
        fact = geometryFactory;
    }

    public static void main(String[] strArr) {
        StringBuilder sb;
        String[] strArr2 = strArr;
        PrintStream printStream = System.out;
        new StringBuilder();
        printStream.println(sb.append("JTS Debugging is ").append(debugOn ? "ON" : "OFF").toString());
    }

    public static boolean isDebugging() {
        return debugOn;
    }

    public static LineString toLine(Coordinate p0, Coordinate p1) {
        GeometryFactory geometryFactory = fact;
        Coordinate[] coordinateArr = new Coordinate[2];
        coordinateArr[0] = p0;
        Coordinate[] coordinateArr2 = coordinateArr;
        coordinateArr2[1] = p1;
        return geometryFactory.createLineString(coordinateArr2);
    }

    public static LineString toLine(Coordinate p0, Coordinate p1, Coordinate p2) {
        GeometryFactory geometryFactory = fact;
        Coordinate[] coordinateArr = new Coordinate[3];
        coordinateArr[0] = p0;
        Coordinate[] coordinateArr2 = coordinateArr;
        coordinateArr2[1] = p1;
        Coordinate[] coordinateArr3 = coordinateArr2;
        coordinateArr3[2] = p2;
        return geometryFactory.createLineString(coordinateArr3);
    }

    public static LineString toLine(Coordinate p0, Coordinate p1, Coordinate p2, Coordinate p3) {
        GeometryFactory geometryFactory = fact;
        Coordinate[] coordinateArr = new Coordinate[4];
        coordinateArr[0] = p0;
        Coordinate[] coordinateArr2 = coordinateArr;
        coordinateArr2[1] = p1;
        Coordinate[] coordinateArr3 = coordinateArr2;
        coordinateArr3[2] = p2;
        Coordinate[] coordinateArr4 = coordinateArr3;
        coordinateArr4[3] = p3;
        return geometryFactory.createLineString(coordinateArr4);
    }

    public static void print(String str) {
        String str2 = str;
        if (debugOn) {
            debug.instancePrint(str2);
        }
    }

    public static void print(Object obj) {
        Object obj2 = obj;
        if (debugOn) {
            debug.instancePrint(obj2);
        }
    }

    public static void print(boolean z, Object obj) {
        boolean isTrue = z;
        Object obj2 = obj;
        if (debugOn && isTrue) {
            debug.instancePrint(obj2);
        }
    }

    public static void println(Object obj) {
        Object obj2 = obj;
        if (debugOn) {
            debug.instancePrint(obj2);
            debug.println();
        }
    }

    public static void resetTime() {
        stopwatch.reset();
        lastTimePrinted = stopwatch.getTime();
    }

    public static void printTime(String str) {
        StringBuilder sb;
        String tag = str;
        if (debugOn) {
            long time = stopwatch.getTime();
            long elapsedTime = time - lastTimePrinted;
            Debug debug2 = debug;
            new StringBuilder();
            debug2.instancePrint(sb.append(formatField(Stopwatch.getTimeString(time), 10)).append(" (").append(formatField(Stopwatch.getTimeString(elapsedTime), 10)).append(" ) ").append(tag).toString());
            debug.println();
            lastTimePrinted = time;
        }
    }

    private static String formatField(String str, int i) {
        StringBuilder sb;
        String s = str;
        int fieldLen = i;
        int nPad = fieldLen - s.length();
        if (nPad <= 0) {
            return s;
        }
        new StringBuilder();
        String padStr = sb.append(spaces(nPad)).append(s).toString();
        return padStr.substring(padStr.length() - fieldLen);
    }

    private static String spaces(int i) {
        String str;
        int n = i;
        char[] ch = new char[n];
        for (int i2 = 0; i2 < n; i2++) {
            ch[i2] = ' ';
        }
        new String(ch);
        return str;
    }

    public static boolean equals(Coordinate c1, Coordinate c2, double tolerance) {
        return c1.distance(c2) <= tolerance;
    }

    public static void addWatch(Object obj) {
        debug.instanceAddWatch(obj);
    }

    public static void printWatch() {
        debug.instancePrintWatch();
    }

    public static void printIfWatch(Object obj) {
        debug.instancePrintIfWatch(obj);
    }

    public static void breakIf(boolean cond) {
        if (cond) {
            doBreak();
        }
    }

    public static void breakIfEqual(Object o1, Object o2) {
        if (o1.equals(o2)) {
            doBreak();
        }
    }

    public static void breakIfEqual(Coordinate p0, Coordinate p1, double tolerance) {
        if (p0.distance(p1) <= tolerance) {
            doBreak();
        }
    }

    private static void doBreak() {
    }

    public static boolean hasSegment(Geometry geom, Coordinate p0, Coordinate p1) {
        SegmentFindingFilter segmentFindingFilter;
        new SegmentFindingFilter(p0, p1);
        SegmentFindingFilter filter = segmentFindingFilter;
        geom.apply((CoordinateSequenceFilter) filter);
        return filter.hasSegment();
    }

    private static class SegmentFindingFilter implements CoordinateSequenceFilter {
        private boolean hasSegment = false;

        /* renamed from: p0 */
        private Coordinate f515p0;

        /* renamed from: p1 */
        private Coordinate f516p1;

        public SegmentFindingFilter(Coordinate p0, Coordinate p1) {
            this.f515p0 = p0;
            this.f516p1 = p1;
        }

        public boolean hasSegment() {
            return this.hasSegment;
        }

        public void filter(CoordinateSequence coordinateSequence, int i) {
            CoordinateSequence seq = coordinateSequence;
            int i2 = i;
            if (i2 != 0) {
                this.hasSegment = this.f515p0.equals2D(seq.getCoordinate(i2 + -1)) && this.f516p1.equals2D(seq.getCoordinate(i2));
            }
        }

        public boolean isDone() {
            return this.hasSegment;
        }

        public boolean isGeometryChanged() {
            return false;
        }
    }

    private Debug() {
        try {
            this.printArgs[0] = Class.forName("java.io.PrintStream");
        } catch (Exception e) {
            Exception exc = e;
        }
    }

    public void instancePrintWatch() {
        if (this.watchObj != null) {
            instancePrint(this.watchObj);
        }
    }

    public void instancePrintIfWatch(Object obj) {
        if (obj == this.watchObj && this.watchObj != null) {
            instancePrint(this.watchObj);
        }
    }

    public void instancePrint(Object obj) {
        Object obj2 = obj;
        if (obj2 instanceof Collection) {
            instancePrint(((Collection) obj2).iterator());
        } else if (obj2 instanceof Iterator) {
            instancePrint((Iterator) obj2);
        } else {
            instancePrintObject(obj2);
        }
    }

    public void instancePrint(Iterator it) {
        Iterator it2 = it;
        while (it2.hasNext()) {
            instancePrintObject(it2.next());
        }
    }

    public void instancePrintObject(Object obj) {
        Object obj2 = obj;
        try {
            try {
                Method printMethod = obj2.getClass().getMethod("print", this.printArgs);
                this.args[0] = this.out;
                this.out.print(DEBUG_LINE_TAG);
                Object invoke = printMethod.invoke(obj2, this.args);
            } catch (NoSuchMethodException e) {
                NoSuchMethodException noSuchMethodException = e;
                instancePrint(obj2.toString());
            }
        } catch (Exception e2) {
            e2.printStackTrace(this.out);
        }
    }

    public void println() {
        this.out.println();
    }

    private void instanceAddWatch(Object obj) {
        Object obj2 = obj;
        this.watchObj = obj2;
    }

    private void instancePrint(String str) {
        this.out.print(DEBUG_LINE_TAG);
        this.out.print(str);
    }
}
