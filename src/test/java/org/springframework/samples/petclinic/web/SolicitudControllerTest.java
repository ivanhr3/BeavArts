package org.springframework.samples.petclinic.web;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.core.AutoConfigureCache;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.samples.petclinic.configuration.SecurityConfiguration;
import org.springframework.samples.petclinic.service.SolicitudService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import javax.transaction.Transactional;


@RunWith(SpringRunner.class)
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@WebMvcTest(value = SolicitudController.class,
    excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
    excludeAutoConfiguration= SecurityConfiguration.class)
@AutoConfigureMockMvc
public class SolicitudControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SolicitudService solicitudService;

    private static final int TEST_SOLICITUD_ID = 1;

    @Test
    @WithMockUser("testuser")
    public void listarSolicitudesTest() throws Exception {
        mockMvc.perform(get("/solicitudes/list")).andExpect(status().isOk());
    }

    @Test
    @WithMockUser("testuser")
    public void mostrarSolicitudesTest() throws Exception {
        mockMvc.perform(get("/solicitudes/solicitudInfo/{solicitudId}", TEST_SOLICITUD_ID)).andExpect(status().isOk());
    }
}
