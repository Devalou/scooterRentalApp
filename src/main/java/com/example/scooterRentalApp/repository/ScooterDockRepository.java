package com.example.scooterRentalApp.repository;

import com.example.scooterRentalApp.model.ScooterDock;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScooterDockRepository extends JpaRepository<ScooterDock, Long> {
    List<ScooterDock> findByDockName(String dockName);
}
