package com.epam.training.ticketservice.services;

import com.epam.training.ticketservice.Modell.AdminAccount;
import org.springframework.stereotype.Service;

@Service
public class SignInOutService {

    public void signIn(String username, String password) throws Exception {

        if(AdminAccount.getPassword().equals(password) && AdminAccount.getUsername().equals(username) &&
            !AdminAccount.isLogedIn()){
            AdminAccount.setIsLogedIn(true);
            return;
        }
        if(AdminAccount.isLogedIn()){
            throw new Exception("You already logged in");
        }
        if(AdminAccount.getPassword().equals(password) || AdminAccount.getUsername().equals(username)){
            throw new Exception("Login failed due to incorrect credentials");
        }





    }

    public void signOut() throws Exception {

        if(!AdminAccount.isLogedIn())
            throw new Exception("You are not logged in");
        AdminAccount.setIsLogedIn(false);

    }

}
