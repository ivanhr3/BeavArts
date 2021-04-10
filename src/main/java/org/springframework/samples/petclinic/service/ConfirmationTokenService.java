package org.springframework.samples.petclinic.service;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.ConfirmationToken;
import org.springframework.samples.petclinic.repository.ConfirmationTokenRepository;
import org.springframework.stereotype.Service;

@Service
public class ConfirmationTokenService {
    
    @Autowired
    private ConfirmationTokenRepository confirmationTokenRepository;

    @Transactional
    public void saveConfirmationToken(ConfirmationToken confirmationToken){
        confirmationTokenRepository.save(confirmationToken);
    }

    @Transactional
    public Optional<ConfirmationToken> findConfirmationTokenByToken(String token){
        return confirmationTokenRepository.findConfirmationTokenByConfirmationToken(token);
    }

    @Transactional
    public void deleteConfirmationToken(Integer id){
        confirmationTokenRepository.deleteById(id);
    }
}
