package no.mesan.model;

/**
 * TODO
 *
 * @author Anders Grotthing Moe
 */
public class UserSettings {
    private final int userId;
    private int postsPerPage;
    
    public UserSettings(final int userId) {
        this.userId = userId;
        this.postsPerPage = 10;
    }
    
    public UserSettings(final int userId, final int postsPerPage) {
        this.userId = userId;
        this.postsPerPage = postsPerPage;
    }
    
    public int getUserId() {
        return userId;
    }
    
    public int getPostsPerPage() {
        return postsPerPage;
    }

    public void setPostsPerPage(int postsPerPage) {
        this.postsPerPage = postsPerPage;
    }
}
