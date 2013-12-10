package no.mesan.persistence.post;

import static no.mesan.persistence.SqlAndDataSetFileNames.*;
import static org.junit.Assert.*;

import java.util.Date;
import java.util.HashMap;
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
    private static Post TEST_POST;
    private static Post TEST_POST_NO_EDIT;
    private static Post TEST_POST_WITH_DIFFERENT_EDIT;
    private static User testmann;
    private static User hestemann;
    
    @BeforeClass
    public static void beforeClass() throws Exception {
        MockDatabaseUtility.executeScript(SQL_DROP_TABLES);
        MockDatabaseUtility.executeScript(SQL_COUNTRIES);
        MockDatabaseUtility.executeScript(SQL_USERS);
        MockDatabaseUtility.executeScript(SQL_FORUM);

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
        TEST_POST = new Post(1, testmann, new Date(0), testmann, new Date(15), "testpost med edit");
        TEST_POST_NO_EDIT = new Post(2, testmann, new Date(10), null, null, "Dette er en testpost uten edit");
        TEST_POST_WITH_DIFFERENT_EDIT= new Post(3, testmann, new Date(20), hestemann, new Date(25), "Dette er en post edited av en annen bruker");
    }
    
    @Before
    public void before() throws Exception {
        MockDatabaseUtility.createDataSet(DATA_SET_COUNTRIES);
        MockDatabaseUtility.createDataSet(DATA_SET_USERS);
        MockDatabaseUtility.createDataSet(DATA_SET_POSTS);
    }

    @Test
    public void getPostByIdShouldReturnPostWithId1WhenInputIs1() {
        final Post postWithId1 = postDao.getPostById(1);
        postsAreEqual(TEST_POST, postWithId1);
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
        final Post updatedPost = new Post(TEST_POST_NO_EDIT.getPostId(), 
                                          TEST_POST_NO_EDIT.getCreatedBy(), 
                                          TEST_POST_NO_EDIT.getCreatedTime(),
                                          TEST_POST_NO_EDIT.getLastEditedBy(),
                                          TEST_POST_NO_EDIT.getLastEditedTime(),
                                          TEST_POST_NO_EDIT.getContent());
        updatedPost.setLastEditedBy(testmann);
        updatedPost.setLastEditedTime(new Date());
        updatedPost.setContent("Nå har den en edit :)");
        postDao.updatePost(updatedPost);
        
        final Post updatedPostFromDatabase = postDao.getPostById(updatedPost.getPostId());
        postsAreEqual(updatedPost, updatedPostFromDatabase);
    }
    
    @Test
    public void aPostEditedByAnotherUserShouldShowAsEditedByAnotherUser() {
        final Post postEditedByAnotherUser = postDao.getPostById(TEST_POST_WITH_DIFFERENT_EDIT.getPostId());
        assertNotEquals(postEditedByAnotherUser.getCreatedBy(), postEditedByAnotherUser.getLastEditedBy());
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
