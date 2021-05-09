package com.epam.training.ticketservice.dataaccess.projection;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class AccountProjection {

    @Id
    private String username;
    private String password;
    private boolean isLoggedIn;

    protected AccountProjection() {

    }

    public AccountProjection(String username,String password, boolean isLoggedIn) {
        this.username = username;
        this.password = password;
        this.isLoggedIn = isLoggedIn;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public boolean isLoggedIn() {
        return isLoggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        isLoggedIn = loggedIn;
    }
}
