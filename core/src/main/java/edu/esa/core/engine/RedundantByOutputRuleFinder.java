package edu.esa.core.engine;

import edu.esa.core.structure.GraphStructure;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Collection;

public class RedundantByOutputRuleFinder {
    public Collection<String> find(@NotNull GraphStructure structure, @NotNull Collection<String> inputVertices) {
        Collection<String> redundantRules = new ArrayList<>();
        for(String inputVertex : inputVertices) {
            redundantRules.addAll(structure.incomeRules().get(inputVertex));
        }
        return redundantRules;
    }
}
