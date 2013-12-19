package no.mesan.controllers;

import java.io.IOException;

import javax.faces.view.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import no.mesan.model.User;
import no.mesan.model.UserSettings;
import no.mesan.persistence.user.UserDao;

/**
 * TODO
 *
 * @author Knut Esten Melandsø Nekså
 */
@Named
@ViewScoped
public class LoginController {
    private String username;
    private String password;

    @Inject
    private UserDao userDao;

    public void login() throws IOException {
        final ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
        final HttpServletRequest request = (HttpServletRequest) context.getRequest();
        try {
            request.login(username, password);
            final User user = userDao.getUserByUsername(username);
            context.getSessionMap().put("user", user);
            final UserSettings userSettings = userDao.getUserSettingsByUser(user);
            context.getSessionMap().put("userSettings", userSettings);
        } catch (ServletException se) {
            System.out.println(se.getMessage());
        }
        context.redirect(context.getApplicationContextPath());
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(final String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(final String password) {
        this.password = password;
    }
}
