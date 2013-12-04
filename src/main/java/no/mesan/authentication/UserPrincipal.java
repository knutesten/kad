package no.mesan.authentication;

import java.security.Principal;

/**
 * TODO
 *
 * @author Knut Esten Melandsø Nekså
 */
public class UserPrincipal implements Principal {
    private final String username;

    public UserPrincipal(final String username) {
        this.username = username;
    }

    @Override
    public String getName() {
        return username;
    }
}
