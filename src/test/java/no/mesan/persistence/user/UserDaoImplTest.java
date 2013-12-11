package no.mesan.persistence.user;

import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

import javax.sql.DataSource;

import no.mesan.model.Country;
import no.mesan.model.User;
import no.mesan.persistence.MockDatabaseUtility;
import no.mesan.properties.PropertiesProvider;

import org.jboss.security.SimplePrincipal;
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
public class UserDaoImplTest {
    private static final UserDao userDao = new UserDaoImpl();
    private static User hestemann;
    private static User grisemann;
    private static User testmann;

    @BeforeClass
    public static void beforeClass() throws Exception {
        final Properties sql        = new PropertiesProvider().createSqlProperties();
        final DataSource dataSource = MockDatabaseUtility.getMockDataSource();

        @SuppressWarnings("unchecked")
        final Map<String, Country> countryCache = mock(Map.class);
        when(countryCache.containsKey(anyString())).thenReturn(true);
        final UserRowMapper userRowMapper = new UserRowMapper();
        Whitebox.setInternalState(userRowMapper, "countryCache", countryCache);

        Whitebox.setInternalState(userDao, "sql",                sql);
        Whitebox.setInternalState(userDao, "jdbcTemplate",       new JdbcTemplate(dataSource));
        Whitebox.setInternalState(userDao, "userRowMapper",      userRowMapper);
        Whitebox.setInternalState(userDao, "userGroupRowMapper", new UserGroupRowMapper());

        initializeTestUsers();
    }

    private static void initializeTestUsers() {
        final User.Builder hestemannBuilder = new User.Builder("hestemann", "hest@hest.no", "pass1", "salt1");
        hestemannBuilder.fullName("Hest Hestesen").locale(new Locale("no", "NO"));
        hestemann = new User(hestemannBuilder);
        final User.Builder grisemannBuilder = new User.Builder("grisemann", "gris@gris.no", "pass2", "salt2");
        grisemannBuilder.fullName("Gris Grisson").locale(new Locale("en", "GB"));
        grisemann = new User(grisemannBuilder);
        final User.Builder testmannBuilder = new User.Builder("testmann", "test@testesen.no", "pass3", "salt3");
        testmannBuilder.fullName("").locale(new Locale("en", "GB"));
        testmann = new User(testmannBuilder);
    }

    @Before
    public void before() throws Exception {
        MockDatabaseUtility.createDataSet(DATA_SET_USERS);
        MockDatabaseUtility.createDataSet(DATA_SET_USER_GROUPS);
        MockDatabaseUtility.createDataSet(DATA_SET_USER_IN_USER_GROUP);
    }

    @Test
    public void createUserShouldCreateNewUserInDatabase() {
        final User.Builder reddhareBuilder = new User.Builder("reddhare", "redd@hare.no", "pass3", "salt3");
        reddhareBuilder.fullName("Hare Hark").locale(Locale.CANADA_FRENCH);
        final User reddhare = new User(reddhareBuilder);
        userDao.createUser(reddhare);
        final User reddhareFromDatabase = userDao.getUserByUsername("reddhare");
        assertUserEquals(reddhare, reddhareFromDatabase);
    }

    @Test
    public void updateUserShouldUpdateTheUserInDatabaseWithUpdatedValues() {
        final User.Builder hestemannBuilder = new User.Builder("hestemann", "hest@hest.no", "pass1", "salt1");
        hestemannBuilder.fullName("Hest Hestesen").locale(new Locale("no", "NO"));
        final User hestemannUpdated = new User(hestemannBuilder);
        hestemannUpdated.setHash("newPass");
        hestemannUpdated.setEmail("email@email.com");
        hestemannUpdated.setFullName("Hest Grisson");
        hestemannUpdated.setLocale(new Locale("en", "US"));
        userDao.updateUser(hestemannUpdated);
        final User updatedHestemannFromDatabase = userDao.getUserByUsername("hestemann");
        assertUserEquals(hestemannUpdated, updatedHestemannFromDatabase);
    }

    @Test
    public void getUserByUsernameShouldReturnHestemannUserWhenInputIsHestemann() {
        final User hestemannFromDatabase = userDao.getUserByUsername("hestemann");
        assertUserEquals(hestemann, hestemannFromDatabase);
    }

    @Test
    public void getUserByEmailShouldReturnHestemannUserWhenInputIsHestemannEmail() {
        final User hestemannFromDatabase = userDao.getUserByEmail("hest@hest.no");
        assertUserEquals(hestemann, hestemannFromDatabase);
    }

    @Test
    public void getUsersShoudReturnAllUsers() {
        final List<User> usersFromDatabase = userDao.getUsers();
        assertUserEquals(hestemann, usersFromDatabase.get(0));
        assertUserEquals(grisemann, usersFromDatabase.get(1));
    }
    
    @Test
    public void addUserToUserGroupShouldAddGrisemannToTheUserUserGroupWhenInputIsGrisemannAndUser() {
        userDao.addUserToUserGroup(grisemann, "user");
        final List<SimplePrincipal> userGroups = userDao.getUserGroups("grisemann");
        final SimplePrincipal user = new SimplePrincipal("user");
        assertEquals(user, userGroups.get(0));
    }
    
    @Test
    public void removeUserFromUserGroupShouldRemoveTestmannFromTheAdminUserGroupWhenInputIsTestmannAndAdmin() {
        userDao.removeUserFromUserGroup(testmann, "admin");
        final List<SimplePrincipal> emptyList = Collections.emptyList();
        final List<SimplePrincipal> noUserGroups = userDao.getUserGroups("testmann");
        assertEquals(emptyList, noUserGroups);
    }

    @Test
    public void getUserGroupIdByNameShouldReturn1WhenInputIsUser() {
        final int expectedUserGroupId = 1;
        final int userGroupId = userDao.getUserGroupIdByName("user");
        assertEquals(expectedUserGroupId, userGroupId);
    }
    
    @Test
    public void getUserGroupIdByNameShouldReturnNullWhenInputIsAUserGroupThatDoesNotExist() {
        final Integer userGroupId = userDao.getUserGroupIdByName("ausergroupthatdoesnotexist");
        assertNull(userGroupId);
    }
    
    @Test
    public void getUserGroupsShouldReturnAllAdminAndUserUserGroupsForUserHestemann() {
        final List<SimplePrincipal> userGroups = userDao.getUserGroups("hestemann");
        final SimplePrincipal user  = new SimplePrincipal("user");
        final SimplePrincipal admin = new SimplePrincipal("admin");
        assertEquals(user,  userGroups.get(0));
        assertEquals(admin, userGroups.get(1));
    }

    private void assertUserEquals(final User expected, final User actual) {
        assertEquals(expected.getUsername(), actual.getUsername());
        assertEquals(expected.getEmail(),    actual.getEmail());
        assertEquals(expected.getHash(),     actual.getHash());
        assertEquals(expected.getSalt(),     actual.getSalt());
        assertEquals(expected.getFullName(), actual.getFullName());
        assertEquals(expected.getLocale(),   actual.getLocale());
    }
}
