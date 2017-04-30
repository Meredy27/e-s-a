package edu.esa.core.parsers.factory.impl;

import edu.esa.core.parsers.KnowledgeBaseParser;
import edu.esa.core.parsers.KnowledgeBaseSource;
import edu.esa.core.parsers.factory.ParsingFactory;
import edu.esa.core.parsers.impl.XmlParser;
import edu.esa.core.parsers.impl.XmlSource;

public class XmlParsingFactory implements ParsingFactory{
    @Override
    public KnowledgeBaseSource buildKnowledgeBaseSourse(String source) {
        return new XmlSource(source);
    }

    @Override
    public KnowledgeBaseParser buildKnowledgeBaseParser() {
        return new XmlParser();
    }
}
