package edu.esa.core.engine;

import edu.esa.core.errors.ErrorsData;
import edu.esa.core.structure.GraphStructureBuilder;
import edu.esa.core.structure.GraphStructureBuilderEngineWrapper;
import edu.esa.core.structure.GraphStructureBuilderImpl;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import static org.junit.Assert.assertTrue;

public class SimpleCyclesTest {
    @Test
    public void testEmptyStructure(){
        ErrorsFinder errorsFinder = new SimpleCyclesFinder();

        prepareBuilder(errorsFinder);

        Collection<String> simpleCycles = findSimpleCycles(errorsFinder);

        assertTrue("There should be no simple cycles found", simpleCycles.isEmpty());
    }

    @Test
    public void testSimpleCyclesExist(){
        ErrorsFinder errorsFinder = new SimpleCyclesFinder();

        prepareBuilder(errorsFinder)
                .addVertices("v1", "v2", "v3", "v4", "v5", "v6", "v7")
                .addRule("r1", "v3", "v1", "v2")
                .addRule("r2", "v4", "v4", "v3")
                .addRule("r3", "v5", "v3", "v4")
                .addRule("r4", "v6", "v1", "v6")
                .addRule("r5", "v7", "v6", "v5", "v7");

        Collection<String> expected = Arrays.asList("r2", "r4", "r5");
        Collection<String> actual = findSimpleCycles(errorsFinder);

        assertTrue("There should be 3 simple cycles found, actual: " + actual, actual.size() == 3);
        assertTrue("Wrong rules have been selected, actual: " + actual, actual.containsAll(expected));
    }

    @Test
    public void testSimpleCyclesAbsent(){
        ErrorsFinder errorsFinder = new SimpleCyclesFinder();

        prepareBuilder(errorsFinder)
                .addVertices("v1", "v2", "v3", "v4", "v5", "v6", "v7")
                .addRule("r1", "v3", "v1", "v2")
                .addRule("r2", "v4", "v2", "v1")
                .addRule("r3", "v6", "v5", "v4")
                .addRule("r4", "v7", "v1", "v5", "v4");

        Collection<String> simpleCycles = findSimpleCycles(errorsFinder);

        assertTrue("There should be no simple cycles found", simpleCycles.isEmpty());
    }

    private GraphStructureBuilder prepareBuilder(ErrorsFinder errorsFinder){
        Collection<ErrorsFinder> finders = Collections.singletonList(errorsFinder);

        GraphStructureBuilder builder = new GraphStructureBuilderImpl();
        return new GraphStructureBuilderEngineWrapper(builder, finders);
    }

    private Collection<String> findSimpleCycles(ErrorsFinder errorsFinder){
        ErrorsData errorsData = new ErrorsData();
        errorsFinder.fillErrorsData(errorsData);
        return errorsData.getSimpleCycles();
    }
}
