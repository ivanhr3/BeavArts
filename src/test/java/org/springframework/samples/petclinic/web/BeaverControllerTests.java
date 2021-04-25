package org.springframework.samples.petclinic.web;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.samples.petclinic.configuration.SecurityConfiguration;
import org.springframework.samples.petclinic.model.*;
import org.springframework.samples.petclinic.service.PortfolioService;
import org.springframework.samples.petclinic.service.AuthoritiesService;
import org.springframework.samples.petclinic.service.BeaverService;
import org.springframework.samples.petclinic.service.UserService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;


//@RunWith(SpringRunner.class)
@WebMvcTest(value = BeaverController.class,
    excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE,
        classes = WebSecurityConfigurer.class), excludeAutoConfiguration = SecurityConfiguration.class)
//@AutoConfigureMockMvc
public class BeaverControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BeaverService beaverService;

    @MockBean
    private UserService userService;

    @MockBean
    private PortfolioService portfolioService;


    @MockBean
    private AuthoritiesService	authoritiesService;

    private static final int TEST_BEAVER_ID = 11;

    private Beaver				beaver1;

    private Beaver				beaver2;

    private List<Beaver>        listaBeaversPaginacion;
    // User user;

    @BeforeEach
    void setup() {

        final User user = new User();
        user.setUsername("beaver1");
        user.setPassword("supersecretpass");
        Authorities au = new Authorities();
		Set<Authorities> col = new HashSet<>();
		col.add(au);
		au.setAuthority("user");
		au.setUser(user);
		user.setAuthorities(col);
        user.setEnabled(true);

        this.beaver1 = new Beaver();
        this.beaver1.setId(TEST_BEAVER_ID);
        this.beaver1.setFirstName("Nombre");
        this.beaver1.setId(11);
        this.beaver1.setUrlFotoPerfil("");
        this.beaver1.setLastName("Apellidos");
        this.beaver1.setEmail("valid@gmail.com");
        Collection<Especialidad> esP = new HashSet<>();
        esP.add(Especialidad.ILUSTRACION);
        this.beaver1.setEspecialidades(esP);
        this.beaver1.setDni("12345678Q");
        this.beaver1.setUser(user);

        final Portfolio portfolio = new Portfolio();
        portfolio.setBeaver(this.beaver1);
        portfolio.setSobreMi("Descripcion perfil");
        Collection<String> fotos = new HashSet<>();
        String foto1 = "fotoPrueba1";
        String foto2 = "fotoPrueba2";
        fotos.add(foto1);
        fotos.add(foto2);

        this.beaver1.setPortfolio(portfolio);

        final User user2 = new User();
        user2.setUsername("beaver2");
        user2.setPassword("supersecretpass");
        user2.setEnabled(true);

        this.beaver2 = new Beaver();
        this.beaver2.setId(12);
        this.beaver2.setFirstName("Nombre 2");
        this.beaver2.setLastName("Apellidos");
        this.beaver2.setEmail("valid4@gmail.com");
        Collection<Especialidad> esp = new HashSet<>();
        esp.add(Especialidad.ILUSTRACION);
        this.beaver2.setEspecialidades(esp);
        this.beaver2.setDni("12345978Q");
        this.beaver2.setUser(user2);

        List<Authorities> lista = new ArrayList<Authorities>();
		lista.add(au);

		listaBeaversPaginacion = new ArrayList<>();
		listaBeaversPaginacion.add(beaver1);
		listaBeaversPaginacion.add(beaver2);

        BDDMockito.given(this.userService.findUserByUsername("beaver2")).willReturn(user2);
        BDDMockito.given(this.userService.findUserByUsername("beaver1")).willReturn(user);
        BDDMockito.given(this.beaverService.findBeaverByUsername("beaver1")).willReturn(this.beaver1);
        BDDMockito.given(this.beaverService.findBeaverByIntId(BeaverControllerTests.TEST_BEAVER_ID)).willReturn(this.beaver1);
        BDDMockito.given(this.beaverService.findBeaverByIntId(12)).willReturn(this.beaver2);

        BDDMockito.given(this.beaverService.calculatePuntuacion(beaver1)).willReturn(4.34554);
        BDDMockito.given(this.beaverService.calculatePuntuacion(beaver2)).willReturn(null);
        BDDMockito.given(this.beaverService.getNumValoraciones(beaver1)).willReturn(17);
        BDDMockito.given(this.beaverService.getNumValoraciones(beaver2)).willReturn(0);
        BDDMockito.given(this.beaverService.findUserAuthorities(user)).willReturn(lista);
        BDDMockito.given(this.beaverService.findUserAuthorities(user2)).willReturn(lista);
    }


    @WithMockUser(value = "beaver1")
    @Test
    public void testMostrarPerfilUsuario() throws Exception {
        //Se mockean las valoraciones, este usuario SÍ tiene.
        System.out.println(this.beaver1.getFirstName());
        BDDMockito.given(this.beaverService.getCurrentBeaver()).willReturn(beaver1);


        this.mockMvc.perform(get("/beavers/beaverInfo/{beaverId}", TEST_BEAVER_ID))
            .andExpect(status().isOk())
            .andExpect(model().attributeExists("beaver"))
            .andExpect(model().attributeExists("portfolio"))
            .andExpect(model().attribute("puntuacionMedia", Math.round(4.34554*100.0)/100.0))
            .andExpect(model().attribute("numValoraciones", 17))
            .andExpect(view().name("users/perfilBeaver"));
    }

    @WithMockUser(value = "beaver2")
    @Test
    public void testMostrarPerfilUsuario2() throws Exception {
        //Se mockean las valoraciones, este usuario NO tiene.
        System.out.println(this.beaver1.getFirstName());
        BDDMockito.given(this.beaverService.getCurrentBeaver()).willReturn(beaver2);

        this.mockMvc.perform(get("/beavers/beaverInfo/{beaverId}", 12))
            .andExpect(status().isOk())
            .andExpect(model().attributeExists("beaver"))
            .andExpect(model().attributeExists("portfolio"))
            .andExpect(model().attribute("sinPuntuacionMedia", "Aún no hay valoraciones"))
            .andExpect(model().attribute("numValoraciones", 0))
            .andExpect(view().name("users/perfilBeaver"));
    }

    @WithMockUser(value = "beaver2")
    @Test
    public void testMostrarPerfilUsuarioPortfolioNulo() throws Exception {

        System.out.println(this.beaver1.getFirstName());
        BDDMockito.given(this.beaverService.getCurrentBeaver()).willReturn(beaver2);

        this.mockMvc.perform(get("/beavers/beaverInfo/{beaverId}", 12))
            .andExpect(status().isOk())
            .andExpect(model().attributeExists("beaver"))
            .andExpect(model().attributeExists("portfolio"))
            .andExpect(view().name("users/perfilBeaver"));
    }

    @WithMockUser(value = "beaver1")
    @Test
    public void testInitActualizarPerfil() throws Exception {
        this.mockMvc.perform(get("/beavers/beaverInfo/{beaverId}/portfolio/edit", TEST_BEAVER_ID))
            .andExpect(status().isOk())
            .andExpect(model().attributeExists("portfolio"))
            .andExpect(view().name("users/editarPortfolio"));
    }

    @WithMockUser(value = "beaver2")
    @Test
    public void testInitActualizarPerfilHasErrors() throws Exception {
        this.mockMvc.perform(get("/beavers/beaverInfo/{beaverId}/portfolio/edit", TEST_BEAVER_ID))
            .andExpect(status().isOk())
            .andExpect(view().name("accesoNoAutorizado"));
    }

    @WithMockUser(value = "beaver1")
    @Test
    public void testProcessActualizarPerfil() throws Exception {
        this.mockMvc.perform(post("/beavers/beaverInfo/{beaverId}/portfolio/edit", TEST_BEAVER_ID).with(csrf())
            .param("sobreMi", "Nueva descripción")
            .param("photos", "https://sites.google.com/site/imagenesdecarrosgratis/_/rsrc/1421516636272/home/carros-deportivos-lamborghini-aventador-tron_aventador.jpg",
                "https://i.pinimg.com/originals/3f/57/60/3f576076e1a6431e9c6d704d2da3a3f9.jpg"))
            .andExpect(status().is3xxRedirection())
            .andExpect(view().name("redirect:/beavers/beaverInfo/"+TEST_BEAVER_ID));
    }

    @WithMockUser(value = "beaver1")
    @Test
    public void testProcessActualizarPerfilFotosMal() throws Exception {
        this.mockMvc.perform(post("/beavers/beaverInfo/{beaverId}/portfolio/edit", TEST_BEAVER_ID).with(csrf())
            .param("sobreMi", "Nueva descripción")
            .param("photos", "fotos", "fotos2"))
            .andExpect(status().isOk())
            .andExpect(view().name("users/editarPortfolio"));
    }

    @WithMockUser(value = "beaver2")
    @Test
    public void testProcessActualizarPerfilNulo() throws Exception {
        this.mockMvc.perform(post("/beavers/beaverInfo/{beaverId}/portfolio/edit", 12).with(csrf())
            .param("sobreMi", "Nueva descripción")
            .param("photos", "https://sites.google.com/site/imagenesdecarrosgratis/_/rsrc/1421516636272/home/carros-deportivos-lamborghini-aventador-tron_aventador.jpg",
                "https://i.pinimg.com/originals/3f/57/60/3f576076e1a6431e9c6d704d2da3a3f9.jpg"))
            .andExpect(status().is3xxRedirection())
            .andExpect(view().name("redirect:/beavers/beaverInfo/"+12));
    }

    @WithMockUser(value = "beaver1")
    @Test
    public void testListAllBeavers() throws Exception {
        Page<Beaver> page = new PageImpl<>(listaBeaversPaginacion, PageRequest.of(0, 5), 1);
        BDDMockito.given(this.beaverService.findAllBeavers(PageRequest.of(0, 5))).willReturn(page);

        this.mockMvc.perform(MockMvcRequestBuilders.get("/beavers/list"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.model().attributeExists("beavers"))
            .andExpect(MockMvcResultMatchers.model().attributeExists("beaversPages"))
            .andExpect(MockMvcResultMatchers.view().name("users/listBeavers"));
    }

    @WithMockUser(value = "beaveradmin")
    @Test
    public void banBeaver() throws Exception {

    	 final User user = new User();
         user.setUsername("beaver3");
         user.setPassword("supersecretpass");
         Authorities au = new Authorities();
 		Set<Authorities> col = new HashSet<>();
 		col.add(au);
 		au.setAuthority("admin");
 		au.setUser(user);
 		user.setAuthorities(col);
         user.setEnabled(true);

         Beaver beaver3 = new Beaver();
         beaver3.setId(34);
         beaver3.setFirstName("Nombre");
         beaver3.setId(11);
         beaver3.setLastName("Apellidos");
         beaver3.setEmail("valid@gmail.com");
         Collection<Especialidad> esP = new HashSet<>();
         esP.add(Especialidad.ILUSTRACION);
         beaver3.setEspecialidades(esP);
         beaver3.setDni("12145678Q");
         beaver3.setUser(user);
         List<Authorities> lista = new ArrayList<Authorities>();
 		 lista.add(au);
 		 BDDMockito.given(this.beaverService.findUserAuthorities(user)).willReturn(lista);
         BDDMockito.given(this.beaverService.getCurrentBeaver()).willReturn(beaver3);

    	this.mockMvc.perform(MockMvcRequestBuilders.post("/beavers/beaverInfo/{beaverId}/ban", TEST_BEAVER_ID).with(csrf()))
    		.andExpect(status().is3xxRedirection())
    		.andExpect(MockMvcResultMatchers.view().name("redirect:/beavers/list/"));
    }

    @WithMockUser(value = "beaveradmin")
    @Test
    public void banBeaverNotAuthorized() throws Exception {

         BDDMockito.given(this.beaverService.getCurrentBeaver()).willReturn(beaver1);

    	this.mockMvc.perform(MockMvcRequestBuilders.post("/beavers/beaverInfo/{beaverId}/ban", TEST_BEAVER_ID).with(csrf()))
    		.andExpect(status().isOk())
    		.andExpect(MockMvcResultMatchers.view().name("accesoNoAutorizado"));
    }


    @WithMockUser(value = "beaver1")
    @Test
    public void testInitEditPhoto() throws Exception {
        this.mockMvc.perform(get("/beavers/beaverInfo/{beaverId}/editPhoto", TEST_BEAVER_ID))
            .andExpect(status().isOk())
            .andExpect(model().attributeExists("beaver"))
            .andExpect(view().name("users/editarFotoPerfil"));
    }

    @WithMockUser(value = "beaver2")
    @Test
    public void testInitEditPhotoHasErrors() throws Exception {
        this.mockMvc.perform(get("/beavers/beaverInfo/{beaverId}/editPhoto", TEST_BEAVER_ID))
            .andExpect(status().isOk())
            .andExpect(view().name("accesoNoAutorizado"));
    }


    @WithMockUser(value = "beaver1")
    @Test
    public void testProcessEditPhoto() throws Exception {
        this.mockMvc.perform(post("/beavers/beaverInfo/{beaverId}/editPhoto", TEST_BEAVER_ID).with(csrf())
            .param("firstName", "Nombre")
            .param("lastName", "Apellidos")
            .param("dni", "12345678Q")
            .param("urlFotoPerfil", "https://cdn.domestika.org/c_fill,dpr_1.0,h_1200,t_base_params.format_jpg,w_1200/v1589759117/project-covers/000/721/921/721921-original.png?1589759117"))
            .andExpect(status().is3xxRedirection())
            .andExpect(view().name("redirect:/beavers/beaverInfo/"+TEST_BEAVER_ID));
    }


    @WithMockUser(value = "beaver1")
    @Test
    public void testProcessEditPhtoHasErrors() throws Exception {
        this.mockMvc.perform(post("/beavers/beaverInfo/{beaverId}//editPhoto", TEST_BEAVER_ID).with(csrf())
            .param("firstName", "Nombre")
            .param("lastName", "Apellidos")
            .param("dni", "12345678Q")
            .param("urlFotoPerfil", "fotillo"))
            .andExpect(status().isOk())
            .andExpect(view().name("users/editarFotoPerfil"));
    }


    @WithMockUser(value = "beaver1")
    @Test
    public void testProcessEditPhotoHasError2() throws Exception {
        this.mockMvc.perform(post("/beavers/beaverInfo/{beaverId}/editPhoto", 12).with(csrf())
            .param("firstName", "Nombre")
            .param("lastName", "Apellidos")
            .param("dni", "12345678Q")
            .param("urlFotoPerfil", "https://cdn.domestika.org/c_fill,dpr_1.0,h_1200,t_base_params.format_jpg,w_1200/v1589759117/project-covers/000/721/921/721921-original.png?1589759117"))
            .andExpect(status().isOk())
            .andExpect(view().name("accesoNoAutorizado"));
    }


}
