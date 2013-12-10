package no.mesan.persistence.category;

import java.sql.ResultSet;
import java.sql.SQLException;

import no.mesan.model.Category;

import org.springframework.jdbc.core.RowMapper;

/**
 * TODO
 *
 * @author Knut Esten Melandsø Nekså
 */
public class CategoryRowMapper implements RowMapper<Category> {
    @Override
    public Category mapRow(final ResultSet resultSet, final int rowNum) throws SQLException {
        final int    id   = resultSet.getInt("category_id");
        final String name = resultSet.getString("category_name");
        return new Category(id, name);
    }
}
