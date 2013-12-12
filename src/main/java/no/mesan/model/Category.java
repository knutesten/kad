package no.mesan.model;

/*
 * TODO
 *
 * @author Knut Esten Melandsø Nekså
 */
public class Category {
    private String name;
    private Topic lastUpdatedTopic;
    private final int id;

    public Category(final int id, final String name, final Topic lastUpdatedTopic) {
        this.id               = id;
        this.name             = name;
        this.lastUpdatedTopic = lastUpdatedTopic;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    @Override
    public boolean equals(final Object object) {
        if (object instanceof Category) {
            final Category that = (Category) object;

            boolean topicsAreEqual = lastUpdatedTopic == null && that.lastUpdatedTopic == null;
            if (lastUpdatedTopic != null)
                topicsAreEqual = lastUpdatedTopic.equals(that.lastUpdatedTopic);

            return name.equals(that.name) &&
                   id == that.id          &&
                   topicsAreEqual;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    public Topic getLastUpdatedTopic() {
        return lastUpdatedTopic;
    }

    public void setLastUpdatedTopic(final Topic lastUpdatedTopic) {
        this.lastUpdatedTopic = lastUpdatedTopic;
    }

    public int getId() {
        return id;
    }
}
