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
    private String hash;
    private final String salt;
    private String fullName;
    private String country;
    private Locale locale;

    public static class Builder {
        private final String username;
        private final String email;
        private final String hash;
        private final String salt;
        private String fullName;
        private String country;
        private Locale locale;

        public Builder (final String username, final String email, final String hash, final String salt) {
            this.username = username;
            this.email = email;
            this.hash = hash;
            this.salt = salt;
        }

        public Builder fullName(final String fullName) {
            this.fullName = fullName;
            return this;
        }

        public Builder country(final String country) {
            this.country = country;
            return this;
        }

        public Builder locale(final Locale locale) {
            this.locale = locale;
            return this;
        }
    }

    public User(final Builder builder) {
        username = builder.username;
        email    = builder.email;
        hash     = builder.hash;
        fullName = builder.fullName;
        country  = builder.country;
        salt     = builder.salt;
        locale   = builder.locale;
    }

    @Override
    public String toString() {
        return username;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(final String hash) {
        this.hash = hash;
    }

    public String getSalt() {
        return salt;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(final String fullName) {
        this.fullName = fullName;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(final String country) {
        this.country = country;
    }

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(final Locale locale) {
        this.locale = locale;
    }
}
