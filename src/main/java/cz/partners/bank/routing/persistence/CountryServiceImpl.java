package cz.partners.bank.routing.persistence;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import cz.partners.bank.routing.persistence.model.Country;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.Set;

/**
 * @author Zdenek Benes
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class CountryServiceImpl implements CountryService {

    public static final String COUNTRIES_JSON = "countries.json";

    private final ObjectMapper objectMapper;
    @Override
    @Cacheable("countries")
    public Set<Country> loadCountries() {

        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(COUNTRIES_JSON)) {
            return objectMapper.readValue(inputStream, new TypeReference<>() {});
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
