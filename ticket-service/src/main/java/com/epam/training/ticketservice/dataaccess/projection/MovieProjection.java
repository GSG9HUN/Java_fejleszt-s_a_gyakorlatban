package com.epam.training.ticketservice.dataaccess.projection;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class MovieProjection {
    @Id
    private String name;
    private String category;
    private int length;

    public MovieProjection(String name, String category, int length) {
        this.name = name;
        this.category = category;
        this.length = length;
    }

    protected MovieProjection() {
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
