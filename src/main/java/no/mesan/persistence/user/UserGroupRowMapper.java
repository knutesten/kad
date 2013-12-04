package no.mesan.persistence.user;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.jboss.security.SimplePrincipal;
import org.springframework.jdbc.core.RowMapper;

/**
 * TODO
 *
 * @author Knut Esten Melandsø Nekså
 */
public class UserGroupRowMapper implements RowMapper<SimplePrincipal> {
    @Override
    public SimplePrincipal mapRow(final ResultSet resultSet, final int i) throws SQLException {
        return null;
    }
}
