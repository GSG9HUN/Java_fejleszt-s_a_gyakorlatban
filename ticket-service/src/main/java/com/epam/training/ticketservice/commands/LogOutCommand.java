package com.epam.training.ticketservice.commands;

import com.epam.training.ticketservice.Modell.AdminAccount;

public class LogOutCommand implements Command{
    @Override
    public String execute(String... args) {
        AdminAccount.setIsLogedIn(false);
        return "";
    }
}
