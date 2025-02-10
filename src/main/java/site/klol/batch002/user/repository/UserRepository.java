package site.klol.batch002.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.klol.batch002.user.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
