package no.mesan.controllers;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import no.mesan.model.User;
import no.mesan.persistence.UserDao;

/**
 * TODO
 *
 * @author Knut Esten Melandsø Nekså
 */
@Named
@RequestScoped
public class NewUserController {
    @Inject
    private UserDao userDao;

    public void registerNewUser() {
        final User newUser = new User("username", "email", "password");
        userDao.createUser(newUser);
    }
}
