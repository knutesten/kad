package no.mesan.persistence;

import java.util.Properties;

import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import no.mesan.properties.DatabaseProperties;

import static no.mesan.properties.PropertiesProvider.*;

import org.springframework.jdbc.core.JdbcTemplate;

/**
 * TODO
 *
 * @author Knut Esten Melandsø Nekså
 */
public class JDBCTemplateProvider {
    private DataSource dataSource;

    @Inject
    public JDBCTemplateProvider(final @DatabaseProperties Properties dbProperties) {
        try {
            final String jndi = dbProperties.getProperty(DATABASE_JNDI);
            dataSource = (DataSource) new InitialContext().lookup(jndi);
        } catch (NamingException ne) {
            ne.printStackTrace();
        }
    }

    @Produces
    public JdbcTemplate getJdbcTemplate() {
        return new JdbcTemplate(dataSource);
    }
}
