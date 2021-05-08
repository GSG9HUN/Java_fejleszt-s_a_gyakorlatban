package com.epam.training.ticketservice.services;

import com.epam.training.ticketservice.exceptions.SignInOutException;
import com.epam.training.ticketservice.modell.AdminAccount;
import com.epam.training.ticketservice.repository.impl.JpaAccountRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SignInOutServiceTest {
    JpaAccountRepository jpaAccountRepository = Mockito.mock(JpaAccountRepository.class);
    SignInOutService signInOutService = new SignInOutService(jpaAccountRepository);

    @Test
    public void testSignInMethodWithInvalidUsername(){
        //given
        String username = "sandor";
        String password = "admin";
        AdminAccount.setIsLogedIn(false);
        String result =null;
        //when
        try {
            signInOutService.signInPrivileged(username,password);
        } catch (SignInOutException e) {
            result = e.getMessage();
        }
        //then
        assertEquals("Login failed due to incorrect credentials",result);


    }

    @Test
    public void testSignInMethodWithInvalidPassword(){
        //given
        String username = "admin";
        String password = "sandor";
        AdminAccount.setIsLogedIn(false);
        String result =null;
        //when
        try {
            signInOutService.signInPrivileged(username,password);
        } catch (SignInOutException e) {
            result = e.getMessage();
        }
        //then
        assertEquals("Login failed due to incorrect credentials",result);
    }

    @Test
    public void testSignInMethodWithInvalidUsernameAndPassword(){
        //given
        String username = "sandor";
        String password = "sandor";
        AdminAccount.setIsLogedIn(false);
        String result =null;
        //when
        try {
            signInOutService.signInPrivileged(username,password);
        } catch (SignInOutException e) {
            result = e.getMessage();
        }
        //then
        assertEquals("Login failed due to incorrect credentials",result);
    }

    @Test
    public void testSignInMethodWithCorrectCerdentials() throws SignInOutException {
        //given
        String username = "admin";
        String password = "admin";
        AdminAccount.setIsLogedIn(false);
        //when
        signInOutService.signInPrivileged(username,password);

        //then
        assertTrue(AdminAccount.isLogedIn());
    }

    @Test
    public void testSignInMethodWithCorrectCerdentailsButUserAlreadyLoggedIn() {
        //given
        String username = "admin";
        String password = "admin";
        String result=null;
        AdminAccount.setIsLogedIn(true);
        //when
        try {
            signInOutService.signInPrivileged(username,password);
        } catch (SignInOutException e) {
            result = e.getMessage();
        }

        //then
        assertEquals("You already logged in",result);
    }

    @Test
    public void testSignOutMethod() throws SignInOutException {
        //given
        AdminAccount.setIsLogedIn(true);
        //when
        signInOutService.signOutPrivileged();
        //then
        assertFalse(AdminAccount.isLogedIn());
    }

    @Test
    public void testSignOutMethodButUserArentLoggedIn(){
        //given
        AdminAccount.setIsLogedIn(false);
        String result = null;
        //when
        try {
            signInOutService.signOutPrivileged();
        } catch (SignInOutException e) {
            result = e.getMessage();
        }
        //then
        assertEquals("You are not logged in",result);
    }

}