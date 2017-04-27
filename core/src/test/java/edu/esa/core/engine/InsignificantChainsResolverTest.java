package edu.esa.core.engine;

import edu.esa.core.errors.ErrorsData;
import edu.esa.core.structure.GraphStructure;
import edu.esa.core.structure.GraphStructureBuilderImpl;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.Assert.assertTrue;

public class InsignificantChainsResolverTest {
    private static InsignificantChainsResolver engine = new InsignificantChainsResolver();
    private TestTool testTool = new TestTool();

    @Test
    public void testEmptyStructure() {
        GraphStructure testStructure = new GraphStructureBuilderImpl().build();

        ErrorsData errorsData = new ErrorsData();

        engine.findErrors(testStructure);
        engine.fillErrorsData(errorsData);

        Collection<List<String>> explicitChains = errorsData.getExplicitInsignificantChains();
        assertTrue("No explicit insignificant chains should be found: "+explicitChains,
                explicitChains.isEmpty());

        Collection<List<String>> implicitChains = errorsData.getImplicitInsignificantChains();
        assertTrue("No implicit insignificant chains should be found",
                implicitChains.isEmpty());
    }

    @Test
    public void testFindExplicitInsignificantChains() {
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
                .addRule("r9", "v7", "v3", "v2")
                .build();

        List<String> chain = new ArrayList<>(3);
        chain.add("r4");
        chain.add("r2");
        chain.add("r5");

        ErrorsData errorsData = new ErrorsData();

        engine.findErrors(testStructure);
        engine.fillErrorsData(errorsData);

        Collection<List<String>> actual = errorsData.getExplicitInsignificantChains();

        assertTrue("There should be 1 chain: " + actual, actual.size() == 1);
        assertTrue("Chain should be present, actual: " + actual, testTool.hasChain(actual, chain));
    }

    @Test
    public void testFindImplicitInsignificantChains() {
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
                .addRule("r9", "v7", "v3", "v2")
                .build();

        List<String> chain1 = new ArrayList<>(3);
        chain1.add("r4");
        chain1.add("r2");
        chain1.add("r3");

        List<String> chain2 = new ArrayList<>(1);
        chain2.add("r7");

        ErrorsData errorsData = new ErrorsData();

        engine.findErrors(testStructure);
        engine.fillErrorsData(errorsData);

        Collection<List<String>> actual = errorsData.getImplicitInsignificantChains();

        assertTrue("There should be 2 chains: " + actual, actual.size() == 2);
        assertTrue("Chain 1 should be present, actual: " + actual, testTool.hasChain(actual, chain2));
        assertTrue("Chain 2 should be present, actual: " + actual, testTool.hasChain(actual, chain2));
    }
}
