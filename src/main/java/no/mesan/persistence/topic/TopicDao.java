package no.mesan.persistence.topic;

import no.mesan.model.Topic;
import no.mesan.model.User;

import java.util.List;

/**
 * TODO
 *
 * @author Dean Lozo
 */
public interface TopicDao {

    public void createTopic(Topic topic);
    public void updateTopic(Topic topic);
    public Topic getTopicByTitle(final String title);
    public List<Topic> getTopicsByCreator(final User creator);
}
