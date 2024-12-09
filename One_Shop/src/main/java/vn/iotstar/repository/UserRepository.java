package vn.iotstar.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import vn.iotstar.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);

    @Query("SELECT u FROM User u WHERE u.role != '0'")
    List<User> findUsersWithRole();

    public Boolean existsByEmail(String email);

    public User findUserByEmail(String email);

    public User findByResetToken(String token);

}
