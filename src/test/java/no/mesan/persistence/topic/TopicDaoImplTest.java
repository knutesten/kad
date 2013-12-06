package no.mesan.persistence.topic;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.sql.DataSource;

import no.mesan.model.Topic;
import no.mesan.model.User;
import no.mesan.persistence.MockDatabaseUtility;
import no.mesan.properties.PropertiesProvider;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.powermock.reflect.Whitebox;
import org.springframework.jdbc.core.JdbcTemplate;

import static no.mesan.persistence.SqlAndDataSetFileNames.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * TODO
 *
 * @author Knut Esten Melandsø Nekså
 */
public class TopicDaoImplTest {
    private static final TopicDao topicDao = new TopicDaoImpl();
    private static Topic HESTER_ER_FINE;

    @BeforeClass
    public static void beforeClass() throws Exception {
        MockDatabaseUtility.executeScript(SQL_FORUM);

        final User hestemann = mock(User.class);
        when(hestemann.getUsername()).thenReturn("hestemann");
        HESTER_ER_FINE   = new Topic("Hester er fine"  , hestemann, new Date(0));

        final Properties sql        = new PropertiesProvider().createSqlProperties();
        final DataSource dataSource = MockDatabaseUtility.getMockDataSource();

        final TopicRowMapper topicRowMapper = new TopicRowMapper();
        final Map<String, User> userCache = new HashMap<>();
        userCache.put("hestemann", hestemann);
        Whitebox.setInternalState(topicRowMapper, "userCache", userCache);

        Whitebox.setInternalState(topicDao, "sql",            sql);
        Whitebox.setInternalState(topicDao, "jdbcTemplate",   new JdbcTemplate(dataSource));
        Whitebox.setInternalState(topicDao, "topicRowMapper", topicRowMapper);
    }

    @Before
    public void before() throws Exception {
        MockDatabaseUtility.createDataSet(DATA_SET_TOPIC);
    }

    @Test
    public void getTopicByTitleShouldReturnHesterErFineTopicWhenInputIsHesterErFine() {
        final Topic hesterErFine = topicDao.getTopicByTitle("Hester er fine");
        topicsAreEqual(HESTER_ER_FINE, hesterErFine);
    }

    @Test
    public void getTopicByTitleShouldReturnNullWhenTheTopicDoesNotExist() {
        final Topic noTopic = topicDao.getTopicByTitle("høbbedøbb");
        assertNull(noTopic);
    }

    private void topicsAreEqual(final Topic expected, final Topic actual) {
        assertEquals(expected.getTitle(), actual.getTitle());
        assertEquals(expected.getCreatedBy().getUsername(), actual.getCreatedBy().getUsername());
        assertEquals(expected.getCreatedTime(), actual.getCreatedTime());
    }
}
