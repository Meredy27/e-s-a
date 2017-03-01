package edu.esa.core.engine;

import edu.esa.core.structure.GraphStructure;
import edu.esa.core.structure.RulesAdjacencyList;
import edu.esa.core.structure.RulesAdjacencyListBuilder;

import javax.validation.constraints.NotNull;
import java.util.*;

public class CyclesFinder {
    public Collection<List<String>> find(@NotNull GraphStructure structure) {
        RulesAdjacencyList adjList = RulesAdjacencyListBuilder.buildRulesAdjacencyList(structure);
        System.out.println(adjList);
        CyclesFindingCore cyclesFindingCore = new CyclesFindingCore(adjList);
        return cyclesFindingCore.getElementaryCycles();
    }

    private class CyclesFindingCore {
        /** List of cycles */
        private List<List<String>> cycles;

        /** Adjacency-list of graph */
        private RulesAdjacencyList adjList;

        /** Blocked nodes, used by the algorithm of Johnson */
        private boolean[] blocked;

        /** B-Lists, used by the algorithm of Johnson */
        private List<Integer>[] B = null;

        /** Stack for nodes, used by the algorithm of Johnson */
        private List<Integer> stack = null;

	    public CyclesFindingCore(RulesAdjacencyList adjList) {
            this.adjList = adjList;
        }

        /**
         * Returns List::List::Object with the Lists of nodes of all elementary
         * cycles in the graph.
         *
         * @return List::List::Object with the Lists of the elementary cycles.
         */
        public List<List<String>> getElementaryCycles() {
            cycles = new ArrayList<>();
            blocked = new boolean[adjList.size()];
            B = new List[adjList.size()];
            stack = new ArrayList<>();

            StrongConnectedComponents sccs = new StrongConnectedComponents(adjList);
            int s = 0;

            while (true) {
                SCCResult sccResult = sccs.getAdjacencyList(s);
                if (sccResult != null && sccResult.getAdjList() != null) {
                    RulesAdjacencyList scc = sccResult.getAdjList();
                    s = sccResult.getMinRuleIndex();
                    for (int j = 0; j < scc.size(); j++) {
                        if (scc.getSucceedingForRule(j).size() > 0) {
                            blocked[j] = false;
                            B[j] = new LinkedList<>();
                        }
                    }

                    findCycles(s, s, scc);
                    s++;
                } else {
                    break;
                }
            }

            return cycles;
        }

        /**
         * Calculates the cycles containing a given node in a strongly connected
         * component. The method calls itself recursivly.
         *
         * @param v
         * @param s
         * @param adjList adjacency-list with the subgraph of the strongly
         * connected component s is part of.
         * @return true, if cycle found; false otherwise
         */
        private boolean findCycles(int v, int s, RulesAdjacencyList adjList) {
            boolean f = false;
            stack.add(v);
            blocked[v] = true;

            for (String rule : adjList.getSucceedingForRule(v)) {
                int w = adjList.getIndex(rule);
                // found cycle
                if (w == s) {
                    List<String> cycle = new ArrayList<>();
                    for (int j = 0; j < stack.size(); j++) {
                        int index = stack.get(j);
                        cycle.add(adjList.getRuleByIndex(index));
                    }
                    cycles.add(cycle);
                    f = true;
                } else if (!blocked[w]) {
                    if (findCycles(w, s, adjList)) {
                        f = true;
                    }
                }
            }

            if (f) {
                unblock(v);
            } else {
                for (String rule : adjList.getSucceedingForRule(v)) {
                    int w = adjList.getIndex(rule);
                    if (!B[w].contains(v)) {
                        B[w].add(v);
                    }
                }
            }

            stack.remove(Integer.valueOf(v));
            return f;
        }

        /**
         * Unblocks recursivly all blocked nodes, starting with a given node.
         *
         * @param node node to unblock
         */
        private void unblock(int node) {
            blocked[node] = false;
            List<Integer> bNode = B[node];
            while (bNode.size() > 0) {
                Integer w = bNode.get(0);
                bNode.remove(0);
                if (blocked[w]) {
                    unblock(w);
                }
            }
        }
    }
}
