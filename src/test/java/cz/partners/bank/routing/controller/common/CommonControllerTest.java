package cz.partners.bank.routing.controller.common;

import cz.partners.bank.routing.controller.client.RoutingClient;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author Zdenek Benes
 */
@SpringBootTest
public class CommonControllerTest {

    @Autowired
    protected MockMvcExecutor mockMvcExecutor;
    @Autowired
    protected RoutingClient routingClient;

    @BeforeEach
    void setup() {
        mockMvcExecutor.init();
    }
}
