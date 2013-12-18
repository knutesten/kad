package no.mesan.persistence.topic;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.sql.DataSource;

import no.mesan.model.Category;
import no.mesan.model.Post;
import no.mesan.model.Topic;
import no.mesan.model.User;
import no.mesan.persistence.MockDatabaseUtility;
import no.mesan.persistence.post.PostDao;
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
//    private static Topic HESTER_ER_STYGGE;
    private static Topic TEST_1;
    private static Topic TEST_2;
    private static Topic TEST_3;
    private static Topic TEST_4;
    private static User hestemann;
    private static User grisemann;
    private static User testmann;
    private static Post post;

    @BeforeClass
    public static void beforeClass() throws Exception {
        hestemann = mock(User.class);
        when(hestemann.getUsername()).thenReturn("hestemann");
        when(hestemann.getId()).thenReturn(1);
        grisemann = mock(User.class);
        when(grisemann.getUsername()).thenReturn("grisemann");
        when(grisemann.getId()).thenReturn(2);
        testmann = mock(User.class);
        when(testmann.getUsername()).thenReturn("testmann");
        when(testmann.getId()).thenReturn(3);

        HESTER_ER_FINE   = new Topic(1, "Hester er fine"  , hestemann, new Date(0));
//        HESTER_ER_STYGGE = new Topic(2, "Hester er stygge", grisemann, new Date(10), null);
        HESTER_ER_SKUMLE = new Topic(3, "Hester er skumle", hestemann, new Date(20));
        TEST_1           = new Topic(4, "test1", hestemann, new Date(30));
        TEST_2           = new Topic(5, "test2", hestemann, new Date(40));
        TEST_3           = new Topic(6, "test3", hestemann, new Date(50));
        TEST_4           = new Topic(7, "test4", hestemann, new Date(60));

        post = new Post(1, testmann, new Date(1), testmann, new Date(15), "testpost med edit");
        
        final TopicRowMapper topicRowMapper = new TopicRowMapper();
        final Map<Integer, User> userCache = new HashMap<>();
        userCache.put(1, hestemann);
        userCache.put(2, grisemann);
        userCache.put(3, testmann);
        Whitebox.setInternalState(topicRowMapper, "userCache", userCache);
        
        final PostDao postDao = mock(PostDao.class);
        when(postDao.getLastPostByTopic(HESTER_ER_FINE)).thenReturn(post);
        Whitebox.setInternalState(topicRowMapper, "postDao", postDao);

        final Properties sql        = new PropertiesProvider().createSqlProperties();
        final DataSource dataSource = MockDatabaseUtility.getMockDataSource();
        Whitebox.setInternalState(topicDao, "sql",            sql);
        Whitebox.setInternalState(topicDao, "jdbcTemplate",   new JdbcTemplate(dataSource));
        Whitebox.setInternalState(topicDao, "topicRowMapper", topicRowMapper);
    }

    @Before
    public void before() throws Exception {
        MockDatabaseUtility.createDataSet(DATA_SET_CATEGOIRES);
        MockDatabaseUtility.createDataSet(DATA_SET_TOPICS);
        MockDatabaseUtility.createDataSet(DATA_SET_POSTS);
        MockDatabaseUtility.createDataSet(DATA_SET_POST_IN_TOPIC);
    }

    private void topicsAreEqual(final Topic expected, final Topic actual) {
        assertEquals(expected.getTitle(), actual.getTitle());
        assertEquals(expected.getCreatedBy().getUsername(), actual.getCreatedBy().getUsername());
        assertEquals(expected.getCreatedTime(), actual.getCreatedTime());
    }

    @Test
    public void getTopicByTopicIdShouldReturnHesterErFineTopicWhenInputIs1() {
        final Topic hesterErFine = topicDao.getTopicById(1);
        topicsAreEqual(HESTER_ER_FINE, hesterErFine);
    }

    @Test
    public void getTopicByTopicIdShouldReturnNullWhenTheTopicDoesNotExist() {
        final Topic noTopic = topicDao.getTopicById(123123);
        assertNull(noTopic);
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
    public void getTopicByCreatorShouldReturnHestemannsSixTopicsWhenInputIsHestemann() {
        final List<Topic> topics = topicDao.getTopicsByCreator(hestemann);
        topicsAreEqual(TEST_4, topics.get(0));
        topicsAreEqual(TEST_3, topics.get(1));
        topicsAreEqual(TEST_2, topics.get(2));
        topicsAreEqual(TEST_1, topics.get(3));
        topicsAreEqual(HESTER_ER_SKUMLE, topics.get(4));
        topicsAreEqual(HESTER_ER_FINE, topics.get(5));
    }

    @Test
    public void getTopicByCreatorShouldReturnAnEmptyArrayListWhenUserHasNoTopics() {
        final User userWithNoTopics = mock(User.class);
        when(userWithNoTopics.getUsername()).thenReturn("userWithNoTopics");
        final List<Topic> topics = topicDao.getTopicsByCreator(userWithNoTopics);
        assertEquals(0, topics.size());
    }

    @Test
    public void getTopicsByCategoryShouldReturnAListWithTheCorrectTopicsBasedOnTheLimit() {
        final Category category = mock(Category.class);
        when(category.getId()).thenReturn(1);
        final int pageSize = 2;
        final int expectedNumberOfTopicsOnPageThatIsNotFull = 1;
        int first = 0;
        List<Topic> topics = topicDao.getLimitedTopicsByCategory(category, first, pageSize);
        topicsAreEqual(TEST_4, topics.get(0));
        topicsAreEqual(TEST_3, topics.get(1));
        assertEquals(pageSize, topics.size());

        first = 2;
        topics = topicDao.getLimitedTopicsByCategory(category, first, pageSize);
        topicsAreEqual(TEST_2, topics.get(0));
        topicsAreEqual(TEST_1, topics.get(1));
        assertEquals(pageSize, topics.size());

        first = 6;
        topics = topicDao.getLimitedTopicsByCategory(category, first, pageSize);
        topicsAreEqual(HESTER_ER_FINE, topics.get(0));
        assertEquals(expectedNumberOfTopicsOnPageThatIsNotFull, topics.size());
    }

    @Test
    public void getLimitedTopicsByCategoryShouldReturnAnEmptyListWhenThereAreNoTopicsOnTheGivenPage() {
        final Category category = mock(Category.class);
        when(category.getId()).thenReturn(1);
        final int userLimitedNumberOfTopics = 2;
        final int pageNumber = 123123123;
        final List<Topic> emptyPostList = Collections.emptyList();
        final List<Topic> topics = topicDao.getLimitedTopicsByCategory(category, pageNumber, userLimitedNumberOfTopics);
        assertEquals(emptyPostList, topics);
    }

    @Test
    public void createTopicShouldCreateANewTopicInTheDatabase() {
        final Category category = mock(Category.class);
        when(category.getId()).thenReturn(1);
        final String title     = "Hester er kule";
        final Topic newTopic   = new Topic(title, hestemann);
        topicDao.createTopic(newTopic, category);

        final Topic newTopicFromDatabase = topicDao.getTopicByTitle(title);
        topicsAreEqual(newTopic, newTopicFromDatabase);
    }

    @Test
    public void updateTopicShouldUpdateExistingTopicWithNewValues() {
        final Topic newTopic = new Topic(HESTER_ER_FINE.getId(), HESTER_ER_FINE.getTitle(),
                HESTER_ER_FINE.getCreatedBy(), HESTER_ER_FINE.getCreatedTime());
        final String newTitle = "Hester er rare";
        newTopic.setTitle(newTitle);
        topicDao.updateTopic(newTopic);

        final Topic updatedTopicFromDatabase = topicDao.getTopicById(HESTER_ER_FINE.getId());
        topicsAreEqual(newTopic, updatedTopicFromDatabase);
    }
    
    @Test
    public void getNumberOfTopicsInCategoryShouldReturnSevenWhenThereAreSevenTopicsInACategory() {
        final Category category = new Category(1, "Hester", HESTER_ER_FINE);
        final int expectedNumberOfTopics = 7;
        final int numberOfTopicsInHesterCategoryFromDatabase = topicDao.getNumberOfTopicsInCategory(category);
        assertEquals(expectedNumberOfTopics, numberOfTopicsInHesterCategoryFromDatabase);
    }

    @Test
    public void getNumberOfTopicsInCategoryShouldReturn0IfTheCategoryDoesNotExist() {
        final Category category = new Category(13131313, "No Category", null);
        final int expectedNumberOfTopics = 0;
        final int numberOfTopicsInCategoryFromDatabase = topicDao.getNumberOfTopicsInCategory(category);
        assertEquals(expectedNumberOfTopics, numberOfTopicsInCategoryFromDatabase);
    }
}
