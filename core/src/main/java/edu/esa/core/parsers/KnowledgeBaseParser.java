package edu.esa.core.parsers;

import edu.esa.core.parsers.exceptions.KnowledgeBaseParseException;
import edu.esa.core.structure.GraphStructureBuilder;

public interface KnowledgeBaseParser {

    void build(GraphStructureBuilder builder, KnowledgeBaseSource source)
            throws KnowledgeBaseParseException;
}
