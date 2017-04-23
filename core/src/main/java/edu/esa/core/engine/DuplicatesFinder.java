package edu.esa.core.engine;

import edu.esa.core.errors.ErrorsData;
import edu.esa.core.structure.GraphStructureBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public class DuplicatesFinder implements ErrorsFinder{

    private GraphStructureBuilder builder;

    private Collection<Collection<String>> inclusiveDuplicates = new ArrayList<>();
    private Collection<Collection<String>> completeDuplicates = new ArrayList<>();
    private Collection<Collection<String>> partialDuplicates = new ArrayList<>();

    @Override
    public void setGraphStructureBuilder(GraphStructureBuilder builder) {
        this.builder = builder;
    }

    @Override
    public void checkAddVertex(String vertexId) {}

    @Override
    public void checkAddVertices(String... verticesIds) {}

    @Override
    public void checkAddRule(String ruleId, String vertexTo, String... verticesFrom) {
        Collection<String> potentialDuplicates = builder.findRules(verticesFrom[0], vertexTo);
        if(potentialDuplicates.isEmpty()) {
            return;
        }

        for(String duplicate : potentialDuplicates) {
            Collection<String> duplicateVerticesFrom = builder.getVerticesFrom(duplicate);

            Collection<String> common = getCommon(duplicateVerticesFrom, verticesFrom);
            if(common.isEmpty()) {
                continue;
            }

            if(common.size() == verticesFrom.length) {
                if(duplicateVerticesFrom.size() > verticesFrom.length) {
                    addInclusiveDuplicate(ruleId, duplicate);
                } else if (duplicateVerticesFrom.size() == verticesFrom.length){
                    addCompleteDuplicate(ruleId, duplicate);
                }
            } else if (common.size() == duplicateVerticesFrom.size()) {
                if(duplicateVerticesFrom.size() < verticesFrom.length) {
                    addInclusiveDuplicate(duplicate, ruleId);
                }
            } else {
                addPartialDuplicate(ruleId, duplicate);
            }
        }
    }

    @Override
    public void fillErrorsData(ErrorsData errorsData) {
        errorsData.setInclusiveDuplicates(inclusiveDuplicates);
        errorsData.setCompleteDuplicates(completeDuplicates);
        errorsData.setPartialDuplicates(partialDuplicates);
    }

    private void addPartialDuplicate(String ruleId, String duplicateId) {
        partialDuplicates.add(Arrays.asList(ruleId, duplicateId));
    }

    private void addInclusiveDuplicate(String ruleId, String duplicateId) {
        inclusiveDuplicates.add(Arrays.asList(ruleId, duplicateId));

    }

    private void addCompleteDuplicate(String ruleId, String duplicateId) {
        completeDuplicates.add(Arrays.asList(ruleId, duplicateId));
    }

    private boolean containsAll(Collection<String> collection, String[] values) {
        for(String value: values) {
            if(!collection.contains(value)) {
                return false;
            }
        }
        return true;
    }

    private Collection<String> getCommon(Collection<String> collection, String[] values) {
        Collection<String> common = new ArrayList<>();

        for(String value : values) {
            if(collection.contains(value)) {
                common.add(value);
            }
        }

        return common;
    }
}
