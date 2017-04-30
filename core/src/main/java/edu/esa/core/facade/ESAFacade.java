package edu.esa.core.facade;

import edu.esa.core.engine.*;
import edu.esa.core.errors.ErrorsData;
import edu.esa.core.parsers.KnowledgeBaseParser;
import edu.esa.core.parsers.KnowledgeBaseSource;
import edu.esa.core.parsers.exceptions.KnowledgeBaseParseException;
import edu.esa.core.parsers.factory.FactoryCreator;
import edu.esa.core.structure.GraphStructure;
import edu.esa.core.structure.GraphStructureBuilder;
import edu.esa.core.structure.GraphStructureBuilderEngineWrapper;
import edu.esa.core.structure.GraphStructureBuilderImpl;

import java.util.ArrayList;
import java.util.Collection;

public class ESAFacade {
    public static ErrorsData searchErrors(KnowledgeBaseSource source, ErrorSearchOptions options)
            throws KnowledgeBaseParseException {
        ErrorsData errorsData = new ErrorsData();
        Collection<ErrorsFinder>  errorsFinders = getErrorFinders(options);

        GraphStructureBuilder builder = new GraphStructureBuilderEngineWrapper(
                new GraphStructureBuilderImpl(), errorsFinders);

        KnowledgeBaseParser parser = FactoryCreator.getParsingFactory(source.getType())
                .buildKnowledgeBaseParser();
        parser.build(builder, source);

        GraphStructure structure = builder.build();

        if(options.searchCycles) {
            CyclesFinder finder = new CyclesFinder();
            errorsData.setCycles(finder.find(structure));
        }

        if(options.searchExplicitInsignificatChain || options.searchImplicitInsignificantChain) {
            InsignificantChainsResolver resolver = new InsignificantChainsResolver();
            resolver.findErrors(structure);
            resolver.fillErrorsData(errorsData);
        }

        if(options.searchIsolatedVertices) {
            IsolatedVerticesFinder finder = new IsolatedVerticesFinder();
            errorsData.setIsolatedVertices(finder.find(structure));
        }

        if(options.searchRedundantForInput) {
            RedundantByInputRuleFinder finder = new RedundantByInputRuleFinder();
            errorsData.setRedundantForInputRules(finder.find(structure, structure.getGoals()));
        }

        if(options.searchRedundantForOutput) {
            RedundantByOutputRuleFinder finder = new RedundantByOutputRuleFinder();
            errorsData.setRedundantForOutputRules(finder.find(structure, structure.getInputFacts()));
        }

        if(options.searchRedundantChains) {
            RedundantChainsFinder finder = new RedundantChainsFinder();
            errorsData.setRedundantChains(finder.find(structure));
        }

        for(ErrorsFinder finder: errorsFinders) {
            finder.fillErrorsData(errorsData);
        }

        return errorsData;
    }

    private static Collection<ErrorsFinder> getErrorFinders(ErrorSearchOptions options) {
        Collection<ErrorsFinder>  errorsFinders = new ArrayList<>();

        if(options.searchCompleteDuplicates || options.searchPartialDuplicates ||
                options.searchInclusiveDuplicates) {
            errorsFinders.add(new DuplicatesFinder());
        }

        if(options.searchSimpleCycles) {
            errorsFinders.add(new SimpleCyclesFinder());
        }

        return errorsFinders;
    }
}
