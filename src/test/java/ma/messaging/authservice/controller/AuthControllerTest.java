package ma.messaging.authservice.controller;

import jakarta.servlet.http.Cookie;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AuthControllerTest {

    @Autowired
    private MockMvc mvc;

    @LocalServerPort
    private int port;

    @InjectMocks
    private AuthController authController;

    private String cookie;

    @BeforeEach
    public void setUp() {
        assertThat(mvc).isNotNull();
    }

    @Test
    @Order(1)
    void userRegister_Success() throws Exception {
        // Perform post request and expect status 200 OK
        mvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\":\"john\"," +
                                "\"password\":\"john12345678\"," +
                                "\"firstName\":\"John\"," +
                                "\"lastName\":\"Doe\"," +
                                "\"email\":\"john@gmail.com\"," +
                                "\"role\":[\"user\"]}"))
                .andExpect(status().isOk());
    }

    @Test
    @Order(2)
    void userRegister_Failure() throws Exception {
        //Try to register the same username and email
        mvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\":\"john\"," +
                                "\"password\":\"john12345678\"," +
                                "\"firstName\":\"John\"," +
                                "\"lastName\":\"Doe\"," +
                                "\"email\":\"john@gmail.com\"," +
                                "\"role\":[\"user\"]}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Order(3)
    void userRegister_FailureUsername() throws Exception {
        mvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\":\"john\"," +
                                "\"password\":\"password\"," +
                                "\"firstName\":\"firstname\"," +
                                "\"lastName\":\"lastname\"," +
                                "\"email\":\"username@gmail.com\"," +
                                "\"role\":[\"user\"]}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Order(4)
    void userRegister_FailureEmail() throws Exception {
        mvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\":\"different\"," +
                                "\"password\":\"password\"," +
                                "\"firstName\":\"firstname\"," +
                                "\"lastName\":\"lastname\"," +
                                "\"email\":\"john@gmail.com\"," +
                                "\"role\":[\"user\"]}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Order(5)
    void userLogin_Success() throws Exception {
        // Perform post request and expect status 200 OK
        MvcResult result = mvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\":\"john\"," +
                                "\"password\":\"john12345678\"}"))
                .andExpect(status().isOk())
                .andExpect(header().exists("Set-Cookie"))
                .andReturn();

        cookie = result.getResponse().getHeader("Set-Cookie");

        Assertions.assertNotNull(cookie);
    }

    @Test
    @Order(6)
    void userLogin_InvalidCredentials() throws Exception {
        // Perform post request and expect status 200 OK
        mvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\":\"john\"," +
                                "\"password\":\"wrong_password\"}"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @Order(8)
    void userLogout_Success() throws Exception {
        // Perform post request and expect status 200 OK
        mvc.perform(post("/auth/logout")
                        .cookie(new Cookie("JWT", cookie)))
                .andExpect(status().isOk());
    }

}

