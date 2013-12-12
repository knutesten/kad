package no.mesan.persistence.category;

import java.util.Date;
import java.util.List;
import java.util.Properties;

import no.mesan.model.Category;
import no.mesan.model.Topic;
import no.mesan.model.User;
import no.mesan.persistence.MockDatabaseUtility;
import no.mesan.persistence.topic.TopicDao;
import no.mesan.properties.PropertiesProvider;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.powermock.reflect.Whitebox;
import org.springframework.jdbc.core.JdbcTemplate;

import static no.mesan.persistence.SqlAndDataSetFileNames.*;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import javax.sql.DataSource;

/**
 * TODO
 *
 * @author Knut Esten Melandsø Nekså
 */
public class CategoryDaoImplTest {
    private static final CategoryDao categoryDao = new CategoryDaoImpl();
    private static final Topic topic1 = new Topic(1, "title1", mock(User.class) , new Date(0));
    private static final Topic topic2 = new Topic(2, "title2", mock(User.class) , new Date(10));
    private static final Category hester  = new Category(1, "Hester",  topic1);
    private static final Category griser  = new Category(2, "Griser",  topic2);
    private static final Category kameler = new Category(3, "Kameler", null);

    @BeforeClass
    public static void beforeClass() throws Exception {
        final Properties sql = new PropertiesProvider().createSqlProperties();
        final DataSource dataSource = MockDatabaseUtility.getMockDataSource();

        final TopicDao topicDao = mock(TopicDao.class);
        when(topicDao.getTopicById(1)).thenReturn(topic1);
        when(topicDao.getTopicById(2)).thenReturn(topic2);
        when(topicDao.getTopicById(3)).thenReturn(null);
        final CategoryRowMapper categoryRowMapper = new CategoryRowMapper();

        Whitebox.setInternalState(categoryRowMapper, "topicDao", topicDao);
        Whitebox.setInternalState(categoryDao, "sql"              , sql);
        Whitebox.setInternalState(categoryDao, "jdbcTemplate"     , new JdbcTemplate(dataSource));
        Whitebox.setInternalState(categoryDao, "categoryRowMapper", categoryRowMapper);
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
