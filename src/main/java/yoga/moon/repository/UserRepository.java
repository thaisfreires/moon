package yoga.moon.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import yoga.moon.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
