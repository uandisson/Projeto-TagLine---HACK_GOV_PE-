package org.locationtech.jts.index;

import java.util.List;
import org.locationtech.jts.geom.Envelope;

public interface SpatialIndex {
    void insert(Envelope envelope, Object obj);

    List query(Envelope envelope);

    void query(Envelope envelope, ItemVisitor itemVisitor);

    boolean remove(Envelope envelope, Object obj);
}
