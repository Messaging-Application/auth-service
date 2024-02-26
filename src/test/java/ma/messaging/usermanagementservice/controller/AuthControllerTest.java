package ma.messaging.usermanagementservice.controller;

public class AuthControllerTest {
    /*
    @Mock
    private AuthenticationService authenticationService;

    @InjectMocks
    private AuthController authController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testUserRegister_Success() {
        // Arrange
        RegisterRequest mockRequest = new RegisterRequest("john",
                "john123",
                "John",
                "Doe",
                "john.doe@uva.nl");
        when(authenticationService.register(any(RegisterRequest.class))).thenReturn(RegisterResponse.REGISTERED);

        // Act
        ResponseEntity<String> response = authController.userRegister(mockRequest);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Successful user registration", response.getBody());
    }

    @Test
    public void testUserRegister_FailureUsername() {
        // Arrange
        RegisterRequest mockRequest = new RegisterRequest("john",
                "john123",
                "John",
                "Doe",
                "john.doe@uva.nl");
        when(authenticationService.register(any(RegisterRequest.class))).thenReturn(RegisterResponse.USERNAME_EXISTS);

        // Act
        ResponseEntity<String> response = authController.userRegister(mockRequest);

        // Assert
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals("Username already exists", response.getBody());
    }

    @Test
    public void testUserRegister_FailureEmail() {
        // Arrange
        RegisterRequest mockRequest = new RegisterRequest("john",
                "john123",
                "John",
                "Doe",
                "john.doe@uva.nl");
        when(authenticationService.register(any(RegisterRequest.class))).thenReturn(RegisterResponse.EMAIL_EXISTS);

        // Act
        ResponseEntity<String> response = authController.userRegister(mockRequest);

        // Assert
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals("Email already exists", response.getBody());
    }

    @Test
    public void testUserLogin_Success() {
        // Arrange
        LoginRequest mockRequest = new LoginRequest("john", "john123");
        when(authenticationService.login(any(LoginRequest.class))).thenReturn(LoginResponse.LOGGED_IN);

        // Act
        ResponseEntity<String> response = authController.userLogin(mockRequest);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Successfully logged in", response.getBody());
    }

    @Test
    public void testUserLogin_FailureCredentials() {
        // Arrange
        LoginRequest mockRequest = new LoginRequest("john", "john123");
        when(authenticationService.login(any(LoginRequest.class))).thenReturn(LoginResponse.WRONG_CREDENTIALS);

        // Act
        ResponseEntity<String> response = authController.userLogin(mockRequest);

        // Assert
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
        assertEquals("Failed login: incorrect username and/or password", response.getBody());
    }

    @Test
    public void testUserLogin_FailureNonExistentAccount() {
        // Arrange
        LoginRequest mockRequest = new LoginRequest("john", "john123");
        when(authenticationService.login(any(LoginRequest.class))).thenReturn(LoginResponse.NON_EXISTENT_ACCOUNT);

        // Act
        ResponseEntity<String> response = authController.userLogin(mockRequest);

        // Assert
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
        assertEquals("Failed login: incorrect username and/or password", response.getBody());
    }

     */
}

