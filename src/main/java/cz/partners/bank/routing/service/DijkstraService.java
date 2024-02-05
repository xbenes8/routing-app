package cz.partners.bank.routing.service;

import cz.partners.bank.routing.service.vo.Graph;
import cz.partners.bank.routing.service.vo.Node;

/**
 * @author Zdenek Benes
 */
public interface DijkstraService {

    /**
     * Reused algorithm from <a href="https://www.baeldung.com/java-dijkstra">...</a>
     *
     * @param graph  input in which distances are calculated
     * @param source source node for which distances are calculated
     */
    void calculateShortestPathFromSource(Graph graph, Node source);
}
