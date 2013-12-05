package no.mesan.filters;

import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import no.mesan.manager.UserManager;
import no.mesan.model.User;
import no.mesan.persistence.user.UserDao;

@WebFilter("/*")
public class SessionFilter implements Filter {
    @Inject
    private UserDao userDao;
    
    @Inject
    private UserManager userManager;
    
    
    @Override
    public void doFilter(final ServletRequest servletRequest, 
                         final ServletResponse servletResponse, 
                         final FilterChain filterChain) 
                                 throws IOException, ServletException {
        final HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        final String username = httpServletRequest.getRemoteUser();
//        final String username = httpServletRequest.getUserPrincipal().getName();
        System.out.println("RUNNING FILTER");
        
        if (username != null) {
            HttpSession httpSession = httpServletRequest.getSession();
            System.out.println("Username exists: " + username);
            userManager.setUsername(username);
            if (httpSession.getAttribute("user") == null) {
                System.out.println("CREATING user attribute in session");
                User user = userDao.getUserByUsername(username);
                httpSession.setAttribute("user", user);
            }
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void init(final FilterConfig filterConfig) throws ServletException {
    }
    
    @Override
    public void destroy() {
    }
}
