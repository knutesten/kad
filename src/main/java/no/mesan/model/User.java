package no.mesan.model;

import java.util.Locale;

/**
 * TODO
 *
 * @author Knut Esten Melandsø Nekså
 */
public class User {
    private final String username;
    private String email;
    private String password;
    private final String salt;
    private String fullName;
    private String country;
    private Locale locale;

    public User(final String username, final String email, final String password) {
        this.username = username;
        this.email = email;
        this.password = password;
        salt = generateSalt();
        locale = Locale.getDefault(); // TODO: This should be set using a locale manager class.
    }

    private String generateSalt() {
        return "salt";
    }

    public String getUsername() {
        return username;
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

    public String getSalt() {
        return salt;
    }
}
