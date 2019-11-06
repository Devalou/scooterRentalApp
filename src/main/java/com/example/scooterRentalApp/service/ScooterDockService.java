package com.example.scooterRentalApp.service;

import com.example.scooterRentalApp.api.BasicResponse;
import com.example.scooterRentalApp.api.request.CreateDockRequest;
import com.example.scooterRentalApp.api.response.CreateDockResponse;
import com.example.scooterRentalApp.model.Scooter;
import org.springframework.http.ResponseEntity;

import java.util.Set;

public interface ScooterDockService {

    ResponseEntity<Set<Scooter>> getAllDockScooters(Long scooterDockId);

    ResponseEntity<BasicResponse> returnScooter(Long scooterDockId, Long scooterId);

    ResponseEntity<BasicResponse> deleteScooter(Long scooterDockId, Long scooterId);

    ResponseEntity<CreateDockResponse> createDock(CreateDockRequest request);
}
