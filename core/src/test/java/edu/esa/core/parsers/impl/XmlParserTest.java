package edu.esa.core.parsers.impl;

import edu.esa.core.parsers.KnowledgeBaseParser;
import edu.esa.core.parsers.KnowledgeBaseSource;
import edu.esa.core.parsers.SourceType;
import edu.esa.core.parsers.factory.FactoryCreator;
import edu.esa.core.structure.GraphStructure;
import edu.esa.core.structure.GraphStructureBuilder;
import edu.esa.core.structure.GraphStructureBuilderImpl;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertTrue;

public class XmlParserTest {
    @Test
    public void testEmptyFile() throws Exception{
        KnowledgeBaseParser parser =
                FactoryCreator.getParsingFactory(SourceType.XML).buildKnowledgeBaseParser();
        KnowledgeBaseSource source =
                FactoryCreator.getParsingFactory(SourceType.XML)
                        .buildKnowledgeBaseSourse("src/test/resources/empty.xml");
        GraphStructureBuilder builder = new GraphStructureBuilderImpl();
        parser.build(builder, source);
    }

    @Test
    public void testBuildGraphStructure() throws Exception {
        KnowledgeBaseParser parser =
                FactoryCreator.getParsingFactory(SourceType.XML).buildKnowledgeBaseParser();
        KnowledgeBaseSource source =
                FactoryCreator.getParsingFactory(SourceType.XML)
                        .buildKnowledgeBaseSourse("src/test/resources/graph.xml");
        GraphStructureBuilder builder = new GraphStructureBuilderImpl();
        parser.build(builder, source);

        GraphStructure structure = builder.build();
        assertTrue("v1 should exist", structure.vertexExist("v1"));
        assertTrue("v2 should exist", structure.vertexExist("v2"));
        assertTrue("v3 should exist", structure.vertexExist("v3"));
        assertTrue("v4 should exist", structure.vertexExist("v4"));
        assertTrue("v5 should exist", structure.vertexExist("v5"));
        assertTrue("There should be 1 goal: " + structure.getGoals(),
                structure.getGoals().size() == 1);
        assertTrue("v5 should be a goal: " + structure.getGoals(),
                structure.getGoals().contains("v5"));
        assertTrue("r1 should exist", structure.ruleExist("r1"));
        assertTrue("r2 should exist", structure.ruleExist("r2"));
        assertTrue("There should be 4 vertices with outcome rules: " + structure.outcomeRules(),
                countOfVerticesWithRules(structure.outcomeRules()) == 4);
        assertTrue("There should be 2 vertices with income rules"  + structure.incomeRules(),
                countOfVerticesWithRules(structure.incomeRules()) == 2);
        assertTrue("There should be 2 rules: " + structure.incomeVertices().keySet(),
                structure.incomeVertices().size() == 2);
        assertTrue("There should be rule: if v1 and v2 then v3",
                structure.ruleExist("v3", Arrays.asList("v1", "v2")));
        assertTrue("There should be rule: if v3 and v4 then v5",
                structure.ruleExist("v5", Arrays.asList("v3", "v4")));
    }

    private int countOfVerticesWithRules(Map<String, List<String>> verticeRules) {
        int count = 0;

        for(String vertex : verticeRules.keySet()) {
            if(verticeRules.get(vertex).isEmpty()) {
                continue;
            }
            count++;
        }

        return count;
    }
}
