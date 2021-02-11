package org.locationtech.jts.index.kdtree;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.CoordinateList;
import org.locationtech.jts.geom.Envelope;

public class KdTree {
    private long numberOfNodes;
    private KdNode root;
    private double tolerance;

    public static Coordinate[] toCoordinates(Collection kdnodes) {
        return toCoordinates(kdnodes, false);
    }

    public static Coordinate[] toCoordinates(Collection kdnodes, boolean z) {
        CoordinateList coordinateList;
        boolean includeRepeated = z;
        new CoordinateList();
        CoordinateList coord = coordinateList;
        Iterator it = kdnodes.iterator();
        while (it.hasNext()) {
            KdNode node = (KdNode) it.next();
            int count = includeRepeated ? node.getCount() : 1;
            for (int i = 0; i < count; i++) {
                coord.add(node.getCoordinate(), true);
            }
        }
        return coord.toCoordinateArray();
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public KdTree() {
        this(0.0d);
    }

    public KdTree(double tolerance2) {
        this.root = null;
        this.tolerance = tolerance2;
    }

    public boolean isEmpty() {
        if (this.root == null) {
            return true;
        }
        return false;
    }

    public KdNode insert(Coordinate p) {
        return insert(p, (Object) null);
    }

    public KdNode insert(Coordinate coordinate, Object obj) {
        KdNode matchNode;
        KdNode kdNode;
        Coordinate p = coordinate;
        Object data = obj;
        if (this.root == null) {
            new KdNode(p, data);
            this.root = kdNode;
            return this.root;
        } else if (this.tolerance <= 0.0d || (matchNode = findBestMatchNode(p)) == null) {
            return insertExact(p, data);
        } else {
            matchNode.increment();
            return matchNode;
        }
    }

    private KdNode findBestMatchNode(Coordinate p) {
        BestMatchVisitor bestMatchVisitor;
        new BestMatchVisitor(p, this.tolerance);
        BestMatchVisitor visitor = bestMatchVisitor;
        query(visitor.queryEnvelope(), (KdNodeVisitor) visitor);
        return visitor.getNode();
    }

    private static class BestMatchVisitor implements KdNodeVisitor {
        private double matchDist = 0.0d;
        private KdNode matchNode = null;

        /* renamed from: p */
        private Coordinate f443p;
        private double tolerance;

        public BestMatchVisitor(Coordinate p, double tolerance2) {
            this.f443p = p;
            this.tolerance = tolerance2;
        }

        public Envelope queryEnvelope() {
            Envelope envelope;
            new Envelope(this.f443p);
            Envelope queryEnv = envelope;
            queryEnv.expandBy(this.tolerance);
            return queryEnv;
        }

        public KdNode getNode() {
            return this.matchNode;
        }

        public void visit(KdNode kdNode) {
            KdNode node = kdNode;
            double dist = this.f443p.distance(node.getCoordinate());
            if (dist <= this.tolerance) {
                boolean update = false;
                if (this.matchNode == null || dist < this.matchDist || (this.matchNode != null && dist == this.matchDist && node.getCoordinate().compareTo(this.matchNode.getCoordinate()) < 1)) {
                    update = true;
                }
                if (update) {
                    this.matchNode = node;
                    this.matchDist = dist;
                }
            }
        }
    }

    private KdNode insertExact(Coordinate coordinate, Object obj) {
        KdNode kdNode;
        boolean z;
        Coordinate p = coordinate;
        Object data = obj;
        KdNode currentNode = this.root;
        KdNode leafNode = this.root;
        boolean isOddLevel = true;
        boolean isLessThan = true;
        while (currentNode != null) {
            if (currentNode != null) {
                if (p.distance(currentNode.getCoordinate()) <= this.tolerance) {
                    currentNode.increment();
                    return currentNode;
                }
            }
            if (isOddLevel) {
                isLessThan = p.f412x < currentNode.getX();
            } else {
                isLessThan = p.f413y < currentNode.getY();
            }
            leafNode = currentNode;
            if (isLessThan) {
                currentNode = currentNode.getLeft();
            } else {
                currentNode = currentNode.getRight();
            }
            if (!isOddLevel) {
                z = true;
            } else {
                z = false;
            }
            isOddLevel = z;
        }
        this.numberOfNodes++;
        new KdNode(p, data);
        KdNode node = kdNode;
        if (isLessThan) {
            leafNode.setLeft(node);
        } else {
            leafNode.setRight(node);
        }
        return node;
    }

    private void queryNode(KdNode kdNode, Envelope envelope, boolean z, KdNodeVisitor kdNodeVisitor) {
        double min;
        double max;
        double discriminant;
        KdNode currentNode = kdNode;
        Envelope queryEnv = envelope;
        boolean odd = z;
        KdNodeVisitor visitor = kdNodeVisitor;
        if (currentNode != null) {
            if (odd) {
                min = queryEnv.getMinX();
                max = queryEnv.getMaxX();
                discriminant = currentNode.getX();
            } else {
                min = queryEnv.getMinY();
                max = queryEnv.getMaxY();
                discriminant = currentNode.getY();
            }
            boolean searchLeft = min < discriminant;
            boolean searchRight = discriminant <= max;
            if (searchLeft) {
                queryNode(currentNode.getLeft(), queryEnv, !odd, visitor);
            }
            if (queryEnv.contains(currentNode.getCoordinate())) {
                visitor.visit(currentNode);
            }
            if (searchRight) {
                queryNode(currentNode.getRight(), queryEnv, !odd, visitor);
            }
        }
    }

    public void query(Envelope queryEnv, KdNodeVisitor visitor) {
        queryNode(this.root, queryEnv, true, visitor);
    }

    public List query(Envelope queryEnv) {
        List list;
        new ArrayList();
        List result = list;
        query(queryEnv, result);
        return result;
    }

    public void query(Envelope queryEnv, List result) {
        KdNodeVisitor kdNodeVisitor;
        final List list = result;
        new KdNodeVisitor(this) {
            final /* synthetic */ KdTree this$0;

            {
                this.this$0 = this$0;
            }

            public void visit(KdNode node) {
                boolean add = list.add(node);
            }
        };
        queryNode(this.root, queryEnv, true, kdNodeVisitor);
    }
}
