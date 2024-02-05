package cz.partners.bank.routing.persistence.model;

import lombok.Builder;
import lombok.Data;

import java.util.Set;

/**
 * @author Zdenek Benes
 */
@Data
@Builder
public class Country {
    private String cca3;
    private Set<String> borders;
}
