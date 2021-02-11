package org.locationtech.jts.geomgraph;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.locationtech.jts.geom.TopologyException;
import org.locationtech.jts.util.Assert;

public class DirectedEdgeStar extends EdgeEndStar {
    private final int LINKING_TO_OUTGOING = 2;
    private final int SCANNING_FOR_INCOMING = 1;
    private Label label;
    private List resultAreaEdgeList;

    public DirectedEdgeStar() {
    }

    public void insert(EdgeEnd ee) {
        DirectedEdge de = (DirectedEdge) ee;
        insertEdgeEnd(de, de);
    }

    public Label getLabel() {
        return this.label;
    }

    public int getOutgoingDegree() {
        int degree = 0;
        Iterator it = iterator();
        while (it.hasNext()) {
            if (((DirectedEdge) it.next()).isInResult()) {
                degree++;
            }
        }
        return degree;
    }

    public int getOutgoingDegree(EdgeRing edgeRing) {
        EdgeRing er = edgeRing;
        int degree = 0;
        Iterator it = iterator();
        while (it.hasNext()) {
            if (((DirectedEdge) it.next()).getEdgeRing() == er) {
                degree++;
            }
        }
        return degree;
    }

    public DirectedEdge getRightmostEdge() {
        List edges = getEdges();
        int size = edges.size();
        if (size < 1) {
            return null;
        }
        DirectedEdge de0 = (DirectedEdge) edges.get(0);
        if (size == 1) {
            return de0;
        }
        DirectedEdge deLast = (DirectedEdge) edges.get(size - 1);
        int quad0 = de0.getQuadrant();
        int quad1 = deLast.getQuadrant();
        if (Quadrant.isNorthern(quad0) && Quadrant.isNorthern(quad1)) {
            return de0;
        }
        if (!Quadrant.isNorthern(quad0) && !Quadrant.isNorthern(quad1)) {
            return deLast;
        }
        if (de0.getDy() != 0.0d) {
            return de0;
        }
        if (deLast.getDy() != 0.0d) {
            return deLast;
        }
        Assert.shouldNeverReachHere("found two horizontal edges incident on node");
        return null;
    }

    public void computeLabelling(GeometryGraph[] geom) {
        Label label2;
        super.computeLabelling(geom);
        new Label(-1);
        this.label = label2;
        Iterator it = iterator();
        while (it.hasNext()) {
            Label eLabel = ((EdgeEnd) it.next()).getEdge().getLabel();
            for (int i = 0; i < 2; i++) {
                int eLoc = eLabel.getLocation(i);
                if (eLoc == 0 || eLoc == 1) {
                    this.label.setLocation(i, 0);
                }
            }
        }
    }

    public void mergeSymLabels() {
        Iterator it = iterator();
        while (it.hasNext()) {
            DirectedEdge de = (DirectedEdge) it.next();
            de.getLabel().merge(de.getSym().getLabel());
        }
    }

    public void updateLabelling(Label label2) {
        Label nodeLabel = label2;
        Iterator it = iterator();
        while (it.hasNext()) {
            Label label3 = ((DirectedEdge) it.next()).getLabel();
            label3.setAllLocationsIfNull(0, nodeLabel.getLocation(0));
            label3.setAllLocationsIfNull(1, nodeLabel.getLocation(1));
        }
    }

    private List getResultAreaEdges() {
        List list;
        if (this.resultAreaEdgeList != null) {
            return this.resultAreaEdgeList;
        }
        new ArrayList();
        this.resultAreaEdgeList = list;
        Iterator it = iterator();
        while (it.hasNext()) {
            DirectedEdge de = (DirectedEdge) it.next();
            if (de.isInResult() || de.getSym().isInResult()) {
                boolean add = this.resultAreaEdgeList.add(de);
            }
        }
        return this.resultAreaEdgeList;
    }

    public void linkResultDirectedEdges() {
        Throwable th;
        List resultAreaEdges = getResultAreaEdges();
        DirectedEdge firstOut = null;
        DirectedEdge incoming = null;
        int state = 1;
        for (int i = 0; i < this.resultAreaEdgeList.size(); i++) {
            DirectedEdge nextOut = (DirectedEdge) this.resultAreaEdgeList.get(i);
            DirectedEdge nextIn = nextOut.getSym();
            if (nextOut.getLabel().isArea()) {
                if (firstOut == null && nextOut.isInResult()) {
                    firstOut = nextOut;
                }
                switch (state) {
                    case 1:
                        if (nextIn.isInResult()) {
                            incoming = nextIn;
                            state = 2;
                            break;
                        } else {
                            break;
                        }
                    case 2:
                        if (nextOut.isInResult()) {
                            incoming.setNext(nextOut);
                            state = 1;
                            break;
                        } else {
                            break;
                        }
                }
            }
        }
        if (state != 2) {
            return;
        }
        if (firstOut == null) {
            Throwable th2 = th;
            new TopologyException("no outgoing dirEdge found", getCoordinate());
            throw th2;
        }
        Assert.isTrue(firstOut.isInResult(), "unable to link last incoming dirEdge");
        incoming.setNext(firstOut);
    }

    public void linkMinimalDirectedEdges(EdgeRing edgeRing) {
        EdgeRing er = edgeRing;
        DirectedEdge firstOut = null;
        DirectedEdge incoming = null;
        int state = 1;
        for (int i = this.resultAreaEdgeList.size() - 1; i >= 0; i--) {
            DirectedEdge nextOut = (DirectedEdge) this.resultAreaEdgeList.get(i);
            DirectedEdge nextIn = nextOut.getSym();
            if (firstOut == null && nextOut.getEdgeRing() == er) {
                firstOut = nextOut;
            }
            switch (state) {
                case 1:
                    if (nextIn.getEdgeRing() == er) {
                        incoming = nextIn;
                        state = 2;
                        break;
                    } else {
                        break;
                    }
                case 2:
                    if (nextOut.getEdgeRing() == er) {
                        incoming.setNextMin(nextOut);
                        state = 1;
                        break;
                    } else {
                        break;
                    }
            }
        }
        if (state == 2) {
            Assert.isTrue(firstOut != null, "found null for first outgoing dirEdge");
            Assert.isTrue(firstOut.getEdgeRing() == er, "unable to link last incoming dirEdge");
            incoming.setNextMin(firstOut);
        }
    }

    public void linkAllDirectedEdges() {
        List edges = getEdges();
        DirectedEdge prevOut = null;
        DirectedEdge firstIn = null;
        for (int i = this.edgeList.size() - 1; i >= 0; i--) {
            DirectedEdge nextOut = (DirectedEdge) this.edgeList.get(i);
            DirectedEdge nextIn = nextOut.getSym();
            if (firstIn == null) {
                firstIn = nextIn;
            }
            if (prevOut != null) {
                nextIn.setNext(prevOut);
            }
            prevOut = nextOut;
        }
        firstIn.setNext(prevOut);
    }

    public void findCoveredLineEdges() {
        int startLoc = -1;
        Iterator it = iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            DirectedEdge nextOut = (DirectedEdge) it.next();
            DirectedEdge nextIn = nextOut.getSym();
            if (!nextOut.isLineEdge()) {
                if (nextOut.isInResult()) {
                    startLoc = 0;
                    break;
                } else if (nextIn.isInResult()) {
                    startLoc = 2;
                    break;
                }
            }
        }
        if (startLoc != -1) {
            int currLoc = startLoc;
            Iterator it2 = iterator();
            while (it2.hasNext()) {
                DirectedEdge nextOut2 = (DirectedEdge) it2.next();
                DirectedEdge nextIn2 = nextOut2.getSym();
                if (nextOut2.isLineEdge()) {
                    nextOut2.getEdge().setCovered(currLoc == 0);
                } else {
                    if (nextOut2.isInResult()) {
                        currLoc = 2;
                    }
                    if (nextIn2.isInResult()) {
                        currLoc = 0;
                    }
                }
            }
        }
    }

    public void computeDepths(DirectedEdge directedEdge) {
        Throwable th;
        StringBuilder sb;
        DirectedEdge de = directedEdge;
        int edgeIndex = findIndex(de);
        Label label2 = de.getLabel();
        int startDepth = de.getDepth(1);
        if (computeDepths(0, edgeIndex, computeDepths(edgeIndex + 1, this.edgeList.size(), startDepth)) != de.getDepth(2)) {
            Throwable th2 = th;
            new StringBuilder();
            new TopologyException(sb.append("depth mismatch at ").append(de.getCoordinate()).toString());
            throw th2;
        }
    }

    private int computeDepths(int startIndex, int i, int startDepth) {
        int endIndex = i;
        int currDepth = startDepth;
        for (int i2 = startIndex; i2 < endIndex; i2++) {
            DirectedEdge nextDe = (DirectedEdge) this.edgeList.get(i2);
            Label label2 = nextDe.getLabel();
            nextDe.setEdgeDepths(2, currDepth);
            currDepth = nextDe.getDepth(1);
        }
        return currDepth;
    }

    public void print(PrintStream printStream) {
        StringBuilder sb;
        PrintStream out = printStream;
        PrintStream printStream2 = System.out;
        new StringBuilder();
        printStream2.println(sb.append("DirectedEdgeStar: ").append(getCoordinate()).toString());
        Iterator it = iterator();
        while (it.hasNext()) {
            DirectedEdge de = (DirectedEdge) it.next();
            out.print("out ");
            de.print(out);
            out.println();
            out.print("in ");
            de.getSym().print(out);
            out.println();
        }
    }
}
