package com.example.scooterRentalApp.api.response;

import com.example.scooterRentalApp.api.BasicResponse;

public class CreateDockResponse extends BasicResponse {

    private Long dockId;

    public CreateDockResponse() {
    }

    public CreateDockResponse(String responseMsg, Long dockId) {
        super(responseMsg);
        this.dockId = dockId;
    }

    public Long getDockId() {
        return dockId;
    }

    public void setDockId(Long dockId) {
        this.dockId = dockId;
    }
}
