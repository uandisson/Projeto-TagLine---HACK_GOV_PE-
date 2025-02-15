package org.osmdroid.views.overlay.simplefastpoint;

import java.util.Iterator;
import java.util.List;
import org.osmdroid.api.IGeoPoint;
import org.osmdroid.views.overlay.simplefastpoint.SimpleFastPointOverlay;

public final class SimplePointTheme implements SimpleFastPointOverlay.PointAdapter {
    private boolean mLabelled;
    private final List<IGeoPoint> mPoints;

    public SimplePointTheme(List<IGeoPoint> pPoints, boolean labelled) {
        this.mPoints = pPoints;
        this.mLabelled = labelled;
    }

    public int size() {
        return this.mPoints.size();
    }

    public IGeoPoint get(int i) {
        return this.mPoints.get(i);
    }

    public boolean isLabelled() {
        return this.mLabelled;
    }

    public Iterator<IGeoPoint> iterator() {
        return this.mPoints.iterator();
    }
}
