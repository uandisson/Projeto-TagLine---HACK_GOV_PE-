package org.locationtech.jts.precision;

import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Polygonal;
import org.locationtech.jts.geom.PrecisionModel;
import org.locationtech.jts.geom.util.GeometryEditor;

public class GeometryPrecisionReducer {
    private boolean changePrecisionModel = false;
    private boolean isPointwise = false;
    private boolean removeCollapsed = true;
    private PrecisionModel targetPM;

    public static Geometry reduce(Geometry g, PrecisionModel precModel) {
        GeometryPrecisionReducer reducer;
        new GeometryPrecisionReducer(precModel);
        return reducer.reduce(g);
    }

    public static Geometry reducePointwise(Geometry g, PrecisionModel precModel) {
        GeometryPrecisionReducer geometryPrecisionReducer;
        new GeometryPrecisionReducer(precModel);
        GeometryPrecisionReducer reducer = geometryPrecisionReducer;
        reducer.setPointwise(true);
        return reducer.reduce(g);
    }

    public GeometryPrecisionReducer(PrecisionModel pm) {
        this.targetPM = pm;
    }

    public void setRemoveCollapsedComponents(boolean removeCollapsed2) {
        boolean z = removeCollapsed2;
        this.removeCollapsed = z;
    }

    public void setChangePrecisionModel(boolean changePrecisionModel2) {
        boolean z = changePrecisionModel2;
        this.changePrecisionModel = z;
    }

    public void setPointwise(boolean isPointwise2) {
        boolean z = isPointwise2;
        this.isPointwise = z;
    }

    public Geometry reduce(Geometry geom) {
        Geometry reducePW = reducePointwise(geom);
        if (this.isPointwise) {
            return reducePW;
        }
        if (!(reducePW instanceof Polygonal)) {
            return reducePW;
        }
        if (reducePW.isValid()) {
            return reducePW;
        }
        return fixPolygonalTopology(reducePW);
    }

    private Geometry reducePointwise(Geometry geometry) {
        GeometryEditor geometryEditor;
        GeometryEditor geomEdit;
        GeometryEditor.GeometryEditorOperation geometryEditorOperation;
        GeometryEditor geometryEditor2;
        Geometry geom = geometry;
        if (this.changePrecisionModel) {
            new GeometryEditor(createFactory(geom.getFactory(), this.targetPM));
            geomEdit = geometryEditor2;
        } else {
            new GeometryEditor();
            geomEdit = geometryEditor;
        }
        boolean finalRemoveCollapsed = this.removeCollapsed;
        if (geom.getDimension() >= 2) {
            finalRemoveCollapsed = true;
        }
        new PrecisionReducerCoordinateOperation(this.targetPM, finalRemoveCollapsed);
        return geomEdit.edit(geom, geometryEditorOperation);
    }

    private Geometry fixPolygonalTopology(Geometry geometry) {
        Geometry geom = geometry;
        Geometry geomToBuffer = geom;
        if (!this.changePrecisionModel) {
            geomToBuffer = changePM(geom, this.targetPM);
        }
        Geometry bufGeom = geomToBuffer.buffer(0.0d);
        Geometry finalGeom = bufGeom;
        if (!this.changePrecisionModel) {
            finalGeom = geom.getFactory().createGeometry(bufGeom);
        }
        return finalGeom;
    }

    private Geometry changePM(Geometry geometry, PrecisionModel newPM) {
        GeometryEditor.GeometryEditorOperation geometryEditorOperation;
        Geometry geom = geometry;
        new GeometryEditor.NoOpGeometryOperation();
        return createEditor(geom.getFactory(), newPM).edit(geom, geometryEditorOperation);
    }

    private GeometryEditor createEditor(GeometryFactory geometryFactory, PrecisionModel precisionModel) {
        GeometryEditor geomEdit;
        GeometryEditor geometryEditor;
        GeometryFactory geomFactory = geometryFactory;
        PrecisionModel newPM = precisionModel;
        if (geomFactory.getPrecisionModel() == newPM) {
            new GeometryEditor();
            return geometryEditor;
        }
        new GeometryEditor(createFactory(geomFactory, newPM));
        return geomEdit;
    }

    private GeometryFactory createFactory(GeometryFactory geometryFactory, PrecisionModel pm) {
        GeometryFactory newFactory;
        GeometryFactory inputFactory = geometryFactory;
        new GeometryFactory(pm, inputFactory.getSRID(), inputFactory.getCoordinateSequenceFactory());
        return newFactory;
    }
}
