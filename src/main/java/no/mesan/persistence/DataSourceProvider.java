package no.mesan.persistence;

import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import no.mesan.properties.DatabaseProperties;

import static no.mesan.properties.PropertiesProvider.*;

/**
 * TODO
 *
 * @author Knut Esten Melandsø Nekså
 */
public class DataSourceProvider {
    @Inject
    @DatabaseProperties
    private Properties dbProperties;
    private DataSource dataSource;

    @PostConstruct
    public void initialize() {
        try {
            final String jndi = dbProperties.getProperty(DATABASE_JNDI);
            dataSource = (DataSource) new InitialContext().lookup(jndi);
        } catch (NamingException ne) {
            ne.printStackTrace();
        }
    }

    @Produces
    public DataSource getDataSource() {
        return dataSource;
    }
}
