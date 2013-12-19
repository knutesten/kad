package no.mesan.manager;

import java.io.IOException;
import java.io.Serializable;
import java.util.Locale;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;

import no.mesan.model.User;
import no.mesan.model.UserSettings;

@Named
@SessionScoped
public class SessionManager implements Serializable {
    private static final long serialVersionUID = 1L;

    private HttpSession httpSession;
    private Locale guestLocale;
    private int guestPostsPerPage;

    @PostConstruct
    public void init() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        httpSession = (HttpSession) facesContext.getExternalContext().getSession(false);
        guestLocale = new Locale("no");
        guestPostsPerPage = 10;
    }

     public User getUser() {
        try {
            return (User) httpSession.getAttribute("user");
        } catch (Exception e) {
            return null;
        }
    }
     
    private void setUser(final User user) {
        httpSession.setAttribute("user", user);
    }
    
    public UserSettings getUserSettings() {
        try {
            return (UserSettings) httpSession.getAttribute("userSettings");
        } catch (Exception e) {
            return null;
        }
    }
    
    private void setUserSettings(final UserSettings userSettings) {
        httpSession.setAttribute("userSettings", userSettings);
    }

    public String getUsername() {
        final User user = getUser();
        if (user == null)
            return "Guest";
        return getUser().getUsername();
    }

    public Locale getLocale() {
        final User user = getUser();
        if (user == null)
            return guestLocale;
        Locale locale = getUser().getLocale();
        if (locale == null)
            return guestLocale;
        return locale;
    }

    public void setLocale(final Locale locale) {
        final User user = getUser();
        if (user == null) {
            guestLocale = locale;
            return;
        }
        user.setLocale(locale);
        setUser(user);
    }

    public int getPostsPerPage() {
        final UserSettings userSettings = getUserSettings();
        if (userSettings == null)
            return guestPostsPerPage;
        return userSettings.getPostsPerPage();
    }

    public void setPostsPerPage(final int postsPerPage) {
        final UserSettings userSettings = getUserSettings();
        if (userSettings == null) {
            this.guestPostsPerPage = postsPerPage;
            return;
        }
        userSettings.setPostsPerPage(postsPerPage);
        setUserSettings(userSettings);
    }

    public void logout() throws ServletException, IOException {
        httpSession.invalidate();
        final ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
        context.redirect(context.getRequestContextPath());
    }

    public boolean getIsLoggedIn() {
        return getUser() != null;
    }
}
