package com.doganmehmet.app.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "refresh_tokens")
public class RefreshToken extends BaseEntity{

    private String refreshToken;

    @Column(name = "expired_date", nullable = false)
    private Date expiresDate;

    @OneToOne(mappedBy = "refreshToken", cascade = CascadeType.ALL, orphanRemoval = true)
    private JwtToken jwtToken;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
