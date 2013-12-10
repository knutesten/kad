package no.mesan.persistence.user;

import static no.mesan.properties.PropertiesProvider.*;

import java.util.List;
import java.util.Properties;

import javax.inject.Inject;

import no.mesan.model.User;
import no.mesan.properties.Sql;

import org.jboss.security.SimplePrincipal;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * TODO
 *
 * @author Knut Esten Melandsø Nekså
 * @author Anders Grotthing Moe
 */
public class UserDaoImpl implements UserDao {
    @Inject @Sql
    private Properties sql;
    @Inject
    private JdbcTemplate jdbcTemplate;
    @Inject
    private UserRowMapper userRowMapper;
    @Inject
    private UserGroupRowMapper userGroupRowMapper;

    @Override
    public void createUser(final User user) {
        jdbcTemplate.update(sql.getProperty(CREATE_USER), user.getUsername(),
                                                          user.getEmail(),
                                                          user.getHash(),
                                                          user.getSalt(),
                                                          user.getFullName(),
                                                          getCountryCode(user),
                                                          getLocaleString(user));
    }

    @Override
    public void updateUser(final User user) {
        jdbcTemplate.update(sql.getProperty(UPDATE_USER), user.getEmail(),
                                                          user.getHash(),
                                                          user.getSalt(),
                                                          user.getFullName(),
                                                          getCountryCode(user),
                                                          getLocaleString(user),
                                                          user.getUsername());
    }

    @Override
    public User getUserById(final int id) {
        try {
            return jdbcTemplate.queryForObject(sql.getProperty(GET_USER_BY_ID), userRowMapper, id);
        } catch (EmptyResultDataAccessException erda) {
            return null;
        }
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

    private String getLocaleString(final User user) {
        if (user.getLocale() != null) return user.getLocale().toString();
        else return null;
    }

    private String getCountryCode(final User user) {
        if (user.getCountry() != null) return user.getCountry().getCode();
        else return null;
    }
}
