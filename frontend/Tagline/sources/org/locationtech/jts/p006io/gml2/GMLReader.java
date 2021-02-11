package org.locationtech.jts.p006io.gml2;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryFactory;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/* renamed from: org.locationtech.jts.io.gml2.GMLReader */
public class GMLReader {
    public GMLReader() {
    }

    public Geometry read(String gml, GeometryFactory geometryFactory) throws SAXException, IOException, ParserConfigurationException {
        Reader reader;
        new StringReader(gml);
        return read(reader, geometryFactory);
    }

    public Geometry read(Reader reader, GeometryFactory geometryFactory) throws SAXException, IOException, ParserConfigurationException {
        GMLHandler gMLHandler;
        InputSource inputSource;
        GeometryFactory geometryFactory2;
        Reader reader2 = reader;
        GeometryFactory geometryFactory3 = geometryFactory;
        SAXParserFactory fact = SAXParserFactory.newInstance();
        fact.setNamespaceAware(false);
        fact.setValidating(false);
        SAXParser parser = fact.newSAXParser();
        if (geometryFactory3 == null) {
            new GeometryFactory();
            geometryFactory3 = geometryFactory2;
        }
        new GMLHandler(geometryFactory3, (ErrorHandler) null);
        GMLHandler gh = gMLHandler;
        new InputSource(reader2);
        parser.parse(inputSource, gh);
        return gh.getGeometry();
    }
}
