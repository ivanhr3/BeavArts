package org.springframework.samples.petclinic.web;



import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.samples.petclinic.configuration.SecurityConfiguration;
import org.springframework.samples.petclinic.model.Beaver;
import org.springframework.samples.petclinic.model.User;
import org.springframework.samples.petclinic.model.Authorities;
import org.springframework.samples.petclinic.service.AuthoritiesService;
import org.springframework.samples.petclinic.service.BeaverService;
import org.springframework.samples.petclinic.service.EncargoService;
import org.springframework.samples.petclinic.service.UserService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;


@RunWith(SpringRunner.class)
@WebMvcTest(controllers = EncargoController.class,
    excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
    excludeAutoConfiguration= SecurityConfiguration.class)
public class EncargoControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EncargoService encargoService;

    @MockBean
    private BeaverService beaverService;

    @MockBean
    private UserService userService;

    @MockBean
    private AuthoritiesService authoritiesService;

    @Autowired
    private EncargoController encargoController;

    private static final int TEST_BEAVER_ID = 1;
    private static final int TEST_ENCARGO_ID = 1;
    private static final String TEST_BEAVERUSERNAME = "beaver2";

    private Beaver beaver1;
    private User beaverUser1;
    private Authorities	authorities;

    @BeforeEach
    void setup() {
    
        beaver1 = new Beaver();
        beaver1.setId(TEST_BEAVER_ID);
        beaver1.setFirstName("Nombre");
        beaver1.setLastName("Apellidos");
        beaver1.setEmail("valid@gmail.com");
        beaver1.setEspecialidades("Pintura");
        beaver1.setDni("12345678Q");

        beaverUser1 = new User();
        beaverUser1.setUsername(TEST_BEAVERUSERNAME);
        beaverUser1.setPassword("supersecretpass");
        beaverUser1.setEnabled(true);

        beaver1.setUser(this.beaverUser1);
        beaver1.getUser().setEnabled(true);

        authorities = new Authorities();
        authorities.setUser(beaverUser1);
        authorities.setAuthority("beaver");

        BDDMockito.given(this.userService.findUserByUsername(TEST_BEAVERUSERNAME)).willReturn(beaverUser1);
        BDDMockito.given(this.beaverService.findBeaverByIntId(TEST_BEAVER_ID)).willReturn(beaver1);
        BDDMockito.given(this.beaverService.findBeaverByIntId(BDDMockito.anyInt())).willReturn(beaver1);

    }



    @Test
    @WithMockUser("spring")
    public void listarEncargosTest() throws Exception {

       /*  Beaver beaver2 = new Beaver();
        beaver2.setId(1);
        beaver2.setFirstName("Nombre");
        beaver2.setLastName("Apellidos");
        beaver2.setEmail("valid@gmail.com");
        beaver2.setEspecialidades("Pintura");
        beaver2.setDni("12345678Q");

        User beaver2User = new User();
        beaver2User.setUsername("beaver2");
        beaver2User.setPassword("superpasssecret");
        beaver2User.setEnabled(true);
        beaver2User.setNombre(beaver2.getFirstName());
        beaver2User.setApellidos(beaver2.getLastName());
        beaver2User.setNombre(beaver2.getFirstName());
        beaver2User.setApellidos(beaver2.getLastName()); */

        mockMvc.perform(get("/beavers/{beaverId}/list",TEST_BEAVER_ID)).andExpect(status().isOk());
    }


    /*
    @WithMockUser(username = "testuser")
    @Test
    public void testInitAñadirComentarioHasErrors() throws Exception {
        mockMvc.perform(get("/beaver/{beaverId}/encargos/nuevo", TEST_BEAVER_ID)).andExpect(status().is4xxClientError());
    }
*/



    @WithMockUser(value = "spring")
    @Test
    public void testInitCreationSucces() throws Exception {
        Beaver beaver2 = new Beaver();
        beaver2.setId(1);
        beaver2.setFirstName("Nombre");
        beaver2.setLastName("Apellidos");
        beaver2.setEmail("valid@gmail.com");
        beaver2.setEspecialidades("Pintura");
        beaver2.setDni("12345678Q");

        User beaver2User = new User();
        beaver2User.setUsername("beaver2");
        beaver2User.setPassword("superpasssecret");
        beaver2User.setEnabled(true);
        beaver2User.setNombre(beaver2.getFirstName());
        beaver2User.setApellidos(beaver2.getLastName());
        
        mockMvc.perform(get("/encargos/new")).andExpect(status().isOk())
            .andExpect(view().name("encargos/nuevo")).andExpect(model().attributeExists("encargo"));
    }


    @WithMockUser(value = "testuser")
    @Test
    public void testProcessCreationSuccess() throws Exception {

        BDDMockito.given(this.userService.findUserByUsername(TEST_BEAVERUSERNAME)).willReturn(this.beaverUser1);
        BDDMockito.when(this.beaverService.findBeaverByIntId(BDDMockito.anyInt())).thenReturn(beaver1);
        mockMvc.perform(post("/beavers/{beaverId}/encargos/new", TEST_BEAVER_ID).with(csrf())
            .param("titulo", "Encargo Pinturas")
            .param("precio", "35.50")
            .param("disponibilidad", "true")
            .param("descripcion", "Descripción del encargo de las pinturas del nuevo Beaver a 35 euros")
            .param("photo", "https://previews.123rf.com/images/max5799/max57991508/max5799150800006/44259458-paisaje-de-la-pintura-al-%C3%B3leo-ramo-de-flores-en-el-fondo-del-mar-mediterr%C3%A1neo-cerca-de-las-monta%C3%B1as-oast.jpg")
            .param("beaver.id", Integer.toString(TEST_BEAVER_ID)))
            /* .param("beaver.email", this.beaver1.getEmail())
            .param("beaver.especialidades", "test@gmail.com")
            .param("beaver.dni", "50506312Z")
            .param("beaver.person.firstname", "TestName")
            .param("beaver.person.lastname", "TestLastName")
            .param("beaver.user.username", "beaver2")
            .param("beaver.user.password", "superpasssecret")
            .param("beaver.user.enabled", "true")) */
            .andExpect(status().is3xxRedirection())
            .andExpect(view().name("redirect:/beavers/" + TEST_BEAVER_ID));
    }



    @WithMockUser(value = "testuser")
    @Test
    public void testProcessCreationFormHasErrors() throws Exception {
        mockMvc.perform(post("/beavers/{beaverId}/encargos/nuevo", TEST_BEAVER_ID).with(csrf())
            .param("titulo", "Encargo Pinturas")
            .param("precio", "40.0")
            .param("disponibilidad", "true")
            .param("descripcion", "Descripción del encargo de las pinturas del nuevo Beaver a 35 euros"))
            //.param("photo", "https://previews.123rf.com/images/max5799/max57991508/max5799150800006/44259458-paisaje-de-la-pintura-al-%C3%B3leo-ramo-de-flores-en-el-fondo-del-mar-mediterr%C3%A1neo-cerca-de-las-monta%C3%B1as-oast.jpg"))
            //.andExpect(model().attributeHasErrors("encargo"))
            //.andExpect(model().attributeHasFieldErrors("encargo", "precio"))
            .andExpect(status().isOk())
            .andExpect(view().name("encargos/nuevo"));
    }


}
