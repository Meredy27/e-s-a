package edu.esa.core.errors;

import java.util.Collection;
import java.util.List;

public class ErrorsData {
    //TODO add differentiation
    private Collection<List<String>> explicitInsignificantChains;
    private Collection<List<String>> imlicitInsignificantChains;

    private Collection<Collection<String>> inclusiveDuplicates;
    private Collection<Collection<String>> completeDuplicates;
    private Collection<Collection<String>> partialDuplicates;

    //TODO add finding
    private Collection<List<String>> redundantChains;

    private Collection<String> redundantForInputRules;
    private Collection<String> redundantForOutputRules;

    private Collection<List<String>> cycles;
    private Collection<String> simpleCycles;

    private Collection<String> isolatedVertices;

    //TODO left for further improvements
    private Collection<Collection<List<String>>> contradictingChains;

    public Collection<List<String>> getExplicitInsignificantChains() {
        return explicitInsignificantChains;
    }

    public void setExplicitInsignificantChains(Collection<List<String>> explicitInsignificantChains) {
        this.explicitInsignificantChains = explicitInsignificantChains;
    }

    public Collection<List<String>> getImlicitInsignificantChains() {
        return imlicitInsignificantChains;
    }

    public void setImlicitInsignificantChains(Collection<List<String>> imlicitInsignificantChains) {
        this.imlicitInsignificantChains = imlicitInsignificantChains;
    }

    public Collection<Collection<String>> getInclusiveDuplicates() {
        return inclusiveDuplicates;
    }

    public void setInclusiveDuplicates(Collection<Collection<String>> inclusiveDuplicates) {
        this.inclusiveDuplicates = inclusiveDuplicates;
    }

    public Collection<Collection<String>> getCompleteDuplicates() {
        return completeDuplicates;
    }

    public void setCompleteDuplicates(Collection<Collection<String>> completeDuplicates) {
        this.completeDuplicates = completeDuplicates;
    }

    public Collection<Collection<String>> getPartialDuplicates() {
        return partialDuplicates;
    }

    public void setPartialDuplicates(Collection<Collection<String>> partialDuplicates) {
        this.partialDuplicates = partialDuplicates;
    }

    public Collection<List<String>> getRedundantChains() {
        return redundantChains;
    }

    public void setRedundantChains(Collection<List<String>> redundantChains) {
        this.redundantChains = redundantChains;
    }

    public Collection<String> getRedundantForInputRules() {
        return redundantForInputRules;
    }

    public void setRedundantForInputRules(Collection<String> redundantForInputRules) {
        this.redundantForInputRules = redundantForInputRules;
    }

    public Collection<String> getRedundantForOutputRules() {
        return redundantForOutputRules;
    }

    public void setRedundantForOutputRules(Collection<String> redundantForOutputRules) {
        this.redundantForOutputRules = redundantForOutputRules;
    }

    public Collection<List<String>> getCycles() {
        return cycles;
    }

    public void setCycles(Collection<List<String>> cycles) {
        this.cycles = cycles;
    }

    public Collection<String> getSimpleCycles() {
        return simpleCycles;
    }

    public void setSimpleCycles(Collection<String> simpleCycles) {
        this.simpleCycles = simpleCycles;
    }

    public Collection<String> getIsolatedVertices() {
        return isolatedVertices;
    }

    public void setIsolatedVertices(Collection<String> isolatedVertices) {
        this.isolatedVertices = isolatedVertices;
    }

    public Collection<Collection<List<String>>> getContradictingChains() {
        return contradictingChains;
    }

    public void setContradictingChains(Collection<Collection<List<String>>> contradictingChains) {
        this.contradictingChains = contradictingChains;
    }
}
