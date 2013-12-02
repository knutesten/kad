package no.mesan.authentication;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;

import javax.xml.bind.DatatypeConverter;

/**
 * TODO
 *
 * @author Knut Esten Melandsø Nekså
 */
public class Authentication {
    public String generateSalt() {
        final Random random = new SecureRandom();
        final byte[] salt = new byte[32];
        random.nextBytes(salt);
        return new BigInteger(1, salt).toString(16);
    }

    public String generatePasswordHash(final String newPassword, final String salt) {
        try {
            final MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(DatatypeConverter.parseHexBinary(salt));
            md.update(newPassword.getBytes("UTF-8"));

            final BigInteger bigInt = new BigInteger(1, md.digest());
            return bigInt.toString(16);
        } catch(NoSuchAlgorithmException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
