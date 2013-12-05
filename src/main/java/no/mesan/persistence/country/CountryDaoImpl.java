package no.mesan.persistence.country;

import java.util.List;
import java.util.Properties;

import javax.inject.Inject;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import no.mesan.model.Country;
import no.mesan.properties.Sql;
import static no.mesan.properties.PropertiesProvider.*;

/**
 * TODO
 *
 * @author Anders Grotthing Moe
 */
public class CountryDaoImpl implements CountryDao {
    @Inject @Sql
    private Properties sql;
    @Inject
    private CountryRowMapper countryRowMapper;
    @Inject
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<Country> getCountries() {
        return jdbcTemplate.query(sql.getProperty(GET_COUNTRIES), countryRowMapper);
    }

    @Override
    public Country getCountryByCode(final String countryCode) {
        try {
            return jdbcTemplate.queryForObject(sql.getProperty(GET_COUNTRY_BY_CODE),
                    countryRowMapper,
                    countryCode);
        } catch (EmptyResultDataAccessException erdae) {
            return null;
        }
    }

    @Override
    public Country getCountryByName(final String countryName) {
        try {
            return jdbcTemplate.queryForObject(sql.getProperty(GET_COUNTRY_BY_NAME),
                    countryRowMapper,
                    countryName);
        } catch (EmptyResultDataAccessException erdae) {
            return null;
        }
    }

}
