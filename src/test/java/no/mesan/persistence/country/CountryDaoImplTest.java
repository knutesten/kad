package no.mesan.persistence.country;

import static org.junit.Assert.*;

import java.util.List;
import java.util.Properties;

import javax.sql.DataSource;

import no.mesan.model.Country;
import no.mesan.persistence.MockDatabaseUtility;
import no.mesan.properties.PropertiesProvider;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.powermock.reflect.Whitebox;
import org.springframework.jdbc.core.JdbcTemplate;

import static no.mesan.persistence.SqlAndDataSetFileNames.*;

/**
 * TODO
 *
 * @author Knut Esten Melandsø Nekså
 */
public class CountryDaoImplTest {
    private static final CountryDao countryDao = new CountryDaoImpl();
    private static final Country AFGHANISTAN = new Country("AF", "Afghanistan");
    private static final Country ALBANIA     = new Country("AL", "Albania");
    private static final Country ALGERIA     = new Country("DZ", "Algeria");

    @BeforeClass
    public static void beforeClass() throws Exception {
        MockDatabaseUtility.executeScript(SQL_COUNTRIES);

        final Properties sql        = new PropertiesProvider().createSqlProperties();
        final DataSource dataSource = MockDatabaseUtility.getMockDataSource();

        Whitebox.setInternalState(countryDao, "sql",              sql);
        Whitebox.setInternalState(countryDao, "jdbcTemplate",     new JdbcTemplate(dataSource));
        Whitebox.setInternalState(countryDao, "countryRowMapper", new CountryRowMapper());
    }

    @Before
    public void before() throws Exception {
        MockDatabaseUtility.createDataSet(DATA_SET_COUNTRIES);
    }

    @Test
    public void getCountriesShouldReturnAll3CountriesSortedByName() throws Exception {
        final List<Country> countries = countryDao.getCountries();
        assertEquals(AFGHANISTAN, countries.get(0));
        assertEquals(ALBANIA,     countries.get(1));
        assertEquals(ALGERIA,     countries.get(2));
    }

    @Test
    public void getCountryByCodeShouldReturnAlgeriaWhenInputIsDZ() {
        final Country algeria = countryDao.getCountryByCode("DZ");
        assertEquals(ALGERIA, algeria);
    }

    @Test
    public void getCounryByCodeShouldReturnNullWhenCountryCodeDoesNotExist() {
        final Country noCountry = countryDao.getCountryByCode("hestekode");
        assertNull(noCountry);
    }

    @Test
    public void getCountryByNameShouldReturnAlgeriaWhenInputIsAlgeria() {
        final Country algeria = countryDao.getCountryByName("Algeria");
        assertEquals(ALGERIA, algeria);
    }

    @Test
    public void getCounryByNameShouldReturnNullWhenCountryNameDoesNotExist() {
        final Country noCountry = countryDao.getCountryByName("hesteland");
        assertNull(noCountry);
    }
}
