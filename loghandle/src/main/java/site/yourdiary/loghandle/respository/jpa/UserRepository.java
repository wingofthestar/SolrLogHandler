package site.yourdiary.loghandle.respository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import site.yourdiary.loghandle.entity.jpa.User;

public interface UserRepository extends JpaRepository<User, String> {
    User findUserByUserNameAndPassword(String userName, String password);
}
