package edu.esa.core.structure;

import java.util.*;

public class GraphStructure {
    private Map<String, List<String>> incomeVertices;
    private Map<String, String> outcomeVertices;
    private Map<String, List<String>> incomeRules;
    private Map<String, List<String>> outcomeRules;

    private Collection<String> inputVertices = Collections.EMPTY_LIST;

    public Collection<String> getGoals() {
        return goals;
    }

    private Collection<String> goals = Collections.EMPTY_LIST;

    GraphStructure(Map<String, List<String>> incomeVertices, Map<String, String> outcomeVertices,
                          Map<String, List<String>> incomeRules, Map<String, List<String>> outcomeRules) {
        this.incomeVertices = Collections.unmodifiableMap(incomeVertices);
        this.outcomeVertices = Collections.unmodifiableMap(outcomeVertices);
        this.incomeRules = Collections.unmodifiableMap(incomeRules);
        this.outcomeRules = Collections.unmodifiableMap(outcomeRules);
    }

    GraphStructure(Map<String, List<String>> incomeVertices, Map<String, String> outcomeVertices,
                   Map<String, List<String>> incomeRules, Map<String, List<String>> outcomeRules,
                   Collection<String> goals) {
        this.incomeVertices = Collections.unmodifiableMap(incomeVertices);
        this.outcomeVertices = Collections.unmodifiableMap(outcomeVertices);
        this.incomeRules = Collections.unmodifiableMap(incomeRules);
        this.outcomeRules = Collections.unmodifiableMap(outcomeRules);
        this.inputVertices = Collections.unmodifiableCollection(inputVertices);
        this.goals = Collections.unmodifiableCollection(goals);
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

    public boolean vertexExist(String vertex) {
        return incomeRules.containsKey(vertex) || outcomeRules.containsKey(vertex);
    }

    public boolean ruleExist(String rule) {
        return incomeVertices.containsKey(rule) || outcomeVertices.containsKey(rule);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n income vertices: ");
        sb.append(incomeVertices);
        sb.append("\n outcome vertices: ");
        sb.append(outcomeVertices);
        sb.append("\n income rules: ");
        sb.append(incomeRules);
        sb.append("\n outcome rules: ");
        sb.append(outcomeRules);
        return sb.toString();
    }
}
