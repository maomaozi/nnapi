package com.thoughtworks.nnapi.model;


public class GeneralResponse {
    private Boolean status;
    private String message;

    public GeneralResponse(Boolean status, String message) {
        this.status = status;
        this.message = message;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
