package org.osmdroid.events;

import org.osmdroid.views.MapView;

public class ScrollEvent implements MapEvent {
    protected MapView source;

    /* renamed from: x */
    protected int f524x;

    /* renamed from: y */
    protected int f525y;

    public ScrollEvent(MapView source2, int x, int y) {
        this.source = source2;
        this.f524x = x;
        this.f525y = y;
    }

    public MapView getSource() {
        return this.source;
    }

    public int getX() {
        return this.f524x;
    }

    public int getY() {
        return this.f525y;
    }

    public String toString() {
        StringBuilder sb;
        new StringBuilder();
        return sb.append("ScrollEvent [source=").append(this.source).append(", x=").append(this.f524x).append(", y=").append(this.f525y).append("]").toString();
    }
}
