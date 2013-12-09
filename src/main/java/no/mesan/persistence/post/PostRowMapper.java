package no.mesan.persistence.post;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import no.mesan.model.Post;
import no.mesan.model.User;
import no.mesan.persistence.user.UserDao;

import org.springframework.jdbc.core.RowMapper;

public class PostRowMapper implements RowMapper<Post> {

    @Inject
    private UserDao userDao;
    private final Map<String, User> userCache = new HashMap<>();
    @Override
    public Post mapRow(final ResultSet resultSet, final int rowNum) throws SQLException {
        final User createdBy = getUser(resultSet.getString("post_createdBy"));
        final Date createdTime = createDate(resultSet.getLong("post_createdTime"));
        final User lastEditedBy = getUser(resultSet.getString("post_lastEditedBy"));
        final Date lastEditedTime = createDate(resultSet.getLong("post_lastEditedBy"));
        final String content = resultSet.getString("post_content");
        
        Post post = new Post(createdBy, createdTime, content);
        post.setLastEditedBy(lastEditedBy);
        post.setLastEditedTime(lastEditedTime);
        
        return post;
    }
    
    private User getUser(final String username){
        if(!userCache.containsKey(username)){
            userCache.put(username, userDao.getUserByUsername(username));
        }
        return userCache.get(username);
    }

    private Date createDate(Long dateTime) {
        if (dateTime == null)
            return null;
        return new Date(dateTime);
    }
}
