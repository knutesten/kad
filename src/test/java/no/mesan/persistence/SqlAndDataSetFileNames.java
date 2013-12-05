package no.mesan.persistence;

/**
 * TODO
 *
 * @author Knut Esten Melandsø Nekså
 */
public class SqlAndDataSetFileNames {
    private static final String SQL_PREFIX = "database/create_tables/";
    public static final String SQL_COUNTRIES = SQL_PREFIX + "create_countries_tables.sql";
    public static final String SQL_FORUM     = SQL_PREFIX + "create_forum_tables.sql";

    private static final String DATA_SET_PREFIX = "database/test_data_sets/";
    public static final String DATA_SET_COUNTRIES = DATA_SET_PREFIX + "countries.xml";
    public static final String DATA_SET_TOPIC     = DATA_SET_PREFIX + "topic.xml";
}
