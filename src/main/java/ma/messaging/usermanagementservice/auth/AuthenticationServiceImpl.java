package ma.messaging.usermanagementservice.auth;

import lombok.RequiredArgsConstructor;
import ma.messaging.usermanagementservice.auth.login.LoginRequest;
import ma.messaging.usermanagementservice.auth.login.LoginResponse;
import ma.messaging.usermanagementservice.auth.login.LoginResponseMessage;
import ma.messaging.usermanagementservice.auth.register.RegisterRequest;
import ma.messaging.usermanagementservice.auth.register.RegisterResponse;
import ma.messaging.usermanagementservice.config.JwtUtil;
import ma.messaging.usermanagementservice.model.Account;
import ma.messaging.usermanagementservice.model.dto.AccountDTO;
import ma.messaging.usermanagementservice.repository.AccountRepository;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@CacheConfig(cacheNames = {"tokens"})
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final AccountRepository accountRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;
    private final CacheManager cacheManager;
    private final AuthenticationManager authManager;

    @Override
    public RegisterResponse register(RegisterRequest request) {
        if (accountRepository.existsByUsername(request.getUsername())) {
            return RegisterResponse.USERNAME_EXISTS;
        }
        if (accountRepository.existsByEmail(request.getEmail())) {
            return RegisterResponse.EMAIL_EXISTS;
        }

        Account account = new Account(
                request.getUsername(),
                passwordEncoder.encode(request.getPassword()),
                request.getFirstName(),
                request.getLastName(),
                request.getEmail());

        accountRepository.save(account);

        return RegisterResponse.REGISTERED;
    }

    @Override
    public LoginResponse login(LoginRequest request) {
        LoginResponse response = new LoginResponse();

        Account user = accountRepository.findByUsername(request.getUsername());
        if (user == null || !passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            response.setResponseMsg(LoginResponseMessage.FAILED);
            return response;
        }

        // generate token
        String token = jwtUtil.generateToken(AccountDTO.fromEntity(user));

        // save token to cache
        saveToCache(request.getUsername(), token);

        // set response
        response.setToken(token);
        response.setResponseMsg(LoginResponseMessage.LOGGED_IN);

        return response;
    }

    @CachePut(key = "#username")
    public String saveToCache(String username, String token) {
        return token;
    }

    // Token verification and validation
    //cacheManager.getCache("tokens").get("john")
    public AuthenticationResponse refreshToken(String authHeader) {
        final String refreshToken = authHeader.substring(7);
        final String tokenUsername = jwtUtil.extractUsername(refreshToken);

        if (tokenUsername != null) {
            AccountDTO user = AccountDTO.fromEntity(accountRepository.findByUsername(tokenUsername));
            AuthenticationResponse authResponse = new AuthenticationResponse();

            if (jwtUtil.isTokenValid(refreshToken, user) && cacheManager.getCache("tokens").get(user.getUsername()).equals(refreshToken)) {
                authResponse.setTokenValid(true);
            } else {
                // generate token
                String token = jwtUtil.generateToken(user);

                // save token to cache
                saveToCache(user.getUsername(), token);

                // set response
                authResponse.setTokenValid(false);
                authResponse.setRefreshToken(token);
            }

            return authResponse;
        }

        // No username found for the given authorization header
        return new AuthenticationResponse(false, null);
    }

}
