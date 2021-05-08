package com.epam.training.ticketservice.services;

import com.epam.training.ticketservice.modell.AdminAccount;
import com.epam.training.ticketservice.exceptions.SignInOutException;
import org.springframework.stereotype.Service;

@Service
public class SignInOutService {

    public void signIn(String username, String password) throws SignInOutException {
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

    public void signOut() throws SignInOutException {
        if (!AdminAccount.isLogedIn()) {
            throw new SignInOutException("You are not logged in");
        }
        AdminAccount.setIsLogedIn(false);
    }
}
