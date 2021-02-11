package org.osmdroid.events;

public abstract class MapAdapter implements MapListener {
    public MapAdapter() {
    }

    public boolean onScroll(ScrollEvent scrollEvent) {
        ScrollEvent scrollEvent2 = scrollEvent;
        return false;
    }

    public boolean onZoom(ZoomEvent zoomEvent) {
        ZoomEvent zoomEvent2 = zoomEvent;
        return false;
    }
}
