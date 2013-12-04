package no.mesan.persistence.country;

import java.util.List;

import no.mesan.model.Country;

/**
 * TODO
 *
 * @author Anders Grotthing Moe
 */
public interface CountryDao {
    public List<Country> getCountries();
    public Country getCountryByCode(final String countryCode);
    public Country getCountryByName(final String countryName);
}
