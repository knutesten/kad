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
    private final Map<Integer, User> userCache = new HashMap<>();
    
    @Override
    public Post mapRow(final ResultSet resultSet, final int rowNum) throws SQLException {
        final int postId = resultSet.getInt("post_id");
        final User createdBy = getUser(resultSet.getInt("post_createdBy"));
        final Date createdTime = createDate(resultSet.getLong("post_createdTime"));
        final User lastEditedBy = getUser((Integer)resultSet.getObject("post_lastEditedBy"));
        final Date lastEditedTime = createDate(resultSet.getLong("post_lastEditedTime"));
        final String content = resultSet.getString("post_content");
        
        Post post = new Post(postId, createdBy, createdTime, lastEditedBy, lastEditedTime, content);
        return post;
    }
    
    private User getUser(final Integer userId){
        if(userId == null)
            return null;
        if(!userCache.containsKey(userId)){
            userCache.put(userId, userDao.getUserById(userId));
        }
        return userCache.get(userId);
    }

    private Date createDate(Long dateTime) {
        return new Date(dateTime);
    }
}
