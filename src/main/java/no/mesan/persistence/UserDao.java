package no.mesan.persistence;

import no.mesan.model.User;

/**
 * TODO
 *
 * @author Knut Esten Melandsø Nekså
 */
public interface UserDao {
    public void createUser(final User user);
    public User getUser(final String username);
}
