package no.mesan.authentication;

import static org.junit.Assert.assertEquals;

import java.io.UnsupportedEncodingException;

import org.junit.Test;

/**
 * TODO
 *
 * @author Knut Esten Melandsø Nekså
 */
public class AuthenticationTest {
    private static final String PASSWORD      = "hestemann er god å ha";
    private static final String SALT          = "80224678a05b29556a67e06db63ff4561623e50e402ee2e3b8c5b9bf049ca23c";
    private static final String EXPECTED_HASH = "5c0ee930804b02788b4914c4c276a26c1b473e0b460f8e7ebbf4f60b0bc75ccc";

    @Test
    public void generatePasswordHashShouldGenerateASHA256Hash() throws UnsupportedEncodingException {
        final Authentication authentication = new Authentication();
        assertEquals(EXPECTED_HASH, authentication.generatePasswordHash(PASSWORD, SALT));
    }
}
