package no.mesan.persistence.user;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Locale;

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

    @Override
    public User mapRow(final ResultSet resultSet, final int i) throws SQLException {
        final String username     = resultSet.getString("user_username");
        final String email        = resultSet.getString("user_email");
        final String hash         = resultSet.getString("user_hash");
        final String salt         = resultSet.getString("user_salt");
        final String fullName     = resultSet.getString("user_fullName");
        final String countryName  = resultSet.getString("user_country");
        final Locale locale       = new Locale(resultSet.getString("user_locale"));
        final Country country  = countryDao.getCountryByName(countryName);


        final User.Builder builder = new User.Builder(username, email, hash, salt);
        builder.fullName(fullName)
               .locale(locale);
        if (country != null)
            builder.country(country.getCode());

        return new User(builder);
    }
}
