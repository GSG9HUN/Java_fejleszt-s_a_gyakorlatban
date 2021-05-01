package com.epam.training.ticketservice.Modell;

public class AdminAccount {
    private static final String username = "admin";
    private static final String password ="admin";

    private static boolean isLogedIn = false;

    public static boolean isIsLogedIn() {
        return isLogedIn;
    }

    public static void setIsLogedIn(boolean isLogedIn) {
        AdminAccount.isLogedIn = isLogedIn;
    }

    public static String getUsername() {
        return username;
    }

    public static String getPassword() {
        return password;
    }
}
