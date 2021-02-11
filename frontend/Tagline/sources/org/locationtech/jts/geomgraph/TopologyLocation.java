package org.locationtech.jts.geomgraph;

import org.locationtech.jts.geom.Location;

public class TopologyLocation {
    int[] location;

    public TopologyLocation(int[] location2) {
        init(location2.length);
    }

    public TopologyLocation(int on, int left, int right) {
        init(3);
        this.location[0] = on;
        this.location[1] = left;
        this.location[2] = right;
    }

    public TopologyLocation(int on) {
        init(1);
        this.location[0] = on;
    }

    public TopologyLocation(TopologyLocation topologyLocation) {
        TopologyLocation gl = topologyLocation;
        init(gl.location.length);
        if (gl != null) {
            for (int i = 0; i < this.location.length; i++) {
                this.location[i] = gl.location[i];
            }
        }
    }

    private void init(int size) {
        this.location = new int[size];
        setAllLocations(-1);
    }

    public int get(int i) {
        int posIndex = i;
        if (posIndex < this.location.length) {
            return this.location[posIndex];
        }
        return -1;
    }

    public boolean isNull() {
        for (int i = 0; i < this.location.length; i++) {
            if (this.location[i] != -1) {
                return false;
            }
        }
        return true;
    }

    public boolean isAnyNull() {
        for (int i = 0; i < this.location.length; i++) {
            if (this.location[i] == -1) {
                return true;
            }
        }
        return false;
    }

    public boolean isEqualOnSide(TopologyLocation le, int i) {
        int locIndex = i;
        return this.location[locIndex] == le.location[locIndex];
    }

    public boolean isArea() {
        return this.location.length > 1;
    }

    public boolean isLine() {
        return this.location.length == 1;
    }

    public void flip() {
        if (this.location.length > 1) {
            int temp = this.location[1];
            this.location[1] = this.location[2];
            this.location[2] = temp;
        }
    }

    public void setAllLocations(int i) {
        int locValue = i;
        for (int i2 = 0; i2 < this.location.length; i2++) {
            this.location[i2] = locValue;
        }
    }

    public void setAllLocationsIfNull(int i) {
        int locValue = i;
        for (int i2 = 0; i2 < this.location.length; i2++) {
            if (this.location[i2] == -1) {
                this.location[i2] = locValue;
            }
        }
    }

    public void setLocation(int locIndex, int locValue) {
        this.location[locIndex] = locValue;
    }

    public void setLocation(int locValue) {
        setLocation(0, locValue);
    }

    public int[] getLocations() {
        return this.location;
    }

    public void setLocations(int on, int left, int right) {
        this.location[0] = on;
        this.location[1] = left;
        this.location[2] = right;
    }

    public boolean allPositionsEqual(int i) {
        int loc = i;
        for (int i2 = 0; i2 < this.location.length; i2++) {
            if (this.location[i2] != loc) {
                return false;
            }
        }
        return true;
    }

    public void merge(TopologyLocation topologyLocation) {
        TopologyLocation gl = topologyLocation;
        if (gl.location.length > this.location.length) {
            int[] newLoc = new int[3];
            newLoc[0] = this.location[0];
            newLoc[1] = -1;
            newLoc[2] = -1;
            this.location = newLoc;
        }
        for (int i = 0; i < this.location.length; i++) {
            if (this.location[i] == -1 && i < gl.location.length) {
                this.location[i] = gl.location[i];
            }
        }
    }

    public String toString() {
        StringBuffer stringBuffer;
        new StringBuffer();
        StringBuffer buf = stringBuffer;
        if (this.location.length > 1) {
            StringBuffer append = buf.append(Location.toLocationSymbol(this.location[1]));
        }
        StringBuffer append2 = buf.append(Location.toLocationSymbol(this.location[0]));
        if (this.location.length > 1) {
            StringBuffer append3 = buf.append(Location.toLocationSymbol(this.location[2]));
        }
        return buf.toString();
    }
}
