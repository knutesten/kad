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
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import no.mesan.model.User;
import no.mesan.persistence.user.UserDao;

@WebFilter("/*")
public class SessionFilter implements Filter {
    @Inject
    private UserDao userDao;

    @Override
    public void doFilter(final ServletRequest servletRequest,
                         final ServletResponse servletResponse,
                         final FilterChain filterChain)
                                 throws IOException, ServletException {
        final HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        final String username = httpServletRequest.getRemoteUser();

        if (username != null) {
            final HttpSession httpSession = httpServletRequest.getSession();
            if (httpSession.getAttribute("user") == null) {
                User user = userDao.getUserByUsername(username);
                httpSession.setAttribute("user", user);
            }
        }

        // Hack to make login redirect to some place else than /secure/index.jsf
        if ((((HttpServletRequest) servletRequest).getRequestURI()).equals("/secure/index.jsf"))
            (((HttpServletResponse) servletResponse)).sendRedirect("../index.jsf");
        else
            filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void init(final FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void destroy() {
    }
}
