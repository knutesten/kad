package no.mesan.controllers;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import no.mesan.model.Category;
import no.mesan.model.LazyTopicDataModel;
import no.mesan.persistence.topic.TopicDao;

/**
 * TODO
 *
 * @author Knut Esten Melandsø Nekså
 */
@Named
@ViewScoped
public class CategoryController {
    private Category category;
    private LazyTopicDataModel topics;

    @Inject
    private TopicDao topicDao;

    public Category getCategory() {
        return category;
    }

    public void setCategory(final Category category) {
        this.category = category;
    }

    public LazyTopicDataModel getTopics() {
        if (topics == null) {
            topics = new LazyTopicDataModel(topicDao, category);
        }
        return topics;
    }
}
