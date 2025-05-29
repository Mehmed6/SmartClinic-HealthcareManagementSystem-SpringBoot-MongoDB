package com.doganmehmet.app.repository;

import com.doganmehmet.app.entity.RefreshToken;
import com.doganmehmet.app.entity.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IRefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    @Transactional
    @Modifying
    @Query("DELETE FROM RefreshToken r WHERE r.user = :user")
    void deleteAllByUser(User user);

    boolean existsRefreshTokenByRefreshToken(String refreshToken);

    Optional<RefreshToken> findByRefreshToken(String refreshToken);
}
