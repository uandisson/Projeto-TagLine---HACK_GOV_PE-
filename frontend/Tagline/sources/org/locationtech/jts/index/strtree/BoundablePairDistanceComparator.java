package org.locationtech.jts.index.strtree;

import java.io.Serializable;
import java.util.Comparator;

public class BoundablePairDistanceComparator implements Comparator<BoundablePair>, Serializable {
    boolean normalOrder;

    public BoundablePairDistanceComparator(boolean normalOrder2) {
        this.normalOrder = normalOrder2;
    }

    public int compare(BoundablePair p1, BoundablePair p2) {
        double distance1 = p1.getDistance();
        double distance2 = p2.getDistance();
        if (this.normalOrder) {
            if (distance1 > distance2) {
                return 1;
            }
            if (distance1 == distance2) {
                return 0;
            }
            return -1;
        } else if (distance1 > distance2) {
            return -1;
        } else {
            if (distance1 == distance2) {
                return 0;
            }
            return 1;
        }
    }
}
