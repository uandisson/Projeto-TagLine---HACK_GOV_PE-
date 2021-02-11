package org.locationtech.jts.geomgraph;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.IntersectionMatrix;
import org.locationtech.jts.util.Assert;

public abstract class GraphComponent {
    private boolean isCovered = false;
    private boolean isCoveredSet = false;
    private boolean isInResult = false;
    private boolean isVisited = false;
    protected Label label;

    /* access modifiers changed from: protected */
    public abstract void computeIM(IntersectionMatrix intersectionMatrix);

    public abstract Coordinate getCoordinate();

    public abstract boolean isIsolated();

    public GraphComponent() {
    }

    public GraphComponent(Label label2) {
        this.label = label2;
    }

    public Label getLabel() {
        return this.label;
    }

    public void setLabel(Label label2) {
        Label label3 = label2;
        this.label = label3;
    }

    public void setInResult(boolean isInResult2) {
        boolean z = isInResult2;
        this.isInResult = z;
    }

    public boolean isInResult() {
        return this.isInResult;
    }

    public void setCovered(boolean isCovered2) {
        this.isCovered = isCovered2;
        this.isCoveredSet = true;
    }

    public boolean isCovered() {
        return this.isCovered;
    }

    public boolean isCoveredSet() {
        return this.isCoveredSet;
    }

    public boolean isVisited() {
        return this.isVisited;
    }

    public void setVisited(boolean isVisited2) {
        boolean z = isVisited2;
        this.isVisited = z;
    }

    public void updateIM(IntersectionMatrix intersectionMatrix) {
        IntersectionMatrix im = intersectionMatrix;
        Assert.isTrue(this.label.getGeometryCount() >= 2, "found partial label");
        computeIM(im);
    }
}
