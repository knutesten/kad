package no.mesan.persistence.user;

import java.util.List;
import java.util.Properties;

import javax.inject.Inject;
import javax.sql.DataSource;

import no.mesan.model.Country;
import no.mesan.model.User;
import no.mesan.persistence.country.CountryDao;
import no.mesan.properties.Sql;

import org.jboss.security.SimplePrincipal;
import org.springframework.dao.EmptyResultDataAccessException;
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
    @Inject
    private JdbcTemplate jdbcTemplate;
    @Inject
    private CountryDao countryDao;
    @Inject
    private UserRowMapper userRowMapper;
    @Inject
    private UserGroupRowMapper userGroupRowMapper;

    @Override
    public void createUser(final User user) {
        String localeString = null;
        String countryCode = null;
        if (user.getLocale() != null)
            localeString = user.getLocale().toLanguageTag();
        if (user.getCountry() != null)
            countryCode = user.getCountry().getCode();
        jdbcTemplate.update(sql.getProperty(CREATE_USER), user.getUsername(),
                                                          user.getEmail(),
                                                          user.getHash(),
                                                          user.getSalt(),
                                                          user.getFullName(),
                                                          countryCode,
                                                          localeString);
    }

    @Override
    public void updateUser(final User user) {
        String localeString = null;
        if (user.getLocale() != null)
            localeString = user.getLocale().toLanguageTag();
        jdbcTemplate.update(sql.getProperty(UPDATE_USER), user.getEmail(),
                                                          user.getHash(),
                                                          user.getSalt(),
                                                          user.getFullName(),
                                                          user.getCountry().getCode(),
                                                          localeString,
                                                          user.getUsername());
    }

    @Override
    public User getUserByUsername(final String username) {
        try {
            return jdbcTemplate.queryForObject(sql.getProperty(GET_USER_BY_USERNAME), userRowMapper, username);
        } catch (EmptyResultDataAccessException erda) {
            return null;
        }
    }

    @Override
    public User getUserByEmail(final String email) {
        try {
            return jdbcTemplate.queryForObject(sql.getProperty(GET_USER_BY_EMAIL), userRowMapper, email);
        } catch (EmptyResultDataAccessException erda) {
            return null;
        }
    }

    @Override
    public List<User> getUsers() {
        return jdbcTemplate.query(sql.getProperty(GET_USERS), userRowMapper);
    }

    @Override
    public List<SimplePrincipal> getUserGroups(final String username) {
        return jdbcTemplate.query(sql.getProperty(GET_USER_GROUPS), userGroupRowMapper, username);
    }
}