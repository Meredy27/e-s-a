package edu.esa.core.engine;

import edu.esa.core.structure.RulesAdjacencyList;
import edu.esa.core.structure.RulesAdjacencyListBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 * This is a helpclass for the search of all elementary cycles in a graph
 * with the algorithm of Johnson. For this it searches for strong connected
 * components, using the algorithm of Tarjan. The constructor gets an
 * adjacency-list of a graph. Based on this graph, it gets a nodenumber s,
 * for which it calculates the subgraph, containing all nodes
 * {s, s + 1, ..., n}, where n is the highest nodenumber in the original
 * graph (e.g. it builds a subgraph with all nodes with higher or same
 * nodenumbers like the given node s). It returns the strong connected
 * component of this subgraph which contains the lowest nodenumber of all
 * nodes in the subgraph.<br><br>
 *
 * For a description of the algorithm for calculating the strong connected
 * components see:<br>
 * Robert Tarjan: Depth-first search and linear graph algorithms. In: SIAM
 * Journal on Computing. Volume 1, Nr. 2 (1972), pp. 146-160.<br>
 * For a description of the algorithm for searching all elementary cycles in
 * a directed graph see:<br>
 * Donald B. Johnson: Finding All the Elementary Circuits of a Directed Graph.
 * SIAM Journal on Computing. Volumne 4, Nr. 1 (1975), pp. 77-84.<br><br>
 *
 */
public class StrongConnectedComponents {
    /** Adjacency-list of original graph */
    private RulesAdjacencyList adjListOriginal;

    /** Adjacency-list of currently viewed subgraph */
    private RulesAdjacencyList adjList;

    /** Helpattribute for finding scc's */
    private boolean[] visited;

    /** Helpattribute for finding scc's */
    private List<Integer> stack;

    /** Helpattribute for finding scc's */
    private int[] lowlink;

    /** Helpattribute for finding scc's */
    private int[] number;

    /** Helpattribute for finding scc's */
    private int sccCounter = 0;

    /** Helpattribute for finding scc's */
    private List<List<Integer>> currentSCCs;

    /**
     * Constructor.
     *
     * @param adjList adjacency-list of the graph
     */
    public StrongConnectedComponents(RulesAdjacencyList adjList) {
        this.adjListOriginal = adjList;
    }

    /**
     * This method returns the adjacency-structure of the strong connected
     * component with the least vertex in a subgraph of the original graph
     * induced by the nodes {s, s + 1, ..., n}, where s is a given node. Note
     * that trivial strong connected components with just one node will not
     * be returned.
     *
     * @param node node s
     * @return SCCResult with adjacency-structure of the strong
     * connected component; null, if no such component exists
     */
    public SCCResult getAdjacencyList(int node) {
        visited = new boolean[adjListOriginal.size()];
        lowlink = new int[adjListOriginal.size()];
        number = new int[adjListOriginal.size()];
        visited = new boolean[adjListOriginal.size()];
        stack = new ArrayList<>();
        currentSCCs = new ArrayList<>();

        adjList = RulesAdjacencyListBuilder.buildRulesAdjacencyList(adjListOriginal, node);

        System.out.println(adjList);

        for (int i = node; i < adjListOriginal.size(); i++) {
            if (!visited[i]) {
                getStrongConnectedComponents(i);
                List<Integer> nodes = getLowestIdComponent();
                if (nodes != null && !nodes.contains(node) && !nodes.contains(node + 1)) {
                    return getAdjacencyList(node + 1);
                } else if (nodes != null) {
                    RulesAdjacencyList adjacencyList =
                            RulesAdjacencyListBuilder.buildRulesAdjacencyList(adjList, nodes);
                    System.out.println(adjacencyList);
                    for (int j = 0; j < this.adjListOriginal.size(); j++) {
                        if (adjacencyList.getSucceedingForRule(j).size() > 0) {
                            return new SCCResult(adjacencyList, j);
                        }
                    }
                }
            }
        }

        return null;
    }

    /**
     * Calculates the strong connected component out of a set of scc's, that
     * contains the node with the lowest index.
     *
     * @return Vector::Integer of the scc containing the lowest nodenumber
     */
    private List<Integer> getLowestIdComponent() {
        int min = adjList.size();
        List<Integer> currScc = null;

        for (List<Integer>  scc : currentSCCs) {
            for (Integer node : scc) {
                if (node < min) {
                    currScc = scc;
                    min = node;
                }
            }
        }

        return currScc;
    }

    /**
     * Searchs for strong connected components reachable from a given node.
     *
     * @param root node to start from.
     */
    private void getStrongConnectedComponents(int root) {
        sccCounter++;
        lowlink[root] = sccCounter;
        number[root] = sccCounter;
        visited[root] = true;
        stack.add(new Integer(root));

        for (String rule : adjList.getSucceedingForRule(root)) {
            int w = adjList.getIndex(rule);
            if (!visited[w]) {
                getStrongConnectedComponents(w);
                lowlink[root] = Math.min(lowlink[root], lowlink[w]);
            } else if (number[w] < number[root]) {
                if (stack.contains(w)) {
                    lowlink[root] = Math.min(lowlink[root], number[w]);
                }
            }
        }

        // found scc
        if ((lowlink[root] == number[root]) && (stack.size() > 0)) {
            int next;
            List<Integer> scc = new ArrayList<>();

            do {
                next = stack.get(stack.size() - 1).intValue();
                stack.remove(stack.size() - 1);
                scc.add(next);
            } while (number[next] > number[root]);

            // simple scc's with just one node will not be added
            if (scc.size() > 1) {
                this.currentSCCs.add(scc);
            }
        }
    }
}
