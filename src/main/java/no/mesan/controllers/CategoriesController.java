package no.mesan.controllers;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import no.mesan.model.Category;
import no.mesan.persistence.category.CategoryDao;


/**
 * TODO
 *
 * @author Knut Esten Melandsø Nekså
 */
@Named
@RequestScoped
public class CategoriesController {
    private List<Category> categories;

    @Inject
    private CategoryDao categoryDao;

    @PostConstruct
    public void populateCategories() {
        categories = categoryDao.getCategories();
    }

    public List<Category> getCategories() {
        return categories;
    }
}
