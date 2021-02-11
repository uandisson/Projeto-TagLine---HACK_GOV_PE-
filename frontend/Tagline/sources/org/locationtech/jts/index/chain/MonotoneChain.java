package org.locationtech.jts.index.chain;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Envelope;
import org.locationtech.jts.geom.LineSegment;

public class MonotoneChain {
    private Object context = null;
    private int end;
    private Envelope env = null;

    /* renamed from: id */
    private int f441id;
    private Coordinate[] pts;
    private int start;

    public MonotoneChain(Coordinate[] pts2, int start2, int end2, Object context2) {
        this.pts = pts2;
        this.start = start2;
        this.end = end2;
        this.context = context2;
    }

    public void setId(int id) {
        int i = id;
        this.f441id = i;
    }

    public int getId() {
        return this.f441id;
    }

    public Object getContext() {
        return this.context;
    }

    public Envelope getEnvelope() {
        Envelope envelope;
        if (this.env == null) {
            new Envelope(this.pts[this.start], this.pts[this.end]);
            this.env = envelope;
        }
        return this.env;
    }

    public int getStartIndex() {
        return this.start;
    }

    public int getEndIndex() {
        return this.end;
    }

    public void getLineSegment(int i, LineSegment lineSegment) {
        int index = i;
        LineSegment ls = lineSegment;
        ls.f422p0 = this.pts[index];
        ls.f423p1 = this.pts[index + 1];
    }

    public Coordinate[] getCoordinates() {
        Coordinate[] coord = new Coordinate[((this.end - this.start) + 1)];
        int index = 0;
        for (int i = this.start; i <= this.end; i++) {
            int i2 = index;
            index++;
            coord[i2] = this.pts[i];
        }
        return coord;
    }

    public void select(Envelope searchEnv, MonotoneChainSelectAction mcs) {
        computeSelect(searchEnv, this.start, this.end, mcs);
    }

    private void computeSelect(Envelope envelope, int i, int i2, MonotoneChainSelectAction monotoneChainSelectAction) {
        Envelope searchEnv = envelope;
        int start0 = i;
        int end0 = i2;
        MonotoneChainSelectAction mcs = monotoneChainSelectAction;
        mcs.tempEnv1.init(this.pts[start0], this.pts[end0]);
        if (end0 - start0 == 1) {
            mcs.select(this, start0);
        } else if (searchEnv.intersects(mcs.tempEnv1)) {
            int mid = (start0 + end0) / 2;
            if (start0 < mid) {
                computeSelect(searchEnv, start0, mid, mcs);
            }
            if (mid < end0) {
                computeSelect(searchEnv, mid, end0, mcs);
            }
        }
    }

    public void computeOverlaps(MonotoneChain monotoneChain, MonotoneChainOverlapAction mco) {
        MonotoneChain mc = monotoneChain;
        computeOverlaps(this.start, this.end, mc, mc.start, mc.end, mco);
    }

    private void computeOverlaps(int i, int i2, MonotoneChain monotoneChain, int i3, int i4, MonotoneChainOverlapAction monotoneChainOverlapAction) {
        int start0 = i;
        int end0 = i2;
        MonotoneChain mc = monotoneChain;
        int start1 = i3;
        int end1 = i4;
        MonotoneChainOverlapAction mco = monotoneChainOverlapAction;
        Coordinate p00 = this.pts[start0];
        Coordinate p01 = this.pts[end0];
        Coordinate p10 = mc.pts[start1];
        Coordinate p11 = mc.pts[end1];
        if (end0 - start0 == 1 && end1 - start1 == 1) {
            mco.overlap(this, start0, mc, start1);
            return;
        }
        mco.tempEnv1.init(p00, p01);
        mco.tempEnv2.init(p10, p11);
        if (mco.tempEnv1.intersects(mco.tempEnv2)) {
            int mid0 = (start0 + end0) / 2;
            int mid1 = (start1 + end1) / 2;
            if (start0 < mid0) {
                if (start1 < mid1) {
                    computeOverlaps(start0, mid0, mc, start1, mid1, mco);
                }
                if (mid1 < end1) {
                    computeOverlaps(start0, mid0, mc, mid1, end1, mco);
                }
            }
            if (mid0 < end0) {
                if (start1 < mid1) {
                    computeOverlaps(mid0, end0, mc, start1, mid1, mco);
                }
                if (mid1 < end1) {
                    computeOverlaps(mid0, end0, mc, mid1, end1, mco);
                }
            }
        }
    }
}
