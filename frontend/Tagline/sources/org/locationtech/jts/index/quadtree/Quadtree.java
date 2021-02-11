package org.locationtech.jts.index.quadtree;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.locationtech.jts.geom.Envelope;
import org.locationtech.jts.index.ArrayListVisitor;
import org.locationtech.jts.index.ItemVisitor;
import org.locationtech.jts.index.SpatialIndex;

public class Quadtree implements SpatialIndex, Serializable {
    private static final long serialVersionUID = -7461163625812743604L;
    private double minExtent = 1.0d;
    private Root root;

    public static Envelope ensureExtent(Envelope envelope, double d) {
        Envelope itemEnv;
        Envelope itemEnv2 = envelope;
        double minExtent2 = d;
        double minx = itemEnv2.getMinX();
        double maxx = itemEnv2.getMaxX();
        double miny = itemEnv2.getMinY();
        double maxy = itemEnv2.getMaxY();
        if (minx != maxx && miny != maxy) {
            return itemEnv2;
        }
        if (minx == maxx) {
            minx -= minExtent2 / 2.0d;
            maxx = minx + (minExtent2 / 2.0d);
        }
        if (miny == maxy) {
            miny -= minExtent2 / 2.0d;
            maxy = miny + (minExtent2 / 2.0d);
        }
        new Envelope(minx, maxx, miny, maxy);
        return itemEnv;
    }

    public Quadtree() {
        Root root2;
        new Root();
        this.root = root2;
    }

    public int depth() {
        if (this.root != null) {
            return this.root.depth();
        }
        return 0;
    }

    public boolean isEmpty() {
        if (this.root == null) {
            return true;
        }
        return false;
    }

    public int size() {
        if (this.root != null) {
            return this.root.size();
        }
        return 0;
    }

    public void insert(Envelope envelope, Object item) {
        Envelope itemEnv = envelope;
        collectStats(itemEnv);
        this.root.insert(ensureExtent(itemEnv, this.minExtent), item);
    }

    public boolean remove(Envelope itemEnv, Object item) {
        return this.root.remove(ensureExtent(itemEnv, this.minExtent), item);
    }

    public List query(Envelope searchEnv) {
        ArrayListVisitor arrayListVisitor;
        new ArrayListVisitor();
        ArrayListVisitor visitor = arrayListVisitor;
        query(searchEnv, visitor);
        return visitor.getItems();
    }

    public void query(Envelope searchEnv, ItemVisitor visitor) {
        this.root.visit(searchEnv, visitor);
    }

    public List queryAll() {
        List list;
        new ArrayList();
        List foundItems = list;
        List addAllItems = this.root.addAllItems(foundItems);
        return foundItems;
    }

    private void collectStats(Envelope envelope) {
        Envelope itemEnv = envelope;
        double delX = itemEnv.getWidth();
        if (delX < this.minExtent && delX > 0.0d) {
            this.minExtent = delX;
        }
        double delY = itemEnv.getHeight();
        if (delY < this.minExtent && delY > 0.0d) {
            this.minExtent = delY;
        }
    }
}
