package lab.aikibo.filter;

import lab.aikibo.service.TokenAuthService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Created by tamami on 05/02/17.
 */
public class TokenAuthFilter extends GenericFilterBean {

    private final TokenAuthService authService;

    public TokenAuthFilter(TokenAuthService authService) {
        this.authService = authService;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
            throws IOException, ServletException {
        Authentication auth = authService.getAuthentication((HttpServletRequest) request);
        SecurityContextHolder.getContext().setAuthentication(auth);
        filterChain.doFilter(request, response);
        SecurityContextHolder.getContext().setAuthentication(null);
    }

}
