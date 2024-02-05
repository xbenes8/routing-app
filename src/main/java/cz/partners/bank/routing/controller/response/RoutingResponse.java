package cz.partners.bank.routing.controller.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Zdenek Benes
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoutingResponse {

    private List<String> route;

}
