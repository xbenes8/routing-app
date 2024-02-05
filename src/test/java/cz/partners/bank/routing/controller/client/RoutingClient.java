package cz.partners.bank.routing.controller.client;

import cz.partners.bank.routing.controller.RoutingController;
import cz.partners.bank.routing.controller.common.MockMvcExecutor;
import cz.partners.bank.routing.controller.response.RoutingResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author Zdenek Benes
 */
@Component
@RequiredArgsConstructor
public class RoutingClient {

    private final MockMvcExecutor mockMvcExecutor;

    public MvcResult findRoutingWithMvcResult(String origin, String destination) throws Exception {
        String routingUri = RoutingController.ROUTING_URI
            .replace("{origin}", origin)
            .replace("{destination}", destination);
        return mockMvcExecutor.getMethod(routingUri);
    }

    public RoutingResponse findRouting(String origin, String destination) throws Exception {
        String routingUri = RoutingController.ROUTING_URI
            .replace("{origin}", origin)
            .replace("{destination}", destination);
        MvcResult mvcResult = mockMvcExecutor.getMethod(routingUri);
        assertTrue(mockMvcExecutor.isResponseOk(mvcResult));
        return mockMvcExecutor.readResponse(mvcResult, RoutingResponse.class);
    }
}
