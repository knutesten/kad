package no.mesan.controllers;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import no.mesan.model.LazyPostDataModel;
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

    private Topic topic;
    private LazyPostDataModel posts;

    public LazyPostDataModel getPosts() {
        if (posts == null)
            posts = new LazyPostDataModel(postDao, topic);
        return posts;
    }

    public Topic getTopic() {
        return topic;
    }

    public void setTopic(final Topic topic) {
        this.topic = topic;
    }
}
