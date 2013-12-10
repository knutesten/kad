package no.mesan.model;

import java.util.Date;

/**
 * TODO
 *
 * @author Dean Lozo
 */
public class Topic {
    private String title;
    private final User createdBy;
    private final Date createdTime;
    private final int id;

    public Topic(final int id, final String title, final User createdBy, final Date createdTime){
        this.id =id;
        this.title = title;
        this.createdBy = createdBy;
        this.createdTime = createdTime;
    }

    @Override
    public String toString(){
        return title;
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
}
