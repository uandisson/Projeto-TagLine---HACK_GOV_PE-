package org.locationtech.jts.geom.util;

import org.locationtech.jts.algorithm.Angle;
import org.locationtech.jts.geom.Coordinate;

public class AffineTransformationFactory {
    public AffineTransformationFactory() {
    }

    public static AffineTransformation createFromControlVectors(Coordinate src0, Coordinate src1, Coordinate src2, Coordinate dest0, Coordinate dest1, Coordinate dest2) {
        AffineTransformationBuilder builder;
        new AffineTransformationBuilder(src0, src1, src2, dest0, dest1, dest2);
        return builder.getTransformation();
    }

    public static AffineTransformation createFromControlVectors(Coordinate coordinate, Coordinate coordinate2, Coordinate coordinate3, Coordinate coordinate4) {
        Coordinate rotPt;
        Coordinate src0 = coordinate;
        Coordinate src1 = coordinate2;
        Coordinate dest0 = coordinate3;
        Coordinate dest1 = coordinate4;
        new Coordinate(dest1.f412x - dest0.f412x, dest1.f413y - dest0.f413y);
        double ang = Angle.angleBetweenOriented(src1, src0, rotPt);
        double srcDist = src1.distance(src0);
        double destDist = dest1.distance(dest0);
        if (srcDist == 0.0d) {
            return null;
        }
        double scale = destDist / srcDist;
        AffineTransformation trans = AffineTransformation.translationInstance(-src0.f412x, -src0.f413y);
        AffineTransformation rotate = trans.rotate(ang);
        AffineTransformation scale2 = trans.scale(scale, scale);
        AffineTransformation translate = trans.translate(dest0.f412x, dest0.f413y);
        return trans;
    }

    public static AffineTransformation createFromControlVectors(Coordinate coordinate, Coordinate coordinate2) {
        Coordinate src0 = coordinate;
        Coordinate dest0 = coordinate2;
        return AffineTransformation.translationInstance(dest0.f412x - src0.f412x, dest0.f413y - src0.f413y);
    }

    public static AffineTransformation createFromControlVectors(Coordinate[] coordinateArr, Coordinate[] coordinateArr2) {
        Throwable th;
        Throwable th2;
        Throwable th3;
        Coordinate[] src = coordinateArr;
        Coordinate[] dest = coordinateArr2;
        if (src.length != dest.length) {
            Throwable th4 = th3;
            new IllegalArgumentException("Src and Dest arrays are not the same length");
            throw th4;
        } else if (src.length <= 0) {
            Throwable th5 = th2;
            new IllegalArgumentException("Too few control points");
            throw th5;
        } else if (src.length > 3) {
            Throwable th6 = th;
            new IllegalArgumentException("Too many control points");
            throw th6;
        } else if (src.length == 1) {
            return createFromControlVectors(src[0], dest[0]);
        } else {
            if (src.length == 2) {
                return createFromControlVectors(src[0], src[1], dest[0], dest[1]);
            }
            return createFromControlVectors(src[0], src[1], src[2], dest[0], dest[1], dest[2]);
        }
    }

    public static AffineTransformation createFromBaseLines(Coordinate coordinate, Coordinate coordinate2, Coordinate coordinate3, Coordinate coordinate4) {
        Coordinate rotPt;
        AffineTransformation affineTransformation;
        Coordinate src0 = coordinate;
        Coordinate src1 = coordinate2;
        Coordinate dest0 = coordinate3;
        Coordinate dest1 = coordinate4;
        new Coordinate((src0.f412x + dest1.f412x) - dest0.f412x, (src0.f413y + dest1.f413y) - dest0.f413y);
        double ang = Angle.angleBetweenOriented(src1, src0, rotPt);
        double srcDist = src1.distance(src0);
        double destDist = dest1.distance(dest0);
        if (srcDist == 0.0d) {
            new AffineTransformation();
            return affineTransformation;
        }
        double scale = destDist / srcDist;
        AffineTransformation trans = AffineTransformation.translationInstance(-src0.f412x, -src0.f413y);
        AffineTransformation rotate = trans.rotate(ang);
        AffineTransformation scale2 = trans.scale(scale, scale);
        AffineTransformation translate = trans.translate(dest0.f412x, dest0.f413y);
        return trans;
    }
}
