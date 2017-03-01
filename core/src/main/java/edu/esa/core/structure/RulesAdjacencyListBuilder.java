package edu.esa.core.structure;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;

import java.util.*;

public class RulesAdjacencyListBuilder {

    public static RulesAdjacencyList buildRulesAdjacencyList(GraphStructure graph) {
        List<String> rules = new ArrayList<>(graph.incomeVertices().keySet());

        Map<String, Set<String>> succeedingRules = buildSucceedingRules(graph, rules);

        return new RulesAdjacencyList(succeedingRules, rules);
    }

    private static Map<String, Set<String>> buildSucceedingRules(GraphStructure graph, List<String> rules){
        Map<String, Set<String>> succeedingRules = new HashMap<>();

        for(String rule : rules) {
            succeedingRules.put(rule, new HashSet<>(graph.outcomeRules().get(
                    graph.outcomeVertices().get(rule))));
        }

        return succeedingRules;
    }

    public static RulesAdjacencyList buildRulesAdjacencyList(RulesAdjacencyList source, int startRuleId) {
        List<String> rules = new ArrayList<>(source.size());

        Set<String> uniqueRules = new HashSet<>();

        for(int i = 0; i<source.size(); i++) {
            rules.add(source.getRules().get(i));
            if(i >= startRuleId) {
                uniqueRules.add(rules.get(i));
            }
        }

        Map<String, Set<String>> successors = new HashMap<>();

        for(String rule : rules) {
            if (uniqueRules.contains(rule)) {
                Set<String> curSuccessors = new HashSet<>();
                for(String sr : source.getSucceedingForRule(rule)) {
                    if(uniqueRules.contains(sr)) {
                        curSuccessors.add(sr);
                    }
                }
                successors.put(rule, curSuccessors);
            } else {
                successors.put(rule, Collections.emptySet());
            }
        }

        return new RulesAdjacencyList(successors, rules);
    }

    public static RulesAdjacencyList buildRulesAdjacencyList(RulesAdjacencyList source, List<Integer> nodes) {
        List<String> rules = new ArrayList<>(source.getRules());

        Map<String, Set<String>> successors = new HashMap<>();

        for(String rule : rules) {
            if (nodes.contains(source.getIndex(rule))) {
                Set<String> curSuccessors = new HashSet<>();
                for(String sr : source.getSucceedingForRule(rule)) {
                    if(nodes.contains(source.getIndex(sr))) {
                        curSuccessors.add(sr);
                    }
                }
                successors.put(rule, curSuccessors);
            } else {
                successors.put(rule, Collections.emptySet());
            }
        }

        return new RulesAdjacencyList(successors, rules);
    }
}
