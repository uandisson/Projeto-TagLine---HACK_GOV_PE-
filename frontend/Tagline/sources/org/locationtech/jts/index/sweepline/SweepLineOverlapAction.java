package org.locationtech.jts.index.sweepline;

public interface SweepLineOverlapAction {
    void overlap(SweepLineInterval sweepLineInterval, SweepLineInterval sweepLineInterval2);
}
