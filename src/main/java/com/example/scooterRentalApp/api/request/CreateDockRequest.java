package com.example.scooterRentalApp.api.request;

import com.example.scooterRentalApp.api.BasicResponse;

public class CreateDockRequest extends BasicResponse {

    private String dockName;
    private Integer availablePlace;

    public String getDockName() {
        return dockName;
    }

    public void setDockName(String dockName) {
        this.dockName = dockName;
    }

    public Integer getAvailablePlace() {
        return availablePlace;
    }

    public void setAvailablePlace(Integer availablePlace) {
        this.availablePlace = availablePlace;
    }
}
