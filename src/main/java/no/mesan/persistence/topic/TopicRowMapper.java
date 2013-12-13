package no.mesan.persistence.topic;

import no.mesan.model.Topic;
import no.mesan.model.User;
import no.mesan.persistence.user.UserDao;
import org.springframework.jdbc.core.RowMapper;

import javax.inject.Inject;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * TODO
 *
 * @author Dean Lozo
 */
public class TopicRowMapper implements RowMapper<Topic> {
    @Inject
    private UserDao userDao;
    private final Map<Integer, User> userCache = new HashMap<>();

    @Override
    public Topic mapRow(final ResultSet resultSet, final int rowNum) throws SQLException {
        final int id = resultSet.getInt("topic_id");
        final User createdBy = getUser(resultSet.getInt("topic_createdBy"));
        final Date createdTime = new Date(resultSet.getLong("topic_createdTime"));
        final String title = resultSet.getString("topic_title");
        
        return new Topic(id, title, createdBy, createdTime);
    }

    private User getUser(final Integer userId){
        if(!userCache.containsKey(userId)){
            userCache.put(userId, userDao.getUserById(userId));
        }
        return userCache.get(userId);
    }
}
