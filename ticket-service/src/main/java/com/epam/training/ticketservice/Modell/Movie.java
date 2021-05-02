package com.epam.training.ticketservice.Modell;

public class Movie {

    private String name;
    private String category;
    private int length;


    public Movie(String name,String category, int length){
        this.name=name;
        this.category=category;
        this.length=length;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public int getLength() {
        return length;
    }
}
