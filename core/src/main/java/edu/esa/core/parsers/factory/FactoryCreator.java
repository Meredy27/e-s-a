package edu.esa.core.parsers.factory;

import edu.esa.core.parsers.SourceType;
import edu.esa.core.parsers.exceptions.InvalidSourceTypeException;
import edu.esa.core.parsers.factory.impl.XmlParsingFactory;

public class FactoryCreator {
    private static XmlParsingFactory xmlParsingFactory = new XmlParsingFactory();

    public static ParsingFactory getParsingFactory(SourceType type){
        if(SourceType.XML.equals(type)) {
            return xmlParsingFactory;
        }

        throw new InvalidSourceTypeException("Unknown source type: " + type);
    }
}
