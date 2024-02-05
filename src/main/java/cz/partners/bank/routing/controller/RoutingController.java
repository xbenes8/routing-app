package cz.partners.bank.routing.controller;

import cz.partners.bank.routing.controller.response.RoutingResponse;
import cz.partners.bank.routing.service.RoutingService;
import cz.partners.bank.routing.service.command.RoutingCommand;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * REST controller for Routing.
 *
 * @author Zdenek Benes
 */
@RestController
@Slf4j
@RequiredArgsConstructor
public class RoutingController {

    public static final String ROUTING_URI =  "/routing/{origin}/{destination}";

    private final RoutingService routingService;

    @GetMapping(ROUTING_URI)
    public ResponseEntity<RoutingResponse> findRouting(@PathVariable String origin, @PathVariable String destination) {
        RoutingCommand routingCommand = RoutingCommand.builder()
            .origin(origin)
            .destination(destination)
            .build();
        List<String> routing = routingService.findRouting(routingCommand);
        return ResponseEntity.ok(RoutingResponse.builder()
            .route(routing)
            .build());
    }
}
