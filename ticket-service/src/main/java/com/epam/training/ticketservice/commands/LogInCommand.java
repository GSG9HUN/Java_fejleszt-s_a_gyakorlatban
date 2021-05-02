package com.epam.training.ticketservice.commands;


import com.epam.training.ticketservice.Modell.AdminAccount;

public class LogInCommand implements Command{


    @Override
    public String execute(String ... parameter) {

        if(AdminAccount.getUsername().equals(parameter[0]) && AdminAccount.getPassword().equals(parameter[1])
            && !AdminAccount.isLogedIn()){

            AdminAccount.setIsLogedIn(true);
            return "You are signed in";
        }

        if(AdminAccount.isLogedIn()){
            return "You already signed in";
        }

        return "Login failed due to incorrect credentials";
    }
}
