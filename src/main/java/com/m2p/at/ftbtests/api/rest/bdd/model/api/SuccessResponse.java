package com.m2p.at.ftbtests.api.rest.bdd.model.api;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SuccessResponse {
    private int code;
    private String message;
    private Details details;

    // Getters и Setters

    public static class Details {
        @JsonProperty("Id") // Указываем правильное имя поля из JSON
        private String id;

        // Getters и Setters
        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Details getDetails() {
        return details;
    }

    public void setDetails(Details details) {
        this.details = details;
    }
}
