package com.epam.training.ticketservice.repository.impl;

import com.epam.training.ticketservice.dataaccess.dao.AccountDao;
import com.epam.training.ticketservice.dataaccess.projection.AccountProjection;
import com.epam.training.ticketservice.exceptions.CrudException;
import com.epam.training.ticketservice.exceptions.SignInOutException;
import com.epam.training.ticketservice.modell.Account;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

class JpaAccountRepositoryTest {

    private AccountDao accountDao = Mockito.mock(AccountDao.class);
    JpaAccountRepository jpaAccountRepository= new JpaAccountRepository(accountDao);

    @Test
    public void testSaveAccountMethodWithNonExistentAccount(){
        //given
        String username = "testUser";
        String password = "testPassword";
        String result = null;
        //when
        try {
            jpaAccountRepository.saveAccount(username,password);
        } catch (CrudException e) {
            result=e.getMessage();
        }
        //then
        assertNull(result);
    }

    @Test
    public void testSaveAccountMethodWithExistentAccount(){
        //given
        String username = "testUser";
        String password = "testPassword";
        String result = null;
        when(accountDao.findAll()).thenReturn(Collections.singletonList(new AccountProjection("testUser", "testPassword", false)));
        //when
        try {
            jpaAccountRepository.saveAccount(username,password);
        } catch (CrudException e) {
            result=e.getMessage();
        }
        //then
        assertEquals("Account already exist with this username",result);
    }

    @Test
    public void testFindAccountMethodWithWrongPassword(){
        //given
        String username = "wrongUser";
        String password = "correctPass";
        String result = null;
        when(accountDao.findAll()).thenReturn(List.of(new AccountProjection("wrongUser","wrongPass",false)));
        //when
        try {
            jpaAccountRepository.findAccount(username,password);
        } catch (SignInOutException e) {
            result = e.getMessage();
        }
        //then
        assertEquals("Login failed due to incorrect credentials",result);
    }

    @Test
    public void testFindAccountMethodWithWrongUsername(){
        //given
        String username = "wrongUser";
        String password = "correctPass";
        String result = null;
        when(accountDao.findAll()).thenReturn(List.of(new AccountProjection("correctPass","correctPass",false)));
        //when
        try {
            jpaAccountRepository.findAccount(username,password);
        } catch (SignInOutException e) {
            result = e.getMessage();
        }
        //then
        assertEquals("Login failed due to incorrect credentials",result);
    }

    @Test
    public void testFindAccountMethodButUserAlreadyLoggedIn(){
        //given
        String username = "correctUser";
        String password = "correctPass";
        String result = null;
        when(accountDao.findAll()).thenReturn(List.of(new AccountProjection("correctUser","correctPass",true)));
        //when
        try {
            jpaAccountRepository.findAccount(username,password);
        } catch (SignInOutException e) {
            result = e.getMessage();
        }
        //then
        assertEquals("You already signed in",result);
    }


    @Test
    public void testFindAccountMethod(){
        //given
        String username = "correctUser";
        String password = "correctPass";
        String result = null;
        when(accountDao.findAll()).thenReturn(List.of(new AccountProjection("correctUser","correctPass",false)));
        //when
        try {
            jpaAccountRepository.findAccount(username,password);
        } catch (SignInOutException e) {
            result = e.getMessage();
        }
        //then
        assertNull(result);
    }
}