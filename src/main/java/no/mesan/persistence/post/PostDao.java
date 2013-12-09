package no.mesan.persistence.post;

import no.mesan.model.Post;

/**
 * TODO
 *
 * @author Anders Grotthing Moe
 */
public interface PostDao {
    public int createPost(final Post post);
    public void updatePost(final Post post);
    public Post getPostById(int id);
}