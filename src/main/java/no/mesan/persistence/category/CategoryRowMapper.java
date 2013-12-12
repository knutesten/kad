package no.mesan.persistence.category;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.inject.Inject;

import no.mesan.model.Category;
import no.mesan.model.Topic;
import no.mesan.persistence.topic.TopicDao;

import org.springframework.jdbc.core.RowMapper;

/**
 * TODO
 *
 * @author Knut Esten Melandsø Nekså
 */
public class CategoryRowMapper implements RowMapper<Category> {
    @Inject
    private TopicDao topicDao;

    @Override
    public Category mapRow(final ResultSet resultSet, final int rowNum) throws SQLException {
        final int    id    = resultSet.getInt("category_id");
        final String name  = resultSet.getString("category_name");
        final Topic topic = topicDao.getTopicById(resultSet.getInt("category_lastUpdatedTopicId"));
        return new Category(id, name);
    }
}
