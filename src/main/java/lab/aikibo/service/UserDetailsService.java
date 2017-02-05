package lab.aikibo.service;

import lab.aikibo.auth.TokenUser;
import lab.aikibo.model.User;
import lab.aikibo.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AccountStatusUserDetailsChecker;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Created by tamami on 04/02/17.
 */
@Service
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    @Autowired
    private UserRepository userRepo;

    private final AccountStatusUserDetailsChecker detailsChecker = new AccountStatusUserDetailsChecker();

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        final User user = userRepo.findOneByNmLogin(s).orElseThrow(() -> new UsernameNotFoundException(""));

        TokenUser currentUser = new TokenUser(user);
        detailsChecker.check(currentUser);

        return currentUser;
    }
}
