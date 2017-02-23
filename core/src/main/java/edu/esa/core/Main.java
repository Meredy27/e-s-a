package edu.esa.core;

import edu.esa.core.engine.ErrorsFinder;
import edu.esa.core.engine.SimpleCyclesFinder;
import edu.esa.core.errors.ErrorsData;
import edu.esa.core.structure.GraphStructureBuilder;
import edu.esa.core.structure.GraphStructureBuilderEngineWrapper;
import edu.esa.core.structure.GraphStructureBuilderImpl;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

public class Main {
    public static void main(String[] args) {
        ErrorsFinder errorsFinder = new SimpleCyclesFinder();

        prepareBuilder(errorsFinder)
                .addVertices("v1", "v2", "v3", "v4", "v5", "v6", "v7")
                .addRule("r1", "v3", "v1", "v2")
                .addRule("r2", "v4", "v2", "v1")
                .addRule("r3", "v6", "v5", "v4")
                .addRule("r4", "v7", "v1", "v5", "v4");

        Collection<String> simpleCycles = findSimpleCycles(errorsFinder);
    }

    private static GraphStructureBuilder prepareBuilder(ErrorsFinder errorsFinder){
        Collection<ErrorsFinder> finders = Collections.singletonList(errorsFinder);

        GraphStructureBuilder builder = new GraphStructureBuilderImpl();
        return new GraphStructureBuilderEngineWrapper(builder, finders);
    }

    private static Collection<String> findSimpleCycles(ErrorsFinder errorsFinder){
        ErrorsData errorsData = new ErrorsData();
        errorsFinder.fillErrorsData(errorsData);
        return errorsData.getSimpleCycles();
    }
}
