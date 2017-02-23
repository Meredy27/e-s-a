package edu.esa.core.structure;

import javax.validation.constraints.NotNull;

public interface GraphStructureBuilder {
    GraphStructureBuilder addVertex(@NotNull String vertexId);

    GraphStructureBuilder addVertices(@NotNull String... verticesIds);

    GraphStructureBuilder addRule(@NotNull String ruleId,
                                      @NotNull String vertexTo, @NotNull String... verticesFrom);

    boolean vertexExist(String vertexId);

    GraphStructure build();
}
