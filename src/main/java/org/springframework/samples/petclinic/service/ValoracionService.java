package org.springframework.samples.petclinic.service;


import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Beaver;
import org.springframework.samples.petclinic.model.Valoracion;

import org.springframework.samples.petclinic.repository.ValoracionRepository;
import org.springframework.stereotype.Service;

@Service
public class ValoracionService {

    private final ValoracionRepository valoracionRepository;

    @Autowired
    private BeaverService beaverService;

    @Autowired
    public ValoracionService(final ValoracionRepository valoracionRepository){
        this.valoracionRepository = valoracionRepository;
    }

    @Transactional
    public Iterable<Valoracion> findValoracionesByBeaverId(final int id){
        return this.valoracionRepository.findValoracionesByBeaverId(id);
    }

    @Transactional
    public void crearValoracion(Valoracion val, Beaver reciever){
        
        Collection<Valoracion> valcurrent = reciever.getValoraciones();
        valcurrent.add(val);
        reciever.setValoraciones(valcurrent);
        val.setBeaver(reciever);

        beaverService.saveBeaver(reciever);
        valoracionRepository.save(val);
    }

    @Transactional
	public Valoracion saveValoracion(final Valoracion valoracion) {
		return this.valoracionRepository.save(valoracion);
	}

    public Double calcularValoracion(Integer beaverId){
        return this.valoracionRepository.calcularPuntuacion(beaverId);   
    }


}
