package org.osmdroid.events;

import org.osmdroid.views.MapView;

public class ZoomEvent implements MapEvent {
    protected MapView source;
    protected double zoomLevel;

    public ZoomEvent(MapView source2, double zoomLevel2) {
        this.source = source2;
        this.zoomLevel = zoomLevel2;
    }

    public MapView getSource() {
        return this.source;
    }

    public double getZoomLevel() {
        return this.zoomLevel;
    }

    public String toString() {
        StringBuilder sb;
        new StringBuilder();
        return sb.append("ZoomEvent [source=").append(this.source).append(", zoomLevel=").append(this.zoomLevel).append("]").toString();
    }
}
