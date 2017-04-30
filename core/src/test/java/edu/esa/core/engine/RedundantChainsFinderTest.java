package edu.esa.core.engine;

import edu.esa.core.GraphData;
import edu.esa.core.structure.GraphStructure;
import edu.esa.core.structure.GraphStructureBuilder;
import edu.esa.core.structure.GraphStructureBuilderImpl;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertTrue;

public class RedundantChainsFinderTest {
    private static RedundantChainsFinder engine = new RedundantChainsFinder();

    @Test
    public void testEmptyGoals() {

        GraphStructure testStructure = new GraphStructureBuilderImpl()
                .addVertices("v1", "v2", "v3", "v4", "v5")
                .addRule("r1", "v3", "v1", "v2")
                .addRule("r2", "v5", "v3", "v4")
                .build();

        GraphData graphData = new GraphData(testStructure, Collections.EMPTY_LIST, Collections.EMPTY_LIST);

        Collection<List<String>> actual = engine.find(testStructure);

        assertTrue("No redundant chains should be found", actual.isEmpty());
    }

    @Test
    public void testFindRedundantChainsExist() {
        GraphStructureBuilder builder = new GraphStructureBuilderImpl()
                .addVertices("v1", "v2", "v3", "v4", "v5", "v6", "v7", "v8", "v9", "v10")
                .addRule("r1", "v3", "v1", "v2")
                .addRule("r2", "v5", "v3", "v4")
                .addRule("r3", "v8", "v5", "v6")
                .addRule("r4", "v7", "v5", "v6");

        builder.setGoals(Arrays.asList("v8", "v10"));
        GraphStructure testStructure = builder.build();

        Collection<List<String>> actual = engine.find(testStructure);

        Collection<String> expected = Arrays.asList("r1", "r2", "r4");

        assertTrue("There should be 1 redundant chains found, actual: " + actual, actual.size() == 1);
        assertTrue("Wrong chains found, actual: " + actual +"; expected: " + expected,
                containsCollection(actual, expected));
    }

    @Test
    public void testFindRedundantChainsAbsent() {
        GraphStructureBuilder builder = new GraphStructureBuilderImpl()
                .addVertices("v1", "v2", "v3", "v4", "v5", "v6", "v7", "v8")
                .addRule("r1", "v3", "v1", "v2")
                .addRule("r2", "v5", "v3", "v4")
                .addRule("r3", "v8", "v5", "v6")
                .addRule("r4", "v7", "v5", "v6");

        builder.setGoals(Arrays.asList("v7", "v8"));
        GraphStructure testStructure = builder.build();

        Collection<List<String>> actual = engine.find(testStructure);

        assertTrue("There should be no redundant chains found, actual: " + actual, actual.isEmpty());
    }


    private boolean containsCollection(Collection<List<String>> source, Collection<String> value) {
        for(List<String> sourceCollection : source) {
            if(sourceCollection.containsAll(value)) {
                return true;
            }
        }
        return false;
    }
}
