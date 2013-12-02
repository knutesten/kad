package no.mesan.controllers;

import java.util.Locale;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.NotNull;

import no.mesan.authentication.Authentication;
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

    @NotNull
    private String username;
    @NotNull
    private String email;
    @NotNull
    private String password;
    @NotNull
    private String password2;
    private String salt;
    private String fullName;
    private String country;
    
    private Locale locale = Locale.UK;
    private String hash;

    
    public void registerNewUser() {
        //check passwords
        
        salt = authentication.generateSalt();
        hash = authentication.generatePasswordHash(password, salt);
        
        final User.Builder userBuilder = new User.Builder(username, email, hash, salt);
        userBuilder.locale(locale);
        final User newUser = new User(userBuilder);
        userDao.createUser(newUser);
        System.out.println(userDao.getUserByUsername("admin"));
        System.out.println(userDao.getUsers());       
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
