package com.epam.training.ticketservice.controllers;

import com.epam.training.ticketservice.exceptions.CrudException;
import com.epam.training.ticketservice.services.CreateAccountService;
import com.epam.training.ticketservice.services.DescribeAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
public class CreateAccountController {

    private CreateAccountService createAccountService;

    @Autowired
   CreateAccountController(CreateAccountService createAccountService) {
        this.createAccountService = createAccountService;
    }

    @ShellMethod(value = "Creating new user", key = "sign up")
    public String createUser(String username,String password) {

        try {
            createAccountService.createAccount(username,password);
        } catch (CrudException e) {
            return e.getMessage();
        }

        return "Account created";
    }
}
