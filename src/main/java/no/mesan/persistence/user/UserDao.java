package no.mesan.persistence.user;

import java.util.List;

import no.mesan.model.User;
import no.mesan.model.UserSettings;

import org.jboss.security.SimplePrincipal;

/**
 * TODO
 *
 * @author Knut Esten Melandsø Nekså
 */
public interface UserDao {
    public void createUser(final User user);
    public void updateUser(final User user);
    public User getUserById(final int id);
    public User getUserByUsername(final String username);
    public User getUserByEmail(final String email);
    public List<User> getUsers();
    
    public void updateUserSettings(final UserSettings userSettings);
    public UserSettings getUserSettingsByUser(final User user);

    public void addUserToUserGroup(final User user, final String userGroupName);
    public void removeUserFromUserGroup(final User user, final String userGroupName);
    public Integer getUserGroupIdByName(final String userGroupName);
    public List<SimplePrincipal> getUserGroups(final String username);
}
