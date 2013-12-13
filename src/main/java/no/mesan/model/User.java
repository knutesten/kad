package no.mesan.model;

import java.util.Locale;

/**
 * TODO
 *
 * @author Knut Esten Melandsø Nekså
 * @author Anders Grotthing Moe
 */
public class User {
    private int id;
    private final String username;
    private String email;
    private String hash;
    private final String salt;
    private String fullName;
    private Country country;
    private Locale locale;

    public static class Builder {
        private int id;
        private final String username;
        private final String email;
        private final String hash;
        private final String salt;
        private String fullName;
        private Country country;
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

        public Builder country(final Country country) {
            this.country = country;
            return this;
        }

        public Builder locale(final Locale locale) {
            this.locale = locale;
            return this;
        }

        public Builder id(final int id) {
            this.id = id;
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
        id       = builder.id;
    }

    @Override
    public String toString() {
        return username;
    }

    @Override
    public boolean equals(final Object object) {
        if (object instanceof User) {
            final User that = (User) object;
            return username.equals(that.username) &&
                   email   .equals(that.email)    &&
                   hash    .equals(that.hash)     &&
                   fullName.equals(that.fullName) &&
                   country .equals(that.country)  &&
                   salt    .equals(that.salt)     &&
                   id      ==      that.id        &&
                   locale  .equals(that.locale);

        }
        return false;
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

    public Country getCountry() {
        return country;
    }

    public void setCountry(final Country country) {
        this.country = country;
    }

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(final Locale locale) {
        this.locale = locale;
    }

    public int getId() {
        return id;
    }

    public void setId(final int id) {
        this.id = id;
    }
}
