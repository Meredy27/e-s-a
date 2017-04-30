package edu.esa.core.parsers.impl;

import edu.esa.core.parsers.KnowledgeBaseParser;
import edu.esa.core.parsers.KnowledgeBaseSource;
import edu.esa.core.parsers.exceptions.InvalidSourceTypeException;
import edu.esa.core.parsers.exceptions.KnowledgeBaseParseException;
import edu.esa.core.structure.GraphStructureBuilder;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.IOException;

public class XmlParser implements KnowledgeBaseParser {
    @Override
    public void build(GraphStructureBuilder builder, KnowledgeBaseSource source)
            throws KnowledgeBaseParseException{
        if(! (source instanceof XmlSource)) {
            throw new InvalidSourceTypeException("Only xml sources are supported");
        }

        XmlSource xmlSource = (XmlSource) source;

        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser parser = factory.newSAXParser();
            XmlSaxHandler saxp = new XmlSaxHandler(builder);

            parser.parse(new File(xmlSource.getFileName()), saxp);
        } catch (IOException | SAXException | ParserConfigurationException e) {
            throw new KnowledgeBaseParseException("Cannot parse knowledge base: ", e);
        }
    }
}
