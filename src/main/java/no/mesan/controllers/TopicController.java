package no.mesan.controllers;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import no.mesan.manager.SessionManager;
import no.mesan.model.LazyPostDataModel;
import no.mesan.model.Post;
import no.mesan.model.Topic;
import no.mesan.model.User;
import no.mesan.persistence.post.PostDao;

/**
 * TODO
 *
 * @author Knut Esten Melandsø Nekså
 */
@Named
@ViewScoped
public class TopicController {
    @Inject
    private PostDao postDao;
    @Inject
    private SessionManager sessionManager;
    private Post editPost;

    private Topic topic;
    private LazyPostDataModel posts;

    public LazyPostDataModel getPosts() {
        if (posts == null)
            posts = new LazyPostDataModel(postDao, topic);
        return posts;
    }

    public void updatePost(final Post post) {
        post.setLastEditedBy(sessionManager.getUser());
        postDao.updatePost(post);
        editPost = null;
    }

    public boolean isEditing(final Post post) {
        return editPost != null && editPost.equals(post);
    }

    public void edit(final Post post) {
        editPost = post;
    }

    public boolean shouldDisplayEditButton(final Post post) {
        if (isEditing(post)) {
            return false;
        }

        if (sessionManager.getIsLoggedIn()) {
            final User loggedInUser = sessionManager.getUser();
            return post.getCreatedBy().equals(loggedInUser);
        }

        return false;
    }

    public Topic getTopic() {
        return topic;
    }

    public void setTopic(final Topic topic) {
        this.topic = topic;
    }
}
