package ma.messaging.authservice.auth;

import ma.messaging.authservice.model.Account;
import ma.messaging.authservice.model.Role;
import ma.messaging.authservice.payload.requests.LoginRequest;
import ma.messaging.authservice.payload.requests.RegisterRequest;
import ma.messaging.authservice.payload.responses.MessageResponse;
import ma.messaging.authservice.repository.AccountRepository;
import ma.messaging.authservice.repository.RoleRepository;
import ma.messaging.authservice.security.jwt.JwtUtils;
import ma.messaging.authservice.security.services.UserDetailsImpl;
import ma.messaging.authservice.service.AccountServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class AuthServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtUtils jwtUtils;

    @InjectMocks
    private AccountServiceImpl accountService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void userRegister_Success() {
        // Arrange
        RegisterRequest request = new RegisterRequest(
                "username",
                "password", "firstName",
                "lastName",
                "email@example.com",
                new HashSet<>(Set.of("user")));
        when(accountRepository.existsByUsername(anyString())).thenReturn(false);
        when(accountRepository.existsByEmail(anyString())).thenReturn(false);
        when(roleRepository.findByName(any())).thenReturn(java.util.Optional.of(new Role()));

        // Act
        ResponseEntity<?> response = accountService.userRegister(request);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(accountRepository).save(any(Account.class));
    }

    @Test
    void userRegister_UsernameAlreadyExists() {
        // Arrange
        RegisterRequest request = new RegisterRequest(
                "username",
                "password", "firstName",
                "lastName",
                "email@example.com",
                new HashSet<>(Set.of("user")));
        when(accountRepository.existsByUsername(request.getUsername())).thenReturn(true);

        // Act
        ResponseEntity<?> response = accountService.userRegister(request);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        Assertions.assertInstanceOf(MessageResponse.class, response.getBody());
        assertEquals("Error: Username is already taken!", ((MessageResponse) response.getBody()).getMessage());
        verify(accountRepository, never()).save(any(Account.class));
    }

    @Test
    void userRegister_EmailAlreadyInUse() {
        // Arrange
        RegisterRequest request = new RegisterRequest(
                "username",
                "password", "firstName",
                "lastName",
                "email@example.com",
                new HashSet<>(Set.of("user")));
        when(accountRepository.existsByUsername(request.getUsername())).thenReturn(false);
        when(accountRepository.existsByEmail(request.getEmail())).thenReturn(true);

        // Act
        ResponseEntity<?> response = accountService.userRegister(request);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        Assertions.assertInstanceOf(MessageResponse.class, response.getBody());
        assertEquals("Error: Email is already in use!", ((MessageResponse) response.getBody()).getMessage());
        verify(accountRepository, never()).save(any(Account.class));
    }

    @Test
    void userRegister_InvalidRole() {
        // Arrange
        RegisterRequest request = new RegisterRequest(
                "username",
                "password", "firstName",
                "lastName",
                "email@example.com",
                new HashSet<>(Set.of("INVALID_ROLE")));
        when(accountRepository.existsByUsername(request.getUsername())).thenReturn(false);
        when(accountRepository.existsByEmail(request.getEmail())).thenReturn(false);
        when(roleRepository.findByName(any())).thenReturn(java.util.Optional.empty());

        // Act
        ResponseEntity<?> response = accountService.userRegister(request);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        Assertions.assertInstanceOf(MessageResponse.class, response.getBody());
        assertEquals("Error: Role is not found.", ((MessageResponse) response.getBody()).getMessage());
        verify(accountRepository, never()).save(any(Account.class));
    }

    @Test
    void userLogin_Success() {
        // Arrange
        LoginRequest loginRequest = new LoginRequest("username", "password");
        Authentication authentication = mock(Authentication.class);
        UserDetailsImpl userDetails = mock(UserDetailsImpl.class);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(userDetails);

        // Mock the response to include the JWT token as a cookie
        String expectedToken = "token";
        ResponseCookie jwtCookie = ResponseCookie.from("ChatApp", expectedToken)
                .path("/api")
                .httpOnly(false)
                .secure(false)
                .build();
        when(jwtUtils.generateJwtCookie(userDetails)).thenReturn(jwtCookie);

        // Act
        ResponseEntity<?> response = accountService.userLogin(loginRequest);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());

        // Validate the cookie in the response headers
        Assertions.assertTrue(response.getHeaders().containsKey("Set-Cookie"));
        String cookieHeader = response.getHeaders().getFirst("Set-Cookie");
        Assertions.assertNotNull(cookieHeader);
        Assertions.assertTrue(cookieHeader.contains(expectedToken));
    }

    @Test
    void userLogin_InvalidCredentials() {
        // Arrange
        LoginRequest loginRequest = new LoginRequest("username", "wrongpassword");
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new BadCredentialsException("Bad credentials"));

        // Act & Assert
        Exception exception = assertThrows(BadCredentialsException.class, () -> {
            accountService.userLogin(loginRequest);
        });

        assertEquals("Bad credentials", exception.getMessage());
    }

    @Test
    void userLogout_Success() {
        // Arrange
        ResponseCookie cookie = ResponseCookie.from("JWT", "")
                .path("/")
                .build();
        when(jwtUtils.getCleanJwtCookie()).thenReturn(cookie);

        // Act
        ResponseEntity<?> response = accountService.userLogout();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertTrue(response.getHeaders().containsKey(HttpHeaders.SET_COOKIE));
        String setCookieHeader = response.getHeaders().getFirst(HttpHeaders.SET_COOKIE);
        Assertions.assertNotNull(setCookieHeader);

        // Verify the body message
        Assertions.assertInstanceOf(MessageResponse.class, response.getBody());
        assertEquals("See you next time!", ((MessageResponse) response.getBody()).getMessage());
    }


}

