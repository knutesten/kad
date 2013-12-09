package no.mesan.authentication;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;

import javax.xml.bind.DatatypeConverter;

import org.apache.commons.lang3.StringUtils;

/**
 * TODO
 *
 * @author Knut Esten Melandsø Nekså
 */
public class Encryption {
    public String generateSalt() {
        final Random random = new SecureRandom();
        final byte[] salt = new byte[32];
        random.nextBytes(salt);
        return StringUtils.leftPad(new BigInteger(1, salt).toString(16), 64, "0");
    }

    public String generatePasswordHash(final String newPassword, final String salt) {
        try {
            final MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(DatatypeConverter.parseHexBinary(salt));
            md.update(newPassword.getBytes(StandardCharsets.UTF_8));

            final BigInteger bigInt = new BigInteger(1, md.digest());
            return bigInt.toString(16);
        } catch(NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }
}
