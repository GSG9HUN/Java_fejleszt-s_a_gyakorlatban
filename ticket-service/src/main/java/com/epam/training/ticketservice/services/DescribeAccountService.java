package com.epam.training.ticketservice.services;

import com.epam.training.ticketservice.Modell.AdminAccount;
import org.springframework.stereotype.Service;

@Service
public class DescribeAccountService {

    public boolean describe(){
        return AdminAccount.isLogedIn();

    }
}
