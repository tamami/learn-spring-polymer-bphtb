package lab.aikibo.auth;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * Created by tamami on 05/02/17.
 */
public class UserAuthentication implements Authentication {

    private final TokenUser user;
    private boolean authenticated = true;

    public UserAuthentication(TokenUser user) {
        this.user = user;
    }

    @Override
    public String getName() {
        return user.getNmLogin();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return user.getAuthorities();
    }

    @Override
    public Object getCredentials() {
        return user.getPassword();
    }

    @Override
    public TokenUser getDetails() {
        return user;
    }

    @Override
    public Object getPrincipal() {
        return user.getNmLogin();
    }

    @Override
    public boolean isAuthenticated() {
        return authenticated;
    }

    @Override
    public void setAuthenticated(boolean authenticated) {
        this.authenticated = authenticated;
    }

}
