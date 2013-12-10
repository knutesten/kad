package no.mesan.persistence;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.dbunit.IDatabaseTester;
import org.dbunit.JdbcDatabaseTester;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.h2.tools.RunScript;

import static no.mesan.persistence.SqlAndDataSetFileNames.*;

/**
 * TODO
 *
 * @author Knut Esten Melandsø Nekså
 */
public class MockDatabaseUtility {
    private static final String CONNECTION_URL = "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1;REFERENTIAL_INTEGRITY=false;INIT=CREATE SCHEMA IF NOT EXISTS test";
    private static final String DRIVER_CLASS = "org.h2.Driver";
    private static final DataSource dataSource;
    static {
        final BasicDataSource basicDataSource = new BasicDataSource();
        basicDataSource.setDriverClassName(DRIVER_CLASS);
        basicDataSource.setUrl(CONNECTION_URL);
        dataSource = basicDataSource;
        initializeDatabaseTables();
    }

    private static void initializeDatabaseTables() {
        try {
            executeScript(SQL_COUNTRIES);
            executeScript(SQL_USERS);
            executeScript(SQL_FORUM);
            executeScript(SQL_CATEGORIES);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void executeScript(final String script) throws SQLException {
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
