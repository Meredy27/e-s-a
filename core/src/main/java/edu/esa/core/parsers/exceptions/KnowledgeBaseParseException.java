package edu.esa.core.parsers.exceptions;

public class KnowledgeBaseParseException extends Exception {
    public KnowledgeBaseParseException(String message) {
        super(message);
    }

    public KnowledgeBaseParseException(String message, Exception e) {
        super(message, e);
    }
}
