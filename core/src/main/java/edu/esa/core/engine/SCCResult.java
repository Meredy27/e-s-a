package edu.esa.core.engine;

import edu.esa.core.structure.RulesAdjacencyList;

import java.util.*;

public class SCCResult {
    private List<String> selectedRules;
    private RulesAdjacencyList adjList;
    private int minRuleIndex = -1;

    public SCCResult(RulesAdjacencyList adjList, int minRuleIndex) {
        this.adjList = adjList;
        this.minRuleIndex = minRuleIndex;
        this.selectedRules = new ArrayList<>();
        if (this.adjList != null) {
            for (int i = minRuleIndex; i < this.adjList.size(); i++) {
                String curRuleId = adjList.getRuleByIndex(i);
                if (!adjList.getSucceedingForRule(curRuleId).isEmpty()) {
                    selectedRules.add(curRuleId);
                }
            }
        }
    }

    public List<String> getSelectedRules() {
        return selectedRules;
    }

    public int getMinRuleIndex() {
        return minRuleIndex;
    }

    public RulesAdjacencyList getAdjList() {
        return adjList;
    }
}
