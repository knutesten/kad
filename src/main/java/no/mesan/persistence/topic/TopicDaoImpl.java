package no.mesan.persistence.topic;

import no.mesan.model.Topic;
import no.mesan.model.User;
import no.mesan.properties.Sql;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.inject.Inject;
import java.util.List;
import java.util.Properties;

import static no.mesan.properties.PropertiesProvider.*;

/**
 * TODO
 *
 * @author Dean Lozo
 */
public class TopicDaoImpl implements TopicDao {
    @Inject @Sql
    private Properties sql;
    @Inject
    private JdbcTemplate jdbcTemplate;
    @Inject
    private TopicRowMapper topicRowMapper;

    @Override
    public void createTopic(final Topic topic){
        jdbcTemplate.update(sql.getProperty(CREATE_TOPIC), topic.getTitle(),
                                                           topic.getCreatedBy().getUsername(),
                                                           topic.getCreatedTime().getTime());
    }

    @Override
    public void updateTopic(final Topic topic){
        jdbcTemplate.update(sql.getProperty(UPDATE_TOPIC), topic.getTitle(),
                                                           topic.getCreatedBy().getUsername(),
                                                           topic.getCreatedTime().getTime(),
                                                           topic.getTitle());
    }

    @Override
    public Topic getTopicByTitle(final String title) {
        try{
            return jdbcTemplate.queryForObject(sql.getProperty(GET_TOPIC_BY_TITLE), topicRowMapper, title);
        } catch(EmptyResultDataAccessException erda){
            return null;
        }

    }

    @Override
    public List<Topic> getTopicsByCreator(final User creator) {
        return jdbcTemplate.query(sql.getProperty(GET_TOPIC_BY_CREATOR), topicRowMapper, creator.getUsername());
    }
}
