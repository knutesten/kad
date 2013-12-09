package no.mesan.controllers;

import java.util.Date;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import no.mesan.model.Post;
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
    
    //TODO this is just a test
    public void createPost() {
        User createdBy = userDao.getUserByUsername("admin");
        Date createdTime = new Date();
        String content = "Dette er jo en ny flott testpost fra admin hestemannen";
        Post post = new Post(createdBy, createdTime, content);
        
        int postId = postDao.createPost(post);
        System.out.println("New POST with id: " + postId);
        //Stuff must be done about such things as what kind of topic its in and ....
        
    }
}
