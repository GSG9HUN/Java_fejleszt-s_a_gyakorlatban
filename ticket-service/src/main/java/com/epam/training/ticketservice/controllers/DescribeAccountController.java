package com.epam.training.ticketservice.controllers;

import com.epam.training.ticketservice.Modell.AdminAccount;
import com.epam.training.ticketservice.services.DescribeAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
public class DescribeAccountController {

    private DescribeAccountService describeAccountService;

    @Autowired
    DescribeAccountController(DescribeAccountService describeAccountService){
        this.describeAccountService = describeAccountService;
    }

    @ShellMethod(value = "Describe account", key = "describe account")
    public String describeAccount(){
        if(describeAccountService.describe())
            return "Signed in with privileged account: "+ AdminAccount.getUsername();
        return "You are not signed in";
    }

}
