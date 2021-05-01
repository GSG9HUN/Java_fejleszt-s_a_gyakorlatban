package com.epam.training.ticketservice.controllers;

import com.epam.training.ticketservice.commands.LogInCommand;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
public class LoginController {

    @ShellMethod(value = "Sign in as an admin",key = "sign in privileged")
    public String login(String username,String password){
        LogInCommand logInCommand = new LogInCommand();

        return logInCommand.execute(username,password);
    }
}
