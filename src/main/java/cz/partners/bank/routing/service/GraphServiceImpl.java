package cz.partners.bank.routing.service;

import cz.partners.bank.routing.persistence.CountryService;
import cz.partners.bank.routing.persistence.model.Country;
import cz.partners.bank.routing.service.vo.Graph;
import cz.partners.bank.routing.service.vo.Node;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author Zdenek Benes
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class GraphServiceImpl implements GraphService {

    private final CountryService countryService;

    @Override
    public Graph initializeGraph() {

        Set<Country> countries = countryService.loadCountries();
        Map<String, Node> mapWithNodes = transformToNodes(countries);
        addAdjacentNodes(mapWithNodes, countries);
        Graph graph = new Graph();
        for (Node node : mapWithNodes.values()) {
            graph.addNode(node);
        }
        return graph;
    }

    private void addAdjacentNodes(Map<String, Node> nodes, Set<Country> countries) {
        for (Country country : countries) {
            Set<String> countryBorders = country.getBorders();
            if (!CollectionUtils.isEmpty(countryBorders)) {
                Node countryNode = nodes.get(country.getCca3());
                for (String countryCode : countryBorders) {
                    Node adjacentNode = nodes.get(countryCode);
                    countryNode.addDestination(adjacentNode, 1);
                }
            }
        }

    }

    private Map<String, Node> transformToNodes(Set<Country> countries) {
        return countries.stream()
            .map(country -> new Node(country.getCca3()))
            .collect(Collectors.toMap(Node::getName, Function.identity()));
    }
}
