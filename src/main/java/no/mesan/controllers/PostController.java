package no.mesan.controllers;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import no.mesan.model.Post;
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

    public Post getPost() {
        return post;
    }

    public void setPost(final Post post) {
        this.post = post;
    }
}
