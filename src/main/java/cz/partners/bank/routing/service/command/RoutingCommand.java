package cz.partners.bank.routing.service.command;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Zdenek Benes
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoutingCommand {

    private String origin;
    private String destination;
}
