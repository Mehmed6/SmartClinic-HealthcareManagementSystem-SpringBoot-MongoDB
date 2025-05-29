package com.doganmehmet.app.repository;

import com.doganmehmet.app.entity.RefreshToken;
import com.doganmehmet.app.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IUserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    Optional<User> findByRefreshToken(RefreshToken refreshToken);

    Optional<User> findByEmail(String email);
}
