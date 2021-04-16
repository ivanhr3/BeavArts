package org.springframework.samples.petclinic.web;

import java.util.Collection;
import java.util.HashSet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.samples.petclinic.configuration.SecurityConfiguration;
import org.springframework.samples.petclinic.model.Beaver;
import org.springframework.samples.petclinic.model.Especialidad;
import org.springframework.samples.petclinic.model.User;
import org.springframework.samples.petclinic.service.AuthoritiesService;
import org.springframework.samples.petclinic.service.BeaverService;
import org.springframework.samples.petclinic.service.ConfirmationTokenService;
import org.springframework.samples.petclinic.service.UserService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(value = UserController.class,
    excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE,
        classes = WebSecurityConfigurer.class), excludeAutoConfiguration = SecurityConfiguration.class)
public class UserControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BeaverService beaverService;

    @MockBean
    private UserService userService;

    @MockBean
    private AuthoritiesService	authoritiesService;

    @MockBean
    private ConfirmationTokenService confirmationTokenService;

    private Beaver				beaver1;

    private User user1;

    @BeforeEach
    void setup() {

        this.user1 = new User();
        this.user1.setUsername("beaver1");
        this.user1.setPassword("supersecretpass");
        this.user1.setEnabled(true);

        this.beaver1 = new Beaver();
        this.beaver1.setId(50);
        this.beaver1.setFirstName("Nombre");
        this.beaver1.setLastName("Apellidos");
        this.beaver1.setEmail("valid@gmail.com");
        Collection<Especialidad> esP = new HashSet<>();
        esP.add(Especialidad.ILUSTRACION);
        this.beaver1.setEspecialidades(esP);
        this.beaver1.setDni("12345678Q");
        this.beaver1.setUser(user1);

        BDDMockito.given(this.userService.findUserByUsername("beaver1")).willReturn(user1);
        BDDMockito.given(this.beaverService.findBeaverByEmail("valid@gmail.com")).willReturn(beaver1);

    }

    @WithMockUser(value = "testuser")
    @Test
    public void testInitCreationForm() throws Exception {

        this.mockMvc.perform(get("/users/new"))
            .andExpect(status().isOk())
            .andExpect(model().attributeExists("beaver"))
            .andExpect(view().name("users/createBeaverForm"));

    }

    @WithMockUser(value = "testuser")
    @Test
    public void testProcessCreationForm() throws Exception {
        this.mockMvc.perform(post("/users/new").with(csrf())
            .param("firstName", "First Name")
            .param("lastName", "Last Name")
            .param("dni", "11111111A")
            .param("email", "email@email.com")
            .param("user.username", "User123")
            .param("user.password", "2345"))
            .andExpect(status().is3xxRedirection())
            .andExpect(view().name("redirect:/"));
    }

    @WithMockUser(value = "testuser")
    @Test
    public void testProcessCreationFormHasError() throws Exception {
        this.mockMvc.perform(post("/users/new").with(csrf())
            .param("firstName", "First Name")
            .param("lastName", "Last Name")
            .param("dni", "11111111A")
            .param("email", "email@email.com")
            .param("user.username", "beaver1")
            .param("user.password", "2345"))
            .andExpect(status().isOk())
            .andExpect(view().name("users/createBeaverForm"));
    }

    @WithMockUser(value = "testuser")
    @Test
    public void testProcessCreationFormHasErrors2() throws Exception {
        this.mockMvc.perform(post("/users/new").with(csrf())
            .param("firstName", "First Name")
            .param("lastName", "Last Name")
            .param("dni", "11111111A")
            .param("email", "valid@gmail.com")
            .param("user.username", "User123")
            .param("user.password", "2345"))
            .andExpect(status().isOk())
            .andExpect(view().name("users/createBeaverForm"));
    }

}
