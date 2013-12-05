package no.mesan.persistence;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.dbunit.IDatabaseTester;
import org.dbunit.JdbcDatabaseTester;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.h2.tools.RunScript;

/**
 * TODO
 *
 * @author Knut Esten Melandsø Nekså
 */
public class MockDatabaseUtility {
    private static final String CONNECTION_URL = "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1";
    private static final String DRIVER_CLASS = "org.h2.Driver";
    private static final DataSource dataSource;
    static {
        final BasicDataSource basicDataSource = new BasicDataSource();
        basicDataSource.setDriverClassName(DRIVER_CLASS);
        basicDataSource.setUrl(CONNECTION_URL);
        dataSource = basicDataSource;
    }

    public static void executeScript(final String script) throws SQLException {
        RunScript.execute(CONNECTION_URL, "", "", script, StandardCharsets.UTF_8, false);
    }

    public static DataSource getMockDataSource() {
        return dataSource;
    }

    public static void createDataSet(final String dataSetXml) throws Exception{
        final IDataSet dataSet = new FlatXmlDataSetBuilder().build(new File(dataSetXml));
        final IDatabaseTester databaseTester = new JdbcDatabaseTester(DRIVER_CLASS, CONNECTION_URL);
        databaseTester.setSetUpOperation(DatabaseOperation.CLEAN_INSERT);
        databaseTester.setDataSet(dataSet);
        databaseTester.onSetup();
    }
}
