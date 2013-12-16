package no.mesan.controllers;

import javax.enterprise.context.RequestScoped;
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
@RequestScoped
public class PostController {
    @Inject
    private UserDao userDao;
    @Inject
    private PostDao postDao;
    
    private Topic topic;
    
    //TODO this is just a test
    public void createPost() {
        User createdBy = userDao.getUserByUsername("admin");
        String content = "Dette er jo en ny flott testpost fra admin hestemannen";
        Post post = new Post(createdBy, content);
        
        postDao.createPost(post, topic);
        System.out.println("New POST with id: " + post.getId());
    }

    public Topic getTopic() {
        return topic;
    }

    public void setTopic(final Topic topic) {
        this.topic = topic;
    }
}
