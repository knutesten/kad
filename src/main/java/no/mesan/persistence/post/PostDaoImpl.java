package no.mesan.persistence.post;

import static no.mesan.properties.PropertiesProvider.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

import javax.inject.Inject;

import no.mesan.model.Post;
import no.mesan.model.Topic;
import no.mesan.properties.Sql;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;


public class PostDaoImpl implements PostDao {
    @Inject @Sql
    private Properties sql;
    @Inject
    private JdbcTemplate jdbcTemplate;
    @Inject
    private PostRowMapper postRowMapper;

    @Override
    public void createPost(final Post post, final Topic topic) {
        final KeyHolder generatedKeyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(
            new PreparedStatementCreator() {
                @Override
                public PreparedStatement createPreparedStatement(final Connection connection) throws SQLException {
                    final PreparedStatement preparedStatement =
                            connection.prepareStatement(sql.getProperty(CREATE_POST), new String[]{"post_id"});
                    preparedStatement.setInt(1, post.getCreatedBy().getId());
                    preparedStatement.setLong(2, post.getCreatedTime().getTime());
                    preparedStatement.setString(3, post.getContent());
                    preparedStatement.setLong(4, post.getCreatedTime().getTime());
                    return preparedStatement;
                }
            },
            generatedKeyHolder);
        post.setId(generatedKeyHolder.getKey().intValue());
        
        updatePostInTopic(post, topic);
    }

    private void updatePostInTopic(final Post post, final Topic topic) {
        jdbcTemplate.update(sql.getProperty(UPDATE_POST_IN_TOPIC_WITH_NEW_POST));
    }

    @Override
    public void updatePost(final Post post) {
        jdbcTemplate.update(sql.getProperty(UPDATE_POST), post.getCreatedBy().getId(),
                                                          post.getCreatedTime().getTime(),
                                                          post.getLastEditedBy().getId(),
                                                          post.getLastEditedTime().getTime(),
                                                          post.getContent(),
                                                          post.getId());
    }

    @Override
    public Post getPostById(final int id) {
        try{
            return jdbcTemplate.queryForObject(sql.getProperty(GET_POST_BY_ID), postRowMapper, id);
        } catch(EmptyResultDataAccessException erda){
            return null;
        }
    }

    @Override
    public Post getLastPostByTopic(Topic topic) {
        try {
            return jdbcTemplate.queryForObject(sql.getProperty(GET_LAST_POST_BY_TOPIC), 
                                               postRowMapper, 
                                               topic.getId());
        } catch(EmptyResultDataAccessException erda) {
            return null;
        }
    }
    
    @Override
    public List<Post> getPostsByTopicId(final int topicId) {
        return jdbcTemplate.query(sql.getProperty(GET_POSTS_BY_TOPIC_ID), postRowMapper, topicId);
    }

    @Override
    public List<Post> getLimitedPostsByTopic(final Topic topic,
                                             final int first,
                                             final int pageSize) {
        return jdbcTemplate.query(sql.getProperty(GET_LIMITED_POSTS_BY_TOPIC),
                                  postRowMapper,
                                  topic.getId(),
                                  first,
                                  pageSize);
    }

    @Override
    public int getNumberOfPostsInTopic(final Topic topic) {
        try {
            return jdbcTemplate.queryForObject(sql.getProperty(GET_NUMBER_OF_POSTS_IN_TOPIC), Integer.class, topic.getId());
        } catch(DataAccessException dae) {
            return 0;
        }
    }
}
