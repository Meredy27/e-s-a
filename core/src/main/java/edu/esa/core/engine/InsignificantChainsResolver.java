package edu.esa.core.engine;

import edu.esa.core.errors.ErrorsData;
import edu.esa.core.structure.GraphStructure;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class InsignificantChainsResolver {

    private InsignificantChainsFinder errorsFinder = new InsignificantChainsFinder();

    private Collection<List<String>> explicitInsignificantChains;
    private Collection<List<String>> implicitInsignificantChains;

    private void clear() {
        explicitInsignificantChains = new ArrayList<>();
        implicitInsignificantChains = new ArrayList<>();
    }

    public void findErrors(GraphStructure structure) {
        clear();

        Collection<List<String>> insignificantChains = errorsFinder.find(structure);

        for(List<String> chain : insignificantChains) {
            String outcomeVertex = structure.outcomeVertices().get(chain.get(chain.size() - 1));
            Collection<String> incomeVertices = structure.incomeVertices().get(chain.get(0));

            Collection<String> rules = structure.getRules(outcomeVertex, incomeVertices);
            if(!rules.isEmpty() && (rules.size() > 1 || (rules.size() == 1 && !rules.contains(chain.get(0)))) ) {
                explicitInsignificantChains.add(chain);
            } else {
                implicitInsignificantChains.add(chain);
            }
        }
    }

    public void fillErrorsData(ErrorsData errorsData) {
        errorsData.setExplicitInsignificantChains(explicitInsignificantChains);
        errorsData.setImlicitInsignificantChains(implicitInsignificantChains);
    }
}
