package com.epam.training.ticketservice.modell;

import java.time.LocalDateTime;

public class Book {

    private String accountName;
    private String movieName;
    private String roomName;
    private LocalDateTime screeningDate;
    private int rowNum;
    private int colNum;

    public Book(String accountName, String movieName, String roomName,
                LocalDateTime screeningDate, int rowNum, int colNum) {
        this.accountName = accountName;
        this.movieName = movieName;
        this.roomName = roomName;
        this.screeningDate = screeningDate;
        this.rowNum = rowNum;
        this.colNum = colNum;
    }

    public String getAccountName() {
        return accountName;
    }

    public String getMovieName() {
        return movieName;
    }

    public String getRoomName() {
        return roomName;
    }

    public LocalDateTime getScreeningDate() {
        return screeningDate;
    }

    public int getRowNum() {
        return rowNum;
    }

    public int getColNum() {
        return colNum;
    }
}
