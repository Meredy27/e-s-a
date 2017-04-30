package edu.esa.core.structure;

import edu.esa.core.engine.ErrorsFinder;

import java.util.Collection;

public class GraphStructureBuilderEngineWrapper implements GraphStructureBuilder{
    private GraphStructureBuilder builder;
    private Collection<ErrorsFinder> finders;

    public GraphStructureBuilderEngineWrapper(GraphStructureBuilder builder,
                                              Collection<ErrorsFinder> finders) {
        this.builder = builder;
        this.finders = finders;
        for( ErrorsFinder finder : finders) {
            finder.setGraphStructureBuilder(builder);
        }
    }

    @Override
    public GraphStructureBuilder addVertex(String vertexId) {
        for( ErrorsFinder finder : finders) {
            finder.checkAddVertex(vertexId);
        }
        builder.addVertex(vertexId);
        return this;
    }

    @Override
    public GraphStructureBuilder addVertices(String... verticesIds) {
        for( ErrorsFinder finder : finders) {
            finder.checkAddVertices(verticesIds);
        }
        builder.addVertices(verticesIds);
        return this;
    }

    @Override
    public GraphStructureBuilder addRule(String ruleId, String vertexTo, String... verticesFrom) {
        for( ErrorsFinder finder : finders) {
            finder.checkAddRule(ruleId, vertexTo, verticesFrom);
        }
        builder.addRule(ruleId, vertexTo, verticesFrom);
        return this;
    }

    @Override
    public boolean vertexExist(String vertexId) {
        return builder.vertexExist(vertexId);
    }

    @Override
    public boolean ruleExist(String vertexFrom, String vertexTo) {
        return builder.ruleExist(vertexFrom, vertexTo);
    }

    @Override
    public void setGoals(Collection<String> goals) {
        builder.setGoals(goals);
    }

    @Override
    public Collection<String> findRules(String vertexFrom, String vertexTo) {
        return builder.findRules(vertexFrom, vertexTo);
    }

    @Override
    public Collection<String> getVerticesFrom(String ruleId) {
        return builder.getVerticesFrom(ruleId);
    }

    @Override
    public String getVertexTo(String ruleId) {
        return builder.getVertexTo(ruleId);
    }

    @Override
    public GraphStructure build() {
        return builder.build();
    }
}
