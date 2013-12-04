package no.mesan.authentication;

import java.security.Principal;

/**
 * TODO
 *
 * @author Knut Esten Melandsø Nekså
 */
public class RolePrincipal implements Principal {
    private final String role;

    public RolePrincipal(final String role) {
        this.role = role;
    }

    @Override
    public String getName() {
        return role;
    }
}
