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
    public static final String DATABASE_JNDI        = "database_jndi";

    public static final String CREATE_USER          = "create_user";
    public static final String UPDATE_USER          = "update_user";
    public static final String GET_USER_BY_USERNAME = "get_user_by_username";

    public static final String GET_USER_BY_EMAIL    = "get_user_by_email";
    public static final String GET_USERS            = "get_users";
    public static final String GET_USER_GROUPS      = "get_user_groups";

    public static final String GET_COUNTRIES       = "get_countries";

    public static final String GET_COUNTRY_BY_CODE = "get_country_by_code";
    public static final String GET_COUNTRY_BY_NAME = "get_country_by_name";

    public static final String CREATE_TOPIC         = "create_topic";
    public static final String UPDATE_TOPIC         = "update_topic";
    public static final String GET_TOPIC_BY_TITLE   = "get_topic_by_title";
    public static final String GET_TOPIC_BY_CREATOR = "get_topic_by_creator";
    
    public static final String CREATE_POST          = "create_post";
    public static final String UPDATE_POST          = "update_post";
    public static final String GET_POST_BY_ID       = "get_post_by_id";

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
