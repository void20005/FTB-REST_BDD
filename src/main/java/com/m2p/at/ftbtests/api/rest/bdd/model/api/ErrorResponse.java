
package com.m2p.at.ftbtests.api.rest.bdd.model.api;

import java.util.List;

public class ErrorResponse {
    private String timestamp;
    private String status;
    private String message;
    private List<String> details;

    public String getTimestamp() {
        return timestamp;
    }

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }


    public List<String> getDetails() {
        return details;
    }

}