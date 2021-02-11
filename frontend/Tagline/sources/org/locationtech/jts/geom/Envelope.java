package org.locationtech.jts.geom;

import java.io.Serializable;

public class Envelope implements Comparable, Serializable {
    private static final long serialVersionUID = 5873921885273102420L;
    private double maxx;
    private double maxy;
    private double minx;
    private double miny;

    public int hashCode() {
        return (37 * ((37 * ((37 * ((37 * 17) + Coordinate.hashCode(this.minx))) + Coordinate.hashCode(this.maxx))) + Coordinate.hashCode(this.miny))) + Coordinate.hashCode(this.maxy);
    }

    public static boolean intersects(Coordinate coordinate, Coordinate coordinate2, Coordinate coordinate3) {
        Coordinate p1 = coordinate;
        Coordinate p2 = coordinate2;
        Coordinate q = coordinate3;
        if (q.f412x >= (p1.f412x < p2.f412x ? p1.f412x : p2.f412x)) {
            if (q.f412x <= (p1.f412x > p2.f412x ? p1.f412x : p2.f412x)) {
                if (q.f413y >= (p1.f413y < p2.f413y ? p1.f413y : p2.f413y)) {
                    if (q.f413y <= (p1.f413y > p2.f413y ? p1.f413y : p2.f413y)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static boolean intersects(Coordinate coordinate, Coordinate coordinate2, Coordinate coordinate3, Coordinate coordinate4) {
        Coordinate p1 = coordinate;
        Coordinate p2 = coordinate2;
        Coordinate q1 = coordinate3;
        Coordinate q2 = coordinate4;
        double minq = Math.min(q1.f412x, q2.f412x);
        double maxq = Math.max(q1.f412x, q2.f412x);
        double minp = Math.min(p1.f412x, p2.f412x);
        double maxp = Math.max(p1.f412x, p2.f412x);
        if (minp > maxq) {
            return false;
        }
        if (maxp < minq) {
            return false;
        }
        double minq2 = Math.min(q1.f413y, q2.f413y);
        double maxq2 = Math.max(q1.f413y, q2.f413y);
        double minp2 = Math.min(p1.f413y, p2.f413y);
        double maxp2 = Math.max(p1.f413y, p2.f413y);
        if (minp2 > maxq2) {
            return false;
        }
        if (maxp2 < minq2) {
            return false;
        }
        return true;
    }

    public Envelope() {
        init();
    }

    public Envelope(double x1, double x2, double y1, double y2) {
        init(x1, x2, y1, y2);
    }

    public Envelope(Coordinate coordinate, Coordinate coordinate2) {
        Coordinate p1 = coordinate;
        Coordinate p2 = coordinate2;
        init(p1.f412x, p2.f412x, p1.f413y, p2.f413y);
    }

    public Envelope(Coordinate coordinate) {
        Coordinate p = coordinate;
        init(p.f412x, p.f412x, p.f413y, p.f413y);
    }

    public Envelope(Envelope env) {
        init(env);
    }

    public void init() {
        setToNull();
    }

    public void init(double d, double d2, double d3, double d4) {
        double x1 = d;
        double x2 = d2;
        double y1 = d3;
        double y2 = d4;
        if (x1 < x2) {
            this.minx = x1;
            this.maxx = x2;
        } else {
            this.minx = x2;
            this.maxx = x1;
        }
        if (y1 < y2) {
            this.miny = y1;
            this.maxy = y2;
            return;
        }
        this.miny = y2;
        this.maxy = y1;
    }

    public void init(Coordinate coordinate, Coordinate coordinate2) {
        Coordinate p1 = coordinate;
        Coordinate p2 = coordinate2;
        init(p1.f412x, p2.f412x, p1.f413y, p2.f413y);
    }

    public void init(Coordinate coordinate) {
        Coordinate p = coordinate;
        init(p.f412x, p.f412x, p.f413y, p.f413y);
    }

    public void init(Envelope envelope) {
        Envelope env = envelope;
        this.minx = env.minx;
        this.maxx = env.maxx;
        this.miny = env.miny;
        this.maxy = env.maxy;
    }

    public void setToNull() {
        this.minx = 0.0d;
        this.maxx = -1.0d;
        this.miny = 0.0d;
        this.maxy = -1.0d;
    }

    public boolean isNull() {
        return this.maxx < this.minx;
    }

    public double getWidth() {
        if (isNull()) {
            return 0.0d;
        }
        return this.maxx - this.minx;
    }

    public double getHeight() {
        if (isNull()) {
            return 0.0d;
        }
        return this.maxy - this.miny;
    }

    public double getMinX() {
        return this.minx;
    }

    public double getMaxX() {
        return this.maxx;
    }

    public double getMinY() {
        return this.miny;
    }

    public double getMaxY() {
        return this.maxy;
    }

    public double getArea() {
        return getWidth() * getHeight();
    }

    public double minExtent() {
        if (isNull()) {
            return 0.0d;
        }
        double w = getWidth();
        double h = getHeight();
        if (w < h) {
            return w;
        }
        return h;
    }

    public double maxExtent() {
        if (isNull()) {
            return 0.0d;
        }
        double w = getWidth();
        double h = getHeight();
        if (w > h) {
            return w;
        }
        return h;
    }

    public void expandToInclude(Coordinate coordinate) {
        Coordinate p = coordinate;
        expandToInclude(p.f412x, p.f413y);
    }

    public void expandBy(double d) {
        double distance = d;
        expandBy(distance, distance);
    }

    public void expandBy(double d, double d2) {
        double deltaX = d;
        double deltaY = d2;
        if (!isNull()) {
            this.minx -= deltaX;
            this.maxx += deltaX;
            this.miny -= deltaY;
            this.maxy += deltaY;
            if (this.minx > this.maxx || this.miny > this.maxy) {
                setToNull();
            }
        }
    }

    public void expandToInclude(double d, double d2) {
        double x = d;
        double y = d2;
        if (isNull()) {
            this.minx = x;
            this.maxx = x;
            this.miny = y;
            this.maxy = y;
            return;
        }
        if (x < this.minx) {
            this.minx = x;
        }
        if (x > this.maxx) {
            this.maxx = x;
        }
        if (y < this.miny) {
            this.miny = y;
        }
        if (y > this.maxy) {
            this.maxy = y;
        }
    }

    public void expandToInclude(Envelope envelope) {
        Envelope other = envelope;
        if (!other.isNull()) {
            if (isNull()) {
                this.minx = other.getMinX();
                this.maxx = other.getMaxX();
                this.miny = other.getMinY();
                this.maxy = other.getMaxY();
                return;
            }
            if (other.minx < this.minx) {
                this.minx = other.minx;
            }
            if (other.maxx > this.maxx) {
                this.maxx = other.maxx;
            }
            if (other.miny < this.miny) {
                this.miny = other.miny;
            }
            if (other.maxy > this.maxy) {
                this.maxy = other.maxy;
            }
        }
    }

    public void translate(double d, double d2) {
        double transX = d;
        double transY = d2;
        if (!isNull()) {
            init(getMinX() + transX, getMaxX() + transX, getMinY() + transY, getMaxY() + transY);
        }
    }

    public Coordinate centre() {
        Coordinate coordinate;
        if (isNull()) {
            return null;
        }
        new Coordinate((getMinX() + getMaxX()) / 2.0d, (getMinY() + getMaxY()) / 2.0d);
        return coordinate;
    }

    public Envelope intersection(Envelope envelope) {
        Envelope envelope2;
        Envelope envelope3;
        Envelope env = envelope;
        if (isNull() || env.isNull() || !intersects(env)) {
            new Envelope();
            return envelope2;
        }
        new Envelope(this.minx > env.minx ? this.minx : env.minx, this.maxx < env.maxx ? this.maxx : env.maxx, this.miny > env.miny ? this.miny : env.miny, this.maxy < env.maxy ? this.maxy : env.maxy);
        return envelope3;
    }

    public boolean intersects(Envelope envelope) {
        Envelope other = envelope;
        if (isNull() || other.isNull()) {
            return false;
        }
        return other.minx <= this.maxx && other.maxx >= this.minx && other.miny <= this.maxy && other.maxy >= this.miny;
    }

    public boolean overlaps(Envelope other) {
        return intersects(other);
    }

    public boolean intersects(Coordinate coordinate) {
        Coordinate p = coordinate;
        return intersects(p.f412x, p.f413y);
    }

    public boolean overlaps(Coordinate p) {
        return intersects(p);
    }

    public boolean intersects(double d, double d2) {
        double x = d;
        double y = d2;
        if (isNull()) {
            return false;
        }
        return x <= this.maxx && x >= this.minx && y <= this.maxy && y >= this.miny;
    }

    public boolean overlaps(double x, double y) {
        return intersects(x, y);
    }

    public boolean contains(Envelope other) {
        return covers(other);
    }

    public boolean contains(Coordinate p) {
        return covers(p);
    }

    public boolean contains(double x, double y) {
        return covers(x, y);
    }

    public boolean covers(double d, double d2) {
        double x = d;
        double y = d2;
        if (isNull()) {
            return false;
        }
        return x >= this.minx && x <= this.maxx && y >= this.miny && y <= this.maxy;
    }

    public boolean covers(Coordinate coordinate) {
        Coordinate p = coordinate;
        return covers(p.f412x, p.f413y);
    }

    public boolean covers(Envelope envelope) {
        Envelope other = envelope;
        if (isNull() || other.isNull()) {
            return false;
        }
        return other.getMinX() >= this.minx && other.getMaxX() <= this.maxx && other.getMinY() >= this.miny && other.getMaxY() <= this.maxy;
    }

    public double distance(Envelope envelope) {
        Envelope env = envelope;
        if (intersects(env)) {
            return 0.0d;
        }
        double dx = 0.0d;
        if (this.maxx < env.minx) {
            dx = env.minx - this.maxx;
        } else if (this.minx > env.maxx) {
            dx = this.minx - env.maxx;
        }
        double dy = 0.0d;
        if (this.maxy < env.miny) {
            dy = env.miny - this.maxy;
        } else if (this.miny > env.maxy) {
            dy = this.miny - env.maxy;
        }
        if (dx == 0.0d) {
            return dy;
        }
        if (dy == 0.0d) {
            return dx;
        }
        return Math.sqrt((dx * dx) + (dy * dy));
    }

    public boolean equals(Object obj) {
        Object other = obj;
        if (!(other instanceof Envelope)) {
            return false;
        }
        Envelope otherEnvelope = (Envelope) other;
        if (isNull()) {
            return otherEnvelope.isNull();
        }
        return this.maxx == otherEnvelope.getMaxX() && this.maxy == otherEnvelope.getMaxY() && this.minx == otherEnvelope.getMinX() && this.miny == otherEnvelope.getMinY();
    }

    public String toString() {
        StringBuilder sb;
        new StringBuilder();
        return sb.append("Env[").append(this.minx).append(" : ").append(this.maxx).append(", ").append(this.miny).append(" : ").append(this.maxy).append("]").toString();
    }

    public int compareTo(Object o) {
        Envelope env = (Envelope) o;
        if (isNull()) {
            if (env.isNull()) {
                return 0;
            }
            return -1;
        } else if (env.isNull()) {
            return 1;
        } else {
            if (this.minx < env.minx) {
                return -1;
            }
            if (this.minx > env.minx) {
                return 1;
            }
            if (this.miny < env.miny) {
                return -1;
            }
            if (this.miny > env.miny) {
                return 1;
            }
            if (this.maxx < env.maxx) {
                return -1;
            }
            if (this.maxx > env.maxx) {
                return 1;
            }
            if (this.maxy < env.maxy) {
                return -1;
            }
            if (this.maxy > env.maxy) {
                return 1;
            }
            return 0;
        }
    }
}
