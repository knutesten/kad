package no.mesan.controllers;

import java.util.Locale;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.Size;

import no.mesan.authentication.Authentication;
import no.mesan.controllers.validators.Email;
import no.mesan.controllers.validators.EmailUnique;
import no.mesan.controllers.validators.UserUnique;
import no.mesan.model.User;
import no.mesan.persistence.UserDao;

/**
 * TODO
 *
 * @author Knut Esten Melandsø Nekså
 * @author Anders Grotthing Moe
 */
@Named
@RequestScoped
public class NewUserController {
    @Inject
    private UserDao userDao;

    @Inject
    private Authentication authentication;

    @Size(min = 2, max = 30, message="{no.mesan.controllers.validators.username_size.message}")
    @UserUnique
    private String username;

    @Email
    @EmailUnique
    private String email;

    @Size(min = 8, message="{no.mesan.controllers.validators.password_size.message}")
    private String password;
    private String password2;
    private String fullName;
    private String country;

    private Locale locale = Locale.UK;


    public void registerNewUser() {
        if (confirmPassword()) {
            //Do error message!
        }

        final String salt = authentication.generateSalt();
        final String hash = authentication.generatePasswordHash(password, salt);

        final User.Builder userBuilder = new User.Builder(username, email, hash, salt);
        userBuilder.locale(locale);
        final User newUser = new User(userBuilder);
        userDao.createUser(newUser);
    }

    public boolean confirmPassword() {
        return password.equals(password2);
    }


    public String getUsername() {
        return username;
    }


    public void setUsername(String username) {
        this.username = username;
    }


    public String getEmail() {
        return email;
    }


    public void setEmail(String email) {
        this.email = email;
    }


    public String getPassword() {
        return password;
    }


    public void setPassword(String password) {
        this.password = password;
    }


    public String getPassword2() {
        return password2;
    }


    public void setPassword2(String password2) {
        this.password2 = password2;
    }


    public String getFullName() {
        return fullName;
    }


    public void setFullName(String fullName) {
        this.fullName = fullName;
    }


    public String getCountry() {
        return country;
    }


    public void setCountry(String country) {
        this.country = country;
    }


    public Locale getLocale() {
        return locale;
    }


    public void setLocale(Locale locale) {
        this.locale = locale;
    }
}
