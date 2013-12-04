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
    private final Map<String, User> userCache = new HashMap<>();

    @Override
    public Topic mapRow(final ResultSet resultSet, final int rowNum) throws SQLException {
        final String title = resultSet.getString("topic_title");
        final String category = resultSet.getString("topic_category");
        final User createdBy = getUser(resultSet.getString("topic_title"));
        final Date createdTime = new Date(resultSet.getLong("topic_createdTime"));

        return new Topic(title, category, createdBy, createdTime);
    }

    private User getUser(final String username){
        if(!userCache.containsKey(username)){
            userCache.put(username, userDao.getUserByUsername(username));
        }
        return userCache.get(username);
    }
}
