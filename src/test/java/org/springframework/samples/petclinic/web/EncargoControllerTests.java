package org.springframework.samples.petclinic.web;



import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.core.AutoConfigureCache;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
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
import javax.transaction.Transactional;
import java.util.Optional;

@RunWith(SpringRunner.class)
@WebMvcTest(value = EncargoController.class,
    excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
    excludeAutoConfiguration= SecurityConfiguration.class)
@AutoConfigureMockMvc
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

    private static final int TEST_BEAVER_ID = 1;
    private static final int TEST_ENCARGO_ID = 1;
    private static final String TEST_BEAVERUSER_ID = "beaver2";

    //private Beaver beaver1;
    //private User beaverUser1;
    //private Authorities	authorities;

    @BeforeEach
    void setup() {
        /*
        this.beaver1 = new Beaver();
        this.beaver1.setId(0);
        this.beaver1.setFirstName("Nombre");
        this.beaver1.setLastName("Apellidos");
        this.beaver1.setEmail("valid@gmail.com");
        this.beaver1.setEspecialidades("Pintura");
        this.beaver1.setDni("12345678Q");

        this.beaverUser1 = new User();
        this.beaverUser1.setUsername(TEST_BEAVERUSER_ID);
        this.beaverUser1.setPassword("supersecretpass");
        this.beaverUser1.setEnabled(true);

        this.beaver1.setUser(this.beaverUser1);
        this.beaver1.getUser().setEnabled(true);

        this.authorities = new Authorities();
        this.authorities.setUser(beaverUser1);
        this.authorities.setAuthority("beaver");

        BDDMockito.given(this.userService.findUserByUsername(TEST_BEAVERUSER_ID)).willReturn(this.beaverUser1);
        BDDMockito.given(this.beaverService.findBeaverByIntId(TEST_BEAVER_ID)).willReturn(this.beaver1);
        BDDMockito.given(this.beaverService.findBeaverByIntId(BDDMockito.anyInt())).willReturn(this.beaver1);

         */

    }



    @Test
    @WithMockUser("testuser")
    public void listarEncargosTest() throws Exception {
        mockMvc.perform(get("/beavers/{beaverId}/encargos/list",TEST_BEAVER_ID )).andExpect(status().isOk());
    }


    /*
    @WithMockUser(username = "testuser")
    @Test
    public void testInitAñadirComentarioHasErrors() throws Exception {
        mockMvc.perform(get("/beaver/{beaverId}/encargos/nuevo", TEST_BEAVER_ID)).andExpect(status().is4xxClientError());
    }
*/



    @WithMockUser(value = "User")
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
        beaver2User.setNombre(beaver2.getFirstName());
        beaver2User.setApellidos(beaver2.getLastName());

        BDDMockito.given(this.beaverService.findBeaverByIntId(TEST_BEAVER_ID)).willReturn(beaver2);
        mockMvc.perform(get("/beavers/{beaverId}/encargos/new", TEST_BEAVER_ID )).andExpect(status().isOk())
            .andExpect(view().name("encargos/nuevo")).andExpect(model().attributeExists("encargo"));
    }


    @WithMockUser(value = "testuser")
    @Test
    public void testProcessCreationSuccess() throws Exception {
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

        beaver2.setUser(beaver2User);
        beaver2.getUser().setEnabled(true);
        beaver2User.setNombre(beaver2.getFirstName());
        beaver2User.setApellidos(beaver2.getLastName());

        Authorities authoritie = new Authorities();
        authoritie.setUser(beaver2User);
        authoritie.setAuthority("beaver");

        BDDMockito.given(this.userService.findUserByUsername(TEST_BEAVERUSER_ID)).willReturn(beaver2User);
        BDDMockito.given(this.beaverService.findBeaverByIntId(BDDMockito.anyInt())).willReturn(beaver2);
        mockMvc.perform(post("/beavers/{beaverId}/encargos/new", TEST_BEAVER_ID).with(csrf())
            .param("titulo", "Encargo Pinturas")
            .param("precio", "35.50")
            .param("disponibilidad", "true")
            .param("descripcion", "Descripción del encargo de las pinturas del nuevo Beaver a 35 euros")
            .param("photo", "https://previews.123rf.com/images/max5799/max57991508/max5799150800006/44259458-paisaje-de-la-pintura-al-%C3%B3leo-ramo-de-flores-en-el-fondo-del-mar-mediterr%C3%A1neo-cerca-de-las-monta%C3%B1as-oast.jpg"))
            //.andExpect(status().is3xxRedirection())
            .andExpect(view().name("redirect:/beavers/{beaverId}"));
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
