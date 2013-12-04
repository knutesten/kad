package no.mesan.persistence;

import no.mesan.model.User;

import java.util.List;

/**
 * TODO
 *
 * @author Dean Lozo
 */
public interface ThreadDao {

    public void createThread(Thread thread);
    public void updateThread(Thread thread);
    public Thread getThreadbyTitle(final String title);
    public List<Thread> getThreadsByOwner(final User owner);
}
