package no.mesan.persistence.category;

import java.sql.SQLException;
import java.util.Properties;

import no.mesan.persistence.MockDatabaseUtility;
import no.mesan.properties.PropertiesProvider;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static no.mesan.persistence.SqlAndDataSetFileNames.*;

import javax.sql.DataSource;

/**
 * TODO
 *
 * @author Knut Esten Melandsø Nekså
 */
public class CategoryDaoImplTest {

    @BeforeClass
    public static void beforeClass() throws Exception {
        final Properties sql = new PropertiesProvider().createSqlProperties();
        final DataSource dataSource = MockDatabaseUtility.getMockDataSource();

    }

    @Before
    public void before() throws Exception {
        MockDatabaseUtility.createDataSet(DATA_SET_CATEGOIRES);
    }

    @Test
    public void getCategoriesShouldReturnAllCategoriesInDatabase() {

    }
}
