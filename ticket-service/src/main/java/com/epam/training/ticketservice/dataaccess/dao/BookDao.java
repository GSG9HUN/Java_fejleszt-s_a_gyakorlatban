package com.epam.training.ticketservice.dataaccess.dao;

import com.epam.training.ticketservice.dataaccess.projection.BookProjection;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookDao extends JpaRepository<BookProjection,String> {
}
