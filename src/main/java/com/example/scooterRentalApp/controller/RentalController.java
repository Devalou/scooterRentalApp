package com.example.scooterRentalApp.controller;

import com.example.scooterRentalApp.api.BasicResponse;
import com.example.scooterRentalApp.service.RentalService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("rental")
public class RentalController {

    private RentalService rentalService;

    public RentalController(RentalService rentalService) {
        this.rentalService = rentalService;
    }

    @PutMapping(value = "/{scooterId}/scooter", produces = "application/json")
    public ResponseEntity<BasicResponse> createUserAccount(
            @PathVariable Long scooterId,
            @RequestParam Long accountId
    ) {
        return rentalService.rentScooter(scooterId, accountId);
    }

}
