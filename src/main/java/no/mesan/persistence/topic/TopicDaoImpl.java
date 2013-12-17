package no.mesan.persistence.topic;

import no.mesan.model.Category;
import no.mesan.model.Topic;
import no.mesan.model.User;
import no.mesan.properties.Sql;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import javax.inject.Inject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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
    public void createTopic(final Topic topic, final Category category){
        final KeyHolder generatedKeyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(
                new PreparedStatementCreator() {
                    @Override
                    public PreparedStatement createPreparedStatement(final Connection connection) throws SQLException {
                        final PreparedStatement preparedStatement =
                                connection.prepareStatement(sql.getProperty(CREATE_TOPIC), new String[]{"topic_id"});
                        preparedStatement.setInt(1, topic.getCreatedBy().getId());
                        preparedStatement.setLong(2, topic.getCreatedTime().getTime());
                        preparedStatement.setInt(3, category.getId());
                        preparedStatement.setString(4, topic.getTitle());
                        return preparedStatement;
                    }
                },
                generatedKeyHolder);

        topic.setId(generatedKeyHolder.getKey().intValue());
    }

    @Override
    public void updateTopic(final Topic oldTopic, final Topic newTopic){
        try {
            jdbcTemplate.update(sql.getProperty(UPDATE_TOPIC), newTopic.getTitle(),
                                                           oldTopic.getId());
        } catch(DataAccessException dae) {
            System.err.println("Could not update topic with new title: ");
            dae.printStackTrace();
        }
    }

    @Override
    public Topic getTopicById(final int topicId) {
        try {
            return jdbcTemplate.queryForObject(sql.getProperty(GET_TOPIC_BY_TOPIC_ID), topicRowMapper, topicId);
        } catch(EmptyResultDataAccessException erdae) {
            return null;
        }
    }

    @Override
    public List<Topic> getLimitedTopicsByCategory(final Category category,
                                                  final int first,
                                                  final int pageSize) {
        return jdbcTemplate.query(sql.getProperty(GET_LIMITED_TOPICS_BY_CATEGORY),
                                  topicRowMapper,
                                  category.getId(),
                                  first,
                                  pageSize);
    }

    @Override
    public List<Topic> getTopicsByCategory(final Category category) {
        return jdbcTemplate.query(sql.getProperty(GET_TOPICS_BY_CATEGORY), topicRowMapper, category.getId());
    }

    @Override
    public Topic getTopicByTitle(final String title) {
        try {
            return jdbcTemplate.queryForObject(sql.getProperty(GET_TOPIC_BY_TITLE), topicRowMapper, title);
        } catch(EmptyResultDataAccessException erdae) {
            return null;
        }
    }

    @Override
    public List<Topic> getTopicsByCreator(final User creator) {
        return jdbcTemplate.query(sql.getProperty(GET_TOPIC_BY_CREATOR), topicRowMapper, creator.getId());
    }

    @Override
    public int getNumberOfTopicsInCategory(final Category category) {
        try {
            return jdbcTemplate.queryForObject(sql.getProperty(GET_NUMBER_OF_TOPICS_IN_CATEGORY), Integer.class, category.getId());
        } catch(DataAccessException dae) {
            return 0;
        }
    }
}
