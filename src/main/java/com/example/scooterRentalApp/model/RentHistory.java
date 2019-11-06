package com.example.scooterRentalApp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class RentHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private LocalDateTime startRent;
    private LocalDateTime endRent;

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "scooter_id", referencedColumnName = "id")
    private Scooter scooter;

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_account_id", referencedColumnName = "id")
    private UserAccount userAccount;

    public RentHistory() {
    }

    public RentHistory(LocalDateTime startRent, LocalDateTime endRent, Scooter scooter, UserAccount userAccount) {
        this.startRent = startRent;
        this.endRent = endRent;
        this.scooter = scooter;
        this.userAccount = userAccount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getStartRent() {
        return startRent;
    }

    public void setStartRent(LocalDateTime startRent) {
        this.startRent = startRent;
    }

    public LocalDateTime getEndRent() {
        return endRent;
    }

    public void setEndRent(LocalDateTime endRent) {
        this.endRent = endRent;
    }

    public Scooter getScooter() {
        return scooter;
    }

    public void setScooter(Scooter scooter) {
        this.scooter = scooter;
    }

    public UserAccount getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(UserAccount userAccount) {
        this.userAccount = userAccount;
    }
}
