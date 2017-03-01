package edu.esa.core.engine;

import edu.esa.core.structure.GraphStructure;
import edu.esa.core.structure.GraphStructureBuilderImpl;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.assertTrue;

public class InsignificantChainsTest {
    private static InsignificantChainsFinder engine = new InsignificantChainsFinder();
    private static TestTool testTool = new TestTool();

    @Test
    public void testEmptyStructure() {
        GraphStructure testStructure = new GraphStructureBuilderImpl().build();

        Collection<List<String>> actual = engine.find(testStructure);

        assertTrue("No insignificant chains should be found", actual.isEmpty());
    }

    @Test
    public void testFindInsignificantChainsExist() {
        GraphStructure testStructure = new GraphStructureBuilderImpl()
                .addVertices("v1", "v2", "v3", "v4", "v5", "v6", "v7", "v8", "v9", "v10")
                .addRule("r1", "v3", "v1", "v2")
                .addRule("r2", "v5", "v4")
                .addRule("r3", "v6", "v5")
                .addRule("r4", "v4", "v3")
                .addRule("r5", "v7", "v5")
                .addRule("r6", "v8", "v1", "v2")
                .addRule("r7", "v10", "v3")
                .addRule("r8", "v10", "v9", "v7")
                .build();

        List<String> chain1 = new ArrayList<>(3);
        chain1.add("r4");
        chain1.add("r2");
        chain1.add("r3");

        List<String> chain2 = new ArrayList<>(3);
        chain2.add("r4");
        chain2.add("r2");
        chain2.add("r5");

        List<String> chain3 = new ArrayList<>(1);
        chain3.add("r7");

        Collection<List<String>> actual = engine.find(testStructure);

        assertTrue("There should be 3 chains: " + actual, actual.size() == 3);
        assertTrue("Chain 1 should be present, actual: " + actual, testTool.hasChain(actual, chain1));
        assertTrue("Chain 2 should be present, actual: " + actual, testTool.hasChain(actual, chain2));
        assertTrue("Chain 3 should be present, actual: " + actual, testTool.hasChain(actual, chain3));
    }

    @Test
    public void testFindInsignificantChainsAbsent() {
        GraphStructure testStructure = new GraphStructureBuilderImpl()
                .addVertices("v1", "v2", "v3", "v4", "v5", "v6")
                .addRule("r1", "v3", "v1", "v2")
                .addRule("r2", "v5", "v3", "v4")
                .addRule("r3", "v6", "v1", "v4")
                .build();

        Collection<List<String>> actual = engine.find(testStructure);

        assertTrue("No insignificant chains should be found", actual.isEmpty());
    }
}
