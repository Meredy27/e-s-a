package edu.esa.core.structure;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertTrue;

public class RulesAdjacencyListBuilderTest {
    @Test
    public void testBuildRulesAdjacencyList(){
        GraphStructure testStructure = new GraphStructureBuilderImpl()
                .addVertices("v1", "v2", "v3", "v4", "v5", "v6", "v7", "v8", "v9", "v10")
                .addRule("r1", "v3", "v1", "v2")
                .addRule("r2", "v6", "v4", "v5")
                .addRule("r3", "v8", "v3", "v6", "v7")
                .addRule("r4", "v4", "v9", "v8")
                .addRule("r5", "v10", "v7", "v8")
                .build();

        List<String> rules = Arrays.asList("r1", "r2", "r3", "r4", "r5");
        List<String> succeedingRules1 = Collections.singletonList("r3");
        List<String> succeedingRules2 = Collections.singletonList("r3");
        List<String> succeedingRules3 = Arrays.asList("r4", "r5");
        List<String> succeedingRules4 = Collections.singletonList("r2");

        RulesAdjacencyList result = RulesAdjacencyListBuilder.buildRulesAdjacencyList(testStructure);

        assertTrue(rules.size() == result.getRules().size());
        assertTrue(result.getRules().containsAll(rules));

        assertTrue(succeedingRules1.size() == result.getSucceedingForRule("r1").size());
        assertTrue(result.getSucceedingForRule("r1").containsAll(succeedingRules1));

        assertTrue(succeedingRules2.size() == result.getSucceedingForRule("r2").size());
        assertTrue(result.getSucceedingForRule("r2").containsAll(succeedingRules2));

        assertTrue(succeedingRules3.size() == result.getSucceedingForRule("r3").size());
        assertTrue(result.getSucceedingForRule("r3").containsAll(succeedingRules3));

        assertTrue(succeedingRules4.size() == result.getSucceedingForRule("r4").size());
        assertTrue(result.getSucceedingForRule("r4").containsAll(succeedingRules4));

        assertTrue(result.getSucceedingForRule("r5").isEmpty());
    }
}
