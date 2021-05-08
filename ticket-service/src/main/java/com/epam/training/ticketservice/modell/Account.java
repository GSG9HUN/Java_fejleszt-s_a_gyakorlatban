package com.epam.training.ticketservice.modell;

public class Account {

    final String username;
    final String password;
    private boolean isLoggedIn;

    public Account(String username, String password, boolean isLoggedIn){
        this.username=username;
        this.password=password;
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
