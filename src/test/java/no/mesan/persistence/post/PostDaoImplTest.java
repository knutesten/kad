package no.mesan.persistence.post;

import static no.mesan.persistence.SqlAndDataSetFileNames.*;
import static org.junit.Assert.*;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

import javax.sql.DataSource;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.powermock.reflect.Whitebox;
import org.springframework.jdbc.core.JdbcTemplate;

import no.mesan.model.Post;
import no.mesan.model.User;
import no.mesan.persistence.MockDatabaseUtility;
import no.mesan.properties.PropertiesProvider;

/**
 * TODO
 *
 * @author Anders Grotthing Moe
 */
public class PostDaoImplTest {
    private static final PostDao postDao = new PostDaoImpl();
    private static Post TEST_POST_ONE;
    private static Post TEST_POST_TWO;
    private static Post TEST_POST_THREE;
    private static Post TEST_POST_FOUR;
    private static Post TEST_POST_FIVE;
    private static Post TEST_POST_SIX;
    private static User testmann;
    private static User hestemann;

    @BeforeClass
    public static void beforeClass() throws Exception {
        final Properties sql = new PropertiesProvider().createSqlProperties();
        final DataSource dataSource = MockDatabaseUtility.getMockDataSource();

        initializeTestUsers();
        initializeTestPosts();

        final Map<String, User> userCache = new HashMap<>();
        userCache.put(null, null);
        userCache.put("hestemann", hestemann);
        userCache.put("testmann", testmann);

        final PostRowMapper postRowMapper = new PostRowMapper();
        Whitebox.setInternalState(postRowMapper, "userCache", userCache);

        Whitebox.setInternalState(postDao, "sql", sql);
        Whitebox.setInternalState(postDao, "jdbcTemplate", new JdbcTemplate(dataSource));
        Whitebox.setInternalState(postDao, "postRowMapper", postRowMapper);

    }

    private static void initializeTestUsers() {
        final User.Builder testmannBuilder = new User.Builder("testmann", "test@testesen.no", "pass3", "salt3");
        testmannBuilder.fullName("").locale(new Locale("en", "GB"));
        testmann = new User(testmannBuilder);
        final User.Builder hestemannBuilder = new User.Builder("hestemann", "hest@hest.no", "pass1", "salt1");
        hestemannBuilder.fullName("Hest Hestesen").locale(new Locale("no", "NO"));
        hestemann = new User(hestemannBuilder);
    }

    private static void initializeTestPosts() {
        TEST_POST_ONE = new Post(1, testmann, new Date(0), testmann, new Date(15), "testpost med edit");
        TEST_POST_TWO = new Post(2, testmann, new Date(10), null, null, "Dette er en testpost uten edit");
        TEST_POST_THREE= new Post(3, testmann, new Date(20), hestemann, new Date(25), "Dette er en post edited av en annen bruker");
        TEST_POST_FOUR = new Post(4, testmann, new Date(30), hestemann, new Date(89), "Whoopdie");
        TEST_POST_FIVE = new Post(5, testmann, new Date(40), hestemann, new Date(78), "Doopdie");
        TEST_POST_SIX = new Post(6, testmann, new Date(50), hestemann, new Date(78), "Doo");
    }

    @Before
    public void before() throws Exception {
        MockDatabaseUtility.createDataSet(DATA_SET_COUNTRIES);
        MockDatabaseUtility.createDataSet(DATA_SET_USERS);
        MockDatabaseUtility.createDataSet(DATA_SET_POSTS);
        MockDatabaseUtility.createDataSet(DATA_SET_POST_IN_TOPIC);
    }

    @Test
    public void getPostByIdShouldReturnPostWithId1WhenInputIs1() {
        final Post postWithId1 = postDao.getPostById(1);
        postsAreEqual(TEST_POST_ONE, postWithId1);
    }

    @Test
    public void getPostByIdShouldReturnNullWhenTheIdDoesNotExist() {
        final Post post = postDao.getPostById(4502);
        assertNull(post);
    }

    @Test
    public void createPostShouldCreateANewPostInTheDatabase() {
        final Date createdTime = new Date();
        final String content = "Dette er en ny post generert av testen";
        final Post newPost = new Post(hestemann, createdTime, content);
        final int newPostId = postDao.createPost(newPost);
        newPost.setPostId(newPostId);

        final Post newPostFromDatabase = postDao.getPostById(newPostId);
        postsAreEqualWithoutEdit(newPost, newPostFromDatabase);
    }

    @Test
    public void updatePostShouldUpdateExistingTopicWithNewValues() {
        final Post updatedPost = new Post(TEST_POST_TWO.getPostId(), 
                                          TEST_POST_TWO.getCreatedBy(), 
                                          TEST_POST_TWO.getCreatedTime(),
                                          TEST_POST_TWO.getLastEditedBy(),
                                          TEST_POST_TWO.getLastEditedTime(),
                                          TEST_POST_TWO.getContent());
        updatedPost.setLastEditedBy(testmann);
        updatedPost.setLastEditedTime(new Date());
        updatedPost.setContent("Nå har den en edit :)");
        postDao.updatePost(updatedPost);

        final Post updatedPostFromDatabase = postDao.getPostById(updatedPost.getPostId());
        postsAreEqual(updatedPost, updatedPostFromDatabase);
    }

    @Test
    public void aPostEditedByAnotherUserShouldShowAsEditedByAnotherUser() {
        final Post postEditedByAnotherUser = postDao.getPostById(TEST_POST_THREE.getPostId());
        assertNotEquals(postEditedByAnotherUser.getCreatedBy(), postEditedByAnotherUser.getLastEditedBy());
    }
    
    @Test
    public void getPostsByTopicIdShouldReturnAllPostsForThatTopic() {
        final int topicId = 1;
        final int numberOfPostsInResult = 5;
        final List<Post> posts = postDao.getPostsByTopicId(topicId);
        postsAreEqual(TEST_POST_ONE, posts.get(0));
        postsAreEqual(TEST_POST_THREE, posts.get(1));
        postsAreEqual(TEST_POST_FOUR, posts.get(2));
        postsAreEqual(TEST_POST_FIVE, posts.get(3));
        postsAreEqual(TEST_POST_SIX, posts.get(4));
        assertEquals(numberOfPostsInResult, posts.size());
    }
    
    @Test
    public void getPostsByTopicIdShouldReturnAnEmptyListWhenTheTopicDoesNotExist() {
        final int topicId = 123123;
        final List<Post> emptyPostList = Collections.emptyList();
        final List<Post> posts = postDao.getPostsByTopicId(topicId);
        assertEquals(emptyPostList, posts);
    }
    
    @Test
    public void getLimitedPostsByTopicIdShouldReturnAListWithTheCorrectPostsBasedOnTheLimit() {
        final int topicId = 1;
        final int userLimitedNumberOfPosts = 2;
        final int expectedNumberOfPostsOnPageThatIsNotFull = 1;
        int pageNumber = 1;
        List<Post> posts = postDao.getLimitedPostsByTopicId(topicId, pageNumber, userLimitedNumberOfPosts);
        postsAreEqual(TEST_POST_ONE, posts.get(0));
        postsAreEqual(TEST_POST_THREE, posts.get(1));
        assertEquals(userLimitedNumberOfPosts, posts.size());
        
        pageNumber = 2;
        posts = postDao.getLimitedPostsByTopicId(topicId, pageNumber, userLimitedNumberOfPosts);
        postsAreEqual(TEST_POST_FOUR, posts.get(0));
        postsAreEqual(TEST_POST_FIVE, posts.get(1));
        assertEquals(userLimitedNumberOfPosts, posts.size());
        
        pageNumber = 3;
        posts = postDao.getLimitedPostsByTopicId(topicId, pageNumber, userLimitedNumberOfPosts);
        postsAreEqual(TEST_POST_SIX, posts.get(0));
        assertEquals(expectedNumberOfPostsOnPageThatIsNotFull, posts.size());
    }

    @Test
    public void getLimitedPostsByTopicIdShouldReturnAnEmptyListWhenThereAreNoPostsOnTheGivenPage() {
        final int topicId = 1;
        final int userLimitedNumberOfPosts = 2;
        final int resultsOnPageNumber = 123123;
        final List<Post> emptyPostList = Collections.emptyList();
        List<Post> posts = postDao.getLimitedPostsByTopicId(topicId, resultsOnPageNumber, userLimitedNumberOfPosts);
        assertEquals(emptyPostList, posts);
    }
    
    @Test
    public void getLimitedPostsByTopicIdShouldReturnAllPostsBelongingToTheTopicIfTheLimitIsLargerThanPostCount() {
            final int topicId = 1;
            final int expectedNumberOfPostsInResult = 5;
            final int userLimitedNumberOfPosts = 20;
            final int resultsOnPageNumber = 1;
            final List<Post> posts = postDao.getLimitedPostsByTopicId(topicId, resultsOnPageNumber, userLimitedNumberOfPosts);
            postsAreEqual(TEST_POST_ONE, posts.get(0));
            postsAreEqual(TEST_POST_THREE, posts.get(1));
            postsAreEqual(TEST_POST_FOUR, posts.get(2));
            postsAreEqual(TEST_POST_FIVE, posts.get(3));
            postsAreEqual(TEST_POST_SIX, posts.get(4));
            assertEquals(expectedNumberOfPostsInResult, posts.size());
    }
    
    private void postsAreEqual(final Post expected, final Post actual) {
        assertEquals(expected.getPostId(), actual.getPostId());
        assertEquals(expected.getCreatedBy().getUsername(), actual.getCreatedBy().getUsername());
        assertEquals(expected.getCreatedTime(), actual.getCreatedTime());
        assertEquals(expected.getLastEditedBy().getUsername(), actual.getLastEditedBy().getUsername());
        assertEquals(expected.getLastEditedTime().getTime(), actual.getLastEditedTime().getTime());
        assertEquals(expected.getContent(), actual.getContent());
    }

    private void postsAreEqualWithoutEdit(final Post expected, final Post actual) {
        assertEquals(expected.getPostId(), actual.getPostId());
        assertEquals(expected.getCreatedBy().getUsername(), actual.getCreatedBy().getUsername());
        assertEquals(expected.getCreatedTime(), actual.getCreatedTime());
        assertEquals(expected.getContent(), actual.getContent());
    }

}
