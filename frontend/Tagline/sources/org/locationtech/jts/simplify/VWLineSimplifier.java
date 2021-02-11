package org.locationtech.jts.simplify;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.CoordinateList;
import org.locationtech.jts.geom.Triangle;

class VWLineSimplifier {
    private Coordinate[] pts;
    private double tolerance;

    public static Coordinate[] simplify(Coordinate[] pts2, double distanceTolerance) {
        VWLineSimplifier simp;
        new VWLineSimplifier(pts2, distanceTolerance);
        return simp.simplify();
    }

    public VWLineSimplifier(Coordinate[] pts2, double d) {
        double distanceTolerance = d;
        this.pts = pts2;
        this.tolerance = distanceTolerance * distanceTolerance;
    }

    public Coordinate[] simplify() {
        Coordinate coordinate;
        VWVertex vwLine = VWVertex.buildLine(this.pts);
        double d = this.tolerance;
        do {
        } while (simplifyVertex(vwLine) < this.tolerance);
        Coordinate[] simp = vwLine.getCoordinates();
        if (simp.length >= 2) {
            return simp;
        }
        Coordinate[] coordinateArr = new Coordinate[2];
        coordinateArr[0] = simp[0];
        Coordinate[] coordinateArr2 = coordinateArr;
        new Coordinate(simp[0]);
        coordinateArr2[1] = coordinate;
        return coordinateArr2;
    }

    private double simplifyVertex(VWVertex vWVertex) {
        VWVertex vwLine = vWVertex;
        VWVertex curr = vwLine;
        double minArea = curr.getArea();
        VWVertex minVertex = null;
        while (curr != null) {
            double area = curr.getArea();
            if (area < minArea) {
                minArea = area;
                minVertex = curr;
            }
            curr = curr.next;
        }
        if (minVertex != null && minArea < this.tolerance) {
            VWVertex remove = minVertex.remove();
        }
        if (!vwLine.isLive()) {
            return -1.0d;
        }
        return minArea;
    }

    static class VWVertex {
        public static double MAX_AREA = Double.MAX_VALUE;
        private double area = MAX_AREA;
        private boolean isLive = true;
        /* access modifiers changed from: private */
        public VWVertex next;
        private VWVertex prev;

        /* renamed from: pt */
        private Coordinate f509pt;

        public static VWVertex buildLine(Coordinate[] coordinateArr) {
            VWVertex vWVertex;
            Coordinate[] pts = coordinateArr;
            VWVertex first = null;
            VWVertex prev2 = null;
            for (int i = 0; i < pts.length; i++) {
                new VWVertex(pts[i]);
                VWVertex v = vWVertex;
                if (first == null) {
                    first = v;
                }
                v.setPrev(prev2);
                if (prev2 != null) {
                    prev2.setNext(v);
                    prev2.updateArea();
                }
                prev2 = v;
            }
            return first;
        }

        public VWVertex(Coordinate pt) {
            this.f509pt = pt;
        }

        public void setPrev(VWVertex prev2) {
            VWVertex vWVertex = prev2;
            this.prev = vWVertex;
        }

        public void setNext(VWVertex next2) {
            VWVertex vWVertex = next2;
            this.next = vWVertex;
        }

        public void updateArea() {
            if (this.prev == null || this.next == null) {
                this.area = MAX_AREA;
                return;
            }
            this.area = Math.abs(Triangle.area(this.prev.f509pt, this.f509pt, this.next.f509pt));
        }

        public double getArea() {
            return this.area;
        }

        public boolean isLive() {
            return this.isLive;
        }

        public VWVertex remove() {
            VWVertex tmpPrev = this.prev;
            VWVertex tmpNext = this.next;
            VWVertex result = null;
            if (this.prev != null) {
                this.prev.setNext(tmpNext);
                this.prev.updateArea();
                result = this.prev;
            }
            if (this.next != null) {
                this.next.setPrev(tmpPrev);
                this.next.updateArea();
                if (result == null) {
                    result = this.next;
                }
            }
            this.isLive = false;
            return result;
        }

        public Coordinate[] getCoordinates() {
            CoordinateList coordinateList;
            new CoordinateList();
            CoordinateList coords = coordinateList;
            VWVertex curr = this;
            do {
                coords.add(curr.f509pt, false);
                curr = curr.next;
            } while (curr != null);
            return coords.toCoordinateArray();
        }
    }
}
