package com.example.scooterRentalApp.repository;

import com.example.scooterRentalApp.model.RentHistory;
import com.example.scooterRentalApp.model.Scooter;
import com.example.scooterRentalApp.model.UserAccount;
import org.springframework.data.repository.CrudRepository;

public interface ScooterRentRepository extends CrudRepository<RentHistory, Long> {
    RentHistory findFirstByUserAccountAndScooterOrderByIdDesc(UserAccount userAccount, Scooter scooter);
}
