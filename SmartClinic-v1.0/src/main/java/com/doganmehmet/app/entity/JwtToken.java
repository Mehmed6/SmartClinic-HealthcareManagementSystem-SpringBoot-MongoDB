package com.doganmehmet.app.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "jwt_tokens")
public class JwtToken extends BaseEntity{

    @Column(unique = true, nullable = false)
    private String jwtToken;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "refresh_token_id", nullable = false)
    private RefreshToken refreshToken;
}
