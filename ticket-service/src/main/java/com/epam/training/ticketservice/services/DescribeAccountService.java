package com.epam.training.ticketservice.services;

import com.epam.training.ticketservice.modell.Account;
import com.epam.training.ticketservice.modell.AdminAccount;
import com.epam.training.ticketservice.repository.impl.JpaAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DescribeAccountService {

    private JpaAccountRepository jpaAccountRepository;

    @Autowired
    public DescribeAccountService(JpaAccountRepository jpaAccountRepository) {
        this.jpaAccountRepository = jpaAccountRepository;
    }

    public boolean describe() {
        return AdminAccount.isLogedIn();
    }

    public boolean describeUser() {
        List<Account> accountList = jpaAccountRepository.getAll();

        for (Account account : accountList) {
            if (account.isLoggedIn()) {
                return true;
            }
        }
        return false;
    }

    public String getAccount() {
        List<Account> accountList = jpaAccountRepository.getAll();

        for (Account account : accountList) {
            if (account.isLoggedIn()) {
                return account.getUsername();
            }
        }
        return null;
    }
}
