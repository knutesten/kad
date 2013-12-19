package no.mesan.persistence.user;

import java.sql.ResultSet;
import java.sql.SQLException;

import no.mesan.model.UserSettings;

import org.springframework.jdbc.core.RowMapper;

/**
 * TODO
 *
 * @author Knut Esten Melandsø Nekså
 * @author Anders Grotthing Moe
 */
public class UserSettingsRowMapper implements RowMapper<UserSettings> {

    @Override
    public UserSettings mapRow(final ResultSet resultSet, final int i) throws SQLException {
        final int userId       = resultSet.getInt("userSetting_userId");
        final int postsPerPage = resultSet.getInt("userSetting_postsPerPage");

        return new UserSettings(userId, postsPerPage);
    }
}
