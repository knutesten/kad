package no.mesan.persistence.category;

import java.util.List;
import java.util.Properties;

import javax.inject.Inject;

import no.mesan.model.Category;
import no.mesan.properties.Sql;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import static no.mesan.properties.PropertiesProvider.*;

/**
 * TODO
 *
 * @author Knut Esten Melandsø Nekså
 */
public class CategoryDaoImpl implements CategoryDao {
    @Inject @Sql
    private Properties sql;
    @Inject
    private JdbcTemplate jdbcTemplate;
    @Inject
    private CategoryRowMapper categoryRowMapper;

    @Override
    public List<Category> getCategories() {
        return jdbcTemplate.query(sql.getProperty(GET_CATEGORIES), categoryRowMapper);
    }

    @Override
    public Category getCategoryByName(final String name) {
        try {
            return jdbcTemplate.queryForObject(sql.getProperty(GET_CATEGORY_BY_NAME), categoryRowMapper, name);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }
}
