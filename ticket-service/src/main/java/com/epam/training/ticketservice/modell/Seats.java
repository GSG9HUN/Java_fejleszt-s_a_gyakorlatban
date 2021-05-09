package com.epam.training.ticketservice.modell;

public class Seats {

    private final int rowNum;
    private final int colNum;

    public Seats(int rowNum, int colNum) {
        this.colNum = colNum;
        this.rowNum = rowNum;
    }

    public int getRowNum() {
        return rowNum;
    }

    public int getColNum() {
        return colNum;
    }
}
