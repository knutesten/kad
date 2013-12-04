package no.mesan.persistence;

import no.mesan.model.User;

import java.util.List;
import no.mesan.model.ForumThread;

/**
 * TODO
 *
 * @author Dean Lozo
 */
public interface forumThreadDao {

    public void createThread(ForumThread forumThread);
    public void updateThread(ForumThread forumThread);
    public ForumThread getThreadbyTitle(final String title);
    public List<ForumThread> getThreadsByOwner(final User owner);
}
