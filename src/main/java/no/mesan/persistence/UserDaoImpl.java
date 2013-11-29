package no.mesan.persistence;

import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.sql.DataSource;

import no.mesan.authentication.Authentication;
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
    @Inject
    private Authentication authentication;
    @Inject @Sql
    private Properties sql;
    @Inject
    private DataSource dataSource;

    private JdbcTemplate jdbcTemplate;

    @PostConstruct
    private void initialize() {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public void createUser(final User user) {
        jdbcTemplate.update(sql.getProperty(CREATE_USER), user.getUsername(),
                                                          user.getEmail(),
                                                          user.getPassword(),
                                                          user.getSalt(),
                                                          user.getLocale());
    }

    public void test() {
        jdbcTemplate.getMaxRows();
    }
}
