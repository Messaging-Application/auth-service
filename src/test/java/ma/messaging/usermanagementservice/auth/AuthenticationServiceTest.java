package ma.messaging.usermanagementservice.auth;

public class AuthenticationServiceTest {
/*
    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private AuthenticationServiceImpl accountService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testRegister_Success() {
        // Arrange
        RegisterRequest request = new RegisterRequest("john",
                "john123",
                "John",
                "Doe",
                "john.doe@uva.nl");
        when(accountRepository.existsByUsername(request.getUsername())).thenReturn(false);
        when(accountRepository.existsByEmail(request.getEmail())).thenReturn(false);

        // Act
        RegisterResponse response = accountService.register(request);

        // Assert
        assertEquals(RegisterResponse.REGISTERED, response);
    }

    @Test
    public void testRegister_UsernameExists() {
        // Arrange
        RegisterRequest request = new RegisterRequest("john",
                "john123",
                "John",
                "Doe",
                "john.doe@uva.nl");
        when(accountRepository.existsByUsername(request.getUsername())).thenReturn(true);

        // Act
        RegisterResponse response = accountService.register(request);

        // Assert
        assertEquals(RegisterResponse.USERNAME_EXISTS, response);
    }

    @Test
    public void testRegister_EmailExists() {
        // Arrange
        RegisterRequest request = new RegisterRequest("john",
                "john123",
                "John",
                "Doe",
                "john.doe@uva.nl");
        when(accountRepository.existsByEmail(request.getEmail())).thenReturn(true);

        // Act
        RegisterResponse response = accountService.register(request);

        // Assert
        assertEquals(RegisterResponse.EMAIL_EXISTS, response);
    }


    @Test
    public void testLogin_Success() {
        // Arrange
        LoginRequest request = new LoginRequest("john", "john123");
        Account account = new Account();
        account.setUsername(request.getUsername());
        account.setPassword(request.getPassword());

        when(accountRepository.findByUsername(any())).thenReturn(Optional.empty());
        when(accountRepository.findByUsername(request.getUsername())).thenReturn(Optional.of(account));

        // Act
        LoginResponse response = accountService.login(request);

        // Assert
        assertEquals(LoginResponse.LOGGED_IN, response);
    }

    @Test
    public void testLogin_FailedNonExistentAccount() {
        // Arrange
        LoginRequest request = new LoginRequest("john", "john123");
        Account account = new Account();
        account.setUsername(request.getUsername());
        account.setPassword(request.getPassword());

        when(accountRepository.findByUsername(any())).thenReturn(Optional.empty());

        // Act
        LoginResponse response = accountService.login(request);

        // Assert
        assertEquals(LoginResponse.NON_EXISTENT_ACCOUNT, response);
    }

    @Test
    public void testLogin_FailedWrongCredentials() {
        // Arrange
        LoginRequest request = new LoginRequest("john", "john123");
        Account account = new Account();
        account.setUsername("wrong-username");
        account.setPassword("123");

        when(accountRepository.findByUsername(any())).thenReturn(Optional.empty());
        when(accountRepository.findByUsername(request.getUsername())).thenReturn(Optional.of(account));

        // Act
        LoginResponse response = accountService.login(request);

        // Assert
        assertEquals(LoginResponse.WRONG_CREDENTIALS, response);
    }

 */


}