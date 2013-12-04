package no.mesan.authentication;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import no.mesan.persistence.user.UserDao;

/**
 * TODO
 *
 * @author Knut Esten Melandsø Nekså
 */
@Named
@RequestScoped
public class Test {
    @Inject
    private UserDao userDao;
    public String getUser() {
//        if (FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal() == null)
//            return "null";
//        return FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal().getName();
        return userDao.getUserByUsername("admin").toString();
    }
}
