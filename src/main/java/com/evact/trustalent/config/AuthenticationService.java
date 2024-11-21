package com.evact.trustalent.config;

import com.evact.trustalent.common.base.BaseService;
import com.evact.trustalent.common.dto.TokenInfo;
import com.evact.trustalent.common.enums.TokenType;
import com.evact.trustalent.common.exception.BadRequestException;
import com.evact.trustalent.dto.request.AuthenticationRequest;
import com.evact.trustalent.dto.response.AuthenticationResponse;
import com.evact.trustalent.entity.TokenEntity;
import com.evact.trustalent.entity.UserEntity;
import com.evact.trustalent.repository.TokenRepository;
import com.evact.trustalent.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthenticationService extends BaseService {

    private final UserRepository repository;
    private final TokenRepository tokenRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

        var user = repository.findByUsername(request.getUsername()).orElseThrow();
        TokenInfo jwtToken = jwtService.generateToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, jwtToken.getToken());

        List<String> roles = user.getUserRoles().stream()
                .map(userRole -> userRole.getRole().name())
                .collect(Collectors.toList());

        return AuthenticationResponse.builder()
                .token(jwtToken.getToken())
                .userInfo(
                        AuthenticationResponse.UserInfo.builder()
                                .username(user.getUsername())
                                .email(user.getEmail())
                                .name(user.getName())
                                .roles(roles)
                                .build()
                )
                .build();
    }

    public AuthenticationResponse refreshToken(String token) {

        String username = jwtService.extractUsername(token.substring(7));
        TokenInfo jwtToken;

        if (!username.isEmpty()) {
            var user = repository.findByUsername(username)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));
            jwtToken = jwtService.generateToken(user);
            revokeAllUserTokens(user);
            saveUserToken(user, jwtToken.getToken());
        } else {
            throw new BadRequestException("Invalid token!");
        }

        return AuthenticationResponse.builder().token(jwtToken.getToken()).build();
    }

    private void saveUserToken(UserEntity user, String jwtToken) {
        var token = TokenEntity.builder().user(user).token(jwtToken).tokenType(TokenType.BEARER).expired(false)
                .revoked(false).build();
        tokenRepository.save(token);
    }

    private void revokeAllUserTokens(UserEntity user) {
        var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }
}
