package org.osmdroid.api;

public interface IMapView {
    public static final String LOGTAG = "OsmDroid";

    IMapController getController();

    @Deprecated
    int getLatitudeSpan();

    double getLatitudeSpanDouble();

    @Deprecated
    int getLongitudeSpan();

    double getLongitudeSpanDouble();

    IGeoPoint getMapCenter();

    int getMaxZoomLevel();

    IProjection getProjection();

    @Deprecated
    int getZoomLevel();

    double getZoomLevelDouble();

    void setBackgroundColor(int i);
}
