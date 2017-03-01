package edu.esa.core.engine;

import com.sun.org.apache.xpath.internal.operations.Bool;
import edu.esa.core.structure.GraphStructure;

import javax.validation.constraints.NotNull;
import java.util.*;

public class InsignificantChainsFinder {
    public Collection<List<String>> find(@NotNull GraphStructure structure) {
        Map<String, Boolean> checkedRules = findCheckedRules(structure);

        if(checkedRules.isEmpty()) {
            return Collections.EMPTY_LIST;
        }

        Collection<List<String>> insignificantChains = new HashSet<>();

        for(String rule : checkedRules.keySet()) {
           insignificantChains.add(buildChain(structure, rule));
        }

        return insignificantChains;
    }

    private Map<String, Boolean> findCheckedRules(GraphStructure structure) {
        Map<String, Boolean> checkedRules = new HashMap<>();
        for(Map.Entry<String, List<String>> entry : structure.incomeVertices().entrySet()) {
            if(entry.getValue().size() == 1) {
                checkedRules.put(entry.getKey(), Boolean.FALSE);
            }
        }
        return checkedRules;
    }

    private List<String> buildChain(GraphStructure structure, String ruleId) {
        List<String> chain = new ArrayList<>();

        String currentRule = ruleId;
        String currentVertex;
        Collection<String> nextRules;

        while(structure.incomeVertices().get(currentRule).size() == 1) {
            chain.add(currentRule);
            currentVertex = structure.outcomeVertices().get(currentRule);
            nextRules = structure.outcomeRules().get(currentVertex);
            if(nextRules.isEmpty()) {
                break;
            }
            currentRule = nextRules.iterator().next();
        }

        currentRule = ruleId;
        currentVertex = structure.incomeVertices().get(currentRule).get(0);
        nextRules = structure.incomeRules().get(currentVertex);
        if(nextRules.isEmpty()) {
            return chain;
        }
        currentRule = nextRules.iterator().next();

        while(structure.incomeVertices().get(currentRule).size() == 1) {
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
