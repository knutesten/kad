package no.mesan.persistence.user;

import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

import javax.sql.DataSource;

import no.mesan.model.Country;
import no.mesan.model.User;
import no.mesan.model.UserSettings;
import no.mesan.persistence.MockDatabaseUtility;
import no.mesan.properties.PropertiesProvider;

import org.jboss.security.SimplePrincipal;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
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
    private static UserSettings hestemannSettings;

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
        Whitebox.setInternalState(userDao, "userSettingsRowMapper", new UserSettingsRowMapper());

        initializeTestUsers();
    }

    private static void initializeTestUsers() {
        final User.Builder hestemannBuilder = new User.Builder("hestemann", "hest@hest.no", "pass1", "salt1");
        hestemannBuilder.fullName("Hest Hestesen").locale(new Locale("no", "NO")).id(1);
        hestemann = new User(hestemannBuilder);
        hestemannSettings = new UserSettings(1, 10);
        final User.Builder grisemannBuilder = new User.Builder("grisemann", "gris@gris.no", "pass2", "salt2");
        grisemannBuilder.fullName("Gris Grisson").locale(new Locale("en", "GB")).id(2);
        grisemann = new User(grisemannBuilder);
        final User.Builder testmannBuilder = new User.Builder("testmann", "test@testesen.no", "pass3", "salt3");
        testmannBuilder.fullName("").locale(new Locale("en", "GB")).id(3);
        testmann = new User(testmannBuilder);
    }
    
    @Before
    public void before() throws Exception {
        MockDatabaseUtility.createDataSet(DATA_SET_USERS);
        MockDatabaseUtility.createDataSet(DATA_SET_USER_GROUPS);
        MockDatabaseUtility.createDataSet(DATA_SET_USER_IN_USER_GROUP);
        MockDatabaseUtility.createDataSet(DATA_SET_USERSETTINGS);
    }

    @Test
    public void createUserShouldCreateNewUserInDatabase() {
        final User.Builder reddhareBuilder = new User.Builder("reddhare", "redd@hare.no", "pass3", "salt3");
        reddhareBuilder.fullName("Hare Hark").locale(Locale.CANADA_FRENCH);
        final User reddhare = new User(reddhareBuilder);
        userDao.createUser(reddhare);
        final User reddhareFromDatabase = userDao.getUserByUsername("reddhare");
        assertEquals(reddhare, reddhareFromDatabase);
    }

    @Test
    public void updateUserShouldUpdateTheUserInDatabaseWithUpdatedValues() {
        final User.Builder hestemannBuilder = new User.Builder("hestemann", "hest@hest.no", "pass1", "salt1");
        hestemannBuilder.fullName("Hest Hestesen").locale(new Locale("no", "NO")).id(1);
        final User hestemannUpdated = new User(hestemannBuilder);
        hestemannUpdated.setHash("newPass");
        hestemannUpdated.setEmail("email@email.com");
        hestemannUpdated.setFullName("Hest Grisson");
        hestemannUpdated.setLocale(new Locale("en", "US"));
        userDao.updateUser(hestemannUpdated);
        final User updatedHestemannFromDatabase = userDao.getUserByUsername("hestemann");
        assertEquals(hestemannUpdated, updatedHestemannFromDatabase);
        assertNotEquals(updatedHestemannFromDatabase, hestemann);
    }

    @Test
    public void getUserByUsernameShouldReturnHestemannUserWhenInputIsHestemann() {
        final User hestemannFromDatabase = userDao.getUserByUsername("hestemann");
        assertEquals(hestemann, hestemannFromDatabase);
    }

    @Test
    public void getUserByEmailShouldReturnHestemannUserWhenInputIsHestemannEmail() {
        final User hestemannFromDatabase = userDao.getUserByEmail("hest@hest.no");
        assertEquals(hestemann, hestemannFromDatabase);
    }

    @Test
    public void getUserByIdShouldReturnHestemannWhenInputIs1() {
        final User userFromDatabase = userDao.getUserById(1);
        assertEquals(hestemann, userFromDatabase);
    }

    @Test
    public void getUsersShoudReturnAllUsers() {
        final List<User> usersFromDatabase = userDao.getUsers();
        assertEquals(hestemann, usersFromDatabase.get(0));
        assertEquals(grisemann, usersFromDatabase.get(1));
    }
    
    @Test
    public void getUserSettingsByUserShouldReturnTheUserSettingsBelongingToHestemannWhenInputIsHestemann() {
        final UserSettings hestemannSettingsFromDatabase = userDao.getUserSettingsByUser(hestemann);
        userSettingsAreEqual(hestemannSettings, hestemannSettingsFromDatabase);
    }
    
    @Test
    public void getUserSettingsByUserShouldReturnNullWhenThereAreNoUserSettingsForInputUser() {
        final User.Builder userBuilder = new User.Builder("noSettingsUser", "no@settings.user", "pass1", "salt1");
        userBuilder.fullName("No Settings User").locale(new Locale("no", "NO")).id(12312323);
        final User noSettingsUser = new User(userBuilder);
        final UserSettings noUserSettings = userDao.getUserSettingsByUser(noSettingsUser);
        assertNull(noUserSettings);
    }    
    
    @Ignore
    @Test
    public void updateUserSettingsShouldUpdateTheUserSettingsWithNewValuesForChosenUser() {
        final UserSettings updatedHestemannUserSettings = new UserSettings(hestemannSettings.getUserId(), 20);
        userDao.updateUserSettings(updatedHestemannUserSettings);
        
        final UserSettings updatedUserSettingsFromDatabase = userDao.getUserSettingsByUser(hestemann);
        userSettingsAreEqual(updatedHestemannUserSettings, updatedUserSettingsFromDatabase);
        assertNotEquals(updatedUserSettingsFromDatabase, hestemannSettings);
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
    
    private void userSettingsAreEqual(final UserSettings expected, final UserSettings actual) {
        assertEquals(expected.getUserId(), actual.getUserId());
        assertEquals(expected.getPostsPerPage(), actual.getPostsPerPage());
    }
}
