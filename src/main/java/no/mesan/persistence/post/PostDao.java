package no.mesan.persistence.post;

import java.util.List;

import no.mesan.model.Post;

/**
 * TODO
 *
 * @author Anders Grotthing Moe
 */
public interface PostDao {
    public void createPost(final Post post);
    public void updatePost(final Post post);
    public Post getPostById(final int id);
    public List<Post> getPostsByTopicId(final int topicId);
    public List<Post> getLimitedPostsByTopicId(final int topicId, 
                                               final int pageNumber,
                                               final int userLimitedNumberOfPosts);
}
