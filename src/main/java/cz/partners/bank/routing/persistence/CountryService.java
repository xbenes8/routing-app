package cz.partners.bank.routing.persistence;

import cz.partners.bank.routing.persistence.model.Country;

import java.util.Set;

/**
 * Service for loading countries
 *
 * @author Zdenek Benes
 */
public interface CountryService {

    Set<Country> loadCountries();
}
