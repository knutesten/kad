package no.mesan.model;

import java.util.List;
import java.util.Map;

import no.mesan.persistence.topic.TopicDao;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;

/**
 * TODO
 *
 * @author Knut Esten Melandsø Nekså
 */
public class LazyTopicDataModel extends LazyDataModel<Topic> {
    private final TopicDao topicDao;
    private final Category category;

    public LazyTopicDataModel(final TopicDao topicDao, final Category category) {
        this.topicDao = topicDao;
        this.category = category;
    }

    @Override
    public List<Topic> load(final int first, final int pageSize,
                            final List<SortMeta> multiSortMeta, final Map<String, String> filters) {
        return topicDao.getLimitedTopicsByCategory(category, first, pageSize);
    }

    @Override
    public int getRowCount() {
        return 1_000_036;
    }
}
