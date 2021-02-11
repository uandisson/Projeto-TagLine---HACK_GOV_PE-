package org.locationtech.jts.geomgraph;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import org.locationtech.jts.algorithm.BoundaryNodeRule;
import org.locationtech.jts.algorithm.locate.SimplePointInAreaLocator;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.TopologyException;
import org.locationtech.jts.util.Assert;

public abstract class EdgeEndStar {
    protected List edgeList;
    protected Map edgeMap;
    private int[] ptInAreaLocation = {-1, -1};

    public abstract void insert(EdgeEnd edgeEnd);

    public EdgeEndStar() {
        Map map;
        new TreeMap();
        this.edgeMap = map;
    }

    /* access modifiers changed from: protected */
    public void insertEdgeEnd(EdgeEnd e, Object obj) {
        Object put = this.edgeMap.put(e, obj);
        this.edgeList = null;
    }

    public Coordinate getCoordinate() {
        Iterator it = iterator();
        if (!it.hasNext()) {
            return null;
        }
        return ((EdgeEnd) it.next()).getCoordinate();
    }

    public int getDegree() {
        return this.edgeMap.size();
    }

    public Iterator iterator() {
        return getEdges().iterator();
    }

    public List getEdges() {
        List list;
        if (this.edgeList == null) {
            new ArrayList(this.edgeMap.values());
            this.edgeList = list;
        }
        return this.edgeList;
    }

    public EdgeEnd getNextCW(EdgeEnd ee) {
        List edges = getEdges();
        int i = this.edgeList.indexOf(ee);
        int iNextCW = i - 1;
        if (i == 0) {
            iNextCW = this.edgeList.size() - 1;
        }
        return (EdgeEnd) this.edgeList.get(iNextCW);
    }

    public void computeLabelling(GeometryGraph[] geometryGraphArr) {
        int loc;
        GeometryGraph[] geomGraph = geometryGraphArr;
        computeEdgeEndLabels(geomGraph[0].getBoundaryNodeRule());
        propagateSideLabels(0);
        propagateSideLabels(1);
        boolean[] hasDimensionalCollapseEdge = {false, false};
        Iterator it = iterator();
        while (it.hasNext()) {
            Label label = ((EdgeEnd) it.next()).getLabel();
            for (int geomi = 0; geomi < 2; geomi++) {
                if (label.isLine(geomi) && label.getLocation(geomi) == 1) {
                    hasDimensionalCollapseEdge[geomi] = true;
                }
            }
        }
        Iterator it2 = iterator();
        while (it2.hasNext()) {
            EdgeEnd e = (EdgeEnd) it2.next();
            Label label2 = e.getLabel();
            for (int geomi2 = 0; geomi2 < 2; geomi2++) {
                if (label2.isAnyNull(geomi2)) {
                    if (hasDimensionalCollapseEdge[geomi2]) {
                        loc = 2;
                    } else {
                        loc = getLocation(geomi2, e.getCoordinate(), geomGraph);
                    }
                    label2.setAllLocationsIfNull(geomi2, loc);
                }
            }
        }
    }

    private void computeEdgeEndLabels(BoundaryNodeRule boundaryNodeRule) {
        BoundaryNodeRule boundaryNodeRule2 = boundaryNodeRule;
        Iterator it = iterator();
        while (it.hasNext()) {
            ((EdgeEnd) it.next()).computeLabel(boundaryNodeRule2);
        }
    }

    private int getLocation(int i, Coordinate coordinate, GeometryGraph[] geometryGraphArr) {
        int geomIndex = i;
        Coordinate p = coordinate;
        GeometryGraph[] geom = geometryGraphArr;
        if (this.ptInAreaLocation[geomIndex] == -1) {
            this.ptInAreaLocation[geomIndex] = SimplePointInAreaLocator.locate(p, geom[geomIndex].getGeometry());
        }
        return this.ptInAreaLocation[geomIndex];
    }

    public boolean isAreaLabelsConsistent(GeometryGraph geomGraph) {
        computeEdgeEndLabels(geomGraph.getBoundaryNodeRule());
        return checkAreaLabelsConsistent(0);
    }

    private boolean checkAreaLabelsConsistent(int i) {
        int geomIndex = i;
        List edges = getEdges();
        if (edges.size() <= 0) {
            return true;
        }
        int startLoc = ((EdgeEnd) edges.get(edges.size() - 1)).getLabel().getLocation(geomIndex, 1);
        Assert.isTrue(startLoc != -1, "Found unlabelled area edge");
        int currLoc = startLoc;
        Iterator it = iterator();
        while (it.hasNext()) {
            Label label = ((EdgeEnd) it.next()).getLabel();
            Assert.isTrue(label.isArea(geomIndex), "Found non-area edge");
            int leftLoc = label.getLocation(geomIndex, 1);
            int rightLoc = label.getLocation(geomIndex, 2);
            if (leftLoc == rightLoc) {
                return false;
            }
            if (rightLoc != currLoc) {
                return false;
            }
            currLoc = leftLoc;
        }
        return true;
    }

    /* access modifiers changed from: package-private */
    public void propagateSideLabels(int i) {
        Throwable th;
        StringBuilder sb;
        int geomIndex = i;
        int startLoc = -1;
        Iterator it = iterator();
        while (it.hasNext()) {
            Label label = ((EdgeEnd) it.next()).getLabel();
            if (label.isArea(geomIndex) && label.getLocation(geomIndex, 1) != -1) {
                startLoc = label.getLocation(geomIndex, 1);
            }
        }
        if (startLoc != -1) {
            int currLoc = startLoc;
            Iterator it2 = iterator();
            while (it2.hasNext()) {
                EdgeEnd e = (EdgeEnd) it2.next();
                Label label2 = e.getLabel();
                if (label2.getLocation(geomIndex, 0) == -1) {
                    label2.setLocation(geomIndex, 0, currLoc);
                }
                if (label2.isArea(geomIndex)) {
                    int leftLoc = label2.getLocation(geomIndex, 1);
                    int rightLoc = label2.getLocation(geomIndex, 2);
                    if (rightLoc == -1) {
                        Assert.isTrue(label2.getLocation(geomIndex, 1) == -1, "found single null side");
                        label2.setLocation(geomIndex, 2, currLoc);
                        label2.setLocation(geomIndex, 1, currLoc);
                    } else if (rightLoc != currLoc) {
                        Throwable th2 = th;
                        new TopologyException("side location conflict", e.getCoordinate());
                        throw th2;
                    } else {
                        if (leftLoc == -1) {
                            new StringBuilder();
                            Assert.shouldNeverReachHere(sb.append("found single null side (at ").append(e.getCoordinate()).append(")").toString());
                        }
                        currLoc = leftLoc;
                    }
                }
            }
        }
    }

    public int findIndex(EdgeEnd edgeEnd) {
        EdgeEnd eSearch = edgeEnd;
        Iterator it = iterator();
        for (int i = 0; i < this.edgeList.size(); i++) {
            if (((EdgeEnd) this.edgeList.get(i)) == eSearch) {
                return i;
            }
        }
        return -1;
    }

    public void print(PrintStream printStream) {
        StringBuilder sb;
        PrintStream out = printStream;
        PrintStream printStream2 = System.out;
        new StringBuilder();
        printStream2.println(sb.append("EdgeEndStar:   ").append(getCoordinate()).toString());
        Iterator it = iterator();
        while (it.hasNext()) {
            ((EdgeEnd) it.next()).print(out);
        }
    }

    public String toString() {
        StringBuffer stringBuffer;
        StringBuilder sb;
        new StringBuffer();
        StringBuffer buf = stringBuffer;
        new StringBuilder();
        StringBuffer append = buf.append(sb.append("EdgeEndStar:   ").append(getCoordinate()).toString());
        StringBuffer append2 = buf.append("\n");
        Iterator it = iterator();
        while (it.hasNext()) {
            StringBuffer append3 = buf.append((EdgeEnd) it.next());
            StringBuffer append4 = buf.append("\n");
        }
        return buf.toString();
    }
}
