package com.epam.training.ticketservice.dataaccess.dao;

import com.epam.training.ticketservice.dataaccess.projection.ScreenProjection;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ScreenDao extends JpaRepository<ScreenProjection, UUID> {
}
