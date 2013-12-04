package no.mesan.persistence;

import no.mesan.properties.Sql;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.inject.Inject;
import javax.sql.DataSource;
import java.util.Properties;

/**
 * TODO
 *
 * @author Dean Lozo
 */
public class ThreadDaoImpl {

    @Inject @Sql
    private Properties sql;
    private final JdbcTemplate jdbcTemplate;

    @Inject
    public ThreadDaoImpl(DataSource dataSource){
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void createThread(final Thread thread){

    }

}
