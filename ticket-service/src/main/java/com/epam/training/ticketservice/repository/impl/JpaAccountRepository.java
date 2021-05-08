package com.epam.training.ticketservice.repository.impl;

import com.epam.training.ticketservice.dataaccess.dao.AccountDao;
import com.epam.training.ticketservice.dataaccess.projection.AccountProjection;
import com.epam.training.ticketservice.exceptions.CrudException;
import com.epam.training.ticketservice.exceptions.SignInOutException;
import com.epam.training.ticketservice.modell.Account;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class JpaAccountRepository {

    private final AccountDao accountDao;

    public JpaAccountRepository(AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    public void saveAccount(String username,String password) throws CrudException {
        Account account = new Account(username,password,false);
        Optional<AccountProjection> accountExist;
        accountExist = findAccount(username);
        if(accountExist.isEmpty()){
            accountDao.save(mapAccount(account));
            return;
        }
        throw new CrudException("Account already exist with this username");

    }

    private AccountProjection mapAccount(Account account) {
        return new AccountProjection(account.getUsername(), account.getPassword(), account.isLoggedIn());
    }

    private Account mapAccount(AccountProjection accountProjection) {
        return new Account(accountProjection.getUsername(),accountProjection.getPassword(),accountProjection.isLoggedIn());
    }

    private Optional<AccountProjection> findAccount(String username){
        return accountDao.findAll()
                .stream()
                .filter(projection -> projection.getUsername().equals(username)).findFirst();

    }

    public void findAccount(String username, String password) throws SignInOutException {
        Optional<AccountProjection> result = findAccount(username);
        if(result.isPresent()){
            Account account = mapAccount(result.get());
            if(!account.getUsername().equals(username) || !account.getPassword().equals(password)){
                throw new SignInOutException("Login failed due to incorrect credentials");
            }

            if(account.isLoggedIn()){
                throw new SignInOutException("You already signed in");
            }
            AccountProjection accountProjection = result.get();
            accountProjection.setLoggedIn(true);
            updateProjection(accountProjection);
            account.setLoggedIn(true);
            return;

        }
        throw new SignInOutException("Login failed due to incorrect credentials");
    }

    private void updateProjection(AccountProjection accountProjection){
        accountDao.save(accountProjection);
    }

}
