package org.springframework.samples.petclinic.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.Collection;
import java.util.HashSet;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.samples.petclinic.model.Beaver;
import org.springframework.samples.petclinic.model.Especialidad;
import org.springframework.samples.petclinic.model.Portfolio;
import org.springframework.samples.petclinic.model.User;

@SpringBootTest
public class PortfolioServiceTests {

    @Autowired
    protected BeaverService beaverService;

    @Autowired
    protected PortfolioService portfolioService;

    //aux method
    public Beaver dummyBeaver(){
        User user = new User();
        user.setUsername("User");
        user.setPassword("supersecretpass");
        user.setEnabled(true);

        Beaver beaver = new Beaver();
        beaver.setFirstName("Nombre");
        beaver.setLastName("Apellidos");
        beaver.setEmail("valid@gmail.com");
        Collection<Especialidad> esp = new HashSet<>();
        esp.add(Especialidad.ILUSTRACION);
        beaver.setEspecialidades(esp);
        beaver.setDni("12345678Q");
        beaver.setUser(user);
        beaver.setEncargos(new HashSet<>());

        this.beaverService.saveBeaver(beaver);
        return beaver;
    }

    //aux method
    public Portfolio dummyPerfil(Beaver beaver){
        Portfolio portfolio = new Portfolio();
        portfolio.setSobreMi("This is my portfolio");
        portfolio.setBeaver(beaver);
        beaver.setPortfolio(portfolio);
        this.beaverService.saveBeaver(beaver);
        return portfolio;
    }

    @Test
    @Transactional
    public void shouldInsertPerfil(){
        Beaver beaver = this.dummyBeaver();
        Portfolio portfolio = this.dummyPerfil(beaver);
        this.portfolioService.savePortfolio(portfolio);
        assertThat(portfolio.getId().longValue()).isNotEqualTo(0);
    }

    @Test
    @Transactional
    public void findPortfolioByBeaverId(){
        Beaver beaver = this.dummyBeaver();
        int beaverId = beaver.getId();
        assertTrue(this.portfolioService.findPortfolioByBeaverId(beaverId).equals(beaver.getPortfolio()));
    }

}
