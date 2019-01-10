package com.thoughtworks.nnapi.model;


public class GeneralResponse {
    private Boolean status;
    private String Message;

    public GeneralResponse(Boolean status, String message) {
        this.status = status;
        Message = message;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public Boolean getStatus() {
        return status;
    }

    public String getMessage() {
        return Message;
    }
}
