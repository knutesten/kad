package no.mesan.persistence.user;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.inject.Inject;

import no.mesan.model.Country;
import no.mesan.model.User;
import no.mesan.persistence.country.CountryDao;

import org.springframework.jdbc.core.RowMapper;

/**
 * TODO
 *
 * @author Knut Esten Melandsø Nekså
 * @author Anders Grotthing Moe
 */
public class UserRowMapper implements RowMapper<User> {
    @Inject
    private CountryDao countryDao;
    private final Map<String, Country> countryCache = new HashMap<>();

    @Override
    public User mapRow(final ResultSet resultSet, final int i) throws SQLException {
        final String username = resultSet.getString("user_username");
        final String email    = resultSet.getString("user_email");
        final String hash     = resultSet.getString("user_hash");
        final String salt     = resultSet.getString("user_salt");
        final String fullName = resultSet.getString("user_fullName");
        final Locale locale   = new Locale(resultSet.getString("user_locale"));
        final Country country = getCountry(resultSet.getString("user_country"));

        final User.Builder builder = new User.Builder(username, email, hash, salt);
        builder.fullName(fullName)
               .locale(locale)
               .country(country);

        return new User(builder);
    }

    private Country getCountry(final String countryCode) {
        if (!countryCache.containsKey(countryCode))
            countryCache.put(countryCode, countryDao.getCountryByCode(countryCode));
        return countryCache.get(countryCode);
    }
}
