package no.mesan.controllers.converters;

import java.util.HashMap;
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
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
public class CategoryConverter implements Converter {
    @Inject
    private CategoryDao categoryDao;

    @Override
    public Object getAsObject(final FacesContext context, final UIComponent component, final String name) {
        return categoryDao.getCategoryByName(name);
    }

    @Override
    public String getAsString(final FacesContext context, final UIComponent component, final Object value) {
        return null;
    }
}
