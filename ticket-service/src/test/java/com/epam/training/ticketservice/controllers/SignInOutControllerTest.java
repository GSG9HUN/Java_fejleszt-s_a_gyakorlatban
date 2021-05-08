package com.epam.training.ticketservice.controllers;

import com.epam.training.ticketservice.exceptions.SignInOutException;
import com.epam.training.ticketservice.modell.AdminAccount;
import com.epam.training.ticketservice.services.SignInOutService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SignInOutControllerTest {
    SignInOutService signInOutService = Mockito.mock(SignInOutService.class);

    @Test
    public void testLogInMethodWithCorrectCerdentails() {
        //given
        String username = "admin";
        String password = "admin";
        AdminAccount.setIsLogedIn(false);
        SignInOutController signInOutController = new SignInOutController(signInOutService);
        //when
        String result =signInOutController.login(username,password);
        //then
        assertEquals("You are signed in",result);

    }

    @Test
    public void testLogInMethodWithInCorrectCerdentails() throws SignInOutException {
        //given
        String username = "sandor";
        String password = "sandor";
        AdminAccount.setIsLogedIn(false);
        String result = null;
        SignInOutController signInOutController = new SignInOutController(signInOutService);
        //when
        doThrow(new SignInOutException("Login failed due to incorrect credentials")).when(signInOutService).signIn(username,password);
        result =signInOutController.login(username,password);


        //then
        assertEquals("Login failed due to incorrect credentials",result);

    }

    @Test
    public void testLogInMethodWithInCorrectCerdentailsButAlreadyLoggedIn() throws SignInOutException {
        //given
        String username = "sandor";
        String password = "sandor";
        AdminAccount.setIsLogedIn(false);
        String result = null;
        SignInOutController signInOutController = new SignInOutController(signInOutService);
        //when
        doThrow(new SignInOutException("You already logged in")).when(signInOutService).signIn(username,password);
        result =signInOutController.login(username,password);


        //then
        assertEquals("You already logged in",result);
    }

    @Test
    public void testLogOutMethodButUserAreNotLoggedIn() throws SignInOutException {
        //given
        SignInOutController signInOutController = new SignInOutController(signInOutService);
        AdminAccount.setIsLogedIn(false);
        doThrow(new SignInOutException("You are not logged in")).when(signInOutService).signOut();
        String result = null;
        //when
        result =signInOutController.logout();

        //then
        assertEquals("You are not logged in",result);
    }

    @Test
    public void testLogOutMethod() throws SignInOutException {
        //given
        SignInOutController signInOutController = new SignInOutController(signInOutService);
        AdminAccount.setIsLogedIn(true);
        //when
        signInOutController.logout();

        //then
        verify(signInOutService).signOut();
    }

}