package com.epam.training.ticketservice.commands;

import com.epam.training.ticketservice.Modell.AdminAccount;

public class DescribeAccountCommand implements Command{
    @Override
    public String execute(String... args) {
        if(AdminAccount.isLogedIn()){
            return "Signed in with privileged account: "+AdminAccount.getUsername();
        }

        return "You are not signed in";
    }
}
