package org.locationtech.jts.geomgraph;

public class Label {
    TopologyLocation[] elt = new TopologyLocation[2];

    public static Label toLineLabel(Label label) {
        Label label2;
        Label label3 = label;
        new Label(-1);
        Label lineLabel = label2;
        for (int i = 0; i < 2; i++) {
            lineLabel.setLocation(i, label3.getLocation(i));
        }
        return lineLabel;
    }

    public Label(int i) {
        TopologyLocation topologyLocation;
        TopologyLocation topologyLocation2;
        int onLoc = i;
        new TopologyLocation(onLoc);
        this.elt[0] = topologyLocation;
        new TopologyLocation(onLoc);
        this.elt[1] = topologyLocation2;
    }

    public Label(int geomIndex, int onLoc) {
        TopologyLocation topologyLocation;
        TopologyLocation topologyLocation2;
        new TopologyLocation(-1);
        this.elt[0] = topologyLocation;
        new TopologyLocation(-1);
        this.elt[1] = topologyLocation2;
        this.elt[geomIndex].setLocation(onLoc);
    }

    public Label(int i, int i2, int i3) {
        TopologyLocation topologyLocation;
        TopologyLocation topologyLocation2;
        int onLoc = i;
        int leftLoc = i2;
        int rightLoc = i3;
        new TopologyLocation(onLoc, leftLoc, rightLoc);
        this.elt[0] = topologyLocation;
        new TopologyLocation(onLoc, leftLoc, rightLoc);
        this.elt[1] = topologyLocation2;
    }

    public Label(int geomIndex, int onLoc, int leftLoc, int rightLoc) {
        TopologyLocation topologyLocation;
        TopologyLocation topologyLocation2;
        new TopologyLocation(-1, -1, -1);
        this.elt[0] = topologyLocation;
        new TopologyLocation(-1, -1, -1);
        this.elt[1] = topologyLocation2;
        this.elt[geomIndex].setLocations(onLoc, leftLoc, rightLoc);
    }

    public Label(Label label) {
        TopologyLocation topologyLocation;
        TopologyLocation topologyLocation2;
        Label lbl = label;
        new TopologyLocation(lbl.elt[0]);
        this.elt[0] = topologyLocation;
        new TopologyLocation(lbl.elt[1]);
        this.elt[1] = topologyLocation2;
    }

    public void flip() {
        this.elt[0].flip();
        this.elt[1].flip();
    }

    public int getLocation(int geomIndex, int posIndex) {
        return this.elt[geomIndex].get(posIndex);
    }

    public int getLocation(int geomIndex) {
        return this.elt[geomIndex].get(0);
    }

    public void setLocation(int geomIndex, int posIndex, int location) {
        this.elt[geomIndex].setLocation(posIndex, location);
    }

    public void setLocation(int geomIndex, int location) {
        this.elt[geomIndex].setLocation(0, location);
    }

    public void setAllLocations(int geomIndex, int location) {
        this.elt[geomIndex].setAllLocations(location);
    }

    public void setAllLocationsIfNull(int geomIndex, int location) {
        this.elt[geomIndex].setAllLocationsIfNull(location);
    }

    public void setAllLocationsIfNull(int i) {
        int location = i;
        setAllLocationsIfNull(0, location);
        setAllLocationsIfNull(1, location);
    }

    public void merge(Label label) {
        TopologyLocation topologyLocation;
        Label lbl = label;
        for (int i = 0; i < 2; i++) {
            if (this.elt[i] != null || lbl.elt[i] == null) {
                this.elt[i].merge(lbl.elt[i]);
            } else {
                new TopologyLocation(lbl.elt[i]);
                this.elt[i] = topologyLocation;
            }
        }
    }

    public int getGeometryCount() {
        int count = 0;
        if (!this.elt[0].isNull()) {
            count = 0 + 1;
        }
        if (!this.elt[1].isNull()) {
            count++;
        }
        return count;
    }

    public boolean isNull(int geomIndex) {
        return this.elt[geomIndex].isNull();
    }

    public boolean isAnyNull(int geomIndex) {
        return this.elt[geomIndex].isAnyNull();
    }

    public boolean isArea() {
        return this.elt[0].isArea() || this.elt[1].isArea();
    }

    public boolean isArea(int geomIndex) {
        return this.elt[geomIndex].isArea();
    }

    public boolean isLine(int geomIndex) {
        return this.elt[geomIndex].isLine();
    }

    public boolean isEqualOnSide(Label label, int i) {
        Label lbl = label;
        int side = i;
        return this.elt[0].isEqualOnSide(lbl.elt[0], side) && this.elt[1].isEqualOnSide(lbl.elt[1], side);
    }

    public boolean allPositionsEqual(int geomIndex, int loc) {
        return this.elt[geomIndex].allPositionsEqual(loc);
    }

    public void toLine(int i) {
        TopologyLocation topologyLocation;
        int geomIndex = i;
        if (this.elt[geomIndex].isArea()) {
            new TopologyLocation(this.elt[geomIndex].location[0]);
            this.elt[geomIndex] = topologyLocation;
        }
    }

    public String toString() {
        StringBuffer stringBuffer;
        new StringBuffer();
        StringBuffer buf = stringBuffer;
        if (this.elt[0] != null) {
            StringBuffer append = buf.append("A:");
            StringBuffer append2 = buf.append(this.elt[0].toString());
        }
        if (this.elt[1] != null) {
            StringBuffer append3 = buf.append(" B:");
            StringBuffer append4 = buf.append(this.elt[1].toString());
        }
        return buf.toString();
    }
}
