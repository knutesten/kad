package no.mesan.manager;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Locale;

import javax.servlet.http.HttpSession;

import no.mesan.model.User;

import org.junit.BeforeClass;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

/**
 * TODO
 *
 * @author Anders Grotthing Moe
 */
public class SessionManagerTest {
    private static final SessionManager sessionManager = new SessionManager();

    private static HttpSession httpSession;
    private static String guestString;
    private static User adminUser;

    @BeforeClass
    public static void beforeClass() throws Exception {
        initializeTestUsers();
    }

    private static void initializeTestUsers() {
        final User.Builder adminBuilder = new User.Builder("admin",
                                                           "dummy@dummymail.com",
                                                           "ec29de432d53e2ddd0b574a4b5d6250667aefa259c1b14ba3a9d7289d642c01e",
                                                           "80224678a05b29556a67e06db63ff4561623e50e402ee2e3b8c5b9bf049ca23c");
        adminBuilder.fullName("Hestemannen").locale(new Locale("en", "GB"));
        adminUser = new User(adminBuilder);
        guestString = "Guest";
    }

    @Test
    public void getUsernameShouldReturnGuestWhenNoUserExists() {
        httpSession = mock(HttpSession.class);
        when(httpSession.getAttribute("user")).thenReturn(null);
        Whitebox.setInternalState(sessionManager, "httpSession", httpSession);
        final String username = sessionManager.getUsername();
        assertEquals(guestString, username);
    }

    @Test
    public void getUsernameShouldReturnTheUsernameAdminIfTheSessionBelongsToAdmin() {
        httpSession = mock(HttpSession.class);
        when(httpSession.getAttribute("user")).thenReturn(adminUser);
        Whitebox.setInternalState(sessionManager, "httpSession", httpSession);
        final String username = sessionManager.getUsername();
        assertEquals(adminUser.getUsername(), username);
    }
}
