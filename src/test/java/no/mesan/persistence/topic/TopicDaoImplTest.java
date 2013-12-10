package no.mesan.persistence.topic;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
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
    private static Topic HESTER_ER_SKUMLE;
    private static User hestemann;

    @BeforeClass
    public static void beforeClass() throws Exception {
        hestemann = mock(User.class);
        when(hestemann.getUsername()).thenReturn("hestemann");
        HESTER_ER_FINE   = new Topic(1, "Hester er fine"  , hestemann, new Date(0));
        HESTER_ER_SKUMLE = new Topic(3, "Hester er skumle", hestemann, new Date(20));

        final TopicRowMapper topicRowMapper = new TopicRowMapper();
        final Map<String, User> userCache = new HashMap<>();
        userCache.put("hestemann", hestemann);
        Whitebox.setInternalState(topicRowMapper, "userCache", userCache);

        final Properties sql        = new PropertiesProvider().createSqlProperties();
        final DataSource dataSource = MockDatabaseUtility.getMockDataSource();
        Whitebox.setInternalState(topicDao, "sql",            sql);
        Whitebox.setInternalState(topicDao, "jdbcTemplate",   new JdbcTemplate(dataSource));
        Whitebox.setInternalState(topicDao, "topicRowMapper", topicRowMapper);
    }

    @Before
    public void before() throws Exception {
        MockDatabaseUtility.createDataSet(DATA_SET_TOPICS);
    }

    private void topicsAreEqual(final Topic expected, final Topic actual) {
        assertEquals(expected.getTitle(), actual.getTitle());
        assertEquals(expected.getCreatedBy().getUsername(), actual.getCreatedBy().getUsername());
        assertEquals(expected.getCreatedTime(), actual.getCreatedTime());
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

    @Test
    public void getTopicByCreatorShouldReturnHestemannsTwoTopicsWhenInputIsHestemann() {
        final List<Topic> topics = topicDao.getTopicsByCreator(hestemann);
        topicsAreEqual(topics.get(0), HESTER_ER_SKUMLE);
        topicsAreEqual(topics.get(1), HESTER_ER_FINE);
    }

    @Test
    public void getTopicByCreatorShouldReturnAnEmptyArrayListWhenUserHasNoTopics() {
        final User userWithNoTopics = mock(User.class);
        when(userWithNoTopics.getUsername()).thenReturn("userWithNoTopics");
        final List<Topic> topics = topicDao.getTopicsByCreator(userWithNoTopics);
        assertEquals(0, topics.size());
    }

    @Test
    public void createTopicShouldCreateANewTopicInTheDatabase() {
        final String title     = "Hester er kule";
        final Date  createdTime = new Date();
        final Topic newTopic   = new Topic(4, title, hestemann, createdTime);
        topicDao.createTopic(newTopic);

        final Topic newTopicFromDatabase = topicDao.getTopicByTitle(title);
        topicsAreEqual(newTopic, newTopicFromDatabase);
    }

    @Test
    public void updateTopicShouldUpdateExistingTopicWithNewValues() {
        final Topic  updatedTopic = new Topic(HESTER_ER_FINE.getId(), HESTER_ER_FINE.getTitle(),
                HESTER_ER_FINE.getCreatedBy(), HESTER_ER_FINE.getCreatedTime());
        final String newTitle = "Hester er stygge";
        updatedTopic.setTitle(newTitle);
        topicDao.updateTopic(updatedTopic);

        final Topic updatedTopicFromDatabase = topicDao.getTopicByTitle(newTitle);
        topicsAreEqual(updatedTopic, updatedTopicFromDatabase);
    }
}
