package no.mesan.controllers;

import java.util.Locale;

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
        final User.Builder builder = new User.Builder("username2", "email2", "password", "saltisalt");
        builder.locale(Locale.UK);
        final User newUser = new User(builder);
        userDao.createUser(newUser);
        System.out.println(userDao.getUserByUsername("admin"));
        System.out.println(userDao.getUsers());
    }
}
