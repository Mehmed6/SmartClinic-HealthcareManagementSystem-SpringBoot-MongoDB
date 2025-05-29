package com.doganmehmet.app.service;

import com.doganmehmet.app.dto.request.AuthRequest;
import com.doganmehmet.app.dto.response.AuthResponse;
import com.doganmehmet.app.entity.JwtToken;
import com.doganmehmet.app.exception.ApiException;
import com.doganmehmet.app.exception.MyError;
import com.doganmehmet.app.jwt.JWTUtil;
import com.doganmehmet.app.repository.IJwtTokenRepository;
import com.doganmehmet.app.repository.IRefreshTokenRepository;
import com.doganmehmet.app.repository.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserDetailsService m_userDetailsService;
    private final JWTUtil m_jwtUtil;
    private final AuthenticationManager m_authenticationManager;
    private final IUserRepository m_userRepository;
    private final IJwtTokenRepository m_jwtTokenRepository;
    private final IRefreshTokenRepository m_refreshTokenRepository;

    public AuthResponse login(AuthRequest authRequest)
    {
        var user = m_userRepository.findByUsername(authRequest.getUsername())
                .orElseThrow(() -> new ApiException(MyError.USER_NOT_FOUND));

        try {
            var auth = new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword());
            m_authenticationManager.authenticate(auth);
        }
        catch (Exception ignored) {
            throw new ApiException(MyError.PASSWORD_INCORRECT);
        }

        var token = m_jwtTokenRepository.findByUser(user);
        var userDetails = m_userDetailsService.loadUserByUsername(user.getUsername());

        if (token.isPresent()) {
            if (m_jwtUtil.isValidToken(token.get().getJwtToken(), userDetails))
                return new AuthResponse(token.get().getJwtToken(), token.get().getRefreshToken().getRefreshToken());

            m_jwtTokenRepository.deleteByAllUser(user);
            m_refreshTokenRepository.deleteAllByUser(user);
        }

        var newJwtToken = m_jwtUtil.generateToken(user);
        var newRefreshToken = m_jwtUtil.generateRefreshToken(user);

        m_refreshTokenRepository.save(newRefreshToken);
        m_jwtTokenRepository.save(new JwtToken(newJwtToken, user, newRefreshToken));

        return new AuthResponse(newJwtToken, newRefreshToken.getRefreshToken());
    }
}
