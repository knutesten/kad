package no.mesan.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import no.mesan.manager.SessionManager;
import no.mesan.model.Category;
import no.mesan.model.Topic;
import no.mesan.persistence.category.CategoryDao;
import no.mesan.persistence.topic.TopicDao;

/**
 * TODO
 *
 * @author Knut Esten Melandsø Nekså
 */

@Named
@RequestScoped
public class FrontPageController {
    private List<Category> categories;
    private final Map<Category, List<Topic>> topicsInCategoryCache = new HashMap<>();

    @Inject
    private CategoryDao categoryDao;
    @Inject
    private TopicDao topicDao;

    @PostConstruct
    public void initialize() {
        categories = categoryDao.getCategories();
    }

    public List<Category> getCategories() {
        return categories;
    }

    public List<Topic> getTopicsByCategory(final Category category) {
        return getTopicsByCategoryCache(category);
    }

    public boolean disableTab(final Category category) {
        return getTopicsByCategoryCache(category).isEmpty();
    }

    private List<Topic> getTopicsByCategoryCache(final Category category) {
        if (!topicsInCategoryCache.containsKey(category))
            topicsInCategoryCache.put(category, topicDao.getTopicsByCategory(category));
        return topicsInCategoryCache.get(category);
    }

    @Inject
    private SessionManager sessionManager;
    public void addSomeTopics() {
        for (int i = 1; i < 1_000_000; i++) {
            final Topic topic = new Topic("hest " + i, sessionManager.getUser());
            topicDao.createTopic(topic, categories.get(0));
        }
    }
}
