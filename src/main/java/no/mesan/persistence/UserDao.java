package no.mesan.persistence;

import java.util.List;

import no.mesan.model.User;

import org.jboss.security.SimplePrincipal;

/**
 * TODO
 *
 * @author Knut Esten Melandsø Nekså
 */
public interface UserDao {
    public void createUser(final User user);
    public void updateUser(final User user);
    public User getUserByUsername(final String username);
    public User getUserByEmail(final String email);
    public List<User> getUsers();
    public List<SimplePrincipal> getUserRoles(final String username);
}
