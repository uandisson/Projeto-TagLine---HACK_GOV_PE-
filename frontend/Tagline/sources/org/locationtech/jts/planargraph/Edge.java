package org.locationtech.jts.planargraph;

public class Edge extends GraphComponent {
    protected DirectedEdge[] dirEdge;

    public Edge() {
    }

    public Edge(DirectedEdge de0, DirectedEdge de1) {
        setDirectedEdges(de0, de1);
    }

    public void setDirectedEdges(DirectedEdge directedEdge, DirectedEdge directedEdge2) {
        DirectedEdge de0 = directedEdge;
        DirectedEdge de1 = directedEdge2;
        DirectedEdge[] directedEdgeArr = new DirectedEdge[2];
        directedEdgeArr[0] = de0;
        DirectedEdge[] directedEdgeArr2 = directedEdgeArr;
        directedEdgeArr2[1] = de1;
        this.dirEdge = directedEdgeArr2;
        de0.setEdge(this);
        de1.setEdge(this);
        de0.setSym(de1);
        de1.setSym(de0);
        de0.getFromNode().addOutEdge(de0);
        de1.getFromNode().addOutEdge(de1);
    }

    public DirectedEdge getDirEdge(int i) {
        return this.dirEdge[i];
    }

    public DirectedEdge getDirEdge(Node node) {
        Node fromNode = node;
        if (this.dirEdge[0].getFromNode() == fromNode) {
            return this.dirEdge[0];
        }
        if (this.dirEdge[1].getFromNode() == fromNode) {
            return this.dirEdge[1];
        }
        return null;
    }

    public Node getOppositeNode(Node node) {
        Node node2 = node;
        if (this.dirEdge[0].getFromNode() == node2) {
            return this.dirEdge[0].getToNode();
        }
        if (this.dirEdge[1].getFromNode() == node2) {
            return this.dirEdge[1].getToNode();
        }
        return null;
    }

    /* access modifiers changed from: package-private */
    public void remove() {
        this.dirEdge = null;
    }

    public boolean isRemoved() {
        return this.dirEdge == null;
    }
}
