package org.locationtech.jts.noding;

import java.util.Iterator;

/* compiled from: SegmentNodeList */
class NodeVertexIterator implements Iterator {
    private SegmentNode currNode = null;
    private int currSegIndex = 0;
    private NodedSegmentString edge;
    private SegmentNode nextNode = null;
    private Iterator nodeIt;
    private SegmentNodeList nodeList;

    NodeVertexIterator(SegmentNodeList segmentNodeList) {
        SegmentNodeList nodeList2 = segmentNodeList;
        this.nodeList = nodeList2;
        this.edge = nodeList2.getEdge();
        this.nodeIt = nodeList2.iterator();
        readNextNode();
    }

    public boolean hasNext() {
        if (this.nextNode == null) {
            return false;
        }
        return true;
    }

    public Object next() {
        if (this.currNode == null) {
            this.currNode = this.nextNode;
            this.currSegIndex = this.currNode.segmentIndex;
            readNextNode();
            return this.currNode;
        } else if (this.nextNode == null) {
            return null;
        } else {
            if (this.nextNode.segmentIndex == this.currNode.segmentIndex) {
                this.currNode = this.nextNode;
                this.currSegIndex = this.currNode.segmentIndex;
                readNextNode();
                return this.currNode;
            }
            if (this.nextNode.segmentIndex > this.currNode.segmentIndex) {
            }
            return null;
        }
    }

    private void readNextNode() {
        if (this.nodeIt.hasNext()) {
            this.nextNode = (SegmentNode) this.nodeIt.next();
            return;
        }
        this.nextNode = null;
    }

    public void remove() {
        Throwable th;
        Throwable th2 = th;
        new UnsupportedOperationException(getClass().getName());
        throw th2;
    }
}
