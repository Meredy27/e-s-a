package edu.esa.core.parsers.factory;

import edu.esa.core.parsers.KnowledgeBaseParser;
import edu.esa.core.parsers.KnowledgeBaseSource;

public interface ParsingFactory {

    KnowledgeBaseSource buildKnowledgeBaseSourse(String source);

    KnowledgeBaseParser buildKnowledgeBaseParser();
}
