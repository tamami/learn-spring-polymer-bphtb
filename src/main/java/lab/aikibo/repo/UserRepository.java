package lab.aikibo.repo;

import lab.aikibo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Created by tamami on 04/02/17.
 */
public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findOneByNmLogin(String nmLogin);
}
