package edu.esa.core.structure;

import javax.validation.constraints.NotNull;
import java.util.*;

public class GraphStructureBuilderImpl implements GraphStructureBuilder {
    private Map<String, List<String>> incomeVertices;
    private Map<String, String> outcomeVertices;
    private Map<String, List<String>> incomeRules;
    private Map<String, List<String>> outcomeRules;

    private Collection<String> goals = Collections.EMPTY_LIST;

    public GraphStructureBuilderImpl() {
        incomeVertices = new HashMap<>();
        outcomeVertices = new HashMap<>();
        incomeRules = new HashMap<>();
        outcomeRules = new HashMap<>();
    }

    @Override
    public GraphStructureBuilderImpl addVertex(@NotNull String vertexId) {
        addVertexInternal(vertexId);
        return this;
    }

    @Override
    public GraphStructureBuilderImpl addVertices(@NotNull String... verticesIds) {
        for(String vertexId : verticesIds) {
            addVertexInternal(vertexId);
        }
        return this;
    }

    private void addVertexInternal(String vertexId){
        if(incomeRules.get(vertexId) == null) {
            incomeRules.put(vertexId, Collections.emptyList());
            outcomeRules.put(vertexId, Collections.emptyList());
        }
    }

    @Override
    public GraphStructureBuilderImpl addRule(@NotNull String ruleId,
                                             @NotNull String vertexTo, @NotNull String... verticesFrom){
        if(incomeVertices.get(ruleId) == null) {
            if (! vertexExist(vertexTo))
                throw new IllegalStateException("Couldn't find a vertex "+vertexTo);
            addOutcomeVertex(ruleId, vertexTo);
            addIncomeRule(vertexTo, ruleId);

            for (String vertexFrom : verticesFrom) {
                if (! vertexExist(vertexTo))
                    throw new IllegalStateException("Couldn't find a vertex "+vertexFrom);
                addOutcomeRule(vertexFrom, ruleId);
                addIncomeVertex(ruleId, vertexFrom);
            }
        }

        return this;
    }

    @Override
    public boolean vertexExist(String vertexId) {
        return incomeRules.get(vertexId) != null || outcomeVertices.get(vertexId) != null;
    }

    @Override
    public boolean ruleExist(String vertexFrom, String vertexTo) {
        List<String> rules = outcomeRules.get(vertexFrom);
        if(rules == null || rules.isEmpty()) {
            return false;
        }

        for(String rule : rules) {
            if(vertexTo.equals(outcomeVertices.get(rule))) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void setGoals(Collection<String> goals) {
        this.goals = goals;
    }

    @Override
    public Collection<String> findRules(String vertexFrom, String vertexTo) {
        List<String> rules = outcomeRules.get(vertexFrom);
        if(rules == null || rules.isEmpty()) {
            return Collections.emptyList();
        }

        Collection<String> appropriateRules = new ArrayList<>();
        for(String rule : rules) {
            if(vertexTo.equals(outcomeVertices.get(rule))) {
                appropriateRules.add(rule);
            }
        }
        return appropriateRules;
    }

    @Override
    public Collection<String> getVerticesFrom(String ruleId) {
        return incomeVertices.get(ruleId);
    }

    @Override
    public String getVertexTo(String ruleId) {
        return outcomeVertices.get(ruleId);
    }

    private void addIncomeVertex(String ruleId, String vertexFrom){
        incomeVertices.computeIfAbsent(ruleId, k -> new ArrayList<>());
        incomeVertices.get(ruleId).add(vertexFrom);
    }

    private void addOutcomeVertex(String ruleId, String vertexTo) {
        outcomeVertices.put(ruleId, vertexTo);
    }

    private void addIncomeRule(String vertexId, String ruleId) {
        if(incomeRules.get(vertexId).isEmpty()) {
            incomeRules.put(vertexId, new ArrayList<>());
        }
        incomeRules.get(vertexId).add(ruleId);
    }

    private void addOutcomeRule(String vertexId, String ruleId) {
        if(outcomeRules.get(vertexId).isEmpty()) {
            outcomeRules.put(vertexId, new ArrayList<>());
        }
        outcomeRules.get(vertexId).add(ruleId);
    }

    @Override
    public GraphStructure build(){
        if(goals.isEmpty()) {
            return new GraphStructure(incomeVertices, outcomeVertices, incomeRules, outcomeRules);
        } else {
            return new GraphStructure(incomeVertices, outcomeVertices, incomeRules, outcomeRules,
                    goals);
        }
    }
}
