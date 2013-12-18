package no.mesan.controllers.converters;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Inject;
import javax.inject.Named;

import no.mesan.persistence.topic.TopicDao;

/**
 * TODO
 *
 * @author Knut Esten Melandsø Nekså
 */
@Named
public class TopicConverter implements Converter {
    @Inject
    private TopicDao topicDao;

    @Override
    public Object getAsObject(final FacesContext context, final UIComponent component, final String id) {
        return topicDao.getTopicById(Integer.parseInt(id));
    }

    @Override
    public String getAsString(final FacesContext context, final UIComponent component, final Object value) {
        return null;
    }
}
