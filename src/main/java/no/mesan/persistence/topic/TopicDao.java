package no.mesan.persistence.topic;

import no.mesan.model.Category;
import no.mesan.model.Topic;
import no.mesan.model.User;

import java.util.List;

/**
 * TODO
 *
 * @author Dean Lozo
 */
public interface TopicDao {
    public void createTopic(final Topic topic, final Category categroy);
    public void updateTopic(final Topic topic);
    public Topic getTopicByTitle(final String title);
    public List<Topic> getTopicsByCreator(final User creator);
    public int getNumberOfPostsInTopic(final int topicId);
    public Topic getTopicById(final int topicId);
    public List<Topic> getTopicsByCategory(final int categoryId, final int pageNumber, final int userLimitedNumberOfTopics);
}
