package no.mesan.persistence.category;

import java.util.List;

import no.mesan.model.Category;

/**
 * TODO
 *
 * @author Knut Esten Melandsø Nekså
 */
public interface CategoryDao {
    public List<Category> getCategories();
    public Category getCategoryByName(final String name);
}
