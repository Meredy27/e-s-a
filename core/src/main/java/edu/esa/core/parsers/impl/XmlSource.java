package edu.esa.core.parsers.impl;

import edu.esa.core.parsers.KnowledgeBaseSource;

public class XmlSource implements KnowledgeBaseSource{
    private String fileName;

    public XmlSource(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }
}
