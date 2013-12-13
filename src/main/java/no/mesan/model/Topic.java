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
    private Integer id;

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
}
