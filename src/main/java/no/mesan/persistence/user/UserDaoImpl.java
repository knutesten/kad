package no.mesan.persistence.user;

import static no.mesan.properties.PropertiesProvider.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

import javax.inject.Inject;

import no.mesan.model.User;
import no.mesan.properties.Sql;

import org.jboss.security.SimplePrincipal;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

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
        final KeyHolder generatedKeyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(
            new PreparedStatementCreator() {
                @Override
                public PreparedStatement createPreparedStatement(final Connection connection) throws SQLException {
                    final PreparedStatement preparedStatement =
                            connection.prepareStatement(sql.getProperty(CREATE_USER), new String[]{"user_id"});
                    preparedStatement.setString(1, user.getUsername());
                    preparedStatement.setString(2, user.getEmail());
                    preparedStatement.setString(3, user.getHash());
                    preparedStatement.setString(4, user.getSalt());
                    preparedStatement.setString(5, user.getFullName());
                    preparedStatement.setString(6, getCountryCode(user));
                    preparedStatement.setString(7, getLocaleString(user));
                    return preparedStatement;
                }
            },
            generatedKeyHolder);

        user.setId(generatedKeyHolder.getKey().intValue());
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
    public void addUserToUserGroup(final User user, final String userGroupName) {
        jdbcTemplate.update(sql.getProperty(ADD_USER_TO_USERGROUP), user.getUsername(), userGroupName);
    }

    @Override
    public void removeUserFromUserGroup(final User user, final String userGroupName) {
        jdbcTemplate.update(sql.getProperty(REMOVE_USER_FROM_USERGROUP), user.getUsername(), userGroupName);
    }

    @Override
    public Integer getUserGroupIdByName(final String userGroupName) {
        try {
            return jdbcTemplate.queryForObject(sql.getProperty(GET_USER_GROUP_ID_BY_NAME),
                                               Integer.class,
                                               userGroupName);
        } catch (EmptyResultDataAccessException erdae) {
            return null;
        }
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
