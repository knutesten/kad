package no.mesan.persistence.post;

import static no.mesan.properties.PropertiesProvider.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

import javax.inject.Inject;

import no.mesan.model.Post;
import no.mesan.properties.Sql;

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
    public int createPost(final Post post) {
        KeyHolder generatedKeyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(
            new PreparedStatementCreator() {
                @Override
                public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                    PreparedStatement preparedStatement = 
                            connection.prepareStatement(sql.getProperty(CREATE_POST), 
                                                        new String[] {"post_id"});
                    preparedStatement.setString(1, post.getCreatedBy().getUsername());
                    preparedStatement.setLong(2, post.getCreatedTime().getTime());
                    preparedStatement.setString(3, post.getContent());
                    return preparedStatement;
                }
            }, 
            generatedKeyHolder);
        
        return generatedKeyHolder.getKey().intValue();
    }

    @Override
    public void updatePost(final Post post) {
        jdbcTemplate.update(sql.getProperty(UPDATE_POST), post.getCreatedBy().getUsername(),
                                                          post.getCreatedTime().getTime(),
                                                          post.getLastEditedBy().getUsername(),
                                                          post.getLastEditedTime().getTime(),
                                                          post.getContent(),
                                                          post.getPostId());
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
    public List<Post> getPostsByTopicId(int topicId) {
        return jdbcTemplate.query(sql.getProperty(GET_POSTS_BY_TOPIC_ID), postRowMapper, topicId);
    }

    @Override
    public List<Post> getLimitedPostsByTopicId(final int topicId, 
                                               final int resultsOnPageNumber,
                                               final int limitNumberOfPostsInResult) {
        final int startAtPostNumber = resultsOnPageNumber * limitNumberOfPostsInResult;
        return jdbcTemplate.query(sql.getProperty(GET_LIMITED_POSTS_BY_TOPIC_ID), 
                                  postRowMapper, 
                                  topicId, 
                                  startAtPostNumber, 
                                  limitNumberOfPostsInResult);
    }
}
