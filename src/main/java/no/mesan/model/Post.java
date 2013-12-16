package no.mesan.model;

import java.util.Date;

/**
 * TODO
 *
 * @author Anders Grotthing Moe
 */
public class Post {
    private int id;
    private final User createdBy;
    private final Date createdTime;
    private User lastEditedBy;
    private Date lastEditedTime;
    private String content;
    
    public Post(final User createdBy, final String content) {
        this.createdBy = createdBy;
        this.createdTime = new Date();
        this.content = content;
    }
    
    public Post(final int id, final User createdBy, final Date createdTime, 
                final User lastEditedBy, final Date lastEditedTime, final String content) {
        this.id = id;
        this.createdBy = createdBy;
        this.createdTime = createdTime;
        this.lastEditedBy = lastEditedBy;
        this.lastEditedTime = lastEditedTime;
        this.content = content;

    }

    public User getLastEditedBy() {
        return lastEditedBy;
    }

    public void setLastEditedBy(final User lastEditedBy) {
        this.lastEditedBy = lastEditedBy;
    }

    public Date getLastEditedTime() {
        return lastEditedTime;
    }

    public void setLastEditedTime(final Date lastEditedTime) {
        this.lastEditedTime = lastEditedTime;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(final String content) {
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public void setId(final int id) {
        this.id = id;
    }
}
