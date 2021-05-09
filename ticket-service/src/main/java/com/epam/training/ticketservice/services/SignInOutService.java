package com.epam.training.ticketservice.services;

import com.epam.training.ticketservice.exceptions.CrudException;
import com.epam.training.ticketservice.modell.Account;
import com.epam.training.ticketservice.modell.AdminAccount;
import com.epam.training.ticketservice.exceptions.SignInOutException;
import com.epam.training.ticketservice.repository.impl.JpaAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SignInOutService {

    private JpaAccountRepository jpaAccountRepository;

    @Autowired
    public SignInOutService(JpaAccountRepository jpaAccountRepository) {
        this.jpaAccountRepository = jpaAccountRepository;
    }

    public void signInPrivileged(String username, String password) throws SignInOutException {
        if (AdminAccount.getPassword().equals(password) && AdminAccount.getUsername().equals(username)
                && !AdminAccount.isLogedIn()) {
            AdminAccount.setIsLogedIn(true);
            return;
        }
        if (AdminAccount.isLogedIn()) {
            throw new SignInOutException("You already logged in");
        }
        throw new SignInOutException("Login failed due to incorrect credentials");
    }

    public void signOut() throws SignInOutException, CrudException {
        if (AdminAccount.isLogedIn()) {
            AdminAccount.setIsLogedIn(false);
            return;
        }

        Optional<Account> optionalAccount = jpaAccountRepository.getLoggedInAccount();
        if (optionalAccount.isPresent()) {
            Account account = optionalAccount.get();
            account.setLoggedIn(false);
            jpaAccountRepository.updateAccount(account);
            return;
        }

        throw new SignInOutException("You are not logged in");

    }

    public void signIn(String username, String password) throws SignInOutException {
        jpaAccountRepository.findAccount(username, password);
    }

}
