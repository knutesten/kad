package no.mesan.properties;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.ResourceBundle;

import javax.enterprise.inject.Produces;

/**
 * TODO
 *
 * @author Knut Esten Melandsø Nekså
 */
public class PropertiesProvider {
    public static final String DATABASE_JNDI     = "database_jndi";
    public static final String DATABASE_USERNAME = "database_username";
    public static final String DATABASE_PASSWORD = "database_password";

    @Produces @Sql
    public Properties createSqlProperties() throws IOException {
        return getProperties("sql.properties");
    }

    @Produces @DatabaseProperties
    public Properties createDatabaseProperties() throws IOException {
        return getProperties("database.properties");
    }

    @Produces @ValidationMessageBundle
    public java.util.ResourceBundle createValidationResourceBundle() {
        return ResourceBundle.getBundle("ValidationMessages");
    }

    @Produces @I18nBundle
    public ResourceBundle createI18nResourceBundle() {
        return ResourceBundle.getBundle("i18n");
    }

    private Properties getProperties(final String file) throws IOException {
        final InputStream inputStream = getClass().getClassLoader().getResourceAsStream(file);
        final Properties properties = new Properties();
        properties.load(inputStream);
        return properties;
    }
}
