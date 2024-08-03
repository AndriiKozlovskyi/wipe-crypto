package org.example.event.dto;

public class BaseApiResponse<T> {
    private T data;
    private String errorMessage;
    private int statusCode;

    // Constructors, getters, and setters

    public BaseApiResponse() {}

    public BaseApiResponse(T data, String errorMessage, int statusCode) {
        this.data = data;
        this.errorMessage = errorMessage;
        this.statusCode = statusCode;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }
}