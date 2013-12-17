package no.mesan.authentication;

import java.security.acl.Group;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.security.auth.Subject;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.login.AccountNotFoundException;
import javax.security.auth.login.LoginException;

import no.mesan.model.User;
import no.mesan.persistence.user.UserDao;

import org.jboss.security.SimpleGroup;
import org.jboss.security.SimplePrincipal;
import org.jboss.security.auth.spi.UsernamePasswordLoginModule;

public class KadLoginModule extends UsernamePasswordLoginModule{
    @Inject
    private UserDao userDao;
    @Inject
    private Encryption encryption;
    private User user;


    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    public void initialize(final Subject subject, final CallbackHandler callbackHandler,
                           final Map sharedState, final Map options) {
        super.initialize(subject, callbackHandler, sharedState, options);
        CdiHelper.programmaticInjection(KadLoginModule.class, this);
    }

    @Override
    protected String getUsersPassword() throws LoginException {
        if (getUser() == null) throw new AccountNotFoundException("User not found.");
        return getUser().getHash();
    }

    @Override
    protected boolean validatePassword(final String inputPassword, final String expectedPasswordHash) {
        final String hashedInputPassword = encryption.generatePasswordHash(inputPassword, getUser().getSalt());
        return hashedInputPassword.equals(expectedPasswordHash);
    }

    @Override
    protected Group[] getRoleSets() throws LoginException {
        final SimpleGroup roles = new SimpleGroup("Roles");
        final List<SimplePrincipal> userGroups = userDao.getUserGroups(getUsername());
        for (final SimplePrincipal userGroup : userGroups)
            roles.addMember(userGroup);
        return new Group[]{ roles };
    }

    private User getUser() {
        if (user == null)
            user = userDao.getUserByUsername(getUsername());
        return user;
    }
}
