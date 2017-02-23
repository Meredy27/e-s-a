package edu.esa.core.engine;

import edu.esa.core.errors.ErrorsData;
import edu.esa.core.structure.GraphStructureBuilder;

import javax.validation.constraints.NotNull;

public interface ErrorsFinder {
    void setGraphStructureBuilder(GraphStructureBuilder builder);

    default void checkAddVertex(@NotNull String vertexId) {}

    default void checkAddVertices(@NotNull String... verticesIds) {}

    default void checkAddRule(@NotNull String ruleId,
                                      @NotNull String vertexTo, @NotNull String... verticesFrom) {}

    void fillErrorsData(@NotNull ErrorsData errorsData);
}
