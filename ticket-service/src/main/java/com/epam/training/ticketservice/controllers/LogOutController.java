package com.epam.training.ticketservice.controllers;


import com.epam.training.ticketservice.commands.LogOutCommand;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
public class LogOutController {
    @ShellMethod(value = "Log out from admin account",key = "sign out")
    public String logout(){
        LogOutCommand logOutCommand = new LogOutCommand();

        return logOutCommand.execute();
    }
}
