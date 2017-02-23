package edu.esa.core.engine;

import edu.esa.core.structure.GraphStructure;

import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.HashSet;

public class IsolatedVerticesFinder {
    public Collection<String> find(@NotNull GraphStructure structure) {
        Collection<String> isolatedVertexes = new HashSet<>();
        for(String vertexId : structure.incomeRules().keySet()) {
            if (structure.incomeRules().get(vertexId).isEmpty()
                    && structure.outcomeRules().get(vertexId).isEmpty()) {
                isolatedVertexes.add(vertexId);
            }
        }
        return isolatedVertexes;
    }
}
