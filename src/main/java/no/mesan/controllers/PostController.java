package no.mesan.controllers;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import no.mesan.model.Post;
import no.mesan.model.Topic;
import no.mesan.model.User;
import no.mesan.persistence.post.PostDao;
import no.mesan.persistence.user.UserDao;

/**
 * TODO
 *
 * @author Anders Grotthing Moe
 */
@Named
@ViewScoped
public class PostController {
    @Inject
    private UserDao userDao;
    @Inject
    private PostDao postDao;

    private Post post;
    
    public void createPost(final Topic topic) {
        final User user = userDao.getUserByUsername("admin");
        post = new Post(user, "DETTE ER EN NY POST MED CAPS =)");

        postDao.createPost(post, topic);
    }

    public Post getPost() {
        return post;
    }

    public void setPost(final Post post) {
        this.post = post;
    }
}
