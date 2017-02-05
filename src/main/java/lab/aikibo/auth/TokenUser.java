package lab.aikibo.auth;

import lab.aikibo.model.User;
import lombok.Getter;
import org.springframework.security.core.authority.AuthorityUtils;

/**
 * Created by tamami on 04/02/17.
 */
public class TokenUser extends org.springframework.security.core.userdetails.User {

    @Getter
    private User user;

    public TokenUser(User user) {
        super(user.getNmLogin(), user.getPassword(),
            AuthorityUtils.createAuthorityList(user.getNmLogin().toString()));
        this.user = user;
    }

    public String getNmLogin() {
        return user.getNmLogin();
    }

}
