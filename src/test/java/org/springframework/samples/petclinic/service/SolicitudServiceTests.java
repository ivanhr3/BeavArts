package org.springframework.samples.petclinic.service;

import static org.assertj.core.api.Assertions.assertThat;

import javax.mail.MessagingException;
import javax.transaction.Transactional;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.samples.petclinic.model.Beaver;
import org.springframework.samples.petclinic.model.Encargo;
import org.springframework.samples.petclinic.model.Especialidad;
import org.springframework.samples.petclinic.model.Estados;
import org.springframework.samples.petclinic.model.Solicitud;
import org.springframework.samples.petclinic.model.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;


import org.springframework.util.Assert;


@SpringBootTest
@Transactional
@SuppressWarnings("deprecation")
public class SolicitudServiceTests {
    @Autowired
    protected SolicitudService solicitudService;
    @Autowired
    protected BeaverService beaverService;
    @Autowired
    protected EncargoService encargoService;
    @Autowired
    private UserService	userService;


    Beaver beaver;
    Encargo encargo;
    User user;
    Beaver beaveralt;
    User useralt;

    @BeforeEach
    void setDummyData(){
        beaver = new Beaver();
        beaver.setFirstName("Nombre");
        beaver.setLastName("Apellidos");
        beaver.setEmail("valid@gmail.com");
        Collection<Especialidad> especialidad = new HashSet<Especialidad>();
        especialidad.add(Especialidad.FOTOGRAFIA);
        beaver.setEspecialidades(especialidad);
        beaver.setDni("12345678Q");
        user = new User();
        user.setUsername("user3");
        user.setPassword("supersecretpass");
        user.setEnabled(true);
        beaver.setUser(user);
        this.beaverService.saveBeaver(beaver);

        beaveralt = new Beaver();
        beaveralt.setFirstName("Nombre");
        beaveralt.setLastName("Apellidos");
        beaveralt.setEmail("valid@gmail.com");
        beaveralt.setEspecialidades(especialidad);
        beaveralt.setDni("12345678Q");
        useralt = new User();
        useralt.setUsername("User4");
        useralt.setPassword("supersecretpass");
        useralt.setEnabled(true);
        beaveralt.setUser(useralt);
        this.beaverService.saveBeaver(beaveralt);

        encargo = new Encargo();
        encargo.setTitulo("Encargo chulisimo");
        encargo.setDescripcion("mira que wapo mi encargo reshulon porque tienen que ser tantos caracteres");
        encargo.setDisponibilidad(true);
        encargo.setPrecio(199.0);
        encargo.setBeaver(beaveralt);
        this.encargoService.saveEncargo(encargo);
    }

    public Beaver createDummyBeaver() {

        User user1 = new User();
        user1.setUsername("testuser");
        user1.setPassword("pass123");

        Beaver beaver1 = new Beaver();
        beaver1.setDni("29519811N");
        beaver1.setEmail("testemail@hotmail.com");
        Collection<Especialidad> especialidad = new HashSet<Especialidad>();
        especialidad.add(Especialidad.FOTOGRAFIA);
        beaver1.setEspecialidades(especialidad);
        beaver1.setFirstName("testbeaver");
        beaver1.setLastName("Perez");
        beaver1.setUser(user1);

        return beaver1;
    }

    public Encargo createDummyEncargo() {

        User user1 = new User();
        user1.setUsername("testuser");
        user1.setPassword("pass123");

        Beaver beaver1 = new Beaver();
        beaver1.setDni("29519811N");
        beaver1.setEmail("testemail@hotmail.com");
        Collection<Especialidad> especialidad = new HashSet<Especialidad>();
        especialidad.add(Especialidad.FOTOGRAFIA);
        beaver1.setEspecialidades(especialidad);
        beaver1.setFirstName("testbeaver");
        beaver1.setLastName("Perez");
        beaver1.setUser(user1);

        Encargo encargo1 = new Encargo();
        encargo1.setBeaver(beaver1);
        encargo1.setDescripcion("Encargo1 correcto mira que largo tiene que ser quien ha sido el que puso");
        encargo1.setTitulo("Encargo1");
        encargo1.setPrecio(50.00);
        encargo1.setDisponibilidad(true);

        Solicitud solicitud1 = new Solicitud();
        solicitud1.setEncargo(encargo1);
        solicitud1.setEstado(Estados.PENDIENTE);
        solicitud1.setPrecio(50.00);
        solicitud1.setDescripcion("descripcion");

        Solicitud solicitud2 = new Solicitud();
        solicitud2.setEncargo(encargo1);
        solicitud2.setEstado(Estados.PENDIENTE);
        solicitud2.setPrecio(50.00);
        solicitud2.setDescripcion("descripcion");

        List<Solicitud> list = new ArrayList<Solicitud>();
        list.add(solicitud1);
        list.add(solicitud2);
        encargo1.setSolicitud(list);

        this.solicitudService.saveSolicitud(solicitud1);
        this.solicitudService.saveSolicitud(solicitud2);

        return encargo1;
    }

    /*
     * @BeforeEach
     * void setup(){
     *
     * User user1 = new User();
     * user1.setUsername("testuser");
     * user1.setApellidos("Perez");
     * user1.setNombre("testuser");
     * user1.setPassword("pass123");
     *
     * Beaver beaver1 = new Beaver();
     * beaver1.setDni("29519811N");
     * beaver1.setEmail("testemail@hotmail.com");
     * beaver1.setEspecialidades("Pintura");
     * beaver1.setFirstName("testbeaver");
     * beaver1.setLastName("Perez");
     * beaver1.setUser(user1);
     *
     * user1.setBeaver(beaver1);
     *
     * Encargo encargo1 = new Encargo();
     * encargo1.setBeaver(this.userService.findUserByUsername("testuser").getBeaver());
     * encargo1.setDescripcion("Encargo1 correcto");
     * encargo1.setTitulo("Encargo1");
     * encargo1.setPrecio(50);
     * encargo1.setDisponibilidad(true);
     *
     * Solicitud solicitud1 = new Solicitud();
     * solicitud1.setEncargo(encargo1);
     * solicitud1.setEstado(true);
     * solicitud1.setPrecio(50);
     * solicitud1.setId(1);
     *
     * Solicitud solicitud2 = new Solicitud();
     * solicitud2.setEncargo(encargo1);
     * solicitud2.setEstado(true);
     * solicitud2.setPrecio(50);
     * solicitud2.setId(2);
     *
     * List<Solicitud> list = new ArrayList<Solicitud>();
     * list.add(solicitud1);
     * list.add(solicitud2);
     * encargo1.setSolicitudes(list);
     *
     * }
     */

    @Test
    @Transactional
    void testSaveSolicitud(){
        Solicitud solicitud = new Solicitud();
        solicitud.setEstado(Estados.PENDIENTE);
        solicitud.setEncargo(encargo);
        solicitud.setBeaver(beaver);
        solicitud.setDescripcion("descripcion");
        solicitud.setPrecio(35.0);
        solicitudService.saveSolicitud(solicitud);

        assertThat(solicitud.getId().longValue()).isNotEqualTo(0);
        assertThat(solicitudService.existsSol(solicitud.getId())).isTrue();
    }

    @Test
    @Transactional
    void testAcceptSolicitud() throws MessagingException{
        Solicitud solicitud = new Solicitud();
        solicitud.setEstado(Estados.PENDIENTE);
        solicitud.setEncargo(encargo);
        solicitud.setBeaver(beaver);
        solicitud.setDescripcion("descripcion");
        solicitud.setPrecio(35.0);
        solicitudService.saveSolicitud(solicitud);

        solicitudService.aceptarSolicitud(solicitud, beaver);

        assertThat(solicitud.getEstado()).isEqualTo(Estados.ACEPTADO);
    }

    @Test
    @Transactional
    void testRejectSolicitud() throws MessagingException{
        Solicitud solicitud = new Solicitud();
        solicitud.setEstado(Estados.PENDIENTE);
        solicitud.setEncargo(encargo);
        solicitud.setBeaver(beaver);
        solicitud.setDescripcion("descripcion");
        solicitud.setPrecio(35.0);
        solicitudService.saveSolicitud(solicitud);

        solicitudService.rechazarSolicitud(solicitud, beaver);

        assertThat(solicitud.getEstado()).isEqualTo(Estados.RECHAZADO);
    }

    @Test
    @Transactional
    void shouldFindById(){
        Solicitud solicitud = new Solicitud();
        solicitud.setEstado(Estados.PENDIENTE);
        solicitud.setEncargo(encargo);
        solicitud.setBeaver(beaver);
        solicitud.setPrecio(199.0);
        solicitud.setDescripcion("descripcion");
        solicitudService.saveSolicitud(solicitud);


        int id = solicitud.getId();
        assertThat(solicitudService.existsSol(id)).isTrue();
        Solicitud test = solicitudService.findById(id);
        assertThat(solicitud).isEqualTo(test);
    }

    @Test
    @Transactional
    public void deleteSolicitudByIdTest() {

        Solicitud solicitud = new Solicitud();
        solicitud.setEstado(Estados.PENDIENTE);
        solicitud.setPrecio(25.00);
        solicitud.setDescripcion("descripcion");

        Solicitud solicitud2 = new Solicitud();
        solicitud2.setEstado(Estados.PENDIENTE);
        solicitud2.setPrecio(25.00);
        solicitud2.setDescripcion("descripcion");

        this.solicitudService.saveSolicitud(solicitud);
        this.solicitudService.saveSolicitud(solicitud2);
        int res = this.solicitudService.solicitudCount();
        this.solicitudService.deleteSolicitudById(solicitud2.getId());

        Assertions.assertEquals(this.solicitudService.solicitudCount(), res - 1);
    }

    @Test
    @Transactional  //Intenta eliminar una solicitud cuyo id no existe
    public void deleteSolicitudByIdTestHasErrors() {
        Solicitud solicitud = new Solicitud();
        solicitud.setEstado(Estados.PENDIENTE);
        solicitud.setPrecio(25.00);
        solicitud.setDescripcion("descripcion");

        Solicitud solicitud2 = new Solicitud();
        solicitud2.setEstado(Estados.PENDIENTE);
        solicitud2.setPrecio(25.00);
        solicitud2.setDescripcion("descripcion");

        this.solicitudService.saveSolicitud(solicitud);
        this.solicitudService.saveSolicitud(solicitud2);
        int res = this.solicitudService.solicitudCount();

        Optional<Solicitud> solicitud3 = this.solicitudService.findSolicitudById(1836516289);
        if(solicitud3.isPresent()){
            this.solicitudService.deleteSolicitudById(1836516289);
        }

        Assertions.assertEquals(this.solicitudService.solicitudCount(), res);
    }

    @Test
    @Transactional
    public void deleteSolicitudTest() {

        Solicitud solicitud = new Solicitud();
        solicitud.setEstado(Estados.PENDIENTE);
        solicitud.setPrecio(25.00);
        solicitud.setId(5);
        solicitud.setDescripcion("descripcion");

        Solicitud solicitud2 = new Solicitud();
        solicitud2.setEstado(Estados.PENDIENTE);
        solicitud2.setPrecio(25.00);
        solicitud2.setId(6);
        solicitud2.setDescripcion("descripcion");

        this.solicitudService.saveSolicitud(solicitud);
        this.solicitudService.saveSolicitud(solicitud2);
        int res = this.solicitudService.solicitudCount();
        this.solicitudService.deleteSolicitud(solicitud2);
        Assertions.assertSame(this.solicitudService.findSolicitudById(solicitud2.getId()), Optional.empty());
    }

    @Test
    @Transactional  //Intenta eliminar una solicitud que no existe
    public void deleteSolicitudTestHasErrors() {
        List<Solicitud> solicitudes = this.solicitudService.findSolicitudByEncargoId(1);
        int res = solicitudes.size();
        Optional<Solicitud> sol = this.solicitudService.findSolicitudById(171671872);
        Solicitud sol2 = new Solicitud();

        if(sol.isPresent()) {
            sol2 = sol.get();
        }

        this.solicitudService.deleteSolicitud(sol2);

        Assertions.assertNotEquals(this.solicitudService.solicitudCount(), res - 1);


    }

    //Se necesita hacer un save encargo, por lo que no funcionará cuando estén implementados los encargos
    /*
     * @Test
     *
     * @Transactional
     * public void findSolicitudByEncargoIdTest() {
     * Encargo encargo = this.createDummyEncargo();
     * Collection<Solicitud> sol = encargo.getSolicitudes();
     * encargo.setId(811);
     * List<Solicitud> res = this.solicitudService.findSolicitudByEncargoId(encargo.getId());
     * Assertions.assertEquals(sol, res);
     * }
     */

    @Test
    @Transactional
    public void solicitudCountTest() {

        int res = this.solicitudService.solicitudCount();
        Solicitud solicitud = new Solicitud();
        solicitud.setEstado(Estados.PENDIENTE);
        solicitud.setPrecio(25.00);
        solicitud.setId(5);
        solicitud.setDescripcion("descripcion");

        Solicitud solicitud2 = new Solicitud();
        solicitud2.setEstado(Estados.PENDIENTE);
        solicitud2.setPrecio(25.00);
        solicitud2.setId(6);
        solicitud2.setDescripcion("descripcion");

        this.solicitudService.saveSolicitud(solicitud);
        this.solicitudService.saveSolicitud(solicitud2);
        Assert.isTrue(this.solicitudService.solicitudCount() == res + 2);
    }

    @Test
    @Transactional
    public void addNewSolicitudForEncargo() {
        Encargo encargo = this.createDummyEncargo();
        Collection<Solicitud> listaSolicitudes = encargo.getSolicitud();
        int numSolicitudes = listaSolicitudes.size();

        Solicitud solicitud = new Solicitud();
        solicitud.setEncargo(encargo);
        solicitud.setEstado(Estados.PENDIENTE);
        solicitud.setPrecio(25.00);
        solicitud.setId(3);
        solicitud.setDescripcion("descripcion");
        this.solicitudService.saveSolicitud(solicitud);

        listaSolicitudes.add(solicitud);

        Assertions.assertEquals(numSolicitudes + 1, encargo.getSolicitud().size());

    }


    @Test
    @Transactional
    public void findSolicitudByIdTest() {
        Solicitud solicitud = new Solicitud();
        solicitud.setEstado(Estados.PENDIENTE);
        solicitud.setPrecio(25.00);
        solicitud.setDescripcion("descripcion");
        this.solicitudService.saveSolicitud(solicitud);

        Optional<Solicitud> res = this.solicitudService.findSolicitudById(solicitud.getId());
        Solicitud sol = res.get();

        Assertions.assertEquals(sol, solicitud);
    }

    @Test
    @Transactional  //Busca una solicitud cuyo id no existe
    public void findSolicitudByIdTestHasErrors(){
        Optional<Solicitud> solicitud = this.solicitudService.findSolicitudById(15265354);
        Solicitud sol;

        if(solicitud.isPresent()){
            sol = solicitud.get();
        } else {
            sol = null;
        }

        Assertions.assertEquals(sol, null);
    }


    @Test
    @Transactional
    public void crearSolicitud(){
        Solicitud solicitud = new Solicitud();
        solicitud.setDescripcion("muy largaesta descripcion eeeeeeeee");
        solicitud.setBeaver(beaveralt);
        solicitud.setEncargo(encargo);
        this.solicitudService.crearSolicitud(solicitud, encargo, beaver);

        Solicitud solBD = this.solicitudService.findById(solicitud.getId());
        assertThat(solBD).isEqualTo(solicitud);
        assertThat(solBD.getEstado()).isEqualTo(Estados.PENDIENTE);
    }

    @Test
    @Transactional
    public void existSolicitudParaUnEncargo(){
        Solicitud solicitud = new Solicitud();
        solicitud.setDescripcion("muy largaesta descripcion eeeeeeeee");
        solicitud.setBeaver(beaveralt);
        solicitud.setEncargo(encargo);
        this.solicitudService.crearSolicitud(solicitud, encargo, beaver);

        Boolean res = this.solicitudService.existSolicitudByBeaver(beaver, encargo);
        assertThat(res).isTrue();
    }

    @Test
    @Transactional
    public void dontExistSolicitudParaUnEncargo(){
        Boolean res = this.solicitudService.existSolicitudByBeaver(beaver, encargo);
        assertThat(res).isFalse();
    }

    @Test
    @Transactional
    public void existASolicitudFinalizadaParaUnEncargo(){
        Solicitud solicitud = new Solicitud();
        solicitud.setDescripcion("muy largaesta descripcion eeeeeeeee");
        solicitud.setBeaver(beaveralt);
        solicitud.setEncargo(encargo);
        this.solicitudService.crearSolicitud(solicitud, encargo, beaver);
        solicitud.setEstado(Estados.FINALIZADO);
        this.solicitudService.saveSolicitud(solicitud);


        Boolean res = this.solicitudService.existSolicitudByBeaver(beaver, encargo);
        assertThat(res).isFalse(); //RN: Pueden existir solicitudes finalizadas previas para un encargo
    }

    @Test
    @Transactional
    public void existASolicitudRechazadaParaUnEncargo(){
        Solicitud solicitud = new Solicitud();
        solicitud.setDescripcion("muy largaesta descripcion eeeeeeeee");
        solicitud.setBeaver(beaveralt);
        solicitud.setEncargo(encargo);
        this.solicitudService.crearSolicitud(solicitud, encargo, beaver);
        solicitud.setEstado(Estados.RECHAZADO);
        this.solicitudService.saveSolicitud(solicitud);

        Boolean res = this.solicitudService.existSolicitudByBeaver(beaver, encargo);
        assertThat(res).isFalse(); //RN: Pueden existir solicitudes Rechazadas previas para un encargo
    }

    @Test
    @Transactional
    public void existASolicitudAceptadaParaUnEncargo(){
        Solicitud solicitud = new Solicitud();
        solicitud.setDescripcion("muy largaesta descripcion eeeeeeeee");
        solicitud.setBeaver(beaveralt);
        solicitud.setEncargo(encargo);
        this.solicitudService.crearSolicitud(solicitud, encargo, beaver);
        solicitud.setEstado(Estados.ACEPTADO);
        this.solicitudService.saveSolicitud(solicitud);

        Boolean res = this.solicitudService.existSolicitudByBeaver(beaver, encargo);
        assertThat(res).isTrue(); //RN: No pueden existir solicitudes previas Aceptadas para un encargo
    }

}
