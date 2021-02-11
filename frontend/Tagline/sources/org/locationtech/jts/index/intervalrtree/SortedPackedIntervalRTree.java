package org.locationtech.jts.index.intervalrtree;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.index.ItemVisitor;
import org.locationtech.jts.index.intervalrtree.IntervalRTreeNode;
import org.locationtech.jts.p006io.WKTWriter;

public class SortedPackedIntervalRTree {
    private List leaves;
    private int level = 0;
    private IntervalRTreeNode root = null;

    public SortedPackedIntervalRTree() {
        List list;
        new ArrayList();
        this.leaves = list;
    }

    public void insert(double d, double d2, Object obj) {
        Object obj2;
        Throwable th;
        double min = d;
        double max = d2;
        Object item = obj;
        if (this.root != null) {
            Throwable th2 = th;
            new IllegalStateException("Index cannot be added to once it has been queried");
            throw th2;
        }
        new IntervalRTreeLeafNode(min, max, item);
        boolean add = this.leaves.add(obj2);
    }

    private void init() {
        if (this.root == null) {
            buildRoot();
        }
    }

    private synchronized void buildRoot() {
        synchronized (this) {
            if (this.root == null) {
                this.root = buildTree();
            }
        }
    }

    private IntervalRTreeNode buildTree() {
        Comparator comparator;
        List list;
        new IntervalRTreeNode.NodeComparator();
        Collections.sort(this.leaves, comparator);
        List src = this.leaves;
        List list2 = list;
        new ArrayList();
        while (true) {
            List dest = list2;
            buildLevel(src, dest);
            if (dest.size() == 1) {
                return (IntervalRTreeNode) dest.get(0);
            }
            List temp = src;
            src = dest;
            list2 = temp;
        }
    }

    private void buildLevel(List list, List list2) {
        Object obj;
        List src = list;
        List dest = list2;
        this.level++;
        dest.clear();
        for (int i = 0; i < src.size(); i += 2) {
            IntervalRTreeNode n1 = (IntervalRTreeNode) src.get(i);
            if ((i + 1 < src.size() ? (IntervalRTreeNode) src.get(i) : null) == null) {
                boolean add = dest.add(n1);
            } else {
                new IntervalRTreeBranchNode((IntervalRTreeNode) src.get(i), (IntervalRTreeNode) src.get(i + 1));
                boolean add2 = dest.add(obj);
            }
        }
    }

    private void printNode(IntervalRTreeNode intervalRTreeNode) {
        Coordinate coordinate;
        Coordinate coordinate2;
        IntervalRTreeNode node = intervalRTreeNode;
        PrintStream printStream = System.out;
        new Coordinate(node.min, (double) this.level);
        new Coordinate(node.max, (double) this.level);
        printStream.println(WKTWriter.toLineString(coordinate, coordinate2));
    }

    public void query(double min, double max, ItemVisitor visitor) {
        init();
        this.root.query(min, max, visitor);
    }
}
