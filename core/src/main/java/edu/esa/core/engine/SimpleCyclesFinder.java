package edu.esa.core.engine;

import edu.esa.core.errors.ErrorsData;
import edu.esa.core.structure.GraphStructureBuilder;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Collection;

public class SimpleCyclesFinder implements ErrorsFinder {
    private GraphStructureBuilder builder;
    private Collection<String> simpleCycles = new ArrayList<>();

    @Override
    public void setGraphStructureBuilder(GraphStructureBuilder builder) {
        this.builder = builder;
    }

    @Override
    public void checkAddRule(String ruleId, String vertexTo, String... verticesFrom) {
        for (String vertexFrom : verticesFrom) {
            if(vertexTo.equals(vertexFrom)) {
                simpleCycles.add(ruleId);
            }
        }
    }

    @Override
    public void fillErrorsData(ErrorsData errorsData) {
        errorsData.setSimpleCycles(simpleCycles);
    }
}
