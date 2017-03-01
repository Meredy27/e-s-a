package edu.esa.core.engine;

import edu.esa.core.structure.GraphStructure;
import edu.esa.core.structure.GraphStructureBuilderImpl;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.Assert.assertTrue;

public class CyclesTest {
    private static CyclesFinder engine = new CyclesFinder();
    private static TestTool testTool = new TestTool();

    @Test
    public void testEmptyStructure() {
        GraphStructure testStructure = new GraphStructureBuilderImpl().build();

        Collection<List<String>> actual = engine.find(testStructure);

        assertTrue("No cycles should be found", actual.isEmpty());
    }

    @Test
    public void testFindCyclesExist() {
        GraphStructure testStructure = new GraphStructureBuilderImpl()
                .addVertices("v1", "v2", "v3", "v4", "v5", "v6", "v7", "v8", "v9", "v10")
                .addRule("r1", "v3", "v1", "v2")
                .addRule("r2", "v5", "v3", "v4")
                .addRule("r3", "v6", "v5", "v7")
                .addRule("r4", "v3", "v6", "v8")
                .addRule("r5", "v9", "v4", "v8")
                .addRule("r6", "v10", "v7", "v9")
                .addRule("r7", "v4", "v2", "v10")
                .addRule("r8", "v1", "v2", "v3")
                .build();

        List<String> chain1 = new ArrayList<>(3);
        chain1.add("r2");
        chain1.add("r3");
        chain1.add("r4");

        List<String> chain2 = new ArrayList<>(5);
        chain2.add("r2");
        chain2.add("r3");
        chain2.add("r4");
        chain2.add("r8");
        chain2.add("r1");

        List<String> chain3 = new ArrayList<>(3);
        chain3.add("r5");
        chain3.add("r6");
        chain3.add("r7");

        List<String> chain4 = new ArrayList<>(2);
        chain4.add("r1");
        chain4.add("r8");

        Collection<List<String>> actual = engine.find(testStructure);

        assertTrue("There should be 3 chains: " + actual, actual.size() == 4);
        assertTrue("Chain 1 should be present, actual: " + actual, testTool.hasChainForCycle(actual, chain1));
        assertTrue("Chain 2 should be present, actual: " + actual, testTool.hasChainForCycle(actual, chain2));
        assertTrue("Chain 3 should be present, actual: " + actual, testTool.hasChainForCycle(actual, chain3));
        assertTrue("Chain 4 should be present, actual: " + actual, testTool.hasChainForCycle(actual, chain4));
    }

    @Test
    public void testCyclesAbsent() {
        GraphStructure testStructure = new GraphStructureBuilderImpl()
                .addVertices("v1", "v2", "v3", "v4", "v5", "v6", "v7", "v8", "v9", "v10")
                .addRule("r1", "v3", "v1", "v2")
                .addRule("r2", "v5", "v3", "v4")
                .addRule("r3", "v8", "v6", "v7")
                .addRule("r4", "v10", "v5", "v8", "v9")
                .build();

        Collection<List<String>> actual = engine.find(testStructure);

        assertTrue("No cycles should be found", actual.isEmpty());
    }
}
