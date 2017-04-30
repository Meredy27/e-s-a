package edu.esa.core.engine;

import edu.esa.core.GraphData;
import edu.esa.core.structure.GraphStructure;

import java.util.*;

public class RedundantChainsFinder {

    public Collection<List<String>> find(GraphStructure structure) {
        if(structure.getGoals().isEmpty()) {
            return Collections.emptyList();
        }

        Collection<String> rulesToBuildChainsTo = new ArrayList<>();
        for(Map.Entry<String, List<String>> vertex : structure.outcomeRules().entrySet()) {
            if(vertex.getValue().isEmpty() && !structure.getGoals().contains(vertex.getKey())) {
                rulesToBuildChainsTo.addAll(structure.incomeRules().get(vertex.getKey()));
            }
        }

        Collection<List<String>> redundantChains = new ArrayList<>();
        for(String rule : rulesToBuildChainsTo) {
            redundantChains.add(buildChain(structure, rule));
        }
        return redundantChains;
    }

    private List<String> buildChain(GraphStructure structure, String ruleId) {
        List<String> chain = new LinkedList<>();

        String currentRule = ruleId;
        String currentVertex = structure.incomeVertices().get(currentRule).get(0);
        Collection<String> nextRules = structure.incomeRules().get(currentVertex);

        while(!structure.incomeRules().get(currentVertex).isEmpty()) {
            chain.add(0, currentRule);
            currentVertex = structure.incomeVertices().get(currentRule).get(0);
            nextRules = structure.incomeRules().get(currentVertex);
            if(nextRules.isEmpty()) {
                break;
            }
            currentRule = nextRules.iterator().next();
        }

        return chain;
    }
}
