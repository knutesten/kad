package no.mesan.controllers;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import no.mesan.model.Category;

/**
 * TODO
 *
 * @author Knut Esten Melandsø Nekså
 */
@Named
@RequestScoped
public class CategoryController {
    private Category category;

    public Category getCategory() {
        return category;
    }

    public void setCategory(final Category category) {
        this.category = category;
    }
}
