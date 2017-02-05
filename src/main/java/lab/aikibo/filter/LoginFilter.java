package lab.aikibo.filter;

import lab.aikibo.auth.TokenUser;
import lab.aikibo.service.TokenAuthService;
import lab.aikibo.util.Encrypt;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * Created by tamami on 05/02/17.
 */
public class LoginFilter extends AbstractAuthenticationProcessingFilter {

    private TokenAuthService tokenAuthService;

    public LoginFilter(String urlMapping, AuthenticationManager authManager, TokenAuthService tokenAuthService) {
        super(new AntPathRequestMatcher(urlMapping));
        setAuthenticationManager(authManager);
        this.tokenAuthService = tokenAuthService;
    }

    /**
     *
     * @param request
     * @param response
     * @return
     * @throws AuthenticationException
     * @throws IOException
     * @throws ServletException
     * @TODO request.getParameter("password") harusnya di encrypt, bisa dilihat di https://github.com/tamami/i-bphtb/blob/master/src/lab/aikibo/util/Encrypt.java
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException, IOException, ServletException {
        return getAuthenticationManager()
                .authenticate(new UsernamePasswordAuthenticationToken(
                    request.getParameter("username"), request.getParameter("password")
                ));
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                            FilterChain chain, Authentication auth) throws IOException, ServletException {
        tokenAuthService.addAuthentication(response, (TokenUser) auth.getPrincipal());
        SecurityContextHolder.getContext().setAuthentication(auth);
    }

}
