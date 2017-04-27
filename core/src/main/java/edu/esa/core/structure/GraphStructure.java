package edu.esa.core.structure;

import java.util.*;

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

    public boolean ruleExist(String vertexTo, Collection<String> verticesFrom) {
        Collection<String> rules = incomeRules.get(vertexTo);

        if(rules.isEmpty()) {
             return false;
        }

        for(String rule : rules) {
            Collection<String> ruleVerticesFrom = incomeVertices.get(rule);
            if(ruleVerticesFrom.containsAll(verticesFrom)) {
                return true;
            }
        }
        return false;
    }

    public Collection<String> getRules(String vertexTo, Collection<String> verticesFrom) {
        Collection<String> rules = incomeRules.get(vertexTo);

        if(rules.isEmpty()) {
            return Collections.EMPTY_LIST;
        }

        Collection<String> result = Collections.EMPTY_LIST;
        for(String rule : rules) {
            Collection<String> ruleVerticesFrom = incomeVertices.get(rule);
            if(ruleVerticesFrom.containsAll(verticesFrom)) {
                if(result.isEmpty()) {
                    result = new ArrayList<>();
                }
                result.add(rule);
            }
        }
        return result;
    }
}
