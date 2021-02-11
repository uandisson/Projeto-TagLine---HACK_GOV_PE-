package org.osmdroid.views.overlay;

public interface OverlayWithIWVisitor {
    void visit(Marker marker);

    void visit(Polygon polygon);

    void visit(Polyline polyline);
}
