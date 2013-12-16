package no.mesan.persistence.post;

import java.util.List;

import no.mesan.model.Post;
import no.mesan.model.Topic;

/**
 * TODO
 *
 * @author Anders Grotthing Moe
 */
public interface PostDao {
    public void createPost(final Post post, final Topic topic);
    public void updatePost(final Post post);
    public Post getPostById(final int id);
    public Post getLastPostByTopic(final Topic topic);
    public List<Post> getPostsByTopicId(final int topicId);
    public List<Post> getLimitedPostsByTopic(final Topic topic, 
                                               final int first,
                                               final int pageSize);
    public int getNumberOfPostsInTopic(final Topic topic);
}
