package no.mesan.persistence;

/**
 * TODO
 *
 * @author Knut Esten Melandsø Nekså
 */
public class SqlAndDataSetFileNames {
    private static final String SQL_PREFIX      = "database/create_tables/";
    public static final String SQL_COUNTRIES    = SQL_PREFIX + "create_countries_tables.sql";
    public static final String SQL_USERS        = SQL_PREFIX + "create_user_tables.sql";
    public static final String SQL_FORUM        = SQL_PREFIX + "create_forum_tables.sql";
    public static final String SQL_CATEGORIES   = SQL_PREFIX + "create_categories_tables.sql";

    private static final String DATA_SET_PREFIX = "database/test_data_sets/";
    public static final String DATA_SET_COUNTRIES          = DATA_SET_PREFIX + "countries.xml";
    public static final String DATA_SET_TOPICS             = DATA_SET_PREFIX + "topics.xml";
    public static final String DATA_SET_USERS              = DATA_SET_PREFIX + "users.xml";
    public static final String DATA_SET_USER_IN_USER_GROUP = DATA_SET_PREFIX + "userInUserGroup.xml";
    public static final String DATA_SET_USER_GROUPS        = DATA_SET_PREFIX + "userGroups.xml";
    public static final String DATA_SET_POSTS              = DATA_SET_PREFIX + "posts.xml";
    public static final String DATA_SET_POST_IN_TOPIC      = DATA_SET_PREFIX + "postInTopic.xml";
    public static final String DATA_SET_CATEGOIRES         = DATA_SET_PREFIX + "categories.xml";
}
