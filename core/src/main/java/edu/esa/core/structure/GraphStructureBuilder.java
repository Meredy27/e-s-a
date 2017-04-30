package edu.esa.core.structure;

import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.Collections;

public interface GraphStructureBuilder {
    GraphStructureBuilder addVertex(@NotNull String vertexId);

    GraphStructureBuilder addVertices(@NotNull String... verticesIds);

    GraphStructureBuilder addRule(@NotNull String ruleId,
                                      @NotNull String vertexTo, @NotNull String... verticesFrom);

    boolean vertexExist(String vertexId);

    boolean ruleExist(String vertexFrom, String vertexTo);

    void setGoals(Collection<String> goals);

    Collection<String> findRules(String vertexFrom, String vertexTo);

    Collection<String> getVerticesFrom(String ruleId);

    String getVertexTo(String ruleId);

    GraphStructure build();
}
