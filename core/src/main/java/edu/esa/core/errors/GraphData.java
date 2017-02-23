package edu.esa.core.errors;

import edu.esa.core.structure.GraphStructure;

import java.util.Collection;

public class GraphData {
    private GraphStructure graphStructure;
    private Collection<String> inputVertices;
    private Collection<String> goals;

    public GraphData(GraphStructure graphStructure, Collection<String> inputVertices, Collection<String> goals) {
        this.graphStructure = graphStructure;
        this.inputVertices = inputVertices;
        this.goals = goals;
    }

    public GraphStructure getGraphStructure() {
        return graphStructure;
    }

    public Collection<String> getInputVertices() {
        return inputVertices;
    }

    public Collection<String> getGoals() {
        return goals;
    }
}
