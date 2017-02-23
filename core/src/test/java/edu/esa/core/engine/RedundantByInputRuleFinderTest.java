package edu.esa.core.engine;

import edu.esa.core.structure.GraphStructure;
import edu.esa.core.structure.GraphStructureBuilderImpl;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import static org.junit.Assert.assertTrue;

public class RedundantByInputRuleFinderTest {
    private static RedundantByInputRuleFinder engine = new RedundantByInputRuleFinder();

    @Test
    public void testEmptyGoals() {
        GraphStructure testStructure = new GraphStructureBuilderImpl()
                .addVertices("v1", "v2", "v3", "v4", "v5")
                .addRule("r1", "v3", "v1", "v2")
                .addRule("r2", "v5", "v3", "v4")
                .build();

        Collection<String> actual = engine.find(testStructure, Collections.EMPTY_LIST);

        assertTrue("No redundant rules should be found", actual.isEmpty());
    }

    @Test
    public void testFindRedundantRulesExist() {
        GraphStructure testStructure = new GraphStructureBuilderImpl()
                .addVertices("v1", "v2", "v3", "v4", "v5", "v6", "v7", "v8")
                .addRule("r1", "v3", "v1", "v2")
                .addRule("r2", "v5", "v3", "v4")
                .addRule("r3", "v7", "v5")
                .addRule("r4", "v6", "v1", "v4")
                .addRule("r5", "v7", "v1", "v6")
                .build();

        Collection<String> expected = Arrays.asList("r3", "r5");
        Collection<String> actual = engine.find(testStructure, Arrays.asList("v5", "v6"));

        assertTrue("There should be 2 redundant rules: "+actual, actual.size() == expected.size());
        assertTrue("Wrong rules have been selected: "+actual, actual.containsAll(expected));
    }

    @Test
    public void testFindRedundantRulesAbsent() {
        GraphStructure testStructure = new GraphStructureBuilderImpl()
                .addVertices("v1", "v2", "v3", "v4", "v5", "v6")
                .addRule("r1", "v3", "v1", "v2")
                .addRule("r2", "v5", "v3", "v4")
                .addRule("r3", "v6", "v1", "v4")
                .build();

        Collection<String> actual = engine.find(testStructure, Arrays.asList("v5", "v6"));

        assertTrue("No redundant should be found", actual.isEmpty());
    }
}
