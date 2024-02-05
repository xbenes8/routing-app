package cz.partners.bank.routing.service;

import cz.partners.bank.routing.service.command.RoutingCommand;

import java.util.List;

/**
 * Service for routing
 *
 * @author Zdenek Benes
 */
public interface RoutingService {

    List<String> findRouting(RoutingCommand routingCommand);
}
