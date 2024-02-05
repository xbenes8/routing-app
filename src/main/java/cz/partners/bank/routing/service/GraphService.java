package cz.partners.bank.routing.service;

import cz.partners.bank.routing.service.vo.Graph;

/**
 * Service for preparation Graph structure and operation over it to retrieve the shortest path
 *
 * @author Zdenek Benes
 */
public interface GraphService {

    Graph initializeGraph();
}
