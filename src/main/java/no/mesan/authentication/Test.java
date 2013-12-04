package no.mesan.authentication;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;

/**
 * TODO
 *
 * @author Knut Esten Melandsø Nekså
 */
@Named
@RequestScoped
public class Test {
    public String getUser() {
        if (FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal() == null)
            return "null";
        return FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal().getName();
    }
}
