package edu.esa.core.engine;

import edu.esa.core.structure.GraphStructure;
import edu.esa.core.structure.GraphStructureBuilderImpl;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertTrue;

public class IsolatedVerticesTest {
    private static IsolatedVerticesFinder engine = new IsolatedVerticesFinder();

    @Test
    public void testEmptyStructure() {
        GraphStructure testStructure = new GraphStructureBuilderImpl().build();

        Collection<String> actual = engine.find(testStructure);

        assertTrue("No isolated vertices should be found", actual.isEmpty());
    }

    @Test
    public void testFindIsolatedVertexesExist() {
        GraphStructure testStructure = new GraphStructureBuilderImpl()
                .addVertices("v1", "v2", "v3", "v4", "v5", "v6", "v7")
                .addRule("r1", "v4", "v2", "v3")
                .addRule("r2", "v5", "v2")
                .addRule("r3", "v7", "v4", "v5")
                .build();

        Collection<String> expected = Arrays.asList("v1", "v6");
        Collection<String> actual = engine.find(testStructure);

        assertTrue("There should be 2 isolated vertices: "+actual, actual.size() == expected.size());
        assertTrue("Wrong vertices have been selected: "+actual, actual.containsAll(expected));
    }

    @Test
    public void testFindIsolatedVertexesAbsent() {
        GraphStructure testStructure = new GraphStructureBuilderImpl()
                .addVertices("v1", "v2", "v3", "v4", "v5")
                .addRule("r1", "v3", "v1", "v2")
                .addRule("r2", "v5", "v3", "v4")
                .build();

        Collection<String> actual = engine.find(testStructure);

        assertTrue("No isolated vertices should be found", actual.isEmpty());
    }
}
