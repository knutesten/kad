package no.mesan.persistence;

import no.mesan.model.User;
import no.mesan.properties.Sql;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.inject.Inject;
import javax.sql.DataSource;
import java.util.List;
import java.util.Properties;

import static no.mesan.properties.PropertiesProvider.*;


import no.mesan.model.ForumThread;

/**
 * TODO
 *
 * @author Dean Lozo
 */
public class forumThreadDaoImpl implements forumThreadDao {

    @Inject @Sql
    private Properties sql;
    private final JdbcTemplate jdbcTemplate;

    @Inject
    public forumThreadDaoImpl(final DataSource dataSource){
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public void createThread(final ForumThread forumThread){
        jdbcTemplate.update(sql.getProperty(CREATE_THREAD), forumThread.getTitle(),
                                                            forumThread.getOwner(),
                                                            forumThread.getCreatedTime().getTime(),
                                                            forumThread.getCategory());
    }

    public void updateThread(final ForumThread forumThread){
        jdbcTemplate.update(sql.getProperty(UPDATE_THREAD), forumThread.getTitle(),
                                                            forumThread.getOwner(),
                                                            forumThread.getCreatedTime().getTime(),
                                                            forumThread.getCategory());

    }

    @Override
    public ForumThread getThreadbyTitle(String title) {
        return null;
    }

    @Override
    public List<ForumThread> getThreadsByOwner(User owner) {
        return null;
    }

    public void
}
