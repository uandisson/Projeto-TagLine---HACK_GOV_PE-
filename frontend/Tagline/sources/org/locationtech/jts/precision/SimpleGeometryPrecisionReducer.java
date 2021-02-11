package org.locationtech.jts.precision;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.CoordinateList;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.LineString;
import org.locationtech.jts.geom.LinearRing;
import org.locationtech.jts.geom.PrecisionModel;
import org.locationtech.jts.geom.util.GeometryEditor;

public class SimpleGeometryPrecisionReducer {
    private boolean changePrecisionModel = false;
    /* access modifiers changed from: private */
    public PrecisionModel newPrecisionModel;
    /* access modifiers changed from: private */
    public boolean removeCollapsed = true;

    public static Geometry reduce(Geometry g, PrecisionModel precModel) {
        SimpleGeometryPrecisionReducer reducer;
        new SimpleGeometryPrecisionReducer(precModel);
        return reducer.reduce(g);
    }

    public SimpleGeometryPrecisionReducer(PrecisionModel pm) {
        this.newPrecisionModel = pm;
    }

    public void setRemoveCollapsedComponents(boolean removeCollapsed2) {
        boolean z = removeCollapsed2;
        this.removeCollapsed = z;
    }

    public void setChangePrecisionModel(boolean changePrecisionModel2) {
        boolean z = changePrecisionModel2;
        this.changePrecisionModel = z;
    }

    public Geometry reduce(Geometry geometry) {
        GeometryEditor geometryEditor;
        GeometryEditor geomEdit;
        GeometryEditor.GeometryEditorOperation geometryEditorOperation;
        GeometryFactory newFactory;
        GeometryEditor geometryEditor2;
        Geometry geom = geometry;
        if (this.changePrecisionModel) {
            new GeometryFactory(this.newPrecisionModel, geom.getFactory().getSRID());
            new GeometryEditor(newFactory);
            geomEdit = geometryEditor2;
        } else {
            new GeometryEditor();
            geomEdit = geometryEditor;
        }
        new PrecisionReducerCoordinateOperation(this, (C15741) null);
        return geomEdit.edit(geom, geometryEditorOperation);
    }

    private class PrecisionReducerCoordinateOperation extends GeometryEditor.CoordinateOperation {
        final /* synthetic */ SimpleGeometryPrecisionReducer this$0;

        private PrecisionReducerCoordinateOperation(SimpleGeometryPrecisionReducer simpleGeometryPrecisionReducer) {
            this.this$0 = simpleGeometryPrecisionReducer;
        }

        /* JADX INFO: this call moved to the top of the method (can break code semantics) */
        /* synthetic */ PrecisionReducerCoordinateOperation(SimpleGeometryPrecisionReducer x0, C15741 r7) {
            this(x0);
            C15741 r2 = r7;
        }

        public Coordinate[] edit(Coordinate[] coordinateArr, Geometry geometry) {
            CoordinateList noRepeatedCoordList;
            Coordinate coordinate;
            Coordinate[] coordinates = coordinateArr;
            Geometry geom = geometry;
            if (coordinates.length == 0) {
                return null;
            }
            Coordinate[] reducedCoords = new Coordinate[coordinates.length];
            for (int i = 0; i < coordinates.length; i++) {
                new Coordinate(coordinates[i]);
                Coordinate coord = coordinate;
                this.this$0.newPrecisionModel.makePrecise(coord);
                reducedCoords[i] = coord;
            }
            new CoordinateList(reducedCoords, false);
            Coordinate[] noRepeatedCoords = noRepeatedCoordList.toCoordinateArray();
            int minLength = 0;
            if (geom instanceof LineString) {
                minLength = 2;
            }
            if (geom instanceof LinearRing) {
                minLength = 4;
            }
            Coordinate[] collapsedCoords = reducedCoords;
            if (this.this$0.removeCollapsed) {
                collapsedCoords = null;
            }
            if (noRepeatedCoords.length < minLength) {
                return collapsedCoords;
            }
            return noRepeatedCoords;
        }
    }
}
