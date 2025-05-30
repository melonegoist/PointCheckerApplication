package ru.melon_egoist.auth;

public class ValidateResponse {
    private boolean isValidated;
    private String username;

    public ValidateResponse(boolean isValidated, String username) {
        this.username = username;
        this.isValidated = isValidated;
    }

    public ValidateResponse(boolean isValidated) {
        this.isValidated = isValidated;
    }

    public boolean isValidated() {
        return isValidated;
    }

    public void setValidated(boolean validated) {
        isValidated = validated;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
