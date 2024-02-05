package cz.partners.bank.routing.controller;

import cz.partners.bank.routing.controller.common.CommonControllerTest;
import cz.partners.bank.routing.controller.response.RoutingResponse;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author Zdenek Benes
 */
class RoutingControllerTest extends CommonControllerTest {

    @ParameterizedTest
    @MethodSource("dataWithInvalidInputs")
    void givenInvalidInputs_findRouting_shouldReturnErrorMessages(String origin,
                                                                  String destination,
                                                                  List<String> errorMessages) throws Exception {
        MvcResult routingWithMvcResult = routingClient.findRoutingWithMvcResult(origin, destination);
        String bodyAsString = routingWithMvcResult.getResponse().getContentAsString();

        assertTrue(mockMvcExecutor.isBadRequest(routingWithMvcResult));
        for (String expectedErrorMessage : errorMessages) {
            assertTrue(bodyAsString.contains(expectedErrorMessage));
        }
    }

    @ParameterizedTest
    @MethodSource("dataWithLandConnection")
    void givenInputsWithLandConnection_findRouting_shouldReturnPathFromOriginToSource(String origin,
                                                                                      String destination) throws Exception {
        RoutingResponse routing = routingClient.findRouting(origin, destination);

        assertNotNull(routing);
        assertTrue(routing.getRoute().size() > 0);
    }

    private static Stream<Arguments> dataWithInvalidInputs() {
        return Stream.of(
            //invalid lengths
            Arguments.of("A", "X", List.of("must be 3 characters")),
            Arguments.of("CZ", "XXX", List.of("must be 3 characters", "XXX is not recognized as Country abbreviation")),
            //No countries
            Arguments.of("GER", "GER", List.of("GER is not recognized as Country abbreviation")),
            Arguments.of("GER", "MAN", List.of("MAN is not recognized as Country abbreviation")),
            //no path australia
            Arguments.of("AUS", "CZE", List.of("Land path does not exist between AUS and CZE")),
            Arguments.of("AUS", "MAR", List.of("Land path does not exist between")),
            Arguments.of("AUS", "USA", List.of("Land path does not exist between")),
            Arguments.of("AUS", "MEX", List.of("Land path does not exist between")),
            Arguments.of("AUS", "JPN", List.of("Land path does not exist between")),
            //no path cze
            Arguments.of("CZE", "AUS", List.of("Land path does not exist between")),
            Arguments.of("CZE", "JPN", List.of("Land path does not exist between")),
            Arguments.of("CZE", "USA", List.of("Land path does not exist between")),
            Arguments.of("CZE", "GBR", List.of("Land path does not exist between"))
        );
    }

    private static Stream<Arguments> dataWithLandConnection() {
        return Stream.of(
            //europe -> europe
            Arguments.of("ITA", "CZE"),
            Arguments.of("CZE", "ITA"),
            Arguments.of("ITA", "ESP"),
            Arguments.of("RUS", "ESP"),
            //europe-> africa
            Arguments.of("CZE", "MAR"),
            Arguments.of("CZE", "KEN"),
            //europe -> asia
            Arguments.of("ITA", "CHN"),
            Arguments.of("ITA", "IND"),
            //north america -> south america
            Arguments.of("CAN", "MEX"),
            Arguments.of("CHL", "USA")
        );
    }
}