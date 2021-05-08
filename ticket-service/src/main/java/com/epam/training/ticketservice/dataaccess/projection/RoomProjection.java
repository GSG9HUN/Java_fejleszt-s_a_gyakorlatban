package com.epam.training.ticketservice.dataaccess.projection;


import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class RoomProjection {
    @Id
    private String name;
    private int rowNum;
    private int colNum;


    public RoomProjection(String name, int rowNum, int colNum) {
        this.name = name;
        this.rowNum = rowNum;
        this.colNum = colNum;
    }

    protected RoomProjection() {
    }

    public String getName() {
        return name;
    }

    public int getRowNum() {
        return rowNum;
    }

    public int getColNum() {
        return colNum;
    }

    public void setColNum(int colNum) {
        this.colNum = colNum;
    }

    public void setRowNum(int rowNum) {
        this.rowNum = rowNum;
    }
}
