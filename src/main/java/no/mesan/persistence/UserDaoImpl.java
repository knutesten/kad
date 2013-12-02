package no.mesan.persistence;

import java.util.List;
import java.util.Properties;

import javax.inject.Inject;
import javax.sql.DataSource;

import no.mesan.model.User;
import no.mesan.properties.Sql;

import org.springframework.jdbc.core.JdbcTemplate;

import static no.mesan.properties.PropertiesProvider.*;

/**
 * TODO
 *
 * @author Knut Esten Melandsø Nekså
 */
public class UserDaoImpl implements UserDao {
    @Inject @Sql
    private Properties sql;
    private final JdbcTemplate jdbcTemplate;

    @Inject
    public UserDaoImpl(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public void createUser(final User user) {
        updateUser(user);
    }

    @Override
    public User getUserByUsername(final String username) {
        return jdbcTemplate.queryForObject(sql.getProperty(GET_USER_BY_USERNAME), new UserRowMapper(), username);
    }

    @Override
    public User getUserByEmail(final String email) {
        return jdbcTemplate.queryForObject(sql.getProperty(GET_USER_BY_EMAIL), new UserRowMapper(), email);
    }

    @Override
    public List<User> getUsers() {
        return jdbcTemplate.query(sql.getProperty(GET_USERS), new UserRowMapper());
    }

    @Override
    public void updateUser(final User user) {
        String localeString = null;
        if (user.getLocale() != null)
            localeString = user.getLocale().toLanguageTag();
        jdbcTemplate.update(sql.getProperty(CREATE_UPDATE_USER), user.getUsername(),
                                                          user.getEmail(),
                                                          user.getPassword(),
                                                          user.getSalt(),
                                                          user.getFullName(),
                                                          user.getCountry(),
                                                          localeString);
    }
}
