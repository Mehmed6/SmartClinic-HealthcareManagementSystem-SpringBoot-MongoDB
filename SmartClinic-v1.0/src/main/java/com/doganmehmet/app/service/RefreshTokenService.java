package com.doganmehmet.app.service;

import com.doganmehmet.app.audit.Auditable;
import com.doganmehmet.app.dto.response.AuthResponse;
import com.doganmehmet.app.entity.JwtToken;
import com.doganmehmet.app.exception.ApiException;
import com.doganmehmet.app.exception.MyError;
import com.doganmehmet.app.jwt.JWTUtil;
import com.doganmehmet.app.repository.IJwtTokenRepository;
import com.doganmehmet.app.repository.IRefreshTokenRepository;
import com.doganmehmet.app.repository.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {
    private final IRefreshTokenRepository m_refreshTokenRepository;
    private final IJwtTokenRepository m_jwtTokenRepository;
    private final JWTUtil m_jwtUtil;
    private final IUserRepository m_userRepository;

    @Auditable(
            action = "New Access and Refresh Tokens Generated",
            entity = "RefreshToken",
            description = "Access and Refresh tokens generated successfully")
    public AuthResponse refreshToken(String refreshToken)
    {
        var getRefreshToken = m_refreshTokenRepository.findByRefreshToken((refreshToken))
                .orElseThrow(() -> new ApiException(MyError.REFRESH_TOKEN_NOT_FOUND));

        if (getRefreshToken.getExpiresDate().before(new Date()))
            throw new ApiException(MyError.REFRESH_TOKEN_EXPIRED);

        var user = m_userRepository.findByRefreshToken(getRefreshToken)
                .orElseThrow(() -> new ApiException(MyError.USER_NOT_FOUND));

        m_jwtTokenRepository.deleteByAllUser(user);
        m_refreshTokenRepository.deleteAllByUser(user);


        var newRefreshToken = m_jwtUtil.generateRefreshToken(user);
        var newJwtToken = new JwtToken(m_jwtUtil.generateToken(user), user, newRefreshToken);

        m_refreshTokenRepository.save(newRefreshToken);
        m_jwtTokenRepository.save(newJwtToken);

        return new AuthResponse(newJwtToken.getJwtToken(), newRefreshToken.getRefreshToken());
    }
}
