package no.mesan.persistence.category;

import java.util.List;
import java.util.Properties;

import no.mesan.model.Category;
import no.mesan.persistence.MockDatabaseUtility;
import no.mesan.properties.PropertiesProvider;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.powermock.reflect.Whitebox;
import org.springframework.jdbc.core.JdbcTemplate;

import static no.mesan.persistence.SqlAndDataSetFileNames.*;

import static org.junit.Assert.*;

import javax.sql.DataSource;

/**
 * TODO
 *
 * @author Knut Esten Melandsø Nekså
 */
public class CategoryDaoImplTest {
    private static final CategoryDao categoryDao = new CategoryDaoImpl();
    private static final Category hester  = new Category("Hester");
    private static final Category griser  = new Category("Griser");
    private static final Category kameler = new Category("Kameler");

    @BeforeClass
    public static void beforeClass() throws Exception {
        final Properties sql = new PropertiesProvider().createSqlProperties();
        final DataSource dataSource = MockDatabaseUtility.getMockDataSource();

        Whitebox.setInternalState(categoryDao, "sql"              , sql);
        Whitebox.setInternalState(categoryDao, "jdbcTemplate"     , new JdbcTemplate(dataSource));
        Whitebox.setInternalState(categoryDao, "categoryRowMapper", new CategoryRowMapper());
    }

    @Before
    public void before() throws Exception {
        MockDatabaseUtility.createDataSet(DATA_SET_CATEGOIRES);
    }

    @Test
    public void getCategoryByNameShouldReturnCategoryHesterWhenInputIsHester() {
        final Category hesterFromDatabase = categoryDao.getCategoryByName("Hester");
        assertEquals(hester, hesterFromDatabase);
    }

    @Test
    public void getCategoriesShouldReturnAllCategoriesInDatabase() {
        final List<Category> categories = categoryDao.getCategories();
        assertEquals(hester,  categories.get(0));
        assertEquals(griser,  categories.get(1));
        assertEquals(kameler, categories.get(2));
    }
}
