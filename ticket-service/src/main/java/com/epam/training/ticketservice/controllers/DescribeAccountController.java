package com.epam.training.ticketservice.controllers;

import com.epam.training.ticketservice.commands.DescribeAccountCommand;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
public class DescribeAccountController {

    @ShellMethod(value = "Describe account", key = "describe account")
    public String describeAccount(){
        DescribeAccountCommand describeAccountCommand = new DescribeAccountCommand();
        return describeAccountCommand.execute();
    }

}
