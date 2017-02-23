package edu.esa.core.structure;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class GraphStructure {
    private Map<String, List<String>> incomeVertices;
    private Map<String, String> outcomeVertices;
    private Map<String, List<String>> incomeRules;
    private Map<String, List<String>> outcomeRules;

    GraphStructure(Map<String, List<String>> incomeVertices, Map<String, String> outcomeVertices,
                          Map<String, List<String>> incomeRules, Map<String, List<String>> outcomeRules) {
        this.incomeVertices = Collections.unmodifiableMap(incomeVertices);
        this.outcomeVertices = Collections.unmodifiableMap(outcomeVertices);
        this.incomeRules = Collections.unmodifiableMap(incomeRules);
        this.outcomeRules = Collections.unmodifiableMap(outcomeRules);
    }

    public Map<String, List<String>> incomeVertices(){
        return incomeVertices;
    }

    public Map<String, String> outcomeVertices(){
        return outcomeVertices;
    }

    public Map<String, List<String>> incomeRules(){
        return incomeRules;
    }

    public Map<String, List<String>> outcomeRules(){
        return outcomeRules;
    }
}
