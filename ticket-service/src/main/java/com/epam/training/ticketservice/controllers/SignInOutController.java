package com.epam.training.ticketservice.controllers;

import com.epam.training.ticketservice.exceptions.SignInOutException;
import com.epam.training.ticketservice.services.SignInOutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
public class SignInOutController {

    SignInOutService signInOutService;

    @Autowired
    SignInOutController(SignInOutService signInOutService){
        this.signInOutService=signInOutService;
    }




    @ShellMethod(value = "Sign in as an admin",key = "sign in privileged")
    public String login(String username,String password){


        try {
            signInOutService.signIn(username,password);
        } catch (SignInOutException e) {
            return e.getMessage();
        }

        return "You are signed in";
    }

    @ShellMethod(value = "Log out from admin account",key = "sign out")
    public String logout(){

        try {
            signInOutService.signOut();
        }catch (Exception e){
            return e.getMessage();
        }
        return null;
    }
}
