package no.mesan.model;

import java.util.ArrayList;
import java.util.List;

/**
 * TODO
 *
 * @author Knut Esten Melandsø Nekså
 */
public class Category {
    private String name;
    private final List<Topic> topics;

    public Category(final String name, final List<Topic> topics) {
        this.name   = name;
        this.topics = topics;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public List<Topic> getTopics() {
        return new ArrayList<>(topics);
    }
}
