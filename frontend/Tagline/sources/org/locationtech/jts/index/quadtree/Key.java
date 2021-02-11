package org.locationtech.jts.index.quadtree;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Envelope;

public class Key {
    private Envelope env = null;
    private int level = 0;

    /* renamed from: pt */
    private Coordinate f445pt;

    public static int computeQuadLevel(Envelope envelope) {
        Envelope env2 = envelope;
        double dx = env2.getWidth();
        double dy = env2.getHeight();
        return DoubleBits.exponent(dx > dy ? dx : dy) + 1;
    }

    public Key(Envelope itemEnv) {
        Coordinate coordinate;
        new Coordinate();
        this.f445pt = coordinate;
        computeKey(itemEnv);
    }

    public Coordinate getPoint() {
        return this.f445pt;
    }

    public int getLevel() {
        return this.level;
    }

    public Envelope getEnvelope() {
        return this.env;
    }

    public Coordinate getCentre() {
        Coordinate coordinate;
        new Coordinate((this.env.getMinX() + this.env.getMaxX()) / 2.0d, (this.env.getMinY() + this.env.getMaxY()) / 2.0d);
        return coordinate;
    }

    public void computeKey(Envelope envelope) {
        Envelope envelope2;
        Envelope itemEnv = envelope;
        this.level = computeQuadLevel(itemEnv);
        new Envelope();
        this.env = envelope2;
        computeKey(this.level, itemEnv);
        while (!this.env.contains(itemEnv)) {
            this.level++;
            computeKey(this.level, itemEnv);
        }
    }

    private void computeKey(int level2, Envelope envelope) {
        Envelope itemEnv = envelope;
        double quadSize = DoubleBits.powerOf2(level2);
        this.f445pt.f412x = Math.floor(itemEnv.getMinX() / quadSize) * quadSize;
        this.f445pt.f413y = Math.floor(itemEnv.getMinY() / quadSize) * quadSize;
        this.env.init(this.f445pt.f412x, this.f445pt.f412x + quadSize, this.f445pt.f413y, this.f445pt.f413y + quadSize);
    }
}
