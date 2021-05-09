package com.epam.training.ticketservice.dataaccess.projection;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
public class BookProjection {

    @Id
    @GeneratedValue
    private UUID id;
    private String accountName;
    private String movieName;
    private String roomName;
    private LocalDateTime screeningDate;
    private int rowNum;
    private int colNum;

    public BookProjection(String accountName, String movieName, String roomName,
                          LocalDateTime screeningDate, int rowNum, int colNum) {
        this.accountName = accountName;
        this.movieName = movieName;
        this.roomName = roomName;
        this.screeningDate = screeningDate;
        this.rowNum = rowNum;
        this.colNum = colNum;
    }

    protected BookProjection() {
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
