package com.epam.training.ticketservice.controllers;

import com.epam.training.ticketservice.modell.AdminAccount;
import com.epam.training.ticketservice.modell.Book;
import com.epam.training.ticketservice.services.BookService;
import com.epam.training.ticketservice.services.DescribeAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.time.LocalDateTime;
import java.util.List;

@ShellComponent
public class DescribeAccountController {

    private final DescribeAccountService describeAccountService;
    private final BookService bookService;
    private final int bookPrice = 1500;

    @Autowired
    DescribeAccountController(DescribeAccountService describeAccountService, BookService bookService) {
        this.describeAccountService = describeAccountService;
        this.bookService = bookService;
    }

    @ShellMethod(value = "Describe account", key = "describe account")
    public String describeAccount() {
        if (describeAccountService.describe()) {
            return "Signed in with privileged account '" + AdminAccount.getUsername() + "'";
        }

        if (describeAccountService.describeUser()) {
            System.out.println("Signed in with account '" + describeAccountService.getAccount() + "'");
            List<Book> bookings = bookService.findAllBookingsForAccount(describeAccountService.getAccount());
            if (bookings.isEmpty()) {
                return "You have not booked any tickets yet";
            }
            System.out.println("Your previous bookings are");
            System.out.print("Seats ");
            int counter = 0;
            StringBuilder result = new StringBuilder();
            for (Book book : bookings) {
                result.append("(").append(book.getRowNum()).append(",").append(book.getColNum()).append("), ");
                counter++;
            }
            Book firstBooking = bookings.get(0);
            LocalDateTime screeningDate = firstBooking.getScreeningDate();
            result.delete(result.length() - 2, result.length() - 1);
            System.out.print(result + " on " + firstBooking.getMovieName() + " in room "
                    + firstBooking.getRoomName() + " starting at ");
            System.out.print(screeningDate.getYear() + "-" + screeningDate.getMonth()
                    + "-" + screeningDate.getDayOfMonth() + " " + screeningDate.getHour()
                    + ":" + screeningDate.getMinute() + " for " + bookPrice * counter + " HUF\n");
            return null;
        }

        return "You are not signed in";
    }

}
