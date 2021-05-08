package com.epam.training.ticketservice.controllers;

import com.epam.training.ticketservice.modell.AdminAccount;
import com.epam.training.ticketservice.services.DescribeAccountService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class DescribeAccountControllerTest {

    DescribeAccountService describeAccountService = Mockito.mock(DescribeAccountService.class);

    @Test
    public void testDescribeAccountMethodButUserNotLogged(){
        //given
        DescribeAccountController describeAccountController = new DescribeAccountController(describeAccountService);
        when(describeAccountService.describe()).thenReturn(false);
        //when
        String result = describeAccountController.describeAccount();
        //then

        assertEquals("You are not signed in",result);
    }

    @Test
    public void testDescribeAccountMethod(){
        //given
        DescribeAccountController describeAccountController = new DescribeAccountController(describeAccountService);
        when(describeAccountService.describe()).thenReturn(true);
        //when
        String result = describeAccountController.describeAccount();
        //then

        assertEquals("Signed in with privileged account: " + AdminAccount.getUsername(),result);
    }

}