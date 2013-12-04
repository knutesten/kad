package no.mesan.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * TODO
 *
 * @author Dean Lozo
 */
public class ForumThread {

    private String title;
    private String category;
    private User owner;
    private final Date createdTime;
    private List<Post> posts = new ArrayList<>();


    public ForumThread(final String title, final String category, final User owner, final Date createdTime){
        this.title = title;
        this.category = category;
        this.owner = owner;
        this.createdTime = createdTime;
    }

    @Override
    public String toString(){
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle(){
        return title;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public User getOwner() {
        return owner;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public List<Post> getPosts() {
        return new ArrayList<>(posts);
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    public void addPost(Post p){
        posts.add(p);
    }

    public void removePost(Post p){
        posts.remove(p);
    }

}
