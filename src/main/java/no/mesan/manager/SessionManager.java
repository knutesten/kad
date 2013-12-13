package no.mesan.manager;

import java.io.Serializable;
import java.util.Locale;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;

import no.mesan.model.User;

@Named
@SessionScoped
public class SessionManager implements Serializable {
    private static final long serialVersionUID = 1L;

    private HttpSession httpSession;
    private Locale guestLocale;
    private int postsPerPage;

    @PostConstruct
    public void init() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        httpSession = (HttpSession) facesContext.getExternalContext().getSession(false);
        guestLocale = new Locale("no");
        postsPerPage = 10;
    }

     public User getUser() {
        try {
            return (User) httpSession.getAttribute("user");
        } catch (Exception e) {
            return null;
        }
    }

    private void setUser(User user) {
        httpSession.setAttribute("user", user);
    }

    public String getUsername() {
        User user = getUser();
        if (user == null)
            return "Guest";
        return getUser().getUsername();
    }

    public Locale getLocale() {
        User user = getUser();
        if (user == null)
            return guestLocale;
        Locale locale = getUser().getLocale();
        if (locale == null)
            return guestLocale;
        return locale;
    }

    public void setLocale(final Locale locale) {
        User user = getUser();
        if (user == null)
            guestLocale = locale;
        user.setLocale(locale);
        setUser(user);
    }

    public int getPostsPerPage() {
        //TODO Check users postPerPage setting when it is implemented
        return postsPerPage;
    }

    public void setPostsPerPage(final int postsPerPage) {
        //TODO Set users postPerPage setting if this is not a guest session.
        this.postsPerPage = postsPerPage;
    }

    public String logout() throws ServletException{
        httpSession.invalidate();
        return "index?faces-redirect=true";
    }

    public boolean getIsLoggedIn() {
        return getUser() != null;
    }
}
