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
    @Inject
    private JdbcTemplate jdbcTemplate;

    public void createThread(final Thread thread){

    }

}
