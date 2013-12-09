package no.mesan.controllers;

import java.util.List;
import java.util.Locale;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.Size;

import no.mesan.authentication.Encryption;
import no.mesan.controllers.validators.Email;
import no.mesan.controllers.validators.EmailUnique;
import no.mesan.controllers.validators.UserUnique;
import no.mesan.model.Country;
import no.mesan.model.User;
import no.mesan.persistence.user.UserDao;
import no.mesan.persistence.country.CountryDao;

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
    private CountryDao countryDao;

    @Inject
    private Encryption encryption;

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
    private String countryName;
    private Locale locale = Locale.UK;

    private List<Country> countries;

    @PostConstruct
    public void init() {
        countries = countryDao.getCountries();

    }

    public void registerNewUser() {
        if (confirmPassword()) {
            //Do error message!
        }

        final String salt     = encryption.generateSalt();
        final String hash     = encryption.generatePasswordHash(password, salt);
        final Country country = countryDao.getCountryByName(countryName);

        final User.Builder userBuilder = new User.Builder(username, email, hash, salt);
        userBuilder.locale(locale)
                   .country(country);

        final User newUser = new User(userBuilder);
        userDao.createUser(newUser);
    }

    boolean confirmPassword() {
        return password.equals(password2);
    }


    public String getUsername() {
        return username;
    }


    public void setUsername(final String username) {
        this.username = username;
    }


    public String getEmail() {
        return email;
    }


    public void setEmail(final String email) {
        this.email = email;
    }


    public String getPassword() {
        return password;
    }


    public void setPassword(final String password) {
        this.password = password;
    }


    public String getPassword2() {
        return password2;
    }


    public void setPassword2(final String password2) {
        this.password2 = password2;
    }


    public String getFullName() {
        return fullName;
    }


    public void setFullName(final String fullName) {
        this.fullName = fullName;
    }


    public String getCountryName() {
        return countryName;
    }


    public void setCountryName(final String country) {
        this.countryName = country;
    }


    public Locale getLocale() {
        return locale;
    }


    public void setLocale(final Locale locale) {
        this.locale = locale;
    }

    public List<Country> getCountries() {
        return countries;
    }

    public void setCountries(final List<Country> countries) {
        this.countries = countries;
    }
}
