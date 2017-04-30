package edu.esa.core.parsers.impl;

import edu.esa.core.structure.GraphStructureBuilder;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.Collection;

public class XmlSaxHandler extends DefaultHandler{
    private GraphStructureBuilder builder;

    private Collection<String> goals = new ArrayList<>();
    private String ruleId;
    private Collection<String> inputFacts = new ArrayList<>();
    private String outputFact;
    private boolean isInput = false;
    private boolean isOutput = false;

    public XmlSaxHandler(GraphStructureBuilder builder) {
        this.builder = builder;
    }

    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes atts) throws SAXException {
        switch(qName) {
            case "fact":
                builder.addVertex(atts.getValue("id"));
                if("true".equals(atts.getValue("isGoal"))) {
                    goals.add(atts.getValue("id"));
                }
                break;
            case "rule":
                ruleId = atts.getValue("id");
                inputFacts = new ArrayList<>();
                outputFact = null;
                break;
            case "input":
                isInput = true;
                break;
            case "output":
                isOutput = true;
                break;
        }
    }

    @Override
    public void endElement(String namespaceURI, String localName, String qName) throws SAXException {
        switch(qName) {
            case "rule":
                builder.addRule(ruleId, outputFact, inputFacts.toArray(new String[0]));
                ruleId = null;
                break;
            case "input":
                isInput = false;
                break;
            case "output":
                isOutput = false;
                break;
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        if(isInput) {
            inputFacts.add(new String(ch, start, length));
        } else if (isOutput) {
            outputFact = new String(ch, start, length);
        }
    }

        @Override
    public void endDocument() {
        builder.setGoals(goals);
    }
}
