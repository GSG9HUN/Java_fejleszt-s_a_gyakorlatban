package com.epam.training.ticketservice.services;

import com.epam.training.ticketservice.exceptions.CrudException;
import com.epam.training.ticketservice.repository.impl.JpaAccountRepository;
import org.springframework.stereotype.Service;

@Service
public class CreateAccountService {

    private JpaAccountRepository jpaAccountRepository;

    public CreateAccountService(JpaAccountRepository jpaAccountRepository) {
        this.jpaAccountRepository = jpaAccountRepository;
    }

    public void createAccount(String username, String password) throws CrudException {
        jpaAccountRepository.saveAccount(username,password);
    }
}
