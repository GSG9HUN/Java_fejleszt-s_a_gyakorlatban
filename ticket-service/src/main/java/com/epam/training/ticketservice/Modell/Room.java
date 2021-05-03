package com.epam.training.ticketservice.Modell;

public class Room {


    private String name;
    private int rowNum;
    private int colNum;

    public Room(String name, int rowNum, int colNum){
        this.name=name;
        this.rowNum=rowNum;
        this.colNum=colNum;
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

}
