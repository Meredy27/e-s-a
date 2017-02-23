package edu.esa.core.engine;

import edu.esa.core.structure.GraphStructure;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Collection;

public class RedundantByInputRuleFinder {
    public Collection<String> find(@NotNull GraphStructure structure, @NotNull Collection<String> goals) {
        Collection<String> redundantRules = new ArrayList<>();
        for(String goal : goals) {
            redundantRules.addAll(structure.outcomeRules().get(goal));
        }
        return redundantRules;
    }
}
