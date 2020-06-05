package ru.itis.kpfu.sweater.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itis.kpfu.sweater.domains.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
