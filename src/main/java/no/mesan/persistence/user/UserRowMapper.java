package no.mesan.persistence.user;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Pattern;

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
    private final Pattern splitLocaleStringRegex = Pattern.compile("_");

    @Override
    public User mapRow(final ResultSet resultSet, final int i) throws SQLException {
        final int    id       = resultSet.getInt   ("user_id");
        final String username = resultSet.getString("user_username");
        final String email    = resultSet.getString("user_email");
        final String hash     = resultSet.getString("user_hash");
        final String salt     = resultSet.getString("user_salt");
        final String fullName = resultSet.getString("user_fullName");
        final Locale locale   = getLocaleFromString(resultSet.getString("user_locale"));
        final Country country = getCountry(resultSet.getString("user_country"));

        final User.Builder builder = new User.Builder(username, email, hash, salt);
        builder.fullName(fullName)
               .locale(locale)
               .country(country)
               .id(id);

        return new User(builder);
    }

    private Country getCountry(final String countryCode) {
        if (!countryCache.containsKey(countryCode))
            countryCache.put(countryCode, countryDao.getCountryByCode(countryCode));
        return countryCache.get(countryCode);
    }

    private Locale getLocaleFromString(final String localeString) {
        final String[] localeAttributes = splitLocaleStringRegex.split(localeString);
        switch (localeAttributes.length) {
            case 0:  return null;
            case 1:  return new Locale(localeAttributes[0]);
            default: return new Locale(localeAttributes[0], localeAttributes[1]);
        }
    }
}
