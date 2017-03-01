package edu.esa.core.structure;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class RulesAdjacencyList {
    private Map<String, Set<String>> succeedingRules;
    private List<String> rules;
    private Map<String,Integer> rulesOrder;

    public RulesAdjacencyList(Map<String, Set<String>> succeedingRules, List<String> rules) {
        this.succeedingRules = succeedingRules;
        this.rules = rules;
        rulesOrder = new HashMap<>();

        for(int i=0; i< rules.size(); i++) {
            rulesOrder.put(rules.get(i), i);
        }
    }

    public Map<String, Set<String>> getSucceedingRules() {
        return succeedingRules;
    }

    public List<String> getRules() {
        return rules;
    }

    public Set<String> getSucceedingForRule(String rule) {
        return succeedingRules.get(rule);
    }

    public Set<String> getSucceedingForRule(int ruleIndex) {
        return succeedingRules.get(rules.get(ruleIndex));
    }

    public int getIndex(String rule) {
        return rulesOrder.get(rule);
    }

    public int size() {
        return rules.size();
    }

    public String getRuleByIndex(int index){
        return rules.get(index);
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder("Rules: ");
        sb.append(rules);
        sb.append("; succeeding rules: ");
        sb.append(succeedingRules);
        return sb.toString();
    }
}
