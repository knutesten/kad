package no.mesan.controllers;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import no.mesan.manager.SessionManager;
import no.mesan.model.LazyPostDataModel;
import no.mesan.model.Post;
import no.mesan.model.Topic;
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
    private String editorValue;

    public LazyPostDataModel getPosts() {
        if (posts == null)
            posts = new LazyPostDataModel(postDao, topic);
        return posts;
    }

    public void updatePost(final Post post) {
        post.setLastEditedBy(sessionManager.getUser());
        postDao.updatePost(post);
    }

    public boolean isEditing(final Post post) {
        return editPost.equals(post);
    }

    public void edit(final Post post) {
        editPost = post;
    }

    public Topic getTopic() {
        return topic;
    }

    public void setTopic(final Topic topic) {
        this.topic = topic;
    }

    public String getEditorValue() {
        return editorValue;
    }

    public void setEditorValue(final String editorValue) {
        this.editorValue = editorValue;
    }
}
