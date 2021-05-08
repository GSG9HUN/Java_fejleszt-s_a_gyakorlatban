package com.epam.training.ticketservice.modell;

public class AdminAccount {
    private static final String username = "admin";
    private static final String password = "admin";

    private static boolean logedIn = false;

    public static boolean isLogedIn() {
        return logedIn;
    }

    public static void setIsLogedIn(boolean isLogedIn) {
        AdminAccount.logedIn = isLogedIn;
    }

    public static String getUsername() {
        return username;
    }

    public static String getPassword() {
        return password;
    }
}
