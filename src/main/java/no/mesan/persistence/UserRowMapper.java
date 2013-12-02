package no.mesan.persistence;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Locale;

import no.mesan.model.User;

import org.springframework.jdbc.core.RowMapper;

/**
 * TODO
 *
 * @author Knut Esten Melandsø Nekså
 */
public class UserRowMapper implements RowMapper<User> {
    @Override
    public User mapRow(final ResultSet resultSet, final int i) throws SQLException {
        final String username = resultSet.getString("user_username");
        final String email    = resultSet.getString("user_email");
        final String password = resultSet.getString("user_password");
        final String salt     = resultSet.getString("user_salt");
        final String fullName = resultSet.getString("user_fullName");
        final String country  = resultSet.getString("user_country");
        final Locale locale   = new Locale(resultSet.getString("user_locale"));

        final User.Builder builder = new User.Builder(username, email, password, salt);
        builder.fullName(fullName)
               .country(country)
               .locale(locale);

        return new User(builder);
    }
}
