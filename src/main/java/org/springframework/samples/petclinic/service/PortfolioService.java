package org.springframework.samples.petclinic.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Portfolio;
import org.springframework.samples.petclinic.repository.PortFolioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PortfolioService {

    private PortFolioRepository portFolioRepository;

    @Autowired
    public PortfolioService(PortFolioRepository portFolioRepository) {
        this.portFolioRepository = portFolioRepository;
    }

    @Transactional
    public Portfolio savePortfolio(Portfolio portfolio) {
        return this.portFolioRepository.save(portfolio);
    }

    @Transactional
    public Portfolio findPortfolioByBeaverId(int id) {
        return this.portFolioRepository.findPortfolioByByBeaverId(id);

    }





}

