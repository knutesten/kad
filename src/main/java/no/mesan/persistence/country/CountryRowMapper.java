package no.mesan.persistence.country;

import java.sql.ResultSet;
import java.sql.SQLException;

import no.mesan.model.Country;

import org.springframework.jdbc.core.RowMapper;

/**
 * TODO
 *
 * @author Anders Grotthing Moe
 */
public class CountryRowMapper implements RowMapper<Country> {
    @Override
    public Country mapRow(final ResultSet resultSet, final int i) throws SQLException {
        final String code = resultSet.getString("country_code");
        final String name = resultSet.getString("country_name");
        
        return new Country(code, name);
    }
}
