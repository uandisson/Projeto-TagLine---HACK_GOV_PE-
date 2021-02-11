package org.locationtech.jts.geom;

import java.io.Serializable;
import java.util.Comparator;
import org.locationtech.jts.util.Assert;
import org.locationtech.jts.util.NumberUtil;

public class Coordinate implements Comparable, Cloneable, Serializable {
    public static final double NULL_ORDINATE = Double.NaN;

    /* renamed from: X */
    public static final int f409X = 0;

    /* renamed from: Y */
    public static final int f410Y = 1;

    /* renamed from: Z */
    public static final int f411Z = 2;
    private static final long serialVersionUID = 6683108902428366910L;

    /* renamed from: x */
    public double f412x;

    /* renamed from: y */
    public double f413y;

    /* renamed from: z */
    public double f414z;

    public Coordinate(double x, double y, double z) {
        this.f412x = x;
        this.f413y = y;
        this.f414z = z;
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public Coordinate() {
        this(0.0d, 0.0d);
    }

    /* JADX WARNING: Illegal instructions before constructor call */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public Coordinate(org.locationtech.jts.geom.Coordinate r11) {
        /*
            r10 = this;
            r1 = r10
            r2 = r11
            r3 = r1
            r4 = r2
            double r4 = r4.f412x
            r6 = r2
            double r6 = r6.f413y
            r8 = r2
            double r8 = r8.f414z
            r3.<init>(r4, r6, r8)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.locationtech.jts.geom.Coordinate.<init>(org.locationtech.jts.geom.Coordinate):void");
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public Coordinate(double x, double y) {
        this(x, y, Double.NaN);
    }

    public void setCoordinate(Coordinate coordinate) {
        Coordinate other = coordinate;
        this.f412x = other.f412x;
        this.f413y = other.f413y;
        this.f414z = other.f414z;
    }

    public double getOrdinate(int i) {
        Throwable th;
        StringBuilder sb;
        int ordinateIndex = i;
        switch (ordinateIndex) {
            case 0:
                return this.f412x;
            case 1:
                return this.f413y;
            case 2:
                return this.f414z;
            default:
                Throwable th2 = th;
                new StringBuilder();
                new IllegalArgumentException(sb.append("Invalid ordinate index: ").append(ordinateIndex).toString());
                throw th2;
        }
    }

    public void setOrdinate(int i, double d) {
        Throwable th;
        StringBuilder sb;
        int ordinateIndex = i;
        double value = d;
        switch (ordinateIndex) {
            case 0:
                this.f412x = value;
                return;
            case 1:
                this.f413y = value;
                return;
            case 2:
                this.f414z = value;
                return;
            default:
                Throwable th2 = th;
                new StringBuilder();
                new IllegalArgumentException(sb.append("Invalid ordinate index: ").append(ordinateIndex).toString());
                throw th2;
        }
    }

    public boolean equals2D(Coordinate coordinate) {
        Coordinate other = coordinate;
        if (this.f412x != other.f412x) {
            return false;
        }
        if (this.f413y != other.f413y) {
            return false;
        }
        return true;
    }

    public boolean equals2D(Coordinate coordinate, double d) {
        Coordinate c = coordinate;
        double tolerance = d;
        if (!NumberUtil.equalsWithTolerance(this.f412x, c.f412x, tolerance)) {
            return false;
        }
        if (!NumberUtil.equalsWithTolerance(this.f413y, c.f413y, tolerance)) {
            return false;
        }
        return true;
    }

    public boolean equals3D(Coordinate coordinate) {
        Coordinate other = coordinate;
        return this.f412x == other.f412x && this.f413y == other.f413y && (this.f414z == other.f414z || (Double.isNaN(this.f414z) && Double.isNaN(other.f414z)));
    }

    public boolean equalInZ(Coordinate c, double tolerance) {
        return NumberUtil.equalsWithTolerance(this.f414z, c.f414z, tolerance);
    }

    public boolean equals(Object obj) {
        Object other = obj;
        if (!(other instanceof Coordinate)) {
            return false;
        }
        return equals2D((Coordinate) other);
    }

    public int compareTo(Object o) {
        Coordinate other = (Coordinate) o;
        if (this.f412x < other.f412x) {
            return -1;
        }
        if (this.f412x > other.f412x) {
            return 1;
        }
        if (this.f413y < other.f413y) {
            return -1;
        }
        if (this.f413y > other.f413y) {
            return 1;
        }
        return 0;
    }

    public String toString() {
        StringBuilder sb;
        new StringBuilder();
        return sb.append("(").append(this.f412x).append(", ").append(this.f413y).append(", ").append(this.f414z).append(")").toString();
    }

    public Object clone() {
        try {
            return (Coordinate) super.clone();
        } catch (CloneNotSupportedException e) {
            CloneNotSupportedException cloneNotSupportedException = e;
            Assert.shouldNeverReachHere("this shouldn't happen because this class is Cloneable");
            return null;
        }
    }

    public double distance(Coordinate coordinate) {
        Coordinate c = coordinate;
        double dx = this.f412x - c.f412x;
        double dy = this.f413y - c.f413y;
        return Math.sqrt((dx * dx) + (dy * dy));
    }

    public double distance3D(Coordinate coordinate) {
        Coordinate c = coordinate;
        double dx = this.f412x - c.f412x;
        double dy = this.f413y - c.f413y;
        double dz = this.f414z - c.f414z;
        return Math.sqrt((dx * dx) + (dy * dy) + (dz * dz));
    }

    public int hashCode() {
        return (37 * ((37 * 17) + hashCode(this.f412x))) + hashCode(this.f413y);
    }

    public static int hashCode(double x) {
        long f = Double.doubleToLongBits(x);
        return (int) (f ^ (f >>> 32));
    }

    public static class DimensionalComparator implements Comparator {
        private int dimensionsToTest;

        public static int compare(double d, double d2) {
            double a = d;
            double b = d2;
            if (a < b) {
                return -1;
            }
            if (a > b) {
                return 1;
            }
            if (Double.isNaN(a)) {
                if (Double.isNaN(b)) {
                    return 0;
                }
                return -1;
            } else if (Double.isNaN(b)) {
                return 1;
            } else {
                return 0;
            }
        }

        /* JADX INFO: this call moved to the top of the method (can break code semantics) */
        public DimensionalComparator() {
            this(2);
        }

        public DimensionalComparator(int i) {
            Throwable th;
            int dimensionsToTest2 = i;
            this.dimensionsToTest = 2;
            if (dimensionsToTest2 == 2 || dimensionsToTest2 == 3) {
                this.dimensionsToTest = dimensionsToTest2;
                return;
            }
            Throwable th2 = th;
            new IllegalArgumentException("only 2 or 3 dimensions may be specified");
            throw th2;
        }

        public int compare(Object o1, Object o2) {
            Coordinate c1 = (Coordinate) o1;
            Coordinate c2 = (Coordinate) o2;
            int compX = compare(c1.f412x, c2.f412x);
            if (compX != 0) {
                return compX;
            }
            int compY = compare(c1.f413y, c2.f413y);
            if (compY != 0) {
                return compY;
            }
            if (this.dimensionsToTest <= 2) {
                return 0;
            }
            return compare(c1.f414z, c2.f414z);
        }
    }
}
