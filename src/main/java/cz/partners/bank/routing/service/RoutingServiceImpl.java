package cz.partners.bank.routing.service;

import cz.partners.bank.routing.common.exception.NotFoundException;
import cz.partners.bank.routing.service.command.RoutingCommand;
import cz.partners.bank.routing.service.validation.RoutingValidator;
import cz.partners.bank.routing.service.vo.Graph;
import cz.partners.bank.routing.service.vo.Node;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Zdenek Benes
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class RoutingServiceImpl implements RoutingService {

    private final RoutingValidator routingValidator;
    private final GraphService graphService;
    private final DijkstraService dijkstraService;
    private final MessageSource messageSource;

    @Override
    public List<String> findRouting(RoutingCommand routingCommand) {
        routingValidator.validateObject(routingCommand);

        String origin = routingCommand.getOrigin();
        String destination = routingCommand.getDestination();
        if (origin.equalsIgnoreCase(destination)) {
            return List.of(origin);
        }

        Graph graph = graphService.initializeGraph();
        Node originNode = graph.getNodeFromGraph(origin);

        dijkstraService.calculateShortestPathFromSource(graph, originNode);

        Node destinationNode = graph.getNodeFromGraph(destination);
        if (destinationNode != null && destinationNode.getShortestPath().size() > 0) {
            return buildRoutingPathResult(destinationNode);
        }
        throw new NotFoundException(getNotFoundDestinationErrorMessage(origin, destination));
    }

    private String getNotFoundDestinationErrorMessage(String origin, String destination) throws NotFoundException {
        return messageSource.getMessage(
            "routing.output.notFound",
            new Object[]{origin, destination},
            LocaleContextHolder.getLocale()
        );
    }

    private static List<String> buildRoutingPathResult(Node destinationNode) {
        List<String> pathFromDestination = destinationNode.getShortestPath().stream()
            .map(Node::getName)
            .collect(Collectors.toList());
        pathFromDestination.add(destinationNode.getName());
        return pathFromDestination;
    }

}
