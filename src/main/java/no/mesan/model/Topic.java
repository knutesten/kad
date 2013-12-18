package no.mesan.model;

import java.io.Serializable;
import java.util.Date;

/**
 * TODO
 *
 * @author Dean Lozo
 */
@SuppressWarnings("serial")
public class Topic implements Serializable {
    private Integer id;
    private String title;
    private final User createdBy;
    private final Date createdTime;
    private int numberOfPosts;
    private Post lastPost;

    public Topic(final String title, final User createdBy) {
        this(null, title, createdBy, new Date());
    }

    public Topic(final Integer id, final String title, final User createdBy, final Date createdTime){
        this.id =id;
        this.title = title;
        this.createdBy = createdBy;
        this.createdTime = createdTime;
    }

    @Override
    public String toString(){
        return title;
    }

    @Override
    public boolean equals(final Object object) {
        if (object instanceof Topic) {
            final Topic that = (Topic) object;
            return title.equals(that.title)             &&
                   createdBy.equals(that.createdBy)     &&
                   createdTime.equals(that.createdTime) &&
                   id.equals(that.id);
        }
        return false;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    public String getTitle(){
        return title;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public int getId() {
        return id;
    }

    public void setId(final int id) {
        this.id = id;
    }

    public Post getLastPost() {
        return lastPost;
    }

    public void setLastPost(final Post lastPost) {
        this.lastPost = lastPost;
    }

    public int getNumberOfPosts() {
        return numberOfPosts;
    }

    public void setNumberOfPosts(final int numberOfPosts) {
        this.numberOfPosts = numberOfPosts;
    }
}
