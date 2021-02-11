package org.locationtech.jts.awt;

import java.awt.Font;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.List;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryFactory;

public class FontGlyphReader {
    private static final double FLATNESS_FACTOR = 400.0d;
    public static final String FONT_MONOSPACED = "Monospaced";
    public static final String FONT_SANSERIF = "SansSerif";
    public static final String FONT_SANSSERIF = "SansSerif";
    public static final String FONT_SERIF = "Serif";

    private FontGlyphReader() {
    }

    public static Geometry read(String text, String fontName, int pointSize, GeometryFactory geomFact) {
        Font font;
        new Font(fontName, 0, pointSize);
        return read(text, font, geomFact);
    }

    public static Geometry read(String text, Font font, GeometryFactory geomFact) {
        Font font2 = font;
        return read(text, font2, ((double) font2.getSize()) / FLATNESS_FACTOR, geomFact);
    }

    public static Geometry read(String text, Font font, double d, GeometryFactory geometryFactory) {
        FontRenderContext fontContext;
        List list;
        double flatness = d;
        GeometryFactory geomFact = geometryFactory;
        char[] chs = text.toCharArray();
        new FontRenderContext((AffineTransform) null, false, true);
        GlyphVector gv = font.createGlyphVector(fontContext, chs);
        new ArrayList();
        List polys = list;
        for (int i = 0; i < gv.getNumGlyphs(); i++) {
            Geometry geom = ShapeReader.read(gv.getGlyphOutline(i), flatness, geomFact);
            for (int j = 0; j < geom.getNumGeometries(); j++) {
                boolean add = polys.add(geom.getGeometryN(j));
            }
        }
        return geomFact.buildGeometry(polys);
    }
}
