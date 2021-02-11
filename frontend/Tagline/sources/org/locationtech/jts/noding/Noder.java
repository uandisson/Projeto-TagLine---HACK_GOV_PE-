package org.locationtech.jts.noding;

import java.util.Collection;

public interface Noder {
    void computeNodes(Collection collection);

    Collection getNodedSubstrings();
}
