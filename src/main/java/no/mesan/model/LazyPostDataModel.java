package no.mesan.model;

import java.util.List;
import java.util.Map;

import no.mesan.persistence.post.PostDao;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

/**
 * TODO
 *
 * @author Knut Esten Melandsø Nekså
 */
@SuppressWarnings("serial")
public class LazyPostDataModel extends LazyDataModel<Post> {
    private final PostDao postDao;
    private final Topic topic;

    public LazyPostDataModel(final PostDao postDao, final Topic topic) {
        this.postDao = postDao;
        this.topic = topic;
    }

    @Override
    public int getRowCount() {
        return postDao.getNumberOfPostsInTopic(topic);
    }

    @Override
    public List<Post> load(final int first, final int pageSize, final String sortField, final SortOrder sortOrder, final Map<String, String> filters) {
        return postDao.getLimitedPostsByTopic(topic, first, pageSize);
    }
}
