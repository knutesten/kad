package no.mesan.authentication;

import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
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

    public String generatePasswordHash(final char[] newPassword, final String salt) {
        try {
            final Charset charset = Charset.forName("UTF-8");
            final ByteBuffer byteBuffer = charset.encode(CharBuffer.wrap(newPassword));

            final MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(DatatypeConverter.parseHexBinary(salt));
            md.update(byteBuffer.array());

            final BigInteger bigInt = new BigInteger(1, md.digest());
            return bigInt.toString(16);
        } catch(NoSuchAlgorithmException nsae) {
            nsae.printStackTrace();
        }
        return null;
    }
}
