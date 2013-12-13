package no.mesan.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import no.mesan.model.Category;
import no.mesan.model.LazyTopicDataModel;
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
    private final Map<Category, LazyTopicDataModel> topicsInCategoryCache = new HashMap<>();

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

    public LazyTopicDataModel getTopicsByCategory(final Category category) {
        return getTopicsByCategoryCache(category);
    }

    public boolean disableTab(final Category category) {
        return getTopicsByCategoryCache(category).getRowCount() == 0;
    }

    private LazyTopicDataModel getTopicsByCategoryCache(final Category category) {
        if (!topicsInCategoryCache.containsKey(category))
            topicsInCategoryCache.put(category, new LazyTopicDataModel(topicDao, category));
        return topicsInCategoryCache.get(category);
    }
}
