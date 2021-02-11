package org.locationtech.jts.simplify;

import java.util.Collection;

class TaggedLinesSimplifier {
    private double distanceTolerance = 0.0d;
    private LineSegmentIndex inputIndex;
    private LineSegmentIndex outputIndex;

    public TaggedLinesSimplifier() {
        LineSegmentIndex lineSegmentIndex;
        LineSegmentIndex lineSegmentIndex2;
        new LineSegmentIndex();
        this.inputIndex = lineSegmentIndex;
        new LineSegmentIndex();
        this.outputIndex = lineSegmentIndex2;
    }

    public void setDistanceTolerance(double distanceTolerance2) {
        double d = distanceTolerance2;
        this.distanceTolerance = d;
    }

    public void simplify(Collection collection) {
        TaggedLineStringSimplifier taggedLineStringSimplifier;
        Collection<TaggedLineString> taggedLines = collection;
        for (TaggedLineString add : taggedLines) {
            this.inputIndex.add(add);
        }
        for (TaggedLineString simplify : taggedLines) {
            new TaggedLineStringSimplifier(this.inputIndex, this.outputIndex);
            TaggedLineStringSimplifier tlss = taggedLineStringSimplifier;
            tlss.setDistanceTolerance(this.distanceTolerance);
            tlss.simplify(simplify);
        }
    }
}
